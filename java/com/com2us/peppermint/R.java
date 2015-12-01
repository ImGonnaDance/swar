package com.com2us.peppermint;

import android.widget.Toast;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;

class r implements Runnable {
    final /* synthetic */ PeppermintDialog a;

    r(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run() {
        Toast.makeText(this.a.getContext(), this.a.f18a, UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS).show();
    }
}
