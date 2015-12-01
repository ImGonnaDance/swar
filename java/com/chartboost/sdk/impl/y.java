package com.chartboost.sdk.impl;

import com.chartboost.sdk.impl.b.a;
import java.util.Map;
import jp.co.cyberz.fox.a.a.i;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

public class y {
    public static a a(i iVar) {
        long a;
        long j;
        long a2;
        Object obj = null;
        long j2 = 0;
        long currentTimeMillis = System.currentTimeMillis();
        Map map = iVar.c;
        String str = (String) map.get("Date");
        if (str != null) {
            a = a(str);
        } else {
            a = 0;
        }
        str = (String) map.get("Cache-Control");
        if (str != null) {
            String[] split = str.split(i.b);
            long j3 = 0;
            for (String trim : split) {
                String trim2 = trim2.trim();
                if (trim2.equals("no-cache") || trim2.equals("no-store")) {
                    return null;
                }
                if (trim2.startsWith("max-age=")) {
                    try {
                        j3 = Long.parseLong(trim2.substring(8));
                    } catch (Exception e) {
                    }
                } else if (trim2.equals("must-revalidate") || trim2.equals("proxy-revalidate")) {
                    j3 = 0;
                }
            }
            j = j3;
            obj = 1;
        } else {
            j = 0;
        }
        str = (String) map.get("Expires");
        if (str != null) {
            a2 = a(str);
        } else {
            a2 = 0;
        }
        str = (String) map.get("ETag");
        if (obj != null) {
            j2 = (1000 * j) + currentTimeMillis;
        } else if (a > 0 && a2 >= a) {
            j2 = (a2 - a) + currentTimeMillis;
        }
        a aVar = new a();
        aVar.a = iVar.b;
        aVar.b = str;
        aVar.e = j2;
        aVar.d = aVar.e;
        aVar.c = a;
        aVar.f = map;
        return aVar;
    }

    public static long a(String str) {
        try {
            return DateUtils.parseDate(str).getTime();
        } catch (DateParseException e) {
            return 0;
        }
    }
}
