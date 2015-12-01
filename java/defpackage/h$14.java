package defpackage;

import com.com2us.wrapper.ui.CSoftKeyboard;

class h$14 implements Runnable {
    final /* synthetic */ h a;

    h$14(h hVar) {
        this.a = hVar;
    }

    public void run() {
        if (this.a.p) {
            this.a.p = false;
            this.a.d.clearFocus();
            CSoftKeyboard.getInstance().hideKeyboardForcedly();
        }
        this.a.d.setOnFocusChangeListener(null);
        this.a.d.removeTextChangedListener(this.a.e);
        this.a.d.setOnEditorActionListener(null);
        this.a.c.removeView(this.a.d);
        this.a.b.removeView(this.a.c);
    }
}
