package com.com2us.module.push.unityplugin;

import android.content.Context;
import com.com2us.module.push.Push;
import com.com2us.module.push.PushCallback;
import com.com2us.module.push.PushConfig;
import java.lang.reflect.Method;

public class PushUnityPlugin implements PushCallback {
    private static PushUnityPlugin mPushPlugin = null;
    public final String PluginVersion = "2015.03.31";
    Class<?> cls_unityPlayer = null;
    Method method_unitySendMessage = null;
    private String unityObjName = null;

    public static PushUnityPlugin getInstance() {
        if (mPushPlugin == null) {
            mPushPlugin = new PushUnityPlugin();
        }
        return mPushPlugin;
    }

    private PushUnityPlugin() {
        PushConfig.LogI("PushUnityPlugin create.");
        try {
            this.cls_unityPlayer = Class.forName("com.unity3d.player.UnityPlayer");
            this.method_unitySendMessage = this.cls_unityPlayer.getMethod("UnitySendMessage", new Class[]{String.class, String.class, String.class});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registerUnityCallbackHandler(Context context, String objName) {
        this.unityObjName = objName;
        Push.getInstance(context).registerCallbackHandler((PushCallback) this);
    }

    public void onReceivedGCMPush(int pushID, int remainPushCallback) {
        if (this.method_unitySendMessage != null) {
            try {
                this.method_unitySendMessage.invoke(null, new Object[]{this.unityObjName, "setOnReceivedGCMPush", String.valueOf(pushID) + "\t" + String.valueOf(remainPushCallback)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onReceivedLocalPush(int pushID, int remainPushCallback) {
        if (this.method_unitySendMessage != null) {
            try {
                this.method_unitySendMessage.invoke(null, new Object[]{this.unityObjName, "setOnReceivedLocalPush", String.valueOf(pushID) + "\t" + String.valueOf(remainPushCallback)});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
