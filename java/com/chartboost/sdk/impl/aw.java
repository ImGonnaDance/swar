package com.chartboost.sdk.impl;

import com.chartboost.sdk.Libraries.e;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.a;
import com.chartboost.sdk.Model.a.c;
import com.chartboost.sdk.Model.b;
import com.chartboost.sdk.d;

public class aw extends d {
    private static aw c;
    protected int b;
    private a d = null;
    private boolean e;
    private boolean f;

    private aw() {
    }

    public static aw f() {
        if (c == null) {
            synchronized (aw.class) {
                if (c == null) {
                    c = new aw();
                }
            }
        }
        return c;
    }

    public void a(String str) {
        this.b = 0;
        g();
        super.a(str);
    }

    protected void a(a aVar, e.a aVar2) {
        if (!this.e && this.f) {
            this.f = false;
            this.b = aVar2.a("cells").o();
        }
        super.a(aVar, aVar2);
    }

    protected a a(String str, boolean z) {
        return new a(c.MORE_APPS, z, null, false);
    }

    protected ba e(a aVar) {
        ba baVar = new ba("/more/get");
        baVar.a(l.a.HIGH);
        baVar.a(b.c);
        return baVar;
    }

    protected ba l(a aVar) {
        ba baVar = new ba("/more/show");
        if (aVar.d != null) {
            baVar.a("location", aVar.d);
        }
        if (aVar.w().c("cells")) {
            baVar.a("cells", aVar.w().a("cells"));
        }
        return baVar;
    }

    public void a() {
        this.d = null;
    }

    protected a d(String str) {
        return this.d;
    }

    protected void e(String str) {
        this.d = null;
    }

    protected void q(a aVar) {
        this.d = aVar;
    }

    protected a c() {
        return new a(this) {
            final /* synthetic */ aw a;

            {
                this.a = r1;
            }

            public void a(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didClickMoreApps(aVar.d);
                }
            }

            public void b(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didCloseMoreApps(aVar.d);
                }
            }

            public void c(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didDismissMoreApps(aVar.d);
                }
            }

            public void d(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didCacheMoreApps(aVar.d);
                }
            }

            public void a(a aVar, CBImpressionError cBImpressionError) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didFailToLoadMoreApps(aVar.d, cBImpressionError);
                }
            }

            public void e(a aVar) {
                this.a.b = 0;
                this.a.g();
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didDisplayMoreApps(aVar.d);
                }
            }

            public boolean f(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    return com.chartboost.sdk.b.d().shouldDisplayMoreApps(aVar.d);
                }
                return true;
            }

            public boolean g(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    return com.chartboost.sdk.b.d().shouldRequestMoreApps(aVar.d);
                }
                return true;
            }

            public boolean h(a aVar) {
                return true;
            }
        };
    }

    protected void g() {
    }

    public String e() {
        return "more-apps";
    }
}
