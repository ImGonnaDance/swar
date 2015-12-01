package jp.co.dimage.android;

import jp.co.dimage.android.InstallReceiver.a;

final class n implements a {
    final /* synthetic */ InstallReceiver a;
    private final /* synthetic */ g b;
    private final /* synthetic */ String c;

    n(InstallReceiver installReceiver, g gVar, String str) {
        this.a = installReceiver;
        this.b = gVar;
        this.c = str;
    }

    public final void a() {
        this.b.e(this.c);
    }

    public final void b() {
        this.b.d(this.c);
    }
}
