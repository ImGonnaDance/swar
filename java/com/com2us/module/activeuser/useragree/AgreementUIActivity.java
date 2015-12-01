package com.com2us.module.activeuser.useragree;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.com2us.module.activeuser.ActiveUserData;
import com.com2us.module.activeuser.ActiveUserData.DATA_INDEX;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import com.com2us.module.activeuser.downloadcheck.InstallService;
import com.com2us.module.offerwall.OfferwallDefine;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.peppermint.PeppermintType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import jp.co.cyberz.fox.a.a.i;
import jp.co.cyberz.fox.notify.a;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AgreementUIActivity extends Activity {
    private float DP;
    private final LayoutParams FILL_PARAMS = new LayoutParams(-1, -1);
    private final LayoutParams FILL_WRAP_PARAMS = new LayoutParams(-1, -2);
    private int agreeBtnDefaultFontColor = Color.rgb(68, 124, 193);
    private int agreeBtnSelectFontColor = Color.rgb(255, 255, 255);
    private String agreement_ex_url = null;
    private int agreement_version = -1;
    private boolean backKeyFlag = false;
    private Bitmap board_down_l_Bitmap = null;
    private Bitmap board_down_logo_Bitmap = null;
    private Bitmap board_down_m_Bitmap = null;
    private Bitmap board_down_r_Bitmap = null;
    private Bitmap board_textbox_center_down_Bitmap = null;
    private Bitmap board_textbox_center_top_Bitmap = null;
    private Bitmap board_textbox_edge_1_Bitmap = null;
    private Bitmap board_textbox_edge_2_Bitmap = null;
    private Bitmap board_textbox_edge_3_Bitmap = null;
    private Bitmap board_textbox_edge_4_Bitmap = null;
    private Bitmap board_textbox_side_left_Bitmap = null;
    private Bitmap board_textbox_side_right_Bitmap = null;
    private Bitmap board_top_l_Bitmap = null;
    private Bitmap board_top_logo_Bitmap = null;
    private Bitmap board_top_m_Bitmap = null;
    private Bitmap board_top_r_Bitmap = null;
    private int bodyBackgroundColor = Color.rgb(232, 232, 232);
    private ViewGroup bodyLayout;
    private LinearLayout bodyLinearLayout = null;
    private Bitmap btn_default_l_Bitmap = null;
    private Bitmap btn_default_m_1_Bitmap = null;
    private Bitmap btn_default_r_Bitmap = null;
    private Bitmap btn_select_l_Bitmap = null;
    private Bitmap btn_select_m_1_Bitmap = null;
    private Bitmap btn_select_r_Bitmap = null;
    private int centerLineColor = Color.rgb(216, 216, 216);
    private ImageView[] centerLines = null;
    private Bitmap check_btn_Bitmap = null;
    private Bitmap closeBitmap = null;
    private int colorType = 0;
    boolean destroyParentActivity = true;
    private UserAgreeDialog dialog;
    private int displayHeight;
    private int displayWidth;
    private int firstIndex = PeppermintType.HUB_E_SYSTEM_ERROR;
    private float fontDP;
    private HorizontalScrollView horizontalScrollView = null;
    private int innerTextboxBackgroundColor = Color.rgb(216, 216, 216);
    private int linkFontColor = Color.rgb(205, 32, 39);
    private Logger logger;
    private Bitmap logoBitmap = null;
    private Handler mBackKeyHandler = null;
    private Handler mHandler = null;
    private ViewGroup mainLayout;
    private int outerTextBoxHorizontalSize = -1;
    private OuterTextBox[] outerTextBoxList = null;
    private int outerTextBoxVerticalSize = -1;
    private int termsDataLength;
    private int textboxFontColor = Color.rgb(102, 102, 102);
    private int titleFontColor = Color.rgb(68, 68, 68);
    private TermsManager tm = null;
    private String url = null;
    private final boolean useWebView = true;
    private UserAgreeAnimation userAgreeAnimation = null;
    private ScrollView verticalScrollView = null;
    private AlertDialog webViewRetryDialog = null;
    private boolean webViewTimeoutFlag = true;
    private float xDP;
    private float yDP;

    class AgreementBtn extends RelativeLayout {
        public static final int NEGATIVE_BTN = 1;
        public static final int POSITIVE_BTN = 0;
        public int index;
        public CreateBtn negativeBtn = null;
        public CreateBtn positiveBtn = null;
        public boolean superChecked = false;

        class CreateBtn extends RelativeLayout {
            public int buttonType;
            ImageView checkView;
            public boolean isChecked = false;
            ImageView leftView;
            ImageView middleView;
            ImageView rightView;
            TextFitTextView textView;

            public CreateBtn(Context context, int buttonType, int index, String text) {
                super(context);
                this.buttonType = buttonType;
                setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
                setId(index);
                RelativeLayout layout = new RelativeLayout(context);
                RelativeLayout.LayoutParams sublayoutParams = new RelativeLayout.LayoutParams(-2, -2);
                sublayoutParams.topMargin = AgreementUIActivity.this.yDP(22);
                layout.setLayoutParams(sublayoutParams);
                RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(-2, -2);
                leftParams.addRule(9);
                this.leftView = new ImageView(context);
                this.leftView.setLayoutParams(leftParams);
                this.leftView.setImageBitmap(AgreementUIActivity.this.btn_default_l_Bitmap);
                this.leftView.setId(index + AgreementBtn.NEGATIVE_BTN);
                layout.addView(this.leftView);
                RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(-2, -2);
                rightParams.addRule(11);
                this.rightView = new ImageView(context);
                this.rightView.setLayoutParams(rightParams);
                this.rightView.setImageBitmap(AgreementUIActivity.this.btn_default_r_Bitmap);
                this.rightView.setId(index + 2);
                layout.addView(this.rightView);
                RelativeLayout.LayoutParams middleParams = new RelativeLayout.LayoutParams(-1, -2);
                middleParams.addRule(AgreementBtn.NEGATIVE_BTN, this.leftView.getId());
                middleParams.addRule(0, this.rightView.getId());
                this.middleView = new ImageView(context);
                this.middleView.setLayoutParams(middleParams);
                this.middleView.setImageBitmap(AgreementUIActivity.this.btn_default_m_1_Bitmap);
                this.middleView.setId(index + 3);
                this.middleView.setScaleType(ScaleType.FIT_XY);
                layout.addView(this.middleView);
                RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(-2, -2);
                textViewParams.addRule(14);
                textViewParams.addRule(15);
                this.textView = new TextFitTextView(context);
                this.textView.setLayoutParams(textViewParams);
                this.textView.setTextSize(AgreementBtn.NEGATIVE_BTN, AgreementUIActivity.this.fontSize(25.0f));
                this.textView.setMaxLines(AgreementBtn.NEGATIVE_BTN);
                this.textView.setPadding(AgreementUIActivity.this.xDP(20), 0, AgreementUIActivity.this.xDP(20), 0);
                this.textView.setFitTextToBox(Boolean.valueOf(true), AgreementUIActivity.this.btn_default_m_1_Bitmap.getHeight() - AgreementUIActivity.this.yDP(30));
                this.textView.setTextColor(AgreementUIActivity.this.agreeBtnDefaultFontColor);
                this.textView.setText(text);
                layout.addView(this.textView);
                RelativeLayout.LayoutParams checkParams = new RelativeLayout.LayoutParams(-2, -2);
                checkParams.addRule(9);
                checkParams.leftMargin = AgreementUIActivity.this.xDP(10);
                checkParams.topMargin = AgreementUIActivity.this.yDP(15);
                this.checkView = new ImageView(context);
                this.checkView.setLayoutParams(checkParams);
                this.checkView.setImageBitmap(AgreementUIActivity.this.check_btn_Bitmap);
                this.checkView.setVisibility(8);
                addView(layout);
                addView(this.checkView);
                setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        CreateBtn.this.clicked(true);
                    }
                });
            }

            public void clicked(boolean isSendSuper) {
                this.isChecked = !this.isChecked;
                if (this.isChecked) {
                    this.leftView.setImageBitmap(AgreementUIActivity.this.btn_select_l_Bitmap);
                    this.rightView.setImageBitmap(AgreementUIActivity.this.btn_select_r_Bitmap);
                    this.middleView.setImageBitmap(AgreementUIActivity.this.btn_select_m_1_Bitmap);
                    this.textView.setTextColor(AgreementUIActivity.this.agreeBtnSelectFontColor);
                    this.checkView.setVisibility(0);
                    if (isSendSuper) {
                        AgreementBtn.this.superClicked(this.buttonType, this.isChecked);
                        return;
                    }
                    return;
                }
                this.leftView.setImageBitmap(AgreementUIActivity.this.btn_default_l_Bitmap);
                this.rightView.setImageBitmap(AgreementUIActivity.this.btn_default_r_Bitmap);
                this.middleView.setImageBitmap(AgreementUIActivity.this.btn_default_m_1_Bitmap);
                this.textView.setTextColor(AgreementUIActivity.this.agreeBtnDefaultFontColor);
                this.checkView.setVisibility(8);
                if (isSendSuper) {
                    AgreementBtn.this.superClicked(this.buttonType, this.isChecked);
                }
            }
        }

        public AgreementBtn(Context context, int type, String positiveText, String negativeText, int index) {
            super(context);
            this.index = index;
            setId(index);
            LinearLayout linear = new LinearLayout(context);
            linear.setLayoutParams(new LayoutParams(-1, -2));
            linear.setOrientation(0);
            linear.setGravity(NEGATIVE_BTN);
            linear.setWeightSum(1.0f);
            this.positiveBtn = new CreateBtn(context, 0, index + 10, positiveText);
            if (type != 0) {
                this.negativeBtn = new CreateBtn(context, NEGATIVE_BTN, index + 20, negativeText);
                LinearLayout positiveInnerLinear = new LinearLayout(context);
                LayoutParams positiveInnerLinearParams = new LayoutParams(-2, -2);
                positiveInnerLinearParams.weight = 0.5f;
                positiveInnerLinear.setLayoutParams(positiveInnerLinearParams);
                positiveInnerLinear.setPadding(0, 0, AgreementUIActivity.this.xDP(10), 0);
                positiveInnerLinear.addView(this.positiveBtn);
                LinearLayout negativeInnerLinear = new LinearLayout(context);
                LayoutParams negativeInnerLinearParams = new LayoutParams(-2, -2);
                negativeInnerLinearParams.weight = 0.5f;
                negativeInnerLinear.setLayoutParams(negativeInnerLinearParams);
                negativeInnerLinear.setPadding(AgreementUIActivity.this.xDP(10), 0, 0, 0);
                negativeInnerLinear.addView(this.negativeBtn);
                linear.addView(positiveInnerLinear);
                linear.addView(negativeInnerLinear);
            } else {
                linear.addView(this.positiveBtn);
            }
            addView(linear);
        }

        public void superClicked(int buttonType, boolean isChecked) {
            if (isChecked) {
                switch (buttonType) {
                    case g.a /*0*/:
                        if (this.negativeBtn != null && this.negativeBtn.isChecked) {
                            this.negativeBtn.clicked(false);
                            break;
                        }
                    default:
                        if (this.positiveBtn != null && this.positiveBtn.isChecked) {
                            this.positiveBtn.clicked(false);
                            break;
                        }
                }
                this.superChecked = true;
                JSONArray agreementCheckList = AgreementUIActivity.this.isAllChecked(this.index);
                if (agreementCheckList != null) {
                    ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_CHECKED_LIST_PROPERTY, agreementCheckList.toString());
                    ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                    AgreementUIActivity.this.destroyParentActivity = false;
                    AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                    return;
                }
                return;
            }
            this.superChecked = false;
        }
    }

    class InnerTextBox {
        RelativeLayout layout;

        public InnerTextBox(Context context, String message, int index) {
            this.layout = new RelativeLayout(context);
            ViewGroup.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
            this.layout.setLayoutParams(layoutParams);
            this.layout.setId(index);
            RelativeLayout.LayoutParams edge1Params = new RelativeLayout.LayoutParams(-2, -2);
            edge1Params.addRule(10);
            edge1Params.addRule(9);
            ImageView edge1View = new ImageView(context);
            edge1View.setLayoutParams(edge1Params);
            edge1View.setImageBitmap(AgreementUIActivity.this.board_textbox_edge_1_Bitmap);
            edge1View.setId(index + 1);
            this.layout.addView(edge1View);
            RelativeLayout.LayoutParams edge2Params = new RelativeLayout.LayoutParams(-2, -2);
            edge2Params.addRule(10);
            edge2Params.addRule(11);
            ImageView edge2View = new ImageView(context);
            edge2View.setLayoutParams(edge2Params);
            edge2View.setImageBitmap(AgreementUIActivity.this.board_textbox_edge_2_Bitmap);
            edge2View.setId(index + 2);
            this.layout.addView(edge2View);
            RelativeLayout.LayoutParams edge3Params = new RelativeLayout.LayoutParams(-2, -2);
            edge3Params.addRule(12);
            edge3Params.addRule(9);
            ImageView edge3View = new ImageView(context);
            edge3View.setLayoutParams(edge3Params);
            edge3View.setImageBitmap(AgreementUIActivity.this.board_textbox_edge_3_Bitmap);
            edge3View.setId(index + 3);
            this.layout.addView(edge3View);
            RelativeLayout.LayoutParams edge4Params = new RelativeLayout.LayoutParams(-2, -2);
            edge4Params.addRule(12);
            edge4Params.addRule(11);
            View imageView = new ImageView(context);
            imageView.setLayoutParams(edge4Params);
            imageView.setImageBitmap(AgreementUIActivity.this.board_textbox_edge_4_Bitmap);
            imageView.setId(index + 4);
            this.layout.addView(imageView);
            RelativeLayout.LayoutParams centertopParams = new RelativeLayout.LayoutParams(-1, -2);
            centertopParams.addRule(10);
            centertopParams.addRule(1, edge1View.getId());
            centertopParams.addRule(0, edge2View.getId());
            ImageView centertopView = new ImageView(context);
            centertopView.setLayoutParams(centertopParams);
            centertopView.setImageBitmap(AgreementUIActivity.this.board_textbox_center_top_Bitmap);
            centertopView.setScaleType(ScaleType.FIT_XY);
            centertopView.setId(index + 5);
            this.layout.addView(centertopView);
            RelativeLayout.LayoutParams centerdownParams = new RelativeLayout.LayoutParams(-1, -2);
            centerdownParams.addRule(12);
            centerdownParams.addRule(1, edge3View.getId());
            centerdownParams.addRule(0, imageView.getId());
            ImageView centerdownView = new ImageView(context);
            centerdownView.setLayoutParams(centerdownParams);
            centerdownView.setImageBitmap(AgreementUIActivity.this.board_textbox_center_down_Bitmap);
            centerdownView.setScaleType(ScaleType.FIT_XY);
            centerdownView.setId(index + 6);
            this.layout.addView(centerdownView);
            layoutParams = new RelativeLayout.LayoutParams(-2, -1);
            layoutParams.addRule(9);
            layoutParams.addRule(3, edge1View.getId());
            layoutParams.addRule(2, edge3View.getId());
            imageView = new ImageView(context);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageBitmap(AgreementUIActivity.this.board_textbox_side_left_Bitmap);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setId(index + 7);
            this.layout.addView(imageView);
            layoutParams = new RelativeLayout.LayoutParams(-2, -1);
            layoutParams.addRule(11);
            layoutParams.addRule(3, edge2View.getId());
            layoutParams.addRule(2, imageView.getId());
            imageView = new ImageView(context);
            imageView.setLayoutParams(layoutParams);
            imageView.setImageBitmap(AgreementUIActivity.this.board_textbox_side_right_Bitmap);
            imageView.setScaleType(ScaleType.FIT_XY);
            imageView.setId(index + 8);
            this.layout.addView(imageView);
            RelativeLayout.LayoutParams centerParams = new RelativeLayout.LayoutParams(-1, -1);
            centerParams.addRule(13);
            centerParams.addRule(1, imageView.getId());
            centerParams.addRule(0, imageView.getId());
            centerParams.addRule(3, centertopView.getId());
            centerParams.addRule(2, centerdownView.getId());
            imageView = new ScrollView(context);
            imageView.setLayoutParams(centerParams);
            imageView.setId(index + 9);
            imageView.setSmoothScrollingEnabled(true);
            imageView.setBackgroundColor(AgreementUIActivity.this.innerTextboxBackgroundColor);
            imageView.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == 1) {
                        AgreementUIActivity.this.verticalScrollView.requestDisallowInterceptTouchEvent(false);
                        AgreementUIActivity.this.horizontalScrollView.requestDisallowInterceptTouchEvent(false);
                    } else {
                        AgreementUIActivity.this.verticalScrollView.requestDisallowInterceptTouchEvent(true);
                        AgreementUIActivity.this.horizontalScrollView.requestDisallowInterceptTouchEvent(true);
                    }
                    return false;
                }
            });
            TextView centerTV = new TextView(context);
            centerTV.setLayoutParams(new LayoutParams(-1, -1));
            centerTV.setText(Html.fromHtml(message));
            centerTV.setTextColor(AgreementUIActivity.this.textboxFontColor);
            centerTV.setTextSize(AgreementUIActivity.this.fontSize(20.0f));
            centerTV.setId(index + 10);
            imageView.addView(centerTV);
            this.layout.addView(imageView);
        }

        public ViewGroup getLayout() {
            return this.layout;
        }
    }

    class OuterTextBox {
        RelativeLayout.LayoutParams agreebtnParams;
        AgreementBtn agreementBtn;
        InnerTextBox innerTextBox;
        RelativeLayout.LayoutParams innerTextBoxParams;
        RelativeLayout layout;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        TextView linkTV;
        RelativeLayout.LayoutParams linkTVParams;
        TextFitTextView titleTV;
        RelativeLayout.LayoutParams titleTVParams;

        public OuterTextBox(Context context, int type, final String title, String message, String positiveAgreementStr, String negativeAgreementStr, String detailStr, String url, int index) {
            this.layout = new RelativeLayout(context);
            this.layout.setLayoutParams(this.layoutParams);
            this.layout.setId(index);
            this.titleTV = new TextFitTextView(context);
            this.titleTV.setId(index + 1);
            this.linkTV = new TextView(context);
            this.linkTV.setId(index + 2);
            this.titleTVParams = new RelativeLayout.LayoutParams(-2, -2);
            this.titleTVParams.addRule(10);
            this.titleTVParams.addRule(9);
            this.titleTVParams.addRule(0, this.linkTV.getId());
            this.titleTVParams.leftMargin = AgreementUIActivity.this.xDP(10);
            this.titleTV.setTextColor(AgreementUIActivity.this.titleFontColor);
            this.titleTV.setTextSize(1, AgreementUIActivity.this.fontSize(26.0f));
            this.titleTV.setText(title);
            this.titleTV.setLayoutParams(this.titleTVParams);
            this.titleTV.setMaxLines(1);
            this.titleTV.setFitTextToBox(Boolean.valueOf(true), AgreementUIActivity.this.logoBitmap.getHeight() - AgreementUIActivity.this.yDP(10));
            this.layout.addView(this.titleTV);
            this.linkTVParams = new RelativeLayout.LayoutParams(-2, -2);
            this.linkTVParams.addRule(10);
            this.linkTVParams.addRule(11);
            this.linkTVParams.topMargin = AgreementUIActivity.this.yDP(16);
            this.linkTVParams.rightMargin = AgreementUIActivity.this.xDP(10);
            this.linkTV.setTextColor(AgreementUIActivity.this.linkFontColor);
            this.linkTV.setTextSize(AgreementUIActivity.this.fontSize(20.0f));
            this.linkTV.setText(Html.fromHtml("<u>" + detailStr + "</u>"));
            this.linkTV.setLayoutParams(this.linkTVParams);
            final String str = url;
            this.linkTV.setOnClickListener(new OnClickListener() {
                public void onClick(View paramView) {
                    AgreementUIActivity.this.showDialog(title, str);
                }
            });
            this.layout.addView(this.linkTV);
            this.agreebtnParams = new RelativeLayout.LayoutParams(-1, -2);
            this.agreebtnParams.addRule(12);
            this.agreebtnParams.addRule(14);
            this.agreementBtn = new AgreementBtn(context, type, positiveAgreementStr, negativeAgreementStr, index + 100);
            this.agreementBtn.setLayoutParams(this.agreebtnParams);
            this.layout.addView(this.agreementBtn);
            this.innerTextBoxParams = new RelativeLayout.LayoutParams(-1, -1);
            this.innerTextBoxParams.addRule(3, this.titleTV.getId());
            this.innerTextBoxParams.addRule(2, this.agreementBtn.getId());
            this.innerTextBoxParams.topMargin = AgreementUIActivity.this.yDP(20);
            this.innerTextBox = new InnerTextBox(context, message, index + SelectTarget.TARGETING_SUCCESS);
            this.innerTextBox.getLayout().setLayoutParams(this.innerTextBoxParams);
            this.layout.addView(this.innerTextBox.getLayout());
        }

        public RelativeLayout getLayout() {
            return this.layout;
        }

        public void setOrientationChange(int orientation) {
            switch (orientation) {
                case o.b /*2*/:
                    this.layout.getLayoutParams().width = AgreementUIActivity.this.outerTextBoxHorizontalSize;
                    this.layout.getLayoutParams().height = -1;
                    return;
                default:
                    this.layout.getLayoutParams().width = -1;
                    this.layout.getLayoutParams().height = AgreementUIActivity.this.outerTextBoxVerticalSize;
                    return;
            }
        }
    }

    protected class TextFitTextView extends TextView {
        boolean fit = false;
        int maxHeight = -1;

        public TextFitTextView(Context context) {
            super(context);
        }

        public TextFitTextView(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public TextFitTextView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        public void setFitTextToBox(Boolean fit) {
            setFitTextToBox(fit, -1);
        }

        public void setFitTextToBox(Boolean fit, int maxHeight) {
            this.fit = fit.booleanValue();
            this.maxHeight = maxHeight;
        }

        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (this.fit) {
                _shrinkToFit();
            }
        }

        protected void _shrinkToFit() {
            int lineBounds = getLineBounds(0, new Rect());
            float size = getTextSize();
            if (this.maxHeight > 0 && lineBounds > this.maxHeight) {
                setTextSize(0, size - 2.0f);
                _shrinkToFit();
            }
        }
    }

    class WebViewCallBack extends WebViewClient {
        boolean isProgressingScheme = false;
        ProgressDialog webProgressDialog = null;

        WebViewCallBack() {
        }

        public void onPageStarted(final WebView view, String url, Bitmap favicon) {
            AgreementUIActivity.this.logger.d("onPageStarted url : " + url);
            AgreementUIActivity.this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    if (!AgreementUIActivity.this.webViewTimeoutFlag) {
                        return;
                    }
                    if (WebViewCallBack.this.webProgressDialog == null || !WebViewCallBack.this.webProgressDialog.isShowing()) {
                        WebViewCallBack.this.webProgressDialog = new ProgressDialog(AgreementUIActivity.this);
                        WebViewCallBack.this.webProgressDialog.requestWindowFeature(1);
                        WebViewCallBack.this.webProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                        WebViewCallBack.this.webProgressDialog.setMessage(AgreementUIActivity.this.tm.getLoadingText());
                        WebViewCallBack.this.webProgressDialog.setCancelable(true);
                        WebViewCallBack.this.webProgressDialog.show();
                    }
                }
            }, 1300);
            AgreementUIActivity.this.mHandler.postDelayed(new Runnable() {
                public void run() {
                    if (AgreementUIActivity.this.webViewTimeoutFlag) {
                        int localVersion;
                        AgreementUIActivity.this.logger.d("webViewTimeoutFlag : " + AgreementUIActivity.this.webViewTimeoutFlag);
                        try {
                            localVersion = Integer.valueOf(ActiveUserData.get(DATA_INDEX.LOCAL_AGREEMENT_VERSION)).intValue();
                        } catch (Exception e) {
                            localVersion = 0;
                        }
                        AgreementUIActivity.this.logger.d("webViewTimeout runnable agreement_version : " + AgreementUIActivity.this.agreement_version);
                        AgreementUIActivity.this.logger.d("webViewTimeout runnable localVersion : " + localVersion);
                        if (AgreementUIActivity.this.agreement_version != localVersion) {
                            AgreementUIActivity.this.logger.d("agreement_version & localVersion are not equals, pass Agreement UI");
                            if (WebViewCallBack.this.webProgressDialog != null && WebViewCallBack.this.webProgressDialog.isShowing()) {
                                WebViewCallBack.this.webProgressDialog.dismiss();
                            }
                            AgreementUIActivity.this.destroyParentActivity = false;
                            AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                        } else if (TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.LOCAL_AGREEMENT_FILENAME))) {
                            AgreementUIActivity.this.logger.d("Local Data is Empty.");
                            if (WebViewCallBack.this.webProgressDialog != null && WebViewCallBack.this.webProgressDialog.isShowing()) {
                                WebViewCallBack.this.webProgressDialog.dismiss();
                            }
                            if (TextUtils.equals(a.d, ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY))) {
                                AgreementUIActivity.this.logger.d("AGREEMENT_VERSION_PROPERTY - ZERO, close Agreement UI");
                                AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(-1);
                                return;
                            }
                            AgreementUIActivity.this.logger.d("AGREEMENT_VERSION_PROPERTY - Exist, pass Agreement UI");
                            AgreementUIActivity.this.destroyParentActivity = false;
                            AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                        } else {
                            AgreementUIActivity.this.logger.d("loading Local Data.");
                            view.loadUrl("file:///android_asset/" + ActiveUserData.get(DATA_INDEX.LOCAL_AGREEMENT_FILENAME));
                        }
                    }
                }
            }, 10000);
            super.onPageStarted(view, url, favicon);
        }

        public void onLoadResource(WebView view, String url) {
            AgreementUIActivity.this.logger.d("onLoadResource url : " + url);
            super.onLoadResource(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            AgreementUIActivity.this.logger.d("onPageFinished url : " + url);
            AgreementUIActivity.this.webViewTimeoutFlag = false;
            if (AgreementUIActivity.this.webViewRetryDialog != null && AgreementUIActivity.this.webViewRetryDialog.isShowing()) {
                AgreementUIActivity.this.webViewRetryDialog.dismiss();
                AgreementUIActivity.this.webViewRetryDialog = null;
            }
            if (this.webProgressDialog != null && this.webProgressDialog.isShowing()) {
                this.webProgressDialog.dismiss();
            }
            super.onPageFinished(view, url);
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Exception e;
            JSONObject agreement_ex_param_json;
            int isAgreeSMS;
            String str;
            String str2;
            AgreementUIActivity.this.logger.d("shouldOverrideUrlLoading url : " + url);
            if (url == null) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            Uri uri = Uri.parse(url);
            if (!TextUtils.equals("c2s", uri.getScheme()) || !TextUtils.equals("activeuser", uri.getHost()) || this.isProgressingScheme) {
                return super.shouldOverrideUrlLoading(view, url);
            }
            this.isProgressingScheme = true;
            String queryParameterName = null;
            try {
                if (!TextUtils.isEmpty(uri.getQueryParameter(Gateway.REQUEST_AGREEMENT))) {
                    queryParameterName = Gateway.REQUEST_AGREEMENT;
                } else if (TextUtils.isEmpty(uri.getQueryParameter("agreement_ex"))) {
                    queryParameterName = null;
                } else {
                    queryParameterName = "agreement_ex";
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                AgreementUIActivity.this.logger.d("queryParameterName : " + queryParameterName);
                if (TextUtils.equals(Gateway.REQUEST_AGREEMENT, queryParameterName)) {
                    ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY, String.valueOf(AgreementUIActivity.this.agreement_version));
                    ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_VERSION_DATA_PROPERTY, url);
                    ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                    if (TextUtils.isEmpty(AgreementUIActivity.this.agreement_ex_url)) {
                        AgreementUIActivity.this.destroyParentActivity = false;
                        AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                        return true;
                    }
                    this.isProgressingScheme = false;
                    view.loadUrl(AgreementUIActivity.this.agreement_ex_url);
                    return true;
                } else if (!TextUtils.equals("agreement_ex", queryParameterName)) {
                    return super.shouldOverrideUrlLoading(view, url);
                } else {
                    try {
                        try {
                            String agreement_ex_param_decodedData = new String(Base64.decode(URLDecoder.decode(uri.getQueryParameter(queryParameterName), "UTF-8"), 4));
                            try {
                                agreement_ex_param_json = new JSONObject(agreement_ex_param_decodedData);
                            } catch (Exception e3) {
                                e2 = e3;
                                String str3 = agreement_ex_param_decodedData;
                                e2.printStackTrace();
                                agreement_ex_param_json = null;
                                if (agreement_ex_param_json != null) {
                                    isAgreeSMS = agreement_ex_param_json.optInt("sms");
                                    str = ActiveUserProperties.AGREEMENT_SMS_PROPERTY;
                                    str2 = isAgreeSMS == 1 ? a.e : isAgreeSMS == 0 ? a.d : ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN;
                                    ActiveUserProperties.setProperty(str, str2);
                                    ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_EX_SCHEME_DATA_PROPERTY, url);
                                    ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                                } else {
                                    ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_SMS_PROPERTY, ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN);
                                    ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                                }
                                AgreementUIActivity.this.destroyParentActivity = false;
                                AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                                return true;
                            }
                        } catch (Exception e4) {
                            e2 = e4;
                            e2.printStackTrace();
                            agreement_ex_param_json = null;
                            if (agreement_ex_param_json != null) {
                                ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_SMS_PROPERTY, ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN);
                                ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                            } else {
                                isAgreeSMS = agreement_ex_param_json.optInt("sms");
                                str = ActiveUserProperties.AGREEMENT_SMS_PROPERTY;
                                if (isAgreeSMS == 1) {
                                    if (isAgreeSMS == 0) {
                                    }
                                }
                                ActiveUserProperties.setProperty(str, str2);
                                ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_EX_SCHEME_DATA_PROPERTY, url);
                                ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                            }
                            AgreementUIActivity.this.destroyParentActivity = false;
                            AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                            return true;
                        }
                        if (agreement_ex_param_json != null) {
                            isAgreeSMS = agreement_ex_param_json.optInt("sms");
                            str = ActiveUserProperties.AGREEMENT_SMS_PROPERTY;
                            if (isAgreeSMS == 1) {
                            }
                            ActiveUserProperties.setProperty(str, str2);
                            ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_EX_SCHEME_DATA_PROPERTY, url);
                            ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                        } else {
                            ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_SMS_PROPERTY, ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN);
                            ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                        }
                    } catch (Exception e22) {
                        e22.printStackTrace();
                        ActiveUserProperties.setProperty(ActiveUserProperties.AGREEMENT_SMS_PROPERTY, ActiveUserProperties.AGREEMENT_SMS_VALUE_UNKNOWN);
                        ActiveUserProperties.storeProperties(AgreementUIActivity.this);
                    }
                    AgreementUIActivity.this.destroyParentActivity = false;
                    AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                    return true;
                }
            } catch (Exception e222) {
                e222.printStackTrace();
                return super.shouldOverrideUrlLoading(view, url);
            }
        }
    }

    enum WebViewState {
        AGREEMENT,
        AGREEMENT_EX
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.tm = TermsManager.getInstance(this);
        this.mHandler = new Handler();
        this.mBackKeyHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0) {
                    AgreementUIActivity.this.backKeyFlag = false;
                }
                super.handleMessage(msg);
            }
        };
        this.logger = LoggerGroup.createLogger(InstallService.TAG, this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.displayWidth = displayMetrics.widthPixels;
        this.displayHeight = displayMetrics.heightPixels;
        this.DP = displayMetrics.density;
        float xdpi = displayMetrics.xdpi;
        float ydpi = displayMetrics.ydpi;
        this.xDP = xdpi / 160.0f;
        this.yDP = ydpi / 160.0f;
        float x_inch = ((float) this.displayWidth) / xdpi;
        float y_inch = ((float) this.displayHeight) / ydpi;
        double display = Math.sqrt((double) ((x_inch * x_inch) + (y_inch * y_inch)));
        this.fontDP = this.DP;
        if (this.displayHeight < 500 && this.displayWidth < 500) {
            this.fontDP = 1.7f;
        } else if (this.fontDP > OfferwallDefine.DEFAULT_HDIP_DENSITY_SCALE) {
            this.fontDP = OfferwallDefine.DEFAULT_HDIP_DENSITY_SCALE;
        }
        if (display > 10.0d) {
            this.xDP *= 0.85f;
            this.yDP *= 0.85f;
            this.fontDP *= 0.85f;
        } else if (display > 7.0d) {
            this.xDP *= 0.8f;
            this.yDP *= 0.8f;
            this.fontDP *= 0.9f;
        } else if (display > 6.0d) {
            this.xDP *= 0.75f;
            this.yDP *= 0.75f;
            this.fontDP *= 0.95f;
        } else if (display > 5.0d) {
            this.xDP *= 0.7f;
            this.yDP *= 0.7f;
            this.fontDP *= 1.0f;
        } else if (display > 4.75d) {
            this.xDP *= 0.65f;
            this.yDP *= 0.65f;
            this.fontDP *= 1.15f;
        } else if (display > 4.5d) {
            this.xDP *= 0.6f;
            this.yDP *= 0.6f;
            this.fontDP *= 1.2f;
        } else if (display > 4.0d) {
            this.xDP *= 0.55f;
            this.yDP *= 0.55f;
            this.fontDP *= 1.3f;
        } else {
            this.xDP *= 0.5f;
            this.yDP *= 0.5f;
            this.fontDP *= 1.35f;
        }
        this.termsDataLength = this.tm.getData().length;
        this.colorType = getIntent().getIntExtra("colorType", 0);
        this.agreement_version = getIntent().getIntExtra(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY, -1);
        this.url = getIntent().getStringExtra(a.g);
        this.agreement_ex_url = getIntent().getStringExtra("agreement_ex_url");
        switch (this.colorType) {
            case o.a /*1*/:
                this.bodyBackgroundColor = Color.rgb(34, 34, 34);
                this.centerLineColor = Color.rgb(17, 17, 17);
                this.innerTextboxBackgroundColor = Color.rgb(17, 17, 17);
                this.titleFontColor = Color.rgb(188, 188, 188);
                this.linkFontColor = Color.rgb(68, 124, 193);
                this.textboxFontColor = Color.rgb(153, 153, 153);
                this.agreeBtnDefaultFontColor = Color.rgb(207, 207, 207);
                this.agreeBtnSelectFontColor = Color.rgb(255, 255, 255);
                break;
            default:
                this.bodyBackgroundColor = Color.rgb(232, 232, 232);
                this.centerLineColor = Color.rgb(216, 216, 216);
                this.innerTextboxBackgroundColor = Color.rgb(216, 216, 216);
                this.titleFontColor = Color.rgb(68, 68, 68);
                this.linkFontColor = Color.rgb(205, 32, 39);
                this.textboxFontColor = Color.rgb(102, 102, 102);
                this.agreeBtnDefaultFontColor = Color.rgb(68, 124, 193);
                this.agreeBtnSelectFontColor = Color.rgb(255, 255, 255);
                break;
        }
        setTheme(16973841);
        this.mainLayout = createUI();
        setContentView(this.mainLayout);
    }

    protected void onStart() {
        super.onStart();
        if (this.userAgreeAnimation == null) {
            this.userAgreeAnimation = new UserAgreeAnimation(this, this.mainLayout, this.xDP, this.yDP, this.displayWidth, this.displayHeight, this.board_top_m_Bitmap.getHeight());
        }
        this.userAgreeAnimation.openAgreementUI();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return super.onKeyDown(keyCode, event);
        }
        if (!this.backKeyFlag) {
            Toast.makeText(this, this.tm.getBackKeyText(), 0).show();
            this.backKeyFlag = true;
            this.mBackKeyHandler.sendEmptyMessageDelayed(0, 2000);
            return false;
        } else if (this.userAgreeAnimation != null) {
            this.userAgreeAnimation.closeAgreementUI(this.destroyParentActivity ? -1 : 0);
            return false;
        } else {
            finish();
            return false;
        }
    }

    private float fontSize(float size) {
        return size / this.fontDP;
    }

    private int xDP(int num) {
        return (int) (((float) num) * this.xDP);
    }

    private int yDP(int num) {
        return (int) (((float) num) * this.yDP);
    }

    private ViewGroup createUI() {
        loadBitmaps(ActiveUserData.get(DATA_INDEX.RESOURCE_OFFSET));
        RelativeLayout mainLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams mainParams = new RelativeLayout.LayoutParams(-1, -1);
        mainParams.addRule(13);
        mainLayout.setLayoutParams(mainParams);
        mainLayout.setPadding(xDP(10), yDP(10), xDP(10), yDP(10));
        this.verticalScrollView = new ScrollView(this);
        this.verticalScrollView.setLayoutParams(this.FILL_PARAMS);
        this.verticalScrollView.setSmoothScrollingEnabled(true);
        this.horizontalScrollView = new HorizontalScrollView(this);
        this.horizontalScrollView.setLayoutParams(this.FILL_PARAMS);
        this.horizontalScrollView.setSmoothScrollingEnabled(true);
        ViewGroup boardTop = createBoardTop();
        ViewGroup boardDown = createBoardDown();
        this.bodyLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams bodyParams = new RelativeLayout.LayoutParams(-1, -1);
        bodyParams.setMargins(xDP(10), -yDP(5), xDP(10), -yDP(5));
        bodyParams.addRule(3, boardTop.getId());
        bodyParams.addRule(2, boardDown.getId());
        this.bodyLayout.setId(PeppermintType.HUB_E_INVALID_URL);
        this.bodyLayout.setLayoutParams(bodyParams);
        this.bodyLayout.setBackgroundColor(this.bodyBackgroundColor);
        this.bodyLayout.addView(createWebView());
        mainLayout.addView(this.bodyLayout);
        mainLayout.addView(boardTop);
        mainLayout.addView(boardDown);
        return mainLayout;
    }

    private WebView createWebView() {
        final WebView web = new WebView(this);
        web.setLayoutParams(this.FILL_PARAMS);
        web.getSettings().setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewCallBack());
        web.getSettings().setDefaultTextEncodingName("UTF-8");
        web.getSettings().setCacheMode(2);
        web.setBackgroundColor(this.innerTextboxBackgroundColor);
        Runnable webViewRun;
        if (TextUtils.isEmpty(this.url) && TextUtils.isEmpty(this.agreement_ex_url)) {
            this.logger.d("url is empty, loading Local Data");
            if (TextUtils.isEmpty(ActiveUserData.get(DATA_INDEX.LOCAL_AGREEMENT_FILENAME))) {
                this.logger.d("loading Local Data failed, Local Data is empty.");
                if (TextUtils.equals(a.d, ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY))) {
                    this.logger.d("AGREEMENT_VERSION_PROPERTY - ZERO, close Agreement UI");
                    if (this.userAgreeAnimation != null) {
                        this.userAgreeAnimation.closeAgreementUI(-1);
                    } else {
                        finish();
                    }
                } else {
                    this.logger.d("AGREEMENT_VERSION_PROPERTY - Exist, pass Agreement UI");
                    this.destroyParentActivity = false;
                    if (this.userAgreeAnimation != null) {
                        this.userAgreeAnimation.closeAgreementUI(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                    } else {
                        finish();
                    }
                }
            } else {
                webViewRun = new Runnable() {
                    public void run() {
                        web.loadUrl("file:///android_asset/" + ActiveUserData.get(DATA_INDEX.LOCAL_AGREEMENT_FILENAME));
                    }
                };
                if (this.mHandler != null) {
                    this.mHandler.postDelayed(webViewRun, 0);
                }
            }
        } else {
            webViewRun = new Runnable() {
                public void run() {
                    if (!TextUtils.isEmpty(AgreementUIActivity.this.url)) {
                        web.loadUrl(AgreementUIActivity.this.url);
                    } else if (TextUtils.isEmpty(AgreementUIActivity.this.agreement_ex_url)) {
                        web.loadUrl(AgreementUIActivity.this.url);
                    } else {
                        web.loadUrl(AgreementUIActivity.this.agreement_ex_url);
                    }
                }
            };
            if (this.mHandler != null) {
                this.mHandler.postDelayed(webViewRun, 0);
            }
        }
        return web;
    }

    private AlertDialog getRetryAgreementVersionDialog(Context context, final WebView webView, final ProgressDialog progressDialog) {
        TermsManager tm = TermsManager.getInstance(context);
        Builder builder = new Builder(context);
        builder.setCancelable(false);
        builder.setTitle(tm.getErrorTitleText());
        builder.setMessage(tm.getErrorMsgText());
        builder.setPositiveButton(tm.getRetryText(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AgreementUIActivity.this.logger.d("getRetryAgreementVersionDialog : retry");
                final WebView webView = webView;
                final ProgressDialog progressDialog = progressDialog;
                Runnable run = new Runnable() {
                    public void run() {
                        if (AgreementUIActivity.this.webViewTimeoutFlag) {
                            AgreementUIActivity.this.webViewRetryDialog = AgreementUIActivity.this.getRetryAgreementVersionDialog(AgreementUIActivity.this, webView, progressDialog);
                            AgreementUIActivity.this.webViewRetryDialog.show();
                        }
                    }
                };
                if (AgreementUIActivity.this.mHandler != null) {
                    AgreementUIActivity.this.mHandler.postDelayed(run, 10000);
                }
                webView.reload();
            }
        });
        builder.setNegativeButton(tm.getQuitText(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                AgreementUIActivity.this.logger.d("getRetryAgreementVersionDialog : quit");
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                AgreementUIActivity.this.userAgreeAnimation.closeAgreementUI(-1);
            }
        });
        return builder.create();
    }

    private String readLocalFileData(String fileName) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(getResources().getAssets().open(fileName)));
            while (true) {
                String s = r.readLine();
                if (s == null) {
                    break;
                }
                builder.append(s);
            }
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = builder.toString();
        this.logger.d("Local Agreement File Data : " + data);
        return data;
    }

    private void setOuterTextSize() {
        int orientation = getResources().getConfiguration().orientation;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.displayWidth = displayMetrics.widthPixels;
        this.displayHeight = displayMetrics.heightPixels;
        switch (orientation) {
            case o.b /*2*/:
                if (this.termsDataLength == 1) {
                    this.outerTextBoxVerticalSize = (this.displayWidth - (this.board_top_m_Bitmap.getHeight() * 2)) - xDP(80);
                    this.outerTextBoxHorizontalSize = this.displayWidth - xDP(80);
                    return;
                } else if (this.termsDataLength == 2) {
                    this.outerTextBoxVerticalSize = ((this.displayWidth - (this.board_top_m_Bitmap.getHeight() * 2)) / 2) - xDP(60);
                    this.outerTextBoxHorizontalSize = (this.displayWidth / 2) - xDP(60);
                    return;
                } else {
                    this.outerTextBoxVerticalSize = (((this.displayWidth - (this.board_top_m_Bitmap.getHeight() * 2)) / 2) - xDP(60)) - xDP(30);
                    this.outerTextBoxHorizontalSize = ((this.displayWidth / 2) - xDP(60)) - xDP(30);
                    return;
                }
            default:
                if (this.termsDataLength == 1) {
                    this.outerTextBoxVerticalSize = (this.displayHeight - (this.board_top_m_Bitmap.getHeight() * 2)) - yDP(80);
                    this.outerTextBoxHorizontalSize = this.displayHeight - yDP(80);
                    return;
                } else if (this.termsDataLength == 2) {
                    this.outerTextBoxVerticalSize = ((this.displayHeight - (this.board_top_m_Bitmap.getHeight() * 2)) / 2) - yDP(60);
                    this.outerTextBoxHorizontalSize = (this.displayHeight / 2) - yDP(60);
                    return;
                } else {
                    this.outerTextBoxVerticalSize = (((this.displayHeight - (this.board_top_m_Bitmap.getHeight() * 2)) / 2) - yDP(60)) - yDP(30);
                    this.outerTextBoxHorizontalSize = ((this.displayHeight / 2) - yDP(60)) - yDP(30);
                    return;
                }
        }
    }

    private ViewGroup createBoardTop() {
        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layout.setLayoutParams(layoutParams);
        layout.setId(UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(-2, -2);
        leftParams.addRule(9);
        ImageView leftView = new ImageView(this);
        leftView.setLayoutParams(leftParams);
        leftView.setImageBitmap(this.board_top_l_Bitmap);
        leftView.setId(1001);
        layout.addView(leftView);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(-2, -2);
        rightParams.addRule(11);
        ImageView rightView = new ImageView(this);
        rightView.setLayoutParams(rightParams);
        rightView.setImageBitmap(this.board_top_r_Bitmap);
        rightView.setId(1002);
        layout.addView(rightView);
        RelativeLayout.LayoutParams middleParams = new RelativeLayout.LayoutParams(-1, -2);
        middleParams.addRule(1, leftView.getId());
        middleParams.addRule(0, rightView.getId());
        ImageView middleView = new ImageView(this);
        middleView.setLayoutParams(middleParams);
        middleView.setImageBitmap(this.board_top_m_Bitmap);
        middleView.setScaleType(ScaleType.FIT_XY);
        layout.addView(middleView, middleParams);
        RelativeLayout.LayoutParams logoParams = new RelativeLayout.LayoutParams(-2, -2);
        logoParams.addRule(13);
        ImageView topLogoView = new ImageView(this);
        topLogoView.setImageBitmap(this.board_top_logo_Bitmap);
        topLogoView.setLayoutParams(logoParams);
        layout.addView(topLogoView);
        return layout;
    }

    private ViewGroup createBoardDown() {
        RelativeLayout layout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(12);
        layout.setLayoutParams(layoutParams);
        layout.setId(PeppermintType.HUB_E_UNKNOWN);
        RelativeLayout.LayoutParams leftParams = new RelativeLayout.LayoutParams(-2, -2);
        leftParams.addRule(9);
        ImageView leftView = new ImageView(this);
        leftView.setLayoutParams(leftParams);
        leftView.setImageBitmap(this.board_down_l_Bitmap);
        leftView.setId(PeppermintType.HUB_E_NOT_CONNECT_DB);
        layout.addView(leftView);
        RelativeLayout.LayoutParams rightParams = new RelativeLayout.LayoutParams(-2, -2);
        rightParams.addRule(11);
        ImageView rightView = new ImageView(this);
        rightView.setLayoutParams(rightParams);
        rightView.setImageBitmap(this.board_down_r_Bitmap);
        rightView.setId(1102);
        layout.addView(rightView);
        RelativeLayout.LayoutParams middleParams = new RelativeLayout.LayoutParams(-1, -2);
        middleParams.addRule(1, leftView.getId());
        middleParams.addRule(0, rightView.getId());
        ImageView middleView = new ImageView(this);
        middleView.setLayoutParams(middleParams);
        middleView.setImageBitmap(this.board_down_m_Bitmap);
        middleView.setScaleType(ScaleType.FIT_XY);
        layout.addView(middleView, middleParams);
        RelativeLayout.LayoutParams logoParams = new RelativeLayout.LayoutParams(-2, -2);
        logoParams.addRule(13);
        ImageView topLogoView = new ImageView(this);
        topLogoView.setImageBitmap(this.board_down_logo_Bitmap);
        topLogoView.setLayoutParams(logoParams);
        layout.addView(topLogoView);
        return layout;
    }

    private JSONArray isAllChecked(int index) {
        JSONArray agreementCheckList = new JSONArray();
        boolean loof = true;
        int currentIndex = index;
        int i = (currentIndex - this.firstIndex) / UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS;
        while (i < this.termsDataLength) {
            if (((AgreementBtn) findViewById(currentIndex)).superChecked) {
                try {
                    agreementCheckList.put(i, 0);
                    if (i != this.termsDataLength - 1) {
                        currentIndex += UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS;
                    } else if (!loof) {
                        return agreementCheckList;
                    } else {
                        loof = false;
                        currentIndex = (index % UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS) + this.firstIndex;
                        i = -1;
                    }
                    i++;
                } catch (JSONException e) {
                    return null;
                }
            }
            try {
                agreementCheckList.put(i, 1);
                this.verticalScrollView.smoothScrollTo((int) this.outerTextBoxList[i].getLayout().getX(), (int) this.outerTextBoxList[i].getLayout().getY());
                this.horizontalScrollView.smoothScrollTo((int) this.outerTextBoxList[i].getLayout().getX(), (int) this.outerTextBoxList[i].getLayout().getY());
                return null;
            } catch (JSONException e2) {
                return null;
            }
        }
        return agreementCheckList;
    }

    private ViewGroup createLogo() {
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(this.FILL_WRAP_PARAMS);
        ImageView imageView = new ImageView(this);
        imageView.setImageBitmap(this.logoBitmap);
        layout.addView(imageView);
        return layout;
    }

    private void loadBitmaps(String resourceOffset) {
        if (TextUtils.isEmpty(resourceOffset)) {
            resourceOffset = i.a;
        }
        if (this.logoBitmap == null) {
            this.logoBitmap = loadBitmap("common/ActiveUserImage/activeuser_logo" + resourceOffset + ".png");
        }
        if (this.closeBitmap == null) {
            this.closeBitmap = loadBitmap("common/ActiveUserImage/activeuser_close_button.png");
        }
        if (this.board_top_l_Bitmap == null) {
            this.board_top_l_Bitmap = loadBitmap("common/ActiveUserImage/00_board_top_l.png");
        }
        if (this.board_top_m_Bitmap == null) {
            this.board_top_m_Bitmap = loadBitmap("common/ActiveUserImage/00_board_top_m.png");
        }
        if (this.board_top_r_Bitmap == null) {
            this.board_top_r_Bitmap = loadBitmap("common/ActiveUserImage/00_board_top_r.png");
        }
        if (this.board_top_logo_Bitmap == null) {
            this.board_top_logo_Bitmap = loadBitmap("common/ActiveUserImage/00_board_top_logo" + resourceOffset + ".png");
        }
        if (this.board_down_l_Bitmap == null) {
            this.board_down_l_Bitmap = loadBitmap("common/ActiveUserImage/00_board_down_l.png");
        }
        if (this.board_down_m_Bitmap == null) {
            this.board_down_m_Bitmap = loadBitmap("common/ActiveUserImage/00_board_down_m.png");
        }
        if (this.board_down_r_Bitmap == null) {
            this.board_down_r_Bitmap = loadBitmap("common/ActiveUserImage/00_board_down_r.png");
        }
        if (this.board_down_logo_Bitmap == null) {
            this.board_down_logo_Bitmap = loadBitmap("common/ActiveUserImage/00_board_down_logo" + resourceOffset + ".png");
        }
        if (this.board_textbox_center_top_Bitmap == null) {
            this.board_textbox_center_top_Bitmap = loadBitmap("common/ActiveUserImage/00_board_textbox_center_top.png");
        }
        if (this.board_textbox_center_down_Bitmap == null) {
            this.board_textbox_center_down_Bitmap = loadBitmap("common/ActiveUserImage/00_board_textbox_center_down.png");
        }
        if (this.board_textbox_side_left_Bitmap == null) {
            this.board_textbox_side_left_Bitmap = loadBitmap("common/ActiveUserImage/00_board_textbox_side_left.png");
        }
        if (this.board_textbox_side_right_Bitmap == null) {
            this.board_textbox_side_right_Bitmap = loadBitmap("common/ActiveUserImage/00_board_textbox_side_right.png");
        }
        if (this.board_textbox_edge_1_Bitmap == null) {
            this.board_textbox_edge_1_Bitmap = loadBitmap("common/ActiveUserImage/00_board_textbox_edge_1.png");
        }
        if (this.board_textbox_edge_2_Bitmap == null) {
            this.board_textbox_edge_2_Bitmap = loadBitmap("common/ActiveUserImage/00_board_textbox_edge_2.png");
        }
        if (this.board_textbox_edge_3_Bitmap == null) {
            this.board_textbox_edge_3_Bitmap = loadBitmap("common/ActiveUserImage/00_board_textbox_edge_3.png");
        }
        if (this.board_textbox_edge_4_Bitmap == null) {
            this.board_textbox_edge_4_Bitmap = loadBitmap("common/ActiveUserImage/00_board_textbox_edge_4.png");
        }
        if (this.btn_default_l_Bitmap == null) {
            this.btn_default_l_Bitmap = loadBitmap("common/ActiveUserImage/00_btn_default_l.png");
        }
        if (this.btn_default_m_1_Bitmap == null) {
            this.btn_default_m_1_Bitmap = loadBitmap("common/ActiveUserImage/00_btn_default_m_1.png");
        }
        if (this.btn_default_r_Bitmap == null) {
            this.btn_default_r_Bitmap = loadBitmap("common/ActiveUserImage/00_btn_default_r.png");
        }
        if (this.btn_select_l_Bitmap == null) {
            this.btn_select_l_Bitmap = loadBitmap("common/ActiveUserImage/00_btn_select_l.png");
        }
        if (this.btn_select_m_1_Bitmap == null) {
            this.btn_select_m_1_Bitmap = loadBitmap("common/ActiveUserImage/00_btn_select_m_1.png");
        }
        if (this.btn_select_r_Bitmap == null) {
            this.btn_select_r_Bitmap = loadBitmap("common/ActiveUserImage/00_btn_select_r.png");
        }
        if (this.check_btn_Bitmap == null) {
            this.check_btn_Bitmap = loadBitmap("common/ActiveUserImage/00_check_btn.png");
        }
    }

    private Bitmap loadBitmap(String filePath) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getResources().getAssets().open(filePath));
            return Bitmap.createScaledBitmap(bitmap, xDP(bitmap.getWidth()), yDP(bitmap.getHeight()), true);
        } catch (IOException e1) {
            e1.printStackTrace();
            if (bitmap != null) {
                bitmap.recycle();
            }
            return null;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.mHandler = null;
        this.mBackKeyHandler = null;
        if (this.logoBitmap != null) {
            this.logoBitmap.recycle();
        }
        this.logoBitmap = null;
        if (this.closeBitmap != null) {
            this.closeBitmap.recycle();
        }
        this.closeBitmap = null;
        if (this.board_top_l_Bitmap != null) {
            this.board_top_l_Bitmap.recycle();
        }
        this.board_top_l_Bitmap = null;
        if (this.board_top_m_Bitmap != null) {
            this.board_top_m_Bitmap.recycle();
        }
        this.board_top_m_Bitmap = null;
        if (this.board_top_r_Bitmap != null) {
            this.board_top_r_Bitmap.recycle();
        }
        this.board_top_r_Bitmap = null;
        if (this.board_top_logo_Bitmap != null) {
            this.board_top_logo_Bitmap.recycle();
        }
        this.board_top_logo_Bitmap = null;
        if (this.board_down_l_Bitmap != null) {
            this.board_down_l_Bitmap.recycle();
        }
        this.board_down_l_Bitmap = null;
        if (this.board_down_m_Bitmap != null) {
            this.board_down_m_Bitmap.recycle();
        }
        this.board_down_m_Bitmap = null;
        if (this.board_down_r_Bitmap != null) {
            this.board_down_r_Bitmap.recycle();
        }
        this.board_down_r_Bitmap = null;
        if (this.board_down_logo_Bitmap != null) {
            this.board_down_logo_Bitmap.recycle();
        }
        this.board_down_logo_Bitmap = null;
        if (this.board_textbox_center_top_Bitmap != null) {
            this.board_textbox_center_top_Bitmap.recycle();
        }
        this.board_textbox_center_top_Bitmap = null;
        if (this.board_textbox_center_down_Bitmap != null) {
            this.board_textbox_center_down_Bitmap.recycle();
        }
        this.board_textbox_center_down_Bitmap = null;
        if (this.board_textbox_side_left_Bitmap != null) {
            this.board_textbox_side_left_Bitmap.recycle();
        }
        this.board_textbox_side_left_Bitmap = null;
        if (this.board_textbox_side_right_Bitmap != null) {
            this.board_textbox_side_right_Bitmap.recycle();
        }
        this.board_textbox_side_right_Bitmap = null;
        if (this.board_textbox_edge_1_Bitmap != null) {
            this.board_textbox_edge_1_Bitmap.recycle();
        }
        this.board_textbox_edge_1_Bitmap = null;
        if (this.board_textbox_edge_2_Bitmap != null) {
            this.board_textbox_edge_2_Bitmap.recycle();
        }
        this.board_textbox_edge_2_Bitmap = null;
        if (this.board_textbox_edge_3_Bitmap != null) {
            this.board_textbox_edge_3_Bitmap.recycle();
        }
        this.board_textbox_edge_3_Bitmap = null;
        if (this.board_textbox_edge_4_Bitmap != null) {
            this.board_textbox_edge_4_Bitmap.recycle();
        }
        this.board_textbox_edge_4_Bitmap = null;
        if (this.btn_default_l_Bitmap != null) {
            this.btn_default_l_Bitmap.recycle();
        }
        this.btn_default_l_Bitmap = null;
        if (this.btn_default_m_1_Bitmap != null) {
            this.btn_default_m_1_Bitmap.recycle();
        }
        this.btn_default_m_1_Bitmap = null;
        if (this.btn_default_r_Bitmap != null) {
            this.btn_default_r_Bitmap.recycle();
        }
        this.btn_default_r_Bitmap = null;
        if (this.btn_select_l_Bitmap != null) {
            this.btn_select_l_Bitmap.recycle();
        }
        this.btn_select_l_Bitmap = null;
        if (this.btn_select_m_1_Bitmap != null) {
            this.btn_select_m_1_Bitmap.recycle();
        }
        this.btn_select_m_1_Bitmap = null;
        if (this.btn_select_r_Bitmap != null) {
            this.btn_select_r_Bitmap.recycle();
        }
        this.btn_select_r_Bitmap = null;
        if (this.check_btn_Bitmap != null) {
            this.check_btn_Bitmap.recycle();
        }
        this.check_btn_Bitmap = null;
        if (this.destroyParentActivity) {
            UserAgreeManager.getInstance().getActivity().finish();
        }
    }

    private void showDialog(String title, String url) {
        if (this.dialog == null || !this.dialog.isShowing()) {
            this.dialog = new UserAgreeDialog(this);
            this.dialog.setTitle(title);
            this.dialog.setUrl(url);
            this.dialog.setBitmap(this.logoBitmap, this.closeBitmap);
            this.dialog.show();
        }
    }

    public void onConfigurationChanged(Configuration paramConfiguration) {
        super.onConfigurationChanged(paramConfiguration);
        if (this.dialog != null) {
            this.dialog.dismiss();
        }
        if (paramConfiguration.orientation == 1) {
            this.userAgreeAnimation.configurationChanged();
        } else if (paramConfiguration.orientation == 2) {
            this.userAgreeAnimation.configurationChanged();
        }
    }
}
