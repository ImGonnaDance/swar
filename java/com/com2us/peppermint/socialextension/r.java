package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.Request.Callback;
import com.facebook.Response;
import org.json.JSONObject;

class r implements Callback {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ q f90a;

    r(q qVar, PeppermintCallback peppermintCallback) {
        this.f90a = qVar;
        this.a = peppermintCallback;
    }

    public void onCompleted(Response response) {
        JSONObject c = this.f90a.f86a.e(response);
        PeppermintLog.i("requestInvitationListWithResponseCallback json=" + c);
        this.a.run(c);
    }
}
