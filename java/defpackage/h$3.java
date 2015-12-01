package defpackage;

class h$3 implements Runnable {
    final /* synthetic */ int a;
    final /* synthetic */ h b;

    h$3(h hVar, int i) {
        this.b = hVar;
        this.a = i;
    }

    public void run() {
        this.b.d.setPadding(0, 0, 0, 0);
        this.b.d.setBackgroundColor(this.a);
    }
}
