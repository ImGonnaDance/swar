package com.chartboost.sdk.impl;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.f;
import jp.co.dimage.android.o;

public abstract class an extends RelativeLayout {
    protected ai a;
    private ao b;
    private a c = a.BOTTOM;

    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] a = new int[a.values().length];

        static {
            try {
                a[a.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[a.BOTTOM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[a.LEFT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[a.RIGHT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public enum a {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT
    }

    protected abstract View a();

    protected abstract int b();

    public an(Context context, ai aiVar) {
        super(context);
        this.a = aiVar;
        a(context);
    }

    public void a(a aVar) {
        if (aVar == null) {
            throw new IllegalArgumentException("the given side cannot be null");
        }
        this.c = aVar;
        LayoutParams layoutParams = null;
        setClickable(false);
        int b = b();
        switch (AnonymousClass2.a[this.c.ordinal()]) {
            case o.a /*1*/:
                layoutParams = new RelativeLayout.LayoutParams(-1, CBUtility.a(b, getContext()));
                layoutParams.addRule(10);
                this.b.b(1);
                break;
            case o.b /*2*/:
                layoutParams = new RelativeLayout.LayoutParams(-1, CBUtility.a(b, getContext()));
                layoutParams.addRule(12);
                this.b.b(4);
                break;
            case o.c /*3*/:
                layoutParams = new RelativeLayout.LayoutParams(CBUtility.a(b, getContext()), -1);
                layoutParams.addRule(9);
                this.b.b(8);
                break;
            case o.d /*4*/:
                layoutParams = new RelativeLayout.LayoutParams(CBUtility.a(b, getContext()), -1);
                layoutParams.addRule(11);
                this.b.b(2);
                break;
        }
        setLayoutParams(layoutParams);
    }

    private void a(Context context) {
        Context context2 = getContext();
        setGravity(17);
        this.b = new ao(context2);
        this.b.a(-1);
        this.b.setBackgroundColor(-855638017);
        addView(this.b, new RelativeLayout.LayoutParams(-1, -1));
        addView(a(), new RelativeLayout.LayoutParams(-1, -1));
    }

    public void a(boolean z) {
        a(z, 510);
    }

    private void a(final boolean z, long j) {
        this.a.t = z;
        if (!z || getVisibility() != 0) {
            if (z || getVisibility() != 8) {
                Animation translateAnimation;
                Runnable anonymousClass1 = new Runnable(this) {
                    final /* synthetic */ an b;

                    public void run() {
                        if (!z) {
                            this.b.setVisibility(8);
                            this.b.clearAnimation();
                        }
                        this.b.a.g.remove(Integer.valueOf(hashCode()));
                    }
                };
                if (z) {
                    setVisibility(0);
                }
                float a = CBUtility.a((float) b(), getContext());
                float f;
                switch (AnonymousClass2.a[this.c.ordinal()]) {
                    case o.a /*1*/:
                        if (z) {
                            f = -a;
                        } else {
                            f = 0.0f;
                        }
                        translateAnimation = new TranslateAnimation(0.0f, 0.0f, f, z ? 0.0f : -a);
                        break;
                    case o.b /*2*/:
                        if (z) {
                            f = a;
                        } else {
                            f = 0.0f;
                        }
                        if (z) {
                            a = 0.0f;
                        }
                        translateAnimation = new TranslateAnimation(0.0f, 0.0f, f, a);
                        break;
                    case o.c /*3*/:
                        if (z) {
                            f = -a;
                        } else {
                            f = 0.0f;
                        }
                        translateAnimation = new TranslateAnimation(f, z ? 0.0f : -a, 0.0f, 0.0f);
                        break;
                    case o.d /*4*/:
                        f = z ? a : 0.0f;
                        if (z) {
                            a = 0.0f;
                        }
                        translateAnimation = new TranslateAnimation(f, a, 0.0f, 0.0f);
                        break;
                    default:
                        translateAnimation = null;
                        break;
                }
                translateAnimation.setDuration(j);
                translateAnimation.setFillAfter(!z);
                startAnimation(translateAnimation);
                this.a.g.put(Integer.valueOf(hashCode()), anonymousClass1);
                f.a.postDelayed(anonymousClass1, j);
            }
        }
    }
}
