package com.com2us.wrapper.kernel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import com.com2us.common.CommonTaskRunner;
import com.com2us.common.ICommonTaskRunnerListener;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.offerwall.OfferwallData;
import com.com2us.smon.google.service.util.GameHelper;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.com2us.wrapper.common.CCommonAPI;
import com.com2us.wrapper.font.CFont;
import com.com2us.wrapper.font.CSGLFont;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.function.CFunctionTaskRunner;
import com.com2us.wrapper.function.CResource;
import com.com2us.wrapper.game.CCustomGLSurfaceView;
import com.com2us.wrapper.game.CGameManager;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import com.com2us.wrapper.kernel.CWrapperKernel.s;
import com.com2us.wrapper.media.CSoundManager;
import com.com2us.wrapper.network.CHttpManager;
import com.com2us.wrapper.sensor.CSensor;
import com.com2us.wrapper.ui.CTextInput;
import com.com2us.wrapper.ui.CUserInput;
import com.com2us.wrapper.utility.CUtility;
import com.wellbia.xigncode.util.WBBase64;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.StringTokenizer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.f;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public abstract class CWrapperActivity extends Activity implements ICommonTaskRunnerListener, s {
    private AudioManager a = null;
    private int b = -1;
    private CFunctionTaskRunner c = null;
    private CWrapperTimer d = null;
    private CEventHandler e = null;
    private CGameManager f = null;
    private CSensor g = null;
    private CUserInput h = null;
    private CTextInput i = null;
    private CFont j = null;
    private CSoundManager k = null;
    private CHttpManager l = null;
    private CUtility m = null;
    protected Activity mActivity = null;
    protected CCustomGLSurfaceView mGLSurfaceView = null;
    private Object n = null;
    private ModuleManager o = ModuleManager.getInstance();
    private ProgressBar p = null;

    static /* synthetic */ class AnonymousClass8 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.ACTIVITY_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[r.ACTIVITY_RESUME.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[r.ACTIVITY_PAUSE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[r.ACTIVITY_STOP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[r.ACTIVITY_RESTART.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[r.ACTIVITY_DESTROY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[r.ACTIVITY_FOCUS_IN.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[r.ACTIVITY_FOCUS_OUT.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[r.NATIVE_STARTCLET_CALLED.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[r.NATIVE_PAUSECLET_CALLED.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[r.NATIVE_RESUMECLET_CALLED.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[r.NATIVE_DESTROYCLET_CALLED.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                a[r.APPLICATION_START.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                a[r.APPLICATION_RESUME_START.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                a[r.APPLICATION_STARTED.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                a[r.APPLICATION_RESUMED.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                a[r.APPLICATION_PAUSE_START.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
        }
    }

    private void a() {
        try {
            Class cls = Class.forName("com.com2us.wrapper.WrapperStatistics");
            cls.getMethod("setContext", new Class[]{Context.class}).invoke(cls, new Object[]{this.mActivity});
        } catch (Exception e) {
        }
    }

    private void a(float f) {
        if (f > 0.0f && f <= 1.0f) {
            Window window = getWindow();
            LayoutParams attributes = window.getAttributes();
            attributes.screenBrightness = f;
            window.setAttributes(attributes);
        }
    }

    private void a(int i) {
        this.a = (AudioManager) this.mActivity.getSystemService("audio");
        this.mActivity.setVolumeControlStream(3);
        switch (i) {
            case o.a /*1*/:
                this.b = this.a.getStreamVolume(3);
                return;
            default:
                this.b = -1;
                return;
        }
    }

    private void a(boolean z) {
        if (z) {
            getWindow().setFlags(ModuleConfig.ACTIVEUSER_MODULE, ModuleConfig.ACTIVEUSER_MODULE);
        }
    }

    private boolean a(CWrapperData cWrapperData) {
        return cWrapperData.isVersionValid();
    }

    private void b() {
        if (this.b != -1) {
            int streamVolume = this.a.getStreamVolume(3);
            this.a.setStreamVolume(3, this.b, 0);
            this.b = streamVolume;
        }
    }

    private boolean b(CWrapperData cWrapperData) {
        String minimumCommonVersion = cWrapperData.getMinimumCommonVersion();
        String version = CommonTaskRunner.getVersion();
        StringTokenizer stringTokenizer = new StringTokenizer(minimumCommonVersion, ".", false);
        String str = i.a;
        int i = 0;
        while (i < 3) {
            i++;
            str = str + String.format("%03d", new Object[]{Integer.valueOf(stringTokenizer.nextToken())});
        }
        stringTokenizer = new StringTokenizer(version, ".", false);
        String str2 = i.a;
        for (i = 0; i < 3; i++) {
            str2 = str2 + String.format("%03d", new Object[]{Integer.valueOf(stringTokenizer.nextToken())});
        }
        return Integer.valueOf(str).intValue() <= Integer.valueOf(str2).intValue();
    }

    private void c(CWrapperData cWrapperData) {
        this.e = new CEventHandler();
        this.f = new CGameManager(this.mActivity, this.mGLSurfaceView, this.e, cWrapperData);
        this.d = new CWrapperTimer(this.mGLSurfaceView);
        this.g = new CSensor(this.mActivity);
        this.h = new CUserInput((FrameLayout) findViewById(CResource.R("R.id.UserInputLayout")), cWrapperData.getTextEncodingType());
        this.i = new CTextInput((FrameLayout) findViewById(CResource.R("R.id.TextInputLayout")), cWrapperData.getTextEncodingType());
        if (!cWrapperData.getUseSGL()) {
            switch (cWrapperData.getRenderType()) {
                case g.a /*0*/:
                    break;
                case o.c /*3*/:
                    this.j = new CSGLFont(ModuleConfig.MERCURY_MODULE, ModuleConfig.MERCURY_MODULE, cWrapperData.getTextEncodingType());
                    break;
                default:
                    this.j = new CFont(this.mActivity, cWrapperData.getTextEncodingType(), cWrapperData);
                    break;
            }
        }
        this.j = new CSGLFont(ModuleConfig.MERCURY_MODULE, ModuleConfig.MERCURY_MODULE, cWrapperData.getTextEncodingType());
        this.k = new CSoundManager();
        this.l = new CHttpManager(this);
        this.m = new CUtility(this.mActivity, cWrapperData.getTextEncodingType());
    }

    private void d(CWrapperData cWrapperData) {
        a();
        e(cWrapperData);
    }

    private void e(CWrapperData cWrapperData) {
        Class cls;
        Class cls2;
        int i = 3;
        int i2 = 2;
        int i3 = 1;
        int i4 = 0;
        try {
            cls = Class.forName("com.com2us.arm.CArmCreator");
            cls2 = Class.forName("com.com2us.arm.CArmCreator$IArmCallback");
        } catch (Exception e) {
            cls2 = null;
            cls = null;
        }
        if (cls != null && cls2 != null) {
            Class cls3;
            Class cls4;
            Class cls5;
            CWrapperKernel.a("ArmLibrary");
            int i5 = -1;
            if (null == null) {
                try {
                    cls3 = Class.forName("com.skt.arm.ArmListener");
                } catch (Exception e2) {
                    i4 = i5;
                    cls3 = null;
                }
            } else {
                i4 = i5;
                cls3 = null;
            }
            if (cls3 == null) {
                try {
                    cls4 = Class.forName("com.kt.olleh.potection.ProtectionService");
                } catch (Exception e3) {
                    i3 = i4;
                    cls4 = null;
                }
            } else {
                i3 = i4;
                cls4 = cls3;
            }
            if (cls4 == null) {
                try {
                    cls5 = Class.forName("com.lgt.arm.ArmInterface");
                } catch (Exception e4) {
                    i2 = i3;
                    cls5 = null;
                }
            } else {
                i2 = i3;
                cls5 = cls4;
            }
            if (cls5 == null) {
                try {
                    Class.forName("com.android.vending.licensing.LicenseChecker");
                } catch (Exception e5) {
                    i = i2;
                }
            } else {
                i = i2;
            }
            try {
                String marketArmId = cWrapperData.getMarketArmId();
                Object newProxyInstance = Proxy.newProxyInstance(cls2.getClassLoader(), new Class[]{cls2}, new InvocationHandler(this) {
                    final /* synthetic */ CWrapperActivity c;

                    public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
                        String name = method.getName();
                        if (!name.equals("onResult")) {
                            return name.equals("toString") ? "IArmCallback InvocationHandler" : null;
                        } else {
                            if (((Boolean) objArr[0]).booleanValue()) {
                                this.c.mActivity.runOnUiThread(new Runnable(this) {
                                    final /* synthetic */ AnonymousClass2 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void run() {
                                        try {
                                            cls.getMethod("release", new Class[0]).invoke(this.a.c.n, new Object[0]);
                                        } catch (Exception e) {
                                        }
                                        this.a.c.n = null;
                                        CWrapperKernel.b("ArmLibrary");
                                    }
                                });
                            } else if (i != 3) {
                                new Thread(this) {
                                    final /* synthetic */ AnonymousClass2 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void run() {
                                        try {
                                            Thread.sleep(5000);
                                        } catch (Exception e) {
                                        }
                                        try {
                                            cls.getMethod("release", new Class[0]).invoke(this.a.c.n, new Object[0]);
                                        } catch (Exception e2) {
                                        }
                                        this.a.c.n = null;
                                        CWrapperKernel.onExitApplication();
                                    }
                                }.start();
                            }
                            return null;
                        }
                    }
                });
                this.n = cls.getConstructor(new Class[]{Activity.class, cls2, String.class, Integer.TYPE}).newInstance(new Object[]{this.mActivity, newProxyInstance, marketArmId, Integer.valueOf(i)});
                cls.getMethod("excute", new Class[0]).invoke(this.n, new Object[0]);
            } catch (Exception e6) {
                e6.printStackTrace();
            }
        }
    }

    public EGL10 getEgl() {
        return this.mGLSurfaceView.a();
    }

    public EGLConfig getEglConfig() {
        return this.mGLSurfaceView.c();
    }

    public EGLContext getEglContext() {
        return this.mGLSurfaceView.d();
    }

    public EGLDisplay getEglDisplay() {
        return this.mGLSurfaceView.b();
    }

    public void initialize(CWrapperData cWrapperData) {
        CWrapperKernel.a("InitializeWrapper");
        CWrapperKernel.a("CommonLibrary");
        CommonTaskRunner.start(this.mActivity, this);
        a(cWrapperData.getKeepScreenOnForcedly());
        a((float) cWrapperData.getScreenBrightness());
        a(cWrapperData.getVolumeStyle());
        c(cWrapperData);
        if (!a(cWrapperData)) {
            this.m.showDialogAndExit("Version is not matched", ".jar \ub77c\uc774\ube0c\ub7ec\ub9ac\uc640 .a \ub77c\uc774\ube0c\ub7ec\ub9ac\uc758 \ubc84\uc804\uc774 \ub2e4\ub985\ub2c8\ub2e4. \uac19\uc740 \ubc84\uc804\uc758 \ub77c\uc774\ube0c\ub7ec\ub9ac\ub97c \uc0ac\uc6a9\ud574\uc8fc\uc138\uc694.");
        } else if (b(cWrapperData)) {
            d(cWrapperData);
            CWrapperKernel.b("InitializeWrapper");
        } else {
            this.m.showDialogAndExit("Version is invalid", "C2SCommon \ub77c\uc774\ube0c\ub7ec\ub9ac \ubc84\uc804\uc774 \ub108\ubb34 \ub0ae\uc2b5\ub2c8\ub2e4. \uc5c5\ub370\uc774\ud2b8 \ud6c4 \uc0ac\uc6a9\ud574\uc8fc\uc138\uc694.");
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        this.o.onActivityResult(i, i2, intent);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mActivity = this;
        this.mGLSurfaceView = new CCustomGLSurfaceView(this.mActivity);
        this.c = new CFunctionTaskRunner(this.mActivity, this.mGLSurfaceView);
        if (CFunction.getSystemVersionCode() >= 9) {
            StrictMode.setThreadPolicy(new Builder().permitAll().build());
        }
        setContentView(CResource.R("R.layout.main"));
        this.p = (ProgressBar) findViewById(CResource.R("R.id.progressBar"));
        CWrapperKernel.a((s) this);
        CWrapperKernel.a(this.mActivity, this.mGLSurfaceView);
        new Thread(this) {
            final /* synthetic */ CWrapperActivity a;

            {
                this.a = r1;
            }

            public void run() {
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i2 <= 0 || r0 <= 0) {
                        FrameLayout frameLayout = (FrameLayout) this.a.mActivity.findViewById(CResource.R("R.id.DeviceLayout"));
                        i2 = frameLayout.getWidth();
                        i = frameLayout.getHeight();
                        try {
                            Thread.sleep(300);
                        } catch (Exception e) {
                        }
                    } else {
                        this.a.mActivity.runOnUiThread(new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                this.a.a.initialize(CWrapperData.getInstance());
                            }
                        });
                        return;
                    }
                }
            }
        }.start();
    }

    protected void onDestroy() {
        super.onDestroy();
        CWrapperKernel.f();
        this.c.uninitialize();
        CommonTaskRunner.stop();
        new Thread(this) {
            final /* synthetic */ CWrapperActivity a;

            {
                this.a = r1;
            }

            public void run() {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                }
                System.exit(0);
            }
        }.start();
    }

    public void onKernelStateChanged(r rVar) {
        switch (AnonymousClass8.a[rVar.ordinal()]) {
            case o.a /*1*/:
                this.o.onActivityStarted();
                break;
            case o.b /*2*/:
                this.o.onActivityResumed();
                break;
            case o.c /*3*/:
                this.o.onActivityPaused();
                break;
            case o.d /*4*/:
                this.o.onActivityStopped();
                break;
            case f.bc /*5*/:
                this.o.onActivityRestarted();
                break;
            case f.aL /*6*/:
                this.o.onActivityDestroyed();
                this.o.destroy();
                break;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
                this.o.onWindowFocusChanged(true);
                break;
            case WBBase64.URL_SAFE /*8*/:
                this.o.onWindowFocusChanged(false);
                break;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoTextColor /*9*/:
                this.o.onCletStarted();
                break;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoImageType /*10*/:
                this.o.onCletPaused();
                break;
            case R.styleable.MapAttrs_uiZoomGestures /*11*/:
                this.o.onCletResumed();
                break;
            case R.styleable.MapAttrs_useViewLifecycle /*12*/:
                this.o.onCletDestroyed();
                break;
        }
        switch (AnonymousClass8.a[rVar.ordinal()]) {
            case R.styleable.MapAttrs_zOrderOnTop /*13*/:
            case f.r /*14*/:
                this.mActivity.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ CWrapperActivity a;

                    {
                        this.a = r1;
                    }

                    public synchronized void run() {
                        this.a.p.setVisibility(0);
                    }
                });
                break;
            case GameHelper.CLIENT_ALL /*15*/:
            case WBBase64.NO_CLOSE /*16*/:
                this.mActivity.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ CWrapperActivity a;

                    {
                        this.a = r1;
                    }

                    public synchronized void run() {
                        this.a.p.setVisibility(8);
                    }
                });
                break;
        }
        switch (AnonymousClass8.a[rVar.ordinal()]) {
            case f.r /*14*/:
            case OfferwallData.ADDITIONAL_INFO /*17*/:
                new Thread(this) {
                    final /* synthetic */ CWrapperActivity a;

                    {
                        this.a = r1;
                    }

                    public synchronized void run() {
                        try {
                            Thread.sleep(400);
                        } catch (Exception e) {
                        }
                        this.a.b();
                    }
                }.start();
                return;
            default:
                return;
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!(i == 25 || i == 24)) {
            if (this.p.getVisibility() == 0) {
                return false;
            }
            this.e.addEvent(4096, i, -1, -1, -1);
        }
        return i == 4 ? false : super.onKeyDown(i, keyEvent);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (!(i == 25 || i == 24)) {
            if (this.p.getVisibility() == 0) {
                return false;
            }
            this.e.addEvent(4097, i, -1, -1, -1);
        }
        return super.onKeyUp(i, keyEvent);
    }

    protected void onModuleManagerInitialize(ModuleManager moduleManager) {
        CWrapperData instance = CWrapperData.getInstance();
        moduleManager.setExitAppIfCracked(instance.getTerminateIfCracked());
        moduleManager.setWrapperDevelopmentRevision(instance.getDevelopmentRevision());
        moduleManager.load(this.mActivity, this.mGLSurfaceView);
        moduleManager.onActivityCreated();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.o.onNewIntent(intent);
    }

    protected void onPause() {
        super.onPause();
        CWrapperKernel.c();
    }

    protected void onRestart() {
        super.onRestart();
        CWrapperKernel.e();
    }

    protected void onResume() {
        super.onResume();
        CWrapperKernel.b();
    }

    protected void onStart() {
        super.onStart();
        CWrapperKernel.a();
    }

    public void onStartTaskCompleted() {
        CFunction.runOnUiThread(new Runnable(this) {
            final /* synthetic */ CWrapperActivity a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.onModuleManagerInitialize(this.a.o);
                CCommonAPI.initialize(this.a.mActivity);
                CWrapperKernel.b("CommonLibrary");
            }
        });
    }

    protected void onStop() {
        super.onStop();
        CWrapperKernel.d();
    }

    public void onStopTaskCompleted() {
        CCommonAPI.uninitialize();
    }

    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        CWrapperKernel.a(z);
    }
}
