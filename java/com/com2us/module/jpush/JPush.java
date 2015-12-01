package com.com2us.module.jpush;

import android.content.Context;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import com.com2us.module.push.ThirdPartyPush;
import com.com2us.module.push.ThirdPartyPush.ThirdPartyPushType;

public class JPush extends ThirdPartyPush {
    public static final String TAG = "JPush";
    private static JPush instance = null;

    public static JPush getInstance(Context context) {
        if (instance == null) {
            instance = new JPush(context, TAG);
        }
        return instance;
    }

    private JPush(Context _context, String logTag) {
        super(_context, logTag);
        this.pushPkgName = "cn.jpush.android.api.JPushInterface";
        this.available = checkIfServiceAvailable(this.pushPkgName);
        if (this.available && checkIfInChina()) {
            this.logger.d("JPushInterface.init(context);");
            this.logger.d("This is list of JPushInterface Actions\n");
            this.logger.d("JPushInterface.ACTION_REGISTRATION_ID : " + JPushInterface.ACTION_REGISTRATION_ID);
            this.logger.d("JPushInterface.ACTION_UNREGISTER : " + JPushInterface.ACTION_UNREGISTER);
            this.logger.d("JPushInterface.ACTION_ACTIVITY_OPENDED : " + JPushInterface.ACTION_ACTIVITY_OPENDED);
            this.logger.d("JPushInterface.ACTION_NOTIFICATION_RECEIVED_PROXY : " + JPushInterface.ACTION_NOTIFICATION_RECEIVED_PROXY);
            this.logger.d("JPushInterface.ACTION_RESTOREPUSH : " + JPushInterface.ACTION_RESTOREPUSH);
            this.logger.d("JPushInterface.ACTION_STATUS : " + JPushInterface.ACTION_STATUS);
            this.logger.d("JPushInterface.ACTION_STOPPUSH : " + JPushInterface.ACTION_STOPPUSH);
            this.logger.d("JPushInterface.ACTION_MESSAGE_RECEIVED : " + JPushInterface.ACTION_MESSAGE_RECEIVED);
            this.logger.d("JPushInterface.ACTION_NOTIFICATION_RECEIVED : " + JPushInterface.ACTION_NOTIFICATION_RECEIVED);
            this.logger.d("JPushInterface.ACTION_NOTIFICATION_OPENED : " + JPushInterface.ACTION_NOTIFICATION_OPENED);
            this.logger.d("JPushInterface.ACTION_RICHPUSH_CALLBACK : " + JPushInterface.ACTION_RICHPUSH_CALLBACK);
            this.logger.d("JPushInterface.ACTION_CONNECTION_CHANGE : " + JPushInterface.ACTION_CONNECTION_CHANGE);
            JPushInterface.init(_context);
            JPushInterface.setDebugMode(true);
            BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(_context);
            builder.statusBarDrawable = getIconResourceID();
            JPushInterface.setDefaultPushNotificationBuilder(builder);
        }
        this.thirdPartyPushType = ThirdPartyPushType.JPush;
        instance = this;
    }

    protected void stopPush() {
        if (this.available && checkIfInChina()) {
            JPushInterface.stopPush(this.context);
        }
    }

    protected void resumePush() {
        if (this.available && checkIfInChina()) {
            JPushInterface.resumePush(this.context);
        }
    }

    public void onResume() {
        this.logger.d("JPush.onResume()");
        if (this.available) {
            this.logger.d("JPush.onResume() available");
            JPushInterface.onResume(this.context);
        }
    }

    public void onPause() {
        this.logger.d("JPush.onPause()");
        if (this.available) {
            this.logger.d("JPush.onPause() available");
            JPushInterface.onPause(this.context);
        }
    }
}
