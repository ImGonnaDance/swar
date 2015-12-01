package com.chartboost.sdk;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.chartboost.sdk.Chartboost.CBFramework;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBLogging.Level;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.g;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.impl.ba;
import com.chartboost.sdk.impl.ba.c;
import java.util.Map;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONObject;

public final class b {
    protected static Context a = null;
    protected static Application b = null;
    public static com.chartboost.sdk.c.a c;
    private static String d;
    private static String e;
    private static a f;
    private static boolean g = false;
    private static boolean h = false;
    private static boolean i = false;
    private static CBFramework j = null;
    private static String k = null;
    private static SharedPreferences l = null;
    private static boolean m = true;
    private static volatile boolean n = false;
    private static boolean o = false;
    private static boolean p = true;
    private static boolean q = false;
    private static boolean r = true;

    public interface a {
        void a();
    }

    private b() {
    }

    private static SharedPreferences t() {
        if (l == null) {
            l = CBUtility.a();
        }
        return l;
    }

    public static void a(CBFramework cBFramework) {
        o();
        if (cBFramework == null) {
            throw new RuntimeException("Pass a valid CBFramework enum value");
        }
        j = cBFramework;
    }

    public static CBFramework a() {
        o();
        return j == null ? null : j;
    }

    public static String b() {
        o();
        d = t().getString("appId", d);
        return d;
    }

    public static void a(String str) {
        o();
        d = str;
        t().edit().putString("appId", str).commit();
    }

    public static String c() {
        o();
        e = t().getString("appSignature", e);
        return e;
    }

    public static void b(String str) {
        o();
        e = str;
        t().edit().putString("appSignature", str).commit();
    }

    public static a d() {
        o();
        return f;
    }

    public static void a(a aVar) {
        o();
        f = aVar;
    }

    public static boolean e() {
        o();
        return g;
    }

    public static void a(boolean z) {
        o();
        g = z;
    }

    public static boolean f() {
        o();
        return i;
    }

    public static boolean g() {
        return m;
    }

    public static void b(boolean z) {
        m = z;
    }

    public static JSONObject h() {
        o();
        Object string = t().getString("trackingLevels", i.a);
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        com.chartboost.sdk.Libraries.e.a j = com.chartboost.sdk.Libraries.e.a.j(string);
        if (j.c()) {
            return j.e();
        }
        return null;
    }

    public static void a(Level level) {
        o();
        CBLogging.a = level;
    }

    public static Level i() {
        o();
        return CBLogging.a;
    }

    public static String j() {
        o();
        return k;
    }

    public static void c(String str) {
        o();
        k = str;
    }

    public static void a(com.chartboost.sdk.Libraries.e.a aVar) {
        if (aVar != null) {
            Map f = aVar.f();
            if (f != null) {
                Editor edit = t().edit();
                for (String str : f.keySet()) {
                    Object obj = f.get(str);
                    if (obj instanceof String) {
                        edit.putString(str, (String) obj);
                    } else if (obj instanceof Integer) {
                        edit.putInt(str, ((Integer) obj).intValue());
                    } else if (obj instanceof Float) {
                        edit.putFloat(str, ((Float) obj).floatValue());
                    } else if (obj instanceof Long) {
                        edit.putLong(str, ((Long) obj).longValue());
                    } else if (obj instanceof Boolean) {
                        edit.putBoolean(str, ((Boolean) obj).booleanValue());
                    } else if (obj != null) {
                        edit.putString(str, obj.toString());
                    }
                }
                edit.commit();
            }
        }
    }

    public static void a(final a aVar) {
        ba baVar = new ba("/api/config");
        baVar.a(false);
        baVar.b(false);
        baVar.a(com.chartboost.sdk.impl.l.a.HIGH);
        baVar.a(g.a(g.a("status", com.chartboost.sdk.Libraries.a.a)));
        baVar.a(new c() {
            public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar) {
                if (aVar != null) {
                    b.a(aVar.a("response"));
                }
                if (aVar != null) {
                    aVar.a();
                }
            }

            public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar, CBError cBError) {
                if (aVar != null) {
                    aVar.a();
                }
            }
        });
    }

    public static Context k() {
        return a;
    }

    public static boolean l() {
        return n;
    }

    protected static void c(boolean z) {
        n = z;
    }

    public static void m() {
        if (!l()) {
            throw new IllegalStateException("You need call Chartboost.onStart() before calling any public APIs ");
        }
    }

    public static void n() {
        if (Chartboost.b == null) {
            throw new IllegalStateException("The activity context must be set through the Chartboost onCreate method");
        }
    }

    public static void o() {
        if (b == null) {
            throw new IllegalStateException("Need to intialize chartboost using Chartboost.init() as the application object is null");
        }
    }

    public static void d(boolean z) {
        if (c != null) {
            c.a(z);
        }
    }

    protected static void e(boolean z) {
        o = z;
    }

    public static boolean p() {
        return o;
    }

    public static void f(boolean z) {
        p = z;
    }

    public static boolean q() {
        return p;
    }

    public static void g(boolean z) {
        q = z;
    }

    public static boolean r() {
        return q;
    }

    public static void h(boolean z) {
        r = z;
    }

    public static boolean s() {
        return r;
    }
}
