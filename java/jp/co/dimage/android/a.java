package jp.co.dimage.android;

import android.util.Log;

public final class a {
    private static boolean a = false;
    private static boolean b = false;

    public static void a() {
        a = true;
    }

    public static void a(String str, String str2) {
        if (b) {
            Log.i(str, str2);
        }
    }

    public static void b() {
        b = true;
    }

    public static void b(String str, String str2) {
        if (b) {
            Log.e(str, str2);
        }
    }

    public static void c(String str, String str2) {
        if (b) {
            Log.w(str, str2);
        }
    }

    public static void d(String str, String str2) {
        if (a) {
            Log.d(str, str2);
        }
    }

    private static void e(String str, String str2) {
        if (a) {
            Log.v(str, str2);
        }
    }
}
