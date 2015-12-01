package defpackage;

class h$15 implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ h b;

    h$15(h hVar, String str) {
        this.b = hVar;
        this.a = str;
    }

    public void run() {
        this.b.d.setText(this.a);
        this.b.d.setSelection(this.a.length());
    }
}
