package com.com2us.peppermint.socialextension;

import android.app.Activity;
import android.os.Bundle;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.peppermint.PeppermintCallback;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.RequestsDialogBuilder;

class w implements Runnable {
    private final /* synthetic */ Activity a;
    private final /* synthetic */ Bundle f98a;
    private final /* synthetic */ PeppermintCallback f99a;
    final /* synthetic */ PeppermintSocialPluginFacebook f100a;

    w(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, Activity activity, Bundle bundle, PeppermintCallback peppermintCallback) {
        this.f100a = peppermintSocialPluginFacebook;
        this.a = activity;
        this.f98a = bundle;
        this.f99a = peppermintCallback;
    }

    public void run() {
        WebDialog build = ((RequestsDialogBuilder) new RequestsDialogBuilder(this.a, Session.getActiveSession(), this.f98a).setOnCompleteListener(new x(this, this.f99a))).build();
        build.getWindow().setFlags(ModuleConfig.MERCURY_MODULE, ModuleConfig.MERCURY_MODULE);
        build.show();
    }
}
