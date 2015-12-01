package com.mobileapptracker;

final class as implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ MobileAppTracker b;

    as(MobileAppTracker mobileAppTracker, boolean z) {
        this.b = mobileAppTracker;
        this.a = z;
    }

    public final void run() {
        if (this.a) {
            this.b.params.setAppAdTrackingEnabled(Integer.toString(1));
        } else {
            this.b.params.setAppAdTrackingEnabled(Integer.toString(0));
        }
    }
}
