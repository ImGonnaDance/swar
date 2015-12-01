package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.LtvManager;
import jp.appAdForce.android.corona.CoronaLtvManager.LtvOpenBrowser;

final class q implements Runnable {
    final /* synthetic */ LtvOpenBrowser a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;

    q(LtvOpenBrowser ltvOpenBrowser, CoronaActivity coronaActivity, String str) {
        this.a = ltvOpenBrowser;
        this.b = coronaActivity;
        this.c = str;
    }

    public final void run() {
        new LtvManager(new AdManager(this.b)).ltvOpenBrowser(this.c);
    }
}
