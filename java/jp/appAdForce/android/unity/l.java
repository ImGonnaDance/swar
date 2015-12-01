package jp.appAdForce.android.unity;

final class l implements Runnable {
    final /* synthetic */ AdManagerUnity a;
    private final /* synthetic */ String b;

    l(AdManagerUnity adManagerUnity, String str) {
        this.a = adManagerUnity;
        this.b = str;
    }

    public final void run() {
        this.a.c.sendConversionForMobageWithCAReward(this.b);
    }
}
