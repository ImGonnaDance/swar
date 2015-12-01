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

class f implements OnCompleteListener {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ e f73a;

    f(e eVar, PeppermintCallback peppermintCallback) {
        this.f73a = eVar;
        this.a = peppermintCallback;
    }

    public void onComplete(Bundle bundle, FacebookException facebookException) {
        if (this.a != null) {
            if (bundle != null) {
                PeppermintLog.i("requestInvite Dialog values=" + bundle.toString());
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, this.f73a.f66a.getServiceName());
                jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_INVITE_DIALOG));
                if (facebookException != null) {
                    if (facebookException instanceof FacebookOperationCanceledException) {
                        jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_DIALOG_CLOSE);
                        jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "user cancelled");
                    } else {
                        jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                        jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, facebookException.toString());
                    }
                    PeppermintLog.i("requestInviteDialog json=" + jSONObject);
                    this.a.run(jSONObject);
                }
                if (bundle.getString("request") != null) {
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
                } else {
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_DIALOG_CLOSE);
                    jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "user cancelled");
                }
                PeppermintLog.i("requestInviteDialog json=" + jSONObject);
                this.a.run(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
