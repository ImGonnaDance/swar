package com.com2us.module.util;

import android.app.Activity;
import android.util.Log;
import java.util.HashMap;
import jp.co.cyberz.fox.a.a.i;

public abstract class LoggerGroup {
    private static boolean locked = false;
    private static boolean logged = false;
    private static HashMap<String, Logger> map = new HashMap();

    private LoggerGroup() {
    }

    public static void setLogged(boolean isLogged) {
        logged = isLogged;
        for (Logger logged : map.values()) {
            logged.setLogged(isLogged);
        }
    }

    public static void setLogged(boolean isLogged, String TAG) {
        try {
            ((Logger) map.get(TAG)).setLogged(isLogged);
        } catch (Exception e) {
        }
    }

    public static void setLogged(boolean isLogged, String... TAGs) {
        for (String tag : TAGs) {
            try {
                ((Logger) map.get(tag)).setLogged(isLogged);
            } catch (Exception e) {
            }
        }
    }

    public static boolean isLocked() {
        return locked;
    }

    public static void setLocked(boolean locked) {
        for (Logger locked2 : map.values()) {
            locked2.setLocked(locked);
        }
        locked = locked;
    }

    public static void showTag() {
        StringBuilder sb = new StringBuilder("[showTag:");
        for (String valueOf : map.keySet()) {
            sb.append(new StringBuilder(String.valueOf(valueOf)).append(i.b).toString());
        }
        Log.v("LoggerGroup", sb.toString() + "]");
    }

    public static Logger createLogger(String TAG) {
        if (map.containsKey(TAG)) {
            Logger logger = (Logger) map.get(TAG);
            logger.setLocked(locked);
            return logger;
        }
        logger = new Logger(TAG);
        logger.setLogged(logged);
        logger.setLocked(locked);
        map.put(TAG, logger);
        return logger;
    }

    public static Logger createLogger(String TAG, Activity activity) {
        Logger logger = createLogger(TAG);
        logger.setActivity(activity);
        return logger;
    }
}
