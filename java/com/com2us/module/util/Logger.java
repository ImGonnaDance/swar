package com.com2us.module.util;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.widget.Toast;
import java.lang.ref.WeakReference;

public class Logger {
    private WeakReference<Activity> activityRef;
    private final boolean isShown;
    private volatile boolean locked;
    private volatile boolean logged;
    final String tag;

    Logger(String TAG) {
        this.logged = false;
        this.locked = false;
        this.isShown = true;
        this.tag = TAG;
    }

    Logger(Activity activity, String TAG) {
        this(TAG);
        setActivity(activity);
    }

    boolean equals(Logger logger) {
        return this.tag == logger.tag;
    }

    String getTag() {
        return this.tag;
    }

    void setActivity(Activity activity) {
        this.activityRef = new WeakReference(activity);
    }

    public synchronized boolean isLogged() {
        return this.logged;
    }

    public synchronized void setLogged(boolean log) {
        if (!this.locked) {
            this.logged = log;
        }
    }

    public void setLocked(boolean isLocked) {
        this.locked = isLocked;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void d(String TAG, String msg, Throwable e) {
        if (this.logged) {
            Log.d(TAG, msg, e);
        } else {
            e.printStackTrace();
        }
    }

    public void w(String TAG, String msg, Throwable e) {
        if (this.logged) {
            Log.w(TAG, msg, e);
            showToast("Warning:" + TAG, "\n" + eMsg(e));
            return;
        }
        e.printStackTrace();
    }

    public void e(String TAG, String msg, Throwable e) {
        if (this.logged) {
            Log.e(TAG, msg, e);
            showDialog("Error:" + TAG, "\n" + eMsg(e));
            return;
        }
        e.printStackTrace();
    }

    public void v(String TAG, String msg) {
        if (this.logged) {
            Log.v(TAG, msg);
        }
    }

    public void d(String TAG, String msg) {
        if (this.logged) {
            Log.d(TAG, msg);
        }
    }

    public void i(String TAG, String msg) {
        if (this.logged) {
            Log.i(TAG, msg);
            showToast("Info:" + TAG, msg);
        }
    }

    public void w(String TAG, String msg) {
        if (this.logged) {
            Log.w(TAG, msg);
            showToast("Warn:" + TAG, msg);
        }
    }

    public void e(String TAG, String msg) {
        if (this.logged) {
            Log.e(TAG, msg);
            showDialog(TAG, msg);
        }
    }

    public void d(String msg, Exception e) {
        d(this.tag, msg, e);
    }

    public void w(String msg, Exception e) {
        w(this.tag, msg, e);
    }

    public void e(String msg, Exception e) {
        e(this.tag, msg, e);
    }

    public void v(String msg) {
        v(this.tag, msg);
    }

    public void i(String msg) {
        i(this.tag, msg);
    }

    public void d(String msg) {
        d(this.tag, msg);
    }

    public void e(String msg) {
        e(this.tag, msg);
    }

    public void w(String msg) {
        w(this.tag, msg);
    }

    private void showToast(final String TAG, final String msg) {
        if (this.activityRef != null) {
            final Activity activity = (Activity) this.activityRef.get();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(activity, TAG + "\n" + msg, 1).show();
                    }
                });
            }
        }
    }

    private void showDialog(final String TAG, final String msg) {
        if (this.activityRef != null) {
            final Activity activity = (Activity) this.activityRef.get();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        new Builder(activity).setTitle(TAG).setMessage(msg).setPositiveButton("OK", new OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
                    }
                });
            }
        }
    }

    public String eMsg(Throwable e) {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString() + "\n");
        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append("\t").append("at").append(" ");
            sb.append(ste.toString());
        }
        return sb.toString();
    }
}
