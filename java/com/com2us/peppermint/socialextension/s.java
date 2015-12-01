package com.com2us.peppermint.socialextension;

import android.app.Activity;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.util.PeppermintUtil;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.FeedDialogBuilder;
import org.json.JSONObject;

class s implements Runnable {
    private final /* synthetic */ Activity a;
    private final /* synthetic */ PeppermintCallback f87a;
    final /* synthetic */ PeppermintSocialPluginFacebook f88a;
    private final /* synthetic */ JSONObject f89a;

    s(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook, JSONObject jSONObject, Activity activity, PeppermintCallback peppermintCallback) {
        this.f88a = peppermintSocialPluginFacebook;
        this.f89a = jSONObject;
        this.a = activity;
        this.f87a = peppermintCallback;
    }

    public void run() {
        WebDialog build = new FeedDialogBuilder(this.a, Session.getActiveSession(), PeppermintUtil.getBundleFrojObj(this.f89a)).build();
        build.setOnCompleteListener(new t(this, this.f87a));
        build.getWindow().setFlags(ModuleConfig.MERCURY_MODULE, ModuleConfig.MERCURY_MODULE);
        build.show();
    }
}
