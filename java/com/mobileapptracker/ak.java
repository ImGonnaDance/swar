package com.mobileapptracker;

import android.widget.Toast;

final class ak implements Runnable {
    final /* synthetic */ MobileAppTracker a;

    ak(MobileAppTracker mobileAppTracker) {
        this.a = mobileAppTracker;
    }

    public final void run() {
        Toast.makeText(this.a.mContext, "MAT Allow Duplicate Requests Enabled, do not release with this enabled!!", 1).show();
    }
}
