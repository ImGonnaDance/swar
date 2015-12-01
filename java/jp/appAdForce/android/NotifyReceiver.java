package jp.appAdForce.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import jp.co.cyberz.fox.notify.BaseNotifyReceiver;

public class NotifyReceiver extends BroadcastReceiver {
    BaseNotifyReceiver a = new BaseNotifyReceiver();

    public void onReceive(Context context, Intent intent) {
        this.a.onReceive(context, intent);
    }

    public void storeRegistrationId(Context context, String str) {
        BaseNotifyReceiver baseNotifyReceiver = this.a;
        BaseNotifyReceiver.a(context, str);
    }
}
