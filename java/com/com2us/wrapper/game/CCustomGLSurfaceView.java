package com.com2us.wrapper.game;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.EGLContextFactory;
import com.com2us.wrapper.kernel.CWrapperData;
import com.com2us.wrapper.kernel.CWrapperKernel;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import com.com2us.wrapper.kernel.CWrapperKernel.s;
import java.util.LinkedList;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import jp.co.dimage.android.o;

public class CCustomGLSurfaceView extends GLSurfaceView implements s {
    private static EGL10 c = null;
    private static EGLDisplay d = null;
    private static EGLConfig e = null;
    private static EGLContext f = null;
    private LinkedList<Runnable> a = new LinkedList();
    private boolean b = false;

    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_PAUSE_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[r.NATIVE_START_CALLED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[r.APPLICATION_RESUMED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[r.APPLICATION_EXITED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public CCustomGLSurfaceView(Context context) {
        super(context);
        CWrapperKernel.a((s) this);
        setEGLContextFactory(new EGLContextFactory(this) {
            final /* synthetic */ CCustomGLSurfaceView a;

            {
                this.a = r1;
            }

            public EGLContext createContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig) {
                CCustomGLSurfaceView.c = egl10;
                CCustomGLSurfaceView.d = eGLDisplay;
                CCustomGLSurfaceView.e = eGLConfig;
                CCustomGLSurfaceView.f = CCustomGLSurfaceView.c.eglCreateContext(CCustomGLSurfaceView.d, CCustomGLSurfaceView.e, EGL10.EGL_NO_CONTEXT, null);
                return CCustomGLSurfaceView.f;
            }

            public void destroyContext(EGL10 egl10, EGLDisplay eGLDisplay, EGLContext eGLContext) {
                egl10.eglDestroyContext(eGLDisplay, eGLContext);
            }
        });
    }

    public EGL10 a() {
        return c;
    }

    public synchronized void a(Runnable runnable) {
        super.queueEvent(runnable);
    }

    public EGLDisplay b() {
        return d;
    }

    public EGLConfig c() {
        return e;
    }

    public EGLContext d() {
        return f;
    }

    public void onKernelStateChanged(r rVar) {
        switch (AnonymousClass2.a[rVar.ordinal()]) {
            case o.a /*1*/:
                this.b = false;
                return;
            case o.b /*2*/:
            case o.c /*3*/:
                synchronized (this) {
                    this.b = true;
                    if (this.b) {
                        while (this.a.size() != 0) {
                            queueEvent((Runnable) this.a.removeFirst());
                        }
                    }
                }
                return;
            case o.d /*4*/:
                this.a.clear();
                return;
            default:
                return;
        }
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i > 0 && i2 > 0) {
            CWrapperData.getInstance().setScreenSize(i, i2);
            CWrapperKernel.j();
        }
    }

    public synchronized void queueEvent(Runnable runnable) {
        if (this.b) {
            super.queueEvent(runnable);
        } else {
            this.a.addLast(runnable);
        }
    }
}
