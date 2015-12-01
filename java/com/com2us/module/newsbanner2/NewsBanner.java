package com.com2us.module.newsbanner2;

import android.app.Activity;
import com.com2us.module.AppStateAdapter;
import com.com2us.module.Modulable;
import com.com2us.module.newsbanner2.Constants.Network;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.view.SurfaceViewWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import jp.co.dimage.android.g;

public class NewsBanner extends AppStateAdapter implements Modulable, NewsBannerHeader, NetworkListener {
    public static final int BANNER_POSITION_CEILING_CENTER = 1;
    public static final int BANNER_POSITION_GROUND_CENTER = 2;
    public static final int BANNER_POSITION_TOP_LEFT = 4;
    public static final int BANNER_POSITION_TOP_RIGHT = 3;
    protected static final int POST_NEWSBANNER_CLOSE = 2;
    protected static final int POST_NEWSBANNER_CPI_SUCCESS = 1000;
    protected static final int POST_NEWSBANNER_FAIL_BACKOFFICE = -3;
    protected static final int POST_NEWSBANNER_FAIL_BANNER_DATA = -7;
    protected static final int POST_NEWSBANNER_FAIL_CONNECT_ERROR = -2;
    protected static final int POST_NEWSBANNER_FAIL_HTTP_ERROR = -1;
    protected static final int POST_NEWSBANNER_FAIL_IMAGE_DATA = -6;
    protected static final int POST_NEWSBANNER_FAIL_NO_BANNER = -4;
    protected static final int POST_NEWSBANNER_FAIL_NO_UID = -5;
    protected static final int POST_NEWSBANNER_OPEN = 1;
    protected static final int POST_NEWSBANNER_SUCCESS_READY = 0;
    private Activity activity;
    private String appIdForIdentity;
    private NewsBannerNetwork bannerNetwork;
    private int bannerOffset;
    private int bannerPosition;
    private NewsBannerUI bannerUI;
    private boolean isCreatedUI;
    boolean isExistCPIBannerForNetworkGame;
    private boolean isRequestReward;
    private boolean isShownUI;
    private boolean isSuspended;
    private int jniNewsBannerCPICallback;
    private int jniNewsBannerCallback;
    private Logger logger;
    private NewsBannerCallBack newsBannerCallback;
    private SurfaceViewWrapper viewEx;

    private native void newsBannerCInit();

    private native void newsBannerCPICallback(int i, int i2, int i3);

    private native void newsBannerCallback(int i, int i2);

    public NewsBanner(Activity activity) {
        this.jniNewsBannerCallback = 0;
        this.jniNewsBannerCPICallback = 0;
        this.isSuspended = false;
        this.isCreatedUI = false;
        this.isShownUI = false;
        this.isRequestReward = false;
        this.isExistCPIBannerForNetworkGame = false;
        this.activity = activity;
        this.logger = LoggerGroup.createLogger(Constants.TAG);
        NewsBannerData.create(activity);
        NewsBannerProperties.loadProperties(activity);
        this.bannerUI = new NewsBannerUI(this, activity);
        this.bannerNetwork = new NewsBannerNetwork(this, this, activity);
        if (Network.LOGIN) {
            NewsBannerData.setUid("TMP_UID");
        }
    }

    public NewsBanner(Activity activity, SurfaceViewWrapper view) {
        this(activity);
        this.viewEx = view;
        newsBannerCInit();
    }

    void newsBannerSetCPICallvack(int cb) {
        this.jniNewsBannerCPICallback = cb;
    }

    void runNewsBannerCallback(int type) {
        if (type == POST_NEWSBANNER_CPI_SUCCESS) {
            throw new IllegalArgumentException("[NewsBanner][runNewsBannerCallback]");
        }
        runNewsBannerCallback(type, 0);
    }

    void runNewsBannerCallback(final int type, final int reward) {
        this.logger.v("[NesBanner][runNewsBannerCallback]has been called : " + type);
        if (this.jniNewsBannerCallback != 0) {
            if (type == POST_NEWSBANNER_CPI_SUCCESS) {
                this.viewEx.queueEvent(new Runnable() {
                    public void run() {
                        NewsBanner.this.newsBannerCPICallback(type, NewsBanner.this.jniNewsBannerCPICallback, reward);
                    }
                });
                return;
            }
            this.logger.v("[NesBanner][runNewsBannerCallback]has been called : jniNewsBannerCallback");
            this.viewEx.queueEvent(new Runnable() {
                public void run() {
                    NewsBanner.this.newsBannerCallback(type, NewsBanner.this.jniNewsBannerCallback);
                }
            });
        } else if (this.newsBannerCallback != null) {
            switch (type) {
                case POST_NEWSBANNER_FAIL_BANNER_DATA /*-7*/:
                    this.newsBannerCallback.POST_NEWSBANNER_FAIL_BANNER_DATA();
                    return;
                case POST_NEWSBANNER_FAIL_IMAGE_DATA /*-6*/:
                    this.newsBannerCallback.POST_NEWSBANNER_FAIL_IMAGE_DATA();
                    return;
                case POST_NEWSBANNER_FAIL_NO_UID /*-5*/:
                    this.newsBannerCallback.POST_NEWSBANNER_FAIL_NO_UID();
                    return;
                case POST_NEWSBANNER_FAIL_NO_BANNER /*-4*/:
                    this.newsBannerCallback.POST_NEWSBANNER_FAIL_NO_BANNER();
                    return;
                case POST_NEWSBANNER_FAIL_BACKOFFICE /*-3*/:
                    this.newsBannerCallback.POST_NEWSBANNER_FAIL_BACKOFFICE();
                    return;
                case POST_NEWSBANNER_FAIL_CONNECT_ERROR /*-2*/:
                    this.newsBannerCallback.POST_NEWSBANNER_FAIL_CONNECT_ERROR();
                    return;
                case POST_NEWSBANNER_FAIL_HTTP_ERROR /*-1*/:
                    this.newsBannerCallback.POST_NEWSBANNER_FAIL_HTTP_ERROR();
                    return;
                case g.a /*0*/:
                    this.newsBannerCallback.POST_NEWSBANNER_SUCCESS_READY();
                    return;
                case POST_NEWSBANNER_OPEN /*1*/:
                    this.newsBannerCallback.POST_NEWSBANNER_OPEN();
                    return;
                case POST_NEWSBANNER_CLOSE /*2*/:
                    this.newsBannerCallback.POST_NEWSBANNER_CLOSE();
                    return;
                case POST_NEWSBANNER_CPI_SUCCESS /*1000*/:
                    this.newsBannerCallback.POST_NEWSBANNER_SUCCESS_CPI(reward);
                    return;
                default:
                    return;
            }
        }
    }

    public void newsBannerInit(int orientation, int position, int offsetVal, int cb) {
        this.jniNewsBannerCallback = cb;
        newsBannerInit(orientation, position, offsetVal, null);
    }

    public void newsBannerInit(int orientation, int position, int offsetVal, NewsBannerCallBack cb) {
        this.logger.d("[NesBanner][newsBannerInit]orientation=" + orientation + ",position=" + position + ",offsetVal=" + offsetVal);
        this.newsBannerCallback = cb;
        this.bannerPosition = position;
        if (this.bannerPosition == POST_NEWSBANNER_OPEN || this.bannerPosition == POST_NEWSBANNER_CLOSE) {
            this.bannerOffset = offsetVal;
            this.bannerUI.initialize();
            final int i = orientation;
            final int i2 = position;
            final int i3 = offsetVal;
            final NewsBannerCallBack newsBannerCallBack = cb;
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    NewsBanner.this.bannerUI.createUI(i, i2, i3, newsBannerCallBack);
                    NewsBanner.this.requestToServer(0, 0);
                    NewsBanner.this.requestReward();
                }
            });
            this.isSuspended = false;
            this.isCreatedUI = true;
            this.isShownUI = false;
            return;
        }
        throw new IllegalArgumentException("Only BANNER_POSITION_CEILING_CENTER, BANNER_POSITION_GROUND_CENTER Positions are Supported");
    }

    public void newsBannerShow() {
        this.logger.d("[NewsBanner][newsBannerShow] has been called!");
        this.bannerUI.show();
    }

    public void newsBannerForceShow(boolean showTab) {
        this.logger.d("[NewsBanner][newsBannerShow] has been called!");
        this.bannerUI.forceShow(showTab);
    }

    public void newsBannerRotate(int orientation) {
        this.logger.d("[NewsBanner][newsBannerRotate] has been called! : orientation=" + orientation);
        if (this.isCreatedUI) {
            this.bannerUI.destroy();
            this.bannerUI.createUI(orientation, this.bannerPosition, this.bannerOffset, this.newsBannerCallback);
        }
    }

    public void newsBannerHide() {
        this.logger.d("[NewsBanner][newsBannerHide] has been called!");
        this.bannerUI.hide();
    }

    public void newsBannerSuspend() {
        this.logger.d("[NewsBanner][newsBannerSuspend] has been called!");
        this.bannerUI.suspend();
        this.isSuspended = true;
    }

    public void newsBannerResume() {
        this.logger.d("[NewsBanner][newsBannerResume] has been called! ");
        boolean isRequestBannerInfo = false;
        if (this.isSuspended) {
            if (!this.bannerUI.isExistBannerData()) {
                isRequestBannerInfo = true;
                requestToServer(0, 0);
            } else if (requestReward()) {
                this.isRequestReward = true;
                isRequestBannerInfo = true;
            } else if (this.isExistCPIBannerForNetworkGame) {
                isRequestBannerInfo = true;
                requestToServer(0, 0);
            }
            if (isRequestBannerInfo) {
                newsBannerHide();
            }
            this.bannerUI.resume(isRequestBannerInfo);
        }
        this.isSuspended = false;
    }

    private boolean requestReward() {
        ArrayList<String> list = this.bannerNetwork.getClickedBannerPidList();
        int count = 0;
        if (list.size() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                try {
                    requestToServer(BANNER_POSITION_TOP_LEFT, Integer.parseInt((String) it.next()));
                    count += POST_NEWSBANNER_OPEN;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (count > 0) {
            return true;
        }
        return false;
    }

    public void newsBannerDestroy() {
        this.logger.d("[NewsBanner][newsBannerDestroy] has been called!");
        this.bannerUI.destroy();
    }

    public static void newsBannerSetUID(String UID) {
        NewsBannerData.setUid(UID);
    }

    @Deprecated
    public void newsBannerSetHubUID(String hubUID) {
        newsBannerSetUID(hubUID);
    }

    @Deprecated
    private void newsBannerSetHubUID(long hubUID) {
        this.logger.d("[NewsBanner][newsBannerSetHubUID]hubUID=" + hubUID);
        newsBannerSetHubUID(String.valueOf(hubUID));
    }

    public String getName() {
        return Constants.TAG;
    }

    public String getVersion() {
        return Constants.Version;
    }

    public void setLogged(boolean b) {
        this.logger.setLogged(b);
        this.logger.i(Constants.TAG, "Enable Logging");
    }

    public void setAppIdForIdentity(String appid) {
        this.appIdForIdentity = appid;
        NewsBannerData.setAppid(appid);
    }

    public void destroy() {
        this.bannerUI.destroy();
    }

    public String[] getPermission() {
        return Constants.PERMISSION;
    }

    void requestToServer(int type, int pid) {
        if (type == 0) {
            this.isExistCPIBannerForNetworkGame = false;
        }
        this.bannerNetwork.request(type, pid);
    }

    public void responseReward(int result, int reward) {
        if (result == POST_NEWSBANNER_CLOSE) {
            runNewsBannerCallback(POST_NEWSBANNER_CPI_SUCCESS, reward);
            if (this.isRequestReward) {
                this.bannerUI.hide();
                requestToServer(0, 0);
                this.isRequestReward = false;
                return;
            }
            return;
        }
        this.bannerUI.responseReward(result, reward);
    }

    public void responseBannerInfo(BannerData[] bannerDataArr, Setting setting, CPI cpi, Tab tab) {
        if (bannerDataArr != null) {
            int length = bannerDataArr.length;
            for (int i = 0; i < length; i += POST_NEWSBANNER_OPEN) {
                if (bannerDataArr[i].reward == POST_NEWSBANNER_CLOSE) {
                    this.logger.v("[NewsBanner][responseBannerInfo]isExistCPIBanner");
                    this.isExistCPIBannerForNetworkGame = true;
                    break;
                }
            }
        }
        this.bannerUI.responseBannerInfo(bannerDataArr, setting, cpi, tab);
    }
}
