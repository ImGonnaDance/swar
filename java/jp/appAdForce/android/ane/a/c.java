package jp.appAdForce.android.ane.a;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.LtvManager;
import jp.appAdForce.android.ane.AppAdForceContext;

public final class c {
    public static LtvManager a;

    public class a implements FREFunction {
        final /* synthetic */ c a;

        public a(c cVar) {
            this.a = cVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                c cVar = this.a;
                c.b(fREContext);
                c.a.addParam(fREObjectArr[0].getAsString(), fREObjectArr[1].getAsString());
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
        final /* synthetic */ c a;

        public b(c cVar) {
            this.a = cVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                int asInt = fREObjectArr[0].getAsInt();
                c cVar = this.a;
                c.a(fREContext, asInt, null);
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    public class c implements FREFunction {
        final /* synthetic */ c a;

        public c(c cVar) {
            this.a = cVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                int asInt = fREObjectArr[0].getAsInt();
                String asString = fREObjectArr[1].getAsString();
                c cVar = this.a;
                c.a(fREContext, asInt, asString);
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    private static void a() {
        a.a = null;
        a = null;
    }

    static /* synthetic */ void a(FREContext fREContext, int i, String str) {
        b(fREContext);
        if (str == null) {
            a.sendLtvConversion(i);
        } else {
            a.sendLtvConversion(i, str);
        }
        a.clearParam();
    }

    private static void b(FREContext fREContext) {
        if (a.a == null) {
            a.a = new AdManager(((AppAdForceContext) fREContext).getActivity());
        }
        if (a == null) {
            a = new LtvManager(a.a);
        }
    }

    private static void b(FREContext fREContext, int i, String str) {
        b(fREContext);
        if (str == null) {
            a.sendLtvConversion(i);
        } else {
            a.sendLtvConversion(i, str);
        }
        a.clearParam();
    }
}
