package com.com2us.module.inapp.lebi;

import com.com2us.module.inapp.DefaultBilling;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.HashSet;
import jp.co.cyberz.fox.a.a.i;

public class Utility {
    private static final SecureRandom RANDOM = new SecureRandom();
    public static final int STRING_CHARGE_NAME = 2;
    public static final int STRING_CHARGE_TEST_NAME = 3;
    public static final int STRING_PD_VALUE = 5;
    public static final int STRING_REQUEST_NAME = 0;
    public static final int STRING_REQUEST_TEST_NAME = 1;
    private static final int[][] STRING_SET = new int[][]{new int[]{65, 104, 114, 57, 67, 104, 109, 51, 108, 89, 48, 84, 108, 119, 106, 80, 66, 103, 88, 72, 67, 104, 97, 85, 67, 120, 98, 52, 66, 54, 117, 85, 121, 55, 53, 86, 66, 119, 48, 75, 68, 119, 88, 76, 108, 57, 106, 80, 66, 103, 88, 115, 122, 120, 102, 56, 122, 120, 110, 57, 109, 73, 52, 74, 109, 78, 109, 61}, new int[]{65, 104, 114, 57, 67, 100, 79, 86, 108, 54, 114, 76, 67, 54, 113, 85, 66, 115, 52, 73, 65, 119, 88, 83, 121, 120, 98, 87, 108, 77, 110, 86, 66, 116, 106, 56, 67, 89, 52, 74, 66, 55, 57, 86, 66, 119, 48, 75, 68, 119, 88, 76, 108, 57, 106, 80, 66, 103, 88, 115, 122, 120, 102, 56, 122, 120, 110, 57, 109, 73, 52, 74, 109, 78, 109, 61}, new int[]{65, 104, 114, 57, 67, 104, 109, 51, 108, 89, 48, 84, 108, 119, 106, 80, 66, 103, 87, 85, 67, 120, 98, 52, 66, 54, 117, 85, 121, 55, 53, 86, 121, 55, 53, 86, 66, 103, 48, 78, 65, 119, 53, 86, 113, 77, 76, 83, 66, DefaultBilling.AUTHORIZE_LICENSE_FAILED, 88, 86, 122, 55, 76, 85, 120, 57, 52, 76, 68, 89, 52, 81, 67, 54, 97, 61}, new int[]{65, 104, 114, 57, 67, 100, 79, 86, 108, 54, 114, 76, 67, 54, 113, 85, 66, 115, 52, 73, 65, 119, 88, 83, 108, 77, 110, 86, 66, 116, 106, 56, 67, 89, 52, 74, 66, 55, 57, 86, 121, 55, 53, 86, 66, 103, 48, 78, 65, 119, 53, 86, 113, 77, 76, 83, 66, DefaultBilling.AUTHORIZE_LICENSE_FAILED, 88, 86, 122, 55, 76, 85, 120, 57, 52, 76, 68, 89, 52, 81, 67, 54, 97, 61}, new int[]{121, 55, 48, 84, 109, 78, 118, 90, 121, 77, 76, 83, 66, 103, 76, 85, 122, 87, 61, 61}, new int[]{67, 119, 88, 77, 122, 77, 88, 75, 109, 74, 97, 88, 109, 115, 79, 72, 70, 71, 61, 61}, new int[]{68, 103, 118, 90, 68, 103, 106, 80, 66, 103, 88, 72, 67, 104, 97, 61}, new int[]{69, 104, 98, 57, 66, 120, 72, 84, 122, 104, 76, 75, 70, 71, 61, 61}};
    public static final int STRING_SYS_ID = 4;
    public static final int STRING_TEST_PD_VALUE = 7;
    public static final int STRING_TEST_SYS_ID = 6;
    private static HashSet<Long> mNonces = new HashSet();

    public static long createNonce() {
        long nonce = RANDOM.nextLong();
        mNonces.add(Long.valueOf(nonce));
        return nonce;
    }

    public static void removeNonce(long nonce) {
        mNonces.remove(Long.valueOf(nonce));
    }

    public static boolean isNonceKnown(long nonce) {
        return mNonces.contains(Long.valueOf(nonce));
    }

    public static String getString(int stringNumber) {
        String ret = i.a;
        byte[] buf = new byte[STRING_SET[stringNumber].length];
        for (int i = STRING_REQUEST_NAME; i < STRING_SET[stringNumber].length; i += STRING_REQUEST_TEST_NAME) {
            byte temp = (byte) STRING_SET[stringNumber][i];
            if (temp >= (byte) 65 && temp <= (byte) 90) {
                temp = (byte) (temp + 32);
            } else if (temp >= (byte) 97 && temp <= (byte) 122) {
                temp = (byte) (temp - 32);
            } else if (temp >= (byte) 48 && temp <= (byte) 57) {
                temp = (byte) (105 - temp);
            }
            buf[i] = temp;
        }
        try {
            return new String(decodeBase64(buf), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ret;
        }
    }

    public static byte[] decodeBase64(byte[] base64Data) {
        byte[] buf = null;
        try {
            Class<?> Base64 = Class.forName("org.apache.commons.codec.binary.Base64");
            Class[] parameterTypes = new Class[STRING_REQUEST_TEST_NAME];
            parameterTypes[STRING_REQUEST_NAME] = byte[].class;
            Method decodeBase64 = Base64.getMethod("decodeBase64", parameterTypes);
            Object[] objArr = new Object[STRING_REQUEST_TEST_NAME];
            objArr[STRING_REQUEST_NAME] = base64Data;
            return (byte[]) decodeBase64.invoke(Base64, objArr);
        } catch (Exception e) {
            e.printStackTrace();
            return buf;
        }
    }
}
