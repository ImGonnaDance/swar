package jp.appAdForce.android;

import android.content.Context;
import android.content.Intent;
import jp.co.dimage.android.d;
import jp.co.dimage.android.f;
import jp.co.dimage.android.g;

public class AdManager implements f {
    public static final String a = "v2.14.7g";
    private static String d = null;
    private d b = null;
    private g c = null;

    public AdManager(Context context) {
        this.b = new d(context);
        this.c = new g(this.b);
    }

    private void a(String str) {
        this.c.d(str);
    }

    private void b(String str) {
        this.c.d(str);
    }

    public static void updateFrom2_10_4g() {
        d.H();
    }

    public final d a() {
        return this.b;
    }

    public final Context b() {
        return this.b.k();
    }

    public boolean isAppConversionCompleted() {
        return this.b.x();
    }

    public boolean isConversionCompleted() {
        return this.b.w() || this.b.x();
    }

    public boolean isWebConversionCompleted() {
        return this.b.w();
    }

    public void openConversionPage(String str) {
        this.c.c(str);
    }

    public void sendConversion() {
        this.b.c(false);
        this.c.a();
    }

    public void sendConversion(String str) {
        this.b.c(true);
        this.c.a(str);
    }

    public void sendConversion(String str, String str2) {
        this.b.c(true);
        this.c.a(str, str2);
    }

    public void sendConversionForMobage(String str) {
        d = str;
        this.c.e(str, f.aX);
    }

    public void sendConversionForMobage(String str, String str2) {
        d = str2;
        this.c.b(str, str2, f.aX);
    }

    public void sendConversionForMobageWithCAReward(String str) {
        d = str;
        this.c.c(str, f.aX);
    }

    public void sendConversionForMobageWithCAReward(String str, String str2) {
        d = str2;
        this.c.a(str, str2, f.aX);
    }

    public void sendConversionWithCAReward(String str) {
        this.c.b(str);
    }

    public void sendConversionWithCAReward(String str, String str2) {
        this.c.b(str, str2);
    }

    public void sendReengagementConversion(Intent intent) {
        if (intent != null) {
            this.c.a(intent.getData());
        }
    }

    public void sendUserIdForMobage(String str) {
        this.c.c(d, f.aX, str);
    }

    public void setOptout(boolean z) {
        this.b.b(z);
        this.c.j(z);
    }

    public void setServerUrl(String str) {
        this.c.g(str);
    }

    public void setUrlScheme(Intent intent) {
        sendReengagementConversion(intent);
    }
}
