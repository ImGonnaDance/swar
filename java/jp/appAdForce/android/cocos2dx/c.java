package jp.appAdForce.android.cocos2dx;

import android.content.Context;

final class c implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;

    c(Context context, String str, String str2) {
        this.a = context;
        this.b = str;
        this.c = str2;
    }

    public final void run() {
        Cocos2dxAdManager.a(this.a).sendConversionForMobageWithCAReward(this.b, this.c);
    }
}
