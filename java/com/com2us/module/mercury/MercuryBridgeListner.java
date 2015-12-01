package com.com2us.module.mercury;

interface MercuryBridgeListner {
    public static final int POST_INVITEFRIEND_FAIL_BACKOFFICE = -6;
    public static final int POST_INVITEFRIEND_FAIL_CONNECT_ERROR = -5;
    public static final int POST_INVITEFRIEND_FAIL_EMAIL = -3;
    public static final int POST_INVITEFRIEND_FAIL_HTTP_ERROR = -4;
    public static final int POST_INVITEFRIEND_FAIL_INITIALIZ = -1;
    public static final int POST_INVITEFRIEND_FAIL_SMS = -2;
    public static final int POST_INVITEFRIEND_INITIALIZED = 1;
    public static final int POST_INVITEFRIEND_SEND_EMAIL = 3;
    public static final int POST_INVITEFRIEND_SEND_SMS = 2;

    void onResult(int i);
}
