package com.mobileapptracker;

import android.widget.Toast;

final class al implements Runnable {
    final /* synthetic */ MobileAppTracker a;

    al(MobileAppTracker mobileAppTracker) {
        this.a = mobileAppTracker;
    }

    public final void run() {
        Toast.makeText(this.a.mContext, "MAT Debug Mode Enabled, do not release with this enabled!!", 1).show();
    }
}
