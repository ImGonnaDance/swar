package com.chartboost.sdk.InPlay;

import android.graphics.Bitmap;
import com.chartboost.sdk.Libraries.e.a;
import com.chartboost.sdk.b;

public final class CBInPlay {
    private static a e = null;
    private String a;
    private Bitmap b;
    private String c;
    private a d;

    CBInPlay() {
    }

    public void show() {
        a.a().a(this);
    }

    public void click() {
        a.a().b(this);
    }

    public String getLocation() {
        return this.a;
    }

    protected void a(String str) {
        this.a = str;
    }

    public Bitmap getAppIcon() {
        return this.b;
    }

    protected void a(Bitmap bitmap) {
        this.b = bitmap;
    }

    public String getAppName() {
        return this.c;
    }

    protected void b(String str) {
        this.c = str;
    }

    protected a a() {
        return this.d;
    }

    protected void a(a aVar) {
        this.d = aVar;
    }

    public static void cacheInPlay(String location) {
        b.o();
        b.n();
        b.m();
        if (e == null) {
            e = a.a();
        }
        e.a(location);
    }

    public static boolean hasInPlay(String location) {
        b.o();
        b.n();
        b.m();
        if (e == null) {
            e = a.a();
        }
        return e.b(location);
    }

    public static CBInPlay getInPlay(String location) {
        b.o();
        b.n();
        b.m();
        if (e == null) {
            e = a.a();
        }
        return e.c(location);
    }
}
