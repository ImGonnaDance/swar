package com.com2us.smon.common;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.com2us.mat.MobileAppTracking;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.module.push.Push;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperActivity;
import com.com2us.wrapper.kernel.CWrapperData;
import com.facebook.widget.FacebookDialog;
import com.wellbia.xigncode.XigncodeClient;
import it.partytrack.sdk.Track;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.AnalyticsManager;
import jp.appAdForce.android.LtvManager;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.f;

public class CCommon extends CWrapper {
    protected static final int BOARD_TYPE_ACHIEVEMENT = 2;
    protected static final int BOARD_TYPE_EVENTQUEST = 3;
    protected static final int BOARD_TYPE_LEADERBOARD = 1;
    private static Matrix ReverseMatrix = null;
    private static final int VIDEOVIEW_RESULT_CANTPLAY = 2;
    private static final int VIDEOVIEW_RESULT_FAILED = 1;
    private static final int VIDEOVIEW_RESULT_SUCCESS = 0;
    private static MainActivity activity = null;
    private static String g_APKPATH = null;
    private static EEditText g_EEditText = null;
    private static GLSurfaceView g_GLSurfaceView = null;
    private static LinearLayout g_Layout = null;
    private static String g_MyPath = null;
    private static Button g_btnDone = null;
    private static Button g_btncancel = null;
    private static Push g_push = null;
    private static boolean g_showSoftKeyboard = false;
    private static String g_strPlace = null;
    private static String g_strText = null;
    static OnCompletionListener mComplete = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mp) {
            CCommon.StopVideoView();
            CCommon.VideoViewPlayCB(CCommon.VIDEOVIEW_RESULT_SUCCESS);
        }
    };
    private static EGLConfig mConfig = null;
    private static EGLDisplay mDisplay = null;
    private static EGL10 mEgl = null;
    private static EGLContext mEglMainContext = null;
    static OnErrorListener mError = new OnErrorListener() {
        public boolean onError(MediaPlayer mp, int what, int extra) {
            CCommon.StopVideoView();
            CCommon.VideoViewPlayCB(CCommon.VIDEOVIEW_RESULT_CANTPLAY);
            return false;
        }
    };
    private static int mGooglePlayServiceCBEventID = VIDEOVIEW_RESULT_SUCCESS;
    private static int mGooglePlayServiceCBEventParam = VIDEOVIEW_RESULT_SUCCESS;
    private static int mGooglePlayServiceCBMemID = VIDEOVIEW_RESULT_SUCCESS;
    private static int mGooglePlayServiceCBParam = VIDEOVIEW_RESULT_SUCCESS;
    private static Integer mMapID = Integer.valueOf(VIDEOVIEW_RESULT_SUCCESS);
    private static MediaPlayer mPlayer = null;
    static OnPreparedListener mPrepared = new OnPreparedListener() {
        public void onPrepared(MediaPlayer mp) {
        }
    };
    private static HashMap<Integer, SharedContext> mSharedContextMap = new HashMap();
    static OnVideoSizeChangedListener mSizeChange = new OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        }
    };
    private static int m_VideoViewCB = VIDEOVIEW_RESULT_SUCCESS;
    private static int m_VideoViewParam = VIDEOVIEW_RESULT_SUCCESS;
    private static String m_XignCodeCookie = i.a;
    public static int m_videoPosition = 0;
    private static final float nTextHeightSpace = 1.5f;
    private static Paint paint = new Paint();
    private static Typeface[] typeface = null;

    class AnonymousClass11 implements Runnable {
        private final /* synthetic */ int val$cb;
        private final /* synthetic */ int val$nKeyboardType;

        AnonymousClass11(int i, int i2) {
            this.val$nKeyboardType = i;
            this.val$cb = i2;
        }

        public synchronized void run() {
            InputMethodManager imm = (InputMethodManager) CCommon.activity.getSystemService("input_method");
            CCommon.setEditTextType(CCommon.g_EEditText, this.val$nKeyboardType);
            CCommon.g_EEditText.setCallBack(this.val$cb);
            CCommon.g_EEditText.setText(null);
            CCommon.g_EEditText.setHint(CCommon.g_strPlace);
            CCommon.g_Layout.setVisibility(CCommon.VIDEOVIEW_RESULT_SUCCESS);
            CCommon.g_showSoftKeyboard = true;
            CCommon.g_EEditText.requestFocus();
            CCommon.g_strText = null;
            imm.showSoftInput(CCommon.g_EEditText, CCommon.VIDEOVIEW_RESULT_CANTPLAY);
        }
    }

    class AnonymousClass13 implements Runnable {
        private final /* synthetic */ int val$cb;
        private final /* synthetic */ String val$message;

        AnonymousClass13(int i, String str) {
            this.val$cb = i;
            this.val$message = str;
        }

        public void run() {
            CCommon.nativeCallback(this.val$cb, this.val$message);
        }
    }

    class AnonymousClass14 implements Runnable {
        private final /* synthetic */ String val$FileName;
        private final /* synthetic */ int val$cb;
        private final /* synthetic */ int val$param;

        AnonymousClass14(int i, int i2, String str) {
            this.val$cb = i;
            this.val$param = i2;
            this.val$FileName = str;
        }

        public void run() {
            if (CCommon.m_VideoViewCB != 0) {
                CCommon.VideoViewPlayCB(CCommon.VIDEOVIEW_RESULT_FAILED);
                return;
            }
            RelativeLayout touchLayout = (RelativeLayout) CCommon.activity.findViewById(R.id.VideoViewTouchLayout);
            SurfaceView surfaceView = (SurfaceView) CCommon.activity.findViewById(R.id.videoSurface);
            if (touchLayout == null || surfaceView == null) {
                CCommon.VideoViewPlayCB(CCommon.VIDEOVIEW_RESULT_FAILED);
                return;
            }
            touchLayout.setVisibility(CCommon.VIDEOVIEW_RESULT_SUCCESS);
            surfaceView.setVisibility(CCommon.VIDEOVIEW_RESULT_SUCCESS);
            CCommon.m_VideoViewCB = this.val$cb;
            CCommon.m_VideoViewParam = this.val$param;
            if (CCommon.mPlayer == null) {
                CCommon.mPlayer = new MediaPlayer();
            } else {
                CCommon.mPlayer.reset();
            }
            try {
                CCommon.mPlayer.setDataSource(CCommon.activity, Uri.parse("android.resource://" + CCommon.activity.getPackageName() + "/raw/" + this.val$FileName));
                CCommon.mPlayer.setDisplay(surfaceView.getHolder());
                CCommon.mPlayer.setOnCompletionListener(CCommon.mComplete);
                CCommon.mPlayer.setOnVideoSizeChangedListener(CCommon.mSizeChange);
                CCommon.mPlayer.setOnErrorListener(CCommon.mError);
                CCommon.mPlayer.setOnPreparedListener(CCommon.mPrepared);
                CCommon.mPlayer.prepare();
                CCommon.mPlayer.start();
            } catch (Exception e) {
                CCommon.StopVideoView();
                CCommon.VideoViewPlayCB(CCommon.VIDEOVIEW_RESULT_FAILED);
            }
        }
    }

    class AnonymousClass7 implements Runnable {
        private final /* synthetic */ String val$mUrl;

        AnonymousClass7(String str) {
            this.val$mUrl = str;
        }

        public void run() {
            Intent i = new Intent(CCommon.activity, Com2usWebView.class);
            i.putExtra("WEBURL", this.val$mUrl);
            CCommon.activity.startActivity(i);
        }
    }

    class AnonymousClass8 implements Runnable {
        private final /* synthetic */ String val$mUrl;
        private final /* synthetic */ int val$nType;

        AnonymousClass8(String str, int i) {
            this.val$mUrl = str;
            this.val$nType = i;
        }

        public void run() {
            Intent i = new Intent(CCommon.activity, Com2usWebView.class);
            i.putExtra("WEBURL", this.val$mUrl);
            i.putExtra("WEBTYPE", this.val$nType);
            CCommon.activity.startActivity(i);
        }
    }

    static class EEditText extends EditText {
        private int cb = CCommon.VIDEOVIEW_RESULT_SUCCESS;

        public EEditText(Context context) {
            super(context);
        }

        public void setCallBack(int cb) {
            this.cb = cb;
        }

        public int getCallBack() {
            return this.cb;
        }

        public boolean onKeyPreIme(int KeyCode, KeyEvent event) {
            if (event.getAction() != CCommon.VIDEOVIEW_RESULT_FAILED || KeyCode != 4) {
                return super.onKeyPreIme(KeyCode, event);
            }
            CCommon.hideSoftKeyboard();
            return true;
        }
    }

    static class SharedContext extends Thread {
        int mCB;
        EGLConfig mConfig;
        EGLContext mContext = this.mEgl.eglCreateContext(this.mDisplay, this.mConfig, this.mMainContext, null);
        EGLDisplay mDisplay;
        EGL10 mEgl;
        EGLContext mMainContext;
        int mParam;
        EGLSurface mSurface;

        public SharedContext(EGL10 egl, EGLDisplay display, EGLConfig config, EGLContext mainContext, int cb, int param) {
            this.mEgl = egl;
            this.mDisplay = display;
            this.mCB = cb;
            this.mParam = param;
            this.mMainContext = mainContext;
            this.mConfig = config;
        }

        public void run() {
            this.mSurface = this.mEgl.eglCreatePbufferSurface(this.mDisplay, this.mConfig, new int[]{12375, CCommon.VIDEOVIEW_RESULT_FAILED, 12374, CCommon.VIDEOVIEW_RESULT_FAILED, 12417, 12380, 12416, 12380, 12344});
            this.mEgl.eglMakeCurrent(this.mDisplay, this.mSurface, this.mSurface, this.mContext);
            CCommon.nativeThreadProc(this.mCB, this.mParam);
        }

        public void setSurface() {
            this.mSurface = this.mEgl.eglCreatePbufferSurface(this.mDisplay, this.mConfig, new int[]{12375, CCommon.VIDEOVIEW_RESULT_FAILED, 12374, CCommon.VIDEOVIEW_RESULT_FAILED, 12344});
            this.mEgl.eglMakeCurrent(this.mDisplay, this.mSurface, this.mSurface, this.mContext);
        }

        public void DestroyContext() {
            this.mEgl.eglDestroySurface(this.mDisplay, this.mSurface);
            this.mEgl.eglDestroyContext(this.mDisplay, this.mContext);
        }
    }

    private static native void nativeCallback(int i, String str);

    private static native void nativeConnectCB(int i, int i2, int i3);

    private static native void nativeEventCallback(int i, String str, int i2);

    private static native String nativeGetGameName();

    private static native int nativeGetHackToolCount();

    private static native String nativeGetHackToolName(int i);

    public static native void nativeHackDetected(int i);

    private static native void nativeSetSignatureAllInfo(Context context, String str);

    private static native void nativeThreadProc(int i, int i2);

    public static native void nativeVideoViewCB(int i, int i2, int i3);

    public void hideSoftKey() {
        if (VERSION.SDK_INT >= 14) {
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 6);
        }
    }

    @SuppressLint({"NewApi"})
    public CCommon(MainActivity activity, GLSurfaceView glSurfaceView, Push push) {
        activity = activity;
        g_GLSurfaceView = glSurfaceView;
        g_push = push;
        paint.setAntiAlias(true);
        paint.setAlpha(255);
        ReverseMatrix = new Matrix();
        ReverseMatrix.postScale(1.0f, -1.0f);
        InitTypeface();
        InitKeyboardUi(activity);
        mMapID = Integer.valueOf(VIDEOVIEW_RESULT_SUCCESS);
        mSharedContextMap.clear();
        SetApkPath();
        setSignatureInfo();
    }

    private static String getApplicationNativeDir() {
        String nativeDir;
        if (VERSION.SDK_INT >= 9) {
            nativeDir = activity.getApplicationInfo().nativeLibraryDir;
        } else {
            nativeDir = new StringBuilder(String.valueOf(activity.getApplicationInfo().dataDir)).append("/lib").toString();
        }
        try {
            nativeDir = new File(nativeDir).getCanonicalPath();
        } catch (Exception e) {
        }
        return nativeDir;
    }

    private static String getCanonicalFilePath(String path) {
        try {
            return new File(path).getCanonicalFile().getCanonicalPath();
        } catch (Exception e) {
            return null;
        }
    }

    private static void setSignatureInfo() {
        nativeSetSignatureAllInfo(activity.getApplicationContext(), activity.getPackageName());
    }

    private static String getRunningHackingToolName() {
        List<RunningAppProcessInfo> runningAppProcessInfoList = ((ActivityManager) activity.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcessInfoList.size() <= 0) {
            return "no-process-name";
        }
        for (RunningAppProcessInfo rapi : runningAppProcessInfoList) {
            int nPool = nativeGetHackToolCount();
            for (int i = VIDEOVIEW_RESULT_SUCCESS; i < nPool; i += VIDEOVIEW_RESULT_FAILED) {
                if (nativeGetHackToolName(i).equals(rapi.processName)) {
                    return rapi.processName;
                }
            }
        }
        return "not-detected";
    }

    private static boolean getRunningHackingTool() {
        List<RunningAppProcessInfo> runningAppProcessInfoList = ((ActivityManager) activity.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcessInfoList.size() <= 0) {
            return true;
        }
        for (RunningAppProcessInfo rapi : runningAppProcessInfoList) {
            int nPool = nativeGetHackToolCount();
            for (int i = VIDEOVIEW_RESULT_SUCCESS; i < nPool; i += VIDEOVIEW_RESULT_FAILED) {
                if (nativeGetHackToolName(i).equals(rapi.processName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean getInstalledHackingTool() {
        return false;
    }

    private static boolean getCallingAppCheck() {
        String cpackage = activity.getCallingPackage();
        if (cpackage == null) {
            return false;
        }
        int nPool = nativeGetHackToolCount();
        for (int i = VIDEOVIEW_RESULT_SUCCESS; i < nPool; i += VIDEOVIEW_RESULT_FAILED) {
            if (nativeGetHackToolName(i).equals(cpackage)) {
                return true;
            }
        }
        return false;
    }

    private static void setGuideInfo(String strTag, String strMessage) {
        Log.i(strTag, strMessage);
    }

    private static boolean getGuideCheck(String strTag, String strMessage, String strTargetTag, String strTargetMessage) {
        boolean bReady = false;
        boolean bGuide = false;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("logcat -d").getInputStream()));
            String str = i.a;
            while (true) {
                str = bufferedReader.readLine();
                if (str == null) {
                    break;
                }
                if (str.substring(VIDEOVIEW_RESULT_SUCCESS, strTag.length()).equals(strTag) && str.indexOf(strMessage) >= 0) {
                    bGuide = false;
                    bReady = true;
                }
                if (bReady && str.substring(VIDEOVIEW_RESULT_SUCCESS, strTargetTag.length()).equals(strTargetTag) && str.indexOf(strTargetMessage) >= 0) {
                    bGuide = true;
                }
            }
        } catch (IOException e) {
        }
        return bGuide;
    }

    public static String getXignCodeCookie(String szBinarySeed) {
        return XigncodeClient.getInstance().getCookie2(szBinarySeed);
    }

    public static void setXignCodeUserInfo(String userInfo) {
        XigncodeClient.getInstance().setUserInfo(userInfo);
    }

    public static int getProcessorInfo() {
        String arc = System.getProperty("os.arch").substring(VIDEOVIEW_RESULT_SUCCESS, BOARD_TYPE_EVENTQUEST).toUpperCase();
        if (arc.equals("ARM")) {
            return VIDEOVIEW_RESULT_FAILED;
        }
        if (arc.equals("MIP")) {
            return VIDEOVIEW_RESULT_CANTPLAY;
        }
        if (arc.equals("X86")) {
            return BOARD_TYPE_EVENTQUEST;
        }
        return VIDEOVIEW_RESULT_SUCCESS;
    }

    private static boolean getGooglePlayServiceSigned() {
        return activity.isSign();
    }

    private static void connectGooglePlayService(int cb, int param) {
        mGooglePlayServiceCBMemID = cb;
        mGooglePlayServiceCBParam = param;
        activity.gpgsSign();
    }

    private static void setGooglePlayServiceEventCallback(int cb, int param) {
        mGooglePlayServiceCBEventID = cb;
        mGooglePlayServiceCBEventParam = param;
    }

    private static void signOutGooglePlayerService() {
        activity.gpgsSignOut();
    }

    private static void submitGooglePlayServiceScore(int nType, String szID, int value) {
        switch (nType) {
            case VIDEOVIEW_RESULT_FAILED /*1*/:
                activity.gpgsReportScore(szID, (long) value);
                return;
            case VIDEOVIEW_RESULT_CANTPLAY /*2*/:
                activity.gpgsUnlockAchievement(szID);
                return;
            case BOARD_TYPE_EVENTQUEST /*3*/:
                activity.gpgsIncrementEvents(szID, value);
                return;
            default:
                return;
        }
    }

    private static void showGooglePlayServiceBoard(int nType, String szID) {
        switch (nType) {
            case VIDEOVIEW_RESULT_FAILED /*1*/:
                activity.gpgsShowLeaderBoard(szID);
                return;
            case VIDEOVIEW_RESULT_CANTPLAY /*2*/:
                activity.gpgsShowAchievement();
                return;
            case BOARD_TYPE_EVENTQUEST /*3*/:
                activity.gpgsShowQuests();
                return;
            default:
                return;
        }
    }

    public void onConnected() {
        procGooglePlayServiceCB(true);
    }

    public void onConnectFailed() {
        procGooglePlayServiceCB(false);
    }

    public void onDisconnected() {
    }

    public void onEventCompleted(final String strResult) {
        if (g_GLSurfaceView != null && mGooglePlayServiceCBEventID != 0) {
            final int cb = mGooglePlayServiceCBEventID;
            final int param = mGooglePlayServiceCBEventParam;
            g_GLSurfaceView.queueEvent(new Runnable() {
                public void run() {
                    CCommon.nativeEventCallback(cb, strResult, param);
                }
            });
        }
    }

    private void procGooglePlayServiceCB(final boolean isConnected) {
        if (g_GLSurfaceView != null && mGooglePlayServiceCBMemID != 0) {
            final int cb = mGooglePlayServiceCBMemID;
            final int param = mGooglePlayServiceCBParam;
            g_GLSurfaceView.queueEvent(new Runnable() {
                public void run() {
                    CCommon.this.SetGooglePlayServiceCallBack(isConnected, cb, param);
                }
            });
            mGooglePlayServiceCBMemID = VIDEOVIEW_RESULT_SUCCESS;
            mGooglePlayServiceCBParam = VIDEOVIEW_RESULT_SUCCESS;
        }
    }

    private void SetGooglePlayServiceCallBack(boolean isConnected, int cb, int param) {
        nativeConnectCB(cb, isConnected ? VIDEOVIEW_RESULT_FAILED : VIDEOVIEW_RESULT_SUCCESS, param);
    }

    public void InitAdxTracker(MainActivity activity) {
    }

    private static void sendAdxEventData(String event, String data, String currency, String custom) {
    }

    public static void sendMATTutorial() {
        MobileAppTracking.sendTutorial();
    }

    public static void sendMATPurchase(String strPID, String strPrice, String strCurrency) {
        MobileAppTracking.sendPurchase(strPID, strPrice, strCurrency);
    }

    public static void setMATUserID(String strUserID) {
        MobileAppTracking.setUserID(strUserID);
    }

    public static void InitAdFoxConversion() {
        new AdManager(activity).sendConversion("com.com2us.smon.normal.freefull.kakaolink");
    }

    public static void sendAdFoxConversionTutorial() {
        Log.i(f.aU, "Send Fox tutorial");
        new LtvManager(new AdManager(activity)).sendLtvConversion(3398);
    }

    public static void sendAdFoxConversionSale(String pid, String price, String currency) {
        Log.i(f.aU, "Send Fox Sale : " + pid + i.b + price + i.b + currency);
        LtvManager ltv = new LtvManager(new AdManager(activity));
        ltv.addParam(LtvManager.URL_PARAM_SKU, pid);
        ltv.addParam(LtvManager.URL_PARAM_PRICE, price);
        ltv.addParam(LtvManager.URL_PARAM_CURRENCY, currency);
        ltv.sendLtvConversion(3399);
        try {
            AnalyticsManager.sendEvent(activity, "buyinapp", null, null, null, pid, null, Double.parseDouble(price), VIDEOVIEW_RESULT_FAILED, currency);
        } catch (Exception e) {
        }
    }

    public static void showCom2usWebViewFromUrl(String mUrl) {
        activity.runOnUiThread(new AnonymousClass7(mUrl));
    }

    public static void showCom2usWebViewTypeFromUrl(String mUrl, int nType) {
        activity.runOnUiThread(new AnonymousClass8(mUrl, nType));
    }

    private static void sendPartyTrackTutorial() {
        Log.i("PartyTrack", "Send PartyTrack tutorial");
        Track.event(30828);
    }

    private static void sendPartyTrackPayment(String szPrice, String szCurrency, String szPname) {
        Log.i("PartyTrack", "Send PartyTrack Payment");
        Track.payment(szPname, Float.parseFloat(szPrice), szCurrency, VIDEOVIEW_RESULT_FAILED);
    }

    public void InitKeyboardUi(CWrapperActivity activity) {
        LayoutParams LP = new LayoutParams(-1, (int) TypedValue.applyDimension(VIDEOVIEW_RESULT_FAILED, 40.0f, activity.getResources().getDisplayMetrics()));
        g_Layout = new LinearLayout(activity);
        g_Layout.setLayoutParams(LP);
        g_Layout.setBackgroundColor(-7829368);
        g_EEditText = new EEditText(activity);
        g_btnDone = new Button(activity);
        g_btncancel = new Button(activity);
        OnClickListener oCL = new OnClickListener() {
            public void onClick(View v) {
                if (v == CCommon.g_btnDone) {
                    if (CCommon.g_EEditText != null) {
                        CCommon.g_strText = CCommon.g_EEditText.getText().toString();
                        CCommon.hideSoftKeyboard();
                    }
                } else if (v == CCommon.g_btncancel && CCommon.g_EEditText != null) {
                    CCommon.g_strText = null;
                    CCommon.hideSoftKeyboard();
                }
            }
        };
        OnEditorActionListener oAL = new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId != 6 && actionId != CCommon.VIDEOVIEW_RESULT_CANTPLAY && actionId != 4 && actionId != 5) {
                    return false;
                }
                CCommon.g_strText = CCommon.g_EEditText.getText().toString();
                CCommon.hideSoftKeyboard();
                return true;
            }
        };
        LP = new LayoutParams(g_Layout.getLayoutParams().width, g_Layout.getLayoutParams().height, 7.0f);
        LayoutParams LPBtn = new LayoutParams(-2, g_Layout.getLayoutParams().height);
        g_btnDone.setLayoutParams(LPBtn);
        g_btnDone.setTextSize(VIDEOVIEW_RESULT_FAILED, 13.0f);
        g_btnDone.setText("Done");
        g_btnDone.setOnClickListener(oCL);
        g_btncancel.setLayoutParams(LPBtn);
        g_btncancel.setTextSize(VIDEOVIEW_RESULT_FAILED, 13.0f);
        g_btncancel.setText(FacebookDialog.COMPLETION_GESTURE_CANCEL);
        g_btncancel.setOnClickListener(oCL);
        g_EEditText.setTextSize(VIDEOVIEW_RESULT_FAILED, 13.0f);
        g_EEditText.setSingleLine();
        g_EEditText.setLayoutParams(LP);
        g_EEditText.setOnEditorActionListener(oAL);
        InputFilter[] inputFilter = new InputFilter[VIDEOVIEW_RESULT_FAILED];
        inputFilter[VIDEOVIEW_RESULT_SUCCESS] = new LengthFilter(ModuleConfig.SPIDER_MODULE);
        g_EEditText.setFilters(inputFilter);
        g_Layout.addView(g_EEditText);
        g_Layout.addView(g_btnDone);
        g_Layout.addView(g_btncancel);
        g_Layout.setVisibility(8);
        activity.addContentView(g_Layout, g_Layout.getLayoutParams());
    }

    public void SetApkPath() {
        String path = null;
        try {
            path = activity.getPackageManager().getApplicationInfo(activity.getPackageName(), VIDEOVIEW_RESULT_SUCCESS).sourceDir;
        } catch (Exception e) {
            e.printStackTrace();
        }
        g_APKPATH = path;
    }

    public static boolean GetIsExternalMounted() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public static long GetExternalStorageMemorySize() {
        if (!GetIsExternalMounted()) {
            return 0;
        }
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) stat.getBlockCount()) * ((long) stat.getBlockSize());
    }

    public static long GetExternalStorageAvailableSize() {
        if (!GetIsExternalMounted()) {
            return 0;
        }
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
    }

    public static void SetGLShare() {
        mEgl = activity.getEgl();
        mEglMainContext = activity.getEglContext();
        mDisplay = activity.getEglDisplay();
        mConfig = activity.getEglConfig();
    }

    public static int CreateGLSharedContext(int cb, int param) {
        mMapID = Integer.valueOf(mMapID.intValue() + VIDEOVIEW_RESULT_FAILED);
        mSharedContextMap.put(mMapID, new SharedContext(mEgl, mDisplay, mConfig, mEglMainContext, cb, param));
        return mMapID.intValue();
    }

    public static void SetGLSharedContext(int nID) {
        Integer key = Integer.valueOf(nID);
        if (mSharedContextMap.containsKey(key)) {
            ((SharedContext) mSharedContextMap.get(key)).start();
        }
    }

    public static void UnsetGLSharedContext(int nIndex) {
        Integer key = Integer.valueOf(nIndex);
        if (mSharedContextMap.containsKey(key)) {
            SharedContext mSharedContext = (SharedContext) mSharedContextMap.get(key);
            try {
                mSharedContext.join();
                mSharedContext.DestroyContext();
                mSharedContextMap.remove(key);
            } catch (Exception e) {
            }
        }
    }

    public static boolean GetGLSharedContextIsEnd(int nIndex) {
        Integer key = Integer.valueOf(nIndex);
        if (mSharedContextMap.containsKey(key) && ((SharedContext) mSharedContextMap.get(key)).isAlive()) {
            return false;
        }
        return true;
    }

    private static void InitTypeface() {
        typeface = new Typeface[BOARD_TYPE_EVENTQUEST];
        typeface[VIDEOVIEW_RESULT_SUCCESS] = Typeface.create(Typeface.DEFAULT, VIDEOVIEW_RESULT_SUCCESS);
        typeface[VIDEOVIEW_RESULT_FAILED] = Typeface.create(Typeface.DEFAULT, VIDEOVIEW_RESULT_FAILED);
        typeface[VIDEOVIEW_RESULT_CANTPLAY] = Typeface.create(Typeface.DEFAULT, VIDEOVIEW_RESULT_CANTPLAY);
    }

    public static void OpenHttpURL(String url) {
        activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
    }

    public static long getSystemUptime() {
        return SystemClock.elapsedRealtime();
    }

    public static void startProgressBar(int width, int height, int x, int y) {
    }

    public static void endProgressBar() {
    }

    public static int getCpuNumCores() {
        try {
            return new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            }).length;
        } catch (Exception e) {
            return VIDEOVIEW_RESULT_FAILED;
        }
    }

    public static Typeface getTypeface(int nFontType) {
        switch (nFontType) {
            case VIDEOVIEW_RESULT_SUCCESS /*0*/:
                return typeface[VIDEOVIEW_RESULT_SUCCESS];
            case VIDEOVIEW_RESULT_FAILED /*1*/:
                return typeface[VIDEOVIEW_RESULT_FAILED];
            case VIDEOVIEW_RESULT_CANTPLAY /*2*/:
                return typeface[VIDEOVIEW_RESULT_CANTPLAY];
            default:
                return typeface[VIDEOVIEW_RESULT_SUCCESS];
        }
    }

    public static int strGetStringWidth(int nFontType, int nFontSize, String str) {
        Rect rct = new Rect();
        paint.setTypeface(getTypeface(nFontType));
        paint.setTextSize((float) nFontSize);
        paint.getTextBounds(str, VIDEOVIEW_RESULT_SUCCESS, str.length(), rct);
        return (int) paint.measureText(str);
    }

    public static int strGetStringHeight(int nFontType, int nFontSize, String str) {
        Rect rct = new Rect();
        paint.setTypeface(getTypeface(nFontType));
        paint.setTextSize((float) nFontSize);
        paint.getTextBounds(str, VIDEOVIEW_RESULT_SUCCESS, str.length(), rct);
        return (int) (((float) rct.height()) + (((float) rct.height()) * 0.2f));
    }

    public static void strSetString(int nTextWidth, int nTextHeight, int nFontType, int nFontSize, String str, byte[] out) {
        Bitmap bitmap = Bitmap.createBitmap(nTextWidth, nTextHeight, Config.ALPHA_8);
        Canvas canvas = new Canvas(bitmap);
        Rect rct = new Rect();
        paint.setTypeface(getTypeface(nFontType));
        paint.setTextSize((float) nFontSize);
        paint.getTextBounds(str, VIDEOVIEW_RESULT_SUCCESS, str.length(), rct);
        canvas.drawColor(VIDEOVIEW_RESULT_SUCCESS, Mode.CLEAR);
        canvas.drawText(str, 0.0f, ((float) (-rct.top)) + (((float) rct.height()) * 0.1f), paint);
        bitmap.copyPixelsToBuffer(ByteBuffer.wrap(out));
        bitmap.recycle();
    }

    public static String GetMyPath() {
        return g_MyPath;
    }

    public static String getApkFilePath() {
        return g_APKPATH;
    }

    public static String getExternalPath() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    public static void getFileDescriptorFromAsset(String fileName, int[] value) {
        long offset = -1;
        long fileSize = -1;
        AssetFileDescriptor assetFD = null;
        try {
            assetFD = activity.getAssets().openFd("common/" + CWrapperData.getInstance().getAssetFolderName() + "/" + fileName + CWrapperData.getInstance().getAssetFileNameAppended());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (assetFD != null) {
            offset = assetFD.getStartOffset();
            fileSize = assetFD.getLength();
            try {
                assetFD.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        value[VIDEOVIEW_RESULT_SUCCESS] = (int) offset;
        value[VIDEOVIEW_RESULT_FAILED] = (int) fileSize;
    }

    public static void sendSMS(String phoneNumber, String message) {
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + phoneNumber));
        intent.putExtra("sms_body", message);
        activity.startActivity(intent);
    }

    public static void sendEmail(String emailAddress, String subject, String message) {
        Intent intent = new Intent("android.intent.action.SENDTO");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", subject);
        intent.putExtra("android.intent.extra.TEXT", message);
        intent.setData(Uri.parse("mailto:" + emailAddress));
        intent.addFlags(268435456);
        activity.startActivity(Intent.createChooser(intent, "Email"));
    }

    public static boolean sendKakaoLink(String message, String urlAndroidURL, String urlIosURL, String urlSchemes) {
        ArrayList<Map<String, String>> metaInfoArray = new ArrayList();
        Map<String, String> metaInfoAndroid = new Hashtable(VIDEOVIEW_RESULT_FAILED);
        metaInfoAndroid.put("os", "android");
        metaInfoAndroid.put("devicetype", "phone");
        metaInfoAndroid.put("installurl", urlAndroidURL);
        metaInfoAndroid.put("executeurl", urlSchemes);
        Map<String, String> metaInfoIOS = new Hashtable(VIDEOVIEW_RESULT_FAILED);
        metaInfoIOS.put("os", "ios");
        metaInfoIOS.put("devicetype", "phone");
        metaInfoIOS.put("installurl", urlIosURL);
        metaInfoIOS.put("executeurl", urlSchemes);
        metaInfoArray.add(metaInfoAndroid);
        metaInfoArray.add(metaInfoIOS);
        KakaoLink kakaoLink = KakaoLink.getLink(activity);
        if (!kakaoLink.isAvailableIntent()) {
            return false;
        }
        try {
            kakaoLink.openKakaoAppLink(activity, "http://m.com2us.com", message, activity.getPackageName(), activity.getPackageManager().getPackageInfo(activity.getPackageName(), VIDEOVIEW_RESULT_SUCCESS).versionName, nativeGetGameName(), "UTF-8", metaInfoArray);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static long getDeviceTotalMemory() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            RandomAccessFile randomAccessFile;
            try {
                randomAccessFile = reader;
                return Long.parseLong(reader.readLine().replaceAll("MemTotal:", i.a).replaceAll("kB", i.a).trim()) * 1024;
            } catch (Exception e) {
                randomAccessFile = reader;
                return 0;
            }
        } catch (Exception e2) {
            return 0;
        }
    }

    public static long getDeviceFreeMemory() {
        RandomAccessFile randomAccessFile;
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/meminfo", "r");
            try {
                reader.readLine();
                long freeMemory = 0 + (Long.parseLong(reader.readLine().replaceAll("MemFree:", i.a).replaceAll("kB", i.a).trim()) * 1024);
                reader.readLine();
                freeMemory += Long.parseLong(reader.readLine().replaceAll("Cached:", i.a).replaceAll("kB", i.a).trim()) * 1024;
                reader.readLine();
                reader.readLine();
                randomAccessFile = reader;
                return freeMemory + (Long.parseLong(reader.readLine().replaceAll("Inactive:", i.a).replaceAll("kB", i.a).trim()) * 1024);
            } catch (Exception e) {
                randomAccessFile = reader;
                return 0;
            }
        } catch (Exception e2) {
            return 0;
        }
    }

    public static String getAppVersionName() {
        try {
            return activity.getPackageManager().getPackageInfo(activity.getPackageName(), VIDEOVIEW_RESULT_SUCCESS).versionName;
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPushRegistrationID() {
        return g_push.getRegistrationId();
    }

    public static String getDeviceName() {
        return Build.MODEL;
    }

    private static void setEditTextType(EditText editText, int nKeyboardType) {
        int inputType = VIDEOVIEW_RESULT_FAILED;
        switch (nKeyboardType) {
            case VIDEOVIEW_RESULT_CANTPLAY /*2*/:
                inputType = VIDEOVIEW_RESULT_CANTPLAY;
                break;
        }
        editText.setImeOptions(6 | 268435456);
        editText.setInputType(inputType);
    }

    public static void setInputtextString(String szMessage) {
        g_strText = szMessage;
    }

    public static int createInputKeyboard(int nKeyboardType, int cb, String strPlaceHolder) {
        if (getSoftKeyboardShow()) {
            return VIDEOVIEW_RESULT_SUCCESS;
        }
        g_strPlace = strPlaceHolder;
        try {
            activity.runOnUiThread(new AnonymousClass11(nKeyboardType, cb));
            return VIDEOVIEW_RESULT_SUCCESS;
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean getSoftKeyboardShow() {
        return g_showSoftKeyboard;
    }

    public static void hideSoftKeyboard() {
        if (getSoftKeyboardShow()) {
            activity.runOnUiThread(new Runnable() {
                public synchronized void run() {
                    if (CCommon.g_EEditText != null) {
                        InputMethodManager imm = (InputMethodManager) CCommon.activity.getSystemService("input_method");
                        CCommon.g_Layout.setVisibility(8);
                        imm.hideSoftInputFromWindow(CCommon.g_GLSurfaceView.getWindowToken(), CCommon.VIDEOVIEW_RESULT_SUCCESS);
                        CCommon.g_GLSurfaceView.requestFocus();
                        CCommon.g_showSoftKeyboard = false;
                        CCommon.nativeCallbackJava(CCommon.g_EEditText.getCallBack(), CCommon.g_strText);
                    }
                }
            });
        }
    }

    private static void nativeCallbackJava(int cb, String message) {
        g_GLSurfaceView.queueEvent(new AnonymousClass13(cb, message));
    }

    private static void showChartboost(int type) {
        activity.chartboostShow(type);
    }

    private static void VideoViewPlayCB(int nResult) {
        nativeVideoViewCB(nResult, m_VideoViewCB, m_VideoViewParam);
        m_VideoViewCB = VIDEOVIEW_RESULT_SUCCESS;
        m_VideoViewParam = VIDEOVIEW_RESULT_SUCCESS;
    }

    private static void StopVideoView() {
        RelativeLayout touchLayout = (RelativeLayout) activity.findViewById(R.id.VideoViewTouchLayout);
        SurfaceView surfaceView = (SurfaceView) activity.findViewById(R.id.videoSurface);
        if (touchLayout != null) {
            touchLayout.setVisibility(8);
        }
        if (surfaceView != null) {
            surfaceView.setVisibility(8);
        }
        if (mPlayer != null) {
            mPlayer.release();
        }
    }

    public static void VideoViewPlay(String FileName, int cb, int param) {
        activity.runOnUiThread(new AnonymousClass14(cb, param, FileName));
    }

    public void PauseVideo() {
        RelativeLayout touchLayout = (RelativeLayout) activity.findViewById(R.id.VideoViewTouchLayout);
        SurfaceView surfaceView = (SurfaceView) activity.findViewById(R.id.videoSurface);
        if (touchLayout != null && surfaceView != null && touchLayout.getVisibility() == 0 && surfaceView.getVisibility() == 0 && mPlayer != null) {
            mPlayer.pause();
            m_videoPosition = mPlayer.getCurrentPosition();
        }
    }

    public void ResumeVideo() {
        RelativeLayout touchLayout = (RelativeLayout) activity.findViewById(R.id.VideoViewTouchLayout);
        SurfaceView surfaceView = (SurfaceView) activity.findViewById(R.id.videoSurface);
        if (touchLayout != null && surfaceView != null && touchLayout.getVisibility() == 0 && surfaceView.getVisibility() == 0 && mPlayer != null) {
            mPlayer.seekTo(m_videoPosition);
            mPlayer.start();
            m_videoPosition = VIDEOVIEW_RESULT_SUCCESS;
        }
    }
}
