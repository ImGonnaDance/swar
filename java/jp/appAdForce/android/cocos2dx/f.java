package jp.appAdForce.android.cocos2dx;

import android.app.Activity;
import android.content.Context;

final class f implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ Activity b;

    f(Context context, Activity activity) {
        this.a = context;
        this.b = activity;
    }

    public final void run() {
        Cocos2dxAdManager.a(this.a).setUrlScheme(this.b.getIntent());
    }
}
