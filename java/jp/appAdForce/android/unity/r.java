package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AnalyticsManager;

final class r implements Runnable {
    private final /* synthetic */ Activity a;

    r(Activity activity) {
        this.a = activity;
    }

    public final void run() {
        AnalyticsManager.sendEndSession(this.a);
    }
}
