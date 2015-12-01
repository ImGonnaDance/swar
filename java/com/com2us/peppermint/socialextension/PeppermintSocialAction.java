package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.util.PeppermintLog;
import com.com2us.smon.google.service.util.GameHelper;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.wellbia.xigncode.util.WBBase64;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;

public class PeppermintSocialAction {
    private static /* synthetic */ int[] a;
    private PeppermintSocialActionType f48a;
    private String f49a;

    static /* synthetic */ int[] a() {
        int[] iArr = a;
        if (iArr == null) {
            iArr = new int[PeppermintSocialActionType.values().length];
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_BUSINESS_MODEL.ordinal()] = 9;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_CONNECT.ordinal()] = 16;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_INVITE_DIALOG.ordinal()] = 10;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_IS_AUTHORIZED.ordinal()] = 14;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_LOGOUT.ordinal()] = 15;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_QUERY.ordinal()] = 8;
            } catch (NoSuchFieldError e7) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_RECEIVED_INVITE.ordinal()] = 11;
            } catch (NoSuchFieldError e8) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_FRIENDS.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_INVITATION_LIST.ordinal()] = 4;
            } catch (NoSuchFieldError e10) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_ME.ordinal()] = 2;
            } catch (NoSuchFieldError e11) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_TOKEN.ordinal()] = 13;
            } catch (NoSuchFieldError e12) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SEND_APP_INVITATION.ordinal()] = 7;
            } catch (NoSuchFieldError e13) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SEND_APP_MESSAGE.ordinal()] = 6;
            } catch (NoSuchFieldError e14) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SHARE_APP_ACTIVITY.ordinal()] = 5;
            } catch (NoSuchFieldError e15) {
            }
            try {
                iArr[PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_USER_PROFILE_IMAGE.ordinal()] = 12;
            } catch (NoSuchFieldError e16) {
            }
            a = iArr;
        }
        return iArr;
    }

    public static PeppermintSocialActionType actionTypeFromName(String str) {
        return str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_USER_PROFILE) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_ME : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_USER_TOKEN) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_TOKEN : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_FRIENDS) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_FRIENDS : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_INVITATION_LIST) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_INVITATION_LIST : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_SHARE_APP_ACTIVITY) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SHARE_APP_ACTIVITY : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_SEND_APP_MESSAGE) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SEND_APP_MESSAGE : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_SEND_APP_INVITATION) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SEND_APP_INVITATION : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_QUERY) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_QUERY : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_BUSINESS_MODEL) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_BUSINESS_MODEL : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_INVITE_DIALOG) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_INVITE_DIALOG : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_RECEIVED_INVITE) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_RECEIVED_INVITE : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_USER_PROFILE_IMAGE) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_USER_PROFILE_IMAGE : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_IS_AUTHORIZED) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_IS_AUTHORIZED : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_LOGOUT) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_LOGOUT : str.equalsIgnoreCase(PeppermintConstant.SOCIAL_REQUEST_CONNECT) ? PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_CONNECT : PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_NONE;
    }

    public static PeppermintSocialAction actionWithName(String str) {
        PeppermintLog.i("actionWithName name=" + str);
        PeppermintSocialActionType actionTypeFromName = actionTypeFromName(str);
        if (actionTypeFromName == PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_NONE) {
            return null;
        }
        PeppermintSocialAction peppermintSocialAction = new PeppermintSocialAction();
        peppermintSocialAction.f49a = str;
        peppermintSocialAction.f48a = actionTypeFromName;
        return peppermintSocialAction;
    }

    public static PeppermintSocialAction actionWithType(PeppermintSocialActionType peppermintSocialActionType) {
        PeppermintLog.i("actionWithType type=" + peppermintSocialActionType);
        String nameFromType = nameFromType(peppermintSocialActionType);
        if (nameFromType == null) {
            return null;
        }
        PeppermintSocialAction peppermintSocialAction = new PeppermintSocialAction();
        peppermintSocialAction.f49a = nameFromType;
        peppermintSocialAction.f48a = peppermintSocialActionType;
        return peppermintSocialAction;
    }

    public static String nameFromType(PeppermintSocialActionType peppermintSocialActionType) {
        switch (a()[peppermintSocialActionType.ordinal()]) {
            case o.b /*2*/:
                return PeppermintConstant.SOCIAL_REQUEST_USER_PROFILE;
            case o.c /*3*/:
                return PeppermintConstant.SOCIAL_REQUEST_FRIENDS;
            case o.d /*4*/:
                return PeppermintConstant.SOCIAL_REQUEST_INVITATION_LIST;
            case f.bc /*5*/:
                return PeppermintConstant.SOCIAL_REQUEST_SHARE_APP_ACTIVITY;
            case f.aL /*6*/:
                return PeppermintConstant.SOCIAL_REQUEST_SEND_APP_MESSAGE;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
                return PeppermintConstant.SOCIAL_REQUEST_SEND_APP_INVITATION;
            case WBBase64.URL_SAFE /*8*/:
                return PeppermintConstant.SOCIAL_REQUEST_QUERY;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoTextColor /*9*/:
                return PeppermintConstant.SOCIAL_REQUEST_BUSINESS_MODEL;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoImageType /*10*/:
                return PeppermintConstant.SOCIAL_REQUEST_INVITE_DIALOG;
            case R.styleable.MapAttrs_uiZoomGestures /*11*/:
                return PeppermintConstant.SOCIAL_REQUEST_RECEIVED_INVITE;
            case R.styleable.MapAttrs_useViewLifecycle /*12*/:
                return PeppermintConstant.SOCIAL_REQUEST_USER_PROFILE_IMAGE;
            case R.styleable.MapAttrs_zOrderOnTop /*13*/:
                return PeppermintConstant.SOCIAL_REQUEST_USER_TOKEN;
            case f.r /*14*/:
                return PeppermintConstant.SOCIAL_REQUEST_IS_AUTHORIZED;
            case GameHelper.CLIENT_ALL /*15*/:
                return PeppermintConstant.SOCIAL_REQUEST_LOGOUT;
            case WBBase64.NO_CLOSE /*16*/:
                return PeppermintConstant.SOCIAL_REQUEST_CONNECT;
            default:
                return null;
        }
    }

    public String getName() {
        return this.f49a;
    }

    public PeppermintSocialActionType getType() {
        return this.f48a;
    }
}
