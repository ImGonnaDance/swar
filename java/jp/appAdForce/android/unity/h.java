package jp.appAdForce.android.unity;

final class h implements Runnable {
    final /* synthetic */ AdManagerUnity a;
    private final /* synthetic */ String b;
    private final /* synthetic */ String c;

    h(AdManagerUnity adManagerUnity, String str, String str2) {
        this.a = adManagerUnity;
        this.b = str;
        this.c = str2;
    }

    public final void run() {
        this.a.c.sendConversionForMobage(this.b, this.c);
    }
}
