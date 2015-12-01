package com.chartboost.sdk.impl;

public class n<T> {
    public final T a;
    public final com.chartboost.sdk.impl.b.a b;
    public final s c;
    public boolean d;

    public interface a {
        void a(s sVar);
    }

    public interface b<T> {
        void a(T t);
    }

    public static <T> n<T> a(T t, com.chartboost.sdk.impl.b.a aVar) {
        return new n(t, aVar);
    }

    public static <T> n<T> a(s sVar) {
        return new n(sVar);
    }

    public boolean a() {
        return this.c == null;
    }

    private n(T t, com.chartboost.sdk.impl.b.a aVar) {
        this.d = false;
        this.a = t;
        this.b = aVar;
        this.c = null;
    }

    private n(s sVar) {
        this.d = false;
        this.a = null;
        this.b = null;
        this.c = sVar;
    }
}
