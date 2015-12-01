package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.corona.CoronaAdManager.SendConversionWithStartPageUrl;
import jp.co.cyberz.fox.a.a.i;

final class h implements Runnable {
    final /* synthetic */ SendConversionWithStartPageUrl a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;

    h(SendConversionWithStartPageUrl sendConversionWithStartPageUrl, CoronaActivity coronaActivity, String str, String str2) {
        this.a = sendConversionWithStartPageUrl;
        this.b = coronaActivity;
        this.c = str;
        this.d = str2;
    }

    public final void run() {
        AdManager a = CoronaAdManager.a(this.b);
        if (this.c == null || i.a.equals(this.c)) {
            a.sendConversion(this.d);
        } else {
            a.sendConversion(this.d, this.c);
        }
    }
}
