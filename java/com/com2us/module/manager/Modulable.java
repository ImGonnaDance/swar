package com.com2us.module.manager;

public interface Modulable extends ActivityStateListener, CletStateListener {
    void destroy();

    String getName();

    String[] getPermission();

    String getVersion();

    void setAppIdForIdentity(String str);

    void setLogged(boolean z);
}
