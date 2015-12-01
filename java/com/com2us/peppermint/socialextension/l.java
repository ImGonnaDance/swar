package com.com2us.peppermint.socialextension;

import android.app.Activity;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import java.util.Arrays;

class l implements Runnable {
    private final /* synthetic */ Activity a;
    final /* synthetic */ PeppermintSocialPluginFacebook f81a;

    l(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, Activity activity) {
        this.f81a = peppermintSocialPluginFacebook;
        this.a = activity;
    }

    public void run() {
        Session build = new Builder(this.a.getApplicationContext()).build();
        Session.setActiveSession(build);
        OpenRequest callback = new OpenRequest(this.a).setPermissions(Arrays.asList(PeppermintSocialPluginFacebook.a())).setLoginBehavior(SessionLoginBehavior.SSO_WITH_FALLBACK).setCallback(this.f81a.f70a);
        Session.setActiveSession(build);
        build.openForRead(callback);
    }
}
