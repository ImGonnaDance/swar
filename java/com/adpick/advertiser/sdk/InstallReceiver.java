package com.adpick.advertiser.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import jp.co.cyberz.fox.a.a.i;

public class InstallReceiver extends BroadcastReceiver {
    private String certkey;

    public void onReceive(Context context, Intent intent) {
        Log.i("adpick", "Adpick Success");
        try {
            AdPickAdvertiser.context = context;
            Bundle extras = intent.getExtras();
            String secretkey = AdPickAdvertiser.GetPref("secretkey");
            if (extras != null) {
                this.certkey = extras.getString(Gateway.REQUEST_REFERRER);
                AdPickAdvertiser.SetPref("installed", "YES");
                AdPickAdvertiser.SetPref("certkey", this.certkey);
                if (secretkey != null) {
                    AdPickAdvertiser.UserActivity("install", i.a);
                }
            }
        } catch (Exception e) {
            Log.i("oddm", "Error Adpick AD");
        }
    }
}
