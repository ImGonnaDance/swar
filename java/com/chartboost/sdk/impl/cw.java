package com.chartboost.sdk.impl;

public class cw extends cv {
    final ck b;

    public ck b() {
        return this.b;
    }

    public boolean equals(Object o) {
        if (!(o instanceof cw)) {
            return false;
        }
        cw cwVar = (cw) o;
        if (this.a.equals(cwVar.a) && this.b.equals(cwVar.b)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.a.hashCode() ^ this.b.hashCode();
    }
}
