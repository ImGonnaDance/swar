package com.chartboost.sdk.impl;

import android.content.Context;
import android.os.CountDownTimer;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.e;
import com.chartboost.sdk.Libraries.h;
import com.chartboost.sdk.Model.CBError;
import com.facebook.Response;
import com.google.android.gcm.GCMConstants;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONObject;
import org.json.JSONTokener;

public class bb implements Observer {
    private static bb b;
    private static ConcurrentHashMap<ba, File> e;
    private static ConcurrentHashMap<ba, File> f;
    private static List<Runnable> g = new ArrayList();
    private az a = null;
    private m c;
    private h d;
    private CountDownTimer h;

    public static class a extends s {
        private CBError b;

        public a(CBError cBError) {
            this.b = cBError;
        }
    }

    public static class b {
        private com.chartboost.sdk.Libraries.e.a a;
        private i b;

        public b(com.chartboost.sdk.Libraries.e.a aVar, i iVar) {
            this.a = aVar;
            this.b = iVar;
        }
    }

    private class c implements Runnable {
        final /* synthetic */ bb a;
        private ba b;

        private class a extends l<b> {
            final /* synthetic */ c a;
            private ba b;

            protected /* synthetic */ void b(Object obj) {
                a((b) obj);
            }

            public a(c cVar, int i, String str, ba baVar) {
                this.a = cVar;
                super(i, str, null);
                this.b = baVar;
            }

            public String p() {
                String b = this.b.b();
                if (b == null) {
                    return "application/json; charset=utf-8";
                }
                return b;
            }

            public byte[] q() {
                return (this.b.i() == null ? i.a : this.b.i().toString()).getBytes();
            }

            public com.chartboost.sdk.impl.l.a s() {
                return this.b.n();
            }

            public Map<String, String> i() throws a {
                Map<String, String> hashMap = new HashMap();
                for (Entry entry : this.b.j().entrySet()) {
                    hashMap.put(entry.getKey(), entry.getValue() != null ? entry.getValue().toString() : null);
                }
                return hashMap;
            }

            protected n<b> a(i iVar) {
                com.chartboost.sdk.Libraries.e.a a;
                CBError cBError;
                com.chartboost.sdk.Libraries.e.a aVar;
                Exception e;
                CBError cBError2;
                Object obj;
                int i = iVar.a;
                if (i <= 300 || i >= SelectTarget.TARGETING_SUCCESS) {
                    try {
                        String str;
                        byte[] bArr = iVar.b;
                        if (bArr != null) {
                            str = new String(bArr);
                        } else {
                            str = null;
                        }
                        if (str != null) {
                            a = com.chartboost.sdk.Libraries.e.a.a(new JSONObject(new JSONTokener(str)));
                            try {
                                CBError cBError3;
                                com.chartboost.sdk.Libraries.g.a l = this.b.l();
                                CBLogging.c("CBRequestManager", "Request " + this.b.g() + " succeeded. Response code: " + i + ", body: " + str);
                                if (a.f("status") == 404) {
                                    cBError3 = new CBError(com.chartboost.sdk.Model.CBError.a.HTTP_NOT_FOUND, "404 error from server");
                                } else {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    if (l == null || l.a(a, stringBuilder)) {
                                        cBError3 = null;
                                    } else {
                                        cBError3 = new CBError(com.chartboost.sdk.Model.CBError.a.UNEXPECTED_RESPONSE, "Json response failed validation");
                                        CBLogging.b("CBRequestManager", "Json response failed validation: " + stringBuilder.toString());
                                    }
                                }
                                com.chartboost.sdk.Libraries.e.a aVar2 = a;
                                cBError = cBError3;
                                aVar = aVar2;
                            } catch (Exception e2) {
                                e = e2;
                                cBError2 = new CBError(com.chartboost.sdk.Model.CBError.a.MISCELLANEOUS, e.getLocalizedMessage());
                                aVar = a;
                                cBError = cBError2;
                                if (obj == null) {
                                }
                                return n.a(new a(cBError));
                            }
                        }
                        cBError = new CBError(com.chartboost.sdk.Model.CBError.a.INVALID_RESPONSE, "Response is not a valid json object");
                        obj = null;
                    } catch (Exception e3) {
                        e = e3;
                        a = null;
                        cBError2 = new CBError(com.chartboost.sdk.Model.CBError.a.MISCELLANEOUS, e.getLocalizedMessage());
                        aVar = a;
                        cBError = cBError2;
                        if (obj == null) {
                        }
                        return n.a(new a(cBError));
                    }
                }
                CBLogging.d("CBRequestManager", "Request " + this.b.g() + " failed. Response code: " + i);
                cBError = new CBError(com.chartboost.sdk.Model.CBError.a.NETWORK_FAILURE, "Request failed. Response code: " + i + " is not valid ");
                obj = null;
                if (obj == null && cBError == null) {
                    return n.a(new b(com.chartboost.sdk.Libraries.e.a.a(obj), iVar), null);
                }
                return n.a(new a(cBError));
            }

            protected void a(b bVar) {
                if (!(this.a.b.r() == null || bVar == null)) {
                    this.a.b.r().a(bVar.a, this.a.b);
                }
                if (this.a.b.h()) {
                    com.chartboost.sdk.Tracking.a.a().m().c((File) bb.f.get(this.a.b));
                    com.chartboost.sdk.Tracking.a.a().q();
                    CBLogging.a("CBRequestManager", "### Removing track events sent to server...");
                    bb.f.remove(this.a.b);
                    return;
                }
                this.a.a.d.c((File) bb.e.get(this.a.b));
                bb.e.remove(this.a.b);
                this.a.a.a(this.a.b, bVar.b, null, true);
            }

            public void b(s sVar) {
                if (sVar != null) {
                    CBError a;
                    if (sVar instanceof a) {
                        a = ((a) sVar).b;
                    } else {
                        a = new CBError(com.chartboost.sdk.Model.CBError.a.NETWORK_FAILURE, sVar.getMessage());
                    }
                    com.chartboost.sdk.Libraries.e.a aVar = com.chartboost.sdk.Libraries.e.a.a;
                    if (sVar != null) {
                        try {
                            if (!(sVar.a == null || sVar.a.b == null || sVar.a.b.length <= 0)) {
                                aVar = com.chartboost.sdk.Libraries.e.a.j(new String(sVar.a.b));
                            }
                        } catch (Throwable e) {
                            CBLogging.d("CBRequestManager", "unable to read error json", e);
                        }
                    }
                    if (sVar.a == null || sVar.a.a != SelectTarget.TARGETING_SUCCESS) {
                        if (this.a.b.r() != null) {
                            this.a.b.r().a(aVar, this.a.b, a);
                        }
                        if (this.a.b.h()) {
                            bb.f.remove(this.a.b);
                            return;
                        }
                        this.a.b.d(false);
                        this.a.a.a(this.a.b, sVar.a, a, false);
                        return;
                    }
                    a(new b(aVar, sVar.a));
                }
            }
        }

        public c(bb bbVar, ba baVar) {
            this.a = bbVar;
            this.b = baVar;
        }

        public void run() {
            this.b.a(com.chartboost.sdk.b.k());
            this.b.c();
            String format = String.format("%s%s", new Object[]{"https://live.chartboost.com", this.b.e()});
            this.b.d();
            this.b.a();
            l aVar = new a(this, 1, format, this.b);
            aVar.a(new d(30000, 0, 0.0f));
            this.a.c.a(aVar);
        }
    }

    public static bb a(Context context) {
        if (b == null) {
            synchronized (bb.class) {
                if (b == null) {
                    b = new bb(context);
                }
            }
        }
        return b;
    }

    private bb(Context context) {
        this.c = ad.a(context.getApplicationContext());
        this.a = az.a();
        this.d = new h("CBRequestManager", false);
        e = new ConcurrentHashMap();
        f = new ConcurrentHashMap();
        this.a.addObserver(this);
    }

    public m a() {
        return this.c;
    }

    private void a(ba baVar, i iVar, CBError cBError, boolean z) {
        if (baVar != null) {
            String str;
            com.chartboost.sdk.Libraries.e.b[] bVarArr = new com.chartboost.sdk.Libraries.e.b[5];
            bVarArr[0] = e.a("endpoint", baVar.g());
            bVarArr[1] = e.a("statuscode", iVar == null ? "None" : Integer.valueOf(iVar.a));
            bVarArr[2] = e.a(GCMConstants.EXTRA_ERROR, cBError == null ? "None" : cBError.a());
            bVarArr[3] = e.a("errorDescription", cBError == null ? "None" : cBError.b());
            bVarArr[4] = e.a("retryCount", Integer.valueOf(baVar.o()));
            com.chartboost.sdk.Libraries.e.a a = e.a(bVarArr);
            String str2 = "request_manager";
            String str3 = "request";
            if (z) {
                str = Response.SUCCESS_KEY;
            } else {
                str = "failure";
            }
            com.chartboost.sdk.Tracking.a.a(str2, str3, str, null, null, null, a.e());
        }
    }

    protected void a(ba baVar, com.chartboost.sdk.impl.ba.c cVar) {
        if (baVar != null) {
            if (this.a.c()) {
                if (!baVar.h() && baVar.p()) {
                    baVar.c(false);
                    a(baVar);
                }
                a(new c(this, baVar));
                return;
            }
            CBError cBError = new CBError(com.chartboost.sdk.Model.CBError.a.INTERNET_UNAVAILABLE, null);
            baVar.d(false);
            if (!baVar.h()) {
                if (baVar.p()) {
                    baVar.c(false);
                    a(baVar);
                }
                a(baVar, null, cBError, false);
                if (cVar != null) {
                    cVar.a(null, baVar, cBError);
                }
            }
        }
    }

    public void a(Runnable runnable) {
        Object obj = null;
        synchronized (com.chartboost.sdk.Libraries.c.class) {
            com.chartboost.sdk.Libraries.c.a c = com.chartboost.sdk.Libraries.c.c();
            if (c == com.chartboost.sdk.Libraries.c.a.PRELOAD || c == com.chartboost.sdk.Libraries.c.a.LOADING) {
                g.add(runnable);
            } else {
                obj = 1;
            }
        }
        if (obj != null) {
            ay.a().execute(runnable);
        }
    }

    public static void b() {
        List<Runnable> arrayList = new ArrayList();
        synchronized (com.chartboost.sdk.Libraries.c.class) {
            arrayList.addAll(g);
            g.clear();
        }
        for (Runnable execute : arrayList) {
            ay.a().execute(execute);
        }
    }

    public synchronized void c() {
        synchronized (this) {
            if (e == null || e.isEmpty()) {
                String[] a = this.d.a();
                if (a != null) {
                    for (String str : a) {
                        ba a2 = a(str);
                        if (a2 != null) {
                            e.put(a2, this.d.d(str));
                            a2.c(false);
                            a2.a(a2.o() + 1);
                            a2.a(a2.r());
                        }
                    }
                }
            } else {
                for (ba baVar : e.keySet()) {
                    if (!(baVar == null || baVar.q())) {
                        baVar.a(baVar.o() + 1);
                        baVar.a(baVar.r());
                    }
                }
            }
            e();
            f();
        }
    }

    public static synchronized void d() {
        synchronized (bb.class) {
            try {
                String[] a;
                h c = h.c();
                if (c != null) {
                    a = c.a();
                } else {
                    a = null;
                }
                if (a != null && a.length > 0) {
                    for (String str : a) {
                        com.chartboost.sdk.Libraries.e.a a2 = c.a(str);
                        if (a2.c()) {
                            c.b(str);
                            ba.a(a2).s();
                        }
                    }
                }
            } catch (Throwable e) {
                CBLogging.b("CBRequestManager", "Error executing saved requests", e);
            }
        }
    }

    public void e() {
        com.chartboost.sdk.Tracking.a a = com.chartboost.sdk.Tracking.a.a();
        synchronized (this) {
            if (f.isEmpty()) {
                try {
                    String[] a2;
                    h m = a.m();
                    if (m != null) {
                        a2 = m.a();
                    } else {
                        a2 = null;
                    }
                    if (a2 != null) {
                        for (String str : a2) {
                            if (!a.b(str)) {
                                com.chartboost.sdk.Libraries.e.a a3 = m.a(str);
                                if (a3.c()) {
                                    CBLogging.a("CBRequestManager", "### Flushing out " + str + "track events from cache to server...");
                                    ba a4 = a.a(a3);
                                    f.put(a4, m.d(str));
                                    a4.s();
                                }
                            }
                        }
                    }
                } catch (Throwable e) {
                    CBLogging.b("CBRequestManager", "Error executing saved requests", e);
                }
            } else {
                for (ba baVar : f.keySet()) {
                    if (!(baVar == null || baVar.q())) {
                        baVar.a(baVar.o() + 1);
                        baVar.a(baVar.r());
                    }
                }
            }
        }
    }

    private void a(ba baVar) {
        if (baVar != null) {
            Object obj = null;
            if (baVar.k() && (baVar instanceof ba)) {
                obj = this.d.a(null, baVar.t());
            }
            if ((baVar.k() || baVar.m()) && obj != null) {
                e.put(baVar, obj);
            }
        }
    }

    private ba a(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        com.chartboost.sdk.Libraries.e.a a = this.d.a(str);
        if (a != null) {
            return ba.a(a);
        }
        return null;
    }

    public void f() {
        if (this.h == null) {
            this.h = new CountDownTimer(this, 240000, 1000) {
                final /* synthetic */ bb a;

                public void onTick(long millisUntilFinished) {
                }

                public void onFinish() {
                    this.a.c();
                }
            }.start();
        }
    }

    public void g() {
        CBLogging.a("CBRequestManager", "Timer stopped:");
        if (this.h != null) {
            this.h.cancel();
            this.h = null;
        }
    }

    public void update(Observable observable, Object data) {
        if (this.h != null) {
            g();
        }
        c();
    }

    public static synchronized boolean h() {
        boolean z;
        synchronized (bb.class) {
            if (f == null || f.isEmpty()) {
                z = false;
            } else {
                z = true;
            }
        }
        return z;
    }

    public ConcurrentHashMap<ba, File> i() {
        return e;
    }

    public h j() {
        return this.d;
    }
}
