package com.com2us.smon.google.service.util;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.util.Log;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.peppermint.PeppermintConstant;
import com.com2us.smon.normal.freefull.google.kr.android.common.R;
import com.facebook.internal.ServerProtocol;
import com.wellbia.xigncode.util.WBBase64;
import com.wellbia.xigncode.util.WBTelecomUtil;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import jp.co.dimage.android.f;
import jp.co.dimage.android.g;
import jp.co.dimage.android.o;

class GameHelperUtils {
    private static final String[] FALLBACK_STRINGS = new String[]{"*Unknown error.", "*Failed to sign in. Please check your network connection and try again.", "*The application is incorrectly configured. Check that the package name and signing certificate match the client ID created in Developer Console. Also, if the application is not yet published, check that the account you are trying to sign in with is listed as a tester account. See logs for more information.", "*License check failed."};
    public static final int R_APP_MISCONFIGURED = 2;
    public static final int R_LICENSE_FAILED = 3;
    public static final int R_SIGN_IN_FAILED = 1;
    public static final int R_UNKNOWN_ERROR = 0;

    GameHelperUtils() {
    }

    static String activityResponseCodeToString(int respCode) {
        switch (respCode) {
            case WBTelecomUtil.TELECOM_TYPE_NONE /*-1*/:
                return "RESULT_OK";
            case g.a /*0*/:
                return "RESULT_CANCELED";
            case PeppermintConstant.RESULT_CODE_RECONNECT_REQUIRED /*10001*/:
                return "RESULT_RECONNECT_REQUIRED";
            case 10002:
                return "SIGN_IN_FAILED";
            case 10003:
                return "RESULT_LICENSE_FAILED";
            case 10004:
                return "RESULT_APP_MISCONFIGURED";
            case 10005:
                return "RESULT_LEFT_ROOM";
            default:
                return String.valueOf(respCode);
        }
    }

    static String errorCodeToString(int errorCode) {
        switch (errorCode) {
            case g.a /*0*/:
                return "SUCCESS(" + errorCode + ")";
            case R_SIGN_IN_FAILED /*1*/:
                return "SERVICE_MISSING(" + errorCode + ")";
            case R_APP_MISCONFIGURED /*2*/:
                return "SERVICE_VERSION_UPDATE_REQUIRED(" + errorCode + ")";
            case R_LICENSE_FAILED /*3*/:
                return "SERVICE_DISABLED(" + errorCode + ")";
            case o.d /*4*/:
                return "SIGN_IN_REQUIRED(" + errorCode + ")";
            case f.bc /*5*/:
                return "INVALID_ACCOUNT(" + errorCode + ")";
            case f.aL /*6*/:
                return "RESOLUTION_REQUIRED(" + errorCode + ")";
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance /*7*/:
                return "NETWORK_ERROR(" + errorCode + ")";
            case WBBase64.URL_SAFE /*8*/:
                return "INTERNAL_ERROR(" + errorCode + ")";
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoTextColor /*9*/:
                return "SERVICE_INVALID(" + errorCode + ")";
            case R.styleable.WalletFragmentStyle_maskedWalletDetailsLogoImageType /*10*/:
                return "DEVELOPER_ERROR(" + errorCode + ")";
            case R.styleable.MapAttrs_uiZoomGestures /*11*/:
                return "LICENSE_CHECK_FAILED(" + errorCode + ")";
            default:
                return "Unknown error code " + errorCode;
        }
    }

    static void printMisconfiguredDebugInfo(Context ctx) {
        Log.w("GameHelper", "****");
        Log.w("GameHelper", "****");
        Log.w("GameHelper", "**** APP NOT CORRECTLY CONFIGURED TO USE GOOGLE PLAY GAME SERVICES");
        Log.w("GameHelper", "**** This is usually caused by one of these reasons:");
        Log.w("GameHelper", "**** (1) Your package name and certificate fingerprint do not match");
        Log.w("GameHelper", "****     the client ID you registered in Developer Console.");
        Log.w("GameHelper", "**** (2) Your App ID was incorrectly entered.");
        Log.w("GameHelper", "**** (3) Your game settings have not been published and you are ");
        Log.w("GameHelper", "****     trying to log in with an account that is not listed as");
        Log.w("GameHelper", "****     a test account.");
        Log.w("GameHelper", "****");
        if (ctx == null) {
            Log.w("GameHelper", "*** (no Context, so can't print more debug info)");
            return;
        }
        Log.w("GameHelper", "**** To help you debug, here is the information about this app");
        Log.w("GameHelper", "**** Package name         : " + ctx.getPackageName());
        Log.w("GameHelper", "**** Cert SHA1 fingerprint: " + getSHA1CertFingerprint(ctx));
        Log.w("GameHelper", "**** App ID from          : " + getAppIdFromResource(ctx));
        Log.w("GameHelper", "****");
        Log.w("GameHelper", "**** Check that the above information matches your setup in ");
        Log.w("GameHelper", "**** Developer Console. Also, check that you're logging in with the");
        Log.w("GameHelper", "**** right account (it should be listed in the Testers section if");
        Log.w("GameHelper", "**** your project is not yet published).");
        Log.w("GameHelper", "****");
        Log.w("GameHelper", "**** For more information, refer to the troubleshooting guide:");
        Log.w("GameHelper", "****   http://developers.google.com/games/services/android/troubleshooting");
    }

    static String getAppIdFromResource(Context ctx) {
        try {
            Resources res = ctx.getResources();
            return res.getString(res.getIdentifier(ServerProtocol.FALLBACK_DIALOG_PARAM_APP_ID, "string", ctx.getPackageName()));
        } catch (Exception ex) {
            ex.printStackTrace();
            return "??? (failed to retrieve APP ID)";
        }
    }

    static String getSHA1CertFingerprint(Context ctx) {
        try {
            Signature[] sigs = ctx.getPackageManager().getPackageInfo(ctx.getPackageName(), 64).signatures;
            if (sigs.length == 0) {
                return "ERROR: NO SIGNATURE.";
            }
            if (sigs.length > R_SIGN_IN_FAILED) {
                return "ERROR: MULTIPLE SIGNATURES";
            }
            byte[] digest = MessageDigest.getInstance("SHA1").digest(sigs[0].toByteArray());
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < digest.length; i += R_SIGN_IN_FAILED) {
                if (i > 0) {
                    hexString.append(":");
                }
                byteToString(hexString, digest[i]);
            }
            return hexString.toString();
        } catch (NameNotFoundException ex) {
            ex.printStackTrace();
            return "(ERROR: package not found)";
        } catch (NoSuchAlgorithmException ex2) {
            ex2.printStackTrace();
            return "(ERROR: SHA1 algorithm not found)";
        }
    }

    static void byteToString(StringBuilder sb, byte b) {
        int unsigned_byte;
        if (b < (byte) 0) {
            unsigned_byte = b + ModuleConfig.SOCIAL_MEDIA_MOUDLE;
        } else {
            byte unsigned_byte2 = b;
        }
        int hi = unsigned_byte / 16;
        int lo = unsigned_byte % 16;
        sb.append("0123456789ABCDEF".substring(hi, hi + R_SIGN_IN_FAILED));
        sb.append("0123456789ABCDEF".substring(lo, lo + R_SIGN_IN_FAILED));
    }

    static String getString(Context ctx, int whichString) {
        return FALLBACK_STRINGS[whichString];
    }
}
