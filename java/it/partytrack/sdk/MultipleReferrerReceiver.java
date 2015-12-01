package it.partytrack.sdk;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.com2us.module.manager.ModuleConfig;

public class MultipleReferrerReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        new ReferrerReceiver().onReceive(context, intent);
        ActivityInfo receiverInfo = context.getPackageManager().getReceiverInfo(new ComponentName(context, MultipleReferrerReceiver.class), ModuleConfig.ACTIVEUSER_MODULE);
        if (receiverInfo != null && receiverInfo.metaData != null && receiverInfo.metaData.keySet() != null) {
            for (String cls : receiverInfo.metaData.keySet()) {
                try {
                    ((BroadcastReceiver) Class.forName(cls).newInstance()).onReceive(context, intent);
                } catch (IllegalAccessException e) {
                    try {
                        e.printStackTrace();
                    } catch (NameNotFoundException e2) {
                        e2.printStackTrace();
                        return;
                    }
                } catch (InstantiationException e3) {
                    e3.printStackTrace();
                } catch (ClassNotFoundException e4) {
                    e4.printStackTrace();
                }
            }
        }
    }
}
