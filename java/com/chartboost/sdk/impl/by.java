package com.chartboost.sdk.impl;

class by extends bx {
    private de<cc> a = new de();

    by() {
    }

    void a(Class cls, cc ccVar) {
        this.a.a(cls, ccVar);
    }

    public void a(Object obj, StringBuilder stringBuilder) {
        Object a = ch.a(obj);
        if (a == null) {
            stringBuilder.append(" null ");
            return;
        }
        cc ccVar = null;
        for (Object a2 : de.a(a.getClass())) {
            ccVar = (cc) this.a.a(a2);
            if (ccVar != null) {
                break;
            }
        }
        if (ccVar == null && a.getClass().isArray()) {
            ccVar = (cc) this.a.a((Object) Object[].class);
        }
        if (ccVar == null) {
            throw new RuntimeException("json can't serialize type : " + a.getClass());
        }
        ccVar.a(a, stringBuilder);
    }
}
