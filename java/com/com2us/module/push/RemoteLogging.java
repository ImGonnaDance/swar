package com.com2us.module.push;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.text.TextUtils;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.module.mercury.Mercury;
import com.com2us.module.util.LoggerGroup;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONObject;

public class RemoteLogging {
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static RemoteLogging remoteLogging = null;
    final int MAX_LOG_MESSAGE_LENGTH = Mercury.MERCURY_SHOW_CUSTOM_BASE;
    final String STATE_LOGGING = "logging";
    final String STATE_SENDING = "sending";
    private String mAdditonalInfo;
    private String mBuffer;
    private CollectLogTask mCollectLogTask;
    private String[] mFilterSpecs;
    private String mFormat;
    private AlertDialog mMainDialog;
    private ProgressDialog mProgressDialog;
    private Intent mSendIntent;

    private class CollectLogTask extends AsyncTask<ArrayList<String>, Void, StringBuilder> {
        Context context;

        protected CollectLogTask(Context context) {
            this.context = context;
        }

        protected void onPreExecute() {
            RemoteLogging.this.showProgressDialog(this.context, "Acquiring log from the system.");
        }

        protected StringBuilder doInBackground(ArrayList<String>... params) {
            StringBuilder log = new StringBuilder();
            try {
                ArrayList<String> commandLine = new ArrayList();
                commandLine.add("logcat");
                commandLine.add("-d");
                ArrayList<String> arguments = (params == null || params.length <= 0) ? null : params[0];
                if (arguments != null) {
                    commandLine.addAll(arguments);
                }
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec((String[]) commandLine.toArray(new String[0])).getInputStream()));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    log.append(line);
                    log.append(RemoteLogging.LINE_SEPARATOR);
                }
            } catch (IOException e) {
                PushConfig.LogI("CollectLogTask.doInBackground failed : " + e.toString());
            }
            return log;
        }

        protected void onPostExecute(StringBuilder log) {
            if (log != null) {
                int keepOffset = Math.max(log.length() - Mercury.MERCURY_SHOW_CUSTOM_BASE, 0);
                if (keepOffset > 0) {
                    log.delete(0, keepOffset);
                }
                if (RemoteLogging.this.mAdditonalInfo != null) {
                    log.insert(0, RemoteLogging.LINE_SEPARATOR);
                    log.insert(0, RemoteLogging.this.mAdditonalInfo);
                }
                RemoteLogging.this.mSendIntent.putExtra("android.intent.extra.TEXT", log.toString());
                this.context.startActivity(Intent.createChooser(RemoteLogging.this.mSendIntent, "Select an application to send the log"));
                RemoteLogging.this.dismissProgressDialog();
                RemoteLogging.this.dismissMainDialog();
                return;
            }
            RemoteLogging.this.dismissProgressDialog();
            RemoteLogging.this.showErrorDialog(this.context, "Failed to get the log from the system.");
        }
    }

    private RemoteLogging() {
    }

    public static RemoteLogging getInstance() {
        if (remoteLogging == null) {
            remoteLogging = new RemoteLogging();
        }
        return remoteLogging;
    }

    public void setEnableLogging(Context context, Intent intent) {
        PropertyUtil propertyUtil = PropertyUtil.getInstance(context);
        propertyUtil.loadProperty();
        String debugString = intent.getExtras().getString("debug");
        if (!TextUtils.isEmpty(debugString)) {
            try {
                JSONObject debugJson = new JSONObject(debugString);
                JSONObject jSONObject;
                try {
                    boolean logging = debugJson.optBoolean("logging", false);
                    String support_email = debugJson.optString("support_email");
                    if (logging) {
                        LoggerGroup.setLogged(true);
                        LoggerGroup.setLocked(true);
                        PushConfig.LOG = true;
                        propertyUtil.setProperty("enableLogging", "logging");
                        if (!TextUtils.isEmpty(support_email)) {
                            propertyUtil.setProperty("supportEmail", support_email);
                        }
                        propertyUtil.storeProperty(null);
                    }
                    jSONObject = debugJson;
                } catch (Exception e) {
                    jSONObject = debugJson;
                }
            } catch (Exception e2) {
            }
        }
    }

    public void collectAndSendLog(final Context context) {
        final PropertyUtil propertyUtil = PropertyUtil.getInstance(context);
        propertyUtil.loadProperty();
        String enableLogging = propertyUtil.getProperty("enableLogging");
        final String supportEmail = propertyUtil.getProperty("supportEmail");
        if (TextUtils.equals("logging", enableLogging)) {
            LoggerGroup.setLogged(true);
            LoggerGroup.setLocked(true);
            propertyUtil.setProperty("enableLogging", "sending");
            propertyUtil.storeProperty(null);
        } else if (TextUtils.equals("sending", enableLogging)) {
            new Handler(context.getMainLooper()).post(new Runnable() {
                public void run() {
                    Builder message = new Builder(context).setTitle("Log Collector").setIcon(17301659).setMessage("Run Log Collector application.\nIt will collect the device log and send it to <" + (TextUtils.isEmpty(supportEmail) ? "support email" : supportEmail) + ">.\nYou will have an opportunity to review and modify the data being sent.");
                    final Context context = context;
                    final String str = supportEmail;
                    message = message.setPositiveButton(17039370, new OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            RemoteLogging.this.collectAndSendLog(context, str);
                        }
                    });
                    final PropertyUtil propertyUtil = propertyUtil;
                    message = message.setNegativeButton(17039360, new OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            propertyUtil.setProperty("enableLogging", "false");
                            propertyUtil.storeProperty(null);
                        }
                    });
                    final PropertyUtil propertyUtil2 = propertyUtil;
                    AlertDialog dialog = message.setOnCancelListener(new OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            propertyUtil2.setProperty("enableLogging", "false");
                            propertyUtil2.storeProperty(null);
                        }
                    }).create();
                    final PropertyUtil propertyUtil3 = propertyUtil;
                    dialog.setOnDismissListener(new OnDismissListener() {
                        public void onDismiss(DialogInterface dialog) {
                            propertyUtil3.setProperty("enableLogging", "false");
                            propertyUtil3.storeProperty(null);
                        }
                    });
                    dialog.show();
                }
            });
        }
    }

    private void collectAndSendLog(Context context, String... supportEmail) {
        this.mSendIntent = new Intent("android.intent.action.SEND");
        this.mSendIntent.setType("text/plain");
        this.mSendIntent.putExtra("android.intent.extra.EMAIL", supportEmail);
        this.mSendIntent.putExtra("android.intent.extra.SUBJECT", "Android device log");
        StringBuffer buffer = new StringBuffer();
        buffer.append("App Version : ").append(getVersionNumber(context)).append(LINE_SEPARATOR).append("Device Model : ").append(Build.MODEL).append(LINE_SEPARATOR).append("Device Manufacturer : ").append(Build.MANUFACTURER).append(LINE_SEPARATOR).append("OS Version : ").append(VERSION.RELEASE).append(LINE_SEPARATOR).append("Kernal Version : ").append(getFormattedKernelVersion());
        this.mAdditonalInfo = buffer.toString();
        this.mFormat = "time";
        ArrayList<String> list = new ArrayList();
        if (this.mFormat != null) {
            list.add("-v");
            list.add(this.mFormat);
        }
        if (this.mBuffer != null) {
            list.add("-b");
            list.add(this.mBuffer);
        }
        if (this.mFilterSpecs != null) {
            for (String filterSpec : this.mFilterSpecs) {
                list.add(filterSpec);
            }
        }
        this.mCollectLogTask = (CollectLogTask) new CollectLogTask(context).execute(new ArrayList[]{list});
    }

    void showErrorDialog(final Context context, final String errorMessage) {
        new Handler(context.getMainLooper()).post(new Runnable() {
            public void run() {
                new Builder(context).setTitle("Application Log Collector").setMessage(errorMessage).setIcon(17301543).setPositiveButton(17039370, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
            }
        });
    }

    void dismissMainDialog() {
        if (this.mMainDialog != null && this.mMainDialog.isShowing()) {
            this.mMainDialog.dismiss();
            this.mMainDialog = null;
        }
    }

    void showProgressDialog(Context context, String message) {
        this.mProgressDialog = new ProgressDialog(context);
        this.mProgressDialog.setIndeterminate(true);
        this.mProgressDialog.setMessage(message);
        this.mProgressDialog.setCancelable(true);
        this.mProgressDialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                RemoteLogging.this.cancellCollectTask();
            }
        });
        new Handler(context.getMainLooper()).post(new Runnable() {
            public void run() {
                RemoteLogging.this.mProgressDialog.show();
            }
        });
    }

    private void dismissProgressDialog() {
        if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
            this.mProgressDialog.dismiss();
            this.mProgressDialog = null;
        }
    }

    void cancellCollectTask() {
        if (this.mCollectLogTask != null && this.mCollectLogTask.getStatus() == Status.RUNNING) {
            this.mCollectLogTask.cancel(true);
            this.mCollectLogTask = null;
        }
    }

    private String getVersionNumber(Context context) {
        String version = "?";
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return version;
        }
    }

    private String getFormattedKernelVersion() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("/proc/version"), ModuleConfig.SOCIAL_MEDIA_MOUDLE);
            String procVersionStr = reader.readLine();
            reader.close();
            String PROC_VERSION_REGEX = "\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\([^)]+\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)";
            Matcher m = Pattern.compile("\\w+\\s+\\w+\\s+([^\\s]+)\\s+\\(([^\\s@]+(?:@[^\\s.]+)?)[^)]*\\)\\s+\\([^)]+\\)\\s+([^\\s]+)\\s+(?:PREEMPT\\s+)?(.+)").matcher(procVersionStr);
            if (!m.matches()) {
                PushConfig.LogI("Regex did not match on /proc/version: " + procVersionStr);
                return "Unavailable";
            } else if (m.groupCount() >= 4) {
                return new StringBuilder(m.group(1)).append("\n").append(m.group(2)).append(" ").append(m.group(3)).append("\n").append(m.group(4)).toString();
            } else {
                PushConfig.LogI("Regex match on /proc/version only returned " + m.groupCount() + " groups");
                return "Unavailable";
            }
        } catch (IOException e) {
            PushConfig.LogI("IO Exception when getting kernel version for Device Info screen : " + e.toString());
            return "Unavailable";
        } catch (Throwable th) {
            reader.close();
        }
    }
}
