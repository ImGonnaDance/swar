package com.com2us.peppermint;

import com.com2us.peppermint.util.PeppermintLog;

class g implements PeppermintDialogCallback {
    final /* synthetic */ f a;

    g(f fVar) {
        this.a = fVar;
    }

    public void dismiss() {
        PeppermintLog.i("dialog dismiss!!");
        this.a.a.f10a = null;
    }
}
