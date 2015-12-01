package defpackage;

import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

class h$12 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ h b;

    h$12(h hVar, int i) {
        this.b = hVar;
        this.a = i;
    }

    public void run() {
        this.b.d.setTextSize((float) this.b.q);
        this.b.d.setImeOptions(268435462);
        switch (this.a) {
            case g.a /*0*/:
                this.b.d.setInputType(1);
                break;
            case o.a /*1*/:
                this.b.d.setInputType(131073);
                break;
        }
        this.b.e = new h$12$1(this);
        this.b.d.addTextChangedListener(this.b.e);
        this.b.d.setOnFocusChangeListener(new h$12$2(this));
        this.b.d.setOnEditorActionListener(new h$12$3(this));
    }
}
