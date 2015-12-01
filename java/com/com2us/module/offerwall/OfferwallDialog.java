package com.com2us.module.offerwall;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

public class OfferwallDialog extends Dialog {
    public static Logger logger = null;
    private Activity activity = null;
    private Bitmap bitmapClose = null;
    private Bitmap bitmapPhone = null;
    private Bitmap bitmapTablet = null;
    private Animation closeAnimation = null;
    private ImageView closeButton = null;
    private int closeHeight = 0;
    private int closeWidth = 0;
    private FrameLayout dialogLayout = null;
    private Offerwall offerwall;
    private Animation openAnimation = null;
    private ProgressDialog spinner = null;
    private String targetURL = null;
    private WebView webView = null;

    class OfferwallBridge {
        Activity activity = null;

        OfferwallBridge(Activity _activity) {
            this.activity = _activity;
        }

        @JavascriptInterface
        public String getClientAppList() throws JSONException {
            OfferwallDialog.logger.d("getClientAppList");
            PackageManager manager = this.activity.getPackageManager();
            List<String> appList = new ArrayList();
            Intent mainIntent = new Intent("android.intent.action.MAIN", null);
            mainIntent.addCategory("android.intent.category.LAUNCHER");
            List<ResolveInfo> resolveInfos = manager.queryIntentActivities(mainIntent, 0);
            int iend = resolveInfos.size();
            for (int i = 0; i < iend; i++) {
                appList.add(((ResolveInfo) resolveInfos.get(i)).activityInfo.applicationInfo.packageName);
            }
            return appList.toString();
        }
    }

    class OfferwallWebViewClient extends WebViewClient {
        OfferwallWebViewClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String _url) {
            String url = _url.trim();
            OfferwallDialog.logger.d("shouldOverrideUrlLoading=" + _url);
            if (url.startsWith(WebClient.INTENT_PROTOCOL_START_HTTP) || url.startsWith("https")) {
                OfferwallDialog.this.webView.loadUrl(url);
                return false;
            }
            String linkUrl = url;
            try {
                int index = linkUrl.indexOf("c2swall");
                if (index >= 0) {
                    int dividedIndex = linkUrl.indexOf("?");
                    if (dividedIndex > 0) {
                        if (linkUrl.substring(index, dividedIndex).endsWith("goBrowser")) {
                            linkUrl = linkUrl.substring(dividedIndex + 1);
                            if (true) {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse(linkUrl));
                                OfferwallDialog.this.activity.startActivity(intent);
                            }
                        } else if (linkUrl.substring(index, dividedIndex).endsWith("setRewardFlag")) {
                            OfferwallDialog.this.offerwall.setRewardFlag(Integer.parseInt(linkUrl.substring(dividedIndex + 1)));
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
            if (!OfferwallDialog.this.spinner.isShowing()) {
                OfferwallDialog.this.spinner.show();
            }
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            OfferwallDialog.this.spinner.dismiss();
        }
    }

    public OfferwallDialog(Offerwall offerwall, Activity _activity, String url, Bitmap _bitmapPhone, Bitmap _bitmapTablet) {
        super(_activity, 16973841);
        this.offerwall = offerwall;
        this.activity = _activity;
        logger = LoggerGroup.createLogger(Constants.TAG, this.activity);
        setCancelable(true);
        setOnKeyListener(new OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (event.getAction() != 1) {
                    return false;
                }
                if (keyCode != 4) {
                    return true;
                }
                OfferwallDialog.this.close();
                return true;
            }
        });
        setCanceledOnTouchOutside(false);
        this.targetURL = url;
        this.bitmapPhone = _bitmapPhone;
        this.bitmapTablet = _bitmapTablet;
    }

    int DPFromPixel(int pixel) {
        return (int) ((((float) pixel) / OfferwallDefine.DEFAULT_HDIP_DENSITY_SCALE) * this.activity.getResources().getDisplayMetrics().density);
    }

    int PixelFromDP(int DP) {
        return (int) ((((float) DP) / this.activity.getResources().getDisplayMetrics().density) * OfferwallDefine.DEFAULT_HDIP_DENSITY_SCALE);
    }

    double getInch() {
        DisplayMetrics dm = this.activity.getResources().getDisplayMetrics();
        float x = ((float) dm.widthPixels) / dm.xdpi;
        float y = ((float) dm.heightPixels) / dm.ydpi;
        return Math.sqrt((double) ((x * x) + (y * y)));
    }

    public void loadURL() {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                OfferwallDialog.this.webView.loadUrl(OfferwallDialog.this.targetURL);
            }
        });
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                OfferwallDialog.this.dialogLayout = new FrameLayout(OfferwallDialog.this.activity);
                OfferwallDialog.this.dialogLayout.setLayoutParams(new LayoutParams(-1, -1));
                OfferwallDialog.this.dialogLayout.setPadding(0, 0, 0, 0);
                OfferwallDialog.this.webView = new WebView(OfferwallDialog.this.activity);
                OfferwallDialog.this.webView.setWebViewClient(new OfferwallWebViewClient());
                OfferwallDialog.this.webView.setLayoutParams(new LayoutParams(-1, -1));
                OfferwallDialog.this.webView.getSettings().setSupportMultipleWindows(false);
                OfferwallDialog.this.webView.getSettings().setDefaultTextEncodingName("UTF-8");
                OfferwallDialog.this.webView.getSettings().setJavaScriptEnabled(true);
                OfferwallDialog.this.webView.addJavascriptInterface(new OfferwallBridge(OfferwallDialog.this.activity), "OfferwallListFilterManager");
                OfferwallDialog.this.webView.setVerticalScrollbarOverlay(true);
                OfferwallDialog.this.loadURL();
                double inch = OfferwallDialog.this.getInch();
                if (6.5d < inch) {
                    OfferwallDialog.this.closeWidth = (int) TypedValue.applyDimension(1, 24.0f, OfferwallDialog.this.activity.getResources().getDisplayMetrics());
                    OfferwallDialog.this.closeHeight = (int) TypedValue.applyDimension(1, 24.0f, OfferwallDialog.this.activity.getResources().getDisplayMetrics());
                    OfferwallDialog.this.bitmapClose = Bitmap.createScaledBitmap(OfferwallDialog.this.bitmapTablet, OfferwallDialog.this.closeWidth, OfferwallDialog.this.closeHeight, true);
                } else {
                    OfferwallDialog.this.closeWidth = (int) TypedValue.applyDimension(1, 19.0f, OfferwallDialog.this.activity.getResources().getDisplayMetrics());
                    OfferwallDialog.this.closeHeight = (int) TypedValue.applyDimension(1, 19.0f, OfferwallDialog.this.activity.getResources().getDisplayMetrics());
                    OfferwallDialog.this.bitmapClose = Bitmap.createScaledBitmap(OfferwallDialog.this.bitmapPhone, OfferwallDialog.this.closeWidth, OfferwallDialog.this.closeHeight, true);
                }
                OfferwallDialog.this.closeButton = new ImageView(OfferwallDialog.this.activity);
                OfferwallDialog.this.closeButton.setImageBitmap(OfferwallDialog.this.bitmapClose);
                OfferwallDialog.this.closeButton.setAdjustViewBounds(true);
                OfferwallDialog.this.closeButton.setScaleType(ScaleType.FIT_XY);
                OfferwallDialog.this.closeButton.setBackgroundColor(0);
                OfferwallDialog.this.closeButton.setPadding(OfferwallDialog.this.DPFromPixel(20), OfferwallDialog.this.DPFromPixel(20), OfferwallDialog.this.DPFromPixel(20), OfferwallDialog.this.DPFromPixel(20));
                OfferwallDialog.this.closeButton.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        OfferwallDialog.this.close();
                    }
                });
                OfferwallDialog.this.closeButton.setLayoutParams(new LayoutParams(-2, -2));
                LinearLayout closeLayout = new LinearLayout(OfferwallDialog.this.activity);
                LinearLayout.LayoutParams closeLayoutParam = new LinearLayout.LayoutParams(-1, -2);
                closeLayout.setGravity(53);
                if (6.5d < inch) {
                    closeLayoutParam.setMargins(0, OfferwallDialog.this.DPFromPixel(12), OfferwallDialog.this.DPFromPixel(20), 0);
                } else {
                    closeLayoutParam.setMargins(0, OfferwallDialog.this.DPFromPixel(11), OfferwallDialog.this.DPFromPixel(12), 0);
                }
                closeLayout.setLayoutParams(closeLayoutParam);
                closeLayout.addView(OfferwallDialog.this.closeButton);
                OfferwallDialog.this.dialogLayout.addView(OfferwallDialog.this.webView);
                OfferwallDialog.this.dialogLayout.addView(closeLayout);
                OfferwallDialog.this.spinner = new ProgressDialog(OfferwallDialog.this.activity);
                OfferwallDialog.this.spinner.requestWindowFeature(1);
                OfferwallDialog.this.spinner.setMessage("Loading...");
                OfferwallDialog.this.openAnimation = new TranslateAnimation(0.0f, 0.0f, (float) (OfferwallDialog.this.activity.getResources().getDisplayMetrics().heightPixels * -1), 0.0f);
                OfferwallDialog.this.openAnimation.setDuration(200);
                OfferwallDialog.this.closeAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (OfferwallDialog.this.activity.getResources().getDisplayMetrics().heightPixels * -1));
                OfferwallDialog.this.closeAnimation.setDuration(200);
                OfferwallDialog.this.closeAnimation.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        OfferwallDialog.this.offerwall.closeDialog();
                        OfferwallDialog.this.destroy();
                    }
                });
                OfferwallDialog.this.setContentView(OfferwallDialog.this.dialogLayout);
            }
        });
    }

    public boolean onSearchRequested() {
        return false;
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
        logger.d("show offerwall dialog");
        super.show();
        if (!this.openAnimation.hasStarted()) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    OfferwallDialog.this.dialogLayout.startAnimation(OfferwallDialog.this.openAnimation);
                }
            });
        }
    }

    public void close() {
        logger.d("close offerwall dialog");
        if (!this.closeAnimation.hasStarted()) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    OfferwallDialog.this.dialogLayout.startAnimation(OfferwallDialog.this.closeAnimation);
                }
            });
        }
    }
}
