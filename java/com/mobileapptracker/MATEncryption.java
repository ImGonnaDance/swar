package com.mobileapptracker;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jp.co.cyberz.fox.a.a.i;

public class MATEncryption {
    private IvParameterSpec a;
    private SecretKeySpec b;
    private Cipher c;

    public MATEncryption(String SecretKey, String iv) {
        this.a = new IvParameterSpec(iv.getBytes());
        this.b = new SecretKeySpec(SecretKey.getBytes(), "AES");
        try {
            this.c = Cipher.getInstance("AES/CBC/NoPadding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
        }
    }

    private static String a(String str) {
        int length = 16 - (str.length() % 16);
        for (int i = 0; i < length; i++) {
            str = str + ' ';
        }
        return str;
    }

    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        }
        int length = data.length;
        String str = i.a;
        for (int i = 0; i < length; i++) {
            str = (data[i] & 255) < 16 ? str + a.d + Integer.toHexString(data[i] & 255) : str + Integer.toHexString(data[i] & 255);
        }
        return str;
    }

    public static byte[] hexToBytes(String str) {
        byte[] bArr = null;
        if (str != null && str.length() >= 2) {
            int length = str.length() / 2;
            bArr = new byte[length];
            for (int i = 0; i < length; i++) {
                bArr[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
            }
        }
        return bArr;
    }

    public static String md5(String s) {
        if (s == null) {
            return i.a;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("MD5");
            instance.update(s.getBytes());
            return bytesToHex(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return i.a;
        }
    }

    public static String sha1(String s) {
        if (s == null) {
            return i.a;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-1");
            instance.update(s.getBytes());
            return bytesToHex(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return i.a;
        }
    }

    public static String sha256(String s) {
        if (s == null) {
            return i.a;
        }
        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(s.getBytes());
            return bytesToHex(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return i.a;
        }
    }

    public byte[] decrypt(String code) {
        if (code == null || code.length() == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.c.init(2, this.b, this.a);
            return this.c.doFinal(hexToBytes(code));
        } catch (Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
    }

    public byte[] encrypt(String text) {
        if (text == null || text.length() == 0) {
            throw new Exception("Empty string");
        }
        try {
            this.c.init(1, this.b, this.a);
            return this.c.doFinal(a(text).getBytes());
        } catch (Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }
    }
}
