package com.com2us.security.cryptography;

import jp.co.cyberz.fox.a.a.i;

public final class Base64Encoder {
    private static final String BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    private static final char BASE64_PADDING = '=';

    private Base64Encoder() {
    }

    public static String encode(byte[] value) {
        if (value == null || value.length == 0) {
            return i.a;
        }
        int requiredSize = (value.length / 3) * 4;
        if (value.length % 3 > 0) {
            requiredSize += 4;
        }
        char[] output = new char[requiredSize];
        int outputIndex = 0;
        int bufferIndex = 0;
        int[] buffer = new int[3];
        int i = 0;
        while (i < value.length) {
            int bufferIndex2 = bufferIndex + 1;
            buffer[bufferIndex] = value[i] & 255;
            if (bufferIndex2 == 3 || i == value.length - 1) {
                int i2 = outputIndex + 1;
                output[outputIndex] = BASE64_CHARS.charAt(buffer[0] >>> 2);
                outputIndex = i2 + 1;
                output[i2] = BASE64_CHARS.charAt(((buffer[0] & 3) << 4) | (buffer[1] >>> 4));
                i2 = outputIndex + 1;
                output[outputIndex] = bufferIndex2 > 1 ? BASE64_CHARS.charAt(((buffer[1] & 15) << 2) | (buffer[2] >>> 6)) : BASE64_PADDING;
                outputIndex = i2 + 1;
                output[i2] = bufferIndex2 > 2 ? BASE64_CHARS.charAt(buffer[2] & 63) : BASE64_PADDING;
                bufferIndex = 0;
                for (int j = 0; j < buffer.length; j++) {
                    buffer[j] = 0;
                }
            } else {
                bufferIndex = bufferIndex2;
            }
            i++;
        }
        return new String(output);
    }

    public static byte[] decode(String value) throws Exception {
        if (value == null || value.length() == 0) {
            return new byte[0];
        }
        if (value.length() % 4 != 0) {
            throw new Exception("The length of a Base64 string must be a multiple of 4.");
        }
        int i = 0;
        while (i < value.length()) {
            char chr = value.charAt(i);
            if ((i == value.length() - 1 && chr == BASE64_PADDING) || (i == value.length() - 2 && chr == BASE64_PADDING && value.charAt(i + 1) == BASE64_PADDING)) {
                break;
            } else if (BASE64_CHARS.indexOf(chr) != -1) {
                i++;
            } else {
                throw new Exception("An invalid character is found in the Base64 string.");
            }
        }
        int requiredSize = (value.length() / 4) * 3;
        if (value.charAt(value.length() - 1) == BASE64_PADDING) {
            requiredSize--;
        }
        if (value.charAt(value.length() - 2) == BASE64_PADDING) {
            requiredSize--;
        }
        byte[] output = new byte[requiredSize];
        int outputIndex = 0;
        int bufferIndex = 0;
        char[] buffer = new char[4];
        for (i = 0; i < value.length(); i++) {
            int bufferIndex2 = bufferIndex + 1;
            buffer[bufferIndex] = value.charAt(i);
            if (bufferIndex2 == 4) {
                output[outputIndex] = (byte) (BASE64_CHARS.indexOf(buffer[0]) << 2);
                int base64CharIndex = BASE64_CHARS.indexOf(buffer[1]);
                int i2 = outputIndex + 1;
                output[outputIndex] = (byte) (output[outputIndex] | (base64CharIndex >>> 4));
                if (buffer[2] == BASE64_PADDING) {
                    bufferIndex = bufferIndex2;
                    outputIndex = i2;
                    return output;
                }
                output[i2] = (byte) ((base64CharIndex & 15) << 4);
                base64CharIndex = BASE64_CHARS.indexOf(buffer[2]);
                outputIndex = i2 + 1;
                output[i2] = (byte) (output[i2] | (base64CharIndex >>> 2));
                if (buffer[3] == BASE64_PADDING) {
                    bufferIndex = bufferIndex2;
                    return output;
                }
                output[outputIndex] = (byte) ((base64CharIndex & 3) << 6);
                i2 = outputIndex + 1;
                output[outputIndex] = (byte) (output[outputIndex] | BASE64_CHARS.indexOf(buffer[3]));
                bufferIndex = 0;
                outputIndex = i2;
            } else {
                bufferIndex = bufferIndex2;
            }
        }
        return output;
    }
}
