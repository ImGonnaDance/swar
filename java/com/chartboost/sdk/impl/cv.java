package com.chartboost.sdk.impl;

import java.io.Serializable;

public class cv implements Serializable {
    final String a;

    public String a() {
        return this.a;
    }

    public boolean equals(Object o) {
        if (!(o instanceof cv)) {
            return false;
        }
        return this.a.equals(((cv) o).a);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public String toString() {
        return a();
    }
}
