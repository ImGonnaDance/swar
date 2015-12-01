package com.com2us.module.activeuser.moduleversioncheck;

import android.content.Context;
import com.com2us.module.activeuser.ActiveUserModule;
import com.com2us.module.activeuser.ActiveUserNetwork.Received;
import com.com2us.module.activeuser.ActiveUserProperties;
import com.com2us.module.manager.ModuleManager;
import com.com2us.module.util.Logger;
import org.json.JSONObject;

public class ModuleVersionCheck implements ActiveUserModule {
    private Context context = null;
    private Logger logger = null;

    public ModuleVersionCheck(Context context, Logger logger) {
        this.context = context;
        this.logger = logger;
        this.logger.d("[ModuleVersionCheck] initialize");
    }

    private void getVersions() {
        JSONObject moduleVersionList = ModuleManager.getVersionList();
        if (moduleVersionList == null || moduleVersionList.length() <= 0) {
            this.logger.d("[ModuleVersionCheck] sendVersionCheck is failed : VersionList is Empty");
            return;
        }
        ActiveUserProperties.setProperty(ActiveUserProperties.MODULEVERSIONCHECK_DATA_PROPERTY, moduleVersionList.toString());
        ActiveUserProperties.storeProperties(this.context);
    }

    public boolean isExecutable() {
        getVersions();
        return false;
    }

    public void responseNetwork(Received data) {
    }

    public void destroy() {
    }
}
