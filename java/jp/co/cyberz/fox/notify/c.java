package jp.co.cyberz.fox.notify;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

public final class c extends AsyncTask {
    private static Long a = Long.valueOf(1);
    private final String b = "F.O.X Notify";
    private Context c;
    private String d;

    public c(Context context, String str) {
        this.c = context;
        this.d = str;
    }

    private static Long a(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return Long.valueOf(0);
        }
        HttpUriRequest httpGet = new HttpGet(strArr[0]);
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), 5000);
        Long valueOf;
        try {
            HttpResponse execute = defaultHttpClient.execute(httpGet);
            int statusCode = execute.getStatusLine().getStatusCode();
            if (statusCode != SelectTarget.TARGETING_SUCCESS) {
                Log.e("F.O.X Notify", "PushManagerTask failed. HTTP Status code = " + statusCode);
                valueOf = Long.valueOf(0);
                return valueOf;
            } else if (execute.getEntity() == null) {
                Log.e("F.O.X Notify", "PushManagerTask failed: Entity nothing.");
                valueOf = Long.valueOf(0);
                defaultHttpClient.getConnectionManager().shutdown();
                return valueOf;
            } else {
                Log.d("F.O.X Notify", "status code : " + statusCode);
                defaultHttpClient.getConnectionManager().shutdown();
                return a;
            }
        } catch (ClientProtocolException e) {
            Log.e("F.O.X Notify", "PushManagerTask failed: " + e.getMessage());
            e.printStackTrace();
            valueOf = Long.valueOf(0);
            return valueOf;
        } catch (IOException e2) {
            Log.e("F.O.X Notify", "PushManagerTask failed. " + e2.getMessage());
            e2.printStackTrace();
            valueOf = Long.valueOf(0);
            return valueOf;
        } finally {
            defaultHttpClient.getConnectionManager().shutdown();
        }
    }

    private void a(Long l) {
        System.out.println("result: " + l);
        if (l.longValue() == 1 && this.d != null) {
            a.c(this.c, this.d);
            Log.d("F.O.X Notify", "logged registed status");
            this.d = null;
            a.c();
        }
    }

    protected final /* synthetic */ Object doInBackground(Object... objArr) {
        return a((String[]) objArr);
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        Long l = (Long) obj;
        System.out.println("result: " + l);
        if (l.longValue() == 1 && this.d != null) {
            a.c(this.c, this.d);
            Log.d("F.O.X Notify", "logged registed status");
            this.d = null;
            a.c();
        }
    }
}
