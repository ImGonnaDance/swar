package com.chartboost.sdk.Libraries;

import com.chartboost.sdk.Libraries.c.a;
import com.chartboost.sdk.b;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;

public final class d {
    private d() {
    }

    protected static String a() {
        Info advertisingIdInfo;
        try {
            advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(b.k());
        } catch (IOException e) {
            advertisingIdInfo = null;
        } catch (GooglePlayServicesRepairableException e2) {
            advertisingIdInfo = null;
        } catch (GooglePlayServicesNotAvailableException e3) {
            advertisingIdInfo = null;
        } catch (Throwable e4) {
            CBLogging.a("CBIdentityAdv", "Security Exception when retrieving AD id", e4);
            advertisingIdInfo = null;
        } catch (Throwable e42) {
            CBLogging.a("CBIdentityAdv", "General Exception when retrieving AD id", e42);
            advertisingIdInfo = null;
        }
        if (advertisingIdInfo == null) {
            c.a(a.UNKNOWN);
            return null;
        }
        if (advertisingIdInfo.isLimitAdTrackingEnabled()) {
            c.a(a.TRACKING_DISABLED);
        } else {
            c.a(a.TRACKING_ENABLED);
        }
        try {
            UUID fromString = UUID.fromString(advertisingIdInfo.getId());
            ByteBuffer wrap = ByteBuffer.wrap(new byte[16]);
            wrap.putLong(fromString.getMostSignificantBits());
            wrap.putLong(fromString.getLeastSignificantBits());
            return b.b(wrap.array());
        } catch (Throwable e5) {
            CBLogging.b("CBIdentityAdv", "Exception raised retrieveAdvertisingID", e5);
            return advertisingIdInfo.getId();
        }
    }
}
