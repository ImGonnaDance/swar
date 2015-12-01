package com.mobileapptracker;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.util.Patterns;
import java.util.HashMap;
import java.util.Set;

final class am implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ MobileAppTracker b;

    am(MobileAppTracker mobileAppTracker, boolean z) {
        this.b = mobileAppTracker;
        this.a = z;
    }

    public final void run() {
        int i = 0;
        int i2 = this.b.mContext.checkCallingOrSelfPermission("android.permission.GET_ACCOUNTS") == 0 ? 1 : 0;
        if (!this.a || i2 == 0) {
            this.b.params.setUserEmail(null);
            return;
        }
        Account[] accountsByType = AccountManager.get(this.b.mContext).getAccountsByType("com.google");
        if (accountsByType.length > 0) {
            this.b.params.setUserEmail(accountsByType[0].name);
        }
        HashMap hashMap = new HashMap();
        Account[] accounts = AccountManager.get(this.b.mContext).getAccounts();
        int length = accounts.length;
        while (i < length) {
            Account account = accounts[i];
            if (Patterns.EMAIL_ADDRESS.matcher(account.name).matches()) {
                hashMap.put(account.name, account.type);
            }
            i++;
        }
        Set keySet = hashMap.keySet();
        this.b.params.setUserEmails((String[]) keySet.toArray(new String[keySet.size()]));
    }
}
