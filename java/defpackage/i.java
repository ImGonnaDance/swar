package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.function.CResource;
import com.com2us.wrapper.ui.CCustomEditText;
import com.com2us.wrapper.ui.CSoftKeyboard;
import com.com2us.wrapper.ui.CUserInput.b;

public class i {
    private FrameLayout a = null;
    private LinearLayout b = null;
    private LinearLayout c = null;
    private TextView d = null;
    private EditText e = null;
    private Button f = null;
    private Button g = null;
    private String h = null;
    private b i = null;
    private boolean j = false;

    public i(FrameLayout frameLayout, b bVar) {
        this.a = frameLayout;
        this.i = bVar;
    }

    private void c() {
        int i;
        Context activity = CFunction.getActivity();
        Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
        if (CFunction.getSystemVersionCode() >= 13) {
            Point point = new Point();
            defaultDisplay.getSize(point);
            i = point.x;
        } else {
            i = defaultDisplay.getWidth();
        }
        int i2 = (i * 64) / 100;
        i -= CFunction.convertDPtoPX(100);
        if (i2 < i) {
            i = i2;
        }
        this.b = new LinearLayout(activity);
        this.b.setLayoutParams(new LayoutParams(-1, -1));
        this.b.setOrientation(1);
        this.b.setPadding(CFunction.convertDPtoPX(50), CFunction.convertDPtoPX(110), CFunction.convertDPtoPX(50), CFunction.convertDPtoPX(50));
        this.b.setGravity(1);
        this.b.setBackgroundColor(-1157627904);
        this.b.setVisibility(8);
        this.b.setOnTouchListener(new i$1(this));
        this.d = new TextView(activity);
        this.d.setLayoutParams(new LayoutParams(-1, -2));
        this.d.setText(CResource.R("R.string.wrapper_userinput_textview"));
        this.d.setVisibility(0);
        this.d.setGravity(17);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.width = i;
        this.e = new CCustomEditText(activity);
        this.e.setLayoutParams(layoutParams);
        this.c = new LinearLayout(activity);
        this.c.setLayoutParams(new LayoutParams(-1, -2));
        this.c.setOrientation(0);
        this.c.setGravity(1);
        layoutParams = new LayoutParams(-2, -2);
        layoutParams.width = i / 2;
        this.g = new Button(activity);
        this.g.setLayoutParams(layoutParams);
        this.g.setText(CResource.R("R.string.wrapper_userinput_cancel"));
        this.f = new Button(activity);
        this.f.setLayoutParams(layoutParams);
        this.f.setText(CResource.R("R.string.wrapper_userinput_confirm"));
        this.c.addView(this.g);
        this.c.addView(this.f);
        this.b.addView(this.d);
        this.b.addView(this.e);
        this.b.addView(this.c);
        this.a.addView(this.b);
        this.e.setOnKeyListener(new i$2(this));
        this.g.setOnClickListener(new i$3(this));
        this.f.setOnClickListener(new i$4(this));
        this.j = true;
    }

    private void d() {
        this.c.removeView(this.g);
        this.c.removeView(this.f);
        this.b.removeView(this.d);
        this.b.removeView(this.e);
        this.b.removeView(this.c);
        this.a.removeView(this.b);
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.j = false;
    }

    public void a() {
        if (this.j) {
            d();
        }
    }

    public void a(int i, String str) {
        if (!this.j) {
            c();
        }
        if (this.b.getVisibility() == 8) {
            this.h = str;
            this.e.setInputType(i);
            this.e.setText(this.h);
            this.e.setSelection(this.h.length());
            this.e.requestFocus();
            this.b.setVisibility(0);
            CSoftKeyboard.getInstance().showKeyboard(this.e);
        }
    }

    public void b() {
        if (this.j && this.b.getVisibility() == 0) {
            this.e.clearFocus();
            this.b.setVisibility(8);
            CSoftKeyboard.getInstance().hideKeyboard(this.e);
        }
    }
}
