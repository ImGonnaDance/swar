package defpackage;

import android.opengl.GLSurfaceView.Renderer;
import com.com2us.wrapper.kernel.CWrapperKernel;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class a implements Renderer {
    public void onDrawFrame(GL10 gl10) {
    }

    public void onSurfaceChanged(GL10 gl10, int i, int i2) {
        CWrapperKernel.h();
    }

    public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
        CWrapperKernel.i();
    }
}
