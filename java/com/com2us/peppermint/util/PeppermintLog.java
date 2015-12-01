package com.com2us.peppermint.util;

import android.util.Log;
import jp.co.cyberz.fox.a.a.i;

public class PeppermintLog {
    private static String a = "peppermint";
    private static boolean f1a = false;
    private static boolean b = false;

    public static void d(String str) {
        if (!f1a) {
            return;
        }
        if (b) {
            Log.d(a, getCallerClassName() + "::" + str);
        } else {
            Log.d(a, str);
        }
    }

    public static void e(String str) {
        if (!f1a) {
            return;
        }
        if (b) {
            Log.e(a, getCallerClassName() + "::" + str);
        } else {
            Log.e(a, str);
        }
    }

    public static String getCallerClassName() {
        try {
            throw new Exception();
        } catch (Exception e) {
            String simpleClassName = getSimpleClassName(e.getStackTrace()[2].getClassName());
            return simpleClassName != null ? simpleClassName : "Not Found Class";
        }
    }

    public static String getSimpleClassName(String str) {
        if (str == null || i.a.equals(str)) {
            return null;
        }
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf >= 0 ? str.substring(lastIndexOf + 1) : str;
    }

    public static void i(String str) {
        if (!f1a) {
            return;
        }
        if (b) {
            Log.i(a, getCallerClassName() + "::" + str);
        } else {
            Log.i(a, str);
        }
    }
}
