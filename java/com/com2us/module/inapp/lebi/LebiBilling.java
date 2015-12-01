package com.com2us.module.inapp.lebi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.com2us.module.inapp.DefaultBilling;
import com.com2us.module.inapp.InAppCallback.ErrorStateValue;
import com.com2us.module.inapp.InAppCallback.ItemList;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.security.message.CryptMessage;
import com.com2us.security.message.MessageCryptException;
import com.com2us.security.message.MessageTimeoutException;
import com.google.android.gcm.GCMConstants;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.o;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class LebiBilling extends DefaultBilling {
    public static final String Ver = "2.10.1";
    private static Thread balanceThread = null;
    private static Dialog billingDialog = null;
    private static Thread getItemListThread = null;
    private static boolean isBilling = false;
    private static ItemList[] itemList = null;
    private static ProgressDialog progressDialog = null;
    private static Thread purchaseCheckThread = null;
    private static Dialog purchaseFstDialog = null;
    private static Dialog purchaseSndDialog = null;
    private static Dialog purchaseTrdDialog = null;
    private static Thread sendThread = null;
    private String Popup_ChargeMsg;
    private String Popup_ChargeTitle;
    private String Popup_FailedMsg;
    private String Popup_FailedTitle;
    private String Popup_FstTitle;
    private String Popup_SuccessTitle;
    private String Progress_BalanceCheckMessage;
    private String Progress_Message;
    private String Progress_WebViewLoading;
    private String Text_Buy;
    private String Text_Cancel;
    private String Text_CurrentLebi;
    private String Text_Error;
    private String Text_Lebi;
    private String Text_Ok;
    private String Text_RemainLebi;
    private String Text_Success;
    private String Toast_BalanceCheckFailed;
    private String Toast_BillingViewClose;
    private String Toast_DateCheckFailed;
    private String Toast_HiveLoginRequired;
    private String Toast_ProgressStoreStart;
    private String Toast_PurchaseCanceled;
    private String pd;
    private String restoreUid;
    private String sysId;

    class WebViewCallBack extends WebViewClient {
        ProgressDialog webProgressDialog = null;

        WebViewCallBack() {
        }

        public void onLoadResource(WebView view, String url) {
            if (this.webProgressDialog == null) {
                this.webProgressDialog = new ProgressDialog(LebiBilling.activity);
                this.webProgressDialog.setMessage(LebiBilling.this.Progress_WebViewLoading);
                this.webProgressDialog.setCancelable(false);
                this.webProgressDialog.show();
            }
            super.onLoadResource(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            DefaultBilling.LogI("url : " + url);
            if (this.webProgressDialog != null && this.webProgressDialog.isShowing()) {
                this.webProgressDialog.dismiss();
                LebiBilling.billingDialog.show();
            }
        }
    }

    public LebiBilling(Activity _activity) {
        super(_activity);
        this.restoreUid = i.a;
        this.sysId = i.a;
        this.pd = i.a;
        this.Popup_FstTitle = "Accept & buy";
        this.Popup_SuccessTitle = "Success Item Purcahse";
        this.Popup_FailedTitle = "Failed Item Purcahse";
        this.Popup_FailedMsg = "Purchase failed. Please try again later.";
        this.Popup_ChargeTitle = "Not enough Lebi";
        this.Popup_ChargeMsg = "Not enough Lebi. Go to Lebi charging site?";
        this.Progress_Message = "Waiting for check buy item...";
        this.Progress_BalanceCheckMessage = "Waiting for check balance...";
        this.Progress_WebViewLoading = "Loading...";
        this.Toast_BillingViewClose = "Checking billing progress...";
        this.Toast_ProgressStoreStart = "Found previous progress. Please try again later.";
        this.Toast_HiveLoginRequired = "Hive login is required.";
        this.Toast_BalanceCheckFailed = "Balance check failed. Please try again later.";
        this.Toast_DateCheckFailed = "If you forcefully adjust date/time, you might be unable to check your balance or make a purchase.";
        this.Toast_PurchaseCanceled = "Purchase Canceled.";
        this.Text_CurrentLebi = "Current Lebi";
        this.Text_RemainLebi = "Remain Lebi";
        this.Text_Lebi = "Lebi";
        this.Text_Success = "Success";
        this.Text_Error = "Error";
        this.Text_Buy = "Buy";
        this.Text_Cancel = "Cancel";
        this.Text_Ok = "OK";
        this.VERSION = Ver;
    }

    protected int iapInitialize(String[] pids, String _appid, boolean _autoVerify, int cbRef) {
        appid = _appid;
        this.autoVerify = _autoVerify;
        CallBackRef = cbRef;
        this.sysId = this.isUseTestServer ? Utility.getString(6) : Utility.getString(4);
        this.pd = this.isUseTestServer ? Utility.getString(7) : Utility.getString(5);
        setLanguage();
        getItemListThread = new Thread(new Runnable() {
            public void run() {
                String response = LebiBilling.this.xmlParse(LebiBilling.this.sendToPost(LebiBilling.this.postDataBuilder("07", LebiBilling.appid), LebiBilling.this.isUseTestServer ? Utility.getString(1) : Utility.getString(0)));
                DefaultBilling.LogI("request item price response : " + response);
                LebiBilling.this.processReturnValue("07", response);
            }
        });
        getItemListThread.start();
        return 1;
    }

    protected void iapUninitialize() {
        DefaultBilling.LogI("Uninitialize");
        CallBackRef = 0;
        appid = i.a;
        this.restoreUid = i.a;
        this.sysId = i.a;
        this.pd = i.a;
        itemList = null;
        billingDialog = null;
        purchaseFstDialog = null;
        purchaseSndDialog = null;
        purchaseTrdDialog = null;
        progressDialog = null;
        getItemListThread = null;
        purchaseCheckThread = null;
        sendThread = null;
        balanceThread = null;
    }

    protected void iapStoreStart() {
        DefaultBilling.LogI("StoreStart");
        if (!checkNetworkState() || !isBilling()) {
            return;
        }
        if (purchaseCheckThread == null || !purchaseCheckThread.isAlive()) {
            purchaseCheckThread = new Thread(new Runnable() {
                public void run() {
                    StringBuffer postDataBuffer = new StringBuffer();
                    postDataBuffer.append(LebiBilling.this.FullStringbyLength(LebiBilling.this.propertyUtil.getProperty("billingNum"), 9)).append(LebiBilling.this.FullStringbyLength(LebiBilling.this.propertyUtil.getProperty(PeppermintConstant.JSON_KEY_UID), 9));
                    String response = LebiBilling.this.xmlParse(LebiBilling.this.sendToPost(LebiBilling.this.postDataBuilder("04", postDataBuffer.toString()), LebiBilling.this.isUseTestServer ? Utility.getString(1) : Utility.getString(0)));
                    DefaultBilling.LogI("request purchase check response : " + response);
                    LebiBilling.this.processReturnValue(response);
                }
            });
            showPreviousProgressInfoDialog(activity, new Runnable() {
                public void run() {
                    LebiBilling.purchaseCheckThread.start();
                }
            });
            return;
        }
        DefaultBilling.LogI("iapStoreStart - purchaseCheckThread is Alive, return");
    }

    protected void iapStoreEnd() {
        DefaultBilling.LogI("StoreEnd");
        isBilling = false;
    }

    protected void iapRestoreItem(final String uid) {
        DefaultBilling.LogI("RestoreItem");
        if (checkNetworkState()) {
            new Thread(new Runnable() {
                public void run() {
                    LebiBilling.this.restoreUid = uid;
                    StringBuffer postDataBuffer = new StringBuffer();
                    postDataBuffer.append(LebiBilling.this.FullStringbyLength(uid, 9)).append(LebiBilling.appid);
                    String response = LebiBilling.this.xmlParse(LebiBilling.this.sendToPost(LebiBilling.this.postDataBuilder("08", postDataBuffer.toString()), LebiBilling.this.isUseTestServer ? Utility.getString(1) : Utility.getString(0)));
                    DefaultBilling.LogI("request restore item response : " + response);
                    LebiBilling.this.processReturnValue("08", response);
                }
            }).start();
        }
    }

    protected void iapBuyItem(String pid, int quantity, String uid, String addtionalInfo) {
        DefaultBilling.LogI("BuyItem");
        if (!checkNetworkState()) {
            return;
        }
        if (isBilling || isBilling()) {
            DefaultBilling.LogI("Found previous progress.");
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_ProgressStoreStart, 0).show();
                }
            });
            if (purchaseCheckThread == null || !purchaseCheckThread.isAlive()) {
                purchaseCheckThread = new Thread(new Runnable() {
                    public void run() {
                        LebiBilling.this.ProgressDialogShow(LebiBilling.this.Progress_Message);
                        StringBuffer postDataBuffer = new StringBuffer();
                        postDataBuffer.append(LebiBilling.this.FullStringbyLength(LebiBilling.this.propertyUtil.getProperty("billingNum"), 9)).append(LebiBilling.this.FullStringbyLength(LebiBilling.this.propertyUtil.getProperty(PeppermintConstant.JSON_KEY_UID), 9));
                        String response = LebiBilling.this.xmlParse(LebiBilling.this.sendToPost(LebiBilling.this.postDataBuilder("04", postDataBuffer.toString()), LebiBilling.this.isUseTestServer ? Utility.getString(1) : Utility.getString(0)));
                        DefaultBilling.LogI("request purchase check response : " + response);
                        LebiBilling.this.ProgressDialogDismiss();
                        LebiBilling.this.processReturnValue(response);
                    }
                });
                showPreviousProgressInfoDialog(activity, new Runnable() {
                    public void run() {
                        LebiBilling.purchaseCheckThread.start();
                    }
                });
                return;
            }
            DefaultBilling.LogI("iapBuyItem - purchaseCheckThread is Alive, return");
        } else if (sendThread == null || !sendThread.isAlive()) {
            final String str = pid;
            final int i = quantity;
            final String str2 = uid;
            final String str3 = addtionalInfo;
            sendThread = new Thread(new Runnable() {
                public void run() {
                    int i;
                    String string;
                    try {
                        LebiBilling.this.ProgressDialogShow(LebiBilling.this.Progress_Message);
                        LebiBilling.this.propertyUtil.setProperty("pid", str);
                        LebiBilling.this.propertyUtil.setProperty("quantity", String.valueOf(i));
                        LebiBilling.this.propertyUtil.setProperty(PeppermintConstant.JSON_KEY_UID, str2);
                        LebiBilling.this.propertyUtil.setProperty("addtionalInfo", str3);
                        LebiBilling.this.propertyUtil.storeProperty(null);
                        String[] restoreItemList = null;
                        StringBuffer postDataBuffer = new StringBuffer();
                        postDataBuffer.append(LebiBilling.this.FullStringbyLength(str2, 9)).append(LebiBilling.appid);
                        String data = LebiBilling.this.xmlParse(LebiBilling.this.sendToPost(LebiBilling.this.postDataBuilder("08", postDataBuffer.toString()), LebiBilling.this.isUseTestServer ? Utility.getString(1) : Utility.getString(0)));
                        DefaultBilling.LogI("request restore item response : " + data);
                        CryptMessage receiverMessage = new CryptMessage();
                        receiverMessage.setMessage(LebiBilling.this.sysId, LebiBilling.this.pd, data);
                        String restoreResponse = receiverMessage.getProperty("pkt");
                        DefaultBilling.LogI("decrypt response : " + restoreResponse);
                    } catch (MessageTimeoutException e) {
                        e.printStackTrace();
                        LebiBilling.activity.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_DateCheckFailed, 1).show();
                            }
                        });
                        restoreResponse = GCMConstants.EXTRA_ERROR;
                    } catch (MessageCryptException e2) {
                        e2.printStackTrace();
                        restoreResponse = GCMConstants.EXTRA_ERROR;
                    } catch (Throwable th) {
                        LebiBilling.this.ProgressDialogDismiss();
                    }
                    try {
                        JSONArray jSONArray = new JSONArray(restoreResponse);
                        int jsonArrayLength = jSONArray.length();
                        if (jsonArrayLength != 0) {
                            restoreItemList = new String[jsonArrayLength];
                        }
                        for (int y = 0; y < jsonArrayLength; y++) {
                            DefaultBilling.LogI("restore jsonArray[" + y + "] : " + jSONArray.getString(y));
                            restoreItemList[y] = jSONArray.getString(y);
                        }
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        DefaultBilling.LogI("process request restore item failed : " + restoreResponse);
                    }
                    if (restoreItemList != null) {
                        for (i = 0; i < restoreItemList.length; i++) {
                            if (restoreItemList[i].compareTo(LebiBilling.this.FullStringbyLength(str, 12)) == 0) {
                                DefaultBilling.LogI("checking managed item (pid) : " + restoreItemList[i]);
                                LebiBilling.this.deleteBuyInfo();
                                LebiBilling.this.ProgressDialogDismiss();
                                LebiBilling.this.resultPostInApp(5, str, i, str2, str3, LebiBilling.this.makeSuccessStateValue(i.a));
                                LebiBilling.this.ProgressDialogDismiss();
                                return;
                            }
                        }
                    }
                    if (LebiBilling.itemList == null) {
                        LebiBilling lebiBilling = LebiBilling.this;
                        LebiBilling lebiBilling2 = LebiBilling.this;
                        List access$4 = LebiBilling.this.postDataBuilder("07", LebiBilling.appid);
                        if (LebiBilling.this.isUseTestServer) {
                            string = Utility.getString(1);
                        } else {
                            string = Utility.getString(0);
                        }
                        String itemPriceResponse = lebiBilling.xmlParse(lebiBilling2.sendToPost(access$4, string));
                        DefaultBilling.LogI("request item price response : " + itemPriceResponse);
                        if (((String) LebiBilling.this.processReturnValue("07", itemPriceResponse)) == null) {
                            DefaultBilling.LogI("out of itemlist");
                            LebiBilling.this.deleteBuyInfo();
                            LebiBilling.this.ProgressDialogDismiss();
                            LebiBilling.this.resultPostInApp(3, str, i, str2, str3, new ErrorStateValue("c2s", "34", "Out of itemlist."));
                            LebiBilling.this.ProgressDialogDismiss();
                            return;
                        }
                    }
                    int balance = -2;
                    if (str2 == null || str2.compareTo(i.a) == 0) {
                        DefaultBilling.LogI("Hive login is required.");
                        LebiBilling.this.deleteBuyInfo();
                        LebiBilling.activity.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_HiveLoginRequired, 0).show();
                            }
                        });
                        LebiBilling.this.ProgressDialogDismiss();
                        LebiBilling.this.resultPostInApp(3, str, i, str2, str3, new ErrorStateValue("c2s", "33", "Hive login is required."));
                        LebiBilling.this.ProgressDialogDismiss();
                        return;
                    }
                    lebiBilling = LebiBilling.this;
                    lebiBilling2 = LebiBilling.this;
                    access$4 = LebiBilling.this.postDataBuilder("06", LebiBilling.this.FullStringbyLength(str2, 9));
                    if (LebiBilling.this.isUseTestServer) {
                        string = Utility.getString(1);
                    } else {
                        string = Utility.getString(0);
                    }
                    String response = lebiBilling.xmlParse(lebiBilling2.sendToPost(access$4, string));
                    DefaultBilling.LogI("request balance response : " + response);
                    if (response.compareTo("-1") != 0) {
                        if (response.compareTo("-2") != 0) {
                            Object processedReturnValue = LebiBilling.this.processReturnValue(response);
                            if (processedReturnValue != null) {
                                try {
                                    balance = ((Integer) processedReturnValue).intValue();
                                } catch (Exception e32) {
                                    e32.printStackTrace();
                                }
                            }
                            DefaultBilling.LogI("balance : " + balance);
                            int itemPrice = -1;
                            i = 0;
                            while (i < LebiBilling.itemList.length) {
                                if (LebiBilling.itemList[i].productID.compareTo(LebiBilling.this.FullStringbyLength(str, 12)) == 0) {
                                    itemPrice = Integer.valueOf(LebiBilling.itemList[i].formattedString).intValue();
                                    break;
                                }
                                i++;
                            }
                            if (i >= LebiBilling.itemList.length) {
                                DefaultBilling.LogI("wrong request item");
                                LebiBilling.this.deleteBuyInfo();
                                LebiBilling.this.ProgressDialogDismiss();
                                LebiBilling.this.resultPostInApp(3, str, i, str2, str3, new ErrorStateValue("c2s", "31", "Wrong request item."));
                                LebiBilling.this.ProgressDialogDismiss();
                                return;
                            }
                            if (itemPrice > balance) {
                                DefaultBilling.LogI("request chage lebi");
                                LebiBilling.this.ProgressDialogDismiss();
                                int price = itemPrice;
                                Activity access$0 = LebiBilling.activity;
                                final String str = str2;
                                final int i2 = price;
                                access$0.runOnUiThread(new Runnable() {
                                    public void run() {
                                        LebiBilling.purchaseTrdDialog = LebiBilling.this.purchasePopup(LebiBilling.activity, 4, LebiBilling.this.Popup_ChargeMsg, i2, str);
                                        LebiBilling.purchaseTrdDialog.show();
                                    }
                                });
                            } else {
                                final StringBuffer fstMsg = new StringBuffer().append(LebiBilling.this.Text_CurrentLebi).append(" : ").append(balance).append(LebiBilling.this.Text_Lebi).append("\n").append(LebiBilling.itemList[i].localizedTitle).append(" : ").append(itemPrice).append(LebiBilling.this.Text_Lebi);
                                LebiBilling.activity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        LebiBilling.purchaseFstDialog = LebiBilling.this.purchasePopup(LebiBilling.activity, 1, fstMsg.toString());
                                        LebiBilling.purchaseFstDialog.show();
                                    }
                                });
                            }
                            LebiBilling.this.ProgressDialogDismiss();
                            return;
                        }
                    }
                    DefaultBilling.LogI("Balance Check Failed.");
                    LebiBilling.this.deleteBuyInfo();
                    LebiBilling.activity.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_BalanceCheckFailed, 0).show();
                        }
                    });
                    LebiBilling.this.ProgressDialogDismiss();
                    LebiBilling.this.resultPostInApp(3, str, i, str2, str3, new ErrorStateValue("c2s", "32", "Balance check failed."));
                    LebiBilling.this.ProgressDialogDismiss();
                }
            });
            sendThread.start();
        } else {
            DefaultBilling.LogI("iapBuyItem - sendThread is Alive, return");
        }
    }

    protected void iapBuyFinish() {
        DefaultBilling.LogI("iapBuyFinish");
        deleteBuyInfo();
    }

    protected void pause() {
        DefaultBilling.LogI("pause");
    }

    protected void resume() {
        DefaultBilling.LogI("resume");
    }

    protected void iapUseTestServer() {
        super.iapUseTestServer();
        if (getItemListThread != null) {
            this.sysId = this.isUseTestServer ? Utility.getString(6) : Utility.getString(4);
            this.pd = this.isUseTestServer ? Utility.getString(7) : Utility.getString(5);
            if (getItemListThread != null && getItemListThread.isAlive()) {
                getItemListThread.interrupt();
                DefaultBilling.LogI("iapUseTestServer - getItemListThread is Alive, interrupt");
            }
            getItemListThread = new Thread(new Runnable() {
                public void run() {
                    String response = LebiBilling.this.xmlParse(LebiBilling.this.sendToPost(LebiBilling.this.postDataBuilder("07", LebiBilling.appid), LebiBilling.this.isUseTestServer ? Utility.getString(1) : Utility.getString(0)));
                    DefaultBilling.LogI("request item price response : " + response);
                    LebiBilling.this.processReturnValue("07", response);
                }
            });
            getItemListThread.start();
        }
    }

    public int iapRequestBalance(String uid) {
        DefaultBilling.LogI("requestBalance");
        int balance = -3;
        ProgressDialogShow(this.Progress_BalanceCheckMessage);
        if (uid == null || uid.compareTo(i.a) == 0) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_HiveLoginRequired, 0).show();
                }
            });
            ProgressDialogDismiss();
            return -2;
        }
        String string;
        List postDataBuilder = postDataBuilder("06", FullStringbyLength(uid, 9));
        if (this.isUseTestServer) {
            string = Utility.getString(1);
        } else {
            string = Utility.getString(0);
        }
        String response = xmlParse(sendToPost(postDataBuilder, string));
        DefaultBilling.LogI("request balance response : " + response);
        if (response.compareTo("-1") == 0 || response.compareTo("-2") == 0) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_BalanceCheckFailed, 0).show();
                }
            });
            ProgressDialogDismiss();
            return -2;
        }
        Object processedReturnValue = processReturnValue(response);
        if (processedReturnValue != null) {
            try {
                balance = ((Integer) processedReturnValue).intValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DefaultBilling.LogI("balance : " + balance);
        ProgressDialogDismiss();
        return balance;
    }

    private void requestPurchase() {
        int quantity;
        DefaultBilling.LogI("requestPurchase");
        String pid = this.propertyUtil.getProperty("pid");
        String strQuantity = this.propertyUtil.getProperty("quantity");
        if (strQuantity == null) {
            quantity = 0;
        } else if (strQuantity.equals(i.a)) {
            quantity = 0;
        } else {
            quantity = Integer.valueOf(strQuantity).intValue();
        }
        String uid = this.propertyUtil.getProperty(PeppermintConstant.JSON_KEY_UID);
        String addtionalInfo = this.propertyUtil.getProperty("addtionalInfo");
        DefaultBilling.LogI("pid : " + pid);
        DefaultBilling.LogI("quantity : " + quantity);
        DefaultBilling.LogI("uid : " + uid);
        DefaultBilling.LogI("addtionalInfo : " + addtionalInfo);
        final StringBuffer postDataBuffer = new StringBuffer().append(FullStringbyLength(pid, 12)).append(FullStringbyLength(uid, 9)).append(FullStringbyLength("03", 2)).append(FullStringbyLength("03", 2)).append(FullStringbyLength(uid, 9));
        new Thread(new Runnable() {
            public void run() {
                LebiBilling.this.processReturnValue(LebiBilling.this.xmlParse(LebiBilling.this.sendToPost(LebiBilling.this.postDataBuilder("02", postDataBuffer.toString()), LebiBilling.this.isUseTestServer ? Utility.getString(1) : Utility.getString(0))));
            }
        }).start();
    }

    private synchronized Object processReturnValue(String data) {
        return processReturnValue(null, data);
    }

    private synchronized Object processReturnValue(String rqCode, String data) {
        Object obj;
        String response = i.a;
        try {
            CryptMessage receiverMessage = new CryptMessage();
            receiverMessage.setMessage(this.sysId, this.pd, data);
            response = receiverMessage.getProperty("pkt");
            DefaultBilling.LogI("decrypt response : " + response);
        } catch (MessageTimeoutException e) {
            e.printStackTrace();
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_DateCheckFailed, 1).show();
                }
            });
            response = GCMConstants.EXTRA_ERROR;
        } catch (MessageCryptException e2) {
            e2.printStackTrace();
            response = GCMConstants.EXTRA_ERROR;
        }
        String requestCode = i.a;
        String responseData = i.a;
        if (rqCode == null) {
            requestCode = response.substring(0, 2);
        } else {
            requestCode = rqCode;
            responseData = response;
        }
        DefaultBilling.LogI("processReturnValue requestCode : " + requestCode);
        JSONArray jSONArray;
        int jsonArrayLength;
        int i;
        if (requestCode.compareTo("07") == 0) {
            DefaultBilling.LogI("process request item price");
            DefaultBilling.LogI("processReturnValue responseData : " + responseData);
            try {
                jSONArray = new JSONArray(responseData);
                jsonArrayLength = jSONArray.length();
                if (jsonArrayLength != 0) {
                    itemList = new ItemList[jsonArrayLength];
                    for (i = 0; i < jsonArrayLength; i++) {
                        itemList[i] = new ItemList();
                        itemList[i].productID = jSONArray.getJSONObject(i).getString("billitemid");
                        itemList[i].formattedString = String.valueOf(jSONArray.getJSONObject(i).getInt("price"));
                        itemList[i].localizedTitle = jSONArray.getJSONObject(i).getString("itemname");
                        itemList[i].localizedDescription = jSONArray.getJSONObject(i).getString("itemdiv");
                        DefaultBilling.LogI("gamename : " + jSONArray.getJSONObject(i).getString("gamename"));
                        DefaultBilling.LogI("itemList[" + i + "] (itemname) : " + itemList[i].productID);
                        DefaultBilling.LogI("itemList[" + i + "] (price)    : " + itemList[i].formattedString);
                        DefaultBilling.LogI("itemList[" + i + "] (itemname) : " + itemList[i].localizedTitle);
                        DefaultBilling.LogI("itemList[" + i + "] (itemdiv)  : " + itemList[i].localizedDescription);
                    }
                    resultPostInApp(1, i.a, 0, i.a, i.a, itemList);
                    obj = "OK";
                } else {
                    obj = null;
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
                itemList = null;
                DefaultBilling.LogI("process request item price failed (jsonObject parse failed) : " + responseData);
                obj = null;
            }
        } else {
            if (requestCode.compareTo("04") == 0) {
                DefaultBilling.LogI("process request purchase check");
                try {
                    responseData = response.substring(2, 6);
                } catch (Exception e4) {
                    e4.printStackTrace();
                    responseData = "0000";
                }
                DefaultBilling.LogI("processReturnValue responseData : " + responseData);
                try {
                    if (responseData.compareTo("0001") == 0) {
                        resultPostInApp(2, this.propertyUtil.getProperty("pid"), Integer.valueOf(this.propertyUtil.getProperty("quantity")).intValue(), this.propertyUtil.getProperty(PeppermintConstant.JSON_KEY_UID), this.propertyUtil.getProperty("addtionalInfo"), makeSuccessStateValue(this.propertyUtil.getProperty("billingNum")));
                    } else {
                        if (responseData.compareTo("5001") != 0) {
                            if (responseData.compareTo("9998") != 0) {
                                if (responseData.compareTo("9999") != 0) {
                                    deleteBuyInfo();
                                    resultPostInApp(3, this.propertyUtil.getProperty("pid"), Integer.valueOf(this.propertyUtil.getProperty("quantity")).intValue(), this.propertyUtil.getProperty(PeppermintConstant.JSON_KEY_UID), this.propertyUtil.getProperty("addtionalInfo"), new ErrorStateValue(responseData, i.a, i.a));
                                }
                            }
                        }
                        DefaultBilling.LogI("process request purchase check failed : " + responseData);
                        resultPostInApp(3, this.propertyUtil.getProperty("pid"), Integer.valueOf(this.propertyUtil.getProperty("quantity")).intValue(), this.propertyUtil.getProperty(PeppermintConstant.JSON_KEY_UID), this.propertyUtil.getProperty("addtionalInfo"), new ErrorStateValue(responseData, i.a, i.a));
                    }
                } catch (Exception e42) {
                    e42.printStackTrace();
                    DefaultBilling.LogI("process request purchase check failed : " + responseData);
                }
                obj = null;
            } else {
                if (requestCode.compareTo("06") == 0) {
                    DefaultBilling.LogI("process request balance");
                    obj = Integer.valueOf(Integer.valueOf(response.substring(2, 11)).intValue() + Integer.valueOf(response.substring(11, 20)).intValue());
                } else {
                    if (requestCode.compareTo("08") == 0) {
                        DefaultBilling.LogI("process request restore item");
                        DefaultBilling.LogI("processReturnValue responseData : " + responseData);
                        obj = null;
                        try {
                            jSONArray = new JSONArray(responseData);
                            jsonArrayLength = jSONArray.length();
                            if (jsonArrayLength == 0) {
                                resultPostInApp(5, i.a, 0, this.restoreUid, i.a, makeSuccessStateValue(i.a));
                            } else {
                                obj = new String[jsonArrayLength];
                            }
                            for (i = 0; i < jsonArrayLength; i++) {
                                DefaultBilling.LogI("restore jsonArray[" + i + "] : " + jSONArray.getString(i));
                                obj[i] = jSONArray.getString(i);
                                resultPostInApp(5, jSONArray.getString(i), 1, this.restoreUid, i.a, makeSuccessStateValue(i.a));
                            }
                            this.restoreUid = i.a;
                        } catch (Exception e422) {
                            e422.printStackTrace();
                            DefaultBilling.LogI("process request restore item failed : " + responseData);
                            this.restoreUid = i.a;
                        }
                    } else {
                        if (requestCode.compareTo("02") == 0) {
                            int quantity;
                            DefaultBilling.LogI("process buy item");
                            String pid = this.propertyUtil.getProperty("pid");
                            String strQuantity = this.propertyUtil.getProperty("quantity");
                            if (strQuantity == null) {
                                quantity = 0;
                            } else {
                                if (strQuantity.equals(i.a)) {
                                    quantity = 0;
                                } else {
                                    quantity = Integer.valueOf(strQuantity).intValue();
                                }
                            }
                            String uid = this.propertyUtil.getProperty(PeppermintConstant.JSON_KEY_UID);
                            String addtionalInfo = this.propertyUtil.getProperty("addtionalInfo");
                            String subData = "0000";
                            try {
                                responseData = response.substring(2, 6);
                            } catch (Exception e4222) {
                                e4222.printStackTrace();
                                responseData = "0000";
                            }
                            try {
                                subData = data.substring(2, 6);
                            } catch (Exception e42222) {
                                e42222.printStackTrace();
                                subData = "0000";
                            }
                            if (responseData.compareTo("0001") == 0) {
                                int balance;
                                StringBuffer successMsg;
                                DefaultBilling.LogI("success check");
                                String resultCode = response.substring(2, 6);
                                String billingNum = response.substring(6, 15);
                                String remainLebi = response.substring(15, 24);
                                String bonusLebi = response.substring(24, 33);
                                DefaultBilling.LogI("resultCode : " + resultCode);
                                DefaultBilling.LogI("billingNum : " + billingNum);
                                DefaultBilling.LogI("remainLebi : " + remainLebi);
                                DefaultBilling.LogI("bonusLebi  : " + bonusLebi);
                                this.propertyUtil.setProperty("billingNum", billingNum);
                                this.propertyUtil.storeProperty(null);
                                try {
                                    balance = Integer.valueOf(remainLebi).intValue() + Integer.valueOf(bonusLebi).intValue();
                                } catch (Exception e422222) {
                                    e422222.printStackTrace();
                                    balance = -1;
                                }
                                if (balance != -1) {
                                    successMsg = new StringBuffer().append(this.Text_RemainLebi).append(" : ").append(balance).append(this.Text_Lebi);
                                } else {
                                    DefaultBilling.LogI("success check - balance error");
                                    successMsg = new StringBuffer().append(this.Text_Success);
                                }
                                final StringBuffer stringBuffer = successMsg;
                                activity.runOnUiThread(new Runnable() {
                                    public void run() {
                                        LebiBilling.purchaseSndDialog = LebiBilling.this.purchasePopup(LebiBilling.activity, 2, stringBuffer.toString());
                                        LebiBilling.purchaseSndDialog.show();
                                    }
                                });
                            } else {
                                if (subData.compareTo("OKAY") == 0) {
                                    DefaultBilling.LogI("cb BUY_SUCCESS");
                                    ProgressDialogDismiss();
                                    resultPostInApp(2, pid, quantity, uid, addtionalInfo, makeSuccessStateValue(this.propertyUtil.getProperty("billingNum")));
                                } else {
                                    if (subData.compareTo("CNCL") == 0) {
                                        DefaultBilling.LogI("user cancel");
                                        deleteBuyInfo();
                                        activity.runOnUiThread(new Runnable() {
                                            public void run() {
                                                Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_PurchaseCanceled, 0).show();
                                            }
                                        });
                                        ProgressDialogDismiss();
                                        resultPostInApp(4, pid, quantity, uid, addtionalInfo, "User Cancel.");
                                    } else {
                                        String errorCode;
                                        if (subData.compareTo("FAIL") == 0) {
                                            errorCode = "Error";
                                            try {
                                                errorCode = response.substring(2, 6);
                                            } catch (Exception e4222222) {
                                                e4222222.printStackTrace();
                                                errorCode = "Error";
                                            }
                                            DefaultBilling.LogI("error code : " + errorCode);
                                            deleteBuyInfo();
                                            ProgressDialogDismiss();
                                            resultPostInApp(3, pid, quantity, uid, addtionalInfo, new ErrorStateValue(errorCode, i.a, i.a));
                                        } else {
                                            errorCode = this.Text_Error;
                                            DefaultBilling.LogI("error code : " + responseData);
                                            final StringBuffer failedMsg = new StringBuffer().append(this.Popup_FailedMsg).append(" Code : ").append(this.Text_Error);
                                            activity.runOnUiThread(new Runnable() {
                                                public void run() {
                                                    LebiBilling.purchaseTrdDialog = LebiBilling.this.purchasePopup(LebiBilling.activity, 3, failedMsg.toString());
                                                    LebiBilling.purchaseTrdDialog.show();
                                                }
                                            });
                                        }
                                    }
                                }
                            }
                            obj = null;
                        } else {
                            obj = null;
                        }
                    }
                }
            }
        }
        return obj;
    }

    private void deleteBuyInfo() {
        DefaultBilling.LogI("deleteBuyInfo");
        this.propertyUtil.setProperty("pid", i.a);
        this.propertyUtil.setProperty("quantity", a.d);
        this.propertyUtil.setProperty(PeppermintConstant.JSON_KEY_UID, i.a);
        this.propertyUtil.setProperty("addtionalInfo", i.a);
        this.propertyUtil.setProperty("billingNum", i.a);
        this.propertyUtil.storeProperty(null);
        isBilling = false;
    }

    private Dialog purchasePopup(Context context, int kind, String msg) {
        return purchasePopup(context, kind, msg, 0, null);
    }

    private Dialog purchasePopup(Context context, int kind, String msg, final int price, final String uid) {
        String title = "Purchase";
        switch (kind) {
            case o.a /*1*/:
                title = this.Popup_FstTitle;
                break;
            case o.b /*2*/:
                title = this.Popup_SuccessTitle;
                break;
            case o.c /*3*/:
                title = this.Popup_FailedTitle;
                break;
            case o.d /*4*/:
                title = this.Popup_ChargeTitle;
                break;
        }
        Builder builder = new Builder(context);
        builder.setIcon(context.getApplicationInfo().icon);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setInverseBackgroundForced(true);
        builder.setCancelable(true);
        switch (kind) {
            case o.a /*1*/:
                builder.setPositiveButton(this.Text_Buy, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LebiBilling.this.requestPurchase();
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton(this.Text_Cancel, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LebiBilling.this.processReturnValue("02", "02CNCL");
                        dialog.dismiss();
                    }
                });
                builder.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        LebiBilling.this.processReturnValue("02", "02CNCL");
                        dialog.dismiss();
                    }
                });
                break;
            case o.b /*2*/:
                builder.setPositiveButton(this.Text_Ok, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LebiBilling.this.processReturnValue("02", "02OKAY");
                        dialog.dismiss();
                    }
                });
                builder.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        LebiBilling.this.processReturnValue("02", "02OKAY");
                        dialog.dismiss();
                    }
                });
                break;
            case o.c /*3*/:
                builder.setPositiveButton(this.Text_Ok, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LebiBilling.this.processReturnValue("02", "02FAIL");
                        dialog.dismiss();
                    }
                });
                builder.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        LebiBilling.this.processReturnValue("02", "02FAIL");
                        dialog.dismiss();
                    }
                });
                break;
            case o.d /*4*/:
                builder.setPositiveButton(this.Text_Ok, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String vid;
                        Object getVid = LebiBilling.this.getVID();
                        if (JSONObject.NULL.equals(getVid)) {
                            vid = null;
                        } else {
                            vid = (String) getVid;
                        }
                        Activity access$0 = LebiBilling.activity;
                        final String str = uid;
                        final int i = price;
                        access$0.runOnUiThread(new Runnable() {
                            public void run() {
                                LebiBilling.billingDialog = LebiBilling.this.onCreateBillingDialog(LebiBilling.this.createBillingView(LebiBilling.this.isUseTestServer ? Utility.getString(3) : Utility.getString(2), "uid=" + str + "&appid=" + LebiBilling.appid + (TextUtils.isEmpty(vid) ? i.a : "&vid=" + LebiBilling.this.getVID())), i);
                            }
                        });
                        dialog.dismiss();
                    }
                });
                builder.setNeutralButton(this.Text_Cancel, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        LebiBilling.this.processReturnValue("02", "02CNCL");
                        dialog.dismiss();
                    }
                });
                builder.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        LebiBilling.this.processReturnValue("02", "02CNCL");
                        dialog.dismiss();
                    }
                });
                break;
            default:
                builder.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        LebiBilling.this.processReturnValue("02", "02CNCL");
                        dialog.dismiss();
                    }
                });
                break;
        }
        return builder.create();
    }

    private Dialog onCreateBillingDialog(View customizeView, final int itemPrice) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(1);
        dialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                int quantity;
                Toast.makeText(LebiBilling.activity, LebiBilling.this.Toast_BillingViewClose, 0).show();
                final String pid = LebiBilling.this.propertyUtil.getProperty("pid");
                String strQuantity = LebiBilling.this.propertyUtil.getProperty("quantity");
                if (strQuantity == null) {
                    quantity = 0;
                } else if (strQuantity.equals(i.a)) {
                    quantity = 0;
                } else {
                    quantity = Integer.valueOf(strQuantity).intValue();
                }
                final String uid = LebiBilling.this.propertyUtil.getProperty(PeppermintConstant.JSON_KEY_UID);
                final String addtionalInfo = LebiBilling.this.propertyUtil.getProperty("addtionalInfo");
                final int i = itemPrice;
                LebiBilling.balanceThread = new Thread(new Runnable() {
                    public void run() {
                        try {
                            LebiBilling.this.ProgressDialogShow(LebiBilling.this.Progress_BalanceCheckMessage);
                            if (i <= LebiBilling.this.iapRequestBalance(LebiBilling.this.FullStringbyLength(uid, 9))) {
                                LebiBilling.this.ProgressDialogDismiss();
                                LebiBilling.this.iapBuyItem(pid, quantity, uid, addtionalInfo);
                                return;
                            }
                            LebiBilling.this.deleteBuyInfo();
                            LebiBilling.this.ProgressDialogDismiss();
                            LebiBilling.this.resultPostInApp(4, pid, quantity, uid, addtionalInfo, "Charge Lebi Cancel.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                LebiBilling.balanceThread.start();
            }
        });
        LayoutParams params = new FrameLayout.LayoutParams(-1, -1);
        if (customizeView != null) {
            dialog.setContentView(customizeView, params);
        }
        return dialog;
    }

    @SuppressLint({"SetJavaScriptEnabled"})
    private View createBillingView(String strURL, String postData) {
        WebView webview = new WebView(activity);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.setInitialScale(0);
        webview.setPadding(0, 0, 0, 0);
        webview.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        webview.setWebViewClient(new WebViewCallBack());
        webview.getSettings().setJavaScriptEnabled(true);
        if (18 < VERSION.SDK_INT) {
            webview.getSettings().setCacheMode(2);
        }
        webview.postUrl(strURL, EncodingUtils.getBytes(postData, "UTF-8"));
        return webview;
    }

    private String xmlParse(String strXml) {
        DefaultBilling.LogI("Start Xml Parse");
        String str = "-1";
        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(new ByteArrayInputStream(strXml.getBytes("UTF-8")), "UTF-8");
            String currentTag = i.a;
            for (int parserEvent = parser.getEventType(); parserEvent != 1; parserEvent = parser.next()) {
                switch (parserEvent) {
                    case o.b /*2*/:
                        currentTag = parser.getName();
                        DefaultBilling.LogI("XmlParser - START_TAG : " + currentTag);
                        break;
                    case o.d /*4*/:
                        if (currentTag.compareTo(PeppermintConstant.JSON_KEY_RESULT) != 0) {
                            break;
                        }
                        str = parser.getText();
                        DefaultBilling.LogI("XmlParser - TEXT : " + str);
                        break;
                    default:
                        break;
                }
            }
            DefaultBilling.LogI("End Xml Parse");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    private List<NameValuePair> postDataBuilder(String RequestCode, String body) {
        StringBuffer postDataBuffer = new StringBuffer();
        postDataBuffer.append(RequestCode).append(body);
        DefaultBilling.LogI("postDataBuilder : " + postDataBuffer.toString());
        String ret = null;
        CryptMessage senderMessage = new CryptMessage();
        senderMessage.setProperty("pkt", postDataBuffer.toString());
        try {
            ret = senderMessage.getMessage(this.sysId, this.pd, 300);
        } catch (Exception e) {
            e.printStackTrace();
        }
        DefaultBilling.LogI("encrypt postdata : " + ret);
        List<NameValuePair> postData = new ArrayList();
        postData.add(new BasicNameValuePair("value", ret));
        return postData;
    }

    private String sendToPost(List<NameValuePair> postData, String postUrl) {
        try {
            DefaultBilling.LogI("postUrl : " + postUrl);
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
            HttpConnectionParams.setSoTimeout(httpParameters, 10000);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(postUrl);
            httpPost.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
            HttpResponse responsePOST = client.execute(httpPost);
            DefaultBilling.LogI("response StatusCode : " + responsePOST.getStatusLine().getStatusCode());
            HttpEntity responseEntity = responsePOST.getEntity();
            if (responseEntity == null) {
                return "-1";
            }
            String returnValue = EntityUtils.toString(responseEntity);
            DefaultBilling.LogI("sendToPost RESPONSE : " + returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            return "-2";
        }
    }

    private String FullStringbyLength(String srcStr, int nLength) {
        int nEmptyLength = nLength;
        String retStr = srcStr;
        if (srcStr != null) {
            nEmptyLength = nLength - srcStr.length();
            if (nEmptyLength < 0) {
                nEmptyLength = 0;
            }
        }
        while (nEmptyLength > 0) {
            retStr = new StringBuilder(a.d).append(retStr).toString();
            nEmptyLength--;
        }
        DefaultBilling.LogI("FullStringbyLength : src = " + srcStr + ", dst = " + retStr);
        return retStr;
    }

    private ProgressDialog onCreateProgressDialog(String msg) {
        ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setProgressStyle(0);
        pDialog.setMessage(msg);
        pDialog.setCancelable(false);
        return pDialog;
    }

    private synchronized void ProgressDialogShow(final String msg) {
        DefaultBilling.LogI("Show Dialog");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    if (LebiBilling.progressDialog != null) {
                        LebiBilling.progressDialog.dismiss();
                    }
                    LebiBilling.progressDialog = LebiBilling.this.onCreateProgressDialog(msg);
                    LebiBilling.progressDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private synchronized void ProgressDialogDismiss() {
        DefaultBilling.LogI("Dismiss Dialog");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    if (LebiBilling.progressDialog != null) {
                        LebiBilling.progressDialog.dismiss();
                    }
                    LebiBilling.progressDialog = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private boolean isBilling() {
        String savedAddtionalInfo = this.propertyUtil.getProperty("billingNum");
        if (savedAddtionalInfo == null) {
            DefaultBilling.LogI("isBilling : false");
            isBilling = false;
            return false;
        } else if (savedAddtionalInfo.equals(i.a)) {
            DefaultBilling.LogI("isBilling : false");
            isBilling = false;
            return false;
        } else {
            DefaultBilling.LogI("isBilling : true");
            isBilling = true;
            return true;
        }
    }

    private String[] makeSuccessStateValue(String data) {
        String[] ret = new String[41];
        ret[0] = String.valueOf(8);
        ret[1] = "Lebi";
        ret[12] = data;
        return ret;
    }

    private void setLanguage() {
        String country = this.moduleData.getCountry();
        String language = this.moduleData.getLanguage();
        DefaultBilling.LogI("Country is : " + country);
        DefaultBilling.LogI("Language is : " + language);
        if (TextUtils.equals("ko", language)) {
            this.Popup_FstTitle = "\uc2b9\uc778 & \uad6c\ub9e4";
            this.Popup_SuccessTitle = "\uc544\uc774\ud15c \uad6c\ub9e4 \uc131\uacf5";
            this.Popup_FailedTitle = "\uc544\uc774\ud15c \uad6c\ub9e4 \uc2e4\ud328";
            this.Popup_FailedMsg = "\uad6c\ub9e4\uc5d0 \uc2e4\ud328\ud558\uc600\uc2b5\ub2c8\ub2e4. \uc7a0\uc2dc \ud6c4\uc5d0 \ub2e4\uc2dc \uc2dc\ub3c4\ud574 \uc8fc\uc138\uc694.";
            this.Popup_ChargeTitle = "\ub7ec\ube44 \uc794\uc561\uc774 \ubd80\uc871\ud569\ub2c8\ub2e4";
            this.Popup_ChargeMsg = "\ub7ec\ube44 \uc794\uc561\uc774 \ubd80\uc871\ud569\ub2c8\ub2e4. \ub7ec\ube44 \ucda9\uc804 \uc0ac\uc774\ud2b8\ub85c \uac00\uc2dc\uaca0\uc2b5\ub2c8\uae4c?";
            this.Progress_Message = "\uad6c\ub9e4 \uc544\uc774\ud15c \ud655\uc778\uc911...";
            this.Progress_BalanceCheckMessage = "\ub7ec\ube44 \uc794\uc561 \uc870\ud68c\uc911...";
            this.Progress_WebViewLoading = "\ub85c\ub529 \uc911...";
            this.Toast_BillingViewClose = "\uacb0\uc81c \uc815\ubcf4\ub97c \ud655\uc778\ud558\uace0 \uc788\uc2b5\ub2c8\ub2e4...";
            this.Toast_ProgressStoreStart = "\uc774\uc804\uc5d0 \uc644\ub8cc\ud558\uc9c0 \ubabb\ud55c \uad6c\ub9e4\uacb0\uacfc\ub97c \uc870\ud68c\ud569\ub2c8\ub2e4.";
            this.Toast_HiveLoginRequired = "HIVE \ub85c\uadf8\uc778\uc774 \ud544\uc694\ud569\ub2c8\ub2e4.";
            this.Toast_BalanceCheckFailed = "\uc794\uc561 \ud655\uc778\uc5d0 \uc2e4\ud328\ud558\uc600\uc2b5\ub2c8\ub2e4. \uc7a0\uc2dc \ud6c4\uc5d0 \ub2e4\uc2dc \uc2dc\ub3c4\ud574 \uc8fc\uc138\uc694.";
            this.Toast_DateCheckFailed = "\ub0a0\uc9dc/\uc2dc\uac04 \uc744 \uc784\uc758\ub85c \uc870\uc815\ud558\uba74 \uc794\uc561 \uc870\ud68c \ubc0f \uad6c\ub9e4\uac00 \ubd88\uac00\ub2a5 \ud560 \uc218 \uc788\uc2b5\ub2c8\ub2e4.";
            this.Toast_PurchaseCanceled = "\uad6c\ub9e4\uac00 \ucde8\uc18c\ub418\uc5c8\uc2b5\ub2c8\ub2e4.";
            this.Text_CurrentLebi = "\ud604\uc7ac \ub7ec\ube44";
            this.Text_RemainLebi = "\ub0a8\uc740 \ub7ec\ube44";
            this.Text_Lebi = "\ub7ec\ube44";
            this.Text_Success = "\uc131\uacf5";
            this.Text_Error = "\uc5d0\ub7ec";
            this.Text_Buy = "\uad6c\ub9e4";
            this.Text_Cancel = "\ucde8\uc18c";
            this.Text_Ok = "\ud655\uc778";
        } else if (TextUtils.equals("en", language)) {
            this.Popup_FstTitle = "Accept & buy";
            this.Popup_SuccessTitle = "Success Item Purcahse";
            this.Popup_FailedTitle = "Failed Item Purcahse";
            this.Popup_FailedMsg = "Purchase failed. Please try again later.";
            this.Popup_ChargeTitle = "Not enough Lebi";
            this.Popup_ChargeMsg = "Not enough Lebi. Go to Lebi charging site?";
            this.Progress_Message = "Waiting for check buy item...";
            this.Progress_BalanceCheckMessage = "Waiting for check balance...";
            this.Progress_WebViewLoading = "Loading...";
            this.Toast_BillingViewClose = "Checking billing progress...";
            this.Toast_ProgressStoreStart = "Found previous progress. Please try again later.";
            this.Toast_HiveLoginRequired = "Hive login is required.";
            this.Toast_BalanceCheckFailed = "Balance check failed. Please try again later.";
            this.Toast_DateCheckFailed = "If you forcefully adjust date/time, you might be unable to check your balance or make a purchase.";
            this.Toast_PurchaseCanceled = "Purchase Canceled.";
            this.Text_CurrentLebi = "Current Lebi";
            this.Text_RemainLebi = "Remain Lebi";
            this.Text_Lebi = "Lebi";
            this.Text_Success = "Success";
            this.Text_Error = "Error";
            this.Text_Buy = "Buy";
            this.Text_Cancel = "Cancel";
            this.Text_Ok = "OK";
        } else if (TextUtils.equals("tw", country)) {
            this.Popup_FstTitle = "\u540c\u610f\u4e26\u4e14\u8cfc\u8cb7";
            this.Popup_SuccessTitle = "\u8cfc\u8cb7\u9053\u5177\u6210\u529f";
            this.Popup_FailedTitle = "\u8cfc\u8cb7\u9053\u5177\u5931\u6557";
            this.Popup_FailedMsg = "\u8cfc\u8cb7\u5931\u6557,\u8acb\u7a0d\u5f8c\u518d\u8a66.";
            this.Popup_ChargeTitle = "\u6a02\u5e63\u9918\u984d\u4e0d\u8db3";
            this.Popup_ChargeMsg = "\u6a02\u5e63\u9918\u984d\u4e0d\u8db3, \u8981\u7acb\u5373\u5145\u503c\u55ce\uff1f";
            this.Progress_Message = "\u4ea4\u6613\u9032\u884c\u4e2d...";
            this.Progress_BalanceCheckMessage = "\u7db2\u7d61\u9023\u63a5\u4e2d...";
            this.Progress_WebViewLoading = "\u52a0\u8f09\u4e2d...";
            this.Toast_BillingViewClose = "\u6b63\u5728\u78ba\u8a8d\u4ea4\u6613\u7d50\u679c...";
            this.Toast_ProgressStoreStart = "\u767c\u73fe\u5df2\u6709\u9032\u7a0b,\u8acb\u7a0d\u5f8c\u518d\u8a66.";
            this.Toast_HiveLoginRequired = "\u8acb\u767b\u9304Com2uS Hive.";
            this.Toast_BalanceCheckFailed = "\u67e5\u8a62\u9918\u984d\u5931\u6557,\u8acb\u7a0d\u5f8c\u518d\u8a66.";
            this.Toast_DateCheckFailed = "\u4efb\u610f\u8abf\u6574\u6642\u9593\u6216\u65e5\u671f,\u53ef\u80fd\u7121\u6cd5\u67e5\u770b\u9918\u984d\u6216\u7121\u6cd5\u9032\u884c\u8cfc\u8cb7";
            this.Toast_PurchaseCanceled = "\u8cfc\u8cb7\u5df2\u53d6\u6d88";
            this.Text_CurrentLebi = "\u7576\u524d\u6301\u6709\u6a02\u5e63";
            this.Text_RemainLebi = "\u5269\u9918\u6a02\u5e63";
            this.Text_Lebi = "\u6a02\u5e63";
            this.Text_Success = "\u6210\u529f";
            this.Text_Error = "\u5931\u6557";
            this.Text_Buy = "\u8cfc\u8cb7";
            this.Text_Cancel = "\u53d6\u6d88";
            this.Text_Ok = "\u78ba\u8a8d";
        } else {
            this.Popup_FstTitle = "\u540c\u610f\u5e76\u4e14\u8d2d\u4e70";
            this.Popup_SuccessTitle = "\u8d2d\u4e70\u9053\u5177\u6210\u529f";
            this.Popup_FailedTitle = "\u8d2d\u4e70\u9053\u5177\u5931\u8d25";
            this.Popup_FailedMsg = "\u8d2d\u4e70\u5931\u8d25,\u8bf7\u7a0d\u540e\u518d\u8bd5.";
            this.Popup_ChargeTitle = "\u4e50\u5e01\u4f59\u989d\u4e0d\u8db3";
            this.Popup_ChargeMsg = "\u4e50\u5e01\u4f59\u989d\u4e0d\u8db3, \u8981\u7acb\u5373\u5145\u503c\u5417\uff1f";
            this.Progress_Message = "\u4ea4\u6613\u8fdb\u884c\u4e2d...";
            this.Progress_BalanceCheckMessage = "\u7f51\u7edc\u8fde\u63a5\u4e2d...";
            this.Progress_WebViewLoading = "\u52a0\u8f7d\u4e2d...";
            this.Toast_BillingViewClose = "\u6b63\u5728\u786e\u8ba4\u4ea4\u6613\u7ed3\u679c...";
            this.Toast_ProgressStoreStart = "\u53d1\u73b0\u5df2\u6709\u8fdb\u7a0b,\u8bf7\u7a0d\u540e\u518d\u8bd5.";
            this.Toast_HiveLoginRequired = "\u8bf7\u767b\u5f55Com2uS Hive.";
            this.Toast_BalanceCheckFailed = "\u67e5\u8be2\u4f59\u989d\u5931\u8d25,\u8bf7\u7a0d\u540e\u518d\u8bd5.";
            this.Toast_DateCheckFailed = "\u4efb\u610f\u8c03\u6574\u65f6\u95f4\u6216\u65e5\u671f, \u53ef\u80fd\u65e0\u6cd5\u67e5\u770b\u4f59\u989d\u6216\u65e0\u6cd5\u8fdb\u884c\u8d2d\u4e70.";
            this.Toast_PurchaseCanceled = "\u8d2d\u4e70\u5df2\u53d6\u6d88";
            this.Text_CurrentLebi = "\u5f53\u524d\u6301\u6709\u4e50\u5e01";
            this.Text_RemainLebi = "\u5269\u4f59\u4e50\u5e01";
            this.Text_Lebi = "\u4e50\u5e01";
            this.Text_Success = "\u6210\u529f";
            this.Text_Error = "\u5931\u8d25";
            this.Text_Buy = "\u8d2d\u4e70";
            this.Text_Cancel = "\u53d6\u6d88";
            this.Text_Ok = "\u786e\u8ba4";
        }
    }
}
