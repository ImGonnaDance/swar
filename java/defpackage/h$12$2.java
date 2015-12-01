package defpackage;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import com.com2us.wrapper.ui.CTextInput.b;

class h$12$2 implements OnFocusChangeListener {
    final /* synthetic */ h$12 a;

    h$12$2(h$12 h_12) {
        this.a = h_12;
    }

    public void onFocusChange(View view, boolean z) {
        int i = 1;
        this.a.b.p = z;
        b f = this.a.b.v;
        int e = this.a.b.a;
        if (z) {
            i = 0;
        }
        f.a(e, i);
    }
}
