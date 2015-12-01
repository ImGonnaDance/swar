package jp.co.cyberz.fox.notify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import com.com2us.module.manager.ModuleConfig;
import com.google.android.gcm.GCMConstants;
import java.lang.reflect.Method;
import jp.co.dimage.android.f;

public class BaseNotifyReceiver extends BroadcastReceiver implements f {
    private static String c = "F.O.X Notify";
    private static final String d = "APPADFORCE_NOTIFY_RECEIVER";
    SharedPreferences a;
    Editor b;

    private int a(Context context, Intent intent, Object obj, String str) {
        Class loadClass = getClass().getClassLoader().loadClass(obj.toString());
        Object newInstance = loadClass.newInstance();
        Bundle extras = intent.getExtras();
        if (extras.getString("messageByFox") != null) {
            return 0;
        }
        Method declaredMethod;
        if (extras.getString(a.a) != null) {
            try {
                declaredMethod = loadClass.getDeclaredMethod("onMessage", new Class[]{Context.class, Intent.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(newInstance, new Object[]{context, intent});
            } catch (NoSuchMethodException e) {
                try {
                    loadClass.getMethod("onReceive", new Class[]{Context.class, Intent.class}).invoke(newInstance, new Object[]{context, intent});
                } catch (NoSuchMethodException e2) {
                    e2.printStackTrace();
                }
            }
            return 1;
        } else if (extras.getString(GCMConstants.EXTRA_REGISTRATION_ID) != null) {
            try {
                declaredMethod = loadClass.getDeclaredMethod("onRegistered", new Class[]{Context.class, String.class});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(newInstance, new Object[]{context, str});
            } catch (NoSuchMethodException e3) {
                try {
                    loadClass.getMethod("onReceive", new Class[]{Context.class, Intent.class}).invoke(newInstance, new Object[]{context, intent});
                } catch (NoSuchMethodException e22) {
                    e22.printStackTrace();
                }
            }
            return 1;
        } else {
            try {
                loadClass.getMethod("onReceive", new Class[]{Context.class, Intent.class}).invoke(newInstance, new Object[]{context, intent});
            } catch (NoSuchMethodException e222) {
                e222.printStackTrace();
            }
            return 1;
        }
    }

    private void a(Context context, Intent intent, String str) {
        try {
            Object obj = context.getPackageManager().getApplicationInfo(context.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE).metaData.get(d);
            if (obj != null && obj.toString().length() != 0) {
                a(context, intent, obj, str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(Context context, String str) {
        if (str == null) {
            Log.d(c, "skip store to preference");
        } else {
            a.b(context, str);
        }
    }

    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(GCMConstants.EXTRA_REGISTRATION_ID);
        if (stringExtra != null) {
            a(context, stringExtra);
            this.a = context.getSharedPreferences(a.b, 0);
            Editor edit = this.a.edit();
            edit.putString(a.e, stringExtra);
            edit.commit();
        }
        Bundle extras = intent.getExtras();
        if (extras.getString("messageByFox") != null) {
            a.d(context, extras.getString(a.a));
            String string = extras.getString(a.f);
            this.a = context.getSharedPreferences(a.b, 0);
            Editor edit2 = this.a.edit();
            edit2.putString(a.f, string);
            edit2.commit();
        }
        String string2 = extras.getString(a.g);
        if (string2 != null) {
            this.a = context.getSharedPreferences(a.b, 0);
            Editor edit3 = this.a.edit();
            edit3.putString(a.g, string2);
            edit3.commit();
        }
        try {
            Object obj = context.getPackageManager().getApplicationInfo(context.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE).metaData.get(d);
            if (obj != null && obj.toString().length() != 0) {
                a(context, intent, obj, stringExtra);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
