package com.mobileapptracker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import jp.co.cyberz.fox.a.a.i;

final class b {
    private static volatile b i;
    private String a = null;
    private String b = null;
    private String c = null;
    private String d = null;
    private int e = 0;
    private String f = null;
    private String g = null;
    private MATResponse h = null;

    private b() {
    }

    public static synchronized b a(String str, String str2, String str3) {
        b bVar;
        synchronized (b.class) {
            bVar = new b();
            i = bVar;
            bVar.a = str;
            i.b = str2;
            i.c = str3;
            bVar = i;
        }
        return bVar;
    }

    public static String a() {
        return i.a;
    }

    public static void a(MATResponse mATResponse) {
        i.h = mATResponse;
    }

    public static void a(String str) {
        i.c = str;
    }

    public static void a(String str, int i) {
        i.d = str;
        i.e = i;
    }

    public static String b() {
        return i.b;
    }

    public static void b(String str) {
        i.g = str;
    }

    public static String c() {
        return i.c;
    }

    public static void c(String str) {
        i.f = str;
    }

    public static String d() {
        return i.g;
    }

    public static String e() {
        return i.d;
    }

    public static int f() {
        return i.e;
    }

    public static String g() {
        return i.f;
    }

    public final String a(Context context, int i) {
        String str = i.a;
        if (!(i.a == null || i.b == null || i.c == null || (i.d == null && i.f == null))) {
            try {
                b bVar = i;
                str = g.a(i);
                if (str.length() != 0) {
                    if (this.h != null) {
                        this.h.didReceiveDeeplink(str);
                    }
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(str));
                    intent.setFlags(268435456);
                    context.startActivity(intent);
                }
            } catch (Exception e) {
            }
        }
        return str;
    }
}
