package defpackage;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

class h$12$3 implements OnEditorActionListener {
    final /* synthetic */ h$12 a;

    h$12$3(h$12 h_12) {
        this.a = h_12;
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        if (i != 6 && i != 2 && i != 4 && i != 5) {
            return false;
        }
        this.a.b.t();
        return true;
    }
}
