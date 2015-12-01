package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AdManager;
import jp.co.dimage.android.f;

public class AdManagerUnity extends AdManager implements f {
    private Activity b;
    private AdManager c = this;

    public AdManagerUnity(Activity activity) {
        super(activity);
        this.b = activity;
    }

    public void sendConversionForMobageUnity(String str) {
        this.b.runOnUiThread(new g(this, str));
    }

    public void sendConversionForMobageUnity(String str, String str2) {
        this.b.runOnUiThread(new h(this, str, str2));
    }

    public void sendConversionForMobageWithCARewardUnity(String str) {
        this.b.runOnUiThread(new l(this, str));
    }

    public void sendConversionForMobageWithCARewardUnity(String str, String str2) {
        this.b.runOnUiThread(new b(this, str, str2));
    }

    public void sendConversionUnity() {
        this.b.runOnUiThread(new a(this));
    }

    public void sendConversionUnity(String str) {
        this.b.runOnUiThread(new e(this, str));
    }

    public void sendConversionUnity(String str, String str2) {
        this.b.runOnUiThread(new f(this, str, str2));
    }

    public void sendConversionWithCARewardUnity(String str) {
        this.b.runOnUiThread(new j(this, str));
    }

    public void sendConversionWithCARewardUnity(String str, String str2) {
        this.b.runOnUiThread(new k(this, str, str2));
    }

    public void sendReengagementConversion(Activity activity) {
        activity.runOnUiThread(new d(this, activity));
    }

    public void sendUserIdForMobageUnity(String str) {
        this.b.runOnUiThread(new i(this, str));
    }

    public void setUrlScheme(Activity activity) {
        activity.runOnUiThread(new c(this, activity));
    }
}
