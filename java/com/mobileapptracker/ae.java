package com.mobileapptracker;

final class ae implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    ae(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setTwitterUserId(this.a);
    }
}
