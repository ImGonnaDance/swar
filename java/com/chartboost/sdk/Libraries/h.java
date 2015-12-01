package com.chartboost.sdk.Libraries;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import com.chartboost.sdk.Libraries.e.a;
import com.chartboost.sdk.b;
import com.chartboost.sdk.impl.cd;
import java.io.File;

public class h {
    private static h f;
    public Context a = b.k();
    private File b;
    private File c;
    private File d;
    private boolean e;

    public h(String str, boolean z) {
        if (this.a == null) {
            throw new RuntimeException("Cannot find context object");
        }
        this.e = this.a.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0;
        if (str != null) {
            this.b = a(str, z);
        } else {
            this.b = a("CBCommonCacheFolder", z);
        }
    }

    private File a(String str, boolean z) {
        if (this.b != null) {
            return this.b;
        }
        File file;
        if (z && d()) {
            file = new File(new File(this.a.getExternalCacheDir(), "__chartboost"), str);
            this.d = file;
        } else {
            file = new File(new File(this.a.getCacheDir(), "__chartboost"), str);
            this.c = file;
        }
        if (file.exists()) {
            return file;
        }
        file.mkdirs();
        return file;
    }

    public synchronized void a(String str, a aVar) {
        if (this.d == null || d()) {
            if (this.b != null) {
                File file = null;
                if (!TextUtils.isEmpty(str)) {
                    file = new File(this.b.getPath(), str);
                }
                a(file, aVar);
            }
        }
    }

    public synchronized File a(File file, a aVar) {
        File file2 = null;
        synchronized (this) {
            if (this.d == null || d()) {
                if (this.b != null) {
                    if (file == null) {
                        file2 = new File(this.b.getPath(), Long.toString(System.nanoTime()));
                    } else {
                        file2 = file;
                    }
                    try {
                        cd.a(file2, aVar.toString().getBytes());
                    } catch (Throwable e) {
                        CBLogging.b("CBFileCache", "IOException attempting to write cache to disk", e);
                    }
                }
            }
        }
        return file2;
    }

    public synchronized void a(String str, byte[] bArr) {
        if (this.d == null || d()) {
            if (this.b != null) {
                File file = null;
                if (!TextUtils.isEmpty(str)) {
                    file = new File(this.b.getPath(), str);
                }
                a(file, bArr);
            }
        }
    }

    public synchronized void a(File file, byte[] bArr) {
        if (this.d == null || d()) {
            if (!(this.b == null || bArr == null)) {
                if (file == null) {
                    file = new File(this.b.getPath(), Long.toString(System.nanoTime()));
                }
                try {
                    cd.a(file, bArr);
                } catch (Throwable e) {
                    CBLogging.b("CBFileCache", "IOException attempting to write cache to disk", e);
                }
            }
        }
    }

    public synchronized a a(String str) {
        a aVar;
        if (this.d != null && !d()) {
            aVar = a.a;
        } else if (this.b == null || str == null) {
            aVar = a.a;
        } else {
            File file = new File(this.b, str);
            if (file.exists()) {
                aVar = a(file);
            } else {
                aVar = a.a;
            }
        }
        return aVar;
    }

    public synchronized a a(File file) {
        a aVar;
        if (this.d != null && !d()) {
            aVar = a.a;
        } else if (this.b == null) {
            aVar = a.a;
        } else {
            String str;
            try {
                str = new String(cd.b(file));
            } catch (Throwable e) {
                CBLogging.b("CBFileCache", "Error loading cache from disk", e);
                str = null;
            }
            aVar = a.j(str);
        }
        return aVar;
    }

    public synchronized byte[] b(File file) {
        byte[] bArr = null;
        synchronized (this) {
            if (this.d == null || d()) {
                if (this.b != null) {
                    try {
                        bArr = cd.b(file);
                    } catch (Throwable e) {
                        CBLogging.b("CBFileCache", "Error loading cache from disk", e);
                    }
                }
            }
        }
        return bArr;
    }

    public synchronized String[] a() {
        String[] strArr = null;
        synchronized (this) {
            if (this.d == null || d()) {
                if (this.b != null) {
                    strArr = this.b.list();
                }
            }
        }
        return strArr;
    }

    public synchronized void c(File file) {
        if (file != null) {
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public synchronized void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            c(d(str));
        }
    }

    public synchronized void b() {
        int i = 0;
        synchronized (this) {
            if (this.d == null || d()) {
                if (this.b != null) {
                    try {
                        if (this.d != null) {
                            File[] listFiles = this.d.listFiles();
                            if (listFiles != null) {
                                for (File delete : listFiles) {
                                    delete.delete();
                                }
                            }
                        }
                        if (this.c != null) {
                            File[] listFiles2 = this.c.listFiles();
                            if (listFiles2 != null) {
                                int length = listFiles2.length;
                                while (i < length) {
                                    listFiles2[i].delete();
                                    i++;
                                }
                            }
                        }
                    } catch (Exception e) {
                        CBLogging.b("CBFileCache", "Error while clearing the file cache");
                    }
                }
            }
        }
    }

    private boolean d() {
        if (this.e && Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    public boolean c(String str) {
        if ((this.d != null && !d()) || this.b == null || str == null) {
            return false;
        }
        return new File(this.b.getPath(), str).exists();
    }

    public File d(String str) {
        if ((this.d == null || d()) && this.b != null) {
            return new File(this.b.getPath(), str);
        }
        return null;
    }

    public static synchronized h c() {
        h hVar;
        synchronized (h.class) {
            if (f == null) {
                f = new h("CBVideoCompletion", false);
            }
            hVar = f;
        }
        return hVar;
    }
}
