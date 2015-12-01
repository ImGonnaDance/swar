package com.com2us.peppermint.socialextension;

import android.os.Bundle;
import com.com2us.peppermint.PeppermintCallback;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Session;

class i implements Runnable {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ PeppermintSocialPluginFacebook f77a;
    private final /* synthetic */ String f78a;

    i(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, String str, PeppermintCallback peppermintCallback) {
        this.f77a = peppermintSocialPluginFacebook;
        this.f78a = str;
        this.a = peppermintCallback;
    }

    public void run() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("redirect", false);
        new Request(Session.getActiveSession(), "/" + this.f78a + "/picture", bundle, HttpMethod.GET, new j(this, this.a)).executeAsync();
    }
}
