package com.com2us.wrapper.font;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperData;

public class CFont extends CWrapper {
    protected Bitmap a = null;
    protected Canvas b = null;
    protected Paint c = new Paint();
    protected int[] d = null;
    protected int e = 0;
    protected int f = 0;
    protected int g = 1;
    protected String h = null;

    public CFont(int i, int i2, int i3, String str) {
        a(i, i2, i3, str);
    }

    public CFont(Activity activity, String str, CWrapperData cWrapperData) {
        int i = 1;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int definedOriginalWidth = cWrapperData.getDefinedOriginalWidth();
        int definedOriginalHeight = cWrapperData.getDefinedOriginalHeight();
        if (!(definedOriginalWidth == 0 || definedOriginalHeight == 0 || definedOriginalWidth >= width)) {
            i = 2;
        }
        a(ModuleConfig.SPIDER_MODULE, ModuleConfig.SOCIAL_MEDIA_MOUDLE, i, str);
    }

    private int a(String str, int i, int i2) {
        this.c.setTextSize((float) i);
        this.c.setTypeface(Typeface.create(Typeface.SERIF, i2 & -5));
        this.c.setUnderlineText((i2 & 4) != 0);
        return (int) (this.c.measureText(str) + 1.0f);
    }

    private void a(int i, int i2, int i3, String str) {
        this.h = str;
        this.e = i;
        this.f = i2;
        this.g = i3;
        this.a = Bitmap.createBitmap(this.e, this.f, Config.ARGB_8888);
        this.b = new Canvas(this.a);
        this.b.setDensity(160);
        this.c.setARGB(255, 255, 255, 255);
        this.c.setAntiAlias(true);
        this.c.setLinearText(false);
        this.d = new int[(this.e * this.f)];
        nativeFontInitialize(this.d, this.e, this.f, this.g);
    }

    private native void nativeFontInitialize(int[] iArr, int i, int i2, int i3);

    public int drawText(byte[] bArr, int i, int i2) {
        try {
            String str = new String(bArr, this.h);
            int a = a(str, i, i2);
            this.c.setTextSize((float) i);
            this.c.setTypeface(Typeface.create(Typeface.SERIF, i2 & -5));
            this.c.setUnderlineText((i2 & 4) != 0);
            this.b.drawText(str, 0.0f, (float) i, this.c);
            this.a.getPixels(this.d, 0, a, 0, 0, a, (int) (((double) i) * 1.3d));
            this.b.drawColor(0, Mode.CLEAR);
            return a;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int drawText(byte[] bArr, int i, int i2, int[] iArr) {
        try {
            String str = new String(bArr, this.h);
            int i3 = iArr[0];
            int length = str.length();
            if (i3 >= length) {
                iArr[0] = -1;
                return 0;
            }
            int i4 = 0;
            int i5 = i3;
            i3 = length;
            while (i5 <= i3) {
                length = (i5 + i3) >> 1;
                if (a(str.substring(iArr[0], length), i, i2) < this.e) {
                    i5 = length + 1;
                    i4 = length;
                } else {
                    i3 = length - 1;
                }
            }
            String substring = str.substring(iArr[0], i4);
            i5 = a(substring, i, i2);
            this.c.setTextSize((float) i);
            this.c.setTypeface(Typeface.create(Typeface.SERIF, i2 & -5));
            this.c.setUnderlineText((i2 & 4) != 0);
            this.b.drawText(substring, 0.0f, (float) i, this.c);
            this.a.getPixels(this.d, 0, i5, 0, 0, i5, (int) (((double) i) * 1.3d));
            this.b.drawColor(0, Mode.CLEAR);
            if (i4 != str.length()) {
                iArr[0] = i4;
            } else {
                iArr[0] = -1;
            }
            return i5;
        } catch (Exception e) {
            e.printStackTrace();
            iArr[0] = -1;
            return 0;
        }
    }

    public int drawTextInRect(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        return 0;
    }

    public int getStringWidth(byte[] bArr, int i, int i2) {
        try {
            return a(new String(bArr, this.h), i, i2);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getStringWidthHeightInRect(byte[] bArr, int i, int i2, int i3, int i4, int[] iArr) {
        return 0;
    }
}
