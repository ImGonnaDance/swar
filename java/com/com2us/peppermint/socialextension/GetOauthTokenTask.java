package com.com2us.peppermint.socialextension;

import android.app.Activity;
import android.os.AsyncTask;
import com.com2us.peppermint.LocalStorage;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.peppermint.util.PeppermintLog;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.plus.Plus;
import java.io.IOException;
import jp.co.cyberz.fox.a.a.i;

public class GetOauthTokenTask extends AsyncTask<Object, Void, Boolean> {
    private int a = 0;
    private PeppermintSocialPluginGooglePlus f59a = null;
    private PeppermintSocialPluginPGSHelper f60a = null;
    String f61a = i.a;
    String b = "GetOauthTokenTask";

    protected Boolean doInBackground(Object... objArr) {
        if (objArr[0] == null) {
            PeppermintLog.i("doInBackground Activity is Null!");
            return Boolean.valueOf(false);
        }
        Activity activity = (Activity) objArr[0];
        String str = i.a;
        GoogleApiClient apiClient;
        if (objArr[1] instanceof PeppermintSocialPluginPGSHelper) {
            PeppermintLog.i("doInBackground Object is PeppermintPGSHelper");
            this.f60a = (PeppermintSocialPluginPGSHelper) objArr[1];
            apiClient = this.f60a.getApiClient();
            this.a = PeppermintConstant.REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR;
            str = Games.getCurrentAccountName(apiClient);
        } else if (objArr[1] instanceof PeppermintSocialPluginGooglePlus) {
            PeppermintLog.i("doInBackground Object is PeppermintSocialPluginGooglePlus");
            this.f59a = (PeppermintSocialPluginGooglePlus) objArr[1];
            apiClient = this.f59a.getApiClient();
            this.a = PeppermintConstant.REQUEST_CODE_RECOVER_PLAY_SERVICE_ERROR;
            str = Plus.AccountApi.getAccountName(apiClient);
        }
        String str2 = Boolean.valueOf(LocalStorage.getDataValueWithKey(PeppermintSocialManager.getPeppermint().getMainActivity(), PeppermintConstant.JSON_KEY_USE_PGS)).booleanValue() ? "oauth2:https://www.googleapis.com/auth/games https://www.googleapis.com/auth/userinfo.email" : "oauth2:https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/userinfo.email";
        try {
            PeppermintLog.i("doInBackground OauthScope is " + str2);
            this.f61a = GoogleAuthUtil.getToken(activity, str, str2);
            PeppermintLog.i("This it GooglePlus Token : " + this.f61a);
            return Boolean.valueOf(true);
        } catch (UserRecoverableAuthException e) {
            activity.startActivityForResult(e.getIntent(), this.a);
            e.printStackTrace();
            return Boolean.valueOf(false);
        } catch (IOException e2) {
            e2.printStackTrace();
            return Boolean.valueOf(false);
        } catch (GoogleAuthException e3) {
            e3.printStackTrace();
            return Boolean.valueOf(false);
        }
    }

    protected void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
        if (!bool.booleanValue()) {
            return;
        }
        if (this.f60a != null) {
            this.f60a.setIsPGS(Boolean.valueOf(true));
            this.f60a.setOauthToken(this.f61a);
            this.f60a.handleAuthSuccess();
        } else if (this.f59a != null) {
            this.f59a.setOauthToken(this.f61a);
            this.f59a.handleAuthSuccess();
        }
    }
}
