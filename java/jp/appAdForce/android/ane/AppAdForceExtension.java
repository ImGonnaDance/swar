package jp.appAdForce.android.ane;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

public class AppAdForceExtension implements FREExtension {
    public FREContext createContext(String str) {
        return new AppAdForceContext();
    }

    public void dispose() {
    }

    public void initialize() {
    }
}
