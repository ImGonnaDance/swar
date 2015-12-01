package it.partytrack.sdk.compress;

import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;

final class k extends WebChromeClient {
    k(j jVar) {
    }

    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        "Adtruth result is : " + consoleMessage.message();
        d.g = consoleMessage.message();
        return true;
    }
}
