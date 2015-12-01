package com.com2us.wrapper.ui;

import android.graphics.Point;
import android.widget.FrameLayout;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import defpackage.h;
import java.util.HashMap;
import jp.co.dimage.android.o;

public class CTextInput extends CWrapper {
    private int a = 0;
    private FrameLayout b = null;
    private HashMap<Integer, h> c = new HashMap();
    private String d = null;

    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_PAUSE_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[r.APPLICATION_RESUMED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[r.APPLICATION_EXITED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public interface b {
        void a(int i, int i2);
    }

    public class a implements b {
        final /* synthetic */ CTextInput a;

        public a(CTextInput cTextInput) {
            this.a = cTextInput;
        }

        public void a(final int i, final int i2) {
            CFunction.runOnGlThread(new Runnable(this) {
                final /* synthetic */ a c;

                public void run() {
                    this.c.a.nativeCallback(i, i2);
                }
            });
        }
    }

    public CTextInput(FrameLayout frameLayout, String str) {
        this.b = frameLayout;
        this.d = str;
    }

    private native void nativeCallback(int i, int i2);

    public int create(int i, int i2, int i3, int i4, int i5) {
        this.a++;
        Point[] convertOriginaltoDisplay = CFunction.convertOriginaltoDisplay(new Point[]{new Point(i, i2), new Point(i3, i4)});
        int i6 = convertOriginaltoDisplay[0].x;
        int i7 = convertOriginaltoDisplay[0].y;
        int i8 = convertOriginaltoDisplay[1].x;
        int i9 = convertOriginaltoDisplay[1].y;
        this.c.put(Integer.valueOf(this.a), new h(this.a, this.b, new a(this), new int[]{i6, i7, i8, i9}, i5));
        return this.a;
    }

    public boolean destroy(int i) {
        h hVar = (h) this.c.remove(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.a();
        return true;
    }

    public int getAutoCapitalizationType(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.p();
    }

    public int getPropertyBackColor(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.j();
    }

    public int getPropertyFocus(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.n() ? 1 : 0;
    }

    public int[] getPropertyFrame(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return null;
        }
        int[] h = hVar.h();
        Point[] convertDisplaytoOriginal = CFunction.convertDisplaytoOriginal(new Point[]{new Point(h[0], h[1]), new Point(h[2], h[3])});
        h[0] = convertDisplaytoOriginal[0].x;
        h[1] = convertDisplaytoOriginal[0].y;
        h[2] = convertDisplaytoOriginal[1].x;
        h[3] = convertDisplaytoOriginal[1].y;
        return h;
    }

    public int getPropertyKeyboardAlwaysShow(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.r() ? 1 : 0;
    }

    public int getPropertyKeyboardType(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.k();
    }

    public int getPropertyMaxTextLength(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.e();
    }

    public byte[] getPropertyPlaceHolder(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return null;
        }
        try {
            return hVar.q().getBytes(this.d);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPropertyReturnKeyType(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.l();
    }

    public int getPropertySecure(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.m() ? 1 : 0;
    }

    public byte[] getPropertyText(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return null;
        }
        try {
            return hVar.b().getBytes(this.d);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPropertyTextAlignmentHorizontal(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.f();
    }

    public int getPropertyTextAlignmentVertical(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.g();
    }

    public int getPropertyTextBytes(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.d();
    }

    public int getPropertyTextColor(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.i();
    }

    public int getPropertyTextLength(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.c();
    }

    public int getPropertyTextSize(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return -1;
        }
        int o = hVar.o();
        Point convertDisplaytoOriginal = CFunction.convertDisplaytoOriginal(new Point(o, o));
        o = convertDisplaytoOriginal.x;
        int i2 = convertDisplaytoOriginal.y;
        return o >= i2 ? i2 : o;
    }

    public int getPropertyTextType(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        return hVar == null ? -1 : hVar.s();
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass3.a[rVar.ordinal()]) {
            case o.a /*1*/:
                CFunction.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ CTextInput a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.b.setVisibility(4);
                    }
                });
                return;
            case o.b /*2*/:
                CFunction.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ CTextInput a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.b.setVisibility(0);
                    }
                });
                return;
            case o.c /*3*/:
                this.c.clear();
                return;
            default:
                return;
        }
    }

    public int requestReturnEventForcedly(int i) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return -1;
        }
        hVar.t();
        return 0;
    }

    public boolean setPropertyAutoCapitalizationType(int i, int i2) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.i(i2);
        return true;
    }

    public boolean setPropertyBackColor(int i, int i2) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.e(i2);
        return true;
    }

    public boolean setPropertyFocus(int i, boolean z) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.b(z);
        return true;
    }

    public boolean setPropertyFrame(int i, int[] iArr) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        Point[] convertOriginaltoDisplay = CFunction.convertOriginaltoDisplay(new Point[]{new Point(iArr[0], iArr[1]), new Point(iArr[2], iArr[3])});
        iArr[0] = convertOriginaltoDisplay[0].x;
        iArr[1] = convertOriginaltoDisplay[0].y;
        iArr[2] = convertOriginaltoDisplay[1].x;
        iArr[3] = convertOriginaltoDisplay[1].y;
        if (hVar == null) {
            return false;
        }
        hVar.a(iArr);
        return true;
    }

    public boolean setPropertyKeyboardAlwaysShow(int i, boolean z) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.c(z);
        return true;
    }

    public boolean setPropertyKeyboardType(int i, int i2) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.f(i2);
        return true;
    }

    public boolean setPropertyMaxTextLength(int i, int i2) {
        try {
            h hVar = (h) this.c.get(Integer.valueOf(i));
            if (hVar == null) {
                return false;
            }
            hVar.a(i2);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setPropertyPlaceHolder(int i, byte[] bArr) {
        try {
            h hVar = (h) this.c.get(Integer.valueOf(i));
            if (hVar == null) {
                return false;
            }
            hVar.b(new String(bArr, this.d));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setPropertyReturnKeyType(int i, int i2) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.g(i2);
        return true;
    }

    public boolean setPropertySecure(int i, boolean z) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.a(z);
        return true;
    }

    public boolean setPropertyText(int i, byte[] bArr) {
        try {
            h hVar = (h) this.c.get(Integer.valueOf(i));
            if (hVar == null) {
                return false;
            }
            hVar.a(new String(bArr, this.d));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setPropertyTextAlignmentHorizontal(int i, int i2) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.b(i2);
        return true;
    }

    public boolean setPropertyTextAlignmentVertical(int i, int i2) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.c(i2);
        return true;
    }

    public boolean setPropertyTextColor(int i, int i2) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        if (hVar == null) {
            return false;
        }
        hVar.d(i2);
        return true;
    }

    public boolean setPropertyTextSize(int i, int i2) {
        h hVar = (h) this.c.get(Integer.valueOf(i));
        Point convertOriginaltoDisplay = CFunction.convertOriginaltoDisplay(new Point(i2, i2));
        int i3 = convertOriginaltoDisplay.x;
        int i4 = convertOriginaltoDisplay.y;
        if (i3 >= i4) {
            i3 = i4;
        }
        if (hVar == null) {
            return false;
        }
        hVar.h(i3);
        return true;
    }
}
