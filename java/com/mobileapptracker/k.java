package com.mobileapptracker;

final class k implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    k(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setDeviceId(this.a);
    }
}
