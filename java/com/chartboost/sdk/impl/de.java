package com.chartboost.sdk.impl;

import java.util.List;
import java.util.Map;

public class de<T> {
    private final Map<Class<?>, T> a = dg.c();
    private final Map<Class<?>, T> b = df.a(new a());

    private final class a implements dh<Class<?>, T> {
        final /* synthetic */ de a;

        private a(de deVar) {
            this.a = deVar;
        }

        public T a(Class<?> cls) {
            for (Class cls2 : de.a((Class) cls)) {
                T t = this.a.a.get(cls2);
                if (t != null) {
                    return t;
                }
            }
            return null;
        }
    }

    public static <T> List<Class<?>> a(Class<T> cls) {
        return dd.a(cls);
    }

    public T a(Object obj) {
        return this.b.get(obj);
    }

    public T a(Class<?> cls, T t) {
        try {
            T put = this.a.put(cls, t);
            return put;
        } finally {
            this.b.clear();
        }
    }

    public int a() {
        return this.a.size();
    }
}
