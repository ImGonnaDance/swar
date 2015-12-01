package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AnalyticsManager;

final class u implements Runnable {
    private final /* synthetic */ Activity a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ int e;

    u(Activity activity, String str, String str2, String str3, int i) {
        this.a = activity;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = i;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.a, this.b, this.c, this.d, this.e);
    }
}
