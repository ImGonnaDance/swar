package com.com2us.peppermint;

import android.opengl.GLSurfaceView;
import com.com2us.peppermint.util.PeppermintResource;

class a extends HubCallbackListener {
    a() {
    }

    public void onCallback(Runnable runnable) {
        try {
            (HubBridge._glSurfaceView != null ? HubBridge._glSurfaceView : (GLSurfaceView) HubBridge.mainActivity.findViewById(PeppermintResource.getID(HubBridge.mainActivity, "R.id.glsurfaceview"))).queueEvent(runnable);
        } catch (NullPointerException e) {
            try {
                runnable.run();
            } catch (UnsatisfiedLinkError e2) {
            }
        } catch (Exception e3) {
        } catch (UnsatisfiedLinkError e4) {
        }
    }
}
