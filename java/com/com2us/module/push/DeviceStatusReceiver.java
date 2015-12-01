package com.com2us.module.push;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import java.util.LinkedHashMap;

public class DeviceStatusReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        PushConfig.LogI("Intent ACTION: " + intent.getAction());
        LinkedHashMap<String, PushResource> map;
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            map = PushResourceHandler.load(context.getApplicationContext());
            for (PushResource res : map.values()) {
                res.triggerAtTime -= System.currentTimeMillis() - res.savedCurrentTime;
                res.elapsedRealtime = SystemClock.elapsedRealtime();
                ((AlarmManager) context.getApplicationContext().getSystemService("alarm")).set(2, res.elapsedRealtime + res.triggerAtTime, PendingIntent.getBroadcast(context.getApplicationContext(), Integer.parseInt(res.noticeID), PushResourceHandler.putIntentExtra(new Intent(context.getApplicationContext(), LocalPushReceiver.class), res.noticeID, map), 134217728));
            }
            PushResourceHandler.save(context, map);
        } else if ("android.intent.action.TIME_SET".equals(intent.getAction())) {
            map = PushResourceHandler.load(context.getApplicationContext());
            for (PushResource res2 : map.values()) {
                res2.savedCurrentTime = System.currentTimeMillis() + (SystemClock.elapsedRealtime() - res2.elapsedRealtime);
            }
            PushResourceHandler.save(context, map);
            PropertyUtil propertyUtil = PropertyUtil.getInstance(context);
            propertyUtil.loadProperty();
            try {
                propertyUtil.setProperty("savedDelayCurrentTime", String.valueOf(System.currentTimeMillis() + (SystemClock.elapsedRealtime() - Long.valueOf(propertyUtil.getProperty("savedDelayElapsedTime")).longValue())));
                propertyUtil.storeProperty(null);
            } catch (Exception e) {
                e.printStackTrace();
                propertyUtil.setProperty("savedDelayCurrentTime", a.d);
                propertyUtil.storeProperty(null);
            }
        }
    }
}
