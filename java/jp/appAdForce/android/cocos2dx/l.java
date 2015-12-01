package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.AnalyticsManager;

final class l implements Runnable {
    private final /* synthetic */ String a;
    private final /* synthetic */ Context b;

    l(String str, Context context) {
        this.a = str;
        this.b = context;
    }

    public final void run() {
        AnalyticsManager.a(this.a, this.b);
    }
}
