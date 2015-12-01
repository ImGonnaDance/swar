package com.chartboost.sdk.impl;

import android.content.Intent;

public class a extends s {
    private Intent b;

    public a(i iVar) {
        super(iVar);
    }

    public String getMessage() {
        if (this.b != null) {
            return "User needs to (re)enter credentials.";
        }
        return super.getMessage();
    }
}
