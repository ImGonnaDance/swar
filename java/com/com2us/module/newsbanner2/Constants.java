package com.com2us.module.newsbanner2;

import com.com2us.module.util.VersionEx;

public interface Constants {
    public static final String CACHED_IMAGE_BANNER_DIR = "newsbanner/image/banner";
    public static final int CACHED_IMAGE_BANNER_MAX = 8;
    public static final String CACHED_IMAGE_DIR_PATH = "newsbanner/image";
    public static final String CACHED_IMAGE_TAB_DIR = "newsbanner/image/tab";
    public static final int HD_SCREEN_SIZE = 640;
    public static final String[] PERMISSION = new String[]{"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE", "android.permission.ACCESS_WIFI_STATE", "android.permission.CHANGE_WIFI_STATE"};
    public static final String RESOURCE_PATH = "common/NoticeImage/";
    public static final int REVISION = 0;
    public static final String STATUS = null;
    public static final String TAB_DOWN_CLOSE_FILE_NAME = "tab_ground_close.dat";
    public static final String TAB_DOWN_OPEN_FILE_NAME = "tab_ground_open.dat";
    public static final String TAB_UP_CLOSE_FILE_NAME = "tab_ceiling_close.dat";
    public static final String TAB_UP_OPEN_FILE_NAME = "tab_ceiling_open.dat";
    public static final String TAG = "NewsBanner2";
    public static final int VERSION_BUILD = 1;
    public static final int VERSION_MAJOR = 2;
    public static final int VERSION_MINOR = 3;
    public static final String Version = new VersionEx(VERSION_MAJOR, VERSION_MINOR, VERSION_BUILD, STATUS, REVISION).toString();

    public static class Network {
        static final String LIVE_SERVER = "https://newsbanner.com2us.net/common/banner/";
        static boolean LOGIN = false;
        static String TARGET_SERVER = LIVE_SERVER;
        static final String TEST_SERVER = "http://mdev.com2us.net/common/banner/";
        static final int TIMEOUT = 10000;
    }
}
