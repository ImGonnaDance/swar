package com.com2us.wrapper.kernel;

import com.com2us.wrapper.kernel.CWrapperKernel.r;
import com.com2us.wrapper.kernel.CWrapperKernel.s;
import jp.co.dimage.android.o;

public abstract class CWrapper implements s {
    private String a = null;
    private boolean b = true;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_EXITED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }

    static {
        nativeSetClass();
    }

    public CWrapper() {
        a(getClass().getName(), true);
    }

    public CWrapper(boolean z) {
        a(getClass().getName(), z);
    }

    private void a(String str, boolean z) {
        this.a = str;
        this.b = z;
        CWrapperKernel.a((s) this);
        if (this.b) {
            nativeInitialize(this.a);
        }
    }

    private native void nativeFinalize(String str);

    private native void nativeInitialize(String str);

    private static native void nativeSetClass();

    public void onKernelStateChanged(r rVar) {
        switch (AnonymousClass1.a[rVar.ordinal()]) {
            case o.a /*1*/:
                if (this.b) {
                    nativeFinalize(this.a);
                    return;
                }
                return;
            default:
                return;
        }
    }
}
