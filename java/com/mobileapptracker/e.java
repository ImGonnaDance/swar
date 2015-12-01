package com.mobileapptracker;

import android.content.Context;
import android.webkit.WebView;
import java.lang.ref.WeakReference;

final class e implements Runnable {
    final /* synthetic */ MATParameters a;
    private final WeakReference<Context> b;

    public e(MATParameters mATParameters, Context context) {
        this.a = mATParameters;
        this.b = new WeakReference(context);
    }

    public final void run() {
        try {
            WebView webView = new WebView((Context) this.b.get());
            String userAgentString = webView.getSettings().getUserAgentString();
            webView.destroy();
            this.a.ai = userAgentString;
        } catch (Exception e) {
        } catch (VerifyError e2) {
        }
    }
}
