package com.com2us.module.push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
    protected void onRegistered(Context context, String registrationId) {
        PushConfig.LogI("onRegistered : " + registrationId);
        if (TextUtils.isEmpty(Push.senderID)) {
            PushConfig.LogI("onRegistered, but no server senderIds. do not send to server token.");
            return;
        }
        try {
            PushConfig.saveSenderIDs(context, Push.getInstance(context).uniqueSenderIDs);
            Push.sendToServer(context, Push.registerState, registrationId);
            PushConfig.LogI("registration complete!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onUnregistered(Context context, String registrationId) {
        PushConfig.LogI("onUnregistered : " + registrationId);
        PushConfig.LogI("unregistration done, new messages from the authorized sender will be rejected");
        try {
            Push.sendToServer(context, RegisterState.UNREGISTER, registrationId);
            PushConfig.LogI("unregistration complete!!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onMessage(Context context, Intent intent) {
        PushConfig.LogI("onMessage");
        for (String key : intent.getExtras().keySet()) {
            PushConfig.LogI(new StringBuilder(String.valueOf(key)).append(" = ").append(intent.getExtras().get(key)).toString());
        }
        if (!intent.getExtras().containsKey("from") || !TextUtils.equals((String) intent.getExtras().get("from"), "google.com/iid")) {
            int gcmNoticeID;
            intent = intent.putExtra("isGCMPush", true);
            try {
                gcmNoticeID = Integer.parseInt(intent.getStringExtra("noticeID")) + 1000000;
            } catch (Exception e) {
                gcmNoticeID = 1100001;
            }
            intent = PushConfig.setReceiveData(context, intent.putExtra("noticeID", gcmNoticeID));
            if (intent != null && intent.getBooleanExtra("isGCMOperation", true)) {
                if (intent.getBooleanExtra("bScreenLock", false)) {
                    PushConfig.LogI("startPushWakeLock");
                    PushConfig.startPushWakeLock(context, intent);
                } else {
                    PushConfig.LogI("setPushType");
                    PushConfig.setPushType(context, intent);
                }
                if (!TextUtils.isEmpty(intent.getStringExtra("broadcastAction"))) {
                    PushConfig.LogI("start broadcasting");
                    Intent broadcastIntent = new Intent(intent.getStringExtra("broadcastAction"));
                    broadcastIntent.putExtras(intent);
                    PushConfig.LogI("context.getPackageName() : " + context.getPackageName());
                    context.sendBroadcast(broadcastIntent);
                }
            }
        }
    }

    protected void onError(Context context, String errorId) {
        PushConfig.LogI("onError : " + errorId);
        PushConfig.LogI("Registration failed, should try again later.");
    }
}
