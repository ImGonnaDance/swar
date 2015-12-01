package com.com2us.chartboost;

import android.os.Bundle;
import android.util.Log;
import com.chartboost.sdk.CBLocation;
import com.chartboost.sdk.Chartboost;
import com.com2us.module.mercury.MercuryDefine;
import com.com2us.smon.google.service.util.GooglePlayServiceActivity;

public class ChartboostActivity extends GooglePlayServiceActivity {
    static final int CHARTBOOST_SHOW_TYPE_INPLAY = 4;
    static final int CHARTBOOST_SHOW_TYPE_INTERSTITIAL = 1;
    static final int CHARTBOOST_SHOW_TYPE_MOREAPPS = 3;
    static final int CHARTBOOST_SHOW_TYPE_NONE = 0;
    static final int CHARTBOOST_SHOW_TYPE_REWAREDVIDEO = 2;
    static final String TAG = "Chartboost";
    static final String appId = "533a5c802d42da7c1795a7dc";
    static final String appSignature = "5062a6a46478143ebd9bf4a160a41d6d312e5c02";
    boolean g_bInit = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Chartboost.startWithAppId(this, appId, appSignature);
        Chartboost.onCreate(this);
        this.g_bInit = true;
        Log.d(TAG, MercuryDefine.ACTION_INIT);
    }

    protected void onStart() {
        super.onStart();
        Chartboost.onStart(this);
    }

    protected void onResume() {
        super.onResume();
        Chartboost.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        Chartboost.onPause(this);
    }

    protected void onStop() {
        super.onStop();
        Chartboost.onStop(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        Chartboost.onDestroy(this);
    }

    public void onBackPressed() {
        if (!Chartboost.onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void chartboostShow(int type) {
        if (this.g_bInit) {
            Log.d(TAG, "chartboostShow(" + type + ")");
            switch (type) {
                case CHARTBOOST_SHOW_TYPE_INTERSTITIAL /*1*/:
                    if (!Chartboost.hasInterstitial(CBLocation.LOCATION_LEADERBOARD)) {
                        Chartboost.showInterstitial(CBLocation.LOCATION_LEADERBOARD);
                        return;
                    }
                    return;
                case CHARTBOOST_SHOW_TYPE_REWAREDVIDEO /*2*/:
                    if (!Chartboost.hasRewardedVideo(CBLocation.LOCATION_ACHIEVEMENTS)) {
                        Chartboost.showRewardedVideo(CBLocation.LOCATION_ACHIEVEMENTS);
                        return;
                    }
                    return;
                case CHARTBOOST_SHOW_TYPE_MOREAPPS /*3*/:
                    if (!Chartboost.hasMoreApps(CBLocation.LOCATION_GAME_SCREEN)) {
                        Chartboost.showMoreApps(CBLocation.LOCATION_GAME_SCREEN);
                        return;
                    }
                    return;
                case CHARTBOOST_SHOW_TYPE_INPLAY /*4*/:
                    return;
                default:
                    return;
            }
        }
        Log.d(TAG, "chartboostShow failed : Ninit(" + type + ")");
    }
}
