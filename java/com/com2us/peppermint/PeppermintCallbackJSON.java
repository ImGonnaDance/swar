package com.com2us.peppermint;

import com.facebook.internal.NativeProtocol;
import org.json.JSONException;
import org.json.JSONObject;

public class PeppermintCallbackJSON {
    public JSONObject getJSON(String str, int i, String str2) {
        JSONObject dictionary = new Dictionary();
        try {
            dictionary.put(PeppermintConstant.JSON_KEY_TYPE, str);
            dictionary.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, Integer.parseInt(new StringBuilder(String.valueOf(i)).toString()));
            dictionary.put(PeppermintConstant.JSON_KEY_ERROR_MSG, str2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    public JSONObject getJSON(String str, Dictionary dictionary) {
        try {
            dictionary.put(PeppermintConstant.JSON_KEY_TYPE, str);
            dictionary.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, dictionary.has(NativeProtocol.BRIDGE_ARG_ERROR_CODE) ? Integer.parseInt((String) dictionary.get(NativeProtocol.BRIDGE_ARG_ERROR_CODE)) : 0);
            Object obj = dictionary.has(PeppermintConstant.JSON_KEY_ERROR_MSG) ? (String) dictionary.get(PeppermintConstant.JSON_KEY_ERROR_MSG) : null;
            if (obj != null) {
                dictionary.put(PeppermintConstant.JSON_KEY_ERROR_MSG, obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dictionary;
    }
}
