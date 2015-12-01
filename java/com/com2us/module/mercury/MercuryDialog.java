package com.com2us.module.mercury;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.SyncRequest;
import com.android.volley.toolbox.Volley;
import com.com2us.module.offerwall.OfferwallDefine;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import jp.co.cyberz.fox.notify.a;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;
import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MercuryDialog extends Dialog {
    public static Activity activity = null;
    public static Logger logger = null;
    private Bitmap bitmapClose = null;
    private Bitmap bitmapWebViewBorder0o = null;
    private Bitmap bitmapWebViewBorder1do = null;
    private Bitmap bitmapWebViewBorder1so = null;
    private Bitmap bitmapWebViewBorder2o = null;
    private ImageView closeButton = null;
    private int closeHeight = 0;
    private LinearLayout closeLayout = null;
    private int closeWidth = 0;
    private ArrayList<MercuryLayout> layoutList = new ArrayList();
    private int layoutListIndexToShow = 0;
    private Mercury mercury = null;
    private MercuryBridge mercuryBridge = null;
    private int screenHeight = 0;
    private int screenWidth = 0;
    private ProgressDialog spinner = null;

    class MercuryLayout extends FrameLayout {
        private Activity activity = null;
        private Bitmap bitmapWebViewBorder0 = null;
        private Bitmap bitmapWebViewBorder1d = null;
        private Bitmap bitmapWebViewBorder1s = null;
        private Bitmap bitmapWebViewBorder2 = null;
        private String board = "full";
        private LinearLayout fullBannerLayout = null;
        private int hRatio = 0;
        private MercuryLayout instance = null;
        private boolean isFullBanner = false;
        private boolean isFullScreen = false;
        private int margin = 0;
        private boolean onTouch1d = false;
        private boolean onTouch1s = false;
        private boolean onTouch2 = false;
        private String pid = null;
        private Rect touchRect1d;
        private Rect touchRect1s;
        private Rect touchRect2;
        private String urlString = null;
        private int wRatio = 0;
        private MercuryWebView webView = null;
        private ImageView webViewBorder0 = null;
        private ImageView webViewBorder1d = null;
        private ImageView webViewBorder1s = null;
        private ImageView webViewBorder2 = null;
        private float webViewBorderHeight = 0.0f;
        private LinearLayout webViewBorderLayout = null;
        private float webViewBorderMargin = 21.5f;
        private RelativeLayout webViewBorderSubLayout = null;
        private float webViewBorderWidth = 0.0f;
        private int webViewHeight = 0;
        private JSONObject webViewJSON = null;
        private float webViewPadding = 0.0f;
        private String webViewType = null;
        private int webViewWidth = 0;
        private float zoomRate = 0.0f;

        class MercuryWebViewClient extends WebViewClient {
            private SyncRequest _syncRequest = null;

            public MercuryWebViewClient() {
                this._syncRequest = Volley.newSyncRequest(MercuryLayout.this.activity, 33554432);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String _url) {
                String url = _url.trim();
                MercuryDialog.logger.d("shouldOverrideUrlLoading=" + _url);
                if (url.startsWith(WebClient.INTENT_PROTOCOL_START_HTTP) || url.startsWith("https")) {
                    MercuryDialog.logger.d("shouldOverrideUrlLoading : startsWith http or https");
                    return false;
                }
                String linkUrl = url;
                try {
                    int index = linkUrl.indexOf("c2smercury");
                    if (index >= 0) {
                        int dividedIndex = linkUrl.indexOf("?");
                        if (dividedIndex >= "c2smercury".length()) {
                            String cmd = linkUrl.substring(index, dividedIndex);
                            String params = linkUrl.substring(dividedIndex + 1);
                            if (cmd.endsWith("gobrowser")) {
                                if (MercuryLayout.this.isFullBanner) {
                                    MercuryDialog.logger.d(Constants.TAG, "endsWith gobrowser. isFullBanner true");
                                    MercuryLayout.this.setWebViewScrollLock(true);
                                    MercuryLayout.this.isFullScreen = false;
                                    MercuryLayout.this.webView.setOnTouchListener(new OnTouchListener() {
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return true;
                                        }
                                    });
                                }
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse(params));
                                MercuryLayout.this.activity.startActivity(intent);
                            } else if (cmd.endsWith("offauto")) {
                                MercuryLayout.this.setWebViewScrollLock(true);
                                MercuryDialog.logger.d(Constants.TAG, "params : " + params);
                                String[] arr = params.split("\\|");
                                MercuryDialog.this.mercury.saveOffAutoParams(arr[0], arr[1]);
                            } else if (cmd.endsWith("sendsms")) {
                                MercuryLayout.this.setWebViewScrollLock(false);
                                MercuryDialog.logger.d(Constants.TAG, "params : " + params);
                                MercuryDialog.this.mercuryBridge.sendSMS(params);
                            } else if (cmd.endsWith("sendemail")) {
                                MercuryLayout.this.setWebViewScrollLock(false);
                                MercuryDialog.logger.d(Constants.TAG, "params : " + params);
                                MercuryDialog.this.mercuryBridge.sendEmail(params);
                            } else if (cmd.endsWith("fullscreen")) {
                                if (MercuryLayout.this.isFullBanner) {
                                    MercuryDialog.logger.d(Constants.TAG, "endsWith fullscreen. isFullBanner true");
                                    MercuryLayout.this.setWebViewScrollLock(false);
                                    MercuryLayout.this.isFullScreen = false;
                                    MercuryLayout.this.webView.setOnTouchListener(new OnTouchListener() {
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return false;
                                        }
                                    });
                                } else {
                                    MercuryDialog.logger.d(Constants.TAG, "endsWith fullscreen. isFullBanner false");
                                    MercuryLayout.this.isFullBanner = true;
                                    MercuryLayout.this.setWebViewScrollLock(true);
                                    MercuryLayout.this.isFullScreen = true;
                                    MercuryLayout.this.webView.setOnTouchListener(new OnTouchListener() {
                                        public boolean onTouch(View v, MotionEvent event) {
                                            return true;
                                        }
                                    });
                                }
                                MercuryDialog.logger.d(Constants.TAG, "params : " + params);
                                MercuryLayout.this.urlString = params;
                                MercuryLayout.this.webView.loadUrl(MercuryLayout.this.urlString);
                                MercuryLayout.this.isFullScreen = true;
                                MercuryLayout.this.setWebViewScrollLock(false);
                            }
                        } else if (linkUrl.endsWith("showOfferwall")) {
                            if (!MercuryDialog.this.mercury.checkIfServiceAvailable("com.com2us.module.offerwall.Offerwall", MercuryLayout.this.activity)) {
                                Toast.makeText(MercuryLayout.this.activity, "Offerwall not supported", 1).show();
                            } else if (MercuryDialog.this.mercury.hasOfferwallInstance()) {
                                MercuryDialog.this.closeDialogOnly();
                                MercuryDialog.this.mercury.showOfferwall();
                            } else {
                                Toast.makeText(MercuryLayout.this.activity, "Offerwall not initialized", 1).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.i("Duration", "onPageStarted=" + url);
                ((MercuryWebView) view).setOnPageDownloading(true);
                if (!MercuryDialog.this.spinner.isShowing() && MercuryDialog.this.mercury.getWebViewIndexLoaded() == 0) {
                    MercuryDialog.this.spinner.show();
                }
            }

            public void onPageFinished(WebView view, String url) {
                Log.i("Duration", "onPageFinished");
                ((MercuryWebView) view).setOnPageDownloading(false);
                MercuryDialog.this.spinner.dismiss();
                if (MercuryLayout.this.isFullScreen) {
                    MercuryDialog.logger.d("full screen paged finished");
                    MercuryLayout.this.removeView(MercuryLayout.this.webViewBorderLayout);
                    MercuryLayout.this.resetWebViewSize(MercuryLayout.this.fullBannerLayout);
                    MercuryLayout.this.resizeWebview(MercuryLayout.this.urlString);
                    MercuryLayout.this.setCloseButton(MercuryDialog.this.closeWidth, MercuryDialog.this.closeHeight, 0, 2, 3, 0);
                    MercuryLayout.this.isFullScreen = false;
                }
                MercuryDialog.logger.d(Constants.TAG, " onPageFinished " + url);
                MercuryDialog.this.mercury.increaseWebViewIndexLoaded();
                MercuryDialog.this.mercury.loadDialog();
                super.onPageFinished(view, url);
            }

            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                try {
                    if (url.endsWith(".png") || url.endsWith(".jpeg") || url.endsWith(".jpg") || url.endsWith(".bmp") || url.endsWith(".bmpx")) {
                        Response<?> response = this._syncRequest.excute(url);
                        if (response != null) {
                            return new WebResourceResponse((String) response.cacheEntry.responseHeaders.get("Content-Type"), null, new ByteArrayInputStream(response.cacheEntry.data));
                        }
                    }
                } catch (Exception e) {
                }
                return super.shouldInterceptRequest(view, url);
            }
        }

        public MercuryLayout(Activity _activity) {
            super(_activity);
            this.activity = _activity;
            this.instance = this;
        }

        public void init(JSONObject jsonObj) {
            try {
                MercuryDialog.logger.d(Constants.TAG, "MercuryLayout Init : " + jsonObj.toString());
                this.webViewJSON = jsonObj;
                this.urlString = jsonObj.getString(a.g);
                this.board = jsonObj.getString("board");
                MercuryDialog.logger.d(Constants.TAG, "board is " + this.board);
                if (this.board.equals(MercuryDefine.WEBVIEW_TYPE_CUSTOM)) {
                    this.margin = jsonObj.getInt("margin");
                    this.wRatio = jsonObj.getInt("wratio");
                    this.hRatio = jsonObj.getInt("hratio");
                    this.pid = Integer.toString(jsonObj.getInt("pid"));
                    this.webViewType = jsonObj.getString("webview_type");
                    this.isFullBanner = true;
                } else {
                    this.isFullBanner = false;
                }
                setLayout();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void setLayout() {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    MercuryLayout.this.setLayoutParams(new LayoutParams(-1, -1));
                    MercuryLayout.this.setPadding(0, 0, 0, 0);
                    MercuryLayout.this.webView = new MercuryWebView(MercuryLayout.this.activity);
                    MercuryLayout.this.webView.setWebViewClient(new MercuryWebViewClient());
                    MercuryLayout.this.webView.setLayoutParams(new LayoutParams(-1, -1));
                    MercuryLayout.this.webView.getSettings().setSupportMultipleWindows(false);
                    MercuryLayout.this.webView.getSettings().setDefaultTextEncodingName("UTF-8");
                    MercuryLayout.this.webView.getSettings().setJavaScriptEnabled(true);
                    MercuryLayout.this.webView.setVerticalScrollbarOverlay(true);
                    if (18 < VERSION.SDK_INT) {
                        MercuryLayout.this.webView.getSettings().setCacheMode(2);
                    }
                    if (VERSION.SDK_INT > 11) {
                        Log.i("Duration", "isHardwareAccelerated? " + MercuryLayout.this.webView.isHardwareAccelerated());
                    }
                    String postStr = "mq_webview=" + MercuryLayout.this.webViewJSON.toString();
                    MercuryDialog.logger.d(Constants.TAG, "postStr : " + postStr);
                    MercuryLayout.this.webView.postUrl(MercuryLayout.this.urlString, EncodingUtils.getBytes(postStr, "UTF-8"));
                    MercuryLayout.this.setWebViewScrollLock(MercuryLayout.this.isFullBanner);
                    MercuryLayout.this.webView.setOnTouchListener(new OnTouchListener() {
                        public boolean onTouch(View v, MotionEvent event) {
                            return MercuryLayout.this.isFullBanner && event.getAction() == 2;
                        }
                    });
                    if (MercuryLayout.this.board.equals(MercuryDefine.WEBVIEW_TYPE_CUSTOM)) {
                        MercuryLayout.this.fullBannerLayout = new LinearLayout(MercuryLayout.this.activity);
                        MercuryLayout.this.setBackgroundColor(-16777216);
                        MercuryLayout.this.getBackground().setAlpha(SelectTarget.TARGETING_SUCCESS);
                        MercuryLayout.this.calculateWebViewBorderSize(MercuryLayout.this.instance);
                        MercuryLayout.this.webView.setBackgroundColor(0);
                        MercuryLayout.this.fullBannerLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
                        MercuryLayout.this.calculateWebViewSize(MercuryLayout.this.fullBannerLayout);
                        MercuryLayout.this.fullBannerLayout.setBackgroundColor(0);
                        MercuryLayout.this.fullBannerLayout.addView(MercuryLayout.this.webView);
                        MercuryLayout.this.addView(MercuryLayout.this.fullBannerLayout);
                        MercuryLayout.this.setWebViewBorder();
                    } else {
                        MercuryLayout.this.webView.setBackgroundColor(-1);
                        MercuryLayout.this.addView(MercuryLayout.this.webView);
                        MercuryLayout.this.setCloseButton(MercuryDialog.this.closeWidth, MercuryDialog.this.closeHeight, 0, 2, 3, 0);
                    }
                    if (MercuryLayout.this.isFullBanner) {
                        MercuryDialog.logger.d("set Layout isFullBanner");
                        float buttonHegith = ((float) Math.min(MercuryLayout.this.webViewWidth, MercuryLayout.this.webViewHeight)) * 0.121875f;
                        float topPadding;
                        float rightPadding;
                        if (MercuryLayout.this.webViewWidth < MercuryLayout.this.webViewHeight) {
                            topPadding = ((float) MercuryLayout.this.webViewWidth) * 0.028125f;
                            rightPadding = ((float) MercuryLayout.this.webViewHeight) * 0.0132f;
                            return;
                        }
                        topPadding = ((float) MercuryLayout.this.webViewHeight) * 0.028125f;
                        rightPadding = ((float) MercuryLayout.this.webViewWidth) * 0.0132f;
                        return;
                    }
                    MercuryDialog.logger.d("set Layout not isFullBanner");
                }
            });
        }

        public boolean changeButtonSize(int buttonWidth, int buttonHeight, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
            if (buttonWidth <= 0 || buttonWidth <= 0) {
                MercuryDialog.logger.i("Error: button size is " + buttonWidth + ", " + buttonHeight);
                return false;
            } else if (leftMargin < 0 || topMargin < 0 || rightMargin < 0 || bottomMargin < 0) {
                MercuryDialog.logger.d(Constants.TAG, "Error : Margin is : " + leftMargin + ", " + topMargin + ", " + rightMargin + ", " + bottomMargin);
                return false;
            } else {
                ImageView closeButton = (ImageView) findViewWithTag("closeButton");
                if (closeButton == null) {
                    MercuryDialog.logger.i(Constants.TAG, "Error : closeButton is null in changeButtonSize");
                    return false;
                }
                closeButton.getLayoutParams().width = buttonWidth;
                closeButton.getLayoutParams().height = buttonHeight;
                closeButton.setPadding(MercuryDialog.this.DPFromPixel(leftMargin), MercuryDialog.this.DPFromPixel(topMargin), MercuryDialog.this.DPFromPixel(rightMargin), MercuryDialog.this.DPFromPixel(rightMargin));
                closeButton.invalidate();
                return true;
            }
        }

        public boolean setWebViewBorder() {
            this.webViewBorder0 = new ImageView(this.activity);
            this.webViewBorder0.setTag("webViewBorder");
            this.webViewBorder1d = new ImageView(this.activity);
            this.webViewBorder1d.setTag("webViewBorder");
            this.webViewBorder1d.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    MercuryLayout.this.webViewBorder1s.setVisibility(0);
                    MercuryDialog.logger.d("1d onClick");
                }
            });
            this.webViewBorder1d.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case g.a /*0*/:
                            MercuryLayout.this.touchRect1d = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                            MercuryLayout.this.onTouch1d = true;
                            MercuryLayout.this.webViewBorder1d.setVisibility(4);
                            MercuryLayout.this.webViewBorder1s.setVisibility(0);
                            break;
                        case o.a /*1*/:
                            if (MercuryLayout.this.onTouch1d) {
                                if (!MercuryLayout.this.onTouch2) {
                                    if (MercuryLayout.this.webViewBorder1s.getVisibility() == 0) {
                                        MercuryDialog.this.mercury.saveOffAutoParams(MercuryLayout.this.webViewType, MercuryLayout.this.pid);
                                    } else {
                                        MercuryDialog.this.close();
                                    }
                                }
                                v.performClick();
                                MercuryLayout.this.onTouch1d = false;
                                break;
                            }
                            break;
                        case o.b /*2*/:
                            if (!MercuryLayout.this.touchRect1d.contains(v.getLeft() + ((int) event.getX()), v.getTop() + ((int) event.getY()))) {
                                MercuryLayout.this.onTouch1d = false;
                                MercuryLayout.this.webViewBorder1s.setVisibility(4);
                                MercuryLayout.this.webViewBorder1d.setVisibility(0);
                                break;
                            }
                            break;
                    }
                    return false;
                }
            });
            this.webViewBorder1s = new ImageView(this.activity);
            this.webViewBorder1s.setTag("webViewBorder");
            this.webViewBorder1s.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                }
            });
            this.webViewBorder1s.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case g.a /*0*/:
                            MercuryLayout.this.touchRect1s = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                            MercuryLayout.this.onTouch1s = true;
                            MercuryLayout.this.webViewBorder1s.setVisibility(4);
                            MercuryLayout.this.webViewBorder1d.setVisibility(0);
                            break;
                        case o.a /*1*/:
                            if (MercuryLayout.this.onTouch1s) {
                                v.performClick();
                                MercuryLayout.this.onTouch1s = false;
                                break;
                            }
                            break;
                        case o.b /*2*/:
                            if (!MercuryLayout.this.touchRect1s.contains(v.getLeft() + ((int) event.getX()), v.getTop() + ((int) event.getY()))) {
                                MercuryLayout.this.onTouch1s = false;
                                MercuryLayout.this.webViewBorder1d.setVisibility(4);
                                MercuryLayout.this.webViewBorder1s.setVisibility(0);
                                break;
                            }
                            break;
                    }
                    return false;
                }
            });
            this.webViewBorder2 = new ImageView(this.activity);
            this.webViewBorder2.setTag("webViewBorder");
            this.webViewBorder2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                }
            });
            this.webViewBorder2.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case g.a /*0*/:
                            MercuryLayout.this.touchRect2 = new Rect(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
                            MercuryLayout.this.onTouch2 = true;
                            break;
                        case o.a /*1*/:
                            if (MercuryLayout.this.onTouch2) {
                                if (!(MercuryLayout.this.onTouch1d || MercuryLayout.this.onTouch1s)) {
                                    if (MercuryLayout.this.webViewBorder1s.getVisibility() == 0) {
                                        MercuryDialog.this.mercury.saveOffAutoParams(MercuryLayout.this.webViewType, MercuryLayout.this.pid);
                                    } else {
                                        MercuryDialog.this.close();
                                    }
                                }
                                v.performClick();
                                MercuryLayout.this.onTouch2 = false;
                                break;
                            }
                            break;
                        case o.b /*2*/:
                            if (!MercuryLayout.this.touchRect2.contains(v.getLeft() + ((int) event.getX()), v.getTop() + ((int) event.getY()))) {
                                MercuryLayout.this.onTouch2 = false;
                                break;
                            }
                            break;
                    }
                    return false;
                }
            });
            this.bitmapWebViewBorder0 = Bitmap.createScaledBitmap(MercuryDialog.this.bitmapWebViewBorder0o, Math.round(((float) MercuryDialog.this.bitmapWebViewBorder0o.getWidth()) * this.zoomRate), Math.round(((float) MercuryDialog.this.bitmapWebViewBorder0o.getHeight()) * this.zoomRate), true);
            this.bitmapWebViewBorder1d = Bitmap.createScaledBitmap(MercuryDialog.this.bitmapWebViewBorder1do, Math.round(((float) MercuryDialog.this.bitmapWebViewBorder1do.getWidth()) * this.zoomRate), Math.round(((float) MercuryDialog.this.bitmapWebViewBorder1do.getHeight()) * this.zoomRate), true);
            this.bitmapWebViewBorder1s = Bitmap.createScaledBitmap(MercuryDialog.this.bitmapWebViewBorder1so, Math.round(((float) MercuryDialog.this.bitmapWebViewBorder1so.getWidth()) * this.zoomRate), Math.round(((float) MercuryDialog.this.bitmapWebViewBorder1so.getHeight()) * this.zoomRate), true);
            this.bitmapWebViewBorder2 = Bitmap.createScaledBitmap(MercuryDialog.this.bitmapWebViewBorder2o, Math.round(((float) MercuryDialog.this.bitmapWebViewBorder2o.getWidth()) * this.zoomRate), Math.round(((float) MercuryDialog.this.bitmapWebViewBorder2o.getHeight()) * this.zoomRate), true);
            if (this.bitmapWebViewBorder0 == null || this.bitmapWebViewBorder1d == null || this.bitmapWebViewBorder1s == null || this.bitmapWebViewBorder2 == null) {
                MercuryDialog.logger.d(Constants.TAG, "Error : Some of WebViewBorder Bitmap is null");
                return false;
            }
            MercuryDialog.logger.d("bitmapWebViewBorder0.getWidth() : " + this.bitmapWebViewBorder0.getWidth() + ", bitmapWebViewBorder1d.getWidth() : " + this.bitmapWebViewBorder1d.getWidth() + ", bitmapWebViewBorder2.getWidth() : " + this.bitmapWebViewBorder2.getWidth());
            MercuryDialog.logger.d("bitmapWebViewBorder0.getHeight() : " + this.bitmapWebViewBorder0.getHeight() + ", bitmapWebViewBorder1d.getHeight() : " + this.bitmapWebViewBorder1d.getHeight() + ", bitmapWebViewBorder2.getHeight() : " + this.bitmapWebViewBorder2.getHeight());
            MercuryDialog.logger.d("bitmapWebViewBorder1d.getWidth()+bitmapWebViewBorder2.getWidth : " + (this.bitmapWebViewBorder1d.getWidth() + this.bitmapWebViewBorder2.getWidth()));
            this.webViewBorder0.setImageBitmap(this.bitmapWebViewBorder0);
            this.webViewBorder0.setAdjustViewBounds(true);
            this.webViewBorder0.setScaleType(ScaleType.FIT_XY);
            this.webViewBorder0.setBackgroundColor(0);
            this.webViewBorder0.setLayoutParams(new LayoutParams(-2, -2));
            this.webViewBorder1d.setImageBitmap(this.bitmapWebViewBorder1d);
            this.webViewBorder1d.setAdjustViewBounds(true);
            this.webViewBorder1d.setScaleType(ScaleType.FIT_XY);
            this.webViewBorder1d.setBackgroundColor(0);
            this.webViewBorder1d.setLayoutParams(new LayoutParams(-2, -2));
            this.webViewBorder1s.setImageBitmap(this.bitmapWebViewBorder1s);
            this.webViewBorder1s.setAdjustViewBounds(true);
            this.webViewBorder1s.setScaleType(ScaleType.FIT_XY);
            this.webViewBorder1s.setBackgroundColor(0);
            this.webViewBorder1s.setLayoutParams(new LayoutParams(-2, -2));
            this.webViewBorder1s.setVisibility(4);
            this.webViewBorder2.setImageBitmap(this.bitmapWebViewBorder2);
            this.webViewBorder2.setAdjustViewBounds(true);
            this.webViewBorder2.setScaleType(ScaleType.FIT_XY);
            this.webViewBorder2.setBackgroundColor(0);
            this.webViewBorder2.setLayoutParams(new LayoutParams(-2, -2));
            this.webViewBorderLayout = new LinearLayout(this.activity);
            this.webViewBorderLayout.setTag("webViewBorderLayout");
            this.webViewBorderLayout.setBackgroundColor(0);
            this.webViewBorderLayout.setGravity(49);
            this.webViewBorderLayout.setOrientation(1);
            this.webViewBorderLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
            this.webViewBorderLayout.addView(this.webViewBorder0);
            this.webViewBorderSubLayout = new RelativeLayout(this.activity);
            this.webViewBorderSubLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
            this.webViewBorderSubLayout.setBackgroundColor(0);
            this.webViewBorderSubLayout.setTag("webViewBorderSubLayout");
            TextView doNotShowAgainText = new TextView(this.activity);
            doNotShowAgainText.setBackgroundColor(0);
            doNotShowAgainText.setText(MercuryData.getDialogBorderText(0));
            doNotShowAgainText.setWidth((this.bitmapWebViewBorder1d.getWidth() * 3) / 4);
            doNotShowAgainText.setTextSize(0, ((float) this.bitmapWebViewBorder1d.getHeight()) * 0.22f);
            doNotShowAgainText.setTextColor(Color.rgb(102, 102, 102));
            LinearLayout doNotShowAgain = new LinearLayout(this.activity);
            doNotShowAgain.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            doNotShowAgain.setBackgroundColor(0);
            doNotShowAgain.setPadding((this.bitmapWebViewBorder1d.getHeight() * 7) / 6, 0, (this.bitmapWebViewBorder1d.getHeight() * 1) / 7, (this.bitmapWebViewBorder1d.getHeight() * 1) / 7);
            doNotShowAgain.setGravity(19);
            doNotShowAgain.addView(doNotShowAgainText);
            RelativeLayout checkBoxLayout = new RelativeLayout(this.activity);
            checkBoxLayout.setLayoutParams(new RelativeLayout.LayoutParams(this.bitmapWebViewBorder1d.getWidth(), this.bitmapWebViewBorder1d.getHeight()));
            checkBoxLayout.addView(this.webViewBorder1d);
            checkBoxLayout.addView(this.webViewBorder1s);
            checkBoxLayout.addView(doNotShowAgain);
            this.webViewBorderSubLayout.addView(checkBoxLayout);
            RelativeLayout closeButtonLayout = new RelativeLayout(this.activity);
            RelativeLayout.LayoutParams closeButtonLayoutParams = new RelativeLayout.LayoutParams(this.bitmapWebViewBorder2.getWidth(), this.bitmapWebViewBorder2.getHeight());
            closeButtonLayoutParams.addRule(11);
            closeButtonLayout.setLayoutParams(closeButtonLayoutParams);
            closeButtonLayout.addView(this.webViewBorder2);
            this.webViewBorderSubLayout.addView(closeButtonLayout);
            this.webViewBorderLayout.addView(this.webViewBorderSubLayout);
            addView(this.webViewBorderLayout);
            return true;
        }

        public boolean setCloseButton(int buttonWidth, int buttonHeight, int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
            if (buttonWidth <= 0 || buttonHeight <= 0) {
                MercuryDialog.logger.d(Constants.TAG, "Error: button Size : " + buttonWidth + ", " + buttonHeight);
                return false;
            } else if (leftMargin < 0 || topMargin < 0 || rightMargin < 0 || bottomMargin < 0) {
                MercuryDialog.logger.d(Constants.TAG, "Error : Margin is : " + leftMargin + ", " + topMargin + ", " + rightMargin + ", " + bottomMargin);
                return false;
            } else {
                LinearLayout templayout = (LinearLayout) findViewWithTag("closeLayout");
                if (templayout != null) {
                    removeView(templayout);
                }
                MercuryDialog.this.closeButton = new ImageView(this.activity);
                MercuryDialog.this.closeButton.setTag("closeButton");
                MercuryDialog.this.closeButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        MercuryDialog.this.close();
                    }
                });
                MercuryDialog.this.bitmapClose = Bitmap.createScaledBitmap(MercuryDialog.this.bitmapClose, buttonWidth, buttonHeight, true);
                if (MercuryDialog.this.bitmapClose == null) {
                    MercuryDialog.logger.d(Constants.TAG, "Error : CloseButton Bitmap is null");
                    return false;
                }
                MercuryDialog.this.closeButton.setPadding(MercuryDialog.this.DPFromPixel(leftMargin), MercuryDialog.this.DPFromPixel(topMargin), MercuryDialog.this.DPFromPixel(rightMargin), MercuryDialog.this.DPFromPixel(rightMargin));
                MercuryDialog.this.closeButton.setImageBitmap(MercuryDialog.this.bitmapClose);
                MercuryDialog.this.closeButton.setAdjustViewBounds(true);
                MercuryDialog.this.closeButton.setScaleType(ScaleType.FIT_XY);
                MercuryDialog.this.closeButton.setBackgroundColor(0);
                MercuryDialog.this.closeButton.setLayoutParams(new LayoutParams(-2, -2));
                MercuryDialog.this.closeLayout = new LinearLayout(this.activity);
                MercuryDialog.this.closeLayout.setTag("closeLayout");
                LinearLayout.LayoutParams closeLayoutParam = new LinearLayout.LayoutParams(-1, -2);
                MercuryDialog.this.closeLayout.setGravity(53);
                MercuryDialog.this.closeLayout.setLayoutParams(closeLayoutParam);
                MercuryDialog.this.closeLayout.addView(MercuryDialog.this.closeButton);
                addView(MercuryDialog.this.closeLayout);
                return true;
            }
        }

        public void resizeWebview(String url) {
            MercuryDialog.logger.d(Constants.TAG, "resizing Webview");
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    MercuryLayout.this.instance.setPadding(0, 0, 0, 0);
                    MercuryDialog.this.applyAnimation(MercuryLayout.this.webView);
                    MercuryLayout.this.instance.setBackgroundColor(0);
                }
            });
        }

        public void setWebViewScrollLock(boolean lockFlag) {
            boolean z;
            boolean z2 = false;
            MercuryWebView mercuryWebView = this.webView;
            if (lockFlag) {
                z = false;
            } else {
                z = true;
            }
            mercuryWebView.setVerticalScrollBarEnabled(z);
            MercuryWebView mercuryWebView2 = this.webView;
            if (!lockFlag) {
                z2 = true;
            }
            mercuryWebView2.setHorizontalScrollBarEnabled(z2);
        }

        private void resetWebViewSize(LinearLayout layout) {
            layout.setPadding(0, 0, 0, 0);
        }

        private void calculateWebViewSize(LinearLayout layout) {
            float verticalRatio = (float) this.hRatio;
            float horizontalRatio = (float) this.wRatio;
            MercuryDialog.logger.d(Constants.TAG, "calculateWebViewSize : horizontalRatio & verticalRatio : " + horizontalRatio + ", " + verticalRatio);
            this.webViewBorderMargin *= this.zoomRate;
            this.webViewPadding = this.webViewBorderMargin * 2.0f;
            MercuryDialog.logger.d(Constants.TAG, "calculateWebViewSize : webViewPadding : " + this.webViewPadding + " @ zoomRate " + this.zoomRate);
            float webViewWidth = this.webViewBorderWidth - this.webViewPadding;
            float webViewHeight = (webViewWidth * verticalRatio) / horizontalRatio;
            MercuryDialog.logger.d(Constants.TAG, "calculateWebViewSize : webView w, h " + webViewWidth + ", " + webViewHeight);
            int edgeWidthLeft = Math.round(this.webViewPadding / 2.0f);
            int edgeWidthRight = Math.round(this.webViewPadding / 2.0f);
            int edgeHeight = ((int) ((this.webViewBorderHeight - this.webViewBorderMargin) - webViewHeight)) - 10;
            MercuryDialog.logger.d(Constants.TAG, "Padding edgeWidth wL, wR, h " + edgeWidthLeft + ", " + edgeWidthRight + ", " + edgeHeight);
            layout.setPadding(edgeWidthLeft, edgeWidthRight, (((double) this.zoomRate) < 1.0d ? 1 : 0) + edgeWidthRight, edgeHeight);
        }

        private void calculateWebViewBorderSize(FrameLayout layout) {
            View curView = this.activity.getWindow().getDecorView();
            if (VERSION.SDK_INT == 11 || VERSION.SDK_INT == 12) {
                MercuryDialog.this.screenWidth = this.activity.getResources().getDisplayMetrics().widthPixels;
                MercuryDialog.this.screenHeight = this.activity.getResources().getDisplayMetrics().heightPixels;
                MercuryDialog.logger.d(Constants.TAG, "calculateWebViewBorderSize : Screen w, h " + MercuryDialog.this.screenWidth + ", " + MercuryDialog.this.screenHeight);
                MercuryDialog.this.screenWidth = curView.getWidth();
                MercuryDialog.this.screenHeight = curView.getHeight();
                MercuryDialog.logger.d(Constants.TAG, "calculateWebViewBorderSize : Screen w, h " + MercuryDialog.this.screenWidth + ", " + MercuryDialog.this.screenHeight);
            } else {
                MercuryDialog.this.screenWidth = curView.getWidth();
                MercuryDialog.this.screenHeight = curView.getHeight();
                MercuryDialog.logger.d(Constants.TAG, "calculateWebViewBorderSize : Screen w, h " + MercuryDialog.this.screenWidth + ", " + MercuryDialog.this.screenHeight);
                MercuryDialog.this.screenWidth = this.activity.getResources().getDisplayMetrics().widthPixels;
                MercuryDialog.this.screenHeight = this.activity.getResources().getDisplayMetrics().heightPixels;
                MercuryDialog.logger.d(Constants.TAG, "calculateWebViewBorderSize : Screen w, h " + MercuryDialog.this.screenWidth + ", " + MercuryDialog.this.screenHeight);
            }
            MercuryDialog.logger.d(Constants.TAG, "bitmapWebViewBorder0o.getWidth() : " + MercuryDialog.this.bitmapWebViewBorder0o.getWidth());
            MercuryDialog.logger.d(Constants.TAG, "bitmapWebViewBorder0o.getHeight() : " + MercuryDialog.this.bitmapWebViewBorder0o.getHeight() + ", bitmapWebViewBorder1do.getHeight() : " + MercuryDialog.this.bitmapWebViewBorder1do.getHeight() + ", sum : " + (MercuryDialog.this.bitmapWebViewBorder0o.getHeight() + MercuryDialog.this.bitmapWebViewBorder1do.getHeight()));
            MercuryDialog.logger.d(Constants.TAG, "bitmapWebViewBorder1do.getWidth() : " + MercuryDialog.this.bitmapWebViewBorder1do.getWidth() + ", bitmapWebViewBorder2o.getWidth() : " + MercuryDialog.this.bitmapWebViewBorder2o.getWidth() + ", sum : " + (MercuryDialog.this.bitmapWebViewBorder1do.getWidth() + MercuryDialog.this.bitmapWebViewBorder2o.getWidth()));
            float verticalRatio = (float) (MercuryDialog.this.bitmapWebViewBorder0o.getHeight() + MercuryDialog.this.bitmapWebViewBorder1do.getHeight());
            float horizontalRatio = (float) MercuryDialog.this.bitmapWebViewBorder0o.getWidth();
            this.zoomRate = Math.min(((float) MercuryDialog.this.screenWidth) / horizontalRatio, ((float) MercuryDialog.this.screenHeight) / verticalRatio);
            MercuryDialog.logger.d(Constants.TAG, "zoomRate1 : " + (((float) MercuryDialog.this.screenWidth) / horizontalRatio) + ", zoomRate2 : " + (((float) MercuryDialog.this.screenHeight) / verticalRatio));
            this.webViewBorderHeight = ((float) (MercuryDialog.this.bitmapWebViewBorder0o.getHeight() + MercuryDialog.this.bitmapWebViewBorder1do.getHeight())) * this.zoomRate;
            this.webViewBorderWidth = ((float) MercuryDialog.this.bitmapWebViewBorder0o.getWidth()) * this.zoomRate;
            MercuryDialog.logger.d(Constants.TAG, "webViewBorderHeight : " + (((float) (MercuryDialog.this.bitmapWebViewBorder0o.getHeight() + MercuryDialog.this.bitmapWebViewBorder1do.getHeight())) * this.zoomRate) + " -> " + this.webViewBorderHeight);
            MercuryDialog.logger.d(Constants.TAG, "webViewBorderWidth : " + (((float) MercuryDialog.this.bitmapWebViewBorder0o.getWidth()) * this.zoomRate) + " -> " + this.webViewBorderWidth);
            int edgeWidthRight = (int) ((((float) MercuryDialog.this.screenWidth) - this.webViewBorderWidth) / 2.0f);
            int edgeWidthLeft = (int) ((((float) MercuryDialog.this.screenWidth) - this.webViewBorderWidth) / 2.0f);
            int edgeHeight = (int) ((((float) MercuryDialog.this.screenHeight) - this.webViewBorderHeight) / 2.0f);
            MercuryDialog.logger.d(Constants.TAG, "Padding edgeWidth wL, wR, h : " + edgeWidthLeft + ", " + edgeWidthRight + ", " + edgeHeight);
            layout.setPadding(edgeWidthLeft, edgeHeight, edgeWidthRight, edgeHeight);
        }
    }

    class MercuryWebView extends WebView {
        private boolean onPageDownloading = false;

        public boolean getOnPageDownloading() {
            return this.onPageDownloading;
        }

        public void setOnPageDownloading(boolean b) {
            this.onPageDownloading = b;
        }

        public MercuryWebView(Context context) {
            super(context);
        }
    }

    public MercuryDialog(Mercury mercury, Activity _activity) {
        super(_activity, 16973841);
        this.mercury = mercury;
        activity = _activity;
        logger = LoggerGroup.createLogger(Constants.TAG, activity);
        this.mercuryBridge = new MercuryBridge(activity);
        MercuryProperties.loadProperties(activity);
        setCancelable(true);
        setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() != 1) {
                    return false;
                }
                if (keyCode != 4) {
                    return true;
                }
                MercuryDialog.this.close();
                return true;
            }
        });
        setCanceledOnTouchOutside(false);
    }

    public boolean applyAnimation(WebView webView) {
        if (this.screenWidth <= 0 || this.screenHeight <= 0) {
            int i = this.screenWidth;
            logger.d(Constants.TAG, "Error : ScreenSize is " + r0 + ", " + this.screenHeight);
            return false;
        }
        float webViewWidth = (float) webView.getWidth();
        float webViewHeight = (float) webView.getHeight();
        int webViewPosX = ((int) (((float) this.screenWidth) - webViewWidth)) / 2;
        int webViewPosY = ((int) (((float) this.screenHeight) - webViewHeight)) / 2;
        float scaleToX = ((float) this.screenWidth) / webViewWidth;
        float scaleToY = ((float) this.screenHeight) / webViewHeight;
        AnimationSet animationSet = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f / scaleToX, 1.0f, 1.0f / scaleToY, 1.0f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setFillAfter(true);
        TranslateAnimation transAnimation = new TranslateAnimation((float) webViewPosX, 0.0f, (float) webViewPosY, 0.0f);
        transAnimation.setDuration(300);
        transAnimation.setFillAfter(true);
        animationSet.addAnimation(transAnimation);
        webView.setAnimation(animationSet);
        webView.startAnimation(animationSet);
        return true;
    }

    public int getLayoutListsize() {
        return this.layoutList.size();
    }

    public void generateMercuryLayout(JSONObject jsonObj, int count) {
        MercuryLayout mercuryLayout = new MercuryLayout(activity);
        mercuryLayout.init(jsonObj);
        logger.d("add to layoutList at " + count);
        this.layoutList.add(count, mercuryLayout);
    }

    int DPFromPixel(int pixel) {
        return (int) ((((float) pixel) / OfferwallDefine.DEFAULT_HDIP_DENSITY_SCALE) * activity.getResources().getDisplayMetrics().density);
    }

    int PixelFromDP(int DP) {
        return (int) ((((float) DP) / activity.getResources().getDisplayMetrics().density) * OfferwallDefine.DEFAULT_HDIP_DENSITY_SCALE);
    }

    double getInch() {
        DisplayMetrics dm = activity.getResources().getDisplayMetrics();
        float x = ((float) dm.widthPixels) / dm.xdpi;
        float y = ((float) dm.heightPixels) / dm.ydpi;
        return Math.sqrt((double) ((x * x) + (y * y)));
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                MercuryDialog.this.spinner = new ProgressDialog(MercuryDialog.activity);
                MercuryDialog.this.spinner.requestWindowFeature(1);
                MercuryDialog.this.spinner.setMessage(MercuryData.getProgressDialogText());
                MercuryDialog.this.setContentView((View) MercuryDialog.this.layoutList.get(MercuryDialog.this.layoutListIndexToShow));
            }
        });
    }

    public boolean onSearchRequested() {
        return false;
    }

    public void setBitmap(Bitmap _bitmap, Bitmap _bitmap1, Bitmap _bitmap2, Bitmap _bitmap3, Bitmap _bitmap4) {
        this.closeWidth = (int) TypedValue.applyDimension(1, (float) ((_bitmap.getWidth() * 4) / 7), activity.getResources().getDisplayMetrics());
        this.closeHeight = (int) TypedValue.applyDimension(1, (float) ((_bitmap.getHeight() * 4) / 7), activity.getResources().getDisplayMetrics());
        this.bitmapClose = Bitmap.createScaledBitmap(_bitmap, this.closeWidth, this.closeHeight, true);
        logger.d("_bitmap.getWidth() : " + _bitmap.getWidth());
        logger.d("closeWidth : " + this.closeWidth);
        logger.d("_bitmap.getHeight() : " + _bitmap.getHeight());
        logger.d("closeHeight : " + this.closeHeight);
        this.bitmapWebViewBorder0o = _bitmap1;
        this.bitmapWebViewBorder1do = _bitmap2;
        this.bitmapWebViewBorder1so = _bitmap3;
        this.bitmapWebViewBorder2o = _bitmap4;
    }

    public void destroy() {
        if (this.spinner != null && this.spinner.isShowing()) {
            this.spinner.dismiss();
        }
        if (isShowing()) {
            dismiss();
        }
    }

    public void show() {
        logger.d("show mercury dialog");
        super.show();
    }

    public void close() {
        close(false);
    }

    public void closeDialogOnly() {
        close(true);
    }

    private void close(boolean dialogOnly) {
        logger.d("close mercury dialog");
        logger.d(Constants.TAG, "layoutListCount : " + this.layoutListIndexToShow + "  , mercury.getWebViewCount() :" + this.mercury.getWebViewCount());
        this.layoutListIndexToShow++;
        if (this.layoutListIndexToShow < this.mercury.getMercuryLayoutGeneratedCount()) {
            logger.d("more things to show");
            setContentView((View) this.layoutList.get(this.layoutListIndexToShow));
            show();
            if (((MercuryLayout) this.layoutList.get(this.layoutListIndexToShow)).webView.getOnPageDownloading() && !this.spinner.isShowing()) {
                this.spinner.show();
                return;
            }
            return;
        }
        logger.d("nothings to show");
        this.layoutListIndexToShow = 0;
        if (!dialogOnly) {
            this.mercury.close();
        }
        destroy();
    }
}
