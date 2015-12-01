package com.com2us.wrapper.common;

import android.content.Context;
import android.net.ConnectivityManager;
import com.com2us.common.network.NetworkInfo;
import com.com2us.common.network.NetworkInfo.ENetworkType;
import com.com2us.common.security.Crypto;
import com.com2us.common.security.Hash;
import jp.co.dimage.android.o;

public class CCommonAPI {
    private static ConnectivityManager a = null;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[ENetworkType.values().length];

        static {
            try {
                a[ENetworkType.WIFI.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[ENetworkType.MOBILE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[ENetworkType.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private CCommonAPI() {
    }

    public static byte[] decodeBase64(String str) {
        return Crypto.decodeBase64(str);
    }

    public static byte[] decodeBase64(byte[] bArr) {
        return Crypto.decodeBase64(bArr);
    }

    public static byte[] decrypt(String str, String str2, byte[] bArr) {
        return Crypto.decrypt(str, str2, bArr);
    }

    public static byte[] encodeBase64(byte[] bArr) {
        return Crypto.encodeBase64(bArr);
    }

    public static String encodeBase64toString(byte[] bArr) {
        return Crypto.encodeBase64toString(bArr);
    }

    public static byte[] encrypt(String str, String str2, byte[] bArr) {
        return Crypto.encrypt(str, str2, bArr);
    }

    public static long generateCRC32(byte[] bArr) {
        return Crypto.generateCRC32(bArr);
    }

    public static String generateCRC32toHexString(byte[] bArr) {
        return Crypto.generateCRC32toHexString(bArr);
    }

    public static byte[] generateHash(byte[] bArr, String str) {
        return Hash.generateHash(bArr, str);
    }

    public static String generateHashtoHexString(byte[] bArr, String str) {
        return Hash.generateHashtoHexString(bArr, str);
    }

    public static int getActiveNetwork() {
        switch (AnonymousClass1.a[NetworkInfo.getActiveNetwork(a).ordinal()]) {
            case o.a /*1*/:
                return 3;
            case o.b /*2*/:
                return 2;
            default:
                return 1;
        }
    }

    public static String getMacAddress() {
        return NetworkInfo.getMacAddress();
    }

    public static String getNonModifiedMacAddress() {
        return NetworkInfo.getNonModifiedMacAddress();
    }

    public static void initialize(Context context) {
        a = (ConnectivityManager) context.getSystemService("connectivity");
        nativeInitialize();
    }

    private static native void nativeInitialize();

    private static native void nativeUninitialize();

    public static void uninitialize() {
        a = null;
        nativeUninitialize();
    }
}
