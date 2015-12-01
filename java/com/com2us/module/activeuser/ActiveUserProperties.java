package com.com2us.module.activeuser;

import android.content.Context;
import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserData.DATA_INDEX;
import com.com2us.module.activeuser.downloadcheck.InstallService;
import com.com2us.module.crypt.Crypt;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import jp.co.cyberz.fox.a.a.i;

public class ActiveUserProperties {
    protected static String ACTIVE_USER_PROPERTIES = "activeuser.props";
    public static final String AGREEMENT_CHECKED_LIST_PROPERTY = "agreement_checked_list";
    public static final String AGREEMENT_EX_SCHEME_DATA_PROPERTY = "agreement_ex_scheme_data";
    public static final String AGREEMENT_LOG_PROPERTY = "agreement_log_property";
    public static final String AGREEMENT_LOG_VALUE_COMPLETED = "agreement_log_completed";
    public static final String AGREEMENT_LOG_VALUE_SENDING = "agreement_log_sending";
    public static final String AGREEMENT_PRIVACY_PROPERTY = "userinfo_agree_privacy";
    public static final String AGREEMENT_PRIVACY_VALUE_AGREE = "AGREE";
    public static final String AGREEMENT_PRIVACY_VALUE_COMPLETED = "COMPLETED";
    public static final String AGREEMENT_REVIEW_URL_PROPERTY = "agreement_review_url";
    public static final String AGREEMENT_SMS_PROPERTY = "agreement_sms_property";
    public static final String AGREEMENT_SMS_VALUE_AGREE = "1";
    public static final String AGREEMENT_SMS_VALUE_DISAGREE = "0";
    public static final String AGREEMENT_SMS_VALUE_UNKNOWN = "null";
    public static final String AGREEMENT_VERSION_DATA_PROPERTY = "agreement_version_data";
    public static final String AGREEMENT_VERSION_PROPERTY = "agreement_version";
    public static final String AVC_PERIOD_PROPERTY = "appversioncheck_period";
    public static final String AVC_REQUESTED_TIME_PROPERTY = "appversioncheck_request_time";
    public static final String DIDCHECK_ANDROID_ID_PROPERTY = "didcheck_android_id_data";
    public static final String DIDCHECK_DEVICE_ID_PROPERTY = "didcheck_device_id_data";
    public static final String DIDCHECK_LINE_NUMBER_PROPERTY = "didcheck_line_number_data";
    public static final String DIDCHECK_MAC_ADDR_PROPERTY = "didcheck_mac_addr_data";
    public static final String DIDCHECK_OS_VERSION_PROPERTY = "didcheck_os_version_data";
    public static final String DID_PROPERTY = "did_data";
    public static final String DOWNLOAD_CHECK_APP_VERSION_PROPERTY = "downloadcheck_app_ver";
    public static final String INSTALL_REFERRER_PROPERTY = "referrer";
    public static final String MODULEVERSIONCHECK_DATA_PROPERTY = "moduleversioncheck_data";
    public static final String SEND_REFERRER_ON_DOWNLOADCHECK_PROPERTY = "downloadchk_referrer";
    public static final String SEND_REFERRER_ON_INSTALLED_PROPERTY = "send_referrer";
    public static final String SESSION_DATA_PROPERTY = "sessions_data";
    public static final String SESSION_LAST_SEND_TIME_PROPERTY = "sessions_last_send_time";
    public static final String SESSION_LAST_SESSION_PROPERTY = "sessions_last_session";
    public static final String SESSION_LAST_STOP_TIME_PROPERTY = "sessions_last_stop_time";
    public static final String SESSION_LIMIT_NUM = "session_limit_num";
    public static final String SESSION_LIMIT_TIME = "session_limit_time";
    private static Logger logger = LoggerGroup.createLogger(InstallService.TAG);
    private static Properties prop = new Properties();

    public static String getProperty(String name) {
        return getProperty(null, name);
    }

    public static String getProperty(Context context, String name) {
        if (!TextUtils.equals(name, DID_PROPERTY)) {
            return prop.getProperty(name);
        }
        String value = prop.getProperty(name);
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        String androidId = ActiveUserData.get(context, DATA_INDEX.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            return i.a;
        }
        value = Crypt.byt2str(Crypt.Decrypt(Crypt.hexToByteArray(value), androidId.getBytes()));
        try {
            return Long.parseLong(value) <= 0 ? i.a : value;
        } catch (Exception e) {
            logger.v(e.toString());
            value = i.a;
            removeProperty(DID_PROPERTY);
            return value;
        }
    }

    public static void setProperty(String name, String value) {
        logger.v("[ActiveUserProperties][setProperty]name=" + name + ", value=" + value);
        if (TextUtils.isEmpty(value)) {
            value = i.a;
        }
        if (TextUtils.equals(name, DID_PROPERTY)) {
            prop.setProperty(name, Crypt.byteArrayToHex(Crypt.Encrypt(Crypt.str2byt(value), ActiveUserData.get(DATA_INDEX.ANDROID_ID).getBytes())));
        } else {
            prop.setProperty(name, value);
        }
    }

    public static void removeProperty(String name) {
        prop.remove(name);
    }

    public static void loadProperties(Context context) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(ACTIVE_USER_PROPERTIES);
            prop.load(fis);
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e2) {
            logger.v("[ActiveUserProperties][loadProperties]" + e2.toString());
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e3) {
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e5) {
                }
            }
        } catch (Exception e6) {
            e6.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e7) {
                }
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e8) {
                }
            }
        }
        logger.v("[ActiveUserProperties][loadProperties]" + prop.toString());
    }

    public static synchronized void storeProperties(Context context) {
        synchronized (ActiveUserProperties.class) {
            logger.v("[ActiveUserProperties][storeProperties]" + prop.toString());
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(ACTIVE_USER_PROPERTIES, 0);
                prop.store(fos, null);
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                    }
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e3) {
                    }
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e5) {
                    }
                }
            } catch (Exception e6) {
                e6.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e7) {
                    }
                }
            } catch (Throwable th) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e8) {
                    }
                }
            }
        }
    }
}
