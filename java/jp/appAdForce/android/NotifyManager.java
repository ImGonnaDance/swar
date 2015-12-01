package jp.appAdForce.android;

import android.content.Context;
import jp.co.cyberz.fox.notify.a;

public class NotifyManager {
    private a a;

    public NotifyManager(Context context, AdManager adManager) {
        this.a = new a(context, adManager);
    }

    public String getRegistrationId() {
        return this.a.a();
    }

    public void registerToGCM(Context context, String str) {
        this.a.a(context, str);
    }
}
