package com.chartboost.sdk.impl;

import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.g;
import com.chartboost.sdk.Libraries.g.k;
import com.chartboost.sdk.Libraries.h;
import com.chartboost.sdk.Model.CBError;
import com.com2us.peppermint.PeppermintConstant;
import java.io.File;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;

public class be {
    private static final String a = be.class.getSimpleName();
    private static h b;
    private static m c;
    private static ConcurrentHashMap<Integer, b> d;
    private static a e;
    private static a f;
    private static AtomicInteger g = new AtomicInteger();
    private static AtomicInteger h = new AtomicInteger();
    private static boolean i = true;
    private static com.chartboost.sdk.Model.a j;
    private static boolean k = false;
    private static Observer l = new Observer() {
        public void update(Observable observable, Object data) {
            be.m();
        }
    };
    private static com.chartboost.sdk.impl.ba.c m = new com.chartboost.sdk.impl.ba.c() {
        public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar) {
            synchronized (be.class) {
                be.e = a.kCBIntial;
                if (aVar != null) {
                    CBLogging.a(be.a, "Got Video list from server :)" + aVar);
                    be.a(aVar.a("videos"));
                }
            }
        }

        public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar, CBError cBError) {
        }
    };

    public enum a {
        kCBIntial,
        kCBInProgress
    }

    private static class b extends l<Object> {
        private String a;
        private long b = System.currentTimeMillis();
        private String c;

        public b(int i, String str, c cVar, String str2) {
            super(i, str, cVar);
            this.a = str2;
            this.c = str;
        }

        protected void b(Object obj) {
            CBLogging.a(be.a, "####### deliverResponse for Video Dwonload");
        }

        protected n<Object> a(i iVar) {
            if (iVar != null) {
                com.chartboost.sdk.Tracking.a.d(this.a, Long.valueOf((System.currentTimeMillis() - this.b) / 1000).toString(), this.c);
                CBLogging.a(be.a, "Video download Success. Storing video in cache" + this.a);
                be.b.a(this.a, iVar.b);
                com.chartboost.sdk.Tracking.a.e("cache", "hit", this.a);
            }
            if (be.h.get() > 0 && be.g.incrementAndGet() >= 1 && be.j != null) {
                final com.chartboost.sdk.Model.a j = be.j;
                be.j = null;
                CBUtility.e().post(new Runnable(this) {
                    final /* synthetic */ b b;

                    public void run() {
                        j.q().r(j);
                    }
                });
            }
            if (be.g.get() == be.h.get()) {
                be.f = a.kCBIntial;
                be.d.clear();
            }
            return n.a(null, null);
        }

        public com.chartboost.sdk.impl.l.a s() {
            return com.chartboost.sdk.impl.l.a.LOW;
        }
    }

    private static class c implements com.chartboost.sdk.impl.n.a {
        private b a;

        private c() {
        }

        public void a(s sVar) {
            if ((sVar instanceof r) || (sVar instanceof q) || (sVar instanceof h)) {
                if (this.a != null) {
                    com.chartboost.sdk.Tracking.a.a(this.a.a, Long.valueOf((System.currentTimeMillis() - this.a.b) / 1000).toString(), this.a.c, sVar.getMessage());
                    com.chartboost.sdk.Tracking.a.e("cache", "miss", this.a.a);
                }
                be.d.put(Integer.valueOf(this.a.hashCode()), this.a);
                CBLogging.a(be.a, "####### onErrorResponse Video Download" + sVar.getMessage() + this.a.a);
            }
        }
    }

    private static synchronized void l() {
        synchronized (be.class) {
            if (!k) {
                k = true;
                b = new h("CBVideoDirectory", true);
                d = new ConcurrentHashMap();
                e = a.kCBIntial;
                f = a.kCBIntial;
                c = bb.a(com.chartboost.sdk.b.k()).a();
                az.a().addObserver(l);
            }
        }
    }

    public static synchronized void a() {
        synchronized (be.class) {
            if (!k) {
                l();
            }
            CBLogging.a(a, "Calling Prfetch Video");
            if (!(a.kCBInProgress == e || a.kCBInProgress == f)) {
                if (!(d == null || d.isEmpty())) {
                    d.clear();
                    c.a(Integer.valueOf(l.hashCode()));
                    f = a.kCBIntial;
                    CBLogging.a(a, "prefetchVideo: Clearing all volley request for new start");
                }
                e = a.kCBInProgress;
                Object jSONArray = new JSONArray();
                if (b() != null) {
                    for (Object put : b()) {
                        jSONArray.put(put);
                    }
                }
                com.chartboost.sdk.Tracking.a.d();
                h.set(0);
                g.set(0);
                ba baVar = new ba("/api/video-prefetch");
                baVar.a("local-videos", jSONArray);
                k[] kVarArr = new k[2];
                kVarArr[0] = g.a("status", com.chartboost.sdk.Libraries.a.a);
                kVarArr[1] = g.a("videos", g.b(g.a(g.a("video", g.a(g.a())), g.a(PeppermintConstant.JSON_KEY_ID, g.a()))));
                baVar.a(g.a(kVarArr));
                baVar.b(true);
                baVar.a(m);
            }
        }
    }

    public static synchronized void a(com.chartboost.sdk.Libraries.e.a aVar) {
        synchronized (be.class) {
            com.chartboost.sdk.Tracking.a.e();
            if (aVar != null) {
                HashMap hashMap = new HashMap();
                HashMap hashMap2 = new HashMap();
                String[] b = b();
                for (int i = 0; i < aVar.o(); i++) {
                    com.chartboost.sdk.Libraries.e.a c = aVar.c(i);
                    if (!(c.b(PeppermintConstant.JSON_KEY_ID) || c.b("video"))) {
                        String e = c.e(PeppermintConstant.JSON_KEY_ID);
                        String e2 = c.e("video");
                        if (!b.c(e)) {
                            hashMap2.put(e, e2);
                            h.incrementAndGet();
                        }
                        hashMap.put(e, e2);
                    }
                }
                if (!hashMap2.isEmpty()) {
                    f = a.kCBInProgress;
                }
                if (i) {
                    i = false;
                }
                CBLogging.a(a, "synchronizeVideos: Delete and Download new videos");
                a(hashMap, b);
                if (com.chartboost.sdk.b.s()) {
                    a(hashMap2);
                }
            }
        }
    }

    private static synchronized void a(HashMap<String, String> hashMap, String[] strArr) {
        synchronized (be.class) {
            CBLogging.a(a, "deleteVideos: Deleteing videos in cache");
            if (!(hashMap == null || strArr == null)) {
                for (String str : strArr) {
                    if (!hashMap.containsKey(str)) {
                        File d = b.d(str);
                        if (d != null) {
                            b.c(d);
                        }
                    }
                }
            }
        }
    }

    private static synchronized void a(HashMap<String, String> hashMap) {
        synchronized (be.class) {
            CBLogging.a(a, "downloadVideos: Downloading videos from server");
            for (String str : hashMap.keySet()) {
                c cVar = new c();
                l bVar = new b(0, (String) hashMap.get(str), cVar, str);
                bVar.a(new d(30000, 0, 0.0f));
                cVar.a = bVar;
                bVar.a((Object) Integer.valueOf(l.hashCode()));
                com.chartboost.sdk.Tracking.a.a((String) hashMap.get(str), str);
                c.a(bVar);
            }
        }
    }

    public static String[] b() {
        return b.a();
    }

    public static synchronized void c() {
        synchronized (be.class) {
            c.a(Integer.valueOf(l.hashCode()));
        }
    }

    public static String a(String str) {
        if (b.c(str)) {
            return b.d(str).getPath();
        }
        return null;
    }

    public static void b(String str) {
        if (b.c(str)) {
            b.b(str);
        }
    }

    public static boolean b(com.chartboost.sdk.Libraries.e.a aVar) {
        if (aVar == null) {
            return false;
        }
        return a(aVar.a("assets").a(CBUtility.c().b() ? "video-portrait" : "video-landscape").e(PeppermintConstant.JSON_KEY_ID)) != null;
    }

    public static synchronized boolean d() {
        boolean z;
        synchronized (be.class) {
            if (i || e == a.kCBInProgress || (f == a.kCBInProgress && g.get() == 0)) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    private static synchronized void m() {
        synchronized (be.class) {
            CBLogging.a(a, "Process Request called");
            if (!(e == a.kCBInProgress || f == a.kCBInProgress)) {
                if ((f == a.kCBIntial && d != null) || d.size() > 0) {
                    for (Integer num : d.keySet()) {
                        f = a.kCBInProgress;
                        c.a((l) d.get(num));
                        d.remove(num);
                    }
                }
            }
        }
    }

    public static synchronized void a(com.chartboost.sdk.Model.a aVar) {
        synchronized (be.class) {
            j = aVar;
        }
    }

    public static synchronized boolean b(com.chartboost.sdk.Model.a aVar) {
        boolean z;
        synchronized (be.class) {
            if (d()) {
                CBLogging.e(a, "saving video impression request for video prefetch completion");
                a(aVar);
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }
}
