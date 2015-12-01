package com.chartboost.sdk;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.a.d;
import com.chartboost.sdk.impl.ba;
import com.chartboost.sdk.impl.bc;
import com.chartboost.sdk.impl.bq;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;
import com.com2us.peppermint.PeppermintConstant;
import jp.co.dimage.android.o;

public final class c {
    private static final String c = c.class.getSimpleName();
    private static c d;
    public com.chartboost.sdk.Model.a.a a = new com.chartboost.sdk.Model.a.a(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void a(com.chartboost.sdk.Model.a aVar) {
            synchronized (this.a) {
                boolean z = aVar.f;
            }
            if (aVar.b == com.chartboost.sdk.Model.a.b.LOADING) {
                aVar.b = com.chartboost.sdk.Model.a.b.LOADED;
                if (z) {
                    aVar.q().a(aVar);
                }
            }
            CBLogging.c(c.c, "####### impressionReadyToBeDisplayed");
            if (!z) {
                aVar.q().g(aVar);
            }
            aVar.q().o(aVar);
        }

        public void b(com.chartboost.sdk.Model.a aVar) {
            aVar.q().b().c(aVar);
            aVar.q().b().b(aVar);
            if (aVar.b == com.chartboost.sdk.Model.a.b.DISPLAYED) {
                e d = Chartboost.d();
                if (d != null) {
                    d.b(aVar);
                }
            }
            com.chartboost.sdk.Tracking.a.c(aVar.q().e(), aVar.d, aVar.p());
        }

        public void a(com.chartboost.sdk.Model.a aVar, String str, com.chartboost.sdk.Libraries.e.a aVar2) {
            aVar.q().b().a(aVar);
            if (aVar.a()) {
                aVar.q().b().c(aVar);
                if (aVar.b == com.chartboost.sdk.Model.a.b.DISPLAYED) {
                    e d = Chartboost.d();
                    if (d != null) {
                        d.b(aVar);
                    }
                }
            }
            if (!TextUtils.isEmpty(str)) {
                ba d2 = this.a.d();
                d2.a("to", aVar.w());
                d2.a("cgn", aVar.w());
                d2.a("creative", aVar.w());
                d2.a("ad_id", aVar.w());
                d2.a("cgn", aVar2);
                d2.a("creative", aVar2);
                d2.a(PeppermintConstant.JSON_KEY_TYPE, aVar2);
                d2.a("more_type", aVar2);
                if (aVar.d()) {
                    d2.a("retarget_reinstall", Boolean.valueOf(aVar.e()));
                }
                if (aVar.e == d.INTERSTITIAL_VIDEO || aVar.e == d.INTERSTITIAL_REWARD_VIDEO) {
                    com.chartboost.sdk.impl.ai.a aVar3;
                    if (aVar.l() != null) {
                        aVar3 = (com.chartboost.sdk.impl.ai.a) aVar.l();
                    } else {
                        aVar3 = null;
                    }
                    if (aVar3 != null) {
                        d2.a("total_time", Integer.valueOf(aVar3.d() / UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS));
                        d2.a("playback_time", Integer.valueOf(aVar3.e() / UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS));
                    }
                }
                aVar.o = d2;
                this.a.b(aVar, str, null);
            } else {
                this.a.b.a(aVar, false, str, CBClickError.URI_INVALID, null);
            }
            com.chartboost.sdk.Tracking.a.b(aVar.q().e(), aVar.d, aVar.p());
        }

        public void a(com.chartboost.sdk.Model.a aVar, CBImpressionError cBImpressionError) {
            aVar.q().a(aVar, cBImpressionError);
        }

        public void c(com.chartboost.sdk.Model.a aVar) {
            aVar.m = true;
            if (aVar.c == com.chartboost.sdk.Model.a.c.REWARDED_VIDEO && b.d() != null) {
                b.d().didCompleteRewardedVideo(aVar.d, aVar.w().f("reward"));
            }
            c.b(aVar);
        }

        public void d(com.chartboost.sdk.Model.a aVar) {
            aVar.n = true;
        }
    };
    public com.chartboost.sdk.impl.bc.a b = new com.chartboost.sdk.impl.bc.a(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void a(com.chartboost.sdk.Model.a aVar, boolean z, String str, CBClickError cBClickError, b bVar) {
            if (aVar != null) {
                aVar.p = false;
                if (aVar.a()) {
                    aVar.b = com.chartboost.sdk.Model.a.b.DISMISSING;
                }
            }
            if (z) {
                if (aVar != null && aVar.o != null) {
                    aVar.o.a(true);
                    aVar.o.s();
                } else if (bVar != null) {
                    bVar.a();
                }
            } else if (b.d() != null) {
                b.d().didFailToRecordClick(str, cBClickError);
            }
        }
    };
    private bc e = bc.a(this.b);

    public interface b {
        void a();
    }

    public interface a {
        void a(boolean z);
    }

    static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] a = new int[com.chartboost.sdk.Model.a.b.values().length];

        static {
            try {
                a[com.chartboost.sdk.Model.a.b.LOADING.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[com.chartboost.sdk.Model.a.b.CACHED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[com.chartboost.sdk.Model.a.b.LOADED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[com.chartboost.sdk.Model.a.b.DISPLAYED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private c() {
    }

    public static c a() {
        if (d == null) {
            d = new c();
        }
        return d;
    }

    public final void a(com.chartboost.sdk.Model.a aVar, String str, b bVar) {
        this.e.a(aVar, str, Chartboost.getHostActivity(), bVar);
    }

    public final void b(final com.chartboost.sdk.Model.a aVar, final String str, final b bVar) {
        b.c = new a(this) {
            final /* synthetic */ c d;

            public void a(final boolean z) {
                Chartboost.a(new Runnable(this) {
                    final /* synthetic */ AnonymousClass1 b;

                    public void run() {
                        if (z) {
                            this.b.d.a(aVar, str, bVar);
                        } else {
                            this.b.d.b.a(aVar, false, str, CBClickError.AGE_GATE_FAILURE, bVar);
                        }
                    }
                });
            }
        };
        if (!b.p()) {
            a(aVar, str, bVar);
        } else if (b.d() != null) {
            b.d().didPauseClickForConfirmation();
            if (aVar != null) {
                aVar.p = false;
            }
        }
    }

    protected final boolean b() {
        if (c() == null) {
            return false;
        }
        this.a.b(c());
        return true;
    }

    private static synchronized void b(com.chartboost.sdk.Model.a aVar) {
        synchronized (c.class) {
            com.chartboost.sdk.impl.ai.a aVar2 = null;
            if (aVar.l() != null) {
                aVar2 = (com.chartboost.sdk.impl.ai.a) aVar.l();
            }
            if (aVar2 == null || !aVar2.h()) {
                ba baVar = new ba("/api/video-complete");
                baVar.a("location", aVar.d);
                baVar.a("reward", aVar.w().e("reward"));
                baVar.a("currency-name", aVar.w().e("currency-name"));
                baVar.a("ad_id", aVar.p());
                if (aVar2 != null) {
                    float e = (float) aVar2.e();
                    float d = (float) aVar2.d();
                    CBLogging.a(aVar.q().getClass().getSimpleName(), String.format("TotalDuration: %f PlaybackTime: %f", new Object[]{Float.valueOf(d), Float.valueOf(e)}));
                    baVar.a("total_time", Float.valueOf(d / 1000.0f));
                    if (e < 0.0f) {
                        baVar.a("playback_time", Float.valueOf(d / 1000.0f));
                    } else {
                        baVar.a("playback_time", Float.valueOf(e / 1000.0f));
                    }
                }
                aVar2.b(true);
                baVar.a(true);
                baVar.s();
            }
        }
    }

    protected final com.chartboost.sdk.Model.a c() {
        e d = Chartboost.d();
        bq d2 = d == null ? null : d.d();
        if (d2 == null) {
            return null;
        }
        return d2.h();
    }

    public ba d() {
        ba baVar = new ba("/api/click");
        Context b = Chartboost.b();
        if (b == null) {
            b = Chartboost.getValidContext();
        }
        baVar.b(b);
        return baVar;
    }

    public final void a(Activity activity, com.chartboost.sdk.Model.a aVar) {
        if (aVar != null) {
            switch (AnonymousClass4.a[aVar.b.ordinal()]) {
                case o.a /*1*/:
                    if (aVar.j) {
                        Chartboost.a(aVar);
                        return;
                    }
                    return;
                case o.b /*2*/:
                case o.c /*3*/:
                    Chartboost.a(aVar);
                    return;
                case o.d /*4*/:
                    if (!aVar.h()) {
                        e d = Chartboost.d();
                        if (d != null) {
                            CBLogging.b(c, "Error onActivityStart " + aVar.b.name());
                            d.d(aVar);
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }
}
