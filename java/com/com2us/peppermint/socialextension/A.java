package com.com2us.peppermint.socialextension;

import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;

class a implements StatusCallback {
    final /* synthetic */ PeppermintSocialPluginFacebook a;

    a(PeppermintSocialPluginFacebook peppermintSocialPluginFacebook) {
        this.a = peppermintSocialPluginFacebook;
    }

    public void call(Session session, SessionState sessionState, Exception exception) {
        this.a.handleFBSessionStateChangeWithSession(session, sessionState, exception);
    }
}
