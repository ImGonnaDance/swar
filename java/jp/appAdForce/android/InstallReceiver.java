package jp.appAdForce.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import com.com2us.module.manager.ModuleConfig;
import java.lang.reflect.Method;
import jp.co.dimage.android.a;
import jp.co.dimage.android.f;

public class InstallReceiver extends BroadcastReceiver {
    private jp.co.dimage.android.InstallReceiver a = new jp.co.dimage.android.InstallReceiver();

    public InstallReceiver() {
        a.a("ADMAGE_DEBUG", "constructor InstallReceiver");
    }

    private void a(Context context, Intent intent) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE);
            Object obj = applicationInfo.metaData.get(f.A);
            if (obj == null || obj.toString().length() == 0) {
                obj = applicationInfo.metaData.get(f.L);
            }
            if (obj != null && obj.toString().length() != 0) {
                Class loadClass = getClass().getClassLoader().loadClass(obj.toString());
                Object newInstance = loadClass.newInstance();
                Method method = loadClass.getMethod("onReceive", new Class[]{Context.class, Intent.class});
                a.a("ADMAGE_DEBUG", "FORWARD INSTALL RECEIVER : " + obj.toString());
                method.invoke(newInstance, new Object[]{context, intent});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onReceive(Context context, Intent intent) {
        this.a.onReceive(context, intent);
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE);
            Object obj = applicationInfo.metaData.get(f.A);
            if (obj == null || obj.toString().length() == 0) {
                obj = applicationInfo.metaData.get(f.L);
            }
            if (obj != null && obj.toString().length() != 0) {
                Class loadClass = getClass().getClassLoader().loadClass(obj.toString());
                Object newInstance = loadClass.newInstance();
                Method method = loadClass.getMethod("onReceive", new Class[]{Context.class, Intent.class});
                a.a("ADMAGE_DEBUG", "FORWARD INSTALL RECEIVER : " + obj.toString());
                method.invoke(newInstance, new Object[]{context, intent});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
