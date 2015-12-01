package jp.co.dimage.android;

import java.security.GeneralSecurityException;
import jp.co.dimage.android.b.a;

final class i implements a {
    final /* synthetic */ g a;

    i(g gVar) {
        this.a = gVar;
    }

    public final void a(String str, boolean z) {
        if (!p.a(str)) {
            try {
                this.a.bE = l.a(str, l.a.XUNIQ);
                if (!this.a.bv.c()) {
                    this.a.bv.a(l.b(str), "4");
                    this.a.bv.a(d.a.ADID);
                }
                this.a.bF = z ? a.d : a.e;
            } catch (GeneralSecurityException e) {
            }
        }
    }
}
