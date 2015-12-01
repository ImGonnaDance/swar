package com.com2us.security.cryptography;

import java.security.MessageDigest;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jp.co.cyberz.fox.a.a.i;

public class StringEncrypter {
    private IvParameterSpec initalVector;
    private SecretKeySpec key;
    private Cipher rijndael;

    public StringEncrypter(String key, String initialVector) throws Exception {
        if (key == null || key.equals(i.a)) {
            throw new Exception("The key can not be null or an empty string.");
        } else if (initialVector == null || initialVector.equals(i.a)) {
            throw new Exception("The initial vector can not be null or an empty string.");
        } else {
            this.rijndael = Cipher.getInstance("AES/CBC/PKCS5Padding");
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            this.key = new SecretKeySpec(md5.digest(key.getBytes("UTF8")), "AES");
            this.initalVector = new IvParameterSpec(md5.digest(initialVector.getBytes("UTF8")));
        }
    }

    public String encrypt(String value) throws Exception {
        if (value == null) {
            value = i.a;
        }
        this.rijndael.init(1, this.key, this.initalVector);
        return Base64Encoder.encode(this.rijndael.doFinal(value.getBytes("UTF8")));
    }

    public String decrypt(String value) throws Exception {
        if (value == null || value.equals(i.a)) {
            throw new Exception("The cipher string can not be null or an empty string.");
        }
        this.rijndael.init(2, this.key, this.initalVector);
        return new String(this.rijndael.doFinal(Base64Encoder.decode(value)), "UTF8");
    }
}
