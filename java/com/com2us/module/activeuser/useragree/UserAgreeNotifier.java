package com.com2us.module.activeuser.useragree;

public interface UserAgreeNotifier {
    public static final int USER_AGREE_PRIVACY_SUCCESS = 1000;
    public static final int USER_AGREE_SUCCESS = 0;

    void onUserAgreeResult(int i);
}
