package com.chartboost.sdk.impl;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;

public abstract class l<T> implements Comparable<l<T>> {
    private final a a;
    private final int b;
    private final String c;
    private final int d;
    private final com.chartboost.sdk.impl.n.a e;
    private Integer f;
    private m g;
    private boolean h;
    private boolean i;
    private boolean j;
    private long k;
    private p l;
    private com.chartboost.sdk.impl.b.a m;
    private Object n;

    public enum a {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }

    protected abstract n<T> a(i iVar);

    protected abstract void b(T t);

    public /* synthetic */ int compareTo(Object obj) {
        return a((l) obj);
    }

    public l(int i, String str, com.chartboost.sdk.impl.n.a aVar) {
        a aVar2;
        if (a.a) {
            aVar2 = new a();
        } else {
            aVar2 = null;
        }
        this.a = aVar2;
        this.h = true;
        this.i = false;
        this.j = false;
        this.k = 0;
        this.m = null;
        this.b = i;
        this.c = str;
        this.e = aVar;
        a(new d());
        this.d = c(str);
    }

    public int a() {
        return this.b;
    }

    public l<?> a(Object obj) {
        this.n = obj;
        return this;
    }

    public Object b() {
        return this.n;
    }

    public int c() {
        return this.d;
    }

    private static int c(String str) {
        if (!TextUtils.isEmpty(str)) {
            Uri parse = Uri.parse(str);
            if (parse != null) {
                String host = parse.getHost();
                if (host != null) {
                    return host.hashCode();
                }
            }
        }
        return 0;
    }

    public l<?> a(p pVar) {
        this.l = pVar;
        return this;
    }

    public void a(String str) {
        if (a.a) {
            this.a.a(str, Thread.currentThread().getId());
        } else if (this.k == 0) {
            this.k = SystemClock.elapsedRealtime();
        }
    }

    void b(final String str) {
        if (this.g != null) {
            this.g.b(this);
        }
        if (a.a) {
            final long id = Thread.currentThread().getId();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                new Handler(Looper.getMainLooper()).post(new Runnable(this) {
                    final /* synthetic */ l a;

                    public void run() {
                        this.a.a.a(str, id);
                        this.a.a.a(toString());
                    }
                });
                return;
            }
            this.a.a(str, id);
            this.a.a(toString());
            return;
        }
        if (SystemClock.elapsedRealtime() - this.k >= 3000) {
            t.b("%d ms: %s", Long.valueOf(SystemClock.elapsedRealtime() - this.k), toString());
        }
    }

    public l<?> a(m mVar) {
        this.g = mVar;
        return this;
    }

    public final l<?> a(int i) {
        this.f = Integer.valueOf(i);
        return this;
    }

    public String d() {
        return this.c;
    }

    public String e() {
        return d();
    }

    public l<?> a(com.chartboost.sdk.impl.b.a aVar) {
        this.m = aVar;
        return this;
    }

    public com.chartboost.sdk.impl.b.a f() {
        return this.m;
    }

    public void g() {
        this.i = true;
    }

    public boolean h() {
        return this.i;
    }

    public Map<String, String> i() throws a {
        return Collections.emptyMap();
    }

    @Deprecated
    protected Map<String, String> j() throws a {
        return n();
    }

    @Deprecated
    protected String k() {
        return o();
    }

    @Deprecated
    public String l() {
        return p();
    }

    @Deprecated
    public byte[] m() throws a {
        Map j = j();
        if (j == null || j.size() <= 0) {
            return null;
        }
        return a(j, k());
    }

    protected Map<String, String> n() throws a {
        return null;
    }

    protected String o() {
        return "UTF-8";
    }

    public String p() {
        return "application/x-www-form-urlencoded; charset=" + o();
    }

    public byte[] q() throws a {
        Map n = n();
        if (n == null || n.size() <= 0) {
            return null;
        }
        return a(n, o());
    }

    private byte[] a(Map<String, String> map, String str) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (Entry entry : map.entrySet()) {
                stringBuilder.append(URLEncoder.encode((String) entry.getKey(), str));
                stringBuilder.append('=');
                stringBuilder.append(URLEncoder.encode((String) entry.getValue(), str));
                stringBuilder.append('&');
            }
            return stringBuilder.toString().getBytes(str);
        } catch (Throwable e) {
            throw new RuntimeException("Encoding not supported: " + str, e);
        }
    }

    public final boolean r() {
        return this.h;
    }

    public a s() {
        return a.NORMAL;
    }

    public final int t() {
        return this.l.a();
    }

    public p u() {
        return this.l;
    }

    public void v() {
        this.j = true;
    }

    public boolean w() {
        return this.j;
    }

    protected s a(s sVar) {
        return sVar;
    }

    public void b(s sVar) {
        if (this.e != null) {
            this.e.a(sVar);
        }
    }

    public int a(l<T> lVar) {
        a s = s();
        a s2 = lVar.s();
        if (s == s2) {
            return this.f.intValue() - lVar.f.intValue();
        }
        return s2.ordinal() - s.ordinal();
    }

    public String toString() {
        return new StringBuilder(String.valueOf(this.i ? "[X] " : "[ ] ")).append(d()).append(" ").append("0x" + Integer.toHexString(c())).append(" ").append(s()).append(" ").append(this.f).toString();
    }
}
