package com.chartboost.sdk.impl;

import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.wellbia.xigncode.util.WBTelecomUtil;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import jp.co.dimage.android.f;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class x implements z {
    protected final HttpClient a;

    public static final class a extends HttpEntityEnclosingRequestBase {
        public a(String str) {
            setURI(URI.create(str));
        }

        public String getMethod() {
            return "PATCH";
        }
    }

    public x(HttpClient httpClient) {
        this.a = httpClient;
    }

    private static void a(HttpUriRequest httpUriRequest, Map<String, String> map) {
        for (String str : map.keySet()) {
            httpUriRequest.setHeader(str, (String) map.get(str));
        }
    }

    public HttpResponse a(l<?> lVar, Map<String, String> map) throws IOException, a {
        HttpUriRequest b = b(lVar, map);
        a(b, (Map) map);
        a(b, lVar.i());
        a(b);
        HttpParams params = b.getParams();
        int t = lVar.t();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, t);
        return this.a.execute(b);
    }

    static HttpUriRequest b(l<?> lVar, Map<String, String> map) throws a {
        HttpEntityEnclosingRequestBase httpPost;
        switch (lVar.a()) {
            case WBTelecomUtil.TELECOM_TYPE_NONE /*-1*/:
                byte[] m = lVar.m();
                if (m == null) {
                    return new HttpGet(lVar.d());
                }
                HttpUriRequest httpPost2 = new HttpPost(lVar.d());
                httpPost2.addHeader("Content-Type", lVar.l());
                httpPost2.setEntity(new ByteArrayEntity(m));
                return httpPost2;
            case g.a /*0*/:
                return new HttpGet(lVar.d());
            case o.a /*1*/:
                httpPost = new HttpPost(lVar.d());
                httpPost.addHeader("Content-Type", lVar.p());
                a(httpPost, (l) lVar);
                return httpPost;
            case o.b /*2*/:
                httpPost = new HttpPut(lVar.d());
                httpPost.addHeader("Content-Type", lVar.p());
                a(httpPost, (l) lVar);
                return httpPost;
            case o.c /*3*/:
                return new HttpDelete(lVar.d());
            case o.d /*4*/:
                return new HttpHead(lVar.d());
            case f.bc /*5*/:
                return new HttpOptions(lVar.d());
            case f.aL /*6*/:
                return new HttpTrace(lVar.d());
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
                httpPost = new a(lVar.d());
                httpPost.addHeader("Content-Type", lVar.p());
                a(httpPost, (l) lVar);
                return httpPost;
            default:
                throw new IllegalStateException("Unknown request method.");
        }
    }

    private static void a(HttpEntityEnclosingRequestBase httpEntityEnclosingRequestBase, l<?> lVar) throws a {
        byte[] q = lVar.q();
        if (q != null) {
            httpEntityEnclosingRequestBase.setEntity(new ByteArrayEntity(q));
        }
    }

    protected void a(HttpUriRequest httpUriRequest) throws IOException {
    }
}
