package com.com2us.peppermint;

import android.os.Build.VERSION;

public class PeppermintURL {
    public static final String PEPPERMINT_AUTH_PATH = "auth";
    public static final String PEPPERMINT_CLOSE_URI = "c2shub://close";
    public static final String PEPPERMINT_COM2US_DOMAIN = "com2us.com/";
    public static final String PEPPERMINT_COOKIE_URL = (VERSION.SDK_INT <= 10 ? "com2us.com" : ".com2us.com");
    public static final String PEPPERMINT_DEVELOPMENT_API_BASE_URL = "http://dev.api.com2us.com/";
    public static final String PEPPERMINT_DEVELOPMENT_QPYOU_API_BASE_URL = "http://dev.api.qpyou.cn/";
    public static final String PEPPERMINT_DEVELOPMENT_QPYOU_WEB_BASE_URL = "http://dev.hub.qpyou.cn/";
    public static final String PEPPERMINT_DEVELOPMENT_URL = "test";
    public static final String PEPPERMINT_DEVELOPMENT_WEB_BASE_URL = "http://dev.hub.com2us.com/";
    public static final String PEPPERMINT_GET_ADDRESSBOOK_URI = "c2shub://getaddressbook";
    public static final String PEPPERMINT_GET_PHONENUMBER_URI = "c2shub://getphonenumber";
    public static final String PEPPERMINT_GET_PICTURE_URI = "c2shub://getpicture";
    public static final String PEPPERMINT_GUEST_ACQUIRE_UID_PATH = "guest";
    public static final String PEPPERMINT_GUEST_ACQUIRE_UID_URI = "c2shub://guest/candidate_uid";
    public static final String PEPPERMINT_GUEST_BIND_PATH = "guest/bind";
    public static final String PEPPERMINT_GUEST_BIND_URI = "c2shub://guest/bind";
    public static final String PEPPERMINT_HTTP = "http://";
    public static final String PEPPERMINT_HTTPS = "https://";
    public static final String PEPPERMINT_LAUNCH_APP_URI = "c2shub://launchapp";
    public static final String PEPPERMINT_LOGIN_URI = "c2shub://login";
    public static final String PEPPERMINT_LOGOUT_PATH = "auth/logout_proc";
    public static final String PEPPERMINT_LOGOUT_URI = "c2shub://logout";
    public static final String PEPPERMINT_OPEN_BROWSER_URI = "c2shub://openbrowser";
    public static final String PEPPERMINT_PRODUCTION = "";
    public static final String PEPPERMINT_PRODUCTION_API_BASE_URL = "http://api.com2us.com/";
    public static final String PEPPERMINT_PRODUCTION_QPYOU_API_BASE_URL = "http://api.qpyou.cn/";
    public static final String PEPPERMINT_PRODUCTION_QPYOU_WEB_BASE_URL = "http://hub.qpyou.cn/";
    public static final String PEPPERMINT_PRODUCTION_WEB_BASE_URL = "http://hub.com2us.com/";
    public static final String PEPPERMINT_QPYOU_COOKIE_URL = (VERSION.SDK_INT <= 10 ? "qpyou.cn" : ".qpyou.cn");
    public static final String PEPPERMINT_QPYOU_DOMAIN = "qpyou.cn/";
    public static final String PEPPERMINT_SCHEME = "c2shub";
    public static final String PEPPERMINT_SEND_SMS_URI = "c2shub://sendsms";
    public static final String PEPPERMINT_SOCIAL_IS_AUTHORIZED = "c2shub://social/is_authorized";
    public static final String PEPPERMINT_SOCIAL_LOGOUT = "c2shub://social/logout";
    public static final String PEPPERMINT_SOCIAL_REQUEST_FRIENDS = "c2shub://social/request_friends";
    public static final String PEPPERMINT_SOCIAL_REQUEST_USER_PROFILE = "c2shub://social/request_user_profile";
    public static final String PEPPERMINT_SOCIAL_REQUEST_USER_TOKEN = "c2shub://social/request_user_token";
    public static final String PEPPERMINT_STAGING_API_BASE_URL = "http://test.api.com2us.com/";
    public static final String PEPPERMINT_STAGING_QPYOU_API_BASE_URL = "http://test.api.qpyou.cn/";
    public static final String PEPPERMINT_STAGING_QPYOU_WEB_BASE_URL = "http://test.hub.qpyou.cn/";
    public static final String PEPPERMINT_STAGING_URL = "staging";
    public static final String PEPPERMINT_STAGING_WEB_BASE_URL = "http://test.hub.com2us.com/";
}
