package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.PeppermintType;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.internal.NativeProtocol;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class c implements Callback {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ PeppermintSocialPluginFacebook f62a;

    c(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, PeppermintCallback peppermintCallback) {
        this.f62a = peppermintSocialPluginFacebook;
        this.a = peppermintCallback;
    }

    public void onCompleted(Response response) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, this.f62a.getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_BUSINESS_MODEL));
            if (response.getError() != null) {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, response.getError().toString());
            } else {
                JSONArray jSONArray = response.getGraphObject().getInnerJSONObject().getJSONArray(PeppermintConstant.JSON_KEY_DATA);
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                jSONObject.put(PeppermintConstant.JSON_KEY_DATA, jSONArray);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("runSendBusinessModel json= " + jSONObject);
        this.a.run(jSONObject);
    }
}
