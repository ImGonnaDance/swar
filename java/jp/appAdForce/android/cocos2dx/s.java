package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.AnalyticsManager;

final class s implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ int d;

    s(Context context, String str, String str2, int i) {
        this.a = context;
        this.b = str;
        this.c = str2;
        this.d = i;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.a, this.b, this.c, this.d);
    }
}
