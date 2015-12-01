package jp.appAdForce.android.cocos2dx;

import android.app.Activity;
import android.content.Context;
import jp.appAdForce.android.AdManager;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.f;

public class Cocos2dxAdManager implements f {
    public static String serverUrl;

    public static AdManager a(Context context) {
        AdManager adManager = new AdManager(context);
        if (!(serverUrl == null || i.a.equals(serverUrl))) {
            adManager.setServerUrl(serverUrl);
        }
        return adManager;
    }

    public static void sendConversion(Context context) {
        ((Activity) context).runOnUiThread(new a(context));
    }

    public static void sendConversion(Context context, String str) {
        ((Activity) context).runOnUiThread(new d(context, str));
    }

    public static void sendConversion(Context context, String str, String str2) {
        ((Activity) context).runOnUiThread(new e(context, str, str2));
    }

    public static void sendConversionForMobage(Context context, String str) {
        ((Activity) context).runOnUiThread(new g(context, str));
    }

    public static void sendConversionForMobage(Context context, String str, String str2) {
        ((Activity) context).runOnUiThread(new h(context, str, str2));
    }

    public static void sendConversionForMobageWithCAReward(Context context, String str) {
        ((Activity) context).runOnUiThread(new b(context, str));
    }

    public static void sendConversionForMobageWithCAReward(Context context, String str, String str2) {
        ((Activity) context).runOnUiThread(new c(context, str, str2));
    }

    public static void sendConversionWithCAReward(Context context, String str) {
        ((Activity) context).runOnUiThread(new j(context, str));
    }

    public static void sendConversionWithCAReward(Context context, String str, String str2) {
        ((Activity) context).runOnUiThread(new k(context, str, str2));
    }

    public static void sendConversionWithUrlScheme(Context context) {
        if (context != null) {
            Activity activity = (Activity) context;
            activity.runOnUiThread(new f(context, activity));
        }
    }

    public static void sendUserIdForMobage(Context context, String str) {
        ((Activity) context).runOnUiThread(new i(context, str));
    }

    public static void setOptout(Context context, boolean z) {
        a(context).setOptout(z);
    }

    public static void setServerUrl(String str) {
        serverUrl = str;
    }

    public static void updateFrom2_10_4g() {
        AdManager.updateFrom2_10_4g();
    }
}
