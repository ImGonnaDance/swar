package com.mobileapptracker;

final class l implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    l(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setDeviceModel(this.a);
    }
}
