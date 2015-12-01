package com.com2us.wrapper.function;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.wrapper.common.CCommonAPI;
import java.util.Locale;
import jp.co.cyberz.fox.a.a.i;

public class CFunction {
    private static int a = 1;
    private static int b = 1;
    private static int c = 1;
    private static int d = 1;
    private static TelephonyManager e = null;
    private static Vibrator f = null;
    private static ActivityManager g = null;
    private static Activity h = null;
    private static GLSurfaceView i = null;
    private static String j;
    private static String k;

    public static int convertDPtoPX(int i) {
        return (int) ((h.getResources().getDisplayMetrics().density * ((float) i)) + 0.5f);
    }

    public static synchronized Point convertDisplaytoOriginal(Point point) {
        Point point2;
        synchronized (CFunction.class) {
            point2 = new Point();
            point2.x = (int) (((double) ((a * point.x) / c)) + 0.5d);
            point2.y = (int) (((double) ((b * point.y) / d)) + 0.5d);
        }
        return point2;
    }

    public static synchronized Point[] convertDisplaytoOriginal(Point[] pointArr) {
        Point[] pointArr2;
        synchronized (CFunction.class) {
            pointArr2 = new Point[pointArr.length];
            for (int i = 0; i < pointArr.length; i++) {
                pointArr2[i] = convertDisplaytoOriginal(pointArr[i]);
            }
        }
        return pointArr2;
    }

    public static synchronized Point convertOriginaltoDisplay(Point point) {
        Point point2;
        synchronized (CFunction.class) {
            point2 = new Point();
            point2.x = (int) (((double) ((c * point.x) / a)) + 0.5d);
            point2.y = (int) (((double) ((d * point.y) / b)) + 0.5d);
        }
        return point2;
    }

    public static synchronized Point[] convertOriginaltoDisplay(Point[] pointArr) {
        Point[] pointArr2;
        synchronized (CFunction.class) {
            pointArr2 = new Point[pointArr.length];
            for (int i = 0; i < pointArr.length; i++) {
                pointArr2[i] = convertOriginaltoDisplay(pointArr[i]);
            }
        }
        return pointArr2;
    }

    public static boolean getActiveAirplaneMode() {
        return System.getInt(h.getContentResolver(), "airplane_mode_on", 0) == 1;
    }

    public static Activity getActivity() {
        return h;
    }

    public static String getAndroidId() {
        return Secure.getString(h.getContentResolver(), "android_id");
    }

    public static String getApkFilePath() {
        try {
            return h.getPackageManager().getApplicationInfo(getPackageName(), 0).sourceDir;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getCom2usUniqueId() {
        String str = null;
        String nonModifiedMacAddress = CCommonAPI.getNonModifiedMacAddress();
        if (nonModifiedMacAddress != null) {
            try {
                str = "01" + CCommonAPI.generateHashtoHexString(nonModifiedMacAddress.getBytes("UTF-8"), "SHA-1") + CCommonAPI.generateCRC32toHexString(nonModifiedMacAddress.getBytes("UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getCountry(String str) {
        String country = Locale.getDefault().getCountry();
        return (country == null || country.length() == 0) ? str : country;
    }

    public static String getDeviceId() {
        return e.getDeviceId();
    }

    public static String getDeviceId(String str) {
        String deviceId = getDeviceId();
        return deviceId == null ? str : deviceId;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }

    public static GLSurfaceView getGLSurfaceView() {
        return i;
    }

    public static String getInitialCountry() {
        return j;
    }

    public static String getInitialLanguage() {
        return k;
    }

    public static String getLanguage(String str) {
        String language = Locale.getDefault().getLanguage();
        return (language == null || language.length() == 0) ? str : language.equals("in") ? PeppermintConstant.JSON_KEY_ID : language.equals("iw") ? "he" : language.equals("ji") ? "yi" : language;
    }

    public static String getMIN(String str) {
        String phoneNumber = getPhoneNumber(str);
        return phoneNumber == null ? str : phoneNumber.length() >= 10 ? phoneNumber.substring(phoneNumber.length() - 10) : i.a;
    }

    public static long getNativeAvailableMemory() {
        MemoryInfo memoryInfo = new MemoryInfo();
        g.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    public static String getPackageName() {
        return h.getPackageName();
    }

    public static String getPhoneNumber() {
        return e.getLine1Number();
    }

    public static String getPhoneNumber(String str) {
        String phoneNumber = getPhoneNumber();
        return phoneNumber == null ? str : phoneNumber;
    }

    public static boolean getRoaming() {
        return new ServiceState().getRoaming();
    }

    public static String getSystemVersion() {
        return VERSION.RELEASE;
    }

    public static int getSystemVersionCode() {
        return VERSION.SDK_INT;
    }

    public static String getUserDir() {
        String path = h.getFilesDir().getPath();
        return path.substring(0, path.indexOf(h.getPackageName()));
    }

    public static void initialize(Activity activity, GLSurfaceView gLSurfaceView) {
        h = activity;
        i = gLSurfaceView;
        g = (ActivityManager) h.getSystemService("activity");
        e = (TelephonyManager) h.getSystemService("phone");
        f = (Vibrator) h.getSystemService("vibrator");
        j = getCountry("US");
        k = getLanguage("en");
    }

    public static boolean isActiveUsim() {
        return e.getSimState() != 1;
    }

    public static synchronized void onSizeChanged(int i, int i2, int i3, int i4) {
        synchronized (CFunction.class) {
            a = i;
            b = i2;
            c = i3;
            d = i4;
        }
    }

    public static void runOnGlThread(Runnable runnable) {
        i.queueEvent(runnable);
    }

    public static void runOnUiThread(Runnable runnable) {
        h.runOnUiThread(runnable);
    }

    public static void setControlByPX(View view, View view2, int i, int i2, int i3, int i4) {
        final View view3 = view;
        final int i5 = i;
        final int i6 = i2;
        final View view4 = view2;
        final int i7 = i3;
        final int i8 = i4;
        runOnUiThread(new Runnable() {
            public void run() {
                view3.setPadding(i5, i6, 0, 0);
                LayoutParams layoutParams = view4.getLayoutParams();
                layoutParams.width = i7;
                layoutParams.height = i8;
                view4.setLayoutParams(layoutParams);
            }
        });
    }

    public static void startVibrate(long j) {
        f.vibrate(j);
    }

    public static void stopVibrate() {
        f.cancel();
    }

    public static void uninitialize() {
        h = null;
        i = null;
        g = null;
        e = null;
        f = null;
    }
}
