package com.chartboost.sdk.impl;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.impl.bh.a;
import java.util.Map;
import jp.co.cyberz.fox.a.a.i;

public class bf extends SurfaceView implements a {
    OnVideoSizeChangedListener a = new OnVideoSizeChangedListener(this) {
        final /* synthetic */ bf a;

        {
            this.a = r1;
        }

        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            this.a.l = mp.getVideoWidth();
            this.a.m = mp.getVideoHeight();
            if (this.a.l != 0 && this.a.m != 0) {
                this.a.getHolder().setFixedSize(this.a.l, this.a.m);
            }
        }
    };
    OnPreparedListener b = new OnPreparedListener(this) {
        final /* synthetic */ bf a;

        {
            this.a = r1;
        }

        public void onPrepared(MediaPlayer mp) {
            this.a.h = 2;
            this.a.l = mp.getVideoWidth();
            this.a.m = mp.getVideoHeight();
            if (this.a.q != null) {
                this.a.q.onPrepared(this.a.k);
            }
            int e = this.a.t;
            if (e != 0) {
                this.a.a(e);
            }
            if (this.a.l != 0 && this.a.m != 0) {
                this.a.getHolder().setFixedSize(this.a.l, this.a.m);
                if (this.a.n == this.a.l && this.a.o == this.a.m && this.a.i == 3) {
                    this.a.a();
                }
            } else if (this.a.i == 3) {
                this.a.a();
            }
        }
    };
    Callback c = new Callback(this) {
        final /* synthetic */ bf a;

        {
            this.a = r1;
        }

        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            Object obj = 1;
            this.a.n = w;
            this.a.o = h;
            Object obj2 = this.a.i == 3 ? 1 : null;
            if (!(this.a.l == w && this.a.m == h)) {
                obj = null;
            }
            if (this.a.k != null && obj2 != null && r1 != null) {
                if (this.a.t != 0) {
                    this.a.a(this.a.t);
                }
                this.a.a();
            }
        }

        public void surfaceCreated(SurfaceHolder holder) {
            this.a.j = holder;
            this.a.g();
        }

        public void surfaceDestroyed(SurfaceHolder holder) {
            this.a.j = null;
            this.a.a(true);
        }
    };
    private String d = "VideoSurfaceView";
    private Uri e;
    private Map<String, String> f;
    private int g;
    private int h = 0;
    private int i = 0;
    private SurfaceHolder j = null;
    private MediaPlayer k = null;
    private int l;
    private int m;
    private int n;
    private int o;
    private OnCompletionListener p;
    private OnPreparedListener q;
    private int r;
    private OnErrorListener s;
    private int t;
    private OnCompletionListener u = new OnCompletionListener(this) {
        final /* synthetic */ bf a;

        {
            this.a = r1;
        }

        public void onCompletion(MediaPlayer mp) {
            this.a.i = 5;
            if (this.a.h != 5) {
                this.a.h = 5;
                if (this.a.p != null) {
                    this.a.p.onCompletion(this.a.k);
                }
            }
        }
    };
    private OnErrorListener v = new OnErrorListener(this) {
        final /* synthetic */ bf a;

        {
            this.a = r1;
        }

        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            CBLogging.a(this.a.d, "Error: " + framework_err + i.b + impl_err);
            this.a.h = -1;
            this.a.i = -1;
            return (this.a.s == null || this.a.s.onError(this.a.k, framework_err, impl_err)) ? true : true;
        }
    };
    private OnBufferingUpdateListener w = new OnBufferingUpdateListener(this) {
        final /* synthetic */ bf a;

        {
            this.a = r1;
        }

        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            this.a.r = percent;
        }
    };

    public bf(Context context) {
        super(context);
        f();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int i;
        int defaultSize = getDefaultSize(0, widthMeasureSpec);
        int defaultSize2 = getDefaultSize(0, heightMeasureSpec);
        if (this.l <= 0 || this.m <= 0) {
            i = defaultSize2;
            defaultSize2 = defaultSize;
        } else {
            i = Math.min(defaultSize2, Math.round((((float) this.m) / ((float) this.l)) * ((float) defaultSize)));
            defaultSize2 = Math.min(defaultSize, Math.round(((float) defaultSize2) * (((float) this.l) / ((float) this.m))));
        }
        setMeasuredDimension(defaultSize2, i);
    }

    private void f() {
        this.l = 0;
        this.m = 0;
        getHolder().addCallback(this.c);
        getHolder().setType(3);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.h = 0;
        this.i = 0;
    }

    public void a(Uri uri) {
        a(uri, null);
    }

    public void a(Uri uri, Map<String, String> map) {
        this.e = uri;
        this.f = map;
        this.t = 0;
        g();
        requestLayout();
        invalidate();
    }

    private void g() {
        if (this.e != null && this.j != null) {
            Intent intent = new Intent("com.android.music.musicservicecommand");
            intent.putExtra("command", "pause");
            getContext().sendBroadcast(intent);
            a(false);
            try {
                this.k = new MediaPlayer();
                this.k.setOnPreparedListener(this.b);
                this.k.setOnVideoSizeChangedListener(this.a);
                this.g = -1;
                this.k.setOnCompletionListener(this.u);
                this.k.setOnErrorListener(this.v);
                this.k.setOnBufferingUpdateListener(this.w);
                this.r = 0;
                this.k.setDataSource(getContext(), this.e, this.f);
                this.k.setDisplay(this.j);
                this.k.setAudioStreamType(3);
                this.k.setScreenOnWhilePlaying(true);
                this.k.prepareAsync();
                this.h = 1;
            } catch (Throwable e) {
                CBLogging.d(this.d, "Unable to open content: " + this.e, e);
                this.h = -1;
                this.i = -1;
                this.v.onError(this.k, 1, 0);
            } catch (Throwable e2) {
                CBLogging.d(this.d, "Unable to open content: " + this.e, e2);
                this.h = -1;
                this.i = -1;
                this.v.onError(this.k, 1, 0);
            }
        }
    }

    public void a(OnPreparedListener onPreparedListener) {
        this.q = onPreparedListener;
    }

    public void a(OnCompletionListener onCompletionListener) {
        this.p = onCompletionListener;
    }

    public void a(OnErrorListener onErrorListener) {
        this.s = onErrorListener;
    }

    private void a(boolean z) {
        if (this.k != null) {
            this.k.reset();
            this.k.release();
            this.k = null;
            this.h = 0;
            if (z) {
                this.i = 0;
            }
        }
    }

    public void a() {
        if (h()) {
            this.k.start();
            this.h = 3;
        }
        this.i = 3;
    }

    public void b() {
        if (h() && this.k.isPlaying()) {
            this.k.pause();
            this.h = 4;
        }
        this.i = 4;
    }

    public int c() {
        if (!h()) {
            this.g = -1;
            return this.g;
        } else if (this.g > 0) {
            return this.g;
        } else {
            this.g = this.k.getDuration();
            return this.g;
        }
    }

    public int d() {
        if (h()) {
            return this.k.getCurrentPosition();
        }
        return 0;
    }

    public void a(int i) {
        if (h()) {
            this.k.seekTo(i);
            this.t = 0;
            return;
        }
        this.t = i;
    }

    public boolean e() {
        return h() && this.k.isPlaying();
    }

    private boolean h() {
        return (this.k == null || this.h == -1 || this.h == 0 || this.h == 1) ? false : true;
    }

    public void a(int i, int i2) {
    }
}
