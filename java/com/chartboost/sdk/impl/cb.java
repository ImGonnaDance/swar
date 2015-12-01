package com.chartboost.sdk.impl;

import java.util.regex.Pattern;
import jp.co.cyberz.fox.a.a.i;

public class cb {
    private static Pattern a = Pattern.compile("\\s+", 40);

    public static class a extends RuntimeException {
        final String a;

        a(String str) {
            super(str);
            this.a = str;
        }

        public String toString() {
            return this.a;
        }
    }

    public static void a(int i, int i2) {
        if (i != i2) {
            throw new a(i.a + i + " != " + i2);
        }
    }
}
