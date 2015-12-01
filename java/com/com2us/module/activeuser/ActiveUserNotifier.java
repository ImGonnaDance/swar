package com.com2us.module.activeuser;

public interface ActiveUserNotifier {
    public static final int ACTIVE_USER_GETDID_FAIL = 1;
    public static final int ACTIVE_USER_GETDID_SUCCESS = 0;

    void onActiveUserResult(int i);
}
