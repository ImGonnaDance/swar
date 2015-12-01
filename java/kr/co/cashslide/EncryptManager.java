package kr.co.cashslide;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptManager {
    private static String PASSKEY = "igaworks1k0i1d4a6e2i5g0ajwyobrks";
    public static String PASSKEY1 = "k1t2m3h4o5w7s8kt9m8h7o6w5s4mhows";

    public static String encryptMsg(String source) {
        SecretKeySpec key = new SecretKeySpec(PASSKEY.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(PASSKEY.substring(0, 16).getBytes());
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(1, key, iv);
            byte[] encrypted = cipher.doFinal(source.getBytes());
            if (encrypted != null) {
                int length = encrypted.length;
            }
            StringBuffer sb = new StringBuffer(encrypted.length * 2);
            for (byte b : encrypted) {
                String hexNumber = new StringBuilder(a.d).append(Integer.toHexString(b & 255)).toString();
                sb.append(hexNumber.substring(hexNumber.length() - 2));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
}
