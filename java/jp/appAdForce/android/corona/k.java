package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.AnalyticsManager;

final class k implements Runnable {
    final /* synthetic */ CoronaAnalyticsManager a;
    private final /* synthetic */ String b;
    private final /* synthetic */ CoronaActivity c;

    k(CoronaAnalyticsManager coronaAnalyticsManager, String str, CoronaActivity coronaActivity) {
        this.a = coronaAnalyticsManager;
        this.b = str;
        this.c = coronaActivity;
    }

    public final void run() {
        AnalyticsManager.a(this.b, this.c);
    }
}
