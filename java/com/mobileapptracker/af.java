package com.mobileapptracker;

final class af implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    af(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setUserEmail(this.a);
    }
}
