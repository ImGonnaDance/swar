package com.com2us.module.manager;

public interface CletStateListener {
    void onCletDestroyed();

    void onCletPaused();

    void onCletResumed();

    void onCletStarted();
}
