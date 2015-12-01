package com.chartboost.sdk.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public abstract class cq extends OutputStream {
    public abstract int a();

    public abstract int a(OutputStream outputStream) throws IOException;

    public abstract void a(int i);

    public abstract int b();

    public abstract void write(int i);

    public abstract void write(byte[] bArr);

    public byte[] c() {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream(b());
            a(byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        } catch (Throwable e) {
            throw new RuntimeException("should be impossible", e);
        }
    }

    public void c(int i) {
        write(i >> 0);
        write(i >> 8);
        write(i >> 16);
        write(i >> 24);
    }

    public void d(int i) {
        write(i >> 24);
        write(i >> 16);
        write(i >> 8);
        write(i);
    }

    public void a(int i, int i2) {
        int a = a();
        a(i);
        c(i2);
        a(a);
    }

    public void a(long j) {
        write((byte) ((int) ((j >> null) & 255)));
        write((byte) ((int) ((j >> 8) & 255)));
        write((byte) ((int) ((j >> 16) & 255)));
        write((byte) ((int) ((j >> 24) & 255)));
        write((byte) ((int) ((j >> 32) & 255)));
        write((byte) ((int) ((j >> 40) & 255)));
        write((byte) ((int) ((j >> 48) & 255)));
        write((byte) ((int) ((j >> 56) & 255)));
    }

    public void a(double d) {
        a(Double.doubleToRawLongBits(d));
    }

    public String toString() {
        return getClass().getName() + " size: " + b() + " pos: " + a();
    }
}
