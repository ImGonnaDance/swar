package com.com2us.peppermint.util;

import android.content.Context;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class PeppermintResource {
    public static int getID(Context context, String str) {
        ArrayList arrayList = new ArrayList();
        StringTokenizer stringTokenizer = new StringTokenizer(str, ".", false);
        while (stringTokenizer.hasMoreTokens()) {
            arrayList.add(stringTokenizer.nextToken());
        }
        int identifier = context.getResources().getIdentifier((String) arrayList.get(2), (String) arrayList.get(1), context.getPackageName());
        arrayList.clear();
        return identifier;
    }
}
