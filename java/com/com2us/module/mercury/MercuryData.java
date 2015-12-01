package com.com2us.module.mercury;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.offerwall.Constants;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.g;

public class MercuryData {
    public static final int ACTION = 16;
    public static final int ADDITIONAL_INFO = 19;
    public static final int APP_ID = 1;
    public static final int APP_VERSION = 7;
    public static final int APP_VERSIONCODE = 8;
    public static final int COUNTRY = 4;
    public static final int DEVICE_MODEL = 5;
    public static final int DID = 12;
    public static final int DID_ASYNC = 13;
    public static final int HEIGHT = 15;
    public static final int LANGUAGE = 3;
    public static final int MAC_ADDRESS = 2;
    public static final int MCC = 20;
    public static final int MNC = 21;
    public static final int ORIENTATION = 9;
    public static final int OS_VERSION = 6;
    public static final int TYPE = 11;
    public static final int UIDCHECKMSG = 17;
    public static final int USERID = 10;
    public static final int VID = 18;
    public static final int WIDTH = 14;
    private static Activity activity;
    private static final SparseArray<String> constanceArray = new SparseArray();
    public static Logger logger = LoggerGroup.createLogger(Constants.TAG);

    public static String get(int key) {
        switch (key) {
            case APP_ID /*1*/:
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
            case ORIENTATION /*9*/:
            case USERID /*10*/:
            case TYPE /*11*/:
            case WIDTH /*14*/:
            case HEIGHT /*15*/:
            case ACTION /*16*/:
            case UIDCHECKMSG /*17*/:
            case ADDITIONAL_INFO /*19*/:
                if (constanceArray != null) {
                    return (String) constanceArray.get(key);
                }
                logger.d(Constants.TAG, "Error : MercuryData didn't created");
                return i.a;
            case DID /*12*/:
                return ModuleManager.getDatas(activity).getDID();
            case DID_ASYNC /*13*/:
                return ModuleManager.getDatas(activity).getSyncDID();
            case VID /*18*/:
                String vid = ModuleManager.getDatas(activity).getVID();
                if (vid == null) {
                    logger.d("vid is null");
                    return vid;
                } else if (vid.equals(ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN)) {
                    logger.d("vid is \"null\"");
                    return vid;
                } else if (!vid.equals(i.a)) {
                    return vid;
                } else {
                    logger.d("vid is empty string");
                    return vid;
                }
            case MCC /*20*/:
                return ModuleManager.getDatas(activity).getMobileCountryCode();
            case MNC /*21*/:
                return ModuleManager.getDatas(activity).getMobileNetworkCode();
            default:
                return null;
        }
    }

    public static void set(int key, String value) {
        if (!TextUtils.isEmpty(value) && !value.equals(get(key))) {
            switch (key) {
                case APP_ID /*1*/:
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
                case ORIENTATION /*9*/:
                case USERID /*10*/:
                case TYPE /*11*/:
                case WIDTH /*14*/:
                case HEIGHT /*15*/:
                case ACTION /*16*/:
                case UIDCHECKMSG /*17*/:
                case ADDITIONAL_INFO /*19*/:
                    if (constanceArray == null) {
                        logger.d(Constants.TAG, "Error : OfferwallData didn't created");
                        return;
                    } else {
                        constanceArray.put(key, value);
                        return;
                    }
                case DID /*12*/:
                    ModuleManager.getDatas(activity).setDeviceID(value);
                    return;
                case VID /*18*/:
                    ModuleManager.getDatas(activity).setVID(value);
                    return;
                case MCC /*20*/:
                    ModuleManager.getDatas(activity).setMobileCountryCode(value);
                    return;
                case MNC /*21*/:
                    ModuleManager.getDatas(activity).setMobileNetworkCode(value);
                    return;
                default:
                    return;
            }
        }
    }

    public static void create(Activity _activity) {
        activity = _activity;
        set(ORIENTATION, String.valueOf(activity.getResources().getConfiguration().orientation));
        if (TextUtils.isEmpty(get(USERID))) {
            set(USERID, i.a);
        }
        set(TYPE, MercuryDefine.TYPE_NOTICE);
        int screenSizeA = activity.getResources().getDisplayMetrics().widthPixels;
        int screenSizeB = activity.getResources().getDisplayMetrics().heightPixels;
        int i;
        switch (activity.getRequestedOrientation()) {
            case g.a /*0*/:
            case OS_VERSION /*6*/:
                if (screenSizeA > screenSizeB) {
                    i = screenSizeA;
                } else {
                    i = screenSizeB;
                }
                set(WIDTH, String.valueOf(i));
                if (screenSizeA <= screenSizeB) {
                    screenSizeB = screenSizeA;
                }
                set(HEIGHT, String.valueOf(screenSizeB));
                break;
            case APP_ID /*1*/:
            case APP_VERSION /*7*/:
                if (screenSizeA < screenSizeB) {
                    i = screenSizeA;
                } else {
                    i = screenSizeB;
                }
                set(WIDTH, String.valueOf(i));
                if (screenSizeA >= screenSizeB) {
                    screenSizeB = screenSizeA;
                }
                set(HEIGHT, String.valueOf(screenSizeB));
                break;
            default:
                if (activity.getResources().getConfiguration().orientation != APP_ID) {
                    if (screenSizeA > screenSizeB) {
                        i = screenSizeA;
                    } else {
                        i = screenSizeB;
                    }
                    set(WIDTH, String.valueOf(i));
                    if (screenSizeA <= screenSizeB) {
                        screenSizeB = screenSizeA;
                    }
                    set(HEIGHT, String.valueOf(screenSizeB));
                    break;
                }
                if (screenSizeA < screenSizeB) {
                    i = screenSizeA;
                } else {
                    i = screenSizeB;
                }
                set(WIDTH, String.valueOf(i));
                if (screenSizeA >= screenSizeB) {
                    screenSizeB = screenSizeA;
                }
                set(HEIGHT, String.valueOf(screenSizeB));
                break;
        }
        setDID(activity, false);
    }

    public static void setDID(Context context, boolean isUseTestServer) {
        set(DID, get(DID_ASYNC));
    }

    public static String getProgressDialogText() {
        String country = get(COUNTRY).toLowerCase();
        String language = get(LANGUAGE).toLowerCase();
        if (TextUtils.equals(language, "ko")) {
            return MercuryDefine.LOADING_TEXT_KO;
        }
        if (TextUtils.equals(language, "fr")) {
            return MercuryDefine.LOADING_TEXT_FR;
        }
        if (TextUtils.equals(language, "de")) {
            return MercuryDefine.LOADING_TEXT_DE;
        }
        if (TextUtils.equals(language, "ja")) {
            return MercuryDefine.LOADING_TEXT_JA;
        }
        if (TextUtils.equals(country, "tw")) {
            return MercuryDefine.LOADING_TEXT_TW;
        }
        if (TextUtils.equals(language, "zh")) {
            return MercuryDefine.LOADING_TEXT_ZH;
        }
        if (TextUtils.equals(language, "ru")) {
            return MercuryDefine.LOADING_TEXT_RU;
        }
        if (TextUtils.equals(language, "es")) {
            return MercuryDefine.LOADING_TEXT_ES;
        }
        if (TextUtils.equals(language, "pt")) {
            return MercuryDefine.LOADING_TEXT_PT;
        }
        if (TextUtils.equals(language, "in")) {
            return MercuryDefine.LOADING_TEXT_IN;
        }
        if (TextUtils.equals(language, "ms")) {
            return MercuryDefine.LOADING_TEXT_MS;
        }
        if (TextUtils.equals(language, "vi")) {
            return MercuryDefine.LOADING_TEXT_VI;
        }
        if (TextUtils.equals(language, "th")) {
            return MercuryDefine.LOADING_TEXT_TH;
        }
        if (TextUtils.equals(language, "it")) {
            return MercuryDefine.LOADING_TEXT_IT;
        }
        if (TextUtils.equals(language, "tr")) {
            return MercuryDefine.LOADING_TEXT_TR;
        }
        return MercuryDefine.LOADING_TEXT_EN;
    }

    public static String getDialogBorderText(int index) {
        String country = get(COUNTRY).toLowerCase();
        String language = get(LANGUAGE).toLowerCase();
        switch (index) {
            case g.a /*0*/:
                if (TextUtils.equals(language, "ko")) {
                    return MercuryDefine.DIALOG1_TEXT_KO;
                }
                if (TextUtils.equals(language, "fr")) {
                    return MercuryDefine.DIALOG1_TEXT_FR;
                }
                if (TextUtils.equals(language, "de")) {
                    return MercuryDefine.DIALOG1_TEXT_DE;
                }
                if (TextUtils.equals(language, "ja")) {
                    return MercuryDefine.DIALOG1_TEXT_JA;
                }
                if (TextUtils.equals(country, "tw")) {
                    return MercuryDefine.DIALOG1_TEXT_ZH;
                }
                if (TextUtils.equals(language, "zh")) {
                    return MercuryDefine.DIALOG1_TEXT_ZH;
                }
                if (TextUtils.equals(language, "ru")) {
                    return MercuryDefine.DIALOG1_TEXT_RU;
                }
                if (TextUtils.equals(language, "es")) {
                    return MercuryDefine.DIALOG1_TEXT_ES;
                }
                if (TextUtils.equals(language, "pt")) {
                    return MercuryDefine.DIALOG1_TEXT_PT;
                }
                if (TextUtils.equals(language, "in")) {
                    return MercuryDefine.DIALOG1_TEXT_IN;
                }
                if (TextUtils.equals(language, "ms")) {
                    return MercuryDefine.DIALOG1_TEXT_MS;
                }
                if (TextUtils.equals(language, "vi")) {
                    return MercuryDefine.DIALOG1_TEXT_VI;
                }
                if (TextUtils.equals(language, "th")) {
                    return MercuryDefine.DIALOG1_TEXT_TH;
                }
                if (TextUtils.equals(language, "it")) {
                    return MercuryDefine.DIALOG1_TEXT_IT;
                }
                if (TextUtils.equals(language, "tr")) {
                    return MercuryDefine.DIALOG1_TEXT_TR;
                }
                return MercuryDefine.DIALOG1_TEXT_EN;
            case APP_ID /*1*/:
                if (TextUtils.equals(language, "ko")) {
                    return MercuryDefine.DAILOG2_TEXT_KO;
                }
                if (TextUtils.equals(language, "fr")) {
                    return MercuryDefine.DAILOG2_TEXT_FR;
                }
                if (TextUtils.equals(language, "de")) {
                    return MercuryDefine.DAILOG2_TEXT_DE;
                }
                if (TextUtils.equals(language, "ja")) {
                    return MercuryDefine.DAILOG2_TEXT_JA;
                }
                if (TextUtils.equals(country, "tw")) {
                    return MercuryDefine.DAILOG2_TEXT_TW;
                }
                if (TextUtils.equals(language, "zh")) {
                    return MercuryDefine.DAILOG2_TEXT_ZH;
                }
                if (TextUtils.equals(language, "ru")) {
                    return MercuryDefine.DAILOG2_TEXT_RU;
                }
                if (TextUtils.equals(language, "es")) {
                    return MercuryDefine.DIALOG2_TEXT_ES;
                }
                if (TextUtils.equals(language, "pt")) {
                    return MercuryDefine.DIALOG2_TEXT_PT;
                }
                if (TextUtils.equals(language, "in")) {
                    return MercuryDefine.DIALOG2_TEXT_MS;
                }
                if (TextUtils.equals(language, "ms")) {
                    return MercuryDefine.DIALOG2_TEXT_MS;
                }
                if (TextUtils.equals(language, "vi")) {
                    return MercuryDefine.DIALOG2_TEXT_VI;
                }
                if (TextUtils.equals(language, "th")) {
                    return MercuryDefine.DIALOG2_TEXT_TH;
                }
                if (TextUtils.equals(language, "it")) {
                    return MercuryDefine.DIALOG2_TEXT_IT;
                }
                if (TextUtils.equals(language, "tr")) {
                    return MercuryDefine.DIALOG2_TEXT_TR;
                }
                return MercuryDefine.DAILOG2_TEXT_EN;
            default:
                return null;
        }
    }
}
