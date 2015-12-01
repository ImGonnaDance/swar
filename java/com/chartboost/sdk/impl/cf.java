package com.chartboost.sdk.impl;

import com.com2us.module.manager.ModuleConfig;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class cf extends OutputStream {
    private static final byte[] a = new byte[0];
    private final List<byte[]> b;
    private int c;
    private int d;
    private byte[] e;
    private int f;

    public cf() {
        this(ModuleConfig.MERCURY_MODULE);
    }

    public cf(int i) {
        this.b = new ArrayList();
        if (i < 0) {
            throw new IllegalArgumentException("Negative initial size: " + i);
        }
        synchronized (this) {
            a(i);
        }
    }

    private void a(int i) {
        if (this.c < this.b.size() - 1) {
            this.d += this.e.length;
            this.c++;
            this.e = (byte[]) this.b.get(this.c);
            return;
        }
        if (this.e == null) {
            this.d = 0;
        } else {
            i = Math.max(this.e.length << 1, i - this.d);
            this.d += this.e.length;
        }
        this.c++;
        this.e = new byte[i];
        this.b.add(this.e);
    }

    public void write(byte[] b, int off, int len) {
        if (off < 0 || off > b.length || len < 0 || off + len > b.length || off + len < 0) {
            throw new IndexOutOfBoundsException();
        } else if (len != 0) {
            synchronized (this) {
                int i = this.f + len;
                int i2 = this.f - this.d;
                int i3 = len;
                while (i3 > 0) {
                    int min = Math.min(i3, this.e.length - i2);
                    System.arraycopy(b, (off + len) - i3, this.e, i2, min);
                    i3 -= min;
                    if (i3 > 0) {
                        a(i);
                        i2 = 0;
                    }
                }
                this.f = i;
            }
        }
    }

    public synchronized void write(int b) {
        int i = this.f - this.d;
        if (i == this.e.length) {
            a(this.f + 1);
            i = 0;
        }
        this.e[i] = (byte) b;
        this.f++;
    }

    public void close() throws IOException {
    }

    public synchronized byte[] a() {
        byte[] bArr;
        int i = this.f;
        if (i == 0) {
            bArr = a;
        } else {
            Object obj = new byte[i];
            int i2 = i;
            i = 0;
            for (byte[] bArr2 : this.b) {
                int min = Math.min(bArr2.length, i2);
                System.arraycopy(bArr2, 0, obj, i, min);
                int i3 = i + min;
                i = i2 - min;
                if (i == 0) {
                    break;
                }
                i2 = i;
                i = i3;
            }
            Object obj2 = obj;
        }
        return bArr2;
    }

    public String toString() {
        return new String(a());
    }
}
