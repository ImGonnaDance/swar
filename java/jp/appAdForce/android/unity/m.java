package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AnalyticsManager;

final class m implements Runnable {
    private final /* synthetic */ String a;
    private final /* synthetic */ Activity b;

    m(String str, Activity activity) {
        this.a = str;
        this.b = activity;
    }

    public final void run() {
        AnalyticsManager.a(this.a, this.b);
    }
}
