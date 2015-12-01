package com.chartboost.sdk.Model;

import jp.co.dimage.android.f;
import jp.co.dimage.android.o;

public final class CBError {
    private a a;
    private String b;
    private boolean c = true;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[a.values().length];

        static {
            try {
                a[a.MISCELLANEOUS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.UNEXPECTED_RESPONSE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.PUBLIC_KEY_MISSING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[a.INTERNET_UNAVAILABLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[a.HTTP_NOT_FOUND.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[a.NETWORK_FAILURE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[a.INVALID_RESPONSE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public enum CBClickError {
        URI_INVALID,
        URI_UNRECOGNIZED,
        AGE_GATE_FAILURE,
        NO_HOST_ACTIVITY,
        INTERNAL
    }

    public enum CBImpressionError {
        INTERNAL,
        INTERNET_UNAVAILABLE,
        TOO_MANY_CONNECTIONS,
        WRONG_ORIENTATION,
        FIRST_SESSION_INTERSTITIALS_DISABLED,
        NETWORK_FAILURE,
        NO_AD_FOUND,
        SESSION_NOT_STARTED,
        IMPRESSION_ALREADY_VISIBLE,
        NO_HOST_ACTIVITY,
        USER_CANCELLATION,
        INVALID_LOCATION
    }

    public enum a {
        MISCELLANEOUS,
        INTERNET_UNAVAILABLE,
        INVALID_RESPONSE,
        UNEXPECTED_RESPONSE,
        NETWORK_FAILURE,
        PUBLIC_KEY_MISSING,
        HTTP_NOT_FOUND
    }

    public CBError(a error, String errorDesc) {
        this.a = error;
        this.b = errorDesc;
    }

    public a a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public CBImpressionError c() {
        switch (AnonymousClass1.a[this.a.ordinal()]) {
            case o.a /*1*/:
            case o.b /*2*/:
            case o.c /*3*/:
                return CBImpressionError.INTERNAL;
            case o.d /*4*/:
                return CBImpressionError.INTERNET_UNAVAILABLE;
            case f.bc /*5*/:
                return CBImpressionError.NO_AD_FOUND;
            default:
                return CBImpressionError.NETWORK_FAILURE;
        }
    }
}
