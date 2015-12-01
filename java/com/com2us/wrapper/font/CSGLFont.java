package com.com2us.wrapper.font;

import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.Typeface;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public class CSGLFont extends CFont {
    public CSGLFont(int i, int i2, String str) {
        super(i, i2, 1, str);
    }

    private boolean a(Character ch) {
        char charValue = ch.charValue();
        return '-' == ch.charValue() ? false : (charValue >= '\u0000' && charValue <= '\u024f') || (('\u1e00' <= charValue && charValue <= '\u1eff') || (('\u2c60' <= charValue && charValue <= '\u2c7f') || ('\ua720' <= charValue && charValue <= '\ua7ff')));
    }

    public int drawTextInRect(byte[] bArr, int i, int i2, int i3, int i4, int i5) {
        int i6 = 0;
        int i7 = 0;
        boolean z = (i2 & 4) != 0;
        int i8 = i2 & -5;
        try {
            int i9;
            String str = new String(bArr, this.h);
            int length = str.length();
            CharSequence subSequence = str.subSequence(0, length);
            Typeface create = Typeface.create(Typeface.SERIF, i8);
            this.c.setTextSize((float) i);
            this.c.setTypeface(create);
            this.c.setUnderlineText(z);
            switch (i3) {
                case g.a /*0*/:
                    this.c.setTextAlign(Align.LEFT);
                    i9 = 0;
                    break;
                case o.a /*1*/:
                    this.c.setTextAlign(Align.CENTER);
                    i9 = i4 / 2;
                    break;
                case o.b /*2*/:
                    this.c.setTextAlign(Align.RIGHT);
                    i9 = i4;
                    break;
                default:
                    i9 = 0;
                    break;
            }
            FontMetricsInt fontMetricsInt = this.c.getFontMetricsInt();
            int i10 = -fontMetricsInt.top;
            int i11 = (fontMetricsInt.descent + (fontMetricsInt.top - fontMetricsInt.ascent)) + 2;
            int i12 = (i10 + i11) % 2 != 0 ? i11 + 1 : i11;
            int i13;
            int i14;
            int i15;
            int i16;
            int i17;
            switch (i5) {
                case g.a /*0*/:
                    i11 = 0;
                    i13 = 0;
                    while (i13 < length) {
                        i14 = i6 + i10;
                        i15 = length;
                        i16 = i11;
                        i17 = i7;
                        i11 = i13;
                        while (true) {
                            if (i11 <= i15 || i16 > i4) {
                                if (i11 > i15) {
                                    i11 = i15;
                                }
                                i6 = (i11 + i15) >> 1;
                                i16 = Math.round(this.c.measureText(subSequence, i13, i13 + i6 > length ? length : i13 + i6) + 1.0f);
                                if (i16 <= i4) {
                                    i11 = i6 + 1;
                                    i17 = i6;
                                } else {
                                    i15 = i6 - 1;
                                }
                            } else {
                                i15 = 0;
                                i11 = i17 + i13 > length ? length - i13 : i17;
                                while (i15 < i11 && '\n' != subSequence.charAt(i13 + i15)) {
                                    i15++;
                                }
                                if (i15 < i17) {
                                    i11 = i15 + 1;
                                    i17 = i15;
                                } else {
                                    i11 = i17;
                                }
                                if (i11 == 0) {
                                    this.b.drawText(str, 0, 1, (float) i9, (float) ((i14 - fontMetricsInt.descent) + 1), this.c);
                                    i6 = i14;
                                    break;
                                }
                                this.b.drawText(subSequence, i13, i13 + i17, (float) i9, (float) ((i14 - fontMetricsInt.descent) + 1), this.c);
                                i13 += i11;
                                i7 = 0;
                                i6 = i14 + i12;
                                i11 = i16;
                            }
                        }
                    }
                    break;
                case o.a /*1*/:
                    i11 = 0;
                    i15 = 0;
                    i13 = 0;
                    while (i13 < length) {
                        i14 = i6 + i10;
                        i16 = i11;
                        i17 = i15;
                        i15 = length;
                        i11 = i13;
                        while (true) {
                            if (i11 <= i15 || i16 > i4) {
                                if (i11 > i15) {
                                    i11 = i15;
                                }
                                i6 = (i11 + i15) >> 1;
                                i16 = Math.round(this.c.measureText(subSequence, i13, i13 + i6 > length ? length : i13 + i6) + 1.0f);
                                if (i16 <= i4) {
                                    i11 = i6 + 1;
                                    i17 = i6;
                                } else {
                                    i15 = i6 - 1;
                                }
                            } else {
                                int i18;
                                i15 = i17;
                                while (i15 > 1 && i13 + i15 < length && true == a(Character.valueOf(subSequence.charAt(i13 + i15))) && !Character.isWhitespace(subSequence.charAt(i13 + i15))) {
                                    i15--;
                                }
                                if (i13 + i15 < length && true == Character.isWhitespace(subSequence.charAt(i13 + i15))) {
                                    i15++;
                                }
                                i7 = 0;
                                i11 = i15 + i13 > length ? length - i13 : i15;
                                while (i7 < i11 && '\n' != subSequence.charAt(i13 + i7)) {
                                    i7++;
                                }
                                if (i7 < i15) {
                                    i11 = i7;
                                    i18 = i7 + 1;
                                } else {
                                    i11 = i15;
                                    i18 = i15;
                                }
                                if (i18 == 0) {
                                    this.b.drawText(str, 0, 1, (float) i9, (float) ((i14 - fontMetricsInt.descent) + 1), this.c);
                                    i6 = i14;
                                    break;
                                }
                                this.b.drawText(subSequence, i13, i13 + i11, (float) i9, (float) ((i14 - fontMetricsInt.descent) + 1), this.c);
                                i13 += i18;
                                i6 = i14 + i12;
                                i15 = 0;
                                i11 = i16;
                            }
                        }
                    }
                    break;
            }
            this.a.getPixels(this.d, 0, i4, 0, 0, i4, i6);
            this.b.drawColor(0, Mode.CLEAR);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getStringWidthHeightInRect(byte[] bArr, int i, int i2, int i3, int i4, int[] iArr) {
        try {
            String str = new String(bArr, this.h);
            boolean z = (i2 & 4) != 0;
            int i5 = 0;
            int i6 = 0;
            int i7 = 0;
            int i8 = 0;
            int length = str.length();
            CharSequence subSequence = str.subSequence(0, length);
            Typeface create = Typeface.create(Typeface.SERIF, i2 & -5);
            this.c.setTextSize((float) i);
            this.c.setTypeface(create);
            this.c.setUnderlineText(z);
            FontMetricsInt fontMetricsInt = this.c.getFontMetricsInt();
            int i9 = -fontMetricsInt.top;
            int i10 = ((fontMetricsInt.top - fontMetricsInt.ascent) + fontMetricsInt.descent) + 2;
            int i11 = (i9 + i10) % 2 != 0 ? i10 + 1 : i10;
            int i12;
            int i13;
            int i14;
            switch (i4) {
                case g.a /*0*/:
                    i10 = 0;
                    while (i5 < length) {
                        i12 = i10 + i9;
                        i13 = length;
                        i14 = i7;
                        i7 = 0;
                        i10 = i5;
                        while (true) {
                            if (i10 <= i13 || r8 > i3) {
                                if (i10 > i13) {
                                    i10 = i13;
                                }
                                i14 = (i10 + i13) >> 1;
                                i7 = Math.round(this.c.measureText(subSequence, i5, i5 + i14 > length ? length : i5 + i14) + 1.0f);
                                int i15;
                                if (i7 <= i3) {
                                    i10 = i14 + 1;
                                    i6 = i14;
                                    i15 = i7;
                                    i7 = i14;
                                    i14 = i15;
                                } else {
                                    i13 = i14 - 1;
                                    i15 = i14;
                                    i14 = i7;
                                    i7 = i15;
                                }
                            } else {
                                i13 = 0;
                                i10 = i6 + i5 > length ? length - i5 : i6;
                                while (i13 < i10 && '\n' != subSequence.charAt(i5 + i13)) {
                                    i13++;
                                }
                                if (i13 < i6) {
                                    i6 = i13 + 1;
                                }
                                i13 = Math.round(this.c.measureText(subSequence, i5, i5 + i7 > length ? length : i5 + i7) + 1.0f);
                                i5 += i6;
                                i6 = 0;
                                i8 = i8 < i13 ? i13 : i8;
                                i10 = i12 + i11;
                                i7 = i13;
                            }
                        }
                    }
                    break;
                case o.a /*1*/:
                    i10 = 0;
                    i13 = 0;
                    i7 = 0;
                    i6 = 0;
                    while (i6 < length) {
                        i12 = i10 + i9;
                        i14 = 0;
                        i5 = i7;
                        i10 = i6;
                        i7 = i13;
                        i13 = length;
                        while (true) {
                            if (i10 <= i13 || r3 > i3) {
                                if (i10 > i13) {
                                    i10 = i13;
                                }
                                i14 = (i10 + i13) >> 1;
                                i7 = Math.round(this.c.measureText(subSequence, i6, i6 + i14 > length ? length : i6 + i14) + 1.0f);
                                if (i7 <= i3) {
                                    i10 = i14 + 1;
                                    i5 = i14;
                                } else {
                                    i13 = i14 - 1;
                                }
                            } else {
                                i13 = i5;
                                while (i13 > 1 && i6 + i13 < length && true == a(Character.valueOf(subSequence.charAt(i6 + i13))) && !Character.isWhitespace(subSequence.charAt(i6 + i13))) {
                                    i13--;
                                }
                                if (i6 + i13 < length && true == Character.isWhitespace(subSequence.charAt(i6 + i13))) {
                                    i13++;
                                }
                                i7 = 0;
                                i10 = i13 + i6 > length ? length - i6 : i13;
                                while (i7 < i10 && '\n' != subSequence.charAt(i6 + i7)) {
                                    i7++;
                                }
                                if (i7 < i13) {
                                    i13 = i7 + 1;
                                }
                                i7 = Math.round(this.c.measureText(subSequence, i6, i6 + i14 > length ? length : i6 + i14) + 1.0f);
                                i10 = i8 < i7 ? i7 : i8;
                                if (i10 > i3) {
                                    i10 = i3;
                                }
                                i8 = i6 + i13;
                                i13 = i7;
                                i7 = 0;
                                i6 = i8;
                                i8 = i10;
                                i10 = i12 + i11;
                            }
                        }
                    }
                    break;
                default:
                    i10 = 0;
                    break;
            }
            iArr[0] = i8;
            iArr[1] = i10;
            return iArr[0];
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
