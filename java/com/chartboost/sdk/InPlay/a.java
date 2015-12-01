package com.chartboost.sdk.InPlay;

import android.graphics.Bitmap;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.impl.ab;
import com.chartboost.sdk.impl.ba;
import com.chartboost.sdk.impl.ba.c;
import com.chartboost.sdk.impl.bb;
import com.chartboost.sdk.impl.bc;
import com.chartboost.sdk.impl.s;
import com.com2us.peppermint.PeppermintConstant;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public final class a {
    private static final String a = a.class.getSimpleName();
    private static ArrayList<CBInPlay> b;
    private static int c = 4;
    private static a d;
    private static LinkedHashMap<String, Bitmap> e;

    private class a implements com.chartboost.sdk.impl.n.a {
        final /* synthetic */ a a;

        private a(a aVar) {
            this.a = aVar;
        }

        public void a(s sVar) {
            CBLogging.b(a.a, "Bitmap download failed " + sVar.getMessage());
        }
    }

    private class b implements com.chartboost.sdk.impl.n.b<Bitmap> {
        protected boolean a;
        protected String b;
        protected CBInPlay c;
        final /* synthetic */ a d;

        private b(a aVar) {
            this.d = aVar;
        }

        public void a(Bitmap bitmap) {
            a.e.put(this.b, bitmap);
            this.d.a(this.c, this.b, this.a);
        }
    }

    private a() {
        b = new ArrayList();
        e = new LinkedHashMap(c);
    }

    public static a a() {
        if (d == null) {
            synchronized (a.class) {
                if (d == null) {
                    d = new a();
                }
            }
        }
        return d;
    }

    public void a(String str) {
        if (!e()) {
            a(str, true);
        }
    }

    private static boolean e() {
        return b.size() == c;
    }

    public boolean b(String str) {
        if (b.size() > 0) {
            com.chartboost.sdk.Tracking.a.b("in-play", str, true);
            return true;
        }
        CBLogging.a(a, "InPlay is not available :(");
        return false;
    }

    public CBInPlay c(String str) {
        CBInPlay cBInPlay = null;
        if (b.size() > 0) {
            cBInPlay = (CBInPlay) b.get(0);
        }
        if (!e()) {
            a(str, true);
        }
        if (cBInPlay == null) {
            CBLogging.a(a, "InPlay Object not available returning null :(");
        }
        return cBInPlay;
    }

    private void a(final String str, final boolean z) {
        ba baVar = new ba("/inplay/get");
        baVar.a("raw", Boolean.valueOf(true));
        baVar.a("cache", Boolean.valueOf(true));
        baVar.a(com.chartboost.sdk.impl.l.a.HIGH);
        baVar.b(true);
        baVar.a("location", (Object) str);
        baVar.a(com.chartboost.sdk.Model.b.d);
        baVar.a(new c(this) {
            final /* synthetic */ a c;

            public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar) {
                if (aVar.c()) {
                    CBInPlay cBInPlay = new CBInPlay();
                    cBInPlay.a(aVar);
                    cBInPlay.b(aVar.e(PeppermintConstant.JSON_KEY_NAME));
                    if (!TextUtils.isEmpty(str)) {
                        cBInPlay.a(str);
                    }
                    com.chartboost.sdk.Libraries.e.a a = aVar.a("icons");
                    if (a.c() && !TextUtils.isEmpty(a.e("lg"))) {
                        String e = a.e("lg");
                        if (a.e.get(e) == null) {
                            com.chartboost.sdk.impl.n.b bVar = new b();
                            com.chartboost.sdk.impl.n.a aVar2 = new a();
                            bVar.c = cBInPlay;
                            bVar.b = e;
                            bVar.a = z;
                            bb.a(com.chartboost.sdk.b.k()).a().a(new ab(e, bVar, 0, 0, null, aVar2));
                            return;
                        }
                        this.c.a(cBInPlay, e, true);
                    }
                }
            }

            public void a(com.chartboost.sdk.Libraries.e.a aVar, ba baVar, CBError cBError) {
                CBLogging.b(a.a, "InPlay cache call failed" + cBError);
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didFailToLoadInPlay(str, cBError != null ? cBError.c() : null);
                }
            }
        });
    }

    private void a(CBInPlay cBInPlay, String str, boolean z) {
        cBInPlay.a((Bitmap) e.get(str));
        b.add(cBInPlay);
        com.chartboost.sdk.a d = com.chartboost.sdk.b.d();
        if (d != null && z) {
            d.didCacheInPlay(cBInPlay.getLocation());
        }
        if (!e()) {
            a(cBInPlay.getLocation(), false);
        }
    }

    protected void a(CBInPlay cBInPlay) {
        Object a = cBInPlay.a();
        ba baVar = new ba("/inplay/show");
        baVar.a("inplay-dictionary", a);
        baVar.a(true);
        baVar.s();
        String str = null;
        if (cBInPlay.a().c()) {
            str = a.e("ad_id");
        }
        com.chartboost.sdk.Tracking.a.a("in-play", cBInPlay.getLocation(), str);
    }

    protected void b(CBInPlay cBInPlay) {
        String str;
        final com.chartboost.sdk.Libraries.e.a a = cBInPlay.a();
        if (a != null) {
            String e = a.e("link");
            String e2 = a.e("deep-link");
            if (!TextUtils.isEmpty(e2)) {
                try {
                    if (!bc.a(e2)) {
                        e2 = e;
                    }
                    str = e2;
                } catch (Exception e3) {
                    CBLogging.b(a, "Cannot open a url");
                }
            }
            str = e;
        } else {
            str = null;
        }
        com.chartboost.sdk.c.b anonymousClass2 = new com.chartboost.sdk.c.b(this) {
            final /* synthetic */ a b;

            public void a() {
                ba d = com.chartboost.sdk.c.a().d();
                d.a("to", a);
                d.a("cgn", a);
                d.a("creative", a);
                d.a("ad_id", a);
                d.a(PeppermintConstant.JSON_KEY_TYPE, a);
                d.a("more_type", a);
                d.a(true);
                d.s();
            }
        };
        com.chartboost.sdk.c a2 = com.chartboost.sdk.c.a();
        if (TextUtils.isEmpty(str)) {
            a2.b.a(null, false, str, CBClickError.URI_INVALID, anonymousClass2);
        } else {
            a2.b(null, str, anonymousClass2);
        }
    }

    public static void b() {
        if (e != null) {
            e.clear();
        }
        if (b != null) {
            b.clear();
        }
    }
}
