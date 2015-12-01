package com.com2us.wrapper.ui;

import android.widget.FrameLayout;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import defpackage.i;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public class CUserInput extends CWrapper {
    private FrameLayout a = null;
    private String b = null;
    private i c = null;
    private String d = null;

    static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_PAUSE_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[r.APPLICATION_RESUMED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[r.APPLICATION_EXITED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public interface b {
        void a(String str);
    }

    public class a implements b {
        final /* synthetic */ CUserInput a;

        public a(CUserInput cUserInput) {
            this.a = cUserInput;
        }

        public void a(final String str) {
            CFunction.runOnGlThread(new Runnable(this) {
                final /* synthetic */ a b;

                public void run() {
                    byte[] bytes;
                    try {
                        bytes = str.getBytes(this.b.a.d);
                    } catch (Exception e) {
                        e.printStackTrace();
                        bytes = new byte[]{(byte) 0};
                    }
                    this.b.a.nativeCallback(bytes);
                }
            });
        }
    }

    public CUserInput(FrameLayout frameLayout, String str) {
        this.d = str;
        this.a = frameLayout;
        this.c = new i(this.a, new a(this));
    }

    private native void nativeCallback(byte[] bArr);

    public void createUserInput(int i) {
        createUserInput(i, null);
    }

    public void createUserInput(final int i, byte[] bArr) {
        try {
            this.b = new String(bArr, this.d);
        } catch (Exception e) {
            this.b = jp.co.cyberz.fox.a.a.i.a;
        }
        CFunction.runOnUiThread(new Runnable(this) {
            final /* synthetic */ CUserInput b;

            public void run() {
                int i = 0;
                switch (i) {
                    case g.a /*0*/:
                        i = 65;
                        break;
                    case o.a /*1*/:
                        i = 2;
                        break;
                }
                this.b.c.a(i, this.b.b);
            }
        });
    }

    public void destroyUserInput() {
        CFunction.runOnUiThread(new Runnable(this) {
            final /* synthetic */ CUserInput a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.c.b();
            }
        });
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass5.a[rVar.ordinal()]) {
            case o.a /*1*/:
                CFunction.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ CUserInput a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.a.setVisibility(4);
                    }
                });
                return;
            case o.b /*2*/:
                CFunction.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ CUserInput a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.a.setVisibility(0);
                    }
                });
                return;
            case o.c /*3*/:
                this.c.a();
                return;
            default:
                return;
        }
    }
}
