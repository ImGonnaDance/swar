package jp.co.dimage.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Log;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import com.com2us.module.manager.ModuleConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jp.appAdForce.android.AnalyticsManager;
import jp.co.cyberz.fox.a.a.i;

public final class d implements f {
    private static boolean bq = false;
    private Context a = null;
    private String b = i.a;
    private String bd = i.a;
    private boolean be = false;
    private boolean bf = false;
    private boolean bg = false;
    private boolean bh = false;
    private String bi;
    private String bj = i.a;
    private Boolean bk = Boolean.valueOf(false);
    private String bl = i.a;
    private String bm = i.a;
    private String bn = i.a;
    private String bo = i.a;
    private boolean bp = true;
    private String c = i.a;
    private a d;
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
    private boolean o = false;

    public enum a {
        IMEI,
        UUID,
        ADID;

        private static a a(String str) {
            return (a) Enum.valueOf(a.class, str);
        }

        private static a[] a() {
            Object obj = d;
            int length = obj.length;
            Object obj2 = new a[length];
            System.arraycopy(obj, 0, obj2, 0, length);
            return obj2;
        }
    }

    enum b {
        INSTALL(a.d),
        START(a.e),
        OTHERS(a.f);
        
        private String d;

        private b(String str) {
            this.d = str;
        }

        private String a() {
            return this.d;
        }

        private static b a(String str) {
            return (b) Enum.valueOf(b.class, str);
        }

        private static b[] b() {
            Object obj = e;
            int length = obj.length;
            Object obj2 = new b[length];
            System.arraycopy(obj, 0, obj2, 0, length);
            return obj2;
        }
    }

    public d(Context context) {
        this.a = context;
        R();
        O();
        L();
        this.n = UUID.randomUUID().toString();
        this.bi = new MessageFormat(f.s).format(new String[]{VERSION.RELEASE, Build.MODEL, Build.DEVICE});
        try {
            this.bl = this.a.getApplicationContext().getPackageName();
        } catch (Exception e) {
            a.b(f.aU, "setBundleId failed. " + e.getMessage());
        }
        try {
            this.bm = this.a.getPackageManager().getPackageInfo(this.a.getApplicationContext().getPackageName(), 1).versionName;
        } catch (NameNotFoundException e2) {
            a.b(f.aU, "setBundleVersion failed. " + e2.getMessage());
        }
        this.bn = URLEncoder.encode(Build.MODEL);
        this.bo = VERSION.RELEASE;
        M();
    }

    public static void H() {
        bq = true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void K() {
        /*
        r7 = this;
        r5 = 0;
        r0 = 0;
        r1 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0055, all -> 0x0079 }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0055, all -> 0x0079 }
        r2 = "xuid";
        r3 = r7.h;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0055, all -> 0x0079 }
        r1.setProperty(r2, r3);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0055, all -> 0x0079 }
        r2 = "xroute";
        r3 = r7.i;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0055, all -> 0x0079 }
        r1.setProperty(r2, r3);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0055, all -> 0x0079 }
        r2 = r7.a;	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0055, all -> 0x0079 }
        r3 = "__ADMAGE_CONVERSION__";
        r4 = 0;
        r0 = r2.openFileOutput(r3, r4);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x0055, all -> 0x0079 }
        r2 = "ADMAGE Session Information";
        r1.store(r0, r2);	 Catch:{ FileNotFoundException -> 0x0045, IOException -> 0x008e }
        if (r0 == 0) goto L_0x0028;
    L_0x0025:
        r0.close();	 Catch:{ IOException -> 0x0085 }
    L_0x0028:
        r0 = new java.text.MessageFormat;
        r1 = "save: xuid={0} xroute={1}";
        r0.<init>(r1);
        r1 = "F.O.X";
        r2 = 2;
        r2 = new java.lang.String[r2];
        r3 = r7.h;
        r2[r5] = r3;
        r3 = 1;
        r4 = r7.i;
        r2[r3] = r4;
        r0 = r0.format(r2);
        jp.co.dimage.android.a.d(r1, r0);
        return;
    L_0x0045:
        r1 = move-exception;
        r1 = "F.O.X";
        r2 = "saveConversion failed. file '__ADMAGE_CONVERSION__' not found.";
        jp.co.dimage.android.a.b(r1, r2);	 Catch:{ all -> 0x0087 }
        if (r0 == 0) goto L_0x0028;
    L_0x004f:
        r0.close();	 Catch:{ IOException -> 0x0053 }
        goto L_0x0028;
    L_0x0053:
        r0 = move-exception;
        goto L_0x0028;
    L_0x0055:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0059:
        r2 = "F.O.X";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x008c }
        r4 = "saveConversion failed. ";
        r3.<init>(r4);	 Catch:{ all -> 0x008c }
        r0 = r0.getMessage();	 Catch:{ all -> 0x008c }
        r0 = r3.append(r0);	 Catch:{ all -> 0x008c }
        r0 = r0.toString();	 Catch:{ all -> 0x008c }
        jp.co.dimage.android.a.b(r2, r0);	 Catch:{ all -> 0x008c }
        if (r1 == 0) goto L_0x0028;
    L_0x0073:
        r1.close();	 Catch:{ IOException -> 0x0077 }
        goto L_0x0028;
    L_0x0077:
        r0 = move-exception;
        goto L_0x0028;
    L_0x0079:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x007d:
        if (r1 == 0) goto L_0x0082;
    L_0x007f:
        r1.close();	 Catch:{ IOException -> 0x0083 }
    L_0x0082:
        throw r0;
    L_0x0083:
        r1 = move-exception;
        goto L_0x0082;
    L_0x0085:
        r0 = move-exception;
        goto L_0x0028;
    L_0x0087:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x007d;
    L_0x008c:
        r0 = move-exception;
        goto L_0x007d;
    L_0x008e:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0059;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.K():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void L() {
        /*
        r6 = this;
        monitor-enter(r6);
        r0 = 0;
        r1 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0060, all -> 0x0084 }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0060, all -> 0x0084 }
        r2 = r6.a;	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0060, all -> 0x0084 }
        r3 = "__ADMAGE_CONVERSION__";
        r0 = r2.openFileInput(r3);	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x0060, all -> 0x0084 }
        r1.load(r0);	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x009e, all -> 0x0097 }
        r2 = "xuid";
        r3 = "";
        r2 = r1.getProperty(r2, r3);	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x009e, all -> 0x0097 }
        r6.h = r2;	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x009e, all -> 0x0097 }
        r2 = "xroute";
        r3 = "";
        r1 = r1.getProperty(r2, r3);	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x009e, all -> 0x0097 }
        r6.i = r1;	 Catch:{ FileNotFoundException -> 0x0057, IOException -> 0x009e, all -> 0x0097 }
        if (r0 == 0) goto L_0x002b;
    L_0x0028:
        r0.close();	 Catch:{ IOException -> 0x0093 }
    L_0x002b:
        r0 = r6.a;	 Catch:{ NullPointerException -> 0x0095 }
        r0 = r0.fileList();	 Catch:{ NullPointerException -> 0x0095 }
        r0 = java.util.Arrays.asList(r0);	 Catch:{ NullPointerException -> 0x0095 }
        r1 = "__ADMAGE_WEB_CONVERSION_COMPLETED__";
        r1 = r0.contains(r1);	 Catch:{ NullPointerException -> 0x0095 }
        r6.be = r1;	 Catch:{ NullPointerException -> 0x0095 }
        r1 = "__ADMAGE_APP_CONVERSION_COMPLETED__";
        r1 = r0.contains(r1);	 Catch:{ NullPointerException -> 0x0095 }
        r6.bf = r1;	 Catch:{ NullPointerException -> 0x0095 }
        r1 = "__ADMAGE_CONVERSION_PAGE_OPENED__";
        r1 = r0.contains(r1);	 Catch:{ NullPointerException -> 0x0095 }
        r6.bg = r1;	 Catch:{ NullPointerException -> 0x0095 }
        r1 = "__ADMAGE_LINE_FC_CONVERSION_COMPLETED__";
        r0 = r0.contains(r1);	 Catch:{ NullPointerException -> 0x0095 }
        r6.bh = r0;	 Catch:{ NullPointerException -> 0x0095 }
    L_0x0055:
        monitor-exit(r6);
        return;
    L_0x0057:
        r1 = move-exception;
        if (r0 == 0) goto L_0x002b;
    L_0x005a:
        r0.close();	 Catch:{ IOException -> 0x005e }
        goto L_0x002b;
    L_0x005e:
        r0 = move-exception;
        goto L_0x002b;
    L_0x0060:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0064:
        r2 = "F.O.X";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x009c }
        r4 = "loadConversion failed. ";
        r3.<init>(r4);	 Catch:{ all -> 0x009c }
        r0 = r0.getMessage();	 Catch:{ all -> 0x009c }
        r0 = r3.append(r0);	 Catch:{ all -> 0x009c }
        r0 = r0.toString();	 Catch:{ all -> 0x009c }
        jp.co.dimage.android.a.b(r2, r0);	 Catch:{ all -> 0x009c }
        if (r1 == 0) goto L_0x002b;
    L_0x007e:
        r1.close();	 Catch:{ IOException -> 0x0082 }
        goto L_0x002b;
    L_0x0082:
        r0 = move-exception;
        goto L_0x002b;
    L_0x0084:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0088:
        if (r1 == 0) goto L_0x008d;
    L_0x008a:
        r1.close();	 Catch:{ IOException -> 0x0091 }
    L_0x008d:
        throw r0;	 Catch:{ all -> 0x008e }
    L_0x008e:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x0091:
        r1 = move-exception;
        goto L_0x008d;
    L_0x0093:
        r0 = move-exception;
        goto L_0x002b;
    L_0x0095:
        r0 = move-exception;
        goto L_0x0055;
    L_0x0097:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0088;
    L_0x009c:
        r0 = move-exception;
        goto L_0x0088;
    L_0x009e:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0064;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.L():void");
    }

    private synchronized void M() {
        Object obj = null;
        synchronized (this) {
            if (new q(this.a, this.k, this.l).b()) {
                obj = 1;
            }
            SharedPreferences sharedPreferences = this.a.getSharedPreferences(f.aC, 0);
            String string = sharedPreferences.getString(f.aD, i.a);
            if (p.a(string) || string.length() <= 0) {
                sharedPreferences.edit().putString(f.aD, obj != null ? a.e : a.d).commit();
            }
        }
    }

    private synchronized String N() {
        String str;
        q qVar = new q(this.a, this.k, this.l);
        if (qVar.b()) {
            this.bj = qVar.c();
            b(qVar.d());
            b(this.bj, qVar.d());
        }
        if (this.bj == null || this.bj.length() <= 0) {
            String str2 = i.a;
            String str3 = i.a;
            Boolean valueOf = Boolean.valueOf(false);
            str = i.a;
            String P = P();
            if (P == null) {
                P = i.a;
            }
            if (valueOf.booleanValue()) {
                if (!i.a(P)) {
                    str = a.f;
                }
                P = str2;
            } else if (i.a(P)) {
                if (!i.a(str3)) {
                    str = a.e;
                    P = str3;
                }
                P = str2;
            } else {
                str = a.f;
            }
            if (i.a(P)) {
                P = UUID.randomUUID().toString();
                str = a.f;
            }
            this.bj = P;
            b(this.bj, str);
            c(this.bj, str);
            str = this.bj;
        } else {
            str = this.bj;
        }
        return str;
    }

    private synchronized void O() {
        if (i.a(this.b)) {
            String P = P();
            b(Q());
            try {
                if (!i.b(P) || bq) {
                    this.b = l.a(P, jp.co.dimage.android.l.a.XUNIQ);
                } else {
                    this.b = P;
                }
            } catch (GeneralSecurityException e) {
                this.b = UUID.randomUUID().toString();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.lang.String P() {
        /*
        r6 = this;
        monitor-enter(r6);
        r1 = "";
        r0 = 0;
        r2 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r2.<init>();	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r3 = r6.a;	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r4 = "__ADMAGE_RANDOM_DEVICE_ID__";
        r0 = r3.openFileInput(r4);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r2.load(r0);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x0050 }
        r3 = "random_device_id";
        r4 = "";
        r1 = r2.getProperty(r3, r4);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x0050 }
        if (r0 == 0) goto L_0x004c;
    L_0x001e:
        r0.close();	 Catch:{ IOException -> 0x004b }
        r0 = r1;
    L_0x0022:
        if (r0 != 0) goto L_0x0026;
    L_0x0024:
        r0 = "";
    L_0x0026:
        monitor-exit(r6);
        return r0;
    L_0x0028:
        r2 = move-exception;
        if (r0 == 0) goto L_0x004c;
    L_0x002b:
        r0.close();	 Catch:{ IOException -> 0x0030 }
        r0 = r1;
        goto L_0x0022;
    L_0x0030:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0022;
    L_0x0033:
        r2 = move-exception;
        if (r0 == 0) goto L_0x004c;
    L_0x0036:
        r0.close();	 Catch:{ IOException -> 0x003b }
        r0 = r1;
        goto L_0x0022;
    L_0x003b:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0022;
    L_0x003e:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0042:
        if (r1 == 0) goto L_0x0047;
    L_0x0044:
        r1.close();	 Catch:{ IOException -> 0x004e }
    L_0x0047:
        throw r0;	 Catch:{ all -> 0x0048 }
    L_0x0048:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x004b:
        r0 = move-exception;
    L_0x004c:
        r0 = r1;
        goto L_0x0022;
    L_0x004e:
        r1 = move-exception;
        goto L_0x0047;
    L_0x0050:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.P():java.lang.String");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized java.lang.String Q() {
        /*
        r6 = this;
        monitor-enter(r6);
        r1 = "";
        r0 = 0;
        r2 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r2.<init>();	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r3 = r6.a;	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r4 = "__ADMAGE_RANDOM_DEVICE_ID__";
        r0 = r3.openFileInput(r4);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r2.load(r0);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x0050 }
        r3 = "random_device_id_type";
        r4 = "";
        r1 = r2.getProperty(r3, r4);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x0050 }
        if (r0 == 0) goto L_0x004c;
    L_0x001e:
        r0.close();	 Catch:{ IOException -> 0x004b }
        r0 = r1;
    L_0x0022:
        if (r0 != 0) goto L_0x0026;
    L_0x0024:
        r0 = "";
    L_0x0026:
        monitor-exit(r6);
        return r0;
    L_0x0028:
        r2 = move-exception;
        if (r0 == 0) goto L_0x004c;
    L_0x002b:
        r0.close();	 Catch:{ IOException -> 0x0030 }
        r0 = r1;
        goto L_0x0022;
    L_0x0030:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0022;
    L_0x0033:
        r2 = move-exception;
        if (r0 == 0) goto L_0x004c;
    L_0x0036:
        r0.close();	 Catch:{ IOException -> 0x003b }
        r0 = r1;
        goto L_0x0022;
    L_0x003b:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0022;
    L_0x003e:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0042:
        if (r1 == 0) goto L_0x0047;
    L_0x0044:
        r1.close();	 Catch:{ IOException -> 0x004e }
    L_0x0047:
        throw r0;	 Catch:{ all -> 0x0048 }
    L_0x0048:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x004b:
        r0 = move-exception;
    L_0x004c:
        r0 = r1;
        goto L_0x0022;
    L_0x004e:
        r1 = move-exception;
        goto L_0x0047;
    L_0x0050:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.Q():java.lang.String");
    }

    private synchronized void R() {
        PackageManager packageManager = this.a.getPackageManager();
        if (packageManager == null) {
            a.b(f.aU, "PackageManager is null.");
        } else {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.a.getPackageName(), 0);
                if (applicationInfo == null) {
                    a.b(f.aU, "ApplicationInfo is null.");
                } else {
                    if ((applicationInfo.flags & 2) == 2) {
                        a.a();
                        a.b();
                    }
                    try {
                        ApplicationInfo applicationInfo2 = this.a.getPackageManager().getApplicationInfo(this.a.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE);
                        if (applicationInfo2 == null) {
                            a.b(f.aU, "ApplicationInfo is null.");
                        } else {
                            this.e = a(applicationInfo2, f.t);
                            this.f = a(applicationInfo2, f.u);
                            String str = null;
                            try {
                                str = a(applicationInfo2, f.v);
                                if (p.a(str)) {
                                    this.g = i.a;
                                } else {
                                    this.g = l.a(str);
                                }
                            } catch (GeneralSecurityException e) {
                                a.b(f.aU, "loadApplicationInfo faild. APPADFORCE_SERVER_URL = " + str + ". " + e.getMessage());
                            }
                            this.j = a(applicationInfo2, f.w);
                            this.m = a(applicationInfo2, f.y);
                            this.bd = a(applicationInfo2, f.z);
                            this.k = a(applicationInfo2, f.H);
                            this.l = a(applicationInfo2, f.I);
                            a(a.UUID);
                            this.c = a();
                            MessageFormat messageFormat = new MessageFormat("appId={0} appOptions={1} serverUrl={2} salt={3}");
                            if (aW.booleanValue()) {
                                a.d(f.aU, messageFormat.format(new String[]{this.e, this.f, this.g, this.bd}));
                            } else {
                                a.d(f.aU, messageFormat.format(new String[]{this.e, this.f, str, this.bd}));
                            }
                        }
                    } catch (NameNotFoundException e2) {
                        a.b(f.aU, "loadApplicationInfo faild. " + e2.getMessage());
                    }
                }
            } catch (NameNotFoundException e22) {
                a.b(f.aU, "loadApplicationInfo faild. " + e22.getMessage());
            }
        }
    }

    private void S() {
        Pattern compile = Pattern.compile("[0-9a-zA-Z-_\\.]*");
        if (this.e == null || this.e.length() == 0) {
            throw new IllegalStateException("Invalid APPADFORCE_APP_ID");
        } else if (this.e.length() > 8) {
            throw new IllegalStateException("Invalid APPADFORCE_APP_ID");
        } else {
            try {
                if (Integer.parseInt(this.e) <= 0) {
                    throw new IllegalStateException("Invalid APPADFORCE_APP_ID");
                }
                if (this.f != null && this.f.length() > 0) {
                    if (this.f.length() > 32) {
                        throw new IllegalStateException("Invalid APPADFORCE_APP_OPTIONS. Too long.");
                    }
                    Matcher matcher = compile.matcher(this.f);
                    if (matcher == null || !matcher.matches()) {
                        throw new IllegalStateException("Invalid APPADFORCE_APP_OPTIONS. Contains invalid characters.");
                    }
                }
                if (this.g == null || this.g.length() == 0) {
                    throw new IllegalStateException("Invalid APPADFORCE_SERVER_URL");
                } else if (this.g != null && this.g.length() > ModuleConfig.MERCURY_MODULE) {
                    throw new IllegalStateException("Invalid APPADFORCE_SERVER_URL");
                } else if (this.j != null && this.j.length() > 0 && !this.j.equals(a.e)) {
                    throw new IllegalStateException("Invalid APPADFORCE_INSTALL_CV");
                } else if (this.m != null && this.m.length() > 0 && !this.m.equals(a.e)) {
                    throw new IllegalStateException("Invalid APPADFORCE_TEST_MODE");
                }
            } catch (NumberFormatException e) {
                throw new IllegalStateException("Invalid APPADFORCE_APP_ID");
            }
        }
    }

    private void T() {
        this.n = UUID.randomUUID().toString();
    }

    private boolean U() {
        return this.bk.booleanValue();
    }

    private void V() {
        try {
            this.bl = this.a.getApplicationContext().getPackageName();
        } catch (Exception e) {
            a.b(f.aU, "setBundleId failed. " + e.getMessage());
        }
    }

    private void W() {
        try {
            this.bm = this.a.getPackageManager().getPackageInfo(this.a.getApplicationContext().getPackageName(), 1).versionName;
        } catch (NameNotFoundException e) {
            a.b(f.aU, "setBundleVersion failed. " + e.getMessage());
        }
    }

    private void X() {
        this.bn = URLEncoder.encode(Build.MODEL);
    }

    private void Y() {
        this.bo = VERSION.RELEASE;
    }

    private static boolean Z() {
        return bq;
    }

    public static synchronized String a(Context context) {
        String str;
        synchronized (d.class) {
            str = i.a;
            FileInputStream fileInputStream = null;
            try {
                Properties properties = new Properties();
                fileInputStream = context.openFileInput(f.aw);
                properties.load(fileInputStream);
                str = properties.getProperty(Gateway.REQUEST_REFERRER, i.a);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                    }
                }
            } catch (FileNotFoundException e2) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e3) {
                    }
                }
            } catch (IOException e4) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e5) {
                    }
                }
            } catch (Throwable th) {
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e6) {
                    }
                }
            }
        }
        return str;
    }

    private static String a(ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return i.a;
        }
        Object obj = applicationInfo.metaData.get(str);
        return obj == null ? i.a : obj.toString();
    }

    private static String a(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            try {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuffer.append(readLine);
            } finally {
                bufferedReader.close();
            }
        }
        String stringBuffer2 = stringBuffer.toString();
        return stringBuffer2;
    }

    public static void a(String str, jp.co.dimage.android.InstallReceiver.a aVar) {
        int i = 0;
        if (!p.a(str) && str.length() > 5 && "LINE_".equals(str.substring(0, 5))) {
            i = 1;
        }
        if (i != 0) {
            aVar.a();
        } else {
            aVar.b();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b(java.lang.String r7, java.lang.String r8) {
        /*
        r6 = this;
        monitor-enter(r6);
        if (r7 == 0) goto L_0x0009;
    L_0x0003:
        r0 = r7.length();	 Catch:{ all -> 0x006f }
        if (r0 > 0) goto L_0x000b;
    L_0x0009:
        monitor-exit(r6);
        return;
    L_0x000b:
        r0 = 0;
        r1 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x0041, all -> 0x0065 }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x0041, all -> 0x0065 }
        r2 = "random_device_id";
        r1.setProperty(r2, r7);	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x0041, all -> 0x0065 }
        r2 = "random_device_id_type";
        r1.setProperty(r2, r8);	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x0041, all -> 0x0065 }
        r2 = r6.a;	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x0041, all -> 0x0065 }
        r3 = "__ADMAGE_RANDOM_DEVICE_ID__";
        r4 = 0;
        r0 = r2.openFileOutput(r3, r4);	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x0041, all -> 0x0065 }
        r2 = "ADMAGE Random Device ID";
        r1.store(r0, r2);	 Catch:{ FileNotFoundException -> 0x0031, IOException -> 0x007b }
        if (r0 == 0) goto L_0x0009;
    L_0x002b:
        r0.close();	 Catch:{ IOException -> 0x002f }
        goto L_0x0009;
    L_0x002f:
        r0 = move-exception;
        goto L_0x0009;
    L_0x0031:
        r1 = move-exception;
        r1 = "F.O.X";
        r2 = "saveRandomDeviceId failed. file '__ADMAGE_RANDOM_DEVICE_ID__' not found.";
        jp.co.dimage.android.a.b(r1, r2);	 Catch:{ all -> 0x0074 }
        if (r0 == 0) goto L_0x0009;
    L_0x003b:
        r0.close();	 Catch:{ IOException -> 0x003f }
        goto L_0x0009;
    L_0x003f:
        r0 = move-exception;
        goto L_0x0009;
    L_0x0041:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0045:
        r2 = "F.O.X";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0079 }
        r4 = "saveRandomDeviceId failed. ";
        r3.<init>(r4);	 Catch:{ all -> 0x0079 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0079 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x0079 }
        r0 = r0.toString();	 Catch:{ all -> 0x0079 }
        jp.co.dimage.android.a.b(r2, r0);	 Catch:{ all -> 0x0079 }
        if (r1 == 0) goto L_0x0009;
    L_0x005f:
        r1.close();	 Catch:{ IOException -> 0x0063 }
        goto L_0x0009;
    L_0x0063:
        r0 = move-exception;
        goto L_0x0009;
    L_0x0065:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0069:
        if (r1 == 0) goto L_0x006e;
    L_0x006b:
        r1.close();	 Catch:{ IOException -> 0x0072 }
    L_0x006e:
        throw r0;	 Catch:{ all -> 0x006f }
    L_0x006f:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x0072:
        r1 = move-exception;
        goto L_0x006e;
    L_0x0074:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0069;
    L_0x0079:
        r0 = move-exception;
        goto L_0x0069;
    L_0x007b:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0045;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.b(java.lang.String, java.lang.String):void");
    }

    private synchronized void c(String str, String str2) {
        if (!(p.a(str) || p.a(str2))) {
            q qVar = new q(this.a, this.k, this.l);
            if (qVar.c() == null) {
                qVar.c(str2, str);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void d(java.lang.String r8) {
        /*
        r7 = this;
        r5 = 0;
        r0 = 0;
        r1 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x0045, all -> 0x0069 }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x0045, all -> 0x0069 }
        r2 = "referrer";
        r1.setProperty(r2, r8);	 Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x0045, all -> 0x0069 }
        r2 = r7.a;	 Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x0045, all -> 0x0069 }
        r3 = "__ADMAGE_REFERRER__";
        r4 = 0;
        r0 = r2.openFileOutput(r3, r4);	 Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x0045, all -> 0x0069 }
        r2 = "ADMAGE Session Information";
        r1.store(r0, r2);	 Catch:{ FileNotFoundException -> 0x0035, IOException -> 0x007e }
        if (r0 == 0) goto L_0x001f;
    L_0x001c:
        r0.close();	 Catch:{ IOException -> 0x0075 }
    L_0x001f:
        r0 = new java.text.MessageFormat;
        r1 = "save: referrer={0}";
        r0.<init>(r1);
        r1 = "F.O.X";
        r2 = 1;
        r2 = new java.lang.String[r2];
        r2[r5] = r8;
        r0 = r0.format(r2);
        jp.co.dimage.android.a.d(r1, r0);
        return;
    L_0x0035:
        r1 = move-exception;
        r1 = "F.O.X";
        r2 = "saveReferrer failed. file '__ADMAGE_REFERRER__' not found.";
        jp.co.dimage.android.a.b(r1, r2);	 Catch:{ all -> 0x0077 }
        if (r0 == 0) goto L_0x001f;
    L_0x003f:
        r0.close();	 Catch:{ IOException -> 0x0043 }
        goto L_0x001f;
    L_0x0043:
        r0 = move-exception;
        goto L_0x001f;
    L_0x0045:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0049:
        r2 = "F.O.X";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x007c }
        r4 = "saveReferrer failed. ";
        r3.<init>(r4);	 Catch:{ all -> 0x007c }
        r0 = r0.getMessage();	 Catch:{ all -> 0x007c }
        r0 = r3.append(r0);	 Catch:{ all -> 0x007c }
        r0 = r0.toString();	 Catch:{ all -> 0x007c }
        jp.co.dimage.android.a.b(r2, r0);	 Catch:{ all -> 0x007c }
        if (r1 == 0) goto L_0x001f;
    L_0x0063:
        r1.close();	 Catch:{ IOException -> 0x0067 }
        goto L_0x001f;
    L_0x0067:
        r0 = move-exception;
        goto L_0x001f;
    L_0x0069:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x006d:
        if (r1 == 0) goto L_0x0072;
    L_0x006f:
        r1.close();	 Catch:{ IOException -> 0x0073 }
    L_0x0072:
        throw r0;
    L_0x0073:
        r1 = move-exception;
        goto L_0x0072;
    L_0x0075:
        r0 = move-exception;
        goto L_0x001f;
    L_0x0077:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x006d;
    L_0x007c:
        r0 = move-exception;
        goto L_0x006d;
    L_0x007e:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.d(java.lang.String):void");
    }

    private void e(String str) {
        this.bd = str;
    }

    private static boolean f(String str) {
        return !p.a(str) && str.length() > 5 && "LINE_".equals(str.substring(0, 5));
    }

    public static boolean g() {
        if (!new File("/system/bin/su").exists()) {
            return false;
        }
        a.d(f.aU, "su fund!");
        return true;
    }

    public final String A() {
        return this.bl;
    }

    public final String B() {
        return this.bm;
    }

    public final String C() {
        return this.bn;
    }

    public final String D() {
        return this.bo;
    }

    public final String E() {
        return this.bd;
    }

    public final boolean F() {
        return this.o;
    }

    public final boolean G() {
        return this.bp;
    }

    public final String I() {
        return this.k;
    }

    public final String J() {
        return this.l;
    }

    public final synchronized String a() {
        return a(this.a);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(java.lang.String r9) {
        /*
        r8 = this;
        r6 = 2;
        r0 = 0;
        monitor-enter(r8);
        if (r9 == 0) goto L_0x000b;
    L_0x0005:
        r1 = r9.length();	 Catch:{ all -> 0x007f }
        if (r1 != 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r8);
        return;
    L_0x000d:
        r1 = "F.O.X";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x007f }
        r3 = "referrer: ";
        r2.<init>(r3);	 Catch:{ all -> 0x007f }
        r2 = r2.append(r9);	 Catch:{ all -> 0x007f }
        r2 = r2.toString();	 Catch:{ all -> 0x007f }
        jp.co.dimage.android.a.d(r1, r2);	 Catch:{ all -> 0x007f }
        r1 = "&";
        r1 = r9.split(r1);	 Catch:{ all -> 0x007f }
        r2 = r1.length;	 Catch:{ all -> 0x007f }
    L_0x0028:
        if (r0 < r2) goto L_0x0082;
    L_0x002a:
        r8.K();	 Catch:{ all -> 0x007f }
        r0 = new java.text.MessageFormat;	 Catch:{ all -> 0x007f }
        r1 = "update: xuid={0} xroute={1}";
        r0.<init>(r1);	 Catch:{ all -> 0x007f }
        r1 = "F.O.X";
        r2 = 2;
        r2 = new java.lang.String[r2];	 Catch:{ all -> 0x007f }
        r3 = 0;
        r4 = r8.h;	 Catch:{ all -> 0x007f }
        r2[r3] = r4;	 Catch:{ all -> 0x007f }
        r3 = 1;
        r4 = r8.i;	 Catch:{ all -> 0x007f }
        r2[r3] = r4;	 Catch:{ all -> 0x007f }
        r0 = r0.format(r2);	 Catch:{ all -> 0x007f }
        jp.co.dimage.android.a.d(r1, r0);	 Catch:{ all -> 0x007f }
        r0 = 0;
        r1 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x00b1, IOException -> 0x00c1, all -> 0x00e5 }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x00b1, IOException -> 0x00c1, all -> 0x00e5 }
        r2 = "referrer";
        r1.setProperty(r2, r9);	 Catch:{ FileNotFoundException -> 0x00b1, IOException -> 0x00c1, all -> 0x00e5 }
        r2 = r8.a;	 Catch:{ FileNotFoundException -> 0x00b1, IOException -> 0x00c1, all -> 0x00e5 }
        r3 = "__ADMAGE_REFERRER__";
        r4 = 0;
        r0 = r2.openFileOutput(r3, r4);	 Catch:{ FileNotFoundException -> 0x00b1, IOException -> 0x00c1, all -> 0x00e5 }
        r2 = "ADMAGE Session Information";
        r1.store(r0, r2);	 Catch:{ FileNotFoundException -> 0x00b1, IOException -> 0x00fb }
        if (r0 == 0) goto L_0x0068;
    L_0x0065:
        r0.close();	 Catch:{ IOException -> 0x00f1 }
    L_0x0068:
        r0 = new java.text.MessageFormat;	 Catch:{ all -> 0x007f }
        r1 = "save: referrer={0}";
        r0.<init>(r1);	 Catch:{ all -> 0x007f }
        r1 = "F.O.X";
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ all -> 0x007f }
        r3 = 0;
        r2[r3] = r9;	 Catch:{ all -> 0x007f }
        r0 = r0.format(r2);	 Catch:{ all -> 0x007f }
        jp.co.dimage.android.a.d(r1, r0);	 Catch:{ all -> 0x007f }
        goto L_0x000b;
    L_0x007f:
        r0 = move-exception;
        monitor-exit(r8);
        throw r0;
    L_0x0082:
        r3 = r1[r0];	 Catch:{ all -> 0x007f }
        r4 = "=";
        r3 = r3.split(r4);	 Catch:{ all -> 0x007f }
        r4 = r3.length;	 Catch:{ all -> 0x007f }
        if (r4 != r6) goto L_0x00ad;
    L_0x008d:
        r4 = "_xuid";
        r5 = 0;
        r5 = r3[r5];	 Catch:{ all -> 0x007f }
        r4 = r4.equals(r5);	 Catch:{ all -> 0x007f }
        if (r4 == 0) goto L_0x009d;
    L_0x0098:
        r4 = 1;
        r4 = r3[r4];	 Catch:{ all -> 0x007f }
        r8.h = r4;	 Catch:{ all -> 0x007f }
    L_0x009d:
        r4 = "_xroute";
        r5 = 0;
        r5 = r3[r5];	 Catch:{ all -> 0x007f }
        r4 = r4.equals(r5);	 Catch:{ all -> 0x007f }
        if (r4 == 0) goto L_0x00ad;
    L_0x00a8:
        r4 = 1;
        r3 = r3[r4];	 Catch:{ all -> 0x007f }
        r8.i = r3;	 Catch:{ all -> 0x007f }
    L_0x00ad:
        r0 = r0 + 1;
        goto L_0x0028;
    L_0x00b1:
        r1 = move-exception;
        r1 = "F.O.X";
        r2 = "saveReferrer failed. file '__ADMAGE_REFERRER__' not found.";
        jp.co.dimage.android.a.b(r1, r2);	 Catch:{ all -> 0x00f4 }
        if (r0 == 0) goto L_0x0068;
    L_0x00bb:
        r0.close();	 Catch:{ IOException -> 0x00bf }
        goto L_0x0068;
    L_0x00bf:
        r0 = move-exception;
        goto L_0x0068;
    L_0x00c1:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x00c5:
        r2 = "F.O.X";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f9 }
        r4 = "saveReferrer failed. ";
        r3.<init>(r4);	 Catch:{ all -> 0x00f9 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x00f9 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x00f9 }
        r0 = r0.toString();	 Catch:{ all -> 0x00f9 }
        jp.co.dimage.android.a.b(r2, r0);	 Catch:{ all -> 0x00f9 }
        if (r1 == 0) goto L_0x0068;
    L_0x00df:
        r1.close();	 Catch:{ IOException -> 0x00e3 }
        goto L_0x0068;
    L_0x00e3:
        r0 = move-exception;
        goto L_0x0068;
    L_0x00e5:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
    L_0x00e9:
        if (r1 == 0) goto L_0x00ee;
    L_0x00eb:
        r1.close();	 Catch:{ IOException -> 0x00ef }
    L_0x00ee:
        throw r0;	 Catch:{ all -> 0x007f }
    L_0x00ef:
        r1 = move-exception;
        goto L_0x00ee;
    L_0x00f1:
        r0 = move-exception;
        goto L_0x0068;
    L_0x00f4:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x00e9;
    L_0x00f9:
        r0 = move-exception;
        goto L_0x00e9;
    L_0x00fb:
        r1 = move-exception;
        r7 = r1;
        r1 = r0;
        r0 = r7;
        goto L_0x00c5;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.a(java.lang.String):void");
    }

    public final void a(String str, String str2) {
        this.bj = str;
        b(str, str2);
        c(str, str2);
    }

    public final synchronized void a(a aVar) {
        this.d = aVar;
        if (aVar == a.UUID) {
            this.bk = Boolean.valueOf(true);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void a(boolean r7) {
        /*
        r6 = this;
        monitor-enter(r6);
        r0 = 0;
        if (r7 == 0) goto L_0x0025;
    L_0x0004:
        r1 = "1";
    L_0x0006:
        r2 = new java.util.Properties;	 Catch:{ Exception -> 0x0028, all -> 0x0031 }
        r2.<init>();	 Catch:{ Exception -> 0x0028, all -> 0x0031 }
        r3 = "car_flg";
        r2.setProperty(r3, r1);	 Catch:{ Exception -> 0x0028, all -> 0x0031 }
        r1 = r6.a;	 Catch:{ Exception -> 0x0028, all -> 0x0031 }
        r3 = "__FOX_REINSTALL_CAR__";
        r4 = 0;
        r0 = r1.openFileOutput(r3, r4);	 Catch:{ Exception -> 0x0028, all -> 0x0031 }
        r1 = "CAReward reinstall flg";
        r2.store(r0, r1);	 Catch:{ Exception -> 0x0028, all -> 0x0042 }
        if (r0 == 0) goto L_0x0023;
    L_0x0020:
        r0.close();	 Catch:{ IOException -> 0x0040 }
    L_0x0023:
        monitor-exit(r6);
        return;
    L_0x0025:
        r1 = "0";
        goto L_0x0006;
    L_0x0028:
        r1 = move-exception;
        if (r0 == 0) goto L_0x0023;
    L_0x002b:
        r0.close();	 Catch:{ IOException -> 0x002f }
        goto L_0x0023;
    L_0x002f:
        r0 = move-exception;
        goto L_0x0023;
    L_0x0031:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0035:
        if (r1 == 0) goto L_0x003a;
    L_0x0037:
        r1.close();	 Catch:{ IOException -> 0x003e }
    L_0x003a:
        throw r0;	 Catch:{ all -> 0x003b }
    L_0x003b:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x003e:
        r1 = move-exception;
        goto L_0x003a;
    L_0x0040:
        r0 = move-exception;
        goto L_0x0023;
    L_0x0042:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0035;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.a(boolean):void");
    }

    public final boolean a(Uri uri) {
        String queryParameter = uri.getQueryParameter("_xuid");
        String queryParameter2 = uri.getQueryParameter(f.ao);
        if (p.a(queryParameter) || p.a(queryParameter2) || !a.f.equals(queryParameter2)) {
            return false;
        }
        this.h = queryParameter;
        this.i = queryParameter2;
        K();
        a.d(f.aU, MessageFormat.format("update: xuid={0} xroute={1}", new Object[]{this.h, this.i}));
        return true;
    }

    public final synchronized void b() {
        String N = N();
        try {
            if (!i.b(N) || bq) {
                this.b = l.a(N, jp.co.dimage.android.l.a.XUNIQ);
            } else {
                this.b = N;
            }
        } catch (GeneralSecurityException e) {
            this.b = UUID.randomUUID().toString();
            c(this.b, a.f);
        }
    }

    public final synchronized void b(String str) {
        if (a.e.equals(str)) {
            a(a.IMEI);
        }
        if (a.f.equals(str)) {
            a(a.UUID);
        }
        if ("4".equals(str)) {
            a(a.ADID);
        }
    }

    public final void b(boolean z) {
        this.o = z;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void c(java.lang.String r7) {
        /*
        r6 = this;
        monitor-enter(r6);
        r0 = jp.co.dimage.android.p.a(r7);	 Catch:{ all -> 0x003d }
        if (r0 == 0) goto L_0x0009;
    L_0x0007:
        monitor-exit(r6);
        return;
    L_0x0009:
        r0 = 0;
        r1 = new java.util.Properties;	 Catch:{ Exception -> 0x002a, all -> 0x0033 }
        r1.<init>();	 Catch:{ Exception -> 0x002a, all -> 0x0033 }
        r2 = "buid";
        r1.setProperty(r2, r7);	 Catch:{ Exception -> 0x002a, all -> 0x0033 }
        r2 = r6.a;	 Catch:{ Exception -> 0x002a, all -> 0x0033 }
        r3 = "__ADMAGE_BUID__";
        r4 = 0;
        r0 = r2.openFileOutput(r3, r4);	 Catch:{ Exception -> 0x002a, all -> 0x0033 }
        r2 = "Buyer unique id";
        r1.store(r0, r2);	 Catch:{ Exception -> 0x002a, all -> 0x0042 }
        if (r0 == 0) goto L_0x0007;
    L_0x0024:
        r0.close();	 Catch:{ IOException -> 0x0028 }
        goto L_0x0007;
    L_0x0028:
        r0 = move-exception;
        goto L_0x0007;
    L_0x002a:
        r1 = move-exception;
        if (r0 == 0) goto L_0x0007;
    L_0x002d:
        r0.close();	 Catch:{ IOException -> 0x0031 }
        goto L_0x0007;
    L_0x0031:
        r0 = move-exception;
        goto L_0x0007;
    L_0x0033:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0037:
        if (r1 == 0) goto L_0x003c;
    L_0x0039:
        r1.close();	 Catch:{ IOException -> 0x0040 }
    L_0x003c:
        throw r0;	 Catch:{ all -> 0x003d }
    L_0x003d:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x0040:
        r1 = move-exception;
        goto L_0x003c;
    L_0x0042:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.d.c(java.lang.String):void");
    }

    public final void c(boolean z) {
        this.bp = z;
    }

    public final boolean c() {
        return !p.a(P());
    }

    public final synchronized String d() {
        String str;
        Object obj;
        Throwable th;
        FileInputStream fileInputStream = null;
        synchronized (this) {
            FileInputStream openFileInput;
            try {
                Properties properties = new Properties();
                openFileInput = this.a.openFileInput(f.ax);
                try {
                    properties.load(openFileInput);
                    String property = properties.getProperty("buid", "Buyer unique id");
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                            str = property;
                        } catch (IOException e) {
                        }
                        if (str == null) {
                            str = i.a;
                        }
                    }
                    str = property;
                } catch (Exception e2) {
                    if (openFileInput == null) {
                        obj = fileInputStream;
                    } else {
                        try {
                            openFileInput.close();
                            obj = fileInputStream;
                        } catch (IOException e3) {
                            obj = fileInputStream;
                        }
                    }
                    if (str == null) {
                        str = i.a;
                    }
                    return str;
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    fileInputStream = openFileInput;
                    th = th3;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                openFileInput = fileInputStream;
                if (openFileInput == null) {
                    openFileInput.close();
                    obj = fileInputStream;
                } else {
                    obj = fileInputStream;
                }
                if (str == null) {
                    str = i.a;
                }
                return str;
            } catch (Throwable th4) {
                th = th4;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
            if (str == null) {
                str = i.a;
            }
        }
        return str;
    }

    public final synchronized String e() {
        FileInputStream openFileInput;
        String str;
        Object obj;
        Throwable th;
        FileInputStream fileInputStream = null;
        synchronized (this) {
            try {
                Properties properties = new Properties();
                openFileInput = this.a.openFileInput(f.az);
                try {
                    properties.load(openFileInput);
                    String property = properties.getProperty("car_flg", "CAReward reinstall flg");
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                            str = property;
                        } catch (IOException e) {
                        }
                        if (str == null) {
                            str = i.a;
                        }
                    }
                    str = property;
                } catch (Exception e2) {
                    if (openFileInput == null) {
                        obj = fileInputStream;
                    } else {
                        try {
                            openFileInput.close();
                            obj = fileInputStream;
                        } catch (IOException e3) {
                            obj = fileInputStream;
                        }
                    }
                    if (str == null) {
                        str = i.a;
                    }
                    return str;
                } catch (Throwable th2) {
                    Throwable th3 = th2;
                    fileInputStream = openFileInput;
                    th = th3;
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (Exception e5) {
                openFileInput = fileInputStream;
                if (openFileInput == null) {
                    openFileInput.close();
                    obj = fileInputStream;
                } else {
                    obj = fileInputStream;
                }
                if (str == null) {
                    str = i.a;
                }
                return str;
            } catch (Throwable th4) {
                th = th4;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                throw th;
            }
            if (str == null) {
                str = i.a;
            }
        }
        return str;
    }

    public final boolean f() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.a.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo == null ? false : activeNetworkInfo.isConnected();
    }

    public final void h() {
        AnalyticsManager.sendStartSession(this.a);
    }

    public final void i() {
        String string = this.a.getSharedPreferences(jp.co.cyberz.fox.notify.a.b, 0).getString(jp.co.cyberz.fox.notify.a.e, i.a);
        if (p.a(string)) {
            Log.i("F.O.X Notify", "Registration not found.");
        } else {
            jp.co.cyberz.fox.notify.a.b(this.a, string);
        }
    }

    public final String j() {
        return this.bi;
    }

    public final Context k() {
        return this.a;
    }

    public final String l() {
        return this.h;
    }

    public final String m() {
        return this.i;
    }

    public final String n() {
        return this.b;
    }

    public final String o() {
        return this.e;
    }

    public final String p() {
        return this.f;
    }

    public final String q() {
        return this.c;
    }

    public final String r() {
        return this.j;
    }

    public final String s() {
        return this.g;
    }

    public final String t() {
        return this.m;
    }

    public final String u() {
        return this.n;
    }

    public final a v() {
        return this.d;
    }

    public final boolean w() {
        return this.be;
    }

    public final boolean x() {
        return this.bf;
    }

    public final boolean y() {
        return this.bg;
    }

    public final boolean z() {
        return this.bh;
    }
}
