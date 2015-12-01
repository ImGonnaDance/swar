package com.com2us.module.push;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.com2us.module.manager.AppStateAdapter;
import com.com2us.module.manager.Modulable;
import com.com2us.module.push.ThirdPartyPush.ThirdPartyPushType;
import com.com2us.module.push.unityplugin.PushUnityPlugin;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.view.SurfaceViewWrapper;
import com.com2us.peppermint.PeppermintConstant;
import com.facebook.internal.ServerProtocol;
import com.google.android.gcm.GCMRegistrar;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONException;
import org.json.JSONObject;

public class Push extends AppStateAdapter implements Modulable {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState = null;
    private static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType = null;
    @Deprecated
    public static final int DEFAULT_FALSE = 0;
    @Deprecated
    public static final int DEFAULT_TRUE = 1;
    public static final int GCMPush = 1;
    public static final int LocalPush = 2;
    public static final int USER_FALSE = 2;
    public static final int USER_TRUE = 3;
    private static String appid = i.a;
    private static SurfaceViewWrapper glView = null;
    private static int isPush = GCMPush;
    private static int isSound = GCMPush;
    private static boolean isStarted = false;
    private static int isVib = GCMPush;
    private static Push mPush = null;
    private static PushCallback pushCallback = null;
    private static int pushCallbackRef = DEFAULT_FALSE;
    protected static RegisterState registerState = RegisterState.REGISTER;
    private static Thread sendThread = null;
    protected static String senderID = i.a;
    public Context context = null;
    protected String[] extendSenderIDs = null;
    private PropertyUtil propertyUtil = null;
    private ThirdPartyPushManager tppm = null;
    protected String[] uniqueSenderIDs = null;

    class AnonymousClass4 implements Runnable {
        private final /* synthetic */ int val$cbPushID;
        private final /* synthetic */ int val$cbremainPushCallback;
        private final /* synthetic */ boolean val$dummyIsGCMPush;

        AnonymousClass4(boolean z, int i, int i2) {
            this.val$dummyIsGCMPush = z;
            this.val$cbPushID = i;
            this.val$cbremainPushCallback = i2;
        }

        public void run() {
            PushConfig.LogI("send glView Callback");
            if (this.val$dummyIsGCMPush) {
                Push.jniPushCallback(Push.pushCallbackRef, Push.GCMPush, this.val$cbPushID, this.val$cbremainPushCallback);
            } else {
                Push.jniPushCallback(Push.pushCallbackRef, Push.USER_FALSE, this.val$cbPushID, this.val$cbremainPushCallback);
            }
        }
    }

    protected enum RegisterState {
        SENDER,
        REGISTER,
        UNREGISTER,
        LOGIN,
        LOGOUT
    }

    private static native void jniPushCallback(int i, int i2, int i3, int i4);

    private static native void jniPushInitialize(Push push);

    private static native void jniPushUninitialize();

    static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState() {
        int[] iArr = $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState;
        if (iArr == null) {
            iArr = new int[RegisterState.values().length];
            try {
                iArr[RegisterState.LOGIN.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[RegisterState.LOGOUT.ordinal()] = 5;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[RegisterState.REGISTER.ordinal()] = USER_FALSE;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[RegisterState.SENDER.ordinal()] = GCMPush;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[RegisterState.UNREGISTER.ordinal()] = USER_TRUE;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType() {
        int[] iArr = $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType;
        if (iArr == null) {
            iArr = new int[ThirdPartyPushType.values().length];
            try {
                iArr[ThirdPartyPushType.AmazonPush.ordinal()] = USER_FALSE;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[ThirdPartyPushType.JPush.ordinal()] = USER_TRUE;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[ThirdPartyPushType.None.ordinal()] = GCMPush;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType = iArr;
        }
        return iArr;
    }

    public static Push getInstance(Context context) {
        PushConfig.LogI("Push getInstance");
        if (mPush == null) {
            mPush = new Push(context);
        }
        return mPush;
    }

    private Push(Context context) {
        Log.i(PushConfig.LOG_TAG, "Push v3.0.1");
        this.context = context.getApplicationContext();
        this.propertyUtil = PropertyUtil.getInstance(this.context);
        this.propertyUtil.loadProperty();
        appid = this.propertyUtil.getAppID();
        appid = TextUtils.isEmpty(appid) ? this.context.getPackageName() : appid;
        RemoteLogging.getInstance().collectAndSendLog(this.context);
        PushConfig.setBadge(this.context, DEFAULT_FALSE);
        loadConfig(this.context);
    }

    public void start() {
        PushConfig.LogI("[Push] Start.");
        try {
            sendThread = new Thread(new Runnable() {
                public void run() {
                    Push.sendToServer(Push.this.context, RegisterState.SENDER, null);
                    Push.isStarted = true;
                    Push.this.setPush(Push.isPush);
                    Push.sendCallback(Push.this.context);
                }
            });
            sendThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUseThirdPartyPush(boolean isUseThirdPartyPush) {
        if (isUseThirdPartyPush) {
            PushConfig.LogI("ThirdPartyPush enable.");
            this.tppm = ThirdPartyPushManager.getInstance(this.context);
            return;
        }
        PushConfig.LogI("ThirdPartyPush disable.");
        this.tppm = null;
    }

    public boolean setExtendsSenderIDs(String... senderIDs) {
        if (senderIDs == null || senderIDs.length == 0) {
            PushConfig.LogI("No senderIds");
            return false;
        }
        PushConfig.LogI("setExtendsSenderIDs : " + Arrays.toString(senderIDs));
        this.extendSenderIDs = senderIDs;
        return true;
    }

    public void setUseCLibrary(SurfaceViewWrapper glView) {
        PushConfig.LogI("setUseCLibrary");
        glView = glView;
        jniPushInitialize(this);
    }

    public void setVID(String vid) {
        PushConfig.LogI("setVID");
        PushConfig.LogI("VID : " + vid);
        PushConfig.setVID(vid);
        registerState = TextUtils.isEmpty(vid) ? RegisterState.LOGOUT : RegisterState.LOGIN;
        String storedVID = this.propertyUtil.getVID();
        if (!isStarted) {
            PushConfig.LogI("registration ignored. isStarted : " + isStarted);
        } else if (TextUtils.isEmpty(storedVID) || !TextUtils.equals(storedVID, vid)) {
            if (GCMRegistrar.isRegistered(this.context)) {
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            PushConfig.LogI("start registrationThreadForVID");
                            Push.sendToServer(Push.this.context, Push.registerState, GCMRegistrar.getRegistrationId(Push.this.context));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            new Thread(new Runnable() {
                public void run() {
                    ThirdPartyPushManager.getInstance(Push.this.context).sendRegId(RegisterState.REGISTER);
                }
            }).start();
        } else {
            PushConfig.LogI("registration ignored. stored VID : " + storedVID + ", input VID : " + vid);
        }
    }

    public String getVersion() {
        PushConfig.LogI("getVersion : 3.0.1");
        return PushConfig.VERSION;
    }

    public void setUseTestServer(boolean useTestServer) {
        if (useTestServer) {
            setLogged(true);
        }
        PushConfig.LogI("setUseTestServer : " + useTestServer);
        PushConfig.isUseTestServer = useTestServer;
        this.propertyUtil.setProperty("isUseTestServer", useTestServer ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        this.propertyUtil.storeProperty(null);
        if (PushConfig.isUseTestServer) {
            Log.i(PushConfig.LOG_TAG, "TestServer is Active.");
        }
    }

    public synchronized void setPush(int push) {
        PushConfig.LogI("setPush : " + push);
        isPush = push;
        this.propertyUtil.setProperty("isPush", Integer.toString(isPush));
        this.propertyUtil.storeProperty(null);
        switch (push) {
            case DEFAULT_FALSE /*0*/:
            case USER_FALSE /*2*/:
                unRegistrationAccount(this.context);
                break;
            case GCMPush /*1*/:
            case USER_TRUE /*3*/:
                registrationAccount(this.context);
                break;
        }
        PushConfig.LogI("storedPush : " + isPush);
    }

    public synchronized void setSound(int sound) {
        PushConfig.LogI("setSound : " + sound);
        isSound = sound;
        this.propertyUtil.setProperty("isSound", Integer.toString(isSound));
        this.propertyUtil.storeProperty(null);
        PushConfig.LogI("storedSound : " + isSound);
    }

    public synchronized void setVib(int vib) {
        PushConfig.LogI("setVib : " + vib);
        isVib = vib;
        this.propertyUtil.setProperty("isVib", Integer.toString(isVib));
        this.propertyUtil.storeProperty(null);
        PushConfig.LogI("storedVib : " + isVib);
    }

    public int getPush() {
        PushConfig.LogI("getPush : " + isPush);
        return isPush;
    }

    public int getSound() {
        PushConfig.LogI("getSound : " + isSound);
        return isSound;
    }

    public int getVib() {
        PushConfig.LogI("getVib : " + isVib);
        return isVib;
    }

    public synchronized void registerLocalpush(String jsonStringPushData) {
        PushConfig.LogI("registerLocalpush (jsonStringPushData) : " + jsonStringPushData);
        JSONObject pushData = null;
        if (!TextUtils.isEmpty(jsonStringPushData)) {
            try {
                pushData = new JSONObject(jsonStringPushData);
            } catch (Exception e) {
                PushConfig.LogI("registerLocalpush String param Exception : " + jsonStringPushData);
                PushConfig.LogI(e.toString());
            }
            if (pushData != null) {
                registerLocalpush(pushData);
            }
        }
    }

    public synchronized void registerLocalpush(JSONObject jsonPushData) {
        PushConfig.LogI("registerLocalpush (jsonPushData) : " + jsonPushData);
        if (jsonPushData != null) {
            int noticeID = GCMPush;
            try {
                noticeID = jsonPushData.getInt("noticeID");
            } catch (JSONException e) {
                String jSONObject = jsonPushData.toString();
                PushConfig.LogI("registerLocalpush JSONObject param Exception : " + r22);
                PushConfig.LogI(e.toString());
            }
            registerLocalpush(noticeID, jsonPushData.optString("title"), jsonPushData.optString("msg"), jsonPushData.optString("bigmsg"), jsonPushData.optString("ticker"), jsonPushData.optString(PeppermintConstant.JSON_KEY_TYPE), jsonPushData.optString("icon"), jsonPushData.optString("sound"), jsonPushData.optString("active"), jsonPushData.optLong("after", 0), jsonPushData.optString("broadcastAction"), jsonPushData.optInt("buckettype", DEFAULT_FALSE), jsonPushData.optInt("bucketsize", GCMPush), jsonPushData.optString("bigpicture"), jsonPushData.optString("icon_color"));
        }
    }

    public synchronized void registerLocalpush(Bundle pushData) {
        PushConfig.LogI("registerLocalpush (pushData) : " + pushData);
        registerLocalpush(pushData.getInt("noticeID", GCMPush), pushData.getString("title"), pushData.getString("msg"), pushData.getString("bigmsg"), pushData.getString("ticker"), pushData.getString(PeppermintConstant.JSON_KEY_TYPE), pushData.getString("icon"), pushData.getString("sound"), pushData.getString("active"), pushData.getLong("after", 0), pushData.getString("broadcastAction"), pushData.getInt("buckettype", DEFAULT_FALSE), pushData.getInt("bucketsize", GCMPush), pushData.getString("bigpicture"), pushData.getString("icon_color"));
    }

    public synchronized void registerLocalpush(int noticeID, String title, String msg, String ticker, String type, String icon, String sound, String active, long after) {
        registerLocalpush(noticeID, title, msg, null, ticker, type, icon, sound, active, after, null, DEFAULT_FALSE, GCMPush, null, null);
    }

    public synchronized void registerLocalpush(int noticeID, String title, String msg, String ticker, String type, String icon, String sound, String active, long after, String broadcastAction) {
        registerLocalpush(noticeID, title, msg, null, ticker, type, icon, sound, active, after, broadcastAction, DEFAULT_FALSE, GCMPush, null, null);
    }

    public synchronized void registerLocalpush(int noticeID, String title, String msg, String bigmsg, String ticker, String type, String icon, String sound, String active, long after) {
        registerLocalpush(noticeID, title, msg, bigmsg, ticker, type, icon, sound, active, after, null, DEFAULT_FALSE, GCMPush, null, null);
    }

    public synchronized void registerLocalpush(int noticeID, String title, String msg, String bigmsg, String ticker, String type, String icon, String sound, String active, long after, String broadcastAction) {
        registerLocalpush(noticeID, title, msg, bigmsg, ticker, type, icon, sound, active, after, broadcastAction, DEFAULT_FALSE, GCMPush, null, null);
    }

    public synchronized void registerLocalpush(int noticeID, String title, String msg, String bigmsg, String ticker, String type, String icon, String sound, String active, long after, String broadcastAction, int buckettype, int bucketsize) {
        registerLocalpush(noticeID, title, msg, bigmsg, ticker, type, icon, sound, active, after, broadcastAction, buckettype, bucketsize, null, null);
    }

    public synchronized void registerLocalpush(int noticeID, String title, String msg, String bigmsg, String ticker, String type, String icon, String sound, String active, long after, String broadcastAction, int buckettype, int bucketsize, String bigpicture) {
        registerLocalpush(noticeID, title, msg, bigmsg, ticker, type, icon, sound, active, after, broadcastAction, buckettype, bucketsize, bigpicture, null);
    }

    private synchronized void registerLocalpush(int noticeID, String title, String msg, String bigmsg, String ticker, String type, String icon, String sound, String active, long after, String broadcastAction, int buckettype, int bucketsize, String bigpicture, String icon_color) {
        PushConfig.LogI("registerLocalpush start : " + noticeID);
        if (!(isPush == 0 || isPush == USER_FALSE)) {
            Intent intent = new Intent(this.context.getApplicationContext(), LocalPushReceiver.class);
            intent.setAction(i.a);
            long elapsedRealtime = SystemClock.elapsedRealtime();
            long savedCurrentTime = System.currentTimeMillis();
            long triggerAtTime = after * 1000;
            LinkedHashMap<String, PushResource> map = PushResourceHandler.load(this.context.getApplicationContext());
            String valueOf = String.valueOf(noticeID);
            LinkedHashMap<String, PushResource> linkedHashMap = map;
            String str = valueOf;
            linkedHashMap.put(str, new PushResource(String.valueOf(noticeID), title, msg, bigmsg, ticker, String.valueOf(noticeID), type, icon, sound, active, this.context.getPackageName(), elapsedRealtime, savedCurrentTime, triggerAtTime, broadcastAction, buckettype, bucketsize, bigpicture, icon_color));
            PushResourceHandler.save(this.context.getApplicationContext(), map);
            AlarmManager alarmManager = (AlarmManager) this.context.getApplicationContext().getSystemService("alarm");
            alarmManager.set(USER_FALSE, elapsedRealtime + triggerAtTime, PendingIntent.getBroadcast(this.context.getApplicationContext(), noticeID, PushResourceHandler.putIntentExtra(intent, String.valueOf(noticeID), map), 134217728));
        }
    }

    public synchronized void unRegisterLocalpush(int pushID) {
        PushConfig.LogI("unRegisterLocalpush start : " + pushID);
        PendingIntent operation = PendingIntent.getBroadcast(this.context.getApplicationContext(), pushID, new Intent(this.context.getApplicationContext(), LocalPushReceiver.class), 134217728);
        AlarmManager manager = (AlarmManager) this.context.getSystemService("alarm");
        LinkedHashMap<String, PushResource> map = PushResourceHandler.load(this.context.getApplicationContext());
        map.remove(String.valueOf(pushID));
        PushResourceHandler.save(this.context.getApplicationContext(), map);
        manager.cancel(operation);
    }

    public synchronized void unRegisterAllLocalpush() {
        PushConfig.LogI("unRegisterAllLocalpush start");
        for (PushResource pushResource : PushResourceHandler.load(this.context.getApplicationContext()).values()) {
            unRegisterLocalpush(Integer.valueOf(pushResource.pushID).intValue());
        }
    }

    public synchronized void registerCallbackHandler(int _pushCallbackRef) {
        PushConfig.LogI("registerCallbackHandler");
        pushCallback = null;
        pushCallbackRef = _pushCallbackRef;
        if (isStarted) {
            sendCallback(this.context);
        } else {
            PushConfig.LogI("is not started");
        }
    }

    public synchronized void registerUnityCallbackHandler(String objName) {
        PushConfig.LogI("registerUnityCallbackHandler");
        PushUnityPlugin.getInstance().registerUnityCallbackHandler(this.context, objName);
    }

    public synchronized void registerCallbackHandler(PushCallback _pushCallback) {
        PushConfig.LogI("registerCallbackHandler");
        pushCallback = _pushCallback;
        pushCallbackRef = DEFAULT_FALSE;
        if (isStarted) {
            sendCallback(this.context);
        } else {
            PushConfig.LogI("is not started");
        }
    }

    public synchronized void unRegisterUnityCallbackHandler() {
        unRegisterCallbackHandler();
    }

    public synchronized void unRegisterCallbackHandler() {
        PushConfig.LogI("unRegisterCallbackHandler");
        pushCallback = null;
        pushCallbackRef = DEFAULT_FALSE;
        if (!isStarted) {
            PushConfig.LogI("is not started");
        }
    }

    @Deprecated
    public synchronized void setOperationOnRunning(boolean b) {
        PushConfig.LogI("setOperationOnRunning : " + b);
        this.propertyUtil.setProperty("isOperation", b ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        this.propertyUtil.setProperty("isGCMOperation", b ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        this.propertyUtil.storeProperty(null);
    }

    public synchronized void setOperationLocalPushOnRunning(boolean b) {
        PushConfig.LogI("setOperationLocalPushOnRunning : " + b);
        this.propertyUtil.setProperty("isOperation", b ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        this.propertyUtil.storeProperty(null);
    }

    public synchronized void setOperationGCMPushOnRunning(boolean b) {
        PushConfig.LogI("setOperationGCMPushOnRunning : " + b);
        this.propertyUtil.setProperty("isGCMOperation", b ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        this.propertyUtil.storeProperty(null);
    }

    public synchronized boolean getOperationLocalPushOnRunning() {
        boolean isOperation;
        try {
            isOperation = !"false".equals(this.propertyUtil.getProperty("isOperation"));
        } catch (Exception e) {
            isOperation = true;
        }
        PushConfig.LogI("getOperationLocalPushOnRunning : " + isOperation);
        return isOperation;
    }

    public synchronized boolean getOperationGCMPushOnRunning() {
        boolean isOperation;
        try {
            isOperation = !"false".equals(this.propertyUtil.getProperty("isGCMOperation"));
        } catch (Exception e) {
            isOperation = true;
        }
        PushConfig.LogI("getOperationGCMPushOnRunning : " + isOperation);
        return isOperation;
    }

    public String getRegistrationId() {
        PushConfig.LogI("getRegistrationId");
        String regId = GCMRegistrar.getRegistrationId(this.context);
        PushConfig.LogI("RegistrationId : " + regId);
        return regId;
    }

    protected static synchronized void sendCallback(Context context) {
        synchronized (Push.class) {
            PushConfig.LogI("sendCallback");
            if (pushCallback == null && pushCallbackRef == 0) {
                PushConfig.LogI("sendCallback all null");
            } else {
                PropertyUtil propertyUtil = PropertyUtil.getInstance(context);
                propertyUtil.loadProperty();
                String receivedPush = propertyUtil.getProperty("receivedPush");
                PushConfig.LogI("receivedPushIDs : " + receivedPush);
                if (receivedPush != null && receivedPush.trim().length() >= GCMPush) {
                    propertyUtil.setProperty("receivedPush", i.a);
                    propertyUtil.storeProperty(null);
                    int pushID = -1;
                    StringTokenizer tokens = new StringTokenizer(receivedPush, "|");
                    while (tokens.hasMoreElements()) {
                        int remainPushCallback;
                        boolean z = false;
                        try {
                            String strPushID = tokens.nextToken();
                            if (strPushID.startsWith("gcm")) {
                                z = true;
                                pushID = Integer.valueOf(strPushID.substring(USER_TRUE)).intValue();
                            } else {
                                z = false;
                                pushID = Integer.valueOf(strPushID.substring(5)).intValue();
                            }
                            remainPushCallback = tokens.countTokens();
                        } catch (Exception e) {
                            e.printStackTrace();
                            PushConfig.LogI("Received PushID Error. : " + pushID);
                            pushID = -1;
                            remainPushCallback = -1;
                        }
                        if (pushCallback != null) {
                            if (z) {
                                pushCallback.onReceivedGCMPush(pushID, remainPushCallback);
                            } else {
                                pushCallback.onReceivedLocalPush(pushID, remainPushCallback);
                            }
                        } else if (pushCallbackRef == 0) {
                            PushConfig.LogI("CallbackHandler does not Exist.");
                        } else if (glView != null) {
                            boolean dummyIsGCMPush = z;
                            glView.queueEvent(new AnonymousClass4(dummyIsGCMPush, pushID, remainPushCallback));
                        } else {
                            PushConfig.LogI("SurfaceViewWrapper does not Exist.");
                        }
                    }
                }
            }
        }
    }

    private synchronized int registrationAccount(final Context context) {
        int i;
        PushConfig.LogI("registrationAccount");
        if (isStarted) {
            String regId;
            try {
                if (TextUtils.isEmpty(senderID)) {
                    new Thread(new Runnable() {
                        public void run() {
                            Push.sendToServer(context, RegisterState.SENDER, null);
                        }
                    }).start();
                } else {
                    String[] newSenderIDs;
                    GCMRegistrar.checkDevice(context);
                    GCMRegistrar.checkManifest(context);
                    regId = GCMRegistrar.getRegistrationId(context);
                    String[] oldSenderIDs = PushConfig.loadSenderIDs(context);
                    if (this.extendSenderIDs == null || this.extendSenderIDs.length == 0) {
                        String[] senderids = new String[GCMPush];
                        senderids[DEFAULT_FALSE] = senderID;
                        newSenderIDs = senderids;
                    } else {
                        newSenderIDs = (String[]) Arrays.copyOf(this.extendSenderIDs, this.extendSenderIDs.length + GCMPush);
                        newSenderIDs[newSenderIDs.length - 1] = senderID;
                    }
                    try {
                        this.uniqueSenderIDs = (String[]) new HashSet(Arrays.asList(newSenderIDs)).toArray(new String[DEFAULT_FALSE]);
                        PushConfig.LogI("set unique SenderIDs : " + Arrays.toString(this.uniqueSenderIDs));
                        Arrays.sort(this.uniqueSenderIDs);
                        PushConfig.LogI("registrationAccount - RegistrationId : " + regId);
                        PushConfig.LogI("registrationAccount - old senderIDs : " + Arrays.toString(oldSenderIDs));
                        PushConfig.LogI("registrationAccount - new senderIDs : " + Arrays.toString(this.uniqueSenderIDs));
                        boolean isArraysEquals = Arrays.equals(this.uniqueSenderIDs, oldSenderIDs);
                        PushConfig.LogI("registrationAccount - isArraysEquals : " + isArraysEquals);
                        if ((TextUtils.isEmpty(regId) || !isArraysEquals) && this.uniqueSenderIDs.length != 0) {
                            PushConfig.LogI("registrationAccount : request register");
                            GCMRegistrar.register(context, this.uniqueSenderIDs);
                        } else {
                            PushConfig.LogI("registrationAccount : Already registered");
                            if (!GCMRegistrar.isRegisteredOnServer(context) || isChangedLanguage(context)) {
                                sendToServer(context, registerState, regId);
                            }
                        }
                    } catch (Exception e) {
                        PushConfig.logger.w("Wrong SenderIDs : " + e.toString());
                        i = isPush;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                PushConfig.LogI("registrationAccount - Registeration failed : " + e2.toString());
            }
            try {
                if (this.propertyUtil.getProperty("AmazonPushRegistered").equals("false")) {
                    regId = this.propertyUtil.getProperty("AmazonPushRegId");
                    if (!TextUtils.isEmpty(regId)) {
                        sendToServer(context, registerState, regId);
                    }
                }
                if (this.propertyUtil.getProperty("JPushRegistered").equals("false")) {
                    regId = this.propertyUtil.getProperty("JPushRegId");
                    if (!TextUtils.isEmpty(regId)) {
                        sendToServer(context, registerState, regId);
                    }
                }
            } catch (Exception e3) {
            }
            try {
                if (this.tppm != null) {
                    this.tppm.resumePush();
                }
            } catch (Exception e4) {
            }
            i = isPush;
        } else {
            PushConfig.LogI("Request registrationAccount ignore, because before start() func.");
            i = isPush;
        }
        return i;
    }

    private synchronized int unRegistrationAccount(Context context) {
        int i;
        PushConfig.LogI("unRegistrationAccount");
        if (isStarted) {
            try {
                GCMRegistrar.checkDevice(context);
                GCMRegistrar.checkManifest(context);
                if (GCMRegistrar.isRegistered(context)) {
                    GCMRegistrar.unregister(context);
                } else {
                    PushConfig.LogI("unRegistrationAccount : Already unregistered");
                }
            } catch (Exception e) {
                e.printStackTrace();
                PushConfig.LogI("unRegistrationAccount - Unregisteration failed : " + e.toString());
            }
            try {
                if (this.tppm != null) {
                    this.tppm.stopPush();
                }
            } catch (Exception e2) {
            }
            i = isPush;
        } else {
            PushConfig.LogI("Request unRegistrationAccount ignore, because before start() func.");
            i = isPush;
        }
        return i;
    }

    private void loadConfig(Context context) {
        PushConfig.LogI("loadConfig");
        try {
            int i;
            isPush = this.propertyUtil.getProperty("isPush") == null ? GCMPush : Integer.parseInt(this.propertyUtil.getProperty("isPush"));
            isSound = this.propertyUtil.getProperty("isSound") == null ? GCMPush : Integer.parseInt(this.propertyUtil.getProperty("isSound"));
            if (this.propertyUtil.getProperty("isVib") == null) {
                i = GCMPush;
            } else {
                i = Integer.parseInt(this.propertyUtil.getProperty("isVib"));
            }
            isVib = i;
        } catch (Exception e) {
            e.printStackTrace();
            isPush = GCMPush;
            isSound = GCMPush;
            isVib = GCMPush;
        }
        PushConfig.LogI("isPush + isSound + isVib : " + isPush + " " + isSound + " " + isVib);
        LinkedHashMap<String, PushResource> map = PushResourceHandler.load(context.getApplicationContext());
        for (PushResource res : map.values()) {
            ((AlarmManager) context.getApplicationContext().getSystemService("alarm")).set(USER_FALSE, res.elapsedRealtime + res.triggerAtTime, PendingIntent.getBroadcast(context.getApplicationContext(), Integer.parseInt(res.noticeID), PushResourceHandler.putIntentExtra(new Intent(context.getApplicationContext(), LocalPushReceiver.class), res.noticeID, map), 134217728));
        }
        PushResourceHandler.save(context.getApplicationContext(), map);
    }

    private boolean isChangedLanguage(Context context) {
        PushConfig.LogI("isChangedLanguage");
        PropertyUtil propertyUtil = PropertyUtil.getInstance(context);
        String storedLanguage = propertyUtil.getProperty(PeppermintConstant.JSON_KEY_LANGUAGE);
        String storedCountry = propertyUtil.getProperty(PeppermintConstant.JSON_KEY_COUNTRY);
        String language = propertyUtil.getLanguage().toLowerCase(Locale.US);
        String country = propertyUtil.getCountry().toLowerCase(Locale.US);
        PushConfig.LogI("isChangedLanguage - storedLanguage : " + storedLanguage);
        PushConfig.LogI("isChangedLanguage - storedCountry : " + storedCountry);
        PushConfig.LogI("isChangedLanguage - language : " + language);
        PushConfig.LogI("isChangedLanguage - country : " + country);
        if (TextUtils.equals(storedLanguage, language) && TextUtils.equals(storedCountry, country)) {
            PushConfig.LogI("isChangedLanguage : false");
            return false;
        }
        propertyUtil.setProperty(PeppermintConstant.JSON_KEY_LANGUAGE, language);
        propertyUtil.setProperty(PeppermintConstant.JSON_KEY_COUNTRY, country);
        GCMRegistrar.setRegisteredOnServer(context, false);
        PushConfig.LogI("isChangedLanguage true");
        return true;
    }

    protected static synchronized int sendToServer(Context context, RegisterState sendType, String regId) {
        int sendToServer;
        synchronized (Push.class) {
            sendToServer = sendToServer(context, sendType, regId, ThirdPartyPushType.None);
        }
        return sendToServer;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static synchronized int sendToServer(android.content.Context r24, com.com2us.module.push.Push.RegisterState r25, java.lang.String r26, com.com2us.module.push.ThirdPartyPush.ThirdPartyPushType r27) {
        /*
        r21 = com.com2us.module.push.Push.class;
        monitor-enter(r21);
        r20 = new java.lang.StringBuilder;	 Catch:{ all -> 0x026c }
        r22 = "sendToServer sendType : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ all -> 0x026c }
        r0 = r20;
        r1 = r25;
        r20 = r0.append(r1);	 Catch:{ all -> 0x026c }
        r22 = " thirdPartyPushType : ";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ all -> 0x026c }
        r0 = r20;
        r1 = r27;
        r20 = r0.append(r1);	 Catch:{ all -> 0x026c }
        r20 = r20.toString();	 Catch:{ all -> 0x026c }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        r14 = 1;
        r18 = "";
        r13 = "";
        r15 = "";
        r8 = new org.json.JSONObject;	 Catch:{ all -> 0x026c }
        r8.<init>();	 Catch:{ all -> 0x026c }
        r20 = "connectivity";
        r0 = r24;
        r1 = r20;
        r3 = r0.getSystemService(r1);	 Catch:{ all -> 0x026c }
        r3 = (android.net.ConnectivityManager) r3;	 Catch:{ all -> 0x026c }
        r9 = r3.getActiveNetworkInfo();	 Catch:{ all -> 0x026c }
        if (r9 != 0) goto L_0x0056;
    L_0x004d:
        r20 = "Network Inactive";
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        r20 = 1;
    L_0x0054:
        monitor-exit(r21);
        return r20;
    L_0x0056:
        r20 = "Network Active";
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        r20 = com.com2us.module.push.PushConfig.isUseTestServer;	 Catch:{ all -> 0x026c }
        if (r20 == 0) goto L_0x0106;
    L_0x005f:
        r15 = "http://mdev.com2us.net/api/";
    L_0x0061:
        r20 = "appid";
        r22 = appid;	 Catch:{ Exception -> 0x0115 }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x0115 }
        r20 = $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState();	 Catch:{ all -> 0x026c }
        r22 = r25.ordinal();	 Catch:{ all -> 0x026c }
        r20 = r20[r22];	 Catch:{ all -> 0x026c }
        switch(r20) {
            case 1: goto L_0x0122;
            case 2: goto L_0x016a;
            case 3: goto L_0x02b9;
            case 4: goto L_0x016a;
            case 5: goto L_0x016a;
            default: goto L_0x0079;
        };
    L_0x0079:
        r6 = new org.apache.http.params.BasicHttpParams;	 Catch:{ Exception -> 0x0382 }
        r6.<init>();	 Catch:{ Exception -> 0x0382 }
        r20 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0 = r20;
        org.apache.http.params.HttpConnectionParams.setConnectionTimeout(r6, r0);	 Catch:{ Exception -> 0x0382 }
        r20 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
        r0 = r20;
        org.apache.http.params.HttpConnectionParams.setSoTimeout(r6, r0);	 Catch:{ Exception -> 0x0382 }
        r2 = new org.apache.http.impl.client.DefaultHttpClient;	 Catch:{ Exception -> 0x0382 }
        r2.<init>(r6);	 Catch:{ Exception -> 0x0382 }
        r7 = new org.apache.http.client.methods.HttpPost;	 Catch:{ Exception -> 0x0382 }
        r7.<init>(r15);	 Catch:{ Exception -> 0x0382 }
        r5 = new org.apache.http.entity.StringEntity;	 Catch:{ Exception -> 0x0382 }
        r20 = "UTF-8";
        r0 = r18;
        r1 = r20;
        r5.<init>(r0, r1);	 Catch:{ Exception -> 0x0382 }
        r20 = "Content-type";
        r22 = "text/html";
        r0 = r20;
        r1 = r22;
        r7.setHeader(r0, r1);	 Catch:{ Exception -> 0x0382 }
        r7.setEntity(r5);	 Catch:{ Exception -> 0x0382 }
        r11 = r2.execute(r7);	 Catch:{ Exception -> 0x0382 }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0382 }
        r22 = "response StatusCode : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0382 }
        r22 = r11.getStatusLine();	 Catch:{ Exception -> 0x0382 }
        r22 = r22.getStatusCode();	 Catch:{ Exception -> 0x0382 }
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0382 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0382 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0382 }
        r12 = r11.getEntity();	 Catch:{ Exception -> 0x0382 }
        r13 = org.apache.http.util.EntityUtils.toString(r12);	 Catch:{ Exception -> 0x0382 }
        r20 = $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState();	 Catch:{ Exception -> 0x0382 }
        r22 = r25.ordinal();	 Catch:{ Exception -> 0x0382 }
        r20 = r20[r22];	 Catch:{ Exception -> 0x0382 }
        switch(r20) {
            case 1: goto L_0x031d;
            case 2: goto L_0x03a9;
            case 3: goto L_0x03a9;
            case 4: goto L_0x03a9;
            case 5: goto L_0x03a9;
            default: goto L_0x00ea;
        };
    L_0x00ea:
        r20 = new java.lang.StringBuilder;	 Catch:{ all -> 0x026c }
        r22 = "responseStr : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ all -> 0x026c }
        r0 = r20;
        r20 = r0.append(r13);	 Catch:{ all -> 0x026c }
        r20 = r20.toString();	 Catch:{ all -> 0x026c }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        r20 = r14;
        goto L_0x0054;
    L_0x0106:
        r20 = com.com2us.module.push.Push.RegisterState.SENDER;	 Catch:{ all -> 0x026c }
        r0 = r25;
        r1 = r20;
        if (r0 != r1) goto L_0x0112;
    L_0x010e:
        r15 = "http://push.com2us.net/api/";
    L_0x0110:
        goto L_0x0061;
    L_0x0112:
        r15 = "https://push.com2us.net/api/";
        goto L_0x0110;
    L_0x0115:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ all -> 0x026c }
        r20 = "create json data Failed (appid)";
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        r20 = 1;
        goto L_0x0054;
    L_0x0122:
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x015d }
        r22 = java.lang.String.valueOf(r15);	 Catch:{ Exception -> 0x015d }
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x015d }
        r22 = "sender.php";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x015d }
        r15 = r20.toString();	 Catch:{ Exception -> 0x015d }
        r18 = r8.toString();	 Catch:{ Exception -> 0x015d }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x015d }
        r22 = "sender verifyString : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x015d }
        r0 = r20;
        r1 = r18;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x015d }
        r20 = r20.toString();	 Catch:{ Exception -> 0x015d }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x015d }
        goto L_0x0079;
    L_0x015d:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ all -> 0x026c }
        r20 = "sender create json data Failed";
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        r20 = 1;
        goto L_0x0054;
    L_0x016a:
        r10 = com.com2us.module.push.PushConfig.PropertyUtil.getInstance(r24);	 Catch:{ Exception -> 0x024d }
        r20 = $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType();	 Catch:{ Exception -> 0x024d }
        r22 = r27.ordinal();	 Catch:{ Exception -> 0x024d }
        r20 = r20[r22];	 Catch:{ Exception -> 0x024d }
        switch(r20) {
            case 1: goto L_0x017b;
            case 2: goto L_0x025a;
            case 3: goto L_0x026f;
            default: goto L_0x017b;
        };	 Catch:{ Exception -> 0x024d }
    L_0x017b:
        r19 = 0;
        r20 = $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState();	 Catch:{ Exception -> 0x024d }
        r22 = r25.ordinal();	 Catch:{ Exception -> 0x024d }
        r20 = r20[r22];	 Catch:{ Exception -> 0x024d }
        switch(r20) {
            case 2: goto L_0x0281;
            case 3: goto L_0x018a;
            case 4: goto L_0x0285;
            case 5: goto L_0x0289;
            default: goto L_0x018a;
        };	 Catch:{ Exception -> 0x024d }
    L_0x018a:
        r19 = "register";
    L_0x018c:
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x024d }
        r22 = java.lang.String.valueOf(r15);	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x024d }
        r22 = "token_reg.php";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x024d }
        r15 = r20.toString();	 Catch:{ Exception -> 0x024d }
        r20 = $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType();	 Catch:{ Exception -> 0x024d }
        r22 = r27.ordinal();	 Catch:{ Exception -> 0x024d }
        r20 = r20[r22];	 Catch:{ Exception -> 0x024d }
        switch(r20) {
            case 1: goto L_0x028d;
            case 2: goto L_0x02a3;
            case 3: goto L_0x02ae;
            default: goto L_0x01b4;
        };	 Catch:{ Exception -> 0x024d }
    L_0x01b4:
        r20 = "language";
        r22 = r10.getLanguage();	 Catch:{ Exception -> 0x024d }
        r23 = java.util.Locale.US;	 Catch:{ Exception -> 0x024d }
        r22 = r22.toLowerCase(r23);	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "country";
        r22 = r10.getCountry();	 Catch:{ Exception -> 0x024d }
        r23 = java.util.Locale.US;	 Catch:{ Exception -> 0x024d }
        r22 = r22.toLowerCase(r23);	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "device";
        r22 = r10.getDeviceModel();	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "libver";
        r22 = "3.0.1";
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "appver";
        r22 = r10.getAppVersion();	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "did";
        r22 = r10.getDID();	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "vid";
        r22 = r10.getVID();	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "vidstatus";
        r0 = r20;
        r1 = r19;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "os";
        r22 = "A";
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r18 = r8.toString();	 Catch:{ Exception -> 0x024d }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x024d }
        r22 = "register verifyString : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r18;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x024d }
        r20 = r20.toString();	 Catch:{ Exception -> 0x024d }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x024d }
        goto L_0x0079;
    L_0x024d:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ all -> 0x026c }
        r20 = "register create json data Failed";
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        r20 = 1;
        goto L_0x0054;
    L_0x025a:
        r20 = "AmazonPushRegId";
        r0 = r20;
        r1 = r26;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x024d }
        goto L_0x017b;
    L_0x026c:
        r20 = move-exception;
        monitor-exit(r21);
        throw r20;
    L_0x026f:
        r20 = "JPushRegId";
        r0 = r20;
        r1 = r26;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x024d }
        goto L_0x017b;
    L_0x0281:
        r19 = "register";
        goto L_0x018c;
    L_0x0285:
        r19 = "login";
        goto L_0x018c;
    L_0x0289:
        r19 = "logout";
        goto L_0x018c;
    L_0x028d:
        r20 = "gcm_token";
        r0 = r20;
        r1 = r26;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        r20 = "gcm_sender";
        r22 = senderID;	 Catch:{ Exception -> 0x024d }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        goto L_0x01b4;
    L_0x02a3:
        r20 = "amazon_token";
        r0 = r20;
        r1 = r26;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        goto L_0x01b4;
    L_0x02ae:
        r20 = "jpush_token";
        r0 = r20;
        r1 = r26;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x024d }
        goto L_0x01b4;
    L_0x02b9:
        r10 = com.com2us.module.push.PushConfig.PropertyUtil.getInstance(r24);	 Catch:{ Exception -> 0x0310 }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0310 }
        r22 = java.lang.String.valueOf(r15);	 Catch:{ Exception -> 0x0310 }
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0310 }
        r22 = "token_unreg.php";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0310 }
        r15 = r20.toString();	 Catch:{ Exception -> 0x0310 }
        r20 = "did";
        r22 = r10.getDID();	 Catch:{ Exception -> 0x0310 }
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x0310 }
        r20 = "os";
        r22 = "A";
        r0 = r20;
        r1 = r22;
        r8.put(r0, r1);	 Catch:{ Exception -> 0x0310 }
        r18 = r8.toString();	 Catch:{ Exception -> 0x0310 }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0310 }
        r22 = "unregister verifyString : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0310 }
        r0 = r20;
        r1 = r18;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0310 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0310 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0310 }
        goto L_0x0079;
    L_0x0310:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ all -> 0x026c }
        r20 = "unregister create json data Failed";
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        r20 = 1;
        goto L_0x0054;
    L_0x031d:
        r20 = android.text.TextUtils.isEmpty(r13);	 Catch:{ Exception -> 0x0360 }
        if (r20 != 0) goto L_0x00ea;
    L_0x0323:
        r17 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0360 }
        r0 = r17;
        r0.<init>(r13);	 Catch:{ Exception -> 0x0360 }
        r20 = "sender";
        r0 = r17;
        r1 = r20;
        r20 = r0.getString(r1);	 Catch:{ Exception -> 0x0360 }
        if (r20 == 0) goto L_0x03a6;
    L_0x0336:
        r20 = "sender";
        r0 = r17;
        r1 = r20;
        r20 = r0.getString(r1);	 Catch:{ Exception -> 0x0360 }
    L_0x0340:
        senderID = r20;	 Catch:{ Exception -> 0x0360 }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0360 }
        r22 = "senderAccount : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0360 }
        r22 = senderID;	 Catch:{ Exception -> 0x0360 }
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0360 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0360 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0360 }
        goto L_0x00ea;
    L_0x0360:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ Exception -> 0x0382 }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0382 }
        r22 = "senderAccount : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0382 }
        r22 = senderID;	 Catch:{ Exception -> 0x0382 }
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0382 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0382 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0382 }
        goto L_0x00ea;
    L_0x0382:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ all -> 0x026c }
        r20 = new java.lang.StringBuilder;	 Catch:{ all -> 0x026c }
        r20.<init>();	 Catch:{ all -> 0x026c }
        r0 = r20;
        r1 = r25;
        r20 = r0.append(r1);	 Catch:{ all -> 0x026c }
        r22 = " Send To Failed";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ all -> 0x026c }
        r20 = r20.toString();	 Catch:{ all -> 0x026c }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ all -> 0x026c }
        goto L_0x00ea;
    L_0x03a6:
        r20 = senderID;	 Catch:{ Exception -> 0x0360 }
        goto L_0x0340;
    L_0x03a9:
        r10 = com.com2us.module.push.PushConfig.PropertyUtil.getInstance(r24);	 Catch:{ Exception -> 0x0382 }
        r20 = android.text.TextUtils.isEmpty(r13);	 Catch:{ Exception -> 0x0412 }
        if (r20 != 0) goto L_0x04fc;
    L_0x03b3:
        r16 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0412 }
        r0 = r16;
        r0.<init>(r13);	 Catch:{ Exception -> 0x0412 }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0412 }
        r22 = "responseStr : ";
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0412 }
        r0 = r20;
        r20 = r0.append(r13);	 Catch:{ Exception -> 0x0412 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0412 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0412 }
        r20 = "errno";
        r0 = r16;
        r1 = r20;
        r20 = r0.getInt(r1);	 Catch:{ Exception -> 0x0412 }
        if (r20 != 0) goto L_0x0498;
    L_0x03de:
        r14 = 0;
        r20 = $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState();	 Catch:{ Exception -> 0x0412 }
        r22 = r25.ordinal();	 Catch:{ Exception -> 0x0412 }
        r20 = r20[r22];	 Catch:{ Exception -> 0x0412 }
        switch(r20) {
            case 2: goto L_0x044a;
            case 3: goto L_0x03ec;
            case 4: goto L_0x044a;
            case 5: goto L_0x044a;
            default: goto L_0x03ec;
        };	 Catch:{ Exception -> 0x0412 }
    L_0x03ec:
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0412 }
        r20.<init>();	 Catch:{ Exception -> 0x0412 }
        r0 = r20;
        r1 = r25;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0412 }
        r22 = " Send To Server OK : ";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0412 }
        r0 = r20;
        r20 = r0.append(r13);	 Catch:{ Exception -> 0x0412 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0412 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0412 }
        goto L_0x00ea;
    L_0x0412:
        r4 = move-exception;
        r4.printStackTrace();	 Catch:{ Exception -> 0x0382 }
        r20 = $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType();	 Catch:{ Exception -> 0x0382 }
        r22 = r27.ordinal();	 Catch:{ Exception -> 0x0382 }
        r20 = r20[r22];	 Catch:{ Exception -> 0x0382 }
        switch(r20) {
            case 1: goto L_0x0560;
            case 2: goto L_0x056b;
            case 3: goto L_0x057f;
            default: goto L_0x0423;
        };	 Catch:{ Exception -> 0x0382 }
    L_0x0423:
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0382 }
        r20.<init>();	 Catch:{ Exception -> 0x0382 }
        r0 = r20;
        r1 = r25;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0382 }
        r22 = " Send To Failed : ";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0382 }
        r0 = r20;
        r20 = r0.append(r13);	 Catch:{ Exception -> 0x0382 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0382 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0382 }
        r14 = 1;
        goto L_0x00ea;
    L_0x044a:
        r20 = $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType();	 Catch:{ Exception -> 0x0412 }
        r22 = r27.ordinal();	 Catch:{ Exception -> 0x0412 }
        r20 = r20[r22];	 Catch:{ Exception -> 0x0412 }
        switch(r20) {
            case 1: goto L_0x0468;
            case 2: goto L_0x0472;
            case 3: goto L_0x0485;
            default: goto L_0x0457;
        };	 Catch:{ Exception -> 0x0412 }
    L_0x0457:
        r20 = com.com2us.module.push.PushConfig.getVID(r24, r25);	 Catch:{ Exception -> 0x0412 }
        r0 = r20;
        r10.setVID(r0);	 Catch:{ Exception -> 0x0412 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0412 }
        goto L_0x03ec;
    L_0x0468:
        r20 = 0;
        r0 = r24;
        r1 = r20;
        com.google.android.gcm.GCMRegistrar.setRegisteredOnServer(r0, r1);	 Catch:{ Exception -> 0x0412 }
        goto L_0x0457;
    L_0x0472:
        r20 = "AmazonPushRegistered";
        r22 = "true";
        r0 = r20;
        r1 = r22;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x0412 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0412 }
        goto L_0x0457;
    L_0x0485:
        r20 = "JPushRegistered";
        r22 = "true";
        r0 = r20;
        r1 = r22;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x0412 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0412 }
        goto L_0x0457;
    L_0x0498:
        r20 = $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType();	 Catch:{ Exception -> 0x0412 }
        r22 = r27.ordinal();	 Catch:{ Exception -> 0x0412 }
        r20 = r20[r22];	 Catch:{ Exception -> 0x0412 }
        switch(r20) {
            case 1: goto L_0x04cc;
            case 2: goto L_0x04d6;
            case 3: goto L_0x04e9;
            default: goto L_0x04a5;
        };	 Catch:{ Exception -> 0x0412 }
    L_0x04a5:
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0412 }
        r20.<init>();	 Catch:{ Exception -> 0x0412 }
        r0 = r20;
        r1 = r25;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0412 }
        r22 = " Send To Failed : ";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0412 }
        r0 = r20;
        r20 = r0.append(r13);	 Catch:{ Exception -> 0x0412 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0412 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0412 }
        r14 = 1;
        goto L_0x03ec;
    L_0x04cc:
        r20 = 0;
        r0 = r24;
        r1 = r20;
        com.google.android.gcm.GCMRegistrar.setRegisteredOnServer(r0, r1);	 Catch:{ Exception -> 0x0412 }
        goto L_0x04a5;
    L_0x04d6:
        r20 = "AmazonPushRegistered";
        r22 = "false";
        r0 = r20;
        r1 = r22;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x0412 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0412 }
        goto L_0x04a5;
    L_0x04e9:
        r20 = "JPushRegistered";
        r22 = "false";
        r0 = r20;
        r1 = r22;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x0412 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0412 }
        goto L_0x04a5;
    L_0x04fc:
        r20 = $SWITCH_TABLE$com$com2us$module$push$ThirdPartyPush$ThirdPartyPushType();	 Catch:{ Exception -> 0x0412 }
        r22 = r27.ordinal();	 Catch:{ Exception -> 0x0412 }
        r20 = r20[r22];	 Catch:{ Exception -> 0x0412 }
        switch(r20) {
            case 1: goto L_0x0530;
            case 2: goto L_0x053a;
            case 3: goto L_0x054d;
            default: goto L_0x0509;
        };	 Catch:{ Exception -> 0x0412 }
    L_0x0509:
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0412 }
        r20.<init>();	 Catch:{ Exception -> 0x0412 }
        r0 = r20;
        r1 = r25;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0412 }
        r22 = " Send To Failed : ";
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);	 Catch:{ Exception -> 0x0412 }
        r0 = r20;
        r20 = r0.append(r13);	 Catch:{ Exception -> 0x0412 }
        r20 = r20.toString();	 Catch:{ Exception -> 0x0412 }
        com.com2us.module.push.PushConfig.LogI(r20);	 Catch:{ Exception -> 0x0412 }
        r14 = 1;
        goto L_0x00ea;
    L_0x0530:
        r20 = 0;
        r0 = r24;
        r1 = r20;
        com.google.android.gcm.GCMRegistrar.setRegisteredOnServer(r0, r1);	 Catch:{ Exception -> 0x0412 }
        goto L_0x0509;
    L_0x053a:
        r20 = "AmazonPushRegistered";
        r22 = "false";
        r0 = r20;
        r1 = r22;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x0412 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0412 }
        goto L_0x0509;
    L_0x054d:
        r20 = "JPushRegistered";
        r22 = "false";
        r0 = r20;
        r1 = r22;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x0412 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0412 }
        goto L_0x0509;
    L_0x0560:
        r20 = 0;
        r0 = r24;
        r1 = r20;
        com.google.android.gcm.GCMRegistrar.setRegisteredOnServer(r0, r1);	 Catch:{ Exception -> 0x0382 }
        goto L_0x0423;
    L_0x056b:
        r20 = "AmazonPushRegistered";
        r22 = "false";
        r0 = r20;
        r1 = r22;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x0382 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0382 }
        goto L_0x0423;
    L_0x057f:
        r20 = "JPushRegistered";
        r22 = "false";
        r0 = r20;
        r1 = r22;
        r10.setProperty(r0, r1);	 Catch:{ Exception -> 0x0382 }
        r20 = 0;
        r0 = r20;
        r10.storeProperty(r0);	 Catch:{ Exception -> 0x0382 }
        goto L_0x0423;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.push.Push.sendToServer(android.content.Context, com.com2us.module.push.Push$RegisterState, java.lang.String, com.com2us.module.push.ThirdPartyPush$ThirdPartyPushType):int");
    }

    public void onActivityResumed() {
        super.onActivityResumed();
        if (this.tppm != null) {
            this.tppm.onResume();
        }
    }

    public void onActivityPaused() {
        if (this.tppm != null) {
            this.tppm.onPause();
        }
        super.onActivityPaused();
    }

    public String getName() {
        return PushConfig.LOG_TAG;
    }

    public void setLogged(boolean b) {
        LoggerGroup.setLogged(b, PushConfig.PUSH_LOG_TAGS);
        PushConfig.LOG = b;
        PushConfig.LogI("setLogged " + b);
        this.propertyUtil.setProperty("log", String.valueOf(b));
        this.propertyUtil.storeProperty(null);
    }

    public void setAppIdForIdentity(String appid) {
        PushConfig.LogI("setAppIdForIdentity : " + appid);
        appid = appid;
        this.propertyUtil.setProperty(PeppermintConstant.JSON_KEY_APP_ID, appid);
        this.propertyUtil.storeProperty(null);
    }

    public void destroy() {
        PushConfig.LogI("destroy");
        GCMRegistrar.onDestroy(this.context);
        if (glView != null) {
            jniPushUninitialize();
        }
    }

    public String[] getPermission() {
        return new String[]{new StringBuilder(String.valueOf(this.context.getPackageName())).append(".permission.C2D_MESSAGE").toString(), "com.google.android.c2dm.permission.RECEIVE", "android.permission.VIBRATE", "android.permission.READ_PHONE_STATE"};
    }
}
