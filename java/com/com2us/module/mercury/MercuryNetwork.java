package com.com2us.module.mercury;

import com.com2us.module.activeuser.Constants.Network;
import com.com2us.peppermint.PeppermintConstant;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MercuryNetwork {
    private String SERVER = null;

    public void setServer(String url) {
        this.SERVER = url;
    }

    public Object processNetworkTask(int mercuryShowType) {
        StringEntity entity;
        RuntimeException e;
        StringEntity stringEntity;
        Throwable th;
        IOException e2;
        Mercury.logger.d(Constants.TAG, "processNetwork");
        String responseStr = null;
        HttpPost httpPost = null;
        HttpClient client = null;
        HttpParams httpClientParams = new BasicHttpParams();
        HashMap<String, String> dataMap = new HashMap();
        dataMap.put(PeppermintConstant.JSON_KEY_APP_ID, MercuryData.get(1));
        dataMap.put(PeppermintConstant.JSON_KEY_DID, MercuryData.get(12));
        dataMap.put("mac", MercuryData.get(2));
        dataMap.put("lan", MercuryData.get(3));
        dataMap.put("con", MercuryData.get(4));
        dataMap.put("width", MercuryData.get(14));
        dataMap.put("height", MercuryData.get(15));
        dataMap.put("devicetype", MercuryData.get(5));
        dataMap.put("appver", MercuryData.get(7));
        dataMap.put("osver", MercuryData.get(6));
        dataMap.put("libver", Constants.Version);
        dataMap.put("act", MercuryData.get(16));
        dataMap.put("uidcheckmsg", MercuryData.get(17));
        String vid = MercuryData.get(18);
        if (vid == null) {
            dataMap.put("vid", "-703");
        } else {
            dataMap.put("vid", vid);
        }
        dataMap.put("additionalInfo", MercuryData.get(19));
        dataMap.put("mcc", MercuryData.get(20));
        dataMap.put("mnc", MercuryData.get(21));
        JSONObject jsonObj = new JSONObject(dataMap);
        try {
            jsonObj.put("showtype", mercuryShowType);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        String jsonStr = jsonObj.toString();
        if (jsonStr == null) {
            return null;
        }
        Mercury.logger.d(Constants.TAG, "processNetwork:" + jsonObj.toString());
        try {
            HttpPost httpPost2;
            HttpConnectionParams.setConnectionTimeout(httpClientParams, Network.TIMEOUT);
            HttpClient client2 = new DefaultHttpClient(httpClientParams);
            try {
                entity = new StringEntity(jsonStr, "UTF-8");
                try {
                    httpPost2 = new HttpPost(this.SERVER);
                } catch (RuntimeException e3) {
                    e = e3;
                    stringEntity = entity;
                    client = client2;
                    try {
                        Mercury.logger.d(Constants.TAG, "Http RuntimeException : " + e.toString());
                        httpPost.abort();
                        client.getConnectionManager().shutdown();
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        client.getConnectionManager().shutdown();
                        throw th;
                    }
                } catch (IOException e4) {
                    e2 = e4;
                    stringEntity = entity;
                    client = client2;
                    Mercury.logger.d(Constants.TAG, "Http IOException : " + e2.toString());
                    client.getConnectionManager().shutdown();
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    stringEntity = entity;
                    client = client2;
                    client.getConnectionManager().shutdown();
                    throw th;
                }
            } catch (UnsupportedEncodingException e5) {
                try {
                    e5.printStackTrace();
                    client2.getConnectionManager().shutdown();
                    client = client2;
                    return null;
                } catch (RuntimeException e6) {
                    e = e6;
                    client = client2;
                    Mercury.logger.d(Constants.TAG, "Http RuntimeException : " + e.toString());
                    httpPost.abort();
                    client.getConnectionManager().shutdown();
                    return null;
                } catch (IOException e7) {
                    e2 = e7;
                    client = client2;
                    Mercury.logger.d(Constants.TAG, "Http IOException : " + e2.toString());
                    client.getConnectionManager().shutdown();
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    client = client2;
                    client.getConnectionManager().shutdown();
                    throw th;
                }
            }
            try {
                httpPost2.setEntity(entity);
                httpPost2.setHeader("Content-Type", "text/html");
                HttpResponse response = client2.execute(httpPost2);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        responseStr = EntityUtils.toString(responseEntity);
                        responseEntity.consumeContent();
                    }
                    client2.getConnectionManager().shutdown();
                    if (responseStr != null) {
                        client = client2;
                        httpPost = httpPost2;
                        return responseStr;
                    }
                    client = client2;
                    httpPost = httpPost2;
                    return null;
                }
                client2.getConnectionManager().shutdown();
                client = client2;
                httpPost = httpPost2;
                return null;
            } catch (RuntimeException e8) {
                e = e8;
                stringEntity = entity;
                client = client2;
                httpPost = httpPost2;
                Mercury.logger.d(Constants.TAG, "Http RuntimeException : " + e.toString());
                httpPost.abort();
                client.getConnectionManager().shutdown();
                return null;
            } catch (IOException e9) {
                e2 = e9;
                stringEntity = entity;
                client = client2;
                httpPost = httpPost2;
                Mercury.logger.d(Constants.TAG, "Http IOException : " + e2.toString());
                client.getConnectionManager().shutdown();
                return null;
            } catch (Throwable th5) {
                th = th5;
                stringEntity = entity;
                client = client2;
                httpPost = httpPost2;
                client.getConnectionManager().shutdown();
                throw th;
            }
        } catch (RuntimeException e10) {
            e = e10;
            Mercury.logger.d(Constants.TAG, "Http RuntimeException : " + e.toString());
            httpPost.abort();
            client.getConnectionManager().shutdown();
            return null;
        } catch (IOException e11) {
            e2 = e11;
            Mercury.logger.d(Constants.TAG, "Http IOException : " + e2.toString());
            client.getConnectionManager().shutdown();
            return null;
        }
    }
}
