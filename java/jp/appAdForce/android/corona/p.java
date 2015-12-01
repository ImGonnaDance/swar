package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import java.util.Map.Entry;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.LtvManager;
import jp.co.cyberz.fox.a.a.i;

final class p implements Runnable {
    final /* synthetic */ CoronaLtvManager a;
    private final /* synthetic */ CoronaActivity b;
    private final /* synthetic */ String c;
    private final /* synthetic */ int d;

    p(CoronaLtvManager coronaLtvManager, CoronaActivity coronaActivity, String str, int i) {
        this.a = coronaLtvManager;
        this.b = coronaActivity;
        this.c = str;
        this.d = i;
    }

    public final void run() {
        LtvManager ltvManager = new LtvManager(new AdManager(this.b));
        if (CoronaLtvManager.a != null && CoronaLtvManager.a.size() > 0) {
            for (Entry entry : CoronaLtvManager.a.entrySet()) {
                ltvManager.addParam((String) entry.getKey(), (String) entry.getValue());
            }
        }
        if (this.c == null || i.a.equals(this.c)) {
            ltvManager.sendLtvConversion(this.d);
        } else {
            ltvManager.sendLtvConversion(this.d, this.c);
        }
        ltvManager.clearParam();
        CoronaLtvManager.a = null;
    }
}
