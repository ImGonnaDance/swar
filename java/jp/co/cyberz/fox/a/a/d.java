package jp.co.cyberz.fox.a.a;

import android.os.AsyncTask;
import android.os.Build.VERSION;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;

public final class d {
    private static Method a;
    private static Object b;
    private static boolean c = (VERSION.SDK_INT >= 13);

    static {
        a = null;
        b = null;
        for (Method method : AsyncTask.class.getMethods()) {
            if ("executeOnExecutor".equals(method.getName())) {
                Class[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 2 && parameterTypes[0] == Executor.class && parameterTypes[1].isArray()) {
                    a = method;
                    break;
                }
            }
        }
        if (a != null) {
            try {
                b = AsyncTask.class.getField("THREAD_POOL_EXECUTOR").get(null);
            } catch (Exception e) {
                a = null;
            }
        }
    }

    public static void a(AsyncTask asyncTask, Object... objArr) {
        try {
            if (!c || a == null) {
                asyncTask.execute(objArr);
                return;
            }
            a.invoke(asyncTask, new Object[]{b, objArr});
        } catch (Exception e) {
        }
    }

    private static boolean a() {
        return VERSION.SDK_INT >= 13;
    }
}
