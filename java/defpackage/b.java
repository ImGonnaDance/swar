package defpackage;

import android.graphics.Point;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.com2us.wrapper.function.CFunction;
import com.com2us.wrapper.kernel.CEventHandler;
import com.com2us.wrapper.kernel.CWrapperData;
import java.util.HashSet;
import jp.co.dimage.android.f;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

public class b implements OnTouchListener {
    private final int a = 65280;
    private final int b = 8;
    private HashSet<Integer> c = new HashSet();
    private RectF d = new RectF();
    private CEventHandler e = null;

    public b(CEventHandler cEventHandler) {
        this.e = cEventHandler;
    }

    private float a(float f) {
        return f < 0.0f ? 0.0f : Math.min(f, this.d.width());
    }

    private void a(int i, float f, float f2, int i2, int i3) {
        int i4;
        int i5;
        int i6 = 0;
        switch (i) {
            case o.b /*2*/:
                i6 = 2;
                break;
            case f.bc /*5*/:
                i6 = 1;
                break;
            case f.aL /*6*/:
                i6 = 3;
                break;
        }
        synchronized (this) {
            Point convertDisplaytoOriginal = CFunction.convertDisplaytoOriginal(new Point((int) f, (int) f2));
            i4 = convertDisplaytoOriginal.x;
            i5 = convertDisplaytoOriginal.y;
        }
        this.e.addEvent(i6, i4, i5, i2, i3);
    }

    private boolean a(float f, float f2) {
        return this.d.contains(f, f2);
    }

    private boolean a(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        int action = (motionEvent.getAction() & 65280) >> 8;
        int pointerId = motionEvent.getPointerId(action) + 1;
        float x = motionEvent.getX(action);
        float y = motionEvent.getY(action);
        if (a(x, y)) {
            a(5, x, y, pointerId, pointerCount);
        }
        return true;
    }

    private float b(float f) {
        return f < 0.0f ? 0.0f : Math.min(f, this.d.height());
    }

    private boolean b(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        int action = (motionEvent.getAction() & 65280) >> 8;
        int pointerId = motionEvent.getPointerId(action) + 1;
        float x = motionEvent.getX(action);
        float y = motionEvent.getY(action);
        if (a(x, y)) {
            a(6, x, y, pointerId, pointerCount);
        } else {
            this.c.remove(Integer.valueOf(pointerId));
        }
        return true;
    }

    private boolean c(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            float x = motionEvent.getX(i);
            float y = motionEvent.getY(i);
            int pointerId = motionEvent.getPointerId(i) + 1;
            if (a(x, y)) {
                if (this.c.remove(Integer.valueOf(pointerId))) {
                    a(5, x, y, pointerId, pointerCount);
                } else {
                    a(2, x, y, pointerId, pointerCount);
                }
            } else if (!this.c.contains(Integer.valueOf(pointerId))) {
                this.c.add(Integer.valueOf(pointerId));
                a(6, a(x), b(y), pointerId, pointerCount);
            }
        }
        return true;
    }

    public synchronized void a() {
        CWrapperData instance = CWrapperData.getInstance();
        this.d.set(0.0f, 0.0f, (float) instance.getDisplayWidth(), (float) instance.getDisplayHeight());
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction() & 255) {
            case g.a /*0*/:
            case f.bc /*5*/:
                a(motionEvent);
                break;
            case o.a /*1*/:
            case f.aL /*6*/:
                b(motionEvent);
                break;
            case o.b /*2*/:
                c(motionEvent);
                break;
        }
        return true;
    }
}
