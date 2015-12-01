package com.com2us.wrapper.kernel;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.game.CCustomGLSurfaceView;
import defpackage.d$a;
import defpackage.d$b;
import java.util.LinkedList;

public class CWrapperKernel {
    private static LinkedList<s> a = new LinkedList();
    private static q b = new q();
    private static IntentFilter c = new IntentFilter();
    private static LinkedList<String> d = new LinkedList();
    private static LinkedList<Boolean> e = new LinkedList();
    private static KeyguardManager f = null;
    private static boolean g = false;
    private static boolean h = false;
    private static boolean i = true;
    private static boolean j = false;
    private static Object k = new Object();
    private static Object l = new Object();
    private static Activity m = null;
    private static CCustomGLSurfaceView n = null;

    public interface s {
        void onKernelStateChanged(r rVar);
    }

    static class a extends d$a {
        private a() {
            super(d$b.ACTIVITY_DESTROY);
        }

        public void a() {
            CWrapperKernel.a.clear();
            CWrapperKernel.n.onPause();
        }
    }

    static class b extends d$a {
        private b() {
            super(d$b.ACTIVITY_FINISH);
        }

        public void a() {
            CWrapperKernel.m.finish();
            new Thread(this) {
                final /* synthetic */ b a;

                {
                    this.a = r1;
                }

                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                    }
                    if (!CWrapperKernel.j) {
                        System.exit(0);
                    }
                }
            }.start();
        }
    }

    static class c extends d$a {
        private c() {
            super(d$b.ACTIVITY_PAUSE);
        }

        public void a() {
            CWrapperKernel.n.a(new Runnable(this) {
                final /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public void run() {
                    defpackage.d.a(d$b.NATIVE_PAUSECLET);
                }
            });
        }
    }

    static class d extends d$a {
        private d() {
            super(d$b.ACTIVITY_RESUME);
        }

        public void a() {
            if ((!CWrapperData.getInstance().getUseSGL() || CFunction.getSystemVersionCode() < 8) && !CWrapperData.getInstance().getUseSelfTextureCache()) {
                CWrapperKernel.n.onResume();
            }
            new Thread(this) {
                final /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public void run() {
                    CWrapperKernel.i = false;
                    while (!CWrapperKernel.i) {
                        try {
                            Thread.sleep(300);
                        } catch (Exception e) {
                        }
                        CWrapperKernel.n.a(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                CWrapperKernel.nativeCheckQueueEvent();
                            }
                        });
                    }
                    CWrapperKernel.n.a(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            defpackage.d.a(d$b.NATIVE_RESUME);
                        }
                    });
                }
            }.start();
        }
    }

    static class e extends d$a {
        private e() {
            super(d$b.NATIVE_DESTROY);
        }

        public void a() {
            CWrapperKernel.nativeDestroy();
            CWrapperKernel.b(r.NATIVE_DESTROY_CALLED);
            defpackage.d.a(d$b.ACTIVITY_FINISH);
        }
    }

    static class f extends d$a {
        private f() {
            super(d$b.NATIVE_DESTROYCLET);
        }

        public void a() {
            CWrapperKernel.nativeDestroyClet();
            CWrapperKernel.b(r.NATIVE_DESTROYCLET_CALLED);
            defpackage.d.a(d$b.NATIVE_DESTROY);
        }
    }

    static class g extends d$a {
        private g() {
            super(d$b.NATIVE_PAUSE);
        }

        public void a() {
            CWrapperKernel.nativePause();
            CWrapperKernel.b(r.NATIVE_PAUSE_CALLED);
            defpackage.d.a(d$b.PAUSED);
        }
    }

    static class h extends d$a {
        private h() {
            super(d$b.NATIVE_PAUSECLET);
        }

        public void a() {
            CWrapperKernel.nativePauseClet();
            CWrapperKernel.b(r.NATIVE_PAUSECLET_CALLED);
            defpackage.d.a(d$b.NATIVE_PAUSE);
        }
    }

    static class i extends d$a {
        private i() {
            super(d$b.NATIVE_RESUME);
        }

        public void a() {
            CWrapperKernel.nativeResume();
            CWrapperKernel.b(r.NATIVE_RESUME_CALLED);
            defpackage.d.a(d$b.NATIVE_WAIT_UNLOCK_SCREEN);
        }
    }

    static class j extends d$a {
        private j() {
            super(d$b.NATIVE_RESUMECLET);
        }

        public void a() {
            CWrapperKernel.nativeResumeClet();
            CWrapperKernel.b(r.NATIVE_RESUMECLET_CALLED);
            CWrapperKernel.b(r.APPLICATION_RESUMED);
            defpackage.d.a(d$b.RUNNING);
        }
    }

    static class k extends d$a {
        private k() {
            super(d$b.NATIVE_START);
        }

        public void a() {
            CWrapperKernel.nativeStart();
            CWrapperKernel.b(r.NATIVE_START_CALLED);
            defpackage.d.a(d$b.WAIT_UNLOCK_STARTCLET);
        }
    }

    static class l extends d$a {
        private l() {
            super(d$b.NATIVE_STARTCLET);
        }

        public void a() {
            CWrapperKernel.nativeStartClet();
            CWrapperKernel.b(r.NATIVE_STARTCLET_CALLED);
            CWrapperKernel.b(r.APPLICATION_STARTED);
            defpackage.d.a(d$b.RUNNING);
        }
    }

    static class m extends d$a {
        private m() {
            super(d$b.PAUSED);
        }

        public void a() {
            CWrapperKernel.m.runOnUiThread(new Runnable(this) {
                final /* synthetic */ m a;

                {
                    this.a = r1;
                }

                public void run() {
                    try {
                        Thread.sleep(300);
                    } catch (Exception e) {
                    }
                    if ((!CWrapperData.getInstance().getUseSGL() || CFunction.getSystemVersionCode() < 8) && !CWrapperData.getInstance().getUseSelfTextureCache()) {
                        CWrapperKernel.n.onPause();
                    }
                    CWrapperKernel.b(r.APPLICATION_PAUSED);
                    CWrapperKernel.d(true);
                }
            });
        }
    }

    static class n extends d$a {
        private n() {
            super(d$b.RUNNING);
        }

        public void a() {
            CWrapperKernel.d(false);
        }
    }

    static class o extends d$a {
        private o() {
            super(d$b.WAIT_UNLOCK_STARTCLET);
        }

        public void a() {
            CWrapperKernel.H();
        }
    }

    static class p extends d$a {
        private p() {
            super(d$b.NATIVE_WAIT_UNLOCK_SCREEN);
        }

        public void a() {
            CWrapperKernel.I();
        }
    }

    static class q extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.USER_PRESENT")) {
                CWrapperKernel.m.unregisterReceiver(CWrapperKernel.b);
                CWrapperKernel.I();
            }
        }
    }

    public enum r {
        ACTIVITY_CREATE,
        ACTIVITY_START,
        ACTIVITY_RESUME,
        ACTIVITY_PAUSE,
        ACTIVITY_STOP,
        ACTIVITY_RESTART,
        ACTIVITY_DESTROY,
        ACTIVITY_FOCUS_IN,
        ACTIVITY_FOCUS_OUT,
        RENDERER_SURFACE_CHANGED,
        RENDERER_SURFACE_CREATED,
        GLSURFACEVIEW_SIZE_CHANGED,
        APPLICATION_START,
        APPLICATION_STARTED,
        APPLICATION_PAUSE_START,
        APPLICATION_PAUSED,
        APPLICATION_RESUME_START,
        APPLICATION_RESUMED,
        APPLICATION_EXIT_START,
        APPLICATION_EXITED,
        NATIVE_START_CALLED,
        NATIVE_PAUSE_CALLED,
        NATIVE_RESUME_CALLED,
        NATIVE_DESTROY_CALLED,
        NATIVE_STARTCLET_CALLED,
        NATIVE_PAUSECLET_CALLED,
        NATIVE_RESUMECLET_CALLED,
        NATIVE_DESTROYCLET_CALLED
    }

    static {
        k kVar = new k();
        o oVar = new o();
        l lVar = new l();
        n nVar = new n();
        c cVar = new c();
        h hVar = new h();
        g gVar = new g();
        m mVar = new m();
        d dVar = new d();
        i iVar = new i();
        p pVar = new p();
        j jVar = new j();
        f fVar = new f();
        e eVar = new e();
        b bVar = new b();
        a aVar = new a();
    }

    private CWrapperKernel() {
    }

    private static void H() {
        synchronized (l) {
            if (defpackage.d.b() == d$b.WAIT_UNLOCK_STARTCLET && d.size() == 0) {
                n.a(new Runnable() {
                    public void run() {
                        defpackage.d.a(d$b.NATIVE_STARTCLET);
                    }
                });
            }
        }
    }

    private static void I() {
        synchronized (l) {
            if (f.inKeyguardRestrictedInputMode()) {
                m.registerReceiver(b, c);
            } else {
                n.a(new Runnable() {
                    public void run() {
                        synchronized (CWrapperKernel.k) {
                            if (CWrapperKernel.e.size() != 0) {
                                boolean booleanValue = ((Boolean) CWrapperKernel.e.removeLast()).booleanValue();
                                CWrapperKernel.e.clear();
                                if (booleanValue) {
                                    defpackage.d.a(d$b.NATIVE_PAUSE);
                                } else {
                                    defpackage.d.a(d$b.NATIVE_RESUMECLET);
                                }
                            } else {
                                defpackage.d.a(d$b.NATIVE_RESUMECLET);
                            }
                        }
                    }
                });
            }
        }
    }

    public static void a() {
        b(r.ACTIVITY_START);
    }

    public static void a(Activity activity, CCustomGLSurfaceView cCustomGLSurfaceView) {
        m = activity;
        n = cCustomGLSurfaceView;
        f = (KeyguardManager) m.getSystemService("keyguard");
        c.addAction("android.intent.action.USER_PRESENT");
        b(r.ACTIVITY_CREATE);
        b(r.APPLICATION_START);
    }

    public static void a(s sVar) {
        a.add(sVar);
    }

    public static void a(String str) {
        d.add(str);
    }

    public static void a(boolean z) {
        if (z) {
            b(r.ACTIVITY_FOCUS_IN);
        } else {
            b(r.ACTIVITY_FOCUS_OUT);
        }
    }

    public static void b() {
        b(r.ACTIVITY_RESUME);
        if (g) {
            synchronized (k) {
                e.addLast(Boolean.valueOf(false));
            }
            d(true);
            return;
        }
        g = true;
    }

    private static void b(r rVar) {
        for (int i = 0; i < a.size(); i++) {
            ((s) a.get(i)).onKernelStateChanged(rVar);
        }
    }

    public static void b(String str) {
        d.remove(str);
        if (d.size() == 0) {
            H();
        }
    }

    public static void c() {
        b(r.ACTIVITY_PAUSE);
        if (!h) {
            synchronized (k) {
                e.addLast(Boolean.valueOf(true));
            }
            d(false);
        }
    }

    public static void confirmQueueEvent() {
        i = true;
    }

    public static void d() {
        b(r.ACTIVITY_STOP);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void d(boolean r4) {
        /*
        r3 = 1;
        r1 = k;
        monitor-enter(r1);
        if (r4 != r3) goto L_0x0010;
    L_0x0006:
        r0 = defpackage.d.b();	 Catch:{ all -> 0x001c }
        r2 = defpackage.d$b.PAUSED;	 Catch:{ all -> 0x001c }
        if (r0 == r2) goto L_0x0010;
    L_0x000e:
        monitor-exit(r1);	 Catch:{ all -> 0x001c }
    L_0x000f:
        return;
    L_0x0010:
        if (r4 != 0) goto L_0x001f;
    L_0x0012:
        r0 = defpackage.d.b();	 Catch:{ all -> 0x001c }
        r2 = defpackage.d$b.RUNNING;	 Catch:{ all -> 0x001c }
        if (r0 == r2) goto L_0x001f;
    L_0x001a:
        monitor-exit(r1);	 Catch:{ all -> 0x001c }
        goto L_0x000f;
    L_0x001c:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x001c }
        throw r0;
    L_0x001f:
        r0 = e;	 Catch:{ all -> 0x001c }
        r0 = r0.size();	 Catch:{ all -> 0x001c }
        if (r0 == 0) goto L_0x004f;
    L_0x0027:
        r0 = e;	 Catch:{ all -> 0x001c }
        r0 = r0.removeLast();	 Catch:{ all -> 0x001c }
        r0 = (java.lang.Boolean) r0;	 Catch:{ all -> 0x001c }
        r0 = r0.booleanValue();	 Catch:{ all -> 0x001c }
        r2 = e;	 Catch:{ all -> 0x001c }
        r2.clear();	 Catch:{ all -> 0x001c }
        if (r0 != r4) goto L_0x0043;
    L_0x003a:
        r2 = e;	 Catch:{ all -> 0x001c }
        r0 = java.lang.Boolean.valueOf(r0);	 Catch:{ all -> 0x001c }
        r2.addLast(r0);	 Catch:{ all -> 0x001c }
    L_0x0043:
        if (r4 != r3) goto L_0x0051;
    L_0x0045:
        r0 = com.com2us.wrapper.kernel.CWrapperKernel.r.APPLICATION_RESUME_START;	 Catch:{ all -> 0x001c }
        b(r0);	 Catch:{ all -> 0x001c }
        r0 = defpackage.d$b.ACTIVITY_RESUME;	 Catch:{ all -> 0x001c }
        defpackage.d.a(r0);	 Catch:{ all -> 0x001c }
    L_0x004f:
        monitor-exit(r1);	 Catch:{ all -> 0x001c }
        goto L_0x000f;
    L_0x0051:
        r0 = com.com2us.wrapper.kernel.CWrapperKernel.r.APPLICATION_PAUSE_START;	 Catch:{ all -> 0x001c }
        b(r0);	 Catch:{ all -> 0x001c }
        goto L_0x004f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.wrapper.kernel.CWrapperKernel.d(boolean):void");
    }

    public static void e() {
        b(r.ACTIVITY_RESTART);
    }

    public static void f() {
        j = true;
        b(r.ACTIVITY_DESTROY);
        b(r.APPLICATION_EXITED);
        defpackage.d.a(d$b.ACTIVITY_DESTROY);
        defpackage.d.a();
    }

    public static void g() {
        nativeProcess();
    }

    public static void h() {
        b(r.RENDERER_SURFACE_CHANGED);
    }

    public static void i() {
        b(r.RENDERER_SURFACE_CREATED);
        synchronized (l) {
            new Thread() {
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (Exception e) {
                    }
                    CWrapperKernel.n.a(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            if (defpackage.d.b() == d$b.INITIAL) {
                                defpackage.d.a(d$b.NATIVE_START);
                            } else {
                                CWrapperKernel.nativeSurfaceRecreated();
                            }
                        }
                    });
                }
            }.start();
        }
    }

    public static void j() {
        CFunction.runOnGlThread(new Runnable() {
            public void run() {
                CWrapperKernel.nativeOnScreenSizeChanged();
            }
        });
        b(r.GLSURFACEVIEW_SIZE_CHANGED);
    }

    public static void k() {
        defpackage.d.a(d$b.ACTIVITY_PAUSE);
    }

    public static void l() {
        n.a(new Runnable() {
            public void run() {
                defpackage.d.a(d$b.NATIVE_DESTROYCLET);
            }
        });
    }

    private static native void nativeCheckQueueEvent();

    private static native void nativeDestroy();

    private static native void nativeDestroyClet();

    private static native void nativeOnScreenSizeChanged();

    private static native void nativePause();

    private static native void nativePauseClet();

    private static native void nativeProcess();

    private static native void nativeResume();

    private static native void nativeResumeClet();

    private static native void nativeStart();

    private static native void nativeStartClet();

    private static native void nativeSurfaceRecreated();

    public static void onExitApplication() {
        h = true;
        b(r.APPLICATION_EXIT_START);
    }
}
