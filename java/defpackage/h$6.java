package defpackage;

import com.com2us.module.manager.ModuleConfig;

class h$6 implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ h b;

    h$6(h hVar, boolean z) {
        this.b = hVar;
        this.a = z;
    }

    public void run() {
        int inputType = this.b.d.getInputType();
        Object obj = (inputType & ModuleConfig.ACTIVEUSER_MODULE) == ModuleConfig.ACTIVEUSER_MODULE ? 1 : null;
        if (this.a && obj == null) {
            this.b.f(4);
            this.b.d.setInputType(this.b.d.getInputType() | ModuleConfig.ACTIVEUSER_MODULE);
        } else if (!this.a && obj == 1) {
            this.b.d.setInputType(inputType & -129);
        }
    }
}
