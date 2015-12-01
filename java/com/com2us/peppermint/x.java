package com.com2us.peppermint;

import android.widget.Toast;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;

class x implements Runnable {
    final /* synthetic */ b a;

    x(b bVar) {
        this.a = bVar;
    }

    public void run() {
        Toast.makeText(this.a.a.getContext(), "GooglePlay does not exist.", UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS).show();
    }
}
