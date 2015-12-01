package com.chartboost.sdk.impl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.j;

public final class ak extends an {
    private LinearLayout b;
    private bk c;
    private TextView d;

    public ak(Context context, ai aiVar) {
        super(context, aiVar);
    }

    protected View a() {
        Context context = getContext();
        int round = Math.round(getContext().getResources().getDisplayMetrics().density * 8.0f);
        this.b = new LinearLayout(context);
        this.b.setOrientation(0);
        this.b.setGravity(17);
        int a = CBUtility.a(36, context);
        this.c = new bk(context);
        this.c.setPadding(round, round, round, round);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(a, a);
        this.c.setScaleType(ScaleType.FIT_CENTER);
        this.d = new TextView(context);
        this.d.setPadding(round / 2, round, round, round);
        this.d.setTextColor(-15264491);
        this.d.setTextSize(2, 16.0f);
        this.d.setTypeface(null, 1);
        this.d.setGravity(17);
        this.b.addView(this.c, layoutParams);
        this.b.addView(this.d, new LinearLayout.LayoutParams(-2, -1));
        return this.b;
    }

    public void a(j jVar) {
        this.c.a(jVar);
        this.c.setScaleType(ScaleType.FIT_CENTER);
    }

    public void a(String str) {
        this.d.setText(str);
    }

    protected int b() {
        return 48;
    }
}
