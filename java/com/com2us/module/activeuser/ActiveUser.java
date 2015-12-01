package com.com2us.module.activeuser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserData.DATA_INDEX;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedAgreementData;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedConfigurationData;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedDIDData;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedDownloadData;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedReferrerData;
import com.com2us.module.activeuser.ActiveUserUpdateDialog.OnFinishListener;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import com.com2us.module.activeuser.appversioncheck.AppVersionCheckListener;
import com.com2us.module.activeuser.didcheck.DIDCheck;
import com.com2us.module.activeuser.downloadcheck.DownloadCheck;
import com.com2us.module.activeuser.downloadcheck.InstallService;
import com.com2us.module.activeuser.moduleversioncheck.ModuleVersionCheck;
import com.com2us.module.activeuser.sessioncheck.SessionCheck;
import com.com2us.module.activeuser.useragree.TermsManager;
import com.com2us.module.activeuser.useragree.UserAgreeManager;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;
import com.com2us.module.manager.AppStateAdapter;
import com.com2us.module.manager.Modulable;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.view.SurfaceViewWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import jp.co.cyberz.fox.a.a.i;

public class ActiveUser extends AppStateAdapter implements Constants, Modulable {
    @Deprecated
    public static final int UserAgreeBLACK = 1;
    @Deprecated
    public static final int UserAgreeDefaultColorType = 0;
    @Deprecated
    public static final int UserAgreeWHITE = 0;
    private static Activity activity;
    private ActiveUserNotifier activeUserNotifier;
    private ArrayList<ActiveUserModule> auModuleList;
    private DIDCheck didCheck;
    private DownloadCheck downloadCheck;
    private boolean isDestroyed;
    private boolean isOpenedUserAgreeUI;
    private int jniActiveUserNotifierPointer;
    private int jniUserAgreeNotifierPointer;
    private final Logger logger;
    private ModuleVersionCheck moduleVersionCheck;
    private SessionCheck sessionCheck;
    private UserAgreeManager userAgreeManager;
    private UserAgreeNotifier userAgreeNotifier;
    private SurfaceViewWrapper viewEx;

    private native void jniActiveUserNotifier(int i, int i2);

    private native void jniInitialize(UserAgreeManager userAgreeManager);

    private native void jniUninitialize();

    private native void jniUserAgreeNotifier(int i, int i2);

    public ActiveUser(final Activity activity) {
        this.auModuleList = new ArrayList();
        this.isDestroyed = true;
        this.jniUserAgreeNotifierPointer = 0;
        this.jniActiveUserNotifierPointer = 0;
        this.isOpenedUserAgreeUI = false;
        activity = activity;
        this.logger = LoggerGroup.createLogger(InstallService.TAG, activity);
        this.isDestroyed = false;
        ActiveUserData.create(activity);
        ActiveUserProperties.loadProperties(activity);
        ArrayList arrayList = this.auModuleList;
        UserAgreeManager instance = UserAgreeManager.getInstance();
        this.userAgreeManager = instance;
        arrayList.add(instance);
        this.userAgreeManager.setParameter(activity, new UserAgreeNotifier() {
            public void onUserAgreeResult(int callbackMsg) {
                ActiveUser.this.logger.d("onUserAgreeResult = " + callbackMsg);
                if (callbackMsg == UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS) {
                    ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_PRIVACY_PROPERTY, ActiveUserProperties.AGREEMENT_PRIVACY_VALUE_AGREE);
                    ActiveUserProperties.storeProperties(activity);
                }
                ActiveUser.this.executeModules();
                if (ActiveUser.this.userAgreeNotifier != null) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            ActiveUser.this.userAgreeNotifier.onUserAgreeResult(0);
                        }
                    });
                }
                if (ActiveUser.this.jniUserAgreeNotifierPointer != 0) {
                    ActiveUser.this.isOpenedUserAgreeUI = true;
                }
            }
        }, this.logger);
        arrayList = this.auModuleList;
        DownloadCheck downloadCheck = new DownloadCheck(this.logger);
        this.downloadCheck = downloadCheck;
        arrayList.add(downloadCheck);
        this.sessionCheck = new SessionCheck(activity, this.logger);
        this.moduleVersionCheck = new ModuleVersionCheck(activity, this.logger);
        this.didCheck = new DIDCheck(activity, this.logger);
    }

    public ActiveUser(Activity activity, SurfaceViewWrapper viewEx) {
        this(activity);
        this.viewEx = viewEx;
        jniInitialize(this.userAgreeManager);
    }

    public void onCletResumed() {
        if (this.isOpenedUserAgreeUI) {
            this.isOpenedUserAgreeUI = false;
            jniUserAgreeNotifier(this.jniUserAgreeNotifierPointer, 0);
        }
    }

    public static String getDID() {
        return getDID(activity);
    }

    public static String getDID(Context context) {
        String did = ActiveUserProperties.getProperty(context, ActiveUserProperties.DID_PROPERTY);
        return TextUtils.isEmpty(did) ? null : did;
    }

    public void start() {
        if (!this.isDestroyed) {
            String sendReferrer = ActiveUserProperties.getProperty(ActiveUserProperties.SEND_REFERRER_ON_INSTALLED_PROPERTY);
            if (sendReferrer != null && sendReferrer.equals("N")) {
                new Thread() {
                    public void run() {
                        Object obj = ActiveUserNetwork.processNetworkTask(Gateway.REQUEST_REFERRER);
                        if (obj != null) {
                            try {
                                if (((ReceivedReferrerData) obj).errno == 0) {
                                    ActiveUserProperties.setProperty(ActiveUserProperties.SEND_REFERRER_ON_INSTALLED_PROPERTY, "Y");
                                    ActiveUserProperties.storeProperties(ActiveUser.activity);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
            startConfigurationThread();
        }
    }

    private void startConfigurationThread() {
        this.logger.d("startConfigurationThread");
        new Thread(new Runnable() {
            public void run() {
                final ReceivedConfigurationData recvConfigurationData = (ReceivedConfigurationData) ActiveUserNetwork.processNetworkTask(Gateway.REQUEST_CONFIGURATION);
                if (recvConfigurationData != null) {
                    ActiveUser.this.logger.d("ReceivedAgreementVersionData : " + recvConfigurationData.toString());
                    if (recvConfigurationData.errno == 0) {
                        ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_REVIEW_URL_PROPERTY, recvConfigurationData.agreement_review_url);
                        ActiveUserProperties.storeProperties(ActiveUser.activity);
                        if (recvConfigurationData.notice_show == ActiveUser.UserAgreeBLACK) {
                            ActiveUser.activity.runOnUiThread(new Runnable() {
                                public void run() {
                                    ActiveUserUpdateDialog updateDialog = new ActiveUserUpdateDialog(ActiveUser.activity, recvConfigurationData);
                                    final ReceivedConfigurationData receivedConfigurationData = recvConfigurationData;
                                    updateDialog.setOnFinishListener(new OnFinishListener() {
                                        public void onFinish() {
                                            ActiveUser.this.processAgreement(receivedConfigurationData);
                                        }
                                    });
                                    updateDialog.show();
                                }
                            });
                            return;
                        } else {
                            ActiveUser.this.processAgreement(recvConfigurationData);
                            return;
                        }
                    }
                    ActiveUser.this.isLocalAgreeShow();
                    return;
                }
                ActiveUser.this.logger.d("ReceivedAgreementVersionData is null");
                ActiveUser.this.isLocalAgreeShow();
            }
        }).start();
    }

    private void processAgreement(ReceivedConfigurationData recvConfigurationData) {
        if (recvConfigurationData.agreement_show == 0 && recvConfigurationData.agreement_ex_show == 0) {
            if (this.userAgreeNotifier != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        ActiveUser.this.userAgreeNotifier.onUserAgreeResult(0);
                    }
                });
            }
            if (this.jniUserAgreeNotifierPointer != 0) {
                this.viewEx.queueEvent(new Runnable() {
                    public void run() {
                        ActiveUser.this.jniUserAgreeNotifier(ActiveUser.this.jniUserAgreeNotifierPointer, 0);
                    }
                });
            }
            executeModules();
            return;
        }
        ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_LOG_PROPERTY, ActiveUserProperties.AGREEMENT_LOG_VALUE_SENDING);
        ActiveUserProperties.storeProperties(activity);
        this.userAgreeManager.setVersion(recvConfigurationData.agreement_version);
        this.userAgreeManager.setURL(recvConfigurationData.agreement_url);
        this.userAgreeManager.setAgreementEx_URL(recvConfigurationData.agreement_ex_url);
        this.userAgreeManager.showAgreementPage();
    }

    private void isLocalAgreeShow() {
        int storedVersion;
        int localVersion;
        this.logger.d("isLocalAgreeShow");
        String agreement_version = ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY);
        if (TextUtils.isEmpty(agreement_version)) {
            storedVersion = 0;
        } else {
            try {
                storedVersion = Integer.valueOf(agreement_version).intValue();
            } catch (Exception e) {
                e.printStackTrace();
                storedVersion = 0;
            }
        }
        try {
            localVersion = Integer.valueOf(ActiveUserData.get(DATA_INDEX.LOCAL_AGREEMENT_VERSION)).intValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            localVersion = 0;
        }
        this.logger.d("storedVersion : " + storedVersion);
        this.logger.d("localVersion : " + localVersion);
        if (storedVersion < localVersion) {
            this.userAgreeManager.setVersion(localVersion);
            this.userAgreeManager.setURL(i.a);
            this.userAgreeManager.showAgreementPage();
            return;
        }
        if (this.userAgreeNotifier != null) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    ActiveUser.this.userAgreeNotifier.onUserAgreeResult(0);
                }
            });
        }
        if (this.jniUserAgreeNotifierPointer != 0) {
            this.viewEx.queueEvent(new Runnable() {
                public void run() {
                    ActiveUser.this.jniUserAgreeNotifier(ActiveUser.this.jniUserAgreeNotifierPointer, 0);
                }
            });
        }
        executeModules();
    }

    private AlertDialog createRetryAgreementVersionDialog(Context context) {
        TermsManager tm = TermsManager.getInstance(context);
        Builder builder = new Builder(context);
        builder.setCancelable(false);
        builder.setTitle(tm.getErrorTitleText());
        builder.setMessage(tm.getErrorMsgText());
        builder.setPositiveButton(tm.getRetryText(), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ActiveUser.this.logger.d("startAgreementVersionThread : retry");
                ActiveUser.this.startConfigurationThread();
            }
        });
        builder.setNegativeButton(tm.getQuitText(), new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ActiveUser.this.logger.d("startAgreementVersionThread : quit");
                ActiveUser.activity.finish();
            }
        });
        return builder.create();
    }

    private void setJNIUserAgreeNotifier(int callbackPointer) {
        this.jniUserAgreeNotifierPointer = callbackPointer;
    }

    public void setUserAgreeNotifier(UserAgreeNotifier notifier) {
        this.userAgreeNotifier = notifier;
    }

    private void setJNIActiveUserNotifier(int callbackPointer) {
        this.jniActiveUserNotifierPointer = callbackPointer;
    }

    public void setActiveUserNotifier(ActiveUserNotifier notifier) {
        this.activeUserNotifier = notifier;
    }

    @Deprecated
    public void setAppVersionCheckListener(AppVersionCheckListener listener) {
    }

    @Deprecated
    public void showAppVersionCheckDialog() {
    }

    @Deprecated
    public void setEnableUserAgreeUI() {
        setEnableUserAgreeUI(0);
    }

    @Deprecated
    public void setEnableUserAgreeUI(int colorType) {
        this.userAgreeManager.setEnable(colorType);
    }

    public void resetUserAgree() {
        this.userAgreeManager.resetUserAgree();
    }

    @Deprecated
    public void setUseTestServer() {
        this.logger.d("[setUseTestServer] is deprecated.");
    }

    public void setServerId(String server_id) {
        this.logger.d("[setServerId] : " + server_id);
        ActiveUserData.setServerId(server_id);
    }

    private void requestNetwork(final String requestCmd) {
        this.logger.d("[requestNetwork] requestCmd : " + requestCmd);
        new Thread() {
            public void run() {
                Object obj = ActiveUserNetwork.processNetworkTask(requestCmd);
                if (requestCmd.equals(Gateway.REQUEST_DOWNLOAD)) {
                    if (obj != null) {
                        try {
                            ReceivedDownloadData data = (ReceivedDownloadData) obj;
                            if (ActiveUser.this.logger.isLogged()) {
                                ActiveUser.this.logger.i(data.toString());
                            }
                            Iterator it = ActiveUser.this.auModuleList.iterator();
                            while (it.hasNext()) {
                                ((ActiveUserModule) it.next()).responseNetwork(data);
                            }
                            ActiveUserProperties.storeProperties(ActiveUser.activity);
                            ActiveUser.this.runGetDIDCallback(data.errno);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (ActiveUser.this.didCheck.isExecutable()) {
                        ActiveUser.this.logger.d("[requestNetwork] didCheck.isExecutable true, REQUEST_DID");
                        ActiveUser.this.requestNetwork(TextUtils.isEmpty(ActiveUser.getDID()) ? Gateway.REQUEST_GETDID : Gateway.REQUEST_UPDATEDID);
                    } else {
                        ActiveUser.this.runGetDIDCallback(0);
                    }
                } else if (requestCmd.equals(Gateway.REQUEST_REFERRER)) {
                    try {
                        if (((ReceivedReferrerData) obj).errno == 0) {
                            ActiveUserProperties.setProperty(ActiveUserProperties.SEND_REFERRER_ON_INSTALLED_PROPERTY, "Y");
                            ActiveUserProperties.storeProperties(ActiveUser.activity);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else if (!requestCmd.equals(Gateway.REQUEST_GETDID) && !requestCmd.equals(Gateway.REQUEST_UPDATEDID)) {
                    ActiveUser.this.logger.d("[requestNetwork] wrong request Cmd !!");
                } else if (obj != null) {
                    try {
                        ReceivedDIDData data2 = (ReceivedDIDData) obj;
                        ActiveUser.this.didCheck.responseNetwork(data2);
                        ActiveUser.this.runGetDIDCallback(data2.errno);
                    } catch (Exception e22) {
                        e22.printStackTrace();
                    }
                } else if (TextUtils.isEmpty(ActiveUser.getDID())) {
                    ActiveUser.this.runGetDIDCallback(-99);
                } else {
                    ActiveUser.this.runGetDIDCallback(0);
                }
            }
        }.start();
    }

    private void executeModules() {
        this.logger.d("[executeModules]");
        this.logger.d("[executeModules] value : " + ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_PRIVACY_PROPERTY));
        ActiveUserNetwork.setEnabledDownload(this.downloadCheck.isExecutable());
        this.sessionCheck.isExecutable();
        this.moduleVersionCheck.isExecutable();
        requestNetwork(Gateway.REQUEST_DOWNLOAD);
    }

    private boolean checkNetworkState() {
        if (((ConnectivityManager) activity.getSystemService("connectivity")).getActiveNetworkInfo() == null) {
            this.logger.d("Network Inactive");
            return false;
        }
        this.logger.d("Network Active");
        return true;
    }

    private void runGetDIDCallback(final int state) {
        this.logger.d("runGetDIDCallback : " + state);
        sendAgreementLog(state);
        if (this.activeUserNotifier != null) {
            if (state == 0) {
                this.activeUserNotifier.onActiveUserResult(0);
            } else {
                this.activeUserNotifier.onActiveUserResult(UserAgreeBLACK);
            }
        }
        if (this.jniActiveUserNotifierPointer != 0) {
            this.viewEx.queueEvent(new Runnable() {
                public void run() {
                    if (state == 0) {
                        ActiveUser.this.jniActiveUserNotifier(ActiveUser.this.jniActiveUserNotifierPointer, 0);
                    } else {
                        ActiveUser.this.jniActiveUserNotifier(ActiveUser.this.jniActiveUserNotifierPointer, ActiveUser.UserAgreeBLACK);
                    }
                }
            });
        }
    }

    private void sendAgreementLog(int state) {
        this.logger.d("sendAgreementLog : " + state);
        String agreementLogProperty = ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_LOG_PROPERTY);
        this.logger.d("sendAgreementLog - agreementLogProperty : " + agreementLogProperty);
        if (state == 0 && !TextUtils.equals(agreementLogProperty, ActiveUserProperties.AGREEMENT_LOG_VALUE_COMPLETED)) {
            new Thread(new Runnable() {
                public void run() {
                    ReceivedAgreementData recvAgreementData = new ReceivedAgreementData();
                    recvAgreementData = (ReceivedAgreementData) ActiveUserNetwork.processNetworkTask(Gateway.REQUEST_AGREEMENT);
                    if (recvAgreementData != null) {
                        ActiveUser.this.logger.d("ReceivedAgreementData : " + recvAgreementData.toString());
                        if (recvAgreementData.errno == 0) {
                            ActiveUserProperties.removeProperty(ActiveUserProperties.AGREEMENT_VERSION_DATA_PROPERTY);
                            ActiveUserProperties.removeProperty(ActiveUserProperties.AGREEMENT_EX_SCHEME_DATA_PROPERTY);
                            ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_LOG_PROPERTY, ActiveUserProperties.AGREEMENT_LOG_VALUE_COMPLETED);
                            ActiveUserProperties.storeProperties(ActiveUser.activity);
                            return;
                        }
                        ActiveUser.this.logger.d("ReceivedAgreementData error : " + recvAgreementData.error);
                    }
                }
            }).start();
        }
    }

    public void onActivityStarted() {
        super.onActivityStarted();
        this.logger.d("onActivityStarted");
        this.sessionCheck.onActivityStarted();
    }

    public void onActivityStopped() {
        super.onActivityStopped();
        this.logger.d("onActivityStopped");
        this.sessionCheck.onActivityStoped();
    }

    public void destroy() {
        if (!this.isDestroyed) {
            if (this.viewEx != null) {
                jniUninitialize();
            }
            activity = null;
            this.viewEx = null;
            Iterator it = this.auModuleList.iterator();
            while (it.hasNext()) {
                ((ActiveUserModule) it.next()).destroy();
            }
            this.isDestroyed = true;
        }
    }

    public String getName() {
        return InstallService.TAG;
    }

    public String getVersion() {
        return Version;
    }

    public void setLogged(boolean b) {
        this.logger.setLogged(b);
        if (b) {
            this.logger.i(InstallService.TAG, "Enable Logging");
        }
    }

    public String[] getPermission() {
        return PERMISSION;
    }

    public void setAppIdForIdentity(String appid) {
        ActiveUserData.setAppid(activity, appid);
    }

    public void showUserAgreeTerms() {
        this.userAgreeManager.showUserAgreeTerms(activity);
    }

    public void showUserAgreeTerms(String url) {
        this.userAgreeManager.showUserAgreeTerms(activity, url);
    }
}
