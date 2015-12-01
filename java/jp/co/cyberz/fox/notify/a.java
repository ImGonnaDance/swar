package jp.co.cyberz.fox.notify;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Settings.System;
import android.util.Log;
import com.com2us.module.manager.ModuleConfig;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.MessageFormat;
import jp.appAdForce.android.AdManager;
import jp.co.cyberz.fox.a.a.e;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.d;
import jp.co.dimage.android.f;
import jp.co.dimage.android.p;

public final class a implements f {
    public static final String a = "message";
    public static final String b = "notify";
    private static String bd = "http://notify.app-adforce.jp";
    private static String be = "/reg/?_appid={0}&_xuniq={1}&_regid={2}";
    private static String bf = "/open/?_appid={0}&_xuniq={1}&_taskid={2}";
    private static String bg = null;
    private static String bj = null;
    private static String bk = null;
    private static boolean bl = false;
    public static final String c = "notifyId";
    public static final String d = "regId";
    public static final String e = "regId_temp";
    public static final String f = "taskId";
    public static final String g = "url";
    public static final String h = "appVersion";
    public static final String i = "appVersionName";
    public static final int j = 9000;
    public static final String k = "DEBUG_NOTIFY_URL";
    public static final boolean l = false;
    private static final int m = 11111;
    private static String n = "F.O.X Notify";
    private static boolean o = true;
    private d bh;
    private Context bi;

    public a(Context context, AdManager adManager) {
        String a;
        this.bh = adManager.a();
        this.bi = context;
        try {
            a = a(context.getPackageManager().getApplicationInfo(context.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE), k);
            if (!i.a.equals(a)) {
                bd = a;
                Log.d(n, "debug url applied : " + bd);
            }
        } catch (NameNotFoundException e) {
        }
        if (e.b(this.bi).booleanValue()) {
            f();
            bk = this.bh.o();
            bj = this.bh.n();
        } else {
            bl = true;
            Log.d(n, "Registration will be suspended until ids are set.");
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(b, 0);
        int i = sharedPreferences.getInt(c, -1);
        if (i != -1) {
            ((NotificationManager) context.getSystemService("notification")).cancel(i);
            bg = bd;
            a = sharedPreferences.getString(f, i.a);
            String string = sharedPreferences.getString(g, i.a);
            String[] strArr = new String[]{bk, bj, a};
            a = bg + bf;
            bg = a;
            bg = p.a(a, strArr);
            new c().execute(new String[]{bg});
            if (!(i.a.equals(string) || string == null)) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(string));
                intent.setFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e2) {
                    Log.d(n, "There is no Activities");
                }
            }
            Editor edit = sharedPreferences.edit();
            edit.remove(f);
            edit.remove(c);
            edit.remove(g);
            edit.commit();
        }
    }

    private static int a(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private static String a(ApplicationInfo applicationInfo, String str) {
        if (applicationInfo == null || applicationInfo.metaData == null) {
            return i.a;
        }
        Object obj = applicationInfo.metaData.get(str);
        return obj == null ? i.a : obj.toString();
    }

    private static void a(String str, String str2) {
        MessageFormat messageFormat = new MessageFormat("appId={0} serverUrl={1} _pushEnable={2}");
        if (aW.booleanValue()) {
            jp.co.dimage.android.a.d(f.aU, messageFormat.format(new String[]{bk, bg}));
            Log.d(f.aU, messageFormat.format(new String[]{bk, bg}));
            return;
        }
        jp.co.dimage.android.a.d(f.aU, messageFormat.format(new String[]{bk, str, str2}));
        Log.d(f.aU, messageFormat.format(new String[]{bk, str, str2}));
    }

    private static String b(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static boolean b() {
        return bl;
    }

    public static boolean b(Context context, String str) {
        String str2 = bd;
        bg = str2;
        bg = new StringBuilder(String.valueOf(str2)).append(be).toString();
        str2 = context.getSharedPreferences(b, 0).getString(d, i.a);
        if (str == null) {
            Log.e("LOG_TAG", "registerId is null");
            return l;
        } else if (str2.equals(str)) {
            Log.d("LOG_TAG", "registerId did not change");
            return l;
        } else if (bj == null) {
            Log.e("LOG_TAG", "xuniq is null");
            return l;
        } else if (bk == null) {
            Log.e("LOG_TAG", "appid is null");
            return l;
        } else {
            bg = p.a(bg, new String[]{bk, bj, str});
            new c(context, str).execute(new String[]{bg});
            return true;
        }
    }

    public static void c() {
        bl = l;
    }

    private static void c(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(b, 0);
        int i = sharedPreferences.getInt(c, -1);
        if (i != -1) {
            ((NotificationManager) context.getSystemService("notification")).cancel(i);
            bg = bd;
            String string = sharedPreferences.getString(f, i.a);
            String string2 = sharedPreferences.getString(g, i.a);
            String[] strArr = new String[]{bk, bj, string};
            string = bg + bf;
            bg = string;
            bg = p.a(string, strArr);
            new c().execute(new String[]{bg});
            if (!(i.a.equals(string2) || string2 == null)) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(string2));
                intent.setFlags(268435456);
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    Log.d(n, "There is no Activities");
                }
            }
            Editor edit = sharedPreferences.edit();
            edit.remove(f);
            edit.remove(c);
            edit.remove(g);
            edit.commit();
        }
    }

    public static void c(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(b, 0);
        int a = a(context);
        String b = b(context);
        Editor edit = sharedPreferences.edit();
        edit.putString(d, str);
        edit.putString(h, String.valueOf(a));
        edit.putString(i, b);
        edit.commit();
    }

    private static void d(Context context) {
        try {
            String a = a(context.getPackageManager().getApplicationInfo(context.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE), k);
            if (!i.a.equals(a)) {
                bd = a;
                Log.d(n, "debug url applied : " + bd);
            }
        } catch (NameNotFoundException e) {
        }
    }

    protected static void d(Context context, String str) {
        if (str != null) {
            CharSequence decode;
            NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
            Notification notification = new Notification();
            String str2 = i.a;
            try {
                context.getPackageManager();
                str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 1).activities[0].name;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName(context.getPackageName(), str2);
            intent.setFlags(268435456);
            PendingIntent activity = PendingIntent.getActivity(context, 0, intent, 0);
            PackageManager packageManager = context.getPackageManager();
            notification.icon = context.getApplicationInfo().icon;
            str2 = (String) context.getApplicationInfo().loadLabel(packageManager);
            notification.tickerText = str;
            notification.sound = System.DEFAULT_NOTIFICATION_URI;
            notification.flags = 8;
            try {
                decode = URLDecoder.decode(str, "UTF-8");
            } catch (UnsupportedEncodingException e2) {
                Log.d(n, "using unsupported character for notification message");
            } catch (IllegalArgumentException e3) {
                Log.d(n, "using percent");
            } catch (Exception e4) {
                Log.e(n, e4.getMessage());
            }
            notification.setLatestEventInfo(context, str2, decode, activity);
            Editor edit = context.getSharedPreferences(b, 0).edit();
            edit.putInt(c, m);
            edit.commit();
            notificationManager.notify(m, notification);
        }
    }

    private static boolean d() {
        return true;
    }

    private void e() {
        if (e.b(this.bi).booleanValue()) {
            f();
            bk = this.bh.o();
            bj = this.bh.n();
            return;
        }
        bl = true;
        Log.d(n, "Registration will be suspended until ids are set.");
    }

    private static void e(Context context, String str) {
        try {
            try {
                Method declaredMethod = Class.forName("com.google.android.gcm.GCMRegistrar").getDeclaredMethod("register", new Class[]{Context.class, String[].class});
                declaredMethod.setAccessible(true);
                Object[] objArr = new Object[2];
                objArr[0] = context;
                objArr[1] = new String[]{str};
                declaredMethod.invoke(null, objArr);
            } catch (NoSuchMethodException e) {
                Log.e(n, "please import proper gcm.jar");
            }
        } catch (ClassNotFoundException e2) {
            Log.e(n, "please import gcm.jar");
        } catch (Exception e3) {
            e3.printStackTrace();
        }
    }

    private synchronized void f() {
        PackageManager packageManager = this.bi.getPackageManager();
        if (packageManager == null) {
            jp.co.dimage.android.a.b(f.aU, "PackageManager is null.");
            Log.e(f.aU, "PackageManager is null.");
        } else {
            try {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(this.bi.getPackageName(), 0);
                if (applicationInfo == null) {
                    jp.co.dimage.android.a.b(f.aU, "ApplicationInfo is null.");
                    Log.e(f.aU, "ApplicationInfo is null.");
                } else {
                    if ((applicationInfo.flags & 2) == 2) {
                        jp.co.dimage.android.a.a();
                        jp.co.dimage.android.a.b();
                    }
                    try {
                        applicationInfo = this.bi.getPackageManager().getApplicationInfo(this.bi.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE);
                        if (applicationInfo == null) {
                            jp.co.dimage.android.a.b(f.aU, "ApplicationInfo is null.");
                            Log.e(f.aU, "ApplicationInfo is null.");
                        } else {
                            bk = a(applicationInfo, f.t);
                        }
                    } catch (NameNotFoundException e) {
                        jp.co.dimage.android.a.b(f.aU, "loadApplicationInfo faild. " + e.getMessage());
                        Log.e(f.aU, "loadApplicationInfo faild. " + e.getMessage());
                    }
                }
            } catch (NameNotFoundException e2) {
                jp.co.dimage.android.a.b(f.aU, "loadApplicationInfo faild. " + e2.getMessage());
                Log.e(f.aU, "loadApplicationInfo faild. " + e2.getMessage());
            }
        }
    }

    private static void g() {
        bl = true;
    }

    public final String a() {
        int i = 0;
        SharedPreferences sharedPreferences = this.bi.getSharedPreferences(b, 0);
        String string = sharedPreferences.getString(d, i.a);
        if (string == null) {
            Log.i(n, "Registration not found.");
            return i.a;
        }
        String string2 = sharedPreferences.getString(h, i.a);
        if (!i.a.equals(string2)) {
            i = Integer.parseInt(string2);
        }
        String string3 = sharedPreferences.getString(i, i.a);
        int a = a(this.bi);
        String b = b(this.bi);
        if (i != a) {
            Log.i(n, "App version changed." + String.valueOf(a));
            return i.a;
        } else if (string3.equals(b)) {
            Log.i(n, "App version stayed:" + a + "  App version name:" + b);
            return string;
        } else {
            Log.i(n, "App version name changed.");
            return i.a;
        }
    }

    public final void a(Context context, String str) {
        String a = a();
        if (p.a(a)) {
            Log.d(n, "No regId");
            try {
                try {
                    Method declaredMethod = Class.forName("com.google.android.gcm.GCMRegistrar").getDeclaredMethod("register", new Class[]{Context.class, String[].class});
                    declaredMethod.setAccessible(true);
                    Object[] objArr = new Object[2];
                    objArr[0] = context;
                    objArr[1] = new String[]{str};
                    declaredMethod.invoke(null, objArr);
                    return;
                } catch (NoSuchMethodException e) {
                    Log.e(n, "please import proper gcm.jar");
                    return;
                }
            } catch (ClassNotFoundException e2) {
                Log.e(n, "please import gcm.jar");
                return;
            } catch (Exception e3) {
                e3.printStackTrace();
                return;
            }
        }
        Log.d(n, "already hold regId:" + a);
    }
}
