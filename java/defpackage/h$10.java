package defpackage;

class h$10 implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ h b;

    h$10(h hVar, String str) {
        this.b = hVar;
        this.a = str;
    }

    public void run() {
        this.b.d.setHint(this.a);
    }
}
