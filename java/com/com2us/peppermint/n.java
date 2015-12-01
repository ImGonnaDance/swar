package com.com2us.peppermint;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import com.com2us.peppermint.util.PeppermintLog;

class n extends WebChromeClient {
    final /* synthetic */ PeppermintDialog a;

    n(PeppermintDialog peppermintDialog) {
        this.a = peppermintDialog;
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        super.onConsoleMessage(consoleMessage);
        PeppermintLog.i("initSubViews concoleMessage message=" + consoleMessage.message() + "line=" + consoleMessage.lineNumber());
        return true;
    }
}
