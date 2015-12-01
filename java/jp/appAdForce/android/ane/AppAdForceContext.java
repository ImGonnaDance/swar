package jp.appAdForce.android.ane;

import com.adobe.fre.FREContext;
import java.util.HashMap;
import java.util.Map;
import jp.appAdForce.android.ane.a.a;
import jp.appAdForce.android.ane.a.a.b;
import jp.appAdForce.android.ane.a.a.d;
import jp.appAdForce.android.ane.a.a.e;
import jp.appAdForce.android.ane.a.a.f;
import jp.appAdForce.android.ane.a.a.g;
import jp.appAdForce.android.ane.a.a.h;
import jp.appAdForce.android.ane.a.a.i;
import jp.appAdForce.android.ane.a.a.j;
import jp.appAdForce.android.ane.a.a.k;
import jp.appAdForce.android.ane.a.c;

public class AppAdForceContext extends FREContext {
    public void dispose() {
        a.a();
        a.a = null;
        c.a = null;
    }

    public Map getFunctions() {
        Map hashMap = new HashMap();
        a aVar = new a();
        aVar.getClass();
        hashMap.put("sendConversion", new a.a(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("sendConversionWithStartPage", new e(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("sendConversionOnconsent", new e(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("sendConversionForMobage", new b(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("sendUserIdForMobage", new f(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("sendConversionWithCAReward", new d(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("sendConversionForMobageWithCAReward", new a.c(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("setServerUrl", new j(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("setMode", new h(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("setMessage", new g(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("setOptout", new i(aVar));
        aVar = new a();
        aVar.getClass();
        hashMap.put("updateFrom2_10_4g", new k(aVar));
        jp.appAdForce.android.ane.a.b bVar = new jp.appAdForce.android.ane.a.b();
        bVar.getClass();
        hashMap.put("sendStartSession", new jp.appAdForce.android.ane.a.b.d(bVar));
        bVar = new jp.appAdForce.android.ane.a.b();
        bVar.getClass();
        hashMap.put("sendEndSession", new jp.appAdForce.android.ane.a.b.a(bVar));
        bVar = new jp.appAdForce.android.ane.a.b();
        bVar.getClass();
        hashMap.put("sendEvent", new jp.appAdForce.android.ane.a.b.b(bVar));
        bVar = new jp.appAdForce.android.ane.a.b();
        bVar.getClass();
        hashMap.put("sendEventPurchase", new jp.appAdForce.android.ane.a.b.c(bVar));
        bVar = new jp.appAdForce.android.ane.a.b();
        bVar.getClass();
        hashMap.put("sendUserInfo", new jp.appAdForce.android.ane.a.b.e(bVar));
        c cVar = new c();
        cVar.getClass();
        hashMap.put("sendLtv", new c.b(cVar));
        cVar = new c();
        cVar.getClass();
        hashMap.put("sendLtvWithAdid", new c.c(cVar));
        cVar = new c();
        cVar.getClass();
        hashMap.put("addParameter", new c.a(cVar));
        return hashMap;
    }
}
