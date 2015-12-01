package com.com2us.peppermint;

import jp.co.cyberz.fox.a.a.i;

public class PeppermintAuthToken {
    private String a = new String();
    private String b = new String();
    private String c = new String();
    private String d = new String();

    public PeppermintAuthToken(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    public static PeppermintAuthToken authTokenWithDictionary(Dictionary dictionary) {
        if (dictionary == null || !dictionary.has(PeppermintConstant.JSON_KEY_UID) || !dictionary.has(PeppermintConstant.JSON_KEY_DID) || !dictionary.has(PeppermintConstant.JSON_KEY_SESSION_KEY)) {
            return null;
        }
        try {
            return new PeppermintAuthToken(String.valueOf(dictionary.get(PeppermintConstant.JSON_KEY_UID)), String.valueOf(dictionary.get(PeppermintConstant.JSON_KEY_DID)), String.valueOf(dictionary.get(PeppermintConstant.JSON_KEY_SESSION_KEY)), dictionary.has(PeppermintConstant.JSON_KEY_AUTH_BY) ? dictionary.get(PeppermintConstant.JSON_KEY_AUTH_BY).toString() : i.a);
        } catch (Exception e) {
            return null;
        }
    }

    public String getAuthBy() {
        return this.d;
    }

    public Dictionary getDictionary() {
        try {
            Dictionary dictionary = new Dictionary();
            dictionary.put(PeppermintConstant.JSON_KEY_UID, this.a);
            dictionary.put(PeppermintConstant.JSON_KEY_DID, this.b);
            dictionary.put(PeppermintConstant.JSON_KEY_SESSION_KEY, this.c);
            dictionary.put(PeppermintConstant.JSON_KEY_AUTH_BY, this.d);
            return dictionary;
        } catch (Exception e) {
            return new Dictionary();
        }
    }

    public String getDid() {
        return this.b;
    }

    public String getSessionKey() {
        return this.c;
    }

    public String getUid() {
        return this.a;
    }
}
