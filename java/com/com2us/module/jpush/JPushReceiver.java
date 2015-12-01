package com.com2us.module.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import cn.jpush.android.api.JPushInterface;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;

public class JPushReceiver extends BroadcastReceiver {
    public static final String TAG = "JPush";
    private static final Logger logger = LoggerGroup.createLogger(TAG);

    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        logger.d("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            logger.d("[MyReceiver] \u63a5\u6536Registration Id : " + bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID));
        } else if (!JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())) {
            if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                logger.d("[MyReceiver] \u63a5\u6536\u5230\u63a8\u9001\u4e0b\u6765\u7684\u81ea\u5b9a\u4e49\u6d88\u606f: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                logger.d("[MyReceiver] \u63a5\u6536\u5230\u63a8\u9001\u4e0b\u6765\u7684\u901a\u77e5");
                logger.d("[MyReceiver] \u63a5\u6536\u5230\u63a8\u9001\u4e0b\u6765\u7684\u901a\u77e5\u7684ID: " + bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID));
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                logger.d("[MyReceiver] \u7528\u6237\u70b9\u51fb\u6253\u5f00\u4e86\u901a\u77e5");
            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                logger.d("[MyReceiver] \u7528\u6237\u6536\u5230\u5230RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                logger.w("[MyReceiver]" + intent.getAction() + " connected state change to " + intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false));
            } else {
                logger.d("[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        }
        logger.v("onReceive: " + intent.getAction());
        String className = getJPushIntentServiceClassName(context);
        logger.v("JPush IntentService class: " + className);
        JPushBaseIntentService.runIntentInService(context, intent, className);
    }

    protected String getJPushIntentServiceClassName(Context context) {
        return "com.com2us.module.push.JPushIntentService";
    }

    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    private void processCustomMessage(Context context, Bundle bundle) {
    }
}
