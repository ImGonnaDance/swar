package jp.co.dimage.android.a;

import java.security.GeneralSecurityException;
import jp.co.dimage.android.j;
import jp.co.dimage.android.l;
import jp.co.dimage.android.l.a;
import jp.co.dimage.android.o.c;
import jp.co.dimage.android.p;

final class b implements c {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public final void a() {
        try {
            String a = jp.co.dimage.android.b.a();
            if (!p.a(a)) {
                a aVar = this.a;
                aVar.k = aVar.k + "&_adid=" + l.a(a, a.XUNIQ);
                a aVar2 = this.a;
                aVar2.k = aVar2.k + "&_adte=" + jp.co.dimage.android.b.c();
            }
        } catch (GeneralSecurityException e) {
        }
        new j(this.a.j).execute(new String[]{this.a.k});
    }
}
