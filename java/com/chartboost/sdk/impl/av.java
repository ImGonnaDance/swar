package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.chartboost.sdk.Libraries.CBUtility;

public final class av extends bk {
    private RectF b;
    private Paint c;
    private Paint d;
    private BitmapShader e;
    private float f = 0.0f;

    public av(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        float f = getContext().getResources().getDisplayMetrics().density;
        this.b = new RectF();
        this.c = new Paint();
        this.c.setStyle(Style.STROKE);
        this.c.setStrokeWidth(Math.max(1.0f, f * 1.0f));
        this.c.setAntiAlias(true);
    }

    public void a(int i) {
        this.c.setColor(i);
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        float a = CBUtility.a(1.0f, getContext());
        Drawable drawable = getDrawable();
        if (drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            if (this.e == null) {
                if (bitmap != null) {
                    this.e = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
                    this.d = new Paint();
                    this.d.setAntiAlias(true);
                    this.d.setShader(this.e);
                } else {
                    postInvalidate();
                    return;
                }
            }
            float max = Math.max(((float) getWidth()) / ((float) bitmap.getWidth()), ((float) getHeight()) / ((float) bitmap.getHeight()));
            canvas.save();
            canvas.scale(max, max);
            this.b.set(0.0f, 0.0f, ((float) getWidth()) / max, ((float) getHeight()) / max);
            this.b.inset(a / (max * 2.0f), a / (max * 2.0f));
            canvas.drawRoundRect(this.b, this.b.width() * this.f, this.b.height() * this.f, this.d);
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }
        this.b.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
        this.b.inset(a / 2.0f, a / 2.0f);
        canvas.drawRoundRect(this.b, this.b.width() * this.f, this.b.height() * this.f, this.c);
    }

    public void a(float f) {
        this.f = f;
        invalidate();
    }
}
