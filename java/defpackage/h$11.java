package defpackage;

class h$11 implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ h b;

    h$11(h hVar, boolean z) {
        this.b = hVar;
        this.a = z;
    }

    public void run() {
        if (this.a) {
            this.b.d.setVisibility(0);
        } else {
            this.b.d.setVisibility(4);
        }
    }
}
