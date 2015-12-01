package jp.appAdForce.android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import jp.co.dimage.android.p;

public class IntentReceiverActivity extends Activity {
    private void a() {
        try {
            startActivity(getPackageManager().getLaunchIntentForPackage("com.android.vending"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finish();
        }
    }

    private void a(Context context) {
        if (context == null) {
            a();
            return;
        }
        String packageName = context.getPackageName();
        try {
            startActivity(context.getPackageManager().getLaunchIntentForPackage(packageName));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            if (p.a(packageName)) {
                a();
                return;
            }
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(new StringBuilder(WebClient.GOOGLE_PLAY_STORE_PREFIX).append(packageName).toString())));
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                finish();
            }
        }
    }

    private void a(String str) {
        if (p.a(str)) {
            a();
            return;
        }
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse(new StringBuilder(WebClient.GOOGLE_PLAY_STORE_PREFIX).append(str).toString())));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finish();
        }
    }

    public void onResume() {
        super.onResume();
        AdManager adManager = new AdManager(getApplicationContext());
        adManager.setUrlScheme(getIntent());
        Context b = adManager.b();
        if (b == null) {
            a();
            return;
        }
        String packageName = b.getPackageName();
        try {
            startActivity(b.getPackageManager().getLaunchIntentForPackage(packageName));
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            if (p.a(packageName)) {
                a();
                return;
            }
            try {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(new StringBuilder(WebClient.GOOGLE_PLAY_STORE_PREFIX).append(packageName).toString())));
            } catch (Exception e2) {
                e2.printStackTrace();
            } finally {
                finish();
            }
        }
    }
}
