package com.mobileapptracker;

import android.location.Location;

final class u implements Runnable {
    final /* synthetic */ Location a;
    final /* synthetic */ MobileAppTracker b;

    u(MobileAppTracker mobileAppTracker, Location location) {
        this.b = mobileAppTracker;
        this.a = location;
    }

    public final void run() {
        this.b.params.setLocation(this.a);
    }
}
