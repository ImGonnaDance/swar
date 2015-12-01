package com.com2us.smon.google.service.util;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.com2us.module.inapp.DefaultBilling;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.smon.google.service.util.GameHelper.GameHelperListener;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.com2us.wrapper.kernel.CWrapperActivity;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.games.event.Events.LoadEventsResult;
import com.google.android.gms.games.quest.Quest;
import com.google.android.gms.games.quest.QuestBuffer;
import com.google.android.gms.games.quest.QuestUpdateListener;
import com.google.android.gms.games.quest.Quests.ClaimMilestoneResult;
import com.google.android.gms.games.quest.Quests.LoadQuestsResult;
import java.io.UnsupportedEncodingException;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public class GooglePlayServiceActivity extends CWrapperActivity implements GameHelperListener, QuestUpdateListener {
    public static final int CLIENT_ALL = 15;
    public static final int CLIENT_APPSTATE = 4;
    public static final int CLIENT_GAMES = 1;
    public static final int CLIENT_PLUS = 2;
    private static final String TAG = "GooglePlayServices";
    private static EventCallback ec;
    private static ResultCallback<ClaimMilestoneResult> mClaimMilestoneResultCallback;
    protected static GameHelper mHelper;
    private static boolean mIsGpgsAvailable = false;
    private static QuestCallback qc;
    protected Activity mActivity = null;
    private ConnectListener mConnectListener = null;
    protected boolean mDebugLog = false;
    protected int mRequestedClients = CLIENT_GAMES;

    public interface ConnectListener {
        void onFailed();

        void onResult(String str);

        void onSuccess();
    }

    class EventCallback implements ResultCallback {
        GooglePlayServiceActivity m_parent;

        public EventCallback(GooglePlayServiceActivity main) {
            this.m_parent = main;
        }

        public void onResult(Result result) {
            EventBuffer eb = ((LoadEventsResult) result).getEvents();
            String message = "Current stats: \n";
            Log.i(GooglePlayServiceActivity.TAG, "number of events: " + eb.getCount());
            for (int i = 0; i < eb.getCount(); i += GooglePlayServiceActivity.CLIENT_GAMES) {
                message = new StringBuilder(String.valueOf(message)).append("event: ").append(eb.get(i).getName()).append(" ").append(eb.get(i).getEventId()).append(" ").append(eb.get(i).getValue()).append("\n").toString();
            }
            eb.close();
            Log.d("TAG", message);
        }
    }

    class QuestCallback implements ResultCallback {
        GooglePlayServiceActivity m_parent;

        public QuestCallback(GooglePlayServiceActivity main) {
            this.m_parent = main;
        }

        public void onResult(Result result) {
            QuestBuffer qb = ((LoadQuestsResult) result).getQuests();
            String message = "Current quest details: \n";
            Log.i(GooglePlayServiceActivity.TAG, "Number of quests: " + qb.getCount());
            for (int i = 0; i < qb.getCount(); i += GooglePlayServiceActivity.CLIENT_GAMES) {
                message = new StringBuilder(String.valueOf(message)).append("Quest: ").append(((Quest) qb.get(i)).getName()).append(" id: ").append(((Quest) qb.get(i)).getQuestId()).toString();
            }
            qb.close();
            Log.d(GooglePlayServiceActivity.TAG, message);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public GameHelper getGameHelper() {
        if (mHelper == null) {
            mHelper = new GameHelper(this, this.mRequestedClients);
            mHelper.enableDebugLog(this.mDebugLog);
        }
        return mHelper;
    }

    protected void onStart() {
        super.onStart();
        Log.d("TAG", "mHelper.onStart");
        mHelper.onStart(this);
    }

    protected void onStop() {
        super.onStop();
        Log.d("TAG", "mHelper.onStop");
        mHelper.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (PeppermintConstant.RESULT_CODE_RECONNECT_REQUIRED == resultCode) {
            if (mHelper != null) {
                mHelper.disconnect();
            }
        } else if (mHelper != null && mHelper.isConnecting()) {
            mHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void notifyListenerEvent(boolean bSuccess) {
        if (this.mConnectListener != null) {
            if (bSuccess) {
                this.mConnectListener.onSuccess();
            } else {
                this.mConnectListener.onFailed();
            }
        }
    }

    public void InitGooglePlayServiceActivity(Activity argActivity, ConnectListener argConnectListener) {
        this.mConnectListener = argConnectListener;
        this.mActivity = argActivity;
        switch (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)) {
            case g.a /*0*/:
                Log.d(TAG, "SUCCESS, Available Google Play Game Service");
                mIsGpgsAvailable = true;
                break;
            case CLIENT_GAMES /*1*/:
                Log.w(TAG, "Not available Google Play Game Service, SERVICE_MISSING");
                break;
            case CLIENT_PLUS /*2*/:
                Log.w(TAG, "Not available Google Play Game Service, SERVICE_VERSION_UPDATE_REQUIRED");
                break;
            case o.c /*3*/:
                Log.w(TAG, "Not available Google Play Game Service, SERVICE_DISABLED");
                break;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoTextColor /*9*/:
                Log.w(TAG, "Not available Google Play Game Service, SERVICE_INVALID");
                break;
        }
        if (mHelper == null) {
            getGameHelper();
        }
        mHelper.setup(this);
        ec = new EventCallback(this);
        qc = new QuestCallback(this);
        mClaimMilestoneResultCallback = new ResultCallback<ClaimMilestoneResult>() {
            public void onResult(ClaimMilestoneResult result) {
                GooglePlayServiceActivity.this.onMilestoneClaimed(result);
            }
        };
        loadAndPrintEvents();
        loadAndListQuests();
    }

    public void gpgsSign() {
        try {
            if (mIsGpgsAvailable && mHelper != null && !isSign()) {
                mHelper.beginUserInitiatedSignIn();
            }
        } catch (Exception e) {
            Log.w(TAG, "gpgsSign," + e.getMessage());
        }
    }

    public void gpgsSignOut() {
        try {
            if (mIsGpgsAvailable && mHelper != null && isSign()) {
                mHelper.signOut();
            }
        } catch (Exception e) {
            Log.w(TAG, "gpgsSignOut," + e.getMessage());
        }
    }

    public boolean isSign() {
        boolean z = false;
        try {
            if (!(mIsGpgsAvailable && mHelper == null)) {
                z = mHelper.isSignedIn();
            }
        } catch (Exception e) {
            Log.w(TAG, "isSign," + e.getMessage());
        }
        return z;
    }

    public void gpgsShowLeaderBoard(String leaderBoardkey) {
        try {
            if (mIsGpgsAvailable && mHelper != null && isSign()) {
                startActivityForResult(Games.Leaderboards.getLeaderboardIntent(mHelper.getApiClient(), leaderBoardkey), 123);
            }
        } catch (Exception e) {
            Log.w(TAG, "gpgsShowLeaderBoard," + e.getMessage());
        }
    }

    public void gpgsShowAchievement() {
        try {
            if (mIsGpgsAvailable && mHelper != null && isSign()) {
                startActivityForResult(Games.Achievements.getAchievementsIntent(mHelper.getApiClient()), 124);
            }
        } catch (Exception e) {
            Log.w(TAG, "gpgsShowAchievement," + e.getMessage());
        }
    }

    public void gpgsUnlockAchievement(String key) {
        try {
            if (!mIsGpgsAvailable || mHelper == null || !isSign()) {
                return;
            }
            if (!mIsGpgsAvailable || mHelper.getApiClient().isConnected()) {
                Games.Achievements.unlock(mHelper.getApiClient(), key);
            }
        } catch (Exception e) {
            Log.w(TAG, "gpgsUnlockAchievement," + e.getMessage());
        }
    }

    public void gpgsReportScore(String key, long score) {
        try {
            if (mIsGpgsAvailable && mHelper != null && isSign() && mHelper.getApiClient().isConnected()) {
                Games.Leaderboards.submitScore(mHelper.getApiClient(), key, score);
            }
        } catch (Exception e) {
            Log.w(TAG, "gpgsReportScore," + e.getMessage());
        }
    }

    public void gpgsShowQuests() {
        try {
            if (mIsGpgsAvailable && mHelper != null && isSign()) {
                startActivityForResult(Games.Quests.getQuestsIntent(mHelper.getApiClient(), new int[]{CLIENT_PLUS, DefaultBilling.AUTHORIZE_LICENSE_FAILED, 3, CLIENT_APPSTATE}), 125);
            }
        } catch (Exception e) {
            Log.w(TAG, "gpgsReportScore," + e.getMessage());
        }
    }

    public void gpgsIncrementEvents(String key, int count) {
        try {
            if (mIsGpgsAvailable && mHelper != null && isSign()) {
                Games.Events.increment(mHelper.getApiClient(), key, count);
            }
        } catch (Exception e) {
            Log.w(TAG, "gpgsReportScore," + e.getMessage());
        }
    }

    public void onSignInFailed() {
        notifyListenerEvent(false);
    }

    public void onSignInSucceeded() {
        notifyListenerEvent(true);
        Games.Quests.registerQuestUpdateListener(mHelper.getApiClient(), this);
    }

    public void loadAndPrintEvents() {
        if (mHelper != null) {
            Games.Events.load(mHelper.getApiClient(), true).setResultCallback(ec);
        }
    }

    public void loadAndListQuests() {
        Games.Quests.load(mHelper.getApiClient(), new int[]{CLIENT_PLUS, DefaultBilling.AUTHORIZE_LICENSE_FAILED, CLIENT_APPSTATE, 3}, CLIENT_GAMES, true).setResultCallback(qc);
    }

    public void onQuestCompleted(Quest quest) {
        Log.i(TAG, "You successfully completed quest " + quest.getName());
        Games.Quests.claim(mHelper.getApiClient(), quest.getQuestId(), quest.getCurrentMilestone().getMilestoneId()).setResultCallback(mClaimMilestoneResultCallback);
    }

    public void onMilestoneClaimed(ClaimMilestoneResult result) {
        try {
            if (result.getStatus().isSuccess()) {
                String reward = new String(result.getQuest().getCurrentMilestone().getCompletionRewardData(), "UTF-8");
                this.mConnectListener.onResult(reward);
                Log.d(TAG, "Congratulations, you got a " + reward);
                return;
            }
            Log.e(TAG, "Reward was not claimed due to error.");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }
    }
}
