package com.com2us.module.newsbanner2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.wellbia.xigncode.util.WBTelecomUtil;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import jp.co.cyberz.fox.a.a.i;
import jp.co.dimage.android.o;

public class NewsBannerUI implements NetworkListener {
    private Activity activity;
    private int animationType = -9;
    private Bitmap bannerBitmap;
    BannerData bannerData;
    BannerData[] bannerDataArr;
    int bannerIndex = 0;
    private FrameLayout bannerLayout;
    private int bannerOffset;
    private int bannerOffsetClone;
    private int bannerPosition;
    private long bannerShowTime = 0;
    private ImageView bannerView;
    CloseBannerThread closeBannerThread;
    private Bitmap closeBitmap;
    private ImageView closeView;
    private Bitmap closedTabBitmap;
    CPI cpi;
    private float dp;
    private boolean enableUI = false;
    private int enalbeUIOnSuspend = 0;
    private boolean isBannerShown = false;
    private boolean isForcsShow = false;
    private boolean isNewBanner = false;
    private boolean isShowRequested = false;
    private boolean isTabVisible = false;
    private Logger logger;
    private LinearLayout mainLayout;
    LayoutParams mainParam = new LayoutParams(-2, -2);
    private NewsBanner newsBanner;
    private Bitmap openedTabBitmap;
    private int orientation;
    private int rollingBannerCount = 0;
    private BannerRollingThread rollingThread;
    private int screenHeight;
    private int screenWidth;
    Setting setting;
    private FrameLayout tabLayout;
    private ImageView tabView;
    LayoutParams tabViewParam = new LayoutParams(-2, -2, 17);

    private class AutoShowBannerAnimationListener extends BannerAnimationListener {
        public AutoShowBannerAnimationListener(FrameLayout bannerLayout, FrameLayout tabLayout, Bitmap bitmap) {
            super(bannerLayout, tabLayout, bitmap);
        }

        public void onAnimationStart(Animation paramAnimation) {
            super.onAnimationStart(paramAnimation);
        }

        public void onAnimationEnd(Animation paramAnimation) {
            super.onAnimationEnd(paramAnimation);
            NewsBannerUI.this.startCloseBannerThread();
        }
    }

    class BannerRollingThread implements Runnable {
        private boolean lived = false;
        Object lock = new Object();
        private boolean paused = false;
        Thread thread = null;

        public BannerRollingThread() {
            Date date = new Date();
            this.thread = new Thread(this);
            this.thread.setName("BannerRollingThread-" + this.thread.hashCode());
        }

        public void start() {
            this.thread.start();
        }

        public void resume() {
            NewsBannerUI.this.logger.v("[NewsBannerUI][BannerRollingThread][resume]" + this.thread.getName());
            synchronized (this.lock) {
                this.paused = false;
                try {
                    this.lock.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public void pause() {
            NewsBannerUI.this.logger.v("[NewsBannerUI][BannerRollingThread][pause]" + this.thread.getName());
            synchronized (this.lock) {
                this.paused = true;
            }
            this.thread.interrupt();
        }

        public void interupt() {
            this.lived = false;
            this.thread.interrupt();
        }

        public boolean isAlive() {
            return this.lived;
        }

        public boolean isInterrupted() {
            return this.thread.isInterrupted();
        }

        public void run() {
            NewsBannerUI.this.logger.v("[NewsBannerUI][BannerRollingThread]start-" + this.thread.getName());
            this.lived = true;
            while (this.lived) {
                for (int i = 0; i < NewsBannerUI.this.bannerDataArr.length; i++) {
                    try {
                        synchronized (this.lock) {
                            if (this.paused) {
                                this.lock.wait();
                            }
                        }
                        Thread.sleep(((long) NewsBannerUI.this.setting.rollingPeriod) * 1000);
                        NewsBannerUI newsBannerUI = NewsBannerUI.this;
                        NewsBannerUI newsBannerUI2 = NewsBannerUI.this;
                        int i2 = newsBannerUI2.bannerIndex + 1;
                        newsBannerUI2.bannerIndex = i2;
                        newsBannerUI.bannerIndex = i2 % NewsBannerUI.this.bannerDataArr.length;
                        NewsBannerUI.this.changeBanner(NewsBannerUI.this.bannerIndex);
                        if (NewsBannerUI.this.bannerDataArr.length - 1 > NewsBannerUI.this.rollingBannerCount) {
                            newsBannerUI = NewsBannerUI.this;
                            newsBannerUI.rollingBannerCount = newsBannerUI.rollingBannerCount + 1;
                            NewsBannerUI.this.newsBanner.requestToServer(1, NewsBannerUI.this.bannerData.pid);
                        }
                    } catch (InterruptedException e) {
                        if (!this.lived) {
                            break;
                        }
                    }
                }
            }
            NewsBannerUI.this.logger.v("[NewsBannerUI][BannerRollingThread]end-" + this.thread.getName());
        }
    }

    class CloseBannerThread implements Runnable {
        private Thread thread = new Thread(this, "CloseBannerThread");

        public CloseBannerThread() {
            this.thread.setName("CloseBannerThread-" + this.thread.hashCode());
        }

        public void start() {
            this.thread.start();
        }

        public void interrupt() {
            this.thread.interrupt();
        }

        public void run() {
            NewsBannerUI.this.logger.v("[NewsBannerUI]" + this.thread.getName() + " : start");
            try {
                Thread.sleep(NewsBannerUI.this.setting.visibleTimeAfterAutoShow * 1000);
                NewsBannerUI.this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        NewsBannerUI.this.closeTab();
                    }
                });
            } catch (InterruptedException e) {
                NewsBannerUI.this.logger.d("[NewsBannerUI][CloseBannerThread]" + e.toString());
            }
            NewsBannerUI.this.logger.v("[NewsBannerUI]" + this.thread.getName() + " : end");
        }
    }

    class WebViewCallBack extends WebViewClient {
        ProgressDialog webProgressDialog = null;
        Dialog webViewDialog = null;

        public WebViewCallBack(Dialog dialog) {
            this.webViewDialog = dialog;
        }

        public void onLoadResource(WebView view, String url) {
            if (this.webProgressDialog == null) {
                this.webProgressDialog = new ProgressDialog(NewsBannerUI.this.activity);
                this.webProgressDialog.setMessage("Loading...");
                this.webProgressDialog.setCancelable(false);
                this.webProgressDialog.show();
            }
            super.onLoadResource(view, url);
        }

        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (this.webProgressDialog != null && this.webProgressDialog.isShowing()) {
                this.webProgressDialog.dismiss();
                this.webViewDialog.show();
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return false;
        }
    }

    public NewsBannerUI(NewsBanner newsBanner, final Activity activity) {
        this.newsBanner = newsBanner;
        this.activity = activity;
        this.logger = LoggerGroup.createLogger(Constants.TAG);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.screenHeight = displayMetrics.heightPixels;
        this.screenWidth = displayMetrics.widthPixels;
        try {
            String time = NewsBannerProperties.getProperty(NewsBannerProperties.AUTO_SHOW_TIME_PROPERTY);
            if (time != null && time.trim().length() > 0) {
                this.bannerShowTime = Long.parseLong(time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.dp = displayMetrics.density;
        NewsBannerData.setApplicationScreenSize(displayMetrics.widthPixels, displayMetrics.heightPixels);
        this.logger.v("[NewsBannerUI]screenHeight=" + this.screenHeight + ", screenWidth=" + this.screenWidth);
        this.mainLayout = new LinearLayout(activity);
        activity.runOnUiThread(new Runnable() {
            public void run() {
                NewsBannerUI.this.mainLayout.setVisibility(4);
                activity.addContentView(NewsBannerUI.this.mainLayout, NewsBannerUI.this.mainParam);
            }
        });
    }

    public void initialize() {
        suspend();
        this.isShowRequested = false;
        this.enalbeUIOnSuspend = 0;
        this.animationType = -9;
        this.bannerIndex = 0;
        this.enableUI = false;
        this.isNewBanner = false;
        destroyBitmap();
    }

    public void createUI(int orientation, int bannerPosition, int offset, NewsBannerCallBack cb) {
        this.bannerPosition = bannerPosition;
        this.bannerOffset = offset;
        this.orientation = orientation;
        this.bannerOffsetClone = offset;
        this.mainLayout.removeAllViews();
        loadBitmap(bannerPosition);
        this.tabLayout = new FrameLayout(this.activity);
        this.bannerLayout = new FrameLayout(this.activity);
        this.tabView = new ImageView(this.activity);
        this.tabView.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                NewsBannerUI.this.logger.v("[NewsBannerUI]TabView onClick");
                NewsBannerUI.this.onTabClicked();
            }
        });
        this.tabView.setImageBitmap(this.closedTabBitmap);
        this.closeView = new ImageView(this.activity);
        this.closeView.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                NewsBannerUI.this.logger.v("[NewsBannerUI]CloseView onClick");
                NewsBannerUI.this.closeTab();
            }
        });
        this.closeView.setImageBitmap(this.closeBitmap);
        this.bannerView = new ImageView(this.activity);
        this.bannerView.setOnClickListener(new OnClickListener() {
            public void onClick(View paramView) {
                NewsBannerUI.this.onBannerClicked();
            }
        });
        this.bannerView.setImageBitmap(this.bannerBitmap);
        this.bannerLayout.addView(this.bannerView);
        this.bannerLayout.addView(this.closeView, new LayoutParams(-2, -2, 5));
        LayoutParams tabLayoutParam = new LayoutParams(-1, -2, 17);
        switch (bannerPosition) {
            case o.a /*1*/:
                this.mainLayout.setOrientation(1);
                this.mainParam.gravity = 49;
                this.mainLayout.addView(this.bannerLayout);
                this.mainLayout.addView(this.tabLayout, tabLayoutParam);
                break;
            case o.b /*2*/:
                this.mainLayout.setOrientation(1);
                this.mainParam.gravity = 81;
                this.mainLayout.addView(this.tabLayout, tabLayoutParam);
                this.mainLayout.addView(this.bannerLayout);
                break;
            case o.c /*3*/:
                this.mainLayout.setOrientation(0);
                this.mainParam.gravity = 53;
                this.mainLayout.addView(this.tabLayout);
                this.mainLayout.addView(this.bannerLayout);
                break;
            case o.d /*4*/:
                this.mainLayout.setOrientation(0);
                this.mainParam.gravity = 51;
                this.mainLayout.addView(this.bannerLayout);
                this.mainLayout.addView(this.tabLayout);
                break;
        }
        this.tabLayout.addView(this.tabView, this.tabViewParam);
        this.mainLayout.setVisibility(4);
    }

    private void loadBitmap(int bannerPosition) {
        StringBuilder closeBtnFileName = new StringBuilder("imgNotice_Close");
        StringBuilder inTabFileName = new StringBuilder("imgNotice_News");
        StringBuilder outTabFileName = new StringBuilder("imgNotice_News");
        String HDMark = "@2x";
        String extName = ".png";
        switch (bannerPosition) {
            case o.a /*1*/:
                inTabFileName.append("_CeilingIn");
                outTabFileName.append("_CeilingOut");
                break;
            case o.b /*2*/:
                inTabFileName.append("_GroundIn");
                outTabFileName.append("_GroundOut");
                break;
            case o.c /*3*/:
                inTabFileName.append("_In");
                outTabFileName.append("_Out");
                break;
            case o.d /*4*/:
                inTabFileName.append("_In_Left");
                outTabFileName.append("_Out_Left");
                break;
        }
        if (isHDScreen()) {
            closeBtnFileName.append("@2x");
            inTabFileName.append("@2x");
            outTabFileName.append("@2x");
        }
        if (this.closeBitmap == null) {
            this.closeBitmap = createBitmap(closeBtnFileName.append(".png").toString(), false);
        }
    }

    public void destroy() {
        hide();
        suspend();
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                NewsBannerUI.this.destroyBitmap();
            }
        });
    }

    private void destroyBitmap() {
        if (this.closeBitmap != null) {
            this.closeBitmap.recycle();
        }
        this.closeBitmap = null;
        if (this.openedTabBitmap != null) {
            this.openedTabBitmap.recycle();
        }
        this.openedTabBitmap = null;
        if (this.closedTabBitmap != null) {
            this.closedTabBitmap.recycle();
        }
        this.closedTabBitmap = null;
        if (this.bannerBitmap != null) {
            this.bannerBitmap.recycle();
        }
        this.bannerBitmap = null;
    }

    private boolean isHDScreen() {
        return this.screenHeight > this.screenWidth ? this.screenWidth >= Constants.HD_SCREEN_SIZE : this.screenHeight >= Constants.HD_SCREEN_SIZE;
    }

    private Bitmap createBitmap(String fileName) {
        return createBitmap(fileName, true);
    }

    private Bitmap createBitmap(String fileName, boolean isDensityDefendancy) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = this.activity.getAssets().open(new StringBuilder(Constants.RESOURCE_PATH).append(fileName).toString());
            Options options = new Options();
            options.inSampleSize = 1;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            if (isDensityDefendancy) {
                bitmap.setDensity(160);
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        } catch (IOException e2) {
            e2.printStackTrace();
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e3) {
                }
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e4) {
                }
            }
        }
        return bitmap;
    }

    public void hide() {
        this.isShowRequested = false;
        if (this.enableUI) {
            destroyCloseBannerThread();
            this.enableUI = false;
            this.isNewBanner = false;
            this.animationType = -9;
            this.rollingBannerCount = 0;
            if (this.bannerBitmap != null) {
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        NewsBannerUI.this.mainLayout.setVisibility(4);
                        NewsBannerUI.this.destroyRollingThread();
                    }
                });
            }
        }
    }

    public void show() {
        this.logger.v("[NewsBannerUI][show]has been called");
        this.isForcsShow = false;
        this.isTabVisible = true;
        _show();
    }

    public void forceShow(boolean showTab) {
        this.logger.v("[NewsBannerUI][forceShow]has been called: showTab=" + showTab);
        this.isForcsShow = true;
        this.isTabVisible = showTab;
        _show();
    }

    private void _show() {
        this.logger.v("[NewsBannerUI][_show]has been called : enableUI=" + this.enableUI + ", bannerBitmap=" + this.bannerBitmap);
        this.isShowRequested = true;
        if (!this.enableUI && this.bannerBitmap != null) {
            long curTime = System.currentTimeMillis();
            this.enableUI = true;
            if (this.isNewBanner || this.isForcsShow || (this.setting != null && curTime - this.bannerShowTime > ((long) this.setting.autoShowPeriod) * 1000)) {
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        NewsBannerUI.this.isBannerShown = true;
                        NewsBannerUI.this.tabLayout.setVisibility(NewsBannerUI.this.isTabVisible ? 0 : 4);
                        if (!NewsBannerUI.this.isForcsShow || NewsBannerUI.this.isTabVisible) {
                            NewsBannerUI.this.logger.v("[NewsBannerUI][_show]has been called : OPEN_BANNER_ANIMATION");
                            NewsBannerUI.this.startAnimation(2, new AutoShowBannerAnimationListener(NewsBannerUI.this.bannerLayout, NewsBannerUI.this.tabLayout, NewsBannerUI.this.openedTabBitmap));
                        } else {
                            NewsBannerUI.this.logger.v("[NewsBannerUI][_show]has been called : (OPEN_TAB_TO_BANNER | OPEN_BANNER) ANIMATION");
                            NewsBannerUI.this.startAnimation(-2, new AutoShowBannerAnimationListener(NewsBannerUI.this.bannerLayout, NewsBannerUI.this.tabLayout, NewsBannerUI.this.openedTabBitmap));
                        }
                        NewsBannerUI.this.mainLayout.setVisibility(8);
                        NewsBannerUI.this.mainLayout.setVisibility(0);
                        NewsBannerUI.this.startRollingThread(NewsBannerUI.this.isBannerShown);
                        NewsBannerUI.this.newsBanner.requestToServer(1, NewsBannerUI.this.bannerData.pid);
                    }
                });
            } else {
                this.logger.v("[NewsBannerUI][show]has been called : OPEN_TAB_ANIMATION");
                this.activity.runOnUiThread(new Runnable() {
                    public void run() {
                        NewsBannerUI.this.tabLayout.setVisibility(NewsBannerUI.this.isTabVisible ? 0 : 4);
                        NewsBannerUI.this.isBannerShown = false;
                        NewsBannerUI.this.startAnimation(1);
                        NewsBannerUI.this.mainLayout.setVisibility(8);
                        NewsBannerUI.this.mainLayout.setVisibility(0);
                        NewsBannerUI.this.startRollingThread(NewsBannerUI.this.isBannerShown);
                    }
                });
            }
            this.isNewBanner = false;
        }
    }

    private void startAnimation(int animation) {
        startAnimation(animation, null);
    }

    private void startAnimation(final int animation, final BannerAnimationListener listener) {
        if (this.animationType != animation && this.animationType + animation != 0) {
            if (animation == 2 || animation == -2) {
                this.bannerShowTime = System.currentTimeMillis();
                NewsBannerProperties.setProperty(NewsBannerProperties.AUTO_SHOW_TIME_PROPERTY, String.valueOf(this.bannerShowTime));
                NewsBannerProperties.storeProperties(this.activity);
            }
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    NewsBannerAnimation.start(animation, NewsBannerUI.this.bannerPosition, NewsBannerUI.this.setting.animationPlayTime, listener, NewsBannerUI.this.bannerOffset, NewsBannerUI.this.bannerBitmap, NewsBannerUI.this.openedTabBitmap, NewsBannerUI.this.closedTabBitmap, NewsBannerUI.this.mainLayout, NewsBannerUI.this.bannerLayout, NewsBannerUI.this.tabLayout);
                }
            });
            this.animationType = animation;
        }
    }

    public void setNewsBannerBitmap(Context context, int pid, String verticalImgUrl, String horizonalImgUrl, String verticalImgHash, String horizonalImgHash) throws IllegalArgumentException, ImageDataException, IOException {
        this.logger.v("[NewsBannerUI][setNewsBannerBitmap] has been called : orientation=" + this.orientation + ",horizonalImgUrl=" + horizonalImgUrl + ",verticalImgUrl=" + verticalImgUrl);
        switch (this.orientation) {
            case o.a /*1*/:
            case o.b /*2*/:
                if (verticalImgUrl == null || verticalImgUrl.trim().equals(i.a)) {
                    throw new IllegalArgumentException("BannerImageURL is Null");
                }
                try {
                    this.bannerBitmap = NewsBannerImage.getBannerBitmap(context, verticalImgUrl, verticalImgHash);
                    break;
                } catch (ImageDataException e) {
                    throw e;
                } catch (IOException e2) {
                    throw e2;
                }
                break;
            case o.c /*3*/:
            case o.d /*4*/:
                if (horizonalImgUrl == null || horizonalImgUrl.trim().equals(i.a)) {
                    throw new IllegalArgumentException("BannerImageURL is Null");
                }
                try {
                    this.bannerBitmap = NewsBannerImage.getBannerBitmap(context, horizonalImgUrl, horizonalImgHash);
                    break;
                } catch (ImageDataException e3) {
                    throw e3;
                } catch (IOException e22) {
                    throw e22;
                }
            default:
                throw new IllegalArgumentException("wrong orientation");
        }
        if (this.bannerBitmap != null) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    NewsBannerUI.this.bannerView.setImageBitmap(NewsBannerUI.this.bannerBitmap);
                    try {
                        switch (NewsBannerUI.this.bannerPosition) {
                            case o.a /*1*/:
                            case o.b /*2*/:
                                NewsBannerUI.this.bannerOffset = NewsBannerUI.this.bannerOffsetClone;
                                NewsBannerUI.this.tabViewParam.setMargins((NewsBannerUI.this.bannerOffset * ((NewsBannerUI.this.bannerBitmap.getWidth() - NewsBannerUI.this.openedTabBitmap.getWidth()) / 2)) / 100, 0, 0, 0);
                                return;
                            case o.c /*3*/:
                            case o.d /*4*/:
                                NewsBannerUI.this.bannerOffset = ((NewsBannerUI.this.screenHeight - NewsBannerUI.this.bannerBitmap.getHeight()) * NewsBannerUI.this.bannerOffsetClone) / 100;
                                NewsBannerUI.this.mainLayout.setPadding(0, NewsBannerUI.this.bannerOffset, 0, 0);
                                NewsBannerUI.this.tabViewParam.setMargins(0, 0, 0, 0);
                                return;
                            default:
                                return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    e.printStackTrace();
                }
            });
        }
    }

    public void setNewsTabBitmap(Context context, Tab tab) throws IllegalArgumentException, ImageDataException, IOException {
        switch (this.bannerPosition) {
            case o.a /*1*/:
                if (deleteTabImage(context, tab.imageurl_ceiling_open, NewsBannerProperties.TAB_IMAGE_URL_UP_OPEN_PROPERTY, Constants.TAB_UP_OPEN_FILE_NAME) && this.closedTabBitmap != null) {
                    this.closedTabBitmap.recycle();
                    this.closedTabBitmap = null;
                }
                if (deleteTabImage(context, tab.imageurl_ceiling_close, NewsBannerProperties.TAB_IMAGE_URL_UP_CLOSE_PROPERTY, Constants.TAB_UP_CLOSE_FILE_NAME) && this.openedTabBitmap != null) {
                    this.openedTabBitmap.recycle();
                    this.openedTabBitmap = null;
                }
                if (this.openedTabBitmap == null) {
                    this.openedTabBitmap = NewsBannerImage.getTabBitmap(context, Constants.TAB_UP_OPEN_FILE_NAME, tab.imageurl_ceiling_open, tab.imagehash_ceiling_open, NewsBannerProperties.TAB_IMAGE_URL_UP_OPEN_PROPERTY, isHDScreen());
                }
                if (this.closedTabBitmap == null) {
                    this.closedTabBitmap = NewsBannerImage.getTabBitmap(context, Constants.TAB_UP_CLOSE_FILE_NAME, tab.imageurl_ceiling_close, tab.imagehash_ceiling_close, NewsBannerProperties.TAB_IMAGE_URL_UP_CLOSE_PROPERTY, isHDScreen());
                    return;
                }
                return;
            case o.b /*2*/:
                if (deleteTabImage(context, tab.imageurl_ground_open, NewsBannerProperties.TAB_IMAGE_URL_DOWN_OPEN_PROPERTY, Constants.TAB_DOWN_OPEN_FILE_NAME) && this.closedTabBitmap != null) {
                    this.closedTabBitmap.recycle();
                    this.closedTabBitmap = null;
                }
                if (deleteTabImage(context, tab.imageurl_ground_close, NewsBannerProperties.TAB_IMAGE_URL_DOWN_CLOSE_PROPERTY, Constants.TAB_DOWN_CLOSE_FILE_NAME) && this.openedTabBitmap != null) {
                    this.openedTabBitmap.recycle();
                    this.openedTabBitmap = null;
                }
                if (this.openedTabBitmap == null) {
                    this.openedTabBitmap = NewsBannerImage.getTabBitmap(context, Constants.TAB_DOWN_OPEN_FILE_NAME, tab.imageurl_ground_open, tab.imagehash_ground_open, NewsBannerProperties.TAB_IMAGE_URL_DOWN_OPEN_PROPERTY, isHDScreen());
                }
                if (this.closedTabBitmap == null) {
                    this.closedTabBitmap = NewsBannerImage.getTabBitmap(context, Constants.TAB_DOWN_CLOSE_FILE_NAME, tab.imageurl_ground_close, tab.imagehash_ground_close, NewsBannerProperties.TAB_IMAGE_URL_DOWN_CLOSE_PROPERTY, isHDScreen());
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean deleteTabImage(Context context, String url, String property, String filename) throws IllegalArgumentException {
        String urlProperty = null;
        if (url == null || url.length() <= 0) {
            throw new IllegalArgumentException("TabImageUrl is Empry");
        }
        try {
            urlProperty = NewsBannerProperties.getProperty(property);
            this.logger.d("[NewsBannerUI][deleteTabImage]property=" + property + ", value=" + urlProperty);
        } catch (Exception e) {
            this.logger.d("[NewsBannerUI][deleteTabImage]property=" + property + ", value=null");
        }
        if (urlProperty != null && urlProperty.equals(url)) {
            return false;
        }
        File file = NewsBannerImage.makeTabFile(context, filename);
        if (!file.exists()) {
            return false;
        }
        if (file.delete()) {
            this.logger.d("[NewsBannerUI][deleteTabImage]Success:" + file.getName());
            return true;
        }
        this.logger.w("[NewsBannerUI][deleteTabImage]Fail:" + file.getName());
        return false;
    }

    public boolean isExistBannerData() {
        return this.bannerData != null;
    }

    private void setBannerData(BannerData[] bannerDataArr, Setting setting) {
        this.bannerIndex = 0;
        this.bannerDataArr = bannerDataArr;
        this.setting = setting;
        changeBanner(this.bannerIndex);
    }

    private void changeBanner(int bannerIndex) {
        this.logger.v("[NewsBannerUI][changeBanner]index=" + bannerIndex);
        if (this.bannerDataArr.length > 0) {
            BannerData tempBannerData = this.bannerDataArr[bannerIndex];
            try {
                setNewsBannerBitmap(this.activity, tempBannerData.pid, tempBannerData.verticalImgUrl, tempBannerData.horizonalImgUrl, tempBannerData.verticalImgHash, tempBannerData.horizonalImgHash);
                this.bannerData = tempBannerData;
                return;
            } catch (Exception e) {
                this.logger.d("[NewsBannerUI][changeBanner]", e);
                this.newsBanner.runNewsBannerCallback(-3);
                return;
            } catch (Exception e2) {
                this.logger.d("[NewsBannerUI][changeBanner]", e2);
                this.newsBanner.runNewsBannerCallback(-6);
                return;
            } catch (Exception e22) {
                this.logger.d("[NewsBannerUI][changeBanner]", e22);
                this.newsBanner.runNewsBannerCallback(-2);
                return;
            }
        }
        hide();
        this.bannerData = null;
    }

    public void onTabClicked() {
        destroyCloseBannerThread();
        if (this.isBannerShown) {
            closeTab();
        } else {
            openTab();
        }
    }

    public void openTab() {
        this.isBannerShown = true;
        if (this.rollingThread != null) {
            this.rollingThread.resume();
        }
        startAnimation(-2);
        this.newsBanner.runNewsBannerCallback(1);
        this.newsBanner.requestToServer(1, this.bannerData.pid);
    }

    public void closeTab() {
        this.isBannerShown = false;
        if (this.rollingThread != null) {
            this.rollingThread.pause();
        }
        startAnimation(-1);
        this.newsBanner.runNewsBannerCallback(2);
        this.rollingBannerCount = 0;
    }

    public void startCloseBannerThread() {
        destroyCloseBannerThread();
        if (this.closeBannerThread == null) {
            this.closeBannerThread = new CloseBannerThread();
            this.closeBannerThread.start();
        }
    }

    public void destroyCloseBannerThread() {
        if (this.closeBannerThread != null) {
            this.closeBannerThread.interrupt();
            this.closeBannerThread = null;
        }
    }

    public void responseBannerInfo(BannerData[] bannerDataArr, Setting setting, CPI cpi, Tab tab) {
        this.cpi = cpi;
        try {
            setNewsTabBitmap(this.activity, tab);
        } catch (IllegalArgumentException e) {
            this.newsBanner.runNewsBannerCallback(-3);
        } catch (Exception e2) {
            this.logger.d("[NewsBannerUI][responseBannerInfo]", e2);
            this.newsBanner.runNewsBannerCallback(-6);
        } catch (Exception e22) {
            this.logger.d("[NewsBannerUI][responseBannerInfo]", e22);
            this.newsBanner.runNewsBannerCallback(-2);
        }
        if (!(this.openedTabBitmap == null || this.closedTabBitmap == null)) {
            setBannerData(bannerDataArr, setting);
        }
        if (this.bannerBitmap != null) {
            String url = null;
            int length = bannerDataArr.length;
            int i = 0;
            while (i < length) {
                BannerData data = bannerDataArr[i];
                switch (this.orientation) {
                    case o.a /*1*/:
                    case o.b /*2*/:
                        url = data.verticalImgUrl;
                        break;
                    case o.c /*3*/:
                    case o.d /*4*/:
                        url = data.horizonalImgUrl;
                        break;
                }
                if (url == null || url.length() <= 0 || NewsBannerImage.makeBannerFile(this.activity, url).exists()) {
                    i++;
                } else {
                    this.isNewBanner = true;
                    this.logger.d("[NewsBannerUI][responseBannerInfo]isNewBanner=" + this.isNewBanner);
                    this.logger.d("[NewsBannerUI][responseBannerInfo]enalbeUIOnSuspend=" + this.enalbeUIOnSuspend + ",isShowRequested=" + this.isShowRequested);
                    this.newsBanner.runNewsBannerCallback(0);
                    if (this.enalbeUIOnSuspend != 0) {
                        if (this.isShowRequested) {
                            _show();
                        }
                    } else if (this.enalbeUIOnSuspend == 1) {
                        _show();
                    }
                }
            }
            this.logger.d("[NewsBannerUI][responseBannerInfo]enalbeUIOnSuspend=" + this.enalbeUIOnSuspend + ",isShowRequested=" + this.isShowRequested);
            this.newsBanner.runNewsBannerCallback(0);
            if (this.enalbeUIOnSuspend != 0) {
                if (this.enalbeUIOnSuspend == 1) {
                    _show();
                }
            } else if (this.isShowRequested) {
                _show();
            }
        }
    }

    public void resume(boolean isRequestBannerInfo) {
        if (!isRequestBannerInfo && this.enalbeUIOnSuspend == 1) {
            this.activity.runOnUiThread(new Runnable() {
                public void run() {
                    NewsBannerUI.this.mainLayout.setVisibility(0);
                }
            });
        }
        startRollingThread(this.isBannerShown);
    }

    public void suspend() {
        destroyRollingThread();
        destroyCloseBannerThread();
        this.enalbeUIOnSuspend = this.enableUI ? 1 : -1;
        this.activity.runOnUiThread(new Runnable() {
            public void run() {
                NewsBannerUI.this.mainLayout.setVisibility(8);
            }
        });
    }

    private void startRollingThread(boolean bannerShown) {
        if (this.enableUI && this.rollingThread == null && this.bannerDataArr != null && this.bannerDataArr.length > 1) {
            this.rollingThread = new BannerRollingThread();
            if (!bannerShown) {
                this.rollingThread.pause();
            }
            this.rollingThread.start();
        }
    }

    private void destroyRollingThread() {
        if (this.rollingThread != null) {
            this.logger.v("[NewsBannerUI][interuptRollingThread] has been called");
            this.rollingThread.interupt();
            this.rollingThread = null;
        }
    }

    public void onBannerClicked() {
        int i = 2;
        destroyCloseBannerThread();
        final String linkUrl = this.bannerData.linkUrl;
        this.logger.v("[NewsBannerUI][onBannerClicked]" + this.bannerData.linkUrl);
        if (linkUrl != null && this.bannerData.linkUrl.trim().length() > 0) {
            if (NewsBannerData.get(12) != null || (this.bannerData.reward != 1 && this.bannerData.reward != 2)) {
                if (linkUrl.equals("web")) {
                    if (this.rollingThread != null) {
                        this.rollingThread.pause();
                    }
                    createWebViewDialog(linkUrl);
                } else {
                    this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.bannerData.linkUrl)));
                }
                NewsBanner newsBanner = this.newsBanner;
                if (this.bannerData.reward == 1) {
                    i = 3;
                }
                newsBanner.requestToServer(i, this.bannerData.pid);
            } else if (this.cpi != null) {
                if (this.rollingThread != null) {
                    this.rollingThread.pause();
                }
                NewsBannerDialog dialog = new NewsBannerDialog(this.activity, linkUrl);
                dialog.setText(this.cpi.msg, this.cpi.msg_ok, this.cpi.msg_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case NewsBannerAnimation.OPEN_TAB_TO_BANNER_ANIMAITON /*-2*/:
                                if (NewsBannerUI.this.rollingThread != null) {
                                    NewsBannerUI.this.rollingThread.resume();
                                }
                                NewsBannerUI.this.newsBanner.runNewsBannerCallback(-5);
                                return;
                            case WBTelecomUtil.TELECOM_TYPE_NONE /*-1*/:
                                if (NewsBannerUI.this.bannerData.linkType.equals("web")) {
                                    NewsBannerUI.this.createWebViewDialog(linkUrl);
                                } else {
                                    NewsBannerUI.this.activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(NewsBannerUI.this.bannerData.linkUrl)));
                                }
                                NewsBannerUI.this.newsBanner.requestToServer(2, NewsBannerUI.this.bannerData.pid);
                                return;
                            default:
                                return;
                        }
                    }
                });
                dialog.show();
            }
        }
    }

    private void createWebViewDialog(String url) {
        Dialog dialog = new Dialog(this.activity);
        dialog.requestWindowFeature(1);
        dialog.setOnCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                if (NewsBannerUI.this.rollingThread != null) {
                    NewsBannerUI.this.rollingThread.resume();
                }
            }
        });
        WebView webview = new WebView(this.activity);
        webview.setWebViewClient(new WebViewCallBack(dialog));
        webview.setLayoutParams(new LayoutParams(-1, -1));
        webview.getSettings().setJavaScriptEnabled(true);
        webview.postUrl(this.bannerData.linkUrl, null);
        dialog.setContentView(webview, new LayoutParams(this.screenWidth - ((int) ((this.dp * 25.0f) + 0.5f)), this.screenHeight - ((int) ((this.dp * 25.0f) + 0.5f))));
    }

    public void responseReward(int result, int reward) {
        if (result != 2 && this.enalbeUIOnSuspend == 1) {
            _show();
        }
    }
}
