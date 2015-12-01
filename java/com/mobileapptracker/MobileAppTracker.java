package com.mobileapptracker;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.facebook.Response;
import com.facebook.internal.ServerProtocol;
import com.mobileapptracker.MATEventQueue.Add;
import com.mobileapptracker.MATEventQueue.Dump;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MobileAppTracker {
    public static final int GENDER_FEMALE = 1;
    public static final int GENDER_MALE = 0;
    private static volatile MobileAppTracker s = null;
    boolean a;
    boolean b;
    boolean c;
    ExecutorService d;
    private final String e = "heF9BATUfWuISyO8";
    protected MATEventQueue eventQueue;
    private b f;
    private MATPreloadData g;
    private g h;
    private MATEncryption i;
    protected boolean initialized;
    protected boolean isRegistered;
    private MATResponse j;
    private boolean k;
    private boolean l;
    private int m;
    protected Context mContext;
    protected MATTestRequest matRequest;
    private boolean n;
    protected BroadcastReceiver networkStateReceiver;
    private boolean o;
    private boolean p;
    protected MATParameters params;
    protected ExecutorService pubQueue;
    private long q;
    private long r;

    protected MobileAppTracker() {
    }

    private String a(int i) {
        if (!this.n) {
            return i.a;
        }
        b bVar = this.f;
        b.b(this.params.getUserAgent());
        bVar = this.f;
        Context context = this.mContext;
        g gVar = this.h;
        return bVar.a(context, i);
    }

    private synchronized void a(MATEvent mATEvent) {
        if (this.initialized) {
            String eventName;
            dumpQueue();
            this.params.setAction("conversion");
            Date date = new Date();
            if (mATEvent.getEventName() != null) {
                eventName = mATEvent.getEventName();
                if (this.p) {
                    c.a(mATEvent);
                }
                if (!eventName.equals("close")) {
                    if (eventName.equals("open") || eventName.equals("install") || eventName.equals("update") || eventName.equals("session")) {
                        this.params.setAction("session");
                        Date date2 = new Date(date.getTime() + 60000);
                    }
                }
            }
            if (mATEvent.getRevenue() > 0.0d) {
                this.params.setIsPayingUser(Integer.toString(GENDER_FEMALE));
            }
            eventName = f.a(mATEvent, this.g, this.k);
            String a = f.a(mATEvent);
            JSONArray jSONArray = new JSONArray();
            if (mATEvent.getEventItems() != null) {
                for (int i = 0; i < mATEvent.getEventItems().size(); i += GENDER_FEMALE) {
                    jSONArray.put(((MATEventItem) mATEvent.getEventItems().get(i)).toJSON());
                }
            }
            JSONObject a2 = f.a(jSONArray, mATEvent.getReceiptData(), mATEvent.getReceiptSignature(), this.params.getUserEmails());
            if (this.matRequest != null) {
                this.matRequest.constructedRequest(eventName, a, a2);
            }
            addEventToQueue(eventName, a, a2, this.o);
            this.o = false;
            dumpQueue();
            if (this.j != null) {
                this.j.enqueuedActionWithRefId(mATEvent.getRefId());
            }
        }
    }

    public static synchronized MobileAppTracker getInstance() {
        MobileAppTracker mobileAppTracker;
        synchronized (MobileAppTracker.class) {
            mobileAppTracker = s;
        }
        return mobileAppTracker;
    }

    public static synchronized MobileAppTracker init(Context context, String advertiserId, String conversionKey) {
        MobileAppTracker mobileAppTracker;
        synchronized (MobileAppTracker.class) {
            if (s == null) {
                mobileAppTracker = new MobileAppTracker();
                s = mobileAppTracker;
                mobileAppTracker.mContext = context.getApplicationContext();
                s.pubQueue = Executors.newSingleThreadExecutor();
                s.initAll(advertiserId, conversionKey);
            }
            mobileAppTracker = s;
        }
        return mobileAppTracker;
    }

    public static boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void addEventToQueue(String link, String data, JSONObject postBody, boolean firstSession) {
        ExecutorService executorService = this.d;
        MATEventQueue mATEventQueue = this.eventQueue;
        mATEventQueue.getClass();
        executorService.execute(new Add(mATEventQueue, link, data, postBody, firstSession));
    }

    protected void dumpQueue() {
        if (isOnline(this.mContext)) {
            ExecutorService executorService = this.d;
            MATEventQueue mATEventQueue = this.eventQueue;
            mATEventQueue.getClass();
            executorService.execute(new Dump(mATEventQueue));
        }
    }

    public String getAction() {
        return this.params.getAction();
    }

    public String getAdvertiserId() {
        return this.params.getAdvertiserId();
    }

    public int getAge() {
        return Integer.parseInt(this.params.getAge());
    }

    public double getAltitude() {
        return Double.parseDouble(this.params.getAltitude());
    }

    public boolean getAppAdTrackingEnabled() {
        return Integer.parseInt(this.params.getAppAdTrackingEnabled()) == GENDER_FEMALE;
    }

    public String getAppName() {
        return this.params.getAppName();
    }

    public int getAppVersion() {
        return Integer.parseInt(this.params.getAppVersion());
    }

    public String getConnectionType() {
        return this.params.getConnectionType();
    }

    public String getCountryCode() {
        return this.params.getCountryCode();
    }

    public String getCurrencyCode() {
        return this.params.getCurrencyCode();
    }

    public String getDeviceBrand() {
        return this.params.getDeviceBrand();
    }

    public String getDeviceCarrier() {
        return this.params.getDeviceCarrier();
    }

    public String getDeviceModel() {
        return this.params.getDeviceModel();
    }

    public boolean getExistingUser() {
        return Integer.parseInt(this.params.getExistingUser()) == GENDER_FEMALE;
    }

    public String getFacebookUserId() {
        return this.params.getFacebookUserId();
    }

    public int getGender() {
        return Integer.parseInt(this.params.getGender());
    }

    public boolean getGoogleAdTrackingLimited() {
        return Integer.parseInt(this.params.getGoogleAdTrackingLimited()) != 0;
    }

    public String getGoogleAdvertisingId() {
        return this.params.getGoogleAdvertisingId();
    }

    public String getGoogleUserId() {
        return this.params.getGoogleUserId();
    }

    public long getInstallDate() {
        return Long.parseLong(this.params.getInstallDate());
    }

    public String getInstallReferrer() {
        return this.params.getInstallReferrer();
    }

    public boolean getIsPayingUser() {
        return this.params.getIsPayingUser().equals(a.e);
    }

    public String getLanguage() {
        return this.params.getLanguage();
    }

    public String getLastOpenLogId() {
        return this.params.getLastOpenLogId();
    }

    public double getLatitude() {
        return Double.parseDouble(this.params.getLatitude());
    }

    public double getLongitude() {
        return Double.parseDouble(this.params.getLongitude());
    }

    public String getMCC() {
        return this.params.getMCC();
    }

    public String getMNC() {
        return this.params.getMNC();
    }

    public String getMatId() {
        return this.params.getMatId();
    }

    public String getOpenLogId() {
        return this.params.getOpenLogId();
    }

    public String getOsVersion() {
        return this.params.getOsVersion();
    }

    public String getPackageName() {
        return this.params.getPackageName();
    }

    public String getPluginName() {
        return this.params.getPluginName();
    }

    public String getRefId() {
        return this.params.getRefId();
    }

    public String getReferralSource() {
        return this.params.getReferralSource();
    }

    public String getReferralUrl() {
        return this.params.getReferralUrl();
    }

    public Double getRevenue() {
        return Double.valueOf(Double.parseDouble(this.params.getRevenue()));
    }

    public String getSDKVersion() {
        return this.params.getSdkVersion();
    }

    public String getScreenDensity() {
        return this.params.getScreenDensity();
    }

    public String getScreenHeight() {
        return this.params.getScreenHeight();
    }

    public String getScreenWidth() {
        return this.params.getScreenWidth();
    }

    public String getSiteId() {
        return this.params.getSiteId();
    }

    public String getTRUSTeId() {
        return this.params.getTRUSTeId();
    }

    public String getTwitterUserId() {
        return this.params.getTwitterUserId();
    }

    public String getUserAgent() {
        return this.params.getUserAgent();
    }

    public String getUserEmail() {
        return this.params.getUserEmail();
    }

    public String getUserId() {
        return this.params.getUserId();
    }

    public String getUserName() {
        return this.params.getUserName();
    }

    protected void initAll(String advertiserId, String conversionKey) {
        this.f = b.a(advertiserId, conversionKey, this.mContext.getPackageName());
        this.params = MATParameters.init(this, this.mContext, advertiserId, conversionKey);
        this.d = Executors.newSingleThreadExecutor();
        this.h = new g();
        this.i = new MATEncryption(conversionKey.trim(), "heF9BATUfWuISyO8");
        this.q = System.currentTimeMillis();
        this.b = !this.mContext.getSharedPreferences("com.mobileapptracking", 0).getString("mat_referrer", i.a).equals(i.a);
        this.n = false;
        this.o = true;
        this.initialized = false;
        this.isRegistered = false;
        this.k = false;
        this.p = false;
        this.eventQueue = new MATEventQueue(this.mContext, this);
        dumpQueue();
        this.networkStateReceiver = new h(this);
        if (this.isRegistered) {
            try {
                this.mContext.unregisterReceiver(this.networkStateReceiver);
            } catch (IllegalArgumentException e) {
            }
            this.isRegistered = false;
        }
        this.mContext.registerReceiver(this.networkStateReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
        this.isRegistered = true;
        this.initialized = true;
    }

    protected boolean makeRequest(String link, String data, JSONObject postBody) {
        boolean z = false;
        if (this.k) {
            Log.d("MobileAppTracker", "Sending event to server...");
        }
        JSONObject a = g.a(link + "&data=" + f.a(data, this.i), postBody, this.k);
        if (a == null) {
            if (this.j == null) {
                return true;
            }
            this.j.didFailWithError(a);
            return true;
        } else if (a.has(Response.SUCCESS_KEY)) {
            if (this.j != null) {
                try {
                    if (a.getString(Response.SUCCESS_KEY).equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)) {
                        z = true;
                    }
                    if (z) {
                        this.j.didSucceedWithData(a);
                    } else {
                        this.j.didFailWithError(a);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            try {
                if (!a.getString("site_event_type").equals("open")) {
                    return true;
                }
                String string = a.getString("log_id");
                if (getOpenLogId().equals(i.a)) {
                    this.params.setOpenLogId(string);
                }
                this.params.setLastOpenLogId(string);
                return true;
            } catch (JSONException e2) {
                return true;
            }
        } else {
            if (this.k) {
                Log.d("MobileAppTracker", "Request failed, event will remain in queue");
            }
            return false;
        }
    }

    public void measureEvent(int eventId) {
        measureEvent(new MATEvent(eventId));
    }

    public void measureEvent(MATEvent eventData) {
        this.pubQueue.execute(new s(this, eventData));
    }

    public void measureEvent(String eventName) {
        measureEvent(new MATEvent(eventName));
    }

    public void measureSession() {
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences("com.mobileapptracking", 0);
        if (!sharedPreferences.contains("mat_installed")) {
            sharedPreferences.edit().putBoolean("mat_installed", true).commit();
            this.n = true;
        }
        this.c = false;
        measureEvent(new MATEvent("session"));
    }

    public void setAdvertiserId(String advertiserId) {
        this.pubQueue.execute(new ad(this, advertiserId));
    }

    public void setAge(int age) {
        this.pubQueue.execute(new an(this, age));
    }

    public void setAllowDuplicates(boolean allow) {
        this.pubQueue.execute(new aj(this, allow));
        if (allow) {
            new Handler(Looper.getMainLooper()).post(new ak(this));
        }
    }

    public void setAltitude(double altitude) {
        this.pubQueue.execute(new ao(this, altitude));
    }

    public void setAndroidId(String androidId) {
        b bVar = this.f;
        b.c(androidId);
        if (this.params != null) {
            this.params.setAndroidId(androidId);
        }
        if (this.l) {
            a(this.m);
        }
    }

    public void setAndroidIdMd5(String androidIdMd5) {
        this.pubQueue.execute(new ap(this, androidIdMd5));
    }

    public void setAndroidIdSha1(String androidIdSha1) {
        this.pubQueue.execute(new aq(this, androidIdSha1));
    }

    public void setAndroidIdSha256(String androidIdSha256) {
        this.pubQueue.execute(new ar(this, androidIdSha256));
    }

    public void setAppAdTrackingEnabled(boolean adTrackingEnabled) {
        this.pubQueue.execute(new as(this, adTrackingEnabled));
    }

    public void setCurrencyCode(String currency_code) {
        this.pubQueue.execute(new i(this, currency_code));
    }

    public void setDebugMode(boolean debug) {
        this.k = debug;
        if (debug) {
            new Handler(Looper.getMainLooper()).post(new al(this));
        }
    }

    public void setDeferredDeeplink(boolean enableDeferredDeeplink, int timeout) {
        this.l = enableDeferredDeeplink;
        this.m = timeout;
    }

    public void setDeviceBrand(String deviceBrand) {
        this.pubQueue.execute(new j(this, deviceBrand));
    }

    public void setDeviceId(String deviceId) {
        this.pubQueue.execute(new k(this, deviceId));
    }

    public void setDeviceModel(String deviceModel) {
        this.pubQueue.execute(new l(this, deviceModel));
    }

    public void setEmailCollection(boolean collectEmail) {
        this.pubQueue.execute(new am(this, collectEmail));
    }

    public void setExistingUser(boolean existing) {
        this.pubQueue.execute(new m(this, existing));
    }

    public void setFacebookEventLogging(boolean logging, Context context, boolean limitEventAndDataUsage) {
        this.p = logging;
        if (logging) {
            c.a(context, limitEventAndDataUsage);
        }
    }

    public void setFacebookUserId(String fb_user_id) {
        this.pubQueue.execute(new n(this, fb_user_id));
    }

    public void setGender(int gender) {
        this.pubQueue.execute(new o(this, gender));
    }

    public void setGoogleAdvertisingId(String adId, boolean isLATEnabled) {
        int i = isLATEnabled ? GENDER_FEMALE : 0;
        b bVar = this.f;
        b.a(adId, i);
        if (this.params != null) {
            this.params.setGoogleAdvertisingId(adId);
            this.params.setGoogleAdTrackingLimited(Integer.toString(i));
        }
        this.a = true;
        if (this.b && !this.c) {
            synchronized (this.d) {
                this.d.notifyAll();
                this.c = true;
            }
        }
        if (this.l) {
            a(this.m);
        }
    }

    public void setGoogleUserId(String google_user_id) {
        this.pubQueue.execute(new p(this, google_user_id));
    }

    public void setInstallReferrer(String referrer) {
        this.b = true;
        this.r = System.currentTimeMillis();
        if (this.params != null) {
            this.params.setReferrerDelay(this.r - this.q);
        }
        this.pubQueue.execute(new q(this, referrer));
    }

    public void setIsPayingUser(boolean isPayingUser) {
        this.pubQueue.execute(new r(this, isPayingUser));
    }

    public void setLatitude(double latitude) {
        this.pubQueue.execute(new t(this, latitude));
    }

    public void setLocation(Location location) {
        this.pubQueue.execute(new u(this, location));
    }

    public void setLongitude(double longitude) {
        this.pubQueue.execute(new v(this, longitude));
    }

    public void setMATResponse(MATResponse response) {
        this.j = response;
        b bVar = this.f;
        b.a(response);
    }

    public void setMacAddress(String macAddress) {
        this.pubQueue.execute(new w(this, macAddress));
    }

    public void setOsVersion(String osVersion) {
        this.pubQueue.execute(new x(this, osVersion));
    }

    public void setPackageName(String packageName) {
        b bVar = this.f;
        b.a(packageName);
        this.pubQueue.execute(new y(this, packageName));
    }

    public void setPhoneNumber(String phoneNumber) {
        this.pubQueue.execute(new z(this, phoneNumber));
    }

    public void setPluginName(String plugin_name) {
        if (Arrays.asList(a.a).contains(plugin_name)) {
            this.pubQueue.execute(new ai(this, plugin_name));
        } else if (this.k) {
            throw new IllegalArgumentException("Plugin name not acceptable");
        }
    }

    public void setPreloadedApp(MATPreloadData preloadData) {
        this.g = preloadData;
    }

    public void setReferralSources(Activity act) {
        this.pubQueue.execute(new aa(this, act));
    }

    public void setSiteId(String siteId) {
        this.pubQueue.execute(new ab(this, siteId));
    }

    public void setTRUSTeId(String tpid) {
        this.pubQueue.execute(new ac(this, tpid));
    }

    public void setTwitterUserId(String twitter_user_id) {
        this.pubQueue.execute(new ae(this, twitter_user_id));
    }

    public void setUserEmail(String userEmail) {
        this.pubQueue.execute(new af(this, userEmail));
    }

    public void setUserId(String userId) {
        this.pubQueue.execute(new ag(this, userId));
    }

    public void setUserName(String userName) {
        this.pubQueue.execute(new ah(this, userName));
    }
}
