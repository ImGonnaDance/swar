package com.com2us.common.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import com.com2us.common.data.CommonProperties;
import com.com2us.common.data.CommonPropertyConstants;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.f;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public final class NetworkInfo {
    private static final int DEFAULT_TRY_COUNT = 20;
    private static final int DEFAULT_TRY_DELAY = 500;
    private static final int MAC_ADDRESS_LENGTH = 17;
    private static final int MAC_ADDRESS_SEPARATOR_LENGTH = 5;
    private static String mMacAddress;
    private static String mNonModifiedMacAddress;

    public enum ENetworkType {
        WIFI,
        MOBILE,
        NONE
    }

    private NetworkInfo() {
    }

    public static void store(Context context) {
        mNonModifiedMacAddress = CommonProperties.getProperty(CommonPropertyConstants.NON_MODIFIED_MAC_ADDR_PROPERTY);
        mMacAddress = CommonProperties.getProperty(CommonPropertyConstants.MAC_ADDR_PROPERTY);
        if (!(varifyMacAddress(mMacAddress) && varifyNonModifiedMacAddress(mNonModifiedMacAddress))) {
            mMacAddress = null;
            mNonModifiedMacAddress = null;
        }
        if (mMacAddress == null || mNonModifiedMacAddress == null) {
            String nonModifiedMacAddress = _getMacAddress(context, DEFAULT_TRY_COUNT, 500);
            if (nonModifiedMacAddress != null) {
                if (mNonModifiedMacAddress == null) {
                    mNonModifiedMacAddress = nonModifiedMacAddress;
                    CommonProperties.setProperty(CommonPropertyConstants.NON_MODIFIED_MAC_ADDR_PROPERTY, mNonModifiedMacAddress);
                }
                if (mMacAddress == null) {
                    mMacAddress = compile(nonModifiedMacAddress);
                    CommonProperties.setProperty(CommonPropertyConstants.MAC_ADDR_PROPERTY, mMacAddress);
                }
                CommonProperties.storeProperties(context);
            }
        }
    }

    public static String getMacAddress() {
        return mMacAddress;
    }

    public static String getNonModifiedMacAddress() {
        return mNonModifiedMacAddress;
    }

    private static boolean varifyMacAddress(String macAddress) {
        if (macAddress == null || macAddress.length() != MAC_ADDRESS_LENGTH) {
            return false;
        }
        for (int i = 0; i < macAddress.length(); i++) {
            char currentChar = macAddress.charAt(i);
            if ((i + 1) % 3 == 0) {
                if (currentChar != ':') {
                    return false;
                }
            } else if (currentChar < '0' || currentChar > '9') {
                if (currentChar < 'a') {
                    return false;
                }
                if (currentChar > 'f') {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean varifyNonModifiedMacAddress(String nonModifiedMacAddress) {
        if (nonModifiedMacAddress == null) {
            return false;
        }
        if (nonModifiedMacAddress.length() != MAC_ADDRESS_LENGTH && nonModifiedMacAddress.length() != 12) {
            return false;
        }
        nonModifiedMacAddress = nonModifiedMacAddress.toLowerCase();
        char separator = nonModifiedMacAddress.charAt(2);
        for (int i = 0; i < nonModifiedMacAddress.length(); i++) {
            char currentChar = nonModifiedMacAddress.charAt(i);
            if (((i + 1) % 3 != 0 || currentChar != separator) && ((currentChar < '0' || currentChar > '9') && (currentChar < 'a' || currentChar > 'f'))) {
                return false;
            }
        }
        return true;
    }

    private static String compile(String macAddress) {
        String temp = macAddress.toLowerCase();
        macAddress = i.a;
        int cnt = 0;
        int i = 0;
        while (i < temp.length()) {
            char currentChar = temp.charAt(i);
            if ((currentChar >= '0' && currentChar <= '9') || (currentChar >= 'a' && currentChar <= 'f')) {
                cnt++;
                macAddress = new StringBuilder(String.valueOf(macAddress)).append(String.valueOf(currentChar)).toString();
                if (cnt % 2 == 0 && i < temp.length() - 1) {
                    macAddress = new StringBuilder(String.valueOf(macAddress)).append(":").toString();
                }
            }
            i++;
        }
        return macAddress;
    }

    private static String _getMacAddress(Context context, int tryCount, long tryDelay) {
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService("connectivity");
            String macAddress = wifiManager.getConnectionInfo().getMacAddress();
            if (macAddress != null) {
                return macAddress;
            }
            boolean currentState = isEnableWifi(cm);
            wifiManager.setWifiEnabled(true);
            for (int i = 0; i < tryCount; i++) {
                try {
                    Thread.sleep(tryDelay);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                macAddress = wifiManager.getConnectionInfo().getMacAddress();
                if (macAddress != null) {
                    break;
                }
            }
            wifiManager.setWifiEnabled(currentState);
            return macAddress;
        } catch (Exception e2) {
            return null;
        }
    }

    public static boolean isEnableWifi(ConnectivityManager connectivityManager) {
        return getActiveNetwork(connectivityManager) == ENetworkType.WIFI;
    }

    public static ENetworkType getActiveNetwork(ConnectivityManager connectivityManager) {
        android.net.NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            android.net.NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
            int i = 0;
            while (i < allNetworkInfo.length) {
                switch (allNetworkInfo[i].getType()) {
                    case g.a /*0*/:
                    case o.a /*1*/:
                    case MAC_ADDRESS_SEPARATOR_LENGTH /*5*/:
                    case f.aL /*6*/:
                        if (allNetworkInfo[i].isConnected()) {
                            networkInfo = allNetworkInfo[i];
                            break;
                        }
                        break;
                }
                if (networkInfo == null) {
                    i++;
                } else if (networkInfo == null) {
                    return ENetworkType.NONE;
                }
            }
            if (networkInfo == null) {
                return ENetworkType.NONE;
            }
        }
        if (!networkInfo.isConnected()) {
            return ENetworkType.NONE;
        }
        if (networkInfo.getType() == 1) {
            return ENetworkType.WIFI;
        }
        return ENetworkType.MOBILE;
    }
}
