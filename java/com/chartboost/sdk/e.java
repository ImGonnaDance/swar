package com.chartboost.sdk;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.a;
import com.chartboost.sdk.Model.a.b;
import com.chartboost.sdk.Model.a.c;
import com.chartboost.sdk.Model.a.d;
import com.chartboost.sdk.impl.bi;
import com.chartboost.sdk.impl.bq;
import jp.co.dimage.android.o;

public final class e {
    private static e c;
    private bq a = null;
    private a b;

    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[b.values().length];

        static {
            try {
                a[b.LOADING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    private e() {
    }

    protected static e a() {
        if (c == null) {
            c = new e();
        }
        return c;
    }

    protected void a(a aVar) {
        switch (AnonymousClass3.a[aVar.b.ordinal()]) {
            case o.a /*1*/:
                if (aVar.j && b.r()) {
                    f(aVar);
                    return;
                }
                return;
            default:
                e(aVar);
                return;
        }
    }

    private void e(a aVar) {
        if (this.a == null || this.a.h() == aVar) {
            Object obj = aVar.b != b.DISPLAYED ? 1 : null;
            aVar.b = b.DISPLAYED;
            Context b = Chartboost.b();
            CBImpressionError cBImpressionError = b == null ? CBImpressionError.NO_HOST_ACTIVITY : null;
            if (cBImpressionError == null) {
                cBImpressionError = aVar.k();
            }
            if (cBImpressionError != null) {
                aVar.a(cBImpressionError);
                return;
            }
            if (this.a == null) {
                this.a = new bq(b, aVar);
                b.addContentView(this.a, new LayoutParams(-1, -1));
            }
            this.a.a();
            aVar.i = this.a;
            if (obj != null) {
                this.a.e().a();
                bi.b bVar = bi.b.PERSPECTIVE_ROTATE;
                if (aVar.c == c.MORE_APPS) {
                    bVar = bi.b.PERSPECTIVE_ZOOM;
                }
                bi.b a = bi.b.a(aVar.w().f("animation"));
                if (a != null) {
                    bVar = a;
                }
                if (b.f()) {
                    bVar = bi.b.NONE;
                }
                aVar.n();
                bi.a(bVar, aVar, new bi.a(this) {
                    final /* synthetic */ e a;

                    {
                        this.a = r1;
                    }

                    public void a(a aVar) {
                        aVar.o();
                    }
                });
                if (aVar.e == d.INTERSTITIAL_VIDEO || aVar.e == d.INTERSTITIAL_REWARD_VIDEO) {
                    b.d().willDisplayVideo(aVar.d);
                }
                aVar.q().b().e(aVar);
                return;
            }
            return;
        }
        aVar.a(CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
    }

    public void b(final a aVar) {
        CBLogging.b("CBViewController", "###### dismiss impressiony");
        Runnable anonymousClass2 = new Runnable(this) {
            final /* synthetic */ e b;

            public void run() {
                aVar.b = b.DISMISSING;
                bi.b bVar = bi.b.PERSPECTIVE_ROTATE;
                if (aVar.c == c.MORE_APPS) {
                    bVar = bi.b.PERSPECTIVE_ZOOM;
                }
                bi.b a = bi.b.a(aVar.w().f("animation"));
                if (a != null) {
                    bVar = a;
                }
                if (b.f()) {
                    bVar = bi.b.NONE;
                }
                bi.b(bVar, aVar, new bi.a(this) {
                    final /* synthetic */ AnonymousClass2 a;

                    {
                        this.a = r1;
                    }

                    public void a(final a aVar) {
                        CBUtility.e().post(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 b;

                            public void run() {
                                this.b.a.b.d(aVar);
                            }
                        });
                        aVar.m();
                    }
                });
            }
        };
        if (aVar.l) {
            aVar.a(anonymousClass2);
        } else {
            anonymousClass2.run();
        }
    }

    private void f(a aVar) {
        Context b = Chartboost.b();
        if (b == null) {
            CBLogging.d(this, "No host activity to display loading view");
            return;
        }
        if (this.a == null) {
            this.a = new bq(b, aVar);
            b.addContentView(this.a, new LayoutParams(-1, -1));
        }
        this.a.b();
        this.b = aVar;
    }

    public void a(a aVar, boolean z) {
        if (aVar == null) {
            return;
        }
        if (aVar == this.b || aVar == c.a().c()) {
            this.b = null;
            CBLogging.b("CBViewController", "###### dismiss loading view");
            if (b()) {
                this.a.c();
                if (z && this.a != null && this.a.h() != null) {
                    d(this.a.h());
                }
            }
        }
    }

    public void c(a aVar) {
        CBLogging.b("CBViewController", "###### removing impression silently");
        if (b()) {
            a(aVar, false);
        }
        aVar.j();
        try {
            ((ViewGroup) this.a.getParent()).removeView(this.a);
        } catch (Throwable e) {
            CBLogging.b("CBViewController", "Exception removing impression silently", e);
        }
        this.a = null;
    }

    public void d(a aVar) {
        CBLogging.b("CBViewController", "###### removing impression ");
        aVar.b = b.NONE;
        if (this.a != null) {
            try {
                ((ViewGroup) this.a.getParent()).removeView(this.a);
            } catch (Throwable e) {
                CBLogging.b("CBViewController", "Exception removing impression ", e);
            }
            aVar.i();
            this.a = null;
            if (b.e()) {
                e();
            }
        } else if (b.e()) {
            e();
        }
    }

    private void e() {
        CBLogging.b("CBViewController", "###### Closeingimpression ");
        Activity b = Chartboost.b();
        if (b != null && (b instanceof CBImpressionActivity)) {
            Chartboost.c();
            b.finish();
        }
    }

    public boolean b() {
        return this.a != null && this.a.g();
    }

    public boolean c() {
        return this.a != null;
    }

    public bq d() {
        return this.a;
    }
}
