package jp.co.dimage.android.b;

import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.d;
import jp.co.dimage.android.f;
import jp.co.dimage.android.p;

public final class c implements f {
    private d a;
    private String b;
    private String c = null;
    private b d;
    private String e = null;
    private String f = null;

    public c(d dVar) {
        this.a = dVar;
        this.b = i.a;
        this.e = dVar.o();
        this.f = dVar.n();
    }

    private String a(String str, String str2, String str3) {
        return p.a(f.R, new String[]{this.e, str, this.f, str2, str3});
    }

    private String c() {
        return this.c;
    }

    public final void a() {
        this.c = this.a.u();
        if (this.d != null) {
            this.d.a();
        }
        this.d = new b(this.a.j());
        this.d.execute(new Void[0]);
    }

    public final void a(String str) {
        if (this.c == null) {
            throw new IllegalStateException("tracking task is not started.");
        }
        String str2 = this.c;
        String str3 = this.b;
        str2 = p.a(f.R, new String[]{this.e, str2, this.f, str, str3});
        this.b = str;
        if (this.a.f()) {
            this.d.a(str2);
        }
    }

    public final void b() {
        this.d.a();
        this.d = null;
        this.c = null;
    }
}
