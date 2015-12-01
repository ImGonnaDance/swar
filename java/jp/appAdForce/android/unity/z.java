package jp.appAdForce.android.unity;

final class z implements Runnable {
    final /* synthetic */ LtvManagerUnity a;
    private final /* synthetic */ int b;

    z(LtvManagerUnity ltvManagerUnity, int i) {
        this.a = ltvManagerUnity;
        this.b = i;
    }

    public final void run() {
        this.a.b.sendLtvConversion(this.b);
    }
}
