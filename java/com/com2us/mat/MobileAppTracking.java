package com.com2us.mat;

import android.app.Activity;
import com.mobileapptracker.MATEvent;
import com.mobileapptracker.MATEventItem;
import com.mobileapptracker.MobileAppTracker;
import java.util.ArrayList;
import java.util.List;

public class MobileAppTracking extends Activity {
    private static Activity activity = null;
    private static MobileAppTracker mobileAppTracker = null;

    public static void initMat(Activity argActivity, String adveriserID, String conversionKey) {
        if (argActivity != null && adveriserID != null && conversionKey != null) {
            activity = argActivity;
            mobileAppTracker = MobileAppTracker.init(activity, adveriserID, conversionKey);
            mobileAppTracker.setFacebookEventLogging(true, activity, false);
        }
    }

    public static void resume() {
        if (activity != null && mobileAppTracker != null) {
            mobileAppTracker.setReferralSources(activity);
            mobileAppTracker.measureSession();
        }
    }

    public static void sendTutorial() {
        if (mobileAppTracker != null) {
            mobileAppTracker.measureEvent(933299364);
        }
    }

    public static void sendPurchase(String strPID, String strPrice, String strCurrency) {
        if (mobileAppTracker != null) {
            List<MATEventItem> eventItems = new ArrayList();
            MATEventItem item = new MATEventItem(strPID);
            item.withQuantity(1);
            item.withUnitPrice(Double.parseDouble(strPrice));
            eventItems.add(item);
            mobileAppTracker.measureEvent(new MATEvent(MATEvent.PURCHASE).withEventItems(eventItems).withRevenue(Double.parseDouble(strPrice)).withCurrencyCode(strCurrency));
        }
    }

    public static void setUserID(String strUserID) {
        if (mobileAppTracker != null) {
            mobileAppTracker.setUserId(strUserID);
        }
    }
}
