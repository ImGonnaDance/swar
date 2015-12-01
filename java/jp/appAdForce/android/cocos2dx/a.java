package jp.appAdForce.android.cocos2dx;

import android.content.Context;

final class a implements Runnable {
    private final /* synthetic */ Context a;

    a(Context context) {
        this.a = context;
    }

    public final void run() {
        Cocos2dxAdManager.a(this.a).sendConversion();
    }
}
