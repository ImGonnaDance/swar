package jp.co.cyberz.fox.a.a;

import java.net.URLEncoder;

public final class c {
    private static final String a = "UTF-8";
    private String b;
    private String c = null;
    private String d = null;
    private String e = null;
    private String f = null;
    private String g = null;
    private boolean h = false;

    public final String a() {
        if (i.a(this.b)) {
            return i.a;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            stringBuffer.append(this.b).append(i.b).append(URLEncoder.encode(i.a(this.c, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(this.d, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(this.e, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(this.f, i.a), a)).append(i.b).append(URLEncoder.encode(i.a(this.g, i.a), a)).append(i.b).append(e.d).append(i.b).append(e.f).append(i.b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuffer.toString();
    }

    public final void a(String str) {
        this.b = str;
    }

    public final void b(String str) {
        this.c = str;
    }

    public final boolean b() {
        return this.h;
    }

    public final void c() {
        this.h = true;
    }

    public final void c(String str) {
        this.d = str;
    }

    public final void d(String str) {
        this.e = str;
    }

    public final void e(String str) {
        this.f = str;
    }

    public final void f(String str) {
        this.g = str;
    }
}
