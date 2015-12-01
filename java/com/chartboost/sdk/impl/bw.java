package com.chartboost.sdk.impl;

public class bw {
    final Object a;
    final String b;

    public String toString() {
        return "{ \"$ref\" : \"" + this.b + "\", \"$id\" : \"" + this.a + "\" }";
    }

    public Object a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        bw bwVar = (bw) o;
        if (this.a == null ? bwVar.a != null : !this.a.equals(bwVar.a)) {
            return false;
        }
        if (this.b != null) {
            if (this.b.equals(bwVar.b)) {
                return true;
            }
        } else if (bwVar.b == null) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (this.a != null) {
            hashCode = this.a.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode *= 31;
        if (this.b != null) {
            i = this.b.hashCode();
        }
        return hashCode + i;
    }
}
