package com.com2us.module.manager;

import android.content.Intent;

public interface ActivityStateListener {
    void onActivityCreated();

    void onActivityDestroyed();

    void onActivityPaused();

    void onActivityRestarted();

    void onActivityResult(int i, int i2, Intent intent);

    void onActivityResumed();

    void onActivityStarted();

    void onActivityStopped();

    void onNewIntent(Intent intent);

    void onWindowFocusChanged(boolean z);
}
