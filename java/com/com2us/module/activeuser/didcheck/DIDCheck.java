package com.com2us.module.activeuser.didcheck;

import android.content.Context;
import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserData;
import com.com2us.module.activeuser.ActiveUserData.DATA_INDEX;
import com.com2us.module.activeuser.ActiveUserModule;
import com.com2us.module.activeuser.ActiveUserNetwork.Received;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedDIDData;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.util.Logger;
import jp.co.cyberz.fox.a.a.i;

public class DIDCheck implements ActiveUserModule {
    private Context context = null;
    private Logger logger = null;

    public DIDCheck(Context context, Logger logger) {
        this.context = context;
        this.logger = logger;
        this.logger.d("[DIDCheck] initialize");
    }

    public boolean isExecutable() {
        if (!isEquals(ActiveUserData.get(DATA_INDEX.MAC_ADDR), ActiveUserProperties.getProperty(ActiveUserProperties.DIDCHECK_MAC_ADDR_PROPERTY)) || !isEquals(ActiveUserData.get(DATA_INDEX.LINE_NUMBER), ActiveUserProperties.getProperty(ActiveUserProperties.DIDCHECK_LINE_NUMBER_PROPERTY)) || !isEquals(ActiveUserData.get(DATA_INDEX.DEVICE_ID), ActiveUserProperties.getProperty(ActiveUserProperties.DIDCHECK_DEVICE_ID_PROPERTY)) || !isEquals(ActiveUserData.get(DATA_INDEX.ANDROID_ID), ActiveUserProperties.getProperty(ActiveUserProperties.DIDCHECK_ANDROID_ID_PROPERTY)) || !isEquals(ActiveUserData.get(DATA_INDEX.OS_VERSION), ActiveUserProperties.getProperty(ActiveUserProperties.DIDCHECK_OS_VERSION_PROPERTY)) || TextUtils.isEmpty(ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY))) {
            return true;
        }
        this.logger.d("[DIDCheck] isExecutable false");
        return false;
    }

    public void responseNetwork(Received data) {
        ReceivedDIDData didData = (ReceivedDIDData) data;
        if (didData.errno == 0) {
            ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_MAC_ADDR_PROPERTY, TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.MAC_ADDR)) ? i.a : ActiveUserData.get(DATA_INDEX.MAC_ADDR));
            ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_LINE_NUMBER_PROPERTY, TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.LINE_NUMBER)) ? i.a : ActiveUserData.get(DATA_INDEX.LINE_NUMBER));
            ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_DEVICE_ID_PROPERTY, TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.DEVICE_ID)) ? i.a : ActiveUserData.get(DATA_INDEX.DEVICE_ID));
            ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_ANDROID_ID_PROPERTY, TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.ANDROID_ID)) ? i.a : ActiveUserData.get(DATA_INDEX.ANDROID_ID));
            ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_OS_VERSION_PROPERTY, TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.OS_VERSION)) ? i.a : ActiveUserData.get(DATA_INDEX.OS_VERSION));
            if (!TextUtils.isEmpty(didData.did) && TextUtils.isEmpty(ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY))) {
                ActiveUserProperties.setProperty(ActiveUserProperties.DID_PROPERTY, didData.did);
            }
            ActiveUserProperties.storeProperties(this.context);
        }
    }

    public void destroy() {
    }

    private boolean isEquals(String a, String b) {
        if (TextUtils.isEmpty(a)) {
            a = i.a;
        }
        if (TextUtils.isEmpty(b)) {
            b = i.a;
        }
        if (TextUtils.equals(a, b)) {
            return true;
        }
        return false;
    }
}
