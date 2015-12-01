package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.corona.CoronaAdManager.SendUserIdForMobage;

final class j implements Runnable {
    final /* synthetic */ SendUserIdForMobage a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;

    j(SendUserIdForMobage sendUserIdForMobage, CoronaActivity coronaActivity, String str) {
        this.a = sendUserIdForMobage;
        this.b = coronaActivity;
        this.c = str;
    }

    public final void run() {
        CoronaAdManager.a(this.b).sendUserIdForMobage(this.c);
    }
}
