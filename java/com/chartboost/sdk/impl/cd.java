package com.chartboost.sdk.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;

public class cd {
    public static final BigInteger a = BigInteger.valueOf(1024);
    public static final BigInteger b = a.multiply(a);
    public static final BigInteger c = a.multiply(b);
    public static final BigInteger d = a.multiply(c);
    public static final BigInteger e = a.multiply(d);
    public static final BigInteger f = a.multiply(e);
    public static final BigInteger g = BigInteger.valueOf(1024).multiply(BigInteger.valueOf(1152921504606846976L));
    public static final BigInteger h = a.multiply(g);
    public static final File[] i = new File[0];
    private static final Charset j = Charset.forName("UTF-8");

    public static FileInputStream a(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        } else if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        } else if (file.canRead()) {
            return new FileInputStream(file);
        } else {
            throw new IOException("File '" + file + "' cannot be read");
        }
    }

    public static FileOutputStream a(File file, boolean z) throws IOException {
        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (!(parentFile == null || parentFile.mkdirs() || parentFile.isDirectory())) {
                throw new IOException("Directory '" + parentFile + "' could not be created");
            }
        } else if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        } else if (!file.canWrite()) {
            throw new IOException("File '" + file + "' cannot be written to");
        }
        return new FileOutputStream(file, z);
    }

    public static byte[] b(File file) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = a(file);
            byte[] a = ce.a(inputStream, file.length());
            return a;
        } finally {
            ce.a(inputStream);
        }
    }

    public static void a(File file, byte[] bArr) throws IOException {
        a(file, bArr, false);
    }

    public static void a(File file, byte[] bArr, boolean z) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = a(file, z);
            outputStream.write(bArr);
            outputStream.close();
        } finally {
            ce.a(outputStream);
        }
    }
}
