package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.MotionEvent;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.e.a;
import com.chartboost.sdk.Libraries.j;
import com.chartboost.sdk.f;
import com.com2us.peppermint.PeppermintConstant;

public class as extends aq {
    private ax a;
    private TextView b;
    private TextView c;
    private TextView d;
    private LinearLayout e;
    private av f;
    private bl g;
    private int h;
    private Point i;
    private j j;
    private OnClickListener k;

    public as(ax axVar, Context context) {
        super(context);
        this.a = axVar;
        this.e = new LinearLayout(context);
        this.e.setOrientation(1);
        setGravity(16);
        boolean a = f.a(context);
        this.b = new TextView(context);
        this.b.setTypeface(null, 1);
        this.b.setTextSize(2, a ? 21.0f : 16.0f);
        this.b.setTextColor(-16777216);
        this.b.setSingleLine();
        this.b.setEllipsize(TruncateAt.END);
        this.c = new TextView(context);
        this.c.setTextSize(2, a ? 16.0f : 10.0f);
        this.c.setTextColor(-16777216);
        this.c.setSingleLine();
        this.c.setEllipsize(TruncateAt.END);
        this.d = new TextView(context);
        this.d.setTextSize(2, a ? 18.0f : 11.0f);
        this.d.setTextColor(-16777216);
        this.d.setMaxLines(2);
        this.d.setEllipsize(TruncateAt.END);
        this.g = new bl(this, context) {
            final /* synthetic */ as a;

            protected void a(MotionEvent motionEvent) {
                this.a.k.onClick(this.a.g);
            }
        };
        this.g.a(ScaleType.FIT_CENTER);
        this.f = new av(context);
        setFocusable(false);
        setGravity(16);
        addView(this.f);
        addView(this.e, new LayoutParams(0, -2, 1.0f));
        addView(this.g);
        setBackgroundColor(0);
        this.e.addView(this.b, new LayoutParams(-1, -2));
        this.e.addView(this.c, new LayoutParams(-1, -2));
        this.e.addView(this.d, new LayoutParams(-1, -1));
    }

    public void setOnClickListener(OnClickListener clickListener) {
        super.setOnClickListener(clickListener);
        this.k = clickListener;
    }

    public void a(a aVar, int i) {
        this.b.setText(aVar.a(PeppermintConstant.JSON_KEY_NAME).d("Unknown App"));
        if (TextUtils.isEmpty(aVar.e("publisher"))) {
            this.c.setVisibility(8);
        } else {
            this.c.setText(aVar.e("publisher"));
        }
        if (TextUtils.isEmpty(aVar.e("description"))) {
            this.d.setVisibility(8);
        } else {
            this.d.setText(aVar.e("description"));
        }
        this.h = aVar.b("border-color") ? -4802890 : f.a(aVar.e("border-color"));
        if (aVar.c("offset")) {
            this.i = new Point(aVar.a("offset").f("x"), aVar.a("offset").f("y"));
        } else {
            this.i = new Point(0, 0);
        }
        this.j = null;
        if (aVar.c("deep-link") && bc.a(aVar.e("deep-link"))) {
            if (this.a.k.e()) {
                this.j = this.a.k;
            } else {
                this.g.a("Play");
            }
        } else if (this.a.j.e()) {
            this.j = this.a.j;
        } else {
            this.g.a("Install");
        }
        int a = CBUtility.a(f.a(getContext()) ? 14 : 7, getContext());
        if (this.j != null) {
            this.g.a(this.j);
            a = (a * 2) + Math.round((((float) this.j.b()) * ((float) c())) / ((float) this.j.c()));
        } else {
            this.g.a().setTextColor(-14571545);
            a = CBUtility.a(8, getContext());
            this.g.a().setPadding(a, a, a, a);
            a = CBUtility.a(100, getContext());
        }
        this.g.setLayoutParams(new LayoutParams(a, c()));
        a(this.f, i, aVar.a("assets").a("icon"));
        this.f.a(this.h);
        this.f.a(0.16666667f);
        b();
    }

    private void a(bk bkVar, int i, a aVar) {
        if (!aVar.b()) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            bd.a().a(aVar.e(jp.co.cyberz.fox.notify.a.g), aVar.e("checksum"), null, bkVar, bundle);
        }
    }

    protected void b() {
        int a = CBUtility.a(f.a(getContext()) ? 14 : 7, getContext());
        ViewGroup.LayoutParams layoutParams = new LayoutParams(a() - (a * 2), a() - (a * 2));
        layoutParams.setMargins(a, a, a, a);
        this.e.setPadding(0, a, 0, a);
        this.g.setPadding((this.i.x * 2) + a, this.i.y * 2, a, 0);
        this.f.setLayoutParams(layoutParams);
        this.f.setScaleType(ScaleType.FIT_CENTER);
    }

    public int a() {
        int i = 134;
        if (CBUtility.c().c()) {
            if (!f.a(getContext())) {
                i = 75;
            }
        } else if (!f.a(getContext())) {
            i = 77;
        }
        return CBUtility.a(i, getContext());
    }

    private int c() {
        int i = 74;
        if (CBUtility.c().c()) {
            if (!f.a(getContext())) {
                i = 41;
            }
        } else if (!f.a(getContext())) {
            i = 41;
        }
        return CBUtility.a(i, getContext());
    }
}
