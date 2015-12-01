package jp.appAdForce.android.unity;

final class j implements Runnable {
    final /* synthetic */ AdManagerUnity a;
    private final /* synthetic */ String b;

    j(AdManagerUnity adManagerUnity, String str) {
        this.a = adManagerUnity;
        this.b = str;
    }

    public final void run() {
        this.a.c.sendConversionWithCAReward(this.b);
    }
}
