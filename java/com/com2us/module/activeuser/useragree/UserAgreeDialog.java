package com.com2us.module.activeuser.useragree;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.com2us.module.activeuser.downloadcheck.InstallService;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;

@SuppressLint({"SetJavaScriptEnabled"})
public class UserAgreeDialog extends Dialog {
    static final float[] DIMENSIONS_DIFF_LANDSCAPE = new float[]{25.0f, 25.0f};
    static final float[] DIMENSIONS_DIFF_PORTRAIT = new float[]{25.0f, 25.0f};
    private Bitmap closeBitmap;
    private volatile boolean destroyed = false;
    private Logger logger = LoggerGroup.createLogger(InstallService.TAG);
    private Bitmap logoBitmap;
    private ProgressDialog mSpinner;
    private LinearLayout mainLayout;
    private float scale;
    private String url;
    private WebView webView;

    private class UserAgreeWebViewClient extends WebViewClient {
        private UserAgreeWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            UserAgreeDialog.this.logger.d("[UserAgreeDialog][UserAgreeWebViewClient][shouldOverrideUrlLoading]" + url);
            return super.shouldOverrideUrlLoading(view, url);
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            UserAgreeDialog.this.logger.d("[UserAgreeDialog][UserAgreeWebViewClient][ReceivedError]" + UserAgreeDialog.this.url);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            UserAgreeDialog.this.logger.d("[UserAgreeDialog][UserAgreeWebViewClient][onPageStarted]" + url);
            super.onPageStarted(view, url, favicon);
            if (!UserAgreeDialog.this.mSpinner.isShowing() && !UserAgreeDialog.this.destroyed) {
                UserAgreeDialog.this.mSpinner.show();
            }
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            UserAgreeDialog.this.logger.d("[UserAgreeDialog][UserAgreeWebViewClient][onPageFinished]" + url);
            if (UserAgreeDialog.this.mSpinner != null && UserAgreeDialog.this.mSpinner.isShowing()) {
                UserAgreeDialog.this.mSpinner.dismiss();
            }
            view.clearCache(true);
        }

        public void onLoadResource(WebView view, String url) {
            UserAgreeDialog.this.logger.d("[UserAgreeDialog][onLoadResource]" + url);
            super.onLoadResource(view, url);
        }
    }

    enum WebViewType {
        Kindle,
        Other
    }

    public UserAgreeDialog(Context context) {
        super(context);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(ModuleConfig.MERCURY_MODULE, ModuleConfig.MERCURY_MODULE);
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        this.scale = getContext().getResources().getDisplayMetrics().density;
        float[] dimensions = getContext().getResources().getConfiguration().orientation == 2 ? DIMENSIONS_DIFF_LANDSCAPE : DIMENSIONS_DIFF_PORTRAIT;
        this.mSpinner = new ProgressDialog(getContext());
        this.mSpinner.requestWindowFeature(1);
        this.mSpinner.setMessage("Loading...");
        this.mSpinner.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() == 1 && keyCode == 4) {
                    UserAgreeDialog.this.destroy();
                }
                return true;
            }
        });
        this.mSpinner.setCanceledOnTouchOutside(false);
        this.mainLayout = new LinearLayout(getContext());
        this.mainLayout.setOrientation(1);
        this.mainLayout.setBackgroundColor(-1);
        createTitle();
        setCanceledOnTouchOutside(false);
        if (Build.MODEL.trim().startsWith("Kindle Fire")) {
            createWebViewForKindle();
            setContentView(this.mainLayout, new LayoutParams(-1, -1));
            return;
        }
        createWebView();
        Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError e) {
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        setContentView(this.mainLayout, new LinearLayout.LayoutParams(point.x - ((int) ((dimensions[0] * this.scale) + 0.5f)), point.y - ((int) ((dimensions[1] * this.scale) + 0.5f))));
    }

    void setUrl(String url) {
        this.url = url;
    }

    void setBitmap(Bitmap logo, Bitmap button) {
        this.logoBitmap = logo;
        this.closeBitmap = button;
    }

    void destroy() {
        this.destroyed = true;
        if (this.mSpinner != null && this.mSpinner.isShowing()) {
            this.mSpinner.hide();
        }
        dismiss();
    }

    private void createTitle() {
        requestWindowFeature(1);
        RelativeLayout layout = new RelativeLayout(getContext());
        ImageView titleView = new ImageView(getContext());
        titleView.setImageBitmap(this.logoBitmap);
        titleView.setLayoutParams(new LayoutParams(-2, -2));
        ImageView closeView = new ImageView(getContext());
        closeView.setImageBitmap(this.closeBitmap);
        closeView.setLayoutParams(new LayoutParams(-2, -2));
        closeView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UserAgreeDialog.this.destroy();
            }
        });
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(-2, -2);
        titleParams.addRule(9);
        titleParams.addRule(13);
        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(-2, -2);
        closeParams.addRule(11);
        closeParams.addRule(15);
        layout.addView(titleView, titleParams);
        layout.addView(closeView, closeParams);
        layout.setBackgroundColor(Color.rgb(167, 169, 171));
        this.mainLayout.addView(layout);
    }

    private void createWebViewForKindle() {
        LinearLayout layout = new LinearLayout(getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layout.setLayoutParams(layoutParams);
        this.webView = new WebView(getContext());
        this.webView.setLayoutParams(new LayoutParams(-1, -2));
        this.webView.setWebViewClient(new UserAgreeWebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(this.url);
        layout.addView(this.webView);
        this.mainLayout.addView(layout, layoutParams);
    }

    private void createWebView() {
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.webView = new WebView(getContext());
        this.webView.setLayoutParams(new LayoutParams(-1, -1));
        this.webView.setWebViewClient(new UserAgreeWebViewClient());
        this.webView.getSettings().setJavaScriptEnabled(true);
        this.webView.loadUrl(this.url);
        layout.addView(this.webView);
        this.mainLayout.addView(layout);
    }

    public void onBackPressed() {
        super.onBackPressed();
        destroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        return true;
    }
}
