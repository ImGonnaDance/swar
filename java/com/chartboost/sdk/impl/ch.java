package com.chartboost.sdk.impl;

import com.com2us.module.manager.ModuleConfig;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ch {
    private static boolean a = false;
    private static boolean b = false;
    static final Logger f = Logger.getLogger("org.bson.BSON");
    static de<List<co>> g = new de();
    static de<List<co>> h = new de();
    protected static Charset i = Charset.forName("UTF-8");
    static ThreadLocal<cj> j = new ThreadLocal<cj>() {
        protected /* synthetic */ Object initialValue() {
            return a();
        }

        protected cj a() {
            return new cm();
        }
    };
    static ThreadLocal<ci> k = new ThreadLocal<ci>() {
        protected /* synthetic */ Object initialValue() {
            return a();
        }

        protected ci a() {
            return new cl();
        }
    };

    private enum a {
        CANON_EQ(ModuleConfig.ACTIVEUSER_MODULE, 'c', "Pattern.CANON_EQ"),
        UNIX_LINES(1, 'd', "Pattern.UNIX_LINES"),
        GLOBAL(ModuleConfig.SOCIAL_MEDIA_MOUDLE, 'g', null),
        CASE_INSENSITIVE(2, 'i', null),
        MULTILINE(8, 'm', null),
        DOTALL(32, 's', "Pattern.DOTALL"),
        LITERAL(16, 't', "Pattern.LITERAL"),
        UNICODE_CASE(64, 'u', "Pattern.UNICODE_CASE"),
        COMMENTS(4, 'x', null);
        
        private static final Map<Character, a> m = null;
        public final int j;
        public final char k;
        public final String l;

        static {
            m = new HashMap();
            a[] values = values();
            int length = values.length;
            int i;
            while (i < length) {
                a aVar = values[i];
                m.put(Character.valueOf(aVar.k), aVar);
                i++;
            }
        }

        private a(int i, char c, String str) {
            this.j = i;
            this.k = c;
            this.l = str;
        }
    }

    public static String a(int i) {
        StringBuilder stringBuilder = new StringBuilder();
        int i2 = i;
        for (a aVar : a.values()) {
            if ((aVar.j & i2) > 0) {
                stringBuilder.append(aVar.k);
                i2 -= aVar.j;
            }
        }
        if (i2 <= 0) {
            return stringBuilder.toString();
        }
        throw new IllegalArgumentException("some flags could not be recognized.");
    }

    public static Object a(Object obj) {
        if (!(!a() || g.a() == 0 || obj == null)) {
            List<co> list = (List) g.a(obj.getClass());
            if (list != null) {
                for (co a : list) {
                    obj = a.a(obj);
                }
            }
        }
        return obj;
    }

    private static boolean a() {
        return a || b;
    }
}
