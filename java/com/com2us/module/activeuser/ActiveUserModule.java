package com.com2us.module.activeuser;

import com.com2us.module.activeuser.ActiveUserNetwork.Received;

public interface ActiveUserModule {
    void destroy();

    boolean isExecutable();

    void responseNetwork(Received received);
}
