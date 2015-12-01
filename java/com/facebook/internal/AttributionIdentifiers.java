package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import android.os.Looper;
import com.facebook.FacebookException;
import java.lang.reflect.Method;

public class AttributionIdentifiers {
    private static final String ANDROID_ID_COLUMN_NAME = "androidid";
    private static final String ATTRIBUTION_ID_COLUMN_NAME = "aid";
    private static final Uri ATTRIBUTION_ID_CONTENT_URI = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
    private static final int CONNECTION_RESULT_SUCCESS = 0;
    private static final long IDENTIFIER_REFRESH_INTERVAL_MILLIS = 3600000;
    private static final String LIMIT_TRACKING_COLUMN_NAME = "limit_tracking";
    private static final String TAG = AttributionIdentifiers.class.getCanonicalName();
    private static AttributionIdentifiers recentlyFetchedIdentifiers;
    private String androidAdvertiserId;
    private String attributionId;
    private long fetchTime;
    private boolean limitTracking;

    public static com.facebook.internal.AttributionIdentifiers getAttributionIdentifiers(android.content.Context r13) {
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x00a8 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:38)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:60)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
	at jadx.api.JavaClass.decompile(JavaClass.java:63)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:167)
*/
        /*
        r12 = 0;
        r0 = recentlyFetchedIdentifiers;
        if (r0 == 0) goto L_0x0018;
    L_0x0005:
        r0 = java.lang.System.currentTimeMillis();
        r3 = recentlyFetchedIdentifiers;
        r4 = r3.fetchTime;
        r0 = r0 - r4;
        r4 = 3600000; // 0x36ee80 float:5.044674E-39 double:1.7786363E-317;
        r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r0 >= 0) goto L_0x0018;
    L_0x0015:
        r10 = recentlyFetchedIdentifiers;
    L_0x0017:
        return r10;
    L_0x0018:
        r10 = getAndroidId(r13);
        r8 = 0;
        r0 = 3;
        r2 = new java.lang.String[r0];	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r1 = "aid";	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r2[r0] = r1;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = 1;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r1 = "androidid";	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r2[r0] = r1;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = 2;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r1 = "limit_tracking";	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r2[r0] = r1;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = r13.getContentResolver();	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r1 = ATTRIBUTION_ID_CONTENT_URI;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r3 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r4 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r5 = 0;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r8 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        if (r8 == 0) goto L_0x0044;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
    L_0x003e:
        r0 = r8.moveToFirst();	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        if (r0 != 0) goto L_0x004a;
    L_0x0044:
        if (r8 == 0) goto L_0x0017;
    L_0x0046:
        r8.close();
        goto L_0x0017;
    L_0x004a:
        r0 = "aid";	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r7 = r8.getColumnIndex(r0);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = "androidid";	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r6 = r8.getColumnIndex(r0);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = "limit_tracking";	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r11 = r8.getColumnIndex(r0);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = r8.getString(r7);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r10.attributionId = r0;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        if (r6 <= 0) goto L_0x007c;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
    L_0x0064:
        if (r11 <= 0) goto L_0x007c;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
    L_0x0066:
        r0 = r10.getAndroidAdvertiserId();	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        if (r0 != 0) goto L_0x007c;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
    L_0x006c:
        r0 = r8.getString(r6);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r10.androidAdvertiserId = r0;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = r8.getString(r11);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r0 = java.lang.Boolean.parseBoolean(r0);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r10.limitTracking = r0;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
    L_0x007c:
        if (r8 == 0) goto L_0x0081;
    L_0x007e:
        r8.close();
    L_0x0081:
        r0 = java.lang.System.currentTimeMillis();
        r10.fetchTime = r0;
        recentlyFetchedIdentifiers = r10;
        goto L_0x0017;
    L_0x008a:
        r9 = move-exception;
        r0 = TAG;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r3 = "Caught unexpected exception in getAttributionId(): ";	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r1.<init>(r3);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r3 = r9.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r1 = r1.append(r3);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        r1 = r1.toString();	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x008a, all -> 0x00ab }
        if (r8 == 0) goto L_0x00a8;
    L_0x00a5:
        r8.close();
    L_0x00a8:
        r10 = r12;
        goto L_0x0017;
    L_0x00ab:
        r0 = move-exception;
        if (r8 == 0) goto L_0x00b1;
    L_0x00ae:
        r8.close();
    L_0x00b1:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.internal.AttributionIdentifiers.getAttributionIdentifiers(android.content.Context):com.facebook.internal.AttributionIdentifiers");
    }

    private static AttributionIdentifiers getAndroidId(Context context) {
        AttributionIdentifiers identifiers = new AttributionIdentifiers();
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                throw new FacebookException("getAndroidId cannot be called on the main thread.");
            }
            Method isGooglePlayServicesAvailable = Utility.getMethodQuietly("com.google.android.gms.common.GooglePlayServicesUtil", "isGooglePlayServicesAvailable", Context.class);
            if (isGooglePlayServicesAvailable != null) {
                Object connectionResult = Utility.invokeMethodQuietly(null, isGooglePlayServicesAvailable, context);
                if ((connectionResult instanceof Integer) && ((Integer) connectionResult).intValue() == 0) {
                    Method getAdvertisingIdInfo = Utility.getMethodQuietly("com.google.android.gms.ads.identifier.AdvertisingIdClient", "getAdvertisingIdInfo", Context.class);
                    if (getAdvertisingIdInfo != null) {
                        Object advertisingInfo = Utility.invokeMethodQuietly(null, getAdvertisingIdInfo, context);
                        if (advertisingInfo != null) {
                            Method getId = Utility.getMethodQuietly(advertisingInfo.getClass(), "getId", new Class[CONNECTION_RESULT_SUCCESS]);
                            Method isLimitAdTrackingEnabled = Utility.getMethodQuietly(advertisingInfo.getClass(), "isLimitAdTrackingEnabled", new Class[CONNECTION_RESULT_SUCCESS]);
                            if (!(getId == null || isLimitAdTrackingEnabled == null)) {
                                identifiers.androidAdvertiserId = (String) Utility.invokeMethodQuietly(advertisingInfo, getId, new Object[CONNECTION_RESULT_SUCCESS]);
                                identifiers.limitTracking = ((Boolean) Utility.invokeMethodQuietly(advertisingInfo, isLimitAdTrackingEnabled, new Object[CONNECTION_RESULT_SUCCESS])).booleanValue();
                            }
                        }
                    }
                }
            }
            return identifiers;
        } catch (Exception e) {
            Utility.logd("android_id", e);
        }
    }

    public String getAttributionId() {
        return this.attributionId;
    }

    public String getAndroidAdvertiserId() {
        return this.androidAdvertiserId;
    }

    public boolean isTrackingLimited() {
        return this.limitTracking;
    }
}
