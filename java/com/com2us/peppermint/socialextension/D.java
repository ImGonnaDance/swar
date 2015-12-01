package com.com2us.peppermint.socialextension;

import com.facebook.RequestAsyncTask;

class d implements Runnable {
    final /* synthetic */ PeppermintSocialPluginFacebook a;
    private final /* synthetic */ RequestAsyncTask f63a;

    d(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, RequestAsyncTask requestAsyncTask) {
        this.a = peppermintSocialPluginFacebook;
        this.f63a = requestAsyncTask;
    }

    public void run() {
        this.f63a.execute(new Void[0]);
    }
}
