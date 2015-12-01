package com.com2us.peppermint;

import com.com2us.peppermint.util.PeppermintLog;

class v implements Runnable {
    final /* synthetic */ a a;
    private final /* synthetic */ String f101a;

    v(a aVar, String str) {
        this.a = aVar;
        this.f101a = str;
    }

    public void run() {
        PeppermintLog.i("PeppermintWebView loadUrl url=" + this.f101a);
        super.loadUrl(this.f101a);
    }
}
