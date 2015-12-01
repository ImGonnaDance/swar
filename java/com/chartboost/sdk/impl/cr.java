package com.chartboost.sdk.impl;

import com.com2us.module.newsbanner2.Constants;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import jp.co.cyberz.fox.a.a.i;

public class cr extends cq {
    private static di<byte[]> g = new di<byte[]>(Constants.HD_SCREEN_SIZE) {
        protected /* synthetic */ Object b() {
            return a();
        }

        protected byte[] a() {
            return new byte[16384];
        }
    };
    final byte[] a = new byte[16384];
    final char[] b = new char[16384];
    final List<byte[]> c = new ArrayList();
    final cs d = new cs();
    private final a e = new a();
    private final a f = new a();

    static class a {
        int a;
        int b;

        a() {
            a();
        }

        void a() {
            this.a = -1;
            this.b = 0;
        }

        void a(a aVar) {
            this.a = aVar.a;
            this.b = aVar.b;
        }

        void a(int i) {
            this.a = (i / 16384) - 1;
            this.b = i % 16384;
        }

        int b() {
            return ((this.a + 1) * 16384) + this.b;
        }

        int c() {
            int i = this.b;
            this.b = i + 1;
            return i;
        }

        void b(int i) {
            this.b += i;
            if (this.b > 16384) {
                throw new IllegalArgumentException("something is wrong");
            }
        }

        void d() {
            if (this.b != 16384) {
                throw new IllegalArgumentException("broken");
            }
            this.a++;
            this.b = 0;
        }

        int c(int i) {
            if (i < this.a) {
                return 16384;
            }
            return this.b;
        }

        public String toString() {
            return this.a + i.b + this.b;
        }
    }

    public cr() {
        d();
    }

    public void d() {
        this.e.a();
        this.f.a();
        for (int i = 0; i < this.c.size(); i++) {
            g.b(this.c.get(i));
        }
        this.c.clear();
    }

    public int a() {
        return this.e.b();
    }

    public void a(int i) {
        this.e.a(i);
    }

    public int b() {
        return this.f.b();
    }

    public void write(byte[] b) {
        write(b, 0, b.length);
    }

    public void write(byte[] b, int off, int len) {
        while (len > 0) {
            Object f = f();
            int min = Math.min(f.length - this.e.b, len);
            System.arraycopy(b, off, f, this.e.b, min);
            this.e.b(min);
            len -= min;
            off += min;
            e();
        }
    }

    public void write(int b) {
        f()[this.e.c()] = (byte) (b & 255);
        e();
    }

    void e() {
        if (this.e.b() >= this.f.b()) {
            this.f.a(this.e);
            if (this.f.b >= 16384) {
                this.c.add(g.c());
                this.f.d();
                this.e.a(this.f);
            }
        } else if (this.e.b == 16384) {
            this.e.d();
        }
    }

    byte[] f() {
        return b(this.e.a);
    }

    byte[] b(int i) {
        if (i < 0) {
            return this.a;
        }
        return (byte[]) this.c.get(i);
    }

    public int a(OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new NullPointerException("out is null");
        }
        int i = 0;
        for (int i2 = -1; i2 < this.c.size(); i2++) {
            byte[] b = b(i2);
            int c = this.f.c(i2);
            outputStream.write(b, 0, c);
            i += c;
        }
        return i;
    }
}
