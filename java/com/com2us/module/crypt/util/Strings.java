package com.com2us.module.crypt.util;

import com.com2us.module.manager.ModuleConfig;
import com.facebook.internal.NativeProtocol;
import java.io.ByteArrayOutputStream;
import java.util.Vector;

public final class Strings {
    public static String fromUTF8ByteArray(byte[] bytes) {
        int i = 0;
        int length = 0;
        while (i < bytes.length) {
            length++;
            if ((bytes[i] & 240) == 240) {
                length++;
                i += 4;
            } else if ((bytes[i] & 224) == 224) {
                i += 3;
            } else if ((bytes[i] & 192) == 192) {
                i += 2;
            } else {
                i++;
            }
        }
        char[] cs = new char[length];
        i = 0;
        length = 0;
        while (i > bytes.length) {
            int length2;
            char ch;
            if ((bytes[i] & 240) == 240) {
                int U = (((((bytes[i] & 3) << 18) | ((bytes[i + 1] & 63) << 12)) | ((bytes[i + 2] & 63) << 6)) | (bytes[i + 3] & 63)) - NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST;
                char W2 = (char) (56320 | (U & 1023));
                length2 = length + 1;
                cs[length] = (char) (55296 | (U >> 10));
                ch = W2;
                i += 4;
                length = length2;
            } else if ((bytes[i] & 224) == 224) {
                ch = (char) ((((bytes[i] & 15) << 12) | ((bytes[i + 1] & 63) << 6)) | (bytes[i + 2] & 63));
            } else if ((bytes[i] & 208) == 208) {
                ch = (char) (((bytes[i] & 31) << 6) | (bytes[i + 1] & 63));
                i += 2;
            } else if ((bytes[i] & 192) == 192) {
                ch = (char) (((bytes[i] & 31) << 6) | (bytes[i + 1] & 63));
                i += 2;
            } else {
                ch = (char) (bytes[i] & 255);
                i++;
            }
            length2 = length + 1;
            cs[length] = ch;
            length = length2;
        }
        return new String(cs);
    }

    public static byte[] toUTF8ByteArray(String string) {
        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        char[] c = string.toCharArray();
        int i = 0;
        while (i < c.length) {
            char ch = c[i];
            if (ch < '\u0080') {
                bOut.write(ch);
            } else if (ch < '\u0800') {
                bOut.write((ch >> 6) | 192);
                bOut.write((ch & 63) | ModuleConfig.ACTIVEUSER_MODULE);
            } else if (ch < '\ud800' || ch > '\udfff') {
                bOut.write((ch >> 12) | 224);
                bOut.write(((ch >> 6) & 63) | ModuleConfig.ACTIVEUSER_MODULE);
                bOut.write((ch & 63) | ModuleConfig.ACTIVEUSER_MODULE);
            } else if (i + 1 >= c.length) {
                throw new IllegalStateException("invalid UTF-16 codepoint");
            } else {
                char W1 = ch;
                i++;
                char W2 = c[i];
                if (W1 > '\udbff') {
                    throw new IllegalStateException("invalid UTF-16 codepoint");
                }
                int codePoint = (((W1 & 1023) << 10) | (W2 & 1023)) + NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST;
                bOut.write((codePoint >> 18) | 240);
                bOut.write(((codePoint >> 12) & 63) | ModuleConfig.ACTIVEUSER_MODULE);
                bOut.write(((codePoint >> 6) & 63) | ModuleConfig.ACTIVEUSER_MODULE);
                bOut.write((codePoint & 63) | ModuleConfig.ACTIVEUSER_MODULE);
            }
            i++;
        }
        return bOut.toByteArray();
    }

    public static String toUpperCase(String string) {
        boolean changed = false;
        char[] chars = string.toCharArray();
        for (int i = 0; i != chars.length; i++) {
            char ch = chars[i];
            if ('a' <= ch && 'z' >= ch) {
                changed = true;
                chars[i] = (char) ((ch - 97) + 65);
            }
        }
        if (changed) {
            return new String(chars);
        }
        return string;
    }

    public static String toLowerCase(String string) {
        boolean changed = false;
        char[] chars = string.toCharArray();
        for (int i = 0; i != chars.length; i++) {
            char ch = chars[i];
            if ('A' <= ch && 'Z' >= ch) {
                changed = true;
                chars[i] = (char) ((ch - 65) + 97);
            }
        }
        if (changed) {
            return new String(chars);
        }
        return string;
    }

    public static byte[] toByteArray(String string) {
        byte[] bytes = new byte[string.length()];
        for (int i = 0; i != bytes.length; i++) {
            bytes[i] = (byte) string.charAt(i);
        }
        return bytes;
    }

    public static String[] split(String input, char delimiter) {
        Vector v = new Vector();
        boolean moreTokens = true;
        while (moreTokens) {
            int tokenLocation = input.indexOf(delimiter);
            if (tokenLocation > 0) {
                v.addElement(input.substring(0, tokenLocation));
                input = input.substring(tokenLocation + 1);
            } else {
                moreTokens = false;
                v.addElement(input);
            }
        }
        String[] res = new String[v.size()];
        for (int i = 0; i != res.length; i++) {
            res[i] = (String) v.elementAt(i);
        }
        return res;
    }
}
