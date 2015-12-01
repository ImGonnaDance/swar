package com.com2us.module.newsbanner2;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import com.com2us.module.util.DeviceIdentity;
import com.com2us.module.util.WrapperUtility;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;

public class NewsBannerData {
    public static final int ANDROID_ID = 7;
    public static final int APP_ID = 1;
    public static final int APP_VERSION = 2;
    public static final int APP_VERSION_CODE = 13;
    public static final int COUNTRY = 6;
    public static final int DEVICE_ID = 4;
    public static final int DEVICE_MODEL = 3;
    public static final int DID = 14;
    public static final int LANGUAGE = 11;
    public static final int LINE_NUMBER = 10;
    public static final int MAC_ADDR = 9;
    public static final int OS_VERSION = 5;
    public static final int SERIAL_NO = 8;
    public static final int UID = 12;
    public static int applicationScreenHeight;
    public static int applicationScreenWidth;
    private static final HashMap<Integer, String> constanceDataMap = new HashMap();

    class AnonymousClass1 implements Runnable {
        private final /* synthetic */ Activity val$activity;

        AnonymousClass1(Activity activity) {
            this.val$activity = activity;
        }

        public void run() {
            try {
                NewsBannerData.constanceDataMap.put(Integer.valueOf(NewsBannerData.DID), DeviceIdentity.getDID(this.val$activity));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String get(int index) {
        switch (index) {
            case COUNTRY /*6*/:
                setCountry();
                break;
            case LANGUAGE /*11*/:
                setLanguage();
                break;
        }
        return (String) constanceDataMap.get(Integer.valueOf(index));
    }

    public static void setApplicationScreenSize(int width, int height) {
        applicationScreenHeight = height;
        applicationScreenWidth = width;
    }

    public static void setAppid(String appid) {
        constanceDataMap.put(Integer.valueOf(APP_ID), appid);
    }

    public static void setUid(String uid) {
        constanceDataMap.put(Integer.valueOf(UID), uid);
    }

    public static void create(Activity activity) {
        String serial_no;
        constanceDataMap.put(Integer.valueOf(APP_ID), activity.getPackageName());
        try {
            constanceDataMap.put(Integer.valueOf(APP_VERSION), activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            constanceDataMap.put(Integer.valueOf(APP_VERSION_CODE), String.valueOf(activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).versionCode));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        TelephonyManager teleManager = (TelephonyManager) activity.getSystemService("phone");
        WifiManager wifiManager = (WifiManager) activity.getSystemService("wifi");
        constanceDataMap.put(Integer.valueOf(DEVICE_MODEL), Build.MODEL);
        try {
            constanceDataMap.put(Integer.valueOf(DEVICE_ID), teleManager.getDeviceId());
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        constanceDataMap.put(Integer.valueOf(OS_VERSION), VERSION.RELEASE);
        constanceDataMap.put(Integer.valueOf(ANDROID_ID), Secure.getString(activity.getContentResolver(), "android_id"));
        if (VERSION.SDK_INT < MAC_ADDR) {
            try {
                Class<?> c = Class.forName("android.os.SystemProperties");
                Class[] clsArr = new Class[APP_ID];
                clsArr[0] = String.class;
                Method get = c.getMethod("get", clsArr);
                Object[] objArr = new Object[APP_ID];
                objArr[0] = "ro.serialno";
                serial_no = (String) get.invoke(c, objArr);
            } catch (Exception e3) {
                serial_no = null;
            }
        } else {
            serial_no = Build.SERIAL;
        }
        constanceDataMap.put(Integer.valueOf(SERIAL_NO), serial_no);
        try {
            constanceDataMap.put(Integer.valueOf(MAC_ADDR), WrapperUtility.getMacAddress(activity));
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            constanceDataMap.put(Integer.valueOf(LINE_NUMBER), teleManager.getLine1Number());
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        setLanguage();
        setCountry();
        new Thread(new AnonymousClass1(activity)).start();
    }

    private static void setCountry() {
        try {
            constanceDataMap.put(Integer.valueOf(COUNTRY), Locale.getDefault().getCountry());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void setLanguage() {
        try {
            constanceDataMap.put(Integer.valueOf(LANGUAGE), Locale.getDefault().getLanguage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
