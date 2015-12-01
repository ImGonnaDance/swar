package defpackage;

import java.util.HashMap;

public class d {
    private static HashMap<d$b, d$a> a = new HashMap();
    private static d$b b = d$b.INITIAL;

    public static void a() {
        a.clear();
    }

    public static void a(d$b d_b) {
        b = d_b;
        ((d$a) a.get(b)).a();
    }

    public static d$b b() {
        return b;
    }
}
