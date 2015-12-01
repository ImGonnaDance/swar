package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

class m implements Runnable {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ PeppermintSocialPluginFacebook f82a;

    m(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, PeppermintCallback peppermintCallback) {
        this.f82a = peppermintSocialPluginFacebook;
        this.a = peppermintCallback;
    }

    public void run() {
        new Request(Session.getActiveSession(), "/me", null, HttpMethod.GET, new n(this, this.a)).executeAsync();
    }
}
