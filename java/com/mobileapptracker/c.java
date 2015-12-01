package com.mobileapptracker;

import android.content.Context;
import android.os.Bundle;
import com.facebook.AppEventsConstants;
import java.lang.reflect.Method;
import java.util.Locale;

final class c {
    private static Object a;
    private static boolean b = false;

    public static void a(Context context, boolean z) {
        try {
            Class[] clsArr = new Class[]{Context.class};
            Class[] clsArr2 = new Class[]{Context.class, Boolean.TYPE};
            Object[] objArr = new Object[]{context};
            Class.forName("com.facebook.AppEventsLogger").getMethod("activateApp", clsArr).invoke(null, objArr);
            b = true;
            Class.forName("com.facebook.Settings").getMethod("setLimitEventAndDataUsage", clsArr2).invoke(null, new Object[]{context, Boolean.valueOf(z)});
            a = Class.forName("com.facebook.AppEventsLogger").getMethod("newLogger", clsArr).invoke(null, objArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void a(Bundle bundle, String str, String str2) {
        if (str2 != null) {
            bundle.putString(str, str2);
        }
    }

    public static void a(MATEvent mATEvent) {
        if (a != null) {
            try {
                Method method = a.getClass().getMethod("logEvent", new Class[]{String.class, Double.TYPE, Bundle.class});
                String eventName = mATEvent.getEventName();
                double revenue = mATEvent.getRevenue();
                MATParameters instance = MATParameters.getInstance();
                String toLowerCase = mATEvent.getEventName().toLowerCase(Locale.US);
                if (toLowerCase.contains("session")) {
                    if (!b) {
                        eventName = AppEventsConstants.EVENT_NAME_ACTIVATED_APP;
                    } else {
                        return;
                    }
                } else if (toLowerCase.contains(MATEvent.REGISTRATION)) {
                    eventName = AppEventsConstants.EVENT_NAME_COMPLETED_REGISTRATION;
                } else if (toLowerCase.contains(MATEvent.CONTENT_VIEW)) {
                    eventName = AppEventsConstants.EVENT_NAME_VIEWED_CONTENT;
                } else if (toLowerCase.contains(MATEvent.SEARCH)) {
                    eventName = AppEventsConstants.EVENT_NAME_SEARCHED;
                } else if (toLowerCase.contains(MATEvent.RATED)) {
                    eventName = AppEventsConstants.EVENT_NAME_RATED;
                    try {
                        revenue = mATEvent.getRating();
                    } catch (Exception e) {
                    }
                } else if (toLowerCase.contains(MATEvent.TUTORIAL_COMPLETE)) {
                    eventName = AppEventsConstants.EVENT_NAME_COMPLETED_TUTORIAL;
                } else if (toLowerCase.contains(MATEvent.ADD_TO_CART)) {
                    eventName = AppEventsConstants.EVENT_NAME_ADDED_TO_CART;
                } else if (toLowerCase.contains(MATEvent.ADD_TO_WISHLIST)) {
                    eventName = AppEventsConstants.EVENT_NAME_ADDED_TO_WISHLIST;
                } else if (toLowerCase.contains(MATEvent.CHECKOUT_INITIATED)) {
                    eventName = AppEventsConstants.EVENT_NAME_INITIATED_CHECKOUT;
                } else if (toLowerCase.contains(MATEvent.ADDED_PAYMENT_INFO)) {
                    eventName = AppEventsConstants.EVENT_NAME_ADDED_PAYMENT_INFO;
                } else if (toLowerCase.contains(MATEvent.PURCHASE)) {
                    eventName = AppEventsConstants.EVENT_NAME_PURCHASED;
                } else if (toLowerCase.contains(MATEvent.LEVEL_ACHIEVED)) {
                    eventName = AppEventsConstants.EVENT_NAME_ACHIEVED_LEVEL;
                } else if (toLowerCase.contains(MATEvent.ACHIEVEMENT_UNLOCKED)) {
                    eventName = AppEventsConstants.EVENT_NAME_UNLOCKED_ACHIEVEMENT;
                } else if (toLowerCase.contains(MATEvent.SPENT_CREDITS)) {
                    eventName = AppEventsConstants.EVENT_NAME_SPENT_CREDITS;
                    try {
                        revenue = (double) mATEvent.getQuantity();
                    } catch (Exception e2) {
                    }
                }
                Bundle bundle = new Bundle();
                a(bundle, AppEventsConstants.EVENT_PARAM_CURRENCY, mATEvent.getCurrencyCode());
                a(bundle, AppEventsConstants.EVENT_PARAM_CONTENT_ID, mATEvent.getContentId());
                a(bundle, AppEventsConstants.EVENT_PARAM_CONTENT_TYPE, mATEvent.getContentType());
                a(bundle, AppEventsConstants.EVENT_PARAM_SEARCH_STRING, mATEvent.getSearchString());
                a(bundle, AppEventsConstants.EVENT_PARAM_NUM_ITEMS, Integer.toString(mATEvent.getQuantity()));
                a(bundle, AppEventsConstants.EVENT_PARAM_LEVEL, Integer.toString(mATEvent.getLevel()));
                a(bundle, "tune_referral_source", instance.getReferralSource());
                a(bundle, "tune_source_sdk", "TUNE-MAT");
                method.invoke(a, new Object[]{eventName, Double.valueOf(revenue), bundle});
                b = false;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }
}
