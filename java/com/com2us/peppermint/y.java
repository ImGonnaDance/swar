package com.com2us.peppermint;

import android.widget.Toast;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;

class y implements Runnable {
    final /* synthetic */ b a;

    y(b bVar) {
        this.a = bVar;
    }

    public void run() {
        Toast.makeText(this.a.a.getContext(), "Application does not exist.", UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS).show();
    }
}
