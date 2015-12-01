package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.widget.LinearLayout;
import com.chartboost.sdk.Libraries.e.a;

public abstract class aq extends LinearLayout {
    private Rect a = new Rect();
    private Paint b = null;
    private Paint c = null;

    public abstract int a();

    public abstract void a(a aVar, int i);

    public aq(Context context) {
        super(context);
    }

    protected void onDraw(Canvas canvas) {
        this.a.set(0, 0, canvas.getWidth(), canvas.getHeight());
        a(canvas, this.a);
        b(canvas, this.a);
    }

    private void a(Canvas canvas, Rect rect) {
        if (this.c == null) {
            this.c = new Paint();
            this.c.setStyle(Style.FILL);
            this.c.setColor(-1);
        }
        canvas.drawRect(rect, this.c);
    }

    private void b(Canvas canvas, Rect rect) {
        if (this.b == null) {
            this.b = new Paint();
            this.b.setStyle(Style.FILL);
            this.b.setAntiAlias(true);
        }
        this.b.setColor(-2631721);
        canvas.drawRect((float) rect.left, (float) (rect.bottom - 1), (float) rect.right, (float) rect.bottom, this.b);
    }
}
