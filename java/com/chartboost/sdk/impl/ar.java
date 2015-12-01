package com.chartboost.sdk.impl;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.e.a;

public class ar extends aq {
    private ImageView a;

    public ar(ax axVar, Context context) {
        super(context);
        this.a = new ImageView(context);
        addView(this.a, new LayoutParams(-1, -1));
    }

    public void a(a aVar, int i) {
        a a = aVar.a("assets").a(CBUtility.c().b() ? "portrait" : "landscape");
        if (a.c()) {
            Bundle bundle = new Bundle();
            bundle.putInt("index", i);
            bd.a().a(a.e(jp.co.cyberz.fox.notify.a.g), a.e("checksum"), null, this.a, bundle);
        }
    }

    public int a() {
        return CBUtility.a(110, getContext());
    }
}
