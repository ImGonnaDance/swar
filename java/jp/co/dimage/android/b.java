package jp.co.dimage.android;

import android.content.Context;
import jp.co.cyberz.fox.a.a.i;

public final class b {
    private static String a = i.a;
    private static boolean b = true;
    private static boolean c = false;

    public interface a {
        void a(String str, boolean z);
    }

    public static String a() {
        return a;
    }

    public static void a(Context context, a aVar) {
        if (b() && context != null) {
            new Thread(new c(context, aVar)).start();
        }
    }

    private static Object b(Context context) {
        Object obj = null;
        try {
            Class cls = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
            obj = cls.getMethod("getAdvertisingIdInfo", new Class[]{Context.class}).invoke(cls, new Object[]{context});
        } catch (Exception e) {
            a.c(f.aU, "Could not get advertisingID from Google Play Services.");
        }
        return obj;
    }

    public static boolean b() {
        try {
            Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient");
            return true;
        } catch (Exception e) {
            a.c(f.aU, "Google Play Services were not found.");
            return false;
        }
    }

    public static String c() {
        return b ? a.d : a.e;
    }

    private static String c(Object obj) {
        try {
            return (String) obj.getClass().getMethod("getId", new Class[0]).invoke(obj, new Object[0]);
        } catch (Exception e) {
            a.c(f.aU, "Could not get advertisingID from Google Play Services.");
            return null;
        }
    }

    private static boolean d(Object obj) {
        try {
            return ((Boolean) obj.getClass().getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(obj, new Object[0])).booleanValue();
        } catch (Exception e) {
            return true;
        }
    }

    private static boolean f() {
        return c;
    }
}
