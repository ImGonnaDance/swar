package defpackage;

import com.com2us.wrapper.function.CFunction;

class h$19 implements Runnable {
    final /* synthetic */ int[] a;
    final /* synthetic */ h b;

    h$19(h hVar, int[] iArr) {
        this.b = hVar;
        this.a = iArr;
    }

    public void run() {
        CFunction.setControlByPX(this.b.c, this.b.d, this.a[0], this.a[1], this.a[2], this.a[3]);
    }
}
