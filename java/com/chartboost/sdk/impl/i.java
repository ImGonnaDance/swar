package com.chartboost.sdk.impl;

import java.util.Map;

public class i {
    public final int a;
    public final byte[] b;
    public final Map<String, String> c;
    public final boolean d;

    public i(int i, byte[] bArr, Map<String, String> map, boolean z) {
        this.a = i;
        this.b = bArr;
        this.c = map;
        this.d = z;
    }

    public i(byte[] bArr, Map<String, String> map) {
        this(SelectTarget.TARGETING_SUCCESS, bArr, map, false);
    }
}
