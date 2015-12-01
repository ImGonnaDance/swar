package com.com2us.module.inapp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import com.com2us.module.manager.AppStateAdapter;
import com.com2us.module.manager.Modulable;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.view.SurfaceViewWrapper;
import java.lang.reflect.Constructor;
import jp.co.cyberz.fox.a.a.i;

public class InApp extends AppStateAdapter implements Modulable {
    public static final int AmazonBilling = 10;
    @Deprecated
    public static final int GoogleInAppBilling = 1;
    public static final int GooglePlayBilling = 15;
    public static final int HamiBilling = 7;
    public static final int KDDIBilling = 11;
    public static final int LebiBilling = 8;
    public static final int MBizBilling = 13;
    public static final int MMBilling = 12;
    public static final String MakingVersion = "2015.04.22";
    public static final int OZstoreBilling = 4;
    public static final int PlasmaBilling = 9;
    public static final int ThirdPartyBilling = 5;
    public static final int TstoreBilling = 2;
    public static String Version = MakingVersion;
    protected static int billingTarget = 0;
    protected static Logger logger = LoggerGroup.createLogger("InApp");
    public static final int ollehMarketBilling = 3;
    public static final int qiipBilling = 6;
    protected static String strBillingTarget = null;
    private DefaultBilling billing;
    private Activity iniActivity;
    private SurfaceViewWrapper iniGlView;
    private InAppCallback iniInAppCallback;
    private boolean iniLog;
    private boolean isInitialized;
    private boolean isTestServer;

    public static native void jniInAppInitialize(InApp inApp);

    public static native void jniUninAppInitialize();

    public static native void resultPostInApp(int i, int i2, String str, int i3, String str2, String str3, Object obj);

    public static native void resultPostLicense(int i, int i2, Object obj);

    public static native void resultPostTarget(int i, int i2, int i3);

    @Deprecated
    public InApp(Activity _activity, InAppCallback inAppCallback, boolean _log) {
        this(_activity, inAppCallback);
    }

    @Deprecated
    public InApp(Activity _activity, InAppCallback inAppCallback, boolean _log, int _billingTarget) {
        this(_activity, inAppCallback);
        billingTarget = _billingTarget;
    }

    @Deprecated
    public InApp(Activity _activity, SurfaceViewWrapper _glView, boolean _log) {
        this(_activity, _glView);
    }

    @Deprecated
    public InApp(Activity _activity, SurfaceViewWrapper _glView, boolean _log, int _billingTarget) {
        this(_activity, _glView);
        billingTarget = _billingTarget;
    }

    public InApp(Activity _activity, InAppCallback inAppCallback) {
        this.billing = null;
        this.iniActivity = null;
        this.iniInAppCallback = null;
        this.iniGlView = null;
        this.iniLog = false;
        this.isInitialized = false;
        this.isTestServer = false;
        this.iniActivity = _activity;
        this.iniInAppCallback = inAppCallback;
    }

    public InApp(Activity _activity, SurfaceViewWrapper _glView) {
        this.billing = null;
        this.iniActivity = null;
        this.iniInAppCallback = null;
        this.iniGlView = null;
        this.iniLog = false;
        this.isInitialized = false;
        this.isTestServer = false;
        this.iniActivity = _activity;
        this.iniGlView = _glView;
        jniInAppInitialize(this);
    }

    private void selectBillingTarget(final Activity _activity, SurfaceViewWrapper _glView, InAppCallback _inAppCallback, int _billingTarget) {
        billingTarget = _billingTarget;
        this.billing = SelectMarket(getClassName(_billingTarget, true), _activity);
        if (this.billing != null) {
            this.billing.setVersion(this.billing.VERSION, strBillingTarget);
            this.isInitialized = false;
        }
        if (this.billing != null) {
            if (this.isTestServer) {
                this.billing.iapUseTestServer();
            }
            if (_glView != null || _inAppCallback == null) {
                this.billing.setGLView(_glView);
            } else {
                this.billing.setCallback(_inAppCallback);
            }
            Version = this.billing.VERSION;
        } else if (logger.isLogged()) {
            _activity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(_activity, "Invalied Select Billing Market.", 0).show();
                }
            });
        }
    }

    protected static String getClassName(int target, boolean setTag) {
        switch (target) {
            case GoogleInAppBilling /*1*/:
                if (setTag) {
                    strBillingTarget = "GoogleInAppBilling";
                }
                return "com.com2us.module.inapp.googleinapp.GoogleInAppBilling";
            case TstoreBilling /*2*/:
                if (setTag) {
                    strBillingTarget = "TstoreBilling";
                }
                return "com.com2us.module.inapp.tstore.TstoreBilling";
            case ollehMarketBilling /*3*/:
                if (setTag) {
                    strBillingTarget = "ollehMarketBilling";
                }
                return "com.com2us.module.inapp.ollehmarket.ollehMarketBilling";
            case OZstoreBilling /*4*/:
                if (setTag) {
                    strBillingTarget = "OZstoreBilling";
                }
                return "com.com2us.module.inapp.ozstore.OZstoreBilling";
            case ThirdPartyBilling /*5*/:
                if (setTag) {
                    strBillingTarget = "ThirdPartyBilling";
                }
                return "com.com2us.module.inapp.thirdpartybilling.ThirdPartyBilling";
            case qiipBilling /*6*/:
                if (setTag) {
                    strBillingTarget = "qiipBilling";
                }
                return "com.com2us.module.inapp.qiip.qiipBilling";
            case HamiBilling /*7*/:
                if (setTag) {
                    strBillingTarget = "HamiBilling";
                }
                return "com.com2us.module.inapp.hami.HamiBilling";
            case LebiBilling /*8*/:
                if (setTag) {
                    strBillingTarget = "LebiBilling";
                }
                return "com.com2us.module.inapp.lebi.LebiBilling";
            case PlasmaBilling /*9*/:
                if (setTag) {
                    strBillingTarget = "PlasmaBilling";
                }
                return "com.com2us.module.inapp.plasma.PlasmaBilling";
            case AmazonBilling /*10*/:
                if (setTag) {
                    strBillingTarget = "AmazonBilling";
                }
                return "com.com2us.module.inapp.amazon.AmazonBilling";
            case KDDIBilling /*11*/:
                if (setTag) {
                    strBillingTarget = "KDDIBilling";
                }
                return "com.com2us.module.inapp.kddi.KDDIBilling";
            case MMBilling /*12*/:
                if (setTag) {
                    strBillingTarget = "MMBilling";
                }
                return "com.com2us.module.inapp.mm.MMBilling";
            case MBizBilling /*13*/:
                if (setTag) {
                    strBillingTarget = "MBizBilling";
                }
                return "com.com2us.module.inapp.mbiz.MBizBilling";
            case GooglePlayBilling /*15*/:
                if (setTag) {
                    strBillingTarget = "GooglePlayBilling";
                }
                return "com.com2us.module.inapp.googleplay.GooglePlayBilling";
            default:
                if (!setTag) {
                    return null;
                }
                strBillingTarget = null;
                return null;
        }
    }

    protected static DefaultBilling SelectMarket(String maketName, Activity _activity) {
        try {
            Class<?> cls = Class.forName(maketName);
            Class[] clsArr = new Class[GoogleInAppBilling];
            clsArr[0] = Activity.class;
            Constructor<?> con = cls.getConstructor(clsArr);
            Object[] params = new Object[GoogleInAppBilling];
            params[0] = _activity;
            return (DefaultBilling) con.newInstance(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Deprecated
    public int iapInitialize(String appid, boolean autoVerify) {
        return iapInitialize(billingTarget, null, appid, autoVerify, 0);
    }

    @Deprecated
    public int iapInitialize(String appid, boolean autoVerify, int cbRef) {
        return iapInitialize(billingTarget, null, appid, autoVerify, cbRef);
    }

    @Deprecated
    public int iapInitialize(String[] pids, String appid, boolean autoVerify) {
        return iapInitialize(billingTarget, pids, appid, autoVerify, 0);
    }

    @Deprecated
    public int iapInitialize(String[] pids, String appid, boolean autoVerify, int cbRef) {
        return iapInitialize(billingTarget, pids, appid, autoVerify, cbRef);
    }

    public int iapInitialize(int billingTarget, String[] pids, String appid, boolean autoVerify) {
        return iapInitialize(billingTarget, pids, appid, autoVerify, 0);
    }

    public synchronized int iapInitialize(int billingTarget, String[] pids, String appid, boolean autoVerify, int cbRef) {
        int i = 0;
        synchronized (this) {
            logger.d("DefaultLibrary MakingVersion : 2015.04.22");
            logger.d("billingTarget : " + billingTarget);
            logger.d("appid : " + appid);
            logger.d("autoVerify : " + autoVerify);
            selectBillingTarget(this.iniActivity, this.iniGlView, this.iniInAppCallback, billingTarget);
            if (this.billing == null) {
                this.isInitialized = false;
            } else {
                this.isInitialized = true;
                i = this.billing.iapInitialize(pids, appid, autoVerify, cbRef);
            }
        }
        return i;
    }

    public synchronized void iapUninitialize() {
        if (this.isInitialized) {
            this.isInitialized = false;
            this.isTestServer = false;
            this.billing.iapUninitialize();
        }
    }

    public synchronized void iapStoreStart() {
        if (this.isInitialized) {
            this.billing.iapStoreStart();
        }
    }

    public synchronized void iapStoreEnd() {
        if (this.isInitialized) {
            this.billing.iapStoreEnd();
        }
    }

    public synchronized void iapRestoreItem(String uid) {
        if (this.isInitialized) {
            this.billing.iapRestoreItem(uid);
        }
    }

    public synchronized void iapBuyItem(String pid, int quantity, String uid, String additionalInfo) {
        if (this.isInitialized) {
            if (uid == null) {
                uid = i.a;
            }
            this.billing.iapBuyItem(pid, quantity, uid, additionalInfo);
        }
    }

    public synchronized void iapBuyFinish() {
        if (this.isInitialized) {
            this.billing.iapBuyFinish();
            switch (billingTarget) {
                case GoogleInAppBilling /*1*/:
                case LebiBilling /*8*/:
                case GooglePlayBilling /*15*/:
                    iapStoreTarget(billingTarget);
                    break;
            }
        }
    }

    public synchronized void iapUseTestServer() {
        if (this.isInitialized) {
            this.billing.iapUseTestServer();
        } else {
            this.isTestServer = true;
        }
    }

    public int iapRequestBalance(String uid) {
        if (this.isInitialized) {
            return this.billing.iapRequestBalance(uid);
        }
        return 0;
    }

    public void iapAuthorizeLicense(LicenseCallback licenseCB) {
        iapAuthorizeLicense(licenseCB, 0);
    }

    public void iapAuthorizeLicense(int licenseCBRef) {
        iapAuthorizeLicense(null, licenseCBRef);
    }

    private void iapAuthorizeLicense(LicenseCallback licenseCB, int licenseCBRef) {
        if (!this.isInitialized) {
            return;
        }
        if (billingTarget != KDDIBilling) {
            this.iniActivity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(InApp.this.iniActivity, "This market does not support licensing authorized.", 0).show();
                }
            });
            return;
        }
        this.billing.licenseCallback = licenseCB;
        this.billing.setLicenseCallbackRef(licenseCBRef);
        this.billing.iapAuthorizeLicense();
    }

    public void iapSelectTarget(SelectTargetCallback targetCB) {
        SelectTarget.iapSelectTarget(this.iniActivity, null, targetCB, 0);
    }

    public void iapSelectTarget(int targetCBRef) {
        SelectTarget.iapSelectTarget(this.iniActivity, this.iniGlView, null, targetCBRef);
    }

    protected void iapStoreTarget(int targetNumber) {
        logger.d("iapStoreTarget : " + SelectTarget.iapStoreTarget(this.iniActivity, targetNumber));
    }

    public void setUseDialog(boolean b) {
        if (this.isInitialized) {
            this.billing.setUseDialog(b);
        }
    }

    public int getBillingTargetNumber() {
        if (this.isInitialized) {
            return billingTarget;
        }
        return 0;
    }

    public String getBillingTargetName() {
        if (this.isInitialized) {
            return strBillingTarget;
        }
        return null;
    }

    public String getVersion() {
        if (this.isInitialized) {
            return this.billing.getVersion();
        }
        return Version;
    }

    public String getName() {
        return "InApp";
    }

    public void setLogged(boolean b) {
        logger.setLogged(b);
        if (this.isInitialized && logger.isLogged()) {
            this.iniActivity.runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(InApp.this.iniActivity, "InApp Purchase Log is Active.", 0).show();
                }
            });
        }
    }

    public void setAppIdForIdentity(String appid) {
        ModuleManager.getDatas(this.iniActivity).setAppID(appid);
    }

    public void destroy() {
        if (this.isInitialized) {
            iapUninitialize();
        }
        if (this.billing != null) {
            this.billing.destroy();
        }
    }

    public String[] getPermission() {
        String[] inappPermission = new String[TstoreBilling];
        inappPermission[0] = "android.permission.INTERNET";
        inappPermission[GoogleInAppBilling] = "android.permission.READ_PHONE_STATE";
        return inappPermission;
    }

    public void onActivityResumed() {
        if (this.isInitialized) {
            this.billing.resume();
        }
    }

    public void onActivityPaused() {
        if (this.isInitialized) {
            this.billing.pause();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.isInitialized) {
            this.billing.activityResult(requestCode, resultCode, data);
        }
    }
}
