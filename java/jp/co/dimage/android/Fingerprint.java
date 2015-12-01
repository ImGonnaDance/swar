package jp.co.dimage.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import jp.co.cyberz.fox.a.a.i;

public class Fingerprint {
    static final String a = "/view/collect.html";
    private static final String h = "FINGER_PRINT";
    private String b = i.a;
    private String c = i.a;
    private String d = i.a;
    private g e;
    private a f;
    private Context g;

    private String a() {
        return this.b;
    }

    private String b() {
        return this.c;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    public final void a(Context context) {
        this.g = context;
        WebView webView = new WebView(context);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(this, "droid");
        if (!p.a(this.d)) {
            webView.loadUrl(this.d + a);
        }
    }

    public final void a(String str) {
        this.d = str;
    }

    public final void a(a aVar) {
        this.f = aVar;
    }

    public final void a(g gVar) {
        this.e = gVar;
    }

    @JavascriptInterface
    public void setFingerprintId(String str, long j) {
        this.b = str;
        if (j > 0) {
            this.c = Long.toString(j);
        }
        this.e.d(this.b, this.c);
    }
}
