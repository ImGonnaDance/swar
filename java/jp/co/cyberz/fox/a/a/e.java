package jp.co.cyberz.fox.a.a;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.util.Log;
import com.com2us.module.manager.ModuleConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import jp.appAdForce.android.AdManager;
import jp.co.dimage.android.b;
import jp.co.dimage.android.d;
import jp.co.dimage.android.d.a;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;
import jp.co.dimage.android.p;
import org.apache.http.message.BasicNameValuePair;

public final class e implements f {
    public static List a = new ArrayList();
    public static c b = null;
    private static e bd = null;
    private static int be = 0;
    private static File bi = null;
    private static final String bj = "__e.fox";
    private static final long bk = 30000;
    private static boolean bl = false;
    protected static String c = null;
    protected static String d = i.a;
    protected static String e = i.a;
    protected static String f = i.a;
    protected static String g = i.a;
    protected static String h = i.a;
    protected static String i = i.a;
    protected static String j = "http://analytics.app-adforce.jp/fax/analytics";
    protected static String k = a.e;
    protected static String l = a.d;
    public static String m = i.a;
    public static long n = 0;
    protected static final String o = "2";
    private int bf = 0;
    private String bg;
    private String bh;
    private Activity bm = null;

    private e(AdManager adManager) {
        d a = adManager.a();
        d = a.o();
        f = a.n();
        g = a.u();
        h = a.l();
        m = a.j();
        Context k = a.k();
        if (k instanceof Activity) {
            this.bm = (Activity) k;
        }
        bi = k.getFilesDir();
        i = AdManager.a;
        try {
            ApplicationInfo applicationInfo = k.getPackageManager().getApplicationInfo(k.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE);
            if (applicationInfo == null) {
                Log.d(f.aV, "ApplicationInfo is null.");
            }
            d = a(applicationInfo, f.t);
            e = a(applicationInfo, f.F);
            String a2 = a(applicationInfo, f.B);
            if (a2 != null && a2.length() > 0) {
                j = a2;
                try {
                    j = h.a(j);
                } catch (Exception e) {
                    Log.d(f.aV, "server url is broken");
                }
            }
            try {
                this.bf = Integer.valueOf(a(applicationInfo, f.C)).intValue();
            } catch (Exception e2) {
            }
            a2 = a(applicationInfo, f.D);
            if (a2 != null && a2.length() > 0) {
                k = a2;
            }
            String a3 = a(applicationInfo, f.E);
            if (a3 != null && a3.length() > 0) {
                l = a3;
            }
            Log.d(f.aV, "APP ID " + d);
            Log.d(f.aV, "debug mode " + l);
            p.a(k.getApplicationContext());
            if (a.ADID == a.v()) {
                b.a(k, null);
            }
            f();
        } catch (NameNotFoundException e3) {
            Log.d(f.aV, "loadApplicationInfo faild. " + e3.getMessage());
        }
    }

    private static String a(ApplicationInfo applicationInfo, String str) {
        Object obj = applicationInfo.metaData.get(str);
        return obj == null ? i.a : obj.toString();
    }

    public static e a(AdManager adManager) {
        if (bd == null) {
            bd = new e(adManager);
        }
        return bd;
    }

    public static void a() {
        if (b != null) {
            b.c();
        }
        g();
    }

    private void a(Context context, a aVar) {
        if ("4".equals(c(context))) {
            new o(context).a(true, new f(this, aVar));
        } else {
            a(aVar);
        }
    }

    public static void a(List list) {
        List arrayList = new ArrayList();
        for (a aVar : list) {
            aVar.a();
            arrayList.add(aVar);
        }
        for (a aVar2 : a) {
            arrayList.add(aVar2);
        }
        a = arrayList;
        g();
    }

    private void a(a aVar) {
        be++;
        aVar.a(be);
        aVar.i(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        a.add(aVar);
        int i = this.bf;
        d();
        g();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.Boolean b(android.content.Context r7) {
        /*
        r2 = jp.co.cyberz.fox.a.a.e.class;
        monitor-enter(r2);
        r1 = "";
        r0 = 0;
        r3 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0033, IOException -> 0x003e, all -> 0x0049 }
        r3.<init>();	 Catch:{ FileNotFoundException -> 0x0033, IOException -> 0x003e, all -> 0x0049 }
        r4 = "__ADMAGE_RANDOM_DEVICE_ID__";
        r0 = r7.openFileInput(r4);	 Catch:{ FileNotFoundException -> 0x0033, IOException -> 0x003e, all -> 0x0049 }
        r3.load(r0);	 Catch:{ FileNotFoundException -> 0x0033, IOException -> 0x003e, all -> 0x0061 }
        r4 = "random_device_id";
        r5 = "";
        r1 = r3.getProperty(r4, r5);	 Catch:{ FileNotFoundException -> 0x0033, IOException -> 0x003e, all -> 0x0061 }
        if (r0 == 0) goto L_0x0057;
    L_0x001e:
        r0.close();	 Catch:{ IOException -> 0x0056 }
        r0 = r1;
    L_0x0022:
        if (r0 == 0) goto L_0x002c;
    L_0x0024:
        r1 = "";
        r0 = r0.equals(r1);	 Catch:{ all -> 0x0053 }
        if (r0 == 0) goto L_0x0059;
    L_0x002c:
        r0 = 0;
        r0 = java.lang.Boolean.valueOf(r0);	 Catch:{ all -> 0x0053 }
    L_0x0031:
        monitor-exit(r2);
        return r0;
    L_0x0033:
        r3 = move-exception;
        if (r0 == 0) goto L_0x0057;
    L_0x0036:
        r0.close();	 Catch:{ IOException -> 0x003b }
        r0 = r1;
        goto L_0x0022;
    L_0x003b:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0022;
    L_0x003e:
        r3 = move-exception;
        if (r0 == 0) goto L_0x0057;
    L_0x0041:
        r0.close();	 Catch:{ IOException -> 0x0046 }
        r0 = r1;
        goto L_0x0022;
    L_0x0046:
        r0 = move-exception;
        r0 = r1;
        goto L_0x0022;
    L_0x0049:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x004d:
        if (r1 == 0) goto L_0x0052;
    L_0x004f:
        r1.close();	 Catch:{ IOException -> 0x005f }
    L_0x0052:
        throw r0;	 Catch:{ all -> 0x0053 }
    L_0x0053:
        r0 = move-exception;
        monitor-exit(r2);
        throw r0;
    L_0x0056:
        r0 = move-exception;
    L_0x0057:
        r0 = r1;
        goto L_0x0022;
    L_0x0059:
        r0 = 1;
        r0 = java.lang.Boolean.valueOf(r0);	 Catch:{ all -> 0x0053 }
        goto L_0x0031;
    L_0x005f:
        r1 = move-exception;
        goto L_0x0052;
    L_0x0061:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x004d;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.cyberz.fox.a.a.e.b(android.content.Context):java.lang.Boolean");
    }

    private static a b(String str) {
        a aVar = new a();
        aVar.l(str);
        return aVar;
    }

    public static void b() {
        bl = true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized java.lang.String c(android.content.Context r7) {
        /*
        r2 = jp.co.cyberz.fox.a.a.e.class;
        monitor-enter(r2);
        r1 = "";
        r0 = 0;
        r3 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r3.<init>();	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r4 = "__ADMAGE_RANDOM_DEVICE_ID__";
        r0 = r7.openFileInput(r4);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x003e }
        r3.load(r0);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x0050 }
        r4 = "random_device_id_type";
        r5 = "";
        r1 = r3.getProperty(r4, r5);	 Catch:{ FileNotFoundException -> 0x0028, IOException -> 0x0033, all -> 0x0050 }
        if (r0 == 0) goto L_0x004c;
    L_0x001e:
        r0.close();	 Catch:{ IOException -> 0x004b }
        r0 = r1;
    L_0x0022:
        if (r0 != 0) goto L_0x0026;
    L_0x0024:
        r0 = "";
    L_0x0026:
        monitor-exit(r2);
        return r0;
    L_0x0028:
        r3 = move-exception;
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
        r3 = move-exception;
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
        r6 = r1;
        r1 = r0;
        r0 = r6;
    L_0x0042:
        if (r1 == 0) goto L_0x0047;
    L_0x0044:
        r1.close();	 Catch:{ IOException -> 0x004e }
    L_0x0047:
        throw r0;	 Catch:{ all -> 0x0048 }
    L_0x0048:
        r0 = move-exception;
        monitor-exit(r2);
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
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.cyberz.fox.a.a.e.c(android.content.Context):java.lang.String");
    }

    private static String c(String str) {
        try {
            if (!i.a(str) && a.e.equals(k)) {
                str = h.a(str, a.XUNIQ);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    private static List c(List list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            a aVar = (a) list.get(i);
            aVar.h(UUID.randomUUID().toString());
            if (i != 0) {
                stringBuffer.append("#");
            }
            stringBuffer.append(aVar.b());
        }
        if (a.e.equals(l)) {
            Log.d(f.aV, "QUE is " + stringBuffer.toString());
        }
        String c = c(stringBuffer.toString());
        List arrayList = new ArrayList();
        arrayList.add(new BasicNameValuePair("v", i));
        arrayList.add(new BasicNameValuePair("p", c));
        arrayList.add(new BasicNameValuePair("e", k));
        arrayList.add(new BasicNameValuePair("d", l));
        arrayList.add(new BasicNameValuePair("o", o));
        if (b == null || b.b()) {
            arrayList.add(new BasicNameValuePair("u", i.a));
        } else {
            arrayList.add(new BasicNameValuePair("u", c(b.a())));
        }
        return arrayList;
    }

    public static boolean c() {
        return bl;
    }

    private static String d(List list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            a aVar = (a) list.get(i);
            aVar.h(UUID.randomUUID().toString());
            if (i != 0) {
                stringBuffer.append("#");
            }
            stringBuffer.append(aVar.b());
        }
        if (a.e.equals(l)) {
            Log.d(f.aV, "QUE is " + stringBuffer.toString());
        }
        return stringBuffer.toString();
    }

    private void d() {
        if ((a != null && a.size() > 0) || (b != null && !b.b())) {
            List list = a;
            a = new ArrayList();
            if (VERSION.SDK_INT > 10) {
                AsyncTask bVar = new b(list, c(list));
                d dVar = new d();
                d.a(bVar, j);
            } else if (this.bm != null) {
                this.bm.runOnUiThread(new g(this, list));
            }
        }
    }

    private static String e() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < a.size(); i++) {
            stringBuffer.append(((a) a.get(i)).b()).append("\n");
        }
        return stringBuffer.toString();
    }

    private static void f() {
        Collection arrayList = new ArrayList();
        try {
            File file = new File(bi, bj);
            if (file.isFile()) {
                Reader fileReader = new FileReader(file.getAbsolutePath());
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                while (true) {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    a aVar = new a();
                    aVar.l(readLine);
                    arrayList.add(aVar);
                }
                bufferedReader.close();
                fileReader.close();
                List arrayList2 = new ArrayList();
                a = arrayList2;
                arrayList2.addAll(arrayList);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private static void g() {
        FileOutputStream fileOutputStream;
        Throwable th;
        FileOutputStream fileOutputStream2 = null;
        try {
            if (a.size() > 0) {
                fileOutputStream = new FileOutputStream(new File(bi, bj), false);
                try {
                    fileOutputStream.write(e().getBytes());
                } catch (FileNotFoundException e) {
                    try {
                        Log.e(f.aV, "Queue save failed. file '__e.fox' not found.");
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e2) {
                                e2.printStackTrace();
                                return;
                            }
                        }
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        fileOutputStream2 = fileOutputStream;
                        th = th3;
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (IOException e4) {
                    fileOutputStream2 = fileOutputStream;
                    try {
                        Log.e(f.aV, "Queue save failed. IOException");
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                                return;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        throw th;
                    }
                }
            }
            File file = new File(bi, bj);
            if (file.isFile()) {
                file.delete();
            }
            fileOutputStream = null;
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
            }
        } catch (FileNotFoundException e5) {
            fileOutputStream = null;
            Log.e(f.aV, "Queue save failed. file '__e.fox' not found.");
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (IOException e6) {
            Log.e(f.aV, "Queue save failed. IOException");
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
        }
    }

    private static void h() {
        File file = new File(bi, bj);
        if (file.isFile()) {
            file.delete();
        }
    }

    private static boolean i() {
        return a.e.equals(l);
    }

    private static boolean j() {
        return a.e.equals(k);
    }

    public final void a(Context context) {
        n = System.currentTimeMillis();
        a aVar = new a();
        aVar.a(a.a.AnalyticsEventEndSession);
        aVar.j(this.bg);
        aVar.k(this.bh);
        a(context, aVar);
    }

    public final void a(Context context, String str, int i) {
        a(context, str, i.a, i);
    }

    public final void a(Context context, String str, String str2, int i) {
        a(context, str, str2, i.a, i);
    }

    public final void a(Context context, String str, String str2, String str3, int i) {
        a aVar = new a();
        aVar.a(a.a.AnalyticsEventTrackEvent);
        aVar.a(str);
        aVar.b(str2);
        aVar.c(str3);
        aVar.b(i);
        aVar.j(this.bg);
        aVar.k(this.bh);
        a(context, aVar);
    }

    public final void a(Context context, String str, String str2, String str3, String str4, double d, int i) {
        a(context, str, i.a, i.a, str2, str3, str4, d, i, "JPY");
    }

    public final void a(Context context, String str, String str2, String str3, String str4, double d, int i, String str5) {
        a(context, str, i.a, i.a, str2, str3, str4, d, i, str5);
    }

    public final void a(Context context, String str, String str2, String str3, String str4, String str5, double d, int i) {
        a(context, str, str2, i.a, str3, str4, str5, d, i, "JPY");
    }

    public final void a(Context context, String str, String str2, String str3, String str4, String str5, double d, int i, String str6) {
        a(context, str, str2, i.a, str3, str4, str5, d, i, str6);
    }

    public final void a(Context context, String str, String str2, String str3, String str4, String str5, String str6, double d, int i) {
        a(context, str, str2, str3, str4, str5, str6, d, i, "JPY");
    }

    public final void a(Context context, String str, String str2, String str3, String str4, String str5, String str6, double d, int i, String str7) {
        a aVar = new a();
        aVar.a(a.a.AnalyticsEventTracTransaction);
        aVar.a(str);
        aVar.b(str2);
        aVar.c(str3);
        aVar.d(str4);
        aVar.e(str5);
        aVar.f(str6);
        aVar.a(d);
        aVar.c(i);
        aVar.g(str7);
        aVar.j(this.bg);
        aVar.k(this.bh);
        a(context, aVar);
    }

    public final void a(String str) {
        a(str, null, null, null, null, null);
    }

    public final void a(String str, Context context) {
        if (c == null) {
            c = UUID.randomUUID().toString();
        } else if (System.currentTimeMillis() - n > bk) {
            c = UUID.randomUUID().toString();
        }
        this.bg = UUID.randomUUID().toString();
        this.bh = str;
        a aVar = new a();
        aVar.a(a.a.AnalyticsEventStartSession);
        aVar.j(this.bg);
        aVar.k(str);
        a(context, aVar);
    }

    public final void a(String str, String str2, String str3, String str4, String str5, String str6) {
        c cVar = new c();
        b = cVar;
        cVar.a(str);
        b.b(str2);
        b.c(str3);
        b.d(str4);
        b.e(str5);
        b.f(str6);
        d();
    }
}
