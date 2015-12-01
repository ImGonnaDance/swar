package jp.co.cyberz.fox.a.a;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.UUID;

public final class i {
    public static final String a = "";
    public static final String b = ",";
    public static final String c = "\\";
    public static final String d = "exists";

    private static String a(Double d) {
        return d == null ? a : d.toString();
    }

    private static String a(Double d, String str) {
        return d == null ? str : d.toString();
    }

    public static String a(Integer num) {
        return num == null ? a : num.toString();
    }

    private static String a(Integer num, String str) {
        return num == null ? str : num.toString();
    }

    private static String a(Long l) {
        return l == null ? a : l.toString();
    }

    private static String a(Long l, String str) {
        return l == null ? str : l.toString();
    }

    public static String a(String str, String str2) {
        return a(str) ? str2 : str;
    }

    private static String a(String str, String str2, String str3) {
        if (str3 == null) {
            str3 = a;
        }
        int length = str2.length();
        int length2 = str3.length();
        if (length != 0 && length2 != 0) {
            int i = 0;
            while (true) {
                i = str.indexOf(str2, i);
                if (i < 0) {
                    break;
                }
                str = str.substring(0, i) + str3 + str.substring(i + length, str.length());
                i += length2;
            }
        }
        return str;
    }

    public static boolean a(String str) {
        return str == null || str.length() == 0;
    }

    private static boolean a(String str, String[] strArr) {
        if (a(str) || strArr == null || strArr.length == 0) {
            return false;
        }
        int i = 0;
        while (i < strArr.length) {
            if (strArr[i].length() != 0 && str.indexOf(strArr[i]) != -1) {
                return true;
            }
            i++;
        }
        return false;
    }

    private static String[] a(Object obj, String str) {
        if (obj == null) {
            return new String[0];
        }
        String obj2 = obj.toString();
        if (obj2.length() == 0) {
            return new String[0];
        }
        ArrayList arrayList = new ArrayList();
        if (str.startsWith(c) && str.length() >= 2) {
            str = str.substring(1);
        }
        int length = obj2.length();
        int i = 0;
        while (i < length) {
            int indexOf = obj2.indexOf(str, i);
            if (indexOf < 0) {
                break;
            }
            arrayList.add(obj2.substring(i, indexOf));
            i = str.length() + indexOf;
        }
        arrayList.add(obj2.substring(i));
        String[] strArr = new String[arrayList.size()];
        length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            strArr[i2] = (String) arrayList.get(i2);
        }
        return strArr;
    }

    private static String b(String str, String str2) {
        return a(str) ? str : a(a(a(str, "\r\n", "\n"), "\r", "\n"), "\n", str2);
    }

    public static boolean b(String str) {
        if (a(str)) {
            return false;
        }
        try {
            UUID fromString = UUID.fromString(str);
            return fromString != null ? str.equals(fromString.toString()) : false;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean b(String str, String[] strArr) {
        if (a(str) || strArr == null || strArr.length == 0) {
            return false;
        }
        int i = 0;
        while (i < strArr.length) {
            if (strArr[i].length() != 0 && str.indexOf(strArr[i]) != -1) {
                return true;
            }
            i++;
        }
        return false;
    }

    private static String[] b(Object obj, String str) {
        if (obj == null) {
            return new String[0];
        }
        String obj2 = obj.toString();
        if (obj2.length() == 0) {
            return new String[0];
        }
        ArrayList arrayList = new ArrayList();
        if (str.startsWith(c) && str.length() >= 2) {
            str = str.substring(1);
        }
        int length = obj2.length();
        int i = 0;
        while (i < length) {
            int indexOf = obj2.indexOf(str, i);
            if (indexOf < 0) {
                break;
            }
            arrayList.add(obj2.substring(i, indexOf));
            i = str.length() + indexOf;
        }
        arrayList.add(obj2.substring(i));
        String[] strArr = new String[arrayList.size()];
        length = strArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            strArr[i2] = (String) arrayList.get(i2);
        }
        return strArr;
    }

    private static String c(String str) {
        return a(str, a);
    }

    private static String d(String str) {
        String str2 = null;
        try {
            if (!a(str)) {
                str2 = URLDecoder.decode(str, "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str2;
    }
}
