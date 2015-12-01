package com.com2us.peppermint;

import android.widget.Toast;

class z implements Runnable {
    final /* synthetic */ b a;
    private final /* synthetic */ String f102a;

    z(b bVar, String str) {
        this.a = bVar;
        this.f102a = str;
    }

    public void run() {
        Toast.makeText(this.a.a.f18a, "Error : " + this.f102a, 0).show();
    }
}
