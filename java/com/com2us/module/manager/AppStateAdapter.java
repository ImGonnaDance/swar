package com.com2us.module.manager;

import android.content.Intent;

public abstract class AppStateAdapter implements ActivityStateListener, CletStateListener {
    public void onActivityStarted() {
    }

    public void onActivityRestarted() {
    }

    public void onActivityResumed() {
    }

    public void onNewIntent(Intent intent) {
    }

    public void onActivityPaused() {
    }

    public void onActivityStopped() {
    }

    public void onActivityDestroyed() {
    }

    public void onActivityCreated() {
    }

    public void onWindowFocusChanged(boolean hasFocus) {
    }

    public void onCletStarted() {
    }

    public void onCletResumed() {
    }

    public void onCletPaused() {
    }

    public void onCletDestroyed() {
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    }
}
