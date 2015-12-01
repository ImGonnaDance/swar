package com.com2us.peppermint.socialextension;

import com.facebook.RequestAsyncTask;

class b implements Runnable {
    final /* synthetic */ PeppermintSocialPluginFacebook a;
    private final /* synthetic */ RequestAsyncTask f58a;

    b(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, RequestAsyncTask requestAsyncTask) {
        this.a = peppermintSocialPluginFacebook;
        this.f58a = requestAsyncTask;
    }

    public void run() {
        this.f58a.execute(new Void[0]);
    }
}
