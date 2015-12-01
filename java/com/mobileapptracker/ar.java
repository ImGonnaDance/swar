package com.mobileapptracker;

final class ar implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    ar(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setAndroidIdSha256(this.a);
    }
}
