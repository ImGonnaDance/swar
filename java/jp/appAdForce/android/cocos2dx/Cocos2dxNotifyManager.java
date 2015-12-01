package jp.appAdForce.android.cocos2dx;

import android.app.Activity;
import android.content.Context;
import jp.appAdForce.android.AdManager;
import jp.co.cyberz.fox.a.a.i;

public class Cocos2dxNotifyManager {
    public static String a;

    static /* synthetic */ AdManager a(Context context) {
        AdManager adManager = new AdManager(context);
        if (!(a == null || i.a.equals(a))) {
            adManager.setServerUrl(a);
        }
        return adManager;
    }

    private static AdManager b(Context context) {
        AdManager adManager = new AdManager(context);
        if (!(a == null || i.a.equals(a))) {
            adManager.setServerUrl(a);
        }
        return adManager;
    }

    public static void notifyManager(Context context) {
        ((Activity) context).runOnUiThread(new aa(context));
    }

    public static void registerToGCM(Context context, String str) {
        ((Activity) context).runOnUiThread(new ab(context, str));
    }
}
