package com.com2us.module.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import java.util.LinkedHashMap;

public class LocalPushReceiver extends BroadcastReceiver {
    public void onReceive(final Context context, final Intent intent) {
        PushConfig.LogI("Local RECEIVE Receive");
        LinkedHashMap<String, PushResource> map = PushResourceHandler.load(context.getApplicationContext());
        map.remove(intent.getExtras().getString("noticeID"));
        PushResourceHandler.save(context.getApplicationContext(), map);
        new Thread(new Runnable() {
            public void run() {
                Intent pushIntent = PushConfig.setReceiveData(context, ((Intent) intent.clone()).putExtra("isGCMPush", false));
                if (pushIntent != null && pushIntent.getBooleanExtra("isOperation", true)) {
                    if (pushIntent.getBooleanExtra("bScreenLock", false)) {
                        PushConfig.LogI("startPushWakeLock");
                        PushConfig.startPushWakeLock(context, pushIntent);
                    } else {
                        PushConfig.LogI("setPushType");
                        PushConfig.setPushType(context, pushIntent);
                    }
                    if (!TextUtils.isEmpty(pushIntent.getStringExtra("broadcastAction"))) {
                        PushConfig.LogI("start broadcasting");
                        Intent broadcastIntent = new Intent(pushIntent.getStringExtra("broadcastAction"));
                        broadcastIntent.putExtras(pushIntent);
                        PushConfig.LogI("context.getPackageName() : " + context.getPackageName());
                        context.sendBroadcast(broadcastIntent);
                    }
                }
            }
        }).start();
    }
}
