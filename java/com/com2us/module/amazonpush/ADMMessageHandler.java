package com.com2us.module.amazonpush;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.amazon.device.messaging.ADMMessageHandlerBase;
import com.amazon.device.messaging.ADMMessageReceiver;
import com.com2us.module.push.PushConfig;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ADMMessageHandler extends ADMMessageHandlerBase {
    public static final String TAG = "ADM";
    public static boolean inBackground = true;
    private static final Logger logger = LoggerGroup.createLogger(TAG);
    private static int numberOfMissedMessages = 0;
    private static String savedMessage = null;

    public static class MessageAlertReceiver extends ADMMessageReceiver {
        public MessageAlertReceiver() {
            super(ADMMessageHandler.class);
        }
    }

    public ADMMessageHandler() {
        super(ADMMessageHandler.class.getName());
        Log.d(TAG, "ADMMessageHandler : " + ADMMessageHandler.class.getName());
    }

    protected void onMessage(Intent intent) {
        int gcmNoticeID;
        logger.d("onMessage : " + intent.getExtras().toString());
        Context context = getApplicationContext();
        verifyMD5Checksum(intent.getExtras());
        for (String key : intent.getExtras().keySet()) {
            PushConfig.LogI(new StringBuilder(String.valueOf(key)).append(" = ").append(intent.getExtras().get(key)).toString());
        }
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

    protected void onRegistered(String registrationId) {
        Log.i(TAG, "onRegistered : " + registrationId);
        AmazonPush.getInstance(getApplicationContext()).setRegId(registrationId);
    }

    protected void onRegistrationError(String string) {
        Log.e(TAG, "onRegistrationError : " + string);
    }

    protected void onUnregistered(String registrationId) {
        Log.i(TAG, "onUnregistered : " + registrationId);
    }

    public static String getMostRecentMissedMessage() {
        numberOfMissedMessages = 0;
        return savedMessage;
    }

    public static int getNumberOfMissedMessages() {
        return numberOfMissedMessages;
    }

    private void verifyMD5Checksum(Bundle extras) {
        String consolidationKey = "collapse_key";
        Set<String> extraKeySet = extras.keySet();
        Map<String, String> extrasHashMap = new HashMap();
        for (String key : extraKeySet) {
            if (!(key.equals("adm_message_md5") || key.equals("collapse_key"))) {
                extrasHashMap.put(key, extras.getString(key));
            }
        }
        if (!extras.getString("adm_message_md5").trim().equals(new ADMSampleMD5ChecksumCalculator().calculateChecksum(extrasHashMap).trim())) {
            Log.w(TAG, "[verifyMD5Checksum] SampleADMMessageHandler:onMessage MD5 checksum verification failure. Message received with errors");
        }
    }
}
