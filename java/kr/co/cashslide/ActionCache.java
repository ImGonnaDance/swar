package kr.co.cashslide;

import android.content.Context;
import android.content.SharedPreferences.Editor;

class ActionCache {
    private static final String FROM_CASHSLIDE = "fromCashslide";
    private static final String PREFS_NAME = "CashslideActions";
    private static final String RESID_APP_FIRST_LAUNCHED = "appFirstLaunched";
    private static final String RESID_MISSION_COMPLETED = "missionCompleted";
    private Context mContext;

    public ActionCache(Context context) {
        this.mContext = context;
    }

    public boolean isAppFirstLaunched() {
        return this.mContext.getSharedPreferences(PREFS_NAME, 0).getBoolean(RESID_APP_FIRST_LAUNCHED, true);
    }

    public void saveAppFirstLaunched() {
        Editor editor = this.mContext.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putBoolean(RESID_APP_FIRST_LAUNCHED, false);
        editor.commit();
    }

    public boolean isMissionCompleted() {
        return this.mContext.getSharedPreferences(PREFS_NAME, 0).getBoolean(RESID_MISSION_COMPLETED, false);
    }

    public void saveMissionCompleted() {
        Editor editor = this.mContext.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putBoolean(RESID_MISSION_COMPLETED, true);
        editor.commit();
    }

    public void setFromCashslideTrue() {
        Editor editor = this.mContext.getSharedPreferences(PREFS_NAME, 0).edit();
        editor.putBoolean(FROM_CASHSLIDE, true);
        editor.commit();
    }

    public boolean getFromCashslide() {
        return this.mContext.getSharedPreferences(PREFS_NAME, 0).getBoolean(FROM_CASHSLIDE, false);
    }
}
