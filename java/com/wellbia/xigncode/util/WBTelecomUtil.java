package com.wellbia.xigncode.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import jp.co.cyberz.fox.a.a.i;

public class WBTelecomUtil {
    static final String CODE_KT = "45008";
    static final String CODE_LGT = "45006";
    static final String CODE_LGT_ERR = "450006";
    static final String CODE_SKT = "45005";
    public static final int TELECOM_TYPE_KT = 2;
    public static final int TELECOM_TYPE_LGT = 3;
    public static final int TELECOM_TYPE_NONE = -1;
    public static final int TELECOM_TYPE_OTHER = 0;
    public static final int TELECOM_TYPE_SKT = 1;
    private static String devPhoneNum = null;
    private static boolean isDevPhone = false;

    public static boolean isDevPhone() {
        return isDevPhone;
    }

    public static void setDevPhone(boolean z) {
        isDevPhone = z;
    }

    public static String getDevPhoneNum() {
        return devPhoneNum;
    }

    public static void setDevPhoneNum(String str) {
        devPhoneNum = str;
    }

    public static boolean isSimCardEnable(Context context) {
        if (((TelephonyManager) context.getSystemService("phone")).getSimState() == TELECOM_TYPE_SKT) {
            info("isSimCardEnable : false ");
            return false;
        }
        info("isSimCardEnable : true ");
        return true;
    }

    public static int getTelecomType(Context context) {
        String networkOperator = ((TelephonyManager) context.getSystemService("phone")).getNetworkOperator();
        info("getTelecomType :  networkOperator " + networkOperator);
        if (networkOperator == null) {
            return TELECOM_TYPE_NONE;
        }
        if (networkOperator.equals(CODE_SKT)) {
            return TELECOM_TYPE_SKT;
        }
        if (networkOperator.equals(CODE_KT)) {
            return TELECOM_TYPE_KT;
        }
        if (networkOperator.equals(CODE_LGT) || networkOperator.equals(CODE_LGT_ERR)) {
            return TELECOM_TYPE_LGT;
        }
        return TELECOM_TYPE_OTHER;
    }

    public static String getTelecomName(Context context, String str) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null) {
            return telephonyManager.getNetworkOperatorName();
        }
        return str;
    }

    public static String getCtnNumber(Context context) {
        String line1Number = ((TelephonyManager) context.getSystemService("phone")).getLine1Number();
        if (line1Number == null || line1Number.equals(a.d)) {
            return null;
        }
        if (isDevPhone && devPhoneNum != null) {
            line1Number = devPhoneNum;
        }
        if (line1Number.startsWith("+82") && line1Number.length() > TELECOM_TYPE_LGT) {
            line1Number = line1Number.substring(TELECOM_TYPE_LGT);
        }
        if (line1Number.startsWith("10") && line1Number.length() == 10) {
            line1Number = a.d + line1Number;
        }
        line1Number = line1Number.replaceAll("[^0-9]+", i.a);
        if (line1Number == null || TextUtils.isEmpty(line1Number)) {
            return null;
        }
        return line1Number;
    }

    public static String getPrintCtnNumber(Context context) {
        String ctnNumber = getCtnNumber(context);
        if (ctnNumber != null) {
            Object[] objArr = new Object[TELECOM_TYPE_LGT];
            objArr[TELECOM_TYPE_OTHER] = ctnNumber.subSequence(TELECOM_TYPE_OTHER, TELECOM_TYPE_LGT);
            objArr[TELECOM_TYPE_SKT] = ctnNumber.substring(TELECOM_TYPE_LGT, ctnNumber.length() - 4);
            objArr[TELECOM_TYPE_KT] = ctnNumber.substring(ctnNumber.length() - 4);
            ctnNumber = String.format("%3s-%4s-%4s", objArr);
        }
        info("getPrintCtnNumber :" + ctnNumber);
        return ctnNumber;
    }

    public static String getMacAddress(Context context) {
        WifiInfo connectionInfo = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo();
        return connectionInfo == null ? null : connectionInfo.getMacAddress();
    }

    public static String getDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        String deviceId = telephonyManager.getDeviceId();
        if (deviceId == null) {
            deviceId = getMacAddress(context);
        }
        if (deviceId != null) {
            return deviceId;
        }
        try {
            Class cls = Build.class;
            String obj = Build.class.getDeclaredField("SERIAL").get(Build.class).toString();
            if (obj != null) {
                deviceId = obj;
            }
            info("mDeviceKey :" + deviceId + " (DeviceId : " + telephonyManager.getDeviceId() + "  SERIAL : " + obj + " MacAddress : " + getMacAddress(context));
            return deviceId;
        } catch (Exception e) {
            Exception exception = e;
            String str = deviceId;
            exception.printStackTrace();
            return str;
        }
    }

    public static void info(String str) {
        Log.v("WBTelecomUtil", str);
    }
}
