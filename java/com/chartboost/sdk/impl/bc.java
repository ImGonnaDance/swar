package com.chartboost.sdk.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.chartboost.sdk.Libraries.CBLogging;
import com.chartboost.sdk.Libraries.CBUtility;
import com.chartboost.sdk.Model.CBError.CBClickError;
import com.chartboost.sdk.c.b;
import com.facebook.internal.NativeProtocol;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public final class bc {
    private static bc c;
    private a a;
    private com.chartboost.sdk.Model.a b;

    public interface a {
        void a(com.chartboost.sdk.Model.a aVar, boolean z, String str, CBClickError cBClickError, b bVar);
    }

    public static bc a(a aVar) {
        if (c == null) {
            c = new bc(aVar);
        }
        return c;
    }

    private bc(a aVar) {
        this.a = aVar;
    }

    public void a(com.chartboost.sdk.Model.a aVar, final String str, final Activity activity, final b bVar) {
        this.b = aVar;
        try {
            String scheme = new URI(str).getScheme();
            if (scheme == null) {
                if (this.a != null) {
                    this.a.a(aVar, false, str, CBClickError.URI_INVALID, bVar);
                }
            } else if (scheme.equals(WebClient.INTENT_PROTOCOL_START_HTTP) || scheme.equals("https")) {
                ay.a().execute(new Runnable(this) {
                    final /* synthetic */ bc d;

                    public void run() {
                        String str;
                        Throwable th;
                        String str2 = str;
                        if (az.a().c()) {
                            HttpURLConnection httpURLConnection = null;
                            try {
                                HttpURLConnection httpURLConnection2 = (HttpURLConnection) new URL(str).openConnection();
                                try {
                                    httpURLConnection2.setInstanceFollowRedirects(false);
                                    httpURLConnection2.setConnectTimeout(30000);
                                    httpURLConnection2.setReadTimeout(30000);
                                    String headerField = httpURLConnection2.getHeaderField("Location");
                                    if (headerField != null) {
                                        str2 = headerField;
                                    }
                                    if (httpURLConnection2 != null) {
                                        httpURLConnection2.disconnect();
                                        str = str2;
                                        a(str);
                                    }
                                } catch (Throwable e) {
                                    Throwable th2 = e;
                                    httpURLConnection = httpURLConnection2;
                                    th = th2;
                                    try {
                                        CBLogging.b("CBURLOpener", "Exception raised while opening a HTTP Conection", th);
                                        if (httpURLConnection != null) {
                                            httpURLConnection.disconnect();
                                            str = str2;
                                            a(str);
                                        }
                                        str = str2;
                                        a(str);
                                    } catch (Throwable th3) {
                                        th = th3;
                                        if (httpURLConnection != null) {
                                            httpURLConnection.disconnect();
                                        }
                                        throw th;
                                    }
                                } catch (Throwable th4) {
                                    httpURLConnection = httpURLConnection2;
                                    th = th4;
                                    if (httpURLConnection != null) {
                                        httpURLConnection.disconnect();
                                    }
                                    throw th;
                                }
                            } catch (Exception e2) {
                                th = e2;
                                CBLogging.b("CBURLOpener", "Exception raised while opening a HTTP Conection", th);
                                if (httpURLConnection != null) {
                                    httpURLConnection.disconnect();
                                    str = str2;
                                    a(str);
                                }
                                str = str2;
                                a(str);
                            }
                        }
                        str = str2;
                        a(str);
                    }

                    public void a(final String str) {
                        Runnable anonymousClass1 = new Runnable(this) {
                            final /* synthetic */ AnonymousClass1 b;

                            public void run() {
                                this.b.d.a(str, activity, bVar);
                            }
                        };
                        if (activity != null) {
                            activity.runOnUiThread(anonymousClass1);
                        } else {
                            CBUtility.e().post(anonymousClass1);
                        }
                    }
                });
            } else {
                a(str, activity, bVar);
            }
        } catch (URISyntaxException e) {
            if (this.a != null) {
                this.a.a(aVar, false, str, CBClickError.URI_INVALID, bVar);
            }
        }
    }

    private void a(String str, Context context, b bVar) {
        Intent intent;
        if (this.b != null && this.b.a()) {
            this.b.b = com.chartboost.sdk.Model.a.b.NONE;
        }
        if (context == null) {
            context = com.chartboost.sdk.b.k();
        }
        if (context != null) {
            String str2;
            try {
                intent = new Intent("android.intent.action.VIEW");
                if (!(context instanceof Activity)) {
                    intent.addFlags(268435456);
                }
                intent.setData(Uri.parse(str));
                context.startActivity(intent);
                str2 = str;
            } catch (Exception e) {
                if (str.startsWith("market://")) {
                    try {
                        str = "http://market.android.com/" + str.substring(9);
                        intent = new Intent("android.intent.action.VIEW");
                        if (!(context instanceof Activity)) {
                            intent.addFlags(268435456);
                        }
                        intent.setData(Uri.parse(str));
                        context.startActivity(intent);
                        str2 = str;
                    } catch (Throwable e2) {
                        str2 = str;
                        CBLogging.b("CBURLOpener", "Exception raised openeing an inavld playstore URL", e2);
                        if (this.a != null) {
                            this.a.a(this.b, false, str2, CBClickError.URI_UNRECOGNIZED, bVar);
                            return;
                        }
                        return;
                    }
                }
                if (this.a != null) {
                    this.a.a(this.b, false, str, CBClickError.URI_UNRECOGNIZED, bVar);
                }
                str2 = str;
            }
            if (this.a != null) {
                this.a.a(this.b, true, str2, null, bVar);
            }
        } else if (this.a != null) {
            this.a.a(this.b, false, str, CBClickError.NO_HOST_ACTIVITY, bVar);
        }
    }

    public static boolean a(String str) {
        Context k = com.chartboost.sdk.b.k();
        Intent intent = new Intent("android.intent.action.VIEW");
        if (!(k instanceof Activity)) {
            intent.addFlags(268435456);
        }
        intent.setData(Uri.parse(str));
        return k.getPackageManager().queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST).size() > 0;
    }
}
