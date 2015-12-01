package com.com2us.module.amazonpush;

import android.util.Base64;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import jp.co.cyberz.fox.a.a.i;

public class ADMSampleMD5ChecksumCalculator {
    private static final String ALGORITHM = "MD5";
    private static final String ENCODING = "UTF-8";

    private class UTF8CodeUnitStringComparator implements Comparator<String>, Serializable {
        private static final long serialVersionUID = 6169953562234080912L;

        private UTF8CodeUnitStringComparator() {
        }

        public int compare(String str1, String str2) {
            try {
                byte[] bytes1 = str1.getBytes(ADMSampleMD5ChecksumCalculator.ENCODING);
                byte[] bytes2 = str2.getBytes(ADMSampleMD5ChecksumCalculator.ENCODING);
                int loopBounds = Math.min(bytes1.length, bytes2.length);
                for (int i = 0; i < loopBounds; i++) {
                    int ub1 = bytes1[i] & 255;
                    int ub2 = bytes2[i] & 255;
                    if (ub1 != ub2) {
                        return ub1 - ub2;
                    }
                }
                return bytes1.length - bytes2.length;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UTF-8 not supported!", e);
            }
        }
    }

    public String calculateChecksum(Map<String, String> input) {
        return new String(Base64.encode(getMd5Bytes(getSerializedMap(input)), 0));
    }

    private String getSerializedMap(Map<String, String> input) {
        SortedMap<String, String> sortedMap = new TreeMap(new UTF8CodeUnitStringComparator());
        sortedMap.putAll(input);
        StringBuilder builder = new StringBuilder();
        int numElements = 1;
        for (Entry<String, String> entry : sortedMap.entrySet()) {
            builder.append(String.format("%s:%s", new Object[]{entry.getKey(), entry.getValue()}));
            int numElements2 = numElements + 1;
            if (numElements < sortedMap.size()) {
                builder.append(i.b);
            }
            numElements = numElements2;
        }
        return builder.toString();
    }

    private byte[] getMd5Bytes(String input) {
        try {
            byte[] serializedBytes = input.getBytes(ENCODING);
            try {
                MessageDigest digest = MessageDigest.getInstance(ALGORITHM);
                digest.update(serializedBytes);
                return digest.digest();
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("MD5 not supported!", e);
            }
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("UTF-8 not supported!", e2);
        }
    }
}
