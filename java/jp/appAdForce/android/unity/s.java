package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AnalyticsManager;

final class s implements Runnable {
    private final /* synthetic */ Activity a;
    private final /* synthetic */ String b;
    private final /* synthetic */ int c;

    s(Activity activity, String str, int i) {
        this.a = activity;
        this.b = str;
        this.c = i;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.a, this.b, this.c);
    }
}
