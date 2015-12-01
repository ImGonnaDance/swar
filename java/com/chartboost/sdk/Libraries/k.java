package com.chartboost.sdk.Libraries;

import android.app.Activity;
import android.content.Context;
import com.chartboost.sdk.b;
import java.lang.ref.WeakReference;

public final class k extends WeakReference<Activity> {
    private static k b;
    private int a;

    private k(Activity activity) {
        super(activity);
        this.a = activity.hashCode();
    }

    public static k a(Activity activity) {
        if (b == null || b.a != activity.hashCode()) {
            b = new k(activity);
        }
        return b;
    }

    public boolean b(Activity activity) {
        if (activity != null && activity.hashCode() == this.a) {
            return true;
        }
        return false;
    }

    public boolean a(k kVar) {
        if (kVar != null && kVar.a() == this.a) {
            return true;
        }
        return false;
    }

    public int a() {
        return this.a;
    }

    public int hashCode() {
        return a();
    }

    public Context b() {
        Context context = (Context) get();
        if (context == null) {
            return b.k();
        }
        return context;
    }
}
