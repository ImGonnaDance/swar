package jp.appAdForce.android.unity;

final class k implements Runnable {
    final /* synthetic */ AdManagerUnity a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;

    k(AdManagerUnity adManagerUnity, String str, String str2) {
        this.a = adManagerUnity;
        this.b = str;
        this.c = str2;
    }

    public final void run() {
        this.a.c.sendConversionWithCAReward(this.b, this.c);
    }
}
