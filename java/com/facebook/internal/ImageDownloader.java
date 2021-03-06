package com.facebook.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import com.facebook.internal.ImageRequest.Callback;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ImageDownloader {
    private static final int CACHE_READ_QUEUE_MAX_CONCURRENT = 2;
    private static final int DOWNLOAD_QUEUE_MAX_CONCURRENT = 8;
    private static WorkQueue cacheReadQueue = new WorkQueue(CACHE_READ_QUEUE_MAX_CONCURRENT);
    private static WorkQueue downloadQueue = new WorkQueue(DOWNLOAD_QUEUE_MAX_CONCURRENT);
    private static Handler handler;
    private static final Map<RequestKey, DownloaderContext> pendingRequests = new HashMap();

    class AnonymousClass1 implements Runnable {
        private final /* synthetic */ Bitmap val$bitmap;
        private final /* synthetic */ Callback val$callback;
        private final /* synthetic */ Exception val$error;
        private final /* synthetic */ boolean val$isCachedRedirect;
        private final /* synthetic */ ImageRequest val$request;

        AnonymousClass1(ImageRequest imageRequest, Exception exception, boolean z, Bitmap bitmap, Callback callback) {
            this.val$request = imageRequest;
            this.val$error = exception;
            this.val$isCachedRedirect = z;
            this.val$bitmap = bitmap;
            this.val$callback = callback;
        }

        public void run() {
            this.val$callback.onCompleted(new ImageResponse(this.val$request, this.val$error, this.val$isCachedRedirect, this.val$bitmap));
        }
    }

    private static class CacheReadWorkItem implements Runnable {
        private boolean allowCachedRedirects;
        private Context context;
        private RequestKey key;

        CacheReadWorkItem(Context context, RequestKey key, boolean allowCachedRedirects) {
            this.context = context;
            this.key = key;
            this.allowCachedRedirects = allowCachedRedirects;
        }

        public void run() {
            ImageDownloader.readFromCache(this.key, this.context, this.allowCachedRedirects);
        }
    }

    private static class DownloadImageWorkItem implements Runnable {
        private Context context;
        private RequestKey key;

        DownloadImageWorkItem(Context context, RequestKey key) {
            this.context = context;
            this.key = key;
        }

        public void run() {
            ImageDownloader.download(this.key, this.context);
        }
    }

    private static class DownloaderContext {
        boolean isCancelled;
        ImageRequest request;
        WorkItem workItem;

        private DownloaderContext() {
        }
    }

    private static class RequestKey {
        private static final int HASH_MULTIPLIER = 37;
        private static final int HASH_SEED = 29;
        Object tag;
        URI uri;

        RequestKey(URI url, Object tag) {
            this.uri = url;
            this.tag = tag;
        }

        public int hashCode() {
            return ((this.uri.hashCode() + 1073) * HASH_MULTIPLIER) + this.tag.hashCode();
        }

        public boolean equals(Object o) {
            if (o == null || !(o instanceof RequestKey)) {
                return false;
            }
            RequestKey compareTo = (RequestKey) o;
            return compareTo.uri == this.uri && compareTo.tag == this.tag;
        }
    }

    public static void downloadAsync(ImageRequest request) {
        if (request != null) {
            RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
            synchronized (pendingRequests) {
                DownloaderContext downloaderContext = (DownloaderContext) pendingRequests.get(key);
                if (downloaderContext != null) {
                    downloaderContext.request = request;
                    downloaderContext.isCancelled = false;
                    downloaderContext.workItem.moveToFront();
                } else {
                    enqueueCacheRead(request, key, request.isCachedRedirectAllowed());
                }
            }
        }
    }

    public static boolean cancelRequest(ImageRequest request) {
        boolean cancelled = false;
        RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = (DownloaderContext) pendingRequests.get(key);
            if (downloaderContext != null) {
                cancelled = true;
                if (downloaderContext.workItem.cancel()) {
                    pendingRequests.remove(key);
                } else {
                    downloaderContext.isCancelled = true;
                }
            }
        }
        return cancelled;
    }

    public static void prioritizeRequest(ImageRequest request) {
        RequestKey key = new RequestKey(request.getImageUri(), request.getCallerTag());
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = (DownloaderContext) pendingRequests.get(key);
            if (downloaderContext != null) {
                downloaderContext.workItem.moveToFront();
            }
        }
    }

    public static void clearCache(Context context) {
        ImageResponseCache.clearCache(context);
        UrlRedirectCache.clearCache(context);
    }

    private static void enqueueCacheRead(ImageRequest request, RequestKey key, boolean allowCachedRedirects) {
        enqueueRequest(request, key, cacheReadQueue, new CacheReadWorkItem(request.getContext(), key, allowCachedRedirects));
    }

    private static void enqueueDownload(ImageRequest request, RequestKey key) {
        enqueueRequest(request, key, downloadQueue, new DownloadImageWorkItem(request.getContext(), key));
    }

    private static void enqueueRequest(ImageRequest request, RequestKey key, WorkQueue workQueue, Runnable workItem) {
        synchronized (pendingRequests) {
            DownloaderContext downloaderContext = new DownloaderContext();
            downloaderContext.request = request;
            pendingRequests.put(key, downloaderContext);
            downloaderContext.workItem = workQueue.addActiveWorkItem(workItem);
        }
    }

    private static void issueResponse(RequestKey key, Exception error, Bitmap bitmap, boolean isCachedRedirect) {
        DownloaderContext completedRequestContext = removePendingRequest(key);
        if (completedRequestContext != null && !completedRequestContext.isCancelled) {
            ImageRequest request = completedRequestContext.request;
            Callback callback = request.getCallback();
            if (callback != null) {
                getHandler().post(new AnonymousClass1(request, error, isCachedRedirect, bitmap, callback));
            }
        }
    }

    private static void readFromCache(RequestKey key, Context context, boolean allowCachedRedirects) {
        InputStream cachedStream = null;
        boolean isCachedRedirect = false;
        if (allowCachedRedirects) {
            URI redirectUri = UrlRedirectCache.getRedirectedUri(context, key.uri);
            if (redirectUri != null) {
                cachedStream = ImageResponseCache.getCachedImageStream(redirectUri, context);
                isCachedRedirect = cachedStream != null;
            }
        }
        if (!isCachedRedirect) {
            cachedStream = ImageResponseCache.getCachedImageStream(key.uri, context);
        }
        if (cachedStream != null) {
            Bitmap bitmap = BitmapFactory.decodeStream(cachedStream);
            Utility.closeQuietly(cachedStream);
            issueResponse(key, null, bitmap, isCachedRedirect);
            return;
        }
        DownloaderContext downloaderContext = removePendingRequest(key);
        if (downloaderContext != null && !downloaderContext.isCancelled) {
            enqueueDownload(downloaderContext.request, key);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void download(com.facebook.internal.ImageDownloader.RequestKey r19, android.content.Context r20) {
        /*
        r5 = 0;
        r14 = 0;
        r8 = 0;
        r2 = 0;
        r10 = 1;
        r15 = new java.net.URL;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r0 = r19;
        r0 = r0.uri;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = r0;
        r16 = r16.toString();	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r15.<init>(r16);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = r15.openConnection();	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r0 = r16;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r5 = r0;
        r16 = 0;
        r0 = r16;
        r5.setInstanceFollowRedirects(r0);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = r5.getResponseCode();	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        switch(r16) {
            case 200: goto L_0x00bf;
            case 301: goto L_0x0070;
            case 302: goto L_0x0070;
            default: goto L_0x002b;
        };	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
    L_0x002b:
        r14 = r5.getErrorStream();	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r9 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r9.<init>();	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        if (r14 == 0) goto L_0x00dc;
    L_0x0036:
        r11 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r11.<init>(r14);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = 128; // 0x80 float:1.8E-43 double:6.3E-322;
        r0 = r16;
        r3 = new char[r0];	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
    L_0x0041:
        r16 = 0;
        r0 = r3.length;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r17 = r0;
        r0 = r16;
        r1 = r17;
        r4 = r11.read(r3, r0, r1);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        if (r4 > 0) goto L_0x00ca;
    L_0x0050:
        com.facebook.internal.Utility.closeQuietly(r11);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
    L_0x0053:
        r8 = new com.facebook.FacebookException;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = r9.toString();	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r0 = r16;
        r8.<init>(r0);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
    L_0x005e:
        com.facebook.internal.Utility.closeQuietly(r14);
        com.facebook.internal.Utility.disconnectQuietly(r5);
    L_0x0064:
        if (r10 == 0) goto L_0x006f;
    L_0x0066:
        r16 = 0;
        r0 = r19;
        r1 = r16;
        issueResponse(r0, r8, r2, r1);
    L_0x006f:
        return;
    L_0x0070:
        r10 = 0;
        r16 = "location";
        r0 = r16;
        r12 = r5.getHeaderField(r0);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = com.facebook.internal.Utility.isNullOrEmpty(r12);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        if (r16 != 0) goto L_0x005e;
    L_0x007f:
        r13 = new java.net.URI;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r13.<init>(r12);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r0 = r19;
        r0 = r0.uri;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = r0;
        r0 = r20;
        r1 = r16;
        com.facebook.internal.UrlRedirectCache.cacheUriRedirect(r0, r1, r13);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r6 = removePendingRequest(r19);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        if (r6 == 0) goto L_0x005e;
    L_0x0097:
        r0 = r6.isCancelled;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = r0;
        if (r16 != 0) goto L_0x005e;
    L_0x009d:
        r0 = r6.request;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r16 = r0;
        r17 = new com.facebook.internal.ImageDownloader$RequestKey;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r0 = r19;
        r0 = r0.tag;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r18 = r0;
        r0 = r17;
        r1 = r18;
        r0.<init>(r13, r1);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r18 = 0;
        enqueueCacheRead(r16, r17, r18);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        goto L_0x005e;
    L_0x00b6:
        r7 = move-exception;
        r8 = r7;
        com.facebook.internal.Utility.closeQuietly(r14);
        com.facebook.internal.Utility.disconnectQuietly(r5);
        goto L_0x0064;
    L_0x00bf:
        r0 = r20;
        r14 = com.facebook.internal.ImageResponseCache.interceptAndCacheImageStream(r0, r5);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r2 = android.graphics.BitmapFactory.decodeStream(r14);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        goto L_0x005e;
    L_0x00ca:
        r16 = 0;
        r0 = r16;
        r9.append(r3, r0, r4);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        goto L_0x0041;
    L_0x00d3:
        r7 = move-exception;
        r8 = r7;
        com.facebook.internal.Utility.closeQuietly(r14);
        com.facebook.internal.Utility.disconnectQuietly(r5);
        goto L_0x0064;
    L_0x00dc:
        r16 = com.facebook.android.R.string.com_facebook_image_download_unknown_error;	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r0 = r20;
        r1 = r16;
        r16 = r0.getString(r1);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        r0 = r16;
        r9.append(r0);	 Catch:{ IOException -> 0x00b6, URISyntaxException -> 0x00d3, all -> 0x00ed }
        goto L_0x0053;
    L_0x00ed:
        r16 = move-exception;
        com.facebook.internal.Utility.closeQuietly(r14);
        com.facebook.internal.Utility.disconnectQuietly(r5);
        throw r16;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.ImageDownloader.download(com.facebook.internal.ImageDownloader$RequestKey, android.content.Context):void");
    }

    private static synchronized Handler getHandler() {
        Handler handler;
        synchronized (ImageDownloader.class) {
            if (handler == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            handler = handler;
        }
        return handler;
    }

    private static DownloaderContext removePendingRequest(RequestKey key) {
        DownloaderContext downloaderContext;
        synchronized (pendingRequests) {
            downloaderContext = (DownloaderContext) pendingRequests.remove(key);
        }
        return downloaderContext;
    }
}
