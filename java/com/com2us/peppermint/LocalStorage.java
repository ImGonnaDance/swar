package com.com2us.peppermint;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.com2us.peppermint.util.PeppermintEncryption;
import java.util.HashMap;
import jp.co.cyberz.fox.a.a.i;

public class LocalStorage {
    public static String HUB_SharedPreferences = "HUB_SharedPreferences";
    private static String a = null;
    private static boolean f2a = false;

    private static String a(Context context) {
        return a;
    }

    public static String getDataValueWithKey(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HUB_SharedPreferences, 0);
        try {
            return f2a ? new PeppermintEncryption().decryptDES(a(context), sharedPreferences.getString(str, i.a), false) : sharedPreferences.getString(str, i.a);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean getEncrytion() {
        return f2a;
    }

    public static HashMap<String, String> getHashDataValueWithKey(Context context, String... strArr) {
        HashMap<String, String> hashMap = new HashMap();
        SharedPreferences sharedPreferences = context.getSharedPreferences(HUB_SharedPreferences, 0);
        int length = strArr.length;
        int i = 0;
        while (i < length) {
            String str = strArr[i];
            try {
                hashMap.put(str, f2a ? new PeppermintEncryption().decryptDES(a(context), sharedPreferences.getString(str, i.a), false) : sharedPreferences.getString(str, i.a));
                i++;
            } catch (Exception e) {
                return null;
            }
        }
        return hashMap;
    }

    public static boolean isKeysExist(Context context, String... strArr) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(HUB_SharedPreferences, 0);
        for (String contains : strArr) {
            if (!sharedPreferences.contains(contains)) {
                return false;
            }
        }
        return true;
    }

    public static boolean removeDataValueWithKey(Context context, String... strArr) {
        int i = 0;
        Editor edit = context.getSharedPreferences(HUB_SharedPreferences, 0).edit();
        int length = strArr.length;
        while (i < length) {
            edit.remove(strArr[i]);
            i++;
        }
        return edit.commit();
    }

    public static boolean setDataValueWithKey(Context context, String str, String str2) {
        Editor edit = context.getSharedPreferences(HUB_SharedPreferences, 0).edit();
        try {
            PeppermintEncryption peppermintEncryption = new PeppermintEncryption();
            if (f2a) {
                str2 = peppermintEncryption.encryptDES(a(context), str2, false);
            }
            edit.putString(str, str2);
            return edit.commit();
        } catch (Exception e) {
            return false;
        }
    }

    public static void setEncrytion(boolean z) {
        f2a = z;
    }

    public static void setEncrytion(boolean z, String str) {
        f2a = z;
        a = str;
    }

    public static boolean setHashValueWithKey(Context context, HashMap<String, String> hashMap) {
        Editor edit = context.getSharedPreferences(HUB_SharedPreferences, 0).edit();
        for (String str : hashMap.keySet()) {
            String encryptDES;
            PeppermintEncryption peppermintEncryption = new PeppermintEncryption();
            if (f2a) {
                encryptDES = peppermintEncryption.encryptDES(a(context), (String) hashMap.get(str), false);
            } else {
                try {
                    encryptDES = (String) hashMap.get(str);
                } catch (Exception e) {
                    return false;
                }
            }
            edit.putString(str, encryptDES);
        }
        return edit.commit();
    }
}
