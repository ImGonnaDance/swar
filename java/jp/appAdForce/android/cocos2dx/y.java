package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import java.util.Map.Entry;
import jp.appAdForce.android.LtvManager;

final class y implements Runnable {
    private final /* synthetic */ Context a;
    private final /* synthetic */ int b;

    y(Context context, int i) {
        this.a = context;
        this.b = i;
    }

    public final void run() {
        LtvManager ltvManager = new LtvManager(Cocos2dxAdManager.a(this.a));
        if (Cocos2dxLtvManager.a != null && Cocos2dxLtvManager.a.size() > 0) {
            for (Entry entry : Cocos2dxLtvManager.a.entrySet()) {
                ltvManager.addParam((String) entry.getKey(), (String) entry.getValue());
            }
        }
        ltvManager.sendLtvConversion(this.b);
        ltvManager.clearParam();
        Cocos2dxLtvManager.a = null;
    }
}
