package jp.appAdForce.android.unity;

final class g implements Runnable {
    final /* synthetic */ AdManagerUnity a;
    private final /* synthetic */ String b;

    g(AdManagerUnity adManagerUnity, String str) {
        this.a = adManagerUnity;
        this.b = str;
    }

    public final void run() {
        this.a.c.sendConversionForMobage(this.b);
    }
}
