package com.chartboost.sdk.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout.LayoutParams;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.f;
import java.net.URI;
import java.net.URLDecoder;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONObject;
import org.json.JSONTokener;

public final class bs extends f {
    private String j = null;

    public class a extends com.chartboost.sdk.f.a {
        public WebView b;
        final /* synthetic */ bs c;

        public a(bs bsVar, Context context, String str) {
            this.c = bsVar;
            super(bsVar, context);
            setFocusable(false);
            this.b = new b(bsVar, context);
            this.b.setWebViewClient(new c());
            addView(this.b);
            this.b.loadDataWithBaseURL("file:///android_asset/", str, "text/html", "utf-8", null);
        }

        protected void a(int i, int i2) {
        }
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private class b extends WebView {
        final /* synthetic */ bs a;

        public b(bs bsVar, Context context) {
            this.a = bsVar;
            super(context);
            setLayoutParams(new LayoutParams(-1, -1));
            setBackgroundColor(0);
            getSettings().setJavaScriptEnabled(true);
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == 4 || keyCode == 3) {
                this.a.h();
            }
            return super.onKeyDown(keyCode, event);
        }
    }

    private class c extends WebViewClient {
        final /* synthetic */ bs a;

        private c(bs bsVar) {
            this.a = bsVar;
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            this.a.i();
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            this.a.a(CBImpressionError.INTERNAL);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String url) {
            String str;
            Throwable th;
            Object obj = null;
            CBLogging.e("CBWebViewProtocol", "loading url: " + url);
            String str2 = i.a;
            try {
                if (new URI(url).getScheme().equals("chartboost")) {
                    String[] split = url.split("/");
                    Integer valueOf = Integer.valueOf(split.length);
                    if (valueOf.intValue() < 3) {
                        this.a.h();
                        return false;
                    }
                    str2 = split[2];
                    if (str2.equals("close")) {
                        this.a.h();
                    } else if (str2.equals("link")) {
                        if (valueOf.intValue() < 4) {
                            this.a.h();
                            return false;
                        }
                        try {
                            str2 = URLDecoder.decode(split[3], "UTF-8");
                            try {
                                JSONObject jSONObject;
                                if (valueOf.intValue() > 4) {
                                    jSONObject = new JSONObject(new JSONTokener(URLDecoder.decode(split[4], "UTF-8")));
                                } else {
                                    jSONObject = null;
                                }
                                obj = jSONObject;
                                str = str2;
                            } catch (Throwable e) {
                                Throwable th2 = e;
                                str = str2;
                                th = th2;
                                CBLogging.b("CBWebViewProtocol", "Error decoding URL or JSON", th);
                                this.a.a(str, com.chartboost.sdk.Libraries.e.a.a(obj));
                                return true;
                            }
                        } catch (Throwable e2) {
                            th = e2;
                            str = null;
                            CBLogging.b("CBWebViewProtocol", "Error decoding URL or JSON", th);
                            this.a.a(str, com.chartboost.sdk.Libraries.e.a.a(obj));
                            return true;
                        }
                        this.a.a(str, com.chartboost.sdk.Libraries.e.a.a(obj));
                    }
                }
                return true;
            } catch (Exception e3) {
                this.a.h();
                return false;
            }
        }
    }

    public bs(com.chartboost.sdk.Model.a aVar) {
        super(aVar);
    }

    protected com.chartboost.sdk.f.a b(Context context) {
        return new a(this, context, this.j);
    }

    public boolean a(com.chartboost.sdk.Libraries.e.a aVar) {
        String e = aVar.e("html");
        if (e == null) {
            a(CBImpressionError.INTERNAL);
            return false;
        }
        this.j = e;
        b();
        return true;
    }
}
