package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.Request.Callback;
import com.facebook.Response;
import org.json.JSONObject;

class p implements Callback {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ o f85a;

    p(o oVar, PeppermintCallback peppermintCallback) {
        this.f85a = oVar;
        this.a = peppermintCallback;
    }

    public void onCompleted(Response response) {
        if (this.a != null) {
            JSONObject b = this.f85a.f84a.d(response);
            PeppermintLog.i("requestFriendsWithResponseCallback json=" + b);
            this.a.run(b);
        }
    }
}
