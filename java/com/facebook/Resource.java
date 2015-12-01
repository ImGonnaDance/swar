package com.facebook;

import android.content.Context;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Resource {
    public static int R(Context context, String identifier) {
        ArrayList<String> returnArr = new ArrayList();
        StringTokenizer stringTokenizer = new StringTokenizer(identifier, ".", false);
        while (stringTokenizer.hasMoreTokens()) {
            returnArr.add(stringTokenizer.nextToken());
        }
        Context ac = context;
        int id = ac.getResources().getIdentifier((String) returnArr.get(2), (String) returnArr.get(1), ac.getPackageName());
        returnArr.clear();
        return id;
    }
}
