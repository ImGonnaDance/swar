package jp.co.dimage.android;

import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import jp.co.cyberz.fox.a.a.i;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

public final class e extends AsyncTask {
    private static Long a = Long.valueOf(1);
    private static final String b = "tke";
    private static final String c = "fbe";
    private static final String d = "bre";
    private static final String e = "bri";
    private static final String f = "sav";
    private static final String g = "cke";
    private static final String h = "fie";
    private static final String i = "ref";
    private static final String j = "ade";
    private static final String k = "1";
    private static final String l = "delay";
    private d m = null;
    private g n = null;
    private a o;

    public e(d dVar, g gVar, a aVar) {
        this.m = dVar;
        this.n = gVar;
        this.o = aVar;
    }

    private Long a(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        HttpUriRequest httpGet = new HttpGet(strArr[0]);
        HttpClient defaultHttpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), 10000);
        defaultHttpClient.getParams().setParameter("http.useragent", this.m.j());
        try {
            HttpResponse execute = defaultHttpClient.execute(httpGet);
            int statusCode = execute.getStatusLine().getStatusCode();
            if (statusCode != SelectTarget.TARGETING_SUCCESS) {
                Log.e(f.aU, "CheckCvModeTask failed. HTTP Status code = " + statusCode);
                return null;
            }
            HttpEntity entity = execute.getEntity();
            if (entity == null) {
                Log.e(f.aU, "CheckCvModeTask: Entity nothing.");
                defaultHttpClient.getConnectionManager().shutdown();
                return null;
            }
            Map map;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
            String readLine = bufferedReader.readLine();
            if (p.a(readLine)) {
                map = null;
            } else {
                map = a(readLine);
                if (map == null) {
                    Log.e(f.aU, "CheckCvModeTask: Parameter is nothing.");
                    defaultHttpClient.getConnectionManager().shutdown();
                    return null;
                }
            }
            bufferedReader.close();
            this.n.a(k.equals(map.get(b)));
            this.n.b(k.equals(map.get(c)));
            this.n.d(k.equals(map.get(d)));
            this.n.e(k.equals(map.get(e)));
            this.n.f(k.equals(map.get(f)));
            this.n.g(k.equals(map.get(g)));
            this.n.h(k.equals(map.get(h)));
            this.n.i();
            this.n.c(k.equals(map.get(j)));
            try {
                this.n.i(k.equals(map.get(i)));
                this.n.a(Integer.valueOf(Integer.parseInt((String) map.get(l))));
            } catch (Exception e) {
            }
            defaultHttpClient.getConnectionManager().shutdown();
            return a;
        } catch (Exception e2) {
            Log.e(f.aU, "CheckCvModeTask faild. " + e2.getMessage());
            e2.printStackTrace();
            return null;
        } finally {
            defaultHttpClient.getConnectionManager().shutdown();
        }
    }

    private static Map a(String str) {
        Map hashMap = new HashMap();
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(str, i.b);
            while (stringTokenizer.hasMoreTokens()) {
                StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "=");
                if (stringTokenizer2.countTokens() == 2) {
                    hashMap.put(stringTokenizer2.nextToken(), stringTokenizer2.nextToken());
                }
            }
            return hashMap;
        } catch (Exception e) {
            return null;
        }
    }

    private void a() {
        if (this.n != null && this.n.d()) {
            this.n.a(this.o);
        }
    }

    private void a(a aVar) {
        this.o = aVar;
    }

    private a b() {
        return this.o;
    }

    protected final /* synthetic */ Object doInBackground(Object... objArr) {
        return a((String[]) objArr);
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        if (this.n != null && this.n.d()) {
            this.n.a(this.o);
        }
    }
}
