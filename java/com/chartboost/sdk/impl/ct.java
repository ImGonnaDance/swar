package com.chartboost.sdk.impl;

import java.io.Serializable;
import java.util.Date;

public class ct implements Serializable, Comparable<ct> {
    static final boolean a = Boolean.getBoolean("DEBUG.DBTIMESTAMP");
    final int b = 0;
    final Date c = null;

    public /* synthetic */ int compareTo(Object x0) {
        return a((ct) x0);
    }

    public int a() {
        if (this.c == null) {
            return 0;
        }
        return (int) (this.c.getTime() / 1000);
    }

    public int b() {
        return this.b;
    }

    public String toString() {
        return "TS time:" + this.c + " inc:" + this.b;
    }

    public int a(ct ctVar) {
        if (a() != ctVar.a()) {
            return a() - ctVar.a();
        }
        return b() - ctVar.b();
    }

    public int hashCode() {
        return ((this.b + 31) * 31) + a();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof ct)) {
            return false;
        }
        ct ctVar = (ct) obj;
        if (a() == ctVar.a() && b() == ctVar.b()) {
            return true;
        }
        return false;
    }
}
