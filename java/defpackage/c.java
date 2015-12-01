package defpackage;

public class c {
    private c$a a = c$a.JOIN;
    private int b = 10;

    public boolean a() {
        if (this.a != c$a.JOIN) {
            return false;
        }
        this.a = c$a.START;
        return true;
    }

    public boolean b() {
        if (this.a == c$a.JOIN) {
            return false;
        }
        while (this.a != c$a.STOP) {
            try {
                Thread.sleep((long) this.b);
            } catch (Exception e) {
                try {
                    e.printStackTrace();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    return false;
                }
            }
        }
        this.a = c$a.JOIN;
        return true;
    }

    public boolean c() {
        synchronized (this.a) {
            if (this.a == c$a.START) {
                this.a = c$a.STOP;
                return true;
            }
            return false;
        }
    }
}
