package com.chartboost.sdk.impl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.e;
import com.chartboost.sdk.Libraries.g;
import com.chartboost.sdk.Libraries.g.k;
import com.chartboost.sdk.Model.CBError;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.peppermint.PeppermintConstant;
import com.google.android.gcm.GCMConstants;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import jp.co.cyberz.fox.a.a.i;

public final class ba {
    private static com.chartboost.sdk.Libraries.e.a g = null;
    private String a;
    private String b;
    private com.chartboost.sdk.Libraries.e.a c;
    private Map<String, Object> d;
    private Map<String, Object> e;
    private String f;
    private c h;
    private boolean i = false;
    private boolean j = false;
    private com.chartboost.sdk.Libraries.g.a k = null;
    private bb l;
    private int m;
    private boolean n = false;
    private boolean o = true;
    private com.chartboost.sdk.impl.l.a p = com.chartboost.sdk.impl.l.a.NORMAL;

    public interface c {
        void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar);

        void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar, CBError cBError);
    }

    public static abstract class d implements c {
        public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar, CBError cBError) {
        }
    }

    private static class a implements c {
        private d a;
        private b b;

        public a(d dVar, b bVar) {
            this.a = dVar;
            this.b = bVar;
        }

        public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar) {
            if (this.a != null) {
                this.a.a(aVar, baVar);
            }
        }

        public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar, CBError cBError) {
            if (this.b != null) {
                this.b.a(aVar, baVar, cBError);
            }
        }
    }

    public static abstract class b implements c {
    }

    public ba(String str) {
        this.a = str;
        this.f = "POST";
        this.l = bb.a(com.chartboost.sdk.b.k());
        a(0);
    }

    protected void a() {
        if (this.e == null) {
            this.e = new HashMap();
        }
        this.e.put("Accept", "application/json");
        this.e.put("X-Chartboost-Client", CBUtility.d());
        this.e.put("X-Chartboost-API", "5.0.3");
        this.e.put("X-Chartboost-Client", CBUtility.d());
    }

    protected String b() {
        return "application/json";
    }

    public void a(String str, Object obj) {
        if (this.c == null) {
            this.c = com.chartboost.sdk.Libraries.e.a.a();
        }
        this.c.a(str, obj);
    }

    public void a(String str, String str2) {
        if (this.e == null) {
            this.e = new HashMap();
        }
        this.e.put(str, str2);
    }

    public void a(String str, com.chartboost.sdk.Libraries.e.a aVar) {
        if (aVar != null && aVar.c(str)) {
            a(str, aVar.e(str));
        }
    }

    protected void a(Context context) {
        Object obj = null;
        a(GCMConstants.EXTRA_APPLICATION_PENDING_INTENT, com.chartboost.sdk.b.b());
        if ("sdk".equals(Build.PRODUCT)) {
            a("model", (Object) "Android Simulator");
        } else {
            a("model", Build.MODEL);
        }
        a("device_type", Build.MANUFACTURER + " " + Build.MODEL);
        a("os", "Android " + VERSION.RELEASE);
        a(PeppermintConstant.JSON_KEY_COUNTRY, Locale.getDefault().getCountry());
        a(PeppermintConstant.JSON_KEY_LANGUAGE, Locale.getDefault().getLanguage());
        a("sdk", (Object) "5.0.3");
        a("timestamp", String.valueOf(Long.valueOf(new Date().getTime() / 1000).intValue()));
        a("session", Integer.valueOf(CBUtility.a().getInt("cbPrefSessionCount", 0)));
        a("reachability", Integer.valueOf(az.a().b()));
        b(context);
        a("scale", i.a + context.getResources().getDisplayMetrics().density);
        try {
            Object packageName = context.getPackageName();
            a("bundle", context.getPackageManager().getPackageInfo(packageName, ModuleConfig.ACTIVEUSER_MODULE).versionName);
            a("bundle_id", packageName);
        } catch (Throwable e) {
            CBLogging.b("CBRequest", "Exception raised getting package mager object", e);
        }
        if (g == null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            if (telephonyManager == null || telephonyManager.getPhoneType() == 0) {
                g = com.chartboost.sdk.Libraries.e.a.a();
            } else {
                Object obj2;
                String simOperator = telephonyManager.getSimOperator();
                if (TextUtils.isEmpty(simOperator)) {
                    obj2 = null;
                } else {
                    obj2 = simOperator.substring(0, 3);
                    obj = simOperator.substring(3);
                }
                g = e.a(e.a("carrier-name", telephonyManager.getNetworkOperatorName()), e.a("mobile-country-code", obj2), e.a("mobile-network-code", obj), e.a("iso-country-code", telephonyManager.getNetworkCountryIso()), e.a("phone-type", Integer.valueOf(telephonyManager.getPhoneType())));
            }
        }
        a("carrier", g);
        a("custom-id", com.chartboost.sdk.b.j());
    }

    public void c() {
        a("identity", com.chartboost.sdk.Libraries.c.b());
        com.chartboost.sdk.Libraries.c.a c = com.chartboost.sdk.Libraries.c.c();
        if (c.b()) {
            a("tracking", Integer.valueOf(c.a()));
        }
    }

    public void d() {
        String b = com.chartboost.sdk.b.b();
        String c = com.chartboost.sdk.b.c();
        c = com.chartboost.sdk.Libraries.b.b(com.chartboost.sdk.Libraries.b.a(String.format(Locale.US, "%s %s\n%s\n%s", new Object[]{this.f, e(), c, f()}).getBytes()));
        a("X-Chartboost-App", b);
        a("X-Chartboost-Signature", c);
    }

    public String e() {
        return g() + CBUtility.a(this.d);
    }

    public String f() {
        return this.c.toString();
    }

    public void b(Context context) {
        int height;
        Throwable th;
        DisplayMetrics displayMetrics;
        int i = 0;
        if (this.c == null || !this.c.a("w").c() || !this.c.a("h").c()) {
            int width;
            int i2;
            try {
                if (context instanceof Activity) {
                    Activity activity = (Activity) context;
                    Rect rect = new Rect();
                    activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                    width = rect.width();
                    try {
                        height = rect.height();
                        i = width;
                    } catch (Throwable e) {
                        Throwable th2 = e;
                        height = width;
                        th = th2;
                        CBLogging.c("CBRequest", "Exception getting activity size", th);
                        i = height;
                        height = 0;
                        displayMetrics = context.getResources().getDisplayMetrics();
                        i2 = displayMetrics.widthPixels;
                        width = displayMetrics.heightPixels;
                        a("dw", i.a + i2);
                        a("dh", i.a + width);
                        a("dpi", i.a + displayMetrics.densityDpi);
                        i = i2;
                        height = width;
                        a("w", i.a + i);
                        a("h", i.a + height);
                    }
                }
                height = 0;
            } catch (Throwable e2) {
                th = e2;
                height = 0;
                CBLogging.c("CBRequest", "Exception getting activity size", th);
                i = height;
                height = 0;
                displayMetrics = context.getResources().getDisplayMetrics();
                i2 = displayMetrics.widthPixels;
                width = displayMetrics.heightPixels;
                a("dw", i.a + i2);
                a("dh", i.a + width);
                a("dpi", i.a + displayMetrics.densityDpi);
                i = i2;
                height = width;
                a("w", i.a + i);
                a("h", i.a + height);
            }
            displayMetrics = context.getResources().getDisplayMetrics();
            i2 = displayMetrics.widthPixels;
            width = displayMetrics.heightPixels;
            a("dw", i.a + i2);
            a("dh", i.a + width);
            a("dpi", i.a + displayMetrics.densityDpi);
            if (i <= 0 || i > i2) {
                i = i2;
            }
            if (height <= 0 || height > width) {
                height = width;
            }
            a("w", i.a + i);
            a("h", i.a + height);
        }
    }

    public String g() {
        if (this.a == null) {
            return "/";
        }
        return (this.a.startsWith("/") ? i.a : "/") + this.a;
    }

    public void a(String str) {
        this.a = str;
    }

    public boolean h() {
        return g().equals("/api/track");
    }

    public com.chartboost.sdk.Libraries.e.a i() {
        return this.c;
    }

    public Map<String, Object> j() {
        return this.e;
    }

    public boolean k() {
        return this.j;
    }

    public void a(boolean z) {
        this.j = z;
    }

    public com.chartboost.sdk.Libraries.g.a l() {
        return this.k;
    }

    public boolean m() {
        return this.n;
    }

    public void b(boolean z) {
        this.n = z;
    }

    public void a(com.chartboost.sdk.Libraries.g.a aVar) {
        if (g.c(aVar)) {
            this.k = aVar;
            return;
        }
        throw new IllegalArgumentException("Validation predicate must be a dictionary style -- either VDictionary, VDictionaryExact, VDictionaryWithValues, or just a list of KV pairs.");
    }

    public void a(k... kVarArr) {
        this.k = g.a(kVarArr);
    }

    public void b(String str) {
        this.b = str;
    }

    public void a(com.chartboost.sdk.impl.l.a aVar) {
        this.p = aVar;
    }

    public com.chartboost.sdk.impl.l.a n() {
        return this.p;
    }

    public int o() {
        return this.m;
    }

    public void a(int i) {
        this.m = i;
    }

    public boolean p() {
        return this.o;
    }

    public void c(boolean z) {
        this.o = z;
    }

    public boolean q() {
        return this.i;
    }

    public void d(boolean z) {
        this.i = z;
    }

    public c r() {
        return this.h;
    }

    public void s() {
        a(null, null);
    }

    public void a(c cVar) {
        this.h = cVar;
        d(true);
        this.l.a(this, cVar);
    }

    public void a(d dVar, b bVar) {
        d(true);
        this.h = new a(dVar, bVar);
        this.l.a(this, this.h);
    }

    public static ba a(com.chartboost.sdk.Libraries.e.a aVar) {
        try {
            ba baVar = new ba(aVar.e("path"));
            baVar.f = aVar.e("method");
            baVar.d = aVar.a("query").f();
            baVar.c = aVar.a(PeppermintConstant.JSON_KEY_BODY);
            baVar.e = aVar.a("headers").f();
            baVar.j = aVar.i("ensureDelivery");
            baVar.b = aVar.e("eventType");
            baVar.a = aVar.e("path");
            baVar.m = aVar.f("retryCount");
            return baVar;
        } catch (Throwable e) {
            CBLogging.d("CBRequest", "Unable to deserialize failed request", e);
            return null;
        }
    }

    public com.chartboost.sdk.Libraries.e.a t() {
        return e.a(e.a("path", this.a), e.a("method", this.f), e.a("query", e.a(this.d)), e.a(PeppermintConstant.JSON_KEY_BODY, this.c), e.a("eventType", this.b), e.a("headers", e.a(this.e)), e.a("ensureDelivery", Boolean.valueOf(this.j)), e.a("retryCount", Integer.valueOf(this.m)));
    }
}
