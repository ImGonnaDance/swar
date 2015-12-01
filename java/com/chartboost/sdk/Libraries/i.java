package com.chartboost.sdk.Libraries;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class i {
    public static boolean a(JSONArray jSONArray, JSONArray jSONArray2) {
        if (jSONArray.length() != jSONArray2.length() && !b(jSONArray.toString(), jSONArray2.toString())) {
            return false;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            Object opt = jSONArray.opt(i);
            Object opt2 = jSONArray2.opt(i);
            if (!a(opt.getClass(), opt2.getClass()) && (!Number.class.isInstance(opt) || !Number.class.isInstance(opt2))) {
                return false;
            }
            if (opt instanceof JSONObject) {
                if (!a((JSONObject) opt, (JSONObject) opt2)) {
                    return false;
                }
            } else if (opt instanceof JSONArray) {
                if (!a((JSONArray) opt, (JSONArray) opt2)) {
                    return false;
                }
            } else if (!b(opt, opt2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(Object obj, Object obj2) {
        return obj == obj2;
    }

    public static boolean a(JSONObject jSONObject, JSONObject jSONObject2) {
        if (jSONObject.length() != jSONObject2.length()) {
            try {
                if (!b(jSONObject.toString(2), jSONObject2.toString(2))) {
                    return false;
                }
            } catch (JSONException e) {
                return false;
            }
        }
        JSONArray names = jSONObject.names();
        if (names == null && jSONObject2.names() == null) {
            return true;
        }
        for (int i = 0; i < names.length(); i++) {
            String optString = names.optString(i);
            Object opt = jSONObject.opt(optString);
            Object opt2 = jSONObject2.opt(optString);
            if (a(opt) && !a(opt2)) {
                return false;
            }
            if (!a(opt.getClass(), opt2.getClass()) && (!Number.class.isInstance(opt) || !Number.class.isInstance(opt2))) {
                return false;
            }
            if (opt instanceof JSONObject) {
                if (!a((JSONObject) opt, (JSONObject) opt2)) {
                    return false;
                }
            } else if (opt instanceof JSONArray) {
                if (!a((JSONArray) opt, (JSONArray) opt2)) {
                    return false;
                }
            } else if (!b(opt, opt2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(Object obj) {
        return (obj == null || obj == JSONObject.NULL) ? false : true;
    }

    private static BigDecimal a(Number number) {
        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        }
        if (number instanceof BigInteger) {
            return new BigDecimal((BigInteger) number);
        }
        if ((number instanceof Byte) || (number instanceof Short) || (number instanceof Integer) || (number instanceof Long)) {
            return new BigDecimal(number.longValue());
        }
        if ((number instanceof Float) || (number instanceof Double)) {
            return new BigDecimal(number.doubleValue());
        }
        try {
            return new BigDecimal(number.toString());
        } catch (Throwable e) {
            throw new RuntimeException("The given number (\"" + number + "\" of class " + number.getClass().getName() + ") does not have a parsable string representation", e);
        }
    }

    public static boolean b(Object obj, Object obj2) {
        if (obj == null || obj == JSONObject.NULL) {
            boolean z = obj2 == null || obj2 == JSONObject.NULL;
            return z;
        }
        if (Number.class.isInstance(obj) && Number.class.isInstance(obj2)) {
            try {
                if (a((Number) obj).compareTo(a((Number) obj2)) != 0) {
                    return false;
                }
                return true;
            } catch (RuntimeException e) {
            }
        }
        return obj.equals(obj2);
    }
}
