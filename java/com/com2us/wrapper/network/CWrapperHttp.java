package com.com2us.wrapper.network;

import com.com2us.module.activeuser.useragree.UserAgreeNotifier;
import com.com2us.peppermint.PeppermintURL;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.o;

public class CWrapperHttp {
    private HttpURLConnection a = null;
    private URL b;
    private HashMap<String, String> c = new HashMap(7);
    private int d;
    private int e;
    private a f = a.GET;
    private int g = 30000;
    private String h;
    private byte[] i = null;
    private int j;
    private int k;
    private CHttpManager l;

    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[a.values().length];

        static {
            try {
                a[a.GET.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.HEAD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.POST.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    enum a {
        GET,
        HEAD,
        POST
    }

    public CWrapperHttp(CHttpManager cHttpManager) {
        this.l = cHttpManager;
    }

    static /* synthetic */ int c(CWrapperHttp cWrapperHttp, int i) {
        int i2 = cWrapperHttp.k + i;
        cWrapperHttp.k = i2;
        return i2;
    }

    private void d(String str) {
        if (!(str.matches("(?i).*http://.*") || str.matches("(?i).*https://.*"))) {
            str = PeppermintURL.PEPPERMINT_HTTP + str;
        }
        try {
            this.b = new URL(str);
        } catch (MalformedURLException e) {
            this.b = null;
            e.printStackTrace();
        }
    }

    static /* synthetic */ int e(CWrapperHttp cWrapperHttp, int i) {
        int i2 = cWrapperHttp.j + i;
        cWrapperHttp.j = i2;
        return i2;
    }

    public static native void nativeConnectCB(int i, int i2);

    public static native void nativeConnectingCB(int i, byte[] bArr, int i2, int i3);

    public static native boolean nativeIsExistConnectingCB(int i);

    public int a(final int i) {
        if (this.e == 1) {
            return -7;
        }
        this.e = 1;
        new Thread(this) {
            final /* synthetic */ CWrapperHttp b;

            public void run() {
                DataOutputStream dataOutputStream;
                Throwable th;
                try {
                    String cookie;
                    this.b.a = (HttpURLConnection) this.b.b.openConnection();
                    this.b.a.setRequestMethod(this.b.f.name());
                    if (this.b.l.getCookie("Set-Cookie") != null) {
                        cookie = this.b.l.getCookie("Set-Cookie");
                        if (cookie != null) {
                            if (this.b.c.containsKey("Cookie")) {
                                this.b.c.remove("Cookie");
                            }
                            this.b.c.put("Cookie", cookie);
                        }
                    }
                    for (String cookie2 : this.b.c.keySet()) {
                        this.b.a.setRequestProperty(cookie2, (String) this.b.c.get(cookie2));
                    }
                    this.b.a.setUseCaches(false);
                    this.b.a.setConnectTimeout(this.b.g);
                    this.b.a.setReadTimeout(this.b.g);
                    switch (AnonymousClass2.a[this.b.f.ordinal()]) {
                        case o.a /*1*/:
                        case o.b /*2*/:
                            this.b.a.setDoInput(true);
                            break;
                        case o.c /*3*/:
                            if (this.b.h == null) {
                                this.b.h = i.a;
                            }
                            this.b.a.setDoInput(true);
                            this.b.a.setDoOutput(true);
                            try {
                                dataOutputStream = new DataOutputStream(this.b.a.getOutputStream());
                                try {
                                    dataOutputStream.write(this.b.h.getBytes());
                                    dataOutputStream.flush();
                                    if (dataOutputStream != null) {
                                        dataOutputStream.close();
                                        break;
                                    }
                                } catch (Throwable th2) {
                                    th = th2;
                                    if (dataOutputStream != null) {
                                        dataOutputStream.close();
                                    }
                                    throw th;
                                }
                            } catch (Throwable th3) {
                                th = th3;
                                dataOutputStream = null;
                                if (dataOutputStream != null) {
                                    dataOutputStream.close();
                                }
                                throw th;
                            }
                            break;
                    }
                    switch (this.b.a.getResponseCode()) {
                        case SelectTarget.TARGETING_SUCCESS /*200*/:
                        case 301:
                            byte[] bArr;
                            int h;
                            int available;
                            if (!CWrapperHttp.nativeIsExistConnectingCB(i)) {
                                this.b.j = this.b.a.getContentLength();
                                this.b.k = 0;
                                if (this.b.j <= 0) {
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bArr = new byte[262144];
                                    BufferedInputStream bufferedInputStream = new BufferedInputStream(this.b.a.getInputStream());
                                    while (true) {
                                        int read = bufferedInputStream.read(bArr);
                                        if (read == -1) {
                                            this.b.i = byteArrayOutputStream.toByteArray();
                                            byteArrayOutputStream.close();
                                            this.b.d = 0;
                                            break;
                                        }
                                        byteArrayOutputStream.write(bArr, 0, read);
                                        CWrapperHttp.e(this.b, read);
                                        CWrapperHttp.c(this.b, read);
                                    }
                                } else {
                                    this.b.i = new byte[(this.b.j + 262144)];
                                    BufferedInputStream bufferedInputStream2 = new BufferedInputStream(this.b.a.getInputStream());
                                    h = this.b.j;
                                    while (h != 0) {
                                        available = bufferedInputStream2.available();
                                        if (available > 0) {
                                            Object obj = new byte[available];
                                            bufferedInputStream2.read(obj);
                                            System.arraycopy(obj, 0, this.b.i, this.b.k, available);
                                            CWrapperHttp.c(this.b, available);
                                            h -= available;
                                        } else {
                                            Object obj2 = new byte[262144];
                                            int read2 = bufferedInputStream2.read(obj2);
                                            if (read2 > 0) {
                                                System.arraycopy(obj2, 0, this.b.i, this.b.k, read2);
                                                CWrapperHttp.c(this.b, read2);
                                                h -= read2;
                                            }
                                        }
                                        if (h < 0) {
                                            this.b.j = this.b.k;
                                            h = 0;
                                        }
                                    }
                                    this.b.d = 0;
                                    break;
                                }
                            }
                            h = this.b.a.getContentLength() <= 0 ? -1 : this.b.a.getContentLength();
                            bArr = new byte[4096];
                            while (true) {
                                available = this.b.a.getInputStream().read(bArr);
                                if (available <= 0) {
                                    this.b.i = null;
                                    break;
                                }
                                CWrapperHttp.nativeConnectingCB(i, bArr, available, h);
                            }
                        default:
                            this.b.d = -1;
                            break;
                    }
                    this.b.e = 2;
                    if (this.b.c("Set-Cookie") != null) {
                        this.b.l.setCookie("Set-Cookie", this.b.c("Set-Cookie"));
                    }
                    CWrapperHttp.nativeConnectCB(i, this.b.d);
                } catch (Exception e) {
                    e.printStackTrace();
                    this.b.e = -1;
                    CWrapperHttp.nativeConnectCB(i, -20);
                }
            }
        }.start();
        return 0;
    }

    public int a(String str, String str2) {
        if (j() || this.e == -1) {
            return -1;
        }
        String toUpperCase = str.toUpperCase();
        if (toUpperCase.equals(a.GET.name())) {
            this.f = a.GET;
        } else if (toUpperCase.equals(a.HEAD.name())) {
            this.f = a.HEAD;
        } else if (!toUpperCase.equals(a.POST.name())) {
            return -9;
        } else {
            this.f = a.POST;
            this.h = str2;
        }
        return 0;
    }

    public boolean a(String str) {
        d(str);
        if (this.b == null) {
            this.e = -1;
            return false;
        }
        this.e = 0;
        return true;
    }

    public byte[] a() {
        return (this.e == 2 && this.i != null) ? this.i : null;
    }

    public int b(int i) {
        if (j()) {
            return -1;
        }
        this.g = i * UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS;
        return 0;
    }

    public int b(String str, String str2) {
        if (j() || this.e == -1) {
            return -1;
        }
        if (this.c.containsKey(str)) {
            this.c.remove(str);
        }
        this.c.put(str, str2);
        return 0;
    }

    public String b(String str) {
        return (j() || this.e == -1) ? null : (String) this.c.get(str);
    }

    public void b() {
        if (this.a != null && this.e != 3) {
            this.e = 3;
            this.a.disconnect();
        }
    }

    public String c() {
        return (j() || this.e == -1) ? null : this.f.name();
    }

    public String c(String str) {
        if (this.e != 2) {
            return null;
        }
        String str2 = i.a;
        List list = (List) this.a.getHeaderFields().get(str);
        if (list == null) {
            list = (List) this.a.getHeaderFields().get(str.toLowerCase());
            if (list == null) {
                return null;
            }
        }
        for (String str3 : r0) {
            str2 = str2 + str3 + ";";
        }
        return str2.lastIndexOf(";") > 0 ? str2.substring(0, str2.lastIndexOf(";")) : str2;
    }

    public int d() {
        int i = -1;
        if (this.e == 2) {
            try {
                i = this.a.getResponseCode();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    public String e() {
        String str = null;
        if (this.e == 2) {
            try {
                str = this.a.getResponseMessage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public String f() {
        return this.e != 2 ? null : this.a.getContentEncoding();
    }

    public String g() {
        return this.e != 2 ? null : this.a.getContentType();
    }

    public int h() {
        return this.j;
    }

    public int i() {
        return this.k;
    }

    public boolean j() {
        return this.e == 1 || this.e == 2;
    }

    public int k() {
        return this.e;
    }

    public int l() {
        return this.g;
    }
}
