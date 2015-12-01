package com.com2us.peppermint;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import com.com2us.peppermint.util.PeppermintLog;
import org.json.JSONException;
import org.json.JSONObject;

public class HubBridge {
    public static GLSurfaceView _glSurfaceView = null;
    private static HubCallbackListener a = new a();
    private static Peppermint f0a = null;
    public static Activity mainActivity = null;

    public static void HubCallbackSetListener(HubCallbackListener hubCallbackListener) {
        a = hubCallbackListener;
    }

    public static void HubCallbackWithJSON(JSONObject jSONObject, int i, int i2) {
        PeppermintLog.i("HubCallbackWithJSON json=" + jSONObject);
        a.onCallback(new c(jSONObject, i, i2));
    }

    private static PeppermintCallback a(int i, int i2) {
        PeppermintLog.i("getCallbackNative");
        return new b(i, i2);
    }

    public static int asyncRequest(String str, String str2, int i, int i2) {
        PeppermintLog.i("asyncRequest requestName=" + str + " params=" + str2);
        if (f0a == null) {
            return -16;
        }
        JSONObject jSONObject;
        if (str2 != null) {
            try {
                if (str2.length() != 0) {
                    jSONObject = new JSONObject(str2);
                    return f0a.asyncRequest(str, jSONObject, a(i, i2));
                }
            } catch (JSONException e) {
                return PeppermintType.HUB_E_INVALID_JSON;
            }
        }
        jSONObject = new JSONObject();
        return f0a.asyncRequest(str, jSONObject, a(i, i2));
    }

    public static int auth(int i, int i2) {
        PeppermintLog.i(PeppermintURL.PEPPERMINT_AUTH_PATH);
        return f0a == null ? -16 : f0a.auth(a(i, i2));
    }

    private static native void callCSHubInitialize();

    public static int getIsPGS() {
        PeppermintLog.i("getIsPGS");
        return f0a == null ? 0 : Peppermint.getIsPGS();
    }

    public static Peppermint getPeppermint() {
        return f0a;
    }

    public static int guestAcquireUid(int i, int i2) {
        PeppermintLog.i("guestAcquireUid");
        return f0a == null ? -16 : f0a.guestAcquireUid(a(i, i2));
    }

    public static int guestBind(String str, String str2, int i, int i2) {
        PeppermintLog.i("guestBind giestUid=" + str + " candidateUid=" + str2);
        return f0a == null ? -16 : f0a.guestBind(str, str2, a(i, i2));
    }

    private static native void hubCallbackWithJSON(String str, int i, int i2);

    public static void hubInitializeJNI(Activity activity) {
        mainActivity = activity;
        callCSHubInitialize();
    }

    public static int initialize(String str, String str2, boolean z) {
        PeppermintLog.i("initialize appId=" + str + " gameIndex=" + str2 + "useTestSever" + z);
        if (f0a != null) {
            return 0;
        }
        f0a = new Peppermint(mainActivity);
        return f0a.initialize(str, str2, z);
    }

    public static int logout(int i, int i2) {
        PeppermintLog.i(PeppermintConstant.JSON_KEY_LOGOUT);
        return f0a == null ? -16 : f0a.logout(a(i, i2));
    }

    public static int pgsLoginProc(int i, int i2) {
        PeppermintLog.i("pgsLoginProc");
        return f0a == null ? -16 : f0a.pgsLoginProc(a(i, i2));
    }

    public static void setGLSurfaceViewForCallback(GLSurfaceView gLSurfaceView) {
        _glSurfaceView = gLSurfaceView;
    }

    public static int setOption(String str, String str2) {
        PeppermintLog.i("setOption allowedKey=" + str + "value=" + str2);
        return f0a == null ? -16 : f0a.setOption(str, str2);
    }

    public static int showDialog(String str, int i, int i2) {
        PeppermintLog.i("showDialog subPath=" + str);
        return f0a == null ? -16 : f0a.showDialog(str, a(i, i2));
    }

    public static int socialRequest(String str, String str2, String str3, int i, int i2) {
        PeppermintLog.i("hubSocialAction service=" + str + " name=" + str2 + " params=" + str3);
        if (f0a == null) {
            return -16;
        }
        JSONObject jSONObject;
        if (str3 != null) {
            try {
                if (str3.length() != 0) {
                    jSONObject = new JSONObject(str3);
                    return f0a.socialRequest(str, str2, jSONObject, a(i, i2));
                }
            } catch (JSONException e) {
                return PeppermintType.HUB_E_INVALID_JSON;
            }
        }
        jSONObject = new JSONObject();
        return f0a.socialRequest(str, str2, jSONObject, a(i, i2));
    }

    public static int uninitialize() {
        PeppermintLog.i("uninitialize");
        return f0a == null ? 0 : f0a.uninitialize();
    }
}
