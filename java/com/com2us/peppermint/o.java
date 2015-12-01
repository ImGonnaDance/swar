package com.com2us.peppermint;

import android.view.View.OnSystemUiVisibilityChangeListener;

class o implements OnSystemUiVisibilityChangeListener {
    final /* synthetic */ PeppermintDialog a;

    o(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void onSystemUiVisibilityChange(int i) {
        if (i == 0) {
            this.a.getWindow().getDecorView().setSystemUiVisibility(4);
        }
    }
}
