package com.com2us.module.push;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AmazonPushReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        PushConfig.LogI("AmazonPushReceiver onReceive");
    }
}
