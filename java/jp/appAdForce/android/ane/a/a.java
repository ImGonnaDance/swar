package jp.appAdForce.android.ane.a;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.ane.AppAdForceContext;

public final class a implements jp.co.dimage.android.f {
    public static AdManager a;

    public class a implements FREFunction {
        final /* synthetic */ a a;

        public a(a aVar) {
            this.a = aVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                a aVar = this.a;
                a.a(fREContext);
                a.a.sendConversion();
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    public class b implements FREFunction {
        final /* synthetic */ a a;

        public b(a aVar) {
            this.a = aVar;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final com.adobe.fre.FREObject call(com.adobe.fre.FREContext r4, com.adobe.fre.FREObject[] r5) {
            /*
            r3 = this;
            r0 = r3.a;	 Catch:{ Exception -> 0x002d }
            jp.appAdForce.android.ane.a.a.a(r4);	 Catch:{ Exception -> 0x002d }
            r0 = r5.length;	 Catch:{ Exception -> 0x002d }
            switch(r0) {
                case 1: goto L_0x0020;
                case 2: goto L_0x003f;
                default: goto L_0x0009;
            };	 Catch:{ Exception -> 0x002d }
        L_0x0009:
            r0 = "F.O.X";
            r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d }
            r2 = "Method not found sendConversionForMobage args[]:";
            r1.<init>(r2);	 Catch:{ Exception -> 0x002d }
            r2 = r5.length;	 Catch:{ Exception -> 0x002d }
            r1 = r1.append(r2);	 Catch:{ Exception -> 0x002d }
            r1 = r1.toString();	 Catch:{ Exception -> 0x002d }
            android.util.Log.e(r0, r1);	 Catch:{ Exception -> 0x002d }
        L_0x001e:
            r0 = 0;
            return r0;
        L_0x0020:
            r0 = 0;
            r0 = r5[r0];	 Catch:{ Exception -> 0x002d }
            r0 = r0.getAsString();	 Catch:{ Exception -> 0x002d }
            r1 = jp.appAdForce.android.ane.a.a.a;	 Catch:{ Exception -> 0x002d }
            r1.sendConversionForMobage(r0);	 Catch:{ Exception -> 0x002d }
            goto L_0x001e;
        L_0x002d:
            r0 = move-exception;
            r0.printStackTrace();
            r1 = r0.getCause();
            if (r1 == 0) goto L_0x001e;
        L_0x0037:
            r0 = r0.getCause();
            r0.printStackTrace();
            goto L_0x001e;
        L_0x003f:
            r0 = 0;
            r0 = r5[r0];	 Catch:{ Exception -> 0x002d }
            r0 = r0.getAsString();	 Catch:{ Exception -> 0x002d }
            r1 = 1;
            r1 = r5[r1];	 Catch:{ Exception -> 0x002d }
            r1 = r1.getAsString();	 Catch:{ Exception -> 0x002d }
            r2 = jp.appAdForce.android.ane.a.a.a;	 Catch:{ Exception -> 0x002d }
            r2.sendConversionForMobage(r0, r1);	 Catch:{ Exception -> 0x002d }
            goto L_0x001e;
            */
            throw new UnsupportedOperationException("Method not decompiled: jp.appAdForce.android.ane.a.a.b.call(com.adobe.fre.FREContext, com.adobe.fre.FREObject[]):com.adobe.fre.FREObject");
        }
    }

    public class c implements FREFunction {
        final /* synthetic */ a a;

        public c(a aVar) {
            this.a = aVar;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final com.adobe.fre.FREObject call(com.adobe.fre.FREContext r4, com.adobe.fre.FREObject[] r5) {
            /*
            r3 = this;
            r0 = r3.a;	 Catch:{ Exception -> 0x002d }
            jp.appAdForce.android.ane.a.a.a(r4);	 Catch:{ Exception -> 0x002d }
            r0 = r5.length;	 Catch:{ Exception -> 0x002d }
            switch(r0) {
                case 1: goto L_0x0020;
                case 2: goto L_0x003f;
                default: goto L_0x0009;
            };	 Catch:{ Exception -> 0x002d }
        L_0x0009:
            r0 = "F.O.X";
            r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d }
            r2 = "Method not found sendConversionForMobageWithCAReward args[]:";
            r1.<init>(r2);	 Catch:{ Exception -> 0x002d }
            r2 = r5.length;	 Catch:{ Exception -> 0x002d }
            r1 = r1.append(r2);	 Catch:{ Exception -> 0x002d }
            r1 = r1.toString();	 Catch:{ Exception -> 0x002d }
            android.util.Log.e(r0, r1);	 Catch:{ Exception -> 0x002d }
        L_0x001e:
            r0 = 0;
            return r0;
        L_0x0020:
            r0 = 0;
            r0 = r5[r0];	 Catch:{ Exception -> 0x002d }
            r0 = r0.getAsString();	 Catch:{ Exception -> 0x002d }
            r1 = jp.appAdForce.android.ane.a.a.a;	 Catch:{ Exception -> 0x002d }
            r1.sendConversionForMobageWithCAReward(r0);	 Catch:{ Exception -> 0x002d }
            goto L_0x001e;
        L_0x002d:
            r0 = move-exception;
            r0.printStackTrace();
            r1 = r0.getCause();
            if (r1 == 0) goto L_0x001e;
        L_0x0037:
            r0 = r0.getCause();
            r0.printStackTrace();
            goto L_0x001e;
        L_0x003f:
            r0 = 0;
            r0 = r5[r0];	 Catch:{ Exception -> 0x002d }
            r0 = r0.getAsString();	 Catch:{ Exception -> 0x002d }
            r1 = 1;
            r1 = r5[r1];	 Catch:{ Exception -> 0x002d }
            r1 = r1.getAsString();	 Catch:{ Exception -> 0x002d }
            r2 = jp.appAdForce.android.ane.a.a.a;	 Catch:{ Exception -> 0x002d }
            r2.sendConversionForMobageWithCAReward(r0, r1);	 Catch:{ Exception -> 0x002d }
            goto L_0x001e;
            */
            throw new UnsupportedOperationException("Method not decompiled: jp.appAdForce.android.ane.a.a.c.call(com.adobe.fre.FREContext, com.adobe.fre.FREObject[]):com.adobe.fre.FREObject");
        }
    }

    public class d implements FREFunction {
        final /* synthetic */ a a;

        public d(a aVar) {
            this.a = aVar;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final com.adobe.fre.FREObject call(com.adobe.fre.FREContext r4, com.adobe.fre.FREObject[] r5) {
            /*
            r3 = this;
            r0 = r3.a;	 Catch:{ Exception -> 0x002d }
            jp.appAdForce.android.ane.a.a.a(r4);	 Catch:{ Exception -> 0x002d }
            r0 = r5.length;	 Catch:{ Exception -> 0x002d }
            switch(r0) {
                case 1: goto L_0x0020;
                case 2: goto L_0x003f;
                default: goto L_0x0009;
            };	 Catch:{ Exception -> 0x002d }
        L_0x0009:
            r0 = "F.O.X";
            r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d }
            r2 = "Method not found sendConversionWithCAReward args[]:";
            r1.<init>(r2);	 Catch:{ Exception -> 0x002d }
            r2 = r5.length;	 Catch:{ Exception -> 0x002d }
            r1 = r1.append(r2);	 Catch:{ Exception -> 0x002d }
            r1 = r1.toString();	 Catch:{ Exception -> 0x002d }
            android.util.Log.e(r0, r1);	 Catch:{ Exception -> 0x002d }
        L_0x001e:
            r0 = 0;
            return r0;
        L_0x0020:
            r0 = 0;
            r0 = r5[r0];	 Catch:{ Exception -> 0x002d }
            r0 = r0.getAsString();	 Catch:{ Exception -> 0x002d }
            r1 = jp.appAdForce.android.ane.a.a.a;	 Catch:{ Exception -> 0x002d }
            r1.sendConversionWithCAReward(r0);	 Catch:{ Exception -> 0x002d }
            goto L_0x001e;
        L_0x002d:
            r0 = move-exception;
            r0.printStackTrace();
            r1 = r0.getCause();
            if (r1 == 0) goto L_0x001e;
        L_0x0037:
            r0 = r0.getCause();
            r0.printStackTrace();
            goto L_0x001e;
        L_0x003f:
            r0 = 0;
            r0 = r5[r0];	 Catch:{ Exception -> 0x002d }
            r0 = r0.getAsString();	 Catch:{ Exception -> 0x002d }
            r1 = 1;
            r1 = r5[r1];	 Catch:{ Exception -> 0x002d }
            r1 = r1.getAsString();	 Catch:{ Exception -> 0x002d }
            r2 = jp.appAdForce.android.ane.a.a.a;	 Catch:{ Exception -> 0x002d }
            r2.sendConversionWithCAReward(r0, r1);	 Catch:{ Exception -> 0x002d }
            goto L_0x001e;
            */
            throw new UnsupportedOperationException("Method not decompiled: jp.appAdForce.android.ane.a.a.d.call(com.adobe.fre.FREContext, com.adobe.fre.FREObject[]):com.adobe.fre.FREObject");
        }
    }

    public class e implements FREFunction {
        final /* synthetic */ a a;

        public e(a aVar) {
            this.a = aVar;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final com.adobe.fre.FREObject call(com.adobe.fre.FREContext r4, com.adobe.fre.FREObject[] r5) {
            /*
            r3 = this;
            r0 = r3.a;	 Catch:{ Exception -> 0x002d }
            jp.appAdForce.android.ane.a.a.a(r4);	 Catch:{ Exception -> 0x002d }
            r0 = r5.length;	 Catch:{ Exception -> 0x002d }
            switch(r0) {
                case 1: goto L_0x0020;
                case 2: goto L_0x003f;
                default: goto L_0x0009;
            };	 Catch:{ Exception -> 0x002d }
        L_0x0009:
            r0 = "F.O.X";
            r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x002d }
            r2 = "Method not found sendConversionWithStartPage args[]:";
            r1.<init>(r2);	 Catch:{ Exception -> 0x002d }
            r2 = r5.length;	 Catch:{ Exception -> 0x002d }
            r1 = r1.append(r2);	 Catch:{ Exception -> 0x002d }
            r1 = r1.toString();	 Catch:{ Exception -> 0x002d }
            android.util.Log.e(r0, r1);	 Catch:{ Exception -> 0x002d }
        L_0x001e:
            r0 = 0;
            return r0;
        L_0x0020:
            r0 = 0;
            r0 = r5[r0];	 Catch:{ Exception -> 0x002d }
            r0 = r0.getAsString();	 Catch:{ Exception -> 0x002d }
            r1 = jp.appAdForce.android.ane.a.a.a;	 Catch:{ Exception -> 0x002d }
            r1.sendConversion(r0);	 Catch:{ Exception -> 0x002d }
            goto L_0x001e;
        L_0x002d:
            r0 = move-exception;
            r0.printStackTrace();
            r1 = r0.getCause();
            if (r1 == 0) goto L_0x001e;
        L_0x0037:
            r0 = r0.getCause();
            r0.printStackTrace();
            goto L_0x001e;
        L_0x003f:
            r0 = 0;
            r0 = r5[r0];	 Catch:{ Exception -> 0x002d }
            r0 = r0.getAsString();	 Catch:{ Exception -> 0x002d }
            r1 = 1;
            r1 = r5[r1];	 Catch:{ Exception -> 0x002d }
            r1 = r1.getAsString();	 Catch:{ Exception -> 0x002d }
            r2 = jp.appAdForce.android.ane.a.a.a;	 Catch:{ Exception -> 0x002d }
            r2.sendConversion(r0, r1);	 Catch:{ Exception -> 0x002d }
            goto L_0x001e;
            */
            throw new UnsupportedOperationException("Method not decompiled: jp.appAdForce.android.ane.a.a.e.call(com.adobe.fre.FREContext, com.adobe.fre.FREObject[]):com.adobe.fre.FREObject");
        }
    }

    public class f implements FREFunction {
        final /* synthetic */ a a;

        public f(a aVar) {
            this.a = aVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                a aVar = this.a;
                a.a(fREContext);
                a.a.sendUserIdForMobage(fREObjectArr[0].getAsString());
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    public class g implements FREFunction {
        final /* synthetic */ a a;

        public g(a aVar) {
            this.a = aVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            return null;
        }
    }

    public class h implements FREFunction {
        final /* synthetic */ a a;

        public h(a aVar) {
            this.a = aVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            return null;
        }
    }

    public class i implements FREFunction {
        final /* synthetic */ a a;

        public i(a aVar) {
            this.a = aVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                a aVar = this.a;
                a.a(fREContext);
                a.a.setOptout(fREObjectArr[0].getAsBool());
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    public class j implements FREFunction {
        final /* synthetic */ a a;

        public j(a aVar) {
            this.a = aVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                a aVar = this.a;
                a.a(fREContext);
                a.a.setServerUrl(fREObjectArr[0].getAsString());
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    public class k implements FREFunction {
        final /* synthetic */ a a;

        public k(a aVar) {
            this.a = aVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                AdManager.updateFrom2_10_4g();
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    public static void a() {
        a = null;
    }

    static /* synthetic */ void a(FREContext fREContext) {
        if (a == null) {
            a = new AdManager(((AppAdForceContext) fREContext).getActivity());
        }
    }

    private static void b(FREContext fREContext) {
        if (a == null) {
            a = new AdManager(((AppAdForceContext) fREContext).getActivity());
        }
    }
}
