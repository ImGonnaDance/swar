package com.com2us.peppermint;

import android.widget.Toast;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;

class s implements Runnable {
    final /* synthetic */ PeppermintDialog a;

    s(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run() {
        Toast.makeText(this.a.getContext(), this.a.f18a, UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS).show();
    }
}
