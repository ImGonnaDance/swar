package com.com2us.peppermint;

import org.json.JSONObject;

class i implements PeppermintCallback {
    final /* synthetic */ PeppermintDialog a;

    i(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public void run(JSONObject jSONObject) {
        this.a.receiveSocialFriends(jSONObject);
    }
}
