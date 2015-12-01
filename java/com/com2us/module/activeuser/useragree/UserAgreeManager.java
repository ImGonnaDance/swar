package com.com2us.module.activeuser.useragree;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserData;
import com.com2us.module.activeuser.ActiveUserData.DATA_INDEX;
import com.com2us.module.activeuser.ActiveUserModule;
import com.com2us.module.activeuser.ActiveUserNetwork.Received;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedDownloadData;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.util.Logger;
import java.io.IOException;
import jp.co.cyberz.fox.a.a.i;
import jp.co.cyberz.fox.notify.a;

public class UserAgreeManager implements ActiveUserModule, UserAgreeNotifier {
    private static UserAgreeManager agreeManager = new UserAgreeManager();
    private String agreement_ex_url = null;
    private int agreement_version = -1;
    private String backgroundImageName = null;
    private Bitmap closeBitmap;
    private int colorType = 0;
    private UserAgreeDialog dialog;
    private boolean enable = false;
    private Logger logger;
    private Bitmap logoBitmap;
    private Activity mainActivity = null;
    private String url = null;
    private UserAgreeNotifier userAgreeNotifier = null;

    public static UserAgreeManager getInstance() {
        return agreeManager;
    }

    public void setParameter(Activity activity, UserAgreeNotifier userAgreeNotifier, Logger logger) {
        this.mainActivity = activity;
        this.userAgreeNotifier = userAgreeNotifier;
        this.logger = logger;
        loadBitmaps(ActiveUserData.get(DATA_INDEX.RESOURCE_OFFSET));
    }

    private void loadBitmaps(String resourceOffset) {
        if (TextUtils.isEmpty(resourceOffset)) {
            resourceOffset = i.a;
        }
        if (this.logoBitmap == null) {
            this.logoBitmap = loadBitmap("common/ActiveUserImage/activeuser_logo" + resourceOffset + ".png");
        }
        if (this.closeBitmap == null) {
            this.closeBitmap = loadBitmap("common/ActiveUserImage/activeuser_close_button.png");
        }
    }

    private Bitmap loadBitmap(String filePath) {
        Bitmap bitmap = null;
        try {
            return BitmapFactory.decodeStream(this.mainActivity.getResources().getAssets().open(filePath));
        } catch (IOException e1) {
            e1.printStackTrace();
            if (bitmap != null) {
                bitmap.recycle();
            }
            return null;
        }
    }

    private UserAgreeManager() {
    }

    public Activity getActivity() {
        return this.mainActivity;
    }

    public void setEnable() {
        setEnable(this.colorType);
    }

    public void setEnable(int colorType) {
        this.logger.d("[UserAgreeManager][setEnable]");
        this.enable = true;
        this.colorType = colorType;
    }

    public void setVersion(int agreement_version) {
        this.agreement_version = agreement_version;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public void setAgreementEx_URL(String agreement_ex_url) {
        this.agreement_ex_url = agreement_ex_url;
    }

    public void showAgreementPage() {
        showAgreementPage(this.colorType);
    }

    public void showAgreementPage(final int colorType) {
        this.mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                Intent intent = new Intent(UserAgreeManager.this.mainActivity, AgreementUIActivity.class);
                intent.putExtra("backgroundImageName", UserAgreeManager.this.backgroundImageName);
                intent.putExtra("config", new Configuration().orientation);
                intent.putExtra("colorType", colorType);
                intent.putExtra(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY, UserAgreeManager.this.agreement_version);
                intent.putExtra(a.g, UserAgreeManager.this.url);
                intent.putExtra("agreement_ex_url", UserAgreeManager.this.agreement_ex_url);
                UserAgreeManager.this.mainActivity.startActivity(intent);
            }
        });
    }

    public void onUserAgreeResult(int callbackMsg) {
        if (this.userAgreeNotifier != null) {
            this.userAgreeNotifier.onUserAgreeResult(callbackMsg);
        }
    }

    public boolean isExecutable() {
        if (this.enable) {
            return true;
        }
        this.logger.d(new StringBuilder("[UserAgreeManager][isExecutable] false : disable").toString());
        return false;
    }

    public void responseNetwork(Received data) {
        if (((ReceivedDownloadData) data).errno == 0) {
            ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_PRIVACY_PROPERTY, ActiveUserProperties.AGREEMENT_PRIVACY_VALUE_COMPLETED);
        }
    }

    public void destroy() {
        if (this.logoBitmap != null) {
            this.logoBitmap.recycle();
        }
        this.logoBitmap = null;
        if (this.closeBitmap != null) {
            this.closeBitmap.recycle();
        }
        this.closeBitmap = null;
    }

    public void showUserAgreeTerms(Context context) {
        showUserAgreeTerms(context, ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_REVIEW_URL_PROPERTY));
    }

    public void showUserAgreeTerms(final Context context, final String url) {
        String defaultUrl = "http://m.member.com2us.com/mobilepolicy2.c2s";
        this.mainActivity.runOnUiThread(new Runnable() {
            public void run() {
                if (UserAgreeManager.this.dialog == null || !UserAgreeManager.this.dialog.isShowing()) {
                    UserAgreeManager.this.dialog = new UserAgreeDialog(context);
                    UserAgreeManager.this.dialog.setUrl(TextUtils.isEmpty(url) ? "http://m.member.com2us.com/mobilepolicy2.c2s" : url);
                    UserAgreeManager.this.dialog.setBitmap(UserAgreeManager.this.logoBitmap, UserAgreeManager.this.closeBitmap);
                    UserAgreeManager.this.dialog.show();
                }
            }
        });
    }

    public void resetUserAgree() {
        ActiveUserProperties.removeProperty(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY);
        ActiveUserProperties.removeProperty(ActiveUserProperties.AGREEMENT_PRIVACY_PROPERTY);
        ActiveUserProperties.removeProperty(ActiveUserProperties.AGREEMENT_LOG_PROPERTY);
        ActiveUserProperties.removeProperty(ActiveUserProperties.AGREEMENT_SMS_PROPERTY);
        ActiveUserProperties.storeProperties(this.mainActivity);
    }
}
