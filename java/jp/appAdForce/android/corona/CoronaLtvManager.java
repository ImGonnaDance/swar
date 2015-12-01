package jp.appAdForce.android.corona;

import android.util.Log;
import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;
import java.util.HashMap;
import java.util.Map;
import jp.appAdForce.android.AdManager;
import jp.appAdForce.android.LtvManager;
import jp.co.cyberz.fox.a.a.i;

public final class CoronaLtvManager implements NamedJavaFunction {
    private static Map a;

    public static class AddLtvParam implements NamedJavaFunction {
        public String getName() {
            return "addLtvParam";
        }

        public int invoke(LuaState luaState) {
            String checkString = luaState.checkString(1);
            String checkString2 = luaState.checkString(2);
            if (!(checkString == null || checkString2 == null || i.a.equals(checkString) || i.a.equals(checkString2))) {
                if (CoronaLtvManager.a == null) {
                    CoronaLtvManager.a = new HashMap();
                }
                CoronaLtvManager.a.put(checkString, checkString2);
                Log.d("SendLtvConversion", "AddParam key : " + checkString + ", value : " + checkString2);
            }
            return 0;
        }
    }

    public static class LtvOpenBrowser implements NamedJavaFunction {
        public String getName() {
            return "ltvOpenBrowser";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            coronaActivity.runOnUiThread(new q(this, coronaActivity, luaState.checkString(1)));
            return 0;
        }
    }

    public static class SetLtvCookie implements NamedJavaFunction {
        public String getName() {
            return "setLtvCookie";
        }

        public int invoke(LuaState luaState) {
            new LtvManager(new AdManager(CoronaEnvironment.getCoronaActivity())).setLtvCookie();
            return 0;
        }
    }

    public final String getName() {
        return "sendLtvConversion";
    }

    public final int invoke(LuaState luaState) {
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        int checkInteger = luaState.checkInteger(1);
        String checkString = luaState.checkString(2);
        if (checkInteger != 0) {
            coronaActivity.runOnUiThread(new p(this, coronaActivity, checkString, checkInteger));
        }
        return 0;
    }
}
