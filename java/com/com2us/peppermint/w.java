package com.com2us.peppermint;

import android.widget.Toast;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;

class w implements Runnable {
    final /* synthetic */ b a;

    w(b bVar) {
        this.a = bVar;
    }

    public void run() {
        Toast.makeText(this.a.a.getContext(), "URL does not exist.", UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS).show();
    }
}
