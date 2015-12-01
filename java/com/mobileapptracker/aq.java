package com.mobileapptracker;

final class aq implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    aq(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setAndroidIdSha1(this.a);
    }
}
