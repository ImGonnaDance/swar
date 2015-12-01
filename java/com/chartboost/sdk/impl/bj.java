package com.chartboost.sdk.impl;

import android.content.Context;
import android.view.View;

public final class bj extends View {
    private boolean a = false;

    public bj(Context context) {
        super(context);
        setFocusable(false);
        setBackgroundColor(-1442840576);
    }

    public void a() {
        if (!this.a) {
            bi.a(true, this);
            this.a = true;
        }
    }
}
