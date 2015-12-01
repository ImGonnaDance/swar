package com.chartboost.sdk.impl;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import java.io.File;

public class ad {
    public static m a(Context context, z zVar) {
        File file = new File(context.getCacheDir(), "volley");
        String str = "volley/0";
        try {
            String packageName = context.getPackageName();
            str = new StringBuilder(String.valueOf(packageName)).append("/").append(context.getPackageManager().getPackageInfo(packageName, 0).versionCode).toString();
        } catch (NameNotFoundException e) {
        }
        if (zVar == null) {
            if (VERSION.SDK_INT >= 9) {
                zVar = new aa();
            } else {
                zVar = new x(AndroidHttpClient.newInstance(str));
            }
        }
        m mVar = new m(new w(file), new u(zVar));
        mVar.a();
        return mVar;
    }

    public static m a(Context context) {
        return a(context, null);
    }
}
