package com.com2us.peppermint;

import android.view.View;
import android.view.View.OnClickListener;

class h implements OnClickListener {
    final /* synthetic */ PeppermintDialog a;

    h(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void onClick(View view) {
        this.a.dismiss();
    }
}
