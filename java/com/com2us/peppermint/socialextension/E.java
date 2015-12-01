package com.com2us.peppermint.socialextension;

import android.app.Activity;
import android.os.Bundle;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.peppermint.PeppermintCallback;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.RequestsDialogBuilder;

class e implements Runnable {
    private final /* synthetic */ Activity a;
    private final /* synthetic */ Bundle f64a;
    private final /* synthetic */ PeppermintCallback f65a;
    final /* synthetic */ PeppermintSocialPluginFacebook f66a;

    e(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, Activity activity, Bundle bundle, PeppermintCallback peppermintCallback) {
        this.f66a = peppermintSocialPluginFacebook;
        this.a = activity;
        this.f64a = bundle;
        this.f65a = peppermintCallback;
    }

    public void run() {
        WebDialog build = ((RequestsDialogBuilder) new RequestsDialogBuilder(this.a, Session.getActiveSession(), this.f64a).setOnCompleteListener(new f(this, this.f65a))).build();
        build.getWindow().setFlags(ModuleConfig.MERCURY_MODULE, ModuleConfig.MERCURY_MODULE);
        build.show();
    }
}
