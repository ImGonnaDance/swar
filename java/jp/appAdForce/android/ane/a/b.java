package jp.appAdForce.android.ane.a;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import jp.appAdForce.android.AnalyticsManager;
import jp.appAdForce.android.ane.AppAdForceContext;

public final class b {

    public class a implements FREFunction {
        final /* synthetic */ b a;

        public a(b bVar) {
            this.a = bVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                AnalyticsManager.sendEndSession(((AppAdForceContext) fREContext).getActivity());
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
        final /* synthetic */ b a;

        public b(b bVar) {
            this.a = bVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                AnalyticsManager.sendEvent(((AppAdForceContext) fREContext).getActivity(), fREObjectArr[0].getAsString(), fREObjectArr[1].getAsString(), fREObjectArr[2].getAsString(), fREObjectArr[3].getAsInt());
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
        final /* synthetic */ b a;

        public c(b bVar) {
            this.a = bVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                AnalyticsManager.sendEvent(((AppAdForceContext) fREContext).getActivity(), fREObjectArr[0].getAsString(), fREObjectArr[1].getAsString(), fREObjectArr[2].getAsString(), fREObjectArr[3].getAsString(), fREObjectArr[4].getAsString(), fREObjectArr[5].getAsString(), fREObjectArr[6].getAsDouble(), fREObjectArr[7].getAsInt(), fREObjectArr[8].getAsString());
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    public class d implements FREFunction {
        final /* synthetic */ b a;

        public d(b bVar) {
            this.a = bVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                AnalyticsManager.a(fREContext.getActivity().getPackageName(), ((AppAdForceContext) fREContext).getActivity());
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }

    public class e implements FREFunction {
        final /* synthetic */ b a;

        public e(b bVar) {
            this.a = bVar;
        }

        public final FREObject call(FREContext fREContext, FREObject[] fREObjectArr) {
            try {
                AnalyticsManager.sendUserInfo(((AppAdForceContext) fREContext).getActivity(), fREObjectArr[0].getAsString(), fREObjectArr[1].getAsString(), fREObjectArr[2].getAsString(), fREObjectArr[3].getAsString(), fREObjectArr[4].getAsString(), fREObjectArr[5].getAsString());
            } catch (Exception e) {
                e.printStackTrace();
                if (e.getCause() != null) {
                    e.getCause().printStackTrace();
                }
            }
            return null;
        }
    }
}
