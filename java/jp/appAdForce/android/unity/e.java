package jp.appAdForce.android.unity;

final class e implements Runnable {
    final /* synthetic */ AdManagerUnity a;
    private final /* synthetic */ String b;

    e(AdManagerUnity adManagerUnity, String str) {
        this.a = adManagerUnity;
        this.b = str;
    }

    public final void run() {
        this.a.c.sendConversion(this.b);
    }
}
