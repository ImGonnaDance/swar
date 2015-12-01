package com.chartboost.sdk.impl;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.chartboost.sdk.Chartboost;
import com.chartboost.sdk.Libraries.CBLogging;

public class bh extends FrameLayout {
    private View a;
    private boolean b;

    public interface a {
        void a();

        void a(int i);

        void a(int i, int i2);

        void a(OnCompletionListener onCompletionListener);

        void a(OnErrorListener onErrorListener);

        void a(OnPreparedListener onPreparedListener);

        void a(Uri uri);

        void b();

        int c();

        int d();

        boolean e();
    }

    public bh(Context context) {
        super(context);
        d();
    }

    private void d() {
        this.b = c();
        if (!Chartboost.getImpressionsUseActivities() && (getContext() instanceof Activity)) {
            this.b = a((Activity) getContext());
        }
        CBLogging.e("VideoInit", "Choosing " + (this.b ? "texture" : "surface") + " solution for video playback");
        if (this.b) {
            this.a = new bg(getContext());
        } else {
            this.a = new bf(getContext());
        }
        addView(this.a, new LayoutParams(-1, -1));
        if (!this.b) {
            ((SurfaceView) this.a).setZOrderMediaOverlay(true);
        }
    }

    public boolean a() {
        return this.b;
    }

    public a b() {
        return (a) this.a;
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        b().a(w, h);
    }

    public static boolean a(Activity activity) {
        if (c()) {
            if (Chartboost.getImpressionsUseActivities()) {
                return true;
            }
            try {
                return activity.getWindow().getDecorView().isHardwareAccelerated();
            } catch (Exception e) {
            }
        }
        return false;
    }

    public static boolean c() {
        return VERSION.SDK_INT >= 14;
    }
}
