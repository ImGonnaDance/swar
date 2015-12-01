package com.com2us.module.inapp.googleplay;

import com.com2us.peppermint.PeppermintConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class SkuDetails {
    String mDescription;
    String mItemType;
    String mJson;
    String mPrice;
    long mPrice_amount_micros;
    String mPrice_currency_code;
    String mSku;
    String mTitle;
    String mType;

    public SkuDetails(String jsonSkuDetails) throws JSONException {
        this(GooglePlayHelper.ITEM_TYPE_INAPP, jsonSkuDetails);
    }

    public SkuDetails(String itemType, String jsonSkuDetails) throws JSONException {
        this.mItemType = itemType;
        this.mJson = jsonSkuDetails;
        JSONObject o = new JSONObject(this.mJson);
        this.mSku = o.optString("productId");
        this.mType = o.optString(PeppermintConstant.JSON_KEY_TYPE);
        this.mPrice = o.optString("price");
        this.mPrice_amount_micros = o.optLong("price_amount_micros");
        this.mPrice_currency_code = o.optString("price_currency_code");
        this.mTitle = o.optString("title");
        this.mDescription = o.optString("description");
    }

    public String getSku() {
        return this.mSku;
    }

    public String getType() {
        return this.mType;
    }

    public String getPrice() {
        return this.mPrice;
    }

    public long getPrice_amount_micros() {
        return this.mPrice_amount_micros;
    }

    public String getPrice_currency_code() {
        return this.mPrice_currency_code;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String toString() {
        return "SkuDetails:" + this.mJson;
    }
}
