package com.chartboost.sdk.impl;

import java.io.Serializable;

public class cx implements Serializable {
    public boolean equals(Object o) {
        return o instanceof cx;
    }

    public int hashCode() {
        return 0;
    }

    public String toString() {
        return "MaxKey";
    }
}
