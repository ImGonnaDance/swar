package com.wellbia.xigncode.util;

import android.content.Context;
import java.lang.reflect.InvocationTargetException;

public class WBSystemProperties {
    public static String getSystemProperties(Context context, String str) {
        try {
            Class loadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return (String) loadClass.getMethod("get", new Class[]{String.class}).invoke(loadClass, new Object[]{new String(str)});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SecurityException e2) {
            e2.printStackTrace();
            return null;
        } catch (NoSuchMethodException e3) {
            e3.printStackTrace();
            return null;
        } catch (IllegalArgumentException e4) {
            e4.printStackTrace();
            return null;
        } catch (IllegalAccessException e5) {
            e5.printStackTrace();
            return null;
        } catch (InvocationTargetException e6) {
            e6.printStackTrace();
            return null;
        }
    }

    public static String getHandSetProperties(Context context, String str) {
        return null;
    }
}
