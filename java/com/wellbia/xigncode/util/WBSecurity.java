package com.wellbia.xigncode.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class WBSecurity {
    public static String convertToHex(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            int i2 = (bArr[i] >>> 4) & 15;
            int i3 = 0;
            while (true) {
                if (i2 < 0 || i2 > 9) {
                    stringBuffer.append((char) ((i2 - 10) + 97));
                } else {
                    stringBuffer.append((char) (i2 + 48));
                }
                int i4 = bArr[i] & 15;
                i2 = i3 + 1;
                if (i3 >= 1) {
                    break;
                }
                i3 = i2;
                i2 = i4;
            }
        }
        return stringBuffer.toString();
    }

    public static String md5hash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest instance = MessageDigest.getInstance("MD5");
        byte[] bArr = new byte[32];
        instance.update(str.getBytes("iso-8859-1"), 0, str.length());
        return convertToHex(instance.digest());
    }
}
