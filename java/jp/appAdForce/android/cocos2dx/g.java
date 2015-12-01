package jp.appAdForce.android.cocos2dx;

import android.content.Context;

final class g implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;

    g(Context context, String str) {
        this.a = context;
        this.b = str;
    }

    public final void run() {
        Cocos2dxAdManager.a(this.a).sendConversionForMobage(this.b);
    }
}
