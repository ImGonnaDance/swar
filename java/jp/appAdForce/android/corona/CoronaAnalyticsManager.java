package jp.appAdForce.android.corona;

import com.ansca.corona.CoronaActivity;
import com.ansca.corona.CoronaEnvironment;
import com.naef.jnlua.LuaState;
import com.naef.jnlua.NamedJavaFunction;

public final class CoronaAnalyticsManager implements NamedJavaFunction {

    public static class SendEndSession implements NamedJavaFunction {
        public String getName() {
            return "sendEndSession";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            coronaActivity.runOnUiThread(new l(this, coronaActivity));
            return 0;
        }
    }

    public static class SendEvent implements NamedJavaFunction {
        public String getName() {
            return "sendEvent";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            coronaActivity.runOnUiThread(new m(this, coronaActivity, luaState.checkString(1), luaState.checkString(2), luaState.checkString(3), luaState.checkInteger(4)));
            return 0;
        }
    }

    public static class SendPurchaseEvent implements NamedJavaFunction {
        public String getName() {
            return "sendPurchaseEvent";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            coronaActivity.runOnUiThread(new n(this, coronaActivity, luaState.checkString(1), luaState.checkString(2), luaState.checkString(3), luaState.checkString(4), luaState.checkString(5), luaState.checkString(6), luaState.checkNumber(7), luaState.checkInteger(8), luaState.checkString(9)));
            return 0;
        }
    }

    public static class SendUserInfo implements NamedJavaFunction {
        public String getName() {
            return "sendUserInfo";
        }

        public int invoke(LuaState luaState) {
            CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
            coronaActivity.runOnUiThread(new o(this, coronaActivity, luaState.checkString(1), luaState.checkString(2), luaState.checkString(3), luaState.checkString(4), luaState.checkString(5), luaState.checkString(6)));
            return 0;
        }
    }

    public final String getName() {
        return "sendStartSession";
    }

    public final int invoke(LuaState luaState) {
        CoronaActivity coronaActivity = CoronaEnvironment.getCoronaActivity();
        coronaActivity.runOnUiThread(new k(this, new Throwable().getStackTrace()[1].getClassName(), coronaActivity));
        return 0;
    }
}
