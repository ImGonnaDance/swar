package com.chartboost.sdk.Tracking;

import android.text.TextUtils;
import android.util.Base64;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.e;
import com.chartboost.sdk.Libraries.e.a;
import com.chartboost.sdk.Libraries.g;
import com.chartboost.sdk.Libraries.h;
import com.chartboost.sdk.Model.CBError;
import com.chartboost.sdk.b;
import com.chartboost.sdk.impl.ba;
import com.chartboost.sdk.impl.ba.c;
import com.chartboost.sdk.impl.bb;
import com.com2us.peppermint.PeppermintConstant;
import java.io.File;
import java.util.EnumMap;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CBAnalytics {

    public enum CBIAPPurchaseInfo {
        PRODUCT_ID,
        PRODUCT_TITLE,
        PRODUCT_DESCRIPTION,
        PRODUCT_PRICE,
        PRODUCT_CURRENCY_CODE,
        GOOGLE_PURCHASE_DATA,
        GOOGLE_PURCHASE_SIGNATURE,
        AMAZON_PURCHASE_TOKEN,
        AMAZON_USER_ID
    }

    public enum CBIAPType {
        GOOGLE_PLAY,
        AMAZON
    }

    private CBAnalytics() {
    }

    public synchronized void trackInAppPurchaseEvent(EnumMap<CBIAPPurchaseInfo, String> map, CBIAPType iapType) {
        if (!(map == null || iapType == null)) {
            if (!(TextUtils.isEmpty((CharSequence) map.get(CBIAPPurchaseInfo.PRODUCT_ID)) || TextUtils.isEmpty((CharSequence) map.get(CBIAPPurchaseInfo.PRODUCT_TITLE)) || TextUtils.isEmpty((CharSequence) map.get(CBIAPPurchaseInfo.PRODUCT_DESCRIPTION)) || TextUtils.isEmpty((CharSequence) map.get(CBIAPPurchaseInfo.PRODUCT_PRICE)) || TextUtils.isEmpty((CharSequence) map.get(CBIAPPurchaseInfo.PRODUCT_CURRENCY_CODE)))) {
                a((String) map.get(CBIAPPurchaseInfo.PRODUCT_ID), (String) map.get(CBIAPPurchaseInfo.PRODUCT_TITLE), (String) map.get(CBIAPPurchaseInfo.PRODUCT_DESCRIPTION), (String) map.get(CBIAPPurchaseInfo.PRODUCT_PRICE), (String) map.get(CBIAPPurchaseInfo.PRODUCT_CURRENCY_CODE), (String) map.get(CBIAPPurchaseInfo.GOOGLE_PURCHASE_DATA), (String) map.get(CBIAPPurchaseInfo.GOOGLE_PURCHASE_SIGNATURE), (String) map.get(CBIAPPurchaseInfo.AMAZON_USER_ID), (String) map.get(CBIAPPurchaseInfo.AMAZON_PURCHASE_TOKEN), iapType);
            }
        }
        CBLogging.b("CBPostInstallTracker", "Null object is passed. Please pass a valid value object");
    }

    private static synchronized void a(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, CBIAPType cBIAPType) {
        synchronized (CBAnalytics.class) {
            if (b.k() == null) {
                CBLogging.b("CBPostInstallTracker", "You need call Chartboost.init() before calling any public API's");
            } else if (!b.l()) {
                CBLogging.b("CBPostInstallTracker", "You need call Chartboost.OnStart() before tracking in-app purchases");
            } else if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3) || TextUtils.isEmpty(str4) || TextUtils.isEmpty(str5)) {
                CBLogging.b("CBPostInstallTracker", "Null object is passed. Please pass a valid value object");
            } else {
                try {
                    Matcher matcher = Pattern.compile("(\\d+\\.\\d+)|(\\d+)").matcher(str4);
                    matcher.find();
                    Object group = matcher.group();
                    if (TextUtils.isEmpty(group)) {
                        CBLogging.b("CBPostInstallTracker", "Invalid price object");
                    } else {
                        float parseFloat = Float.parseFloat(group);
                        a aVar = null;
                        if (cBIAPType == CBIAPType.GOOGLE_PLAY) {
                            if (TextUtils.isEmpty(str6) || TextUtils.isEmpty(str7)) {
                                CBLogging.b("CBPostInstallTracker", "Null object is passed for for purchase data or purchase signature");
                            } else {
                                aVar = e.a(e.a("purchaseData", str6), e.a("purchaseSignature", str7), e.a(PeppermintConstant.JSON_KEY_TYPE, Integer.valueOf(CBIAPType.GOOGLE_PLAY.ordinal())));
                            }
                        } else if (cBIAPType == CBIAPType.AMAZON) {
                            if (TextUtils.isEmpty(str8) || TextUtils.isEmpty(str9)) {
                                CBLogging.b("CBPostInstallTracker", "Null object is passed for for amazon user id or amazon purchase token");
                            } else {
                                aVar = e.a(e.a("userID", str8), e.a("purchaseToken", str9), e.a(PeppermintConstant.JSON_KEY_TYPE, Integer.valueOf(CBIAPType.AMAZON.ordinal())));
                            }
                        }
                        if (aVar == null) {
                            CBLogging.b("CBPostInstallTracker", "Error while parsing the receipt to a JSON Object, ");
                        } else {
                            String encodeToString = Base64.encodeToString(aVar.toString().getBytes(), 2);
                            a(e.a(e.a("localized-title", str2), e.a("localized-description", str3), e.a("price", Float.valueOf(parseFloat)), e.a("currency", str5), e.a("productID", str), e.a("receipt", encodeToString)), "iap", cBIAPType);
                        }
                    }
                } catch (IllegalStateException e) {
                    CBLogging.b("CBPostInstallTracker", "Invalid price object");
                }
            }
        }
    }

    public static synchronized void trackInAppGooglePlayPurchaseEvent(String title, String description, String price, String currency, String productID, String purchaseData, String purchaseSignature) {
        synchronized (CBAnalytics.class) {
            a(productID, title, description, price, currency, purchaseData, purchaseSignature, null, null, CBIAPType.GOOGLE_PLAY);
        }
    }

    public static synchronized void trackInAppAmazonStorePurchaseEvent(String title, String description, String price, String currency, String productID, String userID, String purchaseToken) {
        synchronized (CBAnalytics.class) {
            a(productID, title, description, price, currency, null, null, userID, purchaseToken, CBIAPType.AMAZON);
        }
    }

    private static synchronized void a(a aVar, final String str, final CBIAPType cBIAPType) {
        synchronized (CBAnalytics.class) {
            ba baVar = new ba(String.format(Locale.US, "%s%s", new Object[]{"/post-install-event/", str}));
            baVar.a(str, (Object) aVar);
            baVar.a(g.a(g.a("status", com.chartboost.sdk.Libraries.a.a)));
            baVar.b(str);
            baVar.a(true);
            baVar.a(new c() {
                public void a(a aVar, ba baVar) {
                    CBLogging.a("CBPostInstallTracker", str + " success response!!");
                }

                public void a(a aVar, ba baVar, CBError cBError) {
                    if (str.equals("iap") && aVar != null && aVar.f("status") == 400 && cBIAPType == CBIAPType.GOOGLE_PLAY) {
                        CBLogging.a("CBPostInstallTracker", str + " 400 response from server!!");
                        bb a = bb.a(b.k());
                        h j = a.j();
                        ConcurrentHashMap i = a.i();
                        if (j != null && i != null) {
                            j.c((File) i.get(baVar));
                            i.remove(baVar);
                            return;
                        }
                        return;
                    }
                    CBLogging.a("CBPostInstallTracker", str + " failure response!!");
                }
            });
        }
    }
}
