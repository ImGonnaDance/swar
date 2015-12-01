package defpackage;

import android.text.InputFilter.LengthFilter;

class h$16 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ h b;

    h$16(h hVar, int i) {
        this.b = hVar;
        this.a = i;
    }

    public void run() {
        this.b.w[0] = new LengthFilter(this.a);
        this.b.d.setFilters(this.b.w);
    }
}
