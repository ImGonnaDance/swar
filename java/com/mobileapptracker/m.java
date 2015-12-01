package com.mobileapptracker;

final class m implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ MobileAppTracker b;

    m(MobileAppTracker mobileAppTracker, boolean z) {
        this.b = mobileAppTracker;
        this.a = z;
    }

    public final void run() {
        if (this.a) {
            this.b.params.setExistingUser(Integer.toString(1));
        } else {
            this.b.params.setExistingUser(Integer.toString(0));
        }
    }
}
