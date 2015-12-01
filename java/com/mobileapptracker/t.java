package com.mobileapptracker;

final class t implements Runnable {
    final /* synthetic */ double a;
    final /* synthetic */ MobileAppTracker b;

    t(MobileAppTracker mobileAppTracker, double d) {
        this.b = mobileAppTracker;
        this.a = d;
    }

    public final void run() {
        this.b.params.setLatitude(Double.toString(this.a));
    }
}
