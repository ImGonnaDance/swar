package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.AnalyticsManager;

final class r implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;
    private final /* synthetic */ int c;

    r(Context context, String str, int i) {
        this.a = context;
        this.b = str;
        this.c = i;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.a, this.b, this.c);
    }
}
