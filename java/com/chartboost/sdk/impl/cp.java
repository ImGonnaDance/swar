package com.chartboost.sdk.impl;

import com.com2us.module.manager.ModuleConfig;
import java.io.IOException;
import java.io.OutputStream;

public class cp extends cq {
    private int a;
    private int b;
    private byte[] c = new byte[ModuleConfig.SPIDER_MODULE];

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        b(len);
        System.arraycopy(b, off, this.c, this.a, len);
        this.a += len;
        this.b = Math.max(this.a, this.b);
    }

    public void write(int b) {
        b(1);
        byte[] bArr = this.c;
        int i = this.a;
        this.a = i + 1;
        bArr[i] = (byte) (b & 255);
        this.b = Math.max(this.a, this.b);
    }

    public int a() {
        return this.a;
    }

    public void a(int i) {
        this.a = i;
    }

    public int b() {
        return this.b;
    }

    public int a(OutputStream outputStream) throws IOException {
        outputStream.write(this.c, 0, this.b);
        return this.b;
    }

    void b(int i) {
        int i2 = this.a + i;
        if (i2 >= this.c.length) {
            int length = this.c.length * 2;
            if (length <= i2) {
                length = i2 + ModuleConfig.ACTIVEUSER_MODULE;
            }
            Object obj = new byte[length];
            System.arraycopy(this.c, 0, obj, 0, this.b);
            this.c = obj;
        }
    }
}
