package com.facebook.internal;

import android.content.Context;
import com.facebook.LoggingBehavior;
import com.facebook.internal.FileLruCache.Limits;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

class UrlRedirectCache {
    private static final String REDIRECT_CONTENT_TAG = (TAG + "_Redirect");
    static final String TAG = UrlRedirectCache.class.getSimpleName();
    private static volatile FileLruCache urlRedirectCache;

    UrlRedirectCache() {
    }

    static synchronized FileLruCache getCache(Context context) throws IOException {
        FileLruCache fileLruCache;
        synchronized (UrlRedirectCache.class) {
            if (urlRedirectCache == null) {
                urlRedirectCache = new FileLruCache(context.getApplicationContext(), TAG, new Limits());
            }
            fileLruCache = urlRedirectCache;
        }
        return fileLruCache;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.net.URI getRedirectedUri(android.content.Context r12, java.net.URI r13) {
        /*
        r9 = 0;
        if (r13 != 0) goto L_0x0004;
    L_0x0003:
        return r9;
    L_0x0004:
        r7 = r13.toString();
        r3 = 0;
        r2 = getCache(r12);	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
        r5 = 0;
        r4 = r3;
    L_0x000f:
        r10 = REDIRECT_CONTENT_TAG;	 Catch:{ URISyntaxException -> 0x0062, IOException -> 0x005f, all -> 0x005c }
        r6 = r2.get(r7, r10);	 Catch:{ URISyntaxException -> 0x0062, IOException -> 0x005f, all -> 0x005c }
        if (r6 != 0) goto L_0x0023;
    L_0x0017:
        if (r5 == 0) goto L_0x0057;
    L_0x0019:
        r10 = new java.net.URI;	 Catch:{ URISyntaxException -> 0x0062, IOException -> 0x005f, all -> 0x005c }
        r10.<init>(r7);	 Catch:{ URISyntaxException -> 0x0062, IOException -> 0x005f, all -> 0x005c }
        com.facebook.internal.Utility.closeQuietly(r4);
        r9 = r10;
        goto L_0x0003;
    L_0x0023:
        r5 = 1;
        r3 = new java.io.InputStreamReader;	 Catch:{ URISyntaxException -> 0x0062, IOException -> 0x005f, all -> 0x005c }
        r3.<init>(r6);	 Catch:{ URISyntaxException -> 0x0062, IOException -> 0x005f, all -> 0x005c }
        r10 = 128; // 0x80 float:1.8E-43 double:6.3E-322;
        r0 = new char[r10];	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
        r8 = new java.lang.StringBuilder;	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
        r8.<init>();	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
    L_0x0032:
        r10 = 0;
        r11 = r0.length;	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
        r1 = r3.read(r0, r10, r11);	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
        if (r1 > 0) goto L_0x0043;
    L_0x003a:
        com.facebook.internal.Utility.closeQuietly(r3);	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
        r7 = r8.toString();	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
        r4 = r3;
        goto L_0x000f;
    L_0x0043:
        r10 = 0;
        r8.append(r0, r10, r1);	 Catch:{ URISyntaxException -> 0x0048, IOException -> 0x004d, all -> 0x0052 }
        goto L_0x0032;
    L_0x0048:
        r10 = move-exception;
    L_0x0049:
        com.facebook.internal.Utility.closeQuietly(r3);
        goto L_0x0003;
    L_0x004d:
        r10 = move-exception;
    L_0x004e:
        com.facebook.internal.Utility.closeQuietly(r3);
        goto L_0x0003;
    L_0x0052:
        r9 = move-exception;
    L_0x0053:
        com.facebook.internal.Utility.closeQuietly(r3);
        throw r9;
    L_0x0057:
        com.facebook.internal.Utility.closeQuietly(r4);
        r3 = r4;
        goto L_0x0003;
    L_0x005c:
        r9 = move-exception;
        r3 = r4;
        goto L_0x0053;
    L_0x005f:
        r10 = move-exception;
        r3 = r4;
        goto L_0x004e;
    L_0x0062:
        r10 = move-exception;
        r3 = r4;
        goto L_0x0049;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.UrlRedirectCache.getRedirectedUri(android.content.Context, java.net.URI):java.net.URI");
    }

    static void cacheUriRedirect(Context context, URI fromUri, URI toUri) {
        if (fromUri != null && toUri != null) {
            OutputStream redirectStream = null;
            try {
                redirectStream = getCache(context).openPutStream(fromUri.toString(), REDIRECT_CONTENT_TAG);
                redirectStream.write(toUri.toString().getBytes());
            } catch (IOException e) {
            } finally {
                Utility.closeQuietly(redirectStream);
            }
        }
    }

    static void clearCache(Context context) {
        try {
            getCache(context).clearCache();
        } catch (IOException e) {
            Logger.log(LoggingBehavior.CACHE, 5, TAG, "clearCache failed " + e.getMessage());
        }
    }
}
