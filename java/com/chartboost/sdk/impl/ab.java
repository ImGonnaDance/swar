package com.chartboost.sdk.impl;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.chartboost.sdk.impl.n.a;
import com.chartboost.sdk.impl.n.b;

public class ab extends l<Bitmap> {
    private static final Object e = new Object();
    private final b<Bitmap> a;
    private final Config b;
    private final int c;
    private final int d;

    protected /* synthetic */ void b(Object obj) {
        a((Bitmap) obj);
    }

    public ab(String str, b<Bitmap> bVar, int i, int i2, Config config, a aVar) {
        throw new Error("Unresolved compilation problem: \n\tThe constructor DefaultRetryPolicy(int, int, float, int) is undefined\n");
    }

    public l.a s() {
        return l.a.LOW;
    }

    private static int b(int i, int i2, int i3, int i4) {
        if (i == 0 && i2 == 0) {
            return i3;
        }
        if (i == 0) {
            return (int) ((((double) i2) / ((double) i4)) * ((double) i3));
        }
        if (i2 == 0) {
            return i;
        }
        double d = ((double) i4) / ((double) i3);
        if (((double) i) * d > ((double) i2)) {
            return (int) (((double) i2) / d);
        }
        return i;
    }

    protected n<Bitmap> a(i iVar) {
        n<Bitmap> b;
        synchronized (e) {
            try {
                b = b(iVar);
            } catch (Throwable e) {
                t.c("Caught OOM for %d byte image, url=%s", Integer.valueOf(iVar.b.length), d());
                b = n.a(new k(e));
            }
        }
        return b;
    }

    private n<Bitmap> b(i iVar) {
        Object decodeByteArray;
        byte[] bArr = iVar.b;
        Options options = new Options();
        if (this.c == 0 && this.d == 0) {
            options.inPreferredConfig = this.b;
            decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        } else {
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int i = options.outWidth;
            int i2 = options.outHeight;
            int b = b(this.c, this.d, i, i2);
            int b2 = b(this.d, this.c, i2, i);
            options.inJustDecodeBounds = false;
            options.inSampleSize = a(i, i2, b, b2);
            Bitmap decodeByteArray2 = BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            if (decodeByteArray2 == null || (decodeByteArray2.getWidth() <= b && decodeByteArray2.getHeight() <= b2)) {
                Bitmap bitmap = decodeByteArray2;
            } else {
                decodeByteArray = Bitmap.createScaledBitmap(decodeByteArray2, b, b2, true);
                decodeByteArray2.recycle();
            }
        }
        if (decodeByteArray == null) {
            return n.a(new k(iVar));
        }
        return n.a(decodeByteArray, y.a(iVar));
    }

    protected void a(Bitmap bitmap) {
        this.a.a(bitmap);
    }

    static int a(int i, int i2, int i3, int i4) {
        float f = 1.0f;
        while (((double) (f * 2.0f)) <= Math.min(((double) i) / ((double) i3), ((double) i2) / ((double) i4))) {
            f *= 2.0f;
        }
        return (int) f;
    }
}
