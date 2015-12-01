package com.com2us.module.activeuser;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.com2us.module.activeuser.ActiveUserNetwork.ReceivedConfigurationData;
import jp.co.dimage.android.o;

public class ActiveUserUpdateDialog extends Dialog {
    private int height = 690;
    private OnFinishListener mlistener = null;
    private int width = 690;

    public interface OnFinishListener {
        void onFinish();
    }

    public ActiveUserUpdateDialog(Activity activity, ReceivedConfigurationData recvConfigurationData) {
        super(activity);
        requestWindowFeature(1);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().addFlags(2);
        setContentView(createUI(activity, recvConfigurationData, processSize(activity)));
        getWindow().getAttributes().width = this.width;
        getWindow().getAttributes().height = this.height;
    }

    public void setOnFinishListener(OnFinishListener listener) {
        this.mlistener = listener;
    }

    private View createUI(Activity activity, ReceivedConfigurationData recvConfigurationData, LayoutParams dialogParams) {
        RelativeLayout mainLayout = new RelativeLayout(activity);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        View imageView = new ImageView(activity);
        layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        imageView = new TextView(activity);
        ViewGroup.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        imageView = new TextView(activity);
        layoutParams2 = new RelativeLayout.LayoutParams(-1, -1);
        imageView = new ScrollView(activity);
        layoutParams2 = new RelativeLayout.LayoutParams(-1, -2);
        final TextView button = new TextView(activity);
        RelativeLayout.LayoutParams buttonParam = new RelativeLayout.LayoutParams(-1, -2);
        final TextView buttonShadow = new TextView(activity);
        RelativeLayout.LayoutParams buttonShadowParam = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams.addRule(13);
        imageView.setId(1001);
        imageView.setLayoutParams(layoutParams);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(-16777216);
        gd.setCornerRadius((float) 20);
        gd.setAlpha(178);
        if (VERSION.SDK_INT >= 16) {
            imageView.setBackground(gd);
        } else {
            imageView.setBackgroundDrawable(gd);
        }
        mainLayout.addView(imageView);
        layoutParams2.addRule(10);
        layoutParams2.addRule(14);
        layoutParams2.setMargins(0, (int) (((double) this.height) * 0.05d), 0, (int) (((double) this.height) * 0.03d));
        imageView.setId(1002);
        imageView.setText(recvConfigurationData.notice_title);
        imageView.setLayoutParams(layoutParams2);
        imageView.setPadding((int) (((double) this.width) * 0.1d), 0, (int) (((double) this.width) * 0.1d), 0);
        imageView.setTextColor(Color.rgb(232, 196, 0));
        double d = ((double) this.height) * 0.08d;
        imageView.setTextSize(0, (float) ((int) r26));
        imageView.setGravity(17);
        imageView.setMaxLines(1);
        imageView.setSingleLine(true);
        imageView.setEllipsize(TruncateAt.MARQUEE);
        mainLayout.addView(imageView);
        buttonParam.addRule(12);
        buttonParam.addRule(14);
        d = ((double) this.height) * 0.06d;
        d = ((double) this.width) * 0.04d;
        buttonParam.setMargins((int) (((double) this.width) * 0.04d), (int) r26, (int) r26, (int) (((double) this.height) * 0.06d));
        button.setId(1003);
        button.setText(recvConfigurationData.notice_button);
        button.setTextColor(-16777216);
        button.setTextSize(0, (float) ((int) (((double) this.height) * 0.08d)));
        button.setGravity(17);
        button.setMaxLines(1);
        button.setSingleLine(true);
        button.setEllipsize(TruncateAt.MARQUEE);
        button.setLayoutParams(buttonParam);
        d = ((double) this.height) * 0.03d;
        d = ((double) this.width) * 0.1d;
        button.setPadding((int) (((double) this.width) * 0.1d), (int) r26, (int) r26, (int) (((double) this.height) * 0.03d));
        final GradientDrawable button_gd = new GradientDrawable();
        button_gd.setColor(Color.rgb(232, 196, 0));
        button_gd.setCornerRadius(10.0f);
        if (VERSION.SDK_INT >= 16) {
            button.setBackground(button_gd);
        } else {
            button.setBackgroundDrawable(button_gd);
        }
        final Activity activity2 = activity;
        button.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                Activity activity;
                final TextView textView;
                final GradientDrawable gradientDrawable;
                final TextView textView2;
                if (event.getAction() == 0) {
                    activity = activity2;
                    textView = button;
                    gradientDrawable = button_gd;
                    textView2 = buttonShadow;
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            textView.setTextColor(-1);
                            gradientDrawable.setColor(Color.rgb(255, 217, 0));
                            if (VERSION.SDK_INT >= 16) {
                                textView.setBackground(gradientDrawable);
                            } else {
                                textView.setBackgroundDrawable(gradientDrawable);
                            }
                            textView2.setVisibility(4);
                        }
                    });
                } else if (event.getAction() == 1) {
                    activity = activity2;
                    textView = button;
                    gradientDrawable = button_gd;
                    textView2 = buttonShadow;
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            textView.setTextColor(-16777216);
                            gradientDrawable.setColor(Color.rgb(232, 196, 0));
                            if (VERSION.SDK_INT >= 16) {
                                textView.setBackground(gradientDrawable);
                            } else {
                                textView.setBackgroundDrawable(gradientDrawable);
                            }
                            textView2.setVisibility(0);
                        }
                    });
                }
                return false;
            }
        });
        final ReceivedConfigurationData receivedConfigurationData = recvConfigurationData;
        final Activity activity3 = activity;
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                switch (receivedConfigurationData.notice_action) {
                    case o.a /*1*/:
                        Intent marketIntent = new Intent("android.intent.action.VIEW", Uri.parse(receivedConfigurationData.notice_url));
                        marketIntent.setFlags(268435456);
                        try {
                            activity3.startActivity(marketIntent);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        ActiveUserUpdateDialog.this.dismiss();
                        activity3.finish();
                        return;
                    case o.c /*3*/:
                        if (ActiveUserUpdateDialog.this.mlistener != null) {
                            ActiveUserUpdateDialog.this.mlistener.onFinish();
                        }
                        ActiveUserUpdateDialog.this.dismiss();
                        return;
                    default:
                        ActiveUserUpdateDialog.this.dismiss();
                        activity3.finish();
                        return;
                }
            }
        });
        buttonShadow.setTextSize(0, (float) ((int) (((double) this.height) * 0.08d)));
        buttonShadow.setClickable(false);
        buttonShadow.setTextColor(0);
        buttonShadowParam.addRule(12);
        d = ((double) this.height) * 0.06d;
        d = ((double) this.width) * 0.04d;
        buttonShadowParam.setMargins((int) (((double) this.width) * 0.04d), (int) r26, (int) r26, (int) (((double) this.height) * 0.04d));
        buttonShadow.setLayoutParams(buttonShadowParam);
        d = ((double) this.height) * 0.03d;
        d = ((double) this.width) * 0.15d;
        buttonShadow.setPadding((int) (((double) this.width) * 0.15d), (int) r26, (int) r26, (int) (((double) this.height) * 0.03d));
        GradientDrawable buttonShadow_gd = new GradientDrawable();
        buttonShadow_gd.setColor(Color.rgb(157, 133, 5));
        buttonShadow_gd.setCornerRadius(10.0f);
        if (VERSION.SDK_INT >= 16) {
            buttonShadow.setBackground(buttonShadow_gd);
        } else {
            buttonShadow.setBackgroundDrawable(buttonShadow_gd);
        }
        mainLayout.addView(button);
        layoutParams2.addRule(3, imageView.getId());
        layoutParams2.addRule(2, button.getId());
        layoutParams2.addRule(14);
        layoutParams2.setMargins((int) (((double) this.width) * 0.1d), 0, (int) (((double) this.width) * 0.1d), 0);
        imageView.setId(1004);
        imageView.setLayoutParams(layoutParams2);
        imageView.setSmoothScrollingEnabled(true);
        imageView.setId(1005);
        imageView.setText(recvConfigurationData.notice_message);
        d = ((double) this.height) * 0.06d;
        imageView.setTextSize(0, (float) ((int) r26));
        imageView.setLayoutParams(layoutParams2);
        imageView.setGravity(7);
        imageView.setTextColor(-1);
        imageView.setBackgroundColor(0);
        imageView.addView(imageView);
        mainLayout.addView(imageView);
        layoutParams.addRule(13);
        mainLayout.setLayoutParams(layoutParams);
        return mainLayout;
    }

    private LayoutParams processSize(Activity activity) {
        LayoutParams params = getWindow().getAttributes();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (((double) displayMetrics.widthPixels) * 0.9d);
        int height = (int) (((double) displayMetrics.heightPixels) * 0.9d);
        if (width > 690) {
            width = 690;
        }
        if (height > 690) {
            height = 690;
        }
        if (width > height) {
            width = height;
        } else {
            height = width;
        }
        params.width = width;
        params.height = (int) (((double) height) * 0.78d);
        this.width = params.width;
        this.height = params.height;
        return params;
    }
}
