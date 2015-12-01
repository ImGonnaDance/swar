package jp.appAdForce.android.unity;

import android.app.Activity;

final class d implements Runnable {
    final /* synthetic */ AdManagerUnity a;
    private final /* synthetic */ Activity b;

    d(AdManagerUnity adManagerUnity, Activity activity) {
        this.a = adManagerUnity;
        this.b = activity;
    }

    public final void run() {
        this.a.c.sendReengagementConversion(this.b.getIntent());
    }
}
