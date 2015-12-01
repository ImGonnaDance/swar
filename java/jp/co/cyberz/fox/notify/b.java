package jp.co.cyberz.fox.notify;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

final class b {
    private static final String[] a = new String[]{"AES"};

    enum a {
        XUNIQ(0, "a582a79754ac4f5fb75bc18271e4712c"),
        SERVER_URL(1, "910c256430ae453496018633be5e1629");
        
        private int c;
        private byte[] d;

        private a(int i, String str) {
            this.c = i;
            this.d = b.c(str);
        }

        public static a a(int i) {
            Object obj = e;
            int length = obj.length;
            Object obj2 = new a[length];
            System.arraycopy(obj, 0, obj2, 0, length);
            for (a aVar : obj2) {
                if (aVar.c == i) {
                    return aVar;
                }
            }
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Unknown key index: " + i);
            return null;
        }

        private static a a(String str) {
            return (a) Enum.valueOf(a.class, str);
        }

        private static a[] c() {
            Object obj = e;
            int length = obj.length;
            Object obj2 = new a[length];
            System.arraycopy(obj, 0, obj2, 0, length);
            return obj2;
        }

        final byte[] a() {
            return this.d;
        }

        final int b() {
            return this.c;
        }
    }

    b() {
    }

    private static String a(String str, a aVar) {
        String str2 = a[0];
        Cipher instance = Cipher.getInstance(str2);
        instance.init(1, new SecretKeySpec(aVar.a(), str2));
        return Integer.toString(0) + Integer.toString(aVar.b()) + a(instance.doFinal(str.getBytes()));
    }

    private static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            if (toHexString.length() == 1) {
                stringBuffer.append(a.d);
            }
            stringBuffer.append(toHexString);
        }
        return stringBuffer.toString();
    }

    private static void a(int i, int i2) {
        if (i < 0 || i >= a.length) {
            throw new NoSuchAlgorithmException("Invalid algorithm index.");
        }
        try {
            if (a.a(i2) == null) {
                throw new InvalidKeyException("Invalid key index.");
            }
        } catch (Exception e) {
            throw new InvalidKeyException("Invalid key index.");
        }
    }

    private static String b(String str) {
        try {
            int parseInt = Integer.parseInt(str.substring(0, 1));
            try {
                int parseInt2 = Integer.parseInt(str.substring(1, 2));
                if (parseInt < 0 || parseInt >= a.length) {
                    throw new NoSuchAlgorithmException("Invalid algorithm index.");
                }
                try {
                    if (a.a(parseInt2) == null) {
                        throw new InvalidKeyException("Invalid key index.");
                    }
                    String str2 = a[parseInt];
                    a a = a.a(parseInt2);
                    String substring = str.substring(2);
                    Cipher instance = Cipher.getInstance(str2);
                    instance.init(2, new SecretKeySpec(a.a(), str2));
                    return new String(instance.doFinal(c(substring)));
                } catch (Exception e) {
                    throw new InvalidKeyException("Invalid key index.");
                }
            } catch (Exception e2) {
                throw new InvalidKeyException(e2.getMessage());
            }
        } catch (Exception e22) {
            throw new NoSuchAlgorithmException(e22.getMessage());
        }
    }

    private static byte[] c(String str) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        for (int i = 0; i < str.length(); i += 2) {
            byteArrayOutputStream.write(Integer.parseInt(str.substring(i, i + 2), 16));
        }
        return byteArrayOutputStream.toByteArray();
    }
}
