package com.chartboost.sdk.Libraries;

import jp.co.dimage.android.o;

public enum f {
    PORTRAIT,
    LANDSCAPE,
    PORTRAIT_REVERSE,
    LANDSCAPE_REVERSE;
    
    public static final f e = null;
    public static final f f = null;
    public static final f g = null;
    public static final f h = null;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = null;

        static {
            a = new int[f.values().length];
            try {
                a[f.LANDSCAPE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[f.PORTRAIT_REVERSE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[f.LANDSCAPE_REVERSE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[f.PORTRAIT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    static {
        e = PORTRAIT_REVERSE;
        f = PORTRAIT;
        g = LANDSCAPE;
        h = LANDSCAPE_REVERSE;
    }

    public f a() {
        switch (AnonymousClass1.a[ordinal()]) {
            case o.a /*1*/:
                return e;
            case o.b /*2*/:
                return h;
            case o.c /*3*/:
                return f;
            default:
                return g;
        }
    }

    public boolean b() {
        return this == PORTRAIT || this == PORTRAIT_REVERSE;
    }

    public boolean c() {
        return this == LANDSCAPE || this == LANDSCAPE_REVERSE;
    }
}
