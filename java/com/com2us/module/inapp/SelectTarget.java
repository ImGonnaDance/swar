package com.com2us.module.inapp;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.com2us.module.view.SurfaceViewWrapper;
import com.com2us.peppermint.PeppermintConstant;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONException;
import org.json.JSONObject;

class SelectTarget {
    private static String Popup_cancel = "Cancel";
    private static String Popup_ok = "Select";
    private static String Popup_title = "Select Payment Type";
    public static final int TARGETING_FAILED = 201;
    public static final int TARGETING_SUCCESS = 200;
    private static String Text_targetA = "Lebi";
    private static String Text_targetB = "Google Play";
    private static ProgressDialog progressDialog = null;
    private static Dialog selectTargetTypePopup = null;
    private static Thread selectThread = null;

    class AnonymousClass1 implements Runnable {
        private final /* synthetic */ Activity val$activity;
        private final /* synthetic */ SurfaceViewWrapper val$glView;
        private final /* synthetic */ SelectTargetCallback val$targetCB;
        private final /* synthetic */ int val$targetCBRef;

        AnonymousClass1(Activity activity, SelectTargetCallback selectTargetCallback, SurfaceViewWrapper surfaceViewWrapper, int i) {
            this.val$activity = activity;
            this.val$targetCB = selectTargetCallback;
            this.val$glView = surfaceViewWrapper;
            this.val$targetCBRef = i;
        }

        public void run() {
            int target;
            SelectTarget.ProgressDialogShow(this.val$activity);
            PropertyUtil propertyUtil = new PropertyUtil(this.val$activity);
            JSONObject jsonObjectPostData = new JSONObject();
            String appversion = i.a;
            try {
                appversion = this.val$activity.getPackageManager().getPackageInfo(this.val$activity.getPackageName(), 0).versionName;
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
            TelephonyManager tel = (TelephonyManager) this.val$activity.getSystemService("phone");
            String networkOperator = tel.getNetworkOperator();
            String simOperator = tel.getSimState() == 5 ? tel.getSimOperator() : null;
            String operator = i.a;
            if (!TextUtils.isEmpty(networkOperator)) {
                operator = networkOperator;
                DefaultBilling.LogI("SelectTarget get networkOperator = " + operator);
            } else if (TextUtils.isEmpty(simOperator)) {
                operator = i.a;
            } else {
                operator = simOperator;
                DefaultBilling.LogI("SelectTarget get simOperator = " + operator);
            }
            int mcc = 0;
            int mnc = 0;
            if (TextUtils.isEmpty(operator)) {
                DefaultBilling.LogI("SelectTarget getOperator is empty");
            } else {
                try {
                    mcc = Integer.parseInt(operator.substring(0, 3));
                    DefaultBilling.LogI("SelectTarget mcc = " + mcc);
                    mnc = Integer.parseInt(operator.substring(3));
                    DefaultBilling.LogI("SelectTarget mnc = " + mnc);
                } catch (Exception e2) {
                    DefaultBilling.LogI("SelectTarget mcc | mnc parse exception : " + e2.toString());
                    mcc = 0;
                    mnc = 0;
                }
            }
            String strLast_iap = propertyUtil.getProperty("last_iap");
            int last_iap = 0;
            if (TextUtils.isEmpty(strLast_iap)) {
                DefaultBilling.LogI("SelectTarget last_iap is empty");
            } else {
                try {
                    last_iap = Integer.parseInt(strLast_iap);
                    DefaultBilling.LogI("SelectTarget last_iap : " + last_iap);
                } catch (Exception e22) {
                    DefaultBilling.LogI("SelectTarget string selected parse exception : " + e22.toString());
                    last_iap = 0;
                }
            }
            boolean google_service = true;
            try {
                SelectTarget.checkGoogleService(this.val$activity);
            } catch (Exception e222) {
                DefaultBilling.LogI("SelectTarget check device exception : " + e222.toString());
                google_service = false;
            }
            DefaultBilling.LogI("SelectTarget google_service : " + google_service);
            try {
                jsonObjectPostData.put(PeppermintConstant.JSON_KEY_APP_ID, this.val$activity.getPackageName());
                jsonObjectPostData.put("appversion", appversion);
                jsonObjectPostData.put("device", Build.MODEL);
                jsonObjectPostData.put("osversion", VERSION.RELEASE);
                jsonObjectPostData.put(PeppermintConstant.JSON_KEY_COUNTRY, this.val$activity.getResources().getConfiguration().locale.getCountry().toUpperCase(Locale.US));
                jsonObjectPostData.put(PeppermintConstant.JSON_KEY_LANGUAGE, this.val$activity.getResources().getConfiguration().locale.getLanguage().toUpperCase(Locale.US));
                jsonObjectPostData.put("libver", InApp.Version);
                if (mcc != 0) {
                    jsonObjectPostData.put("mcc", mcc);
                }
                if (mnc != 0) {
                    jsonObjectPostData.put("mnc", mnc);
                }
                if (last_iap != 0) {
                    jsonObjectPostData.put("last_iap", last_iap);
                }
                jsonObjectPostData.put("google_service", google_service);
            } catch (Exception e2222) {
                DefaultBilling.LogI("SelectTarget create jsonData exception : " + e2222.toString());
            }
            DefaultBilling.LogI("SelectTarget jsonObjectPostData : " + jsonObjectPostData.toString());
            String responseStr = DefaultBilling.sendToServer(jsonObjectPostData.toString(), "http://s.com2us.net/common/billing/check2.c2s");
            DefaultBilling.LogI("responseStr : " + responseStr);
            if (responseStr == null) {
                try {
                    responseStr = i.a;
                } catch (Exception e22222) {
                    DefaultBilling.LogI("SelectTarget responseStr exception : " + e22222.toString());
                    target = last_iap;
                }
            }
            target = new JSONObject(responseStr).getInt(PeppermintConstant.JSON_KEY_TYPE);
            DefaultBilling.LogI("Target : " + target);
            SelectTarget.ProgressDialogDismiss(this.val$activity);
            if (target > 0) {
                if (target == 1 && SelectTarget.isExistLibrary(InApp.getClassName(15, false))) {
                    target = 15;
                }
                if (target == 100) {
                    SelectTarget.showSelectTargetTypePopup(this.val$activity, target, jsonObjectPostData, this.val$targetCB, this.val$glView, this.val$targetCBRef);
                    return;
                } else if (SelectTarget.isExistLibrary(InApp.getClassName(target, false))) {
                    SelectTarget.resultPostTarget(this.val$targetCB, this.val$glView, this.val$targetCBRef, SelectTarget.TARGETING_SUCCESS, target);
                    return;
                } else {
                    SelectTarget.resultPostTarget(this.val$targetCB, this.val$glView, this.val$targetCBRef, SelectTarget.TARGETING_FAILED, target * -1);
                    return;
                }
            }
            target = 1;
            if (1 == 1 && SelectTarget.isExistLibrary(InApp.getClassName(15, false))) {
                target = 15;
            }
            SelectTarget.resultPostTarget(this.val$targetCB, this.val$glView, this.val$targetCBRef, SelectTarget.TARGETING_SUCCESS, target);
        }
    }

    class AnonymousClass2 implements OnClickListener {
        private final /* synthetic */ RadioButton val$RBTargetB;
        private final /* synthetic */ Activity val$activity;
        private final /* synthetic */ JSONObject val$data;
        private final /* synthetic */ SurfaceViewWrapper val$glView;
        private final /* synthetic */ SelectTargetCallback val$targetCB;
        private final /* synthetic */ int val$targetCBRef;

        AnonymousClass2(Activity activity, RadioButton radioButton, SelectTargetCallback selectTargetCallback, SurfaceViewWrapper surfaceViewWrapper, int i, JSONObject jSONObject) {
            this.val$activity = activity;
            this.val$RBTargetB = radioButton;
            this.val$targetCB = selectTargetCallback;
            this.val$glView = surfaceViewWrapper;
            this.val$targetCBRef = i;
            this.val$data = jSONObject;
        }

        public void onClick(DialogInterface dialog, int which) {
            int targetNumber = 15;
            PropertyUtil propertyUtil = new PropertyUtil(this.val$activity);
            if (this.val$RBTargetB.isChecked()) {
                if (!SelectTarget.isExistLibrary(InApp.getClassName(15, false))) {
                    targetNumber = 1;
                }
                SelectTarget.resultPostTarget(this.val$targetCB, this.val$glView, this.val$targetCBRef, SelectTarget.TARGETING_SUCCESS, targetNumber);
            } else {
                targetNumber = 8;
                SelectTarget.resultPostTarget(this.val$targetCB, this.val$glView, this.val$targetCBRef, SelectTarget.TARGETING_SUCCESS, 8);
            }
            final JSONObject jSONObject = this.val$data;
            new Thread(new Runnable() {
                public void run() {
                    try {
                        JSONObject sendData = jSONObject;
                        sendData.put("select", targetNumber);
                        DefaultBilling.sendToServer(sendData.toString(), "http://s.com2us.net/common/billing/select_log.c2s");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            dialog.dismiss();
        }
    }

    class AnonymousClass5 implements Runnable {
        private final /* synthetic */ RadioButton val$RBTargetA;
        private final /* synthetic */ Builder val$builder;

        AnonymousClass5(RadioButton radioButton, Builder builder) {
            this.val$RBTargetA = radioButton;
            this.val$builder = builder;
        }

        public void run() {
            this.val$RBTargetA.setChecked(true);
            SelectTarget.selectTargetTypePopup = this.val$builder.create();
            SelectTarget.selectTargetTypePopup.show();
        }
    }

    class AnonymousClass6 implements Runnable {
        private final /* synthetic */ int val$state;
        private final /* synthetic */ int val$target;
        private final /* synthetic */ int val$targetCBRef;

        AnonymousClass6(int i, int i2, int i3) {
            this.val$targetCBRef = i;
            this.val$state = i2;
            this.val$target = i3;
        }

        public void run() {
            InApp.resultPostTarget(this.val$targetCBRef, this.val$state, this.val$target);
        }
    }

    class AnonymousClass7 implements Runnable {
        private final /* synthetic */ Activity val$activity;

        AnonymousClass7(Activity activity) {
            this.val$activity = activity;
        }

        public void run() {
            try {
                SelectTarget.progressDialog = SelectTarget.onCreateProgressDialog(this.val$activity);
                SelectTarget.progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static class PropertyUtil {
        private static PropertyUtil m_propertyUtil = null;
        private String PROPERTIY_FILE;
        private String TAG;
        private File m_profile;
        private Properties m_pros;

        public static PropertyUtil getInstance(Context context) {
            if (m_propertyUtil == null) {
                m_propertyUtil = new PropertyUtil(context);
            }
            return m_propertyUtil;
        }

        private PropertyUtil(Context context) {
            Exception e;
            Throwable th;
            this.TAG = "InApp";
            this.PROPERTIY_FILE = "/" + this.TAG + ".properties";
            this.m_profile = null;
            this.m_pros = null;
            this.PROPERTIY_FILE = "/" + this.TAG + ".properties";
            this.m_profile = new File(new StringBuilder(String.valueOf(context.getFilesDir().getPath())).append(this.PROPERTIY_FILE).toString());
            this.m_pros = new Properties();
            FileInputStream m_fis = null;
            try {
                if (!this.m_profile.getParentFile().exists()) {
                    this.m_profile.getParentFile().mkdir();
                }
                if (!this.m_profile.exists()) {
                    this.m_profile.createNewFile();
                }
                FileInputStream m_fis2 = new FileInputStream(this.m_profile);
                try {
                    this.m_pros.load(m_fis2);
                    if (m_fis2 != null) {
                        try {
                            m_fis2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        }
                        return;
                    }
                } catch (Exception e3) {
                    e = e3;
                    m_fis = m_fis2;
                    try {
                        e.printStackTrace();
                        if (m_fis != null) {
                            try {
                                m_fis.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (m_fis != null) {
                            try {
                                m_fis.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    m_fis = m_fis2;
                    if (m_fis != null) {
                        m_fis.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
                if (m_fis != null) {
                    m_fis.close();
                }
            }
        }

        public String getProperty(String key) {
            return this.m_pros.getProperty(key);
        }

        public synchronized void setProperty(String key, String value) {
            this.m_pros.setProperty(key, value);
        }

        public void clearProperty() {
            this.m_pros.clear();
        }

        public Enumeration<Object> keysProperty() {
            return this.m_pros.keys();
        }

        public synchronized void storeProperty(String comment) {
            Throwable th;
            Exception e;
            FileOutputStream m_fos = null;
            try {
                FileOutputStream m_fos2 = new FileOutputStream(this.m_profile);
                try {
                    this.m_pros.store(m_fos2, comment);
                    if (m_fos2 != null) {
                        try {
                            m_fos2.close();
                            m_fos = m_fos2;
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        } catch (Throwable th2) {
                            th = th2;
                            m_fos = m_fos2;
                            throw th;
                        }
                    }
                    m_fos = m_fos2;
                } catch (Exception e3) {
                    e = e3;
                    m_fos = m_fos2;
                    try {
                        e.printStackTrace();
                        if (m_fos != null) {
                            try {
                                m_fos.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            } catch (Throwable th3) {
                                th = th3;
                                throw th;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        if (m_fos != null) {
                            try {
                                m_fos.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    m_fos = m_fos2;
                    if (m_fos != null) {
                        m_fos.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                e.printStackTrace();
                if (m_fos != null) {
                    m_fos.close();
                }
            }
        }

        public void deleteFile() {
            this.m_profile.delete();
        }
    }

    SelectTarget() {
    }

    public static void iapSelectTarget(Activity activity, SurfaceViewWrapper glView, SelectTargetCallback targetCB, int targetCBRef) {
        if (activity == null) {
            DefaultBilling.LogI("iapSelectTarget - activity is null");
        } else if (selectThread == null || !selectThread.isAlive()) {
            selectThread = new Thread(new AnonymousClass1(activity, targetCB, glView, targetCBRef));
            selectThread.start();
        } else {
            DefaultBilling.LogI("iapSelectTarget - selectThread is Alive, return");
        }
    }

    protected static boolean iapStoreTarget(Context context, int targetNumber) {
        try {
            PropertyUtil propertyUtil = new PropertyUtil(context);
            propertyUtil.setProperty("last_iap", String.valueOf(targetNumber));
            propertyUtil.storeProperty(null);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void showSelectTargetTypePopup(Activity activity, int target, JSONObject data, SelectTargetCallback targetCB, SurfaceViewWrapper glView, int targetCBRef) {
        setLanguage(activity, target);
        RadioGroup RGtarget = new RadioGroup(activity);
        RadioButton RBTargetA = new RadioButton(activity);
        RadioButton RBTargetB = new RadioButton(activity);
        RGtarget.setBackgroundColor(-1);
        RBTargetA.setText(Text_targetA);
        RBTargetA.setTextColor(-16777216);
        RBTargetB.setText(Text_targetB);
        RBTargetB.setTextColor(-16777216);
        RGtarget.setScrollbarFadingEnabled(true);
        RGtarget.setScrollBarStyle(0);
        LayoutParams layoutParams = new RadioGroup.LayoutParams(-2, -2);
        RGtarget.addView(RBTargetA, RBTargetA.getId(), layoutParams);
        RGtarget.addView(RBTargetB, RBTargetB.getId(), layoutParams);
        Builder builder = new Builder(activity);
        builder.setTitle(Popup_title);
        builder.setInverseBackgroundForced(true);
        builder.setCancelable(false);
        builder.setView(RGtarget);
        builder.setPositiveButton(Popup_ok, new AnonymousClass2(activity, RBTargetB, targetCB, glView, targetCBRef, data));
        builder.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
            }
        });
        if (selectTargetTypePopup != null) {
            try {
                if (selectTargetTypePopup.isShowing()) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            SelectTarget.selectTargetTypePopup.dismiss();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        activity.runOnUiThread(new AnonymousClass5(RBTargetA, builder));
    }

    private static boolean isExistLibrary(String className) {
        try {
            if (Class.forName(className) != null) {
                return true;
            }
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private static void checkGoogleService(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.android.vending", 0);
        } catch (NameNotFoundException e) {
            throw new UnsupportedOperationException("Device does not have package com.android.vending");
        }
    }

    private static void resultPostTarget(SelectTargetCallback targetCB, SurfaceViewWrapper glView, int targetCBRef, int state, int target) {
        if (targetCBRef == 0 || glView == null) {
            switch (state) {
                case TARGETING_SUCCESS /*200*/:
                    targetCB.TARGETING_SUCCESS(target);
                    return;
                case TARGETING_FAILED /*201*/:
                    targetCB.TARGETING_FAILED(target);
                    return;
                default:
                    return;
            }
        }
        glView.queueEvent(new AnonymousClass6(targetCBRef, state, target));
    }

    private static void setLanguage(Activity activity, int target) {
        String country = activity.getResources().getConfiguration().locale.getCountry().toLowerCase(Locale.US);
        String language = activity.getResources().getConfiguration().locale.getLanguage().toLowerCase(Locale.US);
        DefaultBilling.LogI("set Country is : " + country);
        DefaultBilling.LogI("set Language is : " + language);
        if (TextUtils.equals("ko", language)) {
            Popup_title = "\uacb0\uc81c \ubc29\uc2dd \uc120\ud0dd";
            Popup_ok = "\uc120\ud0dd";
            Popup_cancel = "\ucde8\uc18c";
            Text_targetA = "Lebi";
            Text_targetB = "Google Play";
        } else if (TextUtils.equals("us", language)) {
            Popup_title = "Select Payment Type";
            Popup_ok = "Select";
            Popup_cancel = "Cancel";
            Text_targetA = "Lebi";
            Text_targetB = "Google Play";
        } else if (TextUtils.equals("tw", country)) {
            Popup_title = "\u8bf7\u9009\u62e9\u652f\u4ed8\u65b9\u5f0f";
            Popup_ok = "\u78ba\u8a8d";
            Popup_cancel = "\u53d6\u6d88";
            Text_targetA = "\u6a02\u5e63(\u7528mycard\u5132\u503c)";
            Text_targetB = "Google\u96fb\u5b50\u9322\u5305";
        } else {
            Popup_title = "\u8bf7\u9009\u62e9\u652f\u4ed8\u65b9\u5f0f";
            Popup_ok = "\u786e\u8ba4";
            Popup_cancel = "\u53d6\u6d88";
            Text_targetA = "\u4e50\u5e01(\u7528\u795e\u5dde\u884c, \u652f\u4ed8\u5b9d\u5145\u503c)";
            Text_targetB = "Google\u7535\u5b50\u94b1\u5305";
        }
    }

    private static ProgressDialog onCreateProgressDialog(Activity activity) {
        ProgressDialog pDialog = new ProgressDialog(activity);
        pDialog.setProgressStyle(0);
        pDialog.setMessage("Checking your billing information...");
        pDialog.setCancelable(true);
        return pDialog;
    }

    private static synchronized void ProgressDialogShow(Activity activity) {
        synchronized (SelectTarget.class) {
            if (progressDialog == null || !progressDialog.isShowing()) {
                activity.runOnUiThread(new AnonymousClass7(activity));
            }
        }
    }

    private static synchronized void ProgressDialogDismiss(Activity activity) {
        synchronized (SelectTarget.class) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        if (SelectTarget.progressDialog != null) {
                            SelectTarget.progressDialog.dismiss();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
