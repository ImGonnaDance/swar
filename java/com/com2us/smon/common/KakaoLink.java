package com.com2us.smon.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import com.com2us.peppermint.PeppermintConstant;
import com.facebook.internal.NativeProtocol;
import com.google.android.gcm.GCMConstants;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jp.co.cyberz.fox.notify.a;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class KakaoLink {
    private static String KakaoLinkApiVersion = "2.0";
    private static Charset KakaoLinkCharset = Charset.forName("UTF-8");
    private static String KakaoLinkEncoding = KakaoLinkCharset.name();
    private static String KakaoLinkURLBaseString = "kakaolink://sendurl";
    private static KakaoLink kakaoLink = null;
    private Context context;
    private String params = getBaseKakaoLinkUrl();

    private KakaoLink(Context context) {
        this.context = context;
    }

    public static KakaoLink getLink(Context context) {
        if (kakaoLink != null) {
            return kakaoLink;
        }
        return new KakaoLink(context);
    }

    private void openKakaoLink(Activity activity, String params) {
        activity.startActivity(new Intent("android.intent.action.SEND", Uri.parse(params)));
    }

    public void openKakaoLink(Activity activity, String url, String message, String appId, String appVer, String appName, String encoding) {
        if (isEmptyString(url) || isEmptyString(message) || isEmptyString(appId) || isEmptyString(appVer) || isEmptyString(appName) || isEmptyString(encoding)) {
            throw new IllegalArgumentException();
        }
        try {
            if (KakaoLinkCharset.equals(Charset.forName(encoding))) {
                message = new String(message.getBytes(encoding), KakaoLinkEncoding);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.params = getBaseKakaoLinkUrl();
        appendParam(a.g, url);
        appendParam("msg", message);
        appendParam("apiver", KakaoLinkApiVersion);
        appendParam(PeppermintConstant.JSON_KEY_APP_ID, appId);
        appendParam("appver", appVer);
        appendParam("appname", appName);
        appendParam(PeppermintConstant.JSON_KEY_TYPE, "link");
        openKakaoLink(activity, this.params);
    }

    public void openKakaoAppLink(Activity activity, String url, String message, String appId, String appVer, String appName, String encoding, ArrayList<Map<String, String>> metaInfoArray) {
        if (isEmptyString(url) || isEmptyString(message) || isEmptyString(appId) || isEmptyString(appVer) || isEmptyString(appName) || isEmptyString(encoding)) {
            throw new IllegalArgumentException();
        }
        try {
            if (KakaoLinkCharset.equals(Charset.forName(encoding))) {
                message = new String(message.getBytes(encoding), KakaoLinkEncoding);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        this.params = getBaseKakaoLinkUrl();
        appendParam(a.g, url);
        appendParam("msg", message);
        appendParam("apiver", KakaoLinkApiVersion);
        appendParam(PeppermintConstant.JSON_KEY_APP_ID, appId);
        appendParam("appver", appVer);
        appendParam("appname", appName);
        appendParam(PeppermintConstant.JSON_KEY_TYPE, GCMConstants.EXTRA_APPLICATION_PENDING_INTENT);
        appendMetaInfo(metaInfoArray);
        openKakaoLink(activity, this.params);
    }

    public boolean isAvailableIntent() {
        List<ResolveInfo> list = this.context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.SEND", Uri.parse(KakaoLinkURLBaseString)), NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    private boolean isEmptyString(String str) {
        return str == null || str.trim().length() == 0;
    }

    private void appendParam(String name, String value) {
        try {
            this.params += name + "=" + URLEncoder.encode(value, KakaoLinkEncoding) + "&";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void appendMetaInfo(ArrayList<Map<String, String>> metaInfoArray) {
        this.params += "metainfo=";
        JSONObject obj = new JSONObject();
        JSONArray arr = new JSONArray();
        Iterator it = metaInfoArray.iterator();
        while (it.hasNext()) {
            Map<String, String> metaInfo = (Map) it.next();
            JSONObject metaObj = new JSONObject();
            for (String key : metaInfo.keySet()) {
                try {
                    metaObj.put(key, metaInfo.get(key));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            arr.put(metaObj);
        }
        obj.put("metainfo", arr);
        try {
            this.params += URLEncoder.encode(obj.toString(), KakaoLinkEncoding);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
    }

    private String getBaseKakaoLinkUrl() {
        return KakaoLinkURLBaseString + "?";
    }
}
