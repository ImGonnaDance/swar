package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.AnalyticsManager;

final class o implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;

    o(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public final void run() {
        AnalyticsManager.sendUserInfo(this.a, this.b);
    }
}
