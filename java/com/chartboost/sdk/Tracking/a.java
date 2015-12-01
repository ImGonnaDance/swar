package com.chartboost.sdk.Tracking;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.c;
import com.chartboost.sdk.Libraries.g;
import com.chartboost.sdk.Libraries.h;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.b;
import com.chartboost.sdk.impl.ba;
import com.chartboost.sdk.impl.bb;
import com.com2us.module.mercury.MercuryDefine;
import com.facebook.Response;
import java.lang.reflect.Method;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONArray;
import org.json.JSONObject;

public class a implements com.chartboost.sdk.Libraries.a {
    private static final String b = a.class.getSimpleName();
    private static a j;
    private static boolean k = false;
    private String c;
    private String d = p();
    private JSONArray e = new JSONArray();
    private long f;
    private long g;
    private long h = System.currentTimeMillis();
    private h i = new h("CBTrackingDirectory", false);

    private a() {
    }

    public static a a() {
        if (j == null) {
            synchronized (Chartboost.class) {
                if (j == null) {
                    j = new a();
                }
            }
        }
        return j;
    }

    public static void b() {
        a("start");
        a("did-become-active");
    }

    public static void a(String str) {
        j.a("session", str, null, null, null, null, "session");
    }

    public void c() {
        a(false);
    }

    public void a(boolean z) {
        com.chartboost.sdk.Libraries.e.a a = com.chartboost.sdk.Libraries.e.a.a();
        a.a("complete", Boolean.valueOf(z));
        j.a("session", "end", null, null, null, null, a.e(), "session");
        a("did-become-active");
    }

    public static void a(String str, String str2, boolean z) {
        j.a("ad-get", str, str2, b(z), "single", null, "system");
    }

    public static void b(String str, String str2, boolean z) {
        j.a("ad-has", str, str2, b(z), null, null, "system");
    }

    public static void c(String str, String str2, boolean z) {
        j.a("ad-loaded", str, str2, b(z), null, null, "system");
    }

    public static void a(String str, String str2, String str3) {
        j.a("ad-show", str, str2, str3, null, null, "system");
    }

    public static void b(String str, String str2, String str3) {
        j.a("ad-click", str, str2, str3, null, null, "system");
    }

    public static void a(String str, String str2, String str3, int i) {
        j.a("ad-click", str, str2, str3, a(i), null, "system");
    }

    public static void c(String str, String str2, String str3) {
        j.a("ad-close", str, str2, str3, null, null, "system");
    }

    public static void a(String str, String str2, CBImpressionError cBImpressionError) {
        j.a("ad-error", str, str2, cBImpressionError != null ? cBImpressionError.toString() : i.a, null, null, "system");
    }

    public static void d() {
        j.a("video-prefetcher", "begin", null, null, null, null, "system");
    }

    public static void e() {
        j.a("video-prefetcher", null, null, null, null, null, "system");
    }

    public static void a(String str, String str2) {
        j.a("start", str, str2, null, null, null, "system");
    }

    public static void a(String str, String str2, String str3, String str4) {
        j.a("failure", str, str2, str3, str4, null, "system");
    }

    public static void d(String str, String str2, String str3) {
        j.a(Response.SUCCESS_KEY, str, str2, str3, null, null, "system");
    }

    public static void e(String str, String str2, String str3) {
        j.a(str, str2, str3, null, null, null, "system");
    }

    public static void b(String str, String str2) {
        j.a("confirmation-show", str, str2, null, null, null, "system");
    }

    public static void d(String str, String str2, boolean z) {
        j.a("confirmation-dismiss", str, str2, b(z), null, null, "system");
    }

    public static void c(String str, String str2) {
        j.a("replay", str, str2, null, null, null, "system");
    }

    public static void a(String str, String str2, int i) {
        j.a("playback-start", str, str2, a(i), null, null, "system");
    }

    public static void d(String str, String str2) {
        j.a("playback-stop", str, str2, null, null, null, "system");
    }

    public static void b(String str, String str2, int i) {
        j.a("close-show", str, str2, a(i), null, null, "system");
    }

    public static void e(String str, String str2, boolean z) {
        j.a("controls-toggle", str, str2, b(z), null, null, "system");
    }

    public static void a(String str, String str2, String str3, int i, int i2) {
        j.a("install-click", str, str3, str2, a(i), a(i2), "system");
    }

    public static void a(String str, String str2, String str3, String str4, String str5, String str6, JSONObject jSONObject) {
        j.a(str, str2, str3, str4, str5, str6, jSONObject, "system");
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        j.a(str, str2, str3, str4, str5, str6, new JSONObject(), str7);
    }

    public void a(String str, String str2, String str3, String str4, String str5, String str6, JSONObject jSONObject, String str7) {
        JSONObject jSONObject2;
        try {
            Method declaredMethod = b.class.getDeclaredMethod("h", new Class[0]);
            declaredMethod.setAccessible(true);
            jSONObject2 = (JSONObject) declaredMethod.invoke(null, new Object[0]);
        } catch (Throwable e) {
            CBLogging.b(this, "Error encountered getting tracking levels", e);
            CBUtility.throwProguardError(e);
            jSONObject2 = null;
        }
        com.chartboost.sdk.Libraries.e.a a = com.chartboost.sdk.Libraries.e.a.a();
        if (jSONObject2 != null && jSONObject2.optBoolean(str7)) {
            long currentTimeMillis = System.currentTimeMillis();
            long j = currentTimeMillis - this.f;
            currentTimeMillis -= this.h;
            a.a(MercuryDefine.TYPE_EVENT, a((Object) str));
            a.a("kingdom", a((Object) str2));
            a.a("phylum", a((Object) str3));
            a.a("class", a((Object) str4));
            a.a("family", a((Object) str5));
            a.a("genus", a((Object) str6));
            String str8 = "meta";
            if (jSONObject == null) {
                jSONObject = new JSONObject();
            }
            a.a(str8, jSONObject);
            a.a("clientTimestamp", Long.valueOf(System.currentTimeMillis() / 1000));
            a.a("session_id", l());
            a.a("totalSessionTime", Long.valueOf(j / 1000));
            a.a("currentSessionTime", Long.valueOf(currentTimeMillis / 1000));
            synchronized (this) {
                if (bb.h() || f()) {
                    q();
                }
                this.e.put(a.e());
                Object a2 = com.chartboost.sdk.Libraries.e.a.a();
                a2.a("events", this.e);
                CBLogging.a(b, "###Writing" + a((Object) str) + "to tracking cache dir");
                this.i.a(this.d, com.chartboost.sdk.Libraries.e.a.a(a2));
                k();
            }
        }
    }

    public boolean f() {
        if (this.e == null || this.e.length() < 50) {
            return false;
        }
        return true;
    }

    public String g() {
        com.chartboost.sdk.Libraries.e.a a = com.chartboost.sdk.Libraries.e.a.a();
        a.a("startTime", Long.valueOf(System.currentTimeMillis()));
        a.a("deviceID", c.e());
        this.c = com.chartboost.sdk.Libraries.b.b(a.toString().getBytes());
        return this.c;
    }

    public void h() {
        com.chartboost.sdk.Libraries.e.a a = this.i.a("cb_previous_session_info");
        if (a != null) {
            this.g = a.h("timestamp");
            this.f = a.h("start_timestamp");
            this.c = a.e("session_id");
            if (System.currentTimeMillis() - this.g > 180000) {
                a(true);
            } else if (!TextUtils.isEmpty(this.c)) {
                k();
                k = false;
                return;
            }
        }
        j();
        k = true;
    }

    public static boolean i() {
        return k;
    }

    public void j() {
        long currentTimeMillis = System.currentTimeMillis();
        this.f = currentTimeMillis;
        this.g = currentTimeMillis;
        this.c = g();
        a(currentTimeMillis, currentTimeMillis);
        SharedPreferences a = CBUtility.a();
        int i = a.getInt("cbPrefSessionCount", 0) + 1;
        Editor edit = a.edit();
        edit.putInt("cbPrefSessionCount", i);
        edit.commit();
    }

    public void k() {
        a(this.f, System.currentTimeMillis());
    }

    public void a(long j, long j2) {
        com.chartboost.sdk.Libraries.e.a a = com.chartboost.sdk.Libraries.e.a.a();
        a.a("start_timestamp", Long.valueOf(j));
        a.a("timestamp", Long.valueOf(j2));
        a.a("session_id", this.c);
        this.i.a("cb_previous_session_info", a);
    }

    public ba a(com.chartboost.sdk.Libraries.e.a aVar) {
        ba baVar = new ba("/api/track");
        baVar.a("track", (Object) aVar);
        baVar.a(g.a(g.a("status", com.chartboost.sdk.Libraries.a.a)));
        baVar.a(com.chartboost.sdk.impl.l.a.LOW);
        return baVar;
    }

    public String toString() {
        return "Session [ startTime: " + o() + " sessionEvents: " + n() + " ]";
    }

    public String l() {
        return this.c;
    }

    public h m() {
        return this.i;
    }

    public JSONArray n() {
        return this.e;
    }

    public long o() {
        return this.f;
    }

    public String p() {
        return new Long(System.nanoTime()).toString();
    }

    public static String b(boolean z) {
        return z ? a.e : a.d;
    }

    public static String a(int i) {
        return new Integer(i).toString();
    }

    private static Object a(Object obj) {
        return obj != null ? obj : i.a;
    }

    public boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }
        return str.equals("cb_previous_session_info");
    }

    public void q() {
        this.e = new JSONArray();
        this.d = p();
    }
}
