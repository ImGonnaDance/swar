package com.com2us.peppermint;

import android.content.Context;
import com.com2us.peppermint.Peppermint.OnGetAsyncAdvertisingIDListener;
import com.com2us.peppermint.util.PeppermintLog;
import java.lang.reflect.Method;
import jp.co.cyberz.fox.a.a.i;

class d implements Runnable {
    private final /* synthetic */ OnGetAsyncAdvertisingIDListener a;
    final /* synthetic */ Peppermint f17a;

    d(Peppermint peppermint, OnGetAsyncAdvertisingIDListener onGetAsyncAdvertisingIDListener) {
        this.f17a = peppermint;
        this.a = onGetAsyncAdvertisingIDListener;
    }

    public void run() {
        String str = i.a;
        try {
            Method method = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class});
            Class cls = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
            str = (String) cls.getMethod("getId", new Class[0]).invoke(method.invoke(null, new Object[]{this.f17a.f9a.getApplicationContext()}), new Object[0]);
            PeppermintLog.i("system getAsyncAdvertisingID : " + str);
            this.a.onGetAsyncAdvertisingIDListener(str);
        } catch (Exception e) {
            PeppermintLog.i("getAsyncAdvertisingID failed.");
            this.a.onGetAsyncAdvertisingIDListener(null);
        }
    }
}
