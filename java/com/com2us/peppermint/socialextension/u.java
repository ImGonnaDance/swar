package com.com2us.peppermint.socialextension;

import android.app.Activity;
import android.os.Bundle;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.peppermint.PeppermintCallback;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.RequestsDialogBuilder;

class u implements Runnable {
    private final /* synthetic */ Activity a;
    private final /* synthetic */ Bundle f92a;
    private final /* synthetic */ PeppermintCallback f93a;
    final /* synthetic */ PeppermintSocialPluginFacebook f94a;

    u(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, Activity activity, Bundle bundle, PeppermintCallback peppermintCallback) {
        this.f94a = peppermintSocialPluginFacebook;
        this.a = activity;
        this.f92a = bundle;
        this.f93a = peppermintCallback;
    }

    public void run() {
        WebDialog build = ((RequestsDialogBuilder) new RequestsDialogBuilder(this.a, Session.getActiveSession(), this.f92a).setOnCompleteListener(new v(this, this.f93a))).build();
        build.getWindow().setFlags(ModuleConfig.MERCURY_MODULE, ModuleConfig.MERCURY_MODULE);
        build.show();
    }
}
