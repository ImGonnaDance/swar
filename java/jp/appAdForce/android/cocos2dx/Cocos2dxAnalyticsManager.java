package jp.appAdForce.android.cocos2dx;

import android.app.Activity;
import android.content.Context;
import jp.co.dimage.android.f;

public final class Cocos2dxAnalyticsManager implements f {
    public static void sendEndSession(Context context) {
        ((Activity) context).runOnUiThread(new q(context));
    }

    public static void sendEvent(Context context, String str, int i) {
        ((Activity) context).runOnUiThread(new r(context, str, i));
    }

    public static void sendEvent(Context context, String str, String str2, int i) {
        ((Activity) context).runOnUiThread(new s(context, str, str2, i));
    }

    public static void sendEvent(Context context, String str, String str2, String str3, int i) {
        ((Activity) context).runOnUiThread(new t(context, str, str2, str3, i));
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, double d, int i) {
        ((Activity) context).runOnUiThread(new u(context, str, str2, str3, str4, d, i));
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, double d, int i, String str5) {
        ((Activity) context).runOnUiThread(new x(context, str, str2, str3, str4, d, i, str5));
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, String str5, double d, int i) {
        ((Activity) context).runOnUiThread(new v(context, str, str2, str3, str4, str5, d, i));
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, String str5, double d, int i, String str6) {
        ((Activity) context).runOnUiThread(new m(context, str, str2, str3, str4, str5, d, i, str6));
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, String str5, String str6, double d, int i) {
        ((Activity) context).runOnUiThread(new w(context, str, str2, str3, str4, str5, str6, d, i));
    }

    public static void sendEvent(Context context, String str, String str2, String str3, String str4, String str5, String str6, double d, int i, String str7) {
        ((Activity) context).runOnUiThread(new n(context, str, str2, str3, str4, str5, str6, d, i, str7));
    }

    public static void sendStartSession(Context context) {
        ((Activity) context).runOnUiThread(new l(new Throwable().getStackTrace()[1].getClassName(), context));
    }

    public static void sendUserInfo(Context context, String str) {
        ((Activity) context).runOnUiThread(new o(context, str));
    }

    public static void sendUserInfo(Context context, String str, String str2, String str3, String str4, String str5, String str6) {
        ((Activity) context).runOnUiThread(new p(context, str, str2, str3, str4, str5, str6));
    }
}
