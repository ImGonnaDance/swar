package jp.co.dimage.android.a;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import java.net.URLEncoder;
import java.util.HashMap;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.b;
import jp.co.dimage.android.d;
import jp.co.dimage.android.f;
import jp.co.dimage.android.j;
import jp.co.dimage.android.o;
import jp.co.dimage.android.p;

public final class a implements f {
    private String a = i.a;
    private String b = i.a;
    private String c = i.a;
    private String d = i.a;
    private String e = i.a;
    private String f = i.a;
    private String g = i.a;
    private HashMap h = new HashMap();
    private Context i = null;
    private d j = null;
    private String k = i.a;

    public a(d dVar) {
        this.j = dVar;
        this.b = this.j.o();
        this.a = this.j.n();
        this.d = this.j.l();
        this.c = this.j.s();
        this.i = this.j.k();
        this.e = this.j.u();
        this.f = this.j.m();
        this.g = this.j.C();
        if (jp.co.dimage.android.d.a.ADID == this.j.v()) {
            b.a(this.i, null);
        }
    }

    private static void a(Context context) {
        CookieSyncManager.createInstance(context);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().removeExpiredCookie();
    }

    private void a(Intent intent) {
        this.i.startActivity(intent);
    }

    private void a(String str, int i) {
        a(str, String.valueOf(i));
    }

    public static void a(String str, String str2, String str3) {
        CookieManager.getInstance().setCookie(str, new StringBuilder(String.valueOf(str2)).append("=").append(str3).toString());
    }

    private void b(String str) {
        if (this.j.f()) {
            String str2 = e() + "&_cvpoint=" + str;
            if (jp.co.dimage.android.d.a.ADID == this.j.v()) {
                this.k = str2;
                c();
                return;
            }
            new j(this.j).execute(new String[]{str2});
        }
    }

    private static String c(String str) {
        return CookieManager.getInstance().getCookie(str);
    }

    private void c() {
        new o(this.i).a(false, new b(this));
    }

    private String d(String str) {
        Object a = p.a(f.P, new String[]{this.b, this.d, this.a});
        if (!p.a(this.e)) {
            a = new StringBuilder(String.valueOf(a)).append("&_xtid=").append(this.e).toString();
        }
        String f = f();
        if (!p.a(f)) {
            a = new StringBuilder(String.valueOf(a)).append("&").append(f).toString();
        }
        if (!p.a(this.f)) {
            a = new StringBuilder(String.valueOf(a)).append("&_xroute=").append(this.f).toString();
        }
        f = "default";
        if (str == null || str.length() <= 0) {
            str = f;
        }
        String stringBuilder = new StringBuilder(String.valueOf(a)).append("&_rurl=").append(URLEncoder.encode(str)).toString();
        if (!p.a(this.g)) {
            stringBuilder = new StringBuilder(String.valueOf(stringBuilder)).append("&_model=").append(this.g).toString();
        }
        return this.c + stringBuilder;
    }

    private static void d() {
        CookieSyncManager.getInstance().sync();
    }

    private String e() {
        String a = p.a(f.O, new String[]{this.b, this.d, this.a});
        if (!p.a(this.e)) {
            a = new StringBuilder(String.valueOf(a)).append("&_xtid=").append(this.e).toString();
        }
        String f = f();
        if (!p.a(f)) {
            a = new StringBuilder(String.valueOf(a)).append("&").append(f).toString();
        }
        if (!p.a(this.g)) {
            a = new StringBuilder(String.valueOf(a)).append("&_model=").append(this.g).toString();
        }
        if (!p.a(this.f)) {
            a = new StringBuilder(String.valueOf(a)).append("&_xroute=").append(this.f).toString();
        }
        return this.c + a;
    }

    private static boolean e(String str) {
        for (Object equals : f.ad) {
            if (str.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    private String f() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.h.size() != 0) {
            Object obj = null;
            for (String str : this.h.keySet()) {
                String str2;
                if (!e(str2)) {
                    str2 = new StringBuilder(String.valueOf(str2)).append("=").append(URLEncoder.encode((String) this.h.get(str2))).toString();
                    if (obj != null) {
                        stringBuffer.append("&");
                    }
                    stringBuffer.append(str2);
                    obj = 1;
                }
            }
        }
        return stringBuffer.toString();
    }

    private void g() {
        this.b = this.j.o();
        this.a = this.j.n();
        this.d = this.j.l();
        this.c = this.j.s();
        this.i = this.j.k();
        this.e = this.j.u();
        this.f = this.j.m();
        this.g = this.j.C();
        if (jp.co.dimage.android.d.a.ADID == this.j.v()) {
            b.a(this.i, null);
        }
    }

    public final void a() {
        if (this.j.f()) {
            String e = e();
            if (jp.co.dimage.android.d.a.ADID == this.j.v()) {
                this.k = e;
                c();
                return;
            }
            new j(this.j).execute(new String[]{e});
        }
    }

    public final void a(String str) {
        if (this.j.f()) {
            Object a = p.a(f.P, new String[]{this.b, this.d, this.a});
            if (!p.a(this.e)) {
                a = new StringBuilder(String.valueOf(a)).append("&_xtid=").append(this.e).toString();
            }
            String f = f();
            if (!p.a(f)) {
                a = new StringBuilder(String.valueOf(a)).append("&").append(f).toString();
            }
            if (!p.a(this.f)) {
                a = new StringBuilder(String.valueOf(a)).append("&_xroute=").append(this.f).toString();
            }
            f = "default";
            if (str == null || str.length() <= 0) {
                str = f;
            }
            String stringBuilder = new StringBuilder(String.valueOf(a)).append("&_rurl=").append(URLEncoder.encode(str)).toString();
            if (!p.a(this.g)) {
                stringBuilder = new StringBuilder(String.valueOf(stringBuilder)).append("&_model=").append(this.g).toString();
            }
            stringBuilder = this.c + stringBuilder;
            Intent intent = new Intent("android.intent.action.VIEW");
            try {
                intent.setData(Uri.parse(stringBuilder));
                this.i.startActivity(intent);
            } catch (ActivityNotFoundException e) {
            }
        }
    }

    public final void a(String str, String str2) {
        if (!p.a(str)) {
            this.h.put(str, str2);
        }
    }

    public final void b() {
        this.h = new HashMap();
    }
}
