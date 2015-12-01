package com.chartboost.sdk.Libraries;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import com.chartboost.sdk.b;
import com.chartboost.sdk.impl.bb;
import com.chartboost.sdk.impl.ck;
import com.chartboost.sdk.impl.cm;
import com.chartboost.sdk.impl.cn;
import it.partytrack.sdk.Track;
import java.util.UUID;
import jp.co.cyberz.fox.a.a.i;

public final class c {
    private static String a = null;
    private static String b = null;
    private static a c = a.PRELOAD;
    private static String d = null;

    public enum a {
        PRELOAD(-1),
        LOADING(-1),
        UNKNOWN(-1),
        TRACKING_ENABLED(0),
        TRACKING_DISABLED(1);
        
        private int f;

        private a(int i) {
            this.f = i;
        }

        public int a() {
            return this.f;
        }

        public boolean b() {
            return this.f != -1;
        }
    }

    private c() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a() {
        /*
        r1 = com.chartboost.sdk.Libraries.d.class;
        monitor-enter(r1);
        r0 = c();	 Catch:{ all -> 0x0020 }
        r2 = com.chartboost.sdk.Libraries.c.a.PRELOAD;	 Catch:{ all -> 0x0020 }
        if (r0 == r2) goto L_0x000d;
    L_0x000b:
        monitor-exit(r1);	 Catch:{ all -> 0x0020 }
    L_0x000c:
        return;
    L_0x000d:
        r0 = com.chartboost.sdk.Libraries.c.a.LOADING;	 Catch:{ all -> 0x0020 }
        a(r0);	 Catch:{ all -> 0x0020 }
        monitor-exit(r1);	 Catch:{ all -> 0x0020 }
        r0 = 0;
        r1 = "com.google.android.gms.ads.identifier.AdvertisingIdClient";
        r0 = java.lang.Class.forName(r1);	 Catch:{ ClassNotFoundException -> 0x0030 }
    L_0x001a:
        if (r0 != 0) goto L_0x0023;
    L_0x001c:
        g();
        goto L_0x000c;
    L_0x0020:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0020 }
        throw r0;
    L_0x0023:
        r0 = com.chartboost.sdk.impl.ay.a();
        r1 = new com.chartboost.sdk.Libraries.c$1;
        r1.<init>();
        r0.execute(r1);
        goto L_0x000c;
    L_0x0030:
        r1 = move-exception;
        goto L_0x001a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chartboost.sdk.Libraries.c.a():void");
    }

    private static void g() {
        CBLogging.b("CBIdentity", "WARNING: It looks like you've forgotten to include the Google Play Services library in your project. Please review the SDK documentation for more details.");
        a(a.UNKNOWN);
        bb.b();
    }

    public static String b() {
        if (a == null) {
            a = b.b(h());
        }
        return a;
    }

    public static synchronized a c() {
        a aVar;
        synchronized (c.class) {
            aVar = c;
        }
        return aVar;
    }

    protected static synchronized void a(a aVar) {
        synchronized (c.class) {
            c = aVar;
        }
    }

    public static synchronized String d() {
        String str;
        synchronized (c.class) {
            str = b;
        }
        return str;
    }

    private static synchronized void b(String str) {
        synchronized (c.class) {
            b = str;
        }
    }

    private static byte[] h() {
        Object e = e();
        if (e == null || "9774d56d682e549c".equals(e)) {
            e = i();
        }
        String j = j();
        String d = d();
        ck cnVar = new cn();
        cnVar.a(Track.UUID, e);
        cnVar.a("macid", j);
        cnVar.a("gaid", d);
        return new cm().a(cnVar);
    }

    public static String e() {
        return Secure.getString(b.k().getContentResolver(), "android_id");
    }

    private static String i() {
        if (d == null) {
            SharedPreferences a = CBUtility.a();
            d = a.getString("cbUUID", null);
            if (d == null) {
                d = UUID.randomUUID().toString();
                Editor edit = a.edit();
                edit.putString("cbUUID", d);
                edit.commit();
            }
        }
        return d;
    }

    private static String j() {
        return b.b(b.a(k()));
    }

    private static byte[] k() {
        try {
            String macAddress = ((WifiManager) b.k().getSystemService("wifi")).getConnectionInfo().getMacAddress();
            if (macAddress == null || macAddress.equals(i.a)) {
                return null;
            }
            String[] split = macAddress.split(":");
            byte[] bArr = new byte[6];
            for (int i = 0; i < split.length; i++) {
                bArr[i] = Integer.valueOf(Integer.parseInt(split[i], 16)).byteValue();
            }
            return bArr;
        } catch (Exception e) {
            return null;
        }
    }
}
