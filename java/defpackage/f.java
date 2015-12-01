package defpackage;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import com.com2us.wrapper.function.CResource;
import java.io.File;
import java.io.FileInputStream;

public class f extends e implements OnErrorListener, OnPreparedListener, OnSeekCompleteListener {
    private MediaPlayer c = new MediaPlayer();
    private String d = null;
    private boolean e = false;

    public f(AssetFileDescriptor assetFileDescriptor, String str) throws Exception {
        a(assetFileDescriptor, str);
    }

    public f(String str) throws Exception {
        a(str);
    }

    private void a(AssetFileDescriptor assetFileDescriptor, String str) throws Exception {
        this.e = true;
        this.c.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
        b(str);
    }

    private void a(String str) throws Exception {
        Throwable th;
        FileInputStream fileInputStream;
        try {
            this.e = false;
            fileInputStream = new FileInputStream(new File(str));
            try {
                this.c.setDataSource(fileInputStream.getFD());
                b(str);
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Throwable th2) {
                th = th2;
                try {
                    fileInputStream.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            fileInputStream = null;
            fileInputStream.close();
            throw th;
        }
    }

    private void b(String str) throws Exception {
        this.d = str;
        this.c.setOnPreparedListener(this);
        this.c.setOnSeekCompleteListener(this);
        this.c.setOnErrorListener(this);
        this.c.setVolume(this.a, this.a);
        this.c.prepareAsync();
    }

    public boolean a() {
        return true;
    }

    public boolean a(int i) {
        this.a = ((float) i) / 100.0f;
        this.c.setVolume(this.a, this.a);
        return true;
    }

    public synchronized boolean a(boolean z) {
        if (this.b) {
            this.c.setLooping(z);
            if (this.c.isPlaying()) {
                this.b = false;
                this.c.seekTo(0);
            } else {
                this.c.start();
            }
        } else {
            a(e$b.PLAY, !z ? 0.0f : 1.0f);
        }
        return true;
    }

    public synchronized boolean b() {
        if (!this.b) {
            a(e$b.PAUSE, -1.0f);
        } else if (this.c.isPlaying()) {
            this.c.pause();
        }
        return true;
    }

    public synchronized boolean c() {
        if (!this.b) {
            a(e$b.RESUME, -1.0f);
        } else if (!this.c.isPlaying()) {
            this.c.start();
        }
        return true;
    }

    public synchronized boolean d() {
        if (this.b) {
            try {
                if (this.c.isPlaying()) {
                    this.c.pause();
                    this.b = false;
                    this.c.seekTo(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            a(e$b.STOP, -1.0f);
        }
        return true;
    }

    public synchronized void e() {
        if (this.b) {
            this.c.release();
            this.c = null;
        } else {
            a(e$b.DESTROY, -1.0f);
        }
    }

    public int f() {
        return (int) (this.a * 100.0f);
    }

    public boolean g() {
        return true;
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        this.c.release();
        this.b = false;
        this.c = new MediaPlayer();
        try {
            if (this.e) {
                a(CResource.assetOpenFd(this.d), this.d);
            } else {
                a(this.d);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized void onPrepared(MediaPlayer mediaPlayer) {
        this.b = true;
        i();
    }

    public void onSeekComplete(MediaPlayer mediaPlayer) {
        this.b = true;
        i();
    }
}
