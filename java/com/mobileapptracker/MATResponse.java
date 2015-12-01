package com.mobileapptracker;

import org.json.JSONObject;

public interface MATResponse {
    void didFailWithError(JSONObject jSONObject);

    void didReceiveDeeplink(String str);

    void didSucceedWithData(JSONObject jSONObject);

    void enqueuedActionWithRefId(String str);
}
