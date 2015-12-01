package com.chartboost.sdk.impl;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.chartboost.sdk.Tracking.a;

public final class ag extends an {
    private LinearLayout b;
    private LinearLayout c;
    private bk d;
    private bl e;
    private TextView f;
    private TextView g;

    public ag(Context context, ai aiVar) {
        super(context, aiVar);
    }

    protected View a() {
        Context context = getContext();
        int round = Math.round(getContext().getResources().getDisplayMetrics().density * 6.0f);
        this.b = new LinearLayout(context);
        this.b.setOrientation(0);
        this.b.setGravity(17);
        this.c = new LinearLayout(context);
        this.c.setOrientation(1);
        this.c.setGravity(19);
        this.d = new bk(context);
        this.d.setPadding(round, round, round, round);
        if (this.a.A.e()) {
            this.d.a(this.a.A);
        }
        this.e = new bl(this, context) {
            final /* synthetic */ ag a;

            protected void a(MotionEvent motionEvent) {
                this.a.a.p().l();
                a.a("install-click", this.a.a.m, this.a.a.G, Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
            }
        };
        this.e.setPadding(round, round, round, round);
        if (this.a.B.e()) {
            this.e.a(this.a.B);
        }
        this.f = new TextView(getContext());
        this.f.setTextColor(-15264491);
        this.f.setTypeface(null, 1);
        this.f.setGravity(3);
        this.f.setPadding(round, round, round, round / 2);
        this.g = new TextView(getContext());
        this.g.setTextColor(-15264491);
        this.g.setTypeface(null, 1);
        this.g.setGravity(3);
        this.g.setPadding(round, 0, round, round);
        this.f.setTextSize(2, 14.0f);
        this.g.setTextSize(2, 11.0f);
        this.c.addView(this.f);
        this.c.addView(this.g);
        this.b.addView(this.d);
        this.b.addView(this.c, new LayoutParams(0, -2, 1.0f));
        this.b.addView(this.e);
        return this.b;
    }

    public void a(String str, String str2) {
        this.f.setText(str);
        this.g.setText(str2);
    }

    protected int b() {
        return 72;
    }
}
