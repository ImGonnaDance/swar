package jp.co.cyberz.fox.a.a;

import android.os.Build;
import android.os.Build.VERSION;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import jp.co.dimage.android.b;
import jp.co.dimage.android.p;

public final class a {
    private static final String a = "UTF-8";
    private int b = 0;
    private String c = i.a;
    private String d = null;
    private String e = null;
    private int f = 0;
    private String g = null;
    private String h = null;
    private String i = null;
    private double j;
    private int k;
    private String l = null;
    private a m = null;
    private String n = null;
    private String o = null;
    private String p = null;
    private int q = 0;
    private String r = null;
    private String s;
    private String t;

    public enum a {
        AnalyticsEventStartSession(a.e),
        AnalyticsEventEndSession(a.f),
        AnalyticsEventPageView("3"),
        AnalyticsEventTrackEvent("4"),
        AnalyticsEventTracTransaction("5");
        
        private final String f;

        private a(String str) {
            this.f = str;
        }

        public static a a(String str) {
            Object obj = g;
            int length = obj.length;
            Object obj2 = new a[length];
            System.arraycopy(obj, 0, obj2, 0, length);
            for (a aVar : obj2) {
                if (aVar.f.equals(str)) {
                    return aVar;
                }
            }
            return null;
        }

        private static a b(String str) {
            return (a) Enum.valueOf(a.class, str);
        }

        private static a[] b() {
            Object obj = g;
            int length = obj.length;
            Object obj2 = new a[length];
            System.arraycopy(obj, 0, obj2, 0, length);
            return obj2;
        }

        public final String a() {
            return this.f;
        }
    }

    private int c() {
        return this.b;
    }

    private String d() {
        return this.c;
    }

    private void d(int i) {
        this.q = i;
    }

    private String e() {
        return this.d;
    }

    private String f() {
        return this.e;
    }

    private int g() {
        return this.f;
    }

    private String h() {
        return this.g;
    }

    private String i() {
        return this.h;
    }

    private String j() {
        return this.i;
    }

    private double k() {
        return this.j;
    }

    private int l() {
        return this.k;
    }

    private String m() {
        return this.l;
    }

    private void m(String str) {
        this.p = str;
    }

    private a n() {
        return this.m;
    }

    private void n(String str) {
        this.r = str;
    }

    private String o() {
        return this.n;
    }

    private String p() {
        return this.o;
    }

    private String q() {
        return this.p;
    }

    private int r() {
        return this.q;
    }

    private String s() {
        return this.r;
    }

    private String t() {
        return this.s;
    }

    private String u() {
        return this.t;
    }

    public final void a() {
        this.q++;
    }

    public final void a(double d) {
        this.j = d;
    }

    public final void a(int i) {
        this.b = i;
    }

    public final void a(String str) {
        this.c = str;
    }

    public final void a(a aVar) {
        this.m = aVar;
    }

    public final String b() {
        String format = new SimpleDateFormat("Z").format(new Date());
        StringBuffer stringBuffer = new StringBuffer();
        try {
            StringBuffer append = stringBuffer.append(i.a(Integer.valueOf(this.b))).append(i.b).append(i.a(this.m.a(), i.a)).append(i.b).append(URLEncoder.encode(i.a(this.c, i.a), a)).append(i.b).append(i.a(this.o, i.a)).append(i.b).append(i.a(e.d, i.a)).append(i.b).append(i.a(e.e, i.a)).append(i.b).append(i.a(this.r, e.c)).append(i.b).append(i.a(this.n, i.a)).append(i.b).append(i.a(e.f, i.a)).append(i.b).append(i.a(e.g, i.a)).append(i.b).append(i.a(e.h, i.a)).append(i.b).append(URLEncoder.encode(i.a(this.d, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(this.e, i.a), a)).append(i.b).append(i.a(Integer.valueOf(this.f))).append(i.b).append(URLEncoder.encode(i.a(this.g, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(this.h, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(this.i, i.a), a)).append(i.b);
            Double valueOf = Double.valueOf(this.j);
            String str = i.a;
            if (valueOf != null) {
                str = valueOf.toString();
            }
            append.append(str).append(i.b).append(i.a(Integer.valueOf(this.k))).append(i.b).append(i.a(this.l, i.a)).append(i.b).append(URLEncoder.encode(i.a(e.m, i.a), a)).append(i.b).append(i.a(e.i, i.a)).append(i.b).append(i.a(Integer.valueOf(this.q))).append(i.b).append(URLEncoder.encode(i.a(Build.MODEL, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(VERSION.RELEASE, i.a), a)).append(i.b).append(i.a(format, i.a)).append(",,,2,").append(URLEncoder.encode(i.a(this.s, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(this.t, i.a), a)).append(i.b).append(i.a(b.a(), i.a)).append(i.b).append(i.a(b.c(), i.a)).append(i.b).append(i.a(p.a(), i.a));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public final void b(int i) {
        this.f = i;
    }

    public final void b(String str) {
        this.d = str;
    }

    public final void c(int i) {
        this.k = i;
    }

    public final void c(String str) {
        this.e = str;
    }

    public final void d(String str) {
        this.g = str;
    }

    public final void e(String str) {
        this.h = str;
    }

    public final void f(String str) {
        this.i = str;
    }

    public final void g(String str) {
        this.l = str;
    }

    public final void h(String str) {
        this.n = str;
    }

    public final void i(String str) {
        this.o = str;
    }

    public final void j(String str) {
        this.s = str;
    }

    public final void k(String str) {
        this.t = str;
    }

    public final void l(String str) {
        try {
            String[] split = str.split(i.b);
            this.b = Integer.valueOf(split[0]).intValue();
            this.m = a.a(split[1]);
            this.c = URLDecoder.decode(split[2], a);
            this.o = split[3];
            this.r = i.a(split[6]) ? null : split[6];
            this.d = URLDecoder.decode(split[11], a);
            this.e = URLDecoder.decode(split[12], a);
            this.f = Integer.valueOf(split[13]).intValue();
            this.g = URLDecoder.decode(split[14], a);
            this.h = URLDecoder.decode(split[15], a);
            this.i = URLDecoder.decode(split[16], a);
            this.j = Double.valueOf(split[17]).doubleValue();
            this.k = Integer.valueOf(split[18]).intValue();
            this.l = split[19];
            this.s = URLDecoder.decode(split[29], a);
            this.t = URLDecoder.decode(split[30], a);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
