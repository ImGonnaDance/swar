package com.com2us.peppermint.socialextension;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.net.Uri;
import android.os.Bundle;
import com.com2us.peppermint.LocalStorage;
import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.PeppermintType;
import com.com2us.peppermint.util.PeppermintLog;
import com.com2us.smon.google.service.util.GameHelper;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.GamesOptions;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;
import com.wellbia.xigncode.util.WBBase64;
import java.util.ArrayList;
import java.util.Collection;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PeppermintSocialPluginGooglePlus extends PeppermintSocialPlugin implements ConnectionCallbacks, OnConnectionFailedListener {
    private static /* synthetic */ int[] a;
    private PeppermintCallback f53a;
    a f54a = a.DEFAULT;
    private GoogleApiClient f55a;
    private String f56a = i.a;
    private boolean b;

    private enum a {
        GET_TOKEN,
        REQUEST_CONNECT,
        DEFAULT
    }

    private JSONArray a(PersonBuffer personBuffer) {
        PeppermintLog.i("friendUidsFromResult");
        try {
            int count = personBuffer.getCount();
            Collection arrayList = new ArrayList(count);
            for (int i = 0; i < count; i++) {
                arrayList.add(i, personBuffer.get(i).getId());
            }
            return new JSONArray(arrayList);
        } finally {
            personBuffer.close();
        }
    }

    static /* synthetic */ int[] b() {
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

    public static PeppermintSocialPlugin plugin() {
        try {
            return new PeppermintSocialPluginGooglePlus().initWithClientID();
        } catch (Exception e) {
            return null;
        }
    }

    public void connect() {
        PeppermintLog.i("connect");
        this.f55a.connect();
    }

    public void disconnect() {
        PeppermintLog.i("disconnect");
        if (this.f55a == null) {
            PeppermintLog.i("disconnect mGoogleApiClient is null!");
        } else if (this.f55a.isConnected()) {
            try {
                Plus.AccountApi.clearDefaultAccount(this.f55a);
                if (Boolean.valueOf(LocalStorage.getDataValueWithKey(PeppermintSocialManager.getPeppermint().getMainActivity(), PeppermintConstant.JSON_KEY_USE_PGS)).booleanValue()) {
                    Games.signOut(this.f55a);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.f55a.disconnect();
            if (PeppermintSocialPluginPGSHelper.get_instance() != null) {
                PeppermintSocialPluginPGSHelper.get_instance().disconnect();
            }
            setOauthToken(null);
        } else if (PeppermintSocialPluginPGSHelper.get_instance() != null) {
            PeppermintSocialPluginPGSHelper.get_instance().disconnectEx();
        }
    }

    public JSONObject friendsJsonForPlugin(Status status, PersonBuffer personBuffer) {
        PeppermintLog.i("friendsJsonForPlugin");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_FRIENDS));
            if (status.getStatusCode() == 0) {
                jSONObject.put(NativeProtocol.AUDIENCE_FRIENDS, a(personBuffer));
            } else {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "ConnectionResult error : " + status.getStatusCode());
                jSONObject.put(NativeProtocol.AUDIENCE_FRIENDS, JSONObject.NULL);
            }
        } catch (JSONException e) {
        }
        PeppermintLog.i("friendsJsonForPlugin json=" + jSONObject);
        return jSONObject;
    }

    public GoogleApiClient getApiClient() {
        if (this.f55a != null) {
            return this.f55a;
        }
        throw new NullPointerException("getApiClient mGoogleApiClient is null");
    }

    public String getOauthToken() {
        return this.f56a;
    }

    public String getServiceName() {
        return "googleplus";
    }

    public void handleAuthSuccess() {
        if (this.f54a == a.DEFAULT) {
            JSONObject jSONObject = new JSONObject();
            if (Plus.PeopleApi.getCurrentPerson(this.f55a) != null) {
                jSONObject = userMeFromResult(Plus.PeopleApi.getCurrentPerson(this.f55a));
            } else {
                try {
                    jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                    jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_ME));
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                    jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "Can not get current Person.");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            PeppermintLog.i("handleAuthSuccess json=" + jSONObject);
            this.f53a.run(jSONObject);
            this.f53a = null;
            return;
        }
        handleTokenRequest();
    }

    public void handleTokenRequest() {
        if (this.f54a == a.REQUEST_CONNECT) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("service_type", getServiceName());
                jSONObject.put(ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN, getOauthToken());
                PeppermintSocialManager.getPeppermint().asyncRequest(PeppermintConstant.SOCIAL_REQUEST_CONNECT, jSONObject, new B(this));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.f54a = a.DEFAULT;
        } else if (this.f54a == a.GET_TOKEN) {
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put(ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN, getOauthToken());
                jSONObject2.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                jSONObject2.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
            this.f53a.run(jSONObject2);
            this.f53a = null;
            this.f54a = a.DEFAULT;
        }
    }

    public PeppermintSocialPluginGooglePlus initWithClientID() {
        PeppermintLog.i("initWithClientID");
        Builder addScope = new Builder(PeppermintSocialManager.getPeppermint().getMainActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Plus.API).addScope(Plus.SCOPE_PLUS_LOGIN);
        if (addScope == null) {
            throw new NullPointerException("initWithClientID apiBuilder is null!");
        }
        if (Boolean.valueOf(LocalStorage.getDataValueWithKey(PeppermintSocialManager.getPeppermint().getMainActivity(), PeppermintConstant.JSON_KEY_USE_PGS)).booleanValue()) {
            addScope.addApi(Games.API, GamesOptions.builder().build()).addScope(Games.SCOPE_GAMES);
        }
        this.f55a = addScope.build();
        this.f53a = null;
        return this;
    }

    public void isAuthorized(PeppermintCallback peppermintCallback) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (isConnected()) {
                jSONObject.put(PeppermintConstant.JSON_KEY_AUTHORIZATION, 1);
            } else {
                jSONObject.put(PeppermintConstant.JSON_KEY_AUTHORIZATION, 0);
            }
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_IS_AUTHORIZED));
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
        } catch (JSONException e) {
        }
        peppermintCallback.run(jSONObject);
    }

    public boolean isConnected() {
        return this.f55a.isConnected();
    }

    public boolean isSupportedAction(PeppermintSocialActionType peppermintSocialActionType) {
        switch (b()[peppermintSocialActionType.ordinal()]) {
            case o.b /*2*/:
            case o.c /*3*/:
            case f.bc /*5*/:
            case R.styleable.MapAttrs_zOrderOnTop /*13*/:
            case GameHelper.CLIENT_ALL /*15*/:
            case WBBase64.NO_CLOSE /*16*/:
                return true;
            default:
                return false;
        }
    }

    public void logout(PeppermintCallback peppermintCallback) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (isConnected()) {
                disconnect();
                jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, 1);
            } else {
                jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, 0);
            }
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_LOGOUT));
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
        } catch (JSONException e) {
        }
        peppermintCallback.run(jSONObject);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        PeppermintLog.i("onActivityResult requestCode=" + i + " resultCode=" + i2 + " data=" + intent);
        switch (i2) {
            case PeppermintConstant.RESULT_CODE_RECONNECT_REQUIRED /*10001*/:
                disconnect();
                return;
            default:
                switch (i) {
                    case jp.co.cyberz.fox.notify.a.j /*9000*/:
                        this.b = false;
                        if (i2 == -1) {
                            try {
                                if (this.f55a == null) {
                                    throw new NullPointerException("onActivityResult is null");
                                } else if (!this.f55a.isConnected()) {
                                    this.f55a.connect();
                                    return;
                                } else {
                                    return;
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        } else if (i2 == 0) {
                            handleAuthCancel(new Exception("onActivityResult resultCode : " + String.valueOf(i2)));
                            return;
                        } else {
                            handleAuthError(new Exception("onActivityResult resultCode : " + String.valueOf(i2)));
                            return;
                        }
                    case PeppermintConstant.REQUEST_CODE_PLAY_SERVICE_ERR /*9001*/:
                        if (i2 != 0) {
                            this.f55a.connect();
                            return;
                        }
                        return;
                    case PeppermintConstant.REQUEST_CODE_RECOVER_PLAY_SERVICE_ERROR /*9002*/:
                        if (i2 == -1) {
                            handleAuthSuccess();
                            return;
                        } else if (i2 == 0) {
                            handleAuthCancel(new Exception("Peppermint GooglePlus Oauthtoken Cancel " + String.valueOf(i2)));
                            return;
                        } else {
                            handleAuthError(new Exception("Peppermint GooglePlus Oauthtoken Unknown Error "));
                            return;
                        }
                    case PeppermintConstant.REQUEST_CODE_SHARE_APP_ACTIVITY /*9100*/:
                        if (this.f53a != null) {
                            JSONObject jSONObject = new JSONObject();
                            try {
                                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                                jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SHARE_APP_ACTIVITY));
                                if (i2 == -1) {
                                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                                    jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, true);
                                } else if (i2 == 0) {
                                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                                    jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, false);
                                } else {
                                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                                    jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, intent);
                                }
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                            }
                            this.f53a.run(jSONObject);
                            this.f53a = null;
                            return;
                        }
                        return;
                    default:
                        return;
                }
        }
    }

    public void onConnected(Bundle bundle) {
        PeppermintLog.i("onConnected");
        doWork();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        PeppermintLog.i("onConnectionFailed");
        int errorCode = connectionResult.getErrorCode();
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        if (!this.b && connectionResult.hasResolution()) {
            try {
                this.b = true;
                connectionResult.startResolutionForResult(mainActivity, jp.co.cyberz.fox.notify.a.j);
            } catch (SendIntentException e) {
                this.b = false;
                this.f55a.connect();
            }
        } else if (errorCode == 1 || errorCode == 2 || errorCode == 3) {
            GooglePlayServicesUtil.getErrorDialog(errorCode, mainActivity, PeppermintConstant.REQUEST_CODE_PLAY_SERVICE_ERR, new A(this, errorCode)).show();
        } else {
            handleAuthError(new Exception("ConnectionResult error code : " + String.valueOf(errorCode)));
        }
    }

    public void onConnectionSuspended(int i) {
        this.f55a.connect();
    }

    public void queryWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
    }

    public void requestConnectWithResponseCallback(PeppermintCallback peppermintCallback) {
        this.f53a = peppermintCallback;
        this.f54a = a.REQUEST_CONNECT;
        if (getOauthToken() == i.a) {
            new GetOauthTokenTask().execute(new Object[]{PeppermintSocialManager.getPeppermint().getMainActivity(), this});
            return;
        }
        handleTokenRequest();
    }

    public void requestFriendsWithResponseCallback(PeppermintCallback peppermintCallback) {
        PeppermintLog.i("requestFriendsWithResponseCallback");
        Plus.PeopleApi.loadVisible(this.f55a, null).setResultCallback(new z(this, peppermintCallback));
    }

    public void requestInvitationListWithResponseCallback(PeppermintCallback peppermintCallback) {
    }

    public void requestInviteDialog(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
    }

    public void requestReceivedInvite(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
    }

    public void requestUserMeWithResponseCallback(PeppermintCallback peppermintCallback) {
        PeppermintLog.i("requestUserMeWithResponseCallback");
        if (peppermintCallback != null) {
            JSONObject jSONObject = new JSONObject();
            if (Plus.PeopleApi.getCurrentPerson(this.f55a) != null) {
                this.f53a = peppermintCallback;
                handleAuthSuccess();
                return;
            }
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_ME));
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "Can not get current Person.");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            PeppermintLog.i("requestUserMeWithResponseCallback json=" + jSONObject);
            peppermintCallback.run(jSONObject);
        }
    }

    public void requestUserProfileImage(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
    }

    public void requestUserTokenWithResponseCallback(PeppermintCallback peppermintCallback) {
        this.f53a = peppermintCallback;
        this.f54a = a.GET_TOKEN;
        if (getOauthToken() == i.a) {
            new GetOauthTokenTask().execute(new Object[]{PeppermintSocialManager.getPeppermint().getMainActivity(), this});
            return;
        }
        handleTokenRequest();
    }

    public void sendAppInvitationWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
    }

    public void sendAppMessageWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
    }

    public void sendBusinessModel(PeppermintCallback peppermintCallback) {
    }

    public void setOauthToken(String str) {
        this.f56a = str;
    }

    public void shareAppActivityWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        CharSequence charSequence;
        PeppermintLog.i("shareAppActivityWithParams json=" + jSONObject);
        this.f53a = peppermintCallback;
        try {
            charSequence = jSONObject.has("text") ? (String) jSONObject.get("text") : i.a;
        } catch (JSONException e) {
            e.printStackTrace();
            charSequence = null;
        }
        PlusShare.Builder builder = new PlusShare.Builder(PeppermintSocialManager.getPeppermint().getMainActivity());
        builder.setText(charSequence);
        builder.addCallToAction("INVITE", Uri.parse("http://m.com2us.com/link/store?appid=" + PeppermintSocialManager.getPeppermint().getMainActivity().getPackageName()), "/hub/deeplinkID/");
        builder.setContentUrl(Uri.parse("http://m.com2us.com/link/store?appid=" + PeppermintSocialManager.getPeppermint().getMainActivity().getPackageName()));
        builder.setContentDeepLinkId("/hub/deeplinkID/", null, null, null);
        PeppermintSocialManager.getPeppermint().getMainActivity().startActivityForResult(builder.getIntent(), PeppermintConstant.REQUEST_CODE_SHARE_APP_ACTIVITY);
    }

    protected JSONObject userFromResult(Person person) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_UID, person.getId());
            jSONObject.put(PeppermintConstant.JSON_KEY_NAME, person.getDisplayName());
            jSONObject.put(PeppermintConstant.JSON_KEY_PICTURE, person.getImage().getUrl());
            jSONObject.put(PeppermintConstant.JSON_KEY_COUNTRY, JSONObject.NULL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }

    protected JSONObject userMeFromResult(Person person) {
        PeppermintLog.i("userMeFromResult");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_ME));
            jSONObject.put(PeppermintConstant.JSON_KEY_UID, person.getId());
            jSONObject.put(PeppermintConstant.JSON_KEY_EMAIL, Plus.AccountApi.getAccountName(this.f55a));
            jSONObject.put(PeppermintConstant.JSON_KEY_BIRTHDAY, person.getBirthday() == null ? JSONObject.NULL : person.getBirthday());
            jSONObject.put(PeppermintConstant.JSON_KEY_GENDER, person.getGender());
            jSONObject.put(PeppermintConstant.JSON_KEY_COUNTRY, person.getCurrentLocation());
            jSONObject.put(PeppermintConstant.JSON_KEY_LANGUAGE, person.getLanguage());
            jSONObject.put(PeppermintConstant.JSON_KEY_SCREEN_NAME, person.getDisplayName());
            jSONObject.put(PeppermintConstant.JSON_KEY_USERNAME, person.getNickname());
            jSONObject.put(PeppermintConstant.JSON_KEY_SECOND_EMAIL, JSONObject.NULL);
            jSONObject.put(PeppermintConstant.JSON_KEY_BIO, person.getTagline() == null ? JSONObject.NULL : person.getTagline());
            jSONObject.put(ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN, this.f56a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject;
    }
}
