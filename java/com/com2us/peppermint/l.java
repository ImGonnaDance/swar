package com.com2us.peppermint;

import org.json.JSONObject;

class l implements PeppermintCallback {
    final /* synthetic */ PeppermintDialog a;

    l(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run(JSONObject jSONObject) {
        this.a.receiveSocialUserProfile(jSONObject);
    }
}
