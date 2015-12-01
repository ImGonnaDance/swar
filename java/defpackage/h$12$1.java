package defpackage;

import android.text.Editable;
import android.text.TextWatcher;

class h$12$1 implements TextWatcher {
    final /* synthetic */ h$12 a;

    h$12$1(h$12 h_12) {
        this.a = h_12;
    }

    public void afterTextChanged(Editable editable) {
        String b;
        String obj = this.a.b.d.getText().toString();
        try {
            b = this.a.b.b();
        } catch (Exception e) {
            e.printStackTrace();
            b = null;
        }
        if (b != null && !b.equals(obj)) {
            this.a.b.f = obj;
            this.a.b.v.a(this.a.b.a, 2);
        }
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
    }
}
