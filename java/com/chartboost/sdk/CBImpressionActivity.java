package com.chartboost.sdk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import com.chartboost.sdk.Libraries.k;
import com.chartboost.sdk.impl.bh;
import com.com2us.module.manager.ModuleConfig;

public final class CBImpressionActivity extends Activity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (bh.c()) {
            getWindow().setFlags(16777216, 16777216);
        }
        requestWindowFeature(1);
        if (getIntent().getBooleanExtra("paramFullscreen", false)) {
            getWindow().addFlags(ModuleConfig.MERCURY_MODULE);
        }
        getWindow().setWindowAnimations(0);
        setContentView(new RelativeLayout(this));
        Chartboost.a(this);
        Chartboost.createSurfaceView(this);
    }

    protected void onStart() {
        super.onStart();
        Chartboost.a((Activity) this);
    }

    protected void onResume() {
        super.onResume();
        Chartboost.a(k.a((Activity) this));
    }

    protected void onPause() {
        super.onPause();
        Chartboost.b(k.a((Activity) this));
    }

    protected void onStop() {
        super.onStop();
        Chartboost.c(k.a((Activity) this));
    }

    protected void onDestroy() {
        super.onDestroy();
        Chartboost.b((Activity) this);
    }

    public void onBackPressed() {
        if (!Chartboost.a()) {
            super.onBackPressed();
        }
    }
}
