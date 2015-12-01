package com.com2us.common.security;

import com.com2us.common.security.base64.Base64;
import com.com2us.module.manager.ModuleConfig;
import java.util.ArrayList;
import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public final class Crypto {
    private Crypto() {
    }

    public static byte[] encrypt(String algorithm, String keyData, byte[] data) {
        try {
            byte[] privateKey;
            if (algorithm.equals("AES")) {
                privateKey = GetKey(keyData.getBytes("UTF-8"));
            } else {
                privateKey = keyData.getBytes("UTF-8");
            }
            SecretKeySpec key = new SecretKeySpec(privateKey, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(1, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(String algorithm, String keyData, byte[] data) {
        try {
            byte[] privateKey;
            if (algorithm.equals("AES")) {
                privateKey = GetKey(keyData.getBytes("UTF-8"));
            } else {
                privateKey = keyData.getBytes("UTF-8");
            }
            SecretKeySpec key = new SecretKeySpec(privateKey, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(2, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] GetKey(byte[] suggestedKey) {
        int i;
        byte[] kRaw = suggestedKey;
        ArrayList<Byte> kList = new ArrayList();
        for (i = 0; i < ModuleConfig.ACTIVEUSER_MODULE; i += 8) {
            kList.add(Byte.valueOf(kRaw[(i / 8) % kRaw.length]));
        }
        byte[] byteArray = new byte[kList.size()];
        for (i = 0; i < kList.size(); i++) {
            byteArray[i] = ((Byte) kList.get(i)).byteValue();
        }
        return byteArray;
    }

    public static byte[] encodeBase64(byte[] data) {
        return Base64.encode(data, 2);
    }

    public static String encodeBase64toString(byte[] data) {
        return Base64.encodeToString(data, 2);
    }

    public static byte[] decodeBase64(byte[] data) {
        return Base64.decode(data, 2);
    }

    public static byte[] decodeBase64(String data) {
        return Base64.decode(data, 2);
    }

    public static long generateCRC32(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    public static String generateCRC32toHexString(byte[] data) {
        return String.format("%08x", new Object[]{Long.valueOf(generateCRC32(data))});
    }
}
