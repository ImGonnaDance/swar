package com.mobileapptracker;

import android.content.Context;
import android.provider.Settings.Secure;
import android.util.Log;
import java.lang.ref.WeakReference;

final class d implements Runnable {
    final /* synthetic */ MATParameters a;
    private final WeakReference<Context> b;

    public d(MATParameters mATParameters, Context context) {
        this.a = mATParameters;
        this.b = new WeakReference(context);
    }

    public final void run() {
        try {
            new Class[1][0] = Context.class;
            Object invoke = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getDeclaredMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(null, new Object[]{this.b.get()});
            String str = (String) Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getDeclaredMethod("getId", new Class[0]).invoke(invoke, new Object[0]);
            boolean booleanValue = ((Boolean) Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info").getDeclaredMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(invoke, new Object[0])).booleanValue();
            if (this.a.b.params == null) {
                this.a.setGoogleAdvertisingId(str);
                this.a.setGoogleAdTrackingLimited(Integer.toString(booleanValue ? 1 : 0));
            }
            this.a.b.setGoogleAdvertisingId(str, booleanValue);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("MobileAppTracker", "MAT SDK failed to get Google Advertising Id, collecting ANDROID_ID instead");
            if (this.a.b.params == null) {
                this.a.setAndroidId(Secure.getString(((Context) this.b.get()).getContentResolver(), "android_id"));
            }
            this.a.b.setAndroidId(Secure.getString(((Context) this.b.get()).getContentResolver(), "android_id"));
        }
    }
}
