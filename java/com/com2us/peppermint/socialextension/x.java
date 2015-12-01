package com.com2us.peppermint.socialextension;

import android.os.Bundle;
import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.PeppermintType;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.internal.NativeProtocol;
import com.facebook.widget.WebDialog.OnCompleteListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class x implements OnCompleteListener {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ w f104a;

    x(w wVar, PeppermintCallback peppermintCallback) {
        this.f104a = wVar;
        this.a = peppermintCallback;
    }

    public void onComplete(Bundle bundle, FacebookException facebookException) {
        if (this.a != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, this.f104a.f100a.getServiceName());
                jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SEND_APP_INVITATION));
                if (facebookException != null) {
                    if (facebookException instanceof FacebookOperationCanceledException) {
                        jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_DIALOG_CLOSE);
                        jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "user cancelled");
                    } else {
                        jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                        jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, facebookException.toString());
                    }
                    PeppermintLog.i("sendAppInvitationWithParams json=" + jSONObject);
                    this.a.run(jSONObject);
                }
                JSONArray jSONArray = new JSONArray();
                if (bundle != null) {
                    for (String str : bundle.keySet()) {
                        if (str.startsWith("to")) {
                            jSONArray.put((String) bundle.get(str));
                        }
                    }
                }
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                jSONObject.put(PeppermintConstant.JSON_KEY_RECEIVERS, jSONArray);
                PeppermintLog.i("sendAppInvitationWithParams json=" + jSONObject);
                this.a.run(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
