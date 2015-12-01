package jp.co.dimage.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.com2us.module.activeuser.Constants.Network.Gateway;

public class InstallReceiver extends BroadcastReceiver {
    private g a = null;

    public interface a {
        void a();

        void b();
    }

    private void a(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra(Gateway.REQUEST_REFERRER);
        a.a("ADMAGE_DEBUG", "referrer: " + stringExtra);
        this.a = new g(new d(context));
        d.a(stringExtra, new n(this, this.a, stringExtra));
    }

    private void a(g gVar, String str) {
        d.a(str, new n(this, gVar, str));
    }

    public void onReceive(Context context, Intent intent) {
        a.a("ADMAGE_DEBUG", "---------- enter ----------");
        a.a("ADMAGE_DEBUG", "action: " + intent.getAction());
        if ("com.android.vending.INSTALL_REFERRER".equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra(Gateway.REQUEST_REFERRER);
            a.a("ADMAGE_DEBUG", "referrer: " + stringExtra);
            this.a = new g(new d(context));
            d.a(stringExtra, new n(this, this.a, stringExtra));
        }
    }
}
