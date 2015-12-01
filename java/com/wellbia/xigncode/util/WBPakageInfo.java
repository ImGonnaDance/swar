package com.wellbia.xigncode.util;

import android.annotation.TargetApi;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;

public class WBPakageInfo {
    private String mDataDir = null;
    private int mFlag = 0;
    private String mNativeLibraryDir = null;
    private String mPackageName = null;
    private String mPermission = null;
    private String mProcessName = null;
    private String mSourceDir = null;
    private int mVersionCode = 0;
    private String mVersionName = null;

    public WBPakageInfo(PackageInfo packageInfo) {
        setInfo(packageInfo);
    }

    @TargetApi(9)
    public void setInfo(PackageInfo packageInfo) {
        this.mPackageName = packageInfo.packageName;
        this.mVersionName = packageInfo.versionName;
        this.mVersionCode = packageInfo.versionCode;
        this.mSourceDir = packageInfo.applicationInfo.sourceDir;
        this.mDataDir = packageInfo.applicationInfo.dataDir;
        if (VERSION.SDK_INT >= 9) {
            this.mNativeLibraryDir = packageInfo.applicationInfo.nativeLibraryDir;
        }
        this.mProcessName = packageInfo.applicationInfo.processName;
        this.mPermission = packageInfo.applicationInfo.permission;
        this.mFlag = packageInfo.applicationInfo.flags;
    }

    public boolean isCheckThirdPartyApp() {
        return (this.mFlag & 1) == 0;
    }

    public boolean isInstalledExternalStorage() {
        return (this.mFlag & 262144) == 0;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPakageName(String str) {
        this.mPackageName = str;
    }

    public String getVersionName() {
        return this.mVersionName;
    }

    public void setVersionName(String str) {
        this.mVersionName = str;
    }

    public int getVersionCode() {
        return this.mVersionCode;
    }

    public void setVersionCode(int i) {
        this.mVersionCode = i;
    }

    public String getSourceDir() {
        return this.mSourceDir;
    }

    public void setSourceDir(String str) {
        this.mSourceDir = str;
    }

    public String getDataDir() {
        return this.mDataDir;
    }

    public void setDataDir(String str) {
        this.mDataDir = str;
    }

    public String getNativeLibraryDir() {
        return this.mNativeLibraryDir;
    }

    public void setNativeLibraryDir(String str) {
        this.mNativeLibraryDir = str;
    }

    public String getProcessName() {
        return this.mProcessName;
    }

    public void setProcessName(String str) {
        this.mProcessName = str;
    }

    public String getPermission() {
        return this.mPermission;
    }

    public void setPermission(String str) {
        this.mPermission = str;
    }

    public int getFlag() {
        return this.mFlag;
    }

    public void setFlag(int i) {
        this.mFlag = i;
    }
}
