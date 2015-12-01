package com.com2us.module.inapp.googleplay;

import android.text.TextUtils;
import com.com2us.peppermint.PeppermintConstant;
import com.facebook.internal.ServerProtocol;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONException;
import org.json.JSONObject;

public class Purchase {
    String mAddtionalInfo;
    String mDeveloperPayload;
    String mItemType;
    boolean mManaged;
    String mOrderId;
    String mOriginalJson;
    String mPackageName;
    int mPurchaseState;
    long mPurchaseTime;
    String mSignature;
    String mSku;
    String mToken;
    String mUid;

    public Purchase(String itemType, String jsonPurchaseInfo, String signature) throws JSONException {
        this.mItemType = itemType;
        this.mOriginalJson = jsonPurchaseInfo;
        JSONObject o = new JSONObject(this.mOriginalJson);
        this.mOrderId = o.optString("orderId");
        this.mPackageName = o.optString("packageName");
        this.mSku = o.optString("productId");
        this.mPurchaseTime = o.optLong("purchaseTime");
        this.mPurchaseState = o.optInt("purchaseState");
        this.mDeveloperPayload = o.optString("developerPayload");
        this.mToken = o.optString(ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN, o.optString("purchaseToken"));
        this.mSignature = signature;
        try {
            if (TextUtils.isEmpty(this.mDeveloperPayload)) {
                this.mManaged = false;
                this.mUid = a.d;
                this.mAddtionalInfo = i.a;
                return;
            }
            JSONObject payload = new JSONObject(this.mDeveloperPayload);
            this.mManaged = payload.optBoolean("isManaged", false);
            this.mUid = payload.optString(PeppermintConstant.JSON_KEY_UID);
            this.mAddtionalInfo = payload.optString("addtionalInfo");
        } catch (Exception e) {
            e.printStackTrace();
            this.mManaged = false;
            this.mUid = a.d;
            this.mAddtionalInfo = i.a;
        }
    }

    public String getItemType() {
        return this.mItemType;
    }

    public String getOrderId() {
        return this.mOrderId;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public String getSku() {
        return this.mSku;
    }

    public long getPurchaseTime() {
        return this.mPurchaseTime;
    }

    public int getPurchaseState() {
        return this.mPurchaseState;
    }

    public String getDeveloperPayload() {
        return this.mDeveloperPayload;
    }

    public String getToken() {
        return this.mToken;
    }

    public String getOriginalJson() {
        return this.mOriginalJson;
    }

    public String getSignature() {
        return this.mSignature;
    }

    public boolean getManaged() {
        return this.mManaged;
    }

    public String getUid() {
        return this.mUid;
    }

    public String getAddtionalInfo() {
        return this.mAddtionalInfo;
    }

    public String toString() {
        return "PurchaseInfo(type:" + this.mItemType + "):" + this.mOriginalJson;
    }
}
