package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.PeppermintType;
import com.com2us.peppermint.util.PeppermintLog;
import com.com2us.smon.google.service.util.GameHelper;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.facebook.internal.NativeProtocol;
import com.wellbia.xigncode.util.WBBase64;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class PeppermintSocialPlugin {
    static final /* synthetic */ boolean a = (!PeppermintSocialPlugin.class.desiredAssertionStatus());
    private static /* synthetic */ int[] f43a;
    private PeppermintCallback f44a = null;
    private PeppermintSocialActionType f45a = null;
    private JSONObject f46a = null;

    private JSONObject a(PeppermintSocialPlugin peppermintSocialPlugin, PeppermintSocialActionType peppermintSocialActionType, Exception exception) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, peppermintSocialPlugin.getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(peppermintSocialActionType));
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_AUTH_FAIL);
            if (exception != null) {
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, exception.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    static /* synthetic */ int[] a() {
        int[] iArr = f43a;
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
            f43a = iArr;
        }
        return iArr;
    }

    private JSONObject b(PeppermintSocialPlugin peppermintSocialPlugin, PeppermintSocialActionType peppermintSocialActionType, Exception exception) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, peppermintSocialPlugin.getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(peppermintSocialActionType));
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_DIALOG_CLOSE);
            if (exception != null) {
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, exception.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    public abstract void connect();

    public boolean connectForAction(PeppermintSocialAction peppermintSocialAction, JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("connectForAction action=" + peppermintSocialAction.getName() + " jObj=" + jSONObject);
        if (!a && this.f45a != null) {
            throw new AssertionError("this.action is null!");
        } else if (!isSupportedAction(peppermintSocialAction.getType())) {
            return false;
        } else {
            if (isConnected() || exceptionConnectCheck(peppermintSocialAction)) {
                performAction(peppermintSocialAction.getType(), jSONObject, peppermintCallback);
            } else {
                this.f45a = peppermintSocialAction.getType();
                this.f46a = jSONObject;
                this.f44a = peppermintCallback;
                connect();
            }
            return true;
        }
    }

    public abstract void disconnect();

    public void doWork() {
        PeppermintLog.i("doWork");
        if (this.f45a != null) {
            performAction(this.f45a, this.f46a, this.f44a);
        }
        this.f45a = null;
        this.f46a = null;
        this.f44a = null;
    }

    public boolean exceptionConnectCheck(PeppermintSocialAction peppermintSocialAction) {
        switch (a()[peppermintSocialAction.getType().ordinal()]) {
            case f.r /*14*/:
            case GameHelper.CLIENT_ALL /*15*/:
                return true;
            default:
                return false;
        }
    }

    public Object genderCodeFromString(String str) {
        return (str.equalsIgnoreCase("male") || str.equalsIgnoreCase("m")) ? "M" : (str.equalsIgnoreCase("female") || str.equalsIgnoreCase("f")) ? "F" : JSONObject.NULL;
    }

    public abstract String getServiceName();

    protected void handleAuthCancel(Exception exception) {
        PeppermintLog.i("handleAuthCancel");
        if (this.f45a != null) {
            if (this.f44a != null) {
                this.f44a.run(b(this, this.f45a, exception));
            }
            this.f45a = null;
            this.f46a = null;
            this.f44a = null;
        }
    }

    protected void handleAuthError(Exception exception) {
        PeppermintLog.i("handleAuthError");
        if (this.f45a != null) {
            if (this.f44a != null) {
                this.f44a.run(a(this, this.f45a, exception));
            }
            this.f45a = null;
            this.f46a = null;
            this.f44a = null;
        }
    }

    public abstract void isAuthorized(PeppermintCallback peppermintCallback);

    public abstract boolean isConnected();

    public abstract boolean isSupportedAction(PeppermintSocialActionType peppermintSocialActionType);

    public boolean isWorking() {
        boolean z;
        synchronized (this) {
            z = this.f45a != null;
        }
        return z;
    }

    public abstract void logout(PeppermintCallback peppermintCallback);

    protected Object pepperStringOrNull(Object obj) {
        return obj == null ? JSONObject.NULL : !(obj instanceof String) ? obj.toString() : obj;
    }

    public void performAction(PeppermintSocialActionType peppermintSocialActionType, JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        switch (a()[peppermintSocialActionType.ordinal()]) {
            case o.b /*2*/:
                requestUserMeWithResponseCallback(peppermintCallback);
                return;
            case o.c /*3*/:
                requestFriendsWithResponseCallback(peppermintCallback);
                return;
            case o.d /*4*/:
                requestInvitationListWithResponseCallback(peppermintCallback);
                return;
            case f.bc /*5*/:
                shareAppActivityWithParams(jSONObject, peppermintCallback);
                return;
            case f.aL /*6*/:
                sendAppMessageWithParams(jSONObject, peppermintCallback);
                return;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
                sendAppInvitationWithParams(jSONObject, peppermintCallback);
                return;
            case WBBase64.URL_SAFE /*8*/:
                queryWithParams(jSONObject, peppermintCallback);
                return;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoTextColor /*9*/:
                sendBusinessModel(peppermintCallback);
                return;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoImageType /*10*/:
                requestInviteDialog(jSONObject, peppermintCallback);
                return;
            case R.styleable.MapAttrs_uiZoomGestures /*11*/:
                requestReceivedInvite(jSONObject, peppermintCallback);
                return;
            case R.styleable.MapAttrs_useViewLifecycle /*12*/:
                requestUserProfileImage(jSONObject, peppermintCallback);
                return;
            case R.styleable.MapAttrs_zOrderOnTop /*13*/:
                requestUserTokenWithResponseCallback(peppermintCallback);
                return;
            case f.r /*14*/:
                isAuthorized(peppermintCallback);
                return;
            case GameHelper.CLIENT_ALL /*15*/:
                logout(peppermintCallback);
                return;
            case WBBase64.NO_CLOSE /*16*/:
                requestConnectWithResponseCallback(peppermintCallback);
                return;
            default:
                return;
        }
    }

    public abstract void queryWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback);

    public abstract void requestConnectWithResponseCallback(PeppermintCallback peppermintCallback);

    public abstract void requestFriendsWithResponseCallback(PeppermintCallback peppermintCallback);

    public abstract void requestInvitationListWithResponseCallback(PeppermintCallback peppermintCallback);

    public abstract void requestInviteDialog(JSONObject jSONObject, PeppermintCallback peppermintCallback);

    public abstract void requestReceivedInvite(JSONObject jSONObject, PeppermintCallback peppermintCallback);

    public abstract void requestUserMeWithResponseCallback(PeppermintCallback peppermintCallback);

    public abstract void requestUserProfileImage(JSONObject jSONObject, PeppermintCallback peppermintCallback);

    public abstract void requestUserTokenWithResponseCallback(PeppermintCallback peppermintCallback);

    public abstract void sendAppInvitationWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback);

    public abstract void sendAppMessageWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback);

    public abstract void sendBusinessModel(PeppermintCallback peppermintCallback);

    public abstract void shareAppActivityWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback);
}
