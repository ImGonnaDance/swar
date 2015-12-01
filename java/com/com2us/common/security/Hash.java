package com.com2us.common.security;

import java.security.MessageDigest;

public final class Hash {
    private Hash() {
    }

    public static byte[] generateHash(byte[] data, String algorithm) {
        byte[] ret = null;
        try {
            MessageDigest hash = MessageDigest.getInstance(algorithm);
            hash.update(data);
            return hash.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String generateHashtoHexString(byte[] data, String algorithm) {
        String ret = null;
        byte[] hash = generateHash(data, algorithm);
        if (hash != null) {
            for (int i = 0; i < hash.length; i++) {
                ret = new StringBuilder(String.valueOf(ret)).append(String.format("%02x", new Object[]{Byte.valueOf(hash[i])})).toString();
            }
        }
        return ret;
    }
}
