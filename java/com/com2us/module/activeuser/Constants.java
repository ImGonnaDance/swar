package com.com2us.module.activeuser;

import com.com2us.module.util.VersionEx;

public interface Constants {
    public static final String[] PERMISSION;
    public static final int REVISION = 0;
    public static final String STATUS = null;
    public static final String TAG = "ActiveUser";
    public static final int VERSION_BUILD = 3;
    public static final int VERSION_MAJOR = 2;
    public static final int VERSION_MINOR = 4;
    public static final String Version = new VersionEx(VERSION_MAJOR, VERSION_MINOR, VERSION_BUILD, STATUS, REVISION).toString();

    public interface Network {
        public static final int TIMEOUT = 15000;

        public interface Gateway {
            public static final String GATEWAY_LIVE_SERVER = "http://activeuser.qpyou.cn/gateway.php";
            public static final String GATEWAY_TARGET_SERVER = "http://activeuser.qpyou.cn/gateway.php";
            public static final String GATEWAY_TEST_SERVER = "http://test.activeuser.com2us.net/gateway.php";
            public static final String HTTP_REQ_AUTHKEY = "REQ-AUTHKEY";
            public static final String HTTP_REQ_TIMESTAMP = "REQ-TIMESTAMP";
            public static final int ONE_SESSION_LIMIT_TIME = 10;
            public static final String REQUEST_AGREEMENT = "agreement";
            public static final String REQUEST_CONFIGURATION = "get_config";
            public static final String REQUEST_DOWNLOAD = "download";
            public static final String REQUEST_GETDID = "get_did";
            public static final String REQUEST_REFERRER = "referrer";
            public static final String REQUEST_SESSION = "session_log";
            public static final String REQUEST_UPDATEDID = "update_did";
            public static final int SEND_SESSION_LIMIT_TIME = 86400;
            public static final int SESSION_LIMIT_NUM = 1;
        }
    }

    static {
        String[] strArr = new String[VERSION_MINOR];
        strArr[REVISION] = "android.permission.INTERNET";
        strArr[1] = "android.permission.ACCESS_NETWORK_STATE";
        strArr[VERSION_MAJOR] = "android.permission.ACCESS_WIFI_STATE";
        strArr[VERSION_BUILD] = "android.permission.CHANGE_WIFI_STATE";
        PERMISSION = strArr;
    }
}
