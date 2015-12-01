package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.co.dimage.android.f;

public final class AnalyticsManagerUnity implements f {
    private AnalyticsManagerUnity() {
    }

    public static void sendEndSession(Activity activity) {
        activity.runOnUiThread(new r(activity));
    }

    public static void sendEvent(Activity activity, String str, int i) {
        activity.runOnUiThread(new s(activity, str, i));
    }

    public static void sendEvent(Activity activity, String str, String str2, int i) {
        activity.runOnUiThread(new t(activity, str, str2, i));
    }

    public static void sendEvent(Activity activity, String str, String str2, String str3, int i) {
        activity.runOnUiThread(new u(activity, str, str2, str3, i));
    }

    public static void sendEvent(Activity activity, String str, String str2, String str3, String str4, double d, int i) {
        activity.runOnUiThread(new v(activity, str, str2, str3, str4, d, i));
    }

    public static void sendEvent(Activity activity, String str, String str2, String str3, String str4, double d, int i, String str5) {
        activity.runOnUiThread(new y(activity, str, str2, str3, str4, d, i, str5));
    }

    public static void sendEvent(Activity activity, String str, String str2, String str3, String str4, String str5, double d, int i) {
        activity.runOnUiThread(new w(activity, str, str2, str3, str4, str5, d, i));
    }

    public static void sendEvent(Activity activity, String str, String str2, String str3, String str4, String str5, double d, int i, String str6) {
        activity.runOnUiThread(new n(activity, str, str2, str3, str4, str5, d, i, str6));
    }

    public static void sendEvent(Activity activity, String str, String str2, String str3, String str4, String str5, String str6, double d, int i) {
        activity.runOnUiThread(new x(activity, str, str2, str3, str4, str5, str6, d, i));
    }

    public static void sendEvent(Activity activity, String str, String str2, String str3, String str4, String str5, String str6, double d, int i, String str7) {
        activity.runOnUiThread(new o(activity, str, str2, str3, str4, str5, str6, d, i, str7));
    }

    public static void sendStartSession(Activity activity) {
        activity.runOnUiThread(new m(new Throwable().getStackTrace()[1].getClassName(), activity));
    }

    public static void sendUserInfo(Activity activity, String str) {
        activity.runOnUiThread(new p(activity, str));
    }

    public static void sendUserInfo(Activity activity, String str, String str2, String str3, String str4, String str5, String str6) {
        activity.runOnUiThread(new q(activity, str, str2, str3, str4, str5, str6));
    }
}
