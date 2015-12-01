package com.com2us.peppermint;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

public class Dictionary extends JSONObject {
    private static Dictionary a(String str) {
        Dictionary dictionary = new Dictionary();
        if (str != null) {
            for (String split : str.split("&")) {
                String[] split2 = split.split("=", 2);
                if (split2.length == 2) {
                    try {
                        try {
                            try {
                                dictionary.put(URLDecoder.decode(split2[0], "UTF-8"), URLDecoder.decode(split2[1], "UTF-8"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (UnsupportedEncodingException e2) {
                            e2.printStackTrace();
                        }
                    } catch (UnsupportedEncodingException e22) {
                        e22.printStackTrace();
                    }
                }
            }
        }
        return dictionary;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static Dictionary queryDictionary(String str) {
        try {
            String query = new URL(str.replace(PeppermintURL.PEPPERMINT_SCHEME, WebClient.INTENT_PROTOCOL_START_HTTP)).getQuery();
            return (query == null || query.length() == 0) ? null : a(query);
        } catch (MalformedURLException e) {
            return new Dictionary();
        }
    }
}
