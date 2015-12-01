package com.com2us.module.manager;

import android.content.Intent;

public abstract class ActivityStateAdapter implements ActivityStateListener {
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

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    }
}
