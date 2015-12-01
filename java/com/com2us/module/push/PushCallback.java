package com.com2us.module.push;

public interface PushCallback {
    void onReceivedGCMPush(int i, int i2);

    void onReceivedLocalPush(int i, int i2);
}
