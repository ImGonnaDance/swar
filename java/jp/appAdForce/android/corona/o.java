package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.AnalyticsManager;
import jp.appAdForce.android.corona.CoronaAnalyticsManager.SendUserInfo;

final class o implements Runnable {
    final /* synthetic */ SendUserInfo a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ String e;
    private final /* synthetic */ String f;
    private final /* synthetic */ String g;
    private final /* synthetic */ String h;

    o(SendUserInfo sendUserInfo, CoronaActivity coronaActivity, String str, String str2, String str3, String str4, String str5, String str6) {
        this.a = sendUserInfo;
        this.b = coronaActivity;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = str4;
        this.g = str5;
        this.h = str6;
    }

    public final void run() {
        AnalyticsManager.sendUserInfo(this.b, this.c, this.d, this.e, this.f, this.g, this.h);
    }
}
