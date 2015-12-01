package com.com2us.module.inapp.googleplay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GooglePlayBroadcastReceiver extends BroadcastReceiver {
    private GooglePlayBroadcastListener mListener;

    public interface GooglePlayBroadcastListener {
        void receivedBroadcast(Context context, Intent intent);
    }

    public GooglePlayBroadcastReceiver(GooglePlayBroadcastListener listener) {
        this.mListener = listener;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.mListener != null) {
            this.mListener.receivedBroadcast(context, intent);
        }
    }
}
