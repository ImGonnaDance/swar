package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.AnalyticsManager;

final class t implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ int e;

    t(Context context, String str, String str2, String str3, int i) {
        this.a = context;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = i;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.a, this.b, this.c, this.d, this.e);
    }
}
