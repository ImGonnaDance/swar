package com.chartboost.sdk.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class v {
    protected static final Comparator<byte[]> a = new Comparator<byte[]>() {
        public /* synthetic */ int compare(Object obj, Object obj2) {
            return a((byte[]) obj, (byte[]) obj2);
        }

        public int a(byte[] bArr, byte[] bArr2) {
            return bArr.length - bArr2.length;
        }
    };
    private List<byte[]> b = new LinkedList();
    private List<byte[]> c = new ArrayList(64);
    private int d = 0;
    private final int e;

    public v(int i) {
        this.e = i;
    }

    public synchronized byte[] a(int i) {
        byte[] bArr;
        for (int i2 = 0; i2 < this.c.size(); i2++) {
            bArr = (byte[]) this.c.get(i2);
            if (bArr.length >= i) {
                this.d -= bArr.length;
                this.c.remove(i2);
                this.b.remove(bArr);
                break;
            }
        }
        bArr = new byte[i];
        return bArr;
    }

    public synchronized void a(byte[] bArr) {
        if (bArr != null) {
            if (bArr.length <= this.e) {
                this.b.add(bArr);
                int binarySearch = Collections.binarySearch(this.c, bArr, a);
                if (binarySearch < 0) {
                    binarySearch = (-binarySearch) - 1;
                }
                this.c.add(binarySearch, bArr);
                this.d += bArr.length;
                a();
            }
        }
    }

    private synchronized void a() {
        while (this.d > this.e) {
            byte[] bArr = (byte[]) this.b.remove(0);
            this.c.remove(bArr);
            this.d -= bArr.length;
        }
    }
}
