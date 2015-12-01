package com.com2us.module.manager;

public interface ModuleConfig {
    public static final int ACTIVEUSER_MODULE = 128;
    public static final int ALL_MODULES = 4095;
    public static final int CHARTBOOST_MODULE = 2;
    public static final int INAPP_PURCHASE_MODULE = 32;
    public static final int MERCURY_MODULE = 1024;
    public static final int MOPUB_MODULE = 4;
    public static final int NEWSBANNER2_MODULE = 64;
    public static final int OFFERWALL_MODULE = 2048;
    public static final int PUSH_MODULE = 16;
    public static final int SNS_MODULE = 8;
    public static final int SOCIAL_MEDIA_MOUDLE = 256;
    public static final int SPIDER_MODULE = 512;
    public static final int TAPJOY_MODULE = 1;

    public interface Push {
        public static final int NOT_USE_CLIBRARY = 2;
        public static final int USE_TEST_SERVER = 1;
    }
}
