package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.AnalyticsManager;

final class p implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ String e;
    private final /* synthetic */ String f;
    private final /* synthetic */ String g;

    p(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        this.a = context;
        this.b = str;
        this.c = str2;
        this.d = str3;
        this.e = str4;
        this.f = str5;
        this.g = str6;
    }

    public final void run() {
        AnalyticsManager.sendUserInfo(this.a, this.b, this.c, this.d, this.e, this.f, this.g);
    }
}
