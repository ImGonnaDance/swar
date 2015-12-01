package com.chartboost.sdk.impl;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chartboost.sdk.f;

public final class am extends LinearLayout {
    private ai a;
    private LinearLayout b;
    private bk c;
    private TextView d;
    private bl e;
    private int f = Integer.MIN_VALUE;

    public am(Context context, ai aiVar) {
        super(context);
        this.a = aiVar;
        a(context);
    }

    private void a(Context context) {
        Context context2 = getContext();
        int round = Math.round(getContext().getResources().getDisplayMetrics().density * 8.0f);
        setOrientation(1);
        setGravity(17);
        this.b = new LinearLayout(context2);
        this.b.setGravity(17);
        this.b.setOrientation(0);
        this.b.setPadding(round, round, round, round);
        this.c = new bk(context2);
        this.c.setScaleType(ScaleType.FIT_CENTER);
        this.c.setPadding(0, 0, round, 0);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        this.a.a(layoutParams, this.a.y, 1.0f);
        this.d = new TextView(getContext());
        this.d.setTextColor(-1);
        this.d.setTypeface(null, 1);
        this.d.setGravity(17);
        this.d.setTextSize(2, f.a(context) ? 26.0f : 16.0f);
        this.b.addView(this.c, layoutParams);
        this.b.addView(this.d, new LinearLayout.LayoutParams(-2, -2));
        this.e = new bl(this, getContext()) {
            final /* synthetic */ am a;

            protected void a(MotionEvent motionEvent) {
                this.a.e.setEnabled(false);
                this.a.a.p().m();
            }
        };
        this.e.setPadding(0, 0, 0, round);
        this.e.a(ScaleType.FIT_CENTER);
        this.e.setPadding(round, round, round, round);
        LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
        this.a.a(layoutParams2, this.a.x, 1.0f);
        if (this.a.y.e()) {
            this.c.a(this.a.y);
        }
        if (this.a.x.e()) {
            this.e.a(this.a.x);
        }
        addView(this.b, new LinearLayout.LayoutParams(-2, -2));
        addView(this.e, layoutParams2);
        a();
    }

    public void a(boolean z) {
        setBackgroundColor(z ? -16777216 : this.f);
    }

    public void a(String str, int i) {
        this.d.setText(str);
        this.f = i;
        a(this.a.r());
    }

    public void a() {
        a(this.a.r());
    }
}
