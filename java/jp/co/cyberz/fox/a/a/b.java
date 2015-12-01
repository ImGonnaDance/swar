package jp.co.cyberz.fox.a.a;

import android.os.AsyncTask;
import java.util.ArrayList;
import java.util.List;
import jp.co.dimage.android.f;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

public final class b extends AsyncTask {
    public List a = null;
    public List b = null;

    class a {
        final /* synthetic */ b a;
        private int b;

        a(b bVar) {
            this.a = bVar;
        }

        public final int a() {
            return this.b;
        }

        public final void b() {
            this.b = 503;
        }
    }

    public b(List list, List list2) {
        if (list != null) {
            this.a = list;
            this.b = list2;
            return;
        }
        this.a = new ArrayList();
    }

    private a a(String... strArr) {
        a aVar = null;
        if (!(strArr == null || strArr.length == 0 || this.a == null || this.b == null || this.b.size() == 0)) {
            aVar = new a(this);
            HttpUriRequest httpPost = new HttpPost(strArr[0]);
            HttpClient defaultHttpClient = new DefaultHttpClient();
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(this.b, "UTF-8"));
                HttpConnectionParams.setConnectionTimeout(defaultHttpClient.getParams(), 5000);
                defaultHttpClient.getParams().setParameter("http.useragent", f.s);
                aVar.b = defaultHttpClient.execute(httpPost).getStatusLine().getStatusCode();
                if (aVar.b == SelectTarget.TARGETING_SUCCESS) {
                    defaultHttpClient.getConnectionManager().shutdown();
                }
            } catch (Exception e) {
                aVar.b();
            } finally {
                defaultHttpClient.getConnectionManager().shutdown();
            }
        }
        return aVar;
    }

    private void a(a aVar) {
        if (aVar != null) {
            if (SelectTarget.TARGETING_SUCCESS != aVar.a()) {
                e.a(this.a);
            } else {
                e.a();
            }
        }
    }

    private void b(String... strArr) {
        super.onProgressUpdate(strArr);
    }

    protected final /* synthetic */ Object doInBackground(Object... objArr) {
        return a((String[]) objArr);
    }

    protected final void onCancelled() {
        super.onCancelled();
    }

    protected final /* synthetic */ void onPostExecute(Object obj) {
        a aVar = (a) obj;
        if (aVar == null) {
            return;
        }
        if (SelectTarget.TARGETING_SUCCESS != aVar.a()) {
            e.a(this.a);
        } else {
            e.a();
        }
    }

    protected final void onPreExecute() {
        super.onPreExecute();
    }

    protected final /* bridge */ /* synthetic */ void onProgressUpdate(Object... objArr) {
        super.onProgressUpdate((String[]) objArr);
    }
}
