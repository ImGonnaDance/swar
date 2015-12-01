package com.chartboost.sdk.impl;

import java.io.Serializable;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class cz implements Serializable, Comparable<cz> {
    static final Logger a = Logger.getLogger("org.bson.ObjectId");
    private static AtomicInteger f = new AtomicInteger(new Random().nextInt());
    private static final int g;
    final int b;
    final int c;
    final int d;
    boolean e;

    public /* synthetic */ int compareTo(Object x0) {
        return a((cz) x0);
    }

    static {
        int hashCode;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                stringBuilder.append(((NetworkInterface) networkInterfaces.nextElement()).toString());
            }
            hashCode = stringBuilder.toString().hashCode() << 16;
        } catch (Throwable th) {
            try {
                a.log(Level.WARNING, th.getMessage(), th);
                hashCode = new Random().nextInt() << 16;
            } catch (Throwable th2) {
                throw new RuntimeException(th2);
            }
        }
        a.fine("machine piece post: " + Integer.toHexString(hashCode));
        int nextInt = new Random().nextInt();
        ClassLoader classLoader = cz.class.getClassLoader();
        int identityHashCode = classLoader != null ? System.identityHashCode(classLoader) : 0;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(Integer.toHexString(nextInt));
        stringBuilder2.append(Integer.toHexString(identityHashCode));
        identityHashCode = stringBuilder2.toString().hashCode() & 65535;
        a.fine("process piece: " + Integer.toHexString(identityHashCode));
        g = identityHashCode | hashCode;
        a.fine("machine : " + Integer.toHexString(g));
    }

    public static boolean a(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length != 24) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if ((charAt < '0' || charAt > '9') && (charAt < 'a' || charAt > 'f')) {
                if (charAt < 'A' || charAt > 'F') {
                    return false;
                }
            }
        }
        return true;
    }

    public static cz a(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof cz) {
            return (cz) obj;
        }
        if (obj instanceof String) {
            String obj2 = obj.toString();
            if (a(obj2)) {
                return new cz(obj2);
            }
        }
        return null;
    }

    public cz(String str) {
        this(str, false);
    }

    public cz(String str, boolean z) {
        if (a(str)) {
            if (z) {
                str = b(str);
            }
            byte[] bArr = new byte[12];
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = (byte) Integer.parseInt(str.substring(i * 2, (i * 2) + 2), 16);
            }
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            this.b = wrap.getInt();
            this.c = wrap.getInt();
            this.d = wrap.getInt();
            this.e = false;
            return;
        }
        throw new IllegalArgumentException("invalid ObjectId [" + str + "]");
    }

    public cz(int i, int i2, int i3) {
        this.b = i;
        this.c = i2;
        this.d = i3;
        this.e = false;
    }

    public cz() {
        this.b = (int) (System.currentTimeMillis() / 1000);
        this.c = g;
        this.d = f.getAndIncrement();
        this.e = true;
    }

    public int hashCode() {
        return (this.b + (this.c * 111)) + (this.d * 17);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        cz a = a(o);
        if (a == null) {
            return false;
        }
        if (this.b == a.b && this.c == a.c && this.d == a.d) {
            return true;
        }
        return false;
    }

    public String a() {
        byte[] b = b();
        StringBuilder stringBuilder = new StringBuilder(24);
        for (byte b2 : b) {
            String toHexString = Integer.toHexString(b2 & 255);
            if (toHexString.length() == 1) {
                stringBuilder.append(a.d);
            }
            stringBuilder.append(toHexString);
        }
        return stringBuilder.toString();
    }

    public byte[] b() {
        byte[] bArr = new byte[12];
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.putInt(this.b);
        wrap.putInt(this.c);
        wrap.putInt(this.d);
        return bArr;
    }

    static String a(String str, int i) {
        return str.substring(i * 2, (i * 2) + 2);
    }

    public static String b(String str) {
        if (a(str)) {
            int i;
            StringBuilder stringBuilder = new StringBuilder(24);
            for (i = 7; i >= 0; i--) {
                stringBuilder.append(a(str, i));
            }
            for (i = 11; i >= 8; i--) {
                stringBuilder.append(a(str, i));
            }
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("invalid object id: " + str);
    }

    public String toString() {
        return a();
    }

    int a(int i, int i2) {
        long j = (((long) i) & 4294967295L) - (((long) i2) & 4294967295L);
        if (j < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        if (j > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) j;
    }

    public int a(cz czVar) {
        if (czVar == null) {
            return -1;
        }
        int a = a(this.b, czVar.b);
        if (a != 0) {
            return a;
        }
        a = a(this.c, czVar.c);
        return a == 0 ? a(this.d, czVar.d) : a;
    }

    public int c() {
        return this.b;
    }

    public int d() {
        return this.c;
    }

    public int e() {
        return this.d;
    }
}
