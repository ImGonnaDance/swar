package com.mobileapptracker;

final class ai implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    ai(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        this.b.params.setPluginName(this.a);
    }
}
