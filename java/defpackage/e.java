package defpackage;

import java.util.Vector;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;

public abstract class e {
    protected float a = 1.0f;
    protected boolean b = false;
    private Vector<e$a> c = new Vector();

    public void a(e$b e_b, float f) {
        this.c.add(new e$a(this, e_b, f));
    }

    public abstract boolean a();

    public abstract boolean a(int i);

    public abstract boolean a(boolean z);

    public abstract boolean b();

    public abstract boolean c();

    public abstract boolean d();

    public abstract void e();

    public abstract int f();

    public abstract boolean g();

    public void h() {
        e$a e_a = (e$a) this.c.remove(0);
        switch (e$1.a[e_a.a().ordinal()]) {
            case o.a /*1*/:
                a(e_a.b() != 0.0f);
                return;
            case o.b /*2*/:
                b();
                return;
            case o.c /*3*/:
                c();
                return;
            case o.d /*4*/:
                d();
                return;
            case f.bc /*5*/:
                e();
                return;
            default:
                return;
        }
    }

    public void i() {
        while (!this.c.isEmpty() && this.b) {
            h();
        }
    }
}
