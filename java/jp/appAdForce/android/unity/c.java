package jp.appAdForce.android.unity;

import android.app.Activity;

final class c implements Runnable {
    final /* synthetic */ AdManagerUnity a;
    private final /* synthetic */ Activity b;

    c(AdManagerUnity adManagerUnity, Activity activity) {
        this.a = adManagerUnity;
        this.b = activity;
    }

    public final void run() {
        this.a.c.setUrlScheme(this.b.getIntent());
    }
}
