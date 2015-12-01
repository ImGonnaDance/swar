package defpackage;

import com.com2us.wrapper.ui.CSoftKeyboard;

class h$7 implements Runnable {
    final /* synthetic */ boolean a;
    final /* synthetic */ h b;

    h$7(h hVar, boolean z) {
        this.b = hVar;
        this.a = z;
    }

    public void run() {
        if (this.a && !this.b.d.hasFocus()) {
            this.b.d.requestFocus();
            CSoftKeyboard.getInstance().showKeyboard(this.b.d);
        } else if (!this.a && this.b.d.hasFocus()) {
            this.b.d.clearFocus();
            CSoftKeyboard.getInstance().hideKeyboard(this.b.d);
        }
    }
}
