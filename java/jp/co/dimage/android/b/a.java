package jp.co.dimage.android.b;

import jp.appAdForce.android.AdManager;
import jp.co.dimage.android.d;
import jp.co.dimage.android.f;

public final class a implements f {
    private AdManager a = null;
    private d b = null;
    private c c = null;

    private a(AdManager adManager) {
        this.a = adManager;
        this.b = this.a.a();
        this.c = new c(this.b);
    }

    private void a() {
        this.c.a();
    }

    private void a(String str) {
        this.c.a(str);
    }

    private void b() {
        this.c.b();
    }
}
