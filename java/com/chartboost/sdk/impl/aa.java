package com.chartboost.sdk.impl;

import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.wellbia.xigncode.util.WBTelecomUtil;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import jp.co.dimage.android.f;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

public class aa implements z {
    private final a a;
    private final SSLSocketFactory b;

    public interface a {
        String a(String str);
    }

    public aa() {
        this(null);
    }

    public aa(a aVar) {
        this(aVar, null);
    }

    public aa(a aVar, SSLSocketFactory sSLSocketFactory) {
        this.a = aVar;
        this.b = sSLSocketFactory;
    }

    public HttpResponse a(l<?> lVar, Map<String, String> map) throws IOException, a {
        String a;
        String d = lVar.d();
        HashMap hashMap = new HashMap();
        hashMap.putAll(lVar.i());
        hashMap.putAll(map);
        if (this.a != null) {
            a = this.a.a(d);
            if (a == null) {
                throw new IOException("URL blocked by rewriter: " + d);
            }
        }
        a = d;
        HttpURLConnection a2 = a(new URL(a), (l) lVar);
        for (String a3 : hashMap.keySet()) {
            a2.addRequestProperty(a3, (String) hashMap.get(a3));
        }
        a(a2, (l) lVar);
        ProtocolVersion protocolVersion = new ProtocolVersion("HTTP", 1, 1);
        if (a2.getResponseCode() == -1) {
            throw new IOException("Could not retrieve response code from HttpUrlConnection.");
        }
        HttpResponse basicHttpResponse = new BasicHttpResponse(new BasicStatusLine(protocolVersion, a2.getResponseCode(), a2.getResponseMessage()));
        basicHttpResponse.setEntity(a(a2));
        for (Entry entry : a2.getHeaderFields().entrySet()) {
            if (entry.getKey() != null) {
                basicHttpResponse.addHeader(new BasicHeader((String) entry.getKey(), (String) ((List) entry.getValue()).get(0)));
            }
        }
        return basicHttpResponse;
    }

    private static HttpEntity a(HttpURLConnection httpURLConnection) {
        InputStream inputStream;
        HttpEntity basicHttpEntity = new BasicHttpEntity();
        try {
            inputStream = httpURLConnection.getInputStream();
        } catch (IOException e) {
            inputStream = httpURLConnection.getErrorStream();
        }
        basicHttpEntity.setContent(inputStream);
        basicHttpEntity.setContentLength((long) httpURLConnection.getContentLength());
        basicHttpEntity.setContentEncoding(httpURLConnection.getContentEncoding());
        basicHttpEntity.setContentType(httpURLConnection.getContentType());
        return basicHttpEntity;
    }

    protected HttpURLConnection a(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private HttpURLConnection a(URL url, l<?> lVar) throws IOException {
        HttpURLConnection a = a(url);
        int t = lVar.t();
        a.setConnectTimeout(t);
        a.setReadTimeout(t);
        a.setUseCaches(false);
        a.setDoInput(true);
        if ("https".equals(url.getProtocol()) && this.b != null) {
            ((HttpsURLConnection) a).setSSLSocketFactory(this.b);
        }
        return a;
    }

    static void a(HttpURLConnection httpURLConnection, l<?> lVar) throws IOException, a {
        switch (lVar.a()) {
            case WBTelecomUtil.TELECOM_TYPE_NONE /*-1*/:
                byte[] m = lVar.m();
                if (m != null) {
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.addRequestProperty("Content-Type", lVar.l());
                    DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
                    dataOutputStream.write(m);
                    dataOutputStream.close();
                    return;
                }
                return;
            case g.a /*0*/:
                httpURLConnection.setRequestMethod("GET");
                return;
            case o.a /*1*/:
                httpURLConnection.setRequestMethod("POST");
                b(httpURLConnection, lVar);
                return;
            case o.b /*2*/:
                httpURLConnection.setRequestMethod("PUT");
                b(httpURLConnection, lVar);
                return;
            case o.c /*3*/:
                httpURLConnection.setRequestMethod("DELETE");
                return;
            case o.d /*4*/:
                httpURLConnection.setRequestMethod("HEAD");
                return;
            case f.bc /*5*/:
                httpURLConnection.setRequestMethod("OPTIONS");
                return;
            case f.aL /*6*/:
                httpURLConnection.setRequestMethod("TRACE");
                return;
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
                httpURLConnection.setRequestMethod("PATCH");
                b(httpURLConnection, lVar);
                return;
            default:
                throw new IllegalStateException("Unknown method type.");
        }
    }

    private static void b(HttpURLConnection httpURLConnection, l<?> lVar) throws IOException, a {
        byte[] q = lVar.q();
        if (q != null) {
            httpURLConnection.setDoOutput(true);
            httpURLConnection.addRequestProperty("Content-Type", lVar.p());
            DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.write(q);
            dataOutputStream.close();
        }
    }
}
