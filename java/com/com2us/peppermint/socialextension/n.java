package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.Request.Callback;
import com.facebook.Response;
import org.json.JSONObject;

class n implements Callback {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ m f83a;

    n(m mVar, PeppermintCallback peppermintCallback) {
        this.f83a = mVar;
        this.a = peppermintCallback;
    }

    public void onCompleted(Response response) {
        if (this.a != null) {
            JSONObject a = this.f83a.f82a.a(response);
            PeppermintLog.i("requestUserMeWithResponseCallback " + a);
            this.a.run(a);
        }
    }
}
