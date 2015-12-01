package com.wellbia.xigncode;

import android.app.Activity;
import com.wellbia.xigncode.XigncodeClientSystem.Callback;

public class XigncodeClient {
    private static XigncodeClient mInstance = new XigncodeClient();
    protected XigncodeClientSystem mXigncodeClient = null;

    private XigncodeClient() {
    }

    public static XigncodeClient getInstance() {
        return mInstance;
    }

    public int initialize(Activity activity, String str, String str2, Callback callback) {
        this.mXigncodeClient = new XigncodeClientSystem();
        int initialize = this.mXigncodeClient.initialize(activity, str, activity.getPackageName(), str2, callback);
        return initialize != 0 ? initialize : 0;
    }

    public void onPause() {
        this.mXigncodeClient.onPause();
    }

    public void onResume() {
        this.mXigncodeClient.onResume();
    }

    public void cleanup() {
        this.mXigncodeClient.cleanup();
    }

    public String getCookie() {
        return this.mXigncodeClient.getCookie();
    }

    public String getCookie2(String str) {
        return this.mXigncodeClient.getCookie2(str);
    }

    public void setUserInfo(String str) {
        this.mXigncodeClient.setUserInfo(str);
    }
}
