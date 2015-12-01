package kr.co.cashslide;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

public class Cashslide {
    private static final String ACTION_REWARD_NOTI = "kr.co.cashslide.REWARD_NOTIFICATION";
    private ActionCache cache;
    private String mAppId;
    private Context mContext;
    private Request request;

    class AppFirstLaunchedTask extends AsyncTask<Void, Void, Boolean> {
        Context ctx;

        private AppFirstLaunchedTask(Context ctx) {
            this.ctx = ctx;
        }

        protected Boolean doInBackground(Void... arg0) {
            return Boolean.valueOf(Cashslide.this.request.sendAppFirstLaunched());
        }

        protected void onPostExecute(Boolean success) {
            if (success.booleanValue()) {
                String packageName = null;
                if (this.ctx != null) {
                    packageName = Cashslide.this.mContext.getPackageName();
                }
                int reward = Cashslide.this.request.getReward();
                Intent intent = new Intent(Cashslide.ACTION_REWARD_NOTI);
                intent.putExtra("REWARD_COST", reward);
                intent.putExtra("PACKAGE_NAME", packageName);
                if (intent != null) {
                    this.ctx.sendBroadcast(intent);
                }
                try {
                    Cashslide.this.cache.setFromCashslideTrue();
                } catch (Exception e) {
                }
            }
        }
    }

    class MissionCompletedTask extends AsyncTask<Void, Void, Boolean> {
        Context ctx;

        private MissionCompletedTask(Context ctx) {
            this.ctx = ctx;
        }

        protected Boolean doInBackground(Void... arg0) {
            return Boolean.valueOf(Cashslide.this.request.sendMissionCompleted());
        }

        protected void onPostExecute(Boolean success) {
            if (success.booleanValue()) {
                String packageName = null;
                if (this.ctx != null) {
                    packageName = Cashslide.this.mContext.getPackageName();
                }
                int reward = Cashslide.this.request.getReward();
                Intent intent = new Intent(Cashslide.ACTION_REWARD_NOTI);
                intent.putExtra("REWARD_COST", reward);
                intent.putExtra("PACKAGE_NAME", packageName);
                if (intent != null) {
                    this.ctx.sendBroadcast(intent);
                }
            }
        }
    }

    public Cashslide(Context context, String appId) {
        this.mContext = context;
        this.mAppId = appId;
    }

    public void appFirstLaunched() {
        this.request = new Request(this.mContext, this.mAppId);
        this.cache = new ActionCache(this.mContext);
        if (this.cache.isAppFirstLaunched()) {
            this.cache.saveAppFirstLaunched();
            new AppFirstLaunchedTask(this.mContext).execute(new Void[0]);
        }
    }

    public boolean isFromCashslide() {
        boolean result = false;
        try {
            result = this.cache.getFromCashslide();
        } catch (Exception e) {
        }
        return result;
    }

    public void missionCompleted() {
        this.request = new Request(this.mContext, this.mAppId);
        this.cache = new ActionCache(this.mContext);
        if (!this.cache.isMissionCompleted()) {
            this.cache.saveMissionCompleted();
            new MissionCompletedTask(this.mContext).execute(new Void[0]);
        }
    }
}
