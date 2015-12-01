package com.wellbia.xigncode;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.wellbia.xigncode.XigncodeClientSystem.Callback;
import com.wellbia.xigncode.util.WBAlertDialog;

public abstract class XigncodeActivity extends Activity implements Callback {
    protected static String mXigncodeLicense = null;
    protected static String mXigncodeParam = null;

    class PopupViewer implements Runnable {
        protected int mCode = 0;
        protected Context mContext = null;
        protected String mMessage = null;

        PopupViewer(Context context, int i, String str) {
            this.mCode = i;
            this.mContext = context;
            this.mMessage = str;
        }

        public void run() {
            WBAlertDialog.show(this.mContext, "Security Alert", String.format("ErrorCode: %08x\r\n%s", new Object[]{Integer.valueOf(this.mCode), this.mMessage}), "OK", new OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        }
    }

    XigncodeActivity() {
    }

    protected int initializeXigncode() {
        int initialize = XigncodeClient.getInstance().initialize(this, mXigncodeLicense, mXigncodeParam, this);
        return initialize != 0 ? initialize : 0;
    }

    protected void onDestroy() {
        XigncodeClient.getInstance().cleanup();
        super.onDestroy();
    }

    protected void onPause() {
        XigncodeClient.getInstance().onPause();
        super.onPause();
    }

    protected void onResume() {
        XigncodeClient.getInstance().onResume();
        super.onResume();
    }

    public void OnHackDetected(int i, String str) {
        runOnUiThread(new PopupViewer(this, i, str));
    }

    public void OnLog(String str) {
    }

    public int SendPacket(byte[] bArr) {
        return 0;
    }
}
