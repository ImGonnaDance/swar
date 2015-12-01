package com.com2us.smon.common;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import com.com2us.module.manager.ModuleConfig;

public class CommonReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            Log.i("CommonReceiver", new StringBuilder(String.valueOf(getClass().getName())).append(", received").toString());
            ActivityInfo ai = context.getPackageManager().getReceiverInfo(new ComponentName(context, getClass().getName()), ModuleConfig.ACTIVEUSER_MODULE);
            if (ai != null) {
                Bundle bundle = ai.metaData;
                for (String k : bundle.keySet()) {
                    String v = bundle.getString(k);
                    ((BroadcastReceiver) Class.forName(v).newInstance()).onReceive(context, intent);
                    Log.i("CommonReceiver", v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("CommonReceiver", e.getMessage());
        }
    }
}
