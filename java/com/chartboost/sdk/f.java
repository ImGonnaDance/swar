package com.chartboost.sdk;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.impl.bi;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class f {
    public static Handler a = CBUtility.e();
    protected List<b> b = new ArrayList();
    protected List<b> c = new ArrayList();
    protected com.chartboost.sdk.Libraries.e.a d;
    protected com.chartboost.sdk.Model.a e;
    protected com.chartboost.sdk.Libraries.f f;
    public Map<Integer, Runnable> g = Collections.synchronizedMap(new HashMap());
    protected boolean h = true;
    protected boolean i = true;
    private boolean j;
    private a k;

    public interface b {
        boolean a();
    }

    public abstract class a extends RelativeLayout {
        final /* synthetic */ f a;
        private boolean b = false;
        private int c = -1;
        private int d = -1;
        private com.chartboost.sdk.Libraries.f e = null;
        private int f = -1;
        private int g = -1;

        protected abstract void a(int i, int i2);

        public a(f fVar, Context context) {
            this.a = fVar;
            super(context);
            fVar.k = this;
            fVar.j = false;
            setFocusableInTouchMode(true);
            requestFocus();
        }

        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            this.f = w;
            this.g = h;
            if (this.c != -1 && this.d != -1) {
                a();
            }
        }

        private boolean b(int i, int i2) {
            boolean z = true;
            if (this.b) {
                return false;
            }
            com.chartboost.sdk.Libraries.f c = CBUtility.c();
            if (this.c == i && this.d == i2 && this.e == c) {
                return true;
            }
            this.b = true;
            try {
                if (this.a.h && c.b()) {
                    this.a.f = c;
                } else if (this.a.i && c.c()) {
                    this.a.f = c;
                }
                a(i, i2);
                post(new Runnable(this) {
                    final /* synthetic */ a a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.requestLayout();
                    }
                });
                this.c = i;
                this.d = i2;
                this.e = c;
            } catch (Throwable e) {
                CBLogging.b("CBViewProtocol", "Exception raised while layouting Subviews", e);
                z = false;
            }
            this.b = false;
            return z;
        }

        public final void a() {
            a(false);
        }

        public final void a(boolean z) {
            if (z) {
                this.e = null;
            }
            a((Activity) getContext());
        }

        public void b() {
        }

        public boolean a(Activity activity) {
            if (this.f == -1 || this.g == -1) {
                int width;
                int height;
                try {
                    width = getWidth();
                    height = getHeight();
                    if (width == 0 || height == 0) {
                        View findViewById = activity.getWindow().findViewById(16908290);
                        if (findViewById == null) {
                            findViewById = activity.getWindow().getDecorView();
                        }
                        width = findViewById.getWidth();
                        height = findViewById.getHeight();
                    }
                } catch (Exception e) {
                    height = 0;
                    width = 0;
                }
                if (width == 0 || r0 == 0) {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    width = displayMetrics.widthPixels;
                    height = displayMetrics.heightPixels;
                }
                this.f = width;
                this.g = height;
            }
            return b(this.f, this.g);
        }

        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            for (int i = 0; i < this.a.g.size(); i++) {
                f.a.removeCallbacks((Runnable) this.a.g.get(Integer.valueOf(i)));
            }
            this.a.g.clear();
        }

        public final void a(View view) {
            int i = SelectTarget.TARGETING_SUCCESS;
            if (SelectTarget.TARGETING_SUCCESS == getId()) {
                i = SelectTarget.TARGETING_FAILED;
            }
            int i2 = i;
            View findViewById = findViewById(i);
            while (findViewById != null) {
                i2++;
                findViewById = findViewById(i2);
            }
            view.setId(i2);
            view.setSaveEnabled(false);
        }

        protected boolean c() {
            return f.a(getContext());
        }
    }

    protected abstract a b(Context context);

    public static boolean a(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 4;
    }

    public f(com.chartboost.sdk.Model.a aVar) {
        this.e = aVar;
        this.k = null;
        this.f = CBUtility.c();
        this.j = false;
    }

    public com.chartboost.sdk.Libraries.f a() {
        return this.f;
    }

    public boolean a(com.chartboost.sdk.Libraries.e.a aVar) {
        this.d = aVar.a("assets");
        if (!this.d.b()) {
            return true;
        }
        a(CBImpressionError.INTERNAL);
        return false;
    }

    public void a(b bVar) {
        if (bVar.a()) {
            this.c.remove(bVar);
        }
        this.b.remove(bVar);
        if (this.b.isEmpty() && !b()) {
            a(CBImpressionError.INTERNAL);
        }
    }

    public boolean b() {
        if (this.c.isEmpty()) {
            i();
            return true;
        }
        CBLogging.d("CBViewProtocol", "not completed loading assets for impression");
        return false;
    }

    public CBImpressionError c() {
        Activity b = Chartboost.b();
        if (b == null) {
            this.k = null;
            return CBImpressionError.NO_HOST_ACTIVITY;
        } else if (!this.i && !this.h) {
            return CBImpressionError.WRONG_ORIENTATION;
        } else {
            this.f = CBUtility.c();
            if ((this.f.c() && !this.i) || (this.f.b() && !this.h)) {
                this.f = this.f.a();
            }
            this.k = b((Context) b);
            if (this.k.a(b)) {
                return null;
            }
            this.k = null;
            return CBImpressionError.INTERNAL;
        }
    }

    public void d() {
        f();
        for (int i = 0; i < this.g.size(); i++) {
            a.removeCallbacks((Runnable) this.g.get(Integer.valueOf(i)));
        }
        this.g.clear();
    }

    public a e() {
        return this.k;
    }

    public void f() {
        if (this.k != null) {
            this.k.b();
        }
        this.k = null;
    }

    public com.chartboost.sdk.Libraries.e.a g() {
        return this.d;
    }

    public void b(b bVar) {
        this.b.add(bVar);
        this.c.add(bVar);
    }

    protected void a(CBImpressionError cBImpressionError) {
        this.e.a(cBImpressionError);
    }

    protected void h() {
        if (!this.j) {
            this.j = true;
            this.e.b();
        }
    }

    protected void i() {
        this.e.c();
    }

    public boolean a(String str, com.chartboost.sdk.Libraries.e.a aVar) {
        return this.e.a(str, aVar);
    }

    public void a(boolean z, View view) {
        a(z, view, true);
    }

    public void a(final boolean z, final View view, boolean z2) {
        int i = 8;
        if (((z && view.getVisibility() == 0) || (!z && view.getVisibility() == 8)) && this.g.get(Integer.valueOf(view.hashCode())) == null) {
            return;
        }
        if (z2) {
            Runnable anonymousClass1 = new Runnable(this) {
                final /* synthetic */ f c;

                public void run() {
                    if (!z) {
                        view.setVisibility(8);
                        view.setClickable(false);
                    }
                    this.c.g.remove(Integer.valueOf(view.hashCode()));
                }
            };
            bi.a(z, view, 510);
            a(view, anonymousClass1, 510);
            return;
        }
        if (z) {
            i = 0;
        }
        view.setVisibility(i);
        view.setClickable(z);
    }

    protected void a(View view, Runnable runnable, long j) {
        Runnable runnable2 = (Runnable) this.g.get(Integer.valueOf(view.hashCode()));
        if (runnable2 != null) {
            a.removeCallbacks(runnable2);
        }
        this.g.put(Integer.valueOf(view.hashCode()), runnable);
        a.postDelayed(runnable, j);
    }

    public static int a(String str) {
        int i = 0;
        if (str != null) {
            if (!str.startsWith("#")) {
                try {
                    i = Color.parseColor(str);
                } catch (IllegalArgumentException e) {
                    str = "#" + str;
                }
            }
            if (str.length() == 4 || str.length() == 5) {
                StringBuilder stringBuilder = new StringBuilder((str.length() * 2) + 1);
                stringBuilder.append("#");
                for (int i2 = i; i2 < str.length() - 1; i2++) {
                    stringBuilder.append(str.charAt(i2 + 1));
                    stringBuilder.append(str.charAt(i2 + 1));
                }
                str = stringBuilder.toString();
            }
            try {
                i = Color.parseColor(str);
            } catch (Throwable e2) {
                CBLogging.d("CBViewProtocol", "error parsing color " + str, e2);
            }
        }
        return i;
    }

    public boolean j() {
        return false;
    }

    public void k() {
    }

    public void l() {
    }
}
