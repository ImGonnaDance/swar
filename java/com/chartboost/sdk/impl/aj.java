package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;
import com.chartboost.sdk.Libraries.j;
import com.chartboost.sdk.f;

public abstract class aj extends f {
    protected j D = new j(this);
    protected j E = new j(this);
    protected com.chartboost.sdk.Libraries.e.a F;
    protected String G;
    protected float H = 1.0f;
    private j j = new j(this);
    private j k = new j(this);

    public abstract class a extends com.chartboost.sdk.f.a {
        private boolean b = false;
        protected bk f;
        protected bl g;
        final /* synthetic */ aj h;

        protected a(aj ajVar, Context context) {
            this.h = ajVar;
            super(ajVar, context);
            setBackgroundColor(0);
            setLayoutParams(new LayoutParams(-1, -1));
            this.f = new bk(context);
            addView(this.f, new LayoutParams(-1, -1));
        }

        protected void f() {
            this.g = new bl(this, getContext()) {
                final /* synthetic */ a a;

                protected void a(MotionEvent motionEvent) {
                    this.a.k();
                }
            };
            addView(this.g);
        }

        protected void a(int i, int i2) {
            j jVar;
            int round;
            int round2;
            if (!this.b) {
                f();
                this.b = true;
            }
            boolean b = this.h.a().b();
            j a = b ? this.h.j : this.h.k;
            if (b) {
                jVar = this.h.D;
            } else {
                jVar = this.h.E;
            }
            ViewGroup.LayoutParams layoutParams = new LayoutParams(-2, -2);
            ViewGroup.LayoutParams layoutParams2 = new LayoutParams(-2, -2);
            this.h.a(layoutParams, a, 1.0f);
            this.h.H = Math.min(Math.min(((float) i) / ((float) layoutParams.width), ((float) i2) / ((float) layoutParams.height)), 1.0f);
            layoutParams.width = (int) (((float) layoutParams.width) * this.h.H);
            layoutParams.height = (int) (((float) layoutParams.height) * this.h.H);
            Point b2 = this.h.b(b ? "frame-portrait" : "frame-landscape");
            layoutParams.leftMargin = Math.round((((float) (i - layoutParams.width)) / 2.0f) + ((((float) b2.x) / a.g()) * this.h.H));
            layoutParams.topMargin = Math.round(((((float) b2.y) / a.g()) * this.h.H) + (((float) (i2 - layoutParams.height)) / 2.0f));
            this.h.a(layoutParams2, jVar, 1.0f);
            b2 = this.h.b(b ? "close-portrait" : "close-landscape");
            if (b2.x == 0 && b2.y == 0) {
                round = Math.round(((float) (-layoutParams2.width)) / 2.0f) + (layoutParams.leftMargin + layoutParams.width);
                round2 = layoutParams.topMargin + Math.round(((float) (-layoutParams2.height)) / 2.0f);
            } else {
                round = Math.round(((((float) layoutParams.leftMargin) + (((float) layoutParams.width) / 2.0f)) + ((float) b2.x)) - (((float) layoutParams2.width) / 2.0f));
                round2 = Math.round((((float) b2.y) + (((float) layoutParams.topMargin) + (((float) layoutParams.height) / 2.0f))) - (((float) layoutParams2.height) / 2.0f));
            }
            layoutParams2.leftMargin = Math.min(Math.max(0, round), i - layoutParams2.width);
            layoutParams2.topMargin = Math.min(Math.max(0, round2), i2 - layoutParams2.height);
            this.f.setLayoutParams(layoutParams);
            this.g.setLayoutParams(layoutParams2);
            this.f.setScaleType(ScaleType.FIT_CENTER);
            this.f.a(a);
            this.g.a(jVar);
        }

        protected void k() {
            this.h.h();
        }

        public void b() {
            super.b();
            this.f = null;
            this.g = null;
        }
    }

    public aj(com.chartboost.sdk.Model.a aVar) {
        super(aVar);
    }

    public boolean a(com.chartboost.sdk.Libraries.e.a aVar) {
        if (!super.a(aVar)) {
            return false;
        }
        this.G = aVar.e("ad_id");
        this.F = aVar.a("ux");
        if (this.d.b("frame-portrait") || this.d.b("close-portrait")) {
            this.h = false;
        }
        if (this.d.b("frame-landscape") || this.d.b("close-landscape")) {
            this.i = false;
        }
        this.k.a("frame-landscape");
        this.j.a("frame-portrait");
        this.E.a("close-landscape");
        this.D.a("close-portrait");
        return true;
    }

    protected Point b(String str) {
        com.chartboost.sdk.Libraries.e.a a = this.d.a(str).a("offset");
        if (a.c()) {
            return new Point(a.f("x"), a.f("y"));
        }
        return new Point(0, 0);
    }

    public void a(ViewGroup.LayoutParams layoutParams, j jVar, float f) {
        layoutParams.width = (int) ((((float) jVar.b()) / jVar.g()) * f);
        layoutParams.height = (int) ((((float) jVar.c()) / jVar.g()) * f);
    }

    public void d() {
        super.d();
        this.k.d();
        this.j.d();
        this.E.d();
        this.D.d();
        this.k = null;
        this.j = null;
        this.E = null;
        this.D = null;
    }
}
