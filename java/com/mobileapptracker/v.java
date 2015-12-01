package com.mobileapptracker;

final class v implements Runnable {
    final /* synthetic */ double a;
    final /* synthetic */ MobileAppTracker b;

    v(MobileAppTracker mobileAppTracker, double d) {
        this.b = mobileAppTracker;
        this.a = d;
    }

    public final void run() {
        this.b.params.setLongitude(Double.toString(this.a));
    }
}
