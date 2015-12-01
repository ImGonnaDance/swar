package com.com2us.smon.common;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class Com2usChromeClient extends WebChromeClient {
    private Activity mActivity;
    private View mCustomView;
    private CustomViewCallback mCustomViewCollback;
    private FullscreenHolder mFullscreenContainer;
    private int mOriginalOrientation;

    static class FullscreenHolder extends FrameLayout {
        public FullscreenHolder(Context ctx) {
            super(ctx);
            setBackgroundColor(ctx.getResources().getColor(17170444));
        }

        public boolean onTouchEvent(MotionEvent evt) {
            return true;
        }
    }

    public Com2usChromeClient(Activity activity) {
        this.mActivity = activity;
    }

    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        result.confirm();
        return super.onJsAlert(view, url, message, result);
    }

    public void onShowCustomView(View view, CustomViewCallback callback) {
        if (this.mCustomView != null) {
            callback.onCustomViewHidden();
            return;
        }
        this.mOriginalOrientation = this.mActivity.getRequestedOrientation();
        FrameLayout decor = (FrameLayout) this.mActivity.getWindow().getDecorView();
        this.mFullscreenContainer = new FullscreenHolder(this.mActivity);
        this.mFullscreenContainer.addView(view, -1);
        decor.addView(this.mFullscreenContainer, -1);
        this.mCustomView = view;
        this.mCustomViewCollback = callback;
        this.mActivity.setRequestedOrientation(this.mOriginalOrientation);
    }

    public void onHideCustomView() {
        if (this.mCustomView != null) {
            ((FrameLayout) this.mActivity.getWindow().getDecorView()).removeView(this.mFullscreenContainer);
            this.mFullscreenContainer = null;
            this.mCustomView = null;
            this.mCustomViewCollback.onCustomViewHidden();
            this.mActivity.setRequestedOrientation(this.mOriginalOrientation);
        }
    }
}
