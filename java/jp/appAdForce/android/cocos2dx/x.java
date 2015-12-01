package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.AnalyticsManager;

final class x implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ String e;
    private final /* synthetic */ double f;
    private final /* synthetic */ int g;
    private final /* synthetic */ String h;

    x(Context context, String str, String str2, String str3, String str4, double d, int i, String str5) {
        this.a = context;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.f = d;
        this.g = i;
        this.h = str5;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
    }
}
