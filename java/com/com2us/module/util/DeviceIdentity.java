package com.com2us.module.util;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import com.com2us.module.crypt.Crypt;
import com.com2us.module.manager.Constants;
import com.com2us.module.manager.ModuleManager;
import com.com2us.peppermint.PeppermintConstant;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Properties;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import jp.co.cyberz.fox.a.a.i;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class DeviceIdentity {
    private static String DID = i.a;
    private static final String DID_PROPERTY = "did_data";
    private static final String ENC_FORMAT = "AES/CBC/PKCS7Padding";
    private static final String GATEWAY_LIVE_SERVER = "http://activeuser.com2us.net/gateway.php";
    private static final String GATEWAY_TEST_SERVER = "http://test.activeuser.com2us.net/gateway.php";
    private static final String PROPERTIES = "activeuser.props";
    private static String TARGET_PROPERTIES = PROPERTIES;
    private static String TARGET_SERVER = GATEWAY_LIVE_SERVER;
    private static final String cryptAlgorithm = "AES";
    private static final String cryptMode = "CBC";
    private static final String cryptPadding = "PKCS7Padding";
    private static Thread getDIDThread = null;
    private static boolean isTestServer = false;
    private static Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private static Properties prop = new Properties();
    private static AlgorithmParameterSpec spec = new IvParameterSpec(new byte[16]);

    class AnonymousClass1 implements Runnable {
        private final /* synthetic */ Context val$context;

        AnonymousClass1(Context context) {
            this.val$context = context;
        }

        public void run() {
            try {
                try {
                    JSONObject jObj = new JSONObject(DeviceIdentity.processNetworkTask(this.val$context));
                    if (jObj.getInt("errno") == 0) {
                        DeviceIdentity.DID = jObj.getString(PeppermintConstant.JSON_KEY_DID);
                        DeviceIdentity.setProperty(this.val$context, DeviceIdentity.DID_PROPERTY, DeviceIdentity.DID);
                        DeviceIdentity.storeProperties(this.val$context);
                    }
                } catch (JSONException e) {
                    DeviceIdentity.logger.d("getDID responseMsg JSONException : " + e.toString());
                }
            } catch (Exception e2) {
                DeviceIdentity.logger.d("getDID processNetworkTask Exception : " + e2.toString());
            }
        }
    }

    public static synchronized String getDID(Context context) {
        String dIDSynchronized;
        synchronized (DeviceIdentity.class) {
            dIDSynchronized = getDIDSynchronized(context, true);
        }
        return dIDSynchronized;
    }

    @Deprecated
    public static synchronized String getDID(Context context, boolean useTestServer) {
        String dIDSynchronized;
        synchronized (DeviceIdentity.class) {
            logger.d("getDID useTest - " + useTestServer);
            if (isTestServer != useTestServer) {
                isTestServer = useTestServer;
                TARGET_SERVER = useTestServer ? GATEWAY_TEST_SERVER : GATEWAY_LIVE_SERVER;
                TARGET_PROPERTIES = useTestServer ? "testactiveuser.props" : PROPERTIES;
                logger.setLogged(useTestServer);
                DID = i.a;
                removeProperty(DID_PROPERTY);
            }
            dIDSynchronized = getDIDSynchronized(context, true);
        }
        return dIDSynchronized;
    }

    public static synchronized String getDIDSynchronized(Context context, boolean sync) {
        String str;
        synchronized (DeviceIdentity.class) {
            logger.d("getDIDSynchronized sync - " + sync);
            if (TextUtils.isEmpty(DID)) {
                loadProperties(context);
                DID = getProperty(context, DID_PROPERTY);
                if (!TextUtils.isEmpty(DID)) {
                    str = DID;
                } else if (sync) {
                    try {
                        try {
                            JSONObject jObj = new JSONObject(processNetworkTask(context));
                            if (jObj.getInt("errno") == 0) {
                                DID = jObj.getString(PeppermintConstant.JSON_KEY_DID);
                                setProperty(context, DID_PROPERTY, DID);
                                storeProperties(context);
                            }
                            str = DID;
                        } catch (JSONException e) {
                            logger.d("getDID responseMsg JSONException : " + e.toString());
                            str = i.a;
                        }
                    } catch (Exception e2) {
                        logger.d("getDID processNetworkTask Exception : " + e2.toString());
                        str = i.a;
                    }
                } else if (getDIDThread == null || !getDIDThread.isAlive()) {
                    getDIDThread = new Thread(new AnonymousClass1(context));
                    getDIDThread.start();
                    str = i.a;
                } else {
                    str = i.a;
                }
            } else {
                str = DID;
            }
        }
        return str;
    }

    private static String getProperty(Context context, String name) {
        String value = prop.getProperty(name);
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        value = Crypt.byt2str(Crypt.Decrypt(Crypt.hexToByteArray(value), Secure.getString(context.getContentResolver(), "android_id").getBytes()));
        try {
            if (Long.parseLong(value) <= 0) {
                return i.a;
            }
            return value;
        } catch (Exception e) {
            logger.v(e.toString());
            value = i.a;
            removeProperty(DID_PROPERTY);
            storeProperties(context);
            return value;
        }
    }

    private static void setProperty(Context context, String name, String value) {
        logger.v("[DeviceIdentity][setProperty]name=" + name + ", value=" + value);
        if (TextUtils.isEmpty(value)) {
            prop.setProperty(name, i.a);
        } else {
            prop.setProperty(name, Crypt.byteArrayToHex(Crypt.Encrypt(Crypt.str2byt(value), Secure.getString(context.getContentResolver(), "android_id").getBytes())));
        }
    }

    private static void removeProperty(String name) {
        prop.remove(name);
    }

    private static void loadProperties(Context context) {
        prop = null;
        prop = new Properties();
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(TARGET_PROPERTIES);
            prop.load(fis);
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e2) {
            logger.v("[DeviceIdentity][loadProperties]" + e2.toString());
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e3) {
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e5) {
                }
            }
        } catch (Exception e6) {
            e6.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e7) {
                }
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e8) {
                }
            }
        }
        logger.v("[DeviceIdentity][loadProperties]" + prop.toString());
    }

    private static synchronized void storeProperties(Context context) {
        synchronized (DeviceIdentity.class) {
            logger.v("[DeviceIdentity][storeProperties]" + prop.toString());
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(TARGET_PROPERTIES, 0);
                prop.store(fos, null);
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                    }
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e3) {
                    }
                }
            } catch (IOException e4) {
                e4.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e5) {
                    }
                }
            } catch (Exception e6) {
                e6.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e7) {
                    }
                }
            } catch (Throwable th) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e8) {
                    }
                }
            }
        }
    }

    private static synchronized String processNetworkTask(Context context) {
        String str;
        HttpPost httpPost;
        Exception e;
        HttpResponse response;
        HttpEntity responseEntity;
        String responseStr;
        String str2;
        byte[] bytes;
        Throwable th;
        synchronized (DeviceIdentity.class) {
            String responseStr2 = null;
            HttpPost httpPost2 = null;
            HttpClient client = null;
            String recvTimeStamp = null;
            String recvHash = null;
            HttpParams httpClientParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpClientParams, 10000);
            HttpConnectionParams.setSoTimeout(httpClientParams, 10000);
            String sendData = makeSendData(context);
            if (TextUtils.isEmpty(sendData)) {
                str = null;
            } else {
                try {
                    HttpClient client2 = new DefaultHttpClient(httpClientParams);
                    StringEntity entity = null;
                    try {
                        httpPost = new HttpPost(TARGET_SERVER);
                    } catch (UnsupportedEncodingException e2) {
                        e = e2;
                        try {
                            logger.d("[DeviceIdentity][processNetworkTask]", e);
                            httpPost2.setEntity(entity);
                            response = client2.execute(httpPost2);
                            if (response.getStatusLine().getStatusCode() == 200) {
                                recvTimeStamp = response.getFirstHeader(Gateway.HTTP_REQ_TIMESTAMP).getValue();
                                recvHash = response.getFirstHeader(Gateway.HTTP_REQ_AUTHKEY).getValue();
                                logger.d("[DeviceIdentity][processNetworkTask] recvTimeStamp=" + recvTimeStamp + ", recvHash=" + recvHash);
                                responseEntity = response.getEntity();
                                if (responseEntity != null) {
                                    responseStr2 = EntityUtils.toString(responseEntity);
                                    responseEntity.consumeContent();
                                }
                            }
                            client2.getConnectionManager().shutdown();
                            client = client2;
                            responseStr = responseStr2;
                        } catch (RuntimeException e3) {
                            e = e3;
                            client = client2;
                            try {
                                logger.d("[DeviceIdentity][processNetworkTask]", e);
                                httpPost2.abort();
                                client.getConnectionManager().shutdown();
                                responseStr = null;
                                if (responseStr != null) {
                                } else {
                                    try {
                                        str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                                        try {
                                            logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                                            bytes = str2.getBytes();
                                            if (createHash("MD5", bytes).equals(recvHash)) {
                                                str = null;
                                            } else {
                                                logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                                                str = str2;
                                            }
                                        } catch (Exception e4) {
                                            e = e4;
                                            logger.d("[DeviceIdentity][processNetworkTask]", e);
                                            str = null;
                                            return str;
                                        }
                                    } catch (Exception e5) {
                                        e = e5;
                                        responseStr2 = responseStr;
                                        logger.d("[DeviceIdentity][processNetworkTask]", e);
                                        str = null;
                                        return str;
                                    } catch (Throwable th2) {
                                        th = th2;
                                        responseStr2 = responseStr;
                                        throw th;
                                    }
                                    return str;
                                }
                                str = null;
                                return str;
                            } catch (Throwable th3) {
                                th = th3;
                                throw th;
                            }
                        } catch (IOException e6) {
                            e = e6;
                            client = client2;
                            logger.d("[DeviceIdentity][processNetworkTask]", e);
                            client.getConnectionManager().shutdown();
                            responseStr = null;
                            if (responseStr != null) {
                                str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                                logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                                bytes = str2.getBytes();
                                if (createHash("MD5", bytes).equals(recvHash)) {
                                    logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                                    str = str2;
                                } else {
                                    str = null;
                                }
                                return str;
                            }
                            str = null;
                            return str;
                        } catch (Throwable th4) {
                            th = th4;
                            client = client2;
                            client.getConnectionManager().shutdown();
                            throw th;
                        }
                        if (responseStr != null) {
                        } else {
                            str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                            logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                            bytes = str2.getBytes();
                            if (createHash("MD5", bytes).equals(recvHash)) {
                                str = null;
                            } else {
                                logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                                str = str2;
                            }
                            return str;
                        }
                        str = null;
                        return str;
                    }
                    try {
                        httpPost.setHeader("Content-type", "text/html");
                        String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
                        String hash = createHash("MD5", sendData.getBytes());
                        String key = createHash("MD5", timestamp.getBytes()).substring(0, 16);
                        httpPost.addHeader(Gateway.HTTP_REQ_TIMESTAMP, timestamp);
                        httpPost.addHeader(Gateway.HTTP_REQ_AUTHKEY, hash);
                        byte[] base64Data = null;
                        try {
                            base64Data = Base64.encode(encrypt(key, sendData), 4);
                        } catch (Exception e7) {
                            logger.d("[DeviceIdentity][processNetworkTask]", e7);
                        }
                        logger.d("[DeviceIdentity][processNetworkTask] send data : " + new String(base64Data));
                        entity = new StringEntity(new String(base64Data), "UTF-8");
                        httpPost2 = httpPost;
                    } catch (UnsupportedEncodingException e8) {
                        e7 = e8;
                        httpPost2 = httpPost;
                        logger.d("[DeviceIdentity][processNetworkTask]", e7);
                        httpPost2.setEntity(entity);
                        response = client2.execute(httpPost2);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            recvTimeStamp = response.getFirstHeader(Gateway.HTTP_REQ_TIMESTAMP).getValue();
                            recvHash = response.getFirstHeader(Gateway.HTTP_REQ_AUTHKEY).getValue();
                            logger.d("[DeviceIdentity][processNetworkTask] recvTimeStamp=" + recvTimeStamp + ", recvHash=" + recvHash);
                            responseEntity = response.getEntity();
                            if (responseEntity != null) {
                                responseStr2 = EntityUtils.toString(responseEntity);
                                responseEntity.consumeContent();
                            }
                        }
                        client2.getConnectionManager().shutdown();
                        client = client2;
                        responseStr = responseStr2;
                        if (responseStr != null) {
                            str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                            logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                            bytes = str2.getBytes();
                            if (createHash("MD5", bytes).equals(recvHash)) {
                                logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                                str = str2;
                            } else {
                                str = null;
                            }
                            return str;
                        }
                        str = null;
                        return str;
                    } catch (RuntimeException e9) {
                        e7 = e9;
                        client = client2;
                        httpPost2 = httpPost;
                        logger.d("[DeviceIdentity][processNetworkTask]", e7);
                        httpPost2.abort();
                        client.getConnectionManager().shutdown();
                        responseStr = null;
                        if (responseStr != null) {
                        } else {
                            str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                            logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                            bytes = str2.getBytes();
                            if (createHash("MD5", bytes).equals(recvHash)) {
                                str = null;
                            } else {
                                logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                                str = str2;
                            }
                            return str;
                        }
                        str = null;
                        return str;
                    } catch (IOException e10) {
                        e7 = e10;
                        client = client2;
                        httpPost2 = httpPost;
                        logger.d("[DeviceIdentity][processNetworkTask]", e7);
                        client.getConnectionManager().shutdown();
                        responseStr = null;
                        if (responseStr != null) {
                            str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                            logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                            bytes = str2.getBytes();
                            if (createHash("MD5", bytes).equals(recvHash)) {
                                logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                                str = str2;
                            } else {
                                str = null;
                            }
                            return str;
                        }
                        str = null;
                        return str;
                    } catch (Throwable th5) {
                        th = th5;
                        client = client2;
                        httpPost2 = httpPost;
                        client.getConnectionManager().shutdown();
                        throw th;
                    }
                    httpPost2.setEntity(entity);
                    response = client2.execute(httpPost2);
                    if (response.getStatusLine().getStatusCode() == 200) {
                        recvTimeStamp = response.getFirstHeader(Gateway.HTTP_REQ_TIMESTAMP).getValue();
                        recvHash = response.getFirstHeader(Gateway.HTTP_REQ_AUTHKEY).getValue();
                        logger.d("[DeviceIdentity][processNetworkTask] recvTimeStamp=" + recvTimeStamp + ", recvHash=" + recvHash);
                        responseEntity = response.getEntity();
                        if (responseEntity != null) {
                            responseStr2 = EntityUtils.toString(responseEntity);
                            responseEntity.consumeContent();
                        }
                    }
                    try {
                        client2.getConnectionManager().shutdown();
                        client = client2;
                        responseStr = responseStr2;
                    } catch (Throwable th6) {
                        th = th6;
                        client = client2;
                        throw th;
                    }
                } catch (RuntimeException e11) {
                    e7 = e11;
                    logger.d("[DeviceIdentity][processNetworkTask]", e7);
                    httpPost2.abort();
                    client.getConnectionManager().shutdown();
                    responseStr = null;
                    if (responseStr != null) {
                    } else {
                        str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                        logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                        bytes = str2.getBytes();
                        if (createHash("MD5", bytes).equals(recvHash)) {
                            str = null;
                        } else {
                            logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                            str = str2;
                        }
                        return str;
                    }
                    str = null;
                    return str;
                } catch (IOException e12) {
                    e7 = e12;
                    logger.d("[DeviceIdentity][processNetworkTask]", e7);
                    client.getConnectionManager().shutdown();
                    responseStr = null;
                    if (responseStr != null) {
                        str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                        logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                        bytes = str2.getBytes();
                        if (createHash("MD5", bytes).equals(recvHash)) {
                            logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                            str = str2;
                        } else {
                            str = null;
                        }
                        return str;
                    }
                    str = null;
                    return str;
                }
                if (responseStr != null) {
                    str2 = new String(decrypt(createHash("MD5", recvTimeStamp.getBytes()).substring(0, 16), Base64.decode(responseStr, 4)));
                    logger.d("[DeviceIdentity][processNetworkTask] gateway responseStr (getDID) : " + str2);
                    bytes = str2.getBytes();
                    if (createHash("MD5", bytes).equals(recvHash)) {
                        logger.d("[DeviceIdentity][processNetworkTask] gateway response hash equals true");
                        str = str2;
                    } else {
                        str = null;
                    }
                } else {
                    str = null;
                }
            }
        }
    }

    private static String makeSendData(Context context) {
        JSONObject dataMap = new JSONObject();
        TelephonyManager teleManager = (TelephonyManager) context.getSystemService("phone");
        Object appid = ModuleManager.getInstance().getAppIdForIdentity();
        String str = i.a;
        String imei = i.a;
        try {
            str = teleManager.getLine1Number();
            imei = teleManager.getDeviceId();
        } catch (Exception e) {
            e.toString();
        }
        try {
            String id = i.a;
            try {
                Method mdthod_getAdvertisingIdInfo = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient").getMethod("getAdvertisingIdInfo", new Class[]{Context.class});
                Class<?> cls_Info = Class.forName("com.google.android.gms.ads.identifier.AdvertisingIdClient$Info");
                Object obj_Info = mdthod_getAdvertisingIdInfo.invoke(null, new Object[]{context});
                Method mdthod_getId = cls_Info.getMethod("getId", new Class[0]);
                id = (String) mdthod_getId.invoke(obj_Info, new Object[0]);
                boolean isLAT = ((Boolean) cls_Info.getMethod("isLimitAdTrackingEnabled", new Class[0]).invoke(obj_Info, new Object[0])).booleanValue();
                dataMap.put("advertising_id", id);
                dataMap.put("is_limit_ad_tracking", isLAT);
            } catch (Exception e2) {
                logger.d("[DeviceIdentity] not found AdvertisingIdClient");
                dataMap.put("advertising_id", null);
                dataMap.put("is_limit_ad_tracking", null);
            }
            String str2 = PeppermintConstant.JSON_KEY_APP_ID;
            if (TextUtils.isEmpty(appid)) {
                appid = context.getPackageName();
            }
            dataMap.put(str2, appid);
            dataMap.put("api", Gateway.REQUEST_GETDID);
            dataMap.put("platform", "A");
            dataMap.put("mac", WrapperUtility.getMacAddress(context));
            dataMap.put("mdn", str);
            dataMap.put("imei", imei);
            dataMap.put("android_id", Secure.getString(context.getContentResolver(), "android_id"));
            dataMap.put("osversion", VERSION.RELEASE);
            dataMap.put("device", Build.MODEL);
            dataMap.put("hacking", WrapperUtility.IsCracked() ? a.e : a.d);
        } catch (Exception e3) {
            logger.d("[DeviceIdentity][makeSendData]", e3);
            dataMap = new JSONObject();
        }
        String jsonStr = dataMap.toString();
        logger.d("[DeviceIdentity][makeSendData]" + jsonStr);
        return jsonStr;
    }

    private static SecretKeySpec creaetSecretKey(String key) {
        return new SecretKeySpec(key.getBytes(), cryptAlgorithm);
    }

    private static String createHash(String algorithm, byte[] data) {
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

    private static byte[] encrypt(String key, String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ENC_FORMAT);
        cipher.init(1, creaetSecretKey(key), spec);
        return cipher.doFinal(data.getBytes());
    }

    private static byte[] decrypt(String key, byte[] data) throws Exception {
        Cipher cipher = Cipher.getInstance(ENC_FORMAT);
        cipher.init(2, creaetSecretKey(key), spec);
        return cipher.doFinal(data);
    }
}
