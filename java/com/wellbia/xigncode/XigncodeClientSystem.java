package com.wellbia.xigncode;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Debug;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;
import com.facebook.internal.Utility;
import com.wellbia.xigncode.util.WBTelecomUtil;
import java.util.List;

public class XigncodeClientSystem implements XigncodeCallback {
    public Activity mActivity;

    public interface Callback {
        void OnHackDetected(int i, String str);

        void OnLog(String str);

        int SendPacket(byte[] bArr);
    }

    public class ShowToastRunnable implements Runnable {
        Context mContext;
        String mText;

        public ShowToastRunnable(Context context, String str) {
            this.mContext = context;
            this.mText = str;
        }

        public void run() {
            Toast makeText = Toast.makeText(this.mContext, this.mText, 1);
            makeText.setGravity(48, 0, 0);
            makeText.show();
        }
    }

    private native void ZCWAVE_AddPackageInfo(String str);

    private native void ZCWAVE_AddPackageInfoEx(String str, String str2);

    private native int ZCWAVE_Cleanup();

    private native String ZCWAVE_GetCooke();

    private native String ZCWAVE_GetCookie2(String str);

    private native int ZCWAVE_GetRevision();

    private native int ZCWAVE_Initialize(String str, String str2, String str3, Callback callback, XigncodeCallback xigncodeCallback);

    private native void ZCWAVE_OnActivityPause();

    private native void ZCWAVE_OnActivityResume();

    private native int ZCWAVE_OnReceive(byte[] bArr);

    private native int ZCWAVE_OnServerConnect();

    private native int ZCWAVE_OnServerDisconnect();

    private native void ZCWAVE_SetApplicationContext(Context context);

    private native void ZCWAVE_SetDeviceId(String str);

    private native void ZCWAVE_SetResolutionInfo(int i, int i2);

    private native void ZCWAVE_SetUserInfo(String str);

    public void RequestInstalledPackage() {
        setPackageInfo(this.mActivity);
    }

    public boolean RequestIsDebuggerConnected() {
        return Debug.isDebuggerConnected();
    }

    public void ShowToast(String str) {
        this.mActivity.runOnUiThread(new ShowToastRunnable(this.mActivity, str));
    }

    public XigncodeClientSystem() {
        System.loadLibrary("xigncode");
    }

    public int initialize(Activity activity, String str, String str2, String str3, Callback callback) {
        this.mActivity = activity;
        int ZCWAVE_Initialize = ZCWAVE_Initialize(str, str2, str3, callback, this);
        if (ZCWAVE_Initialize != 0) {
            return ZCWAVE_Initialize;
        }
        ZCWAVE_SetApplicationContext(this.mActivity);
        ZCWAVE_SetDeviceId(WBTelecomUtil.getDeviceId(this.mActivity));
        setResolutionInfo(this.mActivity);
        return 0;
    }

    public void cleanup() {
        ZCWAVE_Cleanup();
    }

    public void onResume() {
        ZCWAVE_OnActivityResume();
    }

    public void onPause() {
        ZCWAVE_OnActivityPause();
    }

    private void setPackageInfo(Context context) {
        List installedPackages = context.getPackageManager().getInstalledPackages(Utility.DEFAULT_STREAM_BUFFER_SIZE);
        int size = installedPackages.size();
        for (int i = 0; i < size; i++) {
            PackageInfo packageInfo = (PackageInfo) installedPackages.get(i);
            ZCWAVE_AddPackageInfoEx(packageInfo.packageName, packageInfo.applicationInfo.sourceDir);
        }
    }

    private void setResolutionInfo(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        ZCWAVE_SetResolutionInfo(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    public String getCookie() {
        return ZCWAVE_GetCooke();
    }

    public String getCookie2(String str) {
        return ZCWAVE_GetCookie2(str);
    }

    public void setUserInfo(String str) {
        ZCWAVE_SetUserInfo(str);
    }
}
