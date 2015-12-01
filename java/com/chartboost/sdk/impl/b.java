package com.chartboost.sdk.impl;

import java.util.Collections;
import java.util.Map;

public interface b {

    public static class a {
        public byte[] a;
        public String b;
        public long c;
        public long d;
        public long e;
        public Map<String, String> f = Collections.emptyMap();

        public boolean a() {
            return this.d < System.currentTimeMillis();
        }

        public boolean b() {
            return this.e < System.currentTimeMillis();
        }
    }

    a a(String str);

    void a();

    void a(String str, a aVar);
}
