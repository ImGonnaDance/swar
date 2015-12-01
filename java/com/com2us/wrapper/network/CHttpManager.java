package com.com2us.wrapper.network;

import android.app.Activity;
import com.com2us.wrapper.common.CCommonAPI;
import com.com2us.wrapper.kernel.CWrapper;
import java.util.HashMap;

public class CHttpManager extends CWrapper {
    public Activity a;
    private HashMap<String, String> b;
    private HashMap<Integer, CWrapperHttp> c;
    private int d;

    public CHttpManager(Activity activity) {
        this.d = 1;
        this.a = null;
        this.c = new HashMap(5);
        this.b = new HashMap(5);
        this.d = 1;
        this.a = activity;
    }

    public String getCookie(String str) {
        return (String) this.b.get(str);
    }

    public int netHttpCloseEx(int i) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        ((CWrapperHttp) this.c.get(Integer.valueOf(i))).b();
        this.c.remove(Integer.valueOf(i));
        return 0;
    }

    public int netHttpConnectEx(int i) {
        if (this.c.containsKey(Integer.valueOf(i))) {
            return ((CWrapperHttp) this.c.get(Integer.valueOf(i))).a(i);
        }
        CWrapperHttp.nativeConnectCB(i, -2);
        return -2;
    }

    public int netHttpGetCurrentLengthEx(int i) {
        return !this.c.containsKey(Integer.valueOf(i)) ? -2 : ((CWrapperHttp) this.c.get(Integer.valueOf(i))).i();
    }

    public int netHttpGetEncodingEx(int i, byte[] bArr, int i2) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        if (bArr == null) {
            return -9;
        }
        String f = ((CWrapperHttp) this.c.get(Integer.valueOf(i))).f();
        if (f == null) {
            return -1;
        }
        int length = f.getBytes().length;
        if (length >= i2) {
            return -18;
        }
        System.arraycopy(f.getBytes(), 0, bArr, 0, length);
        return length;
    }

    public int netHttpGetHeaderFieldEx(int i, String str, byte[] bArr, int i2) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        if (str == null || bArr == null) {
            return -9;
        }
        String c = ((CWrapperHttp) this.c.get(Integer.valueOf(i))).c(str);
        if (c == null) {
            return -1;
        }
        int length = c.getBytes().length;
        if (length >= i2) {
            return -18;
        }
        System.arraycopy(c.getBytes(), 0, bArr, 0, length);
        return length;
    }

    public int netHttpGetLengthEx(int i) {
        return !this.c.containsKey(Integer.valueOf(i)) ? -2 : ((CWrapperHttp) this.c.get(Integer.valueOf(i))).h();
    }

    public int netHttpGetProperty(int i) {
        return !this.c.containsKey(Integer.valueOf(i)) ? -2 : ((CWrapperHttp) this.c.get(Integer.valueOf(i))).l();
    }

    public int netHttpGetRequestMethodEx(int i, byte[] bArr, int i2) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        if (bArr == null) {
            return -9;
        }
        String c = ((CWrapperHttp) this.c.get(Integer.valueOf(i))).c();
        if (c == null) {
            return -1;
        }
        int length = c.getBytes().length;
        if (length >= i2) {
            return -18;
        }
        System.arraycopy(c.getBytes(), 0, bArr, 0, length);
        return length;
    }

    public int netHttpGetRequestPropertyEx(int i, String str, byte[] bArr, int i2) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        if (str == null || bArr == null) {
            return -9;
        }
        String b = ((CWrapperHttp) this.c.get(Integer.valueOf(i))).b(str);
        if (b == null) {
            return -1;
        }
        int length = b.getBytes().length;
        if (length >= i2) {
            return -18;
        }
        System.arraycopy(b.getBytes(), 0, bArr, 0, length);
        return length;
    }

    public int netHttpGetResponseBody(int i, byte[] bArr, int i2) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        if (bArr == null) {
            return -9;
        }
        CWrapperHttp cWrapperHttp = (CWrapperHttp) this.c.get(Integer.valueOf(i));
        if (cWrapperHttp.k() != 2) {
            return -1;
        }
        Object a = cWrapperHttp.a();
        if (a == null) {
            return -1;
        }
        int length = a.length;
        if (length >= i2) {
            return -18;
        }
        System.arraycopy(a, 0, bArr, 0, length);
        return length;
    }

    public int netHttpGetResponseBodyLength(int i) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        CWrapperHttp cWrapperHttp = (CWrapperHttp) this.c.get(Integer.valueOf(i));
        if (cWrapperHttp.k() != 2) {
            return -1;
        }
        byte[] a = cWrapperHttp.a();
        return a == null ? -1 : a.length == 0 ? 0 : a.length + 1;
    }

    public int netHttpGetResponseCodeEx(int i) {
        return !this.c.containsKey(Integer.valueOf(i)) ? -2 : ((CWrapperHttp) this.c.get(Integer.valueOf(i))).d();
    }

    public int netHttpGetResponseMessageEx(int i, byte[] bArr, int i2) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        if (bArr == null) {
            return -9;
        }
        String e = ((CWrapperHttp) this.c.get(Integer.valueOf(i))).e();
        if (e == null) {
            return -1;
        }
        int length = e.getBytes().length;
        if (length >= i2) {
            return -18;
        }
        System.arraycopy(e.getBytes(), 0, bArr, 0, length);
        return length;
    }

    public int netHttpGetTypeEx(int i, byte[] bArr, int i2) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        if (bArr == null) {
            return -9;
        }
        String g = ((CWrapperHttp) this.c.get(Integer.valueOf(i))).g();
        if (g == null) {
            return -1;
        }
        int length = g.getBytes().length;
        if (length >= i2) {
            return -18;
        }
        System.arraycopy(g.getBytes(), 0, bArr, 0, length);
        return length;
    }

    public int netHttpIsContains(int i) {
        return !this.c.containsKey(Integer.valueOf(i)) ? -2 : 0;
    }

    public int netHttpOpenEx(String str) {
        if (CCommonAPI.getActiveNetwork() == 1) {
            return -14;
        }
        CWrapperHttp cWrapperHttp = new CWrapperHttp(this);
        if (!cWrapperHttp.a(str)) {
            return -9;
        }
        if (this.c.containsKey(Integer.valueOf(this.d))) {
            this.d++;
        }
        this.c.put(Integer.valueOf(this.d), cWrapperHttp);
        int i = this.d;
        this.d = i + 1;
        return i;
    }

    public int netHttpSetProperty(int i, int i2) {
        if (!this.c.containsKey(Integer.valueOf(i))) {
            return -2;
        }
        if (i2 < 0) {
            return -9;
        }
        ((CWrapperHttp) this.c.get(Integer.valueOf(i))).b(i2);
        return 0;
    }

    public int netHttpSetRequestMethodEx(int i, String str, String str2) {
        return !this.c.containsKey(Integer.valueOf(i)) ? -2 : str == null ? -9 : ((CWrapperHttp) this.c.get(Integer.valueOf(i))).a(str, str2);
    }

    public int netHttpSetRequestPropertyEx(int i, String str, String str2) {
        return !this.c.containsKey(Integer.valueOf(i)) ? -2 : str == null ? -9 : ((CWrapperHttp) this.c.get(Integer.valueOf(i))).b(str, str2);
    }

    public void setCookie(String str, String str2) {
        if (this.b.containsKey(str)) {
            this.b.remove(str);
        }
        this.b.put(str, str2);
    }
}
