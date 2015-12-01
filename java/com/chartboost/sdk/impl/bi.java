package com.chartboost.sdk.impl;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import jp.co.dimage.android.f;
import jp.co.dimage.android.o;

public final class bi {

    public interface a {
        void a(com.chartboost.sdk.Model.a aVar);
    }

    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[b.values().length];

        static {
            try {
                a[b.FADE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[b.PERSPECTIVE_ZOOM.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[b.PERSPECTIVE_ROTATE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[b.SLIDE_FROM_BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[b.SLIDE_FROM_TOP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[b.BOUNCE.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public enum b {
        PERSPECTIVE_ROTATE,
        BOUNCE,
        PERSPECTIVE_ZOOM,
        SLIDE_FROM_BOTTOM,
        SLIDE_FROM_TOP,
        FADE,
        NONE;

        public static b a(int i) {
            if (i != 0 && i > 0 && i <= values().length) {
                return values()[i - 1];
            }
            return null;
        }
    }

    public static void a(b bVar, com.chartboost.sdk.Model.a aVar, a aVar2) {
        b(bVar, aVar, aVar2, true);
    }

    public static void b(b bVar, com.chartboost.sdk.Model.a aVar, a aVar2) {
        c(bVar, aVar, aVar2, false);
    }

    private static void b(b bVar, com.chartboost.sdk.Model.a aVar, a aVar2, boolean z) {
        if (bVar == b.NONE) {
            if (aVar2 != null) {
                aVar2.a(aVar);
            }
        } else if (aVar == null || aVar.i == null) {
            CBLogging.a("CBAnimationManager", "Transition of impression canceled due to lack of container");
        } else {
            final View f = aVar.i.f();
            if (f == null) {
                CBLogging.a("CBAnimationManager", "Transition of impression canceled due to lack of view");
                return;
            }
            ViewTreeObserver viewTreeObserver = f.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                final b bVar2 = bVar;
                final com.chartboost.sdk.Model.a aVar3 = aVar;
                final a aVar4 = aVar2;
                final boolean z2 = z;
                viewTreeObserver.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                    public void onGlobalLayout() {
                        f.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        bi.c(bVar2, aVar3, aVar4, z2);
                    }
                });
            }
        }
    }

    private static void c(b bVar, com.chartboost.sdk.Model.a aVar, a aVar2, boolean z) {
        Animation animationSet = new AnimationSet(true);
        animationSet.addAnimation(new AlphaAnimation(1.0f, 1.0f));
        if (aVar == null || aVar.i == null) {
            CBLogging.a("CBAnimationManager", "Transition of impression canceled due to lack of container");
            return;
        }
        View f = aVar.i.f();
        if (f == null) {
            CBLogging.a("CBAnimationManager", "Transition of impression canceled due to lack of view");
            return;
        }
        Animation alphaAnimation;
        float width = (float) f.getWidth();
        float height = (float) f.getHeight();
        float f2 = (1.0f - 0.4f) / 2.0f;
        Animation translateAnimation;
        switch (AnonymousClass3.a[bVar.ordinal()]) {
            case o.a /*1*/:
                if (z) {
                    alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                } else {
                    alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                }
                alphaAnimation.setDuration(600);
                alphaAnimation.setFillAfter(true);
                Animation animationSet2 = new AnimationSet(true);
                animationSet2.addAnimation(alphaAnimation);
                alphaAnimation = animationSet2;
                break;
            case o.b /*2*/:
                if (z) {
                    alphaAnimation = new bn(-1114636288, 0.0f, width / 2.0f, height / 2.0f, false);
                } else {
                    alphaAnimation = new bn(0.0f, 60.0f, width / 2.0f, height / 2.0f, false);
                }
                alphaAnimation.setDuration(600);
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                if (z) {
                    alphaAnimation = new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f);
                } else {
                    alphaAnimation = new ScaleAnimation(1.0f, 0.4f, 1.0f, 0.4f);
                }
                alphaAnimation.setDuration(600);
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                if (z) {
                    alphaAnimation = new TranslateAnimation(width * f2, 0.0f, (-height) * 0.4f, 0.0f);
                } else {
                    alphaAnimation = new TranslateAnimation(0.0f, width * f2, 0.0f, height);
                }
                alphaAnimation.setDuration(600);
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                alphaAnimation = animationSet;
                break;
            case o.c /*3*/:
                if (z) {
                    alphaAnimation = new bn(-1114636288, 0.0f, width / 2.0f, height / 2.0f, true);
                } else {
                    alphaAnimation = new bn(0.0f, 60.0f, width / 2.0f, height / 2.0f, true);
                }
                alphaAnimation.setDuration(600);
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                if (z) {
                    alphaAnimation = new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f);
                } else {
                    alphaAnimation = new ScaleAnimation(1.0f, 0.4f, 1.0f, 0.4f);
                }
                alphaAnimation.setDuration(600);
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                if (z) {
                    alphaAnimation = new TranslateAnimation((-width) * 0.4f, 0.0f, height * f2, 0.0f);
                } else {
                    alphaAnimation = new TranslateAnimation(0.0f, width, 0.0f, height * f2);
                }
                alphaAnimation.setDuration(600);
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                alphaAnimation = animationSet;
                break;
            case o.d /*4*/:
                float f3;
                float f4 = z ? height : 0.0f;
                if (z) {
                    f3 = 0.0f;
                } else {
                    f3 = height;
                }
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, f4, f3);
                translateAnimation.setDuration(600);
                translateAnimation.setFillAfter(true);
                animationSet.addAnimation(translateAnimation);
                alphaAnimation = animationSet;
                break;
            case f.bc /*5*/:
                translateAnimation = new TranslateAnimation(0.0f, 0.0f, z ? -height : 0.0f, z ? 0.0f : -height);
                translateAnimation.setDuration(600);
                translateAnimation.setFillAfter(true);
                animationSet.addAnimation(translateAnimation);
                alphaAnimation = animationSet;
                break;
            case f.aL /*6*/:
                if (!z) {
                    alphaAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 0.5f, 1, 0.5f);
                    alphaAnimation.setDuration(600);
                    alphaAnimation.setStartOffset(0);
                    alphaAnimation.setFillAfter(true);
                    animationSet.addAnimation(alphaAnimation);
                    alphaAnimation = animationSet;
                    break;
                }
                alphaAnimation = new ScaleAnimation(0.6f, 1.1f, 0.6f, 1.1f, 1, 0.5f, 1, 0.5f);
                alphaAnimation.setDuration((long) Math.round(((float) 600) * 0.6f));
                alphaAnimation.setStartOffset(0);
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                alphaAnimation = new ScaleAnimation(1.0f, 0.81818175f, 1.0f, 0.81818175f, 1, 0.5f, 1, 0.5f);
                alphaAnimation.setDuration((long) Math.round(((float) 600) * 0.19999999f));
                alphaAnimation.setStartOffset((long) Math.round(((float) 600) * 0.6f));
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                alphaAnimation = new ScaleAnimation(1.0f, 1.1111112f, 1.0f, 1.1111112f, 1, 0.5f, 1, 0.5f);
                alphaAnimation.setDuration((long) Math.round(((float) 600) * 0.099999964f));
                alphaAnimation.setStartOffset((long) Math.round(((float) 600) * 0.8f));
                alphaAnimation.setFillAfter(true);
                animationSet.addAnimation(alphaAnimation);
                alphaAnimation = animationSet;
                break;
            default:
                alphaAnimation = animationSet;
                break;
        }
        if (bVar != b.NONE) {
            if (aVar2 != null) {
                final a aVar3 = aVar2;
                final com.chartboost.sdk.Model.a aVar4 = aVar;
                CBUtility.e().postDelayed(new Runnable() {
                    public void run() {
                        aVar3.a(aVar4);
                    }
                }, 600);
            }
            f.startAnimation(alphaAnimation);
        } else if (aVar2 != null) {
            aVar2.a(aVar);
        }
    }

    public static void a(boolean z, View view) {
        a(z, view, 510);
    }

    public static void a(boolean z, View view, long j) {
        float f;
        float f2 = 1.0f;
        view.clearAnimation();
        if (z) {
            view.setVisibility(0);
        }
        if (z) {
            f = 0.0f;
        } else {
            f = 1.0f;
        }
        if (!z) {
            f2 = 0.0f;
        }
        Animation alphaAnimation = new AlphaAnimation(f, f2);
        alphaAnimation.setDuration(510);
        alphaAnimation.setFillBefore(true);
        view.startAnimation(alphaAnimation);
    }
}
