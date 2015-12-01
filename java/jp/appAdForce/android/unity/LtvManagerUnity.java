package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.LtvManager;
import jp.co.dimage.android.f;

public class LtvManagerUnity extends LtvManager implements f {
    private Activity a;
    private LtvManager b = this;

    public LtvManagerUnity(Activity activity, AdManager adManager) {
        super(adManager);
        this.a = activity;
    }

    public void sendLtvConversionUnity(int i) {
        this.a.runOnUiThread(new z(this, i));
    }

    public void sendLtvConversionUnity(int i, String str) {
        this.a.runOnUiThread(new aa(this, i, str));
    }
}
