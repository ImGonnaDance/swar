package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.h;
import com.chartboost.sdk.Libraries.j;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.a.d;
import com.chartboost.sdk.f;
import com.com2us.peppermint.PeppermintConstant;
import com.facebook.widget.FacebookDialog;
import jp.co.dimage.android.o;

public class ai extends ah {
    protected j A;
    protected j B;
    protected j C;
    private boolean I;
    private boolean J;
    private boolean K;
    private boolean L;
    private boolean M;
    private boolean N;
    protected b j;
    protected int k;
    protected String l;
    protected String m;
    protected int n;
    protected int o;
    protected boolean p;
    protected boolean q;
    protected boolean r;
    protected boolean s;
    protected boolean t;
    protected int u;
    protected j v;
    protected j w;
    protected j x;
    protected j y;
    protected j z;

    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[b.values().length];

        static {
            try {
                a[b.REWARD_OFFER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.VIDEO_PLAYING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.POST_VIDEO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public class a extends com.chartboost.sdk.impl.ah.a {
        final /* synthetic */ ai e;
        private bl i;
        private ap j;
        private am k;
        private ag l;
        private ak m;
        private bl n;

        private a(final ai aiVar, Context context) {
            this.e = aiVar;
            super(aiVar, context);
            if (aiVar.e.e == d.INTERSTITIAL_REWARD_VIDEO) {
                this.k = new am(context, aiVar);
                this.k.setVisibility(8);
                addView(this.k);
            }
            this.j = new ap(context, aiVar);
            this.j.setVisibility(8);
            addView(this.j);
            this.l = new ag(context, aiVar);
            this.l.setVisibility(8);
            addView(this.l);
            if (aiVar.e.e == d.INTERSTITIAL_REWARD_VIDEO) {
                this.m = new ak(context, aiVar);
                this.m.setVisibility(8);
                addView(this.m);
            }
            this.i = new bl(this, getContext()) {
                final /* synthetic */ a b;

                protected void a(MotionEvent motionEvent) {
                    if (this.b.e.e.e == d.INTERSTITIAL_REWARD_VIDEO) {
                        this.b.m.a(false);
                    }
                    if (this.b.e.j == b.VIDEO_PLAYING) {
                        this.b.f(false);
                    }
                    com.chartboost.sdk.Tracking.a.c(this.b.e.m, this.b.e.e.p());
                    this.b.e(true);
                }
            };
            this.i.setVisibility(8);
            addView(this.i);
            this.n = new bl(this, getContext()) {
                final /* synthetic */ a b;

                protected void a(MotionEvent motionEvent) {
                    this.b.k();
                }
            };
            this.n.setVisibility(8);
            addView(this.n);
            if (aiVar.F.a("progress").c("background-color") && aiVar.F.a("progress").c("border-color") && aiVar.F.a("progress").c("progress-color") && aiVar.F.a("progress").c("radius")) {
                al c = this.j.c();
                c.a(f.a(aiVar.F.a("progress").e("background-color")));
                c.b(f.a(aiVar.F.a("progress").e("border-color")));
                c.c(f.a(aiVar.F.a("progress").e("progress-color")));
                c.b(aiVar.F.a("progress").a("radius").j());
            }
            if (aiVar.F.a("video-controls-background").c("color")) {
                this.j.a(f.a(aiVar.F.a("video-controls-background").e("color")));
            }
            if (aiVar.e.e == d.INTERSTITIAL_REWARD_VIDEO && aiVar.r) {
                this.l.a(aiVar.F.a("post-video-toaster").e("title"), aiVar.F.a("post-video-toaster").e("tagline"));
            }
            if (aiVar.e.e == d.INTERSTITIAL_REWARD_VIDEO && aiVar.q) {
                this.k.a(aiVar.F.a("confirmation").e("text"), f.a(aiVar.F.a("confirmation").e("color")));
            }
            if (aiVar.e.e == d.INTERSTITIAL_REWARD_VIDEO && aiVar.s) {
                this.m.a(aiVar.F.a("post-video-reward-toaster").a("position").equals("inside-top") ? com.chartboost.sdk.impl.an.a.TOP : com.chartboost.sdk.impl.an.a.BOTTOM);
                this.m.a(aiVar.F.a("post-video-reward-toaster").e("text"));
                if (aiVar.A.e()) {
                    this.m.a(aiVar.C);
                }
            }
            if (aiVar.d.a("video-click-button").b()) {
                this.j.d();
            }
            this.j.c(aiVar.F.i("video-progress-timer-enabled"));
            aiVar.m = aiVar.d.a(aiVar.a().b() ? "video-portrait" : "video-landscape").e(PeppermintConstant.JSON_KEY_ID);
            if (TextUtils.isEmpty(aiVar.m)) {
                aiVar.a(CBImpressionError.INTERNAL);
                return;
            }
            if (aiVar.l == null) {
                aiVar.l = be.a(aiVar.m);
            }
            if (aiVar.l == null) {
                aiVar.a(CBImpressionError.INTERNAL);
            } else {
                this.j.a(aiVar.l);
            }
        }

        public int d() {
            return this.e.o;
        }

        public int e() {
            return this.e.n;
        }

        protected void f() {
            super.f();
            if (this.e.j != b.REWARD_OFFER || (this.e.q && !this.e.m())) {
                a(this.e.j, false);
            } else {
                e(false);
            }
        }

        public void g() {
            f(true);
            this.j.h();
            ai aiVar = this.e;
            aiVar.k++;
            if (this.e.k <= 1 && !this.e.N) {
                this.e.s();
                this.e.e.f();
            }
        }

        public boolean h() {
            return this.e.L;
        }

        public void b(boolean z) {
            this.e.L = z;
        }

        public boolean i() {
            return this.e.M;
        }

        public void c(boolean z) {
            this.e.M = z;
        }

        protected void a(int i, int i2) {
            super.a(i, i2);
            a(this.e.j, false);
            boolean b = this.e.a().b();
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
            LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, -1);
            LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-1, -1);
            RelativeLayout.LayoutParams layoutParams5 = (RelativeLayout.LayoutParams) this.f.getLayoutParams();
            this.e.a(layoutParams, b ? this.e.w : this.e.v, 1.0f);
            Point b2 = this.e.b(b ? "replay-portrait" : "replay-landscape");
            int round = Math.round(((((float) layoutParams5.leftMargin) + (((float) layoutParams5.width) / 2.0f)) + ((float) b2.x)) - (((float) layoutParams.width) / 2.0f));
            int round2 = Math.round((((((float) layoutParams5.height) / 2.0f) + ((float) layoutParams5.topMargin)) + ((float) b2.y)) - (((float) layoutParams.height) / 2.0f));
            layoutParams.leftMargin = Math.min(Math.max(0, round), i - layoutParams.width);
            layoutParams.topMargin = Math.min(Math.max(0, round2), i2 - layoutParams.height);
            this.i.bringToFront();
            if (b) {
                this.i.a(this.e.w);
            } else {
                this.i.a(this.e.v);
            }
            layoutParams5 = (RelativeLayout.LayoutParams) this.b.getLayoutParams();
            if (this.e.r()) {
                LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(-2, -2);
                j jVar = b ? this.e.D : this.e.E;
                this.e.a(layoutParams6, jVar, 1.0f);
                layoutParams6.leftMargin = 0;
                layoutParams6.topMargin = 0;
                layoutParams6.addRule(11);
                this.n.setLayoutParams(layoutParams6);
                this.n.a(jVar);
            } else {
                layoutParams2.width = layoutParams5.width;
                layoutParams2.height = layoutParams5.height;
                layoutParams2.leftMargin = layoutParams5.leftMargin;
                layoutParams2.topMargin = layoutParams5.topMargin;
                layoutParams3.width = layoutParams5.width;
                layoutParams3.height = layoutParams5.height;
                layoutParams3.leftMargin = layoutParams5.leftMargin;
                layoutParams3.topMargin = layoutParams5.topMargin;
            }
            layoutParams4.width = layoutParams5.width;
            layoutParams4.height = 72;
            layoutParams4.leftMargin = layoutParams5.leftMargin;
            layoutParams4.topMargin = (layoutParams5.height + layoutParams5.topMargin) - 72;
            if (this.e.e.e == d.INTERSTITIAL_REWARD_VIDEO) {
                this.k.setLayoutParams(layoutParams2);
            }
            this.j.setLayoutParams(layoutParams3);
            this.l.setLayoutParams(layoutParams4);
            this.i.setLayoutParams(layoutParams);
            if (this.e.e.e == d.INTERSTITIAL_REWARD_VIDEO) {
                this.k.a();
            }
            this.j.a();
        }

        private void e(boolean z) {
            if (this.e.j != b.VIDEO_PLAYING) {
                if (this.e.q) {
                    com.chartboost.sdk.Tracking.a.b("integrated", this.e.G);
                    a(b.REWARD_OFFER, z);
                    return;
                }
                a(b.VIDEO_PLAYING, z);
                if (this.e.k >= 1 || !this.e.F.a("timer").c("delay")) {
                    this.j.a(!this.e.p);
                } else {
                    String str = "InterstitialVideoViewProtocol";
                    String str2 = "controls starting %s, setting timer";
                    Object[] objArr = new Object[1];
                    objArr[0] = this.e.p ? "visible" : "hidden";
                    CBLogging.c(str, String.format(str2, objArr));
                    this.j.a(this.e.p);
                    this.e.a(this.j, new Runnable(this) {
                        final /* synthetic */ a a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            boolean z;
                            String str = "InterstitialVideoViewProtocol";
                            String str2 = "controls %s automatically from timer";
                            Object[] objArr = new Object[1];
                            objArr[0] = this.a.e.p ? "hidden" : "shown";
                            CBLogging.c(str, String.format(str2, objArr));
                            ap b = this.a.j;
                            if (this.a.e.p) {
                                z = false;
                            } else {
                                z = true;
                            }
                            b.a(z, true);
                            this.a.e.g.remove(Integer.valueOf(this.a.j.hashCode()));
                        }
                    }, Math.round(1000.0d * this.e.F.a("timer").g("delay")));
                }
                com.chartboost.sdk.Tracking.a.a(this.e.m, this.e.G, this.e.k);
                this.j.e();
                if (this.e.k <= 1) {
                    this.e.e.g();
                }
            }
        }

        private void f(boolean z) {
            this.j.f();
            com.chartboost.sdk.Tracking.a.d(this.e.m, this.e.G);
            if (this.e.j == b.VIDEO_PLAYING && z) {
                if (this.e.k < 1 && this.e.F.c("post-video-reward-toaster") && this.e.s && this.e.A.e() && this.e.B.e()) {
                    g(true);
                }
                a(b.POST_VIDEO, true);
                if (CBUtility.c().b()) {
                    requestLayout();
                }
            }
        }

        private void g(boolean z) {
            if (z) {
                this.m.a(true);
            } else {
                this.m.setVisibility(0);
            }
            f.a.postDelayed(new Runnable(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.m.a(false);
                }
            }, 2500);
        }

        private void a(b bVar, boolean z) {
            boolean z2;
            boolean z3 = true;
            this.e.j = bVar;
            switch (AnonymousClass2.a[bVar.ordinal()]) {
                case o.a /*1*/:
                    this.e.a(!this.e.r(), this.b, z);
                    if (this.e.e.e == d.INTERSTITIAL_REWARD_VIDEO) {
                        this.e.a(true, this.k, z);
                    }
                    this.e.a(false, this.j, z);
                    this.e.a(false, this.i, z);
                    this.e.a(false, this.l, z);
                    this.b.setEnabled(false);
                    this.i.setEnabled(false);
                    this.j.setEnabled(false);
                    break;
                case o.b /*2*/:
                    this.e.a(false, this.b, z);
                    if (this.e.e.e == d.INTERSTITIAL_REWARD_VIDEO) {
                        this.e.a(false, this.k, z);
                    }
                    this.e.a(true, this.j, z);
                    this.e.a(false, this.i, z);
                    this.e.a(false, this.l, z);
                    this.b.setEnabled(true);
                    this.i.setEnabled(false);
                    this.j.setEnabled(true);
                    break;
                case o.c /*3*/:
                    this.e.a(true, this.b, z);
                    if (this.e.e.e == d.INTERSTITIAL_REWARD_VIDEO) {
                        this.e.a(false, this.k, z);
                    }
                    this.e.a(false, this.j, z);
                    this.e.a(true, this.i, z);
                    z2 = this.e.B.e() && this.e.A.e() && this.e.r;
                    this.e.a(z2, this.l, z);
                    this.i.setEnabled(true);
                    this.b.setEnabled(true);
                    this.j.setEnabled(false);
                    if (this.e.t) {
                        g(false);
                        break;
                    }
                    break;
            }
            z2 = j();
            View d = d(true);
            d.setEnabled(z2);
            this.e.a(z2, d, z);
            View d2 = d(false);
            d2.setEnabled(false);
            this.e.a(false, d2, z);
            this.e.a(!this.e.r(), this.c, z);
            ai aiVar = this.e;
            if (this.e.r()) {
                z2 = false;
            } else {
                z2 = true;
            }
            aiVar.a(z2, this.f, z);
            if (bVar == b.REWARD_OFFER) {
                z3 = false;
            }
            a(z3);
        }

        protected boolean j() {
            if (this.e.j == b.VIDEO_PLAYING && this.e.k < 1) {
                float a = this.e.d.a("close-" + (this.e.a().b() ? "portrait" : "landscape")).a("delay").a(-1.0f);
                int round = a >= 0.0f ? Math.round(a * 1000.0f) : -1;
                this.e.u = round;
                if (round < 0) {
                    return false;
                }
                if (round > this.j.b().d()) {
                    return false;
                }
            }
            return true;
        }

        public void b() {
            this.e.l();
            super.b();
        }

        protected void k() {
            if (this.e.j == b.VIDEO_PLAYING && this.e.F.a("cancel-popup").c("title") && this.e.F.a("cancel-popup").c("text") && this.e.F.a("cancel-popup").c(FacebookDialog.COMPLETION_GESTURE_CANCEL) && this.e.F.a("cancel-popup").c("confirm")) {
                this.j.g();
                if (this.e.k < 1) {
                    this.e.n();
                    return;
                }
            }
            if (this.e.j == b.VIDEO_PLAYING) {
                f(false);
                this.j.h();
                if (this.e.k < 1) {
                    ai aiVar = this.e;
                    aiVar.k++;
                    this.e.s();
                    this.e.e.f();
                }
            } else {
                this.e.L = true;
                this.e.M = true;
            }
            f.a.post(new Runnable(this) {
                final /* synthetic */ a a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.e.h();
                }
            });
            com.chartboost.sdk.Tracking.a.b(this.e.m, this.e.G, this.e.u);
        }

        protected void a(float f, float f2) {
            if ((!this.e.p || this.e.j != b.VIDEO_PLAYING) && this.e.j != b.REWARD_OFFER) {
                l();
                com.chartboost.sdk.Tracking.a.a("insterstitial", this.e.m, this.e.e.p(), (int) f, (int) f2);
            }
        }

        protected void l() {
            if (this.e.j == b.VIDEO_PLAYING) {
                f(false);
            }
            this.e.a(null, null);
        }

        protected void m() {
            com.chartboost.sdk.Tracking.a.d("integrated", this.e.G, true);
            this.e.q = false;
            e(true);
        }

        public void n() {
            this.e.N = true;
            be.b(this.e.m);
            this.e.a(CBImpressionError.INTERNAL);
        }

        public bl d(boolean z) {
            return (!(this.e.r() && z) && (this.e.r() || z)) ? this.g : this.n;
        }
    }

    protected enum b {
        REWARD_OFFER,
        VIDEO_PLAYING,
        POST_VIDEO
    }

    public /* synthetic */ com.chartboost.sdk.f.a e() {
        return p();
    }

    public ai(com.chartboost.sdk.Model.a aVar) {
        super(aVar);
        this.j = b.REWARD_OFFER;
        this.I = true;
        this.J = false;
        this.K = false;
        this.n = 0;
        this.o = 0;
        this.L = false;
        this.M = false;
        this.N = false;
        this.t = false;
        this.u = 0;
        this.j = b.REWARD_OFFER;
        this.v = new j(this);
        this.w = new j(this);
        this.x = new j(this);
        this.y = new j(this);
        this.z = new j(this);
        this.A = new j(this);
        this.B = new j(this);
        this.C = new j(this);
        this.k = 0;
    }

    public boolean m() {
        return this.e.e == d.INTERSTITIAL_VIDEO;
    }

    public void n() {
        com.chartboost.sdk.impl.bm.a aVar = new com.chartboost.sdk.impl.bm.a();
        aVar.a(this.F.a("cancel-popup").e("title")).b(this.F.a("cancel-popup").e("text")).d(this.F.a("cancel-popup").e("confirm")).c(this.F.a("cancel-popup").e(FacebookDialog.COMPLETION_GESTURE_CANCEL));
        aVar.a(p().getContext(), new com.chartboost.sdk.impl.bm.b(this) {
            final /* synthetic */ ai a;

            {
                this.a = r1;
            }

            public void a(bm bmVar) {
                a p = this.a.p();
                if (p != null) {
                    p.j.e();
                }
            }

            public void a(bm bmVar, int i) {
                a p = this.a.p();
                if (i != 1) {
                    if (p != null) {
                        p.f(false);
                        p.j.h();
                    }
                    this.a.h();
                } else if (p != null) {
                    p.j.e();
                }
            }
        });
    }

    protected com.chartboost.sdk.f.a b(Context context) {
        return new a(context);
    }

    public boolean j() {
        if (!(p().d(true).getVisibility() == 4 || p().d(true).getVisibility() == 8)) {
            p().k();
        }
        return true;
    }

    public void k() {
        super.k();
        if (this.j == b.VIDEO_PLAYING && this.J) {
            p().j.b().a(this.n);
            if (!this.K) {
                p().j.e();
            }
        }
        this.K = false;
        this.J = false;
    }

    public void l() {
        super.l();
        if (this.j == b.VIDEO_PLAYING && !this.J) {
            if (!p().j.i()) {
                this.K = true;
            }
            this.J = true;
            p().j.g();
            if (this.k < 1) {
                t();
            }
        }
    }

    private void s() {
        h.c().b(Integer.toString(this.e.hashCode()));
    }

    private void t() {
        ba baVar = new ba("/api/video-complete");
        baVar.a("location", this.e.d);
        baVar.a("reward", this.e.w().e("reward"));
        baVar.a("currency-name", this.e.w().e("currency-name"));
        baVar.a("ad_id", this.e.p());
        float f = (float) this.n;
        float f2 = (float) this.o;
        baVar.a("total_time", Float.valueOf(f2 / 1000.0f));
        if (this.n < 0) {
            baVar.a("playback_time", Float.valueOf(f2 / 1000.0f));
        } else {
            baVar.a("playback_time", Float.valueOf(f / 1000.0f));
        }
        h.c().a(Integer.toString(this.e.hashCode()), baVar.t());
    }

    public boolean a(com.chartboost.sdk.Libraries.e.a aVar) {
        if (!super.a(aVar)) {
            return false;
        }
        if (this.d.b("video-landscape") || this.d.b("replay-landscape")) {
            this.i = false;
        }
        this.v.a("replay-landscape");
        this.w.a("replay-portrait");
        this.z.a("video-click-button");
        this.A.a("post-video-reward-icon");
        this.B.a("post-video-button");
        this.x.a("video-confirmation-button");
        this.y.a("video-confirmation-icon");
        this.C.a("post-video-reward-icon");
        this.p = aVar.a("ux").i("video-controls-togglable");
        if (this.e.e == d.INTERSTITIAL_REWARD_VIDEO && this.F.a("post-video-toaster").c("title") && this.F.a("post-video-toaster").c("tagline")) {
            this.r = true;
        }
        if (this.e.e == d.INTERSTITIAL_REWARD_VIDEO && this.F.a("confirmation").c("text") && this.F.a("confirmation").c("color")) {
            this.q = true;
        }
        if (this.e.e == d.INTERSTITIAL_REWARD_VIDEO && this.F.c("post-video-reward-toaster")) {
            this.s = true;
        }
        return true;
    }

    protected void i() {
        if (this.q && !(this.x.e() && this.y.e())) {
            this.q = false;
        }
        if (this.I) {
            super.i();
        } else {
            a(CBImpressionError.INTERNAL);
        }
    }

    public void d() {
        super.d();
        this.v.d();
        this.w.d();
        this.z.d();
        this.A.d();
        this.B.d();
        this.x.d();
        this.y.d();
        this.C.d();
        this.v = null;
        this.w = null;
        this.z = null;
        this.A = null;
        this.B = null;
        this.x = null;
        this.y = null;
        this.C = null;
    }

    public boolean o() {
        return this.j == b.VIDEO_PLAYING;
    }

    public a p() {
        return (a) super.e();
    }

    protected void q() {
        this.e.r();
    }

    protected boolean r() {
        return CBUtility.c().b() && this.j != b.POST_VIDEO;
    }
}
