package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.widget.LinearLayout;

public class ao extends LinearLayout {
    private Paint a;
    private float b = 1.0f;
    private int c = 0;

    public ao(Context context) {
        super(context);
        int round = Math.round(context.getResources().getDisplayMetrics().density * 5.0f);
        setPadding(round, round, round, round);
        setBaselineAligned(false);
        this.a = new Paint();
        this.a.setStyle(Style.FILL);
    }

    public void a(int i) {
        this.a.setColor(i);
        invalidate();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float f = getContext().getResources().getDisplayMetrics().density;
        if ((this.c & 1) > 0) {
            canvas.drawRect(0.0f, 0.0f, (float) canvas.getWidth(), this.b * f, this.a);
        }
        if ((this.c & 2) > 0) {
            canvas.drawRect(((float) canvas.getWidth()) - (this.b * f), 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), this.a);
        }
        if ((this.c & 4) > 0) {
            canvas.drawRect(0.0f, ((float) canvas.getHeight()) - (this.b * f), (float) canvas.getWidth(), (float) canvas.getHeight(), this.a);
        }
        if ((this.c & 8) > 0) {
            canvas.drawRect(0.0f, 0.0f, this.b * f, (float) canvas.getHeight(), this.a);
        }
    }

    public void b(int i) {
        this.c = i;
    }
}
