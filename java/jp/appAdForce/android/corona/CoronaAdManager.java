package jp.appAdForce.android.corona;

import android.content.Context;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;
import jp.appAdForce.android.AdManager;
import jp.co.cyberz.fox.a.a.i;

public final class CoronaAdManager implements NamedJavaFunction {
    private static String a;

    public static class SendConversionForMobage implements NamedJavaFunction {
        public String getName() {
            return "sendConversionForMobage";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            if (!(coronaActivity == null || checkString == null || i.a.equals(checkString))) {
                coronaActivity.runOnUiThread(new b(this, coronaActivity, checkString));
            }
            return 0;
        }
    }

    public static class SendConversionForMobageAndCAReward implements NamedJavaFunction {
        public String getName() {
            return "sendConversionForMobageAndCAReward";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            if (!(coronaActivity == null || checkString == null || i.a.equals(checkString))) {
                coronaActivity.runOnUiThread(new c(this, coronaActivity, checkString));
            }
            return 0;
        }
    }

    public static class SendConversionForMobageAndCARewardWithStartPageUrl implements NamedJavaFunction {
        public String getName() {
            return "sendConversionForMobageAndCARewardWithStartPageUrl";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            String checkString2 = luaState.checkString(2);
            if (!(coronaActivity == null || checkString2 == null || i.a.equals(checkString2))) {
                coronaActivity.runOnUiThread(new d(this, coronaActivity, checkString, checkString2));
            }
            return 0;
        }
    }

    public static class SendConversionForMobageWithStartPageUrl implements NamedJavaFunction {
        public String getName() {
            return "sendConversionForMobageWithStartPageUrl";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            String checkString2 = luaState.checkString(2);
            if (coronaActivity != null) {
                coronaActivity.runOnUiThread(new e(this, coronaActivity, checkString, checkString2));
            }
            return 0;
        }
    }

    public static class SendConversionViaCAReward implements NamedJavaFunction {
        public String getName() {
            return "sendConversionViaCAReward";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            if (!(coronaActivity == null || checkString == null || i.a.equals(checkString))) {
                coronaActivity.runOnUiThread(new f(this, coronaActivity, checkString));
            }
            return 0;
        }
    }

    public static class SendConversionViaCARewardWithBuid implements NamedJavaFunction {
        public String getName() {
            return "sendConversionViaCARewardWithBuid";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            String checkString2 = luaState.checkString(2);
            if (!(coronaActivity == null || checkString == null || i.a.equals(checkString))) {
                coronaActivity.runOnUiThread(new g(this, coronaActivity, checkString, checkString2));
            }
            return 0;
        }
    }

    public static class SendConversionWithStartPageUrl implements NamedJavaFunction {
        public String getName() {
            return "sendConversionWithStartPageUrl";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            String checkString2 = luaState.checkString(2);
            if (!(coronaActivity == null || checkString == null || i.a.equals(checkString))) {
                coronaActivity.runOnUiThread(new h(this, coronaActivity, checkString2, checkString));
            }
            return 0;
        }
    }

    public static class SendConversionWithUrlScheme implements NamedJavaFunction {
        public String getName() {
            return "sendConversionWithUrlScheme";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            String checkString2 = luaState.checkString(2);
            if (!(coronaActivity == null || checkString == null || i.a.equals(checkString))) {
                coronaActivity.runOnUiThread(new i(this, coronaActivity, checkString2, checkString));
            }
            return 0;
        }
    }

    public static class SendUserIdForMobage implements NamedJavaFunction {
        public String getName() {
            return "sendUserIdForMobage";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            String checkString = luaState.checkString(1);
            if (!(coronaActivity == null || checkString == null || i.a.equals(checkString))) {
                coronaActivity.runOnUiThread(new j(this, coronaActivity, checkString));
            }
            return 0;
        }
    }

    public static class SetServerUrl implements NamedJavaFunction {
        public String getName() {
            return "setServerUrl";
        }

        public int invoke(LuaState luaState) {
            CoronaAdManager.a = luaState.checkString(1);
            return (CoronaAdManager.a == null || i.a.equals(CoronaAdManager.a)) ? 0 : 0;
        }
    }

    public static AdManager a(Context context) {
        AdManager adManager = new AdManager(context);
        if (!(a == null || i.a.equals(a))) {
            adManager.setServerUrl(a);
        }
        return adManager;
    }

    public final String getName() {
        return "sendConversion";
    }

    public final int invoke(LuaState luaState) {
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        if (coronaActivity != null) {
            coronaActivity.runOnUiThread(new a(this, coronaActivity));
        }
        return 0;
    }
}
