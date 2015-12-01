package com.com2us.peppermint;

import com.com2us.peppermint.util.PeppermintLog;
import org.json.JSONObject;

public class PeppermintRequest {
    private static final int a = 30000;
    private String f7a;
    private String b;
    private String c = new String();

    public PeppermintRequest request(String str, String str2, JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("request url=" + str + " subPath=" + str2 + " jObj=" + jSONObject);
        this.f7a = new StringBuilder(String.valueOf(str)).append(str2).toString();
        this.b = str2;
        new Thread(new A(this, jSONObject, str.contains("qpyou") ? PeppermintURL.PEPPERMINT_QPYOU_COOKIE_URL : PeppermintURL.PEPPERMINT_COOKIE_URL, peppermintCallback)).start();
        return this;
    }
}
