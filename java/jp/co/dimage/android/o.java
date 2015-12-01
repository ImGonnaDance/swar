package jp.co.dimage.android;

import android.content.Context;
import android.os.AsyncTask;
import jp.co.cyberz.fox.a.a.d;

public final class o {
    public static final int a = 1;
    public static final int b = 2;
    public static final int c = 3;
    public static final int d = 4;
    private g e;
    private a f;
    private Context g;

    public interface c {
        void a();
    }

    public interface b {
        void a(a aVar);
    }

    class a extends AsyncTask {
        final /* synthetic */ o a;
        private int b = 5;
        private b c = null;
        private c d = null;
        private boolean e = false;
        private boolean f = false;
        private boolean g = false;
        private boolean h = false;
        private boolean i = false;
        private boolean j = false;
        private boolean k = false;
        private boolean l = false;

        public a(o oVar, int i) {
            this.a = oVar;
            if (i > 0) {
                this.b = i;
            }
        }

        private void a(Void voidR) {
            super.onPostExecute(voidR);
            if (this.c != null) {
                this.c.a(this.a.f);
            }
            if (this.d != null) {
                this.d.a();
            }
        }

        private Void e() {
            if (this.b > 0) {
                int i = 0;
                while (i < this.b) {
                    try {
                        if (!this.e) {
                            this.i = true;
                        }
                        if (!this.f) {
                            this.j = true;
                        }
                        if (!(this.g && b.b())) {
                            this.k = true;
                        }
                        if (!this.h) {
                            this.l = true;
                        }
                        if (!this.i) {
                            this.i = o.a(this.a);
                        }
                        if (!this.j) {
                            this.j = o.b(this.a);
                        }
                        if (!this.k) {
                            o oVar = this.a;
                            this.k = o.a();
                        }
                        if (!this.l) {
                            this.l = o.c(this.a);
                        }
                        Object obj = (this.i && this.j && this.k && this.l) ? o.a : null;
                        if (obj != null) {
                            break;
                        }
                        Thread.sleep(1000);
                        i += o.a;
                    } catch (Exception e) {
                        i += o.a;
                    }
                }
            }
            return null;
        }

        private boolean f() {
            if (!this.e) {
                this.i = true;
            }
            if (!this.f) {
                this.j = true;
            }
            if (!(this.g && b.b())) {
                this.k = true;
            }
            if (!this.h) {
                this.l = true;
            }
            if (!this.i) {
                this.i = o.a(this.a);
            }
            if (!this.j) {
                this.j = o.b(this.a);
            }
            if (!this.k) {
                o oVar = this.a;
                this.k = o.a();
            }
            if (!this.l) {
                this.l = o.c(this.a);
            }
            return this.i && this.j && this.k && this.l;
        }

        public final void a() {
            this.e = true;
        }

        public final void a(b bVar) {
            this.c = bVar;
        }

        public final void a(c cVar) {
            this.d = cVar;
        }

        public final void b() {
            this.f = true;
        }

        public final void c() {
            this.g = true;
        }

        public final void d() {
            this.h = true;
        }

        protected final /* synthetic */ Object doInBackground(Object... objArr) {
            return e();
        }

        protected final /* synthetic */ void onPostExecute(Object obj) {
            super.onPostExecute((Void) obj);
            if (this.c != null) {
                this.c.a(this.a.f);
            }
            if (this.d != null) {
                this.d.a();
            }
        }
    }

    public o(Context context) {
        this(context, null, null);
    }

    public o(Context context, g gVar, a aVar) {
        this.g = context;
        this.e = gVar;
        this.f = aVar;
    }

    static /* synthetic */ boolean a() {
        return !p.a(b.a());
    }

    static /* synthetic */ boolean a(o oVar) {
        return !p.a(d.a(oVar.g));
    }

    private boolean b() {
        return !p.a(d.a(this.g));
    }

    static /* synthetic */ boolean b(o oVar) {
        return oVar.e.b();
    }

    private boolean c() {
        return this.e.b();
    }

    static /* synthetic */ boolean c(o oVar) {
        return !p.a(p.a(oVar.g));
    }

    private static boolean d() {
        return !p.a(b.a());
    }

    private boolean e() {
        return !p.a(p.a(this.g));
    }

    public final void a(Integer num, b bVar) {
        if (num == null) {
            num = Integer.valueOf(0);
        }
        AsyncTask aVar = new a(this, num.intValue());
        aVar.a(bVar);
        if (this.e.h()) {
            aVar.a();
        }
        if (this.e.g()) {
            aVar.b();
        }
        if (this.e.f()) {
            aVar.c();
        }
        if (this.e.e()) {
            aVar.d();
        }
        d dVar = new d();
        d.a(aVar, null);
    }

    public final void a(boolean z, c cVar) {
        AsyncTask aVar = new a(this, Integer.valueOf(0).intValue());
        aVar.a(cVar);
        aVar.c();
        if (z) {
            aVar.d();
        }
        d dVar = new d();
        d.a(aVar, null);
    }
}
