package com.mobileapptracker;

final class ao implements Runnable {
    final /* synthetic */ double a;
    final /* synthetic */ MobileAppTracker b;

    ao(MobileAppTracker mobileAppTracker, double d) {
        this.b = mobileAppTracker;
        this.a = d;
    }

    public final void run() {
        this.b.params.setAltitude(Double.toString(this.a));
    }
}
