package defpackage;

import android.text.method.TextKeyListener;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

class h$9 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ h b;

    h$9(h hVar, int i) {
        this.b = hVar;
        this.a = i;
    }

    public void run() {
        int inputType = this.b.d.getInputType();
        if ((inputType & 15) == 1) {
            switch (this.a) {
                case g.a /*0*/:
                    this.b.d.setInputType(TextKeyListener.getInstance().getInputType());
                    return;
                case o.a /*1*/:
                    this.b.d.setInputType(inputType & -28673);
                    return;
                default:
                    return;
            }
        }
    }
}
