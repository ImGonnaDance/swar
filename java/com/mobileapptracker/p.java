package com.mobileapptracker;

final class p implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    p(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setGoogleUserId(this.a);
    }
}
