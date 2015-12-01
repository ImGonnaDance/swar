package com.mobileapptracker;

import jp.co.cyberz.fox.a.a.i;

final class z implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ MobileAppTracker b;

    z(MobileAppTracker mobileAppTracker, String str) {
        this.b = mobileAppTracker;
        this.a = str;
    }

    public final void run() {
        String replaceAll = this.a.replaceAll("\\D+", i.a);
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < replaceAll.length(); i++) {
            stringBuilder.append(Integer.parseInt(String.valueOf(replaceAll.charAt(i))));
        }
        this.b.params.setPhoneNumber(stringBuilder.toString());
    }
}
