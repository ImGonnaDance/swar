package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.AnalyticsManager;
import jp.appAdForce.android.corona.CoronaAnalyticsManager.SendPurchaseEvent;

final class n implements Runnable {
    final /* synthetic */ SendPurchaseEvent a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ String e;
    private final /* synthetic */ String f;
    private final /* synthetic */ String g;
    private final /* synthetic */ String h;
    private final /* synthetic */ double i;
    private final /* synthetic */ int j;
    private final /* synthetic */ String k;

    n(SendPurchaseEvent sendPurchaseEvent, CoronaActivity coronaActivity, String str, String str2, String str3, String str4, String str5, String str6, double d, int i, String str7) {
        this.a = sendPurchaseEvent;
        this.b = coronaActivity;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = str4;
        this.g = str5;
        this.h = str6;
        this.i = d;
        this.j = i;
        this.k = str7;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k);
    }
}
