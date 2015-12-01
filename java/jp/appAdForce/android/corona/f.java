package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.corona.CoronaAdManager.SendConversionViaCAReward;

final class f implements Runnable {
    final /* synthetic */ SendConversionViaCAReward a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;

    f(SendConversionViaCAReward sendConversionViaCAReward, CoronaActivity coronaActivity, String str) {
        this.a = sendConversionViaCAReward;
        this.b = coronaActivity;
        this.c = str;
    }

    public final void run() {
        CoronaAdManager.a(this.b).sendConversionWithCAReward(this.c);
    }
}
