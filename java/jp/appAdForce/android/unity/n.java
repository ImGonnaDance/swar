package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AnalyticsManager;

final class n implements Runnable {
    private final /* synthetic */ Activity a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ String e;
    private final /* synthetic */ String f;
    private final /* synthetic */ double g;
    private final /* synthetic */ int h;
    private final /* synthetic */ String i;

    n(Activity activity, String str, String str2, String str3, String str4, String str5, double d, int i, String str6) {
        this.a = activity;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.f = str5;
        this.g = d;
        this.h = i;
        this.i = str6;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i);
    }
}
