package jp.appAdForce.android.unity;

final class aa implements Runnable {
    final /* synthetic */ LtvManagerUnity a;
    private final /* synthetic */ int b;
    private final /* synthetic */ String c;

    aa(LtvManagerUnity ltvManagerUnity, int i, String str) {
        this.a = ltvManagerUnity;
        this.b = i;
        this.c = str;
    }

    public final void run() {
        this.a.b.sendLtvConversion(this.b, this.c);
    }
}
