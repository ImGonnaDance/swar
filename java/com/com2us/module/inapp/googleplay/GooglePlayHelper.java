package com.com2us.module.inapp.googleplay;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.android.vending.billing.IInAppBillingService;
import com.android.vending.billing.IInAppBillingService.Stub;
import com.com2us.module.inapp.DefaultBilling;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONException;

public class GooglePlayHelper {
    public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;
    public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;
    public static final int BILLING_RESPONSE_RESULT_ERROR = 6;
    public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;
    public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;
    public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;
    public static final int BILLING_RESPONSE_RESULT_OK = 0;
    public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;
    public static final String GET_SKU_DETAILS_ITEM_LIST = "ITEM_ID_LIST";
    public static final String GET_SKU_DETAILS_ITEM_TYPE_LIST = "ITEM_TYPE_LIST";
    public static final int IABHELPER_BAD_RESPONSE = -1002;
    public static final int IABHELPER_ERROR_BASE = -1000;
    public static final int IABHELPER_INVALID_CONSUMPTION = -1010;
    public static final int IABHELPER_MISSING_TOKEN = -1007;
    public static final int IABHELPER_REMOTE_EXCEPTION = -1001;
    public static final int IABHELPER_SEND_INTENT_FAILED = -1004;
    public static final int IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE = -1009;
    public static final int IABHELPER_UNKNOWN_ERROR = -1008;
    public static final int IABHELPER_UNKNOWN_PURCHASE_RESPONSE = -1006;
    public static final int IABHELPER_USER_CANCELLED = -1005;
    public static final int IABHELPER_VERIFICATION_FAILED = -1003;
    public static final String INAPP_CONTINUATION_TOKEN = "INAPP_CONTINUATION_TOKEN";
    public static final String ITEM_TYPE_INAPP = "inapp";
    public static final String ITEM_TYPE_SUBS = "subs";
    public static final String RESPONSE_BUY_INTENT = "BUY_INTENT";
    public static final String RESPONSE_CODE = "RESPONSE_CODE";
    public static final String RESPONSE_GET_SKU_DETAILS_LIST = "DETAILS_LIST";
    public static final String RESPONSE_INAPP_ITEM_LIST = "INAPP_PURCHASE_ITEM_LIST";
    public static final String RESPONSE_INAPP_PURCHASE_DATA = "INAPP_PURCHASE_DATA";
    public static final String RESPONSE_INAPP_PURCHASE_DATA_LIST = "INAPP_PURCHASE_DATA_LIST";
    public static final String RESPONSE_INAPP_SIGNATURE = "INAPP_DATA_SIGNATURE";
    public static final String RESPONSE_INAPP_SIGNATURE_LIST = "INAPP_DATA_SIGNATURE_LIST";
    boolean mAsyncInProgress = false;
    String mAsyncOperation = i.a;
    Context mContext;
    boolean mDebugLog = false;
    String mDebugTag = "IabHelper";
    boolean mDisposed = false;
    OnIabPurchaseFinishedListener mPurchaseListener;
    String mPurchasingItemType;
    int mRequestCode;
    IInAppBillingService mService;
    ServiceConnection mServiceConn;
    boolean mSetupDone = false;
    boolean mSubscriptionsSupported = false;

    public interface QueryInventoryFinishedListener {
        void onQueryInventoryFinished(GooglePlayResult googlePlayResult, Inventory inventory);
    }

    public interface OnIabPurchaseFinishedListener {
        void onIabPurchaseFinished(GooglePlayResult googlePlayResult, Purchase purchase);
    }

    public interface OnConsumeFinishedListener {
        void onConsumeFinished(Purchase purchase, GooglePlayResult googlePlayResult);
    }

    public interface OnConsumeMultiFinishedListener {
        void onConsumeMultiFinished(List<Purchase> list, List<GooglePlayResult> list2);
    }

    public interface OnIabSetupFinishedListener {
        void onIabSetupFinished(GooglePlayResult googlePlayResult);
    }

    public GooglePlayHelper(Context ctx) {
        this.mContext = ctx.getApplicationContext();
        logDebug("IAB helper created.");
    }

    public void enableDebugLogging(boolean enable, String tag) {
        checkNotDisposed();
        this.mDebugLog = enable;
        this.mDebugTag = tag;
    }

    public void enableDebugLogging(boolean enable) {
        checkNotDisposed();
        this.mDebugLog = enable;
    }

    public void startSetup(final OnIabSetupFinishedListener listener) throws Exception {
        checkNotDisposed();
        if (this.mSetupDone) {
            throw new IllegalStateException("IAB helper is already set up.");
        }
        logDebug("Starting in-app billing setup.");
        this.mServiceConn = new ServiceConnection() {
            public void onServiceDisconnected(ComponentName name) {
                GooglePlayHelper.this.logDebug("Billing service disconnected.");
                GooglePlayHelper.this.mService = null;
            }

            public void onServiceConnected(ComponentName name, IBinder service) {
                if (!GooglePlayHelper.this.mDisposed) {
                    GooglePlayHelper.this.logDebug("Billing service connected.");
                    GooglePlayHelper.this.mService = Stub.asInterface(service);
                    String packageName = GooglePlayHelper.this.mContext.getPackageName();
                    try {
                        GooglePlayHelper.this.logDebug("Checking for in-app billing 3 support.");
                        int response = GooglePlayHelper.this.mService.isBillingSupported(GooglePlayHelper.BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE, packageName, GooglePlayHelper.ITEM_TYPE_INAPP);
                        if (response != 0) {
                            if (listener != null) {
                                listener.onIabSetupFinished(new GooglePlayResult(response, "Error checking for billing v3 support."));
                            }
                            GooglePlayHelper.this.mSubscriptionsSupported = false;
                            return;
                        }
                        GooglePlayHelper.this.logDebug("In-app billing version 3 supported for " + packageName);
                        response = GooglePlayHelper.this.mService.isBillingSupported(GooglePlayHelper.BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE, packageName, GooglePlayHelper.ITEM_TYPE_SUBS);
                        if (response == 0) {
                            GooglePlayHelper.this.logDebug("Subscriptions AVAILABLE.");
                            GooglePlayHelper.this.mSubscriptionsSupported = true;
                        } else {
                            GooglePlayHelper.this.logDebug("Subscriptions NOT AVAILABLE. Response: " + response);
                        }
                        GooglePlayHelper.this.mSetupDone = true;
                        if (listener != null) {
                            listener.onIabSetupFinished(new GooglePlayResult(GooglePlayHelper.BILLING_RESPONSE_RESULT_OK, "Setup successful."));
                        }
                    } catch (RemoteException e) {
                        if (listener != null) {
                            listener.onIabSetupFinished(new GooglePlayResult(GooglePlayHelper.IABHELPER_REMOTE_EXCEPTION, "RemoteException while setting up in-app billing."));
                        }
                        e.printStackTrace();
                    } catch (Exception e2) {
                        if (listener != null) {
                            listener.onIabSetupFinished(new GooglePlayResult(GooglePlayHelper.IABHELPER_UNKNOWN_ERROR, "UnknownException while setting up in-app billing."));
                        }
                        e2.printStackTrace();
                    }
                }
            }
        };
        Intent serviceIntent = new Intent("com.android.vending.billing.InAppBillingService.BIND");
        serviceIntent.setPackage("com.android.vending");
        if (!this.mContext.getPackageManager().queryIntentServices(serviceIntent, BILLING_RESPONSE_RESULT_OK).isEmpty()) {
            this.mContext.bindService(serviceIntent, this.mServiceConn, BILLING_RESPONSE_RESULT_USER_CANCELED);
        } else if (listener != null) {
            listener.onIabSetupFinished(new GooglePlayResult(BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE, "Billing service unavailable on device."));
        }
    }

    public void dispose() {
        logDebug("Disposing.");
        this.mSetupDone = false;
        if (this.mServiceConn != null) {
            logDebug("Unbinding from service.");
            if (this.mContext != null) {
                this.mContext.unbindService(this.mServiceConn);
            }
        }
        this.mDisposed = true;
        this.mContext = null;
        this.mServiceConn = null;
        this.mService = null;
        this.mPurchaseListener = null;
    }

    private void checkNotDisposed() {
        if (this.mDisposed) {
            throw new IllegalStateException("IabHelper was disposed of, so it cannot be used.");
        }
    }

    public boolean subscriptionsSupported() {
        checkNotDisposed();
        return this.mSubscriptionsSupported;
    }

    public void launchPurchaseFlow(Activity act, String sku, int requestCode, OnIabPurchaseFinishedListener listener) {
        launchPurchaseFlow(act, sku, requestCode, listener, i.a);
    }

    public void launchPurchaseFlow(Activity act, String sku, int requestCode, OnIabPurchaseFinishedListener listener, String extraData) {
        launchPurchaseFlow(act, sku, ITEM_TYPE_INAPP, requestCode, listener, extraData);
    }

    public void launchSubscriptionPurchaseFlow(Activity act, String sku, int requestCode, OnIabPurchaseFinishedListener listener) {
        launchSubscriptionPurchaseFlow(act, sku, requestCode, listener, i.a);
    }

    public void launchSubscriptionPurchaseFlow(Activity act, String sku, int requestCode, OnIabPurchaseFinishedListener listener, String extraData) {
        launchPurchaseFlow(act, sku, ITEM_TYPE_SUBS, requestCode, listener, extraData);
    }

    public void launchPurchaseFlow(Activity act, String sku, String itemType, int requestCode, OnIabPurchaseFinishedListener listener, String extraData) {
        checkNotDisposed();
        checkSetupDone("launchPurchaseFlow");
        flagStartAsync("launchPurchaseFlow");
        if (!itemType.equals(ITEM_TYPE_SUBS) || this.mSubscriptionsSupported) {
            GooglePlayResult result;
            try {
                logDebug("Constructing buy intent for " + sku + ", item type: " + itemType);
                Bundle buyIntentBundle = this.mService.getBuyIntent(BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE, this.mContext.getPackageName(), sku, itemType, extraData);
                int response = getResponseCodeFromBundle(buyIntentBundle);
                if (response != 0) {
                    logError("Unable to buy item, Error response: " + getResponseDesc(response));
                    flagEndAsync();
                    result = new GooglePlayResult(response, "Unable to buy item");
                    if (listener != null) {
                        listener.onIabPurchaseFinished(result, null);
                        return;
                    }
                    return;
                }
                PendingIntent pendingIntent = (PendingIntent) buyIntentBundle.getParcelable(RESPONSE_BUY_INTENT);
                logDebug("Launching buy intent for " + sku + ". Request code: " + requestCode);
                this.mRequestCode = requestCode;
                this.mPurchaseListener = listener;
                this.mPurchasingItemType = itemType;
                act.startIntentSenderForResult(pendingIntent.getIntentSender(), requestCode, new Intent(), Integer.valueOf(BILLING_RESPONSE_RESULT_OK).intValue(), Integer.valueOf(BILLING_RESPONSE_RESULT_OK).intValue(), Integer.valueOf(BILLING_RESPONSE_RESULT_OK).intValue());
                return;
            } catch (SendIntentException e) {
                logError("SendIntentException while launching purchase flow for sku " + sku);
                e.printStackTrace();
                flagEndAsync();
                result = new GooglePlayResult(IABHELPER_SEND_INTENT_FAILED, "Failed to send intent.");
                if (listener != null) {
                    listener.onIabPurchaseFinished(result, null);
                    return;
                }
                return;
            } catch (RemoteException e2) {
                logError("RemoteException while launching purchase flow for sku " + sku);
                e2.printStackTrace();
                flagEndAsync();
                result = new GooglePlayResult(IABHELPER_REMOTE_EXCEPTION, "Remote exception while starting purchase flow");
                if (listener != null) {
                    listener.onIabPurchaseFinished(result, null);
                    return;
                }
                return;
            }
        }
        GooglePlayResult r = new GooglePlayResult(IABHELPER_SUBSCRIPTIONS_NOT_AVAILABLE, "Subscriptions are not available.");
        flagEndAsync();
        if (listener != null) {
            listener.onIabPurchaseFinished(r, null);
        }
    }

    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode != this.mRequestCode) {
            logError("requestCode invalid in IAB activity result.");
            return false;
        }
        checkNotDisposed();
        checkSetupDone("handleActivityResult");
        flagEndAsync();
        GooglePlayResult result;
        if (data == null) {
            logError("Null data in IAB activity result.");
            result = new GooglePlayResult(IABHELPER_BAD_RESPONSE, "Null data in IAB result");
            if (this.mPurchaseListener != null) {
                this.mPurchaseListener.onIabPurchaseFinished(result, null);
            }
            return true;
        }
        int responseCode = getResponseCodeFromIntent(data);
        String purchaseData = data.getStringExtra(RESPONSE_INAPP_PURCHASE_DATA);
        String dataSignature = data.getStringExtra(RESPONSE_INAPP_SIGNATURE);
        logDebug("Purchase data: " + purchaseData);
        logDebug("Data signature: " + dataSignature);
        logDebug("Extras: " + data.getExtras());
        logDebug("Expected item type: " + this.mPurchasingItemType);
        if (resultCode == -1 && responseCode == 0) {
            logDebug("Successful resultcode from purchase activity.");
            if (purchaseData == null || dataSignature == null) {
                logError("BUG: either purchaseData or dataSignature is null.");
                logDebug("Extras: " + data.getExtras().toString());
                result = new GooglePlayResult(IABHELPER_UNKNOWN_ERROR, "IAB returned null purchaseData or dataSignature");
                if (this.mPurchaseListener != null) {
                    this.mPurchaseListener.onIabPurchaseFinished(result, null);
                }
                return true;
            }
            try {
                Purchase purchase = new Purchase(this.mPurchasingItemType, purchaseData, dataSignature);
                if (this.mPurchaseListener != null) {
                    this.mPurchaseListener.onIabPurchaseFinished(new GooglePlayResult(BILLING_RESPONSE_RESULT_OK, "Success"), purchase);
                }
            } catch (JSONException e) {
                logError("Failed to parse purchase data.");
                e.printStackTrace();
                result = new GooglePlayResult(IABHELPER_BAD_RESPONSE, "Failed to parse purchase data.");
                if (this.mPurchaseListener != null) {
                    this.mPurchaseListener.onIabPurchaseFinished(result, null);
                }
                return true;
            }
        } else if (resultCode == -1) {
            logDebug("Result code was OK but in-app billing response was not OK: " + getResponseDesc(responseCode));
            if (this.mPurchaseListener != null) {
                this.mPurchaseListener.onIabPurchaseFinished(new GooglePlayResult(responseCode, "Problem purchashing item."), null);
            }
        } else if (resultCode == 0) {
            logDebug("Purchase canceled - Response: " + getResponseDesc(responseCode));
            result = new GooglePlayResult(IABHELPER_USER_CANCELLED, "User canceled.");
            if (this.mPurchaseListener != null) {
                this.mPurchaseListener.onIabPurchaseFinished(result, null);
            }
        } else {
            logError("Purchase failed. Result code: " + Integer.toString(resultCode) + ". Response: " + getResponseDesc(responseCode));
            result = new GooglePlayResult(IABHELPER_UNKNOWN_PURCHASE_RESPONSE, "Unknown purchase response.");
            if (this.mPurchaseListener != null) {
                this.mPurchaseListener.onIabPurchaseFinished(result, null);
            }
        }
        return true;
    }

    public Inventory queryInventory(boolean querySkuDetails, List<String> moreSkus) throws GooglePlayException {
        return queryInventory(querySkuDetails, moreSkus, null);
    }

    public Inventory queryInventory(boolean querySkuDetails, List<String> moreItemSkus, List<String> list) throws GooglePlayException {
        checkNotDisposed();
        checkSetupDone("queryInventory");
        try {
            Inventory inv = new Inventory();
            int r = queryPurchases(inv, ITEM_TYPE_INAPP);
            if (r != 0) {
                throw new GooglePlayException(r, "Error refreshing inventory (querying owned items).");
            }
            if (querySkuDetails) {
                r = querySkuDetails(ITEM_TYPE_INAPP, inv, moreItemSkus);
                if (r != 0) {
                    throw new GooglePlayException(r, "Error refreshing inventory (querying prices of items).");
                }
            }
            if (this.mSubscriptionsSupported) {
                r = queryPurchases(inv, ITEM_TYPE_SUBS);
                if (r != 0) {
                    throw new GooglePlayException(r, "Error refreshing inventory (querying owned subscriptions).");
                } else if (querySkuDetails) {
                    r = querySkuDetails(ITEM_TYPE_SUBS, inv, moreItemSkus);
                    if (r != 0) {
                        throw new GooglePlayException(r, "Error refreshing inventory (querying prices of subscriptions).");
                    }
                }
            }
            return inv;
        } catch (RemoteException e) {
            throw new GooglePlayException(IABHELPER_REMOTE_EXCEPTION, "Remote exception while refreshing inventory.", e);
        } catch (JSONException e2) {
            throw new GooglePlayException(IABHELPER_BAD_RESPONSE, "Error parsing JSON response while refreshing inventory.", e2);
        } catch (Exception e3) {
            throw new GooglePlayException(IABHELPER_BAD_RESPONSE, "Error unknown response exception while refreshing inventory.", e3);
        }
    }

    public void queryInventoryAsync(boolean querySkuDetails, List<String> moreSkus, QueryInventoryFinishedListener listener) {
        final Handler handler = new Handler();
        checkNotDisposed();
        checkSetupDone("queryInventory");
        flagStartAsync("refresh inventory");
        final boolean z = querySkuDetails;
        final List<String> list = moreSkus;
        final QueryInventoryFinishedListener queryInventoryFinishedListener = listener;
        new Thread(new Runnable() {
            public void run() {
                GooglePlayResult result = new GooglePlayResult(GooglePlayHelper.BILLING_RESPONSE_RESULT_OK, "Inventory refresh successful.");
                Inventory inv = null;
                try {
                    inv = GooglePlayHelper.this.queryInventory(z, list);
                } catch (GooglePlayException ex) {
                    result = ex.getResult();
                }
                GooglePlayHelper.this.flagEndAsync();
                final GooglePlayResult result_f = result;
                final Inventory inv_f = inv;
                if (!GooglePlayHelper.this.mDisposed && queryInventoryFinishedListener != null) {
                    Handler handler = handler;
                    final QueryInventoryFinishedListener queryInventoryFinishedListener = queryInventoryFinishedListener;
                    handler.post(new Runnable() {
                        public void run() {
                            queryInventoryFinishedListener.onQueryInventoryFinished(result_f, inv_f);
                        }
                    });
                }
            }
        }).start();
    }

    public void queryInventoryAsync(QueryInventoryFinishedListener listener) {
        queryInventoryAsync(true, null, listener);
    }

    public void queryInventoryAsync(boolean querySkuDetails, QueryInventoryFinishedListener listener) {
        queryInventoryAsync(querySkuDetails, null, listener);
    }

    void consume(Purchase itemInfo) throws GooglePlayException {
        checkNotDisposed();
        checkSetupDone("consume");
        if (itemInfo.mItemType.equals(ITEM_TYPE_INAPP)) {
            try {
                String token = itemInfo.getToken();
                String sku = itemInfo.getSku();
                if (token == null || token.equals(i.a)) {
                    logError("Can't consume " + sku + ". No token.");
                    throw new GooglePlayException((int) IABHELPER_MISSING_TOKEN, "PurchaseInfo is missing token for sku: " + sku + " " + itemInfo);
                }
                logDebug("Consuming sku: " + sku + ", token: " + token);
                int response = this.mService.consumePurchase(BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE, this.mContext.getPackageName(), token);
                if (response == 0) {
                    logDebug("Successfully consumed sku: " + sku);
                    return;
                } else {
                    logDebug("Error consuming consuming sku " + sku + ". " + getResponseDesc(response));
                    throw new GooglePlayException(response, "Error consuming sku " + sku);
                }
            } catch (RemoteException e) {
                throw new GooglePlayException(IABHELPER_REMOTE_EXCEPTION, "Remote exception while consuming. PurchaseInfo: " + itemInfo, e);
            }
        }
        throw new GooglePlayException((int) IABHELPER_INVALID_CONSUMPTION, "Items of type '" + itemInfo.mItemType + "' can't be consumed.");
    }

    public void consumeAsync(Purchase purchase, OnConsumeFinishedListener listener) {
        checkNotDisposed();
        checkSetupDone("consume");
        List<Purchase> purchases = new ArrayList();
        purchases.add(purchase);
        consumeAsyncInternal(purchases, listener, null);
    }

    public void consumeAsync(List<Purchase> purchases, OnConsumeMultiFinishedListener listener) {
        checkNotDisposed();
        checkSetupDone("consume");
        consumeAsyncInternal(purchases, null, listener);
    }

    public static String getResponseDesc(int code) {
        String[] iab_msgs = "0:OK/1:User Canceled/2:Unknown/3:Billing Unavailable/4:Item unavailable/5:Developer Error/6:Error/7:Item Already Owned/8:Item not owned".split("/");
        String[] iabhelper_msgs = "0:OK/-1001:Remote exception during initialization/-1002:Bad response received/-1003:Purchase signature verification failed/-1004:Send intent failed/-1005:User cancelled/-1006:Unknown purchase response/-1007:Missing token/-1008:Unknown error/-1009:Subscriptions not available/-1010:Invalid consumption attempt".split("/");
        if (code <= IABHELPER_ERROR_BASE) {
            int index = -1000 - code;
            if (index < 0 || index >= iabhelper_msgs.length) {
                return String.valueOf(code) + ":Unknown IAB Helper Error";
            }
            return iabhelper_msgs[index];
        } else if (code < 0 || code >= iab_msgs.length) {
            return String.valueOf(code) + ":Unknown";
        } else {
            return iab_msgs[code];
        }
    }

    void checkSetupDone(String operation) {
        if (!this.mSetupDone) {
            logError("Illegal state for operation (" + operation + "): IAB helper is not set up.");
            throw new IllegalStateException("IAB helper is not set up. Can't perform operation: " + operation);
        }
    }

    int getResponseCodeFromBundle(Bundle b) {
        Object o = b.get(RESPONSE_CODE);
        if (o == null) {
            logDebug("Bundle with null response code, assuming OK (known issue)");
            return BILLING_RESPONSE_RESULT_OK;
        } else if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {
            if (o instanceof Long) {
                return (int) ((Long) o).longValue();
            }
            logError("Unexpected type for bundle response code.");
            logError(o.getClass().getName());
            throw new RuntimeException("Unexpected type for bundle response code: " + o.getClass().getName());
        }
    }

    int getResponseCodeFromIntent(Intent i) {
        Object o = i.getExtras().get(RESPONSE_CODE);
        if (o == null) {
            logError("Intent with no response code, assuming OK (known issue)");
            return BILLING_RESPONSE_RESULT_OK;
        } else if (o instanceof Integer) {
            return ((Integer) o).intValue();
        } else {
            if (o instanceof Long) {
                return (int) ((Long) o).longValue();
            }
            logError("Unexpected type for intent response code.");
            logError(o.getClass().getName());
            throw new RuntimeException("Unexpected type for intent response code: " + o.getClass().getName());
        }
    }

    void flagStartAsync(String operation) {
        if (this.mAsyncInProgress) {
            throw new IllegalStateException("Can't start async operation (" + operation + ") because another async operation(" + this.mAsyncOperation + ") is in progress.");
        }
        this.mAsyncOperation = operation;
        this.mAsyncInProgress = true;
        logDebug("Starting async operation: " + operation);
    }

    void flagEndAsync() {
        logDebug("Ending async operation: " + this.mAsyncOperation);
        this.mAsyncOperation = i.a;
        this.mAsyncInProgress = false;
    }

    int queryPurchases(Inventory inv, String itemType) throws JSONException, RemoteException, Exception {
        int i;
        logDebug("Querying owned items, item type: " + itemType);
        logDebug("Package name: " + this.mContext.getPackageName());
        String continueToken = null;
        do {
            logDebug("Calling getPurchases with continuation token: " + continueToken);
            Bundle ownedItems = this.mService.getPurchases(BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE, this.mContext.getPackageName(), itemType, continueToken);
            int response = getResponseCodeFromBundle(ownedItems);
            logDebug("Owned items response: " + String.valueOf(response));
            if (response != 0) {
                logDebug("getPurchases() failed: " + getResponseDesc(response));
                return response;
            } else if (ownedItems.containsKey(RESPONSE_INAPP_ITEM_LIST) && ownedItems.containsKey(RESPONSE_INAPP_PURCHASE_DATA_LIST) && ownedItems.containsKey(RESPONSE_INAPP_SIGNATURE_LIST)) {
                ArrayList<String> ownedSkus = ownedItems.getStringArrayList(RESPONSE_INAPP_ITEM_LIST);
                ArrayList<String> purchaseDataList = ownedItems.getStringArrayList(RESPONSE_INAPP_PURCHASE_DATA_LIST);
                ArrayList<String> signatureList = ownedItems.getStringArrayList(RESPONSE_INAPP_SIGNATURE_LIST);
                for (int i2 = BILLING_RESPONSE_RESULT_OK; i2 < purchaseDataList.size(); i2 += BILLING_RESPONSE_RESULT_USER_CANCELED) {
                    String purchaseData = (String) purchaseDataList.get(i2);
                    String signature = (String) signatureList.get(i2);
                    logDebug("Sku is owned: " + ((String) ownedSkus.get(i2)));
                    Purchase purchase = new Purchase(itemType, purchaseData, signature);
                    if (TextUtils.isEmpty(purchase.getToken())) {
                        logWarn("BUG: empty/null token!");
                        logDebug("Purchase data: " + purchaseData);
                    }
                    inv.addPurchase(purchase);
                }
                continueToken = ownedItems.getString(INAPP_CONTINUATION_TOKEN);
                logDebug("Continuation token: " + continueToken);
            } else {
                logError("Bundle returned from getPurchases() doesn't contain required fields.");
                return IABHELPER_BAD_RESPONSE;
            }
        } while (!TextUtils.isEmpty(continueToken));
        if (false) {
            i = IABHELPER_VERIFICATION_FAILED;
        } else {
            i = BILLING_RESPONSE_RESULT_OK;
        }
        return i;
    }

    int querySkuDetails(String itemType, Inventory inv, List<String> moreSkus) throws RemoteException, JSONException, Exception {
        Iterator it;
        logDebug("Querying SKU details.");
        ArrayList<String> skuList = new ArrayList();
        skuList.addAll(inv.getAllOwnedSkus(itemType));
        if (moreSkus != null) {
            for (String sku : moreSkus) {
                if (!skuList.contains(sku)) {
                    skuList.add(sku);
                }
            }
        }
        if (skuList.size() == 0) {
            logDebug("queryPrices: nothing to do because there are no SKUs.");
            return BILLING_RESPONSE_RESULT_OK;
        }
        while (skuList.size() > 0) {
            ArrayList<String> skuSubList = new ArrayList(skuList.subList(BILLING_RESPONSE_RESULT_OK, Math.min(19, skuList.size())));
            skuList.removeAll(skuSubList);
            Bundle querySkus = new Bundle();
            querySkus.putStringArrayList(GET_SKU_DETAILS_ITEM_LIST, skuSubList);
            Bundle skuDetails = this.mService.getSkuDetails(BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE, this.mContext.getPackageName(), itemType, querySkus);
            if (skuDetails.containsKey(RESPONSE_GET_SKU_DETAILS_LIST)) {
                it = skuDetails.getStringArrayList(RESPONSE_GET_SKU_DETAILS_LIST).iterator();
                while (it.hasNext()) {
                    SkuDetails d = new SkuDetails(itemType, (String) it.next());
                    logDebug("Got sku details: " + d);
                    inv.addSkuDetails(d);
                }
            } else {
                int response = getResponseCodeFromBundle(skuDetails);
                if (response != 0) {
                    logDebug("getSkuDetails() failed: " + getResponseDesc(response));
                    return response;
                }
                logError("getSkuDetails() returned a bundle with neither an error nor a detail list.");
                return IABHELPER_BAD_RESPONSE;
            }
        }
        return BILLING_RESPONSE_RESULT_OK;
    }

    void consumeAsyncInternal(List<Purchase> purchases, OnConsumeFinishedListener singleListener, OnConsumeMultiFinishedListener multiListener) {
        final Handler handler = new Handler();
        flagStartAsync("consume");
        final List<Purchase> list = purchases;
        final OnConsumeFinishedListener onConsumeFinishedListener = singleListener;
        final OnConsumeMultiFinishedListener onConsumeMultiFinishedListener = multiListener;
        new Thread(new Runnable() {
            public void run() {
                final List<GooglePlayResult> results = new ArrayList();
                for (Purchase purchase : list) {
                    try {
                        GooglePlayHelper.this.consume(purchase);
                        results.add(new GooglePlayResult(GooglePlayHelper.BILLING_RESPONSE_RESULT_OK, "Successful consume of sku " + purchase.getSku()));
                    } catch (GooglePlayException ex) {
                        results.add(ex.getResult());
                    }
                }
                GooglePlayHelper.this.flagEndAsync();
                if (!(GooglePlayHelper.this.mDisposed || onConsumeFinishedListener == null)) {
                    Handler handler = handler;
                    final OnConsumeFinishedListener onConsumeFinishedListener = onConsumeFinishedListener;
                    final List list = list;
                    handler.post(new Runnable() {
                        public void run() {
                            onConsumeFinishedListener.onConsumeFinished((Purchase) list.get(GooglePlayHelper.BILLING_RESPONSE_RESULT_OK), (GooglePlayResult) results.get(GooglePlayHelper.BILLING_RESPONSE_RESULT_OK));
                        }
                    });
                }
                if (!GooglePlayHelper.this.mDisposed && onConsumeMultiFinishedListener != null) {
                    handler = handler;
                    final OnConsumeMultiFinishedListener onConsumeMultiFinishedListener = onConsumeMultiFinishedListener;
                    list = list;
                    handler.post(new Runnable() {
                        public void run() {
                            onConsumeMultiFinishedListener.onConsumeMultiFinished(list, results);
                        }
                    });
                }
            }
        }).start();
    }

    void logDebug(String msg) {
        DefaultBilling.LogI("GooglePlayHelper - " + msg);
    }

    void logError(String msg) {
        DefaultBilling.LogI("GooglePlayHelper - In-app billing error: " + msg);
    }

    void logWarn(String msg) {
        DefaultBilling.LogI("GooglePlayHelper - In-app billing warning: " + msg);
    }
}
