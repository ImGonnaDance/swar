package jp.co.dimage.android.b;

import android.os.AsyncTask;
import android.util.Log;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import jp.co.dimage.android.f;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

final class b extends AsyncTask {
    private List a = Collections.synchronizedList(new LinkedList());
    private String b;
    private boolean c;

    b(String str) {
        this.b = str;
    }

    private Void b() {
        synchronized (this) {
            while (!this.c) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Log.e(f.aU, "TrackTask faild. " + e.getMessage());
                }
                c();
            }
        }
        return null;
    }

    private void c() {
        if (this.a.size() != 0) {
            while (this.a.size() > 0) {
                String str = (String) this.a.get(0);
                HttpClient defaultHttpClient = new DefaultHttpClient();
                defaultHttpClient.getParams().setParameter("http.useragent", this.b);
                try {
                    defaultHttpClient.execute(new HttpGet(str));
                } catch (Exception e) {
                    Log.e(f.aU, "TrackTask faild. " + e.getMessage());
                    this.a.remove(0);
                } finally {
                    defaultHttpClient.getConnectionManager().shutdown();
                }
                this.a.remove(0);
            }
        }
    }

    final synchronized void a() {
        this.c = true;
        notify();
    }

    final synchronized void a(String str) {
        this.a.add(str);
        notify();
    }

    protected final /* synthetic */ Object doInBackground(Object... objArr) {
        return b();
    }
}
