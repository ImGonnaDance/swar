package com.com2us.peppermint;

import android.widget.Toast;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;

class u implements Runnable {
    final /* synthetic */ PeppermintDialog a;

    u(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run() {
        Toast.makeText(this.a.getContext(), "Browser does not exist.", UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS).show();
    }
}
