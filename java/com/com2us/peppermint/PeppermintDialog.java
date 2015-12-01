package com.com2us.peppermint;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.peppermint.socialextension.PeppermintSocialAction;
import com.com2us.peppermint.socialextension.PeppermintSocialActionType;
import com.com2us.peppermint.socialextension.PeppermintSocialManager;
import com.com2us.peppermint.socialextension.PeppermintSocialPlugin;
import com.com2us.peppermint.socialextension.PeppermintSocialPluginPGSHelper;
import com.com2us.peppermint.util.PeppermintLog;
import com.com2us.peppermint.util.PeppermintResource;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import java.io.File;
import java.util.HashMap;
import jp.co.cyberz.fox.a.a.i;
import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class PeppermintDialog extends Dialog {
    static final LayoutParams a = new LayoutParams(-1, -1);
    private Activity f18a;
    private Uri f19a = null;
    private ImageView f20a;
    private ProgressBar f21a;
    private Dictionary f22a;
    private PeppermintAuthToken f23a = null;
    private PeppermintCallback f24a;
    private a f25a;
    private PeppermintDialogCallback f26a;
    private Object f27a = null;
    private String f28a;
    private boolean f29a;
    private Uri b = null;
    private String f30b;
    private String c;
    private String d;
    private String e = null;
    private String f;

    private class a extends WebView {
        final /* synthetic */ PeppermintDialog a;

        public a(PeppermintDialog peppermintDialog, Context context) {
            this.a = peppermintDialog;
            super(context);
        }

        public void loadUrl(String str) {
            post(new v(this, str));
        }
    }

    private class b extends WebViewClient {
        final /* synthetic */ PeppermintDialog a;

        private b(PeppermintDialog peppermintDialog) {
            this.a = peppermintDialog;
        }

        public void onPageFinished(WebView webView, String str) {
            PeppermintLog.i("PeppermintWebViewClient onPageFinished url=" + str);
            this.a.f18a.setVisibility(8);
            if (this.a.f18a) {
                this.a.f18a;
            }
            CookieSyncManager.getInstance().sync();
            String cookie = CookieManager.getInstance().getCookie(str);
            if (cookie != null) {
                String[] split = cookie.split(";");
                HashMap hashMap = new HashMap();
                String[] strArr = new String[0];
                for (String split2 : split) {
                    String[] split3 = split2.split("=", 2);
                    if (split3[0].startsWith(" ")) {
                        PeppermintLog.i("onPageFinished parts[0] starts with spaceBar and its value is " + split3[0]);
                    }
                    hashMap.put(split3[0], split3[1]);
                }
                LocalStorage.setHashValueWithKey(this.a.f18a, hashMap);
            }
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            PeppermintLog.i("PeppermintWebViewClient onPageStarted url=" + str);
            this.a.f18a.setVisibility(0);
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            PeppermintLog.i("onReceivedError errorCode=" + i + "description=" + str);
            this.a.f18a.runOnUiThread(new z(this, str));
            if (this.a.f18a) {
                this.a.dismiss();
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            try {
                PeppermintLog.i("PeppermintWebViewClient shouldOverrideUrlLoading url=" + str);
                Dictionary queryDictionary = Dictionary.queryDictionary(str);
                if (str.startsWith(PeppermintURL.PEPPERMINT_LAUNCH_APP_URI)) {
                    this.a.a(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_LOGIN_URI)) {
                    this.a.c(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_SEND_SMS_URI)) {
                    this.a.b(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_GUEST_ACQUIRE_UID_URI)) {
                    this.a.o(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_GUEST_BIND_URI)) {
                    this.a.p(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_LOGOUT_URI)) {
                    this.a.d(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_CLOSE_URI)) {
                    this.a.e(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_GET_PICTURE_URI)) {
                    this.a.f(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_OPEN_BROWSER_URI)) {
                    this.a.i(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_GET_ADDRESSBOOK_URI)) {
                    this.a.g(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_GET_PHONENUMBER_URI)) {
                    this.a.h(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_SOCIAL_REQUEST_USER_PROFILE)) {
                    this.a.m(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_SOCIAL_REQUEST_USER_TOKEN)) {
                    this.a.n(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_SOCIAL_REQUEST_FRIENDS)) {
                    this.a.j(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_SOCIAL_IS_AUTHORIZED)) {
                    this.a.k(queryDictionary);
                    return true;
                } else if (str.startsWith(PeppermintURL.PEPPERMINT_SOCIAL_LOGOUT)) {
                    this.a.l(queryDictionary);
                    return true;
                } else if (str.startsWith(WebClient.INTENT_PROTOCOL_START_HTTP) || str.startsWith("https")) {
                    String str2 = "link/download?url=";
                    int indexOf = str.indexOf(str2);
                    if (indexOf != -1) {
                        str2 = str.substring(str2.length() + indexOf, str.length());
                        indexOf = str2.indexOf("m.com2us.com/r?c=");
                        int indexOf2 = str2.indexOf("m.withhive.com/link/s");
                        int indexOf3 = str2.indexOf("m.com2us.com/b?");
                        if (indexOf == -1 && indexOf2 == -1 && indexOf3 == -1) {
                            PeppermintLog.i("PeppermintWebViewClient shouldOverrideUrlLoading downloadURL=" + str2);
                            try {
                                Intent intent = new Intent("android.intent.action.VIEW");
                                intent.setData(Uri.parse(str2));
                                this.a.f18a.startActivity(intent);
                                return true;
                            } catch (ActivityNotFoundException e) {
                                this.a.f18a.runOnUiThread(new w(this));
                            }
                        }
                    }
                    int indexOf4 = str.indexOf("play.google.com/store/apps");
                    indexOf = str.indexOf(".apk");
                    if (indexOf4 == -1 && indexOf == -1) {
                        this.a.f18a.loadUrl(str);
                        return true;
                    }
                    try {
                        r2 = new Intent("android.intent.action.VIEW");
                        r2.setData(Uri.parse(str));
                        this.a.f18a.startActivity(r2);
                        return true;
                    } catch (ActivityNotFoundException e2) {
                        this.a.f18a.runOnUiThread(new x(this));
                        return true;
                    }
                } else if (str.equals("about:blank")) {
                    return false;
                } else {
                    try {
                        r2 = new Intent("android.intent.action.VIEW");
                        r2.setData(Uri.parse(str));
                        this.a.f18a.startActivity(r2);
                        return true;
                    } catch (ActivityNotFoundException e3) {
                        e3.printStackTrace();
                        this.a.f18a.runOnUiThread(new y(this));
                        return true;
                    }
                }
            } catch (JSONException e4) {
                return false;
            }
        }
    }

    public PeppermintDialog(Peppermint peppermint, String str, String str2, boolean z, PeppermintCallback peppermintCallback, PeppermintDialogCallback peppermintDialogCallback) {
        super(peppermint.getMainActivity(), 16973833);
        PeppermintLog.i("PeppermintDialog serverBaseURL=" + str + " subPath=" + str2 + " isViewInvisible=" + z);
        this.f18a = peppermint.getMainActivity();
        this.f28a = str;
        this.f30b = str2;
        this.c = new StringBuilder(String.valueOf(str)).append(str2).toString();
        if (this.f28a.contains("qpyou")) {
            this.d = PeppermintURL.PEPPERMINT_QPYOU_COOKIE_URL;
        } else {
            this.d = PeppermintURL.PEPPERMINT_COOKIE_URL;
        }
        this.f24a = peppermintCallback;
        this.f26a = peppermintDialogCallback;
        this.f29a = z;
        this.f27a = null;
        this.e = null;
        initSubViews();
    }

    private String a() {
        return this.f30b.equals(PeppermintURL.PEPPERMINT_AUTH_PATH) ? PeppermintURL.PEPPERMINT_AUTH_PATH : this.f30b.equals(PeppermintURL.PEPPERMINT_LOGOUT_PATH) ? PeppermintConstant.JSON_KEY_LOGOUT : this.f30b.equals(PeppermintURL.PEPPERMINT_GUEST_ACQUIRE_UID_PATH) ? PeppermintConstant.JSON_KEY_GUEST_ACQUIRE_UID : this.f30b.startsWith(PeppermintURL.PEPPERMINT_GUEST_BIND_PATH) ? PeppermintURL.PEPPERMINT_GUEST_BIND_PATH : PeppermintConstant.JSON_KEY_DIALOG;
    }

    private void m1a() {
        PeppermintLog.i("runCloseCallback");
        if (this.f24a == null) {
            PeppermintLog.i("runCloseCallback closeCallback is null");
            return;
        }
        if (this.f22a != null) {
            PeppermintLog.i("runCloseCallback closeCallback with params");
            this.f24a.run(new PeppermintCallbackJSON().getJSON(a(), this.f22a));
        } else if (this.f27a != null) {
            PeppermintLog.i("runCloseCallback closeCallback with error");
            this.f24a.run(this.e != null ? new PeppermintCallbackJSON().getJSON(a(), PeppermintType.HUB_E_SYSTEM_ERROR, this.e) : new PeppermintCallbackJSON().getJSON(a(), PeppermintType.HUB_E_SYSTEM_ERROR, "system error"));
            this.f27a = null;
        } else {
            PeppermintLog.i("runCloseCallback closeCallback Dialog closed");
            this.f24a.run(new PeppermintCallbackJSON().getJSON(a(), PeppermintType.HUB_E_DIALOG_CLOSE, "Dialog closed."));
        }
        this.f24a = null;
    }

    private void a(Dictionary dictionary) throws JSONException {
        String str;
        Intent intent;
        PeppermintLog.i("handleLaunchAppScheme params=" + dictionary);
        if (dictionary != null && dictionary.has(PeppermintConstant.JSON_KEY_APP_ID)) {
            try {
                str = (String) dictionary.get(PeppermintConstant.JSON_KEY_APP_ID);
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(new StringBuilder(String.valueOf(str)).append("://").toString()));
                this.f18a.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                if (dictionary.has(PeppermintConstant.JSON_KEY_DOWNLOAD_URL)) {
                    try {
                        str = (String) dictionary.get(PeppermintConstant.JSON_KEY_DOWNLOAD_URL);
                        intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(str));
                        this.f18a.startActivity(intent);
                    } catch (ActivityNotFoundException e2) {
                        this.f18a.runOnUiThread(new p(this));
                    }
                }
            }
        }
    }

    private void b() {
        PeppermintLog.i("showWebView");
        getWindow().getDecorView().setVisibility(0);
    }

    private void b(Dictionary dictionary) {
        String str;
        PeppermintLog.i("handleSendSMSScheme params=" + dictionary);
        String str2 = i.a;
        if (dictionary != null && dictionary.has(PeppermintConstant.JSON_KEY_BODY)) {
            try {
                str = (String) dictionary.get(PeppermintConstant.JSON_KEY_BODY);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setData(Uri.parse("smsto:"));
            intent.putExtra("sms_body", str);
            this.f18a.startActivity(intent);
        }
        str = str2;
        try {
            Intent intent2 = new Intent();
            intent2.setAction("android.intent.action.VIEW");
            intent2.setData(Uri.parse("smsto:"));
            intent2.putExtra("sms_body", str);
            this.f18a.startActivity(intent2);
        } catch (ActivityNotFoundException e2) {
            this.f18a.runOnUiThread(new q(this));
        }
    }

    private void c() {
        PeppermintLog.i("load serverURL" + this.c);
        if (a().equals(PeppermintURL.PEPPERMINT_AUTH_PATH) || a().equals(PeppermintConstant.JSON_KEY_GUEST_ACQUIRE_UID)) {
            try {
                Class.forName("com.com2us.peppermint.socialextension.PeppermintSocialPluginPGSHelper");
                PeppermintSocialPluginPGSHelper peppermintSocialPluginPGSHelper = PeppermintSocialPluginPGSHelper.get_instance();
                if (!(peppermintSocialPluginPGSHelper == null || !peppermintSocialPluginPGSHelper.getIsPGS().booleanValue() || peppermintSocialPluginPGSHelper.getUid() == null)) {
                    String str = "&uid=" + peppermintSocialPluginPGSHelper.getUid() + "&name=" + peppermintSocialPluginPGSHelper.getName() + "&email=" + peppermintSocialPluginPGSHelper.getEmail() + "&logouthistory=" + peppermintSocialPluginPGSHelper.getLogoutHistory() + "&gaccountnum=" + peppermintSocialPluginPGSHelper.getAccountNum();
                    this.f25a.postUrl(this.c, EncodingUtils.getBytes(str, "BASE64"));
                    PeppermintLog.i("load postData=" + str);
                    return;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        this.f25a.loadUrl(this.c);
    }

    private void c(Dictionary dictionary) throws JSONException {
        PeppermintLog.i("handleLoginScheme params=" + dictionary);
        this.f23a = PeppermintAuthToken.authTokenWithDictionary(dictionary);
        this.f22a = dictionary;
        if (dictionary != null && dictionary.has(PeppermintConstant.JSON_KEY_SERVER_TOAST)) {
            this.f = (String) dictionary.get(PeppermintConstant.JSON_KEY_SERVER_TOAST);
            this.f18a.runOnUiThread(new r(this));
        }
        if (dictionary == null || !dictionary.has(ServerProtocol.DIALOG_PARAM_REDIRECT_URI)) {
            CookieSyncManager.getInstance().sync();
            dismiss();
            return;
        }
        this.f25a.loadUrl(this.f28a + ((String) dictionary.get(ServerProtocol.DIALOG_PARAM_REDIRECT_URI)));
    }

    private void d(Dictionary dictionary) {
        PeppermintLog.i("handleLogoutScheme params=" + dictionary);
        if (!PeppermintSocialPluginPGSHelper.isOnlyCookieClear()) {
            HashMap plugins = PeppermintSocialManager.sharedInstance().getPlugins();
            PeppermintSocialPlugin peppermintSocialPlugin = (PeppermintSocialPlugin) plugins.get("facebook");
            PeppermintSocialPlugin peppermintSocialPlugin2 = (PeppermintSocialPlugin) plugins.get("googleplus");
            if (peppermintSocialPlugin != null) {
                peppermintSocialPlugin.disconnect();
            }
            if (peppermintSocialPlugin2 != null) {
                peppermintSocialPlugin2.disconnect();
            } else if (PeppermintSocialPluginPGSHelper.get_instance() != null) {
                PeppermintSocialPluginPGSHelper.get_instance().disconnectEx();
            }
        }
        this.f23a = null;
        CookieManager instance = CookieManager.getInstance();
        String cookie = instance.getCookie(this.d);
        if (cookie != null) {
            for (String split : cookie.split(";")) {
                String[] split2 = split.split("=");
                if (split2.length > 0 && split2[0].startsWith("peppermint")) {
                    instance.setCookie(this.d, new StringBuilder(String.valueOf(split2[0].trim())).append("=;expires=Sat, 1 Jan 2000 00:00:01 UTC;").toString());
                }
            }
            instance.removeExpiredCookie();
            PeppermintSocialPluginPGSHelper.setOnlycookieclear(false);
            this.f22a = dictionary;
            dismiss();
        }
    }

    private void e(Dictionary dictionary) throws JSONException {
        PeppermintLog.i("handleCloseScheme params=" + dictionary);
        if (dictionary != null) {
            this.f22a = dictionary;
            if (dictionary.has(PeppermintConstant.JSON_KEY_SERVER_TOAST)) {
                this.f = (String) dictionary.get(PeppermintConstant.JSON_KEY_SERVER_TOAST);
                this.f18a.runOnUiThread(new s(this));
            }
        }
        dismiss();
    }

    private void f(Dictionary dictionary) throws JSONException {
        PeppermintLog.i("handleGetPictureScheme params=" + dictionary);
        String str = "gallery";
        if (dictionary != null && dictionary.has(PeppermintConstant.JSON_KEY_TYPE) && (dictionary.get(PeppermintConstant.JSON_KEY_TYPE).equals("camera") || dictionary.get(PeppermintConstant.JSON_KEY_TYPE).equals("gallery"))) {
            str = (String) dictionary.get(PeppermintConstant.JSON_KEY_TYPE);
        }
        if (str.equals("camera")) {
            this.f19a = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg"));
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("output", this.f19a);
            this.f18a.startActivityForResult(intent, PeppermintConstant.REQUEST_CODE_PICK_FROM_CAMERA);
            return;
        }
        intent = new Intent("android.intent.action.PICK");
        intent.setType("vnd.android.cursor.dir/image");
        this.f18a.startActivityForResult(intent, PeppermintConstant.REQUEST_CODE_PICK_FROM_ALBUM);
    }

    private void g(Dictionary dictionary) throws JSONException {
        PeppermintLog.i("handleGetAddressBookScheme params=" + dictionary);
        Object obj = (dictionary == null || !dictionary.has(PeppermintConstant.JSON_KEY_TYPE)) ? "phonenumber" : dictionary.get(PeppermintConstant.JSON_KEY_TYPE);
        new Thread(new t(this, (String) obj)).start();
    }

    private void h(Dictionary dictionary) {
        String line1Number;
        PeppermintLog.i("handleGetPhoneNumberScheme params=" + dictionary);
        String str = i.a;
        try {
            line1Number = ((TelephonyManager) this.f18a.getSystemService("phone")).getLine1Number();
        } catch (Exception e) {
            e.printStackTrace();
            line1Number = str;
        }
        this.f25a.loadUrl("javascript:window['native'].getPhoneNumberCallback('" + line1Number + "')");
    }

    private void i(Dictionary dictionary) {
        PeppermintLog.i("handleOpenBrowserScheme params=" + dictionary);
        if (dictionary != null && dictionary.has(jp.co.cyberz.fox.notify.a.g)) {
            String str;
            try {
                str = (String) dictionary.get(jp.co.cyberz.fox.notify.a.g);
            } catch (JSONException e) {
                e.printStackTrace();
                str = null;
            }
            try {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(str));
                this.f18a.startActivity(intent);
            } catch (ActivityNotFoundException e2) {
                this.f18a.runOnUiThread(new u(this));
            }
        }
    }

    private void j(Dictionary dictionary) {
        String str;
        PeppermintLog.i("handleSocialRequestFriendsScheme params=" + dictionary);
        try {
            str = (String) dictionary.get(PeppermintConstant.JSON_KEY_SERVICE);
        } catch (JSONException e) {
            e.printStackTrace();
            str = null;
        }
        PeppermintSocialPlugin pluginByName = PeppermintSocialManager.pluginByName(str);
        if (pluginByName == null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, str);
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_NOTSUP);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            receiveSocialFriends(jSONObject);
            return;
        }
        pluginByName.connectForAction(PeppermintSocialAction.actionWithType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_FRIENDS), null, new i(this));
    }

    private void k(Dictionary dictionary) {
        String str;
        PeppermintLog.i("handleSocialIsAuthorizedScheme params=" + dictionary);
        try {
            str = (String) dictionary.get(PeppermintConstant.JSON_KEY_SERVICE);
        } catch (JSONException e) {
            e.printStackTrace();
            str = null;
        }
        PeppermintSocialPlugin pluginByName = PeppermintSocialManager.pluginByName(str);
        if (pluginByName == null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, str);
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_NOTSUP);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            receiveSocialLogout(jSONObject);
            return;
        }
        pluginByName.connectForAction(PeppermintSocialAction.actionWithType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_IS_AUTHORIZED), null, new j(this));
    }

    private void l(Dictionary dictionary) {
        String str;
        PeppermintLog.i("handleSocialLogoutScheme params=" + dictionary);
        try {
            str = (String) dictionary.get(PeppermintConstant.JSON_KEY_SERVICE);
        } catch (JSONException e) {
            e.printStackTrace();
            str = null;
        }
        PeppermintSocialPlugin pluginByName = PeppermintSocialManager.pluginByName(str);
        if (pluginByName == null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, str);
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_NOTSUP);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            receiveSocialLogout(jSONObject);
            return;
        }
        pluginByName.connectForAction(PeppermintSocialAction.actionWithType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_LOGOUT), null, new k(this));
    }

    private void m(Dictionary dictionary) {
        String str;
        PeppermintLog.i("handleSocialRequestUserProfileScheme params=" + dictionary);
        try {
            str = (String) dictionary.get(PeppermintConstant.JSON_KEY_SERVICE);
        } catch (JSONException e) {
            e.printStackTrace();
            str = null;
        }
        PeppermintSocialPlugin pluginByName = PeppermintSocialManager.pluginByName(str);
        if (pluginByName == null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, str);
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_NOTSUP);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            receiveSocialUserProfile(jSONObject);
            return;
        }
        pluginByName.connectForAction(PeppermintSocialAction.actionWithType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_ME), null, new l(this));
    }

    private void n(Dictionary dictionary) {
        String str;
        PeppermintLog.i("handleSocialRequestUserTokenScheme params=" + dictionary);
        try {
            str = (String) dictionary.get(PeppermintConstant.JSON_KEY_SERVICE);
        } catch (JSONException e) {
            e.printStackTrace();
            str = null;
        }
        PeppermintSocialPlugin pluginByName = PeppermintSocialManager.pluginByName(str);
        if (pluginByName == null) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, str);
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_NOTSUP);
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            receiveSocialUserToken(jSONObject);
            return;
        }
        pluginByName.connectForAction(PeppermintSocialAction.actionWithType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_TOKEN), null, new m(this));
    }

    private void o(Dictionary dictionary) throws JSONException {
        PeppermintLog.i("handleGuestAquireUidScheme params=" + dictionary);
        this.f22a = dictionary;
        if (dictionary == null || !dictionary.has(ServerProtocol.DIALOG_PARAM_REDIRECT_URI)) {
            CookieSyncManager.getInstance().sync();
            dismiss();
            return;
        }
        this.f25a.loadUrl(this.f28a + ((String) dictionary.get(ServerProtocol.DIALOG_PARAM_REDIRECT_URI)));
    }

    private void p(Dictionary dictionary) {
        PeppermintLog.i("handleGuestBindScheme params=" + dictionary);
        PeppermintAuthToken authTokenWithDictionary = PeppermintAuthToken.authTokenWithDictionary(dictionary);
        if (authTokenWithDictionary != null) {
            this.f23a = authTokenWithDictionary;
        }
        this.f22a = dictionary;
        dismiss();
    }

    public void dismiss() {
        super.dismiss();
        PeppermintLog.i("dismiss isViewInvisible=" + this.f29a);
        if (this.f26a != null) {
            this.f26a.dismiss();
        }
        a();
    }

    public PeppermintAuthToken getAuthToken() {
        return this.f23a;
    }

    public Uri getImageCaptureUri() {
        return this.f19a;
    }

    public Uri getImageOutputUri() {
        return this.b;
    }

    public WebView getWebView() {
        return this.f25a;
    }

    public void initSubViews() {
        this.f20a = new ImageView(this.f18a);
        Bitmap decodeResource = BitmapFactory.decodeResource(this.f18a.getResources(), PeppermintResource.getID(this.f18a, "R.drawable.hub_btn_close"));
        DisplayMetrics displayMetrics = this.f18a.getResources().getDisplayMetrics();
        int applyDimension = (int) TypedValue.applyDimension(1, 54.0f, displayMetrics);
        int applyDimension2 = (int) TypedValue.applyDimension(1, 30.0f, displayMetrics);
        if (((float) displayMetrics.widthPixels) / displayMetrics.density > 640.0f) {
            applyDimension *= 2;
            applyDimension2 *= 2;
        }
        this.f20a.setImageBitmap(Bitmap.createScaledBitmap(decodeResource, applyDimension, applyDimension2, true));
        this.f20a.setAdjustViewBounds(true);
        this.f20a.setScaleType(ScaleType.FIT_XY);
        this.f20a.setBackgroundColor(0);
        this.f20a.setOnClickListener(new h(this));
        this.f20a.setLayoutParams(new LayoutParams(-2, -2, 5));
        this.f21a = new ProgressBar(this.f18a, null, 16842871);
        this.f21a.setLayoutParams(new LayoutParams(-2, -2, 17));
        this.f25a = new a(this, this.f18a);
        WebSettings settings = this.f25a.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setSaveFormData(false);
        this.f25a.setLayoutParams(a);
        this.f25a.setWebViewClient(new b());
        this.f25a.setWebChromeClient(new n(this));
        this.f25a.setLayoutParams(new LayoutParams(-1, -1));
        this.f25a.setVerticalScrollbarOverlay(true);
        this.f25a.getSettings().setAppCacheEnabled(true);
        this.f25a.getSettings().setCacheMode(-1);
        this.f25a.getSettings().setDefaultTextEncodingName("UTF-8");
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().getAttributes().windowAnimations = 16973826;
        View frameLayout = new FrameLayout(this.f18a);
        if (VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(4);
            getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new o(this));
        } else {
            getWindow().setFlags(ModuleConfig.MERCURY_MODULE, ModuleConfig.MERCURY_MODULE);
        }
        getWindow().setGravity(48);
        frameLayout.addView(this.f25a);
        frameLayout.addView(this.f21a);
        frameLayout.addView(this.f20a);
        addContentView(frameLayout, new LayoutParams(-1, -1));
        getWindow().getDecorView().setVisibility(8);
        CookieSyncManager.createInstance(this.f18a);
        getWindow().setSoftInputMode(16);
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (i != 4) {
            return super.onKeyDown(i, keyEvent);
        }
        if (this.f25a == null || !this.f25a.canGoBack()) {
            dismiss();
            return true;
        }
        this.f25a.goBack();
        return true;
    }

    public void onWindowFocusChanged(boolean z) {
        PeppermintLog.i("onWindowFocusChanged " + z);
        if (VERSION.SDK_INT >= 16) {
            getWindow().getDecorView().setSystemUiVisibility(4);
        }
        super.onWindowFocusChanged(z);
    }

    protected void receiveSocialFriends(JSONObject jSONObject) {
        PeppermintLog.i("receiveSocialFriends json=" + jSONObject);
        this.f25a.loadUrl("javascript:window['native'].putSocialFriendList(eval(" + jSONObject.toString() + "))");
    }

    protected void receiveSocialIsAuthorized(JSONObject jSONObject) {
        PeppermintLog.i("receiveIsAuthorized json=" + jSONObject);
        this.f25a.loadUrl("javascript:window['native'].socialIsAuthorizedResult(eval(" + jSONObject.toString() + "))");
    }

    protected void receiveSocialLogout(JSONObject jSONObject) {
        PeppermintLog.i("receiveLogout json=" + jSONObject);
        this.f25a.loadUrl("javascript:window['native'].socialLogoutResultCallback(eval(" + jSONObject.toString() + "))");
    }

    protected void receiveSocialUserProfile(JSONObject jSONObject) {
        PeppermintLog.i("receiveSocialUserProfile json=" + jSONObject);
        this.f25a.loadUrl("javascript:window['native'].putSocialUserData(eval(" + jSONObject + "))");
    }

    protected void receiveSocialUserToken(JSONObject jSONObject) {
        PeppermintLog.i("receiveSocialUserToken json=" + jSONObject);
        this.f25a.loadUrl("javascript:window['native'].putSocialUserToken(eval(" + jSONObject + "))");
    }

    public void setImageCaptureUri(Uri uri) {
        this.f19a = uri;
    }

    public void setImageOutputUri(Uri uri) {
        this.b = uri;
    }

    public void show() {
        super.show();
        PeppermintLog.i("show");
        c();
        if (!this.f29a) {
            b();
        }
    }
}
