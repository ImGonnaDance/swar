package com.com2us.module.newsbanner2;

import android.graphics.Bitmap;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import jp.co.dimage.android.o;

public class NewsBannerAnimation {
    public static final int CLOSE_BANNER_TO_TAB_ANIMATION = -1;
    public static final int OPEN_BANNER_ANIMATION = 2;
    public static final int OPEN_TAB_ANIMATION = 1;
    public static final int OPEN_TAB_TO_BANNER_ANIMAITON = -2;
    private Animation closeAni;
    private Animation openAni;

    static class BannerAnimationListener implements AnimationListener {
        FrameLayout bannerLayout;
        Bitmap bitmap;
        FrameLayout tabLayout;

        public BannerAnimationListener(FrameLayout bannerLayout, FrameLayout tabLayout, Bitmap bitmap) {
            this.bannerLayout = bannerLayout;
            this.tabLayout = tabLayout;
            this.bitmap = bitmap;
        }

        private void setClickable(boolean clickable) {
            int i = 0;
            View[] views = new View[]{this.tabLayout.getChildAt(0), this.bannerLayout.getChildAt(0), this.bannerLayout.getChildAt(NewsBannerAnimation.OPEN_TAB_ANIMATION)};
            int length = views.length;
            while (i < length) {
                try {
                    views[i].setClickable(clickable);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i += NewsBannerAnimation.OPEN_TAB_ANIMATION;
            }
        }

        public void onAnimationStart(Animation paramAnimation) {
            setClickable(false);
        }

        public void onAnimationEnd(Animation paramAnimation) {
            setClickable(true);
            if (this.bitmap != null) {
                try {
                    ((ImageView) this.tabLayout.getChildAt(0)).setImageBitmap(this.bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void onAnimationRepeat(Animation paramAnimation) {
        }
    }

    class AnonymousClass1 extends BannerAnimationListener {
        private final /* synthetic */ View val$mainLayout;
        private final /* synthetic */ int val$offset;
        private final /* synthetic */ int val$position;

        AnonymousClass1(FrameLayout $anonymous0, FrameLayout $anonymous1, Bitmap $anonymous2, int i, View view, int i2) {
            this.val$position = i;
            this.val$mainLayout = view;
            this.val$offset = i2;
            super($anonymous0, $anonymous1, $anonymous2);
        }

        public void onAnimationStart(Animation paramAnimation) {
            super.onAnimationStart(paramAnimation);
        }

        public void onAnimationEnd(Animation paramAnimation) {
            switch (this.val$position) {
                case NewsBannerAnimation.OPEN_TAB_ANIMATION /*1*/:
                    this.val$mainLayout.setPadding(0, -this.bannerLayout.getHeight(), 0, 0);
                    break;
                case NewsBannerAnimation.OPEN_BANNER_ANIMATION /*2*/:
                    this.val$mainLayout.setPadding(0, 0, 0, -this.bannerLayout.getHeight());
                    break;
                case o.c /*3*/:
                    this.val$mainLayout.setPadding(0, this.val$offset, -this.bannerLayout.getWidth(), 0);
                    break;
                case o.d /*4*/:
                    this.val$mainLayout.setPadding(-this.bannerLayout.getWidth(), this.val$offset, 0, 0);
                    break;
            }
            super.onAnimationEnd(paramAnimation);
        }

        public void onAnimationRepeat(Animation paramAnimation) {
            super.onAnimationRepeat(paramAnimation);
        }
    }

    public static void start(int animation, int position, int duration, BannerAnimationListener listener, int offset, Bitmap bannerBitmap, Bitmap inTabBitmap, Bitmap outBitmap, View mainLayout, FrameLayout bannerLayout, FrameLayout tabLayout) {
        switch (animation) {
            case OPEN_TAB_TO_BANNER_ANIMAITON /*-2*/:
                openTabToBannerAnimation(position, duration, offset, inTabBitmap, mainLayout, bannerLayout, tabLayout);
                return;
            case CLOSE_BANNER_TO_TAB_ANIMATION /*-1*/:
                closeBannerToTabAnimation(position, duration, offset, outBitmap, mainLayout, bannerLayout, tabLayout);
                return;
            case OPEN_TAB_ANIMATION /*1*/:
                openTabAnimation(position, duration, offset, outBitmap, mainLayout, bannerLayout, tabLayout, bannerBitmap);
                return;
            case OPEN_BANNER_ANIMATION /*2*/:
                openBannerAnimation(position, duration, listener, offset, inTabBitmap, mainLayout, bannerLayout, tabLayout);
                return;
            default:
                return;
        }
    }

    public static void openBannerAnimation(int position, int duration, BannerAnimationListener listener, int offset, Bitmap bitmap, View mainLayout, FrameLayout bannerLayout, FrameLayout tabLayout) {
        AnimationListener animationListener;
        Animation animation = null;
        switch (position) {
            case OPEN_TAB_ANIMATION /*1*/:
                mainLayout.setPadding(0, 0, 0, 0);
                animation = new TranslateAnimation(0.0f, 0.0f, (float) (-mainLayout.getHeight()), 0.0f);
                break;
            case OPEN_BANNER_ANIMATION /*2*/:
                mainLayout.setPadding(0, 0, 0, 0);
                animation = new TranslateAnimation(0.0f, 0.0f, (float) mainLayout.getHeight(), 0.0f);
                break;
            case o.c /*3*/:
                mainLayout.setPadding(0, offset, 0, 0);
                animation = new TranslateAnimation((float) mainLayout.getWidth(), 0.0f, 0.0f, 0.0f);
                break;
            case o.d /*4*/:
                mainLayout.setPadding(0, offset, 0, 0);
                animation = new TranslateAnimation((float) (-mainLayout.getWidth()), 0.0f, 0.0f, 0.0f);
                break;
        }
        if (listener != null) {
            animationListener = listener;
        } else {
            animationListener = new BannerAnimationListener(bannerLayout, tabLayout, bitmap);
        }
        setAnimation(animation, duration, bitmap, mainLayout, bannerLayout, tabLayout, animationListener);
        mainLayout.startAnimation(animation);
    }

    public static void openTabToBannerAnimation(int position, int duration, int offset, Bitmap bitmap, View mainLayout, FrameLayout bannerLayout, FrameLayout tabLayout) {
        Animation animation = null;
        switch (position) {
            case OPEN_TAB_ANIMATION /*1*/:
                mainLayout.setPadding(0, 0, 0, 0);
                animation = new TranslateAnimation(0.0f, 0.0f, (float) (-bannerLayout.getHeight()), 0.0f);
                break;
            case OPEN_BANNER_ANIMATION /*2*/:
                mainLayout.setPadding(0, 0, 0, 0);
                animation = new TranslateAnimation(0.0f, 0.0f, (float) bannerLayout.getHeight(), 0.0f);
                break;
            case o.c /*3*/:
                mainLayout.setPadding(0, offset, 0, 0);
                animation = new TranslateAnimation((float) bannerLayout.getWidth(), 0.0f, 0.0f, 0.0f);
                break;
            case o.d /*4*/:
                mainLayout.setPadding(0, offset, 0, 0);
                animation = new TranslateAnimation((float) (-bannerLayout.getWidth()), 0.0f, 0.0f, 0.0f);
                break;
        }
        setAnimation(animation, duration, bitmap, mainLayout, bannerLayout, tabLayout, new BannerAnimationListener(bannerLayout, tabLayout, bitmap));
        mainLayout.startAnimation(animation);
    }

    public static void closeBannerToTabAnimation(int position, int duration, int offset, Bitmap bitmap, View mainLayout, FrameLayout bannerLayout, FrameLayout tabLayout) {
        Animation animation = null;
        switch (position) {
            case OPEN_TAB_ANIMATION /*1*/:
                mainLayout.setPadding(0, 0, 0, 0);
                animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) (-bannerLayout.getHeight()));
                break;
            case OPEN_BANNER_ANIMATION /*2*/:
                mainLayout.setPadding(0, 0, 0, 0);
                animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) bannerLayout.getHeight());
                break;
            case o.c /*3*/:
                mainLayout.setPadding(0, offset, 0, 0);
                animation = new TranslateAnimation(0.0f, (float) bannerLayout.getWidth(), 0.0f, 0.0f);
                break;
            case o.d /*4*/:
                mainLayout.setPadding(0, offset, 0, 0);
                animation = new TranslateAnimation(0.0f, (float) (-bannerLayout.getWidth()), 0.0f, 0.0f);
                break;
        }
        setAnimation(animation, duration, bitmap, mainLayout, bannerLayout, tabLayout, new AnonymousClass1(bannerLayout, tabLayout, bitmap, position, mainLayout, offset));
        mainLayout.startAnimation(animation);
    }

    public static void openTabAnimation(int position, int duration, int offset, Bitmap bitmap, View mainLayout, FrameLayout bannerLayout, FrameLayout tabLayout, Bitmap bannerBitmap) {
        Animation animation = null;
        switch (position) {
            case OPEN_TAB_ANIMATION /*1*/:
                mainLayout.setPadding(0, -bannerBitmap.getHeight(), 0, 0);
                animation = new TranslateAnimation(0.0f, 0.0f, (float) (-tabLayout.getHeight()), 0.0f);
                break;
            case OPEN_BANNER_ANIMATION /*2*/:
                mainLayout.setPadding(0, 0, 0, -bannerBitmap.getHeight());
                animation = new TranslateAnimation(0.0f, 0.0f, (float) tabLayout.getHeight(), 0.0f);
                break;
            case o.c /*3*/:
                mainLayout.setPadding(0, offset, -bannerBitmap.getWidth(), 0);
                animation = new TranslateAnimation((float) tabLayout.getWidth(), 0.0f, 0.0f, 0.0f);
                break;
            case o.d /*4*/:
                mainLayout.setPadding(-bannerBitmap.getWidth(), offset, 0, 0);
                animation = new TranslateAnimation((float) (-tabLayout.getWidth()), 0.0f, 0.0f, 0.0f);
                break;
        }
        setAnimation(animation, duration, bitmap, mainLayout, bannerLayout, tabLayout, new BannerAnimationListener(bannerLayout, tabLayout, bitmap));
        mainLayout.startAnimation(animation);
    }

    private static void setAnimation(Animation animation, int duration, Bitmap bitmap, View mainLayout, FrameLayout bannerLayout, FrameLayout tabLayout, AnimationListener listener) {
        animation.setDuration((long) duration);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(listener);
    }
}
