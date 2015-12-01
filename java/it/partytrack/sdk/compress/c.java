package it.partytrack.sdk.compress;

import android.content.Intent;

final class c implements Runnable {
    private final /* synthetic */ Intent a;

    c(Intent intent) {
        this.a = intent;
    }

    public final void run() {
        d.f110a.startActivity(this.a);
    }
}
