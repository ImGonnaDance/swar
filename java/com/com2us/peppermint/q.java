package com.com2us.peppermint;

import android.widget.Toast;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;

class q implements Runnable {
    final /* synthetic */ PeppermintDialog a;

    q(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run() {
        Toast.makeText(this.a.getContext(), "Application does not exist.", UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS).show();
    }
}
