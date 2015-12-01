package com.chartboost.sdk.impl;

import android.os.Process;
import com.chartboost.sdk.impl.b.a;
import java.util.concurrent.BlockingQueue;

public class c extends Thread {
    private static final boolean a = t.b;
    private final BlockingQueue<l<?>> b;
    private final BlockingQueue<l<?>> c;
    private final b d;
    private final o e;
    private volatile boolean f = false;

    public c(BlockingQueue<l<?>> blockingQueue, BlockingQueue<l<?>> blockingQueue2, b bVar, o oVar) {
        this.b = blockingQueue;
        this.c = blockingQueue2;
        this.d = bVar;
        this.e = oVar;
    }

    public void a() {
        this.f = true;
        interrupt();
    }

    public void run() {
        if (a) {
            t.a("start new dispatcher", new Object[0]);
        }
        Process.setThreadPriority(10);
        this.d.a();
        while (true) {
            try {
                final l lVar = (l) this.b.take();
                lVar.a("cache-queue-take");
                if (lVar.h()) {
                    lVar.b("cache-discard-canceled");
                } else {
                    a a = this.d.a(lVar.e());
                    if (a == null) {
                        lVar.a("cache-miss");
                        this.c.put(lVar);
                    } else if (a.a()) {
                        lVar.a("cache-hit-expired");
                        lVar.a(a);
                        this.c.put(lVar);
                    } else {
                        lVar.a("cache-hit");
                        n a2 = lVar.a(new i(a.a, a.f));
                        lVar.a("cache-hit-parsed");
                        if (a.b()) {
                            lVar.a("cache-hit-refresh-needed");
                            lVar.a(a);
                            a2.d = true;
                            this.e.a(lVar, a2, new Runnable(this) {
                                final /* synthetic */ c a;

                                public void run() {
                                    try {
                                        this.a.c.put(lVar);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            });
                        } else {
                            this.e.a(lVar, a2);
                        }
                    }
                }
            } catch (InterruptedException e) {
                if (this.f) {
                    return;
                }
            }
        }
    }
}
