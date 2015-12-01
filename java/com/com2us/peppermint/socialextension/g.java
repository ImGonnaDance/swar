package com.com2us.peppermint.socialextension;

import android.os.Bundle;
import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

class g implements Runnable {
    private final /* synthetic */ int a;
    private final /* synthetic */ PeppermintCallback f74a;
    final /* synthetic */ PeppermintSocialPluginFacebook f75a;

    g(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, int i, PeppermintCallback peppermintCallback) {
        this.f75a = peppermintSocialPluginFacebook;
        this.a = i;
        this.f74a = peppermintCallback;
    }

    public void run() {
        Bundle bundle = new Bundle();
        bundle.putInt(PeppermintConstant.JSON_KEY_LIMIT, this.a);
        new Request(Session.getActiveSession(), "me/apprequests", bundle, HttpMethod.GET, new h(this, this.f74a)).executeAsync();
    }
}
