package com.com2us.module.view;

import android.view.SurfaceView;

public abstract class SurfaceViewWrapper {
    protected SurfaceView view;

    public abstract void queueEvent(Runnable runnable);

    public SurfaceViewWrapper(SurfaceView view) {
        this.view = view;
    }

    public SurfaceView getView() {
        return this.view;
    }
}
