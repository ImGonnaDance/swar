package com.chartboost.sdk.impl;

import java.io.Serializable;

public class da implements Serializable {
    private final String a;

    public String a() {
        return this.a;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o instanceof da) {
            o = ((da) o).a;
        } else if (!(o instanceof String)) {
            return false;
        } else {
            String o2 = (String) o;
        }
        if (this.a != null) {
            if (this.a.equals(o)) {
                return true;
            }
        } else if (o == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.a != null ? this.a.hashCode() : 0;
    }

    public String toString() {
        return this.a;
    }
}
