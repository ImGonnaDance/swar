package com.chartboost.sdk.Libraries;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class g {
    private static o a = new o();
    private static n b = new n();
    private static j c = new j();
    private static d d = new d();
    private static l e = new l();

    public static abstract class a {
        private String a = null;

        public abstract String a();

        public abstract boolean a(Object obj);

        public boolean a(Object obj, StringBuilder stringBuilder) {
            if (obj instanceof com.chartboost.sdk.Libraries.e.a) {
                obj = ((com.chartboost.sdk.Libraries.e.a) obj).n();
            }
            boolean a = a(obj);
            if (!a) {
                stringBuilder.append(this.a != null ? this.a : a());
            }
            return a;
        }
    }

    public static abstract class e extends a {
    }

    private static class b extends a {
        private b() {
        }

        public boolean a(Object obj) {
            return (obj instanceof List) || (obj instanceof JSONArray);
        }

        public String a() {
            return "object must be an array.";
        }
    }

    private static class c extends b {
        private a a;

        public c(a aVar) {
            super();
            this.a = aVar;
        }

        public boolean a(Object obj) {
            int i;
            if (obj instanceof List) {
                List list = (List) obj;
                for (i = 0; i < list.size(); i++) {
                    if (!this.a.a(list.get(i))) {
                        return false;
                    }
                }
                return true;
            } else if (!(obj instanceof JSONArray)) {
                return false;
            } else {
                JSONArray jSONArray = (JSONArray) obj;
                for (i = 0; i < jSONArray.length(); i++) {
                    if (!this.a.a(jSONArray.opt(i))) {
                        return false;
                    }
                }
                return true;
            }
        }

        public String a() {
            return "object must be an array of objects matching: <" + this.a.a() + ">";
        }
    }

    private static class d extends a {
        private d() {
        }

        public boolean a(Object obj) {
            return Boolean.class.isInstance(obj) || Boolean.TYPE.isInstance(obj);
        }

        public String a() {
            return "object must be a boolean.";
        }
    }

    private static class f extends a {
        protected k[] a;
        protected String b = null;

        public f(k[] kVarArr) {
            this.a = kVarArr;
        }

        public boolean a(Object obj) {
            a b;
            int i;
            String a;
            if (obj instanceof Map) {
                Map map = (Map) obj;
                for (Entry entry : map.entrySet()) {
                    if (!(entry.getKey() instanceof String)) {
                        this.b = "key '" + entry.getKey().toString() + "' is not a string";
                        return false;
                    }
                }
                if (this.a != null && this.a.length >= 1) {
                    for (i = 0; i < this.a.length; i++) {
                        a = this.a[i].a;
                        b = this.a[i].b;
                        if (map.containsKey(a)) {
                            if (!b.a(map.get(a))) {
                                this.b = "key '" + a + "' fails to match: <" + b.a() + ">";
                                return false;
                            }
                        } else if (!b.a(null)) {
                            this.b = "no key for required mapping '" + a + "' : <" + b.a() + ">";
                            return false;
                        }
                    }
                }
                return true;
            } else if (!(obj instanceof JSONObject)) {
                return false;
            } else {
                JSONObject jSONObject = (JSONObject) obj;
                if (this.a != null && this.a.length >= 1) {
                    i = 0;
                    while (i < this.a.length) {
                        a = this.a[i].a;
                        b = this.a[i].b;
                        try {
                            if (b.a(jSONObject.get(a))) {
                                i++;
                            } else {
                                this.b = "key '" + a + "' fails to match: <" + b.a() + ">";
                                return false;
                            }
                        } catch (JSONException e) {
                            if (!b.a(null)) {
                                this.b = "no key for required mapping '" + a + "' : <" + b.a() + ">";
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        }

        public String a() {
            if (this.b != null) {
                return this.b;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("object must contain the following key-value schema: {\n");
            for (int i = 0; i < this.a.length; i++) {
                stringBuilder.append("<");
                stringBuilder.append(this.a[i].a);
                stringBuilder.append(": [");
                stringBuilder.append(this.a[i].b.a());
                stringBuilder.append("]>");
                if (i < this.a.length - 1) {
                    stringBuilder.append(",\n");
                }
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    private static class g extends a {
        private Set<String> a;
        private f b;
        private String c = null;

        public g(f fVar) {
            this.b = fVar;
            this.a = new HashSet();
            for (k a : this.b.a) {
                this.a.add(a.a);
            }
        }

        public boolean a(Object obj) {
            if (!this.b.a(obj)) {
                this.c = this.b.b;
                return false;
            } else if (obj instanceof Map) {
                for (Object next : ((Map) obj).keySet()) {
                    if (!this.a.contains(next)) {
                        this.c = "key '" + next + "' is not allowed in this dictionary";
                        return false;
                    }
                }
                return true;
            } else if (obj instanceof JSONObject) {
                Iterator keys = ((JSONObject) obj).keys();
                while (keys.hasNext()) {
                    String str = (String) keys.next();
                    if (!this.a.contains(str)) {
                        this.c = "key '" + str + "' is not allowed in this dictionary";
                        return false;
                    }
                }
                return true;
            } else {
                this.c = "object must be a dictionary";
                return false;
            }
        }

        public String a() {
            if (this.c != null) {
                return this.c;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("object must EXACTLY MATCH the following key-value schema: {\n");
            for (int i = 0; i < this.b.a.length; i++) {
                stringBuilder.append("<");
                stringBuilder.append(this.b.a[i].a);
                stringBuilder.append(": [");
                stringBuilder.append(this.b.a[i].b.a());
                stringBuilder.append("]>");
                if (i < this.b.a.length - 1) {
                    stringBuilder.append(",\n");
                }
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

    private static class h extends a {
        private Object[] a;

        public h(Object[] objArr) {
            this.a = objArr;
        }

        public boolean a(Object obj) {
            for (Object obj2 : this.a) {
                if (obj == null) {
                    if (obj2 == null) {
                        return true;
                    }
                } else if (obj.equals(obj2)) {
                    return true;
                }
            }
            return false;
        }

        public String a() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("object must equal one of the following: ");
            for (int i = 0; i < this.a.length; i++) {
                stringBuilder.append("<");
                stringBuilder.append(this.a[i].toString());
                stringBuilder.append(">");
                if (i < this.a.length - 1) {
                    stringBuilder.append(", ");
                }
            }
            return stringBuilder.toString();
        }
    }

    private static class i extends a {
        private Class<?> a;

        public i(Class<?> cls) {
            this.a = cls;
        }

        public boolean a(Object obj) {
            return this.a.isInstance(obj);
        }

        public String a() {
            return "object must be an instance of " + this.a.getName() + ".";
        }
    }

    private static class j extends a {
        private j() {
        }

        public boolean a(Object obj) {
            return Integer.class.isInstance(obj) || Long.class.isInstance(obj) || Short.class.isInstance(obj) || Byte.class.isInstance(obj) || BigInteger.class.isInstance(obj) || Integer.TYPE.isInstance(obj) || Long.TYPE.isInstance(obj) || Short.TYPE.isInstance(obj) || Byte.TYPE.isInstance(obj);
        }

        public String a() {
            return "object must be a number w/o decimals (int, long, short, or byte).";
        }
    }

    public static class k {
        private String a;
        private a b;

        public k(String str, a aVar) {
            this.a = str;
            this.b = aVar;
        }
    }

    private static class l extends a {
        private l() {
        }

        public boolean a(Object obj) {
            return obj == null || obj == JSONObject.NULL;
        }

        public String a() {
            return "object must be null.";
        }
    }

    private static class m extends l {
        private a a;

        public m(a aVar) {
            super();
            this.a = aVar;
        }

        public boolean a(Object obj) {
            if (super.a(obj)) {
                return true;
            }
            return this.a.a(obj);
        }

        public String a() {
            return "object must be either null or " + this.a.a();
        }
    }

    private static class n extends a {
        private n() {
        }

        public boolean a(Object obj) {
            return (obj instanceof Number) || Integer.TYPE.isInstance(obj) || Long.TYPE.isInstance(obj) || Short.TYPE.isInstance(obj) || Float.TYPE.isInstance(obj) || Double.TYPE.isInstance(obj) || Byte.TYPE.isInstance(obj);
        }

        public String a() {
            return "object must be a number (primitive type or derived from Number).";
        }
    }

    private static class o extends i {
        public o() {
            super(String.class);
        }
    }

    private static class p extends a {
        protected String a = null;
        private a[] b;

        public p(a[] aVarArr) {
            this.b = aVarArr;
        }

        public boolean a(Object obj) {
            int i = 0;
            while (i < this.b.length) {
                if (this.b[i].a(obj)) {
                    i++;
                } else {
                    this.a = "object failed to match: <" + this.b[i].a() + ">";
                    return false;
                }
            }
            return true;
        }

        public String a() {
            if (this.a != null) {
                return this.a;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("object must match ALL of the following: ");
            for (int i = 0; i < this.b.length; i++) {
                stringBuilder.append("<");
                stringBuilder.append(this.b[i].a());
                stringBuilder.append(">");
                if (i < this.b.length - 1) {
                    stringBuilder.append(", ");
                }
            }
            return stringBuilder.toString();
        }
    }

    public static a a(Class<?> cls) {
        return new i(cls);
    }

    public static a a() {
        return a;
    }

    public static a b() {
        return b;
    }

    public static a c() {
        return d;
    }

    public static a a(a aVar) {
        return new m(aVar);
    }

    public static a b(a aVar) {
        return new c(aVar);
    }

    public static a a(a... aVarArr) {
        return new p(aVarArr);
    }

    public static a a(Object... objArr) {
        return new h(objArr);
    }

    public static k a(String str, a aVar) {
        return new k(str, aVar);
    }

    public static a a(k... kVarArr) {
        return new f(kVarArr);
    }

    public static a b(k... kVarArr) {
        return new g(new f(kVarArr));
    }

    public static boolean c(a aVar) {
        return (aVar instanceof f) || (aVar instanceof g);
    }
}
