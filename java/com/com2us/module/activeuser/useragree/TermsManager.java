package com.com2us.module.activeuser.useragree;

import android.content.Context;
import android.text.TextUtils;
import com.chartboost.sdk.CBLocation;
import java.util.Locale;

public class TermsManager {
    public static final int TERMS_BUTTON_MESSAGE_NEGATIVE_IDX = 6;
    public static final int TERMS_BUTTON_MESSAGE_POSITIVE_IDX = 5;
    public static final int TERMS_LINK_MESSAGE_IDX = 4;
    public static final int TERMS_MESSAGE_IDX = 2;
    public static final int TERMS_TITLE_IDX = 1;
    public static final int TERMS_TYPE_IDX = 0;
    public static final int TERMS_URL_IDX = 3;
    private static String[][] data = null;
    private static TermsManager termsManager = null;
    private String backKeyText = "Back press again to exit.";
    private String errorMsgText = "Network connection is unstable.Please try again after checking your network status.";
    private String errorTitleText = "Privacy Policy";
    private String loadingText = "Loading...";
    private String quitText = CBLocation.LOCATION_QUIT;
    private String retryText = "Retry";

    public static TermsManager getInstance(Context context) {
        if (termsManager == null) {
            termsManager = new TermsManager(context);
        }
        return termsManager;
    }

    private TermsManager(Context context) {
        setText(context);
        String[][] strArr = new String[TERMS_URL_IDX][];
        strArr[TERMS_TYPE_IDX] = new String[]{a.e, "\uac8c\uc784 \uc774\uc6a9 \uc57d\uad00", "\ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud574 \uc8fc\uc154\uc11c \uac10\uc0ac\ud569\ub2c8\ub2e4.<br><br>\uc774 \uc57d\uad00\uc740 \ucef4\ud22c\uc2a4\uc5d0\uc11c \uc81c\uacf5\ud558\ub294 \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4(\ucef4\ud22c\uc2a4\uc571 \ubc0f \ucef4\ud22c\uc2a4 \ud5c8\ube0c \uc11c\ube44\uc2a4 \ub4f1)\uc758 \uc774\uc6a9\uacfc \uad00\ub828\ud558\uc5ec \uc774\uc6a9\uc790\uc640 \ucef4\ud22c\uc2a4\uac04\uc758 \uad8c\ub9ac, \uc758\ubb34 \ubc0f \ud544\uc694\ud55c \uc81c\ubc18 \uc0ac\ud56d\uc744 \uc815\ud558\uace0 \uc788\uc2b5\ub2c8\ub2e4. \uc989, \uc774 \uc57d\uad00\uc740 \uc774\uc6a9\uc790\uac00 \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud558\uae30 \uc704\ud55c \uc2e0\uccad \uacfc\uc815\uacfc \uc774\uc6a9\uc790\uc758 \uad8c\ub9ac\uc640 \uc758\ubb34, \ucef4\ud22c\uc2a4\uc758 \uad8c\ub9ac\uc640 \uc758\ubb34, \uac74\uc804\ud558\uace0 \uc990\uac81\uac8c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud558\uae30 \uc704\ud558\uc5ec \uc9c0\ucf1c\uc57c \ud560 \uaddc\uce59\uacfc \uc900\uc218\uc0ac\ud56d \ubc0f \uc774\ub97c \uc704\ubc18\ud558\ub294 \uacbd\uc6b0\uc5d0 \ub530\ub978 \ubd88\uac00\ud53c\ud55c \uc81c\uc7ac \uc0ac\ud56d \ubc0f \uae30\uc900 \ub4f1\uc744 \uc815\ud558\uace0 \uc788\uc2b5\ub2c8\ub2e4. \ub530\ub77c\uc11c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9 \uc804\uc5d0 \uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9\uc744 \uc8fc\uc758 \uae4a\uac8c \uc77d\uc5b4 \uc8fc\uc2dc\uae30 \ubc14\ub78d\ub2c8\ub2e4.<br><br>\ucef4\ud22c\uc2a4\ub294 \ud56d\uc0c1 \uc591\uc9c8\uc758 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc81c\uacf5\ud558\uace0 \uc18c\ube44\uc790\uc758 \uad8c\uc775\uc744 \ubcf4\ud638\ud558\uae30 \uc704\ud558\uc5ec \ucd5c\uc120\uc758 \ub178\ub825\uc744 \ub2e4\ud558\uace0 \uc788\uc73c\uba70, \uc548\uc804\ud558\uace0 \uac74\uc804\ud55c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9\uc744 \uc704\ud558\uc5ec \uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9\uc744 \uc798 \uc77d\uc5b4 \ubcf4\uc2dc\uace0 \uc801\uadf9\uc801\uc774\uace0 \ub2a5\ub3d9\uc801\uc778 \ud611\uc870\ub97c \ubd80\ud0c1 \ub4dc\ub9bd\ub2c8\ub2e4.<br><br>\uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9 \ubc0f \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\uc758 \uc774\uc6a9\uacfc \uad00\ub828\ud558\uc5ec \uad81\uae08\ud558\uc2e0 \uc810\uc774 \uc788\uc73c\uc2dc\uba74 \uc5b8\uc81c\ub77c\ub3c4 \uc774 \uc57d\uad00\uc5d0 \uae30\uc7ac\ub418\uc5b4 \uc788\ub294 \ubc14\uc5d0 \ub530\ub77c \ucef4\ud22c\uc2a4\uc5d0 \uc5f0\ub77d\uc744 \uc8fc\uc2dc\uba74 \uc131\uc2e4\ud788 \ub2f5\ubcc0 \ub4dc\ub9ac\ub3c4\ub85d \ud558\uaca0\uc2b5\ub2c8\ub2e4.<br><br>\ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9\uc744 \ud1b5\ud558\uc5ec \ud56d\uc0c1 \uc990\uac70\uc6c0\uc774 \uac00\ub4dd\ud558\uc2dc\uae38 \uae30\uc6d0\ud569\ub2c8\ub2e4.", "http://m.com2us.com/", "\ubaa8\ub450\ubcf4\uae30", "\ub3d9\uc758", "\uac70\ubd80"};
        strArr[TERMS_TITLE_IDX] = new String[]{a.d, "\uac8c\uc784 \uc774\uc6a9 \uc57d\uad00", "\ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud574 \uc8fc\uc154\uc11c \uac10\uc0ac\ud569\ub2c8\ub2e4.<br><br>\uc774 \uc57d\uad00\uc740 \ucef4\ud22c\uc2a4\uc5d0\uc11c \uc81c\uacf5\ud558\ub294 \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4(\ucef4\ud22c\uc2a4\uc571 \ubc0f \ucef4\ud22c\uc2a4 \ud5c8\ube0c \uc11c\ube44\uc2a4 \ub4f1)\uc758 \uc774\uc6a9\uacfc \uad00\ub828\ud558\uc5ec \uc774\uc6a9\uc790\uc640 \ucef4\ud22c\uc2a4\uac04\uc758 \uad8c\ub9ac, \uc758\ubb34 \ubc0f \ud544\uc694\ud55c \uc81c\ubc18 \uc0ac\ud56d\uc744 \uc815\ud558\uace0 \uc788\uc2b5\ub2c8\ub2e4. \uc989, \uc774 \uc57d\uad00\uc740 \uc774\uc6a9\uc790\uac00 \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud558\uae30 \uc704\ud55c \uc2e0\uccad \uacfc\uc815\uacfc \uc774\uc6a9\uc790\uc758 \uad8c\ub9ac\uc640 \uc758\ubb34, \ucef4\ud22c\uc2a4\uc758 \uad8c\ub9ac\uc640 \uc758\ubb34, \uac74\uc804\ud558\uace0 \uc990\uac81\uac8c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud558\uae30 \uc704\ud558\uc5ec \uc9c0\ucf1c\uc57c \ud560 \uaddc\uce59\uacfc \uc900\uc218\uc0ac\ud56d \ubc0f \uc774\ub97c \uc704\ubc18\ud558\ub294 \uacbd\uc6b0\uc5d0 \ub530\ub978 \ubd88\uac00\ud53c\ud55c \uc81c\uc7ac \uc0ac\ud56d \ubc0f \uae30\uc900 \ub4f1\uc744 \uc815\ud558\uace0 \uc788\uc2b5\ub2c8\ub2e4. \ub530\ub77c\uc11c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9 \uc804\uc5d0 \uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9\uc744 \uc8fc\uc758 \uae4a\uac8c \uc77d\uc5b4 \uc8fc\uc2dc\uae30 \ubc14\ub78d\ub2c8\ub2e4.<br><br>\ucef4\ud22c\uc2a4\ub294 \ud56d\uc0c1 \uc591\uc9c8\uc758 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc81c\uacf5\ud558\uace0 \uc18c\ube44\uc790\uc758 \uad8c\uc775\uc744 \ubcf4\ud638\ud558\uae30 \uc704\ud558\uc5ec \ucd5c\uc120\uc758 \ub178\ub825\uc744 \ub2e4\ud558\uace0 \uc788\uc73c\uba70, \uc548\uc804\ud558\uace0 \uac74\uc804\ud55c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9\uc744 \uc704\ud558\uc5ec \uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9\uc744 \uc798 \uc77d\uc5b4 \ubcf4\uc2dc\uace0 \uc801\uadf9\uc801\uc774\uace0 \ub2a5\ub3d9\uc801\uc778 \ud611\uc870\ub97c \ubd80\ud0c1 \ub4dc\ub9bd\ub2c8\ub2e4.<br><br>\uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9 \ubc0f \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\uc758 \uc774\uc6a9\uacfc \uad00\ub828\ud558\uc5ec \uad81\uae08\ud558\uc2e0 \uc810\uc774 \uc788\uc73c\uc2dc\uba74 \uc5b8\uc81c\ub77c\ub3c4 \uc774 \uc57d\uad00\uc5d0 \uae30\uc7ac\ub418\uc5b4 \uc788\ub294 \ubc14\uc5d0 \ub530\ub77c \ucef4\ud22c\uc2a4\uc5d0 \uc5f0\ub77d\uc744 \uc8fc\uc2dc\uba74 \uc131\uc2e4\ud788 \ub2f5\ubcc0 \ub4dc\ub9ac\ub3c4\ub85d \ud558\uaca0\uc2b5\ub2c8\ub2e4.<br><br>\ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9\uc744 \ud1b5\ud558\uc5ec \ud56d\uc0c1 \uc990\uac70\uc6c0\uc774 \uac00\ub4dd\ud558\uc2dc\uae38 \uae30\uc6d0\ud569\ub2c8\ub2e4.", "http://m.com2us.com/", "\ubaa8\ub450\ubcf4\uae30", "\ub3d9\uc758", "\uac70\ubd80"};
        strArr[TERMS_MESSAGE_IDX] = new String[]{a.e, "\uac8c\uc784 \uc774\uc6a9 \uc57d\uad00", "\ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud574 \uc8fc\uc154\uc11c \uac10\uc0ac\ud569\ub2c8\ub2e4.<br><br>\uc774 \uc57d\uad00\uc740 \ucef4\ud22c\uc2a4\uc5d0\uc11c \uc81c\uacf5\ud558\ub294 \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4(\ucef4\ud22c\uc2a4\uc571 \ubc0f \ucef4\ud22c\uc2a4 \ud5c8\ube0c \uc11c\ube44\uc2a4 \ub4f1)\uc758 \uc774\uc6a9\uacfc \uad00\ub828\ud558\uc5ec \uc774\uc6a9\uc790\uc640 \ucef4\ud22c\uc2a4\uac04\uc758 \uad8c\ub9ac, \uc758\ubb34 \ubc0f \ud544\uc694\ud55c \uc81c\ubc18 \uc0ac\ud56d\uc744 \uc815\ud558\uace0 \uc788\uc2b5\ub2c8\ub2e4. \uc989, \uc774 \uc57d\uad00\uc740 \uc774\uc6a9\uc790\uac00 \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud558\uae30 \uc704\ud55c \uc2e0\uccad \uacfc\uc815\uacfc \uc774\uc6a9\uc790\uc758 \uad8c\ub9ac\uc640 \uc758\ubb34, \ucef4\ud22c\uc2a4\uc758 \uad8c\ub9ac\uc640 \uc758\ubb34, \uac74\uc804\ud558\uace0 \uc990\uac81\uac8c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc774\uc6a9\ud558\uae30 \uc704\ud558\uc5ec \uc9c0\ucf1c\uc57c \ud560 \uaddc\uce59\uacfc \uc900\uc218\uc0ac\ud56d \ubc0f \uc774\ub97c \uc704\ubc18\ud558\ub294 \uacbd\uc6b0\uc5d0 \ub530\ub978 \ubd88\uac00\ud53c\ud55c \uc81c\uc7ac \uc0ac\ud56d \ubc0f \uae30\uc900 \ub4f1\uc744 \uc815\ud558\uace0 \uc788\uc2b5\ub2c8\ub2e4. \ub530\ub77c\uc11c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9 \uc804\uc5d0 \uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9\uc744 \uc8fc\uc758 \uae4a\uac8c \uc77d\uc5b4 \uc8fc\uc2dc\uae30 \ubc14\ub78d\ub2c8\ub2e4.<br><br>\ucef4\ud22c\uc2a4\ub294 \ud56d\uc0c1 \uc591\uc9c8\uc758 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\ub97c \uc81c\uacf5\ud558\uace0 \uc18c\ube44\uc790\uc758 \uad8c\uc775\uc744 \ubcf4\ud638\ud558\uae30 \uc704\ud558\uc5ec \ucd5c\uc120\uc758 \ub178\ub825\uc744 \ub2e4\ud558\uace0 \uc788\uc73c\uba70, \uc548\uc804\ud558\uace0 \uac74\uc804\ud55c \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9\uc744 \uc704\ud558\uc5ec \uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9\uc744 \uc798 \uc77d\uc5b4 \ubcf4\uc2dc\uace0 \uc801\uadf9\uc801\uc774\uace0 \ub2a5\ub3d9\uc801\uc778 \ud611\uc870\ub97c \ubd80\ud0c1 \ub4dc\ub9bd\ub2c8\ub2e4.<br><br>\uc774 \uc57d\uad00\uc758 \ub0b4\uc6a9 \ubc0f \ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4\uc758 \uc774\uc6a9\uacfc \uad00\ub828\ud558\uc5ec \uad81\uae08\ud558\uc2e0 \uc810\uc774 \uc788\uc73c\uc2dc\uba74 \uc5b8\uc81c\ub77c\ub3c4 \uc774 \uc57d\uad00\uc5d0 \uae30\uc7ac\ub418\uc5b4 \uc788\ub294 \ubc14\uc5d0 \ub530\ub77c \ucef4\ud22c\uc2a4\uc5d0 \uc5f0\ub77d\uc744 \uc8fc\uc2dc\uba74 \uc131\uc2e4\ud788 \ub2f5\ubcc0 \ub4dc\ub9ac\ub3c4\ub85d \ud558\uaca0\uc2b5\ub2c8\ub2e4.<br><br>\ucef4\ud22c\uc2a4 \ubaa8\ubc14\uc77c\uc11c\ube44\uc2a4 \uc774\uc6a9\uc744 \ud1b5\ud558\uc5ec \ud56d\uc0c1 \uc990\uac70\uc6c0\uc774 \uac00\ub4dd\ud558\uc2dc\uae38 \uae30\uc6d0\ud569\ub2c8\ub2e4.", "http://m.com2us.com/", "\ubaa8\ub450\ubcf4\uae30", "\ub3d9\uc758", "\uac70\ubd80"};
        data = strArr;
    }

    public String[][] getData() {
        return data;
    }

    public boolean setData(String[][] newData) {
        try {
            data = (String[][]) newData.clone();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorTitleText() {
        return this.errorTitleText;
    }

    public String getErrorMsgText() {
        return this.errorMsgText;
    }

    public String getRetryText() {
        return this.retryText;
    }

    public String getQuitText() {
        return this.quitText;
    }

    public String getBackKeyText() {
        return this.backKeyText;
    }

    public String getLoadingText() {
        return this.loadingText;
    }

    private void setText(Context context) {
        String country = context.getResources().getConfiguration().locale.getCountry().toLowerCase(Locale.US);
        String language = context.getResources().getConfiguration().locale.getLanguage().toLowerCase(Locale.US);
        if (TextUtils.equals("ko", language)) {
            this.errorTitleText = "Privacy Policy";
            this.errorMsgText = "\ub124\ud2b8\uc6cc\ud06c \uc811\uc18d\uc774 \uc6d0\ud65c\ud558\uc9c0 \uc54a\uc2b5\ub2c8\ub2e4. \ub124\ud2b8\uc6cc\ud06c \uc0c1\ud0dc \ud655\uc778 \ud6c4 \ub2e4\uc2dc \uc2dc\ub3c4\ud574 \uc8fc\uc2dc\uae30 \ubc14\ub78d\ub2c8\ub2e4.";
            this.retryText = "\uc7ac\uc2dc\ub3c4";
            this.quitText = "\uc885\ub8cc";
            this.backKeyText = "'\ub4a4\ub85c' \ubc84\ud2bc\uc744 \ud55c\ubc88 \ub354 \ub204\ub974\uc2dc\uba74 \uc885\ub8cc\ub429\ub2c8\ub2e4.";
            this.loadingText = "\ub85c\ub529 \uc911...";
        } else if (TextUtils.equals("tw", country)) {
            this.errorTitleText = "\u96b1\u79c1\u653f\u7b56";
            this.errorMsgText = "\u7db2\u8def\u9023\u63a5\u4e0d\u7a69\u5b9a\u3002\u8acb\u78ba\u8a8d\u7db2\u8def\u72c0\u614b\u4e26\u91cd\u8a66\u3002";
            this.retryText = "\u91cd\u8a66";
            this.quitText = "\u9000\u51fa";
            this.backKeyText = "'\u8fd4\u56de'\u518d\u6b21\u6309\u4e0b\u6309\u9215\u9000\u51fa\u3002";
            this.loadingText = "\u52a0\u8f09\u4e2d...";
        } else if (TextUtils.equals("zh", language)) {
            this.errorTitleText = "\u9690\u79c1\u653f\u7b56";
            this.errorMsgText = "\u7f51\u7edc\u8fde\u63a5\u4e0d\u7a33\u5b9a\u3002\u8bf7\u786e\u8ba4\u7f51\u7edc\u72b6\u6001\u5e76\u91cd\u8bd5\u3002";
            this.retryText = "\u91cd\u8bd5";
            this.quitText = "\u9000\u51fa";
            this.backKeyText = "'\u8fd4\u56de'\u518d\u6b21\u6309\u4e0b\u6309\u94ae\u9000\u51fa\u3002";
            this.loadingText = "\u52a0\u8f7d\u4e2d...";
        } else if (TextUtils.equals("ja", language)) {
            this.errorTitleText = "\u30d7\u30e9\u30a4\u30d0\u30b7\u30fc\u30dd\u30ea\u30b7\u30fc";
            this.errorMsgText = "\u30cd\u30c3\u30c8\u30ef\u30fc\u30af\u306b\u63a5\u7d9a\u3067\u304d\u307e\u305b\u3093\u3002\u30cd\u30c3\u30c8\u30ef\u30fc\u30af\u72b6\u614b\u3092\u78ba\u8a8d\u3057\u3066\u304b\u3089\u518d\u5ea6\u304a\u8a66\u3057\u304f\u3060\u3055\u3044\u3002";
            this.retryText = "\u30ea\u30c8\u30e9\u30a4";
            this.quitText = "\u7d42\u4e86";
            this.backKeyText = "\u3082\u3046\u4e00\u5ea6\u300c\u623b\u308b\u300d\u30dc\u30bf\u30f3\u3092\u62bc\u3057\u3066\u7d42\u4e86\u3057\u307e\u3059\u3002";
            this.loadingText = "\u30ed\u30fc\u30c7\u30a3\u30f3\u30b0\u4e2d...";
        } else if (TextUtils.equals("de", language)) {
            this.errorTitleText = "Datenschutzerkl\u00e4rung";
            this.errorMsgText = "Netzwerkverbindung ist instabil. Bitte erneut versuchen nach der \u00dcberpr\u00fcfung Ihrer Netzwerk-Status.";
            this.retryText = "Nochmals spielen";
            this.quitText = "Verlassen";
            this.backKeyText = "'Zur\u00fcck' dr\u00fccken Sie die Taste erneut, um beendet.";
            this.loadingText = "Verladung...";
        } else if (TextUtils.equals("fr", language)) {
            this.errorTitleText = "Politique de confidentialit\u00e9";
            this.errorMsgText = "La connexion r\u00e9seau est instable. R\u00e9essaie apr\u00e8s avoir v\u00e9rifi\u00e9 l'\u00e9tat du r\u00e9seau.";
            this.retryText = "Rejouer";
            this.quitText = "Quitter";
            this.backKeyText = "Bouton 'retour' de presse de nouveau pour les sorties.";
            this.loadingText = "Chargement...";
        } else {
            this.errorTitleText = "Privacy Policy";
            this.errorMsgText = "Network connection is unstable.Please try again after checking your network status.";
            this.retryText = "Retry";
            this.quitText = CBLocation.LOCATION_QUIT;
            this.backKeyText = "'Back' press button again to exits.";
            this.loadingText = "Loading...";
        }
    }
}
