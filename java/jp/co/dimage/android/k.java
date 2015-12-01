package jp.co.dimage.android;

import android.os.AsyncTask;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

public final class k extends AsyncTask {
    private String a = null;
    private d b = null;

    class a {
        final /* synthetic */ k a;
        private int b;

        a(k kVar) {
            this.a = kVar;
        }

        public final int a() {
            return this.b;
        }

        public final void b() {
            this.b = 503;
        }
    }

    public k(d dVar, String str) {
        this.b = dVar;
        this.a = str;
    }

    private a a(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            return null;
        }
        a aVar = new a(this);
        HttpUriRequest httpGet = new HttpGet(strArr[0]);
        HttpClient defaultHttpClient = new DefaultHttpClient();
        try {
            HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), 5000);
            defaultHttpClient.getParams().setParameter("http.useragent", f.s);
            aVar.b = defaultHttpClient.execute(httpGet).getStatusLine().getStatusCode();
            if (aVar.b != SelectTarget.TARGETING_SUCCESS) {
                return aVar;
            }
            defaultHttpClient.getConnectionManager().shutdown();
            return aVar;
        } catch (Exception e) {
            aVar.b();
            return aVar;
        } finally {
            defaultHttpClient.getConnectionManager().shutdown();
        }
    }

    private void a(a aVar) {
        if (aVar != null && SelectTarget.TARGETING_SUCCESS == aVar.a()) {
            this.b.c(this.a);
        }
    }

    protected final /* synthetic */ Object doInBackground(Object... objArr) {
        return a((String[]) objArr);
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        a aVar = (a) obj;
        if (aVar != null && SelectTarget.TARGETING_SUCCESS == aVar.a()) {
            this.b.c(this.a);
        }
    }
}
