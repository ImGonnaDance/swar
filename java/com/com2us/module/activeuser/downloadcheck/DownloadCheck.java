package com.com2us.module.activeuser.downloadcheck;

import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserData;
import com.com2us.module.activeuser.ActiveUserData.DATA_INDEX;
import com.com2us.module.activeuser.ActiveUserModule;
import com.com2us.module.activeuser.ActiveUserNetwork.Received;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedDownloadData;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.util.Logger;

public class DownloadCheck implements ActiveUserModule {
    private Logger logger;

    public DownloadCheck(Logger logger) {
        this.logger = logger;
    }

    public boolean isExecutable() {
        String propVersion = ActiveUserProperties.getProperty(ActiveUserProperties.DOWNLOAD_CHECK_APP_VERSION_PROPERTY);
        String curVersion = ActiveUserData.get(DATA_INDEX.APP_VERSION);
        if (propVersion != null) {
            boolean isUpdatedVersion = false;
            try {
                isUpdatedVersion = propVersion.trim().equals(curVersion.trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (isUpdatedVersion) {
                this.logger.d("[DownloadCheck][isExecutable] false : current version is " + curVersion);
                return false;
            }
            this.logger.d("[DownloadCheck][isExecutable] true : updated app had been installed");
        } else {
            this.logger.d("[DownloadCheck][isExecutable] true : new app had been installed");
        }
        return true;
    }

    public void responseNetwork(Received data) {
        ReceivedDownloadData downloadData = (ReceivedDownloadData) data;
        this.logger.d("ReceivedDownloadData : " + downloadData.toString());
        if (downloadData.errno == 0) {
            ActiveUserProperties.setProperty(ActiveUserProperties.DOWNLOAD_CHECK_APP_VERSION_PROPERTY, ActiveUserData.get(DATA_INDEX.APP_VERSION));
            if (!TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.MAC_ADDR))) {
                ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_MAC_ADDR_PROPERTY, ActiveUserData.get(DATA_INDEX.MAC_ADDR));
            }
            if (!TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.LINE_NUMBER))) {
                ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_LINE_NUMBER_PROPERTY, ActiveUserData.get(DATA_INDEX.LINE_NUMBER));
            }
            if (!TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.DEVICE_ID))) {
                ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_DEVICE_ID_PROPERTY, ActiveUserData.get(DATA_INDEX.DEVICE_ID));
            }
            if (!TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.ANDROID_ID))) {
                ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_ANDROID_ID_PROPERTY, ActiveUserData.get(DATA_INDEX.ANDROID_ID));
            }
            if (!TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.OS_VERSION))) {
                ActiveUserProperties.setProperty(ActiveUserProperties.DIDCHECK_OS_VERSION_PROPERTY, ActiveUserData.get(DATA_INDEX.OS_VERSION));
            }
            if (TextUtils.isEmpty(ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY))) {
                ActiveUserProperties.setProperty(ActiveUserProperties.DID_PROPERTY, downloadData.did);
            }
            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LIMIT_NUM, String.valueOf(downloadData.session_max_num));
            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LIMIT_TIME, String.valueOf(downloadData.session_max_time));
        }
    }

    public void destroy() {
    }
}
