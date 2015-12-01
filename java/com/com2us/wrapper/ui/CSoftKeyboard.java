package com.com2us.wrapper.ui;

import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import jp.co.dimage.android.o;

public class CSoftKeyboard extends CWrapper {
    private static CSoftKeyboard d = new CSoftKeyboard();
    private InputMethodManager a;
    private TextView b;
    private TextView c;

    static /* synthetic */ class AnonymousClass4 {
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

    private CSoftKeyboard() {
        super(false);
        this.a = null;
        this.b = null;
        this.c = null;
        this.a = (InputMethodManager) CFunction.getActivity().getSystemService("input_method");
    }

    public static CSoftKeyboard getInstance() {
        return d;
    }

    public void hideKeyboard(final TextView textView) {
        if (this.b == textView && this.b != null) {
            if ((textView.getImeOptions() & 5) == 5) {
                new Thread(this) {
                    final /* synthetic */ CSoftKeyboard b;

                    public void run() {
                        try {
                            Thread.sleep(300);
                        } catch (Exception e) {
                        }
                        if (this.b.b == textView) {
                            this.b.a.hideSoftInputFromWindow(this.b.b.getWindowToken(), 0);
                            this.b.onKeyboardHide();
                        }
                    }
                }.start();
                return;
            }
            this.a.hideSoftInputFromWindow(this.b.getWindowToken(), 0);
            onKeyboardHide();
        }
    }

    public TextView hideKeyboardForcedly() {
        TextView textView = this.b;
        if (this.b != null) {
            this.a.hideSoftInputFromWindow(this.b.getWindowToken(), 0);
            onKeyboardHide();
        }
        return textView;
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass4.a[rVar.ordinal()]) {
            case o.a /*1*/:
                CFunction.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ CSoftKeyboard a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        this.a.c = this.a.hideKeyboardForcedly();
                        if (this.a.c != null) {
                            this.a.c.clearFocus();
                        }
                    }
                });
                return;
            case o.b /*2*/:
                CFunction.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ CSoftKeyboard a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        if (this.a.c != null) {
                            this.a.c.requestFocus();
                            new Thread(this) {
                                final /* synthetic */ AnonymousClass3 a;

                                {
                                    this.a = r1;
                                }

                                public void run() {
                                    this.a.a.showKeyboard(this.a.a.c);
                                }
                            }.start();
                        }
                    }
                });
                return;
            case o.c /*3*/:
                hideKeyboardForcedly();
                return;
            default:
                return;
        }
    }

    public void onKeyboardHide() {
        this.b = null;
    }

    public void onKeyboardShow(TextView textView) {
        this.b = textView;
    }

    public void showKeyboard(TextView textView) {
        if (textView != null) {
            int i = 0;
            while (i < 5) {
                if (this.a.showSoftInput(textView, 2)) {
                    onKeyboardShow(textView);
                    return;
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (Exception e) {
                    }
                    i++;
                }
            }
        }
    }
}
