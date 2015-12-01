package com.chartboost.sdk.impl;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout.LayoutParams;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.e.a;

public class at extends as {
    private ax a;
    private Button b;
    private bh c;
    private a d;

    public at(ax axVar, Context context) {
        super(axVar, context);
        this.a = axVar;
        this.b = new Button(context);
        this.b.setTextColor(-14571545);
        this.b.setText("Preview");
        this.b.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ at a;

            {
                this.a = r1;
            }

            public void onClick(View v) {
                this.a.c();
            }
        });
        addView(this.b, 2);
    }

    public void a(a aVar, int i) {
        super.a(aVar, i);
        this.d = aVar;
    }

    private void c() {
        CBLogging.c(this, "play the video");
        if (this.c == null) {
            this.c = new bh(getContext());
            this.a.e().addView(this.c, new LayoutParams(-1, -1));
            this.c.setVisibility(8);
        }
        this.c.b().a(new OnCompletionListener(this) {
            final /* synthetic */ at a;

            {
                this.a = r1;
            }

            public void onCompletion(MediaPlayer arg0) {
                bi.a(false, this.a.c);
            }
        });
        bi.a(true, this.c);
        this.c.b().a();
    }
}
