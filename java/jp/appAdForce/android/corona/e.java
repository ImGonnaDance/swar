package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.corona.CoronaAdManager.SendConversionForMobageWithStartPageUrl;

final class e implements Runnable {
    final /* synthetic */ SendConversionForMobageWithStartPageUrl a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;

    e(SendConversionForMobageWithStartPageUrl sendConversionForMobageWithStartPageUrl, CoronaActivity coronaActivity, String str, String str2) {
        this.a = sendConversionForMobageWithStartPageUrl;
        this.b = coronaActivity;
        this.c = str;
        this.d = str2;
    }

    public final void run() {
        CoronaAdManager.a(this.b).sendConversionForMobage(this.c, this.d);
    }
}
