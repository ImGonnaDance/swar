package com.com2us.module.push;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.amazonpush.ADMMessageHandler;
import com.com2us.module.amazonpush.AmazonPush;
import com.com2us.module.jpush.JPushBaseIntentService;
import com.com2us.module.jpush.JPushReceiver;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.peppermint.PeppermintConstant;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.f;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class PushConfig {
    private static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState = null;
    protected static boolean LOG = false;
    public static final String LOG_TAG = "Push";
    public static final String[] PUSH_LOG_TAGS = new String[]{LOG_TAG, JPushReceiver.TAG, JPushBaseIntentService.TAG, JPushReceiver.TAG, AmazonPush.TAG, ADMMessageHandler.TAG};
    public static final String VERSION = "3.0.1";
    private static String VID = i.a;
    protected static boolean isUseTestServer = false;
    protected static Logger logger = LoggerGroup.createLogger(LOG_TAG);
    protected static final String realServerSenderURL = "http://push.com2us.net/api/";
    protected static final String realServerURL = "https://push.com2us.net/api/";
    protected static final String testServerURL = "http://mdev.com2us.net/api/";

    protected static class PropertyUtil {
        private static File m_profile = null;
        private static PropertyUtil m_propertyUtil = null;
        private static Properties m_pros = null;
        private final String PROPERTIY_FILE = "/PushConfig.properties";
        private Context context = null;

        private PropertyUtil(Context _context) {
            this.context = _context;
            m_profile = new File(new StringBuilder(String.valueOf(_context.getApplicationContext().getFilesDir().getPath())).append("/PushConfig.properties").toString());
            PushConfig.LogI("m_profile Path : " + _context.getApplicationContext().getFilesDir().getPath() + "/PushConfig.properties");
            m_pros = new Properties();
            try {
                if (!m_profile.getParentFile().exists()) {
                    m_profile.getParentFile().mkdir();
                }
                if (!m_profile.exists()) {
                    m_profile.createNewFile();
                }
            } catch (Exception e) {
                e.printStackTrace();
                PushConfig.LogI("Property File Load Failed -> " + e.getMessage());
            }
        }

        public static synchronized PropertyUtil getInstance(Context context) {
            PropertyUtil propertyUtil;
            synchronized (PropertyUtil.class) {
                if (m_propertyUtil == null) {
                    m_propertyUtil = new PropertyUtil(context.getApplicationContext());
                }
                propertyUtil = m_propertyUtil;
            }
            return propertyUtil;
        }

        public synchronized void loadProperty() {
            IOException e;
            Throwable th;
            FileInputStream m_fis = null;
            try {
                FileInputStream m_fis2 = new FileInputStream(m_profile);
                try {
                    m_pros.load(m_fis2);
                    if (m_fis2 != null) {
                        try {
                            m_fis2.close();
                        } catch (IOException e2) {
                            e2.printStackTrace();
                        } catch (Throwable th2) {
                            th = th2;
                            m_fis = m_fis2;
                            throw th;
                        }
                    }
                } catch (IOException e3) {
                    e2 = e3;
                    m_fis = m_fis2;
                    try {
                        e2.printStackTrace();
                        if (m_fis != null) {
                            try {
                                m_fis.close();
                            } catch (IOException e22) {
                                e22.printStackTrace();
                            } catch (Throwable th3) {
                                th = th3;
                                throw th;
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        if (m_fis != null) {
                            try {
                                m_fis.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    m_fis = m_fis2;
                    if (m_fis != null) {
                        m_fis.close();
                    }
                    throw th;
                }
            } catch (IOException e4) {
                e222 = e4;
                e222.printStackTrace();
                if (m_fis != null) {
                    m_fis.close();
                }
            }
        }

        public String getAppID() {
            return ModuleManager.getDatas(this.context).getAppID();
        }

        public String getAppVersion() {
            return ModuleManager.getDatas(this.context).getAppVersion();
        }

        public String getAndroidID() {
            return ModuleManager.getDatas(this.context).getAndroidID();
        }

        public String getCountry() {
            return ModuleManager.getDatas(this.context).getCountry();
        }

        public String getDeviceModel() {
            return ModuleManager.getDatas(this.context).getDeviceModel();
        }

        public String getDeviceID() {
            return ModuleManager.getDatas(this.context).getDeviceID();
        }

        public String getDID() {
            return ModuleManager.getDatas(this.context).getDID();
        }

        public String getLanguage() {
            return ModuleManager.getDatas(this.context).getLanguage();
        }

        public String getMacAddress() {
            return ModuleManager.getDatas(this.context).getMacAddress();
        }

        public String getMobileCountryCode() {
            return ModuleManager.getDatas(this.context).getMobileCountryCode();
        }

        public String getMobileNetworkCode() {
            return ModuleManager.getDatas(this.context).getMobileNetworkCode();
        }

        public String getVID() {
            return ModuleManager.getDatas(this.context).getVID();
        }

        public String getProperty(String key) {
            return m_pros.getProperty(key);
        }

        public synchronized void setAppID(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setAppID(value);
        }

        public synchronized void setAppVersion(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setAppVersion(value);
        }

        public synchronized void setAndroidID(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setAndroidID(value);
        }

        public synchronized void setCountry(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setCountry(value);
        }

        public synchronized void setDeviceModel(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setDeviceModel(value);
        }

        public synchronized void setDeviceID(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setDeviceID(value);
        }

        public synchronized void setDID(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setDID(value);
        }

        public synchronized void setLanguage(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setLanguage(value);
        }

        public synchronized void setMacAddress(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setMacAddress(value);
        }

        public synchronized void setMobileCountryCode(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setMobileCountryCode(value);
        }

        public synchronized void setMobileNetworkCode(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setMobileNetworkCode(value);
        }

        public synchronized void setVID(String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            ModuleManager.getDatas(this.context).setVID(value);
        }

        public synchronized void setProperty(String key, String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
            m_pros.setProperty(key, value);
        }

        public void clearProperty() {
            m_pros.clear();
        }

        public Enumeration<Object> keysProperty() {
            return m_pros.keys();
        }

        public synchronized void storeProperty(String comment) {
            Throwable th;
            Exception e;
            FileOutputStream m_fos = null;
            try {
                FileOutputStream m_fos2 = new FileOutputStream(m_profile);
                try {
                    m_pros.store(m_fos2, comment);
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
            m_profile.delete();
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState() {
        int[] iArr = $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState;
        if (iArr == null) {
            iArr = new int[RegisterState.values().length];
            try {
                iArr[RegisterState.LOGIN.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[RegisterState.LOGOUT.ordinal()] = 5;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[RegisterState.REGISTER.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[RegisterState.SENDER.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[RegisterState.UNREGISTER.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$com$com2us$module$push$Push$RegisterState = iArr;
        }
        return iArr;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.content.Intent setReceiveData(android.content.Context r37, android.content.Intent r38) {
        /*
        r29 = com.com2us.module.push.PushConfig.PropertyUtil.getInstance(r37);
        r29.loadProperty();
        r33 = com.com2us.module.push.RemoteLogging.getInstance();
        r0 = r33;
        r1 = r37;
        r2 = r38;
        r0.setEnableLogging(r1, r2);
        r33 = "true";
        r34 = "isUseTestServer";
        r0 = r29;
        r1 = r34;
        r34 = r0.getProperty(r1);
        r33 = r33.equals(r34);
        if (r33 == 0) goto L_0x0073;
    L_0x0026:
        r33 = 1;
        isUseTestServer = r33;
    L_0x002a:
        r33 = "true";
        r34 = "log";
        r0 = r29;
        r1 = r34;
        r34 = r0.getProperty(r1);
        r33 = r33.equals(r34);
        if (r33 == 0) goto L_0x0078;
    L_0x003c:
        r33 = 1;
        r34 = PUSH_LOG_TAGS;
        com.com2us.module.util.LoggerGroup.setLogged(r33, r34);
        r33 = 1;
        LOG = r33;
    L_0x0047:
        r33 = "0";
        r34 = "isPush";
        r0 = r29;
        r1 = r34;
        r34 = r0.getProperty(r1);
        r33 = r33.equals(r34);
        if (r33 != 0) goto L_0x006b;
    L_0x0059:
        r33 = "2";
        r34 = "isPush";
        r0 = r29;
        r1 = r34;
        r34 = r0.getProperty(r1);
        r33 = r33.equals(r34);
        if (r33 == 0) goto L_0x0084;
    L_0x006b:
        r33 = "isPush : DEFAULT FALSE or USER FALSE";
        LogI(r33);
        r38 = 0;
    L_0x0072:
        return r38;
    L_0x0073:
        r33 = 0;
        isUseTestServer = r33;
        goto L_0x002a;
    L_0x0078:
        r33 = 0;
        r34 = PUSH_LOG_TAGS;
        com.com2us.module.util.LoggerGroup.setLogged(r33, r34);
        r33 = 0;
        LOG = r33;
        goto L_0x0047;
    L_0x0084:
        r19 = 0;
        r25 = 0;
        r33 = r38.getExtras();
        r34 = "icon";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 == 0) goto L_0x09b1;
    L_0x0098:
        r33 = "";
    L_0x009a:
        r34 = "drawable";
        r35 = r37.getPackageName();
        r0 = r37;
        r1 = r33;
        r2 = r34;
        r3 = r35;
        r16 = ResourceID(r0, r1, r2, r3);
        if (r16 != 0) goto L_0x09e1;
    L_0x00ae:
        r19 = 0;
        r33 = r37.getApplicationInfo();
        r0 = r33;
        r0 = r0.icon;
        r16 = r0;
        r33 = r38.getExtras();
        r34 = "icon";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x00ea;
    L_0x00ca:
        r33 = "ReceiveData icon setting...";
        LogI(r33);
        r33 = r38.getExtras();	 Catch:{ Exception -> 0x09c6 }
        r34 = "icon";
        r33 = r33.getString(r34);	 Catch:{ Exception -> 0x09c6 }
        r15 = getByteFromUrl(r33);	 Catch:{ Exception -> 0x09c6 }
        if (r15 == 0) goto L_0x09bd;
    L_0x00df:
        r19 = 1;
        r33 = "iconData";
        r0 = r38;
        r1 = r33;
        r0.putExtra(r1, r15);	 Catch:{ Exception -> 0x09c6 }
    L_0x00ea:
        r33 = "ic_c2s_notification_small_icon";
        r34 = "drawable";
        r35 = r37.getPackageName();
        r0 = r37;
        r1 = r33;
        r2 = r34;
        r3 = r35;
        r31 = ResourceID(r0, r1, r2, r3);
        if (r31 != 0) goto L_0x09e5;
    L_0x0100:
        r25 = 0;
        r33 = r37.getApplicationInfo();
        r0 = r33;
        r0 = r0.icon;
        r31 = r0;
    L_0x010c:
        r33 = "smallIconResID";
        r0 = r38;
        r1 = r33;
        r2 = r31;
        r0.putExtra(r1, r2);
        if (r19 != 0) goto L_0x09e9;
    L_0x0119:
        if (r25 == 0) goto L_0x09e9;
    L_0x011b:
        r33 = android.os.Build.VERSION.SDK_INT;
        r34 = 21;
        r0 = r33;
        r1 = r34;
        if (r0 < r1) goto L_0x09e9;
    L_0x0125:
        r33 = "isSetLargeIcon";
        r34 = 0;
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r33 = "lollipop icon setting : false";
        LogI(r33);
    L_0x0137:
        r33 = r38.getExtras();
        r34 = "icon_color";
        r14 = r33.getString(r34);
        r12 = 0;
        r17 = 0;
        r33 = android.text.TextUtils.isEmpty(r14);
        if (r33 != 0) goto L_0x017a;
    L_0x014a:
        r13 = new org.json.JSONObject;	 Catch:{ Exception -> 0x09fd }
        r13.<init>(r14);	 Catch:{ Exception -> 0x09fd }
        r33 = "r";
        r34 = 0;
        r0 = r33;
        r1 = r34;
        r30 = r13.optInt(r0, r1);	 Catch:{ Exception -> 0x0b15 }
        r33 = "g";
        r34 = 0;
        r0 = r33;
        r1 = r34;
        r11 = r13.optInt(r0, r1);	 Catch:{ Exception -> 0x0b15 }
        r33 = "b";
        r34 = 0;
        r0 = r33;
        r1 = r34;
        r5 = r13.optInt(r0, r1);	 Catch:{ Exception -> 0x0b15 }
        r0 = r30;
        r17 = android.graphics.Color.rgb(r0, r11, r5);	 Catch:{ Exception -> 0x0b15 }
        r12 = r13;
    L_0x017a:
        r33 = "icon_color";
        r0 = r38;
        r1 = r33;
        r2 = r17;
        r0.putExtra(r1, r2);
        r33 = new java.lang.StringBuilder;
        r34 = "lollipop icon color setting : ";
        r33.<init>(r34);
        r0 = r33;
        r1 = r17;
        r33 = r0.append(r1);
        r33 = r33.toString();
        LogI(r33);
        r6 = 0;
        r33 = "keyguard";
        r0 = r37;
        r1 = r33;
        r26 = r0.getSystemService(r1);
        r26 = (android.app.KeyguardManager) r26;
        r6 = r26.inKeyguardRestrictedInputMode();
        r33 = new java.lang.StringBuilder;
        r34 = "bScreenLock : ";
        r33.<init>(r34);
        r0 = r33;
        r33 = r0.append(r6);
        r33 = r33.toString();
        LogI(r33);
        r33 = "power";
        r0 = r37;
        r1 = r33;
        r28 = r0.getSystemService(r1);
        r28 = (android.os.PowerManager) r28;
        if (r6 != 0) goto L_0x0a1e;
    L_0x01ce:
        r33 = android.os.Build.VERSION.SDK_INT;
        r34 = 21;
        r0 = r33;
        r1 = r34;
        if (r0 < r1) goto L_0x0a18;
    L_0x01d8:
        r33 = r28.isInteractive();
        if (r33 == 0) goto L_0x0a1e;
    L_0x01de:
        r6 = 0;
    L_0x01df:
        r33 = new java.lang.StringBuilder;
        r34 = "bScreenLock || !pm.isScreenOn() : ";
        r33.<init>(r34);
        r0 = r33;
        r33 = r0.append(r6);
        r33 = r33.toString();
        LogI(r33);
        r23 = 1;
        r33 = "isSound";
        r0 = r29;
        r1 = r33;
        r33 = r0.getProperty(r1);	 Catch:{ Exception -> 0x0a31 }
        if (r33 != 0) goto L_0x0a21;
    L_0x0201:
        r23 = 1;
    L_0x0203:
        r24 = 1;
        r33 = "isVib";
        r0 = r29;
        r1 = r33;
        r33 = r0.getProperty(r1);	 Catch:{ Exception -> 0x0a46 }
        if (r33 != 0) goto L_0x0a36;
    L_0x0211:
        r24 = 1;
    L_0x0213:
        r22 = isRunningThisActivity(r37);
        r21 = 1;
        r33 = "false";
        r34 = "isOperation";
        r0 = r29;
        r1 = r34;
        r34 = r0.getProperty(r1);	 Catch:{ Exception -> 0x0a4f }
        r33 = r33.equals(r34);	 Catch:{ Exception -> 0x0a4f }
        if (r33 == 0) goto L_0x0a4b;
    L_0x022b:
        r21 = 0;
    L_0x022d:
        if (r6 == 0) goto L_0x0a54;
    L_0x022f:
        r21 = 1;
    L_0x0231:
        r20 = 1;
        r33 = "false";
        r34 = "isGCMOperation";
        r0 = r29;
        r1 = r34;
        r34 = r0.getProperty(r1);	 Catch:{ Exception -> 0x0a66 }
        r33 = r33.equals(r34);	 Catch:{ Exception -> 0x0a66 }
        if (r33 == 0) goto L_0x0a62;
    L_0x0245:
        r20 = 0;
    L_0x0247:
        if (r6 == 0) goto L_0x0a6b;
    L_0x0249:
        r20 = 1;
    L_0x024b:
        r33 = r38.getExtras();
        r34 = "noticeID";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 == 0) goto L_0x027b;
    L_0x025b:
        r33 = "ReceiveData noticeID is null or not String.";
        LogI(r33);
        r33 = "noticeID";
        r34 = r38.getExtras();	 Catch:{ Exception -> 0x0a79 }
        r35 = "noticeID";
        r36 = 1;
        r34 = r34.getInt(r35, r36);	 Catch:{ Exception -> 0x0a79 }
        r34 = java.lang.String.valueOf(r34);	 Catch:{ Exception -> 0x0a79 }
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0a79 }
    L_0x027b:
        r33 = r38.getExtras();
        r34 = "badge";
        r32 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r32);	 Catch:{ Exception -> 0x0a9d }
        if (r33 == 0) goto L_0x0a90;
    L_0x028b:
        r33 = "badge";
        r34 = r38.getExtras();	 Catch:{ Exception -> 0x0a9d }
        r35 = "badge";
        r36 = 1;
        r34 = r34.getInt(r35, r36);	 Catch:{ Exception -> 0x0a9d }
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0a9d }
    L_0x02a2:
        r33 = android.os.Build.VERSION.SDK_INT;
        r34 = 16;
        r0 = r33;
        r1 = r34;
        if (r0 < r1) goto L_0x0ad4;
    L_0x02ac:
        r33 = r38.getExtras();
        r34 = "bigpicture";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x0ad4;
    L_0x02bc:
        r33 = "ReceiveData bigpicture setting...";
        LogI(r33);
        r33 = r38.getExtras();	 Catch:{ Exception -> 0x0abb }
        r34 = "bigpicture";
        r33 = r33.getString(r34);	 Catch:{ Exception -> 0x0abb }
        r7 = getByteFromUrl(r33);	 Catch:{ Exception -> 0x0abb }
        if (r7 == 0) goto L_0x0ab4;
    L_0x02d1:
        r33 = "bigpictureData";
        r0 = r38;
        r1 = r33;
        r0.putExtra(r1, r7);	 Catch:{ Exception -> 0x0abb }
    L_0x02da:
        r33 = r38.getExtras();	 Catch:{ Exception -> 0x0ade }
        r34 = "buckettype";
        r35 = 0;
        r9 = r33.getInt(r34, r35);	 Catch:{ Exception -> 0x0ade }
        r33 = "buckettype";
        if (r9 < 0) goto L_0x0adb;
    L_0x02ea:
        r0 = r38;
        r1 = r33;
        r0.putExtra(r1, r9);	 Catch:{ Exception -> 0x0ade }
    L_0x02f1:
        r33 = r38.getExtras();	 Catch:{ Exception -> 0x0af8 }
        r34 = "bucketsize";
        r35 = 1;
        r8 = r33.getInt(r34, r35);	 Catch:{ Exception -> 0x0af8 }
        r33 = "bucketsize";
        r34 = 1;
        r0 = r34;
        if (r8 < r0) goto L_0x0af5;
    L_0x0305:
        r0 = r38;
        r1 = r33;
        r0.putExtra(r1, r8);	 Catch:{ Exception -> 0x0af8 }
    L_0x030c:
        r33 = r38.getExtras();
        r34 = "htmltitle";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x0331;
    L_0x031c:
        r33 = "title";
        r34 = r38.getExtras();
        r35 = "htmltitle";
        r34 = r34.getString(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x0331:
        r33 = r38.getExtras();
        r34 = "cn.jpush.android.NOTIFICATION_CONTENT_TITLE";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x0356;
    L_0x0341:
        r33 = "title";
        r34 = r38.getExtras();
        r35 = "cn.jpush.android.NOTIFICATION_CONTENT_TITLE";
        r34 = r34.getString(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x0356:
        r33 = r38.getExtras();
        r34 = "htmlmsg";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x037b;
    L_0x0366:
        r33 = "msg";
        r34 = r38.getExtras();
        r35 = "htmlmsg";
        r34 = r34.getString(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x037b:
        r33 = r38.getExtras();
        r34 = "cn.jpush.android.ALERT";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x03a0;
    L_0x038b:
        r33 = "msg";
        r34 = r38.getExtras();
        r35 = "cn.jpush.android.ALERT";
        r34 = r34.getString(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x03a0:
        r33 = r38.getExtras();
        r34 = "htmlbigmsg";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x03c5;
    L_0x03b0:
        r33 = "bigmsg";
        r34 = r38.getExtras();
        r35 = "htmlbigmsg";
        r34 = r34.getString(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x03c5:
        r33 = r38.getExtras();
        r34 = "htmlsummaryText";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x03ea;
    L_0x03d5:
        r33 = "summaryText";
        r34 = r38.getExtras();
        r35 = "htmlsummaryText";
        r34 = r34.getString(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x03ea:
        r33 = r38.getExtras();
        r34 = "htmlticker";
        r33 = r33.getString(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 != 0) goto L_0x040f;
    L_0x03fa:
        r33 = "ticker";
        r34 = r38.getExtras();
        r35 = "htmlticker";
        r34 = r34.getString(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x040f:
        r33 = r38.getExtras();
        r34 = "title";
        r33 = r33.getCharSequence(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 == 0) goto L_0x042c;
    L_0x041f:
        r33 = "title";
        r34 = "";
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x042c:
        r33 = r38.getExtras();
        r34 = "msg";
        r33 = r33.getCharSequence(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 == 0) goto L_0x0449;
    L_0x043c:
        r33 = "msg";
        r34 = "";
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x0449:
        r33 = r38.getExtras();
        r34 = "bigmsg";
        r33 = r33.getCharSequence(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 == 0) goto L_0x0466;
    L_0x0459:
        r33 = "bigmsg";
        r34 = "";
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x0466:
        r33 = r38.getExtras();
        r34 = "summaryText";
        r33 = r33.getCharSequence(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 == 0) goto L_0x0483;
    L_0x0476:
        r33 = "summaryText";
        r34 = "";
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x0483:
        r33 = r38.getExtras();
        r34 = "ticker";
        r33 = r33.getCharSequence(r34);
        r33 = android.text.TextUtils.isEmpty(r33);
        if (r33 == 0) goto L_0x04a0;
    L_0x0493:
        r33 = "ticker";
        r34 = "";
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
    L_0x04a0:
        r33 = "className";
        r34 = getLauncherClassName(r37);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r34 = "udid";
        r33 = getUDID(r37);
        r33 = (java.lang.String) r33;
        r0 = r38;
        r1 = r34;
        r2 = r33;
        r0.putExtra(r1, r2);
        r27 = GetMACAddress(r37);
        if (r27 == 0) goto L_0x04d1;
    L_0x04c6:
        r33 = "mac";
        r0 = r38;
        r1 = r33;
        r2 = r27;
        r0.putExtra(r1, r2);
    L_0x04d1:
        r33 = "androidid";
        r34 = r37.getContentResolver();
        r35 = "android_id";
        r34 = android.provider.Settings.Secure.getString(r34, r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r33 = "isUseTestServer";
        r34 = isUseTestServer;
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r33 = "log";
        r34 = LOG;
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r33 = "appid";
        r0 = r29;
        r1 = r33;
        r4 = r0.getProperty(r1);
        r33 = "appid";
        if (r4 == 0) goto L_0x0b0f;
    L_0x050e:
        r34 = "";
        r0 = r34;
        r34 = r0.equals(r4);
        if (r34 != 0) goto L_0x0b0f;
    L_0x0518:
        r0 = r38;
        r1 = r33;
        r0.putExtra(r1, r4);
        r33 = "packageName";
        r34 = r37.getPackageName();
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r33 = "iconResID";
        r0 = r38;
        r1 = r33;
        r2 = r16;
        r0.putExtra(r1, r2);
        r33 = "bScreenLock";
        r0 = r38;
        r1 = r33;
        r0.putExtra(r1, r6);
        r33 = "isSound";
        r0 = r38;
        r1 = r33;
        r2 = r23;
        r0.putExtra(r1, r2);
        r33 = "isVib";
        r0 = r38;
        r1 = r33;
        r2 = r24;
        r0.putExtra(r1, r2);
        r33 = "isRunning";
        r0 = r38;
        r1 = r33;
        r2 = r22;
        r0.putExtra(r1, r2);
        r33 = "isOperation";
        r0 = r38;
        r1 = r33;
        r2 = r21;
        r0.putExtra(r1, r2);
        r33 = "isGCMOperation";
        r0 = r38;
        r1 = r33;
        r2 = r20;
        r0.putExtra(r1, r2);
        r33 = "language";
        r34 = r29.getLanguage();
        r35 = java.util.Locale.US;
        r34 = r34.toLowerCase(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r33 = "country";
        r34 = r29.getCountry();
        r35 = java.util.Locale.US;
        r34 = r34.toLowerCase(r35);
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData title : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "title";
        r34 = r34.getCharSequence(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData msg : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "msg";
        r34 = r34.getCharSequence(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData bigmsg : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "bigmsg";
        r34 = r34.getCharSequence(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData summaryText : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "summaryText";
        r34 = r34.getCharSequence(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData ticker : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "ticker";
        r34 = r34.getCharSequence(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData noticeID : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "noticeID";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData type : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "type";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData icon : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "icon";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData sound : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "sound";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData vib : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "vib";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData active : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "active";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData bulk_id : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "bulk_id";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData appid : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "appid";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData packageName : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "packageName";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData className : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "className";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData udid : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "udid";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData mac : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "mac";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData androidid : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "androidid";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData broadcastAction : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "broadcastAction";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData language : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "language";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData country : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "country";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData sendtype : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "sendtype";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData badge : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "badge";
        r34 = r34.getInt(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData bigpicture : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "bigpicture";
        r34 = r34.getString(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData buckettype : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "buckettype";
        r34 = r34.getInt(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData bucketsize : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "bucketsize";
        r34 = r34.getInt(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData iconResID : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "iconResID";
        r34 = r34.getInt(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData bScreenLock : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "bScreenLock";
        r34 = r34.getBoolean(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData isSound : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "isSound";
        r34 = r34.getInt(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData isVib : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "isVib";
        r34 = r34.getInt(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData isRunning : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "isRunning";
        r34 = r34.getInt(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData isOperation : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "isOperation";
        r34 = r34.getBoolean(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData isGCMOperation : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "isGCMOperation";
        r34 = r34.getBoolean(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData isGCMPush : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "isGCMPush";
        r34 = r34.getBoolean(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData is containsKey - bigpictureData : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "bigpictureData";
        r34 = r34.containsKey(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData is containsKey - iconData : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "iconData";
        r34 = r34.containsKey(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        r33 = new java.lang.StringBuilder;
        r34 = "SettingData is smallIconResID : ";
        r33.<init>(r34);
        r34 = r38.getExtras();
        r35 = "smallIconResID";
        r34 = r34.getInt(r35);
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        goto L_0x0072;
    L_0x09b1:
        r33 = r38.getExtras();
        r34 = "icon";
        r33 = r33.getString(r34);
        goto L_0x009a;
    L_0x09bd:
        r19 = 0;
        r33 = "ReceiveData icon is null";
        LogI(r33);	 Catch:{ Exception -> 0x09c6 }
        goto L_0x00ea;
    L_0x09c6:
        r10 = move-exception;
        r19 = 0;
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData icon Exception : ";
        r33.<init>(r34);
        r34 = r10.toString();
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        goto L_0x00ea;
    L_0x09e1:
        r19 = 1;
        goto L_0x00ea;
    L_0x09e5:
        r25 = 1;
        goto L_0x010c;
    L_0x09e9:
        r33 = "isSetLargeIcon";
        r34 = 1;
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        r33 = "lollipop icon setting : true";
        LogI(r33);
        goto L_0x0137;
    L_0x09fd:
        r18 = move-exception;
    L_0x09fe:
        r17 = 0;
        r33 = new java.lang.StringBuilder;
        r34 = "icon color setting exception : ";
        r33.<init>(r34);
        r0 = r33;
        r1 = r18;
        r33 = r0.append(r1);
        r33 = r33.toString();
        LogI(r33);
        goto L_0x017a;
    L_0x0a18:
        r33 = r28.isScreenOn();
        if (r33 != 0) goto L_0x01de;
    L_0x0a1e:
        r6 = 1;
        goto L_0x01df;
    L_0x0a21:
        r33 = "isSound";
        r0 = r29;
        r1 = r33;
        r33 = r0.getProperty(r1);	 Catch:{ Exception -> 0x0a31 }
        r23 = java.lang.Integer.parseInt(r33);	 Catch:{ Exception -> 0x0a31 }
        goto L_0x0203;
    L_0x0a31:
        r10 = move-exception;
        r23 = 1;
        goto L_0x0203;
    L_0x0a36:
        r33 = "isVib";
        r0 = r29;
        r1 = r33;
        r33 = r0.getProperty(r1);	 Catch:{ Exception -> 0x0a46 }
        r24 = java.lang.Integer.parseInt(r33);	 Catch:{ Exception -> 0x0a46 }
        goto L_0x0213;
    L_0x0a46:
        r10 = move-exception;
        r24 = 1;
        goto L_0x0213;
    L_0x0a4b:
        r21 = 1;
        goto L_0x022d;
    L_0x0a4f:
        r10 = move-exception;
        r21 = 1;
        goto L_0x022d;
    L_0x0a54:
        if (r21 != 0) goto L_0x0231;
    L_0x0a56:
        r33 = 1;
        r0 = r22;
        r1 = r33;
        if (r0 == r1) goto L_0x0231;
    L_0x0a5e:
        r21 = 1;
        goto L_0x0231;
    L_0x0a62:
        r20 = 1;
        goto L_0x0247;
    L_0x0a66:
        r10 = move-exception;
        r20 = 1;
        goto L_0x0247;
    L_0x0a6b:
        if (r20 != 0) goto L_0x024b;
    L_0x0a6d:
        r33 = 1;
        r0 = r22;
        r1 = r33;
        if (r0 == r1) goto L_0x024b;
    L_0x0a75:
        r20 = 1;
        goto L_0x024b;
    L_0x0a79:
        r10 = move-exception;
        r33 = r10.toString();
        LogI(r33);
        r33 = "noticeID";
        r34 = "1";
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        goto L_0x027b;
    L_0x0a90:
        r33 = "badge";
        r0 = r38;
        r1 = r33;
        r2 = r32;
        r0.putExtra(r1, r2);	 Catch:{ Exception -> 0x0a9d }
        goto L_0x02a2;
    L_0x0a9d:
        r10 = move-exception;
        r33 = r10.toString();
        LogI(r33);
        r33 = "badge";
        r34 = 1;
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        goto L_0x02a2;
    L_0x0ab4:
        r33 = "ReceiveData bigpictureData is null";
        LogI(r33);	 Catch:{ Exception -> 0x0abb }
        goto L_0x02da;
    L_0x0abb:
        r10 = move-exception;
        r33 = new java.lang.StringBuilder;
        r34 = "ReceiveData bigpicture Exception : ";
        r33.<init>(r34);
        r34 = r10.toString();
        r33 = r33.append(r34);
        r33 = r33.toString();
        LogI(r33);
        goto L_0x02da;
    L_0x0ad4:
        r33 = "ReceiveData bigpictureData is null or low SDK Version";
        LogI(r33);
        goto L_0x02da;
    L_0x0adb:
        r9 = 0;
        goto L_0x02ea;
    L_0x0ade:
        r10 = move-exception;
        r33 = r10.toString();
        LogI(r33);
        r33 = "buckettype";
        r34 = 0;
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        goto L_0x02f1;
    L_0x0af5:
        r8 = 1;
        goto L_0x0305;
    L_0x0af8:
        r10 = move-exception;
        r33 = r10.toString();
        LogI(r33);
        r33 = "bucketsize";
        r34 = 1;
        r0 = r38;
        r1 = r33;
        r2 = r34;
        r0.putExtra(r1, r2);
        goto L_0x030c;
    L_0x0b0f:
        r4 = r37.getPackageName();
        goto L_0x0518;
    L_0x0b15:
        r18 = move-exception;
        r12 = r13;
        goto L_0x09fe;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.push.PushConfig.setReceiveData(android.content.Context, android.content.Intent):android.content.Intent");
    }

    public static void startPushWakeLock(Context context, Intent intent) {
        Intent i = new Intent(context, PushWakeLock.class);
        i.putExtras(intent.getExtras());
        i.setFlags(276824064);
        context.startActivity(i);
    }

    protected static void startDialogActivity(Context context, Intent intent) {
        Intent i = new Intent(context, ShowMsgActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(276824064);
        context.startActivity(i);
    }

    public static void setPushType(Context context, Intent intent) {
        int isRunning = intent.getIntExtra("isRunning", 0);
        boolean bScreenLock = intent.getBooleanExtra("bScreenLock", false);
        if (TextUtils.isEmpty(intent.getExtras().getString(PeppermintConstant.JSON_KEY_TYPE))) {
            LogI("setPushType : type is null, default bar");
            NotificationRegister.setNotification(context, intent);
        } else {
            StringTokenizer typeToken = new StringTokenizer(intent.getExtras().getString(PeppermintConstant.JSON_KEY_TYPE), i.b);
            while (typeToken.hasMoreElements()) {
                String token = typeToken.nextToken();
                if ("bar".equals(token)) {
                    LogI("setPushType : bar");
                    NotificationRegister.setNotification(context, intent);
                } else if ("popup".equals(token)) {
                    switch (isRunning) {
                        case g.a /*0*/:
                            LogI("setPushType : show nothing");
                            if (!bScreenLock) {
                                NotificationRegister.setToast(context, intent);
                                break;
                            } else {
                                startDialogActivity(context, intent);
                                break;
                            }
                        case o.a /*1*/:
                            LogI("setPushType : gaming");
                            if (!bScreenLock) {
                                NotificationRegister.setToast(context, intent);
                                break;
                            } else {
                                startDialogActivity(context, intent);
                                break;
                            }
                        case o.b /*2*/:
                            LogI("setPushType : showing popup");
                            startDialogActivity(context, intent);
                            break;
                        default:
                            LogI("setPushType : default");
                            break;
                    }
                } else if ("toast".equals(token)) {
                    LogI("setPushType : toast");
                    NotificationRegister.setToast(context, intent);
                } else {
                    LogI("setPushType : token is nothing, default bar");
                    NotificationRegister.setNotification(context, intent);
                }
            }
        }
        setBadge(context, intent.getIntExtra("badge", 1));
    }

    protected static void setBadge(Context context, int badgeCount) {
        LogI("setBadge");
        Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        intent.putExtra("badge_count", badgeCount);
        intent.putExtra("badge_count_package_name", context.getPackageName());
        intent.putExtra("badge_count_class_name", getLauncherClassName(context));
        context.sendBroadcast(intent);
    }

    protected static Object getUDID(Context context) {
        Object UDID = JSONObject.NULL;
        try {
            UDID = PropertyUtil.getInstance(context).getDeviceID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UDID;
    }

    protected static String GetMACAddress(Context context) {
        String macAddress = null;
        try {
            macAddress = PropertyUtil.getInstance(context).getMacAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return macAddress;
    }

    protected static String getDID(Context context) {
        LogI("getDID");
        String did = PropertyUtil.getInstance(context).getDID();
        LogI("DID : " + did);
        return did;
    }

    protected static String getVID(Context _context, RegisterState sendType) {
        LogI("getVID");
        switch ($SWITCH_TABLE$com$com2us$module$push$Push$RegisterState()[sendType.ordinal()]) {
            case o.b /*2*/:
            case o.c /*3*/:
                return PropertyUtil.getInstance(_context).getProperty(VID);
            case o.d /*4*/:
            case f.bc /*5*/:
                return VID;
            default:
                return null;
        }
    }

    protected static void setVID(String vid) {
        LogI("setVID");
        VID = vid;
    }

    protected static Bitmap getBitmapFromByteArray(byte[] bitmapData) {
        LogI("getBitmapFromByteArray");
        if (bitmapData == null) {
            return null;
        }
        try {
            return BitmapFactory.decodeByteArray(bitmapData, 0, bitmapData.length);
        } catch (Exception e) {
            LogI("getBitmapFromByteArray Exception : " + e.toString());
            return null;
        }
    }

    protected static byte[] getByteFromUrl(String url) {
        LogI("getByteFromUrl : " + url);
        if (TextUtils.isEmpty(url)) {
            LogI("url is Empty");
            return null;
        }
        InputStream is = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        HttpURLConnection connection = null;
        byte[] buffer = new byte[8096];
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            is = connection.getInputStream();
            while (true) {
                int readLen = is.read(buffer);
                if (readLen <= 0) {
                    break;
                }
                baos.write(buffer, 0, readLen);
            }
            byte[] imageByte = baos.toByteArray();
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e2) {
                }
            }
            if (connection == null) {
                return imageByte;
            }
            try {
                connection.disconnect();
                return imageByte;
            } catch (Exception e3) {
                return imageByte;
            }
        } catch (Exception e4) {
            LogI(e4.toString());
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e5) {
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e6) {
                }
            }
            if (connection == null) {
                return null;
            }
            try {
                connection.disconnect();
                return null;
            } catch (Exception e7) {
                return null;
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e8) {
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e9) {
                }
            }
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception e10) {
                }
            }
        }
    }

    protected static String strPostDataBuilder(Context context, Bundle data) {
        JSONObject jsonObjectPostData = new JSONObject();
        String noticeID = "1100001";
        try {
            noticeID = String.valueOf(Integer.parseInt(data.getString("noticeID")) - 1100000);
        } catch (Exception e) {
            noticeID = "1100001";
        }
        String mcc = PropertyUtil.getInstance(context).getMobileCountryCode();
        String mnc = PropertyUtil.getInstance(context).getMobileNetworkCode();
        try {
            jsonObjectPostData.put("bulk_id", data.getString("bulk_id"));
            jsonObjectPostData.put(PeppermintConstant.JSON_KEY_APP_ID, data.getString(PeppermintConstant.JSON_KEY_APP_ID));
            jsonObjectPostData.put("udid", data.getString("udid"));
            if (!ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN.equals(data.getString("mac"))) {
                jsonObjectPostData.put("mac", data.getString("mac"));
            }
            jsonObjectPostData.put("androidid", data.getString("androidid"));
            jsonObjectPostData.put("noticeid", noticeID);
            jsonObjectPostData.put(PeppermintConstant.JSON_KEY_LANGUAGE, data.getString(PeppermintConstant.JSON_KEY_LANGUAGE));
            jsonObjectPostData.put(PeppermintConstant.JSON_KEY_COUNTRY, data.getString(PeppermintConstant.JSON_KEY_COUNTRY));
            jsonObjectPostData.put("sendtype", data.getString("sendtype"));
            jsonObjectPostData.put("platform", "gcm");
            jsonObjectPostData.put(PeppermintConstant.JSON_KEY_DID, getDID(context));
            jsonObjectPostData.put("mcc", mcc);
            jsonObjectPostData.put("mnc", mnc);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        LogI("jsonObjectPostData : " + jsonObjectPostData.toString());
        return jsonObjectPostData.toString();
    }

    protected static String sendToServer(String strPostData) {
        LogI("Send To Log");
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
            HttpConnectionParams.setSoTimeout(httpParameters, 10000);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(new StringBuilder(String.valueOf(isUseTestServer ? testServerURL : realServerURL)).append("confirm.php").toString());
            StringEntity entity = new StringEntity(strPostData, "UTF-8");
            httpPost.setHeader("Content-type", "text/html");
            httpPost.setEntity(entity);
            HttpResponse response = client.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            LogI("sendToPost Response Status Code : " + response.getStatusLine().getStatusCode());
            if (responseEntity == null) {
                return "Wrong Return Value";
            }
            String returnValue = EntityUtils.toString(responseEntity);
            LogI("sendToPost RESPONSE : " + returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            return "Send to Server Failed";
        }
    }

    protected static int ResourceID(Context context, String name, String type, String packageName) {
        return context.getResources().getIdentifier(name, type, packageName);
    }

    protected static int isRunningThisActivity(Context context) {
        String[] activePackages;
        LogI("isRunningThisActivity check");
        ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
        String activeClasses = null;
        if (VERSION.SDK_INT >= 21) {
            LogI("isRunningThisActivity - in Android SDK 21 higher");
            Set<String> activePackageSet = new HashSet();
            for (RunningAppProcessInfo processInfo : activityManager.getRunningAppProcesses()) {
                if (processInfo.importance == 100) {
                    activePackageSet.addAll(Arrays.asList(processInfo.pkgList));
                }
            }
            activePackages = (String[]) activePackageSet.toArray(new String[activePackageSet.size()]);
        } else {
            LogI("isRunningThisActivity - in Android 20 and before version");
            if (context.checkCallingOrSelfPermission("android.permission.GET_TASKS") != 0) {
                LogI("isRunningThisActivity - GET_TASKS check return");
                return 0;
            }
            activePackages = new String[]{((RunningTaskInfo) activityManager.getRunningTasks(1).get(0)).topActivity.getPackageName()};
            activeClasses = ((RunningTaskInfo) activityManager.getRunningTasks(1).get(0)).topActivity.getClassName();
        }
        if (activePackages != null) {
            int length = activePackages.length;
            int i = 0;
            while (i < length) {
                if (!activePackages[i].equals(context.getPackageName())) {
                    i++;
                } else if (TextUtils.equals(activeClasses, "com.com2us.module.push.ShowMsgActivity")) {
                    LogI("isRunningThisActivity - showing popup");
                    return 2;
                } else {
                    LogI("isRunningThisActivity - gaming");
                    return 1;
                }
            }
        }
        LogI("isRunningThisActivity - show nothing");
        return 0;
    }

    protected static String getLauncherClassName(Context context) {
        PackageManager manager = context.getPackageManager();
        Intent mainIntent = new Intent("android.intent.action.MAIN", null);
        mainIntent.addCategory("android.intent.category.LAUNCHER");
        List<ResolveInfo> resolveInfos = manager.queryIntentActivities(mainIntent, 0);
        int iend = resolveInfos.size();
        for (int i = 0; i < iend; i++) {
            ResolveInfo ri = (ResolveInfo) resolveInfos.get(i);
            String pkgName = ri.activityInfo.applicationInfo.packageName;
            String className = ri.activityInfo.name;
            if (pkgName.equalsIgnoreCase(context.getPackageName())) {
                LogI("pkgName : " + pkgName);
                LogI("return className ok : " + className);
                return className;
            }
        }
        return null;
    }

    protected static void saveReceivedPush(Context context, Bundle data) {
        LogI("saveReceivedPush() : " + data.getString("noticeID"));
        String noticeID = data.getString("noticeID");
        if (noticeID != null && noticeID.trim().length() >= 1 && noticeID.compareTo(ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN) != 0) {
            if (data.getBoolean("isGCMPush")) {
                try {
                    int inNoticeID = Integer.valueOf(noticeID).intValue() - 1000000;
                    if (inNoticeID <= 0) {
                        inNoticeID = 1;
                    }
                    noticeID = String.valueOf(inNoticeID);
                } catch (Exception e) {
                    LogI("convert original noticeID Exception : " + noticeID);
                    noticeID = a.e;
                }
                noticeID = "gcm" + noticeID;
            } else {
                noticeID = "local" + noticeID;
            }
            PropertyUtil propertyUtil = PropertyUtil.getInstance(context);
            propertyUtil.loadProperty();
            StringBuffer strBuf = new StringBuffer();
            String savedReceivedPush = propertyUtil.getProperty("receivedPush");
            LogI("saveReceivedPush() saved Received Push : " + savedReceivedPush);
            if (savedReceivedPush == null || savedReceivedPush.trim().length() < 1 || savedReceivedPush.compareTo(ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN) == 0) {
                strBuf.append(noticeID);
                propertyUtil.setProperty("receivedPush", strBuf.toString());
            } else {
                strBuf.append("|").append(noticeID);
                propertyUtil.setProperty("receivedPush", new StringBuilder(String.valueOf(savedReceivedPush)).append(strBuf.toString()).toString());
            }
            propertyUtil.storeProperty(null);
            Push.sendCallback(context);
        }
    }

    protected static synchronized void saveSenderIDs(Context context, String... list) {
        Throwable th;
        Exception e;
        synchronized (PushConfig.class) {
            ObjectOutputStream oos = null;
            try {
                Arrays.sort(list);
                ObjectOutputStream oos2 = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput("sender.dat", 0)));
                try {
                    oos2.writeObject(list);
                    if (oos2 != null) {
                        try {
                            oos2.close();
                            oos = oos2;
                        } catch (Exception e2) {
                            oos = oos2;
                        } catch (Throwable th2) {
                            th = th2;
                            oos = oos2;
                            throw th;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    oos = oos2;
                    try {
                        if (LOG) {
                            e.printStackTrace();
                        }
                        if (oos != null) {
                            try {
                                oos.close();
                            } catch (Exception e4) {
                            }
                        }
                        LogI("[PushConfig]saveSenderIDs: " + Arrays.toString(list));
                    } catch (Throwable th3) {
                        th = th3;
                        if (oos != null) {
                            try {
                                oos.close();
                            } catch (Exception e5) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    oos = oos2;
                    if (oos != null) {
                        oos.close();
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                if (LOG) {
                    e.printStackTrace();
                }
                if (oos != null) {
                    oos.close();
                }
                LogI("[PushConfig]saveSenderIDs: " + Arrays.toString(list));
            }
            try {
                LogI("[PushConfig]saveSenderIDs: " + Arrays.toString(list));
            } catch (Throwable th5) {
                th = th5;
            }
        }
    }

    protected static synchronized String[] loadSenderIDs(Context context) {
        Throwable th;
        Exception e;
        synchronized (PushConfig.class) {
            String[] list = null;
            ObjectInputStream ois = null;
            try {
                ObjectInputStream ois2 = new ObjectInputStream(new BufferedInputStream(context.openFileInput("sender.dat")));
                try {
                    list = (String[]) ois2.readObject();
                    Arrays.sort(list);
                    if (ois2 != null) {
                        try {
                            ois2.close();
                            ois = ois2;
                        } catch (Exception e2) {
                            ois = ois2;
                        } catch (Throwable th2) {
                            th = th2;
                            ois = ois2;
                            throw th;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    ois = ois2;
                    try {
                        if (LOG) {
                            e.printStackTrace();
                        }
                        if (ois != null) {
                            try {
                                ois.close();
                            } catch (Exception e4) {
                            }
                        }
                        return list;
                    } catch (Throwable th3) {
                        th = th3;
                        if (ois != null) {
                            try {
                                ois.close();
                            } catch (Exception e5) {
                            }
                        }
                        try {
                            throw th;
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                    ois = ois2;
                    if (ois != null) {
                        ois.close();
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                if (LOG) {
                    e.printStackTrace();
                }
                if (ois != null) {
                    ois.close();
                }
                return list;
            }
        }
    }

    public static void LogI(String msg) {
        logger.d(msg);
    }
}
