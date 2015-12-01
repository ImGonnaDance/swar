package com.com2us.module.activeuser.appversioncheck;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import com.com2us.module.activeuser.ActiveUserModule;
import com.com2us.module.activeuser.ActiveUserNetwork.Received;
import com.com2us.module.util.Logger;
import java.text.SimpleDateFormat;

@Deprecated
public class AppVersionCheck implements ActiveUserModule {
    private static final int AVC_RECEIVE_DATA = 0;
    private static final long HOUR = 3600000;
    private Activity activity;
    volatile String cancelStr;
    private AppVersionCheckDialog dialog;
    private GLSurfaceView glView;
    private boolean isShownDialog;
    private int jniResponsePointer;
    private AppVersionCheckListener listener;
    private final Logger logger;
    volatile String okStr;
    volatile int period;
    private long requestedTime;
    private SimpleDateFormat sdf;
    volatile String text;
    volatile String url;

    public AppVersionCheck(Activity activity, Logger logger) {
        this.isShownDialog = false;
        this.jniResponsePointer = AVC_RECEIVE_DATA;
        this.glView = null;
        this.sdf = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss");
        this.activity = activity;
        this.logger = logger;
    }

    public AppVersionCheck(Activity activity, GLSurfaceView view, Logger logger) {
        this(activity, logger);
        this.glView = view;
    }

    public void setReponseListener(AppVersionCheckListener listener) {
        this.listener = listener;
    }

    public void setJNIReponseListener(int callbackPointer) {
        this.logger.v("[AppVersionCheck][setNativeReponseListener]pointer : " + callbackPointer);
        this.jniResponsePointer = callbackPointer;
    }

    public void destroy() {
        setReponseListener(null);
        this.dialog = null;
        this.activity = null;
        this.glView = null;
    }

    public void showDialog() {
        this.logger.v("[AppVersionCheck][showDialog]text=" + this.text);
        if (this.text != null && this.text.trim().length() > 0) {
            if (this.dialog == null || !(this.dialog == null || this.dialog.isShowing())) {
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        if (AppVersionCheck.this.dialog == null) {
                            AppVersionCheck.this.dialog = new AppVersionCheckDialog(AppVersionCheck.this.activity);
                        }
                        AppVersionCheck.this.dialog.setPositiveButton(AppVersionCheck.this.okStr);
                        AppVersionCheck.this.dialog.setNegativeButton(AppVersionCheck.this.cancelStr);
                        AppVersionCheck.this.dialog.setMessage(AppVersionCheck.this.text);
                        AppVersionCheck.this.dialog.setUrl(AppVersionCheck.this.url);
                        AppVersionCheck.this.dialog.show();
                    }
                });
            }
        }
    }

    public boolean isExecutable() {
        return false;
    }

    public void responseNetwork(Received data) {
        this.logger.d("[AppVersionCheck][responseNetwork]" + data.toString());
    }
}
