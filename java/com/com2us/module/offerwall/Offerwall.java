package com.com2us.module.offerwall;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import com.com2us.module.manager.AppStateAdapter;
import com.com2us.module.manager.Modulable;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.view.SurfaceViewWrapper;
import com.com2us.peppermint.PeppermintConstant;
import com.google.android.gcm.GCMConstants;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONException;
import org.json.JSONObject;

public class Offerwall extends AppStateAdapter implements Modulable, Constants {
    public static final int OFFERWALL_ACTIVATION = -14;
    public static final int OFFERWALL_CLOSE = -12;
    public static final int OFFERWALL_DEACTIVATION = -15;
    public static final int OFFERWALL_NETWORK_DISCONNECT = -13;
    public static final int OFFERWALL_OPEN = -11;
    public static final int OFFERWALL_REWARD_CANCEL = 1;
    public static final int OFFERWALL_REWARD_FINISH = 2;
    public static final int OFFERWALL_REWARD_IN_PROGRESS = 3;
    public static final int OFFERWALL_REWARD_SUCCESS = 4;
    private static Offerwall instance = null;
    private static boolean isNetworkProgressing = false;
    public static Logger logger = null;
    private Activity activity;
    private String appIdForIdentity;
    private Bitmap bitmapPhone;
    private Bitmap bitmapTablet;
    private String cookieDomain;
    private CookieManager cookieManager;
    private CookieSyncManager cookieSyncManager;
    private OfferwallDialog dialog;
    private boolean isInitialized;
    private boolean isShowingOfferWall;
    private boolean isSingleGame;
    private boolean isUsingStaging;
    private OfferwallCB mCB;
    private OfferwallCBforMercury mCBforMercury;
    private int mNativeCB;
    private int mNativeRewardCB;
    private OfferwallRewardCB mRewardCB;
    private OfferwallNetwork network;
    private Thread networkRequestThread;
    private boolean rewardFlag;
    private int rewardResult;
    private WebView webView;

    public interface OfferwallCBforMercury {
        void offerwallCallBackforMercury(int i);
    }

    public interface OfferwallCB {
        void offerwallCallBack(int i);
    }

    public interface OfferwallRewardCB {
        void offerwallRewardCallBack(int i, String str, int i2, String str2, String str3, int i3);
    }

    public native void nativeOfferwallCallBack(int i, int i2);

    public native void nativeOfferwallInitialize();

    public native void nativeOfferwallRewardCallBack(int i, int i2, String str, int i3, String str2, String str3, int i4);

    public Offerwall(Activity _activity) {
        this.activity = null;
        this.appIdForIdentity = null;
        this.cookieSyncManager = null;
        this.cookieManager = null;
        this.network = null;
        this.mCBforMercury = null;
        this.mCB = null;
        this.mRewardCB = null;
        this.mNativeCB = 0;
        this.mNativeRewardCB = 0;
        this.isUsingStaging = false;
        this.isSingleGame = false;
        this.webView = null;
        this.cookieDomain = null;
        this.isShowingOfferWall = false;
        this.isInitialized = false;
        this.rewardFlag = false;
        this.rewardResult = 0;
        this.bitmapPhone = null;
        this.bitmapTablet = null;
        this.networkRequestThread = null;
        this.activity = _activity;
        instance = this;
        logger = LoggerGroup.createLogger(Constants.TAG, this.activity);
        OfferwallData.create(this.activity);
    }

    public Offerwall(Activity _activity, SurfaceViewWrapper view) {
        this(_activity);
        nativeOfferwallInitialize();
    }

    public static Offerwall getInstance() {
        return instance;
    }

    private void initialize(String uid) {
        int i = OFFERWALL_ACTIVATION;
        logger.d("offerwall initialize(uid)");
        this.network = new OfferwallNetwork();
        OfferwallData.setUID(uid);
        OfferwallProperties.loadProperties(this.activity);
        if (TextUtils.isEmpty(OfferwallProperties.getProperty("activation"))) {
            saveOfferwallState(a.d);
        }
        if (OfferwallProperties.getProperty("rewardResult") != null) {
            this.rewardResult = Integer.parseInt(OfferwallProperties.getProperty("rewardResult"));
        }
        this.cookieSyncManager = CookieSyncManager.createInstance(this.activity);
        this.cookieManager = CookieManager.getInstance();
        this.cookieManager.setAcceptCookie(true);
        try {
            this.bitmapPhone = BitmapFactory.decodeStream(this.activity.getAssets().open("common/offerwall/btn_native_X.png"));
            this.bitmapTablet = BitmapFactory.decodeStream(this.activity.getAssets().open("common/offerwall/btn_native_X_tablet.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.isUsingStaging) {
            this.network.setServer(OfferwallDefine.OFFERWALL_STAGING_SERVER);
            this.cookieDomain = OfferwallDefine.OFFERWALL_WEB_STAGING_SERVER;
        } else {
            this.network.setServer(OfferwallDefine.OFFERWALL_REAL_SERVER);
            this.cookieDomain = OfferwallDefine.OFFERWALL_WEB_REAL_SERVER;
        }
        this.isInitialized = true;
        Log.d(Constants.TAG, "initialize. call reauestNetwork");
        requestNetwork();
        if (getOfferwallState() != OFFERWALL_ACTIVATION) {
            i = OFFERWALL_DEACTIVATION;
        }
        callback(i);
    }

    public void initialize(String uid, boolean _isUsingStaging, OfferwallCB callback) {
        logger.d("offerwall initialize(uid, isStaging, callback)");
        this.mCB = callback;
        this.isUsingStaging = _isUsingStaging;
        initialize(uid);
    }

    public void initializeEx(String uid, boolean _isUsingStaging, OfferwallCB callback, OfferwallRewardCB rewardCallBack) {
        logger.d("offerwall initializeEx(uid, isStaging, callback, rewardCallback)");
        this.mCB = callback;
        this.mRewardCB = rewardCallBack;
        this.isUsingStaging = _isUsingStaging;
        initialize(uid);
    }

    public void setCBforMercury(OfferwallCBforMercury CBforMercury) {
        this.mCBforMercury = CBforMercury;
    }

    public void nativeInitialize(String uid, int _isUsingStaging, int offerwallCallback) {
        logger.d("offerwall nativeInitialize(uid, isStaging, callback)");
        this.mNativeCB = offerwallCallback;
        if (OFFERWALL_REWARD_CANCEL == _isUsingStaging) {
            this.isUsingStaging = true;
        } else {
            this.isUsingStaging = false;
        }
        initialize(uid);
    }

    public void nativeInitializeEx(String uid, int _isUsingStaging, int offerwallCallback, int rewardCallback) {
        this.mNativeCB = offerwallCallback;
        this.mNativeRewardCB = rewardCallback;
        if (OFFERWALL_REWARD_CANCEL == _isUsingStaging) {
            this.isUsingStaging = true;
        } else {
            this.isUsingStaging = false;
        }
        initialize(uid);
    }

    private void callback(int callbackMsg) {
        if (this.mCBforMercury != null) {
            this.mCBforMercury.offerwallCallBackforMercury(callbackMsg);
        } else if (this.mCB == null && this.mNativeCB != 0) {
            nativeOfferwallCallBack(this.mNativeCB, callbackMsg);
        } else if (this.mCB == null || this.mNativeCB != 0) {
            logger.d(Constants.TAG, "Offerwall-Callback didn't register");
        } else {
            this.mCB.offerwallCallBack(callbackMsg);
        }
    }

    private void rewardCallback(String error, String errorMessage, String result, String eventID, String assetCode, String assetAmount) {
        if (this.mRewardCB == null && this.mNativeRewardCB != 0) {
            nativeOfferwallRewardCallBack(this.mNativeRewardCB, Integer.valueOf(error).intValue(), errorMessage, Integer.valueOf(result).intValue(), eventID, assetCode, Integer.valueOf(assetAmount).intValue());
        } else if (this.mRewardCB == null || this.mNativeRewardCB != 0) {
            logger.d(Constants.TAG, "Reward-Callback didn't register");
        } else {
            this.mRewardCB.offerwallRewardCallBack(Integer.valueOf(error).intValue(), errorMessage, Integer.valueOf(result).intValue(), eventID, assetCode, Integer.valueOf(assetAmount).intValue());
        }
    }

    private void requestNetwork() {
        if (this.networkRequestThread == null || !this.networkRequestThread.isAlive()) {
            this.networkRequestThread = new Thread() {
                public void run() {
                    JSONObject jSONObject;
                    JSONException e;
                    Offerwall.isNetworkProgressing = true;
                    Offerwall.logger.d(Constants.TAG, "requestNetwork run");
                    Object obj = Offerwall.this.network.processNetworkTask();
                    Offerwall.logger.d(Constants.TAG, "Offerwall json String :" + obj);
                    if (obj != null) {
                        try {
                            JSONObject jsonObj = new JSONObject((String) obj);
                            try {
                                Offerwall.logger.d(Constants.TAG, "json : " + jsonObj.toString());
                                Offerwall.this.rewardResult = jsonObj.getInt(PeppermintConstant.JSON_KEY_RESULT);
                                Offerwall.this.saveOfferwallState(jsonObj.getString("activation"));
                                String[] rewardInfo = new String[]{jsonObj.getString(GCMConstants.EXTRA_ERROR), jsonObj.getString("errormsg"), jsonObj.getString(PeppermintConstant.JSON_KEY_RESULT), jsonObj.getString("eventid"), jsonObj.getString("asset_code"), jsonObj.getString("asset_amount")};
                                Offerwall.this.saveRewardData(rewardInfo);
                                Offerwall.this.rewardCallback(rewardInfo[0], rewardInfo[Offerwall.OFFERWALL_REWARD_CANCEL], rewardInfo[Offerwall.OFFERWALL_REWARD_FINISH], rewardInfo[Offerwall.OFFERWALL_REWARD_IN_PROGRESS], rewardInfo[Offerwall.OFFERWALL_REWARD_SUCCESS], rewardInfo[5]);
                                if (Offerwall.this.isShowingOfferWall) {
                                    Offerwall.this.dialog.loadURL();
                                    jSONObject = jsonObj;
                                }
                            } catch (JSONException e2) {
                                e = e2;
                                jSONObject = jsonObj;
                                e.printStackTrace();
                                Offerwall.isNetworkProgressing = false;
                            }
                        } catch (JSONException e3) {
                            e = e3;
                            e.printStackTrace();
                            Offerwall.isNetworkProgressing = false;
                        }
                    }
                    Offerwall.logger.d(Constants.TAG, "Error: Offerwall network disconnect");
                    Offerwall.isNetworkProgressing = false;
                }
            };
            this.networkRequestThread.start();
            return;
        }
        logger.d(Constants.TAG, "network thread already running");
    }

    private void saveRewardData(String[] rewardInfo) {
        OfferwallProperties.setProperty("rewardEventID", rewardInfo[OFFERWALL_REWARD_IN_PROGRESS]);
        OfferwallProperties.storeProperties(this.activity);
        logger.d(Constants.TAG, "saved reward data in file");
        OfferwallData.setAwardResult(rewardInfo[OFFERWALL_REWARD_FINISH]);
        OfferwallData.setEventID(rewardInfo[OFFERWALL_REWARD_IN_PROGRESS]);
        OfferwallData.setAssetCode(rewardInfo[OFFERWALL_REWARD_SUCCESS]);
        OfferwallData.setAssetAmount(rewardInfo[5]);
        logger.d(Constants.TAG, "saved reward data in cookie");
        logger.d(Constants.TAG, "Reward Result\n error: " + rewardInfo[0] + "\n errormsg: " + rewardInfo[OFFERWALL_REWARD_CANCEL] + "\n result:" + rewardInfo[OFFERWALL_REWARD_FINISH] + "\n eventID: " + rewardInfo[OFFERWALL_REWARD_IN_PROGRESS] + "\n assetcode: " + rewardInfo[OFFERWALL_REWARD_SUCCESS] + "\n assetaomunt: " + rewardInfo[5]);
    }

    private void saveOfferwallState(String activationInfo) {
        if (activationInfo == null) {
            logger.d(Constants.TAG, "Error: activationInfo string is null");
            return;
        }
        OfferwallProperties.setProperty("activation", activationInfo);
        OfferwallProperties.storeProperties(this.activity);
        logger.d(Constants.TAG, "saved activation info in file. Value is " + activationInfo);
    }

    public int getOfferwallState() {
        String offerwallStateString = OfferwallProperties.getProperty("activation");
        if (isNetworkProgressing) {
            return OFFERWALL_REWARD_FINISH;
        }
        if (offerwallStateString == null) {
            logger.d(Constants.TAG, "Error: Failed to get activation value from property");
            return -1;
        }
        logger.d("offerwallStateString : " + offerwallStateString);
        int offerwallState = Integer.parseInt(offerwallStateString);
        if (offerwallState == 0) {
            return OFFERWALL_DEACTIVATION;
        }
        if (offerwallState == OFFERWALL_REWARD_CANCEL) {
            return OFFERWALL_ACTIVATION;
        }
        return offerwallState;
    }

    public void rewardFinish() throws JSONException {
        logger.d(Constants.TAG, "Called rewardFinish");
        new Thread() {
            public void run() {
                JSONObject jSONObject;
                JSONException e;
                Object obj = Offerwall.this.network.processNetworkRewardTask();
                if (obj != null) {
                    try {
                        JSONObject jsonObj = new JSONObject((String) obj);
                        try {
                            Offerwall.logger.d(Constants.TAG, "rewardFinish Result: " + jsonObj.toString());
                            Offerwall.this.saveRewardData(new String[]{jsonObj.getString(GCMConstants.EXTRA_ERROR), jsonObj.getString("errormsg"), a.e, a.d, a.d, a.d});
                            jSONObject = jsonObj;
                            return;
                        } catch (JSONException e2) {
                            e = e2;
                            jSONObject = jsonObj;
                            e.printStackTrace();
                            return;
                        }
                    } catch (JSONException e3) {
                        e = e3;
                        e.printStackTrace();
                        return;
                    }
                }
                Offerwall.this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Offerwall.this.callback(Offerwall.OFFERWALL_NETWORK_DISCONNECT);
                    }
                });
            }
        }.start();
    }

    private void syncCookie() {
        logger.d(Constants.TAG, "syncCookie");
        this.cookieManager.setCookie(this.cookieDomain, "oq_appid=" + OfferwallData.get(OFFERWALL_REWARD_CANCEL));
        this.cookieManager.setCookie(this.cookieDomain, "oq_mac=" + OfferwallData.get(OFFERWALL_REWARD_FINISH));
        this.cookieManager.setCookie(this.cookieDomain, "oq_lan=" + OfferwallData.get(OFFERWALL_REWARD_IN_PROGRESS));
        this.cookieManager.setCookie(this.cookieDomain, "oq_con=" + OfferwallData.get(OFFERWALL_REWARD_SUCCESS));
        this.cookieManager.setCookie(this.cookieDomain, "oq_devicetype=" + OfferwallData.get(5));
        this.cookieManager.setCookie(this.cookieDomain, "oq_osver=" + OfferwallData.get(6));
        this.cookieManager.setCookie(this.cookieDomain, "oq_libver=" + Constants.Version);
        this.cookieManager.setCookie(this.cookieDomain, "oq_appver=" + OfferwallData.get(7));
        this.cookieManager.setCookie(this.cookieDomain, "oq_appvercode=" + OfferwallData.get(8));
        this.cookieManager.setCookie(this.cookieDomain, "oq_uid=" + OfferwallData.get(9));
        this.cookieManager.setCookie(this.cookieDomain, "oq_did=" + OfferwallData.get(10));
        String vid = OfferwallData.get(12);
        if (vid == null) {
            this.cookieManager.setCookie(this.cookieDomain, "oq_vid=-703");
        } else {
            this.cookieManager.setCookie(this.cookieDomain, "oq_vid=" + vid);
        }
        this.cookieManager.setCookie(this.cookieDomain, "oq_additionalInfo=" + OfferwallData.get(17));
        this.cookieManager.setCookie(this.cookieDomain, "oq_mcc=" + OfferwallData.get(18));
        this.cookieManager.setCookie(this.cookieDomain, "oq_mnc=" + OfferwallData.get(19));
        this.cookieManager.setCookie(this.cookieDomain, "oq_path=" + OfferwallData.get(20));
        this.cookieManager.setCookie(this.cookieDomain, "oq_eventID=" + OfferwallData.get(13));
        this.cookieManager.setCookie(this.cookieDomain, "oq_assetcode=" + OfferwallData.get(14));
        this.cookieManager.setCookie(this.cookieDomain, "oq_assetamount=" + OfferwallData.get(15));
        this.cookieManager.setCookie(this.cookieDomain, "oq_result=" + OfferwallData.get(16));
        this.cookieSyncManager.sync();
        logger.d(Constants.TAG, "get cookie" + this.cookieManager.getCookie(this.cookieDomain));
    }

    public void show() {
        logger.d("dialog show()");
        OfferwallData.setPath(OFFERWALL_REWARD_CANCEL);
        show(OfferwallData.get(9), i.a);
    }

    public void show(String additionalInfo) {
        logger.d("dialog show(additionalInfo)");
        OfferwallData.setPath(OFFERWALL_REWARD_CANCEL);
        show(OfferwallData.get(9), additionalInfo);
    }

    public void showEx(String additionalInfo) {
        logger.d("dialog showEx(addtionalInfo)");
        OfferwallData.setPath(OFFERWALL_REWARD_CANCEL);
        show(OfferwallData.get(9), additionalInfo);
    }

    public void showExforMercury(String addtionalInfo) {
        logger.d("dialog showExforMercury(addtionalInfo)");
        OfferwallData.setPath(OFFERWALL_REWARD_FINISH);
        show(OfferwallData.get(9), addtionalInfo);
    }

    private void show(String uid, String additionalInfo) {
        logger.d("dialog show(uid, additionalInfo)");
        if (additionalInfo == null) {
            additionalInfo = i.a;
        }
        if (uid == null) {
            uid = i.a;
        }
        Log.d(Constants.TAG, "show. call reauestNetwork");
        requestNetwork();
        OfferwallData.setUID(uid);
        OfferwallData.setAdditionalInfo(additionalInfo);
        logger.d("dialog show(addInfo : " + additionalInfo + ")");
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                String serverURL;
                Offerwall.this.syncCookie();
                Offerwall.this.callback(Offerwall.OFFERWALL_OPEN);
                if (Offerwall.this.isUsingStaging) {
                    serverURL = OfferwallDefine.OFFERWALL_WEB_STAGING_SERVER;
                } else {
                    serverURL = OfferwallDefine.OFFERWALL_WEB_REAL_SERVER;
                }
                Offerwall.this.dialog = new OfferwallDialog(Offerwall.this, Offerwall.this.activity, serverURL, Offerwall.this.bitmapPhone, Offerwall.this.bitmapTablet);
                Offerwall.this.dialog.show();
                Offerwall.this.isShowingOfferWall = true;
            }
        });
    }

    public void closeDialog() {
        this.isShowingOfferWall = false;
        callback(OFFERWALL_CLOSE);
    }

    public boolean setRewardFlag(int reward) {
        logger.d(Constants.TAG, "setRewardFlag : " + reward);
        if (reward == 0) {
            this.rewardFlag = false;
            return true;
        } else if (OFFERWALL_REWARD_CANCEL != reward) {
            return false;
        } else {
            this.rewardFlag = true;
            return true;
        }
    }

    public String getName() {
        return Constants.TAG;
    }

    public String getVersion() {
        return Version;
    }

    public void setLogged(boolean b) {
        logger.setLogged(b);
    }

    public void setAppIdForIdentity(String appid) {
        this.appIdForIdentity = appid;
        OfferwallData.setAppID(this.appIdForIdentity);
    }

    public void destroy() {
        if (this.dialog != null) {
            this.dialog.destroy();
        }
        if (this.bitmapPhone != null) {
            this.bitmapPhone.recycle();
            this.bitmapPhone = null;
        }
        if (this.bitmapTablet != null) {
            this.bitmapTablet.recycle();
            this.bitmapTablet = null;
        }
    }

    public String[] getPermission() {
        return PERMISSION;
    }

    public void onActivityResumed() {
        if (this.isInitialized) {
            this.cookieSyncManager.startSync();
            Log.d(Constants.TAG, "onActivityResumed. call reauestNetwork");
            requestNetwork();
        }
    }

    public void onActivityPaused() {
        if (this.isInitialized) {
            this.cookieSyncManager.stopSync();
        }
    }
}
