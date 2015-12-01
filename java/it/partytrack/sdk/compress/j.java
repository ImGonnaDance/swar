package it.partytrack.sdk.compress;

import android.webkit.WebView;

final class j implements Runnable {
    private final /* synthetic */ String a;

    j(String str) {
        this.a = str;
    }

    public final void run() {
        WebView webView = new WebView(d.f110a);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new k(this));
        webView.loadUrl("javascript:" + this.a);
    }
}
