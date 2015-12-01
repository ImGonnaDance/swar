package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.corona.CoronaAdManager.SendConversionWithUrlScheme;

final class i implements Runnable {
    final /* synthetic */ SendConversionWithUrlScheme a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;

    i(SendConversionWithUrlScheme sendConversionWithUrlScheme, CoronaActivity coronaActivity, String str, String str2) {
        this.a = sendConversionWithUrlScheme;
        this.b = coronaActivity;
        this.c = str;
        this.d = str2;
    }

    public final void run() {
        AdManager a = CoronaAdManager.a(this.b);
        if (this.c == null || jp.co.cyberz.fox.a.a.i.a.equals(this.c)) {
            a.sendConversion(this.d);
        } else {
            a.sendConversion(this.d, this.c);
        }
        a.setUrlScheme(this.b.getIntent());
    }
}
