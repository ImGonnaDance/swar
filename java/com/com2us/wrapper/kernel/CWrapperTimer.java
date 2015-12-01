package com.com2us.wrapper.kernel;

import com.com2us.wrapper.game.CCustomGLSurfaceView;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import defpackage.c;
import jp.co.dimage.android.o;

public class CWrapperTimer extends CWrapper {
    private a a = null;
    private c b = new c();
    private CCustomGLSurfaceView c = null;
    private Thread d = new Thread(new Runnable(this) {
        final /* synthetic */ CWrapperTimer a;

        {
            this.a = r1;
        }

        public void run() {
            while (this.a.a != a.EXIT) {
                if (this.a.a == a.WAIT_START) {
                    synchronized (this.a.d) {
                        try {
                            this.a.a = a.PAUSED;
                            CWrapperKernel.k();
                            this.a.d.wait();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                this.a.b.a();
                this.a.c.a(new Runnable(this) {
                    final /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        CWrapperKernel.g();
                        this.a.a.b.c();
                    }
                });
                this.a.b.b();
            }
            CWrapperKernel.l();
        }
    });

    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[r.APPLICATION_PAUSE_START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[r.APPLICATION_RESUMED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[r.APPLICATION_EXIT_START.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    enum a {
        RUNNING,
        WAIT_START,
        PAUSED,
        EXIT
    }

    public CWrapperTimer(CCustomGLSurfaceView cCustomGLSurfaceView) {
        super(false);
        this.c = cCustomGLSurfaceView;
    }

    public synchronized void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass2.a[rVar.ordinal()]) {
            case o.a /*1*/:
                if (this.a == null) {
                    this.a = a.RUNNING;
                    this.d.start();
                    break;
                }
                break;
            case o.b /*2*/:
                if (this.a == a.RUNNING) {
                    this.a = a.WAIT_START;
                    break;
                }
                break;
            case o.c /*3*/:
                break;
            case o.d /*4*/:
                if (this.a == null) {
                    CWrapperKernel.l();
                }
                this.a = a.EXIT;
                break;
        }
        while (this.a != a.PAUSED) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
        synchronized (this.d) {
            this.d.notifyAll();
        }
        this.a = a.RUNNING;
    }
}
