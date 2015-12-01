package com.com2us.wrapper.function;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperData;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import jp.co.dimage.android.o;

public class CFunctionTaskRunner extends CWrapper {

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.GLSURFACEVIEW_SIZE_CHANGED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    public CFunctionTaskRunner(Activity activity, GLSurfaceView gLSurfaceView) {
        a(activity, gLSurfaceView);
    }

    private void a(Activity activity, GLSurfaceView gLSurfaceView) {
        CFunction.initialize(activity, gLSurfaceView);
        CResource.initialize(activity);
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass1.a[rVar.ordinal()]) {
            case o.a /*1*/:
                CWrapperData instance = CWrapperData.getInstance();
                CFunction.onSizeChanged(instance.getOriginalWidth(), instance.getOriginalHeight(), instance.getDisplayWidth(), instance.getDisplayHeight());
                return;
            default:
                return;
        }
    }

    public void uninitialize() {
        CFunction.uninitialize();
        CResource.uninitialize();
    }
}
