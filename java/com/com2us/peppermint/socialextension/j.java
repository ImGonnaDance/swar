package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.Request.Callback;
import com.facebook.Response;
import org.json.JSONObject;

class j implements Callback {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ i f79a;

    j(i iVar, PeppermintCallback peppermintCallback) {
        this.f79a = iVar;
        this.a = peppermintCallback;
    }

    public void onCompleted(Response response) {
        if (this.a != null) {
            if (response != null) {
                PeppermintLog.i("requestUserProfileImage = " + response.toString());
            }
            JSONObject e = this.f79a.f77a.c(response);
            PeppermintLog.i("requestUserProfileImage " + e);
            this.a.run(e);
        }
    }
}
