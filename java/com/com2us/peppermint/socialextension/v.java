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
import org.json.JSONException;
import org.json.JSONObject;

class v implements OnCompleteListener {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ u f97a;

    v(u uVar, PeppermintCallback peppermintCallback) {
        this.f97a = uVar;
        this.a = peppermintCallback;
    }

    public void onComplete(Bundle bundle, FacebookException facebookException) {
        boolean z = false;
        if (this.a != null) {
            if (!(bundle == null || bundle.get("request") == null)) {
                z = true;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, this.f97a.f94a.getServiceName());
                jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SEND_APP_MESSAGE));
                if (facebookException != null) {
                    if (facebookException instanceof FacebookOperationCanceledException) {
                        jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_DIALOG_CLOSE);
                        jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "user cancelled");
                        jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, z);
                    } else {
                        jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                        jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, facebookException.toString());
                    }
                    PeppermintLog.i("sendAppMessageWithParams json=" + jSONObject);
                    this.a.run(jSONObject);
                }
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, z);
                PeppermintLog.i("sendAppMessageWithParams json=" + jSONObject);
                this.a.run(jSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
