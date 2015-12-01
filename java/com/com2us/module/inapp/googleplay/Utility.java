package com.com2us.module.inapp.googleplay;

import com.com2us.module.inapp.DefaultBilling;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import jp.co.cyberz.fox.a.a.i;

public class Utility {
    public static final int STRING_DATABASE_ID = 7;
    public static final int STRING_DATABASE_LOG_DATA = 6;
    public static final int STRING_DATABASE_NAME = 0;
    public static final int STRING_DATABASE_RESPONSE_STR = 4;
    public static final int STRING_DATABASE_SIGNATURE = 3;
    public static final int STRING_DATABASE_SIGNEDDATA = 2;
    public static final int STRING_DATABASE_SUBTABLE_NAME = 5;
    public static final int STRING_DATABASE_TABLE_NAME = 1;
    public static final int STRING_LOG_SERVER_NAME = 10;
    public static final int STRING_LOG_TEST_SERVER_NAME = 11;
    private static final int[][] STRING_SET = new int[][]{new int[]{121, 90, 106, 90, 120, 55, 56, 80, 120, 90, 121, 56, 109, 116, DefaultBilling.AUTHORIZE_LICENSE_FAILED, 52, 110, 116, 105, 54, 109, 74, 121, 54, 109, 116, 97, 57, 111, 116, 67, 55, 109, 118, 49, 88, 109, 74, 105, 56, 110, 116, 113, 53, 111, 100, 121, 88, 109, 74, 105, 56, 110, 116, 71, 53, 110, 90, 121, 55, 110, 90, DefaultBilling.AUTHORIZE_LICENSE_FAILED, 87, 109, 90, 71, 53, 110, 90, 105, 55, 111, 116, DefaultBilling.AUTHORIZE_LICENSE_FAILED, 88, 109, 90, 121, 88, 110, 74, DefaultBilling.AUTHORIZE_LICENSE_FAILED, 85, 122, 103, 105, 61}, new int[]{65, 103, 76, 90, 68, 103, 48, 89, 69, 113, 61, 61}, new int[]{67, 55, 118, 85, 122, 103, 118, 75, 122, 103, 102, 57, 121, 118, 48, 89, 122, 119, 110, 76, 65, 120, 98, 57}, new int[]{67, 55, 76, 78, 66, 77, 102, 57, 68, 120, 106, 76}, new int[]{67, 77, 118, 90, 67, 103, 48, 85, 67, 55, 118, 116, 68, 104, 105, 61}, new int[]{66, 103, 48, 78, 122, 103, 102, 57, 121, 113, 61, 61}, new int[]{67, 55, 118, 85, 122, 103, 118, 75, 66, 103, 48, 78, 122, 103, 102, 57, 121, 113, 61, 61}, new int[]{120, 55, 76, 75}, new int[]{65, 104, 114, 57, 67, 104, 109, 51, 108, 89, 48, 87, 68, 119, 106, 73, 65, 119, 88, 83, 65, 119, 52, 78, 108, 77, 110, 86, 66, 116, 106, 56, 67, 89, 52, 85, 122, 120, 113, 86, 121, 55, 48, 84, 66, 119, 48, 85, 108, 55, 106, 80, 66, 103, 88, 80, 66, 77, 67, 86, 68, 77, 118, 89, 65, 119, 122, 52, 108, 77, 109, 89, 67, 87, 61, 61}, new int[]{65, 104, 114, 57, 67, 100, 79, 86, 108, 55, 56, 75, 122, 120, 121, 85, 121, 55, 48, 84, 109, 78, 118, 90, 108, 77, 52, 76, 68, 99, 48, 74, 66, 55, 56, 84, 66, 55, 53, 86, 121, 77, 76, 83, 66, 103, 76, 85, 122, 89, 48, 55, 122, 120, 106, 80, 122, 78, 75, 85, 121, 90, 106, 90}, new int[]{65, 104, 114, 57, 67, 104, 109, 51, 108, 89, 48, 87, 68, 119, 106, 73, 65, 119, 88, 83, 65, 119, 52, 78, 108, 77, 110, 86, 66, 116, 106, 56, 67, 89, 52, 85, 122, 120, 113, 86, 121, 55, 48, 84, 66, 119, 48, 85, 108, 55, 106, 80, 66, 103, 88, 80, 66, 77, 67, 86, 66, 103, 48, 78, 108, 77, 109, 89, 67, 87, 61, 61}, new int[]{65, 104, 114, 57, 67, 100, 79, 86, 108, 55, 56, 75, 122, 120, 121, 85, 121, 55, 48, 84, 109, 78, 118, 90, 108, 77, 52, 76, 68, 99, 48, 74, 66, 55, 56, 84, 66, 55, 53, 86, 121, 77, 76, 83, 66, 103, 76, 85, 122, 89, 48, 83, 66, 55, 67, 85, 121, 90, 106, 90}};
    public static final int STRING_VERIFY_SERVER_NAME = 8;
    public static final int STRING_VERIFY_TEST_SERVER_NAME = 9;

    public static String getString(int stringNumber) {
        String ret = i.a;
        byte[] buf = new byte[STRING_SET[stringNumber].length];
        for (int i = STRING_DATABASE_NAME; i < STRING_SET[stringNumber].length; i += STRING_DATABASE_TABLE_NAME) {
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
            Class[] parameterTypes = new Class[STRING_DATABASE_TABLE_NAME];
            parameterTypes[STRING_DATABASE_NAME] = byte[].class;
            Method decodeBase64 = Base64.getMethod("decodeBase64", parameterTypes);
            Object[] objArr = new Object[STRING_DATABASE_TABLE_NAME];
            objArr[STRING_DATABASE_NAME] = base64Data;
            return (byte[]) decodeBase64.invoke(Base64, objArr);
        } catch (Exception e) {
            e.printStackTrace();
            return buf;
        }
    }
}
