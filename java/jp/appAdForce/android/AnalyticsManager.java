package jp.appAdForce.android;

import android.content.Context;
import jp.co.cyberz.fox.a.a.e;
import jp.co.dimage.android.f;

public final class AnalyticsManager implements f {
    private static e a = null;

    private AnalyticsManager() {
    }

    private static void a(Context context) {
        if (a == null) {
            a = e.a(new AdManager(context));
        }
    }

    public static void a(String str, Context context) {
        if (e.b(context).booleanValue()) {
            a(context);
            a.a(str, context);
            return;
        }
        e.b();
    }

    public static void sendEndSession(Context context) {
        if (e.b(context).booleanValue()) {
            a(context);
            a.a(context);
        }
    }

    public static void sendEvent(Context context, String str, int i) {
        a(context);
        a.a(context, str, i);
    }

    public static void sendEvent(Context context, String str, String str2, int i) {
        a(context);
        a.a(context, str, str2, i);
    }

    public static void sendEvent(Context context, String str, String str2, String str3, int i) {
        a(context);
        a.a(context, str, str2, str3, i);
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, double d, int i) {
        a(context);
        a.a(context, str, str2, str3, str4, d, i);
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, double d, int i, String str5) {
        a(context);
        a.a(context, str, str2, str3, str4, d, i, str5);
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, String str5, double d, int i) {
        a(context);
        a.a(context, str, str2, str3, str4, str5, d, i);
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, String str5, double d, int i, String str6) {
        a(context);
        a.a(context, str, str2, str3, str4, str5, d, i, str6);
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, String str5, String str6, double d, int i) {
        a(context);
        a.a(context, str, str2, str3, str4, str5, str6, d, i);
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, String str5, String str6, double d, int i, String str7) {
        a(context);
        a.a(context, str, str2, str3, str4, str5, str6, d, i, str7);
    }

    public static void sendStartSession(Context context) {
        a(new Throwable().getStackTrace()[1].getClassName(), context);
    }

    public static void sendUserInfo(Context context, String str) {
        a(context);
        a.a(str);
    }

    public static void sendUserInfo(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        a(context);
        a.a(str, str2, str3, str4, str5, str6);
    }
}
