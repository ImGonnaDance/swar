package com.com2us.peppermint.util;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jp.co.cyberz.fox.a.a.i;

public class PeppermintEncryption {
    private static final String a = "AES";
    private static AlgorithmParameterSpec f3a = new IvParameterSpec(new byte[16]);
    private static final String b = "PKCS7Padding";
    private static final String c = "CBC";
    private static final String d = "AES/CBC/PKCS7Padding";

    private Key a(String str) throws Exception {
        return SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(str.getBytes()));
    }

    private Key a(String str, boolean z) throws Exception {
        return z ? b(str) : a(str);
    }

    private Key b(String str) throws Exception {
        return SecretKeyFactory.getInstance("DESede").generateSecret(new DESedeKeySpec(str.getBytes()));
    }

    public static SecretKeySpec creaetSecretKeyAES(byte[] bArr) throws UnsupportedEncodingException {
        return new SecretKeySpec(bArr, a);
    }

    public static byte[] decodeBase64(byte[] bArr) {
        try {
            Class cls = Class.forName("org.apache.commons.codec.binary.Base64");
            Method method = cls.getMethod("decodeBase64", new Class[]{byte[].class});
            if (method == null) {
                throw new NullPointerException("decodeBase64 decodeBase64 is null");
            }
            return (byte[]) method.invoke(cls, new Object[]{bArr});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decryptAES(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance(d);
        instance.init(2, creaetSecretKeyAES(bArr), f3a);
        return instance.doFinal(bArr2);
    }

    public static byte[] encodeBase64(byte[] bArr) {
        try {
            Class cls = Class.forName("org.apache.commons.codec.binary.Base64");
            Method method = cls.getMethod("encodeBase64", new Class[]{byte[].class});
            if (method == null) {
                throw new NullPointerException("encodeBase64 encodeBase64 is null");
            }
            return (byte[]) method.invoke(cls, new Object[]{bArr});
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] encryptAES(byte[] bArr, byte[] bArr2) throws Exception {
        Cipher instance = Cipher.getInstance(d);
        instance.init(1, creaetSecretKeyAES(bArr), f3a);
        return instance.doFinal(bArr2);
    }

    public static String getMD5Hash(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(bArr);
            if (digest == null) {
                throw new NullPointerException("getMD5Hash digest is null");
            }
            for (int i = 0; i < digest.length; i++) {
                stringBuffer.append(Integer.toString((digest[i] & 240) >> 4, 16));
                stringBuffer.append(Integer.toString(digest[i] & 15, 16));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NullPointerException e2) {
            return i.a;
        }
    }

    public String decryptDES(String str, String str2, boolean z) throws Exception {
        if (str2 == null || str2.length() == 0) {
            return i.a;
        }
        Cipher instance = Cipher.getInstance(z ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding");
        instance.init(2, a(str, z));
        return new String(instance.doFinal(decodeBase64(str2.getBytes("UTF8"))), "UTF8");
    }

    public String encryptDES(String str, String str2, boolean z) throws Exception {
        if (str2 == null || str2.length() == 0) {
            return i.a;
        }
        Cipher instance = Cipher.getInstance(z ? "DESede/ECB/PKCS5Padding" : "DES/ECB/PKCS5Padding");
        instance.init(1, a(str, z));
        return new String(encodeBase64(instance.doFinal(str2.getBytes("UTF8"))), "UTF8");
    }
}
