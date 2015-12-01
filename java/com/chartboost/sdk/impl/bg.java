package com.chartboost.sdk.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.impl.bh.a;
import java.util.Map;
import jp.co.cyberz.fox.a.a.i;

public class bg extends TextureView implements SurfaceTextureListener, a {
    OnVideoSizeChangedListener a = new OnVideoSizeChangedListener(this) {
        final /* synthetic */ bg a;

        {
            this.a = r1;
        }

        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
            this.a.k = mp.getVideoWidth();
            this.a.l = mp.getVideoHeight();
            if (this.a.k != 0 && this.a.l != 0) {
                this.a.a(this.a.getWidth(), this.a.getHeight());
            }
        }
    };
    OnPreparedListener b = new OnPreparedListener(this) {
        final /* synthetic */ bg a;

        {
            this.a = r1;
        }

        public void onPrepared(MediaPlayer mp) {
            this.a.g = 2;
            this.a.k = mp.getVideoWidth();
            this.a.l = mp.getVideoHeight();
            if (this.a.n != null) {
                this.a.n.onPrepared(this.a.j);
            }
            int e = this.a.q;
            if (e != 0) {
                this.a.a(e);
            }
            if (this.a.h == 3) {
                this.a.a();
            }
        }
    };
    private String c = "VideoTextureView";
    private Uri d;
    private Map<String, String> e;
    private int f;
    private int g = 0;
    private int h = 0;
    private Surface i = null;
    private MediaPlayer j = null;
    private int k;
    private int l;
    private OnCompletionListener m;
    private OnPreparedListener n;
    private int o;
    private OnErrorListener p;
    private int q;
    private OnCompletionListener r = new OnCompletionListener(this) {
        final /* synthetic */ bg a;

        {
            this.a = r1;
        }

        public void onCompletion(MediaPlayer mp) {
            this.a.h = 5;
            if (this.a.g != 5) {
                this.a.g = 5;
                if (this.a.m != null) {
                    this.a.m.onCompletion(this.a.j);
                }
            }
        }
    };
    private OnErrorListener s = new OnErrorListener(this) {
        final /* synthetic */ bg a;

        {
            this.a = r1;
        }

        public boolean onError(MediaPlayer mp, int framework_err, int impl_err) {
            CBLogging.a(this.a.c, "Error: " + framework_err + i.b + impl_err);
            this.a.g = -1;
            this.a.h = -1;
            return (this.a.p == null || this.a.p.onError(this.a.j, framework_err, impl_err)) ? true : true;
        }
    };
    private OnBufferingUpdateListener t = new OnBufferingUpdateListener(this) {
        final /* synthetic */ bg a;

        {
            this.a = r1;
        }

        public void onBufferingUpdate(MediaPlayer mp, int percent) {
            this.a.o = percent;
        }
    };

    public bg(Context context) {
        super(context);
        f();
    }

    private void f() {
        this.k = 0;
        this.l = 0;
        setSurfaceTextureListener(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        requestFocus();
        this.g = 0;
        this.h = 0;
    }

    public void a(Uri uri) {
        a(uri, null);
    }

    public void a(int i, int i2) {
        if (this.k != 0 && this.l != 0 && i != 0 && i2 != 0) {
            float min = Math.min(((float) i) / ((float) this.k), ((float) i2) / ((float) this.l));
            float f = ((float) this.k) * min;
            min *= (float) this.l;
            Matrix matrix = new Matrix();
            matrix.setScale(f / ((float) i), min / ((float) i2), ((float) i) / 2.0f, ((float) i2) / 2.0f);
            setTransform(matrix);
        }
    }

    public void a(Uri uri, Map<String, String> map) {
        this.d = uri;
        this.e = map;
        this.q = 0;
        g();
        requestLayout();
        invalidate();
    }

    private void g() {
        if (this.d != null && this.i != null) {
            Intent intent = new Intent("com.android.music.musicservicecommand");
            intent.putExtra("command", "pause");
            getContext().sendBroadcast(intent);
            a(false);
            try {
                MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
                mediaMetadataRetriever.setDataSource(this.d.toString());
                String extractMetadata = mediaMetadataRetriever.extractMetadata(19);
                String extractMetadata2 = mediaMetadataRetriever.extractMetadata(18);
                this.l = (int) Float.parseFloat(extractMetadata);
                this.k = (int) Float.parseFloat(extractMetadata2);
            } catch (Throwable e) {
                CBLogging.d("play video", "read size error", e);
            }
            try {
                this.j = new MediaPlayer();
                this.j.setOnPreparedListener(this.b);
                this.j.setOnVideoSizeChangedListener(this.a);
                this.f = -1;
                this.j.setOnCompletionListener(this.r);
                this.j.setOnErrorListener(this.s);
                this.j.setOnBufferingUpdateListener(this.t);
                this.o = 0;
                this.j.setDataSource(getContext(), this.d, this.e);
                this.j.setSurface(this.i);
                this.j.setAudioStreamType(3);
                this.j.setScreenOnWhilePlaying(true);
                this.j.prepareAsync();
                a(getWidth(), getHeight());
                this.g = 1;
            } catch (Throwable e2) {
                CBLogging.d(this.c, "Unable to open content: " + this.d, e2);
                this.g = -1;
                this.h = -1;
                this.s.onError(this.j, 1, 0);
            } catch (Throwable e22) {
                CBLogging.d(this.c, "Unable to open content: " + this.d, e22);
                this.g = -1;
                this.h = -1;
                this.s.onError(this.j, 1, 0);
            }
        }
    }

    public void a(OnPreparedListener onPreparedListener) {
        this.n = onPreparedListener;
    }

    public void a(OnCompletionListener onCompletionListener) {
        this.m = onCompletionListener;
    }

    public void a(OnErrorListener onErrorListener) {
        this.p = onErrorListener;
    }

    private void a(boolean z) {
        if (this.j != null) {
            this.j.reset();
            this.j.release();
            this.j = null;
            this.g = 0;
            if (z) {
                this.h = 0;
            }
        }
    }

    public void a() {
        if (h()) {
            this.j.start();
            this.g = 3;
        }
        this.h = 3;
    }

    public void b() {
        if (h() && this.j.isPlaying()) {
            this.j.pause();
            this.g = 4;
        }
        this.h = 4;
    }

    public int c() {
        if (!h()) {
            this.f = -1;
            return this.f;
        } else if (this.f > 0) {
            return this.f;
        } else {
            this.f = this.j.getDuration();
            return this.f;
        }
    }

    public int d() {
        if (h()) {
            return this.j.getCurrentPosition();
        }
        return 0;
    }

    public void a(int i) {
        if (h()) {
            this.j.seekTo(i);
            this.q = 0;
            return;
        }
        this.q = i;
    }

    public boolean e() {
        return h() && this.j.isPlaying();
    }

    private boolean h() {
        return (this.j == null || this.g == -1 || this.g == 0 || this.g == 1) ? false : true;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surface, int w, int h) {
        this.i = new Surface(surface);
        g();
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        this.i = null;
        a(true);
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int w, int h) {
        Object obj = this.h == 3 ? 1 : null;
        if (this.j != null && obj != null) {
            if (this.q != 0) {
                a(this.q);
            }
            a();
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }
}
