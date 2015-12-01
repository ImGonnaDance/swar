package com.com2us.peppermint;

import com.com2us.peppermint.util.PeppermintLog;
import com.com2us.peppermint.util.PeppermintUtil;
import java.util.ArrayList;
import jp.co.cyberz.fox.a.a.i;

class t implements Runnable {
    final /* synthetic */ PeppermintDialog a;
    private final /* synthetic */ String f103a;

    t(PeppermintDialog peppermintDialog, String str) {
        this.a = peppermintDialog;
        this.f103a = str;
    }

    public void run() {
        int i = 0;
        ArrayList arrayList = new ArrayList();
        arrayList = this.f103a.equals("phonenumber") ? PeppermintUtil.getContactsPhonenumber(this.a.f18a, 0, 500) : PeppermintUtil.getContactsEmail(this.a.f18a, 0, 500);
        StringBuilder stringBuilder = new StringBuilder();
        String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
        int length = strArr.length - 1;
        while (i < strArr.length) {
            if (i == length) {
                stringBuilder.append("\"" + strArr[i] + "\"");
                break;
            } else {
                stringBuilder.append("\"" + strArr[i] + "\"" + i.b);
                i++;
            }
        }
        String str = "javascript:window['native'].getAddressBookCallback([" + stringBuilder.toString() + "]" + ")";
        PeppermintLog.i("handleGetAddressBookScheme params=" + str);
        this.a.f18a.loadUrl(str);
    }
}
