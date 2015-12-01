package com.mobileapptracker;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

final class aa implements Runnable {
    final /* synthetic */ Activity a;
    final /* synthetic */ MobileAppTracker b;

    aa(MobileAppTracker mobileAppTracker, Activity activity) {
        this.b = mobileAppTracker;
        this.a = activity;
    }

    public final void run() {
        this.b.params.setReferralSource(this.a.getCallingPackage());
        Intent intent = this.a.getIntent();
        if (intent != null) {
            Uri data = intent.getData();
            if (data != null) {
                this.b.params.setReferralUrl(data.toString());
            }
        }
    }
}
