package com.chartboost.sdk;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBLogging.Level;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.c;
import com.chartboost.sdk.Libraries.g;
import com.chartboost.sdk.Libraries.k;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.a.b;
import com.chartboost.sdk.impl.ae;
import com.chartboost.sdk.impl.af;
import com.chartboost.sdk.impl.aw;
import com.chartboost.sdk.impl.az;
import com.chartboost.sdk.impl.ba;
import com.chartboost.sdk.impl.ba.d;
import com.chartboost.sdk.impl.bb;
import com.chartboost.sdk.impl.bd;
import com.chartboost.sdk.impl.be;
import com.chartboost.sdk.impl.bh;
import com.chartboost.sdk.impl.m;
import com.com2us.module.manager.ModuleConfig;
import java.util.Locale;

public final class Chartboost {
    protected static Handler a;
    protected static k b = null;
    private static volatile Chartboost c = null;
    private static CBImpressionActivity d = null;
    private static com.chartboost.sdk.Model.a e = null;
    private static az f = null;
    private static bb g = null;
    private static m h = null;
    private static com.chartboost.sdk.Tracking.a i = null;
    private static boolean j = false;
    private static SparseBooleanArray k = new SparseBooleanArray();
    private static e l = null;
    private static c m = null;
    private static boolean n = false;
    private static Runnable o;

    public enum CBFramework {
        CBFrameworkUnity("Unity"),
        CBFrameworkCorona("Corona"),
        CBFrameworkAir("AIR"),
        CBFrameworkGameSalad("GameSalad");
        
        private final String a;

        private CBFramework(String s) {
            this.a = s;
        }

        public String toString() {
            return this.a;
        }
    }

    private static class a implements Runnable {
        private int a;
        private int b;
        private int c;

        private a a() {
            return b.d();
        }

        private a() {
            int i = -1;
            a a = a();
            this.a = Chartboost.d == null ? -1 : Chartboost.d.hashCode();
            this.b = Chartboost.b == null ? -1 : Chartboost.b.hashCode();
            if (a != null) {
                i = a.hashCode();
            }
            this.c = i;
        }

        public void run() {
            a a = a();
            if (b.k() != null) {
                Chartboost.clearCache();
            }
            if (Chartboost.b != null && Chartboost.b.hashCode() == this.b) {
                Chartboost.b = null;
            }
            if (Chartboost.d != null && Chartboost.d.hashCode() == this.a) {
                Chartboost.d = null;
            }
            if (a != null && a.hashCode() == this.c) {
                b.a(null);
            }
        }
    }

    private Chartboost(Activity app, String appId, String appSignature) {
        b.b = app.getApplication();
        b.a = app.getApplicationContext();
        c = this;
        a = new Handler();
        CBUtility.a(a);
        f = az.a();
        l = e.a();
        g = bb.a(b.a);
        h = g.a();
        m = c.a();
        i = com.chartboost.sdk.Tracking.a.a();
        f.a(b.a);
        o = new a();
        b.a(appId);
        b.b(appSignature);
        c.a();
        be.a();
    }

    public static void startWithAppId(final Activity activity, final String appId, final String appSignature) {
        if (c == null) {
            synchronized (Chartboost.class) {
                if (c == null) {
                    if (activity == null && !(activity instanceof Activity)) {
                        throw new RuntimeException("Activity object is null. Please pass a valid activity object");
                    } else if (appId == null || appSignature == null) {
                        throw new RuntimeException("appId or appSignature is null. Please pass a valid id's");
                    } else {
                        a(new Runnable() {
                            public void run() {
                                Chartboost.c = new Chartboost(activity, appId, appSignature);
                            }
                        });
                    }
                }
            }
        }
    }

    public static void onCreate(final Activity activity) {
        b.o();
        a(new Runnable() {
            public void run() {
                Chartboost.d(activity);
            }
        });
    }

    private static void d(Activity activity) {
        if (!(b == null || b.b(activity) || !m())) {
            f(b);
            c(b, false);
        }
        a.removeCallbacks(o);
        b = k.a(activity);
        if (!getImpressionsUseActivities()) {
            createSurfaceView(activity);
        }
        bb.d();
    }

    public static void onStart(final Activity activity) {
        b.o();
        a(new Runnable() {
            public void run() {
                Chartboost.a.removeCallbacks(Chartboost.o);
                if (!(Chartboost.b == null || Chartboost.b.b(activity) || !Chartboost.m())) {
                    Chartboost.f(Chartboost.b);
                    Chartboost.c(Chartboost.b, false);
                }
                Chartboost.b(activity, true);
                Chartboost.b = k.a(activity);
                if (!b.e()) {
                    Chartboost.a(activity);
                }
                Chartboost.f.b(b.a);
                Chartboost.h.a();
                Chartboost.g.f();
                Chartboost.l();
            }
        });
    }

    protected static void a(Activity activity) {
        b.a = activity.getApplicationContext();
        if (activity instanceof CBImpressionActivity) {
            l();
            a((CBImpressionActivity) activity);
        } else {
            b = k.a(activity);
            c(b, true);
        }
        a.removeCallbacks(o);
        if (activity != null && e(activity)) {
            b(k.a(activity), true);
            if (activity instanceof CBImpressionActivity) {
                n = false;
            }
            m.a(activity, e);
            e = null;
            com.chartboost.sdk.Model.a c = m.c();
            if (c != null) {
                c.u();
            }
        }
    }

    private static void l() {
        if (b.a == null) {
            throw new IllegalStateException("The context must be set through the Chartboost method onCreate() before calling startSession().");
        }
        b.c(true);
        i.h();
        com.chartboost.sdk.Tracking.a.b();
        final boolean i = com.chartboost.sdk.Tracking.a.i();
        b.a(new com.chartboost.sdk.b.a() {
            public void a() {
                if (i) {
                    ba baVar = new ba("api/install");
                    baVar.b(Chartboost.getValidContext());
                    baVar.a(true);
                    baVar.a(g.a("status", com.chartboost.sdk.Libraries.a.a));
                    baVar.a(new d(this) {
                        final /* synthetic */ AnonymousClass6 a;

                        {
                            this.a = r1;
                        }

                        public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar) {
                            if (CBUtility.a(b.k())) {
                                Object e = aVar.e("latest-sdk-version");
                                if (!TextUtils.isEmpty(e) && !e.equals("5.0.3")) {
                                    CBLogging.a(String.format(Locale.US, "Chartboost SDK is not up to date. (Current: %s, Latest: %s)\n Download latest SDK at:\n\thttps://www.chartboost.com/support/sdk_download/?os=ios", new Object[]{"5.0.3", e}));
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public static void onResume(final Activity activity) {
        b.o();
        a(new Runnable() {
            public void run() {
                k a = k.a(activity);
                if (Chartboost.d(a)) {
                    Chartboost.a(a);
                }
            }
        });
    }

    protected static void a(k kVar) {
        com.chartboost.sdk.Model.a c = c.a().c();
        if (c != null) {
            c.t();
        }
    }

    public static void onPause(final Activity activity) {
        b.o();
        a(new Runnable() {
            public void run() {
                k a = k.a(activity);
                if (Chartboost.d(a)) {
                    Chartboost.b(a);
                }
            }
        });
    }

    protected static void b(k kVar) {
        com.chartboost.sdk.Model.a c = c.a().c();
        if (c != null) {
            c.v();
        }
    }

    public static void onStop(final Activity activity) {
        b.o();
        a(new Runnable() {
            public void run() {
                k a = k.a(activity);
                if (Chartboost.d(a)) {
                    Chartboost.f(a);
                }
            }
        });
    }

    private static void f(k kVar) {
        if (!b.e()) {
            c(kVar);
        }
        if (!(kVar.get() instanceof CBImpressionActivity)) {
            c(kVar, false);
        }
        f.c(b.a);
        h.b();
        g.g();
        if (i == null) {
            i = com.chartboost.sdk.Tracking.a.a();
        }
        i.c();
    }

    protected static void c(k kVar) {
        e d = d();
        if (g(kVar) && d != null) {
            com.chartboost.sdk.Model.a c = c.a().c();
            if (c != null) {
                d.c(c);
                e = c;
            }
            b(kVar, false);
            if (kVar.get() instanceof CBImpressionActivity) {
                c();
            }
        }
        if (!(kVar.get() instanceof CBImpressionActivity)) {
            c(kVar, false);
        }
    }

    public static boolean onBackPressed() {
        b.o();
        if (b == null) {
            throw new IllegalStateException("The Chartboost methods onCreate(), onStart(), onStop(), and onDestroy() must be called in the corresponding methods of your activity in order for Chartboost to function properly.");
        } else if (!b.e()) {
            return a();
        } else {
            if (!n) {
                return false;
            }
            n = false;
            a();
            return true;
        }
    }

    protected static boolean a() {
        final c a = c.a();
        com.chartboost.sdk.Model.a c = a.c();
        if (c == null || c.b != b.DISPLAYED) {
            final e d = d();
            if (d == null || !d.b()) {
                return false;
            }
            a(new Runnable() {
                public void run() {
                    d.a(a.c(), true);
                }
            });
            return true;
        } else if (c.s()) {
            return true;
        } else {
            a(new Runnable() {
                public void run() {
                    a.b();
                }
            });
            return true;
        }
    }

    public static void onDestroy(final Activity activity) {
        b.o();
        a(new Runnable() {
            public void run() {
                if (Chartboost.b == null || Chartboost.b.b(activity)) {
                    Chartboost.a.removeCallbacks(Chartboost.o);
                    Chartboost.o = new a();
                    Chartboost.a.postDelayed(Chartboost.o, 10000);
                }
                Chartboost.b(activity);
            }
        });
    }

    protected static void b(Activity activity) {
        b(k.a(activity), false);
    }

    public static void didPassAgeGate(boolean pass) {
        b.d(pass);
    }

    public static void setShouldPauseClickForConfirmation(boolean shouldPause) {
        b.e(shouldPause);
    }

    public static void clearCache() {
        b.o();
        b.n();
        b.m();
        if (b.a == null) {
            throw new IllegalStateException("The context must be set through the Chartboost method onCreate() before calling clearImageCache().");
        }
        bd.a().b();
        af.h().a();
        ae.f().a();
        aw.f().a();
        com.chartboost.sdk.InPlay.a.b();
    }

    public static boolean hasRewardedVideo(String location) {
        b.o();
        b.n();
        b.m();
        return af.h().c(location);
    }

    public static void cacheRewardedVideo(String location) {
        b.o();
        b.n();
        b.m();
        if (TextUtils.isEmpty(location)) {
            CBLogging.b("Chartboost", "cacheRewardedVideo location cannot be empty");
            if (b.d() != null) {
                b.d().didFailToLoadRewardedVideo(location, CBImpressionError.INVALID_LOCATION);
                return;
            }
            return;
        }
        af.h().b(location);
    }

    public static void showRewardedVideo(String location) {
        b.o();
        b.n();
        b.m();
        if (TextUtils.isEmpty(location)) {
            CBLogging.b("Chartboost", "showRewardedVideo location cannot be empty");
            if (b.d() != null) {
                b.d().didFailToLoadRewardedVideo(location, CBImpressionError.INVALID_LOCATION);
                return;
            }
            return;
        }
        af.h().a(location);
    }

    public static boolean hasInterstitial(String location) {
        b.o();
        b.n();
        b.m();
        return ae.f().c(location);
    }

    public static void cacheInterstitial(String location) {
        b.o();
        b.n();
        b.m();
        if (TextUtils.isEmpty(location)) {
            CBLogging.b("Chartboost", "cacheInterstitial location cannot be empty");
            if (b.d() != null) {
                b.d().didFailToLoadInterstitial(location, CBImpressionError.INVALID_LOCATION);
                return;
            }
            return;
        }
        ae.f().b(location);
    }

    public static void showInterstitial(String location) {
        b.o();
        b.n();
        b.m();
        if (TextUtils.isEmpty(location)) {
            CBLogging.b("Chartboost", "showInterstitial location cannot be empty");
            if (b.d() != null) {
                b.d().didFailToLoadInterstitial(location, CBImpressionError.INVALID_LOCATION);
                return;
            }
            return;
        }
        ae.f().a(location);
    }

    public static boolean hasMoreApps(String location) {
        b.o();
        b.n();
        b.m();
        return aw.f().c(location);
    }

    public static void cacheMoreApps(String location) {
        b.o();
        b.n();
        b.m();
        if (TextUtils.isEmpty(location)) {
            CBLogging.b("Chartboost", "cacheMoreApps location cannot be empty");
            if (b.d() != null) {
                b.d().didFailToLoadMoreApps(location, CBImpressionError.INVALID_LOCATION);
                return;
            }
            return;
        }
        aw.f().b(location);
    }

    public static void showMoreApps(String location) {
        b.o();
        b.n();
        b.m();
        if (TextUtils.isEmpty(location)) {
            CBLogging.b("Chartboost", "showMoreApps location cannot be empty");
            if (b.d() != null) {
                b.d().didFailToLoadMoreApps(location, CBImpressionError.INVALID_LOCATION);
                return;
            }
            return;
        }
        aw.f().a(location);
    }

    public static void setFramework(CBFramework framework) {
        b.a(framework);
    }

    public static String getCustomId() {
        return b.j();
    }

    public static void setCustomId(String customID) {
        b.c(customID);
    }

    public static void setLoggingLevel(Level lvl) {
        b.a(lvl);
    }

    public static Level getLoggingLevel() {
        return b.i();
    }

    public static a getDelegate() {
        return b.d();
    }

    public static void setDelegate(ChartboostDelegate delegate) {
        b.a((a) delegate);
    }

    public static boolean getAutoCacheAds() {
        return b.g();
    }

    public static void setAutoCacheAds(boolean autoCacheAds) {
        b.b(autoCacheAds);
    }

    public static void setShouldRequestInterstitialsInFirstSession(boolean shouldRequest) {
        b.f(shouldRequest);
    }

    public static void setShouldDisplayLoadingViewForMoreApps(boolean shouldDisplay) {
        b.g(shouldDisplay);
    }

    public static void setShouldPrefetchVideoContent(boolean shouldPrefetch) {
        b.h(shouldPrefetch);
        if (shouldPrefetch) {
            be.a();
        } else {
            be.c();
        }
    }

    protected static Activity b() {
        if (b.e()) {
            return d;
        }
        return getHostActivity();
    }

    private static boolean e(Activity activity) {
        if (b.e()) {
            if (d == activity) {
                return true;
            }
            return false;
        } else if (b != null) {
            return b.b(activity);
        } else {
            if (activity != null) {
                return false;
            }
            return true;
        }
    }

    private static boolean g(k kVar) {
        if (b.e()) {
            if (kVar != null) {
                return kVar.b(d);
            }
            if (d == null) {
                return true;
            }
            return false;
        } else if (b != null) {
            return b.a(kVar);
        } else {
            if (kVar != null) {
                return false;
            }
            return true;
        }
    }

    protected static void a(CBImpressionActivity cBImpressionActivity) {
        if (!j) {
            b.a = cBImpressionActivity.getApplicationContext();
            d = cBImpressionActivity;
            j = true;
        }
        a.removeCallbacks(o);
    }

    protected static void c() {
        if (j) {
            d = null;
            j = false;
        }
    }

    protected static void a(com.chartboost.sdk.Model.a aVar) {
        boolean z = true;
        e d = d();
        if (d != null && d.c() && d.d().h() != aVar) {
            aVar.a(CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
        } else if (!b.e()) {
            d = d();
            if (d == null || !m()) {
                aVar.a(CBImpressionError.NO_HOST_ACTIVITY);
            } else {
                d.a(aVar);
            }
        } else if (j) {
            if (b() == null || d == null) {
                CBLogging.b("Chartboost", "Missing view controller to manage the open impression activity");
                aVar.a(CBImpressionError.INTERNAL);
                return;
            }
            d.a(aVar);
        } else if (m()) {
            Context hostActivity = getHostActivity();
            if (hostActivity == null) {
                CBLogging.b("Chartboost", "Failed to display impression as the host activity reference has been lost!");
                aVar.a(CBImpressionError.NO_HOST_ACTIVITY);
            } else if (e == null || e == aVar) {
                e = aVar;
                Intent intent = new Intent(hostActivity, CBImpressionActivity.class);
                boolean z2 = (hostActivity.getWindow().getAttributes().flags & ModuleConfig.MERCURY_MODULE) != 0;
                boolean z3;
                if ((hostActivity.getWindow().getAttributes().flags & ModuleConfig.OFFERWALL_MODULE) != 0) {
                    z3 = true;
                } else {
                    z3 = false;
                }
                String str = "paramFullscreen";
                if (!z2 || r3) {
                    z = false;
                }
                intent.putExtra(str, z);
                try {
                    hostActivity.startActivity(intent);
                    n = true;
                } catch (ActivityNotFoundException e) {
                    throw new RuntimeException("Chartboost impression activity not declared in manifest. Please add the following inside your manifest's <application> tag: \n<activity android:name=\"com.chartboost.sdk.CBImpressionActivity\" android:theme=\"@android:style/Theme.Translucent.NoTitleBar\" android:excludeFromRecents=\"true\" />");
                }
            } else {
                aVar.a(CBImpressionError.IMPRESSION_ALREADY_VISIBLE);
            }
        } else {
            aVar.a(CBImpressionError.NO_HOST_ACTIVITY);
        }
    }

    protected static Activity getHostActivity() {
        return b != null ? (Activity) b.get() : null;
    }

    protected static void a(Runnable runnable) {
        if (CBUtility.b()) {
            runnable.run();
        } else {
            a.post(runnable);
        }
    }

    protected static Context getValidContext() {
        return b != null ? b.b() : b.k();
    }

    private static void b(k kVar, boolean z) {
    }

    private static boolean m() {
        return d(b);
    }

    protected static boolean d(k kVar) {
        if (kVar == null) {
            return false;
        }
        Boolean valueOf = Boolean.valueOf(k.get(kVar.a()));
        if (valueOf != null) {
            return valueOf.booleanValue();
        }
        return false;
    }

    private static void b(Activity activity, boolean z) {
        if (activity != null) {
            a(activity.hashCode(), z);
        }
    }

    private static void c(k kVar, boolean z) {
        if (kVar != null) {
            a(kVar.a(), z);
        }
    }

    private static void a(int i, boolean z) {
        k.put(i, z);
    }

    protected static e d() {
        if (b() == null) {
            return null;
        }
        return l;
    }

    public static boolean getImpressionsUseActivities() {
        return b.e();
    }

    public static void setImpressionsUseActivities(boolean impressionsUseActivities) {
        b.a(impressionsUseActivities);
    }

    public static void createSurfaceView(final Activity activity) {
        if (!bh.a(activity)) {
            a.post(new Runnable() {
                public void run() {
                    CBLogging.e("VideoInit", "preparing activity for video surface");
                    activity.addContentView(new SurfaceView(activity), new LayoutParams(0, 0));
                }
            });
        }
    }
}
