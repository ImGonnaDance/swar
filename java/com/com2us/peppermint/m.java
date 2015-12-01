package com.com2us.peppermint;

import org.json.JSONObject;

class m implements PeppermintCallback {
    final /* synthetic */ PeppermintDialog a;

    m(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run(JSONObject jSONObject) {
        this.a.receiveSocialUserToken(jSONObject);
    }
}
