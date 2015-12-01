package com.com2us.module.manager;

import com.com2us.module.util.Version;
import com.com2us.module.util.VersionEx;

public interface Constants {
    public static final int REVISION = 0;
    public static final String STATUS = null;
    public static final String TAG = "ModuleManager";
    public static final int VERSION_BUILD = 1;
    public static final int VERSION_MAJOR = 2;
    public static final int VERSION_MINOR = 0;
    public static final String Version = new VersionEx(VERSION_MAJOR, REVISION, VERSION_BUILD, STATUS, REVISION).toString();
    public static final Version supportedActiveUserVersion = new Version(VERSION_BUILD, REVISION, REVISION);
    public static final Version supportedNewsBanner2Version = new Version(VERSION_BUILD, REVISION, REVISION);
}
