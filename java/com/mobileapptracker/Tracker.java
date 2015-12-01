package com.mobileapptracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import java.net.URLDecoder;

public class Tracker extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
                    String stringExtra = intent.getStringExtra(Gateway.REQUEST_REFERRER);
                    if (stringExtra != null) {
                        stringExtra = URLDecoder.decode(stringExtra, "UTF-8");
                        Log.d("MobileAppTracker", "MAT received referrer " + stringExtra);
                        context.getSharedPreferences("com.mobileapptracking", 0).edit().putString("mat_referrer", stringExtra).commit();
                        MobileAppTracker instance = MobileAppTracker.getInstance();
                        if (instance != null) {
                            instance.setInstallReferrer(stringExtra);
                            if (instance.a && !instance.c) {
                                synchronized (instance.d) {
                                    instance.d.notifyAll();
                                    instance.c = true;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
