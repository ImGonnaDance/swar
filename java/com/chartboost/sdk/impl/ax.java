package com.chartboost.sdk.impl;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Libraries.j;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.f;
import com.com2us.peppermint.PeppermintConstant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ax extends f {
    protected j j = new j(this);
    protected j k = new j(this);
    private List<com.chartboost.sdk.Libraries.e.a> l = new ArrayList();
    private j m = new j(this);
    private j n = new j(this);
    private j o = new j(this);
    private j p = new j(this);
    private j q = new j(this);
    private j r = new j(this);
    private j s = new j(this);
    private Set<j> t;
    private int u;
    private com.chartboost.sdk.Libraries.e.a v;
    private int w;
    private int x;
    private String y;

    public class a extends com.chartboost.sdk.f.a {
        final /* synthetic */ ax b;
        private bl c;
        private bk d;
        private TextView e;
        private RelativeLayout f;
        private ListView g;
        private a h;

        public class a extends ArrayAdapter<com.chartboost.sdk.Libraries.e.a> {
            final /* synthetic */ a a;
            private Context b;

            public /* synthetic */ Object getItem(int x0) {
                return a(x0);
            }

            public a(a aVar, Context context) {
                this.a = aVar;
                super(context, 0, aVar.b.l);
                this.b = context;
            }

            public View getView(final int position, View convertView, ViewGroup parent) {
                int i = 0;
                final com.chartboost.sdk.Libraries.e.a a = a(position);
                com.chartboost.sdk.Libraries.e.a a2 = a.a(PeppermintConstant.JSON_KEY_TYPE);
                if (convertView == null) {
                    View view;
                    b[] values = b.values();
                    while (i < values.length) {
                        if (a2.equals(values[i].e)) {
                            try {
                                view = (aq) values[i].f.getConstructor(new Class[]{ax.class, Context.class}).newInstance(new Object[]{this.a.b, this.b});
                                break;
                            } catch (Throwable e) {
                                CBLogging.b(this, "error in more apps list", e);
                                view = null;
                            }
                        } else {
                            i++;
                        }
                    }
                    view = null;
                    convertView = view;
                } else if (!(convertView instanceof aq)) {
                    return convertView;
                } else {
                    aq convertView2 = (aq) convertView;
                }
                if (convertView == null) {
                    return new View(getContext());
                }
                convertView.a(a, position);
                LayoutParams layoutParams = convertView.getLayoutParams();
                if (layoutParams == null || !(layoutParams instanceof AbsListView.LayoutParams)) {
                    layoutParams = new AbsListView.LayoutParams(-1, convertView.a());
                } else {
                    layoutParams = (AbsListView.LayoutParams) layoutParams;
                    layoutParams.width = -1;
                    layoutParams.height = convertView.a();
                }
                convertView.setLayoutParams(layoutParams);
                convertView.setOnClickListener(new OnClickListener(this) {
                    final /* synthetic */ a c;

                    public void onClick(View v) {
                        String e = a.e("deep-link");
                        if (TextUtils.isEmpty(e) || !bc.a(e)) {
                            e = a.e("link");
                        }
                        if (this.c.a.b.a(e, a)) {
                            com.chartboost.sdk.Tracking.a.a(this.c.a.b.e.q().e(), a.e("location"), a.e("ad_id"), position);
                        }
                    }
                });
                return convertView;
            }

            public int getCount() {
                return this.a.b.l.size();
            }

            public com.chartboost.sdk.Libraries.e.a a(int i) {
                return (com.chartboost.sdk.Libraries.e.a) this.a.b.l.get(i);
            }

            public int getItemViewType(int position) {
                com.chartboost.sdk.Libraries.e.a a = a(position).a(PeppermintConstant.JSON_KEY_TYPE);
                b[] values = b.values();
                for (int i = 0; i < values.length; i++) {
                    if (a.equals(values[i].e)) {
                        return i;
                    }
                }
                return 0;
            }

            public int getViewTypeCount() {
                return b.values().length;
            }
        }

        private a(final ax axVar, Context context) {
            this.b = axVar;
            super(axVar, context);
            setBackgroundColor(-1);
            this.d = new bk(context);
            this.c = new bl(this, context) {
                final /* synthetic */ a b;

                protected void a(MotionEvent motionEvent) {
                    this.b.b.h();
                }
            };
            this.e = new TextView(context);
            this.e.setBackgroundColor(axVar.w);
            this.e.setText(axVar.y);
            this.e.setTextColor(axVar.x);
            this.e.setTextSize(2, c() ? 30.0f : 18.0f);
            this.e.setGravity(17);
            this.g = new ListView(context);
            this.g.setBackgroundColor(-1);
            this.g.setDividerHeight(0);
            a(this.g);
            addView(this.g);
            this.d.setFocusable(false);
            this.c.setFocusable(false);
            this.c.setClickable(true);
            this.d.setScaleType(ScaleType.CENTER_CROP);
            this.c.a(ScaleType.FIT_CENTER);
            this.f = new RelativeLayout(context);
            this.f.addView(this.d, new RelativeLayout.LayoutParams(-1, -1));
            this.f.addView(this.e, new RelativeLayout.LayoutParams(-1, -1));
            addView(this.f, new RelativeLayout.LayoutParams(-1, -1));
            addView(this.c, new RelativeLayout.LayoutParams(-1, -1));
            a(this.f);
            this.h = new a(this, context);
        }

        protected void a(int i, int i2) {
            j e;
            int i3;
            Context context = getContext();
            com.chartboost.sdk.Libraries.f c = CBUtility.c();
            if (c.b() && this.b.p.e()) {
                e = this.b.p;
            } else if (c.c() && this.b.q.e()) {
                e = this.b.q;
            } else if (this.b.s.e()) {
                e = this.b.s;
            } else {
                e = null;
            }
            if (e != null) {
                this.b.u = e.i();
                if (e.h() < i) {
                    this.b.u = Math.round(((float) this.b.u) * (((float) i) / ((float) e.h())));
                }
                this.e.setVisibility(8);
                this.d.a(e);
            } else {
                this.b.u = CBUtility.a(c() ? 80 : 40, context);
                this.e.setVisibility(0);
            }
            if (this.b.v.c()) {
                this.b.u = CBUtility.a(this.b.v.k(), context);
            }
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
            LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(-1, this.b.u);
            if (this.b.n.e() && c.b()) {
                e = this.b.n;
            } else if (this.b.o.e() && c.c()) {
                e = this.b.o;
            } else if (this.b.m.e()) {
                e = this.b.m;
            } else {
                e = null;
            }
            if (e != null) {
                this.c.a(e, layoutParams2);
                if (c()) {
                    i3 = 14;
                } else {
                    i3 = 7;
                }
                i3 = CBUtility.a(i3, context);
            } else {
                this.c.a("X");
                this.c.a().setTextSize(2, c() ? 26.0f : 16.0f);
                this.c.a().setTextColor(this.b.x);
                this.c.a().setTypeface(Typeface.SANS_SERIF, 1);
                layoutParams2.width = this.b.u / 2;
                layoutParams2.height = this.b.u / 3;
                if (c()) {
                    i3 = 30;
                } else {
                    i3 = 20;
                }
                i3 = CBUtility.a(i3, context);
            }
            int round = Math.round(((float) (this.b.u - layoutParams2.height)) / 2.0f);
            layoutParams2.rightMargin = i3;
            layoutParams2.topMargin = round;
            layoutParams2.addRule(11);
            layoutParams.width = -1;
            layoutParams.height = -1;
            layoutParams.addRule(3, this.f.getId());
            this.g.setAdapter(this.h);
            this.g.setLayoutParams(layoutParams);
            this.c.setLayoutParams(layoutParams2);
            this.f.setLayoutParams(layoutParams3);
        }

        public void b() {
            super.b();
            this.c = null;
            this.d = null;
            this.g = null;
        }
    }

    private enum b {
        FEATURED("featured", ar.class),
        REGULAR("regular", as.class),
        WEBVIEW("webview", au.class),
        VIDEO("video", at.class);
        
        private String e;
        private Class<? extends aq> f;

        private b(String str, Class<? extends aq> cls) {
            this.e = str;
            this.f = cls;
        }
    }

    public ax(com.chartboost.sdk.Model.a aVar) {
        super(aVar);
    }

    protected com.chartboost.sdk.f.a b(Context context) {
        return new a(context);
    }

    public boolean a(com.chartboost.sdk.Libraries.e.a aVar) {
        int i = 0;
        if (!super.a(aVar)) {
            return false;
        }
        com.chartboost.sdk.Libraries.e.a a = aVar.a("cells");
        if (a.b()) {
            a(CBImpressionError.INTERNAL);
            return false;
        }
        this.t = new HashSet();
        while (i < a.o()) {
            com.chartboost.sdk.Libraries.e.a c = a.c(i);
            this.l.add(c);
            com.chartboost.sdk.Libraries.e.a a2 = c.a(PeppermintConstant.JSON_KEY_TYPE);
            if (a2.equals("regular")) {
                c = c.a("assets");
                if (c.c()) {
                    a(c, "icon");
                }
            } else if (a2.equals("featured")) {
                c = c.a("assets");
                if (c.c()) {
                    a(c, "portrait");
                    a(c, "landscape");
                }
            } else if (a2.equals("webview")) {
            }
            i++;
        }
        this.m.a("close");
        this.o.a("close-landscape");
        this.n.a("close-portrait");
        this.s.a("header-center");
        this.p.a("header-portrait");
        this.q.a("header-landscape");
        this.r.a("header-tile");
        this.k.a("play-button");
        this.j.a("install-button");
        this.v = this.d.a("header-height");
        if (this.v.c()) {
            this.u = this.v.k();
        } else {
            this.u = f.a(com.chartboost.sdk.b.k()) ? 80 : 40;
        }
        this.w = this.d.c("background-color") ? f.a(this.d.e("background-color")) : -14571545;
        this.y = this.d.c("header-text") ? this.d.e("header-text") : "More Free Games";
        this.x = this.d.c("text-color") ? f.a(this.d.e("text-color")) : -1;
        return true;
    }

    private void a(com.chartboost.sdk.Libraries.e.a aVar, String str) {
        if (!aVar.b(str)) {
            j jVar = new j(this);
            this.t.add(jVar);
            jVar.a(aVar, str, new Bundle());
        }
    }

    public void d() {
        super.d();
        this.l = null;
        for (j d : this.t) {
            d.d();
        }
        this.t.clear();
        this.m.d();
        this.o.d();
        this.n.d();
        this.s.d();
        this.r.d();
        this.p.d();
        this.q.d();
        this.k.d();
        this.j.d();
    }
}
