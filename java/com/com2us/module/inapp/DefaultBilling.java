package com.com2us.module.inapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.widget.Toast;
import com.com2us.module.activeuser.Constants.Network;
import com.com2us.module.manager.ModuleData;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.view.SurfaceViewWrapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.Properties;
import jp.co.cyberz.fox.a.a.i;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public abstract class DefaultBilling {
    public static final int AUTHORIZE_LICENSE_FAILED = 101;
    public static final int AUTHORIZE_LICENSE_SUCCESS = 100;
    public static final int BUY_CANCELED = 4;
    public static final int BUY_FAILED = 3;
    public static final int BUY_SUCCESS = 2;
    protected static int CallBackRef = 0;
    public static final int GET_ITEM_LIST = 1;
    protected static String LOG_TAG = "InApp";
    protected static int LicenseCallbackRef = 0;
    public static final int PURCHASE_UPDATED = 8;
    public static final int RESTORE_SUCCESS = 5;
    protected static Activity activity = null;
    protected static String appid = i.a;
    protected static SurfaceViewWrapper glView = null;
    protected String Toast_NetworkInactive = "Network inactive.";
    protected String VERSION = a.d;
    protected boolean autoVerify = false;
    protected InAppCallback inAppCallback;
    protected boolean isUseTestServer = false;
    protected LicenseCallback licenseCallback;
    protected ModuleData moduleData = null;
    protected PropertyUtil propertyUtil = null;
    protected boolean useDialog = false;
    protected boolean useRestoring = false;

    protected class PropertyUtil {
        private String PROPERTIY_FILE = ("/" + this.TAG + ".properties");
        private String TAG = "InApp";
        private File m_profile = null;
        private Properties m_pros = null;

        PropertyUtil(Context context, String tag) {
            Exception e;
            Throwable th;
            if (tag == null) {
                this.TAG = DefaultBilling.LOG_TAG;
            } else {
                this.TAG = tag;
            }
            this.PROPERTIY_FILE = "/" + this.TAG + ".properties";
            this.m_profile = new File(new StringBuilder(String.valueOf(context.getFilesDir().getPath())).append(this.PROPERTIY_FILE).toString());
            DefaultBilling.LogI("m_profile Path : " + context.getFilesDir().getPath() + this.PROPERTIY_FILE);
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
                        DefaultBilling.LogI("Property File Load Failed -> " + e.getMessage());
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
                DefaultBilling.LogI("Property File Load Failed -> " + e.getMessage());
                if (m_fis != null) {
                    m_fis.close();
                }
            }
        }

        public String getProperty(String key) {
            return this.m_pros.getProperty(key);
        }

        public synchronized void setProperty(String key, String value) {
            if (TextUtils.isEmpty(value)) {
                value = i.a;
            }
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

    protected abstract void iapBuyFinish();

    protected abstract void iapBuyItem(String str, int i, String str2, String str3);

    protected abstract int iapInitialize(String[] strArr, String str, boolean z, int i);

    protected abstract void iapRestoreItem(String str);

    protected abstract void iapStoreEnd();

    protected abstract void iapStoreStart();

    protected abstract void iapUninitialize();

    protected abstract void pause();

    protected abstract void resume();

    public DefaultBilling(Activity _activity) {
        activity = _activity;
        this.moduleData = ModuleManager.getDatas(activity);
    }

    protected void setCallback(InAppCallback _inAppCallback) {
        this.inAppCallback = _inAppCallback;
    }

    protected void setLicenseCallbackRef(int _licenseCallbackRef) {
        LicenseCallbackRef = _licenseCallbackRef;
    }

    protected void setGLView(SurfaceViewWrapper _glView) {
        glView = _glView;
    }

    protected int iapRequestBalance(String uid) {
        return 0;
    }

    protected void iapAuthorizeLicense() {
    }

    protected void destroy() {
    }

    protected void activityResult(int requestCode, int resultCode, Intent data) {
    }

    protected void iapUseTestServer() {
        LogI("UseTestServer");
        this.isUseTestServer = true;
        if (this.isUseTestServer) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(DefaultBilling.activity, "InApp Purchase TestServer is Active.", 0).show();
                }
            });
        }
    }

    protected void setUseDialog(boolean b) {
        this.useDialog = b;
    }

    protected void resultPostInApp(int state, String pid, int quantity, String uid, String additionalInfo, Object stateValue) {
        if (CallBackRef == 0 || glView == null) {
            switch (state) {
                case GET_ITEM_LIST /*1*/:
                    this.inAppCallback.GET_ITEM_LIST(pid, quantity, uid, additionalInfo, stateValue);
                    return;
                case BUY_SUCCESS /*2*/:
                    this.inAppCallback.BUY_SUCCESS(pid, quantity, uid, additionalInfo, stateValue);
                    return;
                case BUY_FAILED /*3*/:
                    this.inAppCallback.BUY_FAILED(pid, quantity, uid, additionalInfo, stateValue);
                    return;
                case BUY_CANCELED /*4*/:
                    this.inAppCallback.BUY_CANCELED(pid, quantity, uid, additionalInfo, stateValue);
                    return;
                case RESTORE_SUCCESS /*5*/:
                    this.inAppCallback.RESTORE_SUCCESS(pid, quantity, uid, additionalInfo, stateValue);
                    return;
                case PURCHASE_UPDATED /*8*/:
                    this.inAppCallback.PURCHASE_UPDATED(pid, quantity, uid, additionalInfo, stateValue);
                    return;
                default:
                    return;
            }
        }
        final int i = state;
        final String str = pid;
        final int i2 = quantity;
        final String str2 = uid;
        final String str3 = additionalInfo;
        final Object obj = stateValue;
        glView.queueEvent(new Runnable() {
            public void run() {
                InApp.resultPostInApp(DefaultBilling.CallBackRef, i, str, i2, str2, str3, obj);
            }
        });
    }

    protected void resultPostLicense(final int state, final Object stateValue) {
        if (LicenseCallbackRef == 0 || glView == null) {
            switch (state) {
                case AUTHORIZE_LICENSE_SUCCESS /*100*/:
                    this.licenseCallback.AUTHORIZE_LICENSE_SUCCESS(stateValue);
                    return;
                case AUTHORIZE_LICENSE_FAILED /*101*/:
                    this.licenseCallback.AUTHORIZE_LICENSE_FAILED(stateValue);
                    return;
                default:
                    return;
            }
        }
        glView.queueEvent(new Runnable() {
            public void run() {
                InApp.resultPostLicense(DefaultBilling.LicenseCallbackRef, state, stateValue);
            }
        });
    }

    protected void setVersion(String ver, String tag) {
        this.VERSION = ver;
        LOG_TAG = tag;
        LogI(new StringBuilder(String.valueOf(tag)).append(" Version : v").append(ver).toString());
        this.propertyUtil = new PropertyUtil(activity, null);
    }

    protected String getVersion() {
        return this.VERSION;
    }

    protected boolean checkNetworkState() {
        if (((ConnectivityManager) activity.getSystemService("connectivity")).getActiveNetworkInfo() == null) {
            LogI("Network Inactive");
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(DefaultBilling.activity, DefaultBilling.this.Toast_NetworkInactive, 0).show();
                }
            });
            resultPostInApp(BUY_CANCELED, i.a, 0, i.a, i.a, "Network Inactive");
            return false;
        }
        LogI("Network Active");
        return true;
    }

    protected Object GetUDID() {
        String udid = this.moduleData.getDeviceID();
        return TextUtils.isEmpty(udid) ? JSONObject.NULL : udid;
    }

    protected Object getVID() {
        String vid = this.moduleData.getVID();
        return TextUtils.isEmpty(vid) ? JSONObject.NULL : vid;
    }

    protected static String sendToServer(String strPostData, String postUrl) {
        try {
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, Network.TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpParameters, Network.TIMEOUT);
            HttpClient client = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(postUrl);
            StringEntity entity = new StringEntity(strPostData, "UTF-8");
            httpPost.setHeader("Content-type", "text/html");
            httpPost.setEntity(entity);
            HttpEntity responseEntity = client.execute(httpPost).getEntity();
            if (responseEntity == null) {
                return null;
            }
            String returnValue = EntityUtils.toString(responseEntity);
            LogI("sendToPost RESPONSE : " + returnValue);
            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void showPreviousProgressInfoDialog(Activity activity, Runnable runnable) {
        String message;
        String okay;
        String language = this.moduleData.getLanguage();
        String country = this.moduleData.getCountry();
        if (TextUtils.equals("ko", language)) {
            message = "\uc9c0\uae09\ub418\uc9c0 \uc54a\uc740 \uc0c1\ud488\uc774 \uc788\uc2b5\ub2c8\ub2e4. \n\uc7ac \uc9c0\uae09\uc744 \uc2dc\ub3c4\ud569\ub2c8\ub2e4.";
            okay = "\ud655\uc778";
        } else if (TextUtils.equals("fr", language)) {
            message = "Echec de lattribution de certains objets. \nLe processus va recommencer.";
            okay = "OK";
        } else if (TextUtils.equals("de", language)) {
            message = "Nicht erhaltene Items vorhanden. \nVorgang wird wiederholt.";
            okay = "OK";
        } else if (TextUtils.equals("ja", language)) {
            message = "\u652f\u7d66\u3055\u308c\u3066\u306a\u3044\u30a2\u30a4\u30c6\u30e0\u304c\u3042\u308a\u307e\u3059\u3002 \n\u518d\u652f\u7d66\u3044\u305f\u3057\u307e\u3059\u3002";
            okay = "OK";
        } else if (TextUtils.equals("ru", language)) {
            message = "\u041d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043d\u0430\u0447\u0438\u0441\u043b\u0438\u0442\u044c \u0432\u0441\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u044b. \n\u041f\u043e\u043f\u0440\u043e\u0431\u0443\u0435\u043c \u0435\u0449\u0435 \u0440\u0430\u0437.";
            okay = "OK";
        } else if (TextUtils.equals("tw", country)) {
            message = "\u5546\u54c1\u672a\u6210\u529f\u7d66\u4ed8\uff0c \n\u5c07\u5617\u8a66\u91cd\u65b0\u9818\u53d6\u3002";
            okay = "\u78ba\u8a8d";
        } else if (TextUtils.equals("cn", country)) {
            message = "\u5b58\u5728\u672a\u80fd\u652f\u4ed8\u7684\u5546\u54c1\u3002 \n\u5c06\u5c1d\u8bd5\u91cd\u65b0\u652f\u4ed8\u3002";
            okay = "\u786e\u8ba4";
        } else {
            message = "Failed to grant some items. \nThe process will restart.";
            okay = "OK";
        }
        final Activity activity2 = activity;
        final Runnable runnable2 = runnable;
        activity.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog dialog = new Builder(activity2).setIcon(17301659).setMessage(message).setPositiveButton(okay, new OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).create();
                final Activity activity = activity2;
                final Runnable runnable = runnable2;
                dialog.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        activity.runOnUiThread(runnable);
                    }
                });
                dialog.show();
            }
        });
    }

    public static String makeHash(String text) {
        String hashStr = i.a;
        try {
            MessageDigest verify = MessageDigest.getInstance("SHA-1");
            verify.update(text.getBytes());
            byte[] hashResult = verify.digest();
            for (int k = 0; k < hashResult.length; k += GET_ITEM_LIST) {
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(hashStr));
                Object[] objArr = new Object[GET_ITEM_LIST];
                objArr[0] = Byte.valueOf(hashResult[k]);
                hashStr = stringBuilder.append(String.format("%02x", objArr)).toString();
            }
            return hashStr;
        } catch (Exception e) {
            e.printStackTrace();
            return i.a;
        }
    }

    public static void LogI(String msg) {
        StringBuffer logDataBuffer = new StringBuffer();
        logDataBuffer.append(LOG_TAG).append(" : ").append(msg);
        InApp.logger.v(logDataBuffer.toString());
    }
}
