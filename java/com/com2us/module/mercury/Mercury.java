package com.com2us.module.mercury;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.com2us.module.manager.AppStateAdapter;
import com.com2us.module.manager.Modulable;
import com.com2us.module.offerwall.Offerwall;
import com.com2us.module.offerwall.Offerwall.OfferwallCBforMercury;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.view.SurfaceViewWrapper;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.facebook.Response;
import com.google.android.gcm.GCMConstants;
import java.util.Calendar;
import jp.co.cyberz.fox.a.a.i;
import jp.co.cyberz.fox.notify.a;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mercury extends AppStateAdapter implements Modulable, Constants {
    public static final int MERCURY_BADGE_FOR_ALL = 0;
    public static final int MERCURY_BADGE_FOR_CUSTOM_BASE = 100000;
    public static final int MERCURY_BADGE_FOR_CUSTOM_MAX = 300000;
    public static final int MERCURY_BADGE_FOR_CUSTOM_NEW = 100000;
    public static final int MERCURY_BADGE_FOR_FULL_BANNER = -15;
    public static final int MERCURY_BADGE_FOR_MAIN_PAGE = -16;
    public static final int MERCURY_BADGE_FOR_NOTICE_ONLY = -17;
    public static final int MERCURY_BADGE_TYPE_MAX = -21;
    public static final int MERCURY_BADGE_TYPE_NEW = -20;
    public static final int MERCURY_CUSTOM_CLOSED = -13;
    public static final int MERCURY_CUSTOM_OPENED = -12;
    public static final int MERCURY_FAILED = -1;
    public static final int MERCURY_FULL_BANNER_CLOSED = -7;
    public static final int MERCURY_FULL_BANNER_OPENED = -6;
    public static final int MERCURY_GET_BADGE_LIST = -10;
    public static final int MERCURY_MAIN_PAGE_CLOSED = -9;
    public static final int MERCURY_MAIN_PAGE_OPENED = -8;
    public static final int MERCURY_NETWORK_DISCONNECTED = -14;
    public static final int MERCURY_NOTICE_ONLY_CLOSED = -11;
    public static final int MERCURY_NOTICE_ONLY_OPENED = -10;
    public static final int MERCURY_REQUEST_BADGE_LIST = -2;
    public static final int MERCURY_REQUEST_SHOW = -1;
    public static final int MERCURY_SHOW_CUSTOM_BASE = 100000;
    public static final int MERCURY_SHOW_EVENT = -11;
    public static final int MERCURY_SHOW_FORCED_CUSTOM_BASE = 200000;
    public static final int MERCURY_SHOW_FORCED_NOTICE_BOTTOM = -15;
    public static final int MERCURY_SHOW_FORCED_NOTICE_ONLY = -17;
    public static final int MERCURY_SHOW_FORCED_NOTICE_TOP = -14;
    public static final int MERCURY_SHOW_NOTICE_BOTTOM = -13;
    public static final int MERCURY_SHOW_NOTICE_ONLY = -16;
    public static final int MERCURY_SHOW_NOTICE_TOP = -12;
    public static final int MERCURY_SUCCESS = 0;
    public static UnitDurationListener durationForcedShowListener = null;
    public static UnitDurationListener durationShowListener = null;
    public static Logger logger = null;
    private static String savedUserID = null;
    private Activity activity = null;
    private String appIdForIdentity = null;
    private boolean bIgnoreForcedFlag = false;
    private boolean bShowRequested = false;
    private Bitmap bitmapClose = null;
    private Bitmap bitmapWebViewBorder0 = null;
    private Bitmap bitmapWebViewBorder1d = null;
    private Bitmap bitmapWebViewBorder1s = null;
    private Bitmap bitmapWebViewBorder2 = null;
    private CookieManager cookieManager = null;
    private CookieSyncManager cookieSyncManager = null;
    private boolean isShowing = false;
    private boolean isUsingStaging = false;
    private JSONObject jsonBadgeObj = null;
    private MercuryCB mCB = null;
    private MercuryCBWithData mCBWithData = null;
    private int mNativeCB = MERCURY_BADGE_FOR_ALL;
    private int mNativeCBWithData = MERCURY_BADGE_FOR_ALL;
    private MercuryDialog mercuryDialog = null;
    private int mercuryLayoutGeneratedCount = MERCURY_BADGE_FOR_ALL;
    private String message = null;
    private MercuryNetwork network = null;
    private Offerwall offerwall = null;
    private PropertyUtil propertyUtil = null;
    private int requestedCustomViewID = MERCURY_BADGE_FOR_ALL;
    private int webViewCount = MERCURY_BADGE_FOR_ALL;
    private int webViewIndexLoaded = MERCURY_BADGE_FOR_ALL;
    private JSONArray webViewJSONArr;
    private int webViewShow = MERCURY_BADGE_FOR_ALL;
    private int webViewShowType = MERCURY_BADGE_FOR_ALL;

    public interface MercuryCB {
        void mercuryCallBack(int i);
    }

    public interface MercuryCBWithData {
        void mercuryCallBackWithData(int i, String str);
    }

    public class OfferwallCBs implements OfferwallCBforMercury {
        public void offerwallCallBackforMercury(int result) {
            switch (result) {
                case Mercury.MERCURY_SHOW_NOTICE_TOP /*-12*/:
                    Mercury.this.close();
                    return;
                default:
                    return;
            }
        }
    }

    interface OnWebViewFinishedListener {
        void onWebViewFinished();
    }

    public native void nativeMercuryCallBack(int i);

    public native void nativeMercuryCallBackWithData(int i, String str);

    public native void nativeMercuryInitialize();

    public Mercury(Activity activity) {
        initialize(activity);
    }

    public Mercury(Activity activity, SurfaceViewWrapper view) {
        initialize(activity);
        nativeMercuryInitialize();
    }

    public int badgeTypeToBadgeType(String badgeType) {
        if (badgeType.equals("new")) {
            return MERCURY_BADGE_TYPE_NEW;
        }
        return MERCURY_BADGE_FOR_ALL;
    }

    public int showTypeToBadgeForWhat(int showType) {
        switch (showType) {
            case MERCURY_SHOW_FORCED_NOTICE_ONLY /*-17*/:
            case MERCURY_SHOW_NOTICE_ONLY /*-16*/:
                return MERCURY_SHOW_FORCED_NOTICE_ONLY;
            case MERCURY_SHOW_FORCED_NOTICE_BOTTOM /*-15*/:
            case MERCURY_SHOW_FORCED_NOTICE_TOP /*-14*/:
            case MERCURY_SHOW_NOTICE_BOTTOM /*-13*/:
            case MERCURY_SHOW_NOTICE_TOP /*-12*/:
                return MERCURY_SHOW_NOTICE_ONLY;
            case MERCURY_SHOW_EVENT /*-11*/:
                return MERCURY_SHOW_FORCED_NOTICE_BOTTOM;
            default:
                return (showType < MERCURY_SHOW_CUSTOM_BASE || showType >= MERCURY_BADGE_FOR_CUSTOM_MAX) ? MERCURY_BADGE_FOR_ALL : showType;
        }
    }

    public int actToBadgeForWhat(String act, int showType) {
        if (act.equals(MercuryDefine.ACTION_INIT)) {
            return MERCURY_SHOW_FORCED_NOTICE_BOTTOM;
        }
        if (act.equals(MercuryDefine.ACTION_BUTTON)) {
            return MERCURY_SHOW_NOTICE_ONLY;
        }
        if (act.equals(MercuryDefine.TYPE_NOTICE)) {
            return MERCURY_SHOW_FORCED_NOTICE_ONLY;
        }
        return showType;
    }

    public static void drchkInitVariables() {
    }

    public static void setDurationShowListener(UnitDurationListener listener) {
        durationShowListener = listener;
    }

    public static void setDurationForcedShowListener(UnitDurationListener listener) {
        durationForcedShowListener = listener;
    }

    private void initialize(Activity _activity) {
        this.activity = _activity;
        this.propertyUtil = PropertyUtil.getInstance(_activity.getApplicationContext());
        this.propertyUtil.loadProperty();
        logger = LoggerGroup.createLogger(Constants.TAG, this.activity);
        this.network = new MercuryNetwork();
        MercuryProperties.loadProperties(this.activity);
        MercuryData.create(this.activity);
        this.cookieSyncManager = CookieSyncManager.createInstance(this.activity);
        this.cookieSyncManager.startSync();
        this.cookieManager = CookieManager.getInstance();
        this.cookieManager.setAcceptCookie(true);
        if (!deleteExpiredPID()) {
            logger.d(Constants.TAG, " You don't have to delete pid list");
        }
        try {
            this.bitmapClose = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/btn_native_X.png"));
            switch (this.activity.getRequestedOrientation()) {
                case MERCURY_BADGE_FOR_ALL /*0*/:
                case f.aL /*6*/:
                    this.bitmapWebViewBorder0 = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/0_wide_outline.png"));
                    this.bitmapWebViewBorder1d = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/1_wide_box_check_d.png"));
                    this.bitmapWebViewBorder1s = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/1_wide_box_check_s.png"));
                    this.bitmapWebViewBorder2 = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/2_wide_box_close.png"));
                    return;
                case o.a /*1*/:
                case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
                    this.bitmapWebViewBorder0 = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/0_length_outline.png"));
                    this.bitmapWebViewBorder1d = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/1_length_box_check_d.png"));
                    this.bitmapWebViewBorder1s = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/1_length_box_check_s.png"));
                    this.bitmapWebViewBorder2 = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/2_length_box_close.png"));
                    return;
                default:
                    if (this.activity.getResources().getConfiguration().orientation == 1) {
                        this.bitmapWebViewBorder0 = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/0_length_outline.png"));
                        this.bitmapWebViewBorder1d = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/1_length_box_check_d.png"));
                        this.bitmapWebViewBorder1s = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/1_length_box_check_s.png"));
                        this.bitmapWebViewBorder2 = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/2_length_box_close.png"));
                        return;
                    }
                    this.bitmapWebViewBorder0 = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/0_wide_outline.png"));
                    this.bitmapWebViewBorder1d = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/1_wide_box_check_d.png"));
                    this.bitmapWebViewBorder1s = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/1_wide_box_check_s.png"));
                    this.bitmapWebViewBorder2 = BitmapFactory.decodeStream(this.activity.getAssets().open("common/mercury/2_wide_box_close.png"));
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        e.printStackTrace();
    }

    public boolean hasOfferwallInstance() {
        this.offerwall = Offerwall.getInstance();
        if (this.offerwall != null) {
            return true;
        }
        return false;
    }

    public void showOfferwall() {
        this.offerwall = Offerwall.getInstance();
        if (this.offerwall != null) {
            this.offerwall.setCBforMercury(new OfferwallCBs());
            this.offerwall.showExforMercury(i.a);
        }
    }

    private void callback(int callbackMsg) {
        if (this.mCB == null && this.mNativeCB != 0) {
            nativeMercuryCallBack(callbackMsg);
        } else if (this.mCB != null && this.mNativeCB == 0) {
            this.mCB.mercuryCallBack(callbackMsg);
        }
    }

    private void callbackWithData(JSONObject response) {
        String error;
        int count;
        int i;
        this.bIgnoreForcedFlag = false;
        JSONObject customViewInfoData = new JSONObject();
        try {
            customViewInfoData.put("origin", response);
        } catch (JSONException e) {
            e.printStackTrace();
            if (this.mCBWithData == null && this.mNativeCBWithData != 0) {
                nativeMercuryCallBackWithData(MERCURY_REQUEST_SHOW, i.a);
            } else if (this.mCBWithData != null && this.mNativeCBWithData == 0) {
                this.mCBWithData.mercuryCallBackWithData(MERCURY_REQUEST_SHOW, i.a);
            }
        }
        int result = MERCURY_BADGE_FOR_ALL;
        JSONObject resultData = new JSONObject();
        try {
            error = response.getString(GCMConstants.EXTRA_ERROR);
            if (!error.equals(a.d)) {
                logger.d(Constants.TAG, "Error: Failed to read errorNum.");
                result = MERCURY_REQUEST_SHOW;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            error = i.a;
            result = MERCURY_REQUEST_SHOW;
        }
        try {
            String errorMsg = response.getString("errormsg");
            logger.d(Constants.TAG, "error: " + error + "errorMessage: " + errorMsg);
        } catch (JSONException e22) {
            e22.printStackTrace();
            result = MERCURY_REQUEST_SHOW;
        }
        try {
            JSONArray badgeInfoList = response.getJSONArray("badge");
            JSONObject requestedBadgeInfo = null;
            count = badgeInfoList.length();
            for (i = MERCURY_BADGE_FOR_ALL; i < count; i++) {
                if (badgeInfoList.getJSONObject(i).getInt("showtype") == this.requestedCustomViewID) {
                    requestedBadgeInfo = badgeInfoList.getJSONObject(i);
                    break;
                }
            }
            if (requestedBadgeInfo != null) {
                customViewInfoData.put("badge", requestedBadgeInfo);
            }
        } catch (JSONException e222) {
            e222.printStackTrace();
        }
        try {
            JSONArray webviewInfoList = response.getJSONArray("webview");
            String customViewURL = null;
            count = webviewInfoList.length();
            for (i = MERCURY_BADGE_FOR_ALL; i < count; i++) {
                if (webviewInfoList.getJSONObject(i).getInt("showtype") == this.requestedCustomViewID) {
                    customViewURL = webviewInfoList.getJSONObject(i).getString(a.g);
                    break;
                }
            }
            if (customViewURL != null) {
                customViewInfoData.put(a.g, customViewURL);
            }
        } catch (JSONException e2222) {
            e2222.printStackTrace();
        }
        try {
            resultData.put("customviewinfo", customViewInfoData);
        } catch (JSONException e22222) {
            e22222.printStackTrace();
        }
        this.requestedCustomViewID = MERCURY_BADGE_FOR_ALL;
        logger.d(Constants.TAG, "customViewInfo:: " + resultData.toString());
        if (this.mCBWithData == null && this.mNativeCBWithData != 0) {
            nativeMercuryCallBackWithData(result, resultData.toString());
        } else if (this.mCBWithData != null && this.mNativeCBWithData == 0) {
            this.mCBWithData.mercuryCallBackWithData(result, resultData.toString());
        }
    }

    private boolean deleteExpiredPID() {
        String pidList = MercuryProperties.getPIDList();
        if (pidList == null) {
            Log.d(Constants.TAG, "PID List isn't exist");
            return false;
        }
        String[] pidArray = pidList.split(i.b);
        String today = Integer.toString(Calendar.getInstance().get(5));
        int pidlistCount = pidArray.length;
        String savedOffAutoDate = i.a;
        for (int i = MERCURY_BADGE_FOR_ALL; i < pidlistCount; i++) {
            savedOffAutoDate = MercuryProperties.getProperty(pidArray[i]);
            if (!today.equals(savedOffAutoDate)) {
                MercuryProperties.removeProperties(pidArray[i]);
                Log.d(Constants.TAG, "deleted pid: " + pidArray[i]);
            }
            Log.d(Constants.TAG, "pid array[" + i + "]" + pidArray[i] + "Today:" + today + ", " + savedOffAutoDate);
        }
        MercuryProperties.storeProperties(this.activity);
        return true;
    }

    public int getWebViewCount() {
        return this.webViewCount;
    }

    public void resetWebViewCount() {
        this.webViewCount = MERCURY_BADGE_FOR_ALL;
    }

    public int getMercuryLayoutGeneratedCount() {
        return this.mercuryLayoutGeneratedCount;
    }

    public void setIsUsingStaging(boolean _isUsingStaging) {
        this.isUsingStaging = _isUsingStaging;
        MercuryData.setDID(this.activity, this.isUsingStaging);
        MercuryBridge.setUsingStaging(this.isUsingStaging);
    }

    public static void setUid(String uid) {
        MercuryData.set(10, uid);
    }

    public void setNativeIsUsingStaging(int _isUsingStaging) {
        if (1 == _isUsingStaging) {
            this.isUsingStaging = true;
        } else {
            this.isUsingStaging = false;
        }
    }

    public void setCallBack(MercuryCB callback) {
        logger.d(Constants.TAG, "setCallback");
        this.mCB = callback;
    }

    public void setNativeCallBack(int callback) {
        this.mNativeCB = callback;
    }

    private void showNoticeOnlyForced(String uid, String additionalInfo) {
        savedUserID = uid;
    }

    private void showNoticeOnly(String uid, String additionalInfo) {
        this.bIgnoreForcedFlag = true;
        showNoticeOnlyForced(uid, additionalInfo);
    }

    public void mercuryResetShowHistory() {
        new Thread() {
            public void run() {
                Mercury.this.propertyUtil.setProperty(String.valueOf(Mercury.MERCURY_SHOW_FORCED_NOTICE_BOTTOM), String.valueOf(Mercury.MERCURY_BADGE_FOR_ALL));
                Mercury.this.propertyUtil.setProperty(String.valueOf(Mercury.MERCURY_SHOW_NOTICE_ONLY), String.valueOf(Mercury.MERCURY_BADGE_FOR_ALL));
                Mercury.this.propertyUtil.setProperty(String.valueOf(Mercury.MERCURY_SHOW_FORCED_NOTICE_ONLY), String.valueOf(Mercury.MERCURY_BADGE_FOR_ALL));
                Mercury.this.propertyUtil.setProperty(String.valueOf(200001), String.valueOf(Mercury.MERCURY_BADGE_FOR_ALL));
                Mercury.this.propertyUtil.storeProperty("new request has occured");
            }
        }.start();
    }

    public void mercuryGetBadge(int mercuryBadgeForWhat) {
        if (this.jsonBadgeObj == null || !checkBadgeInfoValidity(mercuryBadgeForWhat)) {
            logger.d("[mercuryGetBadge] request for badge info " + mercuryBadgeForWhat);
            this.network.setServer(getServerURL(MERCURY_REQUEST_BADGE_LIST));
            requestNetworkForBadge(mercuryBadgeForWhat);
            return;
        }
        logger.d("[mercuryGetBadge] use cached value for " + mercuryBadgeForWhat);
        handleBadgeInfo(null, mercuryBadgeForWhat);
    }

    public int mercuryGetBadgeType(int mercuryBadgeForWhat) {
        return checkBadgeType(mercuryBadgeForWhat);
    }

    public int mercuryNativeGetCustomViewInfo(String uid, int viewID, int mercuryNativeCBWithData) {
        this.mNativeCBWithData = mercuryNativeCBWithData;
        return mercuryGetCustomViewInfo(uid, viewID, null);
    }

    public int mercuryGetCustomViewInfo(String uid, int viewID, MercuryCBWithData mercuryCBWithData) {
        if (uid.isEmpty()) {
            logger.d(Constants.TAG, "Failed to get customViewInfo. Check your parameter.");
            return MERCURY_REQUEST_SHOW;
        }
        if (viewID < 0) {
            setMercuryDataWithShowType(viewID);
        } else if (viewID >= MERCURY_BADGE_FOR_CUSTOM_MAX || viewID < MERCURY_SHOW_CUSTOM_BASE) {
            logger.d("Invalid Mercury Showtype");
            return MERCURY_REQUEST_SHOW;
        } else {
            MercuryData.set(16, MercuryDefine.WEBVIEW_TYPE_CUSTOM);
            MercuryData.set(11, MercuryDefine.TYPE_EVENT);
            if (viewID > MERCURY_SHOW_FORCED_CUSTOM_BASE) {
                logger.d("Action : custom page with forced");
            } else {
                logger.d("Action : custom page");
            }
        }
        if (this.requestedCustomViewID != 0 || this.bShowRequested) {
            logger.d(Constants.TAG, "You already call the getCustomViewInfo or mercuryShow.");
            return MERCURY_REQUEST_SHOW;
        }
        MercuryData.set(19, i.a);
        this.network.setServer(getServerURL(MERCURY_REQUEST_SHOW));
        this.mCBWithData = mercuryCBWithData;
        this.requestedCustomViewID = viewID;
        this.bShowRequested = true;
        savedUserID = uid;
        syncCookie();
        long timeRequested = System.currentTimeMillis() / 1000;
        logger.d("propertyUtil.setProperty(" + String.valueOf(showTypeToBadgeForWhat(viewID)) + i.b + String.valueOf(timeRequested) + ");");
        this.propertyUtil.setProperty(String.valueOf(showTypeToBadgeForWhat(viewID)), String.valueOf(timeRequested));
        this.propertyUtil.storeProperty("new request has occured");
        setUidCheckMSG();
        requestNetwork(viewID);
        return MERCURY_BADGE_FOR_ALL;
    }

    public void mercuryShowEx(String uid, int mercuryShowType, String additionalInfo) {
        if (additionalInfo == null) {
            additionalInfo = i.a;
        }
        if (!this.isShowing && !this.bShowRequested) {
            this.bShowRequested = true;
            Log.i("Duration", "RenderPriorityHigh");
            drchkInitVariables();
            resetWebViewIndexLoaded();
            resetWebViewCount();
            savedUserID = uid;
            MercuryData.set(19, additionalInfo);
            this.network.setServer(getServerURL(MERCURY_REQUEST_SHOW));
            setMercuryDataWithShowType(mercuryShowType);
            if (mercuryShowType >= 0) {
                if (mercuryShowType >= MERCURY_BADGE_FOR_CUSTOM_MAX || mercuryShowType < MERCURY_SHOW_CUSTOM_BASE) {
                    logger.d("Invalid Mercury Showtype");
                    return;
                }
                MercuryData.set(16, MercuryDefine.WEBVIEW_TYPE_CUSTOM);
                MercuryData.set(11, MercuryDefine.TYPE_EVENT);
                if (mercuryShowType > MERCURY_SHOW_FORCED_CUSTOM_BASE) {
                    logger.d("Action : custom page with forced");
                } else {
                    logger.d("Action : custom page");
                }
            }
            long timeRequested = System.currentTimeMillis() / 1000;
            logger.d("propertyUtil.setProperty(" + String.valueOf(showTypeToBadgeForWhat(mercuryShowType)) + i.b + String.valueOf(timeRequested) + ");");
            this.propertyUtil.setProperty(String.valueOf(showTypeToBadgeForWhat(mercuryShowType)), String.valueOf(timeRequested));
            this.propertyUtil.storeProperty("new request has occured");
            setUidCheckMSG();
            requestNetwork(mercuryShowType);
        }
    }

    private void setMercuryDataWithShowType(int mercuryShowType) {
        switch (mercuryShowType) {
            case MERCURY_SHOW_FORCED_NOTICE_ONLY /*-17*/:
                logger.d("Action : notice with forced");
                MercuryData.set(16, MercuryDefine.TYPE_NOTICE);
                MercuryData.set(11, MercuryDefine.TYPE_NOTICE);
                return;
            case MERCURY_SHOW_NOTICE_ONLY /*-16*/:
                logger.d("Action : notice");
                this.bIgnoreForcedFlag = true;
                MercuryData.set(16, MercuryDefine.TYPE_NOTICE);
                MercuryData.set(11, MercuryDefine.TYPE_NOTICE);
                return;
            case MERCURY_SHOW_FORCED_NOTICE_BOTTOM /*-15*/:
                logger.d("Action : button with forced, notice bottom");
                MercuryData.set(16, MercuryDefine.ACTION_BUTTON);
                MercuryData.set(11, MercuryDefine.TYPE_EVENT);
                return;
            case MERCURY_SHOW_FORCED_NOTICE_TOP /*-14*/:
                logger.d("Action : button with forced, notice top");
                MercuryData.set(16, MercuryDefine.ACTION_BUTTON);
                MercuryData.set(11, MercuryDefine.TYPE_NOTICE);
                return;
            case MERCURY_SHOW_NOTICE_BOTTOM /*-13*/:
                logger.d("Action : button, notice bottom");
                this.bIgnoreForcedFlag = true;
                MercuryData.set(16, MercuryDefine.ACTION_BUTTON);
                MercuryData.set(11, MercuryDefine.TYPE_EVENT);
                return;
            case MERCURY_SHOW_NOTICE_TOP /*-12*/:
                logger.d("Action : button, notice top");
                this.bIgnoreForcedFlag = true;
                MercuryData.set(16, MercuryDefine.ACTION_BUTTON);
                MercuryData.set(11, MercuryDefine.TYPE_NOTICE);
                return;
            case MERCURY_SHOW_EVENT /*-11*/:
                logger.d("Action : init");
                MercuryData.set(16, MercuryDefine.ACTION_INIT);
                MercuryData.set(11, MercuryDefine.TYPE_EVENT);
                return;
            default:
                return;
        }
    }

    private void setUidCheckMSG() {
        boolean isUserIdCorrectSize = true;
        String currentUserID = MercuryData.get(10);
        if (currentUserID == null || savedUserID == null) {
            logger.d(Constants.TAG, "saavedUserID is null. (first time call)");
            return;
        }
        boolean isUidNull;
        boolean isUidCorrectSize;
        int cUidLength = currentUserID.length();
        int sUidLength = savedUserID.length();
        if (savedUserID == null || savedUserID.equals(i.a)) {
            isUidNull = true;
        } else {
            isUidNull = false;
        }
        boolean isUserIdNull;
        if (currentUserID == null || currentUserID.equals(i.a)) {
            isUserIdNull = true;
        } else {
            isUserIdNull = false;
        }
        if (sUidLength <= 0 || 18 <= sUidLength) {
            isUidCorrectSize = false;
        } else {
            isUidCorrectSize = true;
        }
        if (cUidLength <= 0 || 18 <= cUidLength) {
            isUserIdCorrectSize = false;
        }
        String nullMsg = "uid is null";
        String uncorrectSizeMsg = "uid is undefined size string";
        if (isUidNull && isUserIdNull) {
            MercuryData.set(10, i.a);
            MercuryData.set(17, nullMsg);
        } else if (isUidNull && !isUserIdCorrectSize) {
            MercuryData.set(17, uncorrectSizeMsg);
        } else if (!isUidCorrectSize && isUserIdNull) {
            MercuryData.set(10, savedUserID);
            MercuryData.set(17, uncorrectSizeMsg);
        } else if (!isUidCorrectSize && !isUserIdCorrectSize) {
            MercuryData.set(10, savedUserID);
            MercuryData.set(17, uncorrectSizeMsg);
        } else if (!isUidNull && !isUidCorrectSize && !currentUserID.equals(savedUserID)) {
            MercuryData.set(10, savedUserID);
            MercuryData.set(17, uncorrectSizeMsg);
        } else if (isUidNull || !isUidCorrectSize || currentUserID.equals(savedUserID)) {
            MercuryData.set(17, Response.SUCCESS_KEY);
        } else {
            MercuryData.set(10, savedUserID);
            MercuryData.set(17, Response.SUCCESS_KEY);
        }
        logger.d(Constants.TAG, "UidCheck Result : " + MercuryData.get(17));
    }

    private void syncCookie() {
        String domain;
        logger.d(Constants.TAG, "syncCookie");
        if (VERSION.SDK_INT <= 10) {
            if (this.isUsingStaging) {
                domain = MercuryDefine.COOKIE_DOMAIN_STAGING;
            } else {
                domain = MercuryDefine.COOKIE_DOMAIN_REAL;
            }
        } else if (this.isUsingStaging) {
            domain = MercuryDefine.COOKIE_DOMAIN_STAGING;
        } else {
            domain = MercuryDefine.COOKIE_DOMAIN;
        }
        logger.d(Constants.TAG, MercuryData.get(1) + MercuryData.get(2) + MercuryData.get(3) + MercuryData.get(4) + MercuryData.get(5) + MercuryData.get(6) + MercuryData.get(7) + MercuryData.get(8) + MercuryData.get(12) + MercuryData.get(10));
        this.cookieManager.setCookie(domain, "mq_appid=" + MercuryData.get(1));
        this.cookieManager.setCookie(domain, "mq_mac=" + MercuryData.get(2));
        this.cookieManager.setCookie(domain, "mq_lan=" + MercuryData.get(3));
        this.cookieManager.setCookie(domain, "mq_con=" + MercuryData.get(4));
        this.cookieManager.setCookie(domain, "mq_devicetype=" + MercuryData.get(5));
        this.cookieManager.setCookie(domain, "mq_osver=" + MercuryData.get(6));
        this.cookieManager.setCookie(domain, "mq_libver=" + Constants.Version);
        this.cookieManager.setCookie(domain, "mq_appver=" + MercuryData.get(7));
        this.cookieManager.setCookie(domain, "mq_appvercode=" + MercuryData.get(8));
        this.cookieManager.setCookie(domain, "mq_orient=" + MercuryData.get(9));
        this.cookieManager.setCookie(domain, "mq_uid=" + MercuryData.get(10));
        this.cookieManager.setCookie(domain, "mq_type=" + MercuryData.get(11));
        this.cookieManager.setCookie(domain, "mq_did=" + MercuryData.get(12));
        String vid = MercuryData.get(18);
        if (vid == null) {
            this.cookieManager.setCookie(domain, "mq_vid=-703");
        } else {
            this.cookieManager.setCookie(domain, "mq_vid=" + vid);
        }
        this.cookieManager.setCookie(domain, "mq_additionalInfo=" + MercuryData.get(19));
        this.cookieManager.setCookie(domain, "mq_mcc=" + MercuryData.get(20));
        this.cookieManager.setCookie(domain, "mq_mnc=" + MercuryData.get(21));
        this.cookieSyncManager.sync();
        logger.d(Constants.TAG, "get cookie" + this.cookieManager.getCookie(MercuryDefine.COOKIE_DOMAIN));
    }

    private void expireCookie() {
        logger.d(Constants.TAG, "expireCookie");
        String expireStr = ";expires=Mon, 17 Oct 2011 10:47:11 UTC;";
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_appid=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_mac=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_lan=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_con=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_devicetype=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_osver=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_libver=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_appver=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_appvercode=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_orient=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_did=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_vid=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_additionalInfo=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_mcc=" + expireStr);
        this.cookieManager.setCookie(MercuryDefine.COOKIE_DOMAIN, "mq_mnc=" + expireStr);
        this.cookieSyncManager.sync();
    }

    private String getServerURL(int type) {
        switch (type) {
            case MERCURY_REQUEST_BADGE_LIST /*-2*/:
                if (this.isUsingStaging) {
                    return MercuryDefine.STAGING_SERVER_FOR_BADGE;
                }
                return MercuryDefine.REAL_SERVER_FOR_BADGE;
            case MERCURY_REQUEST_SHOW /*-1*/:
                if (this.isUsingStaging) {
                    return MercuryDefine.STAGING_SERVER_FOR_SHOW;
                }
                return MercuryDefine.REAL_SERVER_FOR_SHOW;
            default:
                return null;
        }
    }

    private int checkBadgeType(int mercuryBadgeForWhat) {
        try {
            JSONArray badgeJSONArr = this.jsonBadgeObj.getJSONArray("badge");
            int badgeCount = badgeJSONArr.length();
            logger.d("badgeCount : " + badgeCount);
            for (int i = MERCURY_BADGE_FOR_ALL; i < badgeCount; i++) {
                JSONObject badgeObj = badgeJSONArr.getJSONObject(i);
                String badgeAct = badgeObj.getString("act");
                int badgeShowType = badgeObj.getInt("showtype");
                String badgeType = badgeObj.getString("badgetype");
                logger.d("badge[" + i + "] : " + badgeType);
                long currentTime = System.currentTimeMillis() / 1000;
                if (mercuryBadgeForWhat == actToBadgeForWhat(badgeAct, badgeShowType)) {
                    return badgeTypeToBadgeType(badgeType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MERCURY_BADGE_FOR_ALL;
    }

    private boolean checkBadgeInfoValidity(int mercuryBadgeForWhat) {
        if (this.jsonBadgeObj == null) {
            return false;
        }
        int badgeCacheTerm = MERCURY_BADGE_FOR_ALL;
        try {
            JSONArray badgeJSONArr = this.jsonBadgeObj.getJSONArray("badge");
            int badgeCount = badgeJSONArr.length();
            logger.d("badgeCount : " + badgeCount);
            for (int i = MERCURY_BADGE_FOR_ALL; i < badgeCount; i++) {
                JSONObject badgeObj = badgeJSONArr.getJSONObject(i);
                long currentTimeMillis = System.currentTimeMillis() / 1000;
                if (mercuryBadgeForWhat == actToBadgeForWhat(badgeObj.getString("act"), badgeObj.getInt("showtype"))) {
                    badgeCacheTerm = badgeObj.getInt("term");
                    logger.d("badge[" + i + "] cache term : " + badgeCacheTerm);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String strCachedAt = this.propertyUtil.getProperty("cachedBadge" + mercuryBadgeForWhat);
        if (TextUtils.isEmpty(strCachedAt)) {
            return false;
        }
        if ((System.currentTimeMillis() / 1000) - Long.parseLong(strCachedAt, 10) > ((long) (badgeCacheTerm * 60))) {
            return false;
        }
        return true;
    }

    private void cacheBadgeInfo(JSONObject jsonObj) {
        this.jsonBadgeObj = jsonObj;
        try {
            JSONArray badgeJSONArr = jsonObj.getJSONArray("badge");
            int badgeCount = badgeJSONArr.length();
            logger.d("badgeCount : " + badgeCount);
            if (badgeCount > 0) {
                for (int i = MERCURY_BADGE_FOR_ALL; i < badgeCount; i++) {
                    JSONObject badgeObj = badgeJSONArr.getJSONObject(i);
                    String badgeAct = badgeObj.getString("act");
                    int badgeShowType = badgeObj.getInt("showtype");
                    logger.d("badge[" + i + "] : " + badgeObj.getInt("term"));
                    this.propertyUtil.setProperty("cachedBadge" + actToBadgeForWhat(badgeAct, badgeShowType), String.valueOf(System.currentTimeMillis() / 1000));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleBadgeInfo(JSONObject jsonObj) {
        handleBadgeInfo(jsonObj, MERCURY_BADGE_FOR_ALL);
    }

    private void handleBadgeInfo(JSONObject jsonObj, int mercuryBadgeForWhat) {
        if (jsonObj == null && this.jsonBadgeObj != null) {
            jsonObj = this.jsonBadgeObj;
        }
        try {
            JSONArray badgeJSONArr = jsonObj.getJSONArray("badge");
            int badgeCount = badgeJSONArr.length();
            logger.d("badgeCount : " + badgeCount);
            if (badgeCount > 0) {
                for (int i = MERCURY_BADGE_FOR_ALL; i < badgeCount; i++) {
                    JSONObject badgeObj = badgeJSONArr.getJSONObject(i);
                    String badgeAct = badgeObj.getString("act");
                    int badgeShowType = badgeObj.getInt("showtype");
                    String badgeType = badgeObj.getString("badgetype");
                    long badgeStartDate = badgeObj.getLong("startdate");
                    int badgeExpire = badgeObj.getInt("expire");
                    int badgeCacheTerm = badgeObj.getInt("term");
                    logger.d("badge[" + i + "] : " + badgeAct + ", " + badgeShowType + ", " + badgeType + ", " + badgeStartDate + ", " + badgeExpire + ", " + badgeCacheTerm);
                    long currentTime = System.currentTimeMillis() / 1000;
                    try {
                        String lastRequested = this.propertyUtil.getProperty(String.valueOf(actToBadgeForWhat(badgeAct, badgeShowType)));
                        if (TextUtils.isEmpty(lastRequested)) {
                            logger.d("lasgRequested is null");
                            if (mercuryBadgeForWhat == 0 || mercuryBadgeForWhat == actToBadgeForWhat(badgeAct, badgeShowType)) {
                                sendBadgeCallback(badgeAct, badgeType, badgeShowType);
                            }
                        } else {
                            logger.d("lastRequested is not null. New contents has been added after " + (badgeStartDate - Long.parseLong(lastRequested, 10)) + "seconds from the last show request");
                            if (badgeStartDate - Long.parseLong(lastRequested, 10) > 0 && (mercuryBadgeForWhat == 0 || mercuryBadgeForWhat == actToBadgeForWhat(badgeAct, badgeShowType))) {
                                sendBadgeCallback(badgeAct, badgeType, badgeShowType);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void requestNetworkForBadge(final int mercuryBadgeForWhat) {
        new Thread() {
            public void run() {
                JSONObject jSONObject;
                JSONException e;
                Mercury.logger.d(Constants.TAG, "requestNetwork run");
                Object obj = Mercury.this.network.processNetworkTask(mercuryBadgeForWhat);
                if (obj != null) {
                    try {
                        JSONObject jsonObj = new JSONObject((String) obj);
                        try {
                            Mercury.logger.d(Constants.TAG, "json : " + jsonObj.toString());
                            Mercury.this.cacheBadgeInfo(jsonObj);
                            Mercury.this.handleBadgeInfo(jsonObj, mercuryBadgeForWhat);
                            String error = jsonObj.getString(GCMConstants.EXTRA_ERROR);
                            String errorMsg = jsonObj.getString("errormsg");
                            if (error.equals(a.d)) {
                                return;
                            }
                            Mercury.logger.d(Constants.TAG, "requestNetwork error : " + error + "error msg : " + errorMsg);
                            Mercury.this.sendCloseCallback();
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
                Mercury.logger.d(Constants.TAG, "Error: Mercury network diconnected");
                Mercury.this.sendNetworkDisconnectCallback();
            }
        }.start();
    }

    private void requestNetwork(final int mercuryShowType) {
        new Thread() {
            public void run() {
                JSONException e;
                Mercury.logger.d(Constants.TAG, "requestNetwork run");
                Object obj = Mercury.this.network.processNetworkTask(mercuryShowType);
                if (obj != null) {
                    try {
                        JSONObject jsonObj = new JSONObject((String) obj);
                        JSONObject jSONObject;
                        try {
                            Mercury.logger.d(Constants.TAG, "json : " + jsonObj.toString());
                            Mercury.this.cacheBadgeInfo(jsonObj);
                            Mercury.this.handleBadgeInfo(jsonObj);
                            if (Mercury.this.requestedCustomViewID != 0) {
                                Mercury.this.bShowRequested = false;
                                final JSONObject customViewInfoDasta = jsonObj;
                                Mercury.this.activity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Mercury.this.callbackWithData(customViewInfoDasta);
                                    }
                                });
                                jSONObject = jsonObj;
                                return;
                            }
                            String error = jsonObj.getString(GCMConstants.EXTRA_ERROR);
                            String errorMsg = jsonObj.getString("errormsg");
                            if (error.equals(a.d)) {
                                Mercury.this.webViewCount = jsonObj.getInt("count");
                                if (Mercury.this.webViewCount <= 0) {
                                    Mercury.logger.d(Constants.TAG, "requestNetwork webViewCount <= 0");
                                    Mercury.this.sendCloseCallback();
                                    jSONObject = jsonObj;
                                    return;
                                }
                                Mercury.this.webViewShow = jsonObj.getInt("show");
                                Mercury.this.webViewShowType = jsonObj.getInt(PeppermintConstant.JSON_KEY_TYPE);
                                Mercury.this.webViewJSONArr = jsonObj.getJSONArray("webview");
                                if (Mercury.this.webViewCount != Mercury.this.webViewJSONArr.length()) {
                                    Mercury.logger.d(Constants.TAG, "requestNetwork webViewCount != webViewJSONArr.length()");
                                    Mercury.this.sendCloseCallback();
                                    jSONObject = jsonObj;
                                    return;
                                }
                                Mercury.this.show();
                                jSONObject = jsonObj;
                                return;
                            }
                            Mercury.logger.d(Constants.TAG, "requestNetwork error : " + error + "error msg : " + errorMsg);
                            Mercury.this.sendCloseCallback();
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
                Mercury.logger.d(Constants.TAG, "Error: Mercury network diconnected");
                Mercury.this.sendNetworkDisconnectCallback();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put(GCMConstants.EXTRA_ERROR, Mercury.MERCURY_REQUEST_SHOW);
                    jsonObject.put("errormsg", "Disconnect Failed.");
                } catch (JSONException e4) {
                    e4.printStackTrace();
                }
                Mercury.this.callbackWithData(jsonObject);
            }
        }.start();
    }

    private void sendNetworkDisconnectCallback() {
        this.isShowing = false;
        this.bShowRequested = false;
        this.bIgnoreForcedFlag = false;
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                Mercury.this.callback(Mercury.MERCURY_SHOW_FORCED_NOTICE_TOP);
            }
        });
    }

    public int getWebViewIndexLoaded() {
        return this.webViewIndexLoaded;
    }

    public int increaseWebViewIndexLoaded() {
        int i = this.webViewIndexLoaded + 1;
        this.webViewIndexLoaded = i;
        return i;
    }

    public void resetWebViewIndexLoaded() {
        this.webViewIndexLoaded = MERCURY_BADGE_FOR_ALL;
    }

    public void loadDialog() {
        logger.d("loadDialog. webViewCount : " + this.webViewCount);
        if (this.mercuryDialog == null) {
            logger.d("create new dialog");
            this.mercuryDialog = new MercuryDialog(this, this.activity);
            this.mercuryDialog.setBitmap(this.bitmapClose, this.bitmapWebViewBorder0, this.bitmapWebViewBorder1d, this.bitmapWebViewBorder1s, this.bitmapWebViewBorder2);
        } else {
            logger.d("use existing dialog");
        }
        while (this.webViewIndexLoaded < this.webViewCount) {
            boolean bNeedToShow = true;
            Log.i("Duration", "start requesting " + (this.webViewIndexLoaded + 1) + "/" + this.webViewCount);
            try {
                JSONObject jsonObject = this.webViewJSONArr.getJSONObject(this.webViewIndexLoaded);
                String today = Integer.toString(Calendar.getInstance().get(5));
                String webViewType = jsonObject.getString("webview_type");
                boolean isTypeAllAndisInitShow = this.webViewShowType == 0 && 1 == this.webViewShow;
                String savedOffAutoDate;
                if (isTypeAllAndisInitShow && webViewType.equals(MercuryDefine.WEBVIEW_TYPE_CPI)) {
                    logger.d(Constants.TAG, "LoadDialog Type : CPI");
                    savedOffAutoDate = MercuryProperties.getProperty(jsonObject.getString("pid"));
                    if (savedOffAutoDate != null && savedOffAutoDate.equals(today)) {
                        bNeedToShow = false;
                    }
                } else {
                    if (isTypeAllAndisInitShow) {
                        if (webViewType.equals(MercuryDefine.WEBVIEW_TYPE_FULLBANNER)) {
                            logger.d(Constants.TAG, "LoadDialog Type : FULLBANNER");
                            savedOffAutoDate = MercuryProperties.getProperty(jsonObject.getString("pid"));
                            if (savedOffAutoDate != null && savedOffAutoDate.equals(today)) {
                                bNeedToShow = false;
                            }
                        }
                    }
                    if (isTypeAllAndisInitShow && webViewType.equals(MercuryDefine.WEBVIEW_TYPE_MAIN)) {
                        logger.d(Constants.TAG, "LoadDialog Type : MAIN");
                        savedOffAutoDate = MercuryProperties.getProperty(MercuryDefine.MAIN_OFFAUTO_DATE);
                        if (savedOffAutoDate != null && savedOffAutoDate.equals(today)) {
                            bNeedToShow = false;
                        }
                    } else if (isTypeAllAndisInitShow && webViewType.equals(MercuryDefine.WEBVIEW_TYPE_CUSTOM)) {
                        logger.d(Constants.TAG, "LoadDialog Type : CUSTOM");
                    } else if (isTypeAllAndisInitShow) {
                        logger.d(Constants.TAG, "undefined webview_type");
                        bNeedToShow = false;
                    } else if (this.bIgnoreForcedFlag && webViewType.equals(MercuryDefine.WEBVIEW_TYPE_MAIN)) {
                        savedOffAutoDate = MercuryProperties.getProperty(MercuryDefine.MAIN_OFFAUTO_DATE);
                        logger.d(Constants.TAG, "LoadDialog Type : MAIN without Forced Option");
                        if (savedOffAutoDate != null && savedOffAutoDate.equals(today)) {
                            bNeedToShow = false;
                        }
                    }
                }
                logger.d(Constants.TAG, "bNeedToShow : " + bNeedToShow);
                if (bNeedToShow) {
                    this.mercuryDialog.generateMercuryLayout(jsonObject, this.mercuryDialog.getLayoutListsize());
                    break;
                }
                increaseWebViewIndexLoaded();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.mercuryLayoutGeneratedCount = this.mercuryDialog.getLayoutListsize();
        logger.d(Constants.TAG, "mercuryLayoutCountGenerated : " + this.mercuryLayoutGeneratedCount);
        switch (this.mercuryLayoutGeneratedCount) {
            case MERCURY_BADGE_FOR_ALL /*0*/:
                sendCloseCallback();
                return;
            case o.a /*1*/:
                this.mercuryDialog.show();
                this.isShowing = true;
                sendOpenCallback();
                return;
            default:
                return;
        }
    }

    private void load() {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                Mercury.this.loadDialog();
            }
        });
    }

    void sendBadgeCallback(final String act, String badgetype, final int showtype) {
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                if (act.equals(MercuryDefine.ACTION_INIT)) {
                    Mercury.this.callback(Mercury.MERCURY_SHOW_FORCED_NOTICE_BOTTOM);
                } else if (act.equals(MercuryDefine.ACTION_BUTTON)) {
                    Mercury.this.callback(Mercury.MERCURY_SHOW_NOTICE_ONLY);
                } else if (act.equals(MercuryDefine.TYPE_NOTICE)) {
                    Mercury.this.callback(Mercury.MERCURY_SHOW_FORCED_NOTICE_ONLY);
                } else if (act.equals(MercuryDefine.WEBVIEW_TYPE_CUSTOM)) {
                    Mercury.this.callback(showtype);
                }
            }
        });
    }

    void sendCloseCallback() {
        this.bIgnoreForcedFlag = false;
        this.bShowRequested = false;
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                String action = MercuryData.get(16);
                if (action.equals(MercuryDefine.ACTION_INIT)) {
                    Mercury.this.callback(Mercury.MERCURY_FULL_BANNER_CLOSED);
                } else if (action.equals(MercuryDefine.ACTION_BUTTON)) {
                    Mercury.this.callback(Mercury.MERCURY_MAIN_PAGE_CLOSED);
                } else if (action.equals(MercuryDefine.TYPE_NOTICE)) {
                    Mercury.this.callback(Mercury.MERCURY_SHOW_EVENT);
                } else if (action.equals(MercuryDefine.WEBVIEW_TYPE_CUSTOM)) {
                    Mercury.this.callback(Mercury.MERCURY_SHOW_NOTICE_BOTTOM);
                }
            }
        });
    }

    void sendOpenCallback() {
        String action = MercuryData.get(16);
        if (action.equals(MercuryDefine.ACTION_INIT)) {
            callback(MERCURY_FULL_BANNER_OPENED);
        } else if (action.equals(MercuryDefine.ACTION_BUTTON)) {
            callback(MERCURY_MAIN_PAGE_OPENED);
        } else if (action.equals(MercuryDefine.TYPE_NOTICE)) {
            callback(MERCURY_NOTICE_ONLY_OPENED);
        } else if (action.equals(MercuryDefine.WEBVIEW_TYPE_CUSTOM)) {
            callback(MERCURY_SHOW_NOTICE_TOP);
        }
    }

    void show() {
        if (!this.isShowing) {
            logger.d(Constants.TAG, "show");
            logger.d(Constants.TAG, "webViewShow : " + this.webViewShow + ",  webViewShowType : " + this.webViewShowType);
            String today = Integer.toString(Calendar.getInstance().get(5));
            if (1 > this.webViewShow) {
                sendCloseCallback();
                return;
            }
            if (2 == this.webViewShow) {
                logger.d(Constants.TAG, "show type is forced Show");
            } else if (1 == this.webViewShowType) {
                String savedDay = MercuryProperties.getProperty(MercuryDefine.MERCURY_DATE);
                logger.d(Constants.TAG, "saved Day:" + savedDay);
                if (savedDay == null || !savedDay.equals(today)) {
                    MercuryProperties.setProperty(MercuryDefine.MERCURY_DATE, today);
                    MercuryProperties.storeProperties(this.activity);
                } else {
                    sendCloseCallback();
                    return;
                }
            }
            syncCookie();
            load();
        }
    }

    void saveOffAutoParams(String _webViewType, String _pid) {
        String today = Integer.toString(Calendar.getInstance().get(5));
        if (_webViewType.equals(MercuryDefine.WEBVIEW_TYPE_CPI)) {
            MercuryProperties.setProperty(_pid, today);
            MercuryProperties.addPID(_pid);
            MercuryProperties.storeProperties(this.activity);
        } else if (_webViewType.equals(MercuryDefine.WEBVIEW_TYPE_FULLBANNER)) {
            MercuryProperties.setProperty(_pid, today);
            MercuryProperties.addPID(_pid);
            MercuryProperties.storeProperties(this.activity);
        } else if (_webViewType.equals(MercuryDefine.WEBVIEW_TYPE_MAIN)) {
            logger.d(Constants.TAG, "Saved OffAuto (MAIN Webview");
            MercuryProperties.setProperty(MercuryDefine.MAIN_OFFAUTO_DATE, today);
            MercuryProperties.storeProperties(this.activity);
        } else {
            logger.d(Constants.TAG, "Undefined typeParam");
        }
        this.mercuryDialog.close();
    }

    public void close() {
        this.isShowing = false;
        sendCloseCallback();
        this.mercuryDialog = null;
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

    public String[] getPermission() {
        return PERMISSION;
    }

    public void setAppIdForIdentity(String appid) {
        this.appIdForIdentity = appid;
        MercuryData.set(1, this.appIdForIdentity);
    }

    public void destroy() {
        if (this.mercuryDialog != null) {
            this.mercuryDialog.destroy();
            this.mercuryDialog = null;
        }
        if (this.bitmapClose != null) {
            this.bitmapClose.recycle();
            this.bitmapClose = null;
        }
    }

    public void onActivityResumed() {
        this.cookieSyncManager.startSync();
    }

    public void onActivityPaused() {
        this.cookieSyncManager.stopSync();
    }

    public boolean checkIfServiceAvailable(String className, Context _context) {
        try {
            logger.d("checkIfServiceAvailable of <" + className + ">");
            Class.forName(className);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
