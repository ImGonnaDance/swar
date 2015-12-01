package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.j;

public class ah extends aj {
    private j j = new j(this);
    private j k = new j(this);

    public class a extends com.chartboost.sdk.impl.aj.a {
        protected bl b;
        protected ImageView c;
        final /* synthetic */ ah d;

        protected a(final ah ahVar, Context context) {
            this.d = ahVar;
            super(ahVar, context);
            this.b = new bl(this, context) {
                final /* synthetic */ a b;

                protected void a(MotionEvent motionEvent) {
                    this.b.a(motionEvent.getX(), motionEvent.getY());
                }
            };
            a(this.b);
            this.c = new ImageView(context);
            this.c.setBackgroundColor(-16777216);
            addView(this.c);
            addView(this.b);
        }

        protected void a(float f, float f2) {
            this.d.a(null, null);
        }

        protected void a(int i, int i2) {
            super.a(i, i2);
            boolean b = this.d.a().b();
            j a = b ? this.d.j : this.d.k;
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            this.d.a(layoutParams, a, this.d.H);
            Point b2 = this.d.b(b ? "ad-portrait" : "ad-landscape");
            layoutParams.leftMargin = Math.round((((float) (i - layoutParams.width)) / 2.0f) + ((((float) b2.x) / a.g()) * this.d.H));
            layoutParams.topMargin = Math.round(((((float) b2.y) / a.g()) * this.d.H) + (((float) (i2 - layoutParams.height)) / 2.0f));
            this.c.setLayoutParams(layoutParams);
            this.b.setLayoutParams(layoutParams);
            this.b.a(ScaleType.FIT_CENTER);
            this.b.a(a);
        }

        public void b() {
            super.b();
            this.b = null;
            this.c = null;
        }
    }

    public ah(com.chartboost.sdk.Model.a aVar) {
        super(aVar);
    }

    protected com.chartboost.sdk.f.a b(Context context) {
        return new a(this, context);
    }

    public boolean a(com.chartboost.sdk.Libraries.e.a aVar) {
        if (!super.a(aVar)) {
            return false;
        }
        if (this.d.b("ad-portrait")) {
            this.h = false;
        }
        if (this.d.b("ad-landscape")) {
            this.i = false;
        }
        this.k.a("ad-landscape");
        this.j.a("ad-portrait");
        return true;
    }

    public void d() {
        super.d();
        this.k.d();
        this.j.d();
        this.k = null;
        this.j = null;
    }
}
