package com.com2us.peppermint.socialextension;

import android.os.Bundle;
import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.FacebookException;
import com.facebook.widget.WebDialog.OnCompleteListener;

class t implements OnCompleteListener {
    private final /* synthetic */ PeppermintCallback a;
    final /* synthetic */ s f91a;

    t(s sVar, PeppermintCallback peppermintCallback) {
        this.f91a = sVar;
        this.a = peppermintCallback;
    }

    public void onComplete(Bundle bundle, FacebookException facebookException) {
        PeppermintLog.i("shareAppActivityWithParams values=" + bundle + " error=" + facebookException);
        if (this.a != null) {
            boolean z = (bundle == null || bundle.get("post_id") == null) ? false : true;
            this.a.run(this.f91a.f88a.a(z, bundle, facebookException));
        }
    }
}
