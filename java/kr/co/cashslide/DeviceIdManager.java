package kr.co.cashslide;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import jp.co.cyberz.fox.a.a.i;

class DeviceIdManager {
    DeviceIdManager() {
    }

    public static String getDeviceId(Context context) {
        String deviceId = null;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
        if (telephonyManager != null) {
            deviceId = telephonyManager.getDeviceId();
        }
        if (deviceId == null) {
            String macAddr = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddr != null) {
                macAddr = macAddr.replaceAll(":", i.a);
            }
            deviceId = macAddr;
        }
        if (deviceId == null || deviceId.equals(i.a)) {
            deviceId = "ErrorDeviceId";
        }
        try {
            deviceId = EncryptManager.encryptMsg(deviceId);
        } catch (Exception e) {
        }
        return deviceId;
    }
}
