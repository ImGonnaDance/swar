package com.chartboost.sdk.impl;

import com.com2us.module.manager.ModuleConfig;
import jp.co.cyberz.fox.a.a.i;

public class cl implements ci {
    static final String[] a = new String[ModuleConfig.ACTIVEUSER_MODULE];
    private byte[] b = new byte[ModuleConfig.MERCURY_MODULE];
    private byte[] c = new byte[ModuleConfig.MERCURY_MODULE];
    private cr d = new cr();

    static {
        a((byte) 48, (byte) 57);
        a((byte) 97, (byte) 122);
        a((byte) 65, (byte) 90);
    }

    static void a(byte b, byte b2) {
        while (b < b2) {
            a[b] = i.a + ((char) b);
            b = (byte) (b + 1);
        }
    }
}
