package com.com2us.module.activeuser.useragree;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

public class UserAgreeAnimation {
    final RelativeLayout boardDown;
    final RelativeLayout boardTop;
    final RelativeLayout bodyLayout;
    long closeBodyDuration = 750;
    int displayHeight;
    int displayWidth;
    long hideTitleDuration = 750;
    boolean isAnimation = false;
    boolean isOpened = false;
    final ViewGroup mainLayout;
    long openBodyDuration = 500;
    long showTitleDuration = 750;
    final AgreementUIActivity thiz;
    final int titleHeight;
    float xDP;
    float yDP;

    public UserAgreeAnimation(AgreementUIActivity thiz, ViewGroup mainLayout, float xDP, float yDP, int displayWidth, int displayHeight, int titleHeight) {
        this.thiz = thiz;
        this.mainLayout = mainLayout;
        this.bodyLayout = (RelativeLayout) mainLayout.getChildAt(0);
        this.boardTop = (RelativeLayout) mainLayout.getChildAt(1);
        this.boardDown = (RelativeLayout) mainLayout.getChildAt(2);
        this.xDP = xDP;
        this.yDP = yDP;
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;
        this.titleHeight = titleHeight;
    }

    public void openAgreementUI() {
        if (!this.isOpened && !this.isAnimation) {
            this.isOpened = true;
            showTitleAnimation();
        }
    }

    public void closeAgreementUI(int msg) {
        if (this.isOpened && !this.isAnimation) {
            this.isOpened = false;
            closeBodyAnimation(msg);
        }
    }

    public void configurationChanged() {
        float tempDP = this.xDP;
        this.xDP = this.yDP;
        this.yDP = tempDP;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.thiz.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.displayWidth = displayMetrics.widthPixels;
        this.displayHeight = displayMetrics.heightPixels;
    }

    private void showTitleAnimation() {
        long time = AnimationUtils.currentAnimationTimeMillis() + 100;
        this.bodyLayout.setVisibility(8);
        TranslateAnimation transAni0 = new TranslateAnimation(1, null, 1, 0.0f, 1, -0.1f, 1, 0.0f);
        TranslateAnimation transAni1 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 0, -((20.0f * this.yDP) + ((float) (this.titleHeight * 2))), 1, 0.0f);
        float f = this.yDP;
        TranslateAnimation transAni2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 0, -(((float) this.displayHeight) + (10.0f * this.yDP)), 0, -(((float) this.displayHeight) - (((20.0f * r0) + ((float) (this.titleHeight * 2))) - (10.0f * this.yDP))));
        transAni0.setDuration(this.showTitleDuration);
        transAni1.setDuration(this.showTitleDuration - 50);
        transAni2.setDuration(this.showTitleDuration - 50);
        transAni0.setInterpolator(new AnticipateOvershootInterpolator());
        transAni1.setInterpolator(new AnticipateOvershootInterpolator());
        transAni2.setInterpolator(new AnticipateOvershootInterpolator());
        transAni1.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                UserAgreeAnimation.this.isAnimation = true;
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                UserAgreeAnimation.this.openBodyAnimation();
            }
        });
        this.mainLayout.invalidate();
        this.boardTop.invalidate();
        this.boardDown.invalidate();
        transAni0.setStartTime(time);
        transAni1.setStartTime(time);
        transAni2.setStartTime(time);
        this.mainLayout.startAnimation(transAni0);
        this.boardTop.startAnimation(transAni1);
        this.boardDown.startAnimation(transAni2);
    }

    private void openBodyAnimation() {
        long time = AnimationUtils.currentAnimationTimeMillis() + 10;
        TranslateAnimation transAni1 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 0, -(((float) this.displayHeight) - (((20.0f * this.yDP) + ((float) (this.titleHeight * 2))) - (10.0f * this.yDP))), 1, 0.0f);
        TranslateAnimation transAni2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 0, -(((float) this.displayHeight) - (((20.0f * this.yDP) + ((float) (this.titleHeight * 2))) - (10.0f * this.yDP))), 1, 0.0f);
        transAni1.setDuration(this.openBodyDuration + 50);
        transAni2.setDuration(this.openBodyDuration);
        transAni1.setInterpolator(new AccelerateDecelerateInterpolator());
        transAni2.setInterpolator(new AccelerateDecelerateInterpolator());
        transAni2.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                UserAgreeAnimation.this.bodyLayout.setVisibility(0);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                UserAgreeAnimation.this.isAnimation = false;
            }
        });
        this.boardDown.invalidate();
        this.bodyLayout.invalidate();
        transAni1.setStartTime(time);
        transAni2.setStartTime(time);
        this.boardDown.startAnimation(transAni1);
        this.bodyLayout.startAnimation(transAni2);
    }

    private void closeBodyAnimation(int msg) {
        long time = AnimationUtils.currentAnimationTimeMillis() + 10;
        TranslateAnimation transAni1 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 0, -(((float) this.displayHeight) - (((20.0f * this.yDP) + ((float) (this.titleHeight * 2))) - (10.0f * this.yDP))));
        TranslateAnimation transAni2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 0, -(((float) this.displayHeight) - (((20.0f * this.yDP) + ((float) (this.titleHeight * 2))) - (10.0f * this.yDP))));
        transAni1.setDuration(this.closeBodyDuration - 50);
        transAni2.setDuration(this.closeBodyDuration);
        transAni1.setInterpolator(new AccelerateDecelerateInterpolator());
        transAni2.setInterpolator(new AccelerateDecelerateInterpolator());
        final int i = msg;
        transAni1.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                UserAgreeAnimation.this.isAnimation = true;
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                UserAgreeAnimation.this.bodyLayout.setVisibility(8);
                UserAgreeAnimation.this.hideTitleAnimation(i);
            }
        });
        this.boardDown.invalidate();
        this.bodyLayout.invalidate();
        transAni1.setStartTime(time);
        transAni2.setStartTime(time);
        this.boardDown.startAnimation(transAni1);
        this.bodyLayout.startAnimation(transAni2);
    }

    private void hideTitleAnimation(int msg) {
        long time = AnimationUtils.currentAnimationTimeMillis() + 10;
        TranslateAnimation transAni0 = new TranslateAnimation(1, null, 1, 0.0f, 1, 0.0f, 1, -0.1f);
        TranslateAnimation transAni1 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 0, -((20.0f * this.yDP) + ((float) (this.titleHeight * 2))));
        TranslateAnimation transAni2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 0, -(((float) this.displayHeight) - (((20.0f * this.yDP) + ((float) (this.titleHeight * 2))) - (10.0f * this.yDP))), 0, -(((float) this.displayHeight) + (10.0f * this.yDP)));
        transAni0.setDuration(this.hideTitleDuration);
        transAni1.setDuration(this.hideTitleDuration);
        transAni2.setDuration(this.hideTitleDuration);
        transAni0.setInterpolator(new AnticipateOvershootInterpolator());
        transAni1.setInterpolator(new AnticipateOvershootInterpolator());
        transAni2.setInterpolator(new AnticipateOvershootInterpolator());
        final int i = msg;
        transAni2.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                UserAgreeAnimation.this.mainLayout.setVisibility(8);
                if (i >= 0) {
                    UserAgreeManager.getInstance().onUserAgreeResult(i);
                }
                UserAgreeAnimation.this.thiz.finish();
                UserAgreeAnimation.this.isAnimation = false;
            }
        });
        this.mainLayout.invalidate();
        this.boardTop.invalidate();
        this.boardDown.invalidate();
        transAni0.setStartTime(time);
        transAni1.setStartTime(time);
        transAni2.setStartTime(time);
        this.mainLayout.startAnimation(transAni0);
        this.boardTop.startAnimation(transAni1);
        this.boardDown.startAnimation(transAni2);
    }
}
