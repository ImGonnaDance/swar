package com.chartboost.sdk.Libraries;

import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserProperties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

public class e {
    private static Map<Integer, a> a = Collections.synchronizedMap(new HashMap());
    private static Runnable b = new Runnable() {
        public void run() {
            e.a.clear();
        }
    };

    public static class a {
        public static a a = new a(null);
        private static JSONObject c = null;
        private static Map<String, Object> d = null;
        private static JSONArray e = null;
        private static List<?> f = null;
        private Object b;

        private a(Object obj) {
            this.b = obj;
        }

        public static a a() {
            return a(new JSONObject());
        }

        public a a(String str) {
            if (this.b instanceof JSONObject) {
                return a(((JSONObject) this.b).opt(str));
            }
            if (this.b instanceof Map) {
                return a(((Map) this.b).get(str));
            }
            return a;
        }

        public boolean b() {
            return this.b == null || this.b == JSONObject.NULL;
        }

        public boolean c() {
            return !b();
        }

        public boolean d() {
            return !TextUtils.isEmpty(h());
        }

        public boolean b(String str) {
            return a(str).b();
        }

        public boolean c(String str) {
            return a(str).c();
        }

        public JSONObject e() {
            if (this.b instanceof JSONObject) {
                return (JSONObject) this.b;
            }
            if (!(this.b instanceof Map)) {
                return null;
            }
            if (c == null) {
                c = e.a((Map) this.b);
            }
            return c;
        }

        public Map<String, Object> f() {
            if (this.b instanceof JSONObject) {
                if (d == null) {
                    d = e.a((JSONObject) this.b);
                }
                return d;
            } else if (this.b instanceof Map) {
                return (Map) this.b;
            } else {
                return null;
            }
        }

        public JSONArray g() {
            if (this.b instanceof JSONArray) {
                return (JSONArray) this.b;
            }
            if (!(this.b instanceof List)) {
                return null;
            }
            if (e == null) {
                e = e.a((List) this.b);
            }
            return e;
        }

        public String h() {
            if (b()) {
                return null;
            }
            return this.b instanceof String ? (String) this.b : this.b.toString();
        }

        public String d(String str) {
            return this.b instanceof String ? (String) this.b : str;
        }

        public double i() {
            return a(0.0d);
        }

        public double a(double d) {
            if (!(this.b instanceof String)) {
                return this.b instanceof Number ? ((Number) this.b).doubleValue() : d;
            } else {
                try {
                    return Double.parseDouble((String) this.b);
                } catch (NumberFormatException e) {
                    return d;
                }
            }
        }

        public float j() {
            return a(0.0f);
        }

        public float a(float f) {
            if (!(this.b instanceof String)) {
                return this.b instanceof Number ? ((Number) this.b).floatValue() : f;
            } else {
                try {
                    return Float.parseFloat((String) this.b);
                } catch (NumberFormatException e) {
                    return f;
                }
            }
        }

        public int k() {
            return a(0);
        }

        public int a(int i) {
            if (!(this.b instanceof String)) {
                return this.b instanceof Number ? ((Number) this.b).intValue() : i;
            } else {
                try {
                    return Integer.parseInt((String) this.b);
                } catch (NumberFormatException e) {
                    return i;
                }
            }
        }

        public long l() {
            return b(0);
        }

        public long b(int i) {
            if (!(this.b instanceof String)) {
                return this.b instanceof Number ? ((Number) this.b).longValue() : (long) i;
            } else {
                try {
                    return Long.parseLong((String) this.b);
                } catch (NumberFormatException e) {
                    return (long) i;
                }
            }
        }

        public boolean m() {
            return a(false);
        }

        public boolean a(boolean z) {
            return this.b instanceof Boolean ? ((Boolean) this.b).booleanValue() : z;
        }

        public boolean equals(Object other) {
            a a = a(other);
            if (b()) {
                return a.b();
            }
            if (e() != null && a.e() != null) {
                return i.a(e(), a.e());
            }
            if (g() != null && a.g() != null) {
                return i.a(g(), a.g());
            }
            if (this.b instanceof String) {
                return this.b.equals(a.h());
            }
            if (a.b instanceof String) {
                return a.b.equals(h());
            }
            return n().equals(a.n());
        }

        public Object n() {
            return this.b;
        }

        public static a a(Object obj) {
            if (obj instanceof a) {
                return (a) obj;
            }
            if (obj == null || obj == JSONObject.NULL) {
                return a;
            }
            a aVar = (a) e.a.get(Integer.valueOf(obj.hashCode()));
            if (aVar != null) {
                return aVar;
            }
            CBUtility.e().removeCallbacks(e.b);
            CBUtility.e().postDelayed(e.b, 1000);
            aVar = new a(obj);
            e.a.put(Integer.valueOf(obj.hashCode()), aVar);
            return aVar;
        }

        public int o() {
            if (this.b instanceof JSONArray) {
                return ((JSONArray) this.b).length();
            }
            if (this.b instanceof List) {
                return ((List) this.b).size();
            }
            return 1;
        }

        public a c(int i) {
            if (this.b instanceof JSONArray) {
                return a(((JSONArray) this.b).opt(i));
            }
            if (!(this.b instanceof List)) {
                return i != 0 ? a : this;
            } else {
                try {
                    return a(((List) this.b).get(i));
                } catch (IndexOutOfBoundsException e) {
                    return a;
                }
            }
        }

        public String e(String str) {
            return a(str).h();
        }

        public int f(String str) {
            return a(str).k();
        }

        public double g(String str) {
            return a(str).i();
        }

        public long h(String str) {
            return a(str).l();
        }

        public boolean i(String str) {
            return a(str).m();
        }

        public void a(String str, Object obj) {
            c = null;
            e = null;
            d = null;
            f = null;
            if (obj instanceof a) {
                obj = ((a) obj).n();
            }
            if (this.b instanceof JSONObject) {
                try {
                    ((JSONObject) this.b).put(str, obj);
                } catch (Throwable e) {
                    CBLogging.b(this, "Error updating balances dictionary.", e);
                }
            } else if (this.b instanceof Map) {
                try {
                    ((Map) this.b).put(str, obj);
                } catch (Throwable e2) {
                    CBLogging.b(this, "Error updating balances dictionary.", e2);
                }
            }
        }

        public static a j(String str) {
            if (str == null) {
                CBLogging.d("CBJSON", "null passed when creating new JSON object");
                return a;
            }
            if (str != null) {
                try {
                    if (str.trim().startsWith("[")) {
                        return a(new JSONArray(str));
                    }
                } catch (Throwable e) {
                    CBLogging.b("CBJSON", "error creating new json object", e);
                    return a;
                }
            }
            return a(new JSONObject(str));
        }

        public String toString() {
            if (e() != null) {
                return e().toString();
            }
            if (g() != null) {
                return g().toString();
            }
            if (this.b != null) {
                return this.b.toString();
            }
            return ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN;
        }
    }

    public static class b {
        private String a;
        private Object b;

        public b(String str, Object obj) {
            this.a = str;
            if (obj instanceof a) {
                this.b = ((a) obj).n();
            } else {
                this.b = obj;
            }
        }
    }

    public static a a(b... bVarArr) {
        a a = a.a();
        for (int i = 0; i < bVarArr.length; i++) {
            a.a(bVarArr[i].a, bVarArr[i].b);
        }
        return a;
    }

    public static b a(String str, Object obj) {
        return new b(str, obj);
    }

    public static List<?> a(JSONArray jSONArray) {
        if (jSONArray == null) {
            return null;
        }
        List<?> arrayList = new ArrayList();
        for (int i = 0; i < jSONArray.length(); i++) {
            try {
                Object obj = jSONArray.get(i);
                if (obj instanceof JSONObject) {
                    obj = a((JSONObject) obj);
                } else if (obj instanceof JSONArray) {
                    obj = a((JSONArray) obj);
                } else if (obj.equals(JSONObject.NULL)) {
                    obj = null;
                }
                arrayList.add(obj);
            } catch (Throwable e) {
                CBLogging.b("CBJSON", "error converting json", e);
            }
        }
        return arrayList;
    }

    public static Map<String, Object> a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        Map<String, Object> hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            try {
                String str = (String) keys.next();
                Object obj = jSONObject.get(str);
                if (obj instanceof JSONObject) {
                    obj = a((JSONObject) obj);
                } else if (obj instanceof JSONArray) {
                    obj = a((JSONArray) obj);
                } else if (obj.equals(JSONObject.NULL)) {
                    obj = null;
                }
                hashMap.put(str, obj);
            } catch (Throwable e) {
                CBLogging.b("CBJSON", "error converting json", e);
            }
        }
        return hashMap;
    }

    public static JSONArray a(List<?> list) {
        if (list == null) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            try {
                Object obj = list.get(i);
                if (obj instanceof Map) {
                    obj = a((Map) obj);
                } else if (obj instanceof List) {
                    obj = a((List) obj);
                } else if (obj == null) {
                    obj = JSONObject.NULL;
                }
                jSONArray.put(obj);
            } catch (Throwable e) {
                CBLogging.b("CBJSON", "error converting json", e);
            }
        }
        return jSONArray;
    }

    public static JSONObject a(Map<?, ?> map) {
        if (map == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : map.entrySet()) {
            String obj = entry.getKey().toString();
            Object value = entry.getValue();
            try {
                if (value instanceof Map) {
                    value = a((Map) value);
                } else if (value instanceof List) {
                    value = a((List) value);
                } else if (value == null) {
                    value = JSONObject.NULL;
                }
                jSONObject.put(obj, value);
            } catch (Throwable e) {
                CBLogging.b("CBJSON", "error converting json", e);
            }
        }
        return jSONObject;
    }
}
