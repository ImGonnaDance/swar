package com.facebook;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import com.facebook.Request.Callback;
import com.facebook.internal.AttributionIdentifiers;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphObject.Factory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.json.JSONException;
import org.json.JSONObject;

public final class Settings {
    private static final String ANALYTICS_EVENT = "event";
    public static final String APPLICATION_ID_PROPERTY = "com.facebook.sdk.ApplicationId";
    private static final String APP_EVENT_PREFERENCES = "com.facebook.sdk.appEventPreferences";
    private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    private static final Uri ATTRIBUTION_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
    private static final String ATTRIBUTION_PREFERENCES = "com.facebook.sdk.attributionTracking";
    private static final String AUTO_PUBLISH = "auto_publish";
    public static final String CLIENT_TOKEN_PROPERTY = "com.facebook.sdk.ClientToken";
    private static final int DEFAULT_CORE_POOL_SIZE = 5;
    private static final int DEFAULT_KEEP_ALIVE = 1;
    private static final int DEFAULT_MAXIMUM_POOL_SIZE = 128;
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger counter = new AtomicInteger(0);

        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "FacebookSdk #" + this.counter.incrementAndGet());
        }
    };
    private static final BlockingQueue<Runnable> DEFAULT_WORK_QUEUE = new LinkedBlockingQueue(10);
    private static final String FACEBOOK_COM = "facebook.com";
    private static final Object LOCK = new Object();
    private static final String MOBILE_INSTALL_EVENT = "MOBILE_APP_INSTALL";
    private static final String PUBLISH_ACTIVITY_PATH = "%s/activities";
    private static final String TAG = Settings.class.getCanonicalName();
    private static volatile String appClientToken;
    private static volatile String appVersion;
    private static volatile String applicationId;
    private static volatile boolean defaultsLoaded = false;
    private static volatile Executor executor;
    private static volatile String facebookDomain = FACEBOOK_COM;
    private static volatile boolean isDebugEnabled = false;
    private static final HashSet<LoggingBehavior> loggingBehaviors;
    private static AtomicLong onProgressThreshold = new AtomicLong(65536);
    private static volatile boolean platformCompatibilityEnabled;
    private static Boolean sdkInitialized = Boolean.valueOf(false);
    private static volatile boolean shouldAutoPublishInstall;

    class AnonymousClass2 implements Runnable {
        private final /* synthetic */ Context val$applicationContext;
        private final /* synthetic */ String val$applicationId;
        private final /* synthetic */ Callback val$callback;

        AnonymousClass2(Context context, String str, Callback callback) {
            this.val$applicationContext = context;
            this.val$applicationId = str;
            this.val$callback = callback;
        }

        public void run() {
            final Response response = Settings.publishInstallAndWaitForResponse(this.val$applicationContext, this.val$applicationId, false);
            if (this.val$callback != null) {
                Handler handler = new Handler(Looper.getMainLooper());
                final Callback callback = this.val$callback;
                handler.post(new Runnable() {
                    public void run() {
                        callback.onCompleted(response);
                    }
                });
            }
        }
    }

    static {
        LoggingBehavior[] loggingBehaviorArr = new LoggingBehavior[DEFAULT_KEEP_ALIVE];
        loggingBehaviorArr[0] = LoggingBehavior.DEVELOPER_ERRORS;
        loggingBehaviors = new HashSet(Arrays.asList(loggingBehaviorArr));
    }

    public static synchronized void sdkInitialize(Context context) {
        synchronized (Settings.class) {
            if (!sdkInitialized.booleanValue()) {
                loadDefaultsFromMetadataIfNeeded(context);
                Utility.loadAppSettingsAsync(context, getApplicationId());
                BoltsMeasurementEventListener.getInstance(context.getApplicationContext());
                sdkInitialized = Boolean.valueOf(true);
            }
        }
    }

    public static final Set<LoggingBehavior> getLoggingBehaviors() {
        Set<LoggingBehavior> unmodifiableSet;
        synchronized (loggingBehaviors) {
            unmodifiableSet = Collections.unmodifiableSet(new HashSet(loggingBehaviors));
        }
        return unmodifiableSet;
    }

    public static final void addLoggingBehavior(LoggingBehavior behavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.add(behavior);
        }
    }

    public static final void removeLoggingBehavior(LoggingBehavior behavior) {
        synchronized (loggingBehaviors) {
            loggingBehaviors.remove(behavior);
        }
    }

    public static final void clearLoggingBehaviors() {
        synchronized (loggingBehaviors) {
            loggingBehaviors.clear();
        }
    }

    public static final boolean isLoggingBehaviorEnabled(LoggingBehavior behavior) {
        boolean z;
        synchronized (loggingBehaviors) {
            z = isDebugEnabled() && loggingBehaviors.contains(behavior);
        }
        return z;
    }

    @Deprecated
    public static final boolean isLoggingEnabled() {
        return isDebugEnabled();
    }

    @Deprecated
    public static final void setIsLoggingEnabled(boolean enabled) {
        setIsDebugEnabled(enabled);
    }

    public static final boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    public static final void setIsDebugEnabled(boolean enabled) {
        isDebugEnabled = enabled;
    }

    public static Executor getExecutor() {
        synchronized (LOCK) {
            if (executor == null) {
                Executor executor = getAsyncTaskExecutor();
                if (executor == null) {
                    executor = new ThreadPoolExecutor(DEFAULT_CORE_POOL_SIZE, DEFAULT_MAXIMUM_POOL_SIZE, 1, TimeUnit.SECONDS, DEFAULT_WORK_QUEUE, DEFAULT_THREAD_FACTORY);
                }
                executor = executor;
            }
        }
        return executor;
    }

    public static void setExecutor(Executor executor) {
        Validate.notNull(executor, "executor");
        synchronized (LOCK) {
            executor = executor;
        }
    }

    public static String getFacebookDomain() {
        return facebookDomain;
    }

    public static void setFacebookDomain(String facebookDomain) {
        Log.w(TAG, "WARNING: Calling setFacebookDomain from non-DEBUG code.");
        facebookDomain = facebookDomain;
    }

    private static Executor getAsyncTaskExecutor() {
        try {
            try {
                Object executorObject = AsyncTask.class.getField("THREAD_POOL_EXECUTOR").get(null);
                if (executorObject == null) {
                    return null;
                }
                if (executorObject instanceof Executor) {
                    return (Executor) executorObject;
                }
                return null;
            } catch (IllegalAccessException e) {
                return null;
            }
        } catch (NoSuchFieldException e2) {
            return null;
        }
    }

    static void publishInstallAsync(Context context, String applicationId, Callback callback) {
        getExecutor().execute(new AnonymousClass2(context.getApplicationContext(), applicationId, callback));
    }

    @Deprecated
    public static void setShouldAutoPublishInstall(boolean shouldAutoPublishInstall) {
        shouldAutoPublishInstall = shouldAutoPublishInstall;
    }

    @Deprecated
    public static boolean getShouldAutoPublishInstall() {
        return shouldAutoPublishInstall;
    }

    static Response publishInstallAndWaitForResponse(Context context, String applicationId, boolean isAutoPublish) {
        if (context == null || applicationId == null) {
            try {
                throw new IllegalArgumentException("Both context and applicationId must be non-null");
            } catch (Exception e) {
                Utility.logd("Facebook-publish", e);
                return new Response(null, null, new FacebookRequestError(null, e));
            }
        }
        AttributionIdentifiers identifiers = AttributionIdentifiers.getAttributionIdentifiers(context);
        SharedPreferences preferences = context.getSharedPreferences(ATTRIBUTION_PREFERENCES, 0);
        String pingKey = new StringBuilder(String.valueOf(applicationId)).append("ping").toString();
        String jsonKey = new StringBuilder(String.valueOf(applicationId)).append("json").toString();
        if (preferences == null) {
            throw new IllegalArgumentException("preferences must be non-null");
        }
        long lastPing = preferences.getLong(pingKey, 0);
        String lastResponseJSON = preferences.getString(jsonKey, null);
        if (!isAutoPublish) {
            setShouldAutoPublishInstall(false);
        }
        GraphObject publishParams = Factory.create();
        publishParams.setProperty(ANALYTICS_EVENT, MOBILE_INSTALL_EVENT);
        Utility.setAppEventAttributionParameters(publishParams, identifiers, Utility.getHashedDeviceAndAppID(context, applicationId), getLimitEventAndDataUsage(context));
        publishParams.setProperty(AUTO_PUBLISH, Boolean.valueOf(isAutoPublish));
        publishParams.setProperty("application_package_name", context.getPackageName());
        String str = PUBLISH_ACTIVITY_PATH;
        Object[] objArr = new Object[DEFAULT_KEEP_ALIVE];
        objArr[0] = applicationId;
        Request publishRequest = Request.newPostRequest(null, String.format(str, objArr), publishParams, null);
        if (lastPing != 0) {
            GraphObject graphObject = null;
            if (lastResponseJSON != null) {
                try {
                    graphObject = Factory.create(new JSONObject(lastResponseJSON));
                } catch (JSONException e2) {
                }
            }
            if (graphObject != null) {
                return new Response(null, null, null, graphObject, true);
            }
            str = ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
            Request[] requestArr = new Request[DEFAULT_KEEP_ALIVE];
            requestArr[0] = publishRequest;
            return (Response) Response.createResponsesFromString(str, null, new RequestBatch(requestArr), true).get(0);
        } else if (identifiers == null || (identifiers.getAndroidAdvertiserId() == null && identifiers.getAttributionId() == null)) {
            throw new FacebookException("No attribution id available to send to server.");
        } else if (Utility.queryAppSettings(applicationId, false).supportsAttribution()) {
            Response publishResponse = publishRequest.executeAndWait();
            Editor editor = preferences.edit();
            editor.putLong(pingKey, System.currentTimeMillis());
            if (!(publishResponse.getGraphObject() == null || publishResponse.getGraphObject().getInnerJSONObject() == null)) {
                editor.putString(jsonKey, publishResponse.getGraphObject().getInnerJSONObject().toString());
            }
            editor.apply();
            return publishResponse;
        } else {
            throw new FacebookException("Install attribution has been disabled on the server.");
        }
    }

    public static String getAttributionId(ContentResolver contentResolver) {
        String str = null;
        Cursor c = null;
        try {
            String[] projection = new String[DEFAULT_KEEP_ALIVE];
            projection[0] = ATTRIBUTION_ID_COLUMN_NAME;
            c = contentResolver.query(ATTRIBUTION_ID_CONTENT_URI, projection, null, null, null);
            if (c == null || !c.moveToFirst()) {
                if (c != null) {
                    c.close();
                }
                return str;
            }
            str = c.getString(c.getColumnIndex(ATTRIBUTION_ID_COLUMN_NAME));
            if (c != null) {
                c.close();
            }
            return str;
        } catch (Exception e) {
            Log.d(TAG, "Caught unexpected exception in getAttributionId(): " + e.toString());
            if (c != null) {
                c.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
        }
    }

    public static String getAppVersion() {
        return appVersion;
    }

    public static void setAppVersion(String appVersion) {
        appVersion = appVersion;
    }

    public static String getSdkVersion() {
        return FacebookSdkVersion.BUILD;
    }

    public static boolean getLimitEventAndDataUsage(Context context) {
        return context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).getBoolean("limitEventUsage", false);
    }

    public static void setLimitEventAndDataUsage(Context context, boolean limitEventUsage) {
        context.getSharedPreferences(APP_EVENT_PREFERENCES, 0).edit().putBoolean("limitEventUsage", limitEventUsage).apply();
    }

    public static long getOnProgressThreshold() {
        return onProgressThreshold.get();
    }

    public static void setOnProgressThreshold(long threshold) {
        onProgressThreshold.set(threshold);
    }

    public static boolean getPlatformCompatibilityEnabled() {
        return platformCompatibilityEnabled;
    }

    public static void setPlatformCompatibilityEnabled(boolean platformCompatibilityEnabled) {
        platformCompatibilityEnabled = platformCompatibilityEnabled;
    }

    public static void loadDefaultsFromMetadata(Context context) {
        defaultsLoaded = true;
        if (context != null) {
            try {
                ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), DEFAULT_MAXIMUM_POOL_SIZE);
                if (ai != null && ai.metaData != null) {
                    if (applicationId == null) {
                        applicationId = ai.metaData.getString(APPLICATION_ID_PROPERTY);
                    }
                    if (appClientToken == null) {
                        appClientToken = ai.metaData.getString(CLIENT_TOKEN_PROPERTY);
                    }
                }
            } catch (NameNotFoundException e) {
            }
        }
    }

    static void loadDefaultsFromMetadataIfNeeded(Context context) {
        if (!defaultsLoaded) {
            loadDefaultsFromMetadata(context);
        }
    }

    public static String getApplicationSignature(Context context) {
        if (context == null) {
            return null;
        }
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return null;
        }
        try {
            PackageInfo pInfo = packageManager.getPackageInfo(context.getPackageName(), 64);
            Signature[] signatures = pInfo.signatures;
            if (signatures == null || signatures.length == 0) {
                return null;
            }
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-1");
                md.update(pInfo.signatures[0].toByteArray());
                return Base64.encodeToString(md.digest(), 9);
            } catch (NoSuchAlgorithmException e) {
                return null;
            }
        } catch (NameNotFoundException e2) {
            return null;
        }
    }

    public static String getApplicationId() {
        return applicationId;
    }

    public static void setApplicationId(String applicationId) {
        applicationId = applicationId;
    }

    public static String getClientToken() {
        return appClientToken;
    }

    public static void setClientToken(String clientToken) {
        appClientToken = clientToken;
    }
}
