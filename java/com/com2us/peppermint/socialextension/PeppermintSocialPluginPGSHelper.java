package com.com2us.peppermint.socialextension;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import com.com2us.peppermint.LocalStorage;
import com.com2us.peppermint.PeppermintCallback;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.PeppermintType;
import com.com2us.peppermint.util.PeppermintLog;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Games.GamesOptions;
import com.google.android.gms.games.Player;
import java.util.Locale;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONException;
import org.json.JSONObject;

public class PeppermintSocialPluginPGSHelper implements ConnectionCallbacks, OnConnectionFailedListener {
    private static Handler a;
    private static PeppermintSocialPluginPGSHelper f33a = null;
    private static GoogleApiClient f34a;
    private static boolean b = false;
    private static final int c = 0;
    private int f35a = 0;
    private PeppermintCallback f36a;
    private Boolean f37a = Boolean.valueOf(false);
    private String f38a = i.a;
    private boolean f39a;
    private int f40b;
    private String f41b;
    private String f42c;
    private String d = null;

    public PeppermintSocialPluginPGSHelper() {
        PeppermintLog.i("PeppermintSocialPluginPGSHelper");
        f34a = new Builder(PeppermintSocialManager.getPeppermint().getMainActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(Games.API, GamesOptions.builder().build()).addScope(Games.SCOPE_GAMES).build();
        set_instance(this);
        CreatePGSHandler();
    }

    private JSONObject a(Exception exception) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_AUTH_FAIL);
            if (exception != null) {
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, exception.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("authFailJsonForPlugin json=" + jSONObject);
        return jSONObject;
    }

    private void a() {
        PeppermintLog.i("connect");
        try {
            if (f34a == null) {
                throw new NullPointerException("mGoogleApiClient is null");
            } else if (f34a.isConnected()) {
                onConnected(null);
            } else {
                f34a.connect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JSONObject b(Exception exception) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, PeppermintType.HUB_E_SOCIAL_DIALOG_CLOSE);
            if (exception != null) {
                jSONObject.put(PeppermintConstant.JSON_KEY_ERROR_MSG, exception.getMessage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PeppermintLog.i("authCancelJsonForPlugin json=" + jSONObject);
        return jSONObject;
    }

    private void b() {
        PeppermintLog.i("resetPGS");
        Context mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        this.f35a = 0;
        LocalStorage.setDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_RETRY_PGS, String.valueOf(this.f35a));
        LocalStorage.removeDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_PGS_ACCOUNT);
        LocalStorage.setDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_LOGOUT_HISTORY, String.valueOf(true));
        this.d = null;
    }

    private void c() {
        PeppermintLog.i("handleAuthSuccessOK");
        Player currentPlayer = Games.Players.getCurrentPlayer(f34a);
        this.f41b = currentPlayer.getDisplayName();
        this.d = currentPlayer.getPlayerId();
        this.f42c = Games.getCurrentAccountName(f34a);
        Context mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        String dataValueWithKey = LocalStorage.getDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_LAST_ACCOUNT);
        PeppermintLog.i("handleAuthSuccessOK lastEmail=" + dataValueWithKey + "currentEmail=" + this.f42c);
        if (this.f42c.compareTo(dataValueWithKey) == 0 || dataValueWithKey.isEmpty()) {
            setIsPGS(Boolean.valueOf(true));
            if (this.f36a != null) {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(PeppermintConstant.JSON_KEY_PLAYER_NAME, this.f41b);
                    jSONObject.put(PeppermintConstant.JSON_KEY_PLAYERID, this.d);
                    jSONObject.put(NativeProtocol.BRIDGE_ARG_ERROR_CODE, 0);
                    jSONObject.put(PeppermintConstant.JSON_KEY_DID_LOGOUT, false);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                this.f36a.run(jSONObject);
            }
            this.f36a = null;
        } else {
            setOnlycookieclear(true);
            PeppermintSocialManager.getPeppermint().logout(new F(this, mainActivity));
        }
        LocalStorage.setDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_LAST_ACCOUNT, this.f42c);
    }

    public static PeppermintSocialPluginPGSHelper get_instance() {
        return f33a;
    }

    public static boolean isOnlyCookieClear() {
        return b;
    }

    public static void setOnlycookieclear(boolean z) {
        PeppermintLog.i("setOnlycookieclear bOnlyCookieClear=" + z);
        b = z;
    }

    public static void set_instance(PeppermintSocialPluginPGSHelper peppermintSocialPluginPGSHelper) {
        f33a = peppermintSocialPluginPGSHelper;
    }

    public void CreatePGSHandler() {
        PeppermintLog.i("CreatePGSHandler");
        a = new C(this, PeppermintSocialManager.getPeppermint().getMainActivity().getMainLooper());
    }

    public int PGSLoginProc(PeppermintCallback peppermintCallback) {
        Context mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        PeppermintLog.i("PGSLoginProc logout_history=" + Boolean.valueOf(LocalStorage.getDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_LOGOUT_HISTORY)).booleanValue());
        this.f36a = peppermintCallback;
        if (LocalStorage.getDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_RETRY_PGS).isEmpty()) {
            this.f35a = 0;
        } else {
            this.f35a = Integer.parseInt(LocalStorage.getDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_RETRY_PGS));
        }
        TelephonyManager telephonyManager = (TelephonyManager) mainActivity.getSystemService("phone");
        String networkOperator = telephonyManager.getNetworkOperator();
        Object obj = a.d;
        if (!networkOperator.isEmpty()) {
            obj = networkOperator.substring(0, 3);
        }
        String networkCountryIso = telephonyManager.getNetworkCountryIso();
        JSONObject jSONObject = new JSONObject();
        this.f40b = AccountManager.get(mainActivity).getAccountsByType("com.google").length;
        PeppermintLog.i("PGSLoginProc Account Number : " + String.valueOf(this.f40b));
        PeppermintLog.i("PGSLoginProc PGS retry Number : " + this.f35a);
        PeppermintLog.i("============================================================");
        try {
            jSONObject.put("gameindex", PeppermintSocialManager.getPeppermint().getGameIndex());
            jSONObject.put(PeppermintConstant.JSON_KEY_APP_ID, PeppermintSocialManager.getPeppermint().getAppId());
            jSONObject.put("mcc", obj);
            jSONObject.put("dcc", networkCountryIso);
            jSONObject.put(PeppermintConstant.JSON_KEY_LANGUAGE, Locale.getDefault().getLanguage());
            jSONObject.put(PeppermintConstant.JSON_KEY_COUNTRY, Locale.getDefault().getCountry());
            jSONObject.put("accountnum", this.f40b);
            jSONObject.put("retrypgs", this.f35a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        checkServerPGSOn(jSONObject);
        return 0;
    }

    public void checkServerPGSOn(JSONObject jSONObject) {
        PeppermintSocialManager.getPeppermint().asyncRequest("device/use_pgs", jSONObject, new E(this, PeppermintSocialManager.getPeppermint().getMainActivity()));
    }

    public void disconnect() {
        PeppermintLog.i("disconnect");
        try {
            if (f34a == null) {
                throw new NullPointerException("disconnect mGoogleApiClient is null");
            }
            if (f34a.isConnected()) {
                f34a.disconnect();
            }
            b();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnectEx() {
        PeppermintLog.i("disconnectEx");
        if (f34a == null) {
            throw new NullPointerException("disconnectEx mGoogleApiClient is null");
        }
        if (f34a.isConnected()) {
            try {
                Games.signOut(f34a);
            } catch (Exception e) {
                e.printStackTrace();
            }
            f34a.disconnect();
        }
        b();
    }

    public String getAccountNum() {
        this.f40b = AccountManager.get(PeppermintSocialManager.getPeppermint().getMainActivity()).getAccountsByType("com.google").length;
        return String.valueOf(this.f40b);
    }

    public GoogleApiClient getApiClient() {
        if (f34a != null) {
            return f34a;
        }
        throw new NullPointerException("getApiClient mGoogleApiClient is null");
    }

    public String getEmail() {
        return this.f42c;
    }

    public Boolean getIsPGS() {
        return this.f37a;
    }

    public String getLogoutHistory() {
        return Boolean.valueOf(LocalStorage.getDataValueWithKey(PeppermintSocialManager.getPeppermint().getMainActivity(), PeppermintConstant.JSON_KEY_LOGOUT_HISTORY)).booleanValue() ? String.valueOf(true) : String.valueOf(false);
    }

    public String getName() {
        return this.f41b;
    }

    public String getOauthToken() {
        return this.f38a;
    }

    public String getUid() {
        return this.d;
    }

    protected void handleAuthCancel(Exception exception) {
        PeppermintLog.i("handleAuthCancel");
        Context mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        if (this.f36a != null) {
            this.f36a.run(b(exception));
        }
        setIsPGS(Boolean.valueOf(false));
        try {
            if (f34a == null) {
                throw new NullPointerException("handleAuthCancel mGoogleApiClient is null");
            }
            if (f34a.isConnected()) {
                Games.signOut(f34a);
                f34a.disconnect();
            }
            this.f36a = null;
            if (LocalStorage.getDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_RETRY_PGS) == null || LocalStorage.getDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_RETRY_PGS) == i.a) {
                this.f35a = 0;
            } else {
                this.f35a = Integer.parseInt(LocalStorage.getDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_RETRY_PGS));
            }
            if (this.f35a <= 255) {
                this.f35a++;
            }
            LocalStorage.setDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_RETRY_PGS, String.valueOf(this.f35a));
            PeppermintLog.i("handleAuthCancel retury_count=" + String.valueOf(this.f35a));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void handleAuthError(Exception exception) {
        PeppermintLog.i("handleAuthError");
        if (this.f36a != null) {
            this.f36a.run(a(exception));
        }
        setIsPGS(Boolean.valueOf(false));
        try {
            if (f34a == null) {
                throw new NullPointerException("handleAuthError mGoogleApiClient is null");
            }
            if (f34a.isConnected()) {
                Games.signOut(f34a);
                f34a.disconnect();
            }
            this.f36a = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleAuthSuccess() {
        PeppermintLog.i("handleAuthSuccess");
        c();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        PeppermintLog.i("onActivityResult requestCode=" + i + "resultCode" + i2);
        Context mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        switch (i2) {
            case PeppermintConstant.RESULT_CODE_RECONNECT_REQUIRED /*10001*/:
                disconnectEx();
                return;
            default:
                switch (i) {
                    case PeppermintConstant.REQUEST_CODE_PLAY_GAME_SERVICE_ERR /*19000*/:
                        this.f39a = false;
                        if (i2 == -1) {
                            try {
                                if (f34a == null) {
                                    throw new NullPointerException("mGoogleApiClient is null");
                                } else if (!f34a.isConnected()) {
                                    f34a.connect();
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
                    case PeppermintConstant.REQUEST_CODE_RECOVER_FROM_AUTH_ERROR /*19001*/:
                    case PeppermintConstant.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR /*19002*/:
                        if (i2 == -1) {
                            setIsPGS(Boolean.valueOf(true));
                            handleAuthSuccess();
                            return;
                        } else if (i2 == 0) {
                            handleAuthCancel(new Exception("Oauthtoken Cancel " + String.valueOf(i2)));
                            LocalStorage.removeDataValueWithKey(mainActivity, PeppermintConstant.JSON_KEY_PGS_ACCOUNT);
                            return;
                        } else {
                            return;
                        }
                    default:
                        return;
                }
        }
    }

    public void onConnected(Bundle bundle) {
        PeppermintLog.i("onConnected");
        setIsPGS(Boolean.valueOf(true));
        handleAuthSuccess();
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        int errorCode = connectionResult.getErrorCode();
        Activity mainActivity = PeppermintSocialManager.getPeppermint().getMainActivity();
        PeppermintLog.i("onConnectionFailed mIntentInProgress=" + this.f39a + " result.hasResolution=" + connectionResult.hasResolution() + " errorCode=" + errorCode);
        if (!this.f39a && connectionResult.hasResolution()) {
            try {
                this.f39a = true;
                connectionResult.startResolutionForResult(mainActivity, PeppermintConstant.REQUEST_CODE_PLAY_GAME_SERVICE_ERR);
            } catch (SendIntentException e) {
                this.f39a = false;
                f34a.connect();
            }
        } else if (errorCode == 1 || errorCode == 2 || errorCode == 3) {
            GooglePlayServicesUtil.getErrorDialog(errorCode, mainActivity, PeppermintConstant.REQUEST_CODE_PLAY_GAME_SERVICE_ERR, new D(this, errorCode)).show();
        } else {
            handleAuthError(new Exception("ConnectionResult error code : " + String.valueOf(errorCode)));
        }
    }

    public void onConnectionSuspended(int i) {
        f34a.connect();
    }

    public void setIsPGS(Boolean bool) {
        this.f37a = bool;
    }

    public void setOauthToken(String str) {
        this.f38a = str;
    }
}
