package com.com2us.peppermint;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.peppermint.socialextension.PeppermintSocialAction;
import com.com2us.peppermint.socialextension.PeppermintSocialManager;
import com.com2us.peppermint.socialextension.PeppermintSocialPlugin;
import com.com2us.peppermint.socialextension.PeppermintSocialPluginGooglePlus;
import com.com2us.peppermint.socialextension.PeppermintSocialPluginPGSHelper;
import com.com2us.peppermint.util.PeppermintLog;
import com.com2us.peppermint.util.PeppermintResource;
import com.com2us.peppermint.util.PeppermintUtil;
import com.facebook.Session;
import com.facebook.Settings;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONObject;

public class Peppermint {
    private static PeppermintSocialPluginPGSHelper a = null;
    private Activity f9a = null;
    private PeppermintDialog f10a;
    private String f11a;
    private String b;
    private String c;
    private String d;
    private String e = null;

    public class ActivityResultCode {
        final /* synthetic */ Peppermint a;

        private ActivityResultCode(Peppermint peppermint) {
            this.a = peppermint;
        }
    }

    public interface OnGetAsyncAdvertisingIDListener {
        void onGetAsyncAdvertisingIDListener(String str);
    }

    public class Version {
        private static final int a = 2;
        private static final int b = 3;
        private static final int c = 5;
        final /* synthetic */ Peppermint f12a;

        private Version(Peppermint peppermint) {
            this.f12a = peppermint;
        }
    }

    private enum a {
        None(0),
        SET_PEPPERMINT_HTTP(0),
        SET_PEPPERMINT_HTTPS(64),
        SET_PEPPERMINT_PRODUCTION(0),
        SET_PEPPERMINT_DEVELOPMENT_URL(8),
        SET_PEPPERMINT_STAGING_URL(16),
        SET_PEPPERMINT_COM2US_DOMAIN(0),
        SET_PEPPERMINT_QPYOU_DOMAIN(1);
        
        private int f13a;

        private a(int i) {
            this.f13a = i;
        }
    }

    public Peppermint(Activity activity) {
        this.f9a = activity;
        PeppermintSocialManager.setPeppermint(this);
    }

    private int a(String str, boolean z, boolean z2, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("showDialog subPath=" + str + " isViewInvisible=" + z + " isFullScreen" + z2);
        this.f9a.runOnUiThread(new f(this, str, z, peppermintCallback));
        return 0;
    }

    private String a() {
        String replaceAll = new String(PeppermintUtil.macAddress(this.f9a)).replaceAll(":", i.a).replaceAll("\\-", i.a).replaceAll("\\.", i.a);
        PeppermintLog.i("macAddress function inner=" + replaceAll);
        return replaceAll;
    }

    private static void m0a() {
        int i = 0;
        String[] split = Settings.getSdkVersion().split("\\.");
        if (split.length == 3 && Integer.parseInt(split[0]) == 3 && Integer.parseInt(split[1]) < 15) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n*****************************************************************************\n");
            stringBuilder.append("This is message from Com2uS Hive team. Hive Android 2.1.6 or higher must use Facebook SDK 3.15.0 or higher.");
            stringBuilder.append("Current Facebook SDK version that you use is ");
            while (i < split.length) {
                stringBuilder.append(split[i]).append(".");
                i++;
            }
            stringBuilder.append("\n*****************************************************************************");
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    private void a(int i) {
        Object obj;
        Object obj2;
        if ((a.SET_PEPPERMINT_HTTPS.f13a & i) == a.SET_PEPPERMINT_HTTPS.f13a) {
            obj = PeppermintURL.PEPPERMINT_HTTPS;
            obj2 = PeppermintURL.PEPPERMINT_HTTPS;
        } else {
            obj = PeppermintURL.PEPPERMINT_HTTP;
            obj2 = PeppermintURL.PEPPERMINT_HTTP;
        }
        if ((a.SET_PEPPERMINT_DEVELOPMENT_URL.f13a & i) == a.SET_PEPPERMINT_DEVELOPMENT_URL.f13a) {
            if ((a.SET_PEPPERMINT_QPYOU_DOMAIN.f13a & i) == a.SET_PEPPERMINT_QPYOU_DOMAIN.f13a) {
                obj = new StringBuilder(String.valueOf(obj)).append("test-hub.").toString();
                obj2 = new StringBuilder(String.valueOf(obj2)).append("test-api.").toString();
            } else {
                obj = new StringBuilder(String.valueOf(obj)).append("test.hub.").toString();
                obj2 = new StringBuilder(String.valueOf(obj2)).append("test.api.").toString();
            }
        } else if ((a.SET_PEPPERMINT_STAGING_URL.f13a & i) == a.SET_PEPPERMINT_STAGING_URL.f13a) {
            obj = new StringBuilder(String.valueOf(obj)).append("staging-hub.").toString();
            obj2 = new StringBuilder(String.valueOf(obj2)).append("staging-api.").toString();
        } else {
            obj = new StringBuilder(String.valueOf(obj)).append("hub.").toString();
            obj2 = new StringBuilder(String.valueOf(obj2)).append("api.").toString();
        }
        if ((a.SET_PEPPERMINT_QPYOU_DOMAIN.f13a & i) == a.SET_PEPPERMINT_QPYOU_DOMAIN.f13a) {
            String stringBuilder = new StringBuilder(String.valueOf(obj)).append(PeppermintURL.PEPPERMINT_QPYOU_DOMAIN).toString();
            String stringBuilder2 = new StringBuilder(String.valueOf(obj2)).append(PeppermintURL.PEPPERMINT_QPYOU_DOMAIN).toString();
            this.c = stringBuilder;
            this.d = stringBuilder2;
            a(PeppermintURL.PEPPERMINT_QPYOU_COOKIE_URL);
            return;
        }
        stringBuilder = new StringBuilder(String.valueOf(obj)).append(PeppermintURL.PEPPERMINT_COM2US_DOMAIN).toString();
        stringBuilder2 = new StringBuilder(String.valueOf(obj2)).append(PeppermintURL.PEPPERMINT_COM2US_DOMAIN).toString();
        this.c = stringBuilder;
        this.d = stringBuilder2;
        a(PeppermintURL.PEPPERMINT_COOKIE_URL);
    }

    private void a(String str) {
        CookieSyncManager.createInstance(this.f9a);
        CookieManager instance = CookieManager.getInstance();
        instance.setAcceptCookie(true);
        instance.setCookie(str, "native_version=" + getNativeVersion());
        instance.setCookie(str, "appid=" + getAppId());
        instance.setCookie(str, "gameindex=" + getGameIndex());
        instance.setCookie(str, "device=" + b());
        instance.setCookie(str, "platform=" + c());
        instance.setCookie(str, "osversion=" + d());
        String g = g();
        if (g != null) {
            instance.setCookie(str, "did=" + g);
        }
        try {
            g = a();
            if (g == null) {
                g = ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN;
            }
            instance.setCookie(str, "hub_ck1=" + URLEncoder.encode(PeppermintUtil.generateClientKey(g), "UTF-8"));
            g = f();
            if (g == null) {
                g = ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN;
            }
            instance.setCookie(str, "hub_ck2=" + URLEncoder.encode(PeppermintUtil.generateClientKey(g), "UTF-8"));
            g = e();
            if (g == null) {
                g = ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN;
            }
            instance.setCookie(str, "hub_ck3=" + URLEncoder.encode(PeppermintUtil.generateClientKey(g), "UTF-8"));
            if (this.e == null) {
                Thread.sleep(500);
            }
            g = this.e;
            if (g == null) {
                g = ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN;
            }
            instance.setCookie(str, "hub_ck4=" + URLEncoder.encode(PeppermintUtil.generateClientKey(g), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        CookieSyncManager.getInstance().startSync();
        PeppermintLog.i("restoreCookies cookie=" + CookieManager.getInstance().getCookie(str));
    }

    private boolean a(String str, JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("sendRequest subPath=" + str + " params=" + jSONObject);
        new PeppermintRequest().request(this.d, str, jSONObject, peppermintCallback);
        return true;
    }

    private String b() {
        return PeppermintUtil.deviceName();
    }

    private String c() {
        return "android";
    }

    private String d() {
        return VERSION.RELEASE;
    }

    private String e() {
        return Secure.getString(this.f9a.getContentResolver(), "android_id");
    }

    private String f() {
        return ((TelephonyManager) this.f9a.getSystemService("phone")).getDeviceId();
    }

    private String g() {
        String str = "getDID";
        try {
            Class cls = Class.forName("com.com2us.module.activeuser.ActiveUser");
            if (cls == null) {
                throw new Exception("getDID cls is null");
            }
            Method method = cls.getMethod(str, new Class[0]);
            if (method != null) {
                return (String) method.invoke(null, new Object[0]);
            }
            throw new Exception("getDID method is null");
        } catch (Exception e) {
            PeppermintLog.i("ActiveUser Module getDID Exception occured!");
            return null;
        }
    }

    public static int getIsPGS() {
        PeppermintLog.i("getIsPGS");
        return (a == null || !a.getIsPGS().booleanValue()) ? 0 : 1;
    }

    public static String getVersion() {
        return "2.3.5";
    }

    public int asyncRequest(String str, JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("asyncRequest requestName=" + str + " params=" + jSONObject);
        if (str != null && str.length() != 0) {
            return a(str, jSONObject, peppermintCallback) ? 0 : -17;
        } else {
            PeppermintLog.i("requestName is null");
            return -9;
        }
    }

    public int auth(PeppermintCallback peppermintCallback) {
        PeppermintLog.i(PeppermintURL.PEPPERMINT_AUTH_PATH);
        if (this.f10a != null) {
            PeppermintLog.i("auth dialog already exist");
            return -8;
        }
        a(PeppermintURL.PEPPERMINT_AUTH_PATH, true, true, peppermintCallback);
        return 0;
    }

    public String getApiBaseURL() {
        return this.d;
    }

    public String getAppId() {
        return this.f11a;
    }

    public void getAsyncAdvertisingID(OnGetAsyncAdvertisingIDListener onGetAsyncAdvertisingIDListener) {
        if (this.e == null) {
            try {
                new Thread(new d(this, onGetAsyncAdvertisingIDListener)).start();
                return;
            } catch (Exception e) {
                this.e = null;
                PeppermintLog.i("getAsyncAdvertisingID failed.");
                onGetAsyncAdvertisingIDListener.onGetAsyncAdvertisingIDListener(this.e);
                return;
            }
        }
        onGetAsyncAdvertisingIDListener.onGetAsyncAdvertisingIDListener(this.e);
        PeppermintLog.i("getAsyncAdvertisingID : " + this.e);
    }

    public PeppermintAuthToken getAuthToken() {
        return this.f10a.getAuthToken();
    }

    public PeppermintDialog getDialog() {
        return this.f10a;
    }

    public String getGameIndex() {
        return this.b;
    }

    public Activity getMainActivity() {
        return this.f9a;
    }

    public String getNativeVersion() {
        return "Hive v.2.3.5";
    }

    public PeppermintDialog getPeppermintDialog() {
        return this.f10a;
    }

    public String getWebBaseURL() {
        return this.c;
    }

    public int guestAcquireUid(PeppermintCallback peppermintCallback) {
        PeppermintLog.i("guestAcquireUid");
        if (this.f10a != null) {
            PeppermintLog.i("guestAcquireUid dialog already exist");
            return -8;
        }
        showDialog(PeppermintURL.PEPPERMINT_GUEST_ACQUIRE_UID_PATH, peppermintCallback);
        return 0;
    }

    public int guestBind(String str, String str2, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("guestBind guestUid=" + str + " candidateUid=" + str2);
        if (this.f10a != null) {
            PeppermintLog.i("guestBind dialog already exist");
            return -8;
        } else if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            PeppermintLog.i("guestBind guestUid or candidateUid is invalid");
            return -9;
        } else {
            a("guest/bind/" + str + "/" + str2, true, false, peppermintCallback);
            return 0;
        }
    }

    public int initialize(String str, String str2, boolean z) {
        PeppermintLog.i("initialize appid=" + str + " gameIndex=" + str2 + " useTestServer=" + z);
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return -9;
        }
        getAsyncAdvertisingID(new e(this));
        CookieSyncManager.createInstance(this.f9a);
        CookieSyncManager.getInstance().startSync();
        try {
            String cookie = CookieManager.getInstance().getCookie(PeppermintURL.PEPPERMINT_QPYOU_COOKIE_URL);
            if (cookie == null) {
                throw new NullPointerException("initialize cookie is null");
            }
            String[] split = cookie.split(";");
            HashMap hashMap = new HashMap();
            for (String split2 : split) {
                String[] split3 = split2.split("=", 2);
                split3[0].startsWith(" ");
                hashMap.put(split3[0], split3[1]);
                PeppermintLog.i("----------------------");
                PeppermintLog.i(split3[0] + " : " + split3[1]);
                PeppermintLog.i("----------------------");
            }
            this.f11a = str;
            this.b = str2;
            this.f10a = null;
            if (z) {
                a((a.SET_PEPPERMINT_HTTP.f13a | a.SET_PEPPERMINT_DEVELOPMENT_URL.f13a) | a.SET_PEPPERMINT_QPYOU_DOMAIN.f13a);
            } else {
                a((a.SET_PEPPERMINT_HTTP.f13a | a.SET_PEPPERMINT_PRODUCTION.f13a) | a.SET_PEPPERMINT_QPYOU_DOMAIN.f13a);
            }
            try {
                cookie = this.f9a.getResources().getString(PeppermintResource.getID(this.f9a, "R.string.applicationId"));
                if (cookie == null || cookie.length() == 0) {
                    return 0;
                }
                a();
                return 0;
            } catch (NotFoundException e) {
                return 0;
            }
        } catch (NullPointerException e2) {
        }
    }

    public int logout(PeppermintCallback peppermintCallback) {
        PeppermintLog.i(PeppermintConstant.JSON_KEY_LOGOUT);
        if (this.f10a != null) {
            PeppermintLog.i("logout dialog already exist");
            return -8;
        }
        a(PeppermintURL.PEPPERMINT_LOGOUT_PATH, true, false, peppermintCallback);
        return 0;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        PeppermintLog.i("onActivityResult requestCode=" + i + " resultCode=" + i2 + " data=" + intent);
        ActivityResult activityResult = new ActivityResult(this);
        PeppermintSocialPluginGooglePlus peppermintSocialPluginGooglePlus;
        PeppermintSocialPluginPGSHelper peppermintSocialPluginPGSHelper;
        switch (i2) {
            case PeppermintConstant.RESULT_CODE_RECONNECT_REQUIRED /*10001*/:
                peppermintSocialPluginGooglePlus = (PeppermintSocialPluginGooglePlus) PeppermintSocialManager.sharedInstance().getPlugins().get("googleplus");
                if (peppermintSocialPluginGooglePlus != null) {
                    peppermintSocialPluginGooglePlus.onActivityResult(i, i2, intent);
                }
                peppermintSocialPluginPGSHelper = PeppermintSocialPluginPGSHelper.get_instance();
                if (peppermintSocialPluginPGSHelper != null) {
                    peppermintSocialPluginPGSHelper.onActivityResult(i, i2, intent);
                    return;
                }
                return;
            default:
                switch (i) {
                    case jp.co.cyberz.fox.notify.a.j /*9000*/:
                    case PeppermintConstant.REQUEST_CODE_PLAY_SERVICE_ERR /*9001*/:
                    case PeppermintConstant.REQUEST_CODE_RECOVER_PLAY_SERVICE_ERROR /*9002*/:
                    case PeppermintConstant.REQUEST_CODE_SHARE_APP_ACTIVITY /*9100*/:
                        peppermintSocialPluginGooglePlus = (PeppermintSocialPluginGooglePlus) PeppermintSocialManager.sharedInstance().getPlugins().get("googleplus");
                        if (peppermintSocialPluginGooglePlus != null) {
                            peppermintSocialPluginGooglePlus.onActivityResult(i, i2, intent);
                            return;
                        }
                        return;
                    case PeppermintConstant.REQUEST_CODE_PLAY_GAME_SERVICE_ERR /*19000*/:
                    case PeppermintConstant.REQUEST_CODE_RECOVER_FROM_AUTH_ERROR /*19001*/:
                    case PeppermintConstant.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR /*19002*/:
                    case PeppermintConstant.REQUEST_CODE_PICK_ACCOUNT /*19500*/:
                    case PeppermintConstant.REQUEST_CODE_PICK_ACCOUNT_RECOVER_FROM_AUTH_ERROR /*19501*/:
                        peppermintSocialPluginPGSHelper = PeppermintSocialPluginPGSHelper.get_instance();
                        if (peppermintSocialPluginPGSHelper != null) {
                            peppermintSocialPluginPGSHelper.onActivityResult(i, i2, intent);
                            return;
                        }
                        return;
                    case Session.DEFAULT_AUTHORIZE_ACTIVITY_CODE /*64206*/:
                    case PeppermintConstant.REQUEST_CODE_REAUTH_ACTIVITY /*15336995*/:
                        activityResult.onActivityResultForFacebook(i, i2, intent);
                        return;
                    case PeppermintConstant.REQUEST_CODE_PICK_FROM_CAMERA /*827393*/:
                    case PeppermintConstant.REQUEST_CODE_CROP_FROM_CAMERA /*827395*/:
                    case PeppermintConstant.REQUEST_CODE_PICK_FROM_ALBUM /*10596354*/:
                        activityResult.onActivityResultForCamera(i, i2, intent);
                        return;
                    default:
                        return;
                }
        }
    }

    public int pgsLoginProc(PeppermintCallback peppermintCallback) {
        PeppermintLog.i("pgsLoginProc");
        if (a == null) {
            a = new PeppermintSocialPluginPGSHelper();
        }
        return a.PGSLoginProc(peppermintCallback);
    }

    public void setApiBaseURL(String str) {
        PeppermintLog.i("setApiBaseURL url=" + str);
        this.d = str;
    }

    public void setMainActivity(Activity activity) {
        this.f9a = activity;
    }

    public int setOption(String str, String str2) {
        PeppermintLog.i("setOption allowedKey=" + str + "value=" + str2);
        String str3 = PeppermintURL.PEPPERMINT_QPYOU_COOKIE_URL;
        CookieManager instance = CookieManager.getInstance();
        instance.setAcceptCookie(true);
        if (!str.equals("login_center_vid")) {
            return -9;
        }
        if (str2 != null) {
            instance.setCookie(str3, new StringBuilder(String.valueOf(str)).append("=").append(str2).toString());
        } else {
            Date date = new Date();
            instance.setCookie(str3, new StringBuilder(String.valueOf(str)).append("=;expires=").append(new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss", Locale.ENGLISH).format(date)).append(";").toString());
        }
        CookieSyncManager.getInstance().startSync();
        PeppermintLog.i("restoreCookies cookie=" + CookieManager.getInstance().getCookie(str3));
        return 0;
    }

    public void setWebBaseURL(String str) {
        PeppermintLog.i("setWebBaseURL url=" + str);
        this.c = str;
    }

    public int showDialog(String str, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("showDialog subPath=" + str);
        if (this.f10a != null) {
            PeppermintLog.i("showDialog dialog already exist");
            return -8;
        } else if (str != null && str.length() != 0) {
            return a(str, false, true, peppermintCallback);
        } else {
            PeppermintLog.i("showDialog subPath is null");
            return -9;
        }
    }

    public int socialRequest(String str, String str2, JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("socialRequest service=" + str + " name=" + str2 + " jObj=" + jSONObject);
        PeppermintSocialPlugin pluginByName = PeppermintSocialManager.pluginByName(str);
        if (pluginByName == null) {
            return PeppermintType.HUB_E_SOCIAL_NOTSUP;
        }
        if (pluginByName.isWorking()) {
            return -8;
        }
        PeppermintSocialAction actionWithName = PeppermintSocialAction.actionWithName(str2);
        return actionWithName == null ? -9 : pluginByName.connectForAction(actionWithName, jSONObject, peppermintCallback) ? 0 : -16;
    }

    public int uninitialize() {
        PeppermintLog.i("uninitialize");
        if (this.f10a == null) {
            return 0;
        }
        PeppermintLog.i("uninitialize dialog already exist");
        return -8;
    }
}
