package com.com2us.wrapper.sensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.WindowManager;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.kernel.CWrapper;
import com.com2us.wrapper.kernel.CWrapperKernel.r;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public class CSensor extends CWrapper implements SensorEventListener {
    private SensorManager a = null;
    private Sensor b = null;
    private SensorEventListener c = null;
    private Display d = null;
    private int e = 0;
    private int f = 1;
    private long g = 0;
    private long h = 0;
    private float[] i = new float[3];
    private float[] j = new float[3];

    static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] a = new int[r.values().length];

        static {
            try {
                a[r.APPLICATION_PAUSE_START.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[r.APPLICATION_STARTED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[r.APPLICATION_RESUMED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public CSensor(Activity activity) {
        this.a = (SensorManager) activity.getSystemService("sensor");
        this.b = this.a.getDefaultSensor(1);
        this.c = this;
        int i = activity.getResources().getConfiguration().orientation;
        if (CFunction.getSystemVersionCode() >= 8) {
            this.d = ((WindowManager) activity.getSystemService("window")).getDefaultDisplay();
            this.e = this.d.getRotation();
            switch (this.e) {
                case g.a /*0*/:
                case o.b /*2*/:
                    this.f = i;
                    break;
                case o.a /*1*/:
                case o.c /*3*/:
                    this.f = i == 1 ? 2 : 1;
                    break;
            }
        }
        nativeSensorInitialize(this.i);
    }

    private native void nativeSensorInitialize(float[] fArr);

    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public void onKernelStateChanged(r rVar) {
        super.onKernelStateChanged(rVar);
        switch (AnonymousClass3.a[rVar.ordinal()]) {
            case o.a /*1*/:
                new Thread(this) {
                    final /* synthetic */ CSensor a;

                    {
                        this.a = r1;
                    }

                    public synchronized void run() {
                        try {
                            Thread.sleep(400);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.a.a.unregisterListener(this.a.c);
                    }
                }.start();
                return;
            case o.b /*2*/:
            case o.c /*3*/:
                this.h = 0;
                new Thread(this) {
                    final /* synthetic */ CSensor a;

                    {
                        this.a = r1;
                    }

                    public synchronized void run() {
                        try {
                            Thread.sleep(400);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        this.a.h = System.currentTimeMillis();
                        this.a.a.registerListener(this.a.c, this.a.b, 0);
                    }
                }.start();
                return;
            default:
                return;
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] fArr = sensorEvent.values;
        this.g = this.h;
        this.h = System.currentTimeMillis();
        float min = Math.min(((float) (this.h - this.g)) * 0.007f, 1.0f);
        System.arraycopy(this.i, 0, this.j, 0, 3);
        if (this.f == 1) {
            this.j[0] = (this.j[0] * (1.0f - min)) + (fArr[0] * min);
            this.j[1] = (this.j[1] * (1.0f - min)) + (fArr[1] * min);
            this.j[2] = (fArr[2] * min) + (this.j[2] * (1.0f - min));
        } else {
            this.j[0] = (this.j[0] * (1.0f - min)) + (fArr[1] * min);
            this.j[1] = (this.j[1] * (1.0f - min)) - (fArr[0] * min);
            this.j[2] = (fArr[2] * min) + (this.j[2] * (1.0f - min));
        }
        System.arraycopy(this.j, 0, this.i, 0, 3);
    }
}
