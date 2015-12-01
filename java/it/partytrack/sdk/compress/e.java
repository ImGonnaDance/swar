package it.partytrack.sdk.compress;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import it.partytrack.sdk.Track;
import java.util.UUID;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.p;

public final class e {
    private static final Uri a = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");

    private static String a() {
        String string;
        Exception e;
        try {
            Cursor query = d.f110a.getContentResolver().query(a, new String[]{p.d}, null, null, null);
            if (query == null || !query.moveToFirst()) {
                return null;
            }
            string = query.getString(query.getColumnIndex(p.d));
            try {
                query.close();
                return string;
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return string;
            }
        } catch (Exception e3) {
            e = e3;
            string = null;
            e.printStackTrace();
            return string;
        }
    }

    static String a(String str) {
        String str2;
        String str3 = null;
        String[] split = str.split(":");
        if (split.length == 2) {
            str2 = split[0];
            str = split[1];
        } else {
            str2 = null;
        }
        try {
            if (str.equals("android_id")) {
                str3 = Secure.getString(d.f110a.getContentResolver(), "android_id");
            } else if (!str.equals(Track.CLIENT_ID)) {
                if (str.equals("imei")) {
                    str3 = ((TelephonyManager) d.f110a.getSystemService("phone")).getDeviceId();
                } else if (str.equals("mac_address")) {
                    str3 = ((WifiManager) d.f110a.getSystemService("wifi")).getConnectionInfo().getMacAddress();
                } else if (str.equals(Track.UUID)) {
                    str3 = a.a(Track.UUID);
                    if (str3 == null || str3.length() <= 0) {
                        str3 = UUID.randomUUID().toString();
                        a.a(Track.UUID, str3);
                        String a = a.a(Track.UUID);
                        if (a == null || a.length() <= 0) {
                            str3 = i.a;
                            d.f119c.put("sdk_error", "can't create uuid file");
                        } else {
                            d.f119c.remove("sdk_error");
                        }
                    }
                } else if (str.equals("facebook_attribution")) {
                    str3 = a();
                } else if (str.equals("odin1")) {
                    str3 = a.a("SHA-1", Secure.getString(d.f110a.getContentResolver(), "android_id"));
                } else if (str.equals("advertiser_id")) {
                    str3 = b();
                } else {
                    "no getter method exists: " + str;
                }
            }
        } catch (SecurityException e) {
            a.a("Failed to get " + str);
            a.a("Please check your app's permission or PartyTrack app setting");
            str3 = i.a;
        }
        if (str3 == null) {
            str3 = i.a;
        }
        return (str2 == null || str3.length() <= 0) ? str3 : a.a(str2, str3);
    }

    private static String b() {
        String str = i.a;
        if (VERSION.SDK_INT < 9) {
            return str;
        }
        try {
            Object invoke = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getDeclaredMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(null, new Object[]{d.f110a});
            Class cls = invoke.getClass();
            String valueOf = String.valueOf(cls.getDeclaredMethod("getId", new Class[0]).invoke(invoke, new Object[0]));
            try {
                if (valueOf.length() <= 0) {
                    return valueOf;
                }
                d.f119c.put("advertiser_tracking_enabled", ((Boolean) cls.getDeclaredMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(invoke, new Object[0])).booleanValue() ? a.d : a.e);
                return valueOf;
            } catch (ClassNotFoundException e) {
                str = valueOf;
                a.a("maybe not include google play library");
                return str;
            } catch (Exception e2) {
                Exception exception = e2;
                str = valueOf;
                r1 = exception;
                a.a("some error ocurred in getting adverting id");
                if (d.f118b) {
                    return str;
                }
                Exception exception2;
                exception2.printStackTrace();
                return str;
            }
        } catch (ClassNotFoundException e3) {
            a.a("maybe not include google play library");
            return str;
        } catch (Exception e4) {
            exception2 = e4;
            a.a("some error ocurred in getting adverting id");
            if (d.f118b) {
                return str;
            }
            exception2.printStackTrace();
            return str;
        }
    }
}
