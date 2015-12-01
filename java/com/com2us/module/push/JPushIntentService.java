package com.com2us.module.push;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import com.com2us.module.jpush.JPushBaseIntentService;

public class JPushIntentService extends JPushBaseIntentService {
    Handler handler = new Handler();

    public JPushIntentService() {
        super(null);
    }

    public JPushIntentService(String senderId) {
        super(senderId);
    }

    protected void onMessage(Context context, Intent intent) {
        PushConfig.LogI("onMessage");
    }
}
