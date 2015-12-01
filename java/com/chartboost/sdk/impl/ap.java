package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.e;
import com.chartboost.sdk.Libraries.j;
import com.chartboost.sdk.impl.ai.a;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;

public final class ap extends RelativeLayout implements OnCompletionListener, OnErrorListener, OnPreparedListener {
    private static final CharSequence a = "00:00";
    private static Handler l = CBUtility.e();
    private RelativeLayout b;
    private ao c;
    private ao d;
    private bl e;
    private TextView f;
    private al g;
    private bh h;
    private ai i;
    private boolean j = false;
    private boolean k = false;
    private Runnable m = new Runnable(this) {
        final /* synthetic */ ap a;

        {
            this.a = r1;
        }

        public void run() {
            this.a.d(false);
        }
    };
    private Runnable n = new Runnable(this) {
        final /* synthetic */ ap a;

        {
            this.a = r1;
        }

        public void run() {
            if (this.a.c != null) {
                this.a.c.setVisibility(8);
            }
            this.a.g.setVisibility(8);
            this.a.d.setVisibility(8);
            if (this.a.e != null) {
                this.a.e.setEnabled(false);
            }
        }
    };
    private Runnable o = new Runnable(this) {
        final /* synthetic */ ap a;
        private int b = 0;

        {
            this.a = r2;
        }

        public void run() {
            if (this.a.h.b().e()) {
                int d = this.a.h.b().d();
                if (d > 0) {
                    this.a.i.n = d;
                    if (d / UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS >= 1 && !this.a.i.p().i()) {
                        this.a.i.q();
                        this.a.i.p().c(true);
                    }
                }
                this.a.g.a(((float) d) / ((float) this.a.h.b().c()));
                d /= UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS;
                if (this.b != d) {
                    this.b = d;
                    int i = d / 60;
                    d %= 60;
                    this.a.f.setText(String.format("%02d:%02d", new Object[]{Integer.valueOf(i), Integer.valueOf(d)}));
                }
            }
            a p = this.a.i.p();
            if (p.j()) {
                View d2 = p.d(true);
                if (d2.getVisibility() == 8) {
                    this.a.i.a(true, d2);
                    d2.setEnabled(true);
                }
            }
            ap.l.removeCallbacks(this.a.o);
            ap.l.postDelayed(this.a.o, 16);
        }
    };

    public ap(Context context, ai aiVar) {
        super(context);
        this.i = aiVar;
        a(context);
    }

    private void a(Context context) {
        LayoutParams layoutParams;
        Context context2 = getContext();
        e.a g = this.i.g();
        float f = getContext().getResources().getDisplayMetrics().density;
        int round = Math.round(f * 10.0f);
        this.h = new bh(context2);
        this.i.p().a(this.h);
        LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams2.addRule(13);
        addView(this.h, layoutParams2);
        this.b = new RelativeLayout(context2);
        if (g.c() && g.a("video-click-button").c()) {
            this.c = new ao(context2);
            this.c.setVisibility(8);
            this.e = new bl(this, context2) {
                final /* synthetic */ ap a;

                protected void a(MotionEvent motionEvent) {
                    this.a.i.a(null, e.a(e.a("paused", Integer.valueOf(1))));
                    com.chartboost.sdk.Tracking.a.a("install-button", this.a.i.m, this.a.i.G, Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
                }
            };
            this.e.a(ScaleType.FIT_CENTER);
            j jVar = this.i.z;
            Point b = this.i.b("video-click-button");
            LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
            layoutParams3.leftMargin = Math.round(((float) b.x) / jVar.g());
            layoutParams3.topMargin = Math.round(((float) b.y) / jVar.g());
            this.i.a(layoutParams3, jVar, 1.0f);
            this.e.a(jVar);
            this.c.addView(this.e, layoutParams3);
            layoutParams = new RelativeLayout.LayoutParams(-1, Math.round(((float) layoutParams3.height) + (10.0f * f)));
            layoutParams.addRule(10);
            this.b.addView(this.c, layoutParams);
        }
        this.d = new ao(context2);
        this.d.setVisibility(8);
        layoutParams = new RelativeLayout.LayoutParams(-1, Math.round(32.5f * f));
        layoutParams.addRule(12);
        this.b.addView(this.d, layoutParams);
        this.d.setGravity(16);
        this.d.setPadding(round, round, round, round);
        this.f = new TextView(context2);
        this.f.setTextColor(-1);
        this.f.setTextSize(2, 11.0f);
        this.f.setText(a);
        this.f.setPadding(0, 0, round, 0);
        this.f.setSingleLine();
        this.f.measure(0, 0);
        int measuredWidth = this.f.getMeasuredWidth();
        this.f.setGravity(17);
        this.d.addView(this.f, new LinearLayout.LayoutParams(measuredWidth, -1));
        this.g = new al(context2);
        this.g.setVisibility(8);
        LayoutParams layoutParams4 = new LinearLayout.LayoutParams(-1, Math.round(10.0f * f));
        layoutParams4.setMargins(0, CBUtility.a(1, getContext()), 0, 0);
        this.d.addView(this.g, layoutParams4);
        layoutParams4 = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams4.addRule(6, this.h.getId());
        layoutParams4.addRule(8, this.h.getId());
        layoutParams4.addRule(5, this.h.getId());
        layoutParams4.addRule(7, this.h.getId());
        addView(this.b, layoutParams4);
        a();
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (this.e != null) {
            this.e.setEnabled(enabled);
        }
        if (enabled) {
            a(false);
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        l.removeCallbacks(this.o);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent e) {
        if (!this.h.b().e() || e.getActionMasked() != 0) {
            return false;
        }
        if (this.i != null) {
            com.chartboost.sdk.Tracking.a.e(this.i.m, this.i.G, this.j);
        }
        d(true);
        return true;
    }

    public void onCompletion(MediaPlayer arg0) {
        this.i.n = this.h.b().c();
        if (this.i.p() != null) {
            this.i.p().g();
        }
    }

    public void onPrepared(MediaPlayer mp) {
        this.i.o = this.h.b().c();
        this.i.p().a(true);
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        this.i.p().n();
        return false;
    }

    private void d(boolean z) {
        a(!this.j, z);
    }

    protected void a(boolean z, boolean z2) {
        l.removeCallbacks(this.m);
        l.removeCallbacks(this.n);
        if (this.i.p && this.i.o() && z != this.j) {
            this.j = z;
            Animation alphaAnimation = this.j ? new AlphaAnimation(0.0f, 1.0f) : new AlphaAnimation(1.0f, 0.0f);
            alphaAnimation.setDuration(z2 ? 100 : 200);
            alphaAnimation.setFillAfter(true);
            if (!(this.k || this.c == null)) {
                this.c.setVisibility(0);
                this.c.startAnimation(alphaAnimation);
                if (this.e != null) {
                    this.e.setEnabled(true);
                }
            }
            this.g.setVisibility(0);
            this.d.setVisibility(0);
            this.d.startAnimation(alphaAnimation);
            if (this.j) {
                l.postDelayed(this.m, 3000);
            } else {
                l.postDelayed(this.n, alphaAnimation.getDuration());
            }
        }
    }

    public void a(boolean z) {
        l.removeCallbacks(this.m);
        l.removeCallbacks(this.n);
        if (z) {
            if (!(this.k || this.c == null)) {
                this.c.setVisibility(0);
            }
            this.g.setVisibility(0);
            this.d.setVisibility(0);
            if (this.e != null) {
                this.e.setEnabled(true);
            }
        } else {
            if (this.c != null) {
                this.c.clearAnimation();
                this.c.setVisibility(8);
            }
            this.d.clearAnimation();
            this.g.setVisibility(8);
            this.d.setVisibility(8);
            if (this.e != null) {
                this.e.setEnabled(false);
            }
        }
        this.j = z;
    }

    public void b(boolean z) {
        setBackgroundColor(z ? -16777216 : 0);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        if (!z) {
            layoutParams.addRule(6, this.h.getId());
            layoutParams.addRule(8, this.h.getId());
            layoutParams.addRule(5, this.h.getId());
            layoutParams.addRule(7, this.h.getId());
        }
        this.b.setLayoutParams(layoutParams);
        if (this.c != null) {
            this.c.setGravity((z ? 3 : 5) | 16);
            this.c.requestLayout();
        }
    }

    public void a() {
        b(CBUtility.c().b());
    }

    public bh.a b() {
        return this.h.b();
    }

    public al c() {
        return this.g;
    }

    public void a(int i) {
        if (this.c != null) {
            this.c.setBackgroundColor(i);
        }
        this.d.setBackgroundColor(i);
    }

    public void d() {
        if (this.c != null) {
            this.c.setVisibility(8);
        }
        this.k = true;
        if (this.e != null) {
            this.e.setEnabled(false);
        }
    }

    public void c(boolean z) {
        this.f.setVisibility(z ? 0 : 8);
    }

    public void a(String str) {
        this.h.b().a((OnCompletionListener) this);
        this.h.b().a((OnErrorListener) this);
        this.h.b().a((OnPreparedListener) this);
        this.h.b().a(Uri.parse(str));
    }

    public void e() {
        if (!this.h.a()) {
            l.postDelayed(new Runnable(this) {
                final /* synthetic */ ap a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.h.setVisibility(0);
                }
            }, 510);
        }
        this.h.b().a();
        l.removeCallbacks(this.o);
        l.postDelayed(this.o, 16);
    }

    public void f() {
        if (this.h.b().e()) {
            this.i.n = this.h.b().d();
            this.h.b().b();
        }
        if (this.i.p().b.getVisibility() == 0) {
            this.i.p().b.postInvalidate();
        }
        l.removeCallbacks(this.o);
    }

    public void g() {
        if (this.h.b().e()) {
            this.i.n = this.h.b().d();
        }
        this.h.b().b();
        l.removeCallbacks(this.o);
    }

    public void h() {
        if (!this.h.a()) {
            this.h.setVisibility(8);
            invalidate();
        }
    }

    public boolean i() {
        return this.h.b().e();
    }
}
