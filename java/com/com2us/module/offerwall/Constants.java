package com.com2us.module.offerwall;

import com.com2us.module.util.Version;

public interface Constants {
    public static final String[] PERMISSION = new String[]{"android.permission.INTERNET"};
    public static final String TAG = "Offerwall";
    public static final int VERSION_BUILD = 8;
    public static final int VERSION_MAJOR = 2;
    public static final int VERSION_MINOR = 0;
    public static final String Version = new Version(VERSION_MAJOR, 0, VERSION_BUILD).toString();
}
