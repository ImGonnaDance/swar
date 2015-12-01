package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import jp.appAdForce.android.corona.CoronaAdManager.SendConversionViaCARewardWithBuid;

final class g implements Runnable {
    final /* synthetic */ SendConversionViaCARewardWithBuid a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ String d;

    g(SendConversionViaCARewardWithBuid sendConversionViaCARewardWithBuid, CoronaActivity coronaActivity, String str, String str2) {
        this.a = sendConversionViaCARewardWithBuid;
        this.b = coronaActivity;
        this.c = str;
        this.d = str2;
    }

    public final void run() {
        CoronaAdManager.a(this.b).sendConversionWithCAReward(this.c, this.d);
    }
}
