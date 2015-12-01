package com.com2us.module.offerwall;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import jp.co.cyberz.fox.a.a.i;

public class OfferwallData {
    public static final int ADDITIONAL_INFO = 17;
    public static final int APP_ID = 1;
    public static final int APP_VERSION = 7;
    public static final int APP_VERSIONCODE = 8;
    public static final int ASSET_AMOUNT = 15;
    public static final int ASSET_CODE = 14;
    public static final int AWARD_RESULT = 16;
    public static final int C2S_MERCURY = 2;
    public static final int C2S_OFFERWALL = 1;
    public static final int COUNTRY = 4;
    public static final int DEVICE_MODEL = 5;
    public static final int DID = 10;
    public static final int DID_ASYNC = 11;
    public static final int EVENTID = 13;
    public static final int LANGUAGE = 3;
    public static final int MAC_ADDRESS = 2;
    public static final int MCC = 18;
    public static final int MNC = 19;
    public static final int OS_VERSION = 6;
    public static final int PATH = 20;
    public static final int UID = 9;
    public static final int VID = 12;
    private static Activity activity;
    private static final SparseArray<String> constanceArray = new SparseArray();
    public static Logger logger = LoggerGroup.createLogger(Constants.TAG);

    public static String get(int key) {
        switch (key) {
            case C2S_OFFERWALL /*1*/:
                return ModuleManager.getDatas(activity).getAppID();
            case MAC_ADDRESS /*2*/:
                return ModuleManager.getDatas(activity).getMacAddress();
            case LANGUAGE /*3*/:
                return ModuleManager.getDatas(activity).getLanguage();
            case COUNTRY /*4*/:
                return ModuleManager.getDatas(activity).getCountry();
            case DEVICE_MODEL /*5*/:
                return ModuleManager.getDatas(activity).getDeviceModel();
            case OS_VERSION /*6*/:
                return ModuleManager.getDatas(activity).getOSVersion();
            case APP_VERSION /*7*/:
                return ModuleManager.getDatas(activity).getAppVersion();
            case APP_VERSIONCODE /*8*/:
                return Integer.toString(ModuleManager.getDatas(activity).getAppVersionCode());
            case UID /*9*/:
            case EVENTID /*13*/:
            case ASSET_CODE /*14*/:
            case ASSET_AMOUNT /*15*/:
            case AWARD_RESULT /*16*/:
            case ADDITIONAL_INFO /*17*/:
            case PATH /*20*/:
                if (constanceArray != null) {
                    return (String) constanceArray.get(key);
                }
                logger.d(Constants.TAG, "Error : OfferwallData didn't created");
                return i.a;
            case DID /*10*/:
                return ModuleManager.getDatas(activity).getDID();
            case DID_ASYNC /*11*/:
                return ModuleManager.getDatas(activity).getSyncDID();
            case VID /*12*/:
                return ModuleManager.getDatas(activity).getVID();
            case MCC /*18*/:
                return ModuleManager.getDatas(activity).getMobileCountryCode();
            case MNC /*19*/:
                return ModuleManager.getDatas(activity).getMobileNetworkCode();
            default:
                return null;
        }
    }

    private static void set(int key, String value) {
        if (!TextUtils.isEmpty(value) && !value.equals(get(key))) {
            switch (key) {
                case C2S_OFFERWALL /*1*/:
                    ModuleManager.getDatas(activity).setAppID(value);
                    return;
                case MAC_ADDRESS /*2*/:
                    ModuleManager.getDatas(activity).setMacAddress(value);
                    return;
                case LANGUAGE /*3*/:
                    ModuleManager.getDatas(activity).setLanguage(value);
                    return;
                case COUNTRY /*4*/:
                    ModuleManager.getDatas(activity).setCountry(value);
                    return;
                case DEVICE_MODEL /*5*/:
                    ModuleManager.getDatas(activity).setDeviceModel(value);
                    return;
                case OS_VERSION /*6*/:
                    ModuleManager.getDatas(activity).setOSVersion(value);
                    return;
                case APP_VERSION /*7*/:
                    ModuleManager.getDatas(activity).setAppVersion(value);
                    return;
                case APP_VERSIONCODE /*8*/:
                    ModuleManager.getDatas(activity).setAppVersionCode(Integer.parseInt(value));
                    return;
                case UID /*9*/:
                case EVENTID /*13*/:
                case ASSET_CODE /*14*/:
                case ASSET_AMOUNT /*15*/:
                case AWARD_RESULT /*16*/:
                case ADDITIONAL_INFO /*17*/:
                case PATH /*20*/:
                    if (constanceArray == null) {
                        logger.d(Constants.TAG, "Error : OfferwallData didn't created");
                        return;
                    } else {
                        constanceArray.put(key, value);
                        return;
                    }
                case DID /*10*/:
                    ModuleManager.getDatas(activity).setDeviceID(value);
                    return;
                case VID /*12*/:
                    ModuleManager.getDatas(activity).setVID(value);
                    return;
                case MCC /*18*/:
                    ModuleManager.getDatas(activity).setMobileCountryCode(value);
                    return;
                case MNC /*19*/:
                    ModuleManager.getDatas(activity).setMobileNetworkCode(value);
                    return;
                default:
                    return;
            }
        }
    }

    public static void create(Activity _activity) {
        activity = _activity;
        if (TextUtils.isEmpty((CharSequence) constanceArray.get(UID))) {
            set(UID, i.a);
        }
        setDID(activity, false);
    }

    public static void setAppID(String appid) {
        set(C2S_OFFERWALL, appid);
    }

    public static void setUID(String uid) {
        set(UID, uid);
    }

    public static void setDID(Context context, boolean isUseTestServer) {
        set(DID, get(DID_ASYNC));
    }

    public static void setAdditionalInfo(String addtionalInfo) {
        set(ADDITIONAL_INFO, addtionalInfo);
    }

    public static void setPath(int oq_path) {
        switch (oq_path) {
            case C2S_OFFERWALL /*1*/:
                set(PATH, "C2S_OFFERWALL");
                return;
            case MAC_ADDRESS /*2*/:
                set(PATH, "C2S_MERCURY");
                return;
            default:
                return;
        }
    }

    public static void setEventID(String eventID) {
        set(EVENTID, eventID);
    }

    public static void setAssetCode(String assetCode) {
        set(ASSET_CODE, assetCode);
    }

    public static void setAssetAmount(String assetAmount) {
        set(ASSET_AMOUNT, assetAmount);
    }

    public static void setAwardResult(String awardResult) {
        set(AWARD_RESULT, awardResult);
    }
}
