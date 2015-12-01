package com.com2us.wrapper.kernel;

import com.com2us.wrapper.kernel.CWrapperKernel.r;
import java.util.TimeZone;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.o;

public class CWrapperData extends CWrapper {
    private static CWrapperData y = new CWrapperData();
    private String a;
    private String b;
    private int c;
    private int d;
    private boolean e;
    private boolean f;
    private boolean g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private int m;
    private int n;
    private int o;
    private int p;
    private String q;
    private String r;
    private boolean s;
    private int t;
    private int u;
    private boolean v;
    private int w;
    private String x = "1.0.1";

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_EXITED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    private CWrapperData() {
        super(false);
        nativeInitialize(TimeZone.getDefault().getRawOffset());
    }

    private int a(String str) {
        String str2 = i.a;
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt >= '0' && charAt <= '9') {
                str2 = str2 + charAt;
            }
        }
        return Integer.valueOf(str2).intValue();
    }

    public static CWrapperData getInstance() {
        return y;
    }

    private native void nativeFinalize();

    private native void nativeInitialize(int i);

    private native void nativeSetScreenSize(int i, int i2, int i3, int i4, int i5, int i6);

    public String getAssetFileNameAppended() {
        return this.b;
    }

    public String getAssetFolderName() {
        return this.a;
    }

    public int getColorBufferFormat() {
        return this.d;
    }

    public int getDefinedOriginalHeight() {
        return this.j;
    }

    public int getDefinedOriginalWidth() {
        return this.i;
    }

    public int getDevelopmentRevision() {
        return this.w;
    }

    public int getDeviceHeight() {
        return this.p;
    }

    public int getDeviceWidth() {
        return this.o;
    }

    public int getDisplayHeight() {
        return this.n;
    }

    public int getDisplayWidth() {
        return this.m;
    }

    public boolean getIsFullStretch() {
        return this.g;
    }

    public boolean getKeepScreenOnForcedly() {
        return this.s;
    }

    public String getMarketArmId() {
        return this.r;
    }

    public String getMinimumCommonVersion() {
        return this.x;
    }

    public int getOriginalHeight() {
        return this.l;
    }

    public int getOriginalWidth() {
        return this.k;
    }

    public int getRenderType() {
        return this.c;
    }

    public int getScreenBrightness() {
        return this.t;
    }

    public boolean getTerminateIfCracked() {
        return this.v;
    }

    public String getTextEncodingType() {
        return this.q;
    }

    public boolean getUseSGL() {
        return this.f;
    }

    public boolean getUseSelfTextureCache() {
        return this.e;
    }

    public int getViewportAlignment() {
        return this.h;
    }

    public int getVolumeStyle() {
        return this.u;
    }

    public boolean isVersionValid() {
        return getDevelopmentRevision() == a("$Revision: 3401 $");
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass1.a[rVar.ordinal()]) {
            case o.a /*1*/:
                nativeFinalize();
                return;
            default:
                return;
        }
    }

    public synchronized void setDataFromNative(String str, String str2, int i, int i2, boolean z, boolean z2, boolean z3, int i3, int i4, int i5, String str3, String str4, boolean z4, int i6, int i7, boolean z5, int i8) {
        this.a = str;
        this.b = str2;
        this.c = i;
        this.d = i2;
        this.e = z;
        this.f = z2;
        this.g = z3;
        this.h = i3;
        this.i = i4;
        this.j = i5;
        this.q = str3;
        this.r = str4;
        this.s = z4;
        this.t = i6;
        this.u = i7;
        this.v = z5;
        this.w = i8;
    }

    public synchronized void setScreenSize(int i, int i2) {
        setScreenSize(this.k, this.l, i, i2, this.o, this.p);
    }

    public synchronized void setScreenSize(int i, int i2, int i3, int i4, int i5, int i6) {
        int i7 = 1;
        synchronized (this) {
            int i8 = (this.o != i5 ? 1 : 0) | ((((0 | (this.k != i ? 1 : 0)) | (this.l != i2 ? 1 : 0)) | (this.m != i3 ? 1 : 0)) | (this.n != i4 ? 1 : 0));
            if (this.p == i6) {
                i7 = 0;
            }
            if ((i7 | i8) != 0) {
                this.k = i;
                this.l = i2;
                this.m = i3;
                this.n = i4;
                this.o = i5;
                this.p = i6;
                nativeSetScreenSize(this.k, this.l, this.m, this.n, this.o, this.p);
            }
        }
    }
}
