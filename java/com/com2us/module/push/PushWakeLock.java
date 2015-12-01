package com.com2us.module.push;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class PushWakeLock extends Activity {
    private static final int REQUEST_TURN_ON_SCREEN = 1;
    private static Activity activity = null;
    private static WakeLock sCpuWakeLock;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushConfig.LogI("PushWakeLock");
        activity = this;
        requestWindowFeature(REQUEST_TURN_ON_SCREEN);
        getWindow().addFlags(6815744);
        getWindow().setFlags(131072, 131072);
        boolean isWakeLock = acquireCpuWakeLock(this);
        PushConfig.setPushType(getApplicationContext(), getIntent());
        setResult(REQUEST_TURN_ON_SCREEN);
        if (isWakeLock) {
            activity.finish();
        } else {
            new Thread(new Runnable() {
                public void run() {
                    int i = 0;
                    while (!((KeyguardManager) PushWakeLock.activity.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
                        i += PushWakeLock.REQUEST_TURN_ON_SCREEN;
                        try {
                            Thread.sleep(1000);
                            if (i >= 5) {
                                break;
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    PushWakeLock.activity.finish();
                }
            }).start();
        }
    }

    static boolean acquireCpuWakeLock(Context context) {
        if (context.checkCallingOrSelfPermission("android.permission.WAKE_LOCK") != 0) {
            PushConfig.LogI("WAKE_LOCK check return");
            return false;
        }
        PushConfig.LogI("PushWakeLock, Acquiring cpu wake lock");
        sCpuWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(((VERSION.SDK_INT >= 17 ? REQUEST_TURN_ON_SCREEN : 6) | 268435456) | 536870912, "PushWakeLock");
        sCpuWakeLock.acquire(5000);
        return true;
    }

    static void releaseCpuLock() {
        PushConfig.LogI("PushWakeLock, Releasing cpu wake lock");
        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }
}
