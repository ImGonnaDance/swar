package com.mobileapptracker;

import jp.co.cyberz.fox.a.a.i;

final class y implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    y(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        if (this.a == null || this.a.equals(i.a)) {
            this.b.params.setPackageName(this.b.mContext.getPackageName());
        } else {
            this.b.params.setPackageName(this.a);
        }
    }
}
