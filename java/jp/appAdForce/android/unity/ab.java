package jp.appAdForce.android.unity;

import android.app.Activity;

final class ab implements Runnable {
    final /* synthetic */ NotifyManagerUnity a;
    private final /* synthetic */ Activity b;
    private final /* synthetic */ String c;

    ab(NotifyManagerUnity notifyManagerUnity, Activity activity, String str) {
        this.a = notifyManagerUnity;
        this.b = activity;
        this.c = str;
    }

    public final void run() {
        this.a.b.registerToGCM(this.b, this.c);
    }
}
