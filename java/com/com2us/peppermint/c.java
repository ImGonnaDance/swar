package com.com2us.peppermint;

import org.json.JSONObject;

class c implements Runnable {
    private final /* synthetic */ int a;
    private final /* synthetic */ JSONObject f8a;
    private final /* synthetic */ int b;

    c(JSONObject jSONObject, int i, int i2) {
        this.f8a = jSONObject;
        this.a = i;
        this.b = i2;
    }

    public void run() {
        String jSONObject = this.f8a != null ? this.f8a.toString() : new JSONObject().toString();
        if (this.a != 0) {
            HubBridge.hubCallbackWithJSON(jSONObject, this.a, this.b);
        }
    }
}
