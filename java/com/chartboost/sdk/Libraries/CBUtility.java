package com.chartboost.sdk.Libraries;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.Signature;
import android.os.Handler;
import android.os.Looper;
import android.view.Display;
import android.view.WindowManager;
import com.chartboost.sdk.b;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.security.auth.x500.X500Principal;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.o;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;

public final class CBUtility {
    private static Boolean a = null;
    private static final X500Principal b = new X500Principal("CN=Android Debug,O=Android,C=US");
    private static Handler c;

    private CBUtility() {
    }

    public static SharedPreferences a() {
        if (b.k() != null) {
            return b.k().getSharedPreferences("cbPrefs", 0);
        }
        throw new IllegalStateException("The context must be set through the Chartboost method onCreate() before modifying or accessing preferences.");
    }

    public static boolean b() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static boolean a(Context context) {
        if (a == null) {
            int equals;
            try {
                Signature[] signatureArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
                int i = 0;
                int i2 = 0;
                while (i < signatureArr.length) {
                    try {
                        equals = ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(signatureArr[i].toByteArray()))).getSubjectX500Principal().equals(b);
                        if (equals != 0) {
                            break;
                        }
                        i++;
                        i2 = equals;
                    } catch (Exception e) {
                        equals = i2;
                    }
                }
                equals = i2;
            } catch (Exception e2) {
                equals = 0;
            }
            a = Boolean.valueOf(equals | ((context.getApplicationInfo().flags & 2) != 0 ? 1 : 0));
        }
        return a.booleanValue();
    }

    public static String a(Map<String, Object> map) {
        if (map == null) {
            return i.a;
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (!map.keySet().isEmpty()) {
            stringBuilder.append("?");
        }
        for (String str : map.keySet()) {
            String str2;
            if (stringBuilder.length() > 1) {
                stringBuilder.append("&");
            }
            String obj = map.get(str2).toString();
            if (str2 != null) {
                str2 = URLEncoder.encode(str2, "UTF-8");
            } else {
                try {
                    str2 = i.a;
                } catch (Throwable e) {
                    throw new RuntimeException("This method requires UTF-8 encoding support", e);
                }
            }
            stringBuilder.append(str2);
            stringBuilder.append("=");
            stringBuilder.append(obj != null ? URLEncoder.encode(obj, "UTF-8") : i.a);
        }
        return stringBuilder.toString();
    }

    public static float b(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int a(int i, Context context) {
        return Math.round(((float) i) * b(context));
    }

    public static float a(float f, Context context) {
        return b(context) * f;
    }

    public static f c() {
        int i;
        Context k = b.k();
        Display defaultDisplay = ((WindowManager) k.getSystemService("window")).getDefaultDisplay();
        int i2 = k.getResources().getConfiguration().orientation;
        int rotation = defaultDisplay.getRotation();
        if (defaultDisplay.getWidth() == defaultDisplay.getHeight()) {
            Object obj = 3;
        } else if (defaultDisplay.getWidth() < defaultDisplay.getHeight()) {
            i = 1;
        } else {
            i = 2;
        }
        if (obj == 1) {
            obj = 1;
        } else if (obj == 2) {
            obj = null;
        } else {
            if (obj == 3) {
                if (i2 == 1) {
                    i = 1;
                } else if (i2 == 2) {
                    obj = null;
                }
            }
            i = 1;
        }
        if (!(rotation == 0 || rotation == 2)) {
            if (obj == null) {
                i = 1;
            } else {
                obj = null;
            }
        }
        if (obj != null) {
            switch (rotation) {
                case o.a /*1*/:
                    return f.g;
                case o.b /*2*/:
                    return f.PORTRAIT_REVERSE;
                case o.c /*3*/:
                    return f.h;
                default:
                    return f.PORTRAIT;
            }
        }
        switch (rotation) {
            case o.a /*1*/:
                return f.e;
            case o.b /*2*/:
                return f.LANDSCAPE_REVERSE;
            case o.c /*3*/:
                return f.f;
            default:
                return f.LANDSCAPE;
        }
    }

    public static void throwProguardError(Exception ex) {
        if (ex instanceof NoSuchMethodException) {
            throw new IllegalStateException("Chartboost library error! Have you used proguard on your application? Make sure to add the line '-keep class com.chartboost.sdk.** { *; }' to your proguard config file.");
        }
        throw new RuntimeException(ex);
    }

    public static void a(HttpResponse httpResponse) {
        if (httpResponse != null) {
            try {
                if (httpResponse.getEntity() != null) {
                    a(httpResponse.getEntity());
                }
            } catch (Throwable e) {
                CBLogging.b("CBUtility", "Exception raised calling consumeQuietly over http response", e);
            }
        }
    }

    public static void a(HttpEntity httpEntity) {
        try {
            httpEntity.consumeContent();
        } catch (Throwable e) {
            CBLogging.b("CBUtility", "Exception raised calling entity.consumeContent()", e);
        }
    }

    public static String d() {
        String str = "%s %s %s";
        Object[] objArr = new Object[3];
        objArr[0] = "Chartboost-Android-SDK";
        objArr[1] = b.a() == null ? i.a : b.a();
        objArr[2] = "5.0.3";
        return String.format(str, objArr);
    }

    public static Handler e() {
        return c;
    }

    public static void a(Handler handler) {
        c = handler;
    }
}
