package com.mobileapptracker;

final class w implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    w(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setMacAddress(this.a);
    }
}
