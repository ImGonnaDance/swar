package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.os.Handler;
import com.chartboost.sdk.Libraries.CBUtility;
import com.com2us.module.offerwall.OfferwallDefine;

public final class br extends bo {
    private static Handler a = CBUtility.e();
    private float b;
    private long c;
    private Paint d;
    private Paint e;
    private Path f;
    private Path g;
    private RectF h;
    private RectF i;
    private Runnable j = new Runnable(this) {
        final /* synthetic */ br a;

        {
            this.a = r1;
        }

        public void run() {
            float f = this.a.getContext().getResources().getDisplayMetrics().density;
            br.a(this.a, (60.0f * f) * 0.016666668f);
            f = ((float) this.a.getHeight()) - (f * 9.0f);
            if (this.a.b > f) {
                br.b(this.a, f * 2.0f);
            }
            if (this.a.getWindowVisibility() == 0) {
                this.a.invalidate();
            }
        }
    };

    static /* synthetic */ float a(br brVar, float f) {
        float f2 = brVar.b + f;
        brVar.b = f2;
        return f2;
    }

    static /* synthetic */ float b(br brVar, float f) {
        float f2 = brVar.b - f;
        brVar.b = f2;
        return f2;
    }

    public br(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        float f = context.getResources().getDisplayMetrics().density;
        this.b = 0.0f;
        this.c = (long) (((double) System.nanoTime()) / 1000000.0d);
        this.d = new Paint();
        this.d.setColor(-1);
        this.d.setStyle(Style.STROKE);
        this.d.setStrokeWidth(f * 3.0f);
        this.d.setAntiAlias(true);
        this.e = new Paint();
        this.e.setColor(-1);
        this.e.setStyle(Style.FILL);
        this.e.setAntiAlias(true);
        this.f = new Path();
        this.g = new Path();
        this.i = new RectF();
        this.h = new RectF();
    }

    protected void a(Canvas canvas) {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.h.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        this.h.inset(OfferwallDefine.DEFAULT_HDIP_DENSITY_SCALE * f, OfferwallDefine.DEFAULT_HDIP_DENSITY_SCALE * f);
        float height = ((float) getHeight()) / 2.0f;
        canvas.drawRoundRect(this.h, height, height, this.d);
        this.i.set(this.h);
        this.i.inset(3.0f * f, f * 3.0f);
        f = this.i.height() / 2.0f;
        this.f.reset();
        this.f.addRoundRect(this.i, f, f, Direction.CW);
        height = this.i.height();
        this.g.reset();
        this.g.moveTo(0.0f, height);
        this.g.lineTo(height, height);
        this.g.lineTo(height * 2.0f, 0.0f);
        this.g.lineTo(height, 0.0f);
        this.g.close();
        canvas.save();
        Object obj = 1;
        try {
            canvas.clipPath(this.f);
        } catch (UnsupportedOperationException e) {
            obj = null;
        }
        if (obj != null) {
            for (f = (-height) + this.b; f < this.i.width() + height; f += 2.0f * height) {
                float f2 = this.i.left + f;
                canvas.save();
                canvas.translate(f2, this.i.top);
                canvas.drawPath(this.g, this.e);
                canvas.restore();
            }
        }
        canvas.restore();
        long max = Math.max(0, 16 - (((long) (((double) System.nanoTime()) / 1000000.0d)) - this.c));
        a.removeCallbacks(this.j);
        a.postDelayed(this.j, max);
    }

    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        a.removeCallbacks(this.j);
        if (visibility == 0) {
            a.post(this.j);
        }
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        a.removeCallbacks(this.j);
        a.post(this.j);
    }

    protected void onDetachedFromWindow() {
        a.removeCallbacks(this.j);
        super.onDetachedFromWindow();
    }
}
