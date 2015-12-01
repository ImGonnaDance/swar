package com.chartboost.sdk.impl;

import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.e;
import com.chartboost.sdk.Model.CBError.CBImpressionError;
import com.chartboost.sdk.Model.a;
import com.chartboost.sdk.Model.a.c;
import com.chartboost.sdk.Model.b;
import com.facebook.widget.FacebookDialog;

public class af extends ae {
    private static af b;
    private static String c = "CBRewardedVideo";

    private af() {
    }

    public static af h() {
        if (b == null) {
            b = new af();
        }
        return b;
    }

    protected boolean b(a aVar, e.a aVar2) {
        return true;
    }

    protected a a(String str, boolean z) {
        return new a(c.REWARDED_VIDEO, z, str, false);
    }

    protected boolean d(a aVar) {
        return !be.b(aVar);
    }

    protected ba e(a aVar) {
        ba baVar = new ba("/reward/get");
        baVar.a(l.a.HIGH);
        baVar.a(b.b);
        baVar.a("local-videos", h().g());
        return baVar;
    }

    protected ba l(a aVar) {
        ba l = super.l(aVar);
        l.a("/reward/show");
        return l;
    }

    protected void i(a aVar) {
    }

    protected void g(a aVar) {
        if (be.b(aVar.w())) {
            super.g(aVar);
            return;
        }
        CBLogging.b(aVar.c, "###Video not available in the cache, so returning empty");
        a(aVar, CBImpressionError.INTERNAL);
        be.a();
    }

    protected void h(final a aVar) {
        final e.a a = aVar.w().a("ux").a("pre-popup");
        if (a.c() && a.a("title").d() && a.a("text").d() && a.a("confirm").d() && a.a(FacebookDialog.COMPLETION_GESTURE_CANCEL).d() && d() != null) {
            a.post(new Runnable(this) {
                final /* synthetic */ af c;

                public void run() {
                    bm.a aVar = new bm.a();
                    aVar.a(a.e("title")).b(a.e("text")).d(a.e("confirm")).c(a.e(FacebookDialog.COMPLETION_GESTURE_CANCEL));
                    aVar.a(this.c.d(), new bm.b(this) {
                        final /* synthetic */ AnonymousClass1 a;

                        {
                            this.a = r1;
                        }

                        public void a(bm bmVar) {
                            this.a.c.a(aVar, CBImpressionError.USER_CANCELLATION);
                        }

                        public void a(bm bmVar, int i) {
                            if (i == 1) {
                                super.h(aVar);
                            } else {
                                this.a.c.a(aVar, CBImpressionError.USER_CANCELLATION);
                            }
                        }
                    });
                }
            });
        } else {
            super.h(aVar);
        }
    }

    protected void s(a aVar) {
        final e.a a = aVar.w().a("ux").a("post-popup");
        if (a.c() && a.a("title").d() && a.a("text").d() && a.a("confirm").d() && d() != null && aVar.m) {
            a.post(new Runnable(this) {
                final /* synthetic */ af b;

                public void run() {
                    bm.a aVar = new bm.a();
                    aVar.a(a.e("title")).b(a.e("text")).c(a.e("confirm"));
                    aVar.a(this.b.d(), new bm.b(this) {
                        final /* synthetic */ AnonymousClass2 a;

                        {
                            this.a = r1;
                        }

                        public void a(bm bmVar, int i) {
                            CBLogging.c(af.c, "post-popup dismissed");
                        }
                    });
                }
            });
        }
    }

    protected a c() {
        return new a(this) {
            final /* synthetic */ af a;

            {
                this.a = r1;
            }

            public void a(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didClickRewardedVideo(aVar.d);
                }
            }

            public void b(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didCloseRewardedVideo(aVar.d);
                }
            }

            public void c(a aVar) {
                this.a.s(aVar);
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didDismissRewardedVideo(aVar.d);
                }
            }

            public void d(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didCacheRewardedVideo(aVar.d);
                }
            }

            public void a(a aVar, CBImpressionError cBImpressionError) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didFailToLoadRewardedVideo(aVar.d, cBImpressionError);
                }
            }

            public void e(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    com.chartboost.sdk.b.d().didDisplayRewardedVideo(aVar.d);
                }
            }

            public boolean f(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    return com.chartboost.sdk.b.d().shouldDisplayRewardedVideo(aVar.d);
                }
                return true;
            }

            public boolean g(a aVar) {
                return true;
            }

            public boolean h(a aVar) {
                if (com.chartboost.sdk.b.d() != null) {
                    return com.chartboost.sdk.b.q();
                }
                return true;
            }
        };
    }

    public String e() {
        return "rewarded-video";
    }
}
