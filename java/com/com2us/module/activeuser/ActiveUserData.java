package com.com2us.module.activeuser;

import android.content.Context;
import android.text.TextUtils;
import com.com2us.module.activeuser.downloadcheck.InstallService;
import com.com2us.module.manager.ModuleData;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.offerwall.OfferwallData;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.smon.google.service.util.GameHelper;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.facebook.internal.ServerProtocol;
import com.wellbia.xigncode.util.WBBase64;
import java.util.HashMap;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;

public class ActiveUserData {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$activeuser$ActiveUserData$DATA_INDEX;
    private static final HashMap<Integer, String> constanceDataMap = new HashMap();
    private static Logger logger = LoggerGroup.createLogger(InstallService.TAG);
    private static ModuleData moduleData = null;

    public enum DATA_INDEX {
        APP_ID,
        APP_VERSION,
        DEVICE_MODEL,
        DEVICE_ID,
        OS_VERSION,
        COUNTRY,
        ANDROID_ID,
        SERIAL_NO,
        MAC_ADDR,
        LINE_NUMBER,
        LANGUAGE,
        ISBLUESTACKS,
        APP_VERSIONCODE,
        MCC_CODE,
        MNC_CODE,
        LOCAL_AGREEMENT_VERSION,
        LOCAL_AGREEMENT_FILENAME,
        VID,
        ADVERTISING_ID,
        IS_LIMIT_AD_TRACKING,
        SERVER_ID,
        RESOURCE_OFFSET
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$activeuser$ActiveUserData$DATA_INDEX() {
        int[] iArr = $SWITCH_TABLE$com$com2us$module$activeuser$ActiveUserData$DATA_INDEX;
        if (iArr == null) {
            iArr = new int[DATA_INDEX.values().length];
            try {
                iArr[DATA_INDEX.ADVERTISING_ID.ordinal()] = 19;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[DATA_INDEX.ANDROID_ID.ordinal()] = 7;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[DATA_INDEX.APP_ID.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[DATA_INDEX.APP_VERSION.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[DATA_INDEX.APP_VERSIONCODE.ordinal()] = 13;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[DATA_INDEX.COUNTRY.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[DATA_INDEX.DEVICE_ID.ordinal()] = 4;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[DATA_INDEX.DEVICE_MODEL.ordinal()] = 3;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[DATA_INDEX.ISBLUESTACKS.ordinal()] = 12;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[DATA_INDEX.IS_LIMIT_AD_TRACKING.ordinal()] = 20;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[DATA_INDEX.LANGUAGE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[DATA_INDEX.LINE_NUMBER.ordinal()] = 10;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr[DATA_INDEX.LOCAL_AGREEMENT_FILENAME.ordinal()] = 17;
            } catch (NoSuchFieldError e13) {
            }
            try {
                iArr[DATA_INDEX.LOCAL_AGREEMENT_VERSION.ordinal()] = 16;
            } catch (NoSuchFieldError e14) {
            }
            try {
                iArr[DATA_INDEX.MAC_ADDR.ordinal()] = 9;
            } catch (NoSuchFieldError e15) {
            }
            try {
                iArr[DATA_INDEX.MCC_CODE.ordinal()] = 14;
            } catch (NoSuchFieldError e16) {
            }
            try {
                iArr[DATA_INDEX.MNC_CODE.ordinal()] = 15;
            } catch (NoSuchFieldError e17) {
            }
            try {
                iArr[DATA_INDEX.OS_VERSION.ordinal()] = 5;
            } catch (NoSuchFieldError e18) {
            }
            try {
                iArr[DATA_INDEX.RESOURCE_OFFSET.ordinal()] = 22;
            } catch (NoSuchFieldError e19) {
            }
            try {
                iArr[DATA_INDEX.SERIAL_NO.ordinal()] = 8;
            } catch (NoSuchFieldError e20) {
            }
            try {
                iArr[DATA_INDEX.SERVER_ID.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                iArr[DATA_INDEX.VID.ordinal()] = 18;
            } catch (NoSuchFieldError e22) {
            }
            $SWITCH_TABLE$com$com2us$module$activeuser$ActiveUserData$DATA_INDEX = iArr;
        }
        return iArr;
    }

    public static String get(DATA_INDEX index) {
        return get(null, index);
    }

    public static String get(Context context, DATA_INDEX index) {
        String ret;
        if (moduleData == null) {
            if (context != null) {
                create(context);
            } else {
                logger.d("[ActiveUserData] get failed. moduleData is null. call create() first.");
                return null;
            }
        }
        switch ($SWITCH_TABLE$com$com2us$module$activeuser$ActiveUserData$DATA_INDEX()[index.ordinal()]) {
            case o.a /*1*/:
                ret = moduleData.getAppID();
                break;
            case o.b /*2*/:
                ret = moduleData.getAppVersion();
                break;
            case o.c /*3*/:
                ret = moduleData.getDeviceModel();
                break;
            case o.d /*4*/:
                ret = moduleData.getDeviceID();
                break;
            case f.bc /*5*/:
                ret = moduleData.getOSVersion();
                break;
            case f.aL /*6*/:
                ret = moduleData.getCountry();
                break;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
                ret = moduleData.getAndroidID();
                break;
            case WBBase64.URL_SAFE /*8*/:
                ret = moduleData.getDeviceSerialNumber();
                break;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoTextColor /*9*/:
                ret = moduleData.getMacAddress();
                break;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoImageType /*10*/:
                ret = moduleData.getMobileDeviceNumber();
                break;
            case R.styleable.MapAttrs_uiZoomGestures /*11*/:
                ret = moduleData.getLanguage();
                break;
            case R.styleable.MapAttrs_useViewLifecycle /*12*/:
                ret = moduleData.getIsBlueStack() ? a.e : a.d;
                break;
            case R.styleable.MapAttrs_zOrderOnTop /*13*/:
                ret = String.valueOf(moduleData.getAppVersionCode());
                break;
            case f.r /*14*/:
                ret = moduleData.getMobileCountryCode();
                break;
            case GameHelper.CLIENT_ALL /*15*/:
                ret = moduleData.getMobileNetworkCode();
                break;
            case OfferwallData.MCC /*18*/:
                ret = moduleData.getVID();
                break;
            case Encoder.LINE_GROUPS /*19*/:
                ret = moduleData.getSyncAdvertisingID();
                break;
            case OfferwallData.PATH /*20*/:
                ret = moduleData.getSyncIsLimitAdTracking() ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false";
                break;
            default:
                ret = (String) constanceDataMap.get(Integer.valueOf(index.ordinal()));
                break;
        }
        logger.d("[ActiveUserData] get " + index.name() + " : " + ret);
        return ret;
    }

    public static void setAppid(Context context, String appid) {
        ModuleManager.getDatas(context).setAppID(appid);
    }

    public static void setServerId(String server_id) {
        constanceDataMap.put(Integer.valueOf(DATA_INDEX.SERVER_ID.ordinal()), server_id);
    }

    public static void create(Context context) {
        create(context, false);
    }

    public static void createOnInstalled(Context context) {
        create(context, true);
    }

    private static void create(Context context, boolean onInstalled) {
        logger.d("[ActiveUserData] create");
        moduleData = ModuleManager.getDatas(context);
        String resourceOffset = i.a;
        try {
            if (context.getPackageName().startsWith("com.com2us")) {
                resourceOffset = "_com2us";
            } else if (context.getPackageName().startsWith("com.gamevil")) {
                resourceOffset = "_gamevil";
            } else {
                resourceOffset = i.a;
            }
            constanceDataMap.put(Integer.valueOf(DATA_INDEX.RESOURCE_OFFSET.ordinal()), resourceOffset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int mcc = 0;
        String str_mcc = moduleData.getMobileCountryCode();
        if (!TextUtils.isEmpty(str_mcc)) {
            try {
                mcc = Integer.parseInt(str_mcc);
            } catch (Exception e2) {
                e2.printStackTrace();
                mcc = 0;
            }
        }
        setAgreementLocalData(context, mcc, moduleData.getLanguage(), moduleData.getCountry());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void setAgreementLocalData(android.content.Context r22, int r23, java.lang.String r24, java.lang.String r25) {
        /*
        r18 = logger;
        r19 = "[ActiveUserData] setAgreementLocalData";
        r18.d(r19);
        r15 = 0;
        r4 = "";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r11 = "common/ActiveUserAgreement/au_terms_meta.json";
        r18 = r22.getPackageName();	 Catch:{ Exception -> 0x006c }
        r19 = "com.com2us";
        r18 = r18.startsWith(r19);	 Catch:{ Exception -> 0x006c }
        if (r18 == 0) goto L_0x005a;
    L_0x001d:
        r11 = "common/ActiveUserAgreement/com2us/au_terms_meta.json";
    L_0x001f:
        r18 = r22.getResources();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r18 = r18.getAssets();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r0 = r18;
        r12 = r0.open(r11);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r13 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r18 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r0 = r18;
        r0.<init>(r12);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r0 = r18;
        r13.<init>(r0);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
    L_0x003b:
        r14 = r13.readLine();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        if (r14 != 0) goto L_0x008b;
    L_0x0041:
        r13.close();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r6 = 0;
        r7 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x02d6, Exception -> 0x0351, IOException -> 0x008f }
        r18 = r2.toString();	 Catch:{ JSONException -> 0x02d6, Exception -> 0x0351, IOException -> 0x008f }
        r0 = r18;
        r7.<init>(r0);	 Catch:{ JSONException -> 0x02d6, Exception -> 0x0351, IOException -> 0x008f }
        r5 = 0;
    L_0x0051:
        r18 = r7.length();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r0 = r18;
        if (r5 < r0) goto L_0x00cb;
    L_0x0059:
        return;
    L_0x005a:
        r18 = r22.getPackageName();	 Catch:{ Exception -> 0x006c }
        r19 = "com.gamevil";
        r18 = r18.startsWith(r19);	 Catch:{ Exception -> 0x006c }
        if (r18 == 0) goto L_0x0069;
    L_0x0066:
        r11 = "common/ActiveUserAgreement/gamevil/au_terms_meta.json";
        goto L_0x001f;
    L_0x0069:
        r11 = "common/ActiveUserAgreement/au_terms_meta.json";
        goto L_0x001f;
    L_0x006c:
        r3 = move-exception;
        r3.printStackTrace();
        r18 = logger;
        r19 = new java.lang.StringBuilder;
        r20 = "[ActiveUserData] setAgreementLocalData metaFilePath exception : ";
        r19.<init>(r20);
        r20 = r3.toString();
        r19 = r19.append(r20);
        r19 = r19.toString();
        r18.d(r19);
        r11 = "common/ActiveUserAgreement/au_terms_meta.json";
        goto L_0x001f;
    L_0x008b:
        r2.append(r14);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        goto L_0x003b;
    L_0x008f:
        r3 = move-exception;
        r18 = logger;
        r19 = new java.lang.StringBuilder;
        r20 = "[ActiveUserData] setAgreementLocalData IOException : ";
        r19.<init>(r20);
        r20 = r3.toString();
        r19 = r19.append(r20);
        r19 = r19.toString();
        r18.d(r19);
        r18 = constanceDataMap;
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_VERSION;
        r19 = r19.ordinal();
        r19 = java.lang.Integer.valueOf(r19);
        r20 = "0";
        r18.put(r19, r20);
        r18 = constanceDataMap;
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_FILENAME;
        r19 = r19.ordinal();
        r19 = java.lang.Integer.valueOf(r19);
        r20 = "";
        r18.put(r19, r20);
        goto L_0x0059;
    L_0x00cb:
        r8 = r7.getJSONObject(r5);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = "mcc";
        r0 = r18;
        r10 = r8.getJSONArray(r0);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r16 = 0;
    L_0x00d9:
        r18 = r10.length();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r0 = r16;
        r1 = r18;
        if (r0 < r1) goto L_0x0102;
    L_0x00e3:
        r18 = "country";
        r0 = r18;
        r9 = r8.getJSONArray(r0);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r17 = 0;
    L_0x00ed:
        r18 = r9.length();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r0 = r17;
        r1 = r18;
        if (r0 < r1) goto L_0x01e4;
    L_0x00f7:
        r18 = logger;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = "[ActiveUserData] setAgreementLocalData nothing";
        r18.d(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r5 = r5 + 1;
        goto L_0x0051;
    L_0x0102:
        r0 = r16;
        r18 = r10.getInt(r0);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r0 = r23;
        r1 = r18;
        if (r0 != r1) goto L_0x01e0;
    L_0x010e:
        r18 = "version";
        r0 = r18;
        r15 = r8.getInt(r0);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = "file";
        r0 = r18;
        r4 = r8.getString(r0);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = r22.getResources();	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = r18.getAssets();	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = "common/ActiveUserAgreement/";
        r19.<init>(r20);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r0 = r19;
        r19 = r0.append(r4);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.toString();	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18.open(r19);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = constanceDataMap;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_VERSION;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.ordinal();	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = java.lang.String.valueOf(r15);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18.put(r19, r20);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = constanceDataMap;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_FILENAME;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.ordinal();	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r21 = "common/ActiveUserAgreement/";
        r20.<init>(r21);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r0 = r20;
        r20 = r0.append(r4);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = r20.toString();	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18.put(r19, r20);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = logger;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = "[ActiveUserData] setAgreementLocalData version : ";
        r19.<init>(r20);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r0 = r19;
        r19 = r0.append(r15);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.toString();	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18.d(r19);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = logger;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = "[ActiveUserData] setAgreementLocalData fileName : ";
        r19.<init>(r20);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r0 = r19;
        r19 = r0.append(r4);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.toString();	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        r18.d(r19);	 Catch:{ Exception -> 0x019b, JSONException -> 0x0392, IOException -> 0x008f }
        goto L_0x0059;
    L_0x019b:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = logger;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = "[ActiveUserData] setAgreementLocalData nothing : ";
        r19.<init>(r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = r3.toString();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.append(r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.toString();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18.d(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = java.lang.System.out;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = "[ActiveUserData] setAgreementLocalData nothing";
        r18.println(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = constanceDataMap;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_VERSION;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.ordinal();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = "0";
        r18.put(r19, r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = constanceDataMap;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_FILENAME;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.ordinal();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = "";
        r18.put(r19, r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
    L_0x01e0:
        r16 = r16 + 1;
        goto L_0x00d9;
    L_0x01e4:
        r18 = java.util.Locale.US;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r0 = r25;
        r1 = r18;
        r18 = r0.toLowerCase(r1);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r0 = r17;
        r19 = r9.getString(r0);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = java.util.Locale.US;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.toLowerCase(r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = android.text.TextUtils.equals(r18, r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        if (r18 == 0) goto L_0x02d2;
    L_0x0200:
        r18 = "version";
        r0 = r18;
        r15 = r8.getInt(r0);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = "file";
        r0 = r18;
        r4 = r8.getString(r0);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = r22.getResources();	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = r18.getAssets();	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = "common/ActiveUserAgreement/";
        r19.<init>(r20);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r0 = r19;
        r19 = r0.append(r4);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.toString();	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18.open(r19);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = constanceDataMap;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_VERSION;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.ordinal();	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = java.lang.String.valueOf(r15);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18.put(r19, r20);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = constanceDataMap;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_FILENAME;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.ordinal();	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r21 = "common/ActiveUserAgreement/";
        r20.<init>(r21);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r0 = r20;
        r20 = r0.append(r4);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = r20.toString();	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18.put(r19, r20);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = logger;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = "[ActiveUserData] setAgreementLocalData version : ";
        r19.<init>(r20);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r0 = r19;
        r19 = r0.append(r15);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.toString();	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18.d(r19);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18 = logger;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r20 = "[ActiveUserData] setAgreementLocalData fileName : ";
        r19.<init>(r20);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r0 = r19;
        r19 = r0.append(r4);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r19 = r19.toString();	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        r18.d(r19);	 Catch:{ Exception -> 0x028d, JSONException -> 0x0392, IOException -> 0x008f }
        goto L_0x0059;
    L_0x028d:
        r3 = move-exception;
        r3.printStackTrace();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = logger;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = "[ActiveUserData] setAgreementLocalData nothing : ";
        r19.<init>(r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = r3.toString();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.append(r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.toString();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18.d(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = java.lang.System.out;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = "[ActiveUserData] setAgreementLocalData nothing";
        r18.println(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = constanceDataMap;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_VERSION;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.ordinal();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = "0";
        r18.put(r19, r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r18 = constanceDataMap;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_FILENAME;	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = r19.ordinal();	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
        r20 = "";
        r18.put(r19, r20);	 Catch:{ JSONException -> 0x0392, Exception -> 0x038f, IOException -> 0x008f }
    L_0x02d2:
        r17 = r17 + 1;
        goto L_0x00ed;
    L_0x02d6:
        r3 = move-exception;
    L_0x02d7:
        r18 = logger;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r20 = "[ActiveUserData] setAgreementLocalData JSONException : ";
        r19.<init>(r20);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r20 = r3.toString();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = r19.toString();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r18.d(r19);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r6 = 0;
        r18 = constanceDataMap;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_VERSION;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = r19.ordinal();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r20 = "0";
        r18.put(r19, r20);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r18 = constanceDataMap;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_FILENAME;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = r19.ordinal();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r20 = "";
        r18.put(r19, r20);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        goto L_0x0059;
    L_0x0314:
        r3 = move-exception;
        r18 = logger;
        r19 = new java.lang.StringBuilder;
        r20 = "[ActiveUserData] setAgreementLocalData unknown Exception : ";
        r19.<init>(r20);
        r20 = r3.toString();
        r19 = r19.append(r20);
        r19 = r19.toString();
        r18.d(r19);
        r18 = constanceDataMap;
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_VERSION;
        r19 = r19.ordinal();
        r19 = java.lang.Integer.valueOf(r19);
        r20 = "0";
        r18.put(r19, r20);
        r18 = constanceDataMap;
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_FILENAME;
        r19 = r19.ordinal();
        r19 = java.lang.Integer.valueOf(r19);
        r20 = "";
        r18.put(r19, r20);
        goto L_0x0059;
    L_0x0351:
        r3 = move-exception;
    L_0x0352:
        r18 = logger;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r20 = "[ActiveUserData] setAgreementLocalData Exception : ";
        r19.<init>(r20);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r20 = r3.toString();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = r19.append(r20);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = r19.toString();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r18.d(r19);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r6 = 0;
        r18 = constanceDataMap;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_VERSION;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = r19.ordinal();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r20 = "0";
        r18.put(r19, r20);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r18 = constanceDataMap;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = com.com2us.module.activeuser.ActiveUserData.DATA_INDEX.LOCAL_AGREEMENT_FILENAME;	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = r19.ordinal();	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r19 = java.lang.Integer.valueOf(r19);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        r20 = "";
        r18.put(r19, r20);	 Catch:{ IOException -> 0x008f, Exception -> 0x0314 }
        goto L_0x0059;
    L_0x038f:
        r3 = move-exception;
        r6 = r7;
        goto L_0x0352;
    L_0x0392:
        r3 = move-exception;
        r6 = r7;
        goto L_0x02d7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.activeuser.ActiveUserData.setAgreementLocalData(android.content.Context, int, java.lang.String, java.lang.String):void");
    }
}
