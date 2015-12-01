package jp.appAdForce.android.cocos2dx;

import android.app.Activity;
import android.content.Context;
import java.util.HashMap;

public class Cocos2dxLtvManager {
    private static HashMap a;

    public static void addParam(String str, int i) {
        if (a == null) {
            a = new HashMap();
        }
        a.put(str, String.valueOf(i));
    }

    public static void addParam(String str, String str2) {
        if (a == null) {
            a = new HashMap();
        }
        a.put(str, str2);
    }

    public static void sendLtvConversion(Context context, int i) {
        ((Activity) context).runOnUiThread(new y(context, i));
    }

    public static void sendLtvConversion(Context context, int i, String str) {
        ((Activity) context).runOnUiThread(new z(context, i, str));
    }
}
