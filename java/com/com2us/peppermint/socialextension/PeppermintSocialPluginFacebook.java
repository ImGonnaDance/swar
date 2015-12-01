package com.com2us.peppermint.socialextension;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.PeppermintType;
import com.com2us.peppermint.util.PeppermintLog;
import com.com2us.peppermint.util.PeppermintResource;
import com.com2us.peppermint.util.PeppermintUtil;
import com.com2us.smon.google.service.util.GameHelper;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionState;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.wellbia.xigncode.util.WBBase64;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import jp.co.cyberz.fox.a.a.i;
import jp.co.cyberz.fox.notify.a;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PeppermintSocialPluginFacebook extends PeppermintSocialPlugin {
    private static /* synthetic */ int[] a;
    private static final String[] f67a = new String[]{"basic_info", PeppermintConstant.JSON_KEY_EMAIL, "user_birthday"};
    private static /* synthetic */ int[] b;
    private static final String[] f68b = new String[]{"public_profile", "user_friends", PeppermintConstant.JSON_KEY_EMAIL};
    private PeppermintCallback f69a = null;
    private StatusCallback f70a = new a(this);
    private JSONObject f71a = null;
    private boolean f72b = false;

    private JSONArray a(Response response) {
        Collection arrayList;
        JSONException e;
        PeppermintLog.i("usersFromResult");
        try {
            JSONArray jSONArray = response.getGraphObject().getInnerJSONObject().getJSONArray(PeppermintConstant.JSON_KEY_DATA);
            int length = jSONArray.length();
            arrayList = new ArrayList(length);
            int i = 0;
            while (i < length) {
                try {
                    arrayList.add(userFromResult(jSONArray.getJSONObject(i)));
                    i++;
                } catch (JSONException e2) {
                    e = e2;
                }
            }
        } catch (JSONException e3) {
            JSONException jSONException = e3;
            arrayList = null;
            e = jSONException;
            e.printStackTrace();
            return new JSONArray(arrayList);
        }
        return new JSONArray(arrayList);
    }

    private JSONArray a(JSONArray jSONArray) {
        PeppermintLog.i("friendUidsFromResult");
        int length = jSONArray.length();
        Collection arrayList = new ArrayList(length);
        for (int i = 0; i < length; i++) {
            try {
                arrayList.add(i, jSONArray.getJSONObject(i).getString(PeppermintConstant.JSON_KEY_ID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return new JSONArray(arrayList);
    }

    private JSONObject m2a(Response response) {
        JSONObject jSONObject;
        JSONException jSONException;
        JSONException jSONException2;
        PeppermintLog.i("requestUserMeWithResponseCallback");
        PeppermintSocialPluginFacebook jSONObject2 = new JSONObject();
        try {
            if (response.getError() != null) {
                jSONObject2.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject2.put(PeppermintConstant.JSON_KEY_ERROR_MSG, response.getError().getErrorMessage());
                jSONObject = jSONObject2;
            } else {
                JSONObject a = a(response.getGraphObject().getInnerJSONObject());
                try {
                    a.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                    jSONObject = a;
                } catch (JSONException e) {
                    jSONException = e;
                    jSONObject = a;
                    jSONException2 = jSONException;
                    jSONException2.printStackTrace();
                    PeppermintLog.i("userMeJsonForPlugin json=" + jSONObject);
                    return jSONObject;
                }
            }
            try {
                jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_USER_ME));
            } catch (JSONException e2) {
                jSONException2 = e2;
                jSONException2.printStackTrace();
                PeppermintLog.i("userMeJsonForPlugin json=" + jSONObject);
                return jSONObject;
            }
        } catch (JSONException e3) {
            jSONException = e3;
            jSONObject = jSONObject2;
            jSONException2 = jSONException;
            jSONException2.printStackTrace();
            PeppermintLog.i("userMeJsonForPlugin json=" + jSONObject);
            return jSONObject;
        }
        PeppermintLog.i("userMeJsonForPlugin json=" + jSONObject);
        return jSONObject;
    }

    private JSONObject a(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(PeppermintConstant.JSON_KEY_UID, jSONObject.has(PeppermintConstant.JSON_KEY_ID) ? jSONObject.get(PeppermintConstant.JSON_KEY_ID) : JSONObject.NULL);
            jSONObject2.put(PeppermintConstant.JSON_KEY_EMAIL, jSONObject.has(PeppermintConstant.JSON_KEY_EMAIL) ? jSONObject.get(PeppermintConstant.JSON_KEY_EMAIL) : JSONObject.NULL);
            jSONObject2.put(PeppermintConstant.JSON_KEY_GENDER, jSONObject.has(PeppermintConstant.JSON_KEY_GENDER) ? genderCodeFromString((String) jSONObject.get(PeppermintConstant.JSON_KEY_GENDER)) : JSONObject.NULL);
            if (jSONObject.has("locale")) {
                String[] split = jSONObject.get("locale").toString().split("_");
                jSONObject2.put(PeppermintConstant.JSON_KEY_LANGUAGE, split[0]);
                jSONObject2.put(PeppermintConstant.JSON_KEY_COUNTRY, split[1]);
            } else {
                jSONObject2.put(PeppermintConstant.JSON_KEY_LANGUAGE, JSONObject.NULL);
                jSONObject2.put(PeppermintConstant.JSON_KEY_COUNTRY, JSONObject.NULL);
            }
            jSONObject2.put(PeppermintConstant.JSON_KEY_SCREEN_NAME, jSONObject.has(PeppermintConstant.JSON_KEY_NAME) ? jSONObject.get(PeppermintConstant.JSON_KEY_NAME) : JSONObject.NULL);
            Object obj = jSONObject.has(PeppermintConstant.JSON_KEY_USERNAME) ? (String) jSONObject.get(PeppermintConstant.JSON_KEY_USERNAME) : null;
            if (obj != null) {
                jSONObject2.put(PeppermintConstant.JSON_KEY_USERNAME, obj);
                jSONObject2.put(PeppermintConstant.JSON_KEY_SECOND_EMAIL, new StringBuilder(String.valueOf(obj)).append("@facebook.com").toString());
            } else {
                jSONObject2.put(PeppermintConstant.JSON_KEY_USERNAME, JSONObject.NULL);
                jSONObject2.put(PeppermintConstant.JSON_KEY_SECOND_EMAIL, JSONObject.NULL);
            }
            jSONObject2.put(PeppermintConstant.JSON_KEY_BIO, jSONObject.has(PeppermintConstant.JSON_KEY_BIO) ? (String) jSONObject.get(PeppermintConstant.JSON_KEY_BIO) : JSONObject.NULL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("userMeFromResult json=" + jSONObject2);
        return jSONObject2;
    }

    private JSONObject a(boolean z, Bundle bundle, FacebookException facebookException) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_SHARE_APP_ACTIVITY));
            if (facebookException != null) {
                if (facebookException instanceof FacebookOperationCanceledException) {
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_DIALOG_CLOSE);
                    jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "user cancelled");
                    jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, z);
                } else {
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                    jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, facebookException.toString());
                }
                PeppermintLog.i("shareAppAcitivityJsonForPlugin json=" + jSONObject);
                return jSONObject;
            }
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
            jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, z);
            PeppermintLog.i("shareAppAcitivityJsonForPlugin json=" + jSONObject);
            return jSONObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void a(Context context) {
        PeppermintLog.i("clearFacebookCookies");
        a(context, "facebook.com");
        a(context, ".facebook.com");
        a(context, "https://facebook.com");
        a(context, "https://.facebook.com");
    }

    private void a(Context context, String str) {
        PeppermintLog.i("clearCookiesForDomain domain=" + str);
        CookieSyncManager.createInstance(context).sync();
        CookieManager instance = CookieManager.getInstance();
        String cookie = instance.getCookie(str);
        if (cookie != null) {
            for (String split : cookie.split(";")) {
                String[] split2 = split.split("=");
                if (split2.length > 0) {
                    instance.setCookie(str, new StringBuilder(String.valueOf(split2[0].trim())).append("=;expires=Sat, 1 Jan 2000 00:00:01 UTC;").toString());
                }
            }
            instance.removeExpiredCookie();
        }
    }

    private void a(PeppermintCallback peppermintCallback) {
        Request request = new Request(Session.getActiveSession(), "/me/ids_for_business", null, HttpMethod.GET, new c(this, peppermintCallback));
        PeppermintSocialManager.getPeppermint().getMainActivity().runOnUiThread(new d(this, new RequestAsyncTask(request)));
    }

    private void a(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        JSONObject jSONObject2;
        String str;
        JSONException jSONException;
        JSONObject jSONObject3;
        JSONException jSONException2;
        HttpMethod httpMethod;
        Request request;
        PeppermintLog.i("runQueryWithParams");
        String str2 = "query";
        str2 = "method";
        str2 = PeppermintConstant.JSON_KEY_PARAMS;
        String str3 = i.a;
        Bundle bundle = new Bundle();
        String str4 = i.a;
        try {
            str3 = jSONObject.has("query") ? (String) jSONObject.get("query") : i.a;
            str4 = jSONObject.has("method") ? (String) jSONObject.get("method") : i.a;
            if (jSONObject.has(PeppermintConstant.JSON_KEY_PARAMS)) {
                JSONObject jSONObject4 = jSONObject.getJSONObject(PeppermintConstant.JSON_KEY_PARAMS);
                try {
                    Iterator keys = jSONObject4.keys();
                    while (keys.hasNext()) {
                        str2 = (String) keys.next();
                        bundle.putString(str2, jSONObject4.get(str2).toString());
                    }
                    str2 = str4;
                    jSONObject2 = jSONObject4;
                    str = str3;
                } catch (JSONException e) {
                    jSONException = e;
                    str2 = str4;
                    jSONObject3 = jSONObject4;
                    str = str3;
                    jSONException2 = jSONException;
                }
            } else {
                str2 = str4;
                jSONObject2 = null;
                str = str3;
            }
        } catch (JSONException e2) {
            jSONException = e2;
            str2 = str4;
            jSONObject3 = null;
            str = str3;
            jSONException2 = jSONException;
            jSONException2.printStackTrace();
            jSONObject2 = jSONObject3;
            httpMethod = str2.equalsIgnoreCase("get") ? str2.equalsIgnoreCase("post") ? str2.equalsIgnoreCase("delete") ? HttpMethod.GET : HttpMethod.DELETE : HttpMethod.POST : HttpMethod.GET;
            PeppermintLog.i("runQueryWithParams isPostQuery=" + (httpMethod.equals(HttpMethod.POST)));
            request = new Request(Session.getActiveSession(), str, bundle, httpMethod, new y(this, jSONObject2, peppermintCallback));
            PeppermintSocialManager.getPeppermint().getMainActivity().runOnUiThread(new b(this, new RequestAsyncTask(request)));
        }
        if (str2.equalsIgnoreCase("get")) {
            if (str2.equalsIgnoreCase("post")) {
                if (str2.equalsIgnoreCase("delete")) {
                }
            }
        }
        if (httpMethod.equals(HttpMethod.POST)) {
        }
        PeppermintLog.i("runQueryWithParams isPostQuery=" + (httpMethod.equals(HttpMethod.POST)));
        request = new Request(Session.getActiveSession(), str, bundle, httpMethod, new y(this, jSONObject2, peppermintCallback));
        PeppermintSocialManager.getPeppermint().getMainActivity().runOnUiThread(new b(this, new RequestAsyncTask(request)));
    }

    private boolean a() {
        Session activeSession = Session.getActiveSession();
        if (activeSession == null || !activeSession.isOpened()) {
            return false;
        }
        List permissions = activeSession.getPermissions();
        for (int i = 0; i < permissions.size(); i++) {
            if (((String) permissions.get(i)).equals("publish_actions")) {
                return true;
            }
        }
        return false;
    }

    private boolean m3a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return false;
        }
        String toUpperCase;
        String str = i.a;
        try {
            toUpperCase = (jSONObject.has("method") ? (String) jSONObject.get("method") : i.a).toUpperCase(Locale.getDefault());
        } catch (JSONException e) {
            JSONException jSONException = e;
            toUpperCase = str;
            jSONException.printStackTrace();
        }
        return toUpperCase.equals("POST");
    }

    private JSONObject b(Response response) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (response.getError() != null) {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, response.getError().getErrorMessage());
            } else {
                jSONObject.put(PeppermintConstant.JSON_KEY_DATA, response.getGraphObject().getInnerJSONObject().getJSONArray(PeppermintConstant.JSON_KEY_DATA));
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
            }
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_RECEIVED_INVITE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("receivedInviteJsonForPlugin json=" + jSONObject);
        return jSONObject;
    }

    private void b(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("requestPublishPermissionForSocialQueryWithParams");
        Session.getActiveSession().requestNewPublishPermissions(new NewPermissionsRequest(PeppermintSocialManager.getPeppermint().getMainActivity(), Arrays.asList(new String[]{"publish_actions"})).setDefaultAudience(SessionDefaultAudience.FRIENDS).setCallback(this.f70a).setRequestCode((int) PeppermintConstant.REQUEST_CODE_REAUTH_ACTIVITY));
    }

    static /* synthetic */ int[] b() {
        int[] iArr = a;
        if (iArr == null) {
            iArr = new int[SessionState.values().length];
            try {
                iArr[SessionState.CLOSED.ordinal()] = 7;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[SessionState.CLOSED_LOGIN_FAILED.ordinal()] = 6;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[SessionState.CREATED.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[SessionState.CREATED_TOKEN_LOADED.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[SessionState.OPENED.ordinal()] = 4;
            } catch (NoSuchFieldError e5) {
            }
            try {
                iArr[SessionState.OPENED_TOKEN_UPDATED.ordinal()] = 5;
            } catch (NoSuchFieldError e6) {
            }
            try {
                iArr[SessionState.OPENING.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            a = iArr;
        }
        return iArr;
    }

    private JSONObject c(Response response) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (response.getError() != null) {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, response.getError().getErrorMessage());
            } else {
                jSONObject.put(a.g, response.getGraphObject().getInnerJSONObject().getJSONObject(PeppermintConstant.JSON_KEY_DATA).getString(a.g));
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
            }
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_USER_PROFILE_IMAGE));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("userProfileImageForPlugin json=" + jSONObject);
        return jSONObject;
    }

    static /* synthetic */ int[] c() {
        int[] iArr = b;
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
            b = iArr;
        }
        return iArr;
    }

    private JSONObject d(Response response) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_FRIENDS));
            if (response.getError() != null) {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, response.getError().getErrorMessage());
                jSONObject.put(NativeProtocol.AUDIENCE_FRIENDS, JSONObject.NULL);
            } else {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                jSONObject.put(NativeProtocol.AUDIENCE_FRIENDS, a(response.getGraphObject().getInnerJSONObject().getJSONArray(PeppermintConstant.JSON_KEY_DATA)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("friendsJsonForPlugin json=" + jSONObject);
        return jSONObject;
    }

    private JSONObject e(Response response) {
        JSONObject jSONObject = new JSONObject();
        try {
            if (response.getError() != null) {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, response.getError().getErrorMessage());
            } else {
                jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                jSONObject.put(PeppermintConstant.JSON_KEY_USERS, a(response));
            }
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
            jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_REQUEST_INVITATION_LIST));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("invitationListJsonForPlugin json=" + jSONObject);
        return jSONObject;
    }

    public static PeppermintSocialPlugin plugin() {
        Context mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        try {
            mainActivity.getResources().getString(PeppermintResource.getID(mainActivity, "R.string.applicationId"));
            return new PeppermintSocialPluginFacebook();
        } catch (NotFoundException e) {
            return null;
        }
    }

    public void connect() {
        PeppermintLog.i("connect!");
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        mainActivity.runOnUiThread(new l(this, mainActivity));
    }

    public void disconnect() {
        PeppermintLog.i("disconnect!");
        try {
            if (Session.getActiveSession() != null) {
                Session.getActiveSession().closeAndClearTokenInformation();
            } else {
                Session build = new Builder(PeppermintSocialManager.getPeppermint().getMainActivity().getApplicationContext()).build();
                if (build != null) {
                    build.closeAndClearTokenInformation();
                }
            }
        } catch (Exception e) {
            PeppermintLog.i("disconnect close session error!!");
        }
        a(PeppermintSocialManager.getPeppermint().getMainActivity());
    }

    public String getServiceName() {
        return "facebook";
    }

    public void handleFBSessionStateChangeWithSession(Session session, SessionState sessionState, Exception exception) {
        PeppermintLog.i("handleFBSessionStateChangeWithSession session=" + session + " status=" + sessionState + " exception=" + exception);
        JSONObject jSONObject;
        if (exception != null) {
            if (this.f69a != null) {
                jSONObject = new JSONObject();
                try {
                    jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                    jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_QUERY));
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                    jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, exception.getMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.f69a.run(jSONObject);
                this.f72b = false;
                this.f71a = null;
                this.f69a = null;
            } else if (exception instanceof FacebookOperationCanceledException) {
                handleAuthCancel(exception);
            } else {
                handleAuthError(exception);
            }
        } else if (this.f72b && sessionState == SessionState.OPENED_TOKEN_UPDATED) {
            if (a()) {
                a(this.f71a, this.f69a);
            } else {
                jSONObject = new JSONObject();
                try {
                    jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                    jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_QUERY));
                    jSONObject.put(PeppermintConstant.JSON_KEY_PARAMS, this.f71a);
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                    jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "Permission denied.");
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                this.f69a.run(jSONObject);
            }
            this.f72b = false;
            this.f71a = null;
            this.f69a = null;
        } else {
            switch (b()[sessionState.ordinal()]) {
                case o.d /*4*/:
                    doWork();
                    return;
                default:
                    return;
            }
        }
    }

    public void isAuthorized(PeppermintCallback peppermintCallback) {
        Session activeSession = Session.getActiveSession();
        JSONObject jSONObject = new JSONObject();
        if (activeSession != null) {
            try {
                if (activeSession.isOpened()) {
                    jSONObject.put(PeppermintConstant.JSON_KEY_AUTHORIZATION, 1);
                    jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                    jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_IS_AUTHORIZED));
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                    peppermintCallback.run(jSONObject);
                }
            } catch (JSONException e) {
            }
        }
        jSONObject.put(PeppermintConstant.JSON_KEY_AUTHORIZATION, 0);
        jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
        jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_IS_AUTHORIZED));
        jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
        peppermintCallback.run(jSONObject);
    }

    public boolean isConnected() {
        return Session.getActiveSession() != null && Session.getActiveSession().isOpened();
    }

    public boolean isSupportedAction(PeppermintSocialActionType peppermintSocialActionType) {
        switch (c()[peppermintSocialActionType.ordinal()]) {
            case o.b /*2*/:
            case o.c /*3*/:
            case o.d /*4*/:
            case f.bc /*5*/:
            case f.aL /*6*/:
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
            case WBBase64.URL_SAFE /*8*/:
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoTextColor /*9*/:
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoImageType /*10*/:
            case R.styleable.MapAttrs_uiZoomGestures /*11*/:
            case R.styleable.MapAttrs_useViewLifecycle /*12*/:
            case R.styleable.MapAttrs_zOrderOnTop /*13*/:
            case f.r /*14*/:
            case GameHelper.CLIENT_ALL /*15*/:
            case WBBase64.NO_CLOSE /*16*/:
                return true;
            default:
                return false;
        }
    }

    public void logout(PeppermintCallback peppermintCallback) {
        Session activeSession = Session.getActiveSession();
        JSONObject jSONObject = new JSONObject();
        if (activeSession != null) {
            try {
                if (activeSession.isOpened()) {
                    disconnect();
                    jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, 1);
                    jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                    jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_LOGOUT));
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                    peppermintCallback.run(jSONObject);
                }
            } catch (JSONException e) {
            }
        }
        jSONObject.put(PeppermintConstant.JSON_KEY_RESULT, 0);
        jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
        jSONObject.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_LOGOUT));
        jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
        peppermintCallback.run(jSONObject);
    }

    public void queryWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        PeppermintLog.i("queryWithParams");
        if (!a(jSONObject) || a()) {
            a(jSONObject, peppermintCallback);
            return;
        }
        this.f72b = true;
        this.f71a = jSONObject;
        this.f69a = peppermintCallback;
        b(jSONObject, peppermintCallback);
    }

    public void requestConnectWithResponseCallback(PeppermintCallback peppermintCallback) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("service_type", getServiceName());
            jSONObject.put(ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN, Session.getActiveSession().getAccessToken());
            jSONObject.put(PeppermintConstant.JSON_KEY_APP_ID, Session.getActiveSession().getApplicationId());
            PeppermintSocialManager.getPeppermint().asyncRequest(PeppermintConstant.SOCIAL_REQUEST_CONNECT, jSONObject, new k(this, peppermintCallback));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestFriendsWithResponseCallback(PeppermintCallback peppermintCallback) {
        PeppermintSocialManager.getPeppermint().getMainActivity().runOnUiThread(new o(this, peppermintCallback));
    }

    public void requestInvitationListWithResponseCallback(PeppermintCallback peppermintCallback) {
        PeppermintSocialManager.getPeppermint().getMainActivity().runOnUiThread(new q(this, peppermintCallback));
    }

    public void requestInviteDialog(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        Bundle bundleFrojObj = PeppermintUtil.getBundleFrojObj(jSONObject);
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        if (bundleFrojObj.containsKey("to")) {
            bundleFrojObj.remove("to");
        }
        if (!bundleFrojObj.containsKey("filters")) {
            bundleFrojObj.putString("filters", "app_non_users");
        }
        mainActivity.runOnUiThread(new e(this, mainActivity, bundleFrojObj, peppermintCallback));
    }

    public void requestReceivedInvite(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        int i = 1;
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        if (jSONObject != null) {
            try {
                if (jSONObject.has(PeppermintConstant.JSON_KEY_LIMIT) && jSONObject.getInt(PeppermintConstant.JSON_KEY_LIMIT) > 1) {
                    i = jSONObject.getInt(PeppermintConstant.JSON_KEY_LIMIT);
                }
            } catch (JSONException e) {
            }
        }
        mainActivity.runOnUiThread(new g(this, i, peppermintCallback));
    }

    public void requestUserMeWithResponseCallback(PeppermintCallback peppermintCallback) {
        PeppermintSocialManager.getPeppermint().getMainActivity().runOnUiThread(new m(this, peppermintCallback));
    }

    public void requestUserProfileImage(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        String str = null;
        try {
            str = jSONObject.has(PeppermintConstant.JSON_KEY_USER_ID) ? jSONObject.getString(PeppermintConstant.JSON_KEY_USER_ID) : i.a;
        } catch (JSONException e) {
        }
        try {
            if (str.equalsIgnoreCase(i.a) || str == null) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
                jSONObject2.put(PeppermintConstant.JSON_KEY_TYPE, PeppermintSocialAction.nameFromType(PeppermintSocialActionType.PEPPERMINT_SOCIAL_ACTION_USER_PROFILE_IMAGE));
                jSONObject2.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_REQUEST_FAIL);
                jSONObject2.put(PeppermintConstant.JSON_KEY_ERROR_MSG, "'user_id' field is null");
                peppermintCallback.run(jSONObject2);
                return;
            }
        } catch (JSONException e2) {
        }
        mainActivity.runOnUiThread(new i(this, str, peppermintCallback));
    }

    public void requestUserTokenWithResponseCallback(PeppermintCallback peppermintCallback) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(ServerProtocol.DIALOG_RESPONSE_TYPE_TOKEN, Session.getActiveSession().getAccessToken());
            jSONObject.put(PeppermintConstant.JSON_KEY_APP_ID, Session.getActiveSession().getApplicationId());
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
            jSONObject.put(PeppermintConstant.JSON_KEY_SERVICE, getServiceName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        peppermintCallback.run(jSONObject);
    }

    public void sendAppInvitationWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        Bundle bundleFrojObj = PeppermintUtil.getBundleFrojObj(jSONObject);
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        mainActivity.runOnUiThread(new w(this, mainActivity, bundleFrojObj, peppermintCallback));
    }

    public void sendAppMessageWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        Bundle bundleFrojObj = PeppermintUtil.getBundleFrojObj(jSONObject);
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        mainActivity.runOnUiThread(new u(this, mainActivity, bundleFrojObj, peppermintCallback));
    }

    public void sendBusinessModel(PeppermintCallback peppermintCallback) {
        a(peppermintCallback);
    }

    public void shareAppActivityWithParams(JSONObject jSONObject, PeppermintCallback peppermintCallback) {
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        mainActivity.runOnUiThread(new s(this, jSONObject, mainActivity, peppermintCallback));
    }

    public JSONObject userFromResult(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put(PeppermintConstant.JSON_KEY_UID, jSONObject.get(PeppermintConstant.JSON_KEY_ID));
            jSONObject2.put(PeppermintConstant.JSON_KEY_ID, jSONObject.get(PeppermintConstant.JSON_KEY_ID));
            jSONObject2.put(PeppermintConstant.JSON_KEY_NAME, jSONObject.get(PeppermintConstant.JSON_KEY_NAME));
            jSONObject2.put(PeppermintConstant.JSON_KEY_PICTURE, jSONObject.getJSONObject(PeppermintConstant.JSON_KEY_PICTURE).getJSONObject(PeppermintConstant.JSON_KEY_DATA).getString(a.g));
            jSONObject2.put(PeppermintConstant.JSON_KEY_COUNTRY, JSONObject.NULL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject2;
    }
}
