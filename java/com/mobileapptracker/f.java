package com.mobileapptracker;

import android.util.Log;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.PeppermintURL;
import com.facebook.internal.NativeProtocol;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

final class f {
    private static MATParameters a;

    public static synchronized String a(MATEvent mATEvent) {
        String stringBuilder;
        synchronized (f.class) {
            a = MATParameters.getInstance();
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("connection_type=" + a.getConnectionType());
            a(stringBuilder2, "age", a.getAge());
            a(stringBuilder2, "altitude", a.getAltitude());
            a(stringBuilder2, "android_id", a.getAndroidId());
            a(stringBuilder2, "android_id_md5", a.getAndroidIdMd5());
            a(stringBuilder2, "android_id_sha1", a.getAndroidIdSha1());
            a(stringBuilder2, "android_id_sha256", a.getAndroidIdSha256());
            a(stringBuilder2, "app_ad_tracking", a.getAppAdTrackingEnabled());
            a(stringBuilder2, NativeProtocol.BRIDGE_ARG_APP_NAME_STRING, a.getAppName());
            a(stringBuilder2, "app_version", a.getAppVersion());
            a(stringBuilder2, "app_version_name", a.getAppVersionName());
            a(stringBuilder2, "country_code", a.getCountryCode());
            a(stringBuilder2, "device_brand", a.getDeviceBrand());
            a(stringBuilder2, "device_carrier", a.getDeviceCarrier());
            a(stringBuilder2, "device_cpu_type", a.getDeviceCpuType());
            a(stringBuilder2, "device_cpu_subtype", a.getDeviceCpuSubtype());
            a(stringBuilder2, "device_model", a.getDeviceModel());
            a(stringBuilder2, "device_id", a.getDeviceId());
            a(stringBuilder2, "existing_user", a.getExistingUser());
            a(stringBuilder2, "facebook_user_id", a.getFacebookUserId());
            a(stringBuilder2, PeppermintConstant.JSON_KEY_GENDER, a.getGender());
            a(stringBuilder2, "google_aid", a.getGoogleAdvertisingId());
            a(stringBuilder2, "google_ad_tracking_disabled", a.getGoogleAdTrackingLimited());
            a(stringBuilder2, "google_user_id", a.getGoogleUserId());
            a(stringBuilder2, "insdate", a.getInstallDate());
            a(stringBuilder2, "installer", a.getInstaller());
            a(stringBuilder2, "install_referrer", a.getInstallReferrer());
            a(stringBuilder2, "is_paying_user", a.getIsPayingUser());
            a(stringBuilder2, PeppermintConstant.JSON_KEY_LANGUAGE, a.getLanguage());
            a(stringBuilder2, "last_open_log_id", a.getLastOpenLogId());
            a(stringBuilder2, "latitude", a.getLatitude());
            a(stringBuilder2, "longitude", a.getLongitude());
            a(stringBuilder2, "mac_address", a.getMacAddress());
            a(stringBuilder2, "mat_id", a.getMatId());
            a(stringBuilder2, "mobile_country_code", a.getMCC());
            a(stringBuilder2, "mobile_network_code", a.getMNC());
            a(stringBuilder2, "open_log_id", a.getOpenLogId());
            a(stringBuilder2, "os_version", a.getOsVersion());
            a(stringBuilder2, "sdk_plugin", a.getPluginName());
            a(stringBuilder2, "android_purchase_status", a.getPurchaseStatus());
            a(stringBuilder2, "referrer_delay", a.getReferrerDelay());
            a(stringBuilder2, "screen_density", a.getScreenDensity());
            a(stringBuilder2, "screen_layout_size", a.getScreenWidth() + "x" + a.getScreenHeight());
            a(stringBuilder2, "sdk_version", a.getSdkVersion());
            a(stringBuilder2, "truste_tpid", a.getTRUSTeId());
            a(stringBuilder2, "twitter_user_id", a.getTwitterUserId());
            a(stringBuilder2, "conversion_user_agent", a.getUserAgent());
            a(stringBuilder2, "user_email_md5", a.getUserEmailMd5());
            a(stringBuilder2, "user_email_sha1", a.getUserEmailSha1());
            a(stringBuilder2, "user_email_sha256", a.getUserEmailSha256());
            a(stringBuilder2, PeppermintConstant.JSON_KEY_USER_ID, a.getUserId());
            a(stringBuilder2, "user_name_md5", a.getUserNameMd5());
            a(stringBuilder2, "user_name_sha1", a.getUserNameSha1());
            a(stringBuilder2, "user_name_sha256", a.getUserNameSha256());
            a(stringBuilder2, "user_phone_md5", a.getPhoneNumberMd5());
            a(stringBuilder2, "user_phone_sha1", a.getPhoneNumberSha1());
            a(stringBuilder2, "user_phone_sha256", a.getPhoneNumberSha256());
            a(stringBuilder2, "attribute_sub1", mATEvent.getAttribute1());
            a(stringBuilder2, "attribute_sub2", mATEvent.getAttribute2());
            a(stringBuilder2, "attribute_sub3", mATEvent.getAttribute3());
            a(stringBuilder2, "attribute_sub4", mATEvent.getAttribute4());
            a(stringBuilder2, "attribute_sub5", mATEvent.getAttribute5());
            a(stringBuilder2, "content_id", mATEvent.getContentId());
            a(stringBuilder2, "content_type", mATEvent.getContentType());
            if (mATEvent.getCurrencyCode() != null) {
                a(stringBuilder2, "currency_code", mATEvent.getCurrencyCode());
            } else {
                a(stringBuilder2, "currency_code", a.getCurrencyCode());
            }
            if (mATEvent.getDate1() != null) {
                a(stringBuilder2, "date1", Long.toString(mATEvent.getDate1().getTime() / 1000));
            }
            if (mATEvent.getDate2() != null) {
                a(stringBuilder2, "date2", Long.toString(mATEvent.getDate2().getTime() / 1000));
            }
            if (mATEvent.getLevel() != 0) {
                a(stringBuilder2, "level", Integer.toString(mATEvent.getLevel()));
            }
            if (mATEvent.getQuantity() != 0) {
                a(stringBuilder2, "quantity", Integer.toString(mATEvent.getQuantity()));
            }
            if (mATEvent.getRating() != 0.0d) {
                a(stringBuilder2, "rating", Double.toString(mATEvent.getRating()));
            }
            a(stringBuilder2, "search_string", mATEvent.getSearchString());
            a(stringBuilder2, "advertiser_ref_id", mATEvent.getRefId());
            a(stringBuilder2, "revenue", Double.toString(mATEvent.getRevenue()));
            if (mATEvent.getDeviceForm() != null) {
                a(stringBuilder2, "device_form", mATEvent.getDeviceForm());
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }

    public static String a(MATEvent mATEvent, MATPreloadData mATPreloadData, boolean z) {
        a = MATParameters.getInstance();
        StringBuilder append = new StringBuilder(PeppermintURL.PEPPERMINT_HTTPS).append(a.getAdvertiserId()).append(".");
        if (z) {
            append.append("debug.engine.mobileapptracking.com");
        } else {
            append.append("engine.mobileapptracking.com");
        }
        append.append("/serve?ver=").append(a.getSdkVersion());
        append.append("&transaction_id=").append(UUID.randomUUID().toString());
        a(append, "sdk", "android");
        a(append, "action", a.getAction());
        a(append, "advertiser_id", a.getAdvertiserId());
        a(append, "package_name", a.getPackageName());
        a(append, "referral_source", a.getReferralSource());
        a(append, "referral_url", a.getReferralUrl());
        a(append, "site_id", a.getSiteId());
        a(append, "tracking_id", a.getTrackingId());
        if (mATEvent.getEventId() != 0) {
            a(append, "site_event_id", Integer.toString(mATEvent.getEventId()));
        }
        if (!a.getAction().equals("session")) {
            a(append, "site_event_name", mATEvent.getEventName());
        }
        if (mATPreloadData != null) {
            append.append("&attr_set=1");
            a(append, "publisher_id", mATPreloadData.publisherId);
            a(append, "offer_id", mATPreloadData.offerId);
            a(append, "agency_id", mATPreloadData.agencyId);
            a(append, "publisher_ref_id", mATPreloadData.publisherReferenceId);
            a(append, "publisher_sub_publisher", mATPreloadData.publisherSubPublisher);
            a(append, "publisher_sub_site", mATPreloadData.publisherSubSite);
            a(append, "publisher_sub_campaign", mATPreloadData.publisherSubCampaign);
            a(append, "publisher_sub_adgroup", mATPreloadData.publisherSubAdgroup);
            a(append, "publisher_sub_ad", mATPreloadData.publisherSubAd);
            a(append, "publisher_sub_keyword", mATPreloadData.publisherSubKeyword);
            a(append, "advertiser_sub_publisher", mATPreloadData.advertiserSubPublisher);
            a(append, "advertiser_sub_site", mATPreloadData.advertiserSubSite);
            a(append, "advertiser_sub_campaign", mATPreloadData.advertiserSubCampaign);
            a(append, "advertiser_sub_adgroup", mATPreloadData.advertiserSubAdgroup);
            a(append, "advertiser_sub_ad", mATPreloadData.advertiserSubAd);
            a(append, "advertiser_sub_keyword", mATPreloadData.advertiserSubKeyword);
            a(append, "publisher_sub1", mATPreloadData.publisherSub1);
            a(append, "publisher_sub2", mATPreloadData.publisherSub2);
            a(append, "publisher_sub3", mATPreloadData.publisherSub3);
            a(append, "publisher_sub4", mATPreloadData.publisherSub4);
            a(append, "publisher_sub5", mATPreloadData.publisherSub5);
        }
        String allowDuplicates = a.getAllowDuplicates();
        if (allowDuplicates != null && Integer.parseInt(allowDuplicates) == 1) {
            append.append("&skip_dup=1");
        }
        if (z) {
            append.append("&debug=1");
        }
        return append.toString();
    }

    public static synchronized String a(String str, MATEncryption mATEncryption) {
        String bytesToHex;
        synchronized (f.class) {
            StringBuilder stringBuilder = new StringBuilder(str);
            MATParameters instance = MATParameters.getInstance();
            a = instance;
            if (instance != null) {
                String googleAdvertisingId = a.getGoogleAdvertisingId();
                if (!(googleAdvertisingId == null || str.contains("&google_aid="))) {
                    a(stringBuilder, "google_aid", googleAdvertisingId);
                    a(stringBuilder, "google_ad_tracking_disabled", a.getGoogleAdTrackingLimited());
                }
                googleAdvertisingId = a.getAndroidId();
                if (!(googleAdvertisingId == null || str.contains("&android_id="))) {
                    a(stringBuilder, "android_id", googleAdvertisingId);
                }
                googleAdvertisingId = a.getInstallReferrer();
                if (!(googleAdvertisingId == null || str.contains("&install_referrer="))) {
                    a(stringBuilder, "install_referrer", googleAdvertisingId);
                }
                googleAdvertisingId = a.getUserAgent();
                if (!(googleAdvertisingId == null || str.contains("&conversion_user_agent="))) {
                    a(stringBuilder, "conversion_user_agent", googleAdvertisingId);
                }
                googleAdvertisingId = a.getFacebookUserId();
                if (!(googleAdvertisingId == null || str.contains("&facebook_user_id="))) {
                    a(stringBuilder, "facebook_user_id", googleAdvertisingId);
                }
            }
            if (!str.contains("&system_date=")) {
                a(stringBuilder, "system_date", Long.toString(new Date().getTime() / 1000));
            }
            try {
                bytesToHex = MATEncryption.bytesToHex(mATEncryption.encrypt(stringBuilder.toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bytesToHex;
    }

    public static synchronized JSONObject a(JSONArray jSONArray, String str, String str2, JSONArray jSONArray2) {
        JSONObject jSONObject;
        synchronized (f.class) {
            jSONObject = new JSONObject();
            if (jSONArray != null) {
                try {
                    jSONObject.put(PeppermintConstant.JSON_KEY_DATA, jSONArray);
                } catch (JSONException e) {
                    Log.d("MobileAppTracker", "Could not build JSON body of request");
                    e.printStackTrace();
                }
            }
            if (str != null) {
                jSONObject.put("store_iap_data", str);
            }
            if (str2 != null) {
                jSONObject.put("store_iap_signature", str2);
            }
            if (jSONArray2 != null) {
                jSONObject.put("user_emails", jSONArray2);
            }
        }
        return jSONObject;
    }

    private static synchronized void a(StringBuilder stringBuilder, String str, String str2) {
        synchronized (f.class) {
            if (str2 != null) {
                if (!str2.equals(i.a)) {
                    try {
                        stringBuilder.append("&" + str + "=" + URLEncoder.encode(str2, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        Log.w("MobileAppTracker", "failed encoding value " + str2 + " for key " + str);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
