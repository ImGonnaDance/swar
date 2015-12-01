package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.Request.Callback;
import com.facebook.Response;
import org.json.JSONObject;

class h implements Callback {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ g f76a;

    h(g gVar, PeppermintCallback peppermintCallback) {
        this.f76a = gVar;
        this.a = peppermintCallback;
    }

    public void onCompleted(Response response) {
        if (this.a != null) {
            if (response != null) {
                PeppermintLog.i("requestReceivedInvite = " + response.toString());
            }
            JSONObject d = this.f76a.f75a.b(response);
            PeppermintLog.i("requestReceivedInvite " + d);
            this.a.run(d);
        }
    }
}
