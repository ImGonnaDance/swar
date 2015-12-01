package defpackage;

import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

class h$18 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ h b;

    h$18(h hVar, int i) {
        this.b = hVar;
        this.a = i;
    }

    public void run() {
        int i;
        switch (this.a) {
            case g.a /*0*/:
                i = 48;
                break;
            case o.a /*1*/:
                i = 80;
                break;
            case o.b /*2*/:
                i = 16;
                break;
            default:
                return;
        }
        this.b.d.setGravity(i | (this.b.d.getGravity() & -113));
    }
}
