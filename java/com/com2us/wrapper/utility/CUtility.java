package com.com2us.wrapper.utility;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import com.com2us.module.mercury.MercuryDefine;
import com.com2us.peppermint.PeppermintURL;
import com.com2us.wrapper.common.CCommonAPI;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.function.CResource;
import com.com2us.wrapper.function.CResource.EResourcePathType;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperData;
import com.com2us.wrapper.kernel.CWrapperKernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map.Entry;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public class CUtility extends CWrapper {
    private static boolean f = false;
    private boolean a = false;
    private Activity b = null;
    private String c = null;
    private SharedPreferences d;
    private HashMap<String, byte[]> e = new HashMap();
    private final int g = 120;
    private final int h = 160;
    private final int i = 213;
    private final int j = 240;
    private final int k = MercuryDefine.PORTRAIT_SIZE_WIDTH;
    private final int l = 480;

    class a extends WebViewClient {
        ProgressDialog a = null;
        Activity b = null;
        final /* synthetic */ CUtility c;

        public a(CUtility cUtility, Activity activity) {
            this.c = cUtility;
            this.b = activity;
        }

        public void a(Context context, String str) {
            a(context, str, "com.google.android.youtube");
        }

        public void a(Context context, String str, String str2) {
            try {
                Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                if (b(context, str2)) {
                    intent.setPackage(str2);
                }
                context.startActivity(intent);
            } catch (Exception e) {
                context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
            }
        }

        public boolean b(Context context, String str) {
            try {
                context.getPackageManager().getPackageInfo(str, 1);
                return true;
            } catch (NameNotFoundException e) {
                return false;
            }
        }

        public void onLoadResource(WebView webView, String str) {
            if (this.a == null) {
                this.a = new ProgressDialog(this.b);
                this.a.setMessage("Loading...");
                this.a.setCancelable(true);
                this.a.show();
            }
            super.onLoadResource(webView, str);
        }

        public void onPageFinished(WebView webView, String str) {
            webView.loadUrl("javascript:var iframes = document.getElementsByTagName('iframe');for (var i = 0, l = iframes.length; i < l; i++) {   var iframe = iframes[i],   a = document.createElement('a');   a.setAttribute('href', iframe.src);   d = document.createElement('div');   d.style.width = iframe.offsetWidth + 'px';   d.style.height = iframe.offsetHeight + 'px';   d.style.top = iframe.offsetTop + 'px';   d.style.left = iframe.offsetLeft + 'px';   d.style.position = 'absolute';   d.style.opacity = '0';   d.style.filter = 'alpha(opacity=0)';   d.style.background = 'black';   a.appendChild(d);   iframe.offsetParent.appendChild(a);}");
            super.onPageFinished(webView, str);
            if (this.a != null && this.a.isShowing()) {
                this.a.dismiss();
            }
            webView.requestFocus();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.startsWith("vnd.youtube:")) {
                if (str.indexOf("?") <= 0) {
                    return true;
                }
                this.b.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(String.format("http://www.youtube.com/watch?v=%s", new Object[]{str.substring("vnd.youtube:".length(), r2)}))));
                return true;
            } else if (str.startsWith(WebClient.INTENT_PROTOCOL_START_MARKET)) {
                this.b.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                return true;
            } else if (!Uri.parse(str).getHost().contains("youtube.com")) {
                return false;
            } else {
                a(this.b, str);
                return true;
            }
        }
    }

    public CUtility(Activity activity, String str) {
        this.b = activity;
        this.c = str;
        b();
    }

    private int a(String str) {
        return str.indexOf("APv3") == 0 ? 3 : str.indexOf("APv2") == 0 ? 2 : 1;
    }

    private void a(byte[] bArr) {
        try {
            String str = new String(bArr, this.c);
            Editor edit = this.b.getSharedPreferences("AppProperty", 0).edit();
            edit.remove(str);
            edit.commit();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void b() {
        for (Entry entry : this.b.getSharedPreferences("AppProperty", 0).getAll().entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            byte[] decrypt;
            Object decrypt2;
            switch (a(str)) {
                case o.b /*2*/:
                    try {
                        decrypt = CCommonAPI.decrypt("AES", CFunction.getAndroidId(), CCommonAPI.decodeBase64(str.substring("APv2".length(), str.length() - 1)));
                        decrypt2 = CCommonAPI.decrypt("AES", CFunction.getAndroidId(), CCommonAPI.decodeBase64(str2.substring(0, str2.length() - 1)));
                        b(decrypt);
                        setAppProperty(decrypt, decrypt2);
                        this.e.put(new String(decrypt, this.c), decrypt2);
                        break;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        break;
                    }
                case o.c /*3*/:
                    try {
                        decrypt = CCommonAPI.decrypt("AES", CFunction.getAndroidId(), CCommonAPI.decodeBase64(str.substring("APv3".length())));
                        this.e.put(new String(decrypt, this.c), CCommonAPI.decrypt("AES", CFunction.getAndroidId(), CCommonAPI.decodeBase64(str2)));
                        break;
                    } catch (UnsupportedEncodingException e2) {
                        e2.printStackTrace();
                        break;
                    }
                default:
                    try {
                        decrypt = str.getBytes(this.c);
                        decrypt2 = str2.getBytes(this.c);
                        a(decrypt);
                        setAppProperty(decrypt, decrypt2);
                        this.e.put(new String(decrypt, this.c), decrypt2);
                        break;
                    } catch (UnsupportedEncodingException e22) {
                        e22.printStackTrace();
                        break;
                    }
            }
        }
    }

    private void b(byte[] bArr) {
        try {
            this.e.remove(new String(bArr, this.c));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str = "APv2" + CCommonAPI.encodeBase64toString(CCommonAPI.encrypt("AES", CFunction.getAndroidId(), bArr));
        Editor edit = this.b.getSharedPreferences("AppProperty", 0).edit();
        edit.remove(str + "\n");
        edit.remove(str + " ");
        edit.commit();
    }

    public static native void nativeWebviewCB(int i);

    public int Shortcut(int i, String str) throws IOException {
        int i2 = 48;
        PackageManager packageManager = this.b.getPackageManager();
        if (i == 0) {
            this.d = this.b.getSharedPreferences("WrapperShortCreate", 0);
            if (!this.d.getBoolean("WrapperShortCreate", true)) {
                return 0;
            }
            Editor edit = this.d.edit();
            edit.putBoolean("WrapperShortCreate", false);
            edit.commit();
        } else if (i == 3) {
            this.d = this.b.getSharedPreferences("WrapperShortCreate", 0);
            boolean z = this.d.getBoolean("WrapperShortCreate", true);
            Editor edit2 = this.d.edit();
            if (z) {
                return Shortcut(0, str);
            }
            edit2.putBoolean("WrapperShortCreate", true);
            edit2.commit();
            int Shortcut = Shortcut(1, str);
            if (Shortcut < 0) {
                return Shortcut;
            }
            Shortcut = Shortcut(0, str);
            return Shortcut >= 0 ? 0 : Shortcut;
        }
        if (packageManager.checkPermission("com.android.launcher.permission.INSTALL_SHORTCUT", this.b.getPackageName()) == -1 || packageManager.checkPermission("com.android.launcher.permission.UNINSTALL_SHORTCUT", this.b.getPackageName()) == -1) {
            return -1;
        }
        ApplicationInfo applicationInfo;
        InputStream open;
        Intent intent;
        try {
            applicationInfo = packageManager.getApplicationInfo(this.b.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        String str2 = (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "(unknown)");
        Object intent2 = new Intent("android.intent.action.MAIN");
        intent2.setClassName(this.b, this.b.getClass().getName());
        new Options().inSampleSize = 4;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.b.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (CFunction.getSystemVersionCode() < 11) {
            switch (displayMetrics.densityDpi) {
                case 120:
                    i2 = 36;
                    break;
                case 160:
                    break;
                case 213:
                    i2 = 85;
                    break;
                case 240:
                    i2 = 72;
                    break;
                case MercuryDefine.PORTRAIT_SIZE_WIDTH /*320*/:
                    i2 = 96;
                    break;
                case 480:
                    i2 = 144;
                    break;
                default:
                    break;
            }
        }
        i2 = ((ActivityManager) this.b.getSystemService("activity")).getLauncherLargeIconSize();
        if (CResource.getFullPathName(str, EResourcePathType.ASSETS) != null) {
            open = this.b.getAssets().open(CResource.getFullPathName(str, EResourcePathType.ASSETS));
        } else if (CResource.getFullPathName(str, EResourcePathType.SDCARD) != null) {
            open = this.b.getAssets().open(CResource.getFullPathName(str, EResourcePathType.SDCARD));
        } else {
            File file = new File(str);
            if (file.exists()) {
                open = new FileInputStream(file);
            } else {
                edit = this.d.edit();
                edit.remove("WrapperShortCreate");
                edit.commit();
                return -1;
            }
        }
        Bitmap decodeStream = BitmapFactory.decodeStream(open);
        Bitmap createScaledBitmap = Bitmap.createScaledBitmap(decodeStream, (decodeStream.getWidth() * decodeStream.getDensity()) / displayMetrics.densityDpi, (decodeStream.getHeight() * decodeStream.getDensity()) / displayMetrics.densityDpi, true);
        createScaledBitmap.setDensity(displayMetrics.densityDpi);
        Object createScaledBitmap2 = Bitmap.createScaledBitmap(createScaledBitmap, i2, i2, true);
        if (i == 0) {
            intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            intent.putExtra("duplicate", false);
        } else if (i == 1) {
            intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        } else {
            intent = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
            intent2.addCategory("android.intent.category.LAUNCHER");
        }
        intent.putExtra("android.intent.extra.shortcut.INTENT", intent2);
        intent.putExtra("android.intent.extra.shortcut.NAME", str2);
        intent.putExtra("android.intent.extra.shortcut.ICON", createScaledBitmap2);
        this.b.sendBroadcast(intent);
        createScaledBitmap2.recycle();
        createScaledBitmap.recycle();
        decodeStream.recycle();
        return 0;
    }

    public byte[] getAppProperty(byte[] bArr) {
        try {
            return (byte[]) this.e.get(new String(bArr, this.c));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int makeWebView(final String str, final int i, final int i2) {
        this.b.runOnUiThread(new Runnable(this) {
            final /* synthetic */ CUtility d;

            public void run() {
                RelativeLayout relativeLayout = (RelativeLayout) this.d.b.findViewById(CResource.R("R.id.WebViewTouchLayout"));
                RelativeLayout relativeLayout2 = (RelativeLayout) this.d.b.findViewById(CResource.R("R.id.WebViewLayout"));
                WebView webView = (WebView) this.d.b.findViewById(CResource.R("R.id.webView"));
                ImageButton imageButton = (ImageButton) this.d.b.findViewById(CResource.R("R.id.webViewCancel"));
                if (relativeLayout == null || relativeLayout2 == null || webView == null || imageButton == null) {
                    Log.e("WRAPPER", "WebView Warning - Please merge to latest version 'res/layout/main.xml' ");
                    return;
                }
                String str;
                relativeLayout.setVisibility(0);
                webView.setVisibility(0);
                relativeLayout.setClickable(true);
                webView.setOnKeyListener(new OnKeyListener(this) {
                    final /* synthetic */ AnonymousClass2 a;

                    {
                        this.a = r1;
                    }

                    public boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (i == 4 && keyEvent.getAction() == 0) {
                            return true;
                        }
                        if (i != 4 || keyEvent.getAction() != 1) {
                            return false;
                        }
                        RelativeLayout relativeLayout = (RelativeLayout) this.a.d.b.findViewById(CResource.R("R.id.WebViewTouchLayout"));
                        ((WebView) this.a.d.b.findViewById(CResource.R("R.id.webView"))).setVisibility(8);
                        relativeLayout.setVisibility(8);
                        return true;
                    }
                });
                if (!CUtility.f) {
                    relativeLayout.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
                        final /* synthetic */ AnonymousClass2 a;

                        {
                            this.a = r1;
                        }

                        public void onGlobalLayout() {
                            CUtility.nativeWebviewCB(((RelativeLayout) this.a.d.b.findViewById(CResource.R("R.id.WebViewTouchLayout"))).getVisibility() == 0 ? 1 : 0);
                        }
                    });
                    CUtility.f = true;
                }
                CWrapperData instance = CWrapperData.getInstance();
                LayoutParams layoutParams;
                if (i == instance.getDisplayWidth() || i2 == instance.getDisplayHeight()) {
                    layoutParams = new RelativeLayout.LayoutParams(i, i2);
                    layoutParams.addRule(13);
                    relativeLayout2.setLayoutParams(layoutParams);
                } else {
                    layoutParams = new RelativeLayout.LayoutParams(i + 2, i2 + 2);
                    layoutParams.addRule(13);
                    relativeLayout2.setLayoutParams(layoutParams);
                    relativeLayout2.setBackgroundColor(-1);
                    relativeLayout2.setPadding(1, 1, 1, 1);
                }
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setDomStorageEnabled(true);
                webView.getSettings().setBuiltInZoomControls(true);
                webView.getSettings().setAllowFileAccess(true);
                webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                webView.getSettings().setPluginState(PluginState.ON);
                webView.getSettings().setSupportMultipleWindows(true);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.getSettings().setUseWideViewPort(true);
                webView.setScrollBarStyle(33554432);
                webView.getSettings().setRenderPriority(RenderPriority.HIGH);
                webView.getSettings().setCacheMode(2);
                webView.clearHistory();
                webView.clearFormData();
                webView.clearCache(true);
                imageButton.setClickable(true);
                if (CResource.R("R.drawable.webview_close_button") != 0) {
                    imageButton.setImageResource(CResource.R("R.drawable.webview_close_button"));
                }
                int i = i / 10 > 96 ? 96 : i / 10;
                int i2 = i2 / 10 > 54 ? 54 : i2 / 10;
                if (i < i2) {
                    i2 = i;
                    i = (i * 3) / 4;
                } else {
                    int i3 = i2;
                    i2 = (i2 * 4) / 3;
                    i = i3;
                }
                imageButton.getLayoutParams().width = i2;
                imageButton.getLayoutParams().height = i;
                imageButton.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ AnonymousClass2 a;

                    {
                        this.a = r1;
                    }

                    public void onClick(View view) {
                        RelativeLayout relativeLayout = (RelativeLayout) this.a.d.b.findViewById(CResource.R("R.id.WebViewTouchLayout"));
                        ((WebView) this.a.d.b.findViewById(CResource.R("R.id.webView"))).setVisibility(8);
                        relativeLayout.setVisibility(8);
                    }
                });
                String str2 = str;
                if (str.startsWith("vnd.youtube:") || str.startsWith(WebClient.INTENT_PROTOCOL_START_MARKET)) {
                    relativeLayout.setVisibility(8);
                    str = str2;
                } else {
                    if (!(str.matches("(?i).*http://.*") || str.matches("(?i).*https://.*"))) {
                        str = PeppermintURL.PEPPERMINT_HTTP + str;
                    }
                    str = str2;
                }
                webView.loadUrl(str);
                webView.setWebViewClient(new a(this.d, this.d.b));
            }
        });
        return 0;
    }

    public void removeAppProperty(byte[] bArr) {
        try {
            this.e.remove(new String(bArr, this.c));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str = "APv3" + CCommonAPI.encodeBase64toString(CCommonAPI.encrypt("AES", CFunction.getAndroidId(), bArr));
        Editor edit = this.b.getSharedPreferences("AppProperty", 0).edit();
        edit.remove(str);
        edit.commit();
    }

    public void setAppProperty(byte[] bArr, byte[] bArr2) {
        try {
            this.e.put(new String(bArr, this.c), bArr2);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String str = "APv3" + CCommonAPI.encodeBase64toString(CCommonAPI.encrypt("AES", CFunction.getAndroidId(), bArr));
        String encodeBase64toString = CCommonAPI.encodeBase64toString(CCommonAPI.encrypt("AES", CFunction.getAndroidId(), bArr2));
        Editor edit = this.b.getSharedPreferences("AppProperty", 0).edit();
        edit.putString(str, encodeBase64toString);
        edit.commit();
    }

    public void showDialogAndExit(int i) {
        String string;
        String str = null;
        switch (i) {
            case g.a /*0*/:
                string = this.b.getResources().getString(CResource.R("R.string.wrapper_utility_exit_type_hacked_title"));
                str = this.b.getResources().getString(CResource.R("R.string.wrapper_utility_exit_type_hacked_message"));
                break;
            case o.a /*1*/:
                string = this.b.getResources().getString(CResource.R("R.string.wrapper_utility_exit_type_rooted_title"));
                str = this.b.getResources().getString(CResource.R("R.string.wrapper_utility_exit_type_rooted_message"));
                break;
            default:
                string = null;
                break;
        }
        showDialogAndExit(string, str);
    }

    public void showDialogAndExit(final String str, final String str2) {
        if (!this.a) {
            this.a = true;
            this.b.runOnUiThread(new Runnable(this) {
                final /* synthetic */ CUtility c;

                public void run() {
                    final AlertDialog create = new Builder(this.c.b).setIcon(CResource.R("R.drawable.alert_dialog_icon")).setTitle(str).setMessage(str2).setCancelable(false).create();
                    create.show();
                    new Thread(this) {
                        final /* synthetic */ AnonymousClass1 b;

                        public void run() {
                            try {
                                Thread.sleep(5000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            create.dismiss();
                            CWrapperKernel.onExitApplication();
                        }
                    }.start();
                }
            });
        }
    }
}
