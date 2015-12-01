package com.mobileapptracker;

final class r implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ MobileAppTracker b;

    r(MobileAppTracker mobileAppTracker, boolean z) {
        this.b = mobileAppTracker;
        this.a = z;
    }

    public final void run() {
        if (this.a) {
            this.b.params.setIsPayingUser(Integer.toString(1));
        } else {
            this.b.params.setIsPayingUser(Integer.toString(0));
        }
    }
}
