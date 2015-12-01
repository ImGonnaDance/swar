package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import org.json.JSONObject;

class k implements PeppermintCallback {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ PeppermintSocialPluginFacebook f80a;

    k(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, PeppermintCallback peppermintCallback) {
        this.f80a = peppermintSocialPluginFacebook;
        this.a = peppermintCallback;
    }

    public void run(JSONObject jSONObject) {
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, this.f80a.getServiceName());
            this.a.run(jSONObject);
        } catch (Exception e) {
        }
    }
}
