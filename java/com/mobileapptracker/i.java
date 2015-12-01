package com.mobileapptracker;

final class i implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    i(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        if (this.a == null || this.a.equals(jp.co.cyberz.fox.a.a.i.a)) {
            this.b.params.setCurrencyCode("USD");
        } else {
            this.b.params.setCurrencyCode(this.a);
        }
    }
}
