package defpackage;

import jp.co.dimage.android.o;

class h$17 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ h b;

    h$17(h hVar, int i) {
        this.b = hVar;
        this.a = i;
    }

    public void run() {
        int i;
        switch (this.a) {
            case o.b /*2*/:
                i = 1;
                break;
            case o.c /*3*/:
                i = 3;
                break;
            case o.d /*4*/:
                i = 5;
                break;
            default:
                return;
        }
        this.b.d.setGravity(i | (this.b.d.getGravity() & -8));
    }
}
