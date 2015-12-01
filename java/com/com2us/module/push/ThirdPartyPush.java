package com.com2us.module.push;

import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.util.Locale;

public abstract class ThirdPartyPush {
    protected static final String ADM = "com.amazon.device.messaging.ADM";
    protected static final String JPUSH = "cn.jpush.android.api.JPushInterface";
    protected String TAG = null;
    protected boolean available = false;
    protected Context context = null;
    protected Logger logger = null;
    protected String pushPkgName = null;
    protected String regId = null;
    protected ThirdPartyPushType thirdPartyPushType = ThirdPartyPushType.None;

    public enum ThirdPartyPushType {
        None,
        AmazonPush,
        JPush
    }

    protected abstract void onPause();

    protected abstract void onResume();

    protected abstract void resumePush();

    protected abstract void stopPush();

    protected ThirdPartyPush(Context _context, String logTag) {
        this.context = _context;
        this.TAG = logTag;
        this.logger = LoggerGroup.createLogger(this.TAG);
        this.logger.setLogged(true);
    }

    protected int getIconResourceID() {
        int iconResID = PushConfig.ResourceID(this.context, "ic_c2s_notification_small_icon", "drawable", this.context.getPackageName());
        if (VERSION.SDK_INT >= 21) {
            return iconResID == 0 ? this.context.getApplicationInfo().icon : iconResID;
        } else {
            return this.context.getApplicationInfo().icon;
        }
    }

    protected boolean checkIfServiceAvailable(String className) {
        try {
            this.logger.d("checkIfServiceAvailable of <" + className + ">");
            Class.forName(className);
            this.logger.d("checkIfServiceAvailable return true");
            return true;
        } catch (Exception e) {
            this.logger.d("checkIfServiceAvailable return false");
            return false;
        }
    }

    protected boolean checkIfInChina() {
        if (Locale.getDefault().getCountry().toLowerCase().equals("cn") && Locale.getDefault().getLanguage().toLowerCase().equals("zh")) {
            return true;
        }
        return hasChineseMcc(this.context);
    }

    private boolean hasChineseMcc(Context _context) {
        String mcc = PropertyUtil.getInstance(_context).getMobileCountryCode();
        if (!TextUtils.isEmpty(mcc) && mcc.equals("460")) {
            return true;
        }
        return false;
    }

    public void setRegId(String _regId) {
        this.logger.d("ready to sendToServer with registerState " + Push.registerState);
        this.regId = _regId;
        sendRegId(RegisterState.REGISTER);
    }

    public void sendRegId(RegisterState registerState) {
        this.logger.d("sendRegId with regId ( " + this.regId + " ). available is " + this.available);
        if (!this.available) {
            return;
        }
        if (registerState == RegisterState.REGISTER) {
            if (!TextUtils.isEmpty(this.regId)) {
                Push.sendToServer(this.context, Push.registerState, this.regId, this.thirdPartyPushType);
            }
        } else if (registerState == RegisterState.UNREGISTER) {
            Push.sendToServer(this.context, RegisterState.UNREGISTER, this.regId, this.thirdPartyPushType);
        }
    }

    public void unsetRegId() {
        this.regId = null;
        sendRegId(RegisterState.UNREGISTER);
    }
}
