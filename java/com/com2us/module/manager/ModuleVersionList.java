package com.com2us.module.manager;

import android.text.TextUtils;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import org.json.JSONObject;

public class ModuleVersionList {
    private static ModuleVersionList moduleVersionList = new ModuleVersionList();
    private Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private JSONObject versionList = new JSONObject();

    public static ModuleVersionList getInstance() {
        return moduleVersionList;
    }

    private void getModuleVersion(String moduleName, String className, String methodName, String fieldName) {
        String version = null;
        try {
            Class<?> cls = Class.forName(className);
            if (!TextUtils.isEmpty(methodName)) {
                version = (String) cls.getMethod(methodName, new Class[0]).invoke(null, new Object[0]);
            } else if (!TextUtils.isEmpty(fieldName)) {
                version = (String) cls.getField(fieldName).get(cls);
            }
            this.logger.d("[ModuleVersionList] " + moduleName + " Version : " + version);
            if (TextUtils.isEmpty(moduleName) || TextUtils.isEmpty(version)) {
                this.logger.d("[ModuleVersionList] " + moduleName + " Version is Empty");
            } else {
                this.versionList.put(moduleName, version);
            }
        } catch (Exception e) {
            this.logger.d("[ModuleVersionList] " + moduleName + " getModuleVersion Exception : " + e.toString());
        }
    }

    private void getWrapperVersion(String moduleName, String className, String methodName) {
        try {
            Class<?> cls = Class.forName(className);
            String version = String.valueOf((Integer) cls.getMethod(methodName, new Class[0]).invoke(cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]), new Object[0]));
            this.logger.d("[ModuleVersionList] " + moduleName + " Version : " + version);
            if (TextUtils.isEmpty(moduleName) || TextUtils.isEmpty(version)) {
                this.logger.d("[ModuleVersionList] " + moduleName + "  Version is Empty");
            } else {
                this.versionList.put(moduleName, version);
            }
        } catch (Exception e) {
            this.logger.d("[ModuleVersionList] " + moduleName + " getWrapperVersion Exception : " + e.toString());
        }
    }

    public JSONObject getList() {
        JSONObject jSONObject = null;
        if (this.versionList.length() > 0) {
            return this.versionList;
        }
        try {
            getModuleVersion("c2s_platform", "com.com2us.module.C2SModuleConstants", null, "Version");
            getModuleVersion("c2s_modulemanager", "com.com2us.module.Constants", null, "Version");
            getModuleVersion("c2s_hub", "com.com2us.peppermint.Peppermint", "getVersion", null);
            getWrapperVersion("c2s_wrapper", "com.com2us.wrapper.kernel.CWrapperData", "getDevelopmentRevision");
            getModuleVersion("c2s_activeuser", "com.com2us.module.activeuser.Constants", null, "Version");
            getModuleVersion("c2s_inapp_default", "com.com2us.module.inapp.InApp", null, "MakingVersion");
            getModuleVersion("c2s_inapp_googleplay", "com.com2us.module.inapp.googleplay.GooglePlayBilling", null, "Ver");
            getModuleVersion("c2s_inapp_googleinapp", "com.com2us.module.inapp.googleinapp.GoogleInAppBilling", null, "Ver");
            getModuleVersion("c2s_inapp_tstore", "com.com2us.module.inapp.tstore.TstoreBilling", null, "Ver");
            getModuleVersion("c2s_inapp_ollehmarket", "com.com2us.module.inapp.ollehmarket.ollehMarketBilling", null, "Ver");
            getModuleVersion("c2s_inapp_ozstore", "com.com2us.module.inapp.ozstore.OZstoreBilling", null, "Ver");
            getModuleVersion("c2s_inapp_thirdpartybilling", "com.com2us.module.inapp.thirdpartybilling.ThirdPartyBilling", null, "Ver");
            getModuleVersion("c2s_inapp_qiip", "com.com2us.module.inapp.qiip.qiipBilling", null, "Ver");
            getModuleVersion("c2s_inapp_lebi", "com.com2us.module.inapp.lebi.LebiBilling", null, "Ver");
            getModuleVersion("c2s_inapp_plasma", "com.com2us.module.inapp.plasma.PlasmaBilling", null, "Ver");
            getModuleVersion("c2s_inapp_amazon", "com.com2us.module.inapp.amazon.AmazonBilling", null, "Ver");
            getModuleVersion("c2s_inapp_kddi", "com.com2us.module.inapp.kddi.KDDIBilling", null, "Ver");
            getModuleVersion("c2s_inapp_mm", "com.com2us.module.inapp.mm.MMBilling", null, "Ver");
            getModuleVersion("c2s_inapp_mbiz", "com.com2us.module.inapp.mbiz.MBizBilling", null, "Ver");
            getModuleVersion("c2s_push", "com.com2us.module.push.PushConfig", null, "VERSION");
            getModuleVersion("c2s_newsbanner", "com.com2us.module.newsbanner2.Constants", null, "Version");
            getModuleVersion("c2s_mercury", "com.com2us.module.mercury.Constants", null, "Version");
            getModuleVersion("c2s_socialmedia", ModuleClassName.SOCIALMEDIA, null, "Version");
            getModuleVersion("c2s_spider", "com.com2us.module.spider.Constants", null, "Version");
            getModuleVersion("c2s_mopub", "com.com2us.ad.mopub.Constants", null, "Version");
            getModuleVersion("c2s_tapjoy", "com.com2us.tapjoy.Constants", null, "Version");
            getModuleVersion("c2s_chartboost", "com.com2us.cpi.chartboost.Constants", null, "Version");
            getModuleVersion("c2s_offerwall", "com.com2us.module.offerwall.Constants", null, "Version");
            getModuleVersion("mopub", "com.mopub.mobileads.MoPub", null, "SDK_VERSION");
            getModuleVersion("admob", "com.google.ads.AdRequest", null, "VERSION");
            getModuleVersion("tapjoy", "com.tapjoy.TapjoyConstants", null, "TJC_LIBRARY_VERSION_NUMBER");
            getModuleVersion("chartboost", "com.chartboost.sdk.CBConstants", null, "CB_SDK_VERSION");
            getModuleVersion("millennial", "com.millennialmedia.android.MMSDK", null, "VERSION");
            return this.versionList;
        } catch (Exception e) {
            return jSONObject;
        }
    }
}
