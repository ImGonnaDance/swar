package com.chartboost.sdk.impl;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.ArrayList;

public class bm {
    private static final String[] a = new String[]{"arg:left", "arg:center", "arg:right"};
    private b b;

    public static abstract class b {
        public abstract void a(bm bmVar, int i);

        public void a(bm bmVar) {
        }
    }

    public static class a {
        private final Bundle a = new Bundle();

        public a a(String str) {
            this.a.putString("arg:title", str);
            return this;
        }

        public a b(String str) {
            this.a.putString("arg:message", str);
            return this;
        }

        public a c(String str) {
            this.a.putString("arg:left", str);
            return this;
        }

        public a d(String str) {
            this.a.putString("arg:right", str);
            return this;
        }

        public bm a(Context context, b bVar) {
            return new bm(context, this.a, bVar);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public bm(android.content.Context r10, android.os.Bundle r11, com.chartboost.sdk.impl.bm.b r12) {
        /*
        r9 = this;
        r1 = 0;
        r8 = 0;
        r9.<init>();
        r9.b = r12;
        r0 = "arg:title";
        r2 = r11.getString(r0);
        r0 = "arg:message";
        r3 = r11.getString(r0);
        r0 = "arg:default";
        r4 = -1;
        r5 = r11.getInt(r0, r4);
        r4 = new java.util.ArrayList;
        r4.<init>();
        r0 = r1;
    L_0x0020:
        r6 = 3;
        if (r0 >= r6) goto L_0x0037;
    L_0x0023:
        r6 = a;
        r6 = r6[r0];
        r6 = r11.getString(r6);
        r7 = android.text.TextUtils.isEmpty(r6);
        if (r7 != 0) goto L_0x0034;
    L_0x0031:
        r4.add(r6);
    L_0x0034:
        r0 = r0 + 1;
        goto L_0x0020;
    L_0x0037:
        r0 = new android.app.AlertDialog$Builder;
        r0.<init>(r10);
        r0 = r0.setTitle(r2);
        r2 = r0.setMessage(r3);
        r3 = r4.size();
        switch(r3) {
            case 1: goto L_0x0078;
            case 2: goto L_0x006e;
            case 3: goto L_0x0064;
            default: goto L_0x004b;
        };
    L_0x004b:
        r2 = r2.create();
        r0 = new com.chartboost.sdk.impl.bm$1;
        r1 = r9;
        r0.<init>(r1, r2, r3, r4, r5);
        r2.setOnShowListener(r0);
        r0 = new com.chartboost.sdk.impl.bm$2;
        r0.<init>(r9);
        r2.setOnCancelListener(r0);
        r2.show();
        return;
    L_0x0064:
        r0 = 2;
        r0 = r4.get(r0);
        r0 = (java.lang.CharSequence) r0;
        r2.setNegativeButton(r0, r8);
    L_0x006e:
        r0 = 1;
        r0 = r4.get(r0);
        r0 = (java.lang.CharSequence) r0;
        r2.setNeutralButton(r0, r8);
    L_0x0078:
        r0 = r4.get(r1);
        r0 = (java.lang.CharSequence) r0;
        r2.setPositiveButton(r0, r8);
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.chartboost.sdk.impl.bm.<init>(android.content.Context, android.os.Bundle, com.chartboost.sdk.impl.bm$b):void");
    }

    private static final Button[] b(AlertDialog alertDialog) {
        ViewGroup viewGroup = (ViewGroup) alertDialog.getButton(-1).getParent();
        int childCount = viewGroup.getChildCount();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt.getVisibility() == 0 && (childAt instanceof Button)) {
                arrayList.add((Button) childAt);
            }
        }
        Button[] buttonArr = new Button[arrayList.size()];
        arrayList.toArray(buttonArr);
        return buttonArr;
    }
}
