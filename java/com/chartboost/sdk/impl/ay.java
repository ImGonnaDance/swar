package com.chartboost.sdk.impl;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.b;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public final class ay {
    private static ExecutorService a = null;
    private static ThreadFactory b = null;
    private static HttpClient c = null;

    public static ExecutorService a() {
        if (b == null) {
            b = new ThreadFactory() {
                private final AtomicInteger a = new AtomicInteger(1);

                public Thread newThread(Runnable r) {
                    return new Thread(r, "Chartboost Thread #" + this.a.getAndIncrement());
                }
            };
        }
        if (a == null) {
            a = Executors.newFixedThreadPool(5, b);
        }
        return a;
    }

    protected static HttpClient b() {
        if (c != null) {
            return c;
        }
        try {
            final Application application = (Application) b.k().getApplicationContext();
            c = new DefaultHttpClient() {
                protected ClientConnectionManager createClientConnectionManager() {
                    SchemeRegistry schemeRegistry = new SchemeRegistry();
                    schemeRegistry.register(new Scheme(WebClient.INTENT_PROTOCOL_START_HTTP, PlainSocketFactory.getSocketFactory(), 80));
                    schemeRegistry.register(new Scheme("https", a(), 443));
                    HttpParams params = getParams();
                    HttpConnectionParams.setConnectionTimeout(params, 30000);
                    HttpConnectionParams.setSoTimeout(params, 30000);
                    HttpProtocolParams.setUserAgent(params, ay.b(application, HttpProtocolParams.getUserAgent(params)));
                    return new ThreadSafeClientConnManager(params, schemeRegistry);
                }

                protected SocketFactory a() {
                    try {
                        Object newInstance = Class.forName("android.net.SSLSessionCache").getConstructor(new Class[]{Context.class}).newInstance(new Object[]{application});
                        return (SocketFactory) Class.forName("android.net.SSLCertificateSocketFactory").getMethod("getHttpSocketFactory", new Class[]{Integer.TYPE, r0}).invoke(null, new Object[]{Integer.valueOf(30000), newInstance});
                    } catch (Throwable e) {
                        CBLogging.b("CBAsync:HttpClientProvider", "Unable to use android.net.SSLCertificateSocketFactory to get a SSL session caching socket factory, falling back to a non-caching socket factory", e);
                        return SSLSocketFactory.getSocketFactory();
                    }
                }
            };
            return c;
        } catch (Throwable e) {
            CBLogging.b("CBAsync", "Exception raised getting a Chartboost HTTPClient on which to run any network request", e);
            try {
                HttpClient defaultHttpClient = new DefaultHttpClient();
                ClientConnectionManager connectionManager = defaultHttpClient.getConnectionManager();
                HttpParams params = defaultHttpClient.getParams();
                c = new DefaultHttpClient(new ThreadSafeClientConnManager(params, connectionManager.getSchemeRegistry()), params);
                return c;
            } catch (Exception e2) {
                CBLogging.b("CBAsync", "Exception raised creating a simple HTTP client", e);
                c = new DefaultHttpClient();
                return c;
            }
        }
    }

    private static String b(Application application, String str) {
        try {
            String str2 = application.getPackageManager().getPackageInfo(application.getPackageName(), 0).versionName;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(application.getPackageName());
            stringBuilder.append("/");
            stringBuilder.append(str2);
            stringBuilder.append(" (");
            stringBuilder.append("Linux; U; Android ");
            stringBuilder.append(VERSION.RELEASE);
            stringBuilder.append("; ");
            stringBuilder.append(Locale.getDefault());
            stringBuilder.append("; ");
            stringBuilder.append(Build.PRODUCT);
            stringBuilder.append(")");
            if (str != null) {
                stringBuilder.append(" ");
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
