package jp.appAdForce.android.cocos2dx;

import android.content.Context;
import jp.appAdForce.android.NotifyManager;

final class aa implements Runnable {
    private final /* synthetic */ Context a;

    aa(Context context) {
        this.a = context;
    }

    public final void run() {
        NotifyManager notifyManager = new NotifyManager(this.a, Cocos2dxNotifyManager.a(this.a));
    }
}
