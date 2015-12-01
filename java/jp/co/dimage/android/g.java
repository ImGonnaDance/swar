package jp.co.dimage.android;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.com2us.peppermint.PeppermintURL;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import jp.appAdForce.android.AdManager;
import jp.co.cyberz.fox.a.a.e;
import jp.co.cyberz.fox.a.a.i;

public final class g implements f {
    public static final int a = 0;
    public static final int b = 1;
    private String bA = i.a;
    private String bB = i.a;
    private String bC = i.a;
    private String bD = i.a;
    private String bE = i.a;
    private String bF = i.a;
    private String bG = null;
    private boolean bH = false;
    private String bI = i.a;
    private String bd = i.a;
    private boolean be = false;
    private boolean bf = false;
    private boolean bg = false;
    private boolean bh = false;
    private boolean bi = false;
    private boolean bj = false;
    private boolean bk = false;
    private boolean bl = false;
    private boolean bm = false;
    private boolean bn = false;
    private boolean bo = false;
    private boolean bp = false;
    private boolean bq = false;
    private boolean br = false;
    private boolean bs = false;
    private Integer bt = Integer.valueOf(a);
    private Boolean bu = Boolean.valueOf(false);
    private d bv = null;
    private String bw = i.a;
    private String bx = i.a;
    private String by = i.a;
    private String bz = i.a;
    private Context c = null;
    private String d = i.a;
    private String e = i.a;
    private String f = i.a;
    private String g = i.a;
    private String h = i.a;
    private String i = i.a;
    private String j = i.a;
    private String k = i.a;
    private String l = i.a;
    private String m = i.a;
    private String n = i.a;
    private String o = i.a;

    enum a {
        INSTALL(a.d),
        START(a.e),
        OTHERS(a.f);
        
        private String d;

        private a(String str) {
            this.d = str;
        }

        private static a a(String str) {
            return (a) Enum.valueOf(a.class, str);
        }

        private static a[] b() {
            Object obj = e;
            int length = obj.length;
            Object obj2 = new a[length];
            System.arraycopy(obj, g.a, obj2, g.a, length);
            return obj2;
        }

        final String a() {
            return this.d;
        }
    }

    public g(d dVar) {
        this.bv = dVar;
        this.f = this.bv.o();
        this.d = this.bv.n();
        this.g = this.bv.p();
        this.j = this.bv.l();
        this.i = this.bv.q();
        this.l = this.bv.t();
        this.h = this.bv.s();
        this.k = this.bv.r();
        this.c = this.bv.k();
        this.bf = this.bv.w();
        this.bg = this.bv.x();
        this.bh = this.bv.y();
        this.bi = this.bv.z();
        this.bw = this.bv.A();
        this.bx = this.bv.B();
        this.by = this.bv.C();
        this.bz = this.bv.D();
        this.m = this.bv.E();
        this.be = this.bv.F();
        if (p.a(this.bw)) {
            this.bw = i.a;
        }
        if (p.a(this.bx)) {
            this.bx = i.a;
        }
        if (p.a(this.by)) {
            this.by = i.a;
        }
        if (p.a(this.bz)) {
            this.bz = i.a;
        }
    }

    private String a(String str, String[] strArr) {
        return this.h + p.a(str, strArr);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String a(jp.co.dimage.android.g.a r10, java.lang.String r11, int r12) {
        /*
        r9 = this;
        r7 = 5;
        r1 = 1;
        r4 = 0;
        r0 = "/p/cv?_app={0}&_xuid={1}&_xuniq={2}&_xevent={3}";
        r2 = 4;
        r2 = new java.lang.String[r2];
        r3 = r9.f;
        r2[r4] = r3;
        r3 = r9.j;
        r2[r1] = r3;
        r3 = 2;
        r5 = r9.d;
        r2[r3] = r5;
        r3 = 3;
        r5 = r10.a();
        r2[r3] = r5;
        r0 = r9.a(r0, r2);
        r2 = r9.e;
        r2 = jp.co.dimage.android.p.a(r2);
        if (r2 != 0) goto L_0x0041;
    L_0x0028:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&_buid=";
        r0 = r2.append(r0);
        r2 = r9.e;
        r0 = r0.append(r2);
        r0 = r0.toString();
    L_0x0041:
        r2 = r9.g;
        if (r2 == 0) goto L_0x0066;
    L_0x0045:
        r2 = r9.g;
        r2 = r2.length();
        if (r2 <= 0) goto L_0x0066;
    L_0x004d:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&appinfo=";
        r0 = r2.append(r0);
        r2 = r9.g;
        r0 = r0.append(r2);
        r0 = r0.toString();
    L_0x0066:
        r2 = r9.l;
        if (r2 == 0) goto L_0x0087;
    L_0x006a:
        r2 = r9.l;
        r3 = "1";
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x0087;
    L_0x0074:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&_sdktest=1";
        r0 = r2.append(r0);
        r0 = r0.toString();
    L_0x0087:
        r2 = r9.bu;
        r2 = r2.booleanValue();
        if (r2 == 0) goto L_0x00a2;
    L_0x008f:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&_isrand=1";
        r0 = r2.append(r0);
        r0 = r0.toString();
    L_0x00a2:
        r2 = "default";
        if (r11 == 0) goto L_0x00b0;
    L_0x00a6:
        r3 = r11.length();
        if (r3 <= 0) goto L_0x00b0;
    L_0x00ac:
        r2 = java.net.URLEncoder.encode(r11);
    L_0x00b0:
        if (r2 == 0) goto L_0x00c9;
    L_0x00b2:
        r3 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r3.<init>(r0);
        r0 = "&_rurl=";
        r0 = r3.append(r0);
        r0 = r0.append(r2);
        r0 = r0.toString();
    L_0x00c9:
        r2 = r9.bw;
        if (r2 == 0) goto L_0x00e6;
    L_0x00cd:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&_bundle_id=";
        r0 = r2.append(r0);
        r2 = r9.bw;
        r0 = r0.append(r2);
        r0 = r0.toString();
    L_0x00e6:
        r2 = r9.by;
        if (r2 == 0) goto L_0x0103;
    L_0x00ea:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&_model=";
        r0 = r2.append(r0);
        r2 = r9.by;
        r0 = r0.append(r2);
        r0 = r0.toString();
    L_0x0103:
        r2 = r9.bz;
        if (r2 == 0) goto L_0x0120;
    L_0x0107:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&_os_ver=";
        r0 = r2.append(r0);
        r2 = r9.bz;
        r0 = r0.append(r2);
        r0 = r0.toString();
    L_0x0120:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&_sdk_ver=v2.14.7g";
        r0 = r2.append(r0);
        r0 = r0.toString();
        r2 = r9.m;
        r2 = jp.co.dimage.android.p.a(r2);
        if (r2 != 0) goto L_0x0177;
    L_0x013b:
        r2 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r2.<init>(r0);
        r0 = "&_hash=";
        r0 = r2.append(r0);
        r2 = r9.f;
        r3 = r9.j;
        r5 = r9.d;
        r6 = new java.lang.StringBuilder;
        r2 = java.lang.String.valueOf(r2);
        r6.<init>(r2);
        r2 = r6.append(r3);
        r2 = r2.append(r5);
        r3 = r9.m;
        r2 = r2.append(r3);
        r2 = r2.toString();
        r2 = jp.co.dimage.android.p.c(r2);
        r0 = r0.append(r2);
        r0 = r0.toString();
    L_0x0177:
        r2 = r9.bv;
        r2 = r2.G();
        if (r2 == 0) goto L_0x044a;
    L_0x017f:
        r2 = r9.bo;
        if (r2 != 0) goto L_0x044a;
    L_0x0183:
        r3 = r1;
    L_0x0184:
        if (r3 == 0) goto L_0x044d;
    L_0x0186:
        r1 = "true";
    L_0x0188:
        r2 = "true";
        if (r12 != 0) goto L_0x0190;
    L_0x018c:
        if (r3 == 0) goto L_0x0190;
    L_0x018e:
        r2 = "false";
    L_0x0190:
        r3 = r9.j;
        r3 = jp.co.dimage.android.p.a(r3);
        if (r3 != 0) goto L_0x019e;
    L_0x0198:
        if (r12 != 0) goto L_0x0451;
    L_0x019a:
        r2 = "true";
        r1 = "false";
    L_0x019e:
        r3 = r9.i;
        r3 = jp.co.dimage.android.p.a(r3);
        if (r3 == 0) goto L_0x01ae;
    L_0x01a6:
        r3 = r9.bv;
        r3 = r3.a();
        r9.i = r3;
    L_0x01ae:
        r3 = r9.i;
        r3 = jp.co.dimage.android.p.a(r3);
        if (r3 != 0) goto L_0x0205;
    L_0x01b6:
        r3 = r9.i;
        r3 = r3.length();
        if (r3 <= r7) goto L_0x0205;
    L_0x01be:
        r3 = "LINE_";
        r5 = r9.i;
        r5 = r5.substring(r4, r7);
        r3 = r3.equals(r5);
        if (r3 == 0) goto L_0x0205;
    L_0x01cc:
        if (r12 != 0) goto L_0x0457;
    L_0x01ce:
        r1 = "true";
        r2 = "false";
    L_0x01d2:
        r3 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r3.<init>(r0);
        r0 = "&_referrer_hash=";
        r0 = r3.append(r0);
        r3 = new java.lang.StringBuilder;
        r5 = r9.i;
        r5 = java.lang.String.valueOf(r5);
        r3.<init>(r5);
        r5 = r9.m;
        r3 = r3.append(r5);
        r3 = r3.toString();
        r3 = jp.co.dimage.android.p.c(r3);
        r0 = r0.append(r3);
        r0 = r0.toString();
        r8 = r1;
        r1 = r2;
        r2 = r8;
    L_0x0205:
        r3 = r9.i;
        r3 = jp.co.dimage.android.p.a(r3);
        if (r3 != 0) goto L_0x022e;
    L_0x020d:
        r3 = r9.i;
        r3 = java.net.URLEncoder.encode(r3);
        r9.i = r3;
        r3 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r3.<init>(r0);
        r0 = "&_referrer=";
        r0 = r3.append(r0);
        r3 = r9.i;
        r0 = r0.append(r3);
        r0 = r0.toString();
    L_0x022e:
        r3 = "2";
        r5 = r9.o;
        r3 = r3.equals(r5);
        if (r3 == 0) goto L_0x023e;
    L_0x0238:
        if (r12 != 0) goto L_0x045d;
    L_0x023a:
        r2 = "true";
        r1 = "false";
    L_0x023e:
        r3 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r3.<init>(r0);
        r0 = "&_use_bw=";
        r0 = r3.append(r0);
        r0 = r0.append(r1);
        r0 = r0.toString();
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_cv_target=";
        r0 = r1.append(r0);
        r0 = r0.append(r2);
        r0 = r0.toString();
        r1 = r9.bv;
        r1 = r1.v();
        r2 = jp.co.dimage.android.d.a.IMEI;
        if (r1 != r2) goto L_0x027a;
    L_0x0276:
        r1 = "1";
        r9.bd = r1;
    L_0x027a:
        r1 = r9.bv;
        r1 = r1.v();
        r2 = jp.co.dimage.android.d.a.UUID;
        if (r1 != r2) goto L_0x0288;
    L_0x0284:
        r1 = "2";
        r9.bd = r1;
    L_0x0288:
        r1 = r9.bv;
        r1 = r1.v();
        r2 = jp.co.dimage.android.d.a.ADID;
        if (r1 != r2) goto L_0x0296;
    L_0x0292:
        r1 = "4";
        r9.bd = r1;
    L_0x0296:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_xuniq_type=";
        r0 = r1.append(r0);
        r1 = r9.bd;
        r0 = r0.append(r1);
        r0 = r0.toString();
        r1 = r9.bB;
        r1 = jp.co.dimage.android.p.a(r1);
        if (r1 != 0) goto L_0x02e9;
    L_0x02b7:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_pfapp=";
        r0 = r1.append(r0);
        r1 = r9.bB;
        r0 = r0.append(r1);
        r0 = r0.toString();
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_pf=";
        r0 = r1.append(r0);
        r1 = r9.bA;
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x02e9:
        r1 = r9.bv;
        r1 = jp.co.dimage.android.d.g();
        if (r1 == 0) goto L_0x0304;
    L_0x02f1:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_jb=1";
        r0 = r1.append(r0);
        r0 = r0.toString();
    L_0x0304:
        r1 = r9.bH;
        if (r1 != 0) goto L_0x0320;
    L_0x0308:
        r1 = r9.c;
        r2 = "__FOX__";
        r1 = r1.getSharedPreferences(r2, r4);
        r2 = "__FOX_REINSTALL__";
        r3 = "0";
        r1 = r1.getString(r2, r3);
        r2 = "1";
        r1 = r2.equals(r1);
        if (r1 == 0) goto L_0x0333;
    L_0x0320:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_dd=1";
        r0 = r1.append(r0);
        r0 = r0.toString();
    L_0x0333:
        r1 = r9.bI;
        r1 = jp.co.dimage.android.p.a(r1);
        if (r1 != 0) goto L_0x0354;
    L_0x033b:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_dd_type=";
        r0 = r1.append(r0);
        r1 = r9.bI;
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x0354:
        r1 = r9.bl;
        if (r1 == 0) goto L_0x0387;
    L_0x0358:
        r1 = r9.n;
        r1 = jp.co.dimage.android.p.a(r1);
        if (r1 == 0) goto L_0x0366;
    L_0x0360:
        r1 = jp.co.dimage.android.p.a();
        r9.n = r1;
    L_0x0366:
        r1 = r9.n;
        r1 = jp.co.dimage.android.p.a(r1);
        if (r1 != 0) goto L_0x0387;
    L_0x036e:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_fbid=";
        r0 = r1.append(r0);
        r1 = r9.n;
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x0387:
        r1 = r9.bC;
        r1 = jp.co.dimage.android.p.a(r1);
        if (r1 != 0) goto L_0x03a8;
    L_0x038f:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_fpid=";
        r0 = r1.append(r0);
        r1 = r9.bC;
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x03a8:
        r1 = r9.bD;
        r1 = jp.co.dimage.android.p.a(r1);
        if (r1 != 0) goto L_0x03c9;
    L_0x03b0:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_fptdl=";
        r0 = r1.append(r0);
        r1 = r9.bD;
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x03c9:
        r1 = r9.bm;
        if (r1 == 0) goto L_0x0411;
    L_0x03cd:
        r1 = r9.bE;
        r1 = jp.co.dimage.android.p.a(r1);
        if (r1 != 0) goto L_0x0411;
    L_0x03d5:
        r1 = "1";
        r2 = r9.bd;
        r1 = r1.equals(r2);
        if (r1 != 0) goto L_0x0411;
    L_0x03df:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_adid=";
        r0 = r1.append(r0);
        r1 = r9.bE;
        r0 = r0.append(r1);
        r0 = r0.toString();
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_adte=";
        r0 = r1.append(r0);
        r1 = r9.bF;
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x0411:
        r1 = r9.be;
        if (r1 == 0) goto L_0x0428;
    L_0x0415:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_optout=1";
        r0 = r1.append(r0);
        r0 = r0.toString();
    L_0x0428:
        r1 = r9.o;
        r1 = jp.co.dimage.android.p.a(r1);
        if (r1 != 0) goto L_0x0449;
    L_0x0430:
        r1 = new java.lang.StringBuilder;
        r0 = java.lang.String.valueOf(r0);
        r1.<init>(r0);
        r0 = "&_xroute=";
        r0 = r1.append(r0);
        r1 = r9.o;
        r0 = r0.append(r1);
        r0 = r0.toString();
    L_0x0449:
        return r0;
    L_0x044a:
        r3 = r4;
        goto L_0x0184;
    L_0x044d:
        r1 = "false";
        goto L_0x0188;
    L_0x0451:
        r2 = "false";
        r1 = "false";
        goto L_0x019e;
    L_0x0457:
        r1 = "false";
        r2 = "false";
        goto L_0x01d2;
    L_0x045d:
        r2 = "false";
        r1 = "false";
        goto L_0x023e;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.g.a(jp.co.dimage.android.g$a, java.lang.String, int):java.lang.String");
    }

    private void b(a aVar) {
        q qVar = new q(this.c, this.bv.I(), this.bv.J());
        if (this.bm) {
            this.bv.a(jp.co.dimage.android.d.a.ADID);
        } else {
            this.bv.a(jp.co.dimage.android.d.a.UUID);
        }
        if (qVar.b()) {
            this.bv.b(qVar.d());
        }
        this.bv.b();
        this.d = this.bv.n();
        this.j = this.bv.l();
        if (e.c()) {
            this.bv.h();
        }
        if (jp.co.cyberz.fox.notify.a.b()) {
            this.bv.i();
        }
        String a = a(aVar, null, (int) a);
        if (aW.booleanValue()) {
            a.d(f.aU, "sendConversion: " + a);
        }
        if (!this.bg) {
            j jVar = new j(this.bv);
            String[] strArr = new String[b];
            strArr[a] = a;
            jVar.execute(strArr);
        }
        if (i.a(this.bG)) {
            n();
        } else if (this.bo) {
            n();
        } else if (!this.bh && this.bn && this.bq) {
            Intent intent = new Intent("android.intent.action.VIEW");
            a = this.bG;
            if (!(this.bf && a.startsWith(PeppermintURL.PEPPERMINT_HTTP) && a.startsWith(PeppermintURL.PEPPERMINT_HTTPS))) {
                a = a(a.START, this.bG, (int) b);
            }
            try {
                intent.setData(Uri.parse(a));
                a(intent);
            } catch (ActivityNotFoundException e) {
            } finally {
                n();
            }
        }
    }

    private void c(a aVar) {
        if (!this.bv.f()) {
            return;
        }
        if (!(this.bg && this.bf) && p.b(this.c)) {
            String a = a(f.S, new String[]{this.f, this.bw, this.bx, this.by, this.bz, AdManager.a});
            if (!p.a(this.bB)) {
                a = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(a)).append("&_pfapp=").append(this.bB).toString())).append("&_pf=").append(this.bA).toString();
            }
            e eVar = new e(this.bv, this, aVar);
            String[] strArr = new String[b];
            strArr[a] = a;
            eVar.execute(strArr);
        }
    }

    private String d(String str, String str2, String str3) {
        return p.c(new StringBuilder(String.valueOf(str)).append(str2).append(str3).append(this.m).toString());
    }

    private void d(a aVar) {
        String a = a(f.S, new String[]{this.f, this.bw, this.bx, this.by, this.bz, AdManager.a});
        if (!p.a(this.bB)) {
            a = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(a)).append("&_pfapp=").append(this.bB).toString())).append("&_pf=").append(this.bA).toString();
        }
        e eVar = new e(this.bv, this, aVar);
        String[] strArr = new String[b];
        strArr[a] = a;
        eVar.execute(strArr);
    }

    private String e(a aVar) {
        return a(aVar, null, (int) a);
    }

    private void h(String str) {
        d(str);
    }

    private void i(String str) {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream openFileOutput = this.c.openFileOutput(str, a);
            if (openFileOutput != null) {
                try {
                    openFileOutput.close();
                } catch (IOException e) {
                }
            }
            if (str.equals(f.as)) {
                this.bg = true;
            } else if (str.equals(f.ar)) {
                this.bf = true;
            } else if (str.equals(f.aA)) {
                this.bi = true;
            }
        } catch (Exception e2) {
            a.b(f.aU, "createFile failed. " + str);
        } catch (Throwable th) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e3) {
                }
            }
        }
    }

    private void j() {
        boolean z = false;
        String e = this.bv.e();
        if (p.a(e)) {
            try {
                Class cls = Class.forName("jp.co.CAReward_Ack.CARController");
                if (cls != null) {
                    Class[] clsArr = new Class[b];
                    clsArr[a] = Context.class;
                    Method method = cls.getMethod("getUID", clsArr);
                    if (method != null) {
                        Object[] objArr = new Object[b];
                        objArr[a] = this.c;
                        if (!p.a((String) method.invoke(null, objArr))) {
                            z = true;
                        }
                        this.bv.a(z);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (a.e.equals(e)) {
            z = true;
        }
        if (z) {
            this.bH = true;
            this.bI = f.ba;
        }
    }

    private void k() {
        b.a(this.c, new i(this));
    }

    private boolean l() {
        return p.b(this.c);
    }

    private void m() {
        if (!p.a(this.j) && !p.a(this.o) && a.f.equals(this.o) && this.bv.f() && p.b(this.c)) {
            String a = a(a.START, null, (int) a);
            j jVar = new j(this.bv);
            String[] strArr = new String[b];
            strArr[a] = a;
            jVar.execute(strArr);
        }
    }

    private void n() {
        i(f.ar);
        this.bf = true;
        i(f.at);
        this.bh = true;
    }

    private String o() {
        String a = a(f.S, new String[]{this.f, this.bw, this.bx, this.by, this.bz, AdManager.a});
        return !p.a(this.bB) ? new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(a)).append("&_pfapp=").append(this.bB).toString())).append("&_pf=").append(this.bA).toString() : a;
    }

    private String p() {
        String a = a(f.T, new String[]{this.f, this.e, this.d, this.bw, this.bx, this.by, this.bz, AdManager.a});
        return !p.a(this.bB) ? new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(a)).append("&_pfapp=").append(this.bB).toString())).append("&_pf=").append(this.bA).toString() : a;
    }

    private boolean q() {
        return this.bf;
    }

    private boolean r() {
        return this.bg;
    }

    private boolean s() {
        return this.bh;
    }

    private boolean t() {
        return this.bi;
    }

    private void u() {
        this.f = this.bv.o();
        this.d = this.bv.n();
        this.g = this.bv.p();
        this.j = this.bv.l();
        this.i = this.bv.q();
        this.l = this.bv.t();
        this.h = this.bv.s();
        this.k = this.bv.r();
        this.c = this.bv.k();
        this.bf = this.bv.w();
        this.bg = this.bv.x();
        this.bh = this.bv.y();
        this.bi = this.bv.z();
        this.bw = this.bv.A();
        this.bx = this.bv.B();
        this.by = this.bv.C();
        this.bz = this.bv.D();
        this.m = this.bv.E();
        this.be = this.bv.F();
        if (p.a(this.bw)) {
            this.bw = i.a;
        }
        if (p.a(this.bx)) {
            this.bx = i.a;
        }
        if (p.a(this.by)) {
            this.by = i.a;
        }
        if (p.a(this.bz)) {
            this.bz = i.a;
        }
    }

    private boolean v() {
        return this.bk;
    }

    private boolean w() {
        return this.bn;
    }

    public final void a() {
        c(a.START);
    }

    public final void a(Intent intent) {
        this.c.startActivity(intent);
    }

    public final void a(Uri uri) {
        if (uri != null && this.bv.a(uri)) {
            this.j = this.bv.l();
            this.o = this.bv.m();
            if (!p.a(this.j) && !p.a(this.o) && a.f.equals(this.o) && this.bv.f() && p.b(this.c)) {
                String a = a(a.START, null, (int) a);
                j jVar = new j(this.bv);
                String[] strArr = new String[b];
                strArr[a] = a;
                jVar.execute(strArr);
            }
        }
    }

    public final void a(Integer num) {
        this.bt = num;
    }

    public final void a(String str) {
        if (this.bv.f()) {
            this.bG = str;
            c(a.START);
        }
    }

    public final void a(String str, String str2) {
        this.e = str2;
        a(str);
    }

    public final void a(String str, String str2, String str3) {
        j();
        b(str, str2, str3);
    }

    public final void a(a aVar) {
        if (this.bk) {
            if (this.bm) {
                b.a(this.c, new i(this));
            }
            if (this.br) {
                Fingerprint fingerprint = new Fingerprint();
                fingerprint.a(this.h);
                fingerprint.a(this);
                fingerprint.a(aVar);
                fingerprint.a(this.c);
            }
            if (this.br || this.bs || this.bm || this.bt.intValue() > 0) {
                new o(this.c, this, aVar).a(this.bt, new h(this));
            } else {
                b(aVar);
            }
        }
    }

    public final void a(boolean z) {
        this.bk = z;
    }

    public final void b(String str) {
        j();
        a(str);
    }

    public final void b(String str, String str2) {
        j();
        a(str, str2);
    }

    public final void b(String str, String str2, String str3) {
        this.bA = str3;
        this.bB = str2;
        if (p.a(this.h)) {
            this.h = f.ap;
        }
        a(str);
    }

    public final void b(boolean z) {
        this.bl = z;
    }

    public final boolean b() {
        return !p.a(this.bC);
    }

    public final void c() {
        i(f.as);
        this.bg = true;
    }

    public final void c(String str) {
        c(a.OTHERS);
        Intent intent = new Intent("android.intent.action.VIEW");
        if (!this.bf && this.bv.f()) {
            str = a(a.OTHERS, str, (int) b);
            n();
        }
        try {
            intent.setData(Uri.parse(str));
            a(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public final void c(String str, String str2) {
        j();
        e(str, str2);
    }

    public final void c(String str, String str2, String str3) {
        if (!p.a(str3)) {
            this.bA = str2;
            this.bB = str;
            this.e = str3;
            String d = this.bv.d();
            if (p.a(d) || !d.equals(str3)) {
                if (p.a(this.h)) {
                    this.h = f.ap;
                }
                d = a(f.T, new String[]{this.f, this.e, this.d, this.bw, this.bx, this.by, this.bz, AdManager.a});
                if (!p.a(this.bB)) {
                    d = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(d)).append("&_pfapp=").append(this.bB).toString())).append("&_pf=").append(this.bA).toString();
                }
                k kVar = new k(this.bv, str3);
                String[] strArr = new String[b];
                strArr[a] = d;
                kVar.execute(strArr);
            }
        }
    }

    public final void c(boolean z) {
        this.bm = z;
    }

    public final void d(String str) {
        this.bv.a(str);
        this.j = this.bv.l();
        if (this.k != null && this.k.equals(a.e)) {
            c(a.INSTALL);
        }
    }

    public final void d(String str, String str2) {
        this.bC = str;
        this.bD = str2;
    }

    public final void d(boolean z) {
        this.bn = z;
    }

    public final boolean d() {
        return this.bj;
    }

    public final void e(String str) {
        if (!this.bi) {
            this.bv.a(str);
            if (this.bv.f() && this.bg && this.bg) {
                String a = a(a.START, null, (int) a);
                j jVar = new j(this.bv, f.aA);
                String[] strArr = new String[b];
                strArr[a] = a;
                jVar.execute(strArr);
            }
        }
    }

    public final void e(String str, String str2) {
        b("default", str, str2);
    }

    public final void e(boolean z) {
        this.bo = z;
    }

    public final boolean e() {
        return this.bl;
    }

    public final void f(String str) {
        i(str);
    }

    public final void f(boolean z) {
        this.bp = z;
    }

    public final boolean f() {
        return this.bm;
    }

    public final void g(String str) {
        this.h = str;
    }

    public final void g(boolean z) {
        this.bq = z;
    }

    public final boolean g() {
        return this.br;
    }

    public final void h(boolean z) {
        this.br = z;
    }

    public final boolean h() {
        return this.bs;
    }

    public final void i() {
        this.bj = true;
    }

    public final void i(boolean z) {
        this.bs = z;
    }

    public final void j(boolean z) {
        this.be = z;
    }
}
