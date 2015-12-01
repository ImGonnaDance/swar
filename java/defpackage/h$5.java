package defpackage;

import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

class h$5 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ h b;

    h$5(h hVar, int i) {
        this.b = hVar;
        this.a = i;
    }

    public void run() {
        int i;
        switch (this.a) {
            case g.a /*0*/:
                i = 2;
                break;
            case o.a /*1*/:
                i = 6;
                break;
            case o.c /*3*/:
                i = 4;
                break;
            case o.d /*4*/:
                i = 5;
                break;
            default:
                return;
        }
        this.b.d.setImeOptions(i | (this.b.d.getImeOptions() & -256));
    }
}
