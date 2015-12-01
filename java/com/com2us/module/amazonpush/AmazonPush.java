package com.com2us.module.amazonpush;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import com.amazon.device.messaging.ADM;
import com.com2us.module.push.ThirdPartyPush;
import com.com2us.module.push.ThirdPartyPush.ThirdPartyPushType;

public class AmazonPush extends ThirdPartyPush {
    public static final String TAG = "AmazonPush";
    private static AmazonPush instance = null;

    public static AmazonPush getInstance(Context context) {
        if (instance == null) {
            instance = new AmazonPush(context, TAG);
        }
        return instance;
    }

    private AmazonPush(Context _context, String logTag) {
        super(_context, logTag);
        this.pushPkgName = "com.amazon.device.messaging.ADM";
        this.available = checkIfServiceAvailable(this.pushPkgName);
        if (this.available) {
            this.logger.d("AmazonPush(context);");
            _context.startService(new Intent(_context, ADMMessageHandler.class));
        }
        this.thirdPartyPushType = ThirdPartyPushType.AmazonPush;
    }

    protected void stopPush() {
        if (this.available) {
            this.logger.d("AmazonPush stopPush;");
            new ADM(this.context).startUnregister();
        }
    }

    protected void resumePush() {
        if (this.available) {
            this.logger.d("AmazonPush resumePush;");
            ADM adm = new ADM(this.context);
            if (adm.isSupported() && adm.getRegistrationId() == null) {
                adm.startRegister();
            }
        }
    }

    public void onResume() {
        if (this.available) {
            this.logger.d("AmazonPush onResume;");
            int numberOfMissedMessages = ADMMessageHandler.getNumberOfMissedMessages();
            ADMMessageHandler.inBackground = false;
            if (numberOfMissedMessages > 0) {
                this.logger.i("savedMsg : " + ADMMessageHandler.getMostRecentMissedMessage());
                ((NotificationManager) this.context.getSystemService("notification")).cancel(this.context.getResources().getIdentifier("ic_c2s_notification_small_icon", "drawable", this.context.getPackageName()));
            }
        }
    }

    public void onPause() {
        if (this.available) {
            this.logger.d("AmazonPush onPause;");
            ADMMessageHandler.inBackground = true;
        }
    }
}
