package com.com2us.wrapper.media;

import android.content.res.AssetFileDescriptor;
import com.com2us.wrapper.function.CResource;
import com.com2us.wrapper.function.CResource.EResourcePathType;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import defpackage.e;
import defpackage.f;
import defpackage.g;
import java.io.File;
import java.util.HashMap;
import jp.co.dimage.android.o;

public class CSoundManager extends CWrapper {
    private static int a = 0;
    private int b = -1;
    private int c = 100;
    private int d = -1;
    private int e = 100;
    private HashMap<Integer, e> f = new HashMap();

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_EXITED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    private static synchronized int a() {
        int i;
        synchronized (CSoundManager.class) {
            a++;
            i = a;
        }
        return i;
    }

    public int getCurrentPlaying(boolean z) {
        return z ? this.b : this.d;
    }

    public int getVolume(boolean z) {
        return z ? this.c : this.e;
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass1.a[rVar.ordinal()]) {
            case o.a /*1*/:
                for (e eVar : this.f.values()) {
                    eVar.d();
                    eVar.e();
                }
                this.f.clear();
                g.j();
                return;
            default:
                return;
        }
    }

    public boolean setVolume(int i, boolean z) {
        int max = Math.max(0, Math.min(100, i));
        for (e eVar : this.f.values()) {
            if (eVar.g() == z) {
                eVar.a(max);
            }
        }
        if (z) {
            this.c = max;
        } else {
            this.e = max;
        }
        return true;
    }

    public int soundCreate(String str, boolean z) {
        Object fVar;
        String fullPathName = CResource.getFullPathName(str, EResourcePathType.ASSETS);
        AssetFileDescriptor assetOpenFd = CResource.assetOpenFd(fullPathName);
        if (assetOpenFd != null) {
            if (z) {
                try {
                    fVar = new f(assetOpenFd, fullPathName);
                } catch (Exception e) {
                    fVar = null;
                }
            } else {
                fVar = new g(assetOpenFd);
            }
            try {
                assetOpenFd.close();
            } catch (Exception e2) {
            }
        } else {
            fullPathName = CResource.getFullPathName(str, EResourcePathType.SDCARD);
            if (fullPathName != null) {
                if (z) {
                    try {
                        fVar = new f(fullPathName);
                    } catch (Exception e3) {
                        fVar = null;
                    }
                } else {
                    fVar = new g(fullPathName);
                }
            } else if (!new File(str).exists()) {
                fVar = null;
            } else if (z) {
                try {
                    fVar = new f(str);
                } catch (Exception e4) {
                    fVar = null;
                }
            } else {
                fVar = new g(str);
            }
        }
        if (fVar == null) {
            return -1;
        }
        int a = a();
        this.f.put(Integer.valueOf(a), fVar);
        return a;
    }

    public boolean soundDestroy(int i) {
        e eVar = (e) this.f.remove(Integer.valueOf(i));
        if (eVar == null) {
            return false;
        }
        eVar.e();
        return true;
    }

    public int soundGetVolume(int i) {
        e eVar = (e) this.f.get(Integer.valueOf(i));
        return eVar == null ? -1 : eVar.f();
    }

    public boolean soundPause(int i) {
        e eVar = (e) this.f.get(Integer.valueOf(i));
        return eVar == null ? false : eVar.b();
    }

    public boolean soundPlay(int i, boolean z) {
        e eVar = (e) this.f.get(Integer.valueOf(i));
        if (eVar == null) {
            return false;
        }
        if (eVar.g()) {
            this.b = i;
        } else {
            this.d = i;
        }
        return eVar.a(z);
    }

    public boolean soundPrepare(int i) {
        e eVar = (e) this.f.get(Integer.valueOf(i));
        return eVar == null ? false : eVar.a();
    }

    public boolean soundResume(int i) {
        e eVar = (e) this.f.get(Integer.valueOf(i));
        return eVar == null ? false : eVar.c();
    }

    public boolean soundSetVolume(int i, int i2) {
        e eVar = (e) this.f.get(Integer.valueOf(i));
        return eVar == null ? false : eVar.a(Math.max(0, Math.min(100, i2)));
    }

    public boolean soundStop(int i) {
        e eVar = (e) this.f.get(Integer.valueOf(i));
        if (eVar == null) {
            return false;
        }
        if (this.b == i) {
            this.b = -1;
        } else if (this.d == i) {
            this.d = -1;
        }
        return eVar.d();
    }

    public void stopAll(boolean z) {
        for (e eVar : this.f.values()) {
            if (eVar.g() == z) {
                eVar.d();
            }
        }
        if (z) {
            this.b = -1;
        } else {
            this.d = -1;
        }
    }

    public void stopCurrent(boolean z) {
        e eVar = (e) this.f.get(Integer.valueOf(z ? this.b : this.d));
        if (eVar != null) {
            eVar.d();
        }
    }
}
