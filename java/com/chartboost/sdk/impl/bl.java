package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.j;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public abstract class bl extends RelativeLayout {
    private static Rect d = new Rect();
    private a a;
    private boolean b = false;
    protected Button c = null;
    private boolean e = true;

    public class a extends bk {
        final /* synthetic */ bl b;

        public a(bl blVar, Context context) {
            this.b = blVar;
            super(context);
            blVar.b = false;
        }

        protected void a(boolean z) {
            if (this.b.e && z) {
                if (!this.b.b) {
                    if (getDrawable() != null) {
                        getDrawable().setColorFilter(1996488704, Mode.SRC_ATOP);
                    } else if (getBackground() != null) {
                        getBackground().setColorFilter(1996488704, Mode.SRC_ATOP);
                    }
                    invalidate();
                    this.b.b = true;
                }
            } else if (this.b.b) {
                if (getDrawable() != null) {
                    getDrawable().clearColorFilter();
                } else if (getBackground() != null) {
                    getBackground().clearColorFilter();
                }
                invalidate();
                this.b.b = false;
            }
        }

        public void a(j jVar, LayoutParams layoutParams) {
            a(jVar);
            layoutParams.width = jVar.h();
            layoutParams.height = jVar.i();
        }
    }

    protected abstract void a(MotionEvent motionEvent);

    public bl(Context context) {
        super(context);
        b();
    }

    private void b() {
        this.a = new a(this, getContext());
        this.a.setOnTouchListener(new OnTouchListener(this) {
            final /* synthetic */ bl a;

            {
                this.a = r1;
            }

            public boolean onTouch(View v, MotionEvent event) {
                boolean a = bl.b(v, event);
                switch (event.getActionMasked()) {
                    case g.a /*0*/:
                        this.a.a.a(a);
                        return a;
                    case o.a /*1*/:
                        if (this.a.getVisibility() == 0 && this.a.isEnabled() && a) {
                            this.a.a(event);
                        }
                        this.a.a.a(false);
                        break;
                    case o.b /*2*/:
                        this.a.a.a(a);
                        break;
                    case o.c /*3*/:
                    case o.d /*4*/:
                        this.a.a.a(false);
                        break;
                }
                return true;
            }
        });
        addView(this.a, new RelativeLayout.LayoutParams(-1, -1));
    }

    private static boolean b(View view, MotionEvent motionEvent) {
        view.getLocalVisibleRect(d);
        Rect rect = d;
        rect.left += view.getPaddingLeft();
        rect = d;
        rect.top += view.getPaddingTop();
        rect = d;
        rect.right -= view.getPaddingRight();
        rect = d;
        rect.bottom -= view.getPaddingBottom();
        return d.contains(Math.round(motionEvent.getX()), Math.round(motionEvent.getY()));
    }

    public void a(String str) {
        if (str != null) {
            a().setText(str);
            addView(a(), new RelativeLayout.LayoutParams(-1, -1));
            this.a.setVisibility(8);
            a(false);
            this.c.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ bl a;

                {
                    this.a = r1;
                }

                public void onClick(View v) {
                    this.a.a(null);
                }
            });
        } else if (this.c != null) {
            removeView(a());
            this.c = null;
            this.a.setVisibility(0);
            a(true);
        }
    }

    public TextView a() {
        if (this.c == null) {
            this.c = new Button(getContext());
            this.c.setGravity(17);
        }
        this.c.postInvalidate();
        return this.c;
    }

    public void a(j jVar) {
        this.a.a(jVar);
        a(null);
    }

    public void a(j jVar, RelativeLayout.LayoutParams layoutParams) {
        this.a.a(jVar, layoutParams);
        a(null);
    }

    public void a(ScaleType scaleType) {
        this.a.setScaleType(scaleType);
    }

    public void a(boolean z) {
        this.e = z;
    }
}
