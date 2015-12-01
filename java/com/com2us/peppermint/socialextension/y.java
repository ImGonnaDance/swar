package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.PeppermintType;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.Request.Callback;
import com.facebook.Response;
import com.facebook.internal.NativeProtocol;
import org.json.JSONException;
import org.json.JSONObject;

class y implements Callback {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ PeppermintSocialPluginFacebook f95a;
    private final /* synthetic */ JSONObject f96a;

    y(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        this.f95a = peppermintSocialPluginFacebook;
        this.f96a = jSONObject;
        this.a = peppermintCallback;
    }

    public void onCompleted(Response response) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, this.f95a.getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_QUERY));
            jSONObject.put(PeppermintConstant.JSON_KEY_PARAMS, this.f96a);
            if (response.getError() != null) {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, response.getError().toString());
            } else {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, response.getGraphObject().getInnerJSONObject());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("runQueryWithParams json=" + jSONObject);
        this.a.run(jSONObject);
    }
}
