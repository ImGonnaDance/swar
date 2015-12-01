package com.mobileapptracker;

final class s implements Runnable {
    final /* synthetic */ MATEvent a;
    final /* synthetic */ MobileAppTracker b;

    s(MobileAppTracker mobileAppTracker, MATEvent mATEvent) {
        this.b = mobileAppTracker;
        this.a = mATEvent;
    }

    public final void run() {
        this.b.a(this.a);
    }
}
