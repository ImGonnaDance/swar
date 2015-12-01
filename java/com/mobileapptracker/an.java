package com.mobileapptracker;

final class an implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ MobileAppTracker b;

    an(MobileAppTracker mobileAppTracker, int i) {
        this.b = mobileAppTracker;
        this.a = i;
    }

    public final void run() {
        this.b.params.setAge(Integer.toString(this.a));
    }
}
