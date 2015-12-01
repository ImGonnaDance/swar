package com.com2us.peppermint;

import com.com2us.peppermint.util.PeppermintLog;
import org.json.JSONObject;

class b implements PeppermintCallback {
    private final /* synthetic */ int a;
    private final /* synthetic */ int b;

    b(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public void run(JSONObject jSONObject) {
        PeppermintLog.i("getCallbackNative callback=" + jSONObject);
        HubBridge.HubCallbackWithJSON(jSONObject, this.a, this.b);
    }
}
