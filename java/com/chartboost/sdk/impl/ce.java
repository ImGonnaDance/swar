package com.chartboost.sdk.impl;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class ce {
    public static final char a = File.separatorChar;
    public static final String b;

    static {
        Writer cgVar = new cg(4);
        PrintWriter printWriter = new PrintWriter(cgVar);
        printWriter.println();
        b = cgVar.toString();
        printWriter.close();
    }

    public static void a(InputStream inputStream) {
        a((Closeable) inputStream);
    }

    public static void a(OutputStream outputStream) {
        a((Closeable) outputStream);
    }

    public static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
            }
        }
    }

    public static byte[] b(InputStream inputStream) throws IOException {
        OutputStream cfVar = new cf();
        a(inputStream, cfVar);
        return cfVar.a();
    }

    public static byte[] a(InputStream inputStream, long j) throws IOException {
        if (j <= 2147483647L) {
            return a(inputStream, (int) j);
        }
        throw new IllegalArgumentException("Size cannot be greater than Integer max value: " + j);
    }

    public static byte[] a(InputStream inputStream, int i) throws IOException {
        int i2 = 0;
        if (i < 0) {
            throw new IllegalArgumentException("Size must be equal or greater than zero: " + i);
        } else if (i == 0) {
            return new byte[0];
        } else {
            byte[] bArr = new byte[i];
            while (i2 < i) {
                int read = inputStream.read(bArr, i2, i - i2);
                if (read == -1) {
                    break;
                }
                i2 += read;
            }
            if (i2 == i) {
                return bArr;
            }
            throw new IOException("Unexpected readed size. current: " + i2 + ", excepted: " + i);
        }
    }

    public static int a(InputStream inputStream, OutputStream outputStream) throws IOException {
        long b = b(inputStream, outputStream);
        if (b > 2147483647L) {
            return -1;
        }
        return (int) b;
    }

    public static long b(InputStream inputStream, OutputStream outputStream) throws IOException {
        return a(inputStream, outputStream, new byte[4096]);
    }

    public static long a(InputStream inputStream, OutputStream outputStream, byte[] bArr) throws IOException {
        long j = 0;
        while (true) {
            int read = inputStream.read(bArr);
            if (-1 == read) {
                return j;
            }
            outputStream.write(bArr, 0, read);
            j += (long) read;
        }
    }
}
