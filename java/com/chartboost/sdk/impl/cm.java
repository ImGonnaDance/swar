package com.chartboost.sdk.impl;

import com.com2us.module.manager.ModuleConfig;
import com.facebook.internal.NativeProtocol;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;

public class cm implements cj {
    protected cq a;

    public byte[] a(ck ckVar) {
        cq cpVar = new cp();
        a(cpVar);
        b(ckVar);
        a();
        return cpVar.c();
    }

    public void a(cq cqVar) {
        if (this.a != null) {
            throw new IllegalStateException("in the middle of something");
        }
        this.a = cqVar;
    }

    public void a() {
        this.a = null;
    }

    protected boolean a(String str, ck ckVar) {
        return false;
    }

    protected boolean a(String str, Object obj) {
        return false;
    }

    public int b(ck ckVar) {
        return b(null, ckVar);
    }

    protected int b(String str, ck ckVar) {
        if (ckVar == null) {
            throw new NullPointerException("can't save a null object");
        }
        byte b;
        int a = this.a.a();
        if (ckVar instanceof List) {
            b = (byte) 4;
        } else {
            b = (byte) 3;
        }
        if (a(str, ckVar)) {
            return this.a.a() - a;
        }
        if (str != null) {
            a(b, str);
        }
        int a2 = this.a.a();
        this.a.c(0);
        List list = null;
        int i = (b == (byte) 3 && str == null) ? 1 : 0;
        if (b == (byte) 3) {
            if (i != 0 && ckVar.b("_id")) {
                b("_id", ckVar.a("_id"));
            }
            Object a3 = ckVar.a("_transientFields");
            if (a3 instanceof List) {
                list = (List) a3;
            }
        }
        if (ckVar instanceof Map) {
            for (Entry entry : ((Map) ckVar).entrySet()) {
                if ((i == 0 || !((String) entry.getKey()).equals("_id")) && (r2 == null || !r2.contains(entry.getKey()))) {
                    b((String) entry.getKey(), entry.getValue());
                }
            }
        } else {
            for (String str2 : ckVar.keySet()) {
                if ((i == 0 || !str2.equals("_id")) && (r2 == null || !r2.contains(str2))) {
                    b(str2, ckVar.a(str2));
                }
            }
        }
        this.a.write(0);
        this.a.a(a2, this.a.a() - a2);
        return this.a.a() - a;
    }

    protected void b(String str, Object obj) {
        if (!str.equals("_transientFields")) {
            if (str.equals("$where") && (obj instanceof String)) {
                a((byte) 13, str);
                b(obj.toString());
                return;
            }
            Object a = ch.a(obj);
            if (a == null) {
                a(str);
            } else if (a instanceof Date) {
                a(str, (Date) a);
            } else if (a instanceof Number) {
                a(str, (Number) a);
            } else if (a instanceof Character) {
                a(str, a.toString());
            } else if (a instanceof String) {
                a(str, a.toString());
            } else if (a instanceof cz) {
                a(str, (cz) a);
            } else if (a instanceof ck) {
                b(str, (ck) a);
            } else if (a instanceof Boolean) {
                a(str, (Boolean) a);
            } else if (a instanceof Pattern) {
                a(str, (Pattern) a);
            } else if (a instanceof Map) {
                a(str, (Map) a);
            } else if (a instanceof Iterable) {
                a(str, (Iterable) a);
            } else if (a instanceof byte[]) {
                a(str, (byte[]) a);
            } else if (a instanceof cu) {
                a(str, (cu) a);
            } else if (a instanceof UUID) {
                a(str, (UUID) a);
            } else if (a.getClass().isArray()) {
                c(str, a);
            } else if (a instanceof da) {
                a(str, (da) a);
            } else if (a instanceof ct) {
                a(str, (ct) a);
            } else if (a instanceof cw) {
                a(str, (cw) a);
            } else if (a instanceof cv) {
                a(str, (cv) a);
            } else if (a instanceof bw) {
                ck cnVar = new cn();
                cnVar.a("$ref", ((bw) a).b());
                cnVar.a("$id", ((bw) a).a());
                b(str, cnVar);
            } else if (a instanceof cy) {
                d(str);
            } else if (a instanceof cx) {
                e(str);
            } else if (!a(str, a)) {
                throw new IllegalArgumentException("can't serialize " + a.getClass());
            }
        }
    }

    private void c(String str, Object obj) {
        a((byte) 4, str);
        int a = this.a.a();
        this.a.c(0);
        int length = Array.getLength(obj);
        for (int i = 0; i < length; i++) {
            b(String.valueOf(i), Array.get(obj, i));
        }
        this.a.write(0);
        this.a.a(a, this.a.a() - a);
    }

    private void a(String str, Iterable iterable) {
        a((byte) 4, str);
        int a = this.a.a();
        this.a.c(0);
        int i = 0;
        for (Object b : iterable) {
            b(String.valueOf(i), b);
            i++;
        }
        this.a.write(0);
        this.a.a(a, this.a.a() - a);
    }

    private void a(String str, Map map) {
        a((byte) 3, str);
        int a = this.a.a();
        this.a.c(0);
        for (Entry entry : map.entrySet()) {
            b(entry.getKey().toString(), entry.getValue());
        }
        this.a.write(0);
        this.a.a(a, this.a.a() - a);
    }

    protected void a(String str) {
        a((byte) 10, str);
    }

    protected void a(String str, ct ctVar) {
        a((byte) 17, str);
        this.a.c(ctVar.b());
        this.a.c(ctVar.a());
    }

    protected void a(String str, cw cwVar) {
        a((byte) 15, str);
        int a = this.a.a();
        this.a.c(0);
        b(cwVar.a());
        b(cwVar.b());
        this.a.a(a, this.a.a() - a);
    }

    protected void a(String str, cv cvVar) {
        a((byte) 13, str);
        this.a.a();
        b(cvVar.a());
    }

    protected void a(String str, Boolean bool) {
        a((byte) 8, str);
        this.a.write(bool.booleanValue() ? 1 : 0);
    }

    protected void a(String str, Date date) {
        a((byte) 9, str);
        this.a.a(date.getTime());
    }

    protected void a(String str, Number number) {
        if ((number instanceof Integer) || (number instanceof Short) || (number instanceof Byte) || (number instanceof AtomicInteger)) {
            a((byte) 16, str);
            this.a.c(number.intValue());
        } else if ((number instanceof Long) || (number instanceof AtomicLong)) {
            a((byte) 18, str);
            this.a.a(number.longValue());
        } else if ((number instanceof Float) || (number instanceof Double)) {
            a((byte) 1, str);
            this.a.a(number.doubleValue());
        } else {
            throw new IllegalArgumentException("can't serialize " + number.getClass());
        }
    }

    protected void a(String str, byte[] bArr) {
        a(str, 0, bArr);
    }

    protected void a(String str, cu cuVar) {
        a(str, cuVar.a(), cuVar.b());
    }

    private void a(String str, int i, byte[] bArr) {
        a((byte) 5, str);
        int length = bArr.length;
        if (i == 2) {
            length += 4;
        }
        this.a.c(length);
        this.a.write(i);
        if (i == 2) {
            this.a.c(length - 4);
        }
        length = this.a.a();
        this.a.write(bArr);
        cb.a(this.a.a() - length, bArr.length);
    }

    protected void a(String str, UUID uuid) {
        a((byte) 5, str);
        this.a.c(16);
        this.a.write(3);
        this.a.a(uuid.getMostSignificantBits());
        this.a.a(uuid.getLeastSignificantBits());
    }

    protected void a(String str, da daVar) {
        a(str, daVar.a(), (byte) 14);
    }

    protected void a(String str, String str2) {
        a(str, str2, (byte) 2);
    }

    private void a(String str, String str2, byte b) {
        a(b, str);
        b(str2);
    }

    protected void a(String str, cz czVar) {
        a((byte) 7, str);
        this.a.d(czVar.c());
        this.a.d(czVar.d());
        this.a.d(czVar.e());
    }

    private void a(String str, Pattern pattern) {
        a((byte) 11, str);
        c(pattern.pattern());
        c(ch.a(pattern.flags()));
    }

    private void d(String str) {
        a((byte) -1, str);
    }

    private void e(String str) {
        a(Byte.MAX_VALUE, str);
    }

    protected void a(byte b, String str) {
        this.a.write((int) b);
        c(str);
    }

    protected void b(String str) {
        int a = this.a.a();
        this.a.c(0);
        this.a.a(a, c(str));
    }

    protected int c(String str) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int codePointAt = Character.codePointAt(str, i);
            if (codePointAt < ModuleConfig.ACTIVEUSER_MODULE) {
                this.a.write((byte) codePointAt);
                i2++;
            } else if (codePointAt < ModuleConfig.OFFERWALL_MODULE) {
                this.a.write((byte) ((codePointAt >> 6) + 192));
                this.a.write((byte) ((codePointAt & 63) + ModuleConfig.ACTIVEUSER_MODULE));
                i2 += 2;
            } else if (codePointAt < NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) {
                this.a.write((byte) ((codePointAt >> 12) + 224));
                this.a.write((byte) (((codePointAt >> 6) & 63) + ModuleConfig.ACTIVEUSER_MODULE));
                this.a.write((byte) ((codePointAt & 63) + ModuleConfig.ACTIVEUSER_MODULE));
                i2 += 3;
            } else {
                this.a.write((byte) ((codePointAt >> 18) + 240));
                this.a.write((byte) (((codePointAt >> 12) & 63) + ModuleConfig.ACTIVEUSER_MODULE));
                this.a.write((byte) (((codePointAt >> 6) & 63) + ModuleConfig.ACTIVEUSER_MODULE));
                this.a.write((byte) ((codePointAt & 63) + ModuleConfig.ACTIVEUSER_MODULE));
                i2 += 4;
            }
            i += Character.charCount(codePointAt);
        }
        this.a.write(0);
        return i2 + 1;
    }
}
