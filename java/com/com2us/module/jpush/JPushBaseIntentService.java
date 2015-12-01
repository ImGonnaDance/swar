package com.com2us.module.jpush;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;
import com.com2us.module.push.JPushIntentService;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;

public abstract class JPushBaseIntentService extends IntentService {
    private static final Object LOCK = JPushIntentService.class;
    public static final String TAG = "JPushIntentService";
    private static final String WAKELOCK_KEY = "JPUSH_LIB";
    private static Logger logger = LoggerGroup.createLogger(TAG);
    private static int sCounter = 0;
    private static WakeLock sWakeLock;

    protected abstract void onMessage(Context context, Intent intent);

    public JPushBaseIntentService(String name) {
        StringBuilder append = new StringBuilder("JPushIntentService-").append(name).append("-");
        int i = sCounter + 1;
        sCounter = i;
        super(append.append(i).toString());
    }

    protected void onHandleIntent(Intent intent) {
        try {
            Context context = getApplicationContext();
            String action = intent.getAction();
            logger.d("onHandleIntent. action : " + action);
            if (action.equals(JPushInterface.ACTION_REGISTRATION_ID)) {
                handleRegistration(context, intent);
            } else if (action.equals(JPushInterface.ACTION_CONNECTION_CHANGE)) {
                handleConnection(context, intent);
            } else if (action.equals(JPushInterface.ACTION_NOTIFICATION_RECEIVED)) {
                onMessage(context, intent);
            }
            synchronized (LOCK) {
                if (sWakeLock != null) {
                    logger.v("Releasing wakelock");
                    sWakeLock.release();
                } else {
                    logger.e("Wakelock reference is null");
                }
            }
        } catch (Throwable th) {
            synchronized (LOCK) {
                if (sWakeLock != null) {
                    logger.v("Releasing wakelock");
                    sWakeLock.release();
                } else {
                    logger.e("Wakelock reference is null");
                }
            }
        }
    }

    private void handleRegistration(Context context, Intent intent) {
        String registrationId = intent.getStringExtra(JPushInterface.EXTRA_REGISTRATION_ID);
        logger.d("handleRegistration: registrationId = " + registrationId);
        if (TextUtils.isEmpty(registrationId)) {
            logger.e("JPush Registration id is null or empty");
        } else if (JPushInterface.getRegistrationID(context).equals(registrationId)) {
            try {
                JPush.getInstance(context).setRegId(registrationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            logger.e(JPushReceiver.TAG, "JPush Registration in received intent is not equal with the one getRegistrationID returned.");
        }
    }

    private void handleConnection(Context context, Intent intent) {
        Boolean connectionState = Boolean.valueOf(intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false));
        logger.d("handleConnection: connectionChanged to " + connectionState.toString());
        if (connectionState.booleanValue()) {
            String registrationId = JPushInterface.getRegistrationID(context);
            if (TextUtils.isEmpty(registrationId)) {
                logger.e("JPush Connection state is true, but regId is null or empty");
                return;
            }
            try {
                JPush.getInstance(context).setRegId(registrationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (connectionState.booleanValue()) {
            logger.i("JPush Connection status has been change to false.");
        }
    }

    static void runIntentInService(Context context, Intent intent, String className) {
        if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") == 0) {
            synchronized (LOCK) {
                if (sWakeLock == null) {
                    sWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, WAKELOCK_KEY);
                }
            }
            logger.v("Acquiring wakelock");
            sWakeLock.acquire();
        }
        intent.setClassName(context, className);
        context.startService(intent);
    }

    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }
}
