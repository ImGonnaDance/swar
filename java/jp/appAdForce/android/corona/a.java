package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;

final class a implements Runnable {
    final /* synthetic */ CoronaAdManager a;
    private final /* synthetic */ CoronaActivity b;

    a(CoronaAdManager coronaAdManager, CoronaActivity coronaActivity) {
        this.a = coronaAdManager;
        this.b = coronaActivity;
    }

    public final void run() {
        CoronaAdManager.a(this.b).sendConversion();
    }
}
