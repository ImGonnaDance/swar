package com.com2us.smon.common;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebView.HitTestResult;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import jp.co.cyberz.fox.a.a.i;

public class Com2usWebView extends Activity {
    public static final String SAVE_IMAGE_PATH = (Environment.getExternalStorageDirectory() + "/Download/");
    public String SAVE_IMAGE_NAME = i.a;
    private final int kTag_WEBVIEW_MEDIA = 3;
    private final int kTag_WEBVIEW_NONE = 0;
    private final int kTag_WEBVIEW_NORMAL = 1;
    private final int kTag_WEBVIEW_SUMMON_PERCENT = 2;
    private Context mContext = null;
    private String mCurrentUrl = i.a;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1004) {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(Com2usWebView.this.mHitResult.getExtra()).openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    Bitmap image = BitmapFactory.decodeStream(connection.getInputStream());
                    SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
                    Date nowDate = new Date();
                    Com2usWebView.this.SAVE_IMAGE_NAME = "img_" + sdFormat.format(nowDate) + ".png";
                    Com2usWebView.this.SaveBitmapToFileCache(image, Com2usWebView.SAVE_IMAGE_PATH, Com2usWebView.this.SAVE_IMAGE_NAME);
                    Com2usWebView.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Com2usWebView.this.mContext, Com2usWebView.SAVE_IMAGE_PATH + Com2usWebView.this.SAVE_IMAGE_NAME, 0).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private HitTestResult mHitResult = null;
    private String mWebUrl = i.a;
    private ImageButton myBtnBack;
    private ImageButton myBtnExit;
    private ImageButton myBtnForward;
    private ImageButton myBtnRefresh;
    private ImageButton myBtnStop;
    private Com2usChromeClient myChromeClient;
    private RelativeLayout myRelativeWebview;
    private LinearLayout myToolbar;
    private WebView myWebView;
    private int nType = 0;
    OnClickListener onclick = new OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_webview_back /*2131427361*/:
                    Com2usWebView.this.myWebView.goBack();
                    Com2usWebView.nativePlaySoundWebView(1);
                    return;
                case R.id.btn_webview_forward /*2131427362*/:
                    Com2usWebView.this.myWebView.goForward();
                    Com2usWebView.nativePlaySoundWebView(1);
                    return;
                case R.id.btn_webview_refresh /*2131427363*/:
                    Com2usWebView.this.myWebView.reload();
                    Com2usWebView.nativePlaySoundWebView(1);
                    return;
                case R.id.btn_webview_stop /*2131427364*/:
                    Com2usWebView.this.myWebView.stopLoading();
                    Com2usWebView.nativePlaySoundWebView(1);
                    return;
                case R.id.btn_webview_exit /*2131427365*/:
                    Com2usWebView.nativePlaySoundWebView(2);
                    Com2usWebView.this.finish();
                    return;
                case R.id.btn_webview_summon_exit /*2131427373*/:
                    Com2usWebView.nativePlaySoundWebView(2);
                    Com2usWebView.this.finish();
                    return;
                default:
                    return;
            }
        }
    };

    class WebClient extends WebViewClient {
        public static final String GOOGLE_PLAY_STORE_PREFIX = "market://details?id=";
        public static final String INTENT_PROTOCOL_END = ";end;";
        public static final String INTENT_PROTOCOL_INTENT = "#Intent;";
        public static final String INTENT_PROTOCOL_INTENT_PACKAGE = "#Intent;package=";
        public static final String INTENT_PROTOCOL_START = "intent:";
        public static final String INTENT_PROTOCOL_START_HTTP = "http";
        public static final String INTENT_PROTOCOL_START_MARKET = "market:";
        public static final String MERCURY_GOBROWSER_PREFIX = "c2smercury://gobrowser?";

        WebClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!(Com2usWebView.this.mCurrentUrl == null || url == null)) {
                if (url.equals(Com2usWebView.this.mCurrentUrl)) {
                    view.goBack();
                    return true;
                }
            }
            Com2usWebView.this.mCurrentUrl = url;
            if (url.startsWith(MERCURY_GOBROWSER_PREFIX)) {
                String str = url;
                view.loadUrl(str.substring(url.indexOf(MERCURY_GOBROWSER_PREFIX) + MERCURY_GOBROWSER_PREFIX.length(), url.length()));
                return true;
            }
            if (url.startsWith(INTENT_PROTOCOL_START_HTTP)) {
                view.loadUrl(url);
                return true;
            }
            Intent intent;
            if (url.startsWith(INTENT_PROTOCOL_START)) {
                int customUrlStartIndex = INTENT_PROTOCOL_START.length();
                int customUrlEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT);
                int customUrlPackageEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT_PACKAGE);
                if (customUrlEndIndex < 0) {
                    return false;
                }
                try {
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(url.substring(customUrlStartIndex, customUrlEndIndex)));
                    intent.addFlags(268435456);
                    Com2usWebView.this.mContext.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    int packageStartIndex;
                    if (customUrlPackageEndIndex != -1) {
                        packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT_PACKAGE.length();
                    } else {
                        packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length();
                    }
                    int packageEndIndex = url.indexOf(INTENT_PROTOCOL_END);
                    if (packageEndIndex < 0) {
                        packageEndIndex = url.length();
                    }
                    intent = new Intent("android.intent.action.VIEW", Uri.parse(new StringBuilder(GOOGLE_PLAY_STORE_PREFIX).append(url.substring(packageStartIndex, packageEndIndex)).toString()));
                    intent.addFlags(268435456);
                    Com2usWebView.this.mContext.startActivity(intent);
                }
                return true;
            }
            if (url.startsWith(INTENT_PROTOCOL_START_MARKET)) {
                String pName = i.a;
                int nStart = url.indexOf("id=");
                int nEnd = url.indexOf("&", nStart);
                nStart += 3;
                if (nStart != -1 && nStart < url.length()) {
                    if (nEnd == -1) {
                        pName = url.substring(nStart);
                    } else {
                        pName = url.substring(nStart, nEnd);
                    }
                    if (Com2usWebView.this.isPackageInstalled(pName, Com2usWebView.this.mContext)) {
                        return true;
                    }
                }
            }
            try {
                intent = new Intent("android.intent.action.VIEW", Uri.parse(url));
                intent.addFlags(268435456);
                Com2usWebView.this.mContext.startActivity(intent);
                return true;
            } catch (ActivityNotFoundException e2) {
                return false;
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (Com2usWebView.this.nType == 1 || Com2usWebView.this.nType == 3) {
                Com2usWebView.this.setButtonAlpha(Com2usWebView.this.myBtnRefresh, false);
                Com2usWebView.this.setButtonAlpha(Com2usWebView.this.myBtnStop, true);
            }
        }

        public void onPageFinished(WebView view, String url) {
            if (Com2usWebView.this.nType == 1 || Com2usWebView.this.nType == 3) {
                Com2usWebView.this.setButtonAlpha(Com2usWebView.this.myBtnBack, view.canGoBack());
                Com2usWebView.this.setButtonAlpha(Com2usWebView.this.myBtnForward, view.canGoForward());
                Com2usWebView.this.setButtonAlpha(Com2usWebView.this.myBtnRefresh, true);
                Com2usWebView.this.setButtonAlpha(Com2usWebView.this.myBtnStop, false);
            }
        }
    }

    public static native void nativePlaySoundWebView(int i);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        Intent intent = getIntent();
        this.mWebUrl = intent.getStringExtra("WEBURL");
        this.nType = intent.getIntExtra("WEBTYPE", 1);
        InitResource();
        LoadURL(this.mWebUrl);
    }

    public void InitResource() {
        WebSettings set;
        if (this.nType == 1 || this.nType == 3) {
            setContentView(R.layout.com2uswebview);
            this.myToolbar = (LinearLayout) findViewById(R.id.view_linearlayout);
            this.myRelativeWebview = (RelativeLayout) findViewById(R.id.view_relativelayout_webview);
            this.myBtnBack = (ImageButton) findViewById(R.id.btn_webview_back);
            this.myBtnForward = (ImageButton) findViewById(R.id.btn_webview_forward);
            this.myBtnRefresh = (ImageButton) findViewById(R.id.btn_webview_refresh);
            this.myBtnStop = (ImageButton) findViewById(R.id.btn_webview_stop);
            this.myBtnExit = (ImageButton) findViewById(R.id.btn_webview_exit);
            this.myBtnBack.setOnClickListener(this.onclick);
            this.myBtnForward.setOnClickListener(this.onclick);
            this.myBtnRefresh.setOnClickListener(this.onclick);
            this.myBtnStop.setOnClickListener(this.onclick);
            this.myBtnExit.setOnClickListener(this.onclick);
            this.myRelativeWebview.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale));
            this.myChromeClient = new Com2usChromeClient(this);
            this.myWebView = (WebView) findViewById(R.id.view_webview);
            this.myWebView.setWebChromeClient(this.myChromeClient);
            this.myWebView.setWebViewClient(new WebClient());
            this.myWebView.getSettings().setJavaScriptEnabled(true);
            set = this.myWebView.getSettings();
            set.setJavaScriptEnabled(true);
            set.setJavaScriptCanOpenWindowsAutomatically(true);
            set.setSupportMultipleWindows(true);
        } else if (this.nType == 2) {
            setContentView(R.layout.com2uswebview_summon);
            this.myToolbar = (LinearLayout) findViewById(R.id.view_linearlayout_summon);
            this.myRelativeWebview = (RelativeLayout) findViewById(R.id.view_relativelayout_percent_webview);
            this.myBtnBack = (ImageButton) findViewById(R.id.btn_webview_summon_back);
            this.myBtnForward = (ImageButton) findViewById(R.id.btn_webview_summon_forward);
            this.myBtnRefresh = (ImageButton) findViewById(R.id.btn_webview_summon_refresh);
            this.myBtnStop = (ImageButton) findViewById(R.id.btn_webview_summon_stop);
            this.myBtnExit = (ImageButton) findViewById(R.id.btn_webview_summon_exit);
            this.myBtnExit.setOnClickListener(this.onclick);
            this.myRelativeWebview.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_scale));
            this.myChromeClient = new Com2usChromeClient(this);
            this.myWebView = (WebView) findViewById(R.id.view_webview_summon);
            this.myWebView.setWebChromeClient(this.myChromeClient);
            this.myWebView.setWebViewClient(new WebClient());
            this.myWebView.getSettings().setJavaScriptEnabled(true);
            set = this.myWebView.getSettings();
            set.setJavaScriptEnabled(true);
            set.setJavaScriptCanOpenWindowsAutomatically(true);
            set.setSupportMultipleWindows(true);
        }
        registerForContextMenu(this.myWebView);
    }

    public void LoadURL(String url) {
        this.myWebView.loadUrl(url);
    }

    private boolean isPackageInstalled(String packagename, Context context) {
        try {
            context.getPackageManager().getPackageInfo(packagename, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public void setButtonAlpha(ImageButton button, boolean isEnable) {
        if (VERSION.SDK_INT < 11) {
            AlphaAnimation animation;
            if (isEnable) {
                animation = new AlphaAnimation(0.35f, 1.0f);
                animation.setDuration(0);
                animation.setFillAfter(true);
                button.startAnimation(animation);
                return;
            }
            animation = new AlphaAnimation(1.0f, 0.35f);
            animation.setDuration(0);
            animation.setFillAfter(true);
            button.startAnimation(animation);
        } else if (isEnable) {
            button.setAlpha(1.0f);
            button.setEnabled(true);
        } else {
            button.setAlpha(0.35f);
            button.setEnabled(false);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (keyCode == 4 && this.myWebView.canGoBack()) {
                nativePlaySoundWebView(1);
                this.myWebView.goBack();
                return true;
            }
            nativePlaySoundWebView(2);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        this.mHitResult = this.myWebView.getHitTestResult();
        OnMenuItemClickListener handler = new OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (VERSION.SDK_INT < 11) {
                    Com2usWebView.this.mHandler.sendEmptyMessage(1004);
                } else {
                    AsyncTask.execute(new Runnable() {
                        public void run() {
                            try {
                                HttpURLConnection connection = (HttpURLConnection) new URL(Com2usWebView.this.mHitResult.getExtra()).openConnection();
                                connection.setDoInput(true);
                                connection.connect();
                                Bitmap image = BitmapFactory.decodeStream(connection.getInputStream());
                                SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
                                Date nowDate = new Date();
                                Com2usWebView.this.SAVE_IMAGE_NAME = "img_" + sdFormat.format(nowDate) + ".png";
                                Com2usWebView.this.SaveBitmapToFileCache(image, Com2usWebView.SAVE_IMAGE_PATH, Com2usWebView.this.SAVE_IMAGE_NAME);
                                Com2usWebView.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(Com2usWebView.this.mContext, Com2usWebView.SAVE_IMAGE_PATH + Com2usWebView.this.SAVE_IMAGE_NAME, 0).show();
                                    }
                                });
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                return true;
            }
        };
        if (this.mHitResult.getType() == 5 || this.mHitResult.getType() == 8) {
            menu.setHeaderTitle(null);
            menu.add(0, v.getId(), 0, "Save Image").setOnMenuItemClickListener(handler);
        }
    }

    private void SaveBitmapToFileCache(Bitmap bitmap, String strFolderPath, String strFileName) {
        Exception e;
        Throwable th;
        File path = new File(strFolderPath);
        if (!path.exists()) {
            path.mkdirs();
        }
        File fileCacheItem = new File(new StringBuilder(String.valueOf(strFolderPath)).append(strFileName).toString());
        OutputStream out = null;
        try {
            fileCacheItem.createNewFile();
            OutputStream out2 = new FileOutputStream(fileCacheItem);
            try {
                bitmap.compress(CompressFormat.PNG, 100, out2);
                try {
                    out2.close();
                    out = out2;
                } catch (IOException e2) {
                    e2.printStackTrace();
                    out = out2;
                }
            } catch (Exception e3) {
                e = e3;
                out = out2;
                try {
                    e.printStackTrace();
                    try {
                        out.close();
                    } catch (IOException e22) {
                        e22.printStackTrace();
                    }
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        out.close();
                    } catch (IOException e222) {
                        e222.printStackTrace();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                out.close();
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            out.close();
        }
    }

    protected void onStop() {
        super.onStop();
    }

    protected void onPause() {
        super.onPause();
        try {
            Class.forName("android.webkit.WebView").getMethod("onPause", null).invoke(this.myWebView, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        } catch (ClassNotFoundException e5) {
            e5.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        try {
            Class.forName("android.webkit.WebView").getMethod("onResume", null).invoke(this.myWebView, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        } catch (NoSuchMethodException e4) {
            e4.printStackTrace();
        } catch (ClassNotFoundException e5) {
            e5.printStackTrace();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
