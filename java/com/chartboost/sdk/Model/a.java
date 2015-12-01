package com.chartboost.sdk.Model;

import android.text.TextUtils;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.f;
import com.chartboost.sdk.impl.ae;
import com.chartboost.sdk.impl.af;
import com.chartboost.sdk.impl.ah;
import com.chartboost.sdk.impl.ai;
import com.chartboost.sdk.impl.aw;
import com.chartboost.sdk.impl.ax;
import com.chartboost.sdk.impl.ba;
import com.chartboost.sdk.impl.bc;
import com.chartboost.sdk.impl.bq;
import com.chartboost.sdk.impl.bs;
import com.com2us.peppermint.PeppermintConstant;
import java.util.Date;
import jp.co.dimage.android.o;

public final class a {
    public Date a;
    public b b = b.LOADING;
    public c c;
    public String d;
    public d e;
    public boolean f;
    public boolean g;
    public boolean h;
    public bq i;
    public boolean j;
    public boolean k = false;
    public boolean l = false;
    public boolean m = false;
    public boolean n = false;
    public ba o;
    public boolean p;
    public boolean q = false;
    private com.chartboost.sdk.Libraries.e.a r;
    private boolean s;
    private Boolean t = null;
    private f u;
    private a v;
    private Runnable w;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[c.values().length];

        static {
            try {
                a[c.INTERSTITIAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[c.REWARDED_VIDEO.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[c.MORE_APPS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[c.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public interface a {
        void a(a aVar);

        void a(a aVar, CBImpressionError cBImpressionError);

        void a(a aVar, String str, com.chartboost.sdk.Libraries.e.a aVar2);

        void b(a aVar);

        void c(a aVar);

        void d(a aVar);
    }

    public enum b {
        LOADING,
        LOADED,
        DISPLAYED,
        CACHED,
        DISMISSING,
        NONE
    }

    public enum c {
        INTERSTITIAL,
        MORE_APPS,
        REWARDED_VIDEO,
        NONE
    }

    public enum d {
        INTERSTITIAL,
        INTERSTITIAL_VIDEO,
        INTERSTITIAL_REWARD_VIDEO,
        NONE
    }

    public a(c cVar, boolean z, String str, boolean z2) {
        this.f = z;
        this.g = z;
        this.h = false;
        this.c = cVar;
        this.j = z2;
        this.r = com.chartboost.sdk.Libraries.e.a.a;
        this.e = d.NONE;
        this.d = str;
        this.s = true;
        if (this.d == null) {
            this.d = CBLocation.LOCATION_DEFAULT;
        }
    }

    public void a(com.chartboost.sdk.Libraries.e.a aVar, a aVar2) {
        if (aVar == null) {
            aVar = com.chartboost.sdk.Libraries.e.a.a();
        }
        this.r = aVar;
        this.a = new Date();
        this.b = b.LOADING;
        this.v = aVar2;
        if (aVar.a(PeppermintConstant.JSON_KEY_TYPE).equals("native")) {
            switch (AnonymousClass1.a[this.c.ordinal()]) {
                case o.a /*1*/:
                    if (!aVar.a("media-type").equals("video")) {
                        this.e = d.INTERSTITIAL;
                        this.u = new ah(this);
                        break;
                    }
                    this.e = d.INTERSTITIAL_VIDEO;
                    this.u = new ai(this);
                    this.s = false;
                    break;
                case o.b /*2*/:
                    this.e = d.INTERSTITIAL_REWARD_VIDEO;
                    this.u = new ai(this);
                    this.s = false;
                    break;
                case o.c /*3*/:
                    this.u = new ax(this);
                    this.s = false;
                    break;
            }
        }
        this.u = new bs(this);
        this.u.a(aVar);
    }

    public boolean a() {
        return this.s;
    }

    public void b() {
        if (this.v != null) {
            this.v.b(this);
        }
    }

    public void c() {
        if (this.v != null) {
            this.v.a(this);
        }
    }

    public boolean a(String str, com.chartboost.sdk.Libraries.e.a aVar) {
        if (this.b != b.DISPLAYED || this.l) {
            return false;
        }
        if (str == null) {
            str = this.r.e("link");
        }
        String e = this.r.e("deep-link");
        if (!TextUtils.isEmpty(e)) {
            try {
                if (bc.a(e)) {
                    try {
                        this.t = new Boolean(true);
                        str = e;
                    } catch (Exception e2) {
                        str = e;
                    }
                } else {
                    this.t = new Boolean(false);
                }
            } catch (Exception e3) {
            }
        }
        if (this.p) {
            return false;
        }
        this.p = true;
        this.v.a(this, str, aVar);
        return true;
    }

    public boolean d() {
        return this.t != null;
    }

    public boolean e() {
        return this.t.booleanValue();
    }

    public void a(CBImpressionError cBImpressionError) {
        if (this.v != null) {
            this.v.a(this, cBImpressionError);
        }
    }

    public void f() {
        if (this.v != null) {
            this.v.c(this);
        }
    }

    public void g() {
        if (this.v != null) {
            this.v.d(this);
        }
    }

    public boolean h() {
        if (this.u != null) {
            this.u.b();
            if (this.u.e() != null) {
                return true;
            }
        }
        CBLogging.b("CBImpression", "reinitializing -- no view protocol exists!!");
        CBLogging.e("CBImpression", "reinitializing -- view not yet created");
        return false;
    }

    public void i() {
        j();
        if (!this.g || this.h) {
            if (this.u != null) {
                this.u.d();
            }
            this.u = null;
            return;
        }
        this.b = b.CACHED;
    }

    public void j() {
        if (this.i != null) {
            this.i.d();
            try {
                if (!(this.u == null || this.u.e() == null || this.u.e().getParent() == null)) {
                    this.i.removeView(this.u.e());
                }
            } catch (Throwable e) {
                CBLogging.b("CBImpression", "Exception raised while cleaning up views", e);
            }
            this.i = null;
        }
        if (this.u != null) {
            this.u.f();
        }
    }

    public CBImpressionError k() {
        if (this.u != null) {
            return this.u.c();
        }
        return null;
    }

    public com.chartboost.sdk.f.a l() {
        if (this.u != null) {
            return this.u.e();
        }
        return null;
    }

    public void m() {
        if (this.u != null && this.u.e() != null) {
            this.u.e().setVisibility(8);
        }
    }

    public void a(Runnable runnable) {
        this.w = runnable;
    }

    public void n() {
        this.l = true;
    }

    public void o() {
        if (this.w != null) {
            this.w.run();
            this.w = null;
        }
        this.l = false;
        this.k = false;
    }

    public String p() {
        return this.r.e("ad_id");
    }

    public com.chartboost.sdk.d q() {
        switch (AnonymousClass1.a[this.c.ordinal()]) {
            case o.b /*2*/:
                return af.h();
            case o.c /*3*/:
                return aw.f();
            default:
                return ae.f();
        }
    }

    public void r() {
        q().j(this);
    }

    public boolean s() {
        if (this.u != null) {
            return this.u.j();
        }
        return false;
    }

    public void t() {
        this.p = false;
        if (this.u != null) {
            this.u.k();
        }
    }

    public void u() {
        this.p = false;
    }

    public void v() {
        if (this.u != null) {
            this.u.l();
        }
    }

    public com.chartboost.sdk.Libraries.e.a w() {
        return this.r == null ? com.chartboost.sdk.Libraries.e.a.a : this.r;
    }

    public void a(com.chartboost.sdk.Libraries.e.a aVar) {
        if (aVar == null) {
            aVar = com.chartboost.sdk.Libraries.e.a.a;
        }
        this.r = aVar;
    }
}
