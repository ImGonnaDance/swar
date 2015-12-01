package com.mobileapptracker;

final class j implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    j(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setDeviceBrand(this.a);
    }
}
