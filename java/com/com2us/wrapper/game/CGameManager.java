package com.com2us.wrapper.game;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.function.CResource;
import com.com2us.wrapper.kernel.CEventHandler;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperData;
import com.com2us.wrapper.kernel.CWrapperKernel;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import defpackage.b;
import defpackage.c;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;

public class CGameManager extends CWrapper {
    private CEventHandler a = null;
    private b b = null;
    private Activity c = null;
    private GLSurfaceView d = null;
    private defpackage.a e = null;

    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_PAUSE_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[r.APPLICATION_STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[r.APPLICATION_RESUMED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[r.GLSURFACEVIEW_SIZE_CHANGED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[r.APPLICATION_EXITED.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static class a implements EGLConfigChooser {
        private static int g = 4;
        private static int[] h = new int[]{12324, 8, 12323, 8, 12322, 8, 12321, 8, 12325, 16, 12326, 8, 12344};
        protected int a;
        protected int b;
        protected int c;
        protected int d;
        protected int e;
        protected int f;
        private int[] i = new int[1];

        public a(int i, int i2, int i3, int i4, int i5, int i6) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
            this.f = i6;
        }

        private int a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
            return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, this.i) ? this.i[0] : i2;
        }

        public EGLConfig a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig[] eGLConfigArr) {
            EGLConfig eGLConfig = null;
            int i = UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS;
            int length = eGLConfigArr.length;
            int i2 = 0;
            while (i2 < length) {
                EGLConfig eGLConfig2 = eGLConfigArr[i2];
                int a = a(egl10, eGLDisplay, eGLConfig2, 12325, 0);
                int a2 = a(egl10, eGLDisplay, eGLConfig2, 12326, 0);
                if (a >= this.e && a2 >= this.f) {
                    a = a(egl10, eGLDisplay, eGLConfig2, 12324, 0);
                    int a3 = a(egl10, eGLDisplay, eGLConfig2, 12323, 0);
                    int a4 = a(egl10, eGLDisplay, eGLConfig2, 12322, 0);
                    int abs = (Math.abs(a - this.a) + Math.abs(a3 - this.b)) + Math.abs(a4 - this.c);
                    a2 = Math.abs(a(egl10, eGLDisplay, eGLConfig2, 12321, 0) - this.d) + abs;
                    if (a2 < i) {
                        i2++;
                        i = a2;
                        eGLConfig = eGLConfig2;
                    }
                }
                a2 = i;
                eGLConfig2 = eGLConfig;
                i2++;
                i = a2;
                eGLConfig = eGLConfig2;
            }
            return eGLConfig;
        }

        public boolean a() {
            return this.a == 8 && this.b == 8 && this.c == 8 && this.d == 8;
        }

        public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
            int[] iArr = new int[1];
            egl10.eglChooseConfig(eGLDisplay, h, null, 0, iArr);
            int i = iArr[0];
            if (i <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] eGLConfigArr = new EGLConfig[i];
            egl10.eglChooseConfig(eGLDisplay, h, eGLConfigArr, i, iArr);
            return a(egl10, eGLDisplay, eGLConfigArr);
        }
    }

    public CGameManager(Activity activity, GLSurfaceView gLSurfaceView, CEventHandler cEventHandler, CWrapperData cWrapperData) {
        this.c = activity;
        this.d = gLSurfaceView;
        this.d.setLayoutParams(new LayoutParams(-1, -1));
        ((FrameLayout) this.c.findViewById(CResource.R("R.id.GLViewLayout"))).addView(this.d);
        this.a = cEventHandler;
        this.b = new b(this.a);
        Object aVar = new a(8, 8, 8, 8, 16, 8);
        this.d.setEGLConfigChooser(aVar);
        if (aVar.a()) {
            this.d.getHolder().setFormat(1);
        }
        this.e = new defpackage.a();
        this.d.setRenderer(this.e);
        this.d.setRenderMode(0);
        this.d.setFocusableInTouchMode(true);
        this.d.setBackgroundColor(0);
        initializeDisplay(cWrapperData);
    }

    public void initializeDisplay(CWrapperData cWrapperData) {
        int i;
        int i2;
        int width;
        int i3 = 0;
        FrameLayout frameLayout = (FrameLayout) this.c.findViewById(CResource.R("R.id.DeviceLayout"));
        int width2 = frameLayout.getWidth();
        int height = frameLayout.getHeight();
        int definedOriginalWidth = cWrapperData.getDefinedOriginalWidth();
        int definedOriginalHeight = cWrapperData.getDefinedOriginalHeight();
        if (definedOriginalWidth == 0 || definedOriginalHeight == 0) {
            if (definedOriginalWidth == 0 && definedOriginalHeight == 0) {
                definedOriginalHeight = height;
                definedOriginalWidth = width2;
            } else if (definedOriginalWidth == 0) {
                definedOriginalWidth = (int) (((float) (definedOriginalHeight * width2)) / ((float) height));
            } else if (definedOriginalHeight == 0) {
                definedOriginalHeight = (int) (((float) (definedOriginalWidth * height)) / ((float) width2));
            }
        }
        if (!cWrapperData.getIsFullStretch()) {
            float min = Math.min(((float) frameLayout.getWidth()) / ((float) definedOriginalWidth), ((float) frameLayout.getHeight()) / ((float) definedOriginalHeight));
            i = (int) (((double) (((float) definedOriginalWidth) * min)) + 0.5d);
            i2 = (int) (((double) (min * ((float) definedOriginalHeight))) + 0.5d);
            switch (cWrapperData.getViewportAlignment()) {
                case o.b /*2*/:
                    width = (frameLayout.getWidth() - i) / 2;
                    i3 = (frameLayout.getHeight() - i2) / 2;
                    break;
                case o.c /*3*/:
                    width = frameLayout.getWidth() - i;
                    i3 = frameLayout.getHeight() - i2;
                    break;
                default:
                    width = 0;
                    break;
            }
        }
        i2 = height;
        i = width2;
        width = 0;
        cWrapperData.setScreenSize(definedOriginalWidth, definedOriginalHeight, i, i2, width2, height);
        setDisplay(width, i3, i, i2, cWrapperData);
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass2.a[rVar.ordinal()]) {
            case o.a /*1*/:
                this.d.setOnTouchListener(null);
                this.a.removeEventAll();
                return;
            case o.b /*2*/:
            case o.c /*3*/:
                this.a.removeEventAll();
                this.d.setOnTouchListener(this.b);
                return;
            case o.d /*4*/:
                this.d.setOnTouchListener(null);
                this.b.a();
                this.a.removeEventAll();
                this.d.setOnTouchListener(this.b);
                return;
            case f.bc /*5*/:
                ((FrameLayout) this.c.findViewById(CResource.R("R.id.GLViewLayout"))).removeView(this.d);
                return;
            default:
                return;
        }
    }

    public void requestRender() {
        this.d.requestRender();
    }

    public boolean setDisplay(int i, int i2, int i3, int i4, CWrapperData cWrapperData) {
        if (i < 0 || i2 < 0 || i + i3 > cWrapperData.getDeviceWidth() || i2 + i4 > cWrapperData.getDeviceHeight()) {
            return false;
        }
        cWrapperData.setScreenSize(i3, i4);
        final int i5 = i;
        final int i6 = i2;
        final int i7 = i3;
        final int i8 = i4;
        new Thread(this) {
            final /* synthetic */ CGameManager e;

            public void run() {
                final c cVar = new c();
                cVar.a();
                CFunction.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ AnonymousClass1 b;

                    public void run() {
                        FrameLayout frameLayout = (FrameLayout) this.b.e.c.findViewById(CResource.R("R.id.ViewPortPaddingLayout"));
                        FrameLayout frameLayout2 = (FrameLayout) this.b.e.c.findViewById(CResource.R("R.id.ViewPortSizeLayout"));
                        if (frameLayout == null || frameLayout2 == null) {
                            CFunction.setControlByPX((FrameLayout) this.b.e.c.findViewById(CResource.R("R.id.GLViewLayout")), this.b.e.d, i5, i6, i7, i8);
                        } else {
                            CFunction.setControlByPX(frameLayout, frameLayout2, i5, i6, i7, i8);
                        }
                        cVar.c();
                    }
                });
                cVar.b();
                CWrapperKernel.j();
            }
        }.start();
        return true;
    }
}
