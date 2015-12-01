package jp.appAdForce.android;

import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.a.a;
import jp.co.dimage.android.d;
import jp.co.dimage.android.f;
import jp.co.dimage.android.p;

public class LtvManager implements f {
    public static final String URL_PARAM_CURRENCY = "_currency";
    public static final String URL_PARAM_OUT = "_out";
    public static final String URL_PARAM_PRICE = "_price";
    public static final String URL_PARAM_SKU = "_sku";
    private d a = null;
    private a b = null;
    private AdManager c = null;
    private String d = i.a;
    private String e = i.a;
    private String f = i.a;
    private String g = i.a;
    private String h = a.e;
    private String i;
    private String j = i.a;
    private String k = i.a;
    private String l = i.a;
    private Context m = null;

    public LtvManager(AdManager adManager) {
        this.c = adManager;
        this.a = this.c.a();
        this.b = new a(this.a);
        this.e = this.a.o();
        this.d = this.a.n();
        this.g = this.a.l();
        this.f = this.a.s();
        this.m = this.a.k();
        this.i = this.a.j();
        this.j = this.a.u();
        this.k = this.a.m();
        this.l = this.a.C();
    }

    private void a() {
        this.e = this.a.o();
        this.d = this.a.n();
        this.g = this.a.l();
        this.f = this.a.s();
        this.m = this.a.k();
        this.i = this.a.j();
        this.j = this.a.u();
        this.k = this.a.m();
        this.l = this.a.C();
    }

    public void addParam(String str, int i) {
        this.b.a(str, String.valueOf(i));
    }

    public void addParam(String str, String str2) {
        this.b.a(str, str2);
    }

    public void clearParam() {
        this.b.b();
    }

    public void ltvOpenBrowser(String str) {
        this.b.a(str);
    }

    public void sendLtvConversion(int i) {
        this.b.a(f.X, String.valueOf(i));
        this.b.a();
    }

    public void sendLtvConversion(int i, String str) {
        this.b.a(f.X, String.valueOf(i));
        this.b.a(f.Z, str);
        this.b.a();
    }

    public void setLtvCookie() {
        a aVar = this.b;
        CookieSyncManager.createInstance(this.m);
        CookieSyncManager.getInstance().startSync();
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().removeExpiredCookie();
        String b = p.b(this.f);
        a aVar2 = this.b;
        a.a(b, f.aP, this.g);
        aVar2 = this.b;
        a.a(b, f.aR, this.i);
        aVar2 = this.b;
        a.a(b, f.aN, this.e);
        aVar2 = this.b;
        a.a(b, f.aO, this.d);
        aVar2 = this.b;
        a.a(b, f.aM, this.h);
        aVar2 = this.b;
        a.a(b, f.aa, this.j);
        aVar2 = this.b;
        a.a(b, f.ao, this.k);
        aVar2 = this.b;
        a.a(b, f.af, this.l);
        aVar2 = this.b;
        CookieSyncManager.getInstance().sync();
        aVar2 = this.b;
        CookieManager.getInstance().getCookie(b);
    }
}
