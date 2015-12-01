package com.facebook.internal;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.com2us.peppermint.PeppermintConstant;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.internal.FileLruCache.Limits;
import com.facebook.internal.PlatformServiceClient.CompletedListener;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.Builder;
import com.facebook.widget.FacebookDialog.DialogFeature;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.google.android.gcm.GCMConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LikeActionController {
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR = "com.facebook.sdk.LikeActionController.DID_ERROR";
    public static final String ACTION_LIKE_ACTION_CONTROLLER_DID_RESET = "com.facebook.sdk.LikeActionController.DID_RESET";
    public static final String ACTION_LIKE_ACTION_CONTROLLER_UPDATED = "com.facebook.sdk.LikeActionController.UPDATED";
    public static final String ACTION_OBJECT_ID_KEY = "com.facebook.sdk.LikeActionController.OBJECT_ID";
    private static final int ERROR_CODE_OBJECT_ALREADY_LIKED = 3501;
    public static final String ERROR_INVALID_OBJECT_ID = "Invalid Object Id";
    private static final String JSON_BOOL_IS_OBJECT_LIKED_KEY = "is_object_liked";
    private static final String JSON_BUNDLE_PENDING_CALL_ANALYTICS_BUNDLE = "pending_call_analytics_bundle";
    private static final String JSON_INT_VERSION_KEY = "com.facebook.internal.LikeActionController.version";
    private static final String JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY = "like_count_string_without_like";
    private static final String JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY = "like_count_string_with_like";
    private static final String JSON_STRING_OBJECT_ID_KEY = "object_id";
    private static final String JSON_STRING_PENDING_CALL_ID_KEY = "pending_call_id";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY = "social_sentence_without_like";
    private static final String JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY = "social_sentence_with_like";
    private static final String JSON_STRING_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final String LIKE_ACTION_CONTROLLER_STORE = "com.facebook.LikeActionController.CONTROLLER_STORE_KEY";
    private static final String LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY = "OBJECT_SUFFIX";
    private static final String LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY = "PENDING_CONTROLLER_KEY";
    private static final int LIKE_ACTION_CONTROLLER_VERSION = 2;
    private static final String LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY = "like_count_string";
    private static final String LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY = "object_is_liked";
    private static final String LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY = "social_sentence";
    private static final String LIKE_DIALOG_RESPONSE_UNLIKE_TOKEN_KEY = "unlike_token";
    private static final int MAX_CACHE_SIZE = 128;
    private static final int MAX_OBJECT_SUFFIX = 1000;
    private static final String TAG = LikeActionController.class.getSimpleName();
    private static final ConcurrentHashMap<String, LikeActionController> cache = new ConcurrentHashMap();
    private static FileLruCache controllerDiskCache;
    private static WorkQueue diskIOWorkQueue = new WorkQueue(1);
    private static Handler handler;
    private static boolean isInitialized;
    private static boolean isPendingBroadcastReset;
    private static WorkQueue mruCacheWorkQueue = new WorkQueue(1);
    private static String objectIdForPendingController;
    private static volatile int objectSuffix;
    private AppEventsLogger appEventsLogger;
    private Context context;
    private boolean isObjectLiked;
    private boolean isObjectLikedOnServer;
    private boolean isPendingLikeOrUnlike;
    private String likeCountStringWithLike;
    private String likeCountStringWithoutLike;
    private String objectId;
    private boolean objectIsPage;
    private Bundle pendingCallAnalyticsBundle;
    private UUID pendingCallId;
    private Session session;
    private String socialSentenceWithLike;
    private String socialSentenceWithoutLike;
    private String unlikeToken;
    private String verifiedObjectId;

    public interface CreationCallback {
        void onComplete(LikeActionController likeActionController);
    }

    class AnonymousClass1 implements CreationCallback {
        private final /* synthetic */ UUID val$callId;
        private final /* synthetic */ Intent val$data;
        private final /* synthetic */ int val$requestCode;
        private final /* synthetic */ int val$resultCode;

        AnonymousClass1(int i, int i2, Intent intent, UUID uuid) {
            this.val$requestCode = i;
            this.val$resultCode = i2;
            this.val$data = intent;
            this.val$callId = uuid;
        }

        public void onComplete(LikeActionController likeActionController) {
            likeActionController.onActivityResult(this.val$requestCode, this.val$resultCode, this.val$data, this.val$callId);
        }
    }

    class AnonymousClass3 implements Runnable {
        private final /* synthetic */ CreationCallback val$callback;
        private final /* synthetic */ LikeActionController val$controller;

        AnonymousClass3(CreationCallback creationCallback, LikeActionController likeActionController) {
            this.val$callback = creationCallback;
            this.val$controller = likeActionController;
        }

        public void run() {
            this.val$callback.onComplete(this.val$controller);
        }
    }

    private interface RequestCompletionCallback {
        void onComplete();
    }

    private abstract class AbstractRequestWrapper {
        FacebookRequestError error;
        protected String objectId;
        private Request request;

        protected abstract void processSuccess(Response response);

        protected AbstractRequestWrapper(String objectId) {
            this.objectId = objectId;
        }

        void addToBatch(RequestBatch batch) {
            batch.add(this.request);
        }

        protected void setRequest(Request request) {
            this.request = request;
            request.setVersion(ServerProtocol.GRAPH_API_VERSION);
            request.setCallback(new Callback() {
                public void onCompleted(Response response) {
                    AbstractRequestWrapper.this.error = response.getError();
                    if (AbstractRequestWrapper.this.error != null) {
                        AbstractRequestWrapper.this.processError(AbstractRequestWrapper.this.error);
                    } else {
                        AbstractRequestWrapper.this.processSuccess(response);
                    }
                }
            });
        }

        protected void processError(FacebookRequestError error) {
            Object[] objArr = new Object[LikeActionController.LIKE_ACTION_CONTROLLER_VERSION];
            objArr[0] = this.objectId;
            objArr[1] = error;
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error running request for object '%s' : %s", objArr);
        }
    }

    private static class CreateLikeActionControllerWorkItem implements Runnable {
        private CreationCallback callback;
        private Context context;
        private String objectId;

        CreateLikeActionControllerWorkItem(Context context, String objectId, CreationCallback callback) {
            this.context = context;
            this.objectId = objectId;
            this.callback = callback;
        }

        public void run() {
            LikeActionController.createControllerForObjectId(this.context, this.objectId, this.callback);
        }
    }

    private class GetEngagementRequestWrapper extends AbstractRequestWrapper {
        String likeCountStringWithLike;
        String likeCountStringWithoutLike;
        String socialSentenceStringWithLike;
        String socialSentenceStringWithoutLike;

        GetEngagementRequestWrapper(String objectId) {
            super(objectId);
            this.likeCountStringWithLike = LikeActionController.this.likeCountStringWithLike;
            this.likeCountStringWithoutLike = LikeActionController.this.likeCountStringWithoutLike;
            this.socialSentenceStringWithLike = LikeActionController.this.socialSentenceWithLike;
            this.socialSentenceStringWithoutLike = LikeActionController.this.socialSentenceWithoutLike;
            Bundle requestParams = new Bundle();
            requestParams.putString("fields", "engagement.fields(count_string_with_like,count_string_without_like,social_sentence_with_like,social_sentence_without_like)");
            setRequest(new Request(LikeActionController.this.session, objectId, requestParams, HttpMethod.GET));
        }

        protected void processSuccess(Response response) {
            JSONObject engagementResults = Utility.tryGetJSONObjectFromResponse(response.getGraphObject(), "engagement");
            if (engagementResults != null) {
                this.likeCountStringWithLike = engagementResults.optString("count_string_with_like", this.likeCountStringWithLike);
                this.likeCountStringWithoutLike = engagementResults.optString("count_string_without_like", this.likeCountStringWithoutLike);
                this.socialSentenceStringWithLike = engagementResults.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, this.socialSentenceStringWithLike);
                this.socialSentenceStringWithoutLike = engagementResults.optString(LikeActionController.JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, this.socialSentenceStringWithoutLike);
            }
        }

        protected void processError(FacebookRequestError error) {
            Object[] objArr = new Object[LikeActionController.LIKE_ACTION_CONTROLLER_VERSION];
            objArr[0] = this.objectId;
            objArr[1] = error;
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching engagement for object '%s' : %s", objArr);
            LikeActionController.this.logAppEventForError("get_engagement", error);
        }
    }

    private class GetOGObjectIdRequestWrapper extends AbstractRequestWrapper {
        String verifiedObjectId;

        GetOGObjectIdRequestWrapper(String objectId) {
            super(objectId);
            Bundle objectIdRequestParams = new Bundle();
            objectIdRequestParams.putString("fields", "og_object.fields(id)");
            objectIdRequestParams.putString("ids", objectId);
            setRequest(new Request(LikeActionController.this.session, i.a, objectIdRequestParams, HttpMethod.GET));
        }

        protected void processError(FacebookRequestError error) {
            if (error.getErrorMessage().contains("og_object")) {
                this.error = null;
                return;
            }
            Object[] objArr = new Object[LikeActionController.LIKE_ACTION_CONTROLLER_VERSION];
            objArr[0] = this.objectId;
            objArr[1] = error;
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error getting the FB id for object '%s' : %s", objArr);
        }

        protected void processSuccess(Response response) {
            JSONObject results = Utility.tryGetJSONObjectFromResponse(response.getGraphObject(), this.objectId);
            if (results != null) {
                JSONObject ogObject = results.optJSONObject("og_object");
                if (ogObject != null) {
                    this.verifiedObjectId = ogObject.optString(PeppermintConstant.JSON_KEY_ID);
                }
            }
        }
    }

    private class GetOGObjectLikesRequestWrapper extends AbstractRequestWrapper {
        boolean objectIsLiked;
        String unlikeToken;

        GetOGObjectLikesRequestWrapper(String objectId) {
            super(objectId);
            this.objectIsLiked = LikeActionController.this.isObjectLiked;
            Bundle requestParams = new Bundle();
            requestParams.putString("fields", "id,application");
            requestParams.putString("object", objectId);
            setRequest(new Request(LikeActionController.this.session, "me/og.likes", requestParams, HttpMethod.GET));
        }

        protected void processSuccess(Response response) {
            JSONArray dataSet = Utility.tryGetJSONArrayFromResponse(response.getGraphObject(), PeppermintConstant.JSON_KEY_DATA);
            if (dataSet != null) {
                for (int i = 0; i < dataSet.length(); i++) {
                    JSONObject data = dataSet.optJSONObject(i);
                    if (data != null) {
                        this.objectIsLiked = true;
                        JSONObject appData = data.optJSONObject("application");
                        if (appData != null && Utility.areObjectsEqual(LikeActionController.this.session.getApplicationId(), appData.optString(PeppermintConstant.JSON_KEY_ID))) {
                            this.unlikeToken = data.optString(PeppermintConstant.JSON_KEY_ID);
                        }
                    }
                }
            }
        }

        protected void processError(FacebookRequestError error) {
            Object[] objArr = new Object[LikeActionController.LIKE_ACTION_CONTROLLER_VERSION];
            objArr[0] = this.objectId;
            objArr[1] = error;
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error fetching like status for object '%s' : %s", objArr);
            LikeActionController.this.logAppEventForError("get_og_object_like", error);
        }
    }

    private class GetPageIdRequestWrapper extends AbstractRequestWrapper {
        boolean objectIsPage;
        String verifiedObjectId;

        GetPageIdRequestWrapper(String objectId) {
            super(objectId);
            Bundle pageIdRequestParams = new Bundle();
            pageIdRequestParams.putString("fields", PeppermintConstant.JSON_KEY_ID);
            pageIdRequestParams.putString("ids", objectId);
            setRequest(new Request(LikeActionController.this.session, i.a, pageIdRequestParams, HttpMethod.GET));
        }

        protected void processSuccess(Response response) {
            JSONObject results = Utility.tryGetJSONObjectFromResponse(response.getGraphObject(), this.objectId);
            if (results != null) {
                this.verifiedObjectId = results.optString(PeppermintConstant.JSON_KEY_ID);
                this.objectIsPage = !Utility.isNullOrEmpty(this.verifiedObjectId);
            }
        }

        protected void processError(FacebookRequestError error) {
            Object[] objArr = new Object[LikeActionController.LIKE_ACTION_CONTROLLER_VERSION];
            objArr[0] = this.objectId;
            objArr[1] = error;
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error getting the FB id for object '%s' : %s", objArr);
        }
    }

    private static class LikeDialogBuilder extends Builder<LikeDialogBuilder> {
        private String objectId;

        public LikeDialogBuilder(Activity activity, String objectId) {
            super(activity);
            this.objectId = objectId;
        }

        protected EnumSet<? extends DialogFeature> getDialogFeatures() {
            return EnumSet.of(LikeDialogFeature.LIKE_DIALOG);
        }

        protected Bundle getMethodArguments() {
            Bundle methodArgs = new Bundle();
            methodArgs.putString(LikeActionController.JSON_STRING_OBJECT_ID_KEY, this.objectId);
            return methodArgs;
        }

        public PendingCall getAppCall() {
            return this.appCall;
        }

        public String getApplicationId() {
            return this.applicationId;
        }

        public String getWebFallbackUrl() {
            return getWebFallbackUrlInternal();
        }
    }

    private enum LikeDialogFeature implements DialogFeature {
        LIKE_DIALOG(NativeProtocol.PROTOCOL_VERSION_20140701);
        
        private int minVersion;

        private LikeDialogFeature(int minVersion) {
            this.minVersion = minVersion;
        }

        public String getAction() {
            return NativeProtocol.ACTION_LIKE_DIALOG;
        }

        public int getMinVersion() {
            return this.minVersion;
        }
    }

    private static class MRUCacheWorkItem implements Runnable {
        private static ArrayList<String> mruCachedItems = new ArrayList();
        private String cacheItem;
        private boolean shouldTrim;

        MRUCacheWorkItem(String cacheItem, boolean shouldTrim) {
            this.cacheItem = cacheItem;
            this.shouldTrim = shouldTrim;
        }

        public void run() {
            if (this.cacheItem != null) {
                mruCachedItems.remove(this.cacheItem);
                mruCachedItems.add(0, this.cacheItem);
            }
            if (this.shouldTrim && mruCachedItems.size() >= LikeActionController.MAX_CACHE_SIZE) {
                while (64 < mruCachedItems.size()) {
                    LikeActionController.cache.remove((String) mruCachedItems.remove(mruCachedItems.size() - 1));
                }
            }
        }
    }

    private class PublishLikeRequestWrapper extends AbstractRequestWrapper {
        String unlikeToken;

        PublishLikeRequestWrapper(String objectId) {
            super(objectId);
            Bundle likeRequestParams = new Bundle();
            likeRequestParams.putString("object", objectId);
            setRequest(new Request(LikeActionController.this.session, "me/og.likes", likeRequestParams, HttpMethod.POST));
        }

        protected void processSuccess(Response response) {
            this.unlikeToken = Utility.safeGetStringFromResponse(response.getGraphObject(), PeppermintConstant.JSON_KEY_ID);
        }

        protected void processError(FacebookRequestError error) {
            if (error.getErrorCode() == LikeActionController.ERROR_CODE_OBJECT_ALREADY_LIKED) {
                this.error = null;
                return;
            }
            Object[] objArr = new Object[LikeActionController.LIKE_ACTION_CONTROLLER_VERSION];
            objArr[0] = this.objectId;
            objArr[1] = error;
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error liking object '%s' : %s", objArr);
            LikeActionController.this.logAppEventForError("publish_like", error);
        }
    }

    private class PublishUnlikeRequestWrapper extends AbstractRequestWrapper {
        private String unlikeToken;

        PublishUnlikeRequestWrapper(String unlikeToken) {
            super(null);
            this.unlikeToken = unlikeToken;
            setRequest(new Request(LikeActionController.this.session, unlikeToken, null, HttpMethod.DELETE));
        }

        protected void processSuccess(Response response) {
        }

        protected void processError(FacebookRequestError error) {
            Object[] objArr = new Object[LikeActionController.LIKE_ACTION_CONTROLLER_VERSION];
            objArr[0] = this.unlikeToken;
            objArr[1] = error;
            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Error unliking object with unlike token '%s' : %s", objArr);
            LikeActionController.this.logAppEventForError("publish_unlike", error);
        }
    }

    private static class SerializeToDiskWorkItem implements Runnable {
        private String cacheKey;
        private String controllerJson;

        SerializeToDiskWorkItem(String cacheKey, String controllerJson) {
            this.cacheKey = cacheKey;
            this.controllerJson = controllerJson;
        }

        public void run() {
            LikeActionController.serializeToDiskSynchronously(this.cacheKey, this.controllerJson);
        }
    }

    public static boolean handleOnActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        UUID callId = NativeProtocol.getCallIdFromIntent(data);
        if (callId == null) {
            return false;
        }
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            objectIdForPendingController = context.getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, null);
        }
        if (Utility.isNullOrEmpty(objectIdForPendingController)) {
            return false;
        }
        getControllerForObjectId(context, objectIdForPendingController, new AnonymousClass1(requestCode, resultCode, data, callId));
        return true;
    }

    public static void getControllerForObjectId(Context context, String objectId, CreationCallback callback) {
        if (!isInitialized) {
            performFirstInitialize(context);
        }
        LikeActionController controllerForObject = getControllerFromInMemoryCache(objectId);
        if (controllerForObject != null) {
            invokeCallbackWithController(callback, controllerForObject);
        } else {
            diskIOWorkQueue.addActiveWorkItem(new CreateLikeActionControllerWorkItem(context, objectId, callback));
        }
    }

    private static void createControllerForObjectId(Context context, String objectId, CreationCallback callback) {
        LikeActionController controllerForObject = getControllerFromInMemoryCache(objectId);
        if (controllerForObject != null) {
            invokeCallbackWithController(callback, controllerForObject);
            return;
        }
        controllerForObject = deserializeFromDiskSynchronously(context, objectId);
        if (controllerForObject == null) {
            controllerForObject = new LikeActionController(context, Session.getActiveSession(), objectId);
            serializeToDiskAsync(controllerForObject);
        }
        putControllerInMemoryCache(objectId, controllerForObject);
        LikeActionController controllerToRefresh = controllerForObject;
        handler.post(new Runnable(controllerToRefresh) {
            private final /* synthetic */ LikeActionController val$controllerToRefresh;

            {
                this.val$controllerToRefresh = r1;
            }

            public void run() {
                this.val$controllerToRefresh.refreshStatusAsync();
            }
        });
        invokeCallbackWithController(callback, controllerToRefresh);
    }

    private static synchronized void performFirstInitialize(Context context) {
        synchronized (LikeActionController.class) {
            if (!isInitialized) {
                handler = new Handler(Looper.getMainLooper());
                objectSuffix = context.getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).getInt(LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, 1);
                controllerDiskCache = new FileLruCache(context, TAG, new Limits());
                registerSessionBroadcastReceivers(context);
                isInitialized = true;
            }
        }
    }

    private static void invokeCallbackWithController(CreationCallback callback, LikeActionController controller) {
        if (callback != null) {
            handler.post(new AnonymousClass3(callback, controller));
        }
    }

    private static void registerSessionBroadcastReceivers(Context context) {
        LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(context);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Session.ACTION_ACTIVE_SESSION_UNSET);
        filter.addAction(Session.ACTION_ACTIVE_SESSION_CLOSED);
        filter.addAction(Session.ACTION_ACTIVE_SESSION_OPENED);
        broadcastManager.registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context receiverContext, Intent intent) {
                if (!LikeActionController.isPendingBroadcastReset) {
                    boolean shouldClearDisk;
                    String action = intent.getAction();
                    if (Utility.areObjectsEqual(Session.ACTION_ACTIVE_SESSION_UNSET, action) || Utility.areObjectsEqual(Session.ACTION_ACTIVE_SESSION_CLOSED, action)) {
                        shouldClearDisk = true;
                    } else {
                        shouldClearDisk = false;
                    }
                    LikeActionController.isPendingBroadcastReset = true;
                    final Context broadcastContext = receiverContext;
                    LikeActionController.handler.postDelayed(new Runnable() {
                        public void run() {
                            if (shouldClearDisk) {
                                LikeActionController.objectSuffix = (LikeActionController.objectSuffix + 1) % LikeActionController.MAX_OBJECT_SUFFIX;
                                broadcastContext.getSharedPreferences(LikeActionController.LIKE_ACTION_CONTROLLER_STORE, 0).edit().putInt(LikeActionController.LIKE_ACTION_CONTROLLER_STORE_OBJECT_SUFFIX_KEY, LikeActionController.objectSuffix).apply();
                                LikeActionController.cache.clear();
                                LikeActionController.controllerDiskCache.clearCache();
                            }
                            LikeActionController.broadcastAction(broadcastContext, null, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
                            LikeActionController.isPendingBroadcastReset = false;
                        }
                    }, 100);
                }
            }
        }, filter);
    }

    private static void putControllerInMemoryCache(String objectId, LikeActionController controllerForObject) {
        String cacheKey = getCacheKeyForObjectId(objectId);
        mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem(cacheKey, true));
        cache.put(cacheKey, controllerForObject);
    }

    private static LikeActionController getControllerFromInMemoryCache(String objectId) {
        String cacheKey = getCacheKeyForObjectId(objectId);
        LikeActionController controller = (LikeActionController) cache.get(cacheKey);
        if (controller != null) {
            mruCacheWorkQueue.addActiveWorkItem(new MRUCacheWorkItem(cacheKey, false));
        }
        return controller;
    }

    private static void serializeToDiskAsync(LikeActionController controller) {
        String controllerJson = serializeToJson(controller);
        String cacheKey = getCacheKeyForObjectId(controller.objectId);
        if (!Utility.isNullOrEmpty(controllerJson) && !Utility.isNullOrEmpty(cacheKey)) {
            diskIOWorkQueue.addActiveWorkItem(new SerializeToDiskWorkItem(cacheKey, controllerJson));
        }
    }

    private static void serializeToDiskSynchronously(String cacheKey, String controllerJson) {
        OutputStream outputStream = null;
        try {
            outputStream = controllerDiskCache.openPutStream(cacheKey);
            outputStream.write(controllerJson.getBytes());
            if (outputStream != null) {
                Utility.closeQuietly(outputStream);
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to serialize controller to disk", e);
            if (outputStream != null) {
                Utility.closeQuietly(outputStream);
            }
        } catch (Throwable th) {
            if (outputStream != null) {
                Utility.closeQuietly(outputStream);
            }
        }
    }

    private static LikeActionController deserializeFromDiskSynchronously(Context context, String objectId) {
        LikeActionController controller = null;
        try {
            InputStream inputStream = controllerDiskCache.get(getCacheKeyForObjectId(objectId));
            if (inputStream != null) {
                String controllerJsonString = Utility.readStreamToString(inputStream);
                if (!Utility.isNullOrEmpty(controllerJsonString)) {
                    controller = deserializeFromJson(context, controllerJsonString);
                }
            }
            if (inputStream != null) {
                Utility.closeQuietly(inputStream);
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to deserialize controller from disk", e);
            controller = null;
            if (null != null) {
                Utility.closeQuietly(null);
            }
        } catch (Throwable th) {
            if (null != null) {
                Utility.closeQuietly(null);
            }
        }
        return controller;
    }

    private static LikeActionController deserializeFromJson(Context context, String controllerJsonString) {
        try {
            JSONObject controllerJson = new JSONObject(controllerJsonString);
            if (controllerJson.optInt(JSON_INT_VERSION_KEY, -1) != LIKE_ACTION_CONTROLLER_VERSION) {
                return null;
            }
            LikeActionController controller = new LikeActionController(context, Session.getActiveSession(), controllerJson.getString(JSON_STRING_OBJECT_ID_KEY));
            controller.likeCountStringWithLike = controllerJson.optString(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, null);
            controller.likeCountStringWithoutLike = controllerJson.optString(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, null);
            controller.socialSentenceWithLike = controllerJson.optString(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, null);
            controller.socialSentenceWithoutLike = controllerJson.optString(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, null);
            controller.isObjectLiked = controllerJson.optBoolean(JSON_BOOL_IS_OBJECT_LIKED_KEY);
            controller.unlikeToken = controllerJson.optString(LIKE_DIALOG_RESPONSE_UNLIKE_TOKEN_KEY, null);
            String pendingCallIdString = controllerJson.optString(JSON_STRING_PENDING_CALL_ID_KEY, null);
            if (!Utility.isNullOrEmpty(pendingCallIdString)) {
                controller.pendingCallId = UUID.fromString(pendingCallIdString);
            }
            JSONObject analyticsJSON = controllerJson.optJSONObject(JSON_BUNDLE_PENDING_CALL_ANALYTICS_BUNDLE);
            if (analyticsJSON == null) {
                return controller;
            }
            controller.pendingCallAnalyticsBundle = BundleJSONConverter.convertToBundle(analyticsJSON);
            return controller;
        } catch (JSONException e) {
            Log.e(TAG, "Unable to deserialize controller from JSON", e);
            return null;
        }
    }

    private static String serializeToJson(LikeActionController controller) {
        JSONObject controllerJson = new JSONObject();
        try {
            controllerJson.put(JSON_INT_VERSION_KEY, LIKE_ACTION_CONTROLLER_VERSION);
            controllerJson.put(JSON_STRING_OBJECT_ID_KEY, controller.objectId);
            controllerJson.put(JSON_STRING_LIKE_COUNT_WITH_LIKE_KEY, controller.likeCountStringWithLike);
            controllerJson.put(JSON_STRING_LIKE_COUNT_WITHOUT_LIKE_KEY, controller.likeCountStringWithoutLike);
            controllerJson.put(JSON_STRING_SOCIAL_SENTENCE_WITH_LIKE_KEY, controller.socialSentenceWithLike);
            controllerJson.put(JSON_STRING_SOCIAL_SENTENCE_WITHOUT_LIKE_KEY, controller.socialSentenceWithoutLike);
            controllerJson.put(JSON_BOOL_IS_OBJECT_LIKED_KEY, controller.isObjectLiked);
            controllerJson.put(LIKE_DIALOG_RESPONSE_UNLIKE_TOKEN_KEY, controller.unlikeToken);
            if (controller.pendingCallId != null) {
                controllerJson.put(JSON_STRING_PENDING_CALL_ID_KEY, controller.pendingCallId.toString());
            }
            if (controller.pendingCallAnalyticsBundle != null) {
                JSONObject analyticsJSON = BundleJSONConverter.convertToJSON(controller.pendingCallAnalyticsBundle);
                if (analyticsJSON != null) {
                    controllerJson.put(JSON_BUNDLE_PENDING_CALL_ANALYTICS_BUNDLE, analyticsJSON);
                }
            }
            return controllerJson.toString();
        } catch (JSONException e) {
            Log.e(TAG, "Unable to serialize controller to JSON", e);
            return null;
        }
    }

    private static String getCacheKeyForObjectId(String objectId) {
        String accessTokenPortion = null;
        Session activeSession = Session.getActiveSession();
        if (activeSession != null && activeSession.isOpened()) {
            accessTokenPortion = activeSession.getAccessToken();
        }
        if (accessTokenPortion != null) {
            accessTokenPortion = Utility.md5hash(accessTokenPortion);
        }
        return String.format("%s|%s|com.fb.sdk.like|%d", new Object[]{objectId, Utility.coerceValueIfNullOrEmpty(accessTokenPortion, i.a), Integer.valueOf(objectSuffix)});
    }

    private static void broadcastAction(Context context, LikeActionController controller, String action) {
        broadcastAction(context, controller, action, null);
    }

    private static void broadcastAction(Context context, LikeActionController controller, String action, Bundle data) {
        Intent broadcastIntent = new Intent(action);
        if (controller != null) {
            if (data == null) {
                data = new Bundle();
            }
            data.putString(ACTION_OBJECT_ID_KEY, controller.getObjectId());
        }
        if (data != null) {
            broadcastIntent.putExtras(data);
        }
        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(broadcastIntent);
    }

    private LikeActionController(Context context, Session session, String objectId) {
        this.context = context;
        this.session = session;
        this.objectId = objectId;
        this.appEventsLogger = AppEventsLogger.newLogger(context, session);
    }

    public String getObjectId() {
        return this.objectId;
    }

    public String getLikeCountString() {
        return this.isObjectLiked ? this.likeCountStringWithLike : this.likeCountStringWithoutLike;
    }

    public String getSocialSentence() {
        return this.isObjectLiked ? this.socialSentenceWithLike : this.socialSentenceWithoutLike;
    }

    public boolean isObjectLiked() {
        return this.isObjectLiked;
    }

    public void toggleLike(Activity activity, Bundle analyticsParameters) {
        this.appEventsLogger.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_TAP, null, analyticsParameters);
        boolean shouldLikeObject = !this.isObjectLiked;
        if (canUseOGPublish()) {
            updateState(shouldLikeObject, this.likeCountStringWithLike, this.likeCountStringWithoutLike, this.socialSentenceWithLike, this.socialSentenceWithoutLike, this.unlikeToken);
            if (this.isPendingLikeOrUnlike) {
                this.appEventsLogger.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNDO_QUICKLY, null, analyticsParameters);
                return;
            }
        }
        performLikeOrUnlike(activity, shouldLikeObject, analyticsParameters);
    }

    private void performLikeOrUnlike(Activity activity, boolean shouldLikeObject, Bundle analyticsParameters) {
        if (!canUseOGPublish()) {
            presentLikeDialog(activity, analyticsParameters);
        } else if (shouldLikeObject) {
            publishLikeAsync(activity, analyticsParameters);
        } else if (Utility.isNullOrEmpty(this.unlikeToken)) {
            fallbackToDialog(activity, analyticsParameters, true);
        } else {
            publishUnlikeAsync(activity, analyticsParameters);
        }
    }

    private void fallbackToDialog(Activity activity, Bundle analyticsParameters, boolean oldLikeState) {
        updateState(oldLikeState, this.likeCountStringWithLike, this.likeCountStringWithoutLike, this.socialSentenceWithLike, this.socialSentenceWithoutLike, this.unlikeToken);
        presentLikeDialog(activity, analyticsParameters);
    }

    private void updateState(boolean isObjectLiked, String likeCountStringWithLike, String likeCountStringWithoutLike, String socialSentenceWithLike, String socialSentenceWithoutLike, String unlikeToken) {
        likeCountStringWithLike = Utility.coerceValueIfNullOrEmpty(likeCountStringWithLike, null);
        likeCountStringWithoutLike = Utility.coerceValueIfNullOrEmpty(likeCountStringWithoutLike, null);
        socialSentenceWithLike = Utility.coerceValueIfNullOrEmpty(socialSentenceWithLike, null);
        socialSentenceWithoutLike = Utility.coerceValueIfNullOrEmpty(socialSentenceWithoutLike, null);
        unlikeToken = Utility.coerceValueIfNullOrEmpty(unlikeToken, null);
        boolean stateChanged = (isObjectLiked == this.isObjectLiked && Utility.areObjectsEqual(likeCountStringWithLike, this.likeCountStringWithLike) && Utility.areObjectsEqual(likeCountStringWithoutLike, this.likeCountStringWithoutLike) && Utility.areObjectsEqual(socialSentenceWithLike, this.socialSentenceWithLike) && Utility.areObjectsEqual(socialSentenceWithoutLike, this.socialSentenceWithoutLike) && Utility.areObjectsEqual(unlikeToken, this.unlikeToken)) ? false : true;
        if (stateChanged) {
            this.isObjectLiked = isObjectLiked;
            this.likeCountStringWithLike = likeCountStringWithLike;
            this.likeCountStringWithoutLike = likeCountStringWithoutLike;
            this.socialSentenceWithLike = socialSentenceWithLike;
            this.socialSentenceWithoutLike = socialSentenceWithoutLike;
            this.unlikeToken = unlikeToken;
            serializeToDiskAsync(this);
            broadcastAction(this.context, this, ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        }
    }

    private void presentLikeDialog(Activity activity, Bundle analyticsParameters) {
        LikeDialogBuilder likeDialogBuilder = new LikeDialogBuilder(activity, this.objectId);
        if (likeDialogBuilder.canPresent()) {
            trackPendingCall(likeDialogBuilder.build().present(), analyticsParameters);
            this.appEventsLogger.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_DIALOG, null, analyticsParameters);
            return;
        }
        String webFallbackUrl = likeDialogBuilder.getWebFallbackUrl();
        if (!Utility.isNullOrEmpty(webFallbackUrl) && FacebookWebFallbackDialog.presentWebFallback(activity, webFallbackUrl, likeDialogBuilder.getApplicationId(), likeDialogBuilder.getAppCall(), getFacebookDialogCallback(analyticsParameters))) {
            this.appEventsLogger.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_PRESENT_FALLBACK, null, analyticsParameters);
        }
    }

    private boolean onActivityResult(int requestCode, int resultCode, Intent data, UUID callId) {
        if (this.pendingCallId == null || !this.pendingCallId.equals(callId)) {
            return false;
        }
        PendingCall pendingCall = PendingCallStore.getInstance().getPendingCallById(this.pendingCallId);
        if (pendingCall == null) {
            return false;
        }
        FacebookDialog.handleActivityResult(this.context, pendingCall, requestCode, data, getFacebookDialogCallback(this.pendingCallAnalyticsBundle));
        stopTrackingPendingCall();
        return true;
    }

    private FacebookDialog.Callback getFacebookDialogCallback(final Bundle analyticsParameters) {
        return new FacebookDialog.Callback() {
            public void onComplete(PendingCall pendingCall, Bundle data) {
                if (data != null && data.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY)) {
                    String unlikeToken;
                    boolean isObjectLiked = data.getBoolean(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY);
                    String likeCountStringWithLike = LikeActionController.this.likeCountStringWithLike;
                    String likeCountStringWithoutLike = LikeActionController.this.likeCountStringWithoutLike;
                    if (data.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY)) {
                        likeCountStringWithLike = data.getString(LikeActionController.LIKE_DIALOG_RESPONSE_LIKE_COUNT_STRING_KEY);
                        likeCountStringWithoutLike = likeCountStringWithLike;
                    }
                    String socialSentenceWithLike = LikeActionController.this.socialSentenceWithLike;
                    String socialSentenceWithoutWithoutLike = LikeActionController.this.socialSentenceWithoutLike;
                    if (data.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY)) {
                        socialSentenceWithLike = data.getString(LikeActionController.LIKE_DIALOG_RESPONSE_SOCIAL_SENTENCE_KEY);
                        socialSentenceWithoutWithoutLike = socialSentenceWithLike;
                    }
                    if (data.containsKey(LikeActionController.LIKE_DIALOG_RESPONSE_OBJECT_IS_LIKED_KEY)) {
                        unlikeToken = data.getString(LikeActionController.LIKE_DIALOG_RESPONSE_UNLIKE_TOKEN_KEY);
                    } else {
                        unlikeToken = LikeActionController.this.unlikeToken;
                    }
                    Bundle logParams = analyticsParameters == null ? new Bundle() : analyticsParameters;
                    logParams.putString(AnalyticsEvents.PARAMETER_CALL_ID, pendingCall.getCallId().toString());
                    LikeActionController.this.appEventsLogger.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DIALOG_DID_SUCCEED, null, logParams);
                    LikeActionController.this.updateState(isObjectLiked, likeCountStringWithLike, likeCountStringWithoutLike, socialSentenceWithLike, socialSentenceWithoutWithoutLike, unlikeToken);
                }
            }

            public void onError(PendingCall pendingCall, Exception error, Bundle data) {
                Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Like Dialog failed with error : %s", error);
                Bundle logParams = analyticsParameters == null ? new Bundle() : analyticsParameters;
                logParams.putString(AnalyticsEvents.PARAMETER_CALL_ID, pendingCall.getCallId().toString());
                LikeActionController.this.logAppEventForError("present_dialog", logParams);
                LikeActionController.broadcastAction(LikeActionController.this.context, LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, data);
            }
        };
    }

    private void trackPendingCall(PendingCall pendingCall, Bundle analyticsParameters) {
        PendingCallStore.getInstance().trackPendingCall(pendingCall);
        this.pendingCallId = pendingCall.getCallId();
        storeObjectIdForPendingController(this.objectId);
        this.pendingCallAnalyticsBundle = analyticsParameters;
        serializeToDiskAsync(this);
    }

    private void stopTrackingPendingCall() {
        PendingCallStore.getInstance().stopTrackingPendingCall(this.pendingCallId);
        this.pendingCallId = null;
        this.pendingCallAnalyticsBundle = null;
        storeObjectIdForPendingController(null);
    }

    private void storeObjectIdForPendingController(String objectId) {
        objectIdForPendingController = objectId;
        this.context.getSharedPreferences(LIKE_ACTION_CONTROLLER_STORE, 0).edit().putString(LIKE_ACTION_CONTROLLER_STORE_PENDING_OBJECT_ID_KEY, objectIdForPendingController).apply();
    }

    private boolean canUseOGPublish() {
        return (this.objectIsPage || this.verifiedObjectId == null || this.session == null || this.session.getPermissions() == null || !this.session.getPermissions().contains("publish_actions")) ? false : true;
    }

    private void publishLikeAsync(final Activity activity, final Bundle analyticsParameters) {
        this.isPendingLikeOrUnlike = true;
        fetchVerifiedObjectId(new RequestCompletionCallback() {
            public void onComplete() {
                if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                    Bundle errorBundle = new Bundle();
                    errorBundle.putString(NativeProtocol.STATUS_ERROR_DESCRIPTION, LikeActionController.ERROR_INVALID_OBJECT_ID);
                    LikeActionController.broadcastAction(LikeActionController.this.context, LikeActionController.this, LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR, errorBundle);
                    return;
                }
                RequestBatch requestBatch = new RequestBatch();
                final PublishLikeRequestWrapper likeRequest = new PublishLikeRequestWrapper(LikeActionController.this.verifiedObjectId);
                likeRequest.addToBatch(requestBatch);
                final Activity activity = activity;
                final Bundle bundle = analyticsParameters;
                requestBatch.addCallback(new RequestBatch.Callback() {
                    public void onBatchCompleted(RequestBatch batch) {
                        LikeActionController.this.isPendingLikeOrUnlike = false;
                        if (likeRequest.error != null) {
                            LikeActionController.this.fallbackToDialog(activity, bundle, false);
                            return;
                        }
                        LikeActionController.this.unlikeToken = Utility.coerceValueIfNullOrEmpty(likeRequest.unlikeToken, null);
                        LikeActionController.this.isObjectLikedOnServer = true;
                        LikeActionController.this.appEventsLogger.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_LIKE, null, bundle);
                        LikeActionController.this.toggleAgainIfNeeded(activity, bundle);
                    }
                });
                requestBatch.executeAsync();
            }
        });
    }

    private void publishUnlikeAsync(final Activity activity, final Bundle analyticsParameters) {
        this.isPendingLikeOrUnlike = true;
        RequestBatch requestBatch = new RequestBatch();
        final PublishUnlikeRequestWrapper unlikeRequest = new PublishUnlikeRequestWrapper(this.unlikeToken);
        unlikeRequest.addToBatch(requestBatch);
        requestBatch.addCallback(new RequestBatch.Callback() {
            public void onBatchCompleted(RequestBatch batch) {
                LikeActionController.this.isPendingLikeOrUnlike = false;
                if (unlikeRequest.error != null) {
                    LikeActionController.this.fallbackToDialog(activity, analyticsParameters, true);
                    return;
                }
                LikeActionController.this.unlikeToken = null;
                LikeActionController.this.isObjectLikedOnServer = false;
                LikeActionController.this.appEventsLogger.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_DID_UNLIKE, null, analyticsParameters);
                LikeActionController.this.toggleAgainIfNeeded(activity, analyticsParameters);
            }
        });
        requestBatch.executeAsync();
    }

    private void refreshStatusAsync() {
        if (this.session == null || this.session.isClosed() || SessionState.CREATED.equals(this.session.getState())) {
            refreshStatusViaService();
        } else if (this.session.isOpened()) {
            fetchVerifiedObjectId(new RequestCompletionCallback() {
                public void onComplete() {
                    final GetOGObjectLikesRequestWrapper objectLikesRequest = new GetOGObjectLikesRequestWrapper(LikeActionController.this.verifiedObjectId);
                    final GetEngagementRequestWrapper engagementRequest = new GetEngagementRequestWrapper(LikeActionController.this.verifiedObjectId);
                    RequestBatch requestBatch = new RequestBatch();
                    objectLikesRequest.addToBatch(requestBatch);
                    engagementRequest.addToBatch(requestBatch);
                    requestBatch.addCallback(new RequestBatch.Callback() {
                        public void onBatchCompleted(RequestBatch batch) {
                            if (objectLikesRequest.error == null && engagementRequest.error == null) {
                                LikeActionController.this.updateState(objectLikesRequest.objectIsLiked, engagementRequest.likeCountStringWithLike, engagementRequest.likeCountStringWithoutLike, engagementRequest.socialSentenceStringWithLike, engagementRequest.socialSentenceStringWithoutLike, objectLikesRequest.unlikeToken);
                                return;
                            }
                            Logger.log(LoggingBehavior.REQUESTS, LikeActionController.TAG, "Unable to refresh like state for id: '%s'", LikeActionController.this.objectId);
                        }
                    });
                    requestBatch.executeAsync();
                }
            });
        }
    }

    private void refreshStatusViaService() {
        LikeStatusClient likeStatusClient = new LikeStatusClient(this.context, Settings.getApplicationId(), this.objectId);
        if (likeStatusClient.start()) {
            likeStatusClient.setCompletedListener(new CompletedListener() {
                public void completed(Bundle result) {
                    if (result != null && result.containsKey(NativeProtocol.EXTRA_OBJECT_IS_LIKED)) {
                        String likeCountWithLike;
                        String likeCountWithoutLike;
                        String socialSentenceWithLike;
                        String socialSentenceWithoutLike;
                        String unlikeToken;
                        boolean objectIsLiked = result.getBoolean(NativeProtocol.EXTRA_OBJECT_IS_LIKED);
                        if (result.containsKey(NativeProtocol.EXTRA_LIKE_COUNT_STRING_WITH_LIKE)) {
                            likeCountWithLike = result.getString(NativeProtocol.EXTRA_LIKE_COUNT_STRING_WITH_LIKE);
                        } else {
                            likeCountWithLike = LikeActionController.this.likeCountStringWithLike;
                        }
                        if (result.containsKey(NativeProtocol.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE)) {
                            likeCountWithoutLike = result.getString(NativeProtocol.EXTRA_LIKE_COUNT_STRING_WITHOUT_LIKE);
                        } else {
                            likeCountWithoutLike = LikeActionController.this.likeCountStringWithoutLike;
                        }
                        if (result.containsKey(NativeProtocol.EXTRA_SOCIAL_SENTENCE_WITH_LIKE)) {
                            socialSentenceWithLike = result.getString(NativeProtocol.EXTRA_SOCIAL_SENTENCE_WITH_LIKE);
                        } else {
                            socialSentenceWithLike = LikeActionController.this.socialSentenceWithLike;
                        }
                        if (result.containsKey(NativeProtocol.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE)) {
                            socialSentenceWithoutLike = result.getString(NativeProtocol.EXTRA_SOCIAL_SENTENCE_WITHOUT_LIKE);
                        } else {
                            socialSentenceWithoutLike = LikeActionController.this.socialSentenceWithoutLike;
                        }
                        if (result.containsKey(NativeProtocol.EXTRA_UNLIKE_TOKEN)) {
                            unlikeToken = result.getString(NativeProtocol.EXTRA_UNLIKE_TOKEN);
                        } else {
                            unlikeToken = LikeActionController.this.unlikeToken;
                        }
                        LikeActionController.this.updateState(objectIsLiked, likeCountWithLike, likeCountWithoutLike, socialSentenceWithLike, socialSentenceWithoutLike, unlikeToken);
                    }
                }
            });
        }
    }

    private void toggleAgainIfNeeded(Activity activity, Bundle analyticsParameters) {
        if (this.isObjectLiked != this.isObjectLikedOnServer) {
            performLikeOrUnlike(activity, this.isObjectLiked, analyticsParameters);
        }
    }

    private void fetchVerifiedObjectId(final RequestCompletionCallback completionHandler) {
        if (Utility.isNullOrEmpty(this.verifiedObjectId)) {
            final GetOGObjectIdRequestWrapper objectIdRequest = new GetOGObjectIdRequestWrapper(this.objectId);
            final GetPageIdRequestWrapper pageIdRequest = new GetPageIdRequestWrapper(this.objectId);
            RequestBatch requestBatch = new RequestBatch();
            objectIdRequest.addToBatch(requestBatch);
            pageIdRequest.addToBatch(requestBatch);
            requestBatch.addCallback(new RequestBatch.Callback() {
                public void onBatchCompleted(RequestBatch batch) {
                    LikeActionController.this.verifiedObjectId = objectIdRequest.verifiedObjectId;
                    if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                        LikeActionController.this.verifiedObjectId = pageIdRequest.verifiedObjectId;
                        LikeActionController.this.objectIsPage = pageIdRequest.objectIsPage;
                    }
                    if (Utility.isNullOrEmpty(LikeActionController.this.verifiedObjectId)) {
                        Logger.log(LoggingBehavior.DEVELOPER_ERRORS, LikeActionController.TAG, "Unable to verify the FB id for '%s'. Verify that it is a valid FB object or page", LikeActionController.this.objectId);
                        LikeActionController.this.logAppEventForError("get_verified_id", pageIdRequest.error != null ? pageIdRequest.error : objectIdRequest.error);
                    }
                    if (completionHandler != null) {
                        completionHandler.onComplete();
                    }
                }
            });
            requestBatch.executeAsync();
        } else if (completionHandler != null) {
            completionHandler.onComplete();
        }
    }

    private void logAppEventForError(String action, Bundle parameters) {
        Bundle logParams = new Bundle(parameters);
        logParams.putString(JSON_STRING_OBJECT_ID_KEY, this.objectId);
        logParams.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_CURRENT_ACTION, action);
        this.appEventsLogger.logSdkEvent(AnalyticsEvents.EVENT_LIKE_VIEW_ERROR, null, logParams);
    }

    private void logAppEventForError(String action, FacebookRequestError error) {
        Bundle logParams = new Bundle();
        if (error != null) {
            JSONObject requestResult = error.getRequestResult();
            if (requestResult != null) {
                logParams.putString(GCMConstants.EXTRA_ERROR, requestResult.toString());
            }
        }
        logAppEventForError(action, logParams);
    }
}
