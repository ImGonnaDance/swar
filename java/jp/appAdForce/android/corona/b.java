package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.corona.CoronaAdManager.SendConversionForMobage;

final class b implements Runnable {
    final /* synthetic */ SendConversionForMobage a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;

    b(SendConversionForMobage sendConversionForMobage, CoronaActivity coronaActivity, String str) {
        this.a = sendConversionForMobage;
        this.b = coronaActivity;
        this.c = str;
    }

    public final void run() {
        CoronaAdManager.a(this.b).sendConversionForMobage(this.c);
    }
}
