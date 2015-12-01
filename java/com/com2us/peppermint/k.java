package com.com2us.peppermint;

import org.json.JSONObject;

class k implements PeppermintCallback {
    final /* synthetic */ PeppermintDialog a;

    k(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run(JSONObject jSONObject) {
        this.a.receiveSocialLogout(jSONObject);
    }
}
