package com.com2us.module.crypt;

import com.com2us.module.crypt.engines.Salsa20Engine;
import com.com2us.module.crypt.params.KeyParameter;
import com.com2us.module.crypt.params.ParametersWithIV;
import com.com2us.module.manager.ModuleConfig;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Crypt {
    public static byte[] Encrypt(byte[] data, byte[] key) {
        return crypt(true, data, key);
    }

    public static byte[] Decrypt(byte[] data, byte[] key) {
        return crypt(false, data, key);
    }

    private static byte[] crypt(boolean isEncryption, byte[] Packet, byte[] _key) {
        if (isNull(Packet) || isNull(_key)) {
            return null;
        }
        byte[] key = str2byt(padnulls(byt2str(_key), 16));
        if (!(key.length == 16 || key.length == 32)) {
            System.out.println("\n  *** key must be illegal *** ");
        }
        byte[] nonce = str2byt(padnulls(byt2str(_key), 8));
        for (int i = 0; i < 8; i++) {
            key[i] = (byte) 0;
        }
        ParametersWithIV params = new ParametersWithIV(new KeyParameter(key), nonce);
        StreamCipher salsa = new Salsa20Engine();
        salsa.init(isEncryption, params);
        byte[] cryptData = new byte[Packet.length];
        salsa.processBytes(Packet, 0, Packet.length, cryptData, 0);
        return cryptData;
    }

    private byte[] getCRC32Value(byte[] packetData) {
        Checksum crc = new CRC32();
        try {
            BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(packetData));
            byte[] buffer = new byte[ModuleConfig.SOCIAL_MEDIA_MOUDLE];
            while (true) {
                int length = in.read(buffer);
                if (length < 0) {
                    break;
                }
                crc.update(buffer, 0, length);
            }
            in.close();
        } catch (IOException e) {
            System.err.println(e);
        }
        String hexString = String.format("%02X%n", new Object[]{Long.valueOf(crc.getValue())});
        byte[] crc32Value = new byte[(hexString.length() / 2)];
        for (int i = 0; i < crc32Value.length; i++) {
            crc32Value[i] = (byte) Integer.parseInt(hexString.substring(i * 2, (i * 2) + 2), 16);
        }
        return crc32Value;
    }

    private static String nullstring(int len) {
        byte[] nulls = new byte[len];
        Arrays.fill(nulls, (byte) 0);
        return new String(nulls);
    }

    private static String padnulls(String str, int len) {
        return str.concat(nullstring(len)).substring(0, len);
    }

    public static short byt2short(byte[] bytes) {
        return (short) ((((short) bytes[1]) & 255) | ((short) (((((short) bytes[0]) << 8) & 65280) | (short) 0)));
    }

    public static byte[] short2byt(short s) {
        return new byte[]{(byte) ((s >> 8) & 255), (byte) (s & 255)};
    }

    public static String byt2str(byte[] content) {
        return content != null ? new String(content) : null;
    }

    public static byte[] str2byt(String str) {
        return str != null ? str.getBytes() : null;
    }

    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() == 0) {
            return null;
        }
        if (hex.length() % 2 != 0) {
            hex = new StringBuilder(a.d).append(hex).toString();
        }
        int i = 0;
        try {
            byte[] results = new byte[(hex.length() / 2)];
            int k = 0;
            while (k < hex.length()) {
                int k2 = k + 1;
                results[i] = (byte) (Character.digit(hex.charAt(k), 16) << 4);
                k = k2 + 1;
                results[i] = (byte) (results[i] + ((byte) Character.digit(hex.charAt(k2), 16)));
                i++;
            }
            return results;
        } catch (Exception e) {
            return null;
        }
    }

    public static String byteArrayToHex(byte[] ba) {
        if (ba == null || ba.length == 0) {
            return null;
        }
        try {
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < ba.length; i++) {
                if ((ba[i] & 255) < 16) {
                    buffer.append(a.d);
                }
                buffer.append(Long.toString((long) (ba[i] & 255), 16));
            }
            return buffer.toString();
        } catch (Exception e) {
            return null;
        }
    }

    private byte[] cvtKey(byte[] key) {
        int i;
        int keyLen = (0 | ((key[1] << 8) & 65280)) | (key[2] & 255);
        byte[] cvtKey = new byte[keyLen];
        for (i = 0; i < keyLen; i++) {
            cvtKey[i] = key[(key.length - 1) - i];
        }
        for (i = 0; i < cvtKey.length; i++) {
            if (i % 2 != 0) {
                byte temp = cvtKey[i - 1];
                cvtKey[i - 1] = cvtKey[i];
                cvtKey[i] = temp;
            }
        }
        return cvtKey;
    }

    private static boolean isNull(byte[] value) {
        if (value == null || value.length <= 0) {
            return true;
        }
        return false;
    }
}
