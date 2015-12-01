package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.AnalyticsManager;
import jp.appAdForce.android.corona.CoronaAnalyticsManager.SendEndSession;

final class l implements Runnable {
    final /* synthetic */ SendEndSession a;
    private final /* synthetic */ CoronaActivity b;

    l(SendEndSession sendEndSession, CoronaActivity coronaActivity) {
        this.a = sendEndSession;
        this.b = coronaActivity;
    }

    public final void run() {
        AnalyticsManager.sendEndSession(this.b);
    }
}
