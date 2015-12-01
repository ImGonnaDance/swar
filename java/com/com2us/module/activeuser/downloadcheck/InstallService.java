package com.com2us.module.activeuser.downloadcheck;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.com2us.module.manager.ModuleConfig;

public class InstallService extends IntentService {
    public static final String TAG = "ActiveUser";
    Context context;

    public InstallService() {
        super("ActiveUserIntentService");
    }

    public InstallService(String name) {
        super(name);
    }

    protected void onHandleIntent(Intent intent) {
        Context localContext = this.context != null ? this.context : getApplicationContext();
        sendReferrerToServer(localContext);
        try {
            Bundle bundle = localContext.getPackageManager().getReceiverInfo(new ComponentName(localContext, "com.com2us.module.activeuser.downloadcheck.InstallReceiver"), ModuleConfig.ACTIVEUSER_MODULE).metaData;
            if (bundle != null) {
                for (String metaDataName : bundle.keySet()) {
                    try {
                        ((BroadcastReceiver) Class.forName(bundle.getString(metaDataName)).newInstance()).onReceive(localContext, intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendReferrerToServer(android.content.Context r7) {
        /*
        r6 = this;
        com.com2us.module.activeuser.ActiveUserData.createOnInstalled(r7);
        r4 = "send_referrer";
        r5 = "N";
        com.com2us.module.activeuser.ActiveUserProperties.setProperty(r4, r5);	 Catch:{ Exception -> 0x0025 }
        r4 = "referrer";
        r3 = com.com2us.module.activeuser.ActiveUserNetwork.processNetworkTask(r4);	 Catch:{ Exception -> 0x0025 }
        if (r3 == 0) goto L_0x0021;
    L_0x0012:
        r0 = r3;
        r0 = (com.com2us.module.activeuser.ActiveUserNetwork.ReceivedReferrerData) r0;	 Catch:{ Exception -> 0x0025 }
        r1 = r0;
        r4 = r1.errno;	 Catch:{ Exception -> 0x0025 }
        if (r4 != 0) goto L_0x0021;
    L_0x001a:
        r4 = "send_referrer";
        r5 = "Y";
        com.com2us.module.activeuser.ActiveUserProperties.setProperty(r4, r5);	 Catch:{ Exception -> 0x0025 }
    L_0x0021:
        com.com2us.module.activeuser.ActiveUserProperties.storeProperties(r7);
    L_0x0024:
        return;
    L_0x0025:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x002d }
        com.com2us.module.activeuser.ActiveUserProperties.storeProperties(r7);
        goto L_0x0024;
    L_0x002d:
        r4 = move-exception;
        com.com2us.module.activeuser.ActiveUserProperties.storeProperties(r7);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.activeuser.downloadcheck.InstallService.sendReferrerToServer(android.content.Context):void");
    }
}
