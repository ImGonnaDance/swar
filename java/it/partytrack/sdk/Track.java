package it.partytrack.sdk;

import android.content.Context;
import android.content.Intent;
import it.partytrack.sdk.compress.a;
import it.partytrack.sdk.compress.d;
import it.partytrack.sdk.compress.h;
import it.partytrack.sdk.compress.i;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Track {
    public static final String CLIENT_ID = "client_id";
    public static final String UUID = "uuid";

    public static void disableAdvertisementOptimize() {
        d.f119c.put("application_tracking_enabled", a.d);
    }

    public static void event(int i) {
        if (d.f114a.isEmpty()) {
            new i(i).start();
            return;
        }
        Map hashMap = new HashMap();
        for (Entry entry : d.f114a.entrySet()) {
            hashMap.put((String) entry.getKey(), (String) entry.getValue());
        }
        d.f114a.clear();
        new i(i, hashMap).start();
    }

    public static void event(String str) {
        if (d.f114a.isEmpty()) {
            new i(str).start();
            return;
        }
        Map hashMap = new HashMap();
        for (Entry entry : d.f114a.entrySet()) {
            hashMap.put((String) entry.getKey(), (String) entry.getValue());
        }
        d.f114a.clear();
        new i(str, hashMap).start();
    }

    public static void payment(String str, float f, String str2, int i) {
        new i(new h(str, f, str2, i)).start();
    }

    public static void setCustomEventParameter(String str, String str2) {
        d.f114a.put(str, str2);
    }

    public static void setDebugMode(boolean z) {
        d.f118b = z;
    }

    public static void setGooglePlayAdvertisingId(String str, boolean z) {
        d.f117b.put("advertiser_id", str);
        d.f119c.put("advertiser_tracking_enabled", z ? a.d : a.e);
    }

    public static void setOptionalParam(String str, String str2) {
        setOptionalparam(str, str2);
    }

    public static void setOptionalparam(String str, String str2) {
        d.f119c.put(str, str2);
    }

    public static void start(Context context, int i, String str) {
        a.a(context, i, str);
        new i().start();
    }

    public static void start(Context context, int i, String str, Intent intent) {
        a.a(context, i, str);
        new i(intent).start();
    }
}
