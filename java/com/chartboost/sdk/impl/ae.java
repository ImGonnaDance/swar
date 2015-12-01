package com.chartboost.sdk.impl;

import com.chartboost.sdk.Libraries.e;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.a;
import com.chartboost.sdk.Model.a.c;
import com.chartboost.sdk.Model.b;
import com.chartboost.sdk.d;
import org.json.JSONArray;

public class ae extends d {
    private static ae b;

    protected ae() {
    }

    public static ae f() {
        if (b == null) {
            b = new ae();
        }
        return b;
    }

    protected boolean b(a aVar, e.a aVar2) {
        return aVar2.a("media-type").equals("video");
    }

    protected void a(a aVar, e.a aVar2) {
        if (!b(aVar, aVar2) || be.b(aVar2)) {
            if (aVar2.c() && aVar2.a("videos").c()) {
                be.a(aVar2.a("videos"));
            }
            super.a(aVar, aVar2);
            return;
        }
        a(aVar, CBImpressionError.INTERNAL);
        if (aVar.f) {
            aVar.a(aVar2);
            be.a(aVar);
        }
        be.a();
    }

    public void r(a aVar) {
        if (!aVar.w().b()) {
            super.a(aVar, aVar.w());
        } else if (!c(aVar.d) && !aVar.q) {
            a(e(aVar), aVar);
        }
    }

    protected a a(String str, boolean z) {
        return new a(c.INTERSTITIAL, z, str, false);
    }

    protected ba e(a aVar) {
        ba baVar = new ba("/interstitial/get");
        baVar.a(l.a.HIGH);
        baVar.a(b.b);
        baVar.a("local-videos", g());
        return baVar;
    }

    protected a c() {
        return new a(this) {
            final /* synthetic */ ae a;

            {
                this.a = r1;
            }

            public void a(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didClickInterstitial(aVar.d);
                }
            }

            public void b(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didCloseInterstitial(aVar.d);
                }
            }

            public void c(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didDismissInterstitial(aVar.d);
                }
            }

            public void d(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didCacheInterstitial(aVar.d);
                }
            }

            public void a(a aVar, CBImpressionError cBImpressionError) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didFailToLoadInterstitial(aVar.d, cBImpressionError);
                }
            }

            public void e(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didDisplayInterstitial(aVar.d);
                }
            }

            public boolean f(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    return com.chartboost.sdk.b.d().shouldDisplayInterstitial(aVar.d);
                }
                return true;
            }

            public boolean g(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    return com.chartboost.sdk.b.d().shouldRequestInterstitial(aVar.d);
                }
                return true;
            }

            public boolean h(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    return com.chartboost.sdk.b.q();
                }
                return true;
            }
        };
    }

    protected ba l(a aVar) {
        return new ba("/interstitial/show");
    }

    public JSONArray g() {
        JSONArray jSONArray = new JSONArray();
        String[] b = be.b();
        if (b != null) {
            for (Object put : b) {
                jSONArray.put(put);
            }
        }
        return jSONArray;
    }

    public String e() {
        return "interstitial";
    }
}
