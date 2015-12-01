package jp.co.dimage.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import com.com2us.module.manager.ModuleConfig;
import java.io.File;
import java.io.IOException;
import jp.co.cyberz.fox.a.a.i;

public final class q extends m {
    private Context a;
    private String b;
    private String c;
    private String d;
    private String e;
    private String f;
    private boolean g;

    private q(Context context) {
        this(context, null, null);
    }

    public q(Context context, String str, String str2) {
        String str3;
        this.b = i.a;
        this.c = i.a;
        this.d = i.a;
        this.e = i.a;
        this.f = i.a;
        this.g = true;
        this.a = context;
        this.b = str;
        this.c = str2;
        this.d = context.getPackageName();
        this.g = b(this.a);
        if (this.g) {
            if (!a(e())) {
                new File(e()).mkdir();
            }
            str3 = e() + f();
            if (!a(str3)) {
                new File(str3).getParentFile().mkdirs();
            }
        }
        File j = j();
        if (j != null) {
            try {
                str3 = m.a(j);
                this.f = str3.split(i.b)[0];
                this.e = str3.split(i.b)[1];
            } catch (Exception e) {
            }
        }
    }

    private static void a(Context context, boolean z) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(f.aC, 0);
        String string = sharedPreferences.getString(f.aD, i.a);
        if (p.a(string) || string.length() <= 0) {
            sharedPreferences.edit().putString(f.aD, z ? a.e : a.d).commit();
        }
    }

    private static boolean a(Context context) {
        return a.e.equals(context.getSharedPreferences(f.aC, 0).getString(f.aD, a.d));
    }

    private boolean a(String str) {
        return (str == null || i.a.equals(str)) ? false : m.a(e(), str);
    }

    private static boolean b(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE);
            return applicationInfo == null || applicationInfo.metaData == null || applicationInfo.metaData.getInt(f.G, 1) != 0;
        } catch (NameNotFoundException e) {
            return true;
        }
    }

    private String e() {
        String str = this.d;
        if (!p.a(this.b)) {
            str = this.b;
        }
        return m.a() + str + "/";
    }

    private String f() {
        return !p.a(this.c) ? this.c : f.aB;
    }

    private void g() {
        String str;
        this.g = b(this.a);
        if (this.g) {
            if (!a(e())) {
                new File(e()).mkdir();
            }
            str = e() + f();
            if (!a(str)) {
                new File(str).getParentFile().mkdirs();
            }
        }
        File j = j();
        if (j != null) {
            try {
                str = m.a(j);
                this.f = str.split(i.b)[0];
                this.e = str.split(i.b)[1];
            } catch (Exception e) {
            }
        }
    }

    private void h() {
        if (!a(e())) {
            new File(e()).mkdir();
        }
        String str = e() + f();
        if (!a(str)) {
            new File(str).getParentFile().mkdirs();
        }
    }

    private void i() {
        File j = j();
        if (j != null) {
            try {
                String a = m.a(j);
                this.f = a.split(i.b)[0];
                this.e = a.split(i.b)[1];
            } catch (Exception e) {
            }
        }
    }

    private File j() {
        File file;
        File file2;
        if (p.a(this.c) || p.a(this.b)) {
            file = null;
        } else {
            file2 = new File(m.a() + this.b, this.c);
            File file3 = new File(m.a() + this.b, f.aB);
            File file4 = new File(m.a() + this.d, this.c);
            if (file2.exists() || file3.exists() || file4.exists()) {
                File[] fileArr = new File[]{file2, file3, file4};
                int length = fileArr.length;
                int i = 0;
                file2 = null;
                while (i < length) {
                    file = fileArr[i];
                    if (file2 == null) {
                        file2 = file;
                    }
                    if (file2 == null || !file.exists() || file2.lastModified() >= file.lastModified()) {
                        file = file2;
                    }
                    i++;
                    file2 = file;
                }
                file = !file2.exists() ? null : file2;
            } else {
                file = null;
            }
        }
        file2 = new File(m.a() + this.d, f.aB);
        if (!file2.exists()) {
            file2 = null;
        }
        return (file == null && file2 == null) ? null : (file == null || file2 == null) ? file != null ? file : file2 != null ? file2 : null : file.lastModified() < file2.lastModified() ? file2 : file;
    }

    private File k() {
        if (p.a(this.c) || p.a(this.b)) {
            return null;
        }
        File file = new File(m.a() + this.b, this.c);
        File file2 = new File(m.a() + this.b, f.aB);
        File file3 = new File(m.a() + this.d, this.c);
        if (!file.exists() && !file2.exists() && !file3.exists()) {
            return null;
        }
        File[] fileArr = new File[]{file, file2, file3};
        int length = fileArr.length;
        int i = 0;
        file = null;
        while (i < length) {
            File file4 = fileArr[i];
            if (file == null) {
                file = file4;
            }
            if (file == null || !file4.exists() || file.lastModified() >= file4.lastModified()) {
                file4 = file;
            }
            i++;
            file = file4;
        }
        return !file.exists() ? null : file;
    }

    private File l() {
        File file = new File(m.a() + this.d, f.aB);
        return file.exists() ? file : null;
    }

    private boolean m() {
        return j() == null ? true : ((p.a(this.b) && p.a(this.c)) || m.a(e(), f())) ? false : true;
    }

    public final boolean b() {
        return (p.a(this.e) || p.a(this.f)) ? false : true;
    }

    public final String c() {
        return p.a(this.e) ? null : this.e;
    }

    public final boolean c(String str, String str2) {
        if (p.a(str) || p.a(str2) || !this.g) {
            return false;
        }
        if (!(Environment.getExternalStorageState().equals("mounted"))) {
            return false;
        }
        boolean z = j() == null ? true : ((p.a(this.b) && p.a(this.c)) || m.a(e(), f())) ? false : true;
        if (!z) {
            return false;
        }
        try {
            File j = j();
            String stringBuilder = new StringBuilder(String.valueOf(str)).append(i.b).append(str2).toString();
            m.b(e(), f());
            String e = e();
            String f = f();
            File parentFile = new File(e, f).getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            m.a(new File(e, f), stringBuilder);
            if (j != null) {
                m.b(j);
            }
            return true;
        } catch (IOException e2) {
            return false;
        }
    }

    public final String d() {
        return p.a(this.f) ? null : this.f;
    }
}
