package com.com2us.smon.normal.freefull.google.kr.android.common;

import android.os.Bundle;
import com.adpick.advertiser.sdk.AdPickAdvertiser;
import com.com2us.smon.common.MainActivity;
import it.partytrack.sdk.Track;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.AnalyticsManager;
import kr.co.cashslide.Cashslide;

public class SubActivity extends MainActivity {
    private final int m_nPartyTrackAppID = 2472;
    private final String m_strAdPickSecretKey = "1164bb43fada56b4832f511ce7453f22";
    private final String m_strCashSlideKey = "g78b1923";
    private final String m_strPartyTrackAppKey = "d0be883c451e2442dc2ef7ab00575012";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.facebookAppID = "1493038024241481";
        new AdManager(this).sendConversion("com.com2us.smon.normal.freefull.kakaolink");
        AdPickAdvertiser.init(this, "1164bb43fada56b4832f511ce7453f22");
        new Cashslide(this, "g78b1923").appFirstLaunched();
        Track.start(getApplicationContext(), 2472, "d0be883c451e2442dc2ef7ab00575012");
    }

    protected void onResume() {
        super.onResume();
        new AdManager(this).sendReengagementConversion(getIntent());
        AnalyticsManager.sendStartSession(this);
    }
}
