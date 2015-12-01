package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public abstract class bo extends View {
    private Bitmap a = null;
    private Canvas b = null;

    protected abstract void a(Canvas canvas);

    public bo(Context context) {
        super(context);
        a(context);
    }

    private void a(Context context) {
        try {
            getClass().getMethod("setLayerType", new Class[]{Integer.TYPE, Paint.class}).invoke(this, new Object[]{Integer.valueOf(1), null});
        } catch (Exception e) {
        }
    }

    private boolean b(Canvas canvas) {
        try {
            return ((Boolean) Canvas.class.getMethod("isHardwareAccelerated", new Class[0]).invoke(canvas, new Object[0])).booleanValue();
        } catch (Exception e) {
            return false;
        }
    }

    protected final void onDraw(Canvas canvas) {
        Canvas canvas2;
        boolean b = b(canvas);
        if (b) {
            if (!(this.a != null && this.a.getWidth() == canvas.getWidth() && this.a.getHeight() == canvas.getHeight())) {
                if (!(this.a == null || this.a.isRecycled())) {
                    this.a.recycle();
                }
                try {
                    this.a = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Config.ARGB_8888);
                    this.b = new Canvas(this.a);
                } catch (Throwable th) {
                    return;
                }
            }
            this.a.eraseColor(0);
            Canvas canvas3 = canvas;
            canvas = this.b;
            canvas2 = canvas3;
        } else {
            canvas2 = null;
        }
        a(canvas);
        if (b && canvas2 != null) {
            canvas2.drawBitmap(this.a, 0.0f, 0.0f, null);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!(this.a == null || this.a.isRecycled())) {
            this.a.recycle();
        }
        this.a = null;
    }
}
