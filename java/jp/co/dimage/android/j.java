package jp.co.dimage.android;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

public final class j extends AsyncTask {
    private d a = null;
    private g b = null;
    private String c = null;

    class a {
        public static final String a = "0";
        public static final String b = "1";
        public static final String c = "2";
        public static final String d = "0";
        public static final String e = "1";
        public static final String f = "2";
        final /* synthetic */ j g;
        private String h;
        private String i;
        private String j;

        a(j jVar) {
            this.g = jVar;
        }

        public final String a() {
            return this.h;
        }

        public final void a(String str) {
            this.h = str;
        }

        public final String b() {
            return this.i;
        }

        public final void b(String str) {
            this.i = str;
        }

        public final String c() {
            return this.j;
        }

        public final void c(String str) {
            this.j = str;
        }
    }

    public j(d dVar) {
        this.a = dVar;
        this.b = new g(dVar);
    }

    public j(d dVar, String str) {
        this.a = dVar;
        this.b = new g(dVar);
        this.c = str;
    }

    private a a(String... strArr) {
        ClientConnectionManager clientConnectionManager = null;
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        a aVar = new a(this);
        HttpUriRequest httpGet = new HttpGet(strArr[0]);
        HttpClient defaultHttpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), 5000);
        defaultHttpClient.getParams().setParameter("http.useragent", this.a.j());
        HttpResponse execute = defaultHttpClient.execute(httpGet);
        int statusCode = execute.getStatusLine().getStatusCode();
        if (statusCode != SelectTarget.TARGETING_SUCCESS) {
            Log.e(f.aU, "ConversionTask faild. HTTP Status code = " + statusCode);
            return aVar;
        }
        HttpEntity entity = execute.getEntity();
        if (entity == null) {
            Log.e(f.aU, "ConversionTask: Entity nothing.");
        }
        LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(entity.getContent()));
        while (true) {
            String readLine = lineNumberReader.readLine();
            if (readLine == null) {
                defaultHttpClient.getConnectionManager().shutdown();
                return aVar;
            }
            String[] split = readLine.split(": *", 2);
            if (split == null || split.length != 2) {
                Log.e(f.aU, "invalid response format '" + readLine + "'");
            } else {
                try {
                    if ("URL".equals(split[0])) {
                        aVar.a(split[1]);
                    }
                    if ("TYPE".equals(split[0])) {
                        aVar.b(split[1]);
                    }
                    if ("STATUS".equals(split[0])) {
                        aVar.c(split[1]);
                    }
                } catch (Exception e) {
                    Log.e(f.aU, "ConversionTask faild. " + e.getMessage());
                    e.printStackTrace();
                    return clientConnectionManager;
                } finally {
                    clientConnectionManager = defaultHttpClient.getConnectionManager();
                    clientConnectionManager.shutdown();
                }
            }
        }
    }

    private void a(a aVar) {
        if (aVar != null) {
            Log.i(f.aU, "ConversionTask: url=" + aVar.a() + " type=" + aVar.b() + " status=" + aVar.c());
            if (a.d.equals(aVar.c())) {
                Log.e(f.aU, "ConversionTask: server status is failed.");
                return;
            }
            if (a.e.equals(aVar.c())) {
                this.b.c();
                if (this.c != null) {
                    this.b.f(this.c);
                }
            }
            if (!a.d.equals(aVar.b())) {
                String a = aVar.a();
                if (a != null && a.length() != 0 && !"*".equals(a)) {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(a));
                        this.b.a(intent);
                    } catch (ActivityNotFoundException e) {
                    }
                }
            }
        }
    }

    protected final /* synthetic */ Object doInBackground(Object... objArr) {
        return a((String[]) objArr);
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        a aVar = (a) obj;
        if (aVar != null) {
            Log.i(f.aU, "ConversionTask: url=" + aVar.a() + " type=" + aVar.b() + " status=" + aVar.c());
            if (a.d.equals(aVar.c())) {
                Log.e(f.aU, "ConversionTask: server status is failed.");
                return;
            }
            if (a.e.equals(aVar.c())) {
                this.b.c();
                if (this.c != null) {
                    this.b.f(this.c);
                }
            }
            if (!a.d.equals(aVar.b())) {
                String a = aVar.a();
                if (a != null && a.length() != 0 && !"*".equals(a)) {
                    try {
                        Intent intent = new Intent("android.intent.action.VIEW");
                        intent.setData(Uri.parse(a));
                        this.b.a(intent);
                    } catch (ActivityNotFoundException e) {
                    }
                }
            }
        }
    }
}
