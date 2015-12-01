package com.mobileapptracker;

final class ap implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    ap(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setAndroidIdMd5(this.a);
    }
}
