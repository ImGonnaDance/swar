package defpackage;

import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

class h$4 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ h b;

    h$4(h hVar, int i) {
        this.b = hVar;
        this.a = i;
    }

    public void run() {
        int inputType = this.b.d.getInputType();
        switch (this.a) {
            case g.a /*0*/:
            case o.d /*4*/:
                if ((inputType & 15) != 1) {
                    inputType = (inputType & -16) | 1;
                } else if ((inputType & 4080) == 0) {
                    return;
                }
                inputType &= -4081;
                break;
            case o.a /*1*/:
                if ((inputType & 15) != 1) {
                    inputType = (inputType & -16) | 1;
                } else if ((inputType & 4080) == 16) {
                    return;
                }
                inputType = (inputType & -4081) | 16;
                break;
            case o.b /*2*/:
                if ((inputType & 15) != 1) {
                    inputType = (inputType & -16) | 1;
                } else if ((inputType & 4080) == 32) {
                    return;
                }
                inputType = (inputType & -4081) | 32;
                break;
            case o.c /*3*/:
                if ((inputType & 15) != 2) {
                    inputType = ((inputType & -16) | 2) & -4081;
                    break;
                }
                return;
            default:
                return;
        }
        this.b.d.setInputType(inputType);
    }
}
