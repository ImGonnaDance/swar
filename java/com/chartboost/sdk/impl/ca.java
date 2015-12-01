package com.chartboost.sdk.impl;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SimpleTimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

public class ca {

    private static abstract class c extends bx {
        protected final cc a;

        c(cc ccVar) {
            this.a = ccVar;
        }
    }

    private static class a extends c {
        a(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            cv cvVar = (cv) obj;
            bt btVar = new bt();
            btVar.a("$code", cvVar.a());
            this.a.a(btVar, stringBuilder);
        }
    }

    private static class b extends c {
        b(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            cw cwVar = (cw) obj;
            bt btVar = new bt();
            btVar.a("$code", cwVar.a());
            btVar.a("$scope", cwVar.b());
            this.a.a(btVar, stringBuilder);
        }
    }

    private static class d extends c {
        d(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            stringBuilder.append("{ ");
            bv bvVar = (bv) obj;
            Object obj2 = 1;
            for (String str : bvVar.keySet()) {
                if (obj2 != null) {
                    obj2 = null;
                } else {
                    stringBuilder.append(" , ");
                }
                bz.a(stringBuilder, str);
                stringBuilder.append(" : ");
                this.a.a(bvVar.a(str), stringBuilder);
            }
            stringBuilder.append("}");
        }
    }

    private static class e extends c {
        e(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            bw bwVar = (bw) obj;
            bt btVar = new bt();
            btVar.a("$ref", bwVar.b());
            btVar.a("$id", bwVar.a());
            this.a.a(btVar, stringBuilder);
        }
    }

    private static class f extends c {
        f(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            Object obj2 = 1;
            stringBuilder.append("[ ");
            for (Object next : (Iterable) obj) {
                if (obj2 != null) {
                    obj2 = null;
                } else {
                    stringBuilder.append(" , ");
                }
                this.a.a(next, stringBuilder);
            }
            stringBuilder.append("]");
        }
    }

    private static class g extends c {
        g(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            ct ctVar = (ct) obj;
            bt btVar = new bt();
            btVar.a("$ts", Integer.valueOf(ctVar.a()));
            btVar.a("$inc", Integer.valueOf(ctVar.b()));
            this.a.a(btVar, stringBuilder);
        }
    }

    private static class h extends bx {
        private h() {
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            stringBuilder.append("<Binary Data>");
        }
    }

    private static class i extends c {
        i(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            Date date = (Date) obj;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simpleDateFormat.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
            this.a.a(new bt("$date", simpleDateFormat.format(date)), stringBuilder);
        }
    }

    private static class j extends c {
        j(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            stringBuilder.append("{ ");
            Object obj2 = 1;
            for (Entry entry : ((Map) obj).entrySet()) {
                if (obj2 != null) {
                    obj2 = null;
                } else {
                    stringBuilder.append(" , ");
                }
                bz.a(stringBuilder, entry.getKey().toString());
                stringBuilder.append(" : ");
                this.a.a(entry.getValue(), stringBuilder);
            }
            stringBuilder.append("}");
        }
    }

    private static class k extends c {
        k(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            this.a.a(new bt("$maxKey", Integer.valueOf(1)), stringBuilder);
        }
    }

    private static class l extends c {
        l(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            this.a.a(new bt("$minKey", Integer.valueOf(1)), stringBuilder);
        }
    }

    private static class m extends c {
        m(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            stringBuilder.append("[ ");
            for (int i = 0; i < Array.getLength(obj); i++) {
                if (i > 0) {
                    stringBuilder.append(" , ");
                }
                this.a.a(Array.get(obj, i), stringBuilder);
            }
            stringBuilder.append("]");
        }
    }

    private static class n extends c {
        n(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            this.a.a(new bt("$oid", obj.toString()), stringBuilder);
        }
    }

    private static class o extends c {
        o(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            bv btVar = new bt();
            btVar.a("$regex", obj.toString());
            if (((Pattern) obj).flags() != 0) {
                btVar.a("$options", ch.a(((Pattern) obj).flags()));
            }
            this.a.a(btVar, stringBuilder);
        }
    }

    private static class p extends bx {
        private p() {
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            bz.a(stringBuilder, (String) obj);
        }
    }

    private static class q extends bx {
        private q() {
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            stringBuilder.append(obj.toString());
        }
    }

    private static class r extends c {
        r(cc ccVar) {
            super(ccVar);
        }

        public void a(Object obj, StringBuilder stringBuilder) {
            UUID uuid = (UUID) obj;
            bt btVar = new bt();
            btVar.a("$uuid", uuid.toString());
            this.a.a(btVar, stringBuilder);
        }
    }

    public static cc a() {
        cc b = b();
        b.a(Date.class, new i(b));
        b.a(ct.class, new g(b));
        b.a(cu.class, new h());
        b.a(byte[].class, new h());
        return b;
    }

    static by b() {
        by byVar = new by();
        byVar.a(Object[].class, new m(byVar));
        byVar.a(Boolean.class, new q());
        byVar.a(cv.class, new a(byVar));
        byVar.a(cw.class, new b(byVar));
        byVar.a(bv.class, new d(byVar));
        byVar.a(bw.class, new e(byVar));
        byVar.a(Iterable.class, new f(byVar));
        byVar.a(Map.class, new j(byVar));
        byVar.a(cx.class, new k(byVar));
        byVar.a(cy.class, new l(byVar));
        byVar.a(Number.class, new q());
        byVar.a(cz.class, new n(byVar));
        byVar.a(Pattern.class, new o(byVar));
        byVar.a(String.class, new p());
        byVar.a(UUID.class, new r(byVar));
        return byVar;
    }
}
