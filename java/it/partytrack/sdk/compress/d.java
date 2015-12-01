package it.partytrack.sdk.compress;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public final class d {
    static float a;
    static int f109a;
    static Context f110a;
    static Boolean f111a;
    static Integer f112a;
    static String f113a;
    public static Map f114a = new HashMap();
    static boolean f115a = true;
    static int b = (TimeZone.getDefault().getOffset(System.currentTimeMillis()) / UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
    static String f116b = Build.MODEL;
    public static Map f117b = new HashMap();
    public static boolean f118b = false;
    static String c = VERSION.RELEASE;
    public static Map f119c = new HashMap();
    static boolean f120c = false;
    static String d = TimeZone.getDefault().getID();
    static String e = Locale.getDefault().getLanguage();
    static String f = Locale.getDefault().getCountry();
    static String g = null;
}
