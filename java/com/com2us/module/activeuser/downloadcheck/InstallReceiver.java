package com.com2us.module.activeuser.downloadcheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.activeuser.Constants.Network.Gateway;

public final class InstallReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        Log.i(InstallService.TAG, "InstallReceiver onReceive");
        String action = intent.getAction();
        String str = intent.getStringExtra(Gateway.REQUEST_REFERRER);
        if (TextUtils.equals("com.android.vending.INSTALL_REFERRER", action)) {
            if (TextUtils.isEmpty(str)) {
                str = "google";
            }
            ActiveUserProperties.setProperty(Gateway.REQUEST_REFERRER, str);
            ActiveUserProperties.storeProperties(context);
            Log.i(InstallService.TAG, "InstallReceiver onReceived referrer data.");
            Intent localIntent = new Intent(context, InstallService.class);
            localIntent.putExtra(Gateway.REQUEST_REFERRER, str);
            context.startService(localIntent);
        }
    }
}
