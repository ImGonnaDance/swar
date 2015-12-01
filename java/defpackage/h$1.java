package defpackage;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.ui.CCustomEditText;

class h$1 implements Runnable {
    final /* synthetic */ int[] a;
    final /* synthetic */ h b;

    h$1(h hVar, int[] iArr) {
        this.b = hVar;
        this.a = iArr;
    }

    public void run() {
        Context activity = CFunction.getActivity();
        this.b.c = new FrameLayout(activity);
        this.b.c.setLayoutParams(new LayoutParams(-1, -1));
        this.b.d = new CCustomEditText(activity);
        this.b.d(false);
        this.b.c.addView(this.b.d);
        CFunction.setControlByPX(this.b.c, this.b.d, this.a[0], this.a[1], this.a[2], this.a[3]);
        this.b.b.addView(this.b.c);
    }
}
