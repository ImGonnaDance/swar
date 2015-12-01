package com.com2us.module.activeuser.sessioncheck;

import android.app.Activity;
import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserModule;
import com.com2us.module.activeuser.ActiveUserNetwork;
import com.com2us.module.activeuser.ActiveUserNetwork.Received;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedSessionData;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import com.com2us.module.util.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class SessionCheck implements ActiveUserModule {
    private Activity activity = null;
    private boolean isExecuted = false;
    private long lastSendTime = -1;
    private JSONObject lastSession = null;
    private long lastStopTime = -1;
    private Logger logger = null;
    private int session_max_num = 1;
    private int session_max_time = 0;
    private JSONArray sessions = null;

    public SessionCheck(Activity activity, Logger logger) {
        this.activity = activity;
        this.logger = logger;
        try {
            this.session_max_num = Integer.valueOf(ActiveUserProperties.getProperty(ActiveUserProperties.SESSION_LIMIT_NUM)).intValue();
        } catch (Exception e) {
            this.session_max_num = 1;
        }
        try {
            this.session_max_time = Integer.valueOf(ActiveUserProperties.getProperty(ActiveUserProperties.SESSION_LIMIT_TIME)).intValue();
        } catch (Exception e2) {
            this.session_max_time = 0;
        }
        this.logger.d("SessionCheck initialize");
    }

    private boolean lastSendSessionTimeCheck() {
        this.logger.d("lastSendSessionTimeCheck");
        boolean ret = false;
        if (!(this.lastSession == null || this.sessions == null)) {
            if (getCurrentTime() - this.lastStopTime > 10) {
                this.logger.d(toastShow("after one session limit time (10sec)"));
                this.sessions.put(this.lastSession);
                this.lastSession = new JSONObject();
                try {
                    this.lastSession.put("start", getCurrentTime());
                    this.lastSession.put("length", 0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.lastStopTime = -1;
                ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_DATA_PROPERTY, this.sessions.toString());
                ActiveUserProperties.removeProperty(ActiveUserProperties.SESSION_LAST_SESSION_PROPERTY);
                ActiveUserProperties.removeProperty(ActiveUserProperties.SESSION_LAST_STOP_TIME_PROPERTY);
                ActiveUserProperties.storeProperties(this.activity);
            } else {
                this.logger.d(toastShow("before one session limit time (10sec)"));
            }
        }
        if (getCurrentTime() - this.lastSendTime > ((long) this.session_max_time)) {
            this.logger.d(toastShow("after interval time (" + this.session_max_time + "sec)"));
            ret = true;
        } else {
            this.logger.d(toastShow("before interval time (" + this.session_max_time + "sec)"));
        }
        if (this.sessions.length() >= this.session_max_num) {
            this.logger.d(toastShow("sessions over : " + this.sessions.length() + " / " + this.session_max_num));
            ret = true;
        } else {
            this.logger.d(toastShow("sessions not over : " + this.sessions.length() + " / " + this.session_max_num));
        }
        if (!TextUtils.isEmpty(ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY))) {
            return ret;
        }
        this.logger.d("DID is Empty");
        return false;
    }

    private void sendSessions() {
        this.logger.d(toastShow("sendSessions"));
        if (this.sessions == null || this.sessions.length() <= 0) {
            this.logger.d(toastShow("non-exist session data"));
        } else {
            new Thread() {
                public void run() {
                    ReceivedSessionData recvSessionData = new ReceivedSessionData();
                    recvSessionData = (ReceivedSessionData) ActiveUserNetwork.processNetworkTask(Gateway.REQUEST_SESSION);
                    if (recvSessionData != null) {
                        SessionCheck.this.logger.d("ReceivedSessionData : " + recvSessionData.toString());
                        if (recvSessionData.errno == 0) {
                            SessionCheck.this.sessions = new JSONArray();
                            SessionCheck.this.lastSendTime = SessionCheck.getCurrentTime();
                            SessionCheck.this.session_max_num = recvSessionData.session_max_num;
                            SessionCheck.this.session_max_time = recvSessionData.session_max_time;
                            ActiveUserProperties.removeProperty(ActiveUserProperties.SESSION_DATA_PROPERTY);
                            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LAST_SEND_TIME_PROPERTY, String.valueOf(SessionCheck.this.lastSendTime));
                            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LIMIT_NUM, String.valueOf(recvSessionData.session_max_num));
                            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LIMIT_TIME, String.valueOf(recvSessionData.session_max_time));
                            ActiveUserProperties.storeProperties(SessionCheck.this.activity);
                            return;
                        }
                        return;
                    }
                    SessionCheck.this.logger.d("ReceivedSessionData is null");
                }
            }.start();
        }
    }

    private void loadSessions() {
        this.logger.d("loadSessions");
        try {
            if (this.sessions == null) {
                String session_data = ActiveUserProperties.getProperty(ActiveUserProperties.SESSION_DATA_PROPERTY);
                if (TextUtils.isEmpty(session_data)) {
                    this.sessions = new JSONArray();
                } else {
                    this.sessions = new JSONArray(session_data);
                }
            }
            this.logger.d(toastShow("loaded Sessions : " + this.sessions.toString()));
        } catch (Exception e) {
            this.logger.d(e.toString());
            this.logger.d("load Sessions failed");
            this.sessions = new JSONArray();
        }
        try {
            if (this.lastSession == null) {
                String session_lastSession = ActiveUserProperties.getProperty(ActiveUserProperties.SESSION_LAST_SESSION_PROPERTY);
                if (TextUtils.isEmpty(session_lastSession)) {
                    this.lastSession = new JSONObject();
                    this.lastSession.put("start", getCurrentTime());
                    this.lastSession.put("length", 0);
                } else {
                    this.lastSession = new JSONObject(session_lastSession);
                }
            }
            this.logger.d("loaded lastSession : " + this.lastSession.toString());
        } catch (Exception e2) {
            try {
                this.logger.d("load lastSession create");
                this.lastSession = new JSONObject();
                this.lastSession.put("start", getCurrentTime());
                this.lastSession.put("length", 0);
            } catch (Exception e1) {
                e1.printStackTrace();
                this.logger.d("load lastSession failed");
            }
        }
        try {
            if (this.lastStopTime == -1) {
                String session_lastStopTime = ActiveUserProperties.getProperty(ActiveUserProperties.SESSION_LAST_STOP_TIME_PROPERTY);
                if (TextUtils.isEmpty(session_lastStopTime)) {
                    this.lastStopTime = getCurrentTime();
                    ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LAST_STOP_TIME_PROPERTY, String.valueOf(getCurrentTime()));
                    ActiveUserProperties.storeProperties(this.activity);
                } else {
                    this.lastStopTime = Long.parseLong(session_lastStopTime);
                }
            }
            this.logger.d("loaded lastStopTime : " + this.lastStopTime);
        } catch (Exception e3) {
            this.logger.d("load lastStopTime failed : " + e3.toString());
            this.lastStopTime = getCurrentTime();
            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LAST_STOP_TIME_PROPERTY, String.valueOf(getCurrentTime()));
            ActiveUserProperties.storeProperties(this.activity);
        }
        try {
            if (this.lastSendTime == -1) {
                String session_lastSendTime = ActiveUserProperties.getProperty(ActiveUserProperties.SESSION_LAST_SEND_TIME_PROPERTY);
                if (TextUtils.isEmpty(session_lastSendTime)) {
                    this.lastSendTime = getCurrentTime();
                    ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LAST_SEND_TIME_PROPERTY, String.valueOf(getCurrentTime()));
                    ActiveUserProperties.storeProperties(this.activity);
                } else {
                    this.lastSendTime = Long.parseLong(session_lastSendTime);
                }
            }
            this.logger.d("loaded lastSendTime : " + this.lastSendTime);
        } catch (Exception e32) {
            this.logger.d("load lastSendTime failed : " + e32.toString());
            this.lastSendTime = getCurrentTime();
            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LAST_SEND_TIME_PROPERTY, String.valueOf(getCurrentTime()));
            ActiveUserProperties.storeProperties(this.activity);
        }
    }

    private void saveSessions() {
        this.logger.d("saveSessions");
        this.lastStopTime = getCurrentTime();
        try {
            if (this.lastSession != null) {
                this.lastSession.put("length", this.lastStopTime - this.lastSession.getLong("start"));
            }
            this.logger.d("saved last session : " + this.lastSession.toString());
            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LAST_SESSION_PROPERTY, this.lastSession.toString());
            ActiveUserProperties.setProperty(ActiveUserProperties.SESSION_LAST_STOP_TIME_PROPERTY, String.valueOf(this.lastStopTime));
            ActiveUserProperties.storeProperties(this.activity);
        } catch (Exception e) {
            this.logger.d("save Sessions failed : " + e.toString());
            e.printStackTrace();
        }
    }

    public void onActivityStarted() {
        if (this.isExecuted) {
            this.logger.d("SessionCheck Started");
            loadSessions();
            if (lastSendSessionTimeCheck()) {
                sendSessions();
                return;
            }
            return;
        }
        this.logger.d("SessionCheck not Started : isExecuted false");
    }

    public void onActivityStoped() {
        if (this.isExecuted) {
            this.logger.d("SessionCheck Stoped");
            saveSessions();
            return;
        }
        this.logger.d("SessionCheck not Stoped : isExecuted false");
    }

    public boolean isExecutable() {
        this.isExecuted = true;
        onActivityStarted();
        return true;
    }

    public void responseNetwork(Received data) {
    }

    public void destroy() {
        this.logger.d("SessionCheck destroy");
    }

    protected static long getCurrentTime() {
        return System.currentTimeMillis() / 1000;
    }

    private String toastShow(String msg) {
        return msg;
    }
}
