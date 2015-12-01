package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.util.PeppermintLog;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People.LoadPeopleResult;
import org.json.JSONObject;

class z implements ResultCallback<LoadPeopleResult> {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ PeppermintSocialPluginGooglePlus f57a;

    z(PeppermintSocialPluginGooglePlus peppermintSocialPluginGooglePlus, PeppermintCallback peppermintCallback) {
        this.f57a = peppermintSocialPluginGooglePlus;
        this.a = peppermintCallback;
    }

    public void a(LoadPeopleResult loadPeopleResult) {
        if (this.a != null) {
            JSONObject friendsJsonForPlugin = this.f57a.friendsJsonForPlugin(loadPeopleResult.getStatus(), loadPeopleResult.getPersonBuffer());
            PeppermintLog.i("requestFriendsWithResponseCallback json=" + friendsJsonForPlugin);
            this.a.run(friendsJsonForPlugin);
        }
    }

    public /* synthetic */ void onResult(Result result) {
        a((LoadPeopleResult) result);
    }
}
