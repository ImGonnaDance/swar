package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.corona.CoronaAdManager.SendConversionForMobageAndCARewardWithStartPageUrl;

final class d implements Runnable {
    final /* synthetic */ SendConversionForMobageAndCARewardWithStartPageUrl a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;

    d(SendConversionForMobageAndCARewardWithStartPageUrl sendConversionForMobageAndCARewardWithStartPageUrl, CoronaActivity coronaActivity, String str, String str2) {
        this.a = sendConversionForMobageAndCARewardWithStartPageUrl;
        this.b = coronaActivity;
        this.c = str;
        this.d = str2;
    }

    public final void run() {
        CoronaAdManager.a(this.b).sendConversionForMobageWithCAReward(this.c, this.d);
    }
}
