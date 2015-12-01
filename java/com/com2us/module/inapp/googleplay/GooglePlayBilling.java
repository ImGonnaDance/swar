package com.com2us.module.inapp.googleplay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.widget.Toast;
import com.com2us.module.inapp.DefaultBilling;
import com.com2us.module.inapp.InAppCallback.ErrorStateValue;
import com.com2us.module.inapp.InAppCallback.ItemList;
import com.com2us.module.inapp.googleplay.GooglePlayBroadcastReceiver.GooglePlayBroadcastListener;
import com.com2us.module.inapp.googleplay.GooglePlayHelper.OnConsumeFinishedListener;
import com.com2us.module.inapp.googleplay.GooglePlayHelper.OnConsumeMultiFinishedListener;
import com.com2us.module.inapp.googleplay.GooglePlayHelper.OnIabPurchaseFinishedListener;
import com.com2us.module.inapp.googleplay.GooglePlayHelper.OnIabSetupFinishedListener;
import com.com2us.module.inapp.googleplay.GooglePlayHelper.QueryInventoryFinishedListener;
import com.com2us.peppermint.PeppermintConstant;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglePlayBilling extends DefaultBilling {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$inapp$googleplay$GooglePlayBilling$RequestPostType = null;
    static final String JSONTOKEN_ADDITIONALINFO = "addtionalInfo";
    static final String JSONTOKEN_ISMANAGED = "isManaged";
    static final String JSONTOKEN_PID = "pid";
    static final String JSONTOKEN_UID = "uid";
    public static final String Ver = "2.10.1";
    private static Thread logThread = null;
    private static ProgressDialog progressDialog = null;
    private static Thread sendThread = null;
    private GooglePlayResult InitializeErrorResult;
    IntentFilter broadcastFilter;
    JSONObject buyItemInfo;
    private boolean isInStore;
    private boolean isPause;
    private boolean isPurchaseUpdated;
    private boolean isSuccessInitialize;
    GooglePlayBroadcastReceiver mBroadcastReceiver;
    OnConsumeFinishedListener mConsumeFinishedListener;
    OnConsumeMultiFinishedListener mConsumeMultiFinishedListener;
    QueryInventoryFinishedListener mGotInventoryForConsumeListener;
    QueryInventoryFinishedListener mGotInventoryForItemListListener;
    QueryInventoryFinishedListener mGotInventoryForRestoreListener;
    GooglePlayHelper mHelper;
    OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    String[] pids;
    String restoreUid;
    private Intent savedPurchaseUpdatedIntent;

    private enum RequestPostType {
        RC_VERIFY,
        RC_LOG
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$inapp$googleplay$GooglePlayBilling$RequestPostType() {
        int[] iArr = $SWITCH_TABLE$com$com2us$module$inapp$googleplay$GooglePlayBilling$RequestPostType;
        if (iArr == null) {
            iArr = new int[RequestPostType.values().length];
            try {
                iArr[RequestPostType.RC_LOG.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[RequestPostType.RC_VERIFY.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$com$com2us$module$inapp$googleplay$GooglePlayBilling$RequestPostType = iArr;
        }
        return iArr;
    }

    public GooglePlayBilling(Activity _activity) {
        super(_activity);
        this.mHelper = null;
        this.mBroadcastReceiver = null;
        this.broadcastFilter = null;
        this.pids = null;
        this.restoreUid = i.a;
        this.buyItemInfo = null;
        this.isSuccessInitialize = false;
        this.InitializeErrorResult = null;
        this.isInStore = false;
        this.isPause = false;
        this.isPurchaseUpdated = false;
        this.savedPurchaseUpdatedIntent = null;
        this.mGotInventoryForItemListListener = new QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(GooglePlayResult result, Inventory inventory) {
                DefaultBilling.LogI("Query inventory for Itemlist finished.");
                if (result.isFailure()) {
                    DefaultBilling.LogI("Inventory Error : Failed to query inventory: " + result);
                    GooglePlayBilling.this.ProgressDialogDismiss();
                    return;
                }
                DefaultBilling.LogI("Query inventory was successful.");
                if (GooglePlayBilling.this.pids != null) {
                    try {
                        ItemList[] itemList = new ItemList[GooglePlayBilling.this.pids.length];
                        for (int i = 0; i < GooglePlayBilling.this.pids.length; i++) {
                            if (inventory.hasDetails(GooglePlayBilling.this.pids[i])) {
                                SkuDetails skuDetails = inventory.getSkuDetails(GooglePlayBilling.this.pids[i]);
                                DefaultBilling.LogI("skuDetails.getSku() : " + skuDetails.getSku());
                                DefaultBilling.LogI("skuDetails.getPrice() : " + skuDetails.getPrice());
                                DefaultBilling.LogI("skuDetails.getPrice_amount_micros() : " + skuDetails.getPrice_amount_micros());
                                DefaultBilling.LogI("skuDetails.getPrice_currency_code() : " + skuDetails.getPrice_currency_code());
                                DefaultBilling.LogI("skuDetails.getTitle() : " + skuDetails.getTitle());
                                DefaultBilling.LogI("skuDetails.getDescription() : " + skuDetails.getDescription());
                                DefaultBilling.LogI("skuDetails.getType() : " + skuDetails.getType());
                                itemList[i] = new ItemList();
                                itemList[i].productID = skuDetails.getSku();
                                itemList[i].formattedString = skuDetails.getPrice();
                                itemList[i].localizedTitle = skuDetails.getTitle();
                                itemList[i].localizedDescription = skuDetails.getDescription();
                                itemList[i].currencyCode = skuDetails.getPrice_currency_code();
                                itemList[i].amountMicros = skuDetails.getPrice_amount_micros();
                            } else {
                                itemList[i] = new ItemList();
                                itemList[i].productID = GooglePlayBilling.this.pids[i];
                                itemList[i].formattedString = i.a;
                                itemList[i].localizedTitle = i.a;
                                itemList[i].localizedDescription = i.a;
                                itemList[i].currencyCode = i.a;
                                itemList[i].amountMicros = 0;
                            }
                        }
                        GooglePlayBilling.this.resultPostInApp(1, i.a, 0, i.a, i.a, itemList);
                    } catch (Exception e) {
                        DefaultBilling.LogI("itemListData Exception");
                        e.printStackTrace();
                    } finally {
                        GooglePlayBilling.this.ProgressDialogDismiss();
                    }
                } else {
                    DefaultBilling.LogI("itemListData is nothing");
                    GooglePlayBilling.this.ProgressDialogDismiss();
                }
            }
        };
        this.mGotInventoryForRestoreListener = new QueryInventoryFinishedListener() {
            /* JADX WARNING: inconsistent code. */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onQueryInventoryFinished(com.com2us.module.inapp.googleplay.GooglePlayResult r21, com.com2us.module.inapp.googleplay.Inventory r22) {
                /*
                r20 = this;
                r2 = "Query inventory for Restore finished.";
                com.com2us.module.inapp.DefaultBilling.LogI(r2);
                r2 = r21.isFailure();
                if (r2 == 0) goto L_0x0051;
            L_0x000b:
                r2 = new java.lang.StringBuilder;
                r3 = "Inventory Error : Failed to query inventory: ";
                r2.<init>(r3);
                r0 = r21;
                r2 = r2.append(r0);
                r2 = r2.toString();
                com.com2us.module.inapp.DefaultBilling.LogI(r2);
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r3 = 0;
                r2.useRestoring = r3;
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r2.ProgressDialogDismiss();
                r8 = new com.com2us.module.inapp.InAppCallback$ErrorStateValue;
                r2 = "googleplay";
                r3 = r21.getResponse();
                r3 = java.lang.String.valueOf(r3);
                r4 = r21.toString();
                r8.<init>(r2, r3, r4);
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r3 = 3;
                r4 = "";
                r5 = 0;
                r6 = "";
                r7 = "";
                r2.resultPostInApp(r3, r4, r5, r6, r7, r8);
            L_0x0050:
                return;
            L_0x0051:
                r2 = "Query inventory was successful.";
                com.com2us.module.inapp.DefaultBilling.LogI(r2);
                r19 = r22.getAllPurchases();	 Catch:{ Exception -> 0x0141 }
                if (r19 == 0) goto L_0x0062;
            L_0x005c:
                r2 = r19.isEmpty();	 Catch:{ Exception -> 0x0141 }
                if (r2 == 0) goto L_0x0099;
            L_0x0062:
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r3 = 0;
                r2.useRestoring = r3;	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r2.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r9 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r10 = 5;
                r11 = "";
                r12 = 0;
                r13 = "";
                r14 = "";
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r3 = 0;
                r15 = r2.makeSuccessStateValue(r3);	 Catch:{ Exception -> 0x0141 }
                r9.resultPostInApp(r10, r11, r12, r13, r14, r15);	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r3 = 0;
                r2.useRestoring = r3;
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r2.ProgressDialogDismiss();
                goto L_0x0050;
            L_0x0099:
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r2 = r2.autoVerify;	 Catch:{ Exception -> 0x0141 }
                if (r2 == 0) goto L_0x0101;
            L_0x00a3:
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.sendThread;	 Catch:{ Exception -> 0x0141 }
                if (r2 == 0) goto L_0x00d8;
            L_0x00a9:
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.sendThread;	 Catch:{ Exception -> 0x0141 }
                r2 = r2.isAlive();	 Catch:{ Exception -> 0x0141 }
                if (r2 == 0) goto L_0x00d8;
            L_0x00b3:
                r2 = "processPurchasedData - sendThread is Alive, return";
                com.com2us.module.inapp.DefaultBilling.LogI(r2);	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r3 = 0;
                r2.useRestoring = r3;	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r2.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r3 = 0;
                r2.useRestoring = r3;
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r2.ProgressDialogDismiss();
                goto L_0x0050;
            L_0x00d8:
                r2 = new java.lang.Thread;	 Catch:{ Exception -> 0x0141 }
                r3 = new com.com2us.module.inapp.googleplay.GooglePlayBilling$2$1;	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r1 = r19;
                r3.<init>(r1);	 Catch:{ Exception -> 0x0141 }
                r2.<init>(r3);	 Catch:{ Exception -> 0x0141 }
                com.com2us.module.inapp.googleplay.GooglePlayBilling.sendThread = r2;	 Catch:{ Exception -> 0x0141 }
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.sendThread;	 Catch:{ Exception -> 0x0141 }
                r2.start();	 Catch:{ Exception -> 0x0141 }
            L_0x00f0:
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r3 = 0;
                r2.useRestoring = r3;
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r2.ProgressDialogDismiss();
                goto L_0x0050;
            L_0x0101:
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r3 = 0;
                r2.useRestoring = r3;	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0141 }
                r2.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0141 }
                r2 = "Restore successful.";
                com.com2us.module.inapp.DefaultBilling.LogI(r2);	 Catch:{ Exception -> 0x0141 }
                r16 = new java.util.ArrayList;	 Catch:{ Exception -> 0x0141 }
                r16.<init>();	 Catch:{ Exception -> 0x0141 }
                r2 = r19.iterator();	 Catch:{ Exception -> 0x0141 }
            L_0x011e:
                r3 = r2.hasNext();	 Catch:{ Exception -> 0x0141 }
                if (r3 != 0) goto L_0x0156;
            L_0x0124:
                r2 = r16.isEmpty();	 Catch:{ Exception -> 0x0141 }
                if (r2 != 0) goto L_0x00f0;
            L_0x012a:
                r2 = r16.size();	 Catch:{ Exception -> 0x0141 }
                if (r2 <= 0) goto L_0x00f0;
            L_0x0130:
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.activity;	 Catch:{ Exception -> 0x0141 }
                r3 = new com.com2us.module.inapp.googleplay.GooglePlayBilling$2$2;	 Catch:{ Exception -> 0x0141 }
                r0 = r20;
                r1 = r16;
                r3.<init>(r1);	 Catch:{ Exception -> 0x0141 }
                r2.runOnUiThread(r3);	 Catch:{ Exception -> 0x0141 }
                goto L_0x00f0;
            L_0x0141:
                r17 = move-exception;
                r17.printStackTrace();	 Catch:{ all -> 0x018c }
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r3 = 0;
                r2.useRestoring = r3;
                r0 = r20;
                r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r2.ProgressDialogDismiss();
                goto L_0x0050;
            L_0x0156:
                r18 = r2.next();	 Catch:{ Exception -> 0x0141 }
                r18 = (com.com2us.module.inapp.googleplay.Purchase) r18;	 Catch:{ Exception -> 0x0141 }
                r3 = r18.getManaged();	 Catch:{ Exception -> 0x0187 }
                if (r3 == 0) goto L_0x019d;
            L_0x0162:
                r3 = "Restore item is managed.";
                com.com2us.module.inapp.DefaultBilling.LogI(r3);	 Catch:{ Exception -> 0x0187 }
                r0 = r20;
                r9 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0187 }
                r10 = 5;
                r11 = r18.getSku();	 Catch:{ Exception -> 0x0187 }
                r12 = 0;
                r13 = r18.getUid();	 Catch:{ Exception -> 0x0187 }
                r14 = r18.getAddtionalInfo();	 Catch:{ Exception -> 0x0187 }
                r0 = r20;
                r3 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0187 }
                r0 = r18;
                r15 = r3.makeSuccessStateValue(r0);	 Catch:{ Exception -> 0x0187 }
                r9.resultPostInApp(r10, r11, r12, r13, r14, r15);	 Catch:{ Exception -> 0x0187 }
                goto L_0x011e;
            L_0x0187:
                r17 = move-exception;
                r17.printStackTrace();	 Catch:{ Exception -> 0x0141 }
                goto L_0x011e;
            L_0x018c:
                r2 = move-exception;
                r0 = r20;
                r3 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r4 = 0;
                r3.useRestoring = r4;
                r0 = r20;
                r3 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                r3.ProgressDialogDismiss();
                throw r2;
            L_0x019d:
                r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0187 }
                r4 = "Restore item is unmanaged. Starting ";
                r3.<init>(r4);	 Catch:{ Exception -> 0x0187 }
                r4 = r18.getSku();	 Catch:{ Exception -> 0x0187 }
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x0187 }
                r4 = " consumption.";
                r3 = r3.append(r4);	 Catch:{ Exception -> 0x0187 }
                r3 = r3.toString();	 Catch:{ Exception -> 0x0187 }
                com.com2us.module.inapp.DefaultBilling.LogI(r3);	 Catch:{ Exception -> 0x0187 }
                r0 = r16;
                r1 = r18;
                r0.add(r1);	 Catch:{ Exception -> 0x0187 }
                goto L_0x011e;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.inapp.googleplay.GooglePlayBilling.2.onQueryInventoryFinished(com.com2us.module.inapp.googleplay.GooglePlayResult, com.com2us.module.inapp.googleplay.Inventory):void");
            }
        };
        this.mGotInventoryForConsumeListener = new QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(GooglePlayResult result, final Inventory inventory) {
                DefaultBilling.LogI("Query inventory for Consume finished.");
                final String sku = GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_PID);
                String uid = GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_UID);
                String addtionalInfo = GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_ADDITIONALINFO);
                if (result.isFailure()) {
                    GooglePlayBilling.this.ProgressDialogDismiss();
                    GooglePlayBilling.this.resultPostInApp(3, sku, 1, uid, addtionalInfo, new ErrorStateValue("googleplay", "7", "Unable to buy item (response: 7:Item Already Owned)"));
                    return;
                }
                GooglePlayBilling.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        GooglePlayBilling.this.mHelper.consumeAsync(inventory.getPurchase(sku), GooglePlayBilling.this.mConsumeFinishedListener);
                    }
                });
            }
        };
        this.mPurchaseFinishedListener = new OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(GooglePlayResult result, Purchase purchase) {
                String addtionalInfo;
                DefaultBilling.LogI("Purchase finished: " + result + ", purchase: " + purchase);
                String sku = purchase != null ? purchase.getSku() : GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_PID);
                boolean isManaged = purchase != null ? purchase.getManaged() : GooglePlayBilling.this.buyItemInfo.optBoolean(GooglePlayBilling.JSONTOKEN_ISMANAGED, true);
                String uid = purchase != null ? purchase.getUid() : GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_UID);
                if (purchase != null) {
                    addtionalInfo = purchase.getAddtionalInfo();
                } else {
                    addtionalInfo = GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_ADDITIONALINFO);
                }
                if (result.isFailure()) {
                    DefaultBilling.LogI("Error purchasing: " + result);
                    if (result.getResponse() != 7 || isManaged || TextUtils.isEmpty(sku)) {
                        GooglePlayBilling.this.ProgressDialogDismiss();
                        if (result.getResponse() == GooglePlayHelper.IABHELPER_USER_CANCELLED) {
                            GooglePlayBilling.this.resultPostInApp(4, sku, isManaged ? 0 : 1, uid, addtionalInfo, "User canceled");
                            return;
                        }
                        GooglePlayBilling.this.resultPostInApp(3, sku, isManaged ? 0 : 1, uid, addtionalInfo, new ErrorStateValue("googleplay", String.valueOf(result.getResponse()), result.getMessage()));
                        return;
                    }
                    DefaultBilling.LogI("Purchase is unmanaged. Starting " + sku + " consumption.");
                    try {
                        DefaultBilling.LogI("Found previous progress.");
                        GooglePlayBilling.this.showPreviousProgressInfoDialog(GooglePlayBilling.activity, new Runnable() {
                            public void run() {
                                GooglePlayBilling.this.mHelper.queryInventoryAsync(GooglePlayBilling.this.mGotInventoryForConsumeListener);
                            }
                        });
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        GooglePlayBilling.this.ProgressDialogDismiss();
                        GooglePlayBilling.this.resultPostInApp(3, sku, 1, uid, addtionalInfo, new ErrorStateValue("googleplay", String.valueOf(result.getResponse()), result.getMessage()));
                        return;
                    }
                }
                DefaultBilling.LogI("Purchase successful.");
                try {
                    if (purchase.getManaged()) {
                        DefaultBilling.LogI("Purchase is managed.");
                        BillingDatabase db = new BillingDatabase(GooglePlayBilling.activity.getApplicationContext());
                        db.updatePurchase(purchase.getOriginalJson(), purchase.getSignature(), null);
                        db.close();
                        GooglePlayBilling.this.processPurchasedData();
                        return;
                    }
                    DefaultBilling.LogI("Purchase is unmanaged. Starting " + purchase.getSku() + " consumption.");
                    GooglePlayBilling.this.mHelper.consumeAsync(purchase, GooglePlayBilling.this.mConsumeFinishedListener);
                } catch (Exception e2) {
                    int i;
                    e2.printStackTrace();
                    GooglePlayBilling.this.ProgressDialogDismiss();
                    ErrorStateValue errorStateValue = new ErrorStateValue("googleplay", String.valueOf(result.getResponse()), result.getMessage());
                    GooglePlayBilling googlePlayBilling = GooglePlayBilling.this;
                    if (isManaged) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    googlePlayBilling.resultPostInApp(3, sku, i, uid, addtionalInfo, errorStateValue);
                }
            }
        };
        this.mConsumeFinishedListener = new OnConsumeFinishedListener() {
            public void onConsumeFinished(Purchase purchase, GooglePlayResult result) {
                String addtionalInfo;
                DefaultBilling.LogI("Consumption finished. Purchase: " + purchase + ", result: " + result);
                String sku = purchase != null ? purchase.getSku() : GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_PID);
                String uid = purchase != null ? purchase.getUid() : GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_UID);
                if (purchase != null) {
                    addtionalInfo = purchase.getAddtionalInfo();
                } else {
                    addtionalInfo = GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_ADDITIONALINFO);
                }
                if (result.isSuccess()) {
                    DefaultBilling.LogI("Consumption successful. Provisioning.");
                    BillingDatabase db = new BillingDatabase(GooglePlayBilling.activity.getApplicationContext());
                    db.updatePurchase(purchase.getOriginalJson(), purchase.getSignature(), null);
                    db.close();
                    GooglePlayBilling.this.processPurchasedData();
                    DefaultBilling.LogI("End consumption flow.");
                    return;
                }
                DefaultBilling.LogI("Error while consuming: " + result);
                GooglePlayBilling.this.ProgressDialogDismiss();
                GooglePlayBilling.this.resultPostInApp(3, sku, 1, uid, addtionalInfo, new ErrorStateValue("googleplay", String.valueOf(result.getResponse()), result.getMessage()));
            }
        };
        this.mConsumeMultiFinishedListener = new OnConsumeMultiFinishedListener() {
            public void onConsumeMultiFinished(List<Purchase> purchases, List<GooglePlayResult> results) {
                DefaultBilling.LogI("Multi Consumption finished. Purchases: " + purchases + ", results: " + results);
                int i = 0;
                while (i < purchases.size()) {
                    String addtionalInfo;
                    Purchase purchase = (Purchase) purchases.get(i);
                    GooglePlayResult result = (GooglePlayResult) results.get(i);
                    String sku = purchase != null ? purchase.getSku() : GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_PID);
                    String uid = purchase != null ? purchase.getUid() : GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_UID);
                    if (purchase != null) {
                        addtionalInfo = purchase.getAddtionalInfo();
                    } else {
                        addtionalInfo = GooglePlayBilling.this.buyItemInfo.optString(GooglePlayBilling.JSONTOKEN_ADDITIONALINFO);
                    }
                    if (result.isSuccess()) {
                        DefaultBilling.LogI("Consumption successful. Provisioning.");
                        BillingDatabase db = new BillingDatabase(GooglePlayBilling.activity.getApplicationContext());
                        db.updatePurchase(purchase.getOriginalJson(), purchase.getSignature(), null);
                        db.close();
                        i++;
                    } else {
                        DefaultBilling.LogI("Error while consuming: " + result);
                        GooglePlayBilling.this.ProgressDialogDismiss();
                        GooglePlayBilling.this.resultPostInApp(3, sku, 1, uid, addtionalInfo, new ErrorStateValue("googleplay", String.valueOf(result.getResponse()), result.getMessage()));
                        return;
                    }
                }
                GooglePlayBilling.this.processPurchasedData();
                DefaultBilling.LogI("End consumption flow.");
            }
        };
        this.VERSION = Ver;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                GooglePlayBilling.this.mHelper = new GooglePlayHelper(GooglePlayBilling.activity);
            }
        });
    }

    protected int iapInitialize(String[] _pids, String _appid, boolean _autoVerify, int cbRef) {
        int i = 0;
        DefaultBilling.LogI("initialize");
        appid = _appid;
        this.autoVerify = _autoVerify;
        CallBackRef = cbRef;
        this.pids = _pids;
        if (this.pids == null || this.pids.length <= 0) {
            DefaultBilling.LogI("pids is nothing.");
            return 0;
        }
        int length = _pids.length;
        while (i < length) {
            DefaultBilling.LogI("pid : " + _pids[i]);
            i++;
        }
        DefaultBilling.LogI("Starting setup.");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    GooglePlayBilling.this.mBroadcastReceiver = new GooglePlayBroadcastReceiver(new GooglePlayBroadcastListener() {
                        public void receivedBroadcast(Context context, Intent intent) {
                            DefaultBilling.LogI("receivedBroadcast : " + intent.toString());
                            GooglePlayBilling.this.isPurchaseUpdated = true;
                            GooglePlayBilling.this.savedPurchaseUpdatedIntent = intent;
                            if (!GooglePlayBilling.this.isPause) {
                                DefaultBilling.LogI("savedPurchaseUpdatedIntent : " + GooglePlayBilling.this.savedPurchaseUpdatedIntent.toString());
                                GooglePlayBilling.this.resultPostInApp(8, i.a, 0, i.a, i.a, null);
                                GooglePlayBilling.this.isPurchaseUpdated = false;
                                GooglePlayBilling.this.savedPurchaseUpdatedIntent = null;
                            }
                        }
                    });
                    GooglePlayBilling.this.broadcastFilter = new IntentFilter("com.android.vending.billing.PURCHASES_UPDATED");
                    GooglePlayBilling.activity.registerReceiver(GooglePlayBilling.this.mBroadcastReceiver, GooglePlayBilling.this.broadcastFilter);
                    GooglePlayBilling.this.mHelper.startSetup(new OnIabSetupFinishedListener() {
                        public void onIabSetupFinished(GooglePlayResult result) {
                            int i = 0;
                            DefaultBilling.LogI("Setup finished.");
                            if (result.isSuccess()) {
                                if (!GooglePlayBilling.this.isSuccessInitialize) {
                                    ArrayList<String> skuList = new ArrayList();
                                    if (GooglePlayBilling.this.pids != null) {
                                        String[] strArr = GooglePlayBilling.this.pids;
                                        int length = strArr.length;
                                        while (i < length) {
                                            skuList.add(strArr[i]);
                                            i++;
                                        }
                                        GooglePlayBilling.this.mHelper.queryInventoryAsync(true, skuList, GooglePlayBilling.this.mGotInventoryForItemListListener);
                                    } else {
                                        GooglePlayBilling.this.mHelper.queryInventoryAsync(GooglePlayBilling.this.mGotInventoryForItemListListener);
                                    }
                                }
                                GooglePlayBilling.this.isSuccessInitialize = true;
                                DefaultBilling.LogI("Setup successful. Querying inventory.");
                                return;
                            }
                            DefaultBilling.LogI("Setting Error : Problem setting up in-app billing: " + result);
                            GooglePlayBilling.this.InitializeErrorResult = result;
                            GooglePlayBilling.this.isSuccessInitialize = false;
                            GooglePlayBilling.this.resultPostInApp(3, i.a, 0, i.a, i.a, new ErrorStateValue("googleplay", String.valueOf(GooglePlayBilling.this.InitializeErrorResult == null ? -1 : GooglePlayBilling.this.InitializeErrorResult.getResponse()), GooglePlayBilling.this.InitializeErrorResult == null ? "Start Setup Error." : GooglePlayBilling.this.InitializeErrorResult.toString()));
                        }
                    });
                } catch (Exception e) {
                    DefaultBilling.LogI("Setting Error : " + e.toString());
                    GooglePlayBilling.this.InitializeErrorResult = new GooglePlayResult(-1994, "Billing service unavailable on device.");
                    GooglePlayBilling.this.isSuccessInitialize = false;
                    GooglePlayBilling.this.resultPostInApp(3, i.a, 0, i.a, i.a, new ErrorStateValue("googleplay", String.valueOf(GooglePlayBilling.this.InitializeErrorResult == null ? -1 : GooglePlayBilling.this.InitializeErrorResult.getResponse()), GooglePlayBilling.this.InitializeErrorResult == null ? "Start Setup Error." : GooglePlayBilling.this.InitializeErrorResult.toString()));
                }
            }
        });
        return 1;
    }

    protected void iapUninitialize() {
        DefaultBilling.LogI("uninitialize");
        this.isInStore = false;
        this.isSuccessInitialize = false;
        CallBackRef = 0;
        appid = i.a;
        if (this.mBroadcastReceiver != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        GooglePlayBilling.activity.unregisterReceiver(GooglePlayBilling.this.mBroadcastReceiver);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        DefaultBilling.LogI("Destroying helper.");
        if (this.mHelper != null) {
            try {
                this.mHelper.dispose();
            } catch (Exception e) {
                DefaultBilling.LogI("GooglePlayHelper dipose exception : " + e.toString());
            }
        }
        this.mHelper = null;
    }

    protected void iapStoreStart() {
        DefaultBilling.LogI("StoreStart");
        this.isInStore = true;
        BillingDatabase db = new BillingDatabase(activity.getApplicationContext());
        String[][] purchasedData = db.getPurchaseData();
        String[][] logData = db.getLogData();
        db.close();
        if (purchasedData != null && purchasedData.length > 0) {
            DefaultBilling.LogI("StoreStart - Found previous progress.");
            showPreviousProgressInfoDialog(activity, new Runnable() {
                public void run() {
                    GooglePlayBilling.this.processPurchasedData();
                }
            });
        } else if (logData != null && logData.length > 0) {
            sendLog();
        }
    }

    protected void iapStoreEnd() {
        DefaultBilling.LogI("StoreEnd");
        this.isInStore = false;
        BillingDatabase db = new BillingDatabase(activity.getApplicationContext());
        String[][] logData = db.getLogData();
        db.close();
        if (logData != null && logData.length > 0) {
            sendLog();
        }
    }

    protected void iapRestoreItem(final String _uid) {
        DefaultBilling.LogI("RestoreItem");
        if (!checkNetworkState() || !this.isInStore) {
            return;
        }
        if (!this.isSuccessInitialize) {
            int i;
            String str = "googleplay";
            if (this.InitializeErrorResult == null) {
                i = -1;
            } else {
                i = this.InitializeErrorResult.getResponse();
            }
            resultPostInApp(3, i.a, 0, _uid, i.a, new ErrorStateValue(str, String.valueOf(i), this.InitializeErrorResult == null ? "Start Setup Error." : this.InitializeErrorResult.toString()));
        } else if (this.useRestoring) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(GooglePlayBilling.activity, "Now being restored...", 0).show();
                }
            });
        } else {
            this.useRestoring = true;
            this.restoreUid = _uid;
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        GooglePlayBilling.this.mHelper.queryInventoryAsync(GooglePlayBilling.this.mGotInventoryForRestoreListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                        GooglePlayBilling.this.resultPostInApp(3, i.a, 0, _uid, i.a, new ErrorStateValue("c2s", "11", "Wrong request data."));
                    }
                }
            });
        }
    }

    protected void iapBuyItem(String _pid, int _quantity, String _uid, String _addtionalInfo) {
        DefaultBilling.LogI("BuyItem");
        if (this.isSuccessInitialize) {
            BillingDatabase db = new BillingDatabase(activity.getApplicationContext());
            String[][] purchasedData = db.getPurchaseData();
            db.close();
            if (purchasedData == null || purchasedData.length <= 0) {
                ProgressDialogShow();
                final JSONObject payload = new JSONObject();
                try {
                    Object obj;
                    payload.put(JSONTOKEN_ISMANAGED, _quantity == 0);
                    String str = JSONTOKEN_UID;
                    if (TextUtils.isEmpty(_uid)) {
                        obj = a.d;
                    } else {
                        String str2 = _uid;
                    }
                    payload.put(str, obj);
                    payload.put(JSONTOKEN_ADDITIONALINFO, _addtionalInfo);
                    this.buyItemInfo = new JSONObject();
                    this.buyItemInfo = payload;
                    this.buyItemInfo.put(JSONTOKEN_PID, _pid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                DefaultBilling.LogI("payload : " + payload.toString());
                DefaultBilling.LogI("Launching purchase flow for " + _pid);
                final String str3 = _pid;
                final int i = _quantity;
                final String str4 = _uid;
                final String str5 = _addtionalInfo;
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        try {
                            int requestCode = new SecureRandom().nextInt();
                            if (requestCode < 0) {
                                requestCode = -requestCode;
                            }
                            GooglePlayBilling.this.mHelper.launchPurchaseFlow(GooglePlayBilling.activity, str3, requestCode, GooglePlayBilling.this.mPurchaseFinishedListener, payload.toString());
                        } catch (Exception e) {
                            GooglePlayBilling.this.resultPostInApp(3, str3, i, str4, str5, new ErrorStateValue("c2s", "11", "Wrong request data."));
                            e.printStackTrace();
                        }
                    }
                });
                return;
            }
            DefaultBilling.LogI("Found previous progress.");
            showPreviousProgressInfoDialog(activity, new Runnable() {
                public void run() {
                    GooglePlayBilling.this.processPurchasedData();
                }
            });
            return;
        }
        int i2;
        str = "googleplay";
        if (this.InitializeErrorResult == null) {
            i2 = -1;
        } else {
            i2 = this.InitializeErrorResult.getResponse();
        }
        resultPostInApp(3, _pid, _quantity, _uid, _addtionalInfo, new ErrorStateValue(str, String.valueOf(i2), this.InitializeErrorResult == null ? "Start Setup Error." : this.InitializeErrorResult.toString()));
    }

    protected void iapBuyFinish() {
        DefaultBilling.LogI("BuyFinish, Receive CB");
        BillingDatabase db = new BillingDatabase(activity.getApplicationContext());
        String[][] purchasedData = db.getPurchaseData();
        if (purchasedData == null || purchasedData.length <= 0) {
            DefaultBilling.LogI("purchasedData is nothing");
            return;
        }
        int i = 0;
        while (i < purchasedData.length) {
            try {
                if (a.d.equals(purchasedData[i][3])) {
                    Purchase purchase = new Purchase(GooglePlayHelper.ITEM_TYPE_INAPP, purchasedData[i][1], purchasedData[i][2]);
                    if (purchase.getPurchaseState() == 0) {
                        db.updateLogData(strPostDataBuilder(RequestPostType.RC_LOG, purchase.getUid(), purchase.getOriginalJson(), purchase.getSignature(), purchase.getAddtionalInfo()));
                        db.deletePurchase(purchase.getOriginalJson());
                        break;
                    }
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            } finally {
                db.close();
            }
        }
        if (i == purchasedData.length) {
            sendLog();
        } else {
            sendSuccess();
        }
        db.close();
    }

    protected void pause() {
        DefaultBilling.LogI("pause");
        this.isPause = true;
    }

    protected void resume() {
        DefaultBilling.LogI("resume");
        this.isPause = false;
        if (this.isPurchaseUpdated) {
            DefaultBilling.LogI("savedPurchaseUpdatedIntent : " + this.savedPurchaseUpdatedIntent.toString());
            resultPostInApp(8, i.a, 0, i.a, i.a, null);
            this.isPurchaseUpdated = false;
            this.savedPurchaseUpdatedIntent = null;
        }
    }

    protected void destroy() {
        DefaultBilling.LogI("destroy");
        if (this.mBroadcastReceiver != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        GooglePlayBilling.activity.unregisterReceiver(GooglePlayBilling.this.mBroadcastReceiver);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        DefaultBilling.LogI("Destroying helper.");
        if (this.mHelper != null) {
            this.mHelper.dispose();
        }
        this.mHelper = null;
    }

    protected void activityResult(final int requestCode, final int resultCode, final Intent data) {
        DefaultBilling.LogI("activityResult : " + requestCode + ", " + resultCode + ", " + data);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                if (GooglePlayBilling.this.mHelper.handleActivityResult(requestCode, resultCode, data)) {
                    DefaultBilling.LogI("onActivityResult handled by IABUtil.");
                }
            }
        });
    }

    private void processPurchasedData() {
        DefaultBilling.LogI("processPurchasedData");
        if (sendThread == null || !sendThread.isAlive()) {
            sendThread = new Thread(new Runnable() {
                /* JADX WARNING: inconsistent code. */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                public void run() {
                    /*
                    r50 = this;
                    r43 = new com.com2us.module.inapp.googleplay.BillingDatabase;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.activity;
                    r1 = r1.getApplicationContext();
                    r0 = r43;
                    r0.<init>(r1);
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ all -> 0x020c }
                    r1.ProgressDialogShow();	 Catch:{ all -> 0x020c }
                    r47 = r43.getPurchaseData();	 Catch:{ all -> 0x020c }
                    if (r47 != 0) goto L_0x002c;
                L_0x001c:
                    r1 = "purchasedData is nothing";
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ all -> 0x020c }
                    r43.close();
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                    r1.ProgressDialogDismiss();
                L_0x002b:
                    return;
                L_0x002c:
                    r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x020c }
                    r2 = "processPurchasedData(buy) - autoVerify : ";
                    r1.<init>(r2);	 Catch:{ all -> 0x020c }
                    r0 = r50;
                    r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ all -> 0x020c }
                    r2 = r2.autoVerify;	 Catch:{ all -> 0x020c }
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r1 = r1.toString();	 Catch:{ all -> 0x020c }
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ all -> 0x020c }
                    r49 = 0;
                L_0x0048:
                    r0 = r47;
                    r1 = r0.length;	 Catch:{ all -> 0x020c }
                    r0 = r49;
                    if (r0 < r1) goto L_0x006d;
                L_0x004f:
                    r45 = 0;
                L_0x0051:
                    r0 = r47;
                    r1 = r0.length;	 Catch:{ all -> 0x020c }
                    r0 = r45;
                    if (r0 < r1) goto L_0x00fd;
                L_0x0058:
                    r43.close();	 Catch:{ all -> 0x020c }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ all -> 0x020c }
                    r1.sendSuccess();	 Catch:{ all -> 0x020c }
                    r43.close();
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                    r1.ProgressDialogDismiss();
                    goto L_0x002b;
                L_0x006d:
                    r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x020c }
                    r2 = "purchasedData[";
                    r1.<init>(r2);	 Catch:{ all -> 0x020c }
                    r0 = r49;
                    r1 = r1.append(r0);	 Catch:{ all -> 0x020c }
                    r2 = "][0] : ";
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r2 = r47[r49];	 Catch:{ all -> 0x020c }
                    r3 = 0;
                    r2 = r2[r3];	 Catch:{ all -> 0x020c }
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r1 = r1.toString();	 Catch:{ all -> 0x020c }
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ all -> 0x020c }
                    r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x020c }
                    r2 = "purchasedData[";
                    r1.<init>(r2);	 Catch:{ all -> 0x020c }
                    r0 = r49;
                    r1 = r1.append(r0);	 Catch:{ all -> 0x020c }
                    r2 = "][1] : ";
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r2 = r47[r49];	 Catch:{ all -> 0x020c }
                    r3 = 1;
                    r2 = r2[r3];	 Catch:{ all -> 0x020c }
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r1 = r1.toString();	 Catch:{ all -> 0x020c }
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ all -> 0x020c }
                    r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x020c }
                    r2 = "purchasedData[";
                    r1.<init>(r2);	 Catch:{ all -> 0x020c }
                    r0 = r49;
                    r1 = r1.append(r0);	 Catch:{ all -> 0x020c }
                    r2 = "][2] : ";
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r2 = r47[r49];	 Catch:{ all -> 0x020c }
                    r3 = 2;
                    r2 = r2[r3];	 Catch:{ all -> 0x020c }
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r1 = r1.toString();	 Catch:{ all -> 0x020c }
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ all -> 0x020c }
                    r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x020c }
                    r2 = "purchasedData[";
                    r1.<init>(r2);	 Catch:{ all -> 0x020c }
                    r0 = r49;
                    r1 = r1.append(r0);	 Catch:{ all -> 0x020c }
                    r2 = "][3] : ";
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r2 = r47[r49];	 Catch:{ all -> 0x020c }
                    r3 = 3;
                    r2 = r2[r3];	 Catch:{ all -> 0x020c }
                    r1 = r1.append(r2);	 Catch:{ all -> 0x020c }
                    r1 = r1.toString();	 Catch:{ all -> 0x020c }
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ all -> 0x020c }
                    r49 = r49 + 1;
                    goto L_0x0048;
                L_0x00fd:
                    r46 = new com.com2us.module.inapp.googleplay.Purchase;	 Catch:{ Exception -> 0x0207 }
                    r1 = "inapp";
                    r2 = r47[r45];	 Catch:{ Exception -> 0x0207 }
                    r3 = 1;
                    r2 = r2[r3];	 Catch:{ Exception -> 0x0207 }
                    r3 = r47[r45];	 Catch:{ Exception -> 0x0207 }
                    r4 = 2;
                    r3 = r3[r4];	 Catch:{ Exception -> 0x0207 }
                    r0 = r46;
                    r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0207 }
                    r2 = "purchaseState : ";
                    r1.<init>(r2);	 Catch:{ Exception -> 0x0207 }
                    r2 = r46.getPurchaseState();	 Catch:{ Exception -> 0x0207 }
                    r1 = r1.append(r2);	 Catch:{ Exception -> 0x0207 }
                    r1 = r1.toString();	 Catch:{ Exception -> 0x0207 }
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getPurchaseState();	 Catch:{ Exception -> 0x0207 }
                    switch(r1) {
                        case 0: goto L_0x0191;
                        case 1: goto L_0x02e5;
                        case 2: goto L_0x034a;
                        default: goto L_0x012d;
                    };	 Catch:{ Exception -> 0x0207 }
                L_0x012d:
                    r1 = "Buy Failed : PurchaseState Nothing.";
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r0 = r43;
                    r0.deletePurchase(r1);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.RequestPostType.RC_LOG;	 Catch:{ Exception -> 0x0207 }
                    r3 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r4 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r5 = r46.getSignature();	 Catch:{ Exception -> 0x0207 }
                    r6 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r1 = r1.strPostDataBuilder(r2, r3, r4, r5, r6);	 Catch:{ Exception -> 0x0207 }
                    r0 = r43;
                    r0.updateLogData(r1);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r1.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0207 }
                    r42 = new com.com2us.module.inapp.InAppCallback$ErrorStateValue;	 Catch:{ Exception -> 0x0207 }
                    r1 = "c2s";
                    r2 = "6";
                    r3 = "PurchaseState Nothing";
                    r0 = r42;
                    r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r0 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r36 = r0;
                    r37 = 3;
                    r38 = r46.getSku();	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getManaged();	 Catch:{ Exception -> 0x0207 }
                    if (r1 == 0) goto L_0x03af;
                L_0x0180:
                    r39 = 0;
                L_0x0182:
                    r40 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r41 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r36.resultPostInApp(r37, r38, r39, r40, r41, r42);	 Catch:{ Exception -> 0x0207 }
                L_0x018d:
                    r45 = r45 + 1;
                    goto L_0x0051;
                L_0x0191:
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r1 = r1.autoVerify;	 Catch:{ Exception -> 0x0207 }
                    if (r1 == 0) goto L_0x02cd;
                L_0x019b:
                    r48 = -1;
                    r1 = r47[r45];	 Catch:{ Exception -> 0x0207 }
                    r2 = 3;
                    r1 = r1[r2];	 Catch:{ Exception -> 0x0207 }
                    if (r1 == 0) goto L_0x01b1;
                L_0x01a4:
                    r1 = "2";
                    r2 = r47[r45];	 Catch:{ Exception -> 0x0207 }
                    r3 = 3;
                    r2 = r2[r3];	 Catch:{ Exception -> 0x0207 }
                    r1 = r1.equals(r2);	 Catch:{ Exception -> 0x0207 }
                    if (r1 == 0) goto L_0x0218;
                L_0x01b1:
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r2 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r0 = r46;
                    r48 = r1.verifyPurchase(r0, r2);	 Catch:{ Exception -> 0x0207 }
                L_0x01bf:
                    switch(r48) {
                        case 0: goto L_0x022d;
                        case 1: goto L_0x0245;
                        case 2: goto L_0x0284;
                        default: goto L_0x01c2;
                    };	 Catch:{ Exception -> 0x0207 }
                L_0x01c2:
                    r1 = "Buy Failed : Error - default";
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r2 = r46.getSignature();	 Catch:{ Exception -> 0x0207 }
                    r3 = 0;
                    r0 = r43;
                    r0.updatePurchase(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r1.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0207 }
                    r21 = new com.com2us.module.inapp.InAppCallback$ErrorStateValue;	 Catch:{ Exception -> 0x0207 }
                    r1 = "c2s";
                    r2 = "3";
                    r3 = "System is being checked.";
                    r0 = r21;
                    r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r15 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r16 = 3;
                    r17 = r46.getSku();	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getManaged();	 Catch:{ Exception -> 0x0207 }
                    if (r1 == 0) goto L_0x02c9;
                L_0x01f9:
                    r18 = 0;
                L_0x01fb:
                    r19 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r20 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r15.resultPostInApp(r16, r17, r18, r19, r20, r21);	 Catch:{ Exception -> 0x0207 }
                    goto L_0x018d;
                L_0x0207:
                    r44 = move-exception;
                    r44.printStackTrace();	 Catch:{ all -> 0x020c }
                    goto L_0x018d;
                L_0x020c:
                    r1 = move-exception;
                    r43.close();
                    r0 = r50;
                    r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;
                    r2.ProgressDialogDismiss();
                    throw r1;
                L_0x0218:
                    r1 = r47[r45];	 Catch:{ Exception -> 0x0226 }
                    r2 = 3;
                    r1 = r1[r2];	 Catch:{ Exception -> 0x0226 }
                    r1 = java.lang.Integer.valueOf(r1);	 Catch:{ Exception -> 0x0226 }
                    r48 = r1.intValue();	 Catch:{ Exception -> 0x0226 }
                    goto L_0x01bf;
                L_0x0226:
                    r44 = move-exception;
                    r44.printStackTrace();	 Catch:{ Exception -> 0x0207 }
                    r48 = -1;
                    goto L_0x01bf;
                L_0x022d:
                    r1 = r47[r45];	 Catch:{ Exception -> 0x0207 }
                    r2 = 3;
                    r3 = "0";
                    r1[r2] = r3;	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r2 = r46.getSignature();	 Catch:{ Exception -> 0x0207 }
                    r3 = "0";
                    r0 = r43;
                    r0.updatePurchase(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    goto L_0x018d;
                L_0x0245:
                    r1 = "Buy Failed : Verification failed";
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r0 = r43;
                    r0.deletePurchase(r1);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r1.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0207 }
                    r7 = new com.com2us.module.inapp.InAppCallback$ErrorStateValue;	 Catch:{ Exception -> 0x0207 }
                    r1 = "c2s";
                    r2 = "1";
                    r3 = "Verification failed.";
                    r7.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r2 = 3;
                    r3 = r46.getSku();	 Catch:{ Exception -> 0x0207 }
                    r4 = r46.getManaged();	 Catch:{ Exception -> 0x0207 }
                    if (r4 == 0) goto L_0x0282;
                L_0x0274:
                    r4 = 0;
                L_0x0275:
                    r5 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r6 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r1.resultPostInApp(r2, r3, r4, r5, r6, r7);	 Catch:{ Exception -> 0x0207 }
                    goto L_0x018d;
                L_0x0282:
                    r4 = 1;
                    goto L_0x0275;
                L_0x0284:
                    r1 = "Buy Failed : System is being checked";
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r2 = r46.getSignature();	 Catch:{ Exception -> 0x0207 }
                    r3 = "2";
                    r0 = r43;
                    r0.updatePurchase(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r1.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0207 }
                    r14 = new com.com2us.module.inapp.InAppCallback$ErrorStateValue;	 Catch:{ Exception -> 0x0207 }
                    r1 = "c2s";
                    r2 = "2";
                    r3 = "System is being checked. Please try again later.";
                    r14.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r8 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r9 = 3;
                    r10 = r46.getSku();	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getManaged();	 Catch:{ Exception -> 0x0207 }
                    if (r1 == 0) goto L_0x02c7;
                L_0x02b9:
                    r11 = 0;
                L_0x02ba:
                    r12 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r13 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r8.resultPostInApp(r9, r10, r11, r12, r13, r14);	 Catch:{ Exception -> 0x0207 }
                    goto L_0x018d;
                L_0x02c7:
                    r11 = 1;
                    goto L_0x02ba;
                L_0x02c9:
                    r18 = 1;
                    goto L_0x01fb;
                L_0x02cd:
                    r1 = r47[r45];	 Catch:{ Exception -> 0x0207 }
                    r2 = 3;
                    r3 = "0";
                    r1[r2] = r3;	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r2 = r46.getSignature();	 Catch:{ Exception -> 0x0207 }
                    r3 = "0";
                    r0 = r43;
                    r0.updatePurchase(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    goto L_0x018d;
                L_0x02e5:
                    r1 = "Buy Failed : Error - Market canceled.";
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r0 = r43;
                    r0.deletePurchase(r1);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.RequestPostType.RC_LOG;	 Catch:{ Exception -> 0x0207 }
                    r3 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r4 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r5 = r46.getSignature();	 Catch:{ Exception -> 0x0207 }
                    r6 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r1 = r1.strPostDataBuilder(r2, r3, r4, r5, r6);	 Catch:{ Exception -> 0x0207 }
                    r0 = r43;
                    r0.updateLogData(r1);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r1.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0207 }
                    r28 = new com.com2us.module.inapp.InAppCallback$ErrorStateValue;	 Catch:{ Exception -> 0x0207 }
                    r1 = "c2s";
                    r2 = "4";
                    r3 = "Market canceled";
                    r0 = r28;
                    r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r0 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r22 = r0;
                    r23 = 3;
                    r24 = r46.getSku();	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getManaged();	 Catch:{ Exception -> 0x0207 }
                    if (r1 == 0) goto L_0x0347;
                L_0x0338:
                    r25 = 0;
                L_0x033a:
                    r26 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r27 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r22.resultPostInApp(r23, r24, r25, r26, r27, r28);	 Catch:{ Exception -> 0x0207 }
                    goto L_0x018d;
                L_0x0347:
                    r25 = 1;
                    goto L_0x033a;
                L_0x034a:
                    r1 = "Buy Failed : Error - Item refunded.";
                    com.com2us.module.inapp.DefaultBilling.LogI(r1);	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r0 = r43;
                    r0.deletePurchase(r1);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r2 = com.com2us.module.inapp.googleplay.GooglePlayBilling.RequestPostType.RC_LOG;	 Catch:{ Exception -> 0x0207 }
                    r3 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r4 = r46.getOriginalJson();	 Catch:{ Exception -> 0x0207 }
                    r5 = r46.getSignature();	 Catch:{ Exception -> 0x0207 }
                    r6 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r1 = r1.strPostDataBuilder(r2, r3, r4, r5, r6);	 Catch:{ Exception -> 0x0207 }
                    r0 = r43;
                    r0.updateLogData(r1);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r1 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r1.ProgressDialogDismiss();	 Catch:{ Exception -> 0x0207 }
                    r35 = new com.com2us.module.inapp.InAppCallback$ErrorStateValue;	 Catch:{ Exception -> 0x0207 }
                    r1 = "c2s";
                    r2 = "5";
                    r3 = "Item Refunded";
                    r0 = r35;
                    r0.<init>(r1, r2, r3);	 Catch:{ Exception -> 0x0207 }
                    r0 = r50;
                    r0 = com.com2us.module.inapp.googleplay.GooglePlayBilling.this;	 Catch:{ Exception -> 0x0207 }
                    r29 = r0;
                    r30 = 3;
                    r31 = r46.getSku();	 Catch:{ Exception -> 0x0207 }
                    r1 = r46.getManaged();	 Catch:{ Exception -> 0x0207 }
                    if (r1 == 0) goto L_0x03ac;
                L_0x039d:
                    r32 = 0;
                L_0x039f:
                    r33 = r46.getUid();	 Catch:{ Exception -> 0x0207 }
                    r34 = r46.getAddtionalInfo();	 Catch:{ Exception -> 0x0207 }
                    r29.resultPostInApp(r30, r31, r32, r33, r34, r35);	 Catch:{ Exception -> 0x0207 }
                    goto L_0x018d;
                L_0x03ac:
                    r32 = 1;
                    goto L_0x039f;
                L_0x03af:
                    r39 = 1;
                    goto L_0x0182;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.inapp.googleplay.GooglePlayBilling.17.run():void");
                }
            });
            sendThread.start();
            return;
        }
        DefaultBilling.LogI("processPurchasedData - sendThread is Alive, return");
    }

    private void sendSuccess() {
        int i = 0;
        DefaultBilling.LogI("sendSuccess");
        BillingDatabase db = new BillingDatabase(activity.getApplicationContext());
        String[][] purchasedData = db.getPurchaseData();
        db.close();
        if (purchasedData == null || purchasedData.length <= 0) {
            DefaultBilling.LogI("purchasedData is nothing");
            sendLog();
            return;
        }
        for (int y = 0; y < purchasedData.length; y++) {
            DefaultBilling.LogI("purchasedData[" + y + "][0] : " + purchasedData[y][0]);
            DefaultBilling.LogI("purchasedData[" + y + "][1] : " + purchasedData[y][1]);
            DefaultBilling.LogI("purchasedData[" + y + "][2] : " + purchasedData[y][2]);
            DefaultBilling.LogI("purchasedData[" + y + "][3] : " + purchasedData[y][3]);
        }
        int i2 = 0;
        while (i2 < purchasedData.length) {
            try {
                if (a.d.equals(purchasedData[i2][3])) {
                    Purchase purchase = new Purchase(GooglePlayHelper.ITEM_TYPE_INAPP, purchasedData[i2][1], purchasedData[i2][2]);
                    if (purchase.getPurchaseState() == 0) {
                        DefaultBilling.LogI("BUY_SUCCESS : " + purchase.getOriginalJson() + "  signature : " + purchase.getSignature());
                        ProgressDialogDismiss();
                        String sku = purchase.getSku();
                        if (!purchase.getManaged()) {
                            i = 1;
                        }
                        resultPostInApp(2, sku, i, purchase.getUid(), purchase.getAddtionalInfo(), makeSuccessStateValue(purchase));
                        return;
                    }
                }
                i2++;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }

    private void sendLog() {
        DefaultBilling.LogI("sendLog");
        if (logThread == null || !logThread.isAlive()) {
            logThread = new Thread(new Runnable() {
                public void run() {
                    BillingDatabase db = new BillingDatabase(GooglePlayBilling.activity.getApplicationContext());
                    String[][] logData = db.getLogData();
                    int y = 0;
                    while (y < logData.length) {
                        try {
                            DefaultBilling.LogI("logData[" + y + "][0] : " + logData[y][0]);
                            y++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Throwable th) {
                            db.close();
                        }
                    }
                    if (logData != null) {
                        for (int i = 0; i < logData.length; i++) {
                            String string;
                            String str = logData[i][0];
                            if (GooglePlayBilling.this.isUseTestServer) {
                                string = Utility.getString(11);
                            } else {
                                string = Utility.getString(10);
                            }
                            String responseStr = DefaultBilling.sendToServer(str, string);
                            int resultLog = -1;
                            if (responseStr != null) {
                                JSONObject toBeParsed = new JSONObject(responseStr);
                                resultLog = toBeParsed.getInt(PeppermintConstant.JSON_KEY_RESULT);
                                DefaultBilling.LogI("ErrorMsg : " + toBeParsed.optString("errormsg"));
                            } else {
                                DefaultBilling.LogI("responseStr is null");
                            }
                            switch (resultLog) {
                                case g.a /*0*/:
                                    db.deleteLogData(logData[i][0]);
                                    break;
                                case o.b /*2*/:
                                    db.deleteLogData(logData[i][0]);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    db.close();
                }
            });
            logThread.start();
        }
    }

    private JSONObject makeVerifyData(Purchase purchase) {
        JSONObject verifyData = new JSONObject();
        try {
            verifyData.put("transaction", purchase.getOriginalJson());
            verifyData.put("signature", purchase.getSignature());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return verifyData;
    }

    private int verifyPurchase(Purchase purchase, String uid) {
        List purchaseList = new ArrayList();
        purchaseList.add(purchase);
        return verifyPurchase(purchaseList, uid);
    }

    private int verifyPurchase(List<Purchase> purchaseList, String uid) {
        DefaultBilling.LogI("verifyPurchase");
        JSONArray verifyList = new JSONArray();
        for (Purchase purchase : purchaseList) {
            verifyList.put(makeVerifyData(purchase));
        }
        try {
            String responseStr = DefaultBilling.sendToServer(strPostDataBuilder(RequestPostType.RC_VERIFY, uid, verifyList.toString(), null, null), this.isUseTestServer ? Utility.getString(9) : Utility.getString(8));
            DefaultBilling.LogI("responseStr : " + responseStr);
            int resultBill = new JSONObject(responseStr).getInt(PeppermintConstant.JSON_KEY_RESULT);
            DefaultBilling.LogI("verifyList : " + verifyList.toString());
            return resultBill;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    private ProgressDialog onCreateProgressDialog() {
        ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setProgressStyle(0);
        pDialog.setMessage("Checking your billing information...");
        pDialog.setCancelable(true);
        return pDialog;
    }

    private synchronized void ProgressDialogShow() {
        DefaultBilling.LogI("Show Dialog");
        if (progressDialog == null || !progressDialog.isShowing()) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        GooglePlayBilling.progressDialog = GooglePlayBilling.this.onCreateProgressDialog();
                        GooglePlayBilling.progressDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private synchronized void ProgressDialogDismiss() {
        DefaultBilling.LogI("Dismiss Dialog");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    if (GooglePlayBilling.progressDialog != null) {
                        GooglePlayBilling.progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String strPostDataBuilder(com.com2us.module.inapp.googleplay.GooglePlayBilling.RequestPostType r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, java.lang.String r11) {
        /*
        r6 = this;
        r1 = new org.json.JSONObject;
        r1.<init>();
        r4 = r6.moduleData;
        r3 = r4.getMobileDeviceNumber();
        r4 = r6.moduleData;
        r2 = r4.getMacAddress();
        r4 = "market";
        r5 = "google";
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "appid";
        r5 = r6.moduleData;	 Catch:{ Exception -> 0x00d6 }
        r5 = r5.getAppID();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "appversion";
        r5 = r6.moduleData;	 Catch:{ Exception -> 0x00d6 }
        r5 = r5.getAppVersion();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "device";
        r5 = r6.moduleData;	 Catch:{ Exception -> 0x00d6 }
        r5 = r5.getDeviceModel();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "udid";
        r5 = r6.GetUDID();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "osversion";
        r5 = r6.moduleData;	 Catch:{ Exception -> 0x00d6 }
        r5 = r5.getOSVersion();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "country";
        r5 = r6.moduleData;	 Catch:{ Exception -> 0x00d6 }
        r5 = r5.getCountry();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "language";
        r5 = r6.moduleData;	 Catch:{ Exception -> 0x00d6 }
        r5 = r5.getLanguage();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        if (r2 == 0) goto L_0x006a;
    L_0x0065:
        r4 = "mac";
        r1.put(r4, r2);	 Catch:{ Exception -> 0x00d6 }
    L_0x006a:
        if (r3 == 0) goto L_0x0071;
    L_0x006c:
        r4 = "mdn";
        r1.put(r4, r3);	 Catch:{ Exception -> 0x00d6 }
    L_0x0071:
        r4 = "androidid";
        r5 = r6.moduleData;	 Catch:{ Exception -> 0x00d6 }
        r5 = r5.getAndroidID();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "uid";
        r1.put(r4, r8);	 Catch:{ Exception -> 0x00d6 }
        r4 = "did";
        r5 = r6.moduleData;	 Catch:{ Exception -> 0x00d6 }
        r5 = r5.getDID();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "libver";
        r5 = r6.VERSION;	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "vid";
        r5 = r6.getVID();	 Catch:{ Exception -> 0x00d6 }
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "apiver";
        r5 = "3";
        r1.put(r4, r5);	 Catch:{ Exception -> 0x00d6 }
        r4 = "addtionalInfo";
        r1.put(r4, r11);	 Catch:{ Exception -> 0x00d6 }
        r4 = $SWITCH_TABLE$com$com2us$module$inapp$googleplay$GooglePlayBilling$RequestPostType();	 Catch:{ Exception -> 0x00d6 }
        r5 = r7.ordinal();	 Catch:{ Exception -> 0x00d6 }
        r4 = r4[r5];	 Catch:{ Exception -> 0x00d6 }
        switch(r4) {
            case 1: goto L_0x00d0;
            case 2: goto L_0x00db;
            default: goto L_0x00b5;
        };
    L_0x00b5:
        r4 = new java.lang.StringBuilder;
        r5 = "jsonObjectPostData : ";
        r4.<init>(r5);
        r5 = r1.toString();
        r4 = r4.append(r5);
        r4 = r4.toString();
        com.com2us.module.inapp.DefaultBilling.LogI(r4);
        r4 = r1.toString();
        return r4;
    L_0x00d0:
        r4 = "verifylist";
        r1.put(r4, r9);	 Catch:{ Exception -> 0x00d6 }
        goto L_0x00b5;
    L_0x00d6:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x00b5;
    L_0x00db:
        r4 = "transaction";
        r1.put(r4, r9);	 Catch:{ Exception -> 0x00d6 }
        r4 = "signature";
        r1.put(r4, r10);	 Catch:{ Exception -> 0x00d6 }
        goto L_0x00b5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.inapp.googleplay.GooglePlayBilling.strPostDataBuilder(com.com2us.module.inapp.googleplay.GooglePlayBilling$RequestPostType, java.lang.String, java.lang.String, java.lang.String, java.lang.String):java.lang.String");
    }

    private String[] makeSuccessStateValue(Purchase purchase) {
        String[] ret = new String[41];
        ret[0] = String.valueOf(15);
        ret[1] = "GooglePlay";
        ret[35] = purchase != null ? purchase.getOriginalJson() : i.a;
        ret[36] = purchase != null ? purchase.getSignature() : i.a;
        return ret;
    }
}
