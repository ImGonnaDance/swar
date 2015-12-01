package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AnalyticsManager;

final class p implements Runnable {
    private final /* synthetic */ Activity a;
    private final /* synthetic */ String b;

    p(Activity activity, String str) {
        this.a = activity;
        this.b = str;
    }

    public final void run() {
        AnalyticsManager.sendUserInfo(this.a, this.b);
    }
}
