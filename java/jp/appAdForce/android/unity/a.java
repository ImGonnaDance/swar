package jp.appAdForce.android.unity;

final class a implements Runnable {
    final /* synthetic */ AdManagerUnity a;

    a(AdManagerUnity adManagerUnity) {
        this.a = adManagerUnity;
    }

    public final void run() {
        this.a.c.sendConversion();
    }
}
