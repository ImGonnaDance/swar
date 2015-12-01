package it.partytrack.sdk.compress;

import android.content.Intent;
import java.util.HashMap;
import java.util.Map;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;
import org.json.JSONException;
import org.json.JSONObject;

public final class i extends Thread {
    private int a;
    private Intent f105a;
    private h f106a;
    private String f107a;
    private Map f108a;
    private int b;

    public i() {
        this.a = 0;
        this.b = 0;
        this.f107a = null;
        this.f105a = null;
        this.f108a = null;
        this.f106a = null;
        this.a = 1;
    }

    public i(int i) {
        this.a = 0;
        this.b = 0;
        this.f107a = null;
        this.f105a = null;
        this.f108a = null;
        this.f106a = null;
        this.a = 2;
        this.b = i;
    }

    public i(int i, Map map) {
        this.a = 0;
        this.b = 0;
        this.f107a = null;
        this.f105a = null;
        this.f108a = null;
        this.f106a = null;
        this.a = 5;
        this.b = i;
        this.f108a = map;
    }

    public i(Intent intent) {
        this.a = 0;
        this.b = 0;
        this.f107a = null;
        this.f105a = null;
        this.f108a = null;
        this.f106a = null;
        if (intent == null || intent.getScheme() == null || intent.getData() == null) {
            this.a = 1;
            return;
        }
        this.a = 4;
        this.f105a = intent;
    }

    public i(h hVar) {
        this.a = 0;
        this.b = 0;
        this.f107a = null;
        this.f105a = null;
        this.f108a = null;
        this.f106a = null;
        this.a = 3;
        this.f106a = hVar;
    }

    public i(String str) {
        this.a = 0;
        this.b = 0;
        this.f107a = null;
        this.f105a = null;
        this.f108a = null;
        this.f106a = null;
        this.a = 2;
        this.f107a = str;
    }

    public i(String str, Map map) {
        this.a = 0;
        this.b = 0;
        this.f107a = null;
        this.f105a = null;
        this.f108a = null;
        this.f106a = null;
        this.a = 5;
        this.f107a = str;
        this.f108a = map;
    }

    public final void run() {
        switch (this.a) {
            case o.a /*1*/:
                b.a("start_event");
                a.a();
                b.a(true);
                return;
            case o.b /*2*/:
                if (!a.b()) {
                    return;
                }
                if (this.b != 0) {
                    b.a(this.b);
                    b.a(false);
                    return;
                } else if (this.f107a != null) {
                    b.a(this.f107a);
                    b.a(false);
                    return;
                } else {
                    return;
                }
            case o.c /*3*/:
                if (a.b() && this.f106a != null) {
                    b.a(this.f106a);
                    b.a(false);
                    return;
                }
                return;
            case o.d /*4*/:
                String uri = this.f105a.getData().toString();
                Map hashMap = new HashMap();
                if (uri != null && uri.length() > 0) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("url_scheme", uri);
                        hashMap.put("parameters", jSONObject.toString());
                    } catch (JSONException e) {
                    }
                }
                b.a("start_event", hashMap);
                a.a();
                b.a(true);
                return;
            case f.bc /*5*/:
                if (a.b() && this.f108a != null && !this.f108a.isEmpty()) {
                    if (this.b != 0) {
                        b.a(this.b, this.f108a);
                        b.a(false);
                        return;
                    } else if (this.f107a != null) {
                        b.a(this.f107a, this.f108a);
                        b.a(false);
                        return;
                    } else {
                        return;
                    }
                }
                return;
            default:
                return;
        }
    }
}
