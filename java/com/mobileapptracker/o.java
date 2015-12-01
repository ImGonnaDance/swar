package com.mobileapptracker;

final class o implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ MobileAppTracker b;

    o(MobileAppTracker mobileAppTracker, int i) {
        this.b = mobileAppTracker;
        this.a = i;
    }

    public final void run() {
        this.b.params.setGender(Integer.toString(this.a));
    }
}
