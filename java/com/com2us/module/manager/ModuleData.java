package com.com2us.module.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.com2us.module.util.DeviceIdentity;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.util.WrapperUtility;
import com.facebook.internal.Utility;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import jp.co.cyberz.fox.a.a.i;

public class ModuleData {
    private static Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private static ModuleData mModuleData = new ModuleData();
    private String advertising_id = null;
    private String android_id = null;
    private String app_version = null;
    private int app_version_code = -1;
    private String appid = null;
    private Context context = null;
    private String country = null;
    private String device_id = null;
    private String device_model = null;
    private String did = null;
    private boolean isSet_bluestack = false;
    private boolean isSet_limit_ad_tracking = false;
    private boolean is_bluestack = false;
    private boolean is_limit_ad_tracking = false;
    private String language = null;
    private String mac_addr = null;
    private String mcc = null;
    private String mdn = null;
    private String mnc = null;
    private String os_version = null;
    private String serial_no = null;
    private String vendor_id = null;
    private String vid = null;

    interface OnGetAsyncAdvertisingIDListener {
        void onGetAsyncAdvertisingIDListener(String str);
    }

    interface OnGetAsyncDIDListener {
        void onGetAsyncDID(String str);
    }

    interface OnGetAsyncIsLimitAdTrackingListener {
        void onGetAsyncIsLimitAdTrackingListener(boolean z);
    }

    public interface SetupResultListener {
        void onSetupResult(boolean z);
    }

    private ModuleData() {
    }

    public static ModuleData getInstance(Context context) {
        if (context == null) {
            logger.d("[ModuleData] context of getInstance is null.");
            return null;
        }
        if (mModuleData == null) {
            mModuleData = new ModuleData();
        }
        mModuleData.context = context.getApplicationContext();
        return mModuleData;
    }

    public String getAppID() {
        if (this.appid == null) {
            try {
                String systemAppid = this.context.getPackageName();
                logger.d("[ModuleData] system getAppID : " + systemAppid);
                return systemAppid;
            } catch (Exception e) {
                this.appid = null;
                logger.d("[ModuleData] getAppID failed.");
            }
        }
        logger.d("[ModuleData] getAppID : " + this.appid);
        return this.appid;
    }

    public boolean setAppID(String appid) {
        try {
            this.appid = appid;
            logger.d("[ModuleData] setAppID : " + this.appid);
            return true;
        } catch (Exception e) {
            this.appid = null;
            logger.d("[ModuleData] setAppID failed. : " + appid);
            return false;
        }
    }

    public String getAppVersion() {
        if (this.app_version == null) {
            try {
                String systemAppVersion = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionName;
                logger.d("[ModuleData] system getAppVersion : " + systemAppVersion);
                return systemAppVersion;
            } catch (NameNotFoundException e) {
                this.app_version = null;
                logger.d("[ModuleData] getAppVersion failed. (NameNotFoundException)");
            } catch (Exception e2) {
                this.app_version = null;
                logger.d("[ModuleData] getAppVersion failed. (Exception)");
            }
        }
        logger.d("[ModuleData] getAppVersion : " + this.app_version);
        return this.app_version;
    }

    public boolean setAppVersion(String app_version) {
        try {
            this.app_version = app_version;
            logger.d("[ModuleData] setAppVersion : " + this.app_version);
            return true;
        } catch (Exception e) {
            this.app_version = null;
            logger.d("[ModuleData] setAppVersion failed. : " + app_version);
            return false;
        }
    }

    public int getAppVersionCode() {
        if (this.app_version_code < 0) {
            try {
                int systemAppVersionCode = this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionCode;
                logger.d("[ModuleData] system getAppVersionCode : " + systemAppVersionCode);
                return systemAppVersionCode;
            } catch (NameNotFoundException e) {
                this.app_version_code = -1;
                logger.d("[ModuleData] getAppVersionCode failed. (NameNotFoundException)");
            } catch (Exception e2) {
                this.app_version_code = -1;
                logger.d("[ModuleData] getAppVersionCode failed. (Exception)");
            }
        }
        logger.d("[ModuleData] getAppVersionCode : " + this.app_version_code);
        return this.app_version_code;
    }

    public boolean setAppVersionCode(int app_version_code) {
        try {
            this.app_version_code = app_version_code;
            logger.d("[ModuleData] setAppVersionCode : " + this.app_version_code);
            return true;
        } catch (Exception e) {
            this.app_version_code = -1;
            logger.d("[ModuleData] setAppVersionCode failed. : " + app_version_code);
            return false;
        }
    }

    public String getDeviceModel() {
        if (this.device_model == null) {
            try {
                String systemDeviceModel = Build.MODEL;
                logger.d("[ModuleData] system getDeviceModel : " + systemDeviceModel);
                return systemDeviceModel;
            } catch (Exception e) {
                this.device_model = null;
                logger.d("[ModuleData] getDeviceModel failed.");
            }
        }
        logger.d("[ModuleData] getDeviceModel : " + this.device_model);
        return this.device_model;
    }

    public boolean setDeviceModel(String device_model) {
        try {
            this.device_model = device_model;
            logger.d("[ModuleData] setDeviceModel : " + this.device_model);
            return true;
        } catch (Exception e) {
            this.device_model = null;
            logger.d("[ModuleData] setDeviceModel failed. : " + device_model);
            return false;
        }
    }

    public String getDeviceID() {
        if (this.device_id == null) {
            try {
                String systemDeviceId = ((TelephonyManager) this.context.getSystemService("phone")).getDeviceId();
                logger.d("[ModuleData] system getDeviceID : " + systemDeviceId);
                return systemDeviceId;
            } catch (Exception e) {
                this.device_id = null;
                logger.d("[ModuleData] getDeviceID failed.");
            }
        }
        logger.d("[ModuleData] getDeviceID : " + this.device_id);
        return this.device_id;
    }

    public boolean setDeviceID(String device_id) {
        try {
            this.device_id = device_id;
            logger.d("[ModuleData] setDeviceID : " + this.device_id);
            return true;
        } catch (Exception e) {
            this.device_id = null;
            logger.d("[ModuleData] setDeviceID failed. : " + device_id);
            return false;
        }
    }

    public String getOSVersion() {
        if (this.os_version == null) {
            try {
                String systemOSVersion = VERSION.RELEASE;
                logger.d("[ModuleData] system getOSVersion : " + systemOSVersion);
                return systemOSVersion;
            } catch (Exception e) {
                this.os_version = null;
                logger.d("[ModuleData] getOSVersion failed.");
            }
        }
        logger.d("[ModuleData] getOSVersion : " + this.os_version);
        return this.os_version;
    }

    public boolean setOSVersion(String os_version) {
        try {
            this.os_version = os_version;
            logger.d("[ModuleData] setOSVersion : " + this.os_version);
            return true;
        } catch (Exception e) {
            this.os_version = null;
            logger.d("[ModuleData] setOSVersion failed. : " + os_version);
            return false;
        }
    }

    public String getCountry() {
        if (this.country == null) {
            try {
                String systemCountry = Locale.getDefault().getCountry().toLowerCase(Locale.US);
                if (TextUtils.isEmpty(systemCountry)) {
                    systemCountry = null;
                }
                logger.d("[ModuleData] system getCountry : " + systemCountry);
                return systemCountry;
            } catch (Exception e) {
                this.country = null;
                logger.d("[ModuleData] getCountry failed.");
            }
        }
        logger.d("[ModuleData] getCountry : " + this.country);
        return this.country;
    }

    public boolean setCountry(String country) {
        try {
            this.country = country;
            logger.d("[ModuleData] setCountry : " + this.country);
            return true;
        } catch (Exception e) {
            this.country = null;
            logger.d("[ModuleData] setCountry failed. : " + country);
            return false;
        }
    }

    public String getLanguage() {
        if (this.language == null) {
            try {
                String systemLanguage = Locale.getDefault().getLanguage().toLowerCase(Locale.US);
                if (TextUtils.isEmpty(systemLanguage)) {
                    systemLanguage = null;
                }
                logger.d("[ModuleData] system getLanguage : " + systemLanguage);
                return systemLanguage;
            } catch (Exception e) {
                this.language = null;
                logger.d("[ModuleData] getLanguage failed.");
            }
        }
        logger.d("[ModuleData] getLanguage : " + this.language);
        return this.language;
    }

    public boolean setLanguage(String language) {
        try {
            this.language = language;
            logger.d("[ModuleData] setLanguage : " + this.language);
            return true;
        } catch (Exception e) {
            this.language = null;
            logger.d("[ModuleData] setLanguage failed. : " + language);
            return false;
        }
    }

    public String getMacAddress() {
        if (this.mac_addr == null) {
            try {
                String systemMacAddr = WrapperUtility.getMacAddress(this.context);
                logger.d("[ModuleData] system getMacAddress : " + systemMacAddr);
                return systemMacAddr;
            } catch (Exception e) {
                this.mac_addr = null;
                logger.d("[ModuleData] getMacAddress failed.");
            }
        }
        logger.d("[ModuleData] getMacAddress : " + this.mac_addr);
        return this.mac_addr;
    }

    public boolean setMacAddress(String mac_addr) {
        try {
            this.mac_addr = mac_addr;
            logger.d("[ModuleData] setMacAddress : " + this.mac_addr);
            return true;
        } catch (Exception e) {
            this.mac_addr = null;
            logger.d("[ModuleData] setMacAddress failed. : " + mac_addr);
            return false;
        }
    }

    public String getMobileDeviceNumber() {
        if (this.mdn == null) {
            try {
                String systemMDN = ((TelephonyManager) this.context.getSystemService("phone")).getLine1Number();
                logger.d("[ModuleData] system getMobileDeviceNumber : " + systemMDN);
                return systemMDN;
            } catch (Exception e) {
                this.mdn = null;
                logger.d("[ModuleData] getMobileDeviceNumber failed.");
            }
        }
        logger.d("[ModuleData] getMobileDeviceNumber : " + this.mdn);
        return this.mdn;
    }

    public boolean setMobileDeviceNumber(String mdn) {
        try {
            this.mdn = mdn;
            logger.d("[ModuleData] setMobileDeviceNumber : " + this.mdn);
            return true;
        } catch (Exception e) {
            this.mdn = null;
            logger.d("[ModuleData] setMobileDeviceNumber failed. : " + mdn);
            return false;
        }
    }

    public String getMobileCountryCode() {
        if (this.mcc == null) {
            try {
                TelephonyManager teleManager = (TelephonyManager) this.context.getSystemService("phone");
                String operatorCode = teleManager.getNetworkOperator();
                if (TextUtils.isEmpty(operatorCode)) {
                    if (teleManager.getSimState() == 5) {
                        operatorCode = teleManager.getSimOperator();
                    } else {
                        operatorCode = null;
                    }
                }
                if (TextUtils.isEmpty(operatorCode)) {
                    this.mcc = null;
                    logger.d("[ModuleData] system getMobileCountryCode is empty.");
                } else {
                    String systemMCC = operatorCode.substring(0, 3);
                    logger.d("[ModuleData] system getMobileCountryCode : " + systemMCC);
                    return systemMCC;
                }
            } catch (Exception e) {
                this.mcc = null;
                logger.d("[ModuleData] getMobileCountryCode failed.");
            }
        }
        logger.d("[ModuleData] getMobileCountryCode : " + this.mcc);
        return this.mcc;
    }

    public boolean setMobileCountryCode(String mcc) {
        try {
            this.mcc = mcc;
            logger.d("[ModuleData] setMobileCountryCode : " + this.mcc);
            return true;
        } catch (Exception e) {
            this.mcc = null;
            logger.d("[ModuleData] setMobileCountryCode failed. : " + mcc);
            return false;
        }
    }

    public String getMobileNetworkCode() {
        if (this.mnc == null) {
            try {
                TelephonyManager teleManager = (TelephonyManager) this.context.getSystemService("phone");
                String operatorCode = teleManager.getNetworkOperator();
                if (TextUtils.isEmpty(operatorCode)) {
                    if (teleManager.getSimState() == 5) {
                        operatorCode = teleManager.getSimOperator();
                    } else {
                        operatorCode = null;
                    }
                }
                if (TextUtils.isEmpty(operatorCode)) {
                    this.mnc = null;
                    logger.d("[ModuleData] system getMobileNetworkCode is empty.");
                } else {
                    String systemMNC = operatorCode.substring(3);
                    logger.d("[ModuleData] system getMobileNetworkCode : " + systemMNC);
                    return systemMNC;
                }
            } catch (Exception e) {
                this.mnc = null;
                logger.d("[ModuleData] getMobileNetworkCode failed.");
            }
        }
        logger.d("[ModuleData] getMobileNetworkCode : " + this.mnc);
        return this.mnc;
    }

    public boolean setMobileNetworkCode(String mnc) {
        try {
            this.mnc = mnc;
            logger.d("[ModuleData] setMobileNetworkCode : " + this.mnc);
            return true;
        } catch (Exception e) {
            this.mnc = null;
            logger.d("[ModuleData] setMobileNetworkCode failed. : " + mnc);
            return false;
        }
    }

    public String getDID() {
        if (this.did == null) {
            try {
                String systemDID = DeviceIdentity.getDIDSynchronized(this.context, false);
                if (TextUtils.isEmpty(systemDID)) {
                    systemDID = null;
                }
                logger.d("[ModuleData] system getDID : " + systemDID);
                return systemDID;
            } catch (Exception e) {
                this.did = null;
                logger.d("[ModuleData] getDID failed.");
            }
        }
        logger.d("[ModuleData] getDID : " + this.did);
        return this.did;
    }

    public synchronized String getSyncDID() {
        String systemDID;
        if (this.did == null) {
            try {
                systemDID = DeviceIdentity.getDIDSynchronized(this.context, true);
                if (TextUtils.isEmpty(systemDID)) {
                    systemDID = null;
                }
                logger.d("[ModuleData] system getSyncDID : " + systemDID);
            } catch (Exception e) {
                this.did = null;
                logger.d("[ModuleData] getSyncDID failed.");
            }
        }
        logger.d("[ModuleData] getSyncDID : " + this.did);
        systemDID = this.did;
        return systemDID;
    }

    public void getAsyncDID(final OnGetAsyncDIDListener listener) {
        if (this.did == null) {
            try {
                new Thread(new Runnable() {
                    public void run() {
                        String systemDID = DeviceIdentity.getDIDSynchronized(ModuleData.this.context, true);
                        if (TextUtils.isEmpty(systemDID)) {
                            systemDID = null;
                        }
                        ModuleData.logger.d("[ModuleData] system getAsyncDID : " + systemDID);
                        listener.onGetAsyncDID(systemDID);
                    }
                }).start();
                return;
            } catch (Exception e) {
                this.did = null;
                logger.d("[ModuleData] getAsyncDID failed.");
                listener.onGetAsyncDID(this.did);
                return;
            }
        }
        logger.d("[ModuleData] getAsyncDID : " + this.did);
        listener.onGetAsyncDID(this.did);
    }

    public boolean setDID(String did) {
        try {
            this.did = did;
            logger.d("[ModuleData] setDID : " + this.did);
            return true;
        } catch (Exception e) {
            this.did = null;
            logger.d("[ModuleData] setDID failed. : " + did);
            return false;
        }
    }

    public String getVID() {
        if (this.vid == null) {
            try {
                String systemVid = (String) Class.forName("com.com2us.module.C2SModule").getMethod("getVID", new Class[0]).invoke(null, new Object[0]);
                logger.d("[ModuleData] system getVID : " + systemVid);
                return systemVid;
            } catch (Exception e) {
                this.vid = null;
                logger.d("[ModuleData] not found Platform Module, system getVID failed.");
            }
        }
        logger.d("[ModuleData] getVID : " + this.vid);
        return this.vid;
    }

    public boolean setVID(String vid) {
        try {
            this.vid = vid;
            logger.d("[ModuleData] setVID : " + this.vid);
            return true;
        } catch (Exception e) {
            this.vid = null;
            logger.d("[ModuleData] setVID failed. : " + vid);
            return false;
        }
    }

    public synchronized String getSyncAdvertisingID() {
        String id;
        if (this.advertising_id == null) {
            id = i.a;
            try {
                Method mdthod_getAdvertisingIdInfo = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class});
                Class<?> cls_Info = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
                id = (String) cls_Info.getMethod("getId", new Class[0]).invoke(mdthod_getAdvertisingIdInfo.invoke(null, new Object[]{this.context}), new Object[0]);
                logger.d("[ModuleData] system getSyncAdvertisingID : " + id);
            } catch (Exception e) {
                this.advertising_id = null;
                logger.d("[ModuleData] getSyncAdvertisingID failed.");
            }
        }
        logger.d("[ModuleData] getSyncAdvertisingID : " + this.advertising_id);
        id = this.advertising_id;
        return id;
    }

    public void getAsyncAdvertisingID(final OnGetAsyncAdvertisingIDListener listener) {
        if (this.advertising_id == null) {
            try {
                new Thread(new Runnable() {
                    public void run() {
                        String id = i.a;
                        try {
                            Method mdthod_getAdvertisingIdInfo = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class});
                            Class<?> cls_Info = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
                            id = (String) cls_Info.getMethod("getId", new Class[0]).invoke(mdthod_getAdvertisingIdInfo.invoke(null, new Object[]{ModuleData.this.context}), new Object[0]);
                            ModuleData.logger.d("[ModuleData] system getAsyncAdvertisingID : " + id);
                            listener.onGetAsyncAdvertisingIDListener(id);
                        } catch (Exception e) {
                            ModuleData.logger.d("[ModuleData] getAsyncAdvertisingID failed.");
                            listener.onGetAsyncAdvertisingIDListener(null);
                        }
                    }
                }).start();
                return;
            } catch (Exception e) {
                this.advertising_id = null;
                logger.d("[ModuleData] getAsyncAdvertisingID failed.");
                listener.onGetAsyncAdvertisingIDListener(this.advertising_id);
                return;
            }
        }
        listener.onGetAsyncAdvertisingIDListener(this.advertising_id);
        logger.d("[ModuleData] getAsyncAdvertisingID : " + this.advertising_id);
    }

    public boolean setAdvertisingID(String advertising_id) {
        try {
            this.advertising_id = advertising_id;
            logger.d("[ModuleData] setAdvertisingID : " + this.advertising_id);
            return true;
        } catch (Exception e) {
            this.advertising_id = null;
            logger.d("[ModuleData] setAdvertisingID failed. : " + advertising_id);
            return false;
        }
    }

    public String getAndroidID() {
        if (this.android_id == null) {
            try {
                String systemAndroidId = Secure.getString(this.context.getContentResolver(), "android_id");
                if (TextUtils.isEmpty(systemAndroidId)) {
                    systemAndroidId = null;
                }
                logger.d("[ModuleData] system getAndroidID : " + systemAndroidId);
                return systemAndroidId;
            } catch (Exception e) {
                this.android_id = null;
                logger.d("[ModuleData] getAndroidID failed.");
            }
        }
        logger.d("[ModuleData] getAndroidID : " + this.android_id);
        return this.android_id;
    }

    public boolean setAndroidID(String android_id) {
        try {
            this.android_id = android_id;
            logger.d("[ModuleData] setAndroidID : " + this.android_id);
            return true;
        } catch (Exception e) {
            this.android_id = null;
            logger.d("[ModuleData] setAndroidID failed. : " + android_id);
            return false;
        }
    }

    public synchronized boolean getSyncIsLimitAdTracking() {
        boolean isLAT;
        if (!this.isSet_limit_ad_tracking) {
            try {
                Method mdthod_getAdvertisingIdInfo = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class});
                Class<?> cls_Info = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
                isLAT = ((Boolean) cls_Info.getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(mdthod_getAdvertisingIdInfo.invoke(null, new Object[]{this.context}), new Object[0])).booleanValue();
                logger.d("[ModuleData] system getSyncIsLimitAdTracking : " + isLAT);
            } catch (Exception e) {
                this.isSet_limit_ad_tracking = false;
                this.is_limit_ad_tracking = false;
                logger.d("[ModuleData] getSyncIsLimitAdTracking failed.");
            }
        }
        logger.d("[ModuleData] getSyncIsLimitAdTracking : " + this.is_limit_ad_tracking);
        isLAT = this.is_limit_ad_tracking;
        return isLAT;
    }

    public void getAsyncIsLimitAdTracking(final OnGetAsyncIsLimitAdTrackingListener listener) {
        if (this.isSet_limit_ad_tracking) {
            logger.d("[ModuleData] getAsyncIsLimitAdTracking : " + this.is_limit_ad_tracking);
            listener.onGetAsyncIsLimitAdTrackingListener(this.is_limit_ad_tracking);
            return;
        }
        try {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Method mdthod_getAdvertisingIdInfo = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class});
                        Class<?> cls_Info = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
                        boolean isLAT = ((Boolean) cls_Info.getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(mdthod_getAdvertisingIdInfo.invoke(null, new Object[]{ModuleData.this.context}), new Object[0])).booleanValue();
                        ModuleData.logger.d("[ModuleData] system getAsyncIsLimitAdTracking : " + isLAT);
                        listener.onGetAsyncIsLimitAdTrackingListener(isLAT);
                    } catch (Exception e) {
                        ModuleData.logger.d("[ModuleData] system getAsyncIsLimitAdTracking failed.");
                        listener.onGetAsyncIsLimitAdTrackingListener(false);
                    }
                }
            }).start();
        } catch (Exception e) {
            this.isSet_limit_ad_tracking = false;
            this.is_limit_ad_tracking = false;
            logger.d("[ModuleData] getAsyncIsLimitAdTracking failed.");
            listener.onGetAsyncIsLimitAdTrackingListener(this.is_limit_ad_tracking);
        }
    }

    public boolean setIsLimitAdTracking(boolean is_limit_ad_tracking) {
        try {
            this.is_limit_ad_tracking = is_limit_ad_tracking;
            this.isSet_limit_ad_tracking = true;
            logger.d("[ModuleData] setIsLimitAdTracking : " + this.is_limit_ad_tracking);
            return true;
        } catch (Exception e) {
            this.isSet_limit_ad_tracking = false;
            this.is_limit_ad_tracking = false;
            logger.d("[ModuleData] setIsLimitAdTracking failed. : " + is_limit_ad_tracking);
            return false;
        }
    }

    public boolean getIsBlueStack() {
        if (!this.isSet_bluestack) {
            try {
                StringBuffer strbuf = new StringBuffer();
                List<PackageInfo> appInfoList = this.context.getPackageManager().getInstalledPackages(Utility.DEFAULT_STREAM_BUFFER_SIZE);
                int jend = appInfoList.size();
                for (int j = 0; j < jend; j++) {
                    PackageInfo pi = (PackageInfo) appInfoList.get(j);
                    strbuf.append(pi.applicationInfo.packageName.startsWith("com.bluestacks.appsyncer") ? i.a : pi.applicationInfo.packageName).append("\n");
                }
                if (strbuf.toString().split("com.bluestacks").length >= 2) {
                    logger.d("[ModuleData] system getIsBlueStack : true");
                    return true;
                }
                logger.d("[ModuleData] system getIsBlueStack : false");
                return false;
            } catch (Exception e) {
                this.isSet_bluestack = false;
                this.is_bluestack = false;
                logger.d("[ModuleData] getIsBlueStack failed.");
            }
        }
        logger.d("[ModuleData] getIsBlueStack : " + this.is_bluestack);
        return this.is_bluestack;
    }

    public boolean setIsBlueStack(boolean is_bluestack) {
        try {
            this.is_bluestack = is_bluestack;
            this.isSet_bluestack = true;
            logger.d("[ModuleData] setIsBlueStack : " + this.is_bluestack);
            return true;
        } catch (Exception e) {
            this.isSet_bluestack = false;
            this.is_bluestack = false;
            logger.d("[ModuleData] setIsBlueStack failed. : " + is_bluestack);
            return false;
        }
    }

    public String getDeviceSerialNumber() {
        if (this.serial_no == null) {
            String system_Serial_no;
            if (VERSION.SDK_INT < 9) {
                try {
                    Class<?> c = Class.forName("android.os.SystemProperties");
                    system_Serial_no = (String) c.getMethod("get", new Class[]{String.class}).invoke(c, new Object[]{"ro.serialno"});
                } catch (Exception e) {
                    system_Serial_no = null;
                    logger.d("[ModuleData] system getDeviceSerialNumber failed.");
                }
            } else {
                system_Serial_no = Build.SERIAL;
            }
            logger.d("[ModuleData] system getDeviceSerialNumber : " + system_Serial_no);
            return system_Serial_no;
        }
        logger.d("[ModuleData] getDeviceSerialNumber : " + this.serial_no);
        return this.serial_no;
    }

    public boolean setDeviceSerialNumber(String serial_no) {
        try {
            this.serial_no = serial_no;
            logger.d("[ModuleData] setDeviceSerialNumber : " + this.serial_no);
            return true;
        } catch (Exception e) {
            this.serial_no = null;
            logger.d("[ModuleData] setDeviceSerialNumber failed. : " + serial_no);
            return false;
        }
    }

    public String getVendorID() {
        return this.vendor_id;
    }

    public boolean setVendorID(String vendor_id) {
        try {
            this.vendor_id = vendor_id;
            logger.d("[ModuleData] setVendorID : " + this.vendor_id);
            return true;
        } catch (Exception e) {
            this.vendor_id = null;
            logger.d("[ModuleData] setVendorID failed. : " + vendor_id);
            return false;
        }
    }

    public boolean resetData() {
        try {
            this.appid = null;
            this.app_version = null;
            this.app_version_code = -1;
            this.device_model = null;
            this.device_id = null;
            this.os_version = null;
            this.country = null;
            this.language = null;
            this.mac_addr = null;
            this.mdn = null;
            this.mcc = null;
            this.mnc = null;
            this.did = null;
            this.vid = null;
            this.advertising_id = null;
            this.android_id = null;
            this.isSet_limit_ad_tracking = false;
            this.is_limit_ad_tracking = false;
            this.isSet_bluestack = false;
            this.is_bluestack = false;
            this.serial_no = null;
            this.vendor_id = null;
            logger.d("[ModuleData] resetData success.");
            return true;
        } catch (Exception e) {
            logger.d("[ModuleData] resetData failed. : " + e.toString());
            return false;
        }
    }

    public boolean showSetupUI(Activity activity, SetupResultListener result) {
        return true;
    }
}
