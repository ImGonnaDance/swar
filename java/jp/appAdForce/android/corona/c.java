package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.corona.CoronaAdManager.SendConversionForMobageAndCAReward;

final class c implements Runnable {
    final /* synthetic */ SendConversionForMobageAndCAReward a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;

    c(SendConversionForMobageAndCAReward sendConversionForMobageAndCAReward, CoronaActivity coronaActivity, String str) {
        this.a = sendConversionForMobageAndCAReward;
        this.b = coronaActivity;
        this.c = str;
    }

    public final void run() {
        CoronaAdManager.a(this.b).sendConversionForMobageWithCAReward(this.c);
    }
}
