package com.com2us.module.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.opengl.GLSurfaceView;
import android.widget.Toast;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.util.WrapperUtility;
import com.com2us.module.view.SurfaceViewWrapper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONObject;

public class ModuleManager implements ActivityStateListener, CletStateListener, Constants {
    private static ModuleManager manager = new ModuleManager();
    private String appIdForIdentity;
    private int appOriginalHeight = 0;
    private int appOriginalWidth = 0;
    private int config = 0;
    private int disableModules = 0;
    private boolean isDestryoed = false;
    private Logger logger;
    private ArrayList<Modulable> moduleList = new ArrayList();
    private String[] testAppIds = new String[]{"com.com2us.module.activeuser.test"};
    private int wrapperDevelopmentRevision;
    private String wrapperRevision;

    public static ModuleManager getInstance() {
        return manager;
    }

    private ModuleManager() {
    }

    public void setAppIdForIdentity(String appid) {
        this.appIdForIdentity = appid;
    }

    public String getAppIdForIdentity() {
        return this.appIdForIdentity;
    }

    public void setExitAppIfCracked(boolean exit) {
        boolean z = exit && WrapperUtility.IsCracked();
        this.isDestryoed = z;
    }

    public void setConfig(int config) {
        this.config = config;
    }

    public void setDisableModules(int modules) {
        this.disableModules = modules;
    }

    public void setAppOriginalResolution(int width, int height) {
        this.appOriginalWidth = width;
        this.appOriginalHeight = height;
    }

    public void setWrapperDevelopmentRevision(int revision) {
        this.wrapperDevelopmentRevision = revision;
    }

    private void loadTapjoyManager(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.TAPJOYMANAGER).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadTapJoyManager]ClassNotFound");
        } catch (Exception e2) {
            this.logger.e("[loadTapJoyManager]", e2);
        }
    }

    private void loadADManager(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.ADMANAGER).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadADManager]ClassNotFound");
        } catch (Exception e2) {
            this.logger.e("[loadADManager]", e2);
        }
    }

    private void loadSNSManager(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.SNSMANAGER).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadSNSManager]ClassNotFound");
        } catch (Exception e2) {
            this.logger.e("[loadSNSManager]", e2);
        }
    }

    private void loadC2DM(Activity activity, SurfaceViewWrapper view) {
        boolean isTestServer = true;
        try {
            Constructor<?> con;
            Object[] params;
            Class<?> cls = Class.forName(ModuleClassName.C2DM);
            if ((this.config & 1) == 0) {
                isTestServer = false;
            }
            if ((this.config & 2) != 0) {
                con = cls.getConstructor(new Class[]{Activity.class, Boolean.TYPE, Boolean.TYPE});
                params = new Object[]{activity, Boolean.valueOf(isTestServer), Boolean.valueOf(false)};
            } else {
                con = cls.getConstructor(new Class[]{Activity.class, Boolean.TYPE, SurfaceViewWrapper.class});
                params = new Object[]{activity, Boolean.valueOf(isTestServer), view};
            }
            Modulable modulable = (Modulable) con.newInstance(params);
            modulable.setLogged(this.logger.isLogged());
            this.moduleList.add(modulable);
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadC2DM]ClassNotFound");
        } catch (Exception e2) {
            this.logger.e("[loadC2DM]", e2);
        }
    }

    private void loadPush(Activity activity, SurfaceViewWrapper view) {
        boolean isTestServer = true;
        try {
            Class<?> cls = Class.forName(ModuleClassName.PUSH);
            Method method_getInstance = cls.getMethod("getInstance", new Class[]{Context.class});
            Method method_setUseTestServer = cls.getMethod("setUseTestServer", new Class[]{Boolean.TYPE});
            Method method_setUseCLibrary = cls.getMethod("setUseCLibrary", new Class[]{SurfaceViewWrapper.class});
            Method method_start = cls.getMethod("start", new Class[0]);
            if ((this.config & 1) == 0) {
                isTestServer = false;
            }
            Modulable modulable = (Modulable) method_getInstance.invoke(null, new Object[]{activity});
            modulable.setLogged(this.logger.isLogged());
            method_setUseTestServer.invoke(modulable, new Object[]{Boolean.valueOf(isTestServer)});
            if ((this.config & 2) == 0) {
                method_setUseCLibrary.invoke(modulable, new Object[]{view});
            }
            method_start.invoke(modulable, new Object[0]);
            this.moduleList.add(modulable);
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadPush]ClassNotFound");
        } catch (Exception e2) {
            this.logger.e("[loadPush]", e2);
        }
    }

    private void loadInApp(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.INAPP).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadInApp]ClassNotFound");
        } catch (Exception e2) {
            this.logger.e("[loadInApp]", e2);
        }
    }

    private void loadActiveUser(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.ACTIVEUSER).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadActiveUser]ClassNotFound");
        } catch (Exception e2) {
            this.logger.e("[loadActiveUser]", e2);
        }
    }

    private void loadNewsBanner2(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.NEWSBANNER2).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadNewsBanner2]ClassNotFound");
        } catch (Exception e2) {
            this.logger.e("[loadNewsBanner2]", e2);
        }
    }

    private void loadChartBoostManager(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.CHARTBOOST).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadChartBoostManager]ClassNotFound : ChartBoostManager");
        } catch (Exception e2) {
            this.logger.e("[loadChartBoostManager]", e2);
        }
    }

    private void loadSocialMedia(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.SOCIALMEDIA).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadSocialMedia]ClassNotFound : SocialMedia");
        } catch (Exception e2) {
            this.logger.e("[loadSocialMedia]", e2);
        }
    }

    private void loadSpider(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.SPIDER).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadSpider]ClassNotFound : Spider");
        } catch (Exception e2) {
            this.logger.e("[loadSpider]", e2);
        }
    }

    private void loadMercury(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.MERCURY).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadMercury]ClassNotFound : Mercury");
        } catch (Exception e2) {
            this.logger.e("[loadMercury]", e2);
        }
    }

    private void loadOfferwall(Activity activity, SurfaceViewWrapper view) {
        try {
            this.moduleList.add((Modulable) Class.forName(ModuleClassName.OFFERWALL).getConstructor(new Class[]{Activity.class, SurfaceViewWrapper.class}).newInstance(new Object[]{activity, view}));
        } catch (ClassNotFoundException e) {
            this.logger.d("[loadOfferwall]ClassNotFound : Offerwall");
        } catch (Exception e2) {
            this.logger.e("[loadOfferwall]", e2);
        }
    }

    public void load(Activity activity, GLSurfaceView view) {
        load(activity, new SurfaceViewWrapper(view) {
            public void queueEvent(Runnable r) {
                if (this.view instanceof GLSurfaceView) {
                    ((GLSurfaceView) this.view).queueEvent(r);
                }
            }
        });
    }

    public void load(Activity activity, SurfaceViewWrapper view) {
        Iterator it;
        ArrayList<String> permissionList = new ArrayList();
        StringBuilder notFoundPermission = new StringBuilder();
        this.logger = LoggerGroup.createLogger(Constants.TAG, activity);
        if (!LoggerGroup.isLocked()) {
            try {
                if (!((activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0).applicationInfo.flags & 2) != 0)) {
                    setLogged(false);
                }
            } catch (NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (this.appIdForIdentity != null) {
            for (String id : this.testAppIds) {
                if (id.equals(this.appIdForIdentity)) {
                    Toast.makeText(activity, "TestAppId : " + id, 1).show();
                    break;
                }
            }
        }
        if ((this.disableModules & 1) == 0) {
            loadTapjoyManager(activity, view);
        }
        if ((this.disableModules & 4) == 0) {
            loadADManager(activity, view);
        }
        if ((this.disableModules & 8) == 0) {
            loadSNSManager(activity, view);
        }
        if ((this.disableModules & 16) == 0) {
            loadPush(activity, view);
        }
        if ((this.disableModules & 32) == 0) {
            loadInApp(activity, view);
        }
        if ((this.disableModules & ModuleConfig.ACTIVEUSER_MODULE) == 0) {
            loadActiveUser(activity, view);
        }
        if ((this.disableModules & 64) == 0) {
            loadNewsBanner2(activity, view);
        }
        if ((this.disableModules & 2) == 0) {
            loadChartBoostManager(activity, view);
        }
        if ((this.disableModules & ModuleConfig.SOCIAL_MEDIA_MOUDLE) == 0) {
            loadSocialMedia(activity, view);
        }
        if ((this.disableModules & ModuleConfig.SPIDER_MODULE) == 0) {
            loadSpider(activity, view);
        }
        if ((this.disableModules & ModuleConfig.MERCURY_MODULE) == 0) {
            loadMercury(activity, view);
        }
        if ((this.disableModules & ModuleConfig.OFFERWALL_MODULE) == 0) {
            loadOfferwall(activity, view);
        }
        try {
            if (this.appIdForIdentity != null) {
                it = this.moduleList.iterator();
                while (it.hasNext()) {
                    ((Modulable) it.next()).setAppIdForIdentity(this.appIdForIdentity);
                }
            }
            if (this.logger.isLogged()) {
                Iterator it2 = this.moduleList.iterator();
                while (it2.hasNext()) {
                    for (String permission : ((Modulable) it2.next()).getPermission()) {
                        if (!permissionList.contains(permission)) {
                            permissionList.add(permission);
                            if (activity.checkCallingOrSelfPermission(permission) != 0) {
                                notFoundPermission.append(permission).append("\n");
                            }
                        }
                    }
                }
                if (notFoundPermission.length() != 0) {
                    this.logger.e("Permission not found : \n" + notFoundPermission.toString());
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        if (this.isDestryoed) {
            destroy();
            if (this.logger.isLogged()) {
                this.logger.i("Failed To Load Modules");
            }
        } else if (this.logger.isLogged()) {
            StringBuilder sb = new StringBuilder();
            sb.append("=== VERSION : " + Version + " ===\n");
            sb.append("=== MODULE LIST ===\n");
            it = this.moduleList.iterator();
            while (it.hasNext()) {
                Modulable module = (Modulable) it.next();
                sb.append(module.getName() + " : " + module.getVersion() + "\n");
            }
            sb.append("==================");
            this.logger.i(sb.toString());
        }
    }

    public static JSONObject getVersionList() {
        return ModuleVersionList.getInstance().getList();
    }

    public static ModuleData getDatas(Context context) {
        return ModuleData.getInstance(context);
    }

    public void setLogged(boolean b) {
        LoggerGroup.setLogged(b);
        LoggerGroup.setLocked(true);
        if (this.logger != null) {
            this.logger.setLogged(b);
        }
    }

    public void destroy() {
        if (!this.isDestryoed) {
            this.isDestryoed = true;
            Iterator it = this.moduleList.iterator();
            while (it.hasNext()) {
                ((Modulable) it.next()).destroy();
            }
            this.moduleList.clear();
        }
    }

    public void onActivityStarted() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onActivityStarted();
        }
    }

    public void onActivityRestarted() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onActivityRestarted();
        }
    }

    public void onActivityResumed() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onActivityResumed();
        }
    }

    public void onNewIntent(Intent intent) {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onNewIntent(intent);
        }
    }

    public void onActivityPaused() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onActivityPaused();
        }
    }

    public void onActivityStopped() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onActivityStopped();
        }
    }

    public void onActivityDestroyed() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onActivityDestroyed();
        }
    }

    public void onActivityCreated() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onActivityCreated();
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onWindowFocusChanged(hasFocus);
        }
    }

    public void onCletStarted() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onCletStarted();
        }
    }

    public void onCletResumed() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onCletResumed();
        }
    }

    public void onCletPaused() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onCletPaused();
        }
    }

    public void onCletDestroyed() {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onCletDestroyed();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Iterator it = this.moduleList.iterator();
        while (it.hasNext()) {
            ((Modulable) it.next()).onActivityResult(requestCode, resultCode, intent);
        }
    }
}
