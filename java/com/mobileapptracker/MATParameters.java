package com.mobileapptracker;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Point;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.WindowManager;
import java.io.File;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONArray;

public class MATParameters {
    private static MATParameters c = null;
    private String A = null;
    private String B = null;
    private String C = null;
    private String D = null;
    private String E = null;
    private String F = null;
    private String G = null;
    private String H = null;
    private String I = null;
    private String J = null;
    private Location K = null;
    private String L = null;
    private String M = null;
    private String N = null;
    private String O = null;
    private String P = null;
    private String Q = null;
    private String R;
    private String S;
    private String T;
    private String U = null;
    private String V = null;
    private String W = null;
    private String X = null;
    private String Y = null;
    private String Z = null;
    private Context a;
    private String aa = null;
    private String ab = null;
    private String ac = null;
    private String ad = null;
    private String ae = null;
    private String af = null;
    private String ag = null;
    private String ah = null;
    private String ai = null;
    private String aj;
    private String ak;
    private String al;
    private JSONArray am = null;
    private String an;
    private String ao;
    private String ap;
    private MobileAppTracker b;
    private String d = null;
    private String e = null;
    private String f = null;
    private String g = null;
    private String h = null;
    private String i = null;
    private String j = null;
    private String k = null;
    private String l = null;
    private String m = null;
    private String n = null;
    private String o = null;
    private String p = null;
    private String q = null;
    private String r = null;
    private String s = null;
    private String t = null;
    private String u = null;
    private String v = null;
    private String w = null;
    private String x = null;
    private String y = null;
    private String z = null;

    private synchronized String a(String str) {
        return this.a.getSharedPreferences("com.mobileapptracking", 0).getString(str, i.a);
    }

    private synchronized void a(String str, String str2) {
        this.a.getSharedPreferences("com.mobileapptracking", 0).edit().putString(str, str2).commit();
    }

    private synchronized boolean a(Context context, String str, String str2) {
        boolean z;
        setAdvertiserId(str.trim());
        setConversionKey(str2.trim());
        setCurrencyCode("USD");
        new Thread(new d(this, context)).start();
        new Handler(Looper.getMainLooper()).post(new e(this, this.a));
        String packageName = this.a.getPackageName();
        setPackageName(packageName);
        PackageManager packageManager = this.a.getPackageManager();
        try {
            setAppName(packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0)).toString());
            setInstallDate(Long.toString(new Date(new File(packageManager.getApplicationInfo(packageName, 0).sourceDir).lastModified()).getTime() / 1000));
        } catch (NameNotFoundException e) {
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
            setAppVersion(Integer.toString(packageInfo.versionCode));
            setAppVersionName(packageInfo.versionName);
        } catch (NameNotFoundException e2) {
            setAppVersion(a.d);
        }
        try {
            int i;
            int i2;
            setInstaller(packageManager.getInstallerPackageName(packageName));
            setDeviceModel(Build.MODEL);
            setDeviceBrand(Build.MANUFACTURER);
            setDeviceCpuType(System.getProperty("os.arch"));
            setOsVersion(VERSION.RELEASE);
            setScreenDensity(Float.toString(context.getResources().getDisplayMetrics().density));
            WindowManager windowManager = (WindowManager) context.getSystemService("window");
            if (VERSION.SDK_INT >= 13) {
                Point point = new Point();
                windowManager.getDefaultDisplay().getSize(point);
                i = point.x;
                i2 = point.y;
            } else {
                i = windowManager.getDefaultDisplay().getWidth();
                i2 = windowManager.getDefaultDisplay().getHeight();
            }
            setScreenWidth(Integer.toString(i));
            setScreenHeight(Integer.toString(i2));
            if (((ConnectivityManager) this.a.getSystemService("connectivity")).getNetworkInfo(1).isConnected()) {
                setConnectionType("wifi");
            } else {
                setConnectionType("mobile");
            }
            setLanguage(Locale.getDefault().getDisplayLanguage(Locale.US));
            TelephonyManager telephonyManager = (TelephonyManager) this.a.getSystemService("phone");
            if (telephonyManager != null) {
                if (telephonyManager.getNetworkCountryIso() != null) {
                    setCountryCode(telephonyManager.getNetworkCountryIso());
                }
                setDeviceCarrier(telephonyManager.getNetworkOperatorName());
                packageName = telephonyManager.getNetworkOperator();
                if (packageName != null) {
                    try {
                        String substring = packageName.substring(0, 3);
                        packageName = packageName.substring(3);
                        setMCC(substring);
                        setMNC(packageName);
                    } catch (IndexOutOfBoundsException e3) {
                    }
                }
            } else {
                setCountryCode(Locale.getDefault().getCountry());
            }
            packageName = getMatId();
            if (packageName == null || packageName.length() == 0) {
                setMatId(UUID.randomUUID().toString());
            }
            z = true;
        } catch (Exception e4) {
            Log.d("MobileAppTracker", "MobileAppTracker initialization failed");
            e4.printStackTrace();
            z = false;
        }
        return z;
    }

    public static MATParameters getInstance() {
        return c;
    }

    public static MATParameters init(MobileAppTracker mat, Context context, String advertiserId, String conversionKey) {
        if (c == null) {
            MATParameters mATParameters = new MATParameters();
            c = mATParameters;
            mATParameters.b = mat;
            c.a = context;
            c.a(context, advertiserId, conversionKey);
        }
        return c;
    }

    public void clear() {
        c = null;
    }

    public synchronized String getAction() {
        return this.d;
    }

    public synchronized String getAdvertiserId() {
        return this.e;
    }

    public synchronized String getAge() {
        return this.f;
    }

    public synchronized String getAllowDuplicates() {
        return this.g;
    }

    public synchronized String getAltitude() {
        return this.h;
    }

    public synchronized String getAndroidId() {
        return this.i;
    }

    public synchronized String getAndroidIdMd5() {
        return this.j;
    }

    public synchronized String getAndroidIdSha1() {
        return this.k;
    }

    public synchronized String getAndroidIdSha256() {
        return this.l;
    }

    public synchronized String getAppAdTrackingEnabled() {
        return this.m;
    }

    public synchronized String getAppName() {
        return this.n;
    }

    public synchronized String getAppVersion() {
        return this.o;
    }

    public synchronized String getAppVersionName() {
        return this.p;
    }

    public synchronized String getConnectionType() {
        return this.q;
    }

    public synchronized String getConversionKey() {
        return this.r;
    }

    public synchronized String getCountryCode() {
        return this.s;
    }

    public synchronized String getCurrencyCode() {
        return this.t;
    }

    public synchronized String getDeviceBrand() {
        return this.u;
    }

    public synchronized String getDeviceCarrier() {
        return this.v;
    }

    public synchronized String getDeviceCpuSubtype() {
        return this.x;
    }

    public synchronized String getDeviceCpuType() {
        return this.w;
    }

    public synchronized String getDeviceId() {
        return this.y;
    }

    public synchronized String getDeviceModel() {
        return this.z;
    }

    public synchronized String getExistingUser() {
        return this.A;
    }

    public synchronized String getFacebookUserId() {
        return this.B;
    }

    public synchronized String getGender() {
        return this.C;
    }

    public synchronized String getGoogleAdTrackingLimited() {
        return this.E;
    }

    public synchronized String getGoogleAdvertisingId() {
        return this.D;
    }

    public synchronized String getGoogleUserId() {
        return this.F;
    }

    public synchronized String getInstallDate() {
        return this.G;
    }

    public synchronized String getInstallReferrer() {
        return a("mat_referrer");
    }

    public synchronized String getInstaller() {
        return this.H;
    }

    public synchronized String getIsPayingUser() {
        return a("mat_is_paying_user");
    }

    public synchronized String getLanguage() {
        return this.I;
    }

    public synchronized String getLastOpenLogId() {
        return a("mat_log_id_last_open");
    }

    public synchronized String getLatitude() {
        return this.J;
    }

    public synchronized Location getLocation() {
        return this.K;
    }

    public synchronized String getLongitude() {
        return this.L;
    }

    public synchronized String getMCC() {
        return this.N;
    }

    public synchronized String getMNC() {
        return this.O;
    }

    public synchronized String getMacAddress() {
        return this.M;
    }

    public synchronized String getMatId() {
        return this.a.getSharedPreferences("mat_id", 0).contains("mat_id") ? this.a.getSharedPreferences("mat_id", 0).getString("mat_id", i.a) : a("mat_id");
    }

    public synchronized String getOpenLogId() {
        return a("mat_log_id_open");
    }

    public synchronized String getOsVersion() {
        return this.P;
    }

    public synchronized String getPackageName() {
        return this.Q;
    }

    public synchronized String getPhoneNumber() {
        return a("mat_phone_number");
    }

    public synchronized String getPhoneNumberMd5() {
        return this.R;
    }

    public synchronized String getPhoneNumberSha1() {
        return this.S;
    }

    public synchronized String getPhoneNumberSha256() {
        return this.T;
    }

    public synchronized String getPluginName() {
        return this.U;
    }

    public synchronized String getPurchaseStatus() {
        return this.V;
    }

    public synchronized String getRefId() {
        return this.Z;
    }

    public synchronized String getReferralSource() {
        return this.W;
    }

    public synchronized String getReferralUrl() {
        return this.X;
    }

    public synchronized String getReferrerDelay() {
        return this.Y;
    }

    public synchronized String getRevenue() {
        return this.aa;
    }

    public synchronized String getScreenDensity() {
        return this.ab;
    }

    public synchronized String getScreenHeight() {
        return this.ac;
    }

    public synchronized String getScreenWidth() {
        return this.ad;
    }

    public synchronized String getSdkVersion() {
        return "3.9.1";
    }

    public synchronized String getSiteId() {
        return this.ae;
    }

    public synchronized String getTRUSTeId() {
        return this.ag;
    }

    public synchronized String getTrackingId() {
        return this.af;
    }

    public synchronized String getTwitterUserId() {
        return this.ah;
    }

    public synchronized String getUserAgent() {
        return this.ai;
    }

    public synchronized String getUserEmail() {
        return a("mat_user_email");
    }

    public synchronized String getUserEmailMd5() {
        return this.aj;
    }

    public synchronized String getUserEmailSha1() {
        return this.ak;
    }

    public synchronized String getUserEmailSha256() {
        return this.al;
    }

    public synchronized JSONArray getUserEmails() {
        return this.am;
    }

    public synchronized String getUserId() {
        return a("mat_user_id");
    }

    public synchronized String getUserName() {
        return a("mat_user_name");
    }

    public synchronized String getUserNameMd5() {
        return this.an;
    }

    public synchronized String getUserNameSha1() {
        return this.ao;
    }

    public synchronized String getUserNameSha256() {
        return this.ap;
    }

    public synchronized void setAction(String action) {
        this.d = action;
    }

    public synchronized void setAdvertiserId(String advertiserId) {
        this.e = advertiserId;
    }

    public synchronized void setAge(String age) {
        this.f = age;
    }

    public synchronized void setAllowDuplicates(String allowDuplicates) {
        this.g = allowDuplicates;
    }

    public synchronized void setAltitude(String altitude) {
        this.h = altitude;
    }

    public synchronized void setAndroidId(String androidId) {
        this.i = androidId;
    }

    public synchronized void setAndroidIdMd5(String androidIdMd5) {
        this.j = androidIdMd5;
    }

    public synchronized void setAndroidIdSha1(String androidIdSha1) {
        this.k = androidIdSha1;
    }

    public synchronized void setAndroidIdSha256(String androidIdSha256) {
        this.l = androidIdSha256;
    }

    public synchronized void setAppAdTrackingEnabled(String adTrackingEnabled) {
        this.m = adTrackingEnabled;
    }

    public synchronized void setAppName(String app_name) {
        this.n = app_name;
    }

    public synchronized void setAppVersion(String appVersion) {
        this.o = appVersion;
    }

    public synchronized void setAppVersionName(String appVersionName) {
        this.p = appVersionName;
    }

    public synchronized void setConnectionType(String connection_type) {
        this.q = connection_type;
    }

    public synchronized void setConversionKey(String conversionKey) {
        this.r = conversionKey;
    }

    public synchronized void setCountryCode(String countryCode) {
        this.s = countryCode;
    }

    public synchronized void setCurrencyCode(String currencyCode) {
        this.t = currencyCode;
    }

    public synchronized void setDeviceBrand(String deviceBrand) {
        this.u = deviceBrand;
    }

    public synchronized void setDeviceCarrier(String carrier) {
        this.v = carrier;
    }

    public synchronized void setDeviceCpuSubtype(String cpuType) {
        this.x = cpuType;
    }

    public synchronized void setDeviceCpuType(String cpuType) {
        this.w = cpuType;
    }

    public synchronized void setDeviceId(String deviceId) {
        this.y = deviceId;
    }

    public synchronized void setDeviceModel(String model) {
        this.z = model;
    }

    public synchronized void setExistingUser(String existingUser) {
        this.A = existingUser;
    }

    public synchronized void setFacebookUserId(String fb_user_id) {
        this.B = fb_user_id;
    }

    public synchronized void setGender(String gender) {
        this.C = gender;
    }

    public synchronized void setGoogleAdTrackingLimited(String limited) {
        this.E = limited;
    }

    public synchronized void setGoogleAdvertisingId(String adId) {
        this.D = adId;
    }

    public synchronized void setGoogleUserId(String google_user_id) {
        this.F = google_user_id;
    }

    public synchronized void setInstallDate(String installDate) {
        this.G = installDate;
    }

    public synchronized void setInstallReferrer(String installReferrer) {
        a("mat_referrer", installReferrer);
    }

    public synchronized void setInstaller(String installer) {
        this.H = installer;
    }

    public synchronized void setIsPayingUser(String isPayingUser) {
        a("mat_is_paying_user", isPayingUser);
    }

    public synchronized void setLanguage(String language) {
        this.I = language;
    }

    public synchronized void setLastOpenLogId(String logId) {
        a("mat_log_id_last_open", logId);
    }

    public synchronized void setLatitude(String latitude) {
        this.J = latitude;
    }

    public synchronized void setLocation(Location location) {
        this.K = location;
    }

    public synchronized void setLongitude(String longitude) {
        this.L = longitude;
    }

    public synchronized void setMCC(String mcc) {
        this.N = mcc;
    }

    public synchronized void setMNC(String mnc) {
        this.O = mnc;
    }

    public synchronized void setMacAddress(String mac_address) {
        this.M = mac_address;
    }

    public synchronized void setMatId(String matId) {
        a("mat_id", matId);
    }

    public synchronized void setOpenLogId(String logId) {
        a("mat_log_id_open", logId);
    }

    public synchronized void setOsVersion(String osVersion) {
        this.P = osVersion;
    }

    public synchronized void setPackageName(String packageName) {
        this.Q = packageName;
    }

    public synchronized void setPhoneNumber(String phoneNumber) {
        a("mat_phone_number", phoneNumber);
        setPhoneNumberMd5(MATEncryption.md5(phoneNumber));
        setPhoneNumberSha1(MATEncryption.sha1(phoneNumber));
        setPhoneNumberSha256(MATEncryption.sha256(phoneNumber));
    }

    public synchronized void setPhoneNumberMd5(String phoneNumberMd5) {
        this.R = phoneNumberMd5;
    }

    public synchronized void setPhoneNumberSha1(String phoneNumberSha1) {
        this.S = phoneNumberSha1;
    }

    public synchronized void setPhoneNumberSha256(String phoneNumberSha256) {
        this.T = phoneNumberSha256;
    }

    public synchronized void setPluginName(String pluginName) {
        this.U = null;
    }

    public synchronized void setPurchaseStatus(String purchaseStatus) {
        this.V = purchaseStatus;
    }

    public synchronized void setRefId(String refId) {
        this.Z = refId;
    }

    public synchronized void setReferralSource(String referralPackage) {
        this.W = referralPackage;
    }

    public synchronized void setReferralUrl(String referralUrl) {
        this.X = referralUrl;
    }

    public synchronized void setReferrerDelay(long referrerDelay) {
        this.Y = Long.toString(referrerDelay);
    }

    public synchronized void setRevenue(String revenue) {
        this.aa = revenue;
    }

    public synchronized void setScreenDensity(String density) {
        this.ab = density;
    }

    public synchronized void setScreenHeight(String screenheight) {
        this.ac = screenheight;
    }

    public synchronized void setScreenWidth(String screenwidth) {
        this.ad = screenwidth;
    }

    public synchronized void setSiteId(String siteId) {
        this.ae = siteId;
    }

    public synchronized void setTRUSTeId(String tpid) {
        this.ag = tpid;
    }

    public synchronized void setTrackingId(String trackingId) {
        this.af = trackingId;
    }

    public synchronized void setTwitterUserId(String twitter_user_id) {
        this.ah = twitter_user_id;
    }

    public synchronized void setUserEmail(String userEmail) {
        a("mat_user_email", userEmail);
        setUserEmailMd5(MATEncryption.md5(userEmail));
        setUserEmailSha1(MATEncryption.sha1(userEmail));
        setUserEmailSha256(MATEncryption.sha256(userEmail));
    }

    public synchronized void setUserEmailMd5(String userEmailMd5) {
        this.aj = userEmailMd5;
    }

    public synchronized void setUserEmailSha1(String userEmailSha1) {
        this.ak = userEmailSha1;
    }

    public synchronized void setUserEmailSha256(String userEmailSha256) {
        this.al = userEmailSha256;
    }

    public synchronized void setUserEmails(String[] emails) {
        this.am = new JSONArray();
        for (Object put : emails) {
            this.am.put(put);
        }
    }

    public synchronized void setUserId(String user_id) {
        a("mat_user_id", user_id);
    }

    public synchronized void setUserName(String userName) {
        a("mat_user_name", userName);
        setUserNameMd5(MATEncryption.md5(userName));
        setUserNameSha1(MATEncryption.sha1(userName));
        setUserNameSha256(MATEncryption.sha256(userName));
    }

    public synchronized void setUserNameMd5(String userNameMd5) {
        this.an = userNameMd5;
    }

    public synchronized void setUserNameSha1(String userNameSha1) {
        this.ao = userNameSha1;
    }

    public synchronized void setUserNameSha256(String userNameSha256) {
        this.ap = userNameSha256;
    }
}
