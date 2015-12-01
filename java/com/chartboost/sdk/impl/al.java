package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;

public class al extends bo {
    private Paint a;
    private Paint b;
    private Path c;
    private RectF d;
    private RectF e;
    private int f = 0;
    private float g;
    private float h;

    public al(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        float f = context.getResources().getDisplayMetrics().density;
        this.g = 4.5f * f;
        this.a = new Paint();
        this.a.setColor(-1);
        this.a.setStyle(Style.STROKE);
        this.a.setStrokeWidth(f * 1.0f);
        this.a.setAntiAlias(true);
        this.b = new Paint();
        this.b.setColor(-855638017);
        this.b.setStyle(Style.FILL);
        this.b.setAntiAlias(true);
        this.c = new Path();
        this.e = new RectF();
        this.d = new RectF();
    }

    protected void a(Canvas canvas) {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.d.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        int min = Math.min(1, Math.round(f * 0.5f));
        this.d.inset((float) min, (float) min);
        this.c.reset();
        this.c.addRoundRect(this.d, this.g, this.g, Direction.CW);
        canvas.save();
        canvas.clipPath(this.c);
        canvas.drawColor(this.f);
        this.e.set(this.d);
        this.e.right = ((this.e.right - this.e.left) * this.h) + this.e.left;
        canvas.drawRect(this.e, this.b);
        canvas.restore();
        canvas.drawRoundRect(this.d, this.g, this.g, this.a);
    }

    public void a(int i) {
        this.f = i;
        invalidate();
    }

    public void b(int i) {
        this.a.setColor(i);
        invalidate();
    }

    public void c(int i) {
        this.b.setColor(i);
        invalidate();
    }

    public void a(float f) {
        this.h = f;
        if (getVisibility() != 8) {
            invalidate();
        }
    }

    public void b(float f) {
        this.g = f;
    }
}
