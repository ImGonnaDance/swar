package com.com2us.smon.common;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import com.com2us.chartboost.ChartboostActivity;
import com.com2us.mat.MobileAppTracking;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.push.Push;
import com.com2us.module.push.PushCallback;
import com.com2us.peppermint.HubBridge;
import com.com2us.peppermint.HubCallbackListener;
import com.com2us.smon.google.service.util.GooglePlayServiceActivity.ConnectListener;
import com.facebook.AppEventsLogger;
import com.wellbia.xigncode.XigncodeClient;
import com.wellbia.xigncode.XigncodeClientSystem.Callback;
import jp.co.cyberz.fox.a.a.i;

public class MainActivity extends ChartboostActivity implements PushCallback, ConnectListener, Callback {
    BroadcastReceiver bR = null;
    protected String facebookAppID = null;
    private CCommon mCCommon = null;
    private final String m_strXignCodeKey = "Ch_cpscitNjV";
    Push push = null;

    static {
        try {
            System.loadLibrary("openal");
        } catch (UnsatisfiedLinkError e) {
        }
        System.loadLibrary("c2scommon");
        System.loadLibrary("c2ssmon");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.push = Push.getInstance(this);
        this.push.start();
        this.mCCommon = new CCommon(this, this.mGLSurfaceView, this.push);
        this.mCCommon.InitAdxTracker(this);
        MobileAppTracking.initMat(this, "169452", "24305c3a354951afe96d1800ad9299bf");
        HubBridge.hubInitializeJNI(this);
        HubBridge.HubCallbackSetListener(new HubCallbackListener() {
            public void onCallback(Runnable r) {
                MainActivity.this.mGLSurfaceView.queueEvent(r);
            }
        });
        InitGooglePlayServiceActivity(this, this);
        int s = XigncodeClient.getInstance().initialize(this, "Ch_cpscitNjV", i.a, this);
        if (s != 0) {
            OnHackDetected(s, "InitFail");
        }
    }

    protected void onDestroy() {
        this.push.destroy();
        super.onDestroy();
        XigncodeClient.getInstance().cleanup();
    }

    protected void onPause() {
        if (CCommon.getSoftKeyboardShow()) {
            CCommon.setInputtextString(null);
            CCommon.hideSoftKeyboard();
        }
        this.mCCommon.PauseVideo();
        this.push.onActivityPaused();
        super.onPause();
        XigncodeClient.getInstance().onPause();
    }

    protected void onResume() {
        AppEventsLogger.activateApp(this, this.facebookAppID);
        super.onResume();
        this.mCCommon.ResumeVideo();
        this.push.onActivityResumed();
        MobileAppTracking.resume();
        XigncodeClient.getInstance().onResume();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
    }

    protected void onModuleManagerInitialize(ModuleManager moduleManager) {
        moduleManager.setLogged(false);
        super.onModuleManagerInitialize(moduleManager);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (HubBridge.getPeppermint() != null) {
            HubBridge.getPeppermint().onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onReceivedGCMPush(int arg0, int arg1) {
    }

    public void onReceivedLocalPush(int arg0, int arg1) {
    }

    public void onSuccess() {
        this.mCCommon.onConnected();
    }

    public void onFailed() {
        this.mCCommon.onConnectFailed();
    }

    public void onResult(String strResult) {
        this.mCCommon.onEventCompleted(strResult);
    }

    public void OnHackDetected(int arg0, String arg1) {
        final int errorcode = arg0;
        this.mGLSurfaceView.queueEvent(new Runnable() {
            public void run() {
                CCommon.nativeHackDetected(errorcode);
            }
        });
    }

    public void OnLog(String arg0) {
    }

    public int SendPacket(byte[] arg0) {
        return 0;
    }
}
