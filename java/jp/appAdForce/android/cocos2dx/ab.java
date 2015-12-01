package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.NotifyManager;

final class ab implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;

    ab(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public final void run() {
        new NotifyManager(this.a, Cocos2dxAdManager.a(this.a)).registerToGCM(this.a, this.b);
    }
}
