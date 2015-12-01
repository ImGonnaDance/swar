package defpackage;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;

class i$2 implements OnKeyListener {
    final /* synthetic */ i a;

    i$2(i iVar) {
        this.a = iVar;
    }

    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (keyEvent.getAction() == 0) {
            if (i == 66) {
                this.a.i.a(this.a.e.getText().toString());
                return true;
            } else if (4 == i) {
                this.a.i.a(this.a.h);
            }
        }
        return false;
    }
}
