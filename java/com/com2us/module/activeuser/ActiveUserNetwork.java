package com.com2us.module.activeuser;

import android.text.TextUtils;
import com.com2us.module.activeuser.ActiveUserData.DATA_INDEX;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import com.com2us.module.activeuser.downloadcheck.InstallService;
import com.com2us.module.activeuser.useragree.UserAgreeNotifier;
import com.com2us.module.mercury.MercuryDefine;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.util.WrapperUtility;
import com.com2us.peppermint.PeppermintConstant;
import com.facebook.internal.ServerProtocol;
import com.google.android.gcm.GCMConstants;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jp.co.cyberz.fox.a.a.i;
import jp.co.cyberz.fox.notify.a;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ActiveUserNetwork {
    private static final String ENC_FORMAT = "AES/CBC/PKCS7Padding";
    protected static String GATEWAY_TARGET_SERVER = Gateway.GATEWAY_TARGET_SERVER;
    public static final String REQUEST_AGREEMENT = "agreement";
    public static final String REQUEST_CONFIGURATION = "get_config";
    public static final String REQUEST_DOWNLOAD = "download";
    public static final String REQUEST_GETDID = "get_did";
    public static final String REQUEST_REFERRER = "referrer";
    public static final String REQUEST_SESSION = "session_log";
    public static final String REQUEST_UPDATEDID = "update_did";
    public static final int TIMEOUT = 15000;
    private static final String cryptAlgorithm = "AES";
    private static final String cryptMode = "CBC";
    private static final String cryptPadding = "PKCS7Padding";
    private static boolean isEnabledDownload = false;
    private static Logger logger = LoggerGroup.createLogger(InstallService.TAG);
    private static AlgorithmParameterSpec spec = new IvParameterSpec(new byte[16]);

    public static class Received {
        public int errno = -1;
        public String error = null;
    }

    public static class ReceivedAgreementData extends Received {
        public String toString() {
            return "errno=" + this.errno + ", error=" + this.error;
        }
    }

    public static class ReceivedConfigurationData extends Received {
        public int agreement_ex_show = -1;
        public String agreement_ex_url = i.a;
        public String agreement_review_url = i.a;
        public int agreement_show = -1;
        public String agreement_url = i.a;
        public int agreement_version = -1;
        public int notice_action = -1;
        public String notice_button = i.a;
        public String notice_message = i.a;
        public int notice_show = -1;
        public String notice_title = i.a;
        public String notice_url = i.a;

        public String toString() {
            return "errno=" + this.errno + ", error=" + this.error + ", agreement_show=" + this.agreement_show + ", agreement_version=" + this.agreement_version + ", agreement_url=" + this.agreement_url + ", agreement_review_url=" + this.agreement_review_url + ", agreement_ex_show=" + this.agreement_ex_show + ", agreement_ex_url=" + this.agreement_ex_url + ", notice_show=" + this.notice_show + ", notice_title=" + this.notice_title + ", notice_message=" + this.notice_message + ", notice_button=" + this.notice_button + ", notice_action=" + this.notice_action + ", notice_url=" + this.notice_url;
        }
    }

    public static class ReceivedDIDData extends Received {
        public String did = null;

        public String toString() {
            return "errno=" + this.errno + ", error=" + this.error + ", did=" + this.did;
        }
    }

    public static class ReceivedDownloadData extends Received {
        public String did = null;
        public int session_max_num = 1;
        public int session_max_time = 0;

        public String toString() {
            return "errno=" + this.errno + ", error=" + this.error + ", did=" + this.did + ", session_max_num=" + this.session_max_num + ", session_max_time=" + this.session_max_time;
        }
    }

    @Deprecated
    public static class ReceivedModuleVersionData extends Received {
    }

    public static class ReceivedReferrerData extends Received {
    }

    public static class ReceivedSessionData extends Received {
        public int session_max_num = 1;
        public int session_max_time = 0;

        public String toString() {
            return "errno=" + this.errno + ", error=" + this.error + ", session_max_num=" + this.session_max_num + ", session_max_time=" + this.session_max_time;
        }
    }

    private ActiveUserNetwork() {
    }

    public static void setEnabledDownload(boolean isEnabled) {
        isEnabledDownload = isEnabled;
    }

    @Deprecated
    public static void setEnableUserAgree(boolean agree) {
    }

    public static Object processNetworkTask(String requestCmd) {
        return processNetworkTask(GATEWAY_TARGET_SERVER, requestCmd);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized java.lang.Object processNetworkTask(java.lang.String r32, java.lang.String r33) {
        /*
        r27 = com.com2us.module.activeuser.ActiveUserNetwork.class;
        monitor-enter(r27);
        r21 = 0;
        r13 = 0;
        r5 = 0;
        r18 = 0;
        r16 = 0;
        r12 = new org.apache.http.params.BasicHttpParams;	 Catch:{ all -> 0x0220 }
        r12.<init>();	 Catch:{ all -> 0x0220 }
        r23 = makeSendData(r33);	 Catch:{ all -> 0x0220 }
        if (r23 != 0) goto L_0x001a;
    L_0x0016:
        r26 = 0;
    L_0x0018:
        monitor-exit(r27);
        return r26;
    L_0x001a:
        r6 = new org.apache.http.impl.client.DefaultHttpClient;	 Catch:{ RuntimeException -> 0x024b, IOException -> 0x0245, Exception -> 0x0200 }
        r6.<init>(r12);	 Catch:{ RuntimeException -> 0x024b, IOException -> 0x0245, Exception -> 0x0200 }
        r9 = 0;
        r14 = new org.apache.http.client.methods.HttpPost;	 Catch:{ UnsupportedEncodingException -> 0x0256, Exception -> 0x01e2, RuntimeException -> 0x01c7, IOException -> 0x01e8, all -> 0x023b }
        r0 = r32;
        r14.<init>(r0);	 Catch:{ UnsupportedEncodingException -> 0x0256, Exception -> 0x01e2, RuntimeException -> 0x01c7, IOException -> 0x01e8, all -> 0x023b }
        r26 = "Content-type";
        r28 = "text/html";
        r0 = r26;
        r1 = r28;
        r14.setHeader(r0, r1);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r28 = java.lang.System.currentTimeMillis();	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r30 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r28 = r28 / r30;
        r25 = java.lang.String.valueOf(r28);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r26 = "MD5";
        r28 = r23.getBytes();	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r0 = r26;
        r1 = r28;
        r11 = createHash(r0, r1);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r26 = "MD5";
        r28 = r25.getBytes();	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r0 = r26;
        r1 = r28;
        r26 = createHash(r0, r1);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r28 = 0;
        r29 = 16;
        r0 = r26;
        r1 = r28;
        r2 = r29;
        r15 = r0.substring(r1, r2);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r26 = "REQ-TIMESTAMP";
        r0 = r26;
        r1 = r25;
        r14.addHeader(r0, r1);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r26 = "REQ-AUTHKEY";
        r0 = r26;
        r14.addHeader(r0, r11);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r4 = 0;
        r0 = r23;
        r26 = encrypt(r15, r0);	 Catch:{ Exception -> 0x01ba, UnsupportedEncodingException -> 0x01c0, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r28 = 4;
        r0 = r26;
        r1 = r28;
        r4 = android.util.Base64.encode(r0, r1);	 Catch:{ Exception -> 0x01ba, UnsupportedEncodingException -> 0x01c0, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
    L_0x0089:
        r24 = new java.lang.String;	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r0 = r24;
        r0.<init>(r4);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r26 = logger;	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r28 = new java.lang.StringBuilder;	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r29 = "[ActiveUserNetwork][processNetworkTask] send data : ";
        r28.<init>(r29);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r0 = r28;
        r1 = r24;
        r28 = r0.append(r1);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r28 = r28.toString();	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r0 = r26;
        r1 = r28;
        r0.d(r1);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r10 = new org.apache.http.entity.StringEntity;	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r26 = "UTF-8";
        r0 = r24;
        r1 = r26;
        r10.<init>(r0, r1);	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        r9 = r10;
        r13 = r14;
    L_0x00b9:
        r13.setEntity(r9);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r19 = r6.execute(r13);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r26 = r19.getStatusLine();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r26 = r26.getStatusCode();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r28 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r26;
        r1 = r28;
        if (r0 != r1) goto L_0x0123;
    L_0x00d0:
        r26 = "REQ-TIMESTAMP";
        r0 = r19;
        r1 = r26;
        r26 = r0.getFirstHeader(r1);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r18 = r26.getValue();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r26 = "REQ-AUTHKEY";
        r0 = r19;
        r1 = r26;
        r26 = r0.getFirstHeader(r1);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r16 = r26.getValue();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r26 = logger;	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r28 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r29 = "[ActiveUserNetwork][processNetworkTask] recvTimeStamp=";
        r28.<init>(r29);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r0 = r28;
        r1 = r18;
        r28 = r0.append(r1);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r29 = ", recvHash=";
        r28 = r28.append(r29);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r0 = r28;
        r1 = r16;
        r28 = r0.append(r1);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r28 = r28.toString();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r0 = r26;
        r1 = r28;
        r0.d(r1);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r20 = r19.getEntity();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        if (r20 == 0) goto L_0x0123;
    L_0x011c:
        r21 = org.apache.http.util.EntityUtils.toString(r20);	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        r20.consumeContent();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
    L_0x0123:
        r26 = r6.getConnectionManager();	 Catch:{ all -> 0x0259 }
        r26.shutdown();	 Catch:{ all -> 0x0259 }
        r5 = r6;
        r22 = r21;
    L_0x012d:
        if (r22 == 0) goto L_0x0260;
    L_0x012f:
        r26 = "MD5";
        r28 = r18.getBytes();	 Catch:{ Exception -> 0x0227, all -> 0x025c }
        r0 = r26;
        r1 = r28;
        r26 = createHash(r0, r1);	 Catch:{ Exception -> 0x0227, all -> 0x025c }
        r28 = 0;
        r29 = 16;
        r0 = r26;
        r1 = r28;
        r2 = r29;
        r15 = r0.substring(r1, r2);	 Catch:{ Exception -> 0x0227, all -> 0x025c }
        r7 = 0;
        r26 = 4;
        r0 = r22;
        r1 = r26;
        r7 = android.util.Base64.decode(r0, r1);	 Catch:{ Exception -> 0x0227, all -> 0x025c }
        r21 = new java.lang.String;	 Catch:{ Exception -> 0x0227, all -> 0x025c }
        r26 = decrypt(r15, r7);	 Catch:{ Exception -> 0x0227, all -> 0x025c }
        r0 = r21;
        r1 = r26;
        r0.<init>(r1);	 Catch:{ Exception -> 0x0227, all -> 0x025c }
        r26 = logger;	 Catch:{ Exception -> 0x0239 }
        r28 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0239 }
        r29 = "[ActiveUserNetwork][processNetworkTask] gateway responseStr (";
        r28.<init>(r29);	 Catch:{ Exception -> 0x0239 }
        r0 = r28;
        r1 = r33;
        r28 = r0.append(r1);	 Catch:{ Exception -> 0x0239 }
        r29 = ") : ";
        r28 = r28.append(r29);	 Catch:{ Exception -> 0x0239 }
        r0 = r28;
        r1 = r21;
        r28 = r0.append(r1);	 Catch:{ Exception -> 0x0239 }
        r28 = r28.toString();	 Catch:{ Exception -> 0x0239 }
        r0 = r26;
        r1 = r28;
        r0.d(r1);	 Catch:{ Exception -> 0x0239 }
        r26 = "MD5";
        r28 = r21.getBytes();	 Catch:{ Exception -> 0x0239 }
        r0 = r26;
        r1 = r28;
        r17 = createHash(r0, r1);	 Catch:{ Exception -> 0x0239 }
        r0 = r17;
        r1 = r16;
        r26 = r0.equals(r1);	 Catch:{ Exception -> 0x0239 }
        if (r26 == 0) goto L_0x0223;
    L_0x01a5:
        r26 = logger;	 Catch:{ Exception -> 0x0239 }
        r28 = "[ActiveUserNetwork][processNetworkTask] gateway response hash equals true";
        r0 = r26;
        r1 = r28;
        r0.d(r1);	 Catch:{ Exception -> 0x0239 }
        r0 = r33;
        r1 = r21;
        r26 = parseReceiveData(r0, r1);	 Catch:{ Exception -> 0x0239 }
        goto L_0x0018;
    L_0x01ba:
        r8 = move-exception;
        r8.printStackTrace();	 Catch:{ UnsupportedEncodingException -> 0x01c0, Exception -> 0x0253, RuntimeException -> 0x024e, IOException -> 0x0247, all -> 0x023e }
        goto L_0x0089;
    L_0x01c0:
        r8 = move-exception;
        r13 = r14;
    L_0x01c2:
        r8.printStackTrace();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        goto L_0x00b9;
    L_0x01c7:
        r8 = move-exception;
        r5 = r6;
    L_0x01c9:
        r26 = logger;	 Catch:{ all -> 0x0217 }
        r28 = "[ActiveUserNetwork][processNetworkTask]";
        r0 = r26;
        r1 = r28;
        r0.d(r1, r8);	 Catch:{ all -> 0x0217 }
        r13.abort();	 Catch:{ all -> 0x0217 }
        r26 = r5.getConnectionManager();	 Catch:{ all -> 0x0220 }
        r26.shutdown();	 Catch:{ all -> 0x0220 }
        r22 = r21;
        goto L_0x012d;
    L_0x01e2:
        r8 = move-exception;
    L_0x01e3:
        r8.printStackTrace();	 Catch:{ RuntimeException -> 0x01c7, IOException -> 0x01e8, Exception -> 0x0242, all -> 0x023b }
        goto L_0x00b9;
    L_0x01e8:
        r8 = move-exception;
        r5 = r6;
    L_0x01ea:
        r26 = logger;	 Catch:{ all -> 0x0217 }
        r28 = "[ActiveUserNetwork][processNetworkTask]";
        r0 = r26;
        r1 = r28;
        r0.d(r1, r8);	 Catch:{ all -> 0x0217 }
        r26 = r5.getConnectionManager();	 Catch:{ all -> 0x0220 }
        r26.shutdown();	 Catch:{ all -> 0x0220 }
        r22 = r21;
        goto L_0x012d;
    L_0x0200:
        r8 = move-exception;
    L_0x0201:
        r26 = logger;	 Catch:{ all -> 0x0217 }
        r28 = "[ActiveUserNetwork][processNetworkTask]";
        r0 = r26;
        r1 = r28;
        r0.d(r1, r8);	 Catch:{ all -> 0x0217 }
        r26 = r5.getConnectionManager();	 Catch:{ all -> 0x0220 }
        r26.shutdown();	 Catch:{ all -> 0x0220 }
        r22 = r21;
        goto L_0x012d;
    L_0x0217:
        r26 = move-exception;
    L_0x0218:
        r28 = r5.getConnectionManager();	 Catch:{ all -> 0x0220 }
        r28.shutdown();	 Catch:{ all -> 0x0220 }
        throw r26;	 Catch:{ all -> 0x0220 }
    L_0x0220:
        r26 = move-exception;
    L_0x0221:
        monitor-exit(r27);
        throw r26;
    L_0x0223:
        r26 = 0;
        goto L_0x0018;
    L_0x0227:
        r8 = move-exception;
        r21 = r22;
    L_0x022a:
        r26 = logger;	 Catch:{ all -> 0x0220 }
        r28 = "[ActiveUserNetwork][processNetworkTask]";
        r0 = r26;
        r1 = r28;
        r0.d(r1, r8);	 Catch:{ all -> 0x0220 }
    L_0x0235:
        r26 = 0;
        goto L_0x0018;
    L_0x0239:
        r8 = move-exception;
        goto L_0x022a;
    L_0x023b:
        r26 = move-exception;
        r5 = r6;
        goto L_0x0218;
    L_0x023e:
        r26 = move-exception;
        r5 = r6;
        r13 = r14;
        goto L_0x0218;
    L_0x0242:
        r8 = move-exception;
        r5 = r6;
        goto L_0x0201;
    L_0x0245:
        r8 = move-exception;
        goto L_0x01ea;
    L_0x0247:
        r8 = move-exception;
        r5 = r6;
        r13 = r14;
        goto L_0x01ea;
    L_0x024b:
        r8 = move-exception;
        goto L_0x01c9;
    L_0x024e:
        r8 = move-exception;
        r5 = r6;
        r13 = r14;
        goto L_0x01c9;
    L_0x0253:
        r8 = move-exception;
        r13 = r14;
        goto L_0x01e3;
    L_0x0256:
        r8 = move-exception;
        goto L_0x01c2;
    L_0x0259:
        r26 = move-exception;
        r5 = r6;
        goto L_0x0221;
    L_0x025c:
        r26 = move-exception;
        r21 = r22;
        goto L_0x0221;
    L_0x0260:
        r21 = r22;
        goto L_0x0235;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.activeuser.ActiveUserNetwork.processNetworkTask(java.lang.String, java.lang.String):java.lang.Object");
    }

    public static String makeSendData(String requestCmd) {
        logger.d("isEnabledDownload : " + isEnabledDownload);
        JSONObject dataMap = new JSONObject();
        try {
            dataMap.put(PeppermintConstant.JSON_KEY_APP_ID, ActiveUserData.get(DATA_INDEX.APP_ID));
            long utcCurrentTime = System.currentTimeMillis();
            int offset = TimeZone.getDefault().getOffset(System.currentTimeMillis());
            long currtentTime = utcCurrentTime + ((long) offset);
            String did;
            String jsonStr;
            if (requestCmd.equals(REQUEST_DOWNLOAD)) {
                dataMap.put("api", REQUEST_DOWNLOAD);
                boolean isEnable = false;
                if (isEnabledDownload) {
                    isEnable = true;
                }
                if (!isEnable) {
                    return null;
                }
                JSONObject versionList;
                if (!"Y".equals(ActiveUserProperties.getProperty(ActiveUserProperties.SEND_REFERRER_ON_DOWNLOADCHECK_PROPERTY))) {
                    dataMap.put(REQUEST_REFERRER, ActiveUserProperties.getProperty(REQUEST_REFERRER));
                }
                dataMap.put("hacking", WrapperUtility.IsCracked() ? a.e : a.d);
                dataMap.put("appversion", ActiveUserData.get(DATA_INDEX.APP_VERSION));
                dataMap.put("platform", "A");
                dataMap.put("mac", ActiveUserData.get(DATA_INDEX.MAC_ADDR));
                dataMap.put("mdn", ActiveUserData.get(DATA_INDEX.LINE_NUMBER));
                dataMap.put("imei", ActiveUserData.get(DATA_INDEX.DEVICE_ID));
                dataMap.put("android_id", ActiveUserData.get(DATA_INDEX.ANDROID_ID));
                dataMap.put("osversion", ActiveUserData.get(DATA_INDEX.OS_VERSION));
                dataMap.put("device", ActiveUserData.get(DATA_INDEX.DEVICE_MODEL));
                dataMap.put(PeppermintConstant.JSON_KEY_COUNTRY, ActiveUserData.get(DATA_INDEX.COUNTRY));
                dataMap.put(PeppermintConstant.JSON_KEY_LANGUAGE, ActiveUserData.get(DATA_INDEX.LANGUAGE));
                dataMap.put("bluestacks", ActiveUserData.get(DATA_INDEX.ISBLUESTACKS));
                did = ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY);
                if (!TextUtils.isEmpty(did)) {
                    dataMap.put(PeppermintConstant.JSON_KEY_DID, did);
                }
                try {
                    JSONObject jSONObject = new JSONObject(ActiveUserProperties.getProperty(ActiveUserProperties.MODULEVERSIONCHECK_DATA_PROPERTY));
                } catch (Exception e) {
                    versionList = null;
                    logger.d("versionList data getProperty failed : " + e.toString());
                }
                dataMap.put("versions", versionList);
                dataMap.put("advertising_id", ActiveUserData.get(DATA_INDEX.ADVERTISING_ID));
                dataMap.put("is_limit_ad_tracking", ActiveUserData.get(DATA_INDEX.IS_LIMIT_AD_TRACKING));
                jsonStr = dataMap.toString();
                logger.d(InstallService.TAG, "[ActiveUserNetwork][makeSendData]" + requestCmd + " " + jsonStr);
                return jsonStr;
            }
            if (requestCmd.equals(REQUEST_REFERRER)) {
                dataMap.put("api", REQUEST_REFERRER);
                dataMap.put("appversion", ActiveUserData.get(DATA_INDEX.APP_VERSION));
                dataMap.put(REQUEST_REFERRER, ActiveUserProperties.getProperty(REQUEST_REFERRER));
            } else {
                if (requestCmd.equals(REQUEST_SESSION)) {
                    JSONArray sessions;
                    dataMap.put("api", REQUEST_SESSION);
                    did = ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY);
                    if (!TextUtils.isEmpty(did)) {
                        dataMap.put(PeppermintConstant.JSON_KEY_DID, did);
                        String vid = ActiveUserData.get(DATA_INDEX.VID);
                        if (!TextUtils.isEmpty(vid)) {
                            dataMap.put("vid", vid);
                        }
                    }
                    try {
                        JSONArray jSONArray = new JSONArray(ActiveUserProperties.getProperty(ActiveUserProperties.SESSION_DATA_PROPERTY));
                    } catch (Exception e2) {
                        sessions = null;
                        logger.d("session data getProperty failed: " + e2.toString());
                    }
                    dataMap.put("sessions", sessions);
                } else {
                    if (requestCmd.equals(REQUEST_GETDID)) {
                        dataMap.put("api", REQUEST_GETDID);
                        dataMap.put("platform", "A");
                        dataMap.put("mac", ActiveUserData.get(DATA_INDEX.MAC_ADDR));
                        dataMap.put("mdn", ActiveUserData.get(DATA_INDEX.LINE_NUMBER));
                        dataMap.put("imei", ActiveUserData.get(DATA_INDEX.DEVICE_ID));
                        dataMap.put("android_id", ActiveUserData.get(DATA_INDEX.ANDROID_ID));
                        dataMap.put("osversion", ActiveUserData.get(DATA_INDEX.OS_VERSION));
                        dataMap.put("device", ActiveUserData.get(DATA_INDEX.DEVICE_MODEL));
                        dataMap.put("hacking", WrapperUtility.IsCracked() ? a.e : a.d);
                        dataMap.put("advertising_id", ActiveUserData.get(DATA_INDEX.ADVERTISING_ID));
                        dataMap.put("is_limit_ad_tracking", ActiveUserData.get(DATA_INDEX.IS_LIMIT_AD_TRACKING));
                    } else {
                        if (requestCmd.equals(REQUEST_UPDATEDID)) {
                            dataMap.put("api", REQUEST_UPDATEDID);
                            did = ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY);
                            if (!TextUtils.isEmpty(did)) {
                                dataMap.put(PeppermintConstant.JSON_KEY_DID, did);
                            }
                            dataMap.put("platform", "A");
                            dataMap.put("mac", ActiveUserData.get(DATA_INDEX.MAC_ADDR));
                            dataMap.put("mdn", ActiveUserData.get(DATA_INDEX.LINE_NUMBER));
                            dataMap.put("imei", ActiveUserData.get(DATA_INDEX.DEVICE_ID));
                            dataMap.put("android_id", ActiveUserData.get(DATA_INDEX.ANDROID_ID));
                            dataMap.put("osversion", ActiveUserData.get(DATA_INDEX.OS_VERSION));
                            dataMap.put("device", ActiveUserData.get(DATA_INDEX.DEVICE_MODEL));
                            dataMap.put("hacking", WrapperUtility.IsCracked() ? a.e : a.d);
                            dataMap.put("advertising_id", ActiveUserData.get(DATA_INDEX.ADVERTISING_ID));
                            dataMap.put("is_limit_ad_tracking", ActiveUserData.get(DATA_INDEX.IS_LIMIT_AD_TRACKING));
                        } else {
                            if (requestCmd.equals(REQUEST_CONFIGURATION)) {
                                dataMap.put("api", REQUEST_CONFIGURATION);
                                dataMap.put("appversion", ActiveUserData.get(DATA_INDEX.APP_VERSION));
                                dataMap.put("platform", "A");
                                did = ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY);
                                if (!TextUtils.isEmpty(did)) {
                                    dataMap.put(PeppermintConstant.JSON_KEY_DID, did);
                                }
                                dataMap.put("mcc", ActiveUserData.get(DATA_INDEX.MCC_CODE));
                                dataMap.put("mnc", ActiveUserData.get(DATA_INDEX.MNC_CODE));
                                dataMap.put(PeppermintConstant.JSON_KEY_COUNTRY, ActiveUserData.get(DATA_INDEX.COUNTRY));
                                dataMap.put(PeppermintConstant.JSON_KEY_LANGUAGE, ActiveUserData.get(DATA_INDEX.LANGUAGE));
                                dataMap.put("server_id", ActiveUserData.get(DATA_INDEX.SERVER_ID));
                                dataMap.put("mdn", ActiveUserData.get(DATA_INDEX.LINE_NUMBER));
                                dataMap.put("timestamp", currtentTime / 1000);
                                dataMap.put("timezone_offset", offset / UserAgreeNotifier.USER_AGREE_PRIVACY_SUCCESS);
                                String agreement_version = ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY);
                                if (TextUtils.isEmpty(agreement_version)) {
                                    dataMap.put(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY, 0);
                                } else {
                                    try {
                                        dataMap.put(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY, Integer.valueOf(agreement_version));
                                    } catch (Exception e3) {
                                        dataMap.put(ActiveUserProperties.AGREEMENT_VERSION_PROPERTY, 0);
                                    }
                                }
                                String agreement_sms = ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_SMS_PROPERTY);
                                if (TextUtils.isEmpty(agreement_sms)) {
                                    dataMap.put("agreement_sms", JSONObject.NULL);
                                } else {
                                    try {
                                        dataMap.put("agreement_sms", Integer.valueOf(agreement_sms));
                                    } catch (Exception e22) {
                                        e22.printStackTrace();
                                        dataMap.put("agreement_sms", JSONObject.NULL);
                                    }
                                }
                            } else {
                                if (requestCmd.equals(REQUEST_AGREEMENT)) {
                                    dataMap.put("api", REQUEST_AGREEMENT);
                                    dataMap.put("appversion", ActiveUserData.get(DATA_INDEX.APP_VERSION));
                                    dataMap.put("platform", "A");
                                    did = ActiveUserProperties.getProperty(ActiveUserProperties.DID_PROPERTY);
                                    if (!TextUtils.isEmpty(did)) {
                                        dataMap.put(PeppermintConstant.JSON_KEY_DID, did);
                                    }
                                    dataMap.put(PeppermintConstant.JSON_KEY_COUNTRY, ActiveUserData.get(DATA_INDEX.COUNTRY));
                                    dataMap.put(PeppermintConstant.JSON_KEY_LANGUAGE, ActiveUserData.get(DATA_INDEX.LANGUAGE));
                                    String agreementData = ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_VERSION_DATA_PROPERTY);
                                    if (!TextUtils.isEmpty(agreementData)) {
                                        dataMap.put(REQUEST_AGREEMENT, agreementData);
                                    }
                                    String agreement_exData = ActiveUserProperties.getProperty(ActiveUserProperties.AGREEMENT_EX_SCHEME_DATA_PROPERTY);
                                    if (!TextUtils.isEmpty(agreement_exData)) {
                                        dataMap.put("agreement_ex", agreement_exData);
                                    }
                                    dataMap.put("mcc", ActiveUserData.get(DATA_INDEX.MCC_CODE));
                                }
                            }
                        }
                    }
                }
            }
            jsonStr = dataMap.toString();
            logger.d(InstallService.TAG, "[ActiveUserNetwork][makeSendData]" + requestCmd + " " + jsonStr);
            return jsonStr;
        } catch (Exception e222) {
            e222.printStackTrace();
            dataMap = new JSONObject();
        }
    }

    public static String createDateAsGMT() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance(new SimpleTimeZone(0, "GMT"));
        formatter.setCalendar(cal);
        return formatter.format(cal.getTime());
    }

    public static Object parseReceiveData(String requestCmd, String msg) throws JSONException {
        logger.d(InstallService.TAG, "[ActiveUserNetwork][parseReceivedData] " + requestCmd + " : " + msg);
        JSONObject config;
        if (requestCmd.equals(REQUEST_DOWNLOAD)) {
            ReceivedDownloadData recvData = new ReceivedDownloadData();
            try {
                JSONObject jObj = new JSONObject(msg);
                recvData.errno = jObj.getInt("errno");
                recvData.error = jObj.optString(GCMConstants.EXTRA_ERROR);
                recvData.did = jObj.optString(PeppermintConstant.JSON_KEY_DID);
                config = jObj.optJSONObject("config");
                if (config == null) {
                    return recvData;
                }
                int optInt;
                recvData.session_max_num = config.optInt("session_max_num", 1) < 1 ? config.optInt("session_max_num", 1) : 1;
                if (config.optInt("session_max_time", 0) < Gateway.SEND_SESSION_LIMIT_TIME) {
                    optInt = config.optInt("session_max_time", 0);
                } else {
                    optInt = 0;
                }
                recvData.session_max_time = optInt;
                return recvData;
            } catch (JSONException e) {
                throw e;
            }
        }
        if (requestCmd.equals(REQUEST_REFERRER)) {
            Received data = new ReceivedReferrerData();
            try {
                jObj = new JSONObject(msg);
                data.errno = jObj.getInt("errno");
                data.error = jObj.optString(GCMConstants.EXTRA_ERROR);
                return data;
            } catch (JSONException e2) {
                throw e2;
            }
        }
        if (requestCmd.equals(REQUEST_SESSION)) {
            Received recvSessionData = new ReceivedSessionData();
            try {
                jObj = new JSONObject(msg);
                recvSessionData.errno = jObj.getInt("errno");
                recvSessionData.error = jObj.optString(GCMConstants.EXTRA_ERROR);
                config = jObj.optJSONObject("config");
                if (config != null) {
                    recvSessionData.session_max_num = config.optInt("session_max_num", 1) < 1 ? config.optInt("session_max_num", 1) : 1;
                    recvSessionData.session_max_time = config.optInt("session_max_time", 0) < Gateway.SEND_SESSION_LIMIT_TIME ? config.optInt("session_max_time", 0) : 0;
                }
                return recvSessionData;
            } catch (JSONException e22) {
                throw e22;
            }
        }
        if (!requestCmd.equals(REQUEST_GETDID)) {
            if (!requestCmd.equals(REQUEST_UPDATEDID)) {
                if (requestCmd.equals(REQUEST_CONFIGURATION)) {
                    Received recvConfigurationData = new ReceivedConfigurationData();
                    try {
                        jObj = new JSONObject(msg);
                        JSONObject agreementObj = new JSONObject();
                        JSONObject agreement_exObj = new JSONObject();
                        JSONObject noticeObj = new JSONObject();
                        recvConfigurationData.errno = jObj.getInt("errno");
                        recvConfigurationData.error = jObj.optString(GCMConstants.EXTRA_ERROR);
                        agreementObj = jObj.optJSONObject(REQUEST_AGREEMENT);
                        agreement_exObj = jObj.optJSONObject("agreement_ex");
                        noticeObj = jObj.optJSONObject(MercuryDefine.TYPE_NOTICE);
                        if (agreementObj != null) {
                            recvConfigurationData.agreement_show = agreementObj.optInt("show");
                            recvConfigurationData.agreement_version = agreementObj.optInt(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION);
                            recvConfigurationData.agreement_url = agreementObj.optString(a.g);
                            recvConfigurationData.agreement_review_url = agreementObj.optString("review_url");
                        }
                        if (agreement_exObj == null) {
                            recvConfigurationData.agreement_ex_show = 0;
                        } else {
                            recvConfigurationData.agreement_ex_show = 1;
                            recvConfigurationData.agreement_ex_url = agreement_exObj.optString(a.g);
                        }
                        if (noticeObj == null) {
                            recvConfigurationData.notice_show = 0;
                        } else {
                            recvConfigurationData.notice_show = 1;
                            recvConfigurationData.notice_title = noticeObj.optString("title");
                            recvConfigurationData.notice_message = noticeObj.optString(a.a);
                            recvConfigurationData.notice_button = noticeObj.optString(MercuryDefine.ACTION_BUTTON);
                            recvConfigurationData.notice_action = noticeObj.optInt("action");
                            recvConfigurationData.notice_url = noticeObj.optString(a.g);
                        }
                        return recvConfigurationData;
                    } catch (JSONException e222) {
                        throw e222;
                    }
                }
                if (!requestCmd.equals(REQUEST_AGREEMENT)) {
                    return null;
                }
                Received recvAgreementData = new ReceivedAgreementData();
                try {
                    jObj = new JSONObject(msg);
                    recvAgreementData.errno = jObj.getInt("errno");
                    recvAgreementData.error = jObj.optString(GCMConstants.EXTRA_ERROR);
                    return recvAgreementData;
                } catch (JSONException e2222) {
                    throw e2222;
                }
            }
        }
        Received recvDIDData = new ReceivedDIDData();
        try {
            jObj = new JSONObject(msg);
            recvDIDData.errno = jObj.getInt("errno");
            recvDIDData.error = jObj.optString(GCMConstants.EXTRA_ERROR);
            recvDIDData.did = jObj.optString(PeppermintConstant.JSON_KEY_DID);
            return recvDIDData;
        } catch (JSONException e22222) {
            throw e22222;
        }
    }

    public static SecretKeySpec creaetSecretKey(String key) {
        return new SecretKeySpec(key.getBytes(), cryptAlgorithm);
    }

    public static String createHash(String algorithm, byte[] data) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data);
        byte[] mdByte = md.digest();
        String mdStr = new String();
        if (mdByte != null) {
            for (int i = 0; i < mdByte.length; i++) {
                mdStr = new StringBuilder(String.valueOf(mdStr)).append(String.format("%02x", new Object[]{Byte.valueOf(mdByte[i])})).toString();
            }
        }
        return mdStr;
    }

    public static byte[] encrypt(String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ENC_FORMAT);
        cipher.init(1, creaetSecretKey(key), spec);
        return cipher.doFinal(data.getBytes());
    }

    public static byte[] decrypt(String key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ENC_FORMAT);
        cipher.init(2, creaetSecretKey(key), spec);
        return cipher.doFinal(data);
    }
}
