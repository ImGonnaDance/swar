package com.mobileapptracker;

final class x implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    x(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setOsVersion(this.a);
    }
}
