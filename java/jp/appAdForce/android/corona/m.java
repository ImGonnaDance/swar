package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.AnalyticsManager;
import jp.appAdForce.android.corona.CoronaAnalyticsManager.SendEvent;

final class m implements Runnable {
    final /* synthetic */ SendEvent a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;
    private final /* synthetic */ String e;
    private final /* synthetic */ int f;

    m(SendEvent sendEvent, CoronaActivity coronaActivity, String str, String str2, String str3, int i) {
        this.a = sendEvent;
        this.b = coronaActivity;
        this.c = str;
        this.d = str2;
        this.e = str3;
        this.f = i;
    }

    public final void run() {
        AnalyticsManager.sendEvent(this.b, this.c, this.d, this.e, this.f);
    }
}
