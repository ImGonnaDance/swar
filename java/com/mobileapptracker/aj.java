package com.mobileapptracker;

final class aj implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ MobileAppTracker b;

    aj(MobileAppTracker mobileAppTracker, boolean z) {
        this.b = mobileAppTracker;
        this.a = z;
    }

    public final void run() {
        if (this.a) {
            this.b.params.setAllowDuplicates(Integer.toString(1));
        } else {
            this.b.params.setAllowDuplicates(Integer.toString(0));
        }
    }
}
