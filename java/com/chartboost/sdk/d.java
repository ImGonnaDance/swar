package com.chartboost.sdk;

import android.content.Context;
import android.os.Handler;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.a.b;
import com.chartboost.sdk.impl.az;
import com.chartboost.sdk.impl.ba;
import com.chartboost.sdk.impl.ba.c;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import jp.co.cyberz.fox.a.a.i;

public abstract class d {
    protected static Handler a = CBUtility.e();
    private Map<String, com.chartboost.sdk.Model.a> b = new HashMap();
    private Map<String, com.chartboost.sdk.Model.a> c = new HashMap();
    private a d = null;

    protected interface a {
        void a(com.chartboost.sdk.Model.a aVar);

        void a(com.chartboost.sdk.Model.a aVar, CBImpressionError cBImpressionError);

        void b(com.chartboost.sdk.Model.a aVar);

        void c(com.chartboost.sdk.Model.a aVar);

        void d(com.chartboost.sdk.Model.a aVar);

        void e(com.chartboost.sdk.Model.a aVar);

        boolean f(com.chartboost.sdk.Model.a aVar);

        boolean g(com.chartboost.sdk.Model.a aVar);

        boolean h(com.chartboost.sdk.Model.a aVar);
    }

    protected abstract com.chartboost.sdk.Model.a a(String str, boolean z);

    protected abstract a c();

    protected abstract ba e(com.chartboost.sdk.Model.a aVar);

    public abstract String e();

    protected abstract ba l(com.chartboost.sdk.Model.a aVar);

    public void a(final String str) {
        final com.chartboost.sdk.Model.a a = a(str, false);
        e d = Chartboost.d();
        if (d != null && d.c()) {
            a(a, CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
        } else if (!b(a)) {
            a.post(new Runnable(this) {
                final /* synthetic */ d c;

                public void run() {
                    if (this.c.c(str)) {
                        this.c.g(this.c.d(str));
                    } else {
                        this.c.c(a);
                    }
                }
            });
        }
    }

    public void b(String str) {
        if (!c(str)) {
            com.chartboost.sdk.Model.a a = a(str, true);
            if (!b(a)) {
                c(a);
            }
        }
    }

    protected void a(com.chartboost.sdk.Model.a aVar) {
        q(aVar);
        b().d(aVar);
        aVar.b = b.CACHED;
    }

    protected final boolean b(com.chartboost.sdk.Model.a aVar) {
        if (b().h(aVar) || CBUtility.a().getInt("cbPrefSessionCount", 0) != 1) {
            return false;
        }
        a(aVar, CBImpressionError.FIRST_SESSION_INTERSTITIALS_DISABLED);
        return true;
    }

    protected void c(com.chartboost.sdk.Model.a aVar) {
        if (f(aVar) && b().g(aVar) && !s(aVar)) {
            if (!aVar.f && b.r()) {
                aVar.j = true;
                Chartboost.a(aVar);
            }
            if (d(aVar)) {
                ba e = e(aVar);
                if (e != null) {
                    a(e, aVar);
                    com.chartboost.sdk.Tracking.a.a(e(), aVar.d, aVar.f);
                    return;
                }
                return;
            }
            o(aVar);
        }
    }

    protected boolean d(com.chartboost.sdk.Model.a aVar) {
        return true;
    }

    private final synchronized boolean s(com.chartboost.sdk.Model.a aVar) {
        boolean z = true;
        synchronized (this) {
            if (n(aVar) != null) {
                CBLogging.b(getClass().getSimpleName(), String.format("%s %s", new Object[]{"Request already in process for impression with location", aVar.d}));
            } else {
                p(aVar);
                z = false;
            }
        }
        return z;
    }

    protected void a(com.chartboost.sdk.Model.a aVar, CBImpressionError cBImpressionError) {
        o(aVar);
        e d = Chartboost.d();
        if (d != null && d.b()) {
            d.a(aVar, true);
        } else if (d != null && d.c()) {
            d.b(aVar);
        }
        com.chartboost.sdk.Tracking.a.a(e(), aVar.d, cBImpressionError);
        b().a(aVar, cBImpressionError);
    }

    protected final boolean f(com.chartboost.sdk.Model.a aVar) {
        if (b.l()) {
            e d = Chartboost.d();
            if (!aVar.f && d != null && d.c()) {
                a(aVar, CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
                return false;
            } else if (az.a().c()) {
                return true;
            } else {
                a(aVar, CBImpressionError.INTERNET_UNAVAILABLE);
                return false;
            }
        }
        a(aVar, CBImpressionError.SESSION_NOT_STARTED);
        return false;
    }

    protected void g(com.chartboost.sdk.Model.a aVar) {
        boolean z = true;
        o(aVar);
        boolean z2 = aVar.b != b.DISPLAYED;
        if (!z2 || b().f(aVar)) {
            if (aVar.b != b.CACHED) {
                z = false;
            }
            i(aVar);
            e d = Chartboost.d();
            if (d != null) {
                if (d.b()) {
                    d.a(aVar, false);
                } else if (!(!aVar.j || r0 || aVar.b == b.DISPLAYED)) {
                    return;
                }
            }
            if (z2) {
                h(aVar);
            } else {
                Chartboost.a(aVar);
            }
        }
    }

    protected void h(com.chartboost.sdk.Model.a aVar) {
        Chartboost.a(aVar);
    }

    protected void i(com.chartboost.sdk.Model.a aVar) {
        j(aVar);
    }

    public final void j(com.chartboost.sdk.Model.a aVar) {
        if (!aVar.h) {
            aVar.h = true;
            aVar.f = false;
            k(aVar);
            if (d(aVar.d) == aVar) {
                e(aVar.d);
            }
            if (b.g() && !c(aVar.d)) {
                b(aVar.d);
            }
        }
    }

    protected final void k(com.chartboost.sdk.Model.a aVar) {
        boolean z = true;
        ba l = l(aVar);
        l.a(true);
        l.b(d());
        if (aVar.b != b.CACHED) {
            z = false;
        }
        if (z || aVar.g) {
            l.a("cached", a.e);
        }
        Object e = aVar.w().e("ad_id");
        if (e != null) {
            l.a("ad_id", e);
        }
        l.s();
        com.chartboost.sdk.Tracking.a.a(e(), aVar.d, aVar.p());
    }

    protected final boolean m(com.chartboost.sdk.Model.a aVar) {
        return TimeUnit.MILLISECONDS.toSeconds(new Date().getTime() - aVar.a.getTime()) >= 86400;
    }

    protected void a(com.chartboost.sdk.Model.a aVar, com.chartboost.sdk.Libraries.e.a aVar2) {
        if (aVar2.f("status") == 404) {
            CBLogging.b(aVar.c, "Invalid status code" + aVar2.a("status"));
            a(aVar, CBImpressionError.NO_AD_FOUND);
        } else if (aVar2.f("status") != SelectTarget.TARGETING_SUCCESS) {
            CBLogging.b(aVar.c, "Invalid status code" + aVar2.a("status"));
            a(aVar, CBImpressionError.INTERNAL);
        } else {
            com.chartboost.sdk.Tracking.a.c(e(), aVar.p(), aVar.f);
            aVar.a(aVar2, c.a().a);
        }
    }

    protected final void a(ba baVar, final com.chartboost.sdk.Model.a aVar) {
        baVar.a("location", aVar.d);
        if (aVar.f) {
            baVar.a("cache", a.e);
            baVar.b(true);
        }
        baVar.b(Chartboost.getValidContext());
        aVar.q = true;
        baVar.a(new c(this) {
            final /* synthetic */ d b;

            public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar) {
                aVar.q = false;
                if (aVar.b() || aVar.a("assets").b()) {
                    this.b.a(aVar, CBImpressionError.NO_AD_FOUND);
                } else {
                    this.b.a(aVar, aVar);
                }
            }

            public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar, CBError cBError) {
                aVar.q = false;
                String str = "network failure";
                String str2 = "request %s failed with error %s: %s";
                Object[] objArr = new Object[3];
                objArr[0] = baVar.g();
                objArr[1] = cBError.a().name();
                objArr[2] = cBError.b() != null ? cBError.b() : i.a;
                CBLogging.d(str, String.format(str2, objArr));
                this.b.a(aVar, cBError.c());
            }
        });
    }

    protected synchronized com.chartboost.sdk.Model.a n(com.chartboost.sdk.Model.a aVar) {
        com.chartboost.sdk.Model.a aVar2;
        if (aVar != null) {
            aVar2 = (com.chartboost.sdk.Model.a) this.b.get(aVar.d);
        } else {
            aVar2 = null;
        }
        return aVar2;
    }

    protected synchronized void o(com.chartboost.sdk.Model.a aVar) {
        if (aVar != null) {
            this.b.remove(aVar.d);
        }
    }

    protected synchronized void p(com.chartboost.sdk.Model.a aVar) {
        if (aVar != null) {
            this.b.put(aVar.d, aVar);
        }
    }

    public boolean c(String str) {
        return d(str) != null;
    }

    protected com.chartboost.sdk.Model.a d(String str) {
        com.chartboost.sdk.Model.a aVar = (com.chartboost.sdk.Model.a) this.c.get(str);
        return (aVar == null || m(aVar)) ? null : aVar;
    }

    protected void e(String str) {
        this.c.remove(str);
    }

    protected void a() {
        this.c.clear();
    }

    protected void q(com.chartboost.sdk.Model.a aVar) {
        this.c.put(aVar.d, aVar);
    }

    protected final a b() {
        if (this.d == null) {
            this.d = c();
        }
        return this.d;
    }

    protected Context d() {
        try {
            Method declaredMethod = Chartboost.class.getDeclaredMethod("getValidContext", new Class[0]);
            declaredMethod.setAccessible(true);
            return (Context) declaredMethod.invoke(null, new Object[0]);
        } catch (Throwable e) {
            CBLogging.b(this, "Error encountered getting valid context", e);
            CBUtility.throwProguardError(e);
            return b.k();
        }
    }

    public void r(com.chartboost.sdk.Model.a aVar) {
    }
}
