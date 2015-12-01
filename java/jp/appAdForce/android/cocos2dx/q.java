package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.AnalyticsManager;

final class q implements Runnable {
    private final /* synthetic */ Context a;

    q(Context context) {
        this.a = context;
    }

    public final void run() {
        AnalyticsManager.sendEndSession(this.a);
    }
}
