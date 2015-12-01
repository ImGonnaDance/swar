package com.com2us.peppermint;

import org.json.JSONObject;

class j implements PeppermintCallback {
    final /* synthetic */ PeppermintDialog a;

    j(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run(JSONObject jSONObject) {
        this.a.receiveSocialLogout(jSONObject);
    }
}
