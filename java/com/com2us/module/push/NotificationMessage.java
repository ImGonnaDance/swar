package com.com2us.module.push;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

public class NotificationMessage extends Activity {
    private static final int REQUEST_APPINTENT = 2;
    private static final int REQUEST_MARKETINTENT = 1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushConfig.isUseTestServer = getIntent().getBooleanExtra("isUseTestServer", false);
        PushConfig.LOG = getIntent().getBooleanExtra("log", false);
        final Bundle data = getIntent().getExtras();
        new Thread(new Runnable() {
            public void run() {
                if (data.getBoolean("isGCMPush", false)) {
                    PushConfig.sendToServer(PushConfig.strPostDataBuilder(NotificationMessage.this, data));
                }
            }
        }).start();
        PushConfig.saveReceivedPush(this, data);
        ((NotificationManager) getSystemService("notification")).cancelAll();
        PushConfig.setBadge(this, 0);
        MsgsOfNoticeID.clearAllMsgs(this);
        String active = getIntent().getExtras().getString("active");
        if (TextUtils.isEmpty(active) || active.length() <= 3 || !active.subSequence(0, 3).equals("web")) {
            ComponentName compName = new ComponentName(getIntent().getExtras().getString("packageName"), getIntent().getExtras().getString("className"));
            Intent appIntent = new Intent("android.intent.action.MAIN");
            appIntent.addCategory("android.intent.category.LAUNCHER");
            appIntent.setComponent(compName);
            appIntent.setFlags(268435456);
            appIntent.putExtras(data);
            startActivityForResult(appIntent, REQUEST_APPINTENT);
            return;
        }
        Intent marketIntent = new Intent("android.intent.action.VIEW", Uri.parse(active.substring(4)));
        marketIntent.setFlags(268435456);
        try {
            startActivityForResult(marketIntent, REQUEST_MARKETINTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_MARKETINTENT /*1*/:
                PushConfig.LogI("REQUEST_MARKETINTENT finish");
                finish();
                return;
            case REQUEST_APPINTENT /*2*/:
                PushConfig.LogI("REQUEST_APPINTENT finish");
                finish();
                return;
            default:
                finish();
                return;
        }
    }
}
