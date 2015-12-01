package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

class q implements Runnable {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ PeppermintSocialPluginFacebook f86a;

    q(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, PeppermintCallback peppermintCallback) {
        this.f86a = peppermintSocialPluginFacebook;
        this.a = peppermintCallback;
    }

    public void run() {
        new Request(Session.getActiveSession(), "/me/invitable_friends", null, HttpMethod.GET, new r(this, this.a)).executeAsync();
    }
}
