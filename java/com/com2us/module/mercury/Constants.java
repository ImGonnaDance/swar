package com.com2us.module.mercury;

import com.com2us.module.util.Version;

public interface Constants {
    public static final String[] PERMISSION = new String[]{"android.permission.INTERNET"};
    public static final String TAG = "Mercury";
    public static final int VERSION_BUILD = 5;
    public static final int VERSION_MAJOR = 2;
    public static final int VERSION_MINOR = 7;
    public static final String Version = new Version(VERSION_MAJOR, VERSION_MINOR, VERSION_BUILD).toString();
}
