package com.mobileapptracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class h extends BroadcastReceiver {
    final /* synthetic */ MobileAppTracker a;

    h(MobileAppTracker mobileAppTracker) {
        this.a = mobileAppTracker;
    }

    public final void onReceive(Context context, Intent intent) {
        if (this.a.isRegistered) {
            this.a.dumpQueue();
        }
    }
}
