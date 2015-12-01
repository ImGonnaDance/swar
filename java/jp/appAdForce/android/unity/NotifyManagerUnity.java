package jp.appAdForce.android.unity;

import android.app.Activity;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.NotifyManager;

public class NotifyManagerUnity extends NotifyManager {
    private Activity a;
    private NotifyManager b = this;

    public NotifyManagerUnity(Activity activity, AdManager adManager) {
        super(activity, adManager);
        this.a = activity;
    }

    public void registerToGCMUnity(Activity activity, String str) {
        this.a.runOnUiThread(new ab(this, activity, str));
    }
}
