package jp.co.cyberz.fox.a.a;

import java.util.List;

final class g implements Runnable {
    final /* synthetic */ e a;
    private final /* synthetic */ List b;

    g(e eVar, List list) {
        this.a = eVar;
        this.b = list;
    }

    public final void run() {
        List list = this.b;
        e eVar = this.a;
        new b(list, e.c(this.b)).execute(new String[]{e.j});
    }
}
