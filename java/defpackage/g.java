package defpackage;

import android.content.res.AssetFileDescriptor;
import android.media.SoundPool;
import com.com2us.wrapper.function.CFunction;

public class g extends e {
    private static SoundPool c = null;
    private static g$a d = null;
    private int e = 0;
    private int f = 0;

    static {
        g.c(12);
    }

    public g(AssetFileDescriptor assetFileDescriptor) {
        if (d != null) {
            d.a(this);
        } else {
            this.b = true;
        }
        this.e = c.load(assetFileDescriptor, 1);
    }

    public g(String str) {
        if (d != null) {
            d.a(this);
        } else {
            this.b = true;
        }
        this.e = c.load(str, 1);
    }

    private static void c(int i) {
        if (c == null) {
            c = new SoundPool(i, 3, 0);
            if (CFunction.getSystemVersionCode() >= 8) {
                d = new g$a(null);
                c.setOnLoadCompleteListener(d);
            }
        }
    }

    public static void j() {
        if (c != null) {
            c.release();
            c = null;
        }
        if (d != null) {
            d.a();
            d = null;
        }
    }

    public boolean a() {
        return true;
    }

    public boolean a(int i) {
        this.a = ((float) i) / 100.0f;
        if (this.f != 0) {
            c.setVolume(this.f, this.a, this.a);
        }
        return true;
    }

    public synchronized boolean a(boolean z) {
        float f = 1.0f;
        boolean z2 = false;
        synchronized (this) {
            if (this.e != 0) {
                if (this.b) {
                    this.f = c.play(this.e, this.a, this.a, z ? 1 : 0, z ? -1 : 0, 1.0f);
                    z2 = this.f != 0;
                } else {
                    e$b e_b = e$b.PLAY;
                    if (!z) {
                        f = 0.0f;
                    }
                    a(e_b, f);
                    z2 = true;
                }
            }
        }
        return z2;
    }

    public void b(int i) {
        if (i == this.e) {
            this.b = true;
            i();
        }
    }

    public synchronized boolean b() {
        boolean z = true;
        synchronized (this) {
            if (this.f == 0) {
                z = false;
            } else if (this.b) {
                c.pause(this.f);
            } else {
                a(e$b.PAUSE, -1.0f);
            }
        }
        return z;
    }

    public synchronized boolean c() {
        boolean z = true;
        synchronized (this) {
            if (this.f == 0) {
                z = false;
            } else if (this.b) {
                c.resume(this.f);
            } else {
                a(e$b.RESUME, -1.0f);
            }
        }
        return z;
    }

    public synchronized boolean d() {
        boolean z = false;
        synchronized (this) {
            if (this.f != 0) {
                if (this.b) {
                    c.stop(this.f);
                    this.f = 0;
                    z = true;
                } else {
                    a(e$b.STOP, -1.0f);
                    z = true;
                }
            }
        }
        return z;
    }

    public synchronized void e() {
        if (this.e != 0) {
            if (this.b) {
                c.unload(this.e);
                d.b(this);
            } else {
                a(e$b.DESTROY, -1.0f);
            }
        }
    }

    public int f() {
        return (int) (this.a * 100.0f);
    }

    public boolean g() {
        return false;
    }
}
