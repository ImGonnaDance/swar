package com.com2us.peppermint.socialextension;

import com.com2us.peppermint.Peppermint;
import java.util.HashMap;

public class PeppermintSocialManager {
    private static Peppermint a;
    private static PeppermintSocialManager f31a = null;
    private HashMap<String, PeppermintSocialPlugin> f32a;

    public PeppermintSocialManager() {
        a();
    }

    private void a() {
        this.f32a = new HashMap();
        try {
            Class.forName("com.com2us.peppermint.socialextension.PeppermintSocialPluginFacebook");
            a(PeppermintSocialPluginFacebook.plugin());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("com.com2us.peppermint.socialextension.PeppermintSocialPluginGooglePlus");
            a(PeppermintSocialPluginGooglePlus.plugin());
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        }
    }

    private void a(PeppermintSocialPlugin peppermintSocialPlugin) {
        if (peppermintSocialPlugin != null) {
            this.f32a.put(peppermintSocialPlugin.getServiceName(), peppermintSocialPlugin);
        }
    }

    public static Peppermint getPeppermint() {
        return a;
    }

    public static PeppermintSocialPlugin pluginByName(String str) {
        return (PeppermintSocialPlugin) sharedInstance().f32a.get(str);
    }

    public static void setPeppermint(Peppermint peppermint) {
        a = peppermint;
    }

    public static PeppermintSocialManager sharedInstance() {
        if (f31a == null) {
            f31a = new PeppermintSocialManager();
        }
        return f31a;
    }

    public HashMap<String, PeppermintSocialPlugin> getPlugins() {
        return this.f32a;
    }
}
