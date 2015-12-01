package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

class o implements Runnable {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ PeppermintSocialPluginFacebook f84a;

    o(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, PeppermintCallback peppermintCallback) {
        this.f84a = peppermintSocialPluginFacebook;
        this.a = peppermintCallback;
    }

    public void run() {
        new Request(Session.getActiveSession(), "/me/friends", null, HttpMethod.GET, new p(this, this.a)).executeAsync();
    }
}
