package com.mobileapptracker;

final class ag implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    ag(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setUserId(this.a);
    }
}
