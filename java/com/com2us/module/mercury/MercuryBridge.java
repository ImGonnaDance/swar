package com.com2us.module.mercury;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.peppermint.PeppermintConstant;
import com.google.android.gcm.GCMConstants;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.o;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class MercuryBridge {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$mercury$MercuryBridge$RecommendType;
    private static boolean isUsingStaging = false;
    private String URL = "http://m-mercury.qpyou.cn/api/messenger";
    private Activity activity = null;
    private Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private MercuryBridgeListner mercuryBridgeListner = null;
    private ProgressDialog progressDialog = null;
    private String stagingURL = "http://test.mercury.com2us.com/api/messenger";

    private interface SendToServerListner {
        void onResponse(int i, String str, String str2, String str3);
    }

    private enum RecommendType {
        RECOMMEND_SMS,
        RECOMMEND_EMAIL
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$mercury$MercuryBridge$RecommendType() {
        int[] iArr = $SWITCH_TABLE$com$com2us$module$mercury$MercuryBridge$RecommendType;
        if (iArr == null) {
            iArr = new int[RecommendType.values().length];
            try {
                iArr[RecommendType.RECOMMEND_EMAIL.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[RecommendType.RECOMMEND_SMS.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$com$com2us$module$mercury$MercuryBridge$RecommendType = iArr;
        }
        return iArr;
    }

    public MercuryBridge(Activity activity) {
        this.activity = activity;
    }

    public static void setUsingStaging(boolean _isUsingStaging) {
        isUsingStaging = _isUsingStaging;
    }

    public void setOnMercuryBridgeListner(MercuryBridgeListner mercuryBridgeListner) {
        this.mercuryBridgeListner = mercuryBridgeListner;
    }

    public void sendSMS(final String eventID) {
        this.logger.d("sendSMS : " + eventID);
        new Thread(new Runnable() {
            public void run() {
                try {
                    MercuryBridge.this.ProgressDialogShow(MercuryBridge.this.activity);
                    MercuryBridge.this.logger.d("sendSMS (onResponse) connectResult : " + MercuryBridge.this.sendToServer(eventID, RecommendType.RECOMMEND_SMS, new SendToServerListner() {
                        public void onResponse(int error, String errormsg, String title, String msg) {
                            MercuryBridge.this.logger.d("sendSMS (onResponse) error : " + error + ", errormsg : " + errormsg + ", title : " + title + ", msg : " + msg);
                            if (error == 0) {
                                Intent sendIntent = new Intent("android.intent.action.SEND");
                                sendIntent.setType("text/plain");
                                sendIntent.putExtra("android.intent.extra.TEXT", msg);
                                MercuryBridge.this.ProgressDialogDismiss(MercuryBridge.this.activity);
                                MercuryBridge.this.activity.startActivity(sendIntent);
                                MercuryBridge.this.postCallBack(2);
                                return;
                            }
                            MercuryBridge.this.postCallBack(-2);
                        }
                    }));
                } catch (Exception e) {
                    MercuryBridge.this.logger.d(e.toString());
                    MercuryBridge.this.postCallBack(-2);
                } finally {
                    MercuryBridge.this.ProgressDialogDismiss(MercuryBridge.this.activity);
                }
            }
        }).start();
    }

    public void sendEmail(final String eventID) {
        this.logger.d("sendEmail : " + eventID);
        new Thread(new Runnable() {
            public void run() {
                try {
                    MercuryBridge.this.ProgressDialogShow(MercuryBridge.this.activity);
                    MercuryBridge.this.logger.d("sendEmail (onResponse) connectResult : " + MercuryBridge.this.sendToServer(eventID, RecommendType.RECOMMEND_EMAIL, new SendToServerListner() {
                        public void onResponse(int error, String errormsg, String title, String msg) {
                            MercuryBridge.this.logger.d("sendEmail (onResponse) error : " + error + ", errormsg : " + errormsg + ", title : " + title + ", msg : " + msg);
                            if (error == 0) {
                                Intent emailIntent = new Intent("android.intent.action.SEND");
                                emailIntent.setType("plain/text");
                                emailIntent.putExtra("android.intent.extra.SUBJECT", title);
                                emailIntent.putExtra("android.intent.extra.TEXT", msg);
                                MercuryBridge.this.ProgressDialogDismiss(MercuryBridge.this.activity);
                                MercuryBridge.this.activity.startActivity(emailIntent);
                                MercuryBridge.this.postCallBack(3);
                                return;
                            }
                            MercuryBridge.this.postCallBack(-3);
                        }
                    }));
                } catch (Exception e) {
                    MercuryBridge.this.logger.d(e.toString());
                    MercuryBridge.this.postCallBack(-3);
                } finally {
                    MercuryBridge.this.ProgressDialogDismiss(MercuryBridge.this.activity);
                }
            }
        }).start();
    }

    private void postCallBack(int callBackType) {
        this.logger.d("postCallBack : " + callBackType);
        if (this.mercuryBridgeListner != null) {
            this.mercuryBridgeListner.onResult(callBackType);
        }
    }

    private boolean sendToServer(String eventID, RecommendType recommendType, SendToServerListner sendToServerListner) throws Exception {
        this.logger.d("sendToServer - eventID : " + eventID + ", recommendType : " + recommendType);
        try {
            String sendDataStr = i.a;
            String responseStr = i.a;
            String URL = i.a;
            JSONObject jsonObject = new JSONObject();
            if (((ConnectivityManager) this.activity.getSystemService("connectivity")).getActiveNetworkInfo() == null) {
                postCallBack(-5);
                return false;
            }
            this.logger.d("Network Active");
            try {
                if (isUsingStaging) {
                    URL = this.stagingURL;
                } else {
                    URL = this.URL;
                }
                jsonObject.put("eventid", eventID);
                jsonObject.put(PeppermintConstant.JSON_KEY_APP_ID, MercuryData.get(1));
                jsonObject.put(PeppermintConstant.JSON_KEY_DID, MercuryData.get(12));
                jsonObject.put(PeppermintConstant.JSON_KEY_UID, MercuryData.get(10));
                jsonObject.put("mac", MercuryData.get(2));
                jsonObject.put("imei", getUDID());
                jsonObject.put("devicetype", MercuryData.get(5));
                jsonObject.put("lan", MercuryData.get(3));
                jsonObject.put("con", MercuryData.get(4));
                jsonObject.put("osver", MercuryData.get(6));
                jsonObject.put("libver", Constants.Version);
                jsonObject.put("appver", MercuryData.get(7));
                switch ($SWITCH_TABLE$com$com2us$module$mercury$MercuryBridge$RecommendType()[recommendType.ordinal()]) {
                    case o.a /*1*/:
                        jsonObject.put(PeppermintConstant.JSON_KEY_TYPE, "sms");
                        break;
                    default:
                        jsonObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintConstant.JSON_KEY_EMAIL);
                        break;
                }
                sendDataStr = jsonObject.toString();
                this.logger.d("verifyString : " + sendDataStr);
                try {
                    HttpClient client = new DefaultHttpClient(new BasicHttpParams());
                    HttpPost httpPost = new HttpPost(URL);
                    httpPost.setHeader("Content-type", "text/html");
                    httpPost.setEntity(new StringEntity(sendDataStr, "UTF-8"));
                    HttpResponse response = client.execute(httpPost);
                    HttpEntity responseEntity = response.getEntity();
                    this.logger.d("sendToServer Response Status Code : " + response.getStatusLine().getStatusCode());
                    if (responseEntity != null) {
                        responseStr = EntityUtils.toString(responseEntity);
                        this.logger.d("responseStr : " + responseStr);
                        try {
                            JSONObject jSONObject = new JSONObject(responseStr);
                            int error = jSONObject.getInt(GCMConstants.EXTRA_ERROR);
                            String errormsg = jSONObject.optString("errormsg");
                            String title = jSONObject.optString("title");
                            String msg = jSONObject.optString("msg");
                            this.logger.d("connectResult : success");
                            sendToServerListner.onResponse(error, errormsg, title, msg);
                            return true;
                        } catch (Exception e) {
                            this.logger.d(e.toString());
                            this.logger.d("REQUEST To Server Failed");
                            postCallBack(-6);
                            return false;
                        }
                    }
                    this.logger.d(recommendType.name() + " To Server Failed");
                    postCallBack(-4);
                    return false;
                } catch (Exception e2) {
                    this.logger.d(e2.toString());
                    this.logger.d(recommendType.name() + " To Server Failed");
                    postCallBack(-4);
                    return false;
                }
            } catch (Exception e22) {
                this.logger.d(e22.toString());
                this.logger.d("create json data Failed");
                return false;
            }
        } catch (Exception e222) {
            this.logger.d(e222.toString());
            switch ($SWITCH_TABLE$com$com2us$module$mercury$MercuryBridge$RecommendType()[recommendType.ordinal()]) {
                case o.a /*1*/:
                    postCallBack(-2);
                    return false;
                default:
                    postCallBack(-3);
                    return false;
            }
        }
    }

    private String getUDID() {
        String UDID = i.a;
        try {
            TelephonyManager telephonyManager = (TelephonyManager) this.activity.getSystemService("phone");
            if (UDID != null && !UDID.equals("NULLERROR")) {
                return UDID;
            }
            UDID = telephonyManager.getDeviceId();
            if (UDID == null) {
                return "NULLERROR";
            }
            return UDID;
        } catch (Exception e) {
            this.logger.d(e.toString());
            return UDID;
        }
    }

    private ProgressDialog onCreateProgressDialog(Activity activity) {
        ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setProgressStyle(0);
        pDialog.setMessage(MercuryData.getProgressDialogText());
        pDialog.setCancelable(true);
        return pDialog;
    }

    private synchronized void ProgressDialogShow(final Activity activity) {
        this.logger.d("Show Dialog");
        if (this.progressDialog == null || !this.progressDialog.isShowing()) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        MercuryBridge.this.progressDialog = MercuryBridge.this.onCreateProgressDialog(activity);
                        MercuryBridge.this.progressDialog.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private synchronized void ProgressDialogDismiss(Activity activity) {
        this.logger.d("Dismiss Dialog");
        activity.runOnUiThread(new Runnable() {
            public void run() {
                try {
                    if (MercuryBridge.this.progressDialog != null) {
                        MercuryBridge.this.progressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
