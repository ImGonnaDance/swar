package com.com2us.module.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import com.com2us.common.data.CommonPropertyConstants;
import com.com2us.module.manager.Constants;
import com.com2us.module.manager.ModuleConfig;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Properties;
import java.util.zip.CRC32;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import jp.co.cyberz.fox.a.a.i;

public class WrapperUtility {
    private static final int MAC_ADDRESS_LENGTH = 17;
    private static boolean isMacAddressLoaded = false;
    private static Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private static String mMacAddress;

    public static boolean IsCracked() {
        boolean ret = false;
        for (String file : new String[]{"/system/bin/su", "/system/app/Superuser.apk"}) {
            ret |= new File(file).exists();
        }
        return ret;
    }

    public static String getMacAddress(Context context) {
        boolean isCommonTaskRunnerStarted = false;
        if (mMacAddress == null) {
            Class<?> c;
            try {
                c = Class.forName("com.com2us.common.CommonTaskRunner");
                isCommonTaskRunnerStarted = ((Boolean) c.getMethod("isStarted", new Class[0]).invoke(c, new Object[0])).booleanValue();
            } catch (ClassNotFoundException e) {
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (isCommonTaskRunnerStarted) {
                try {
                    c = Class.forName("com.com2us.common.network.NetworkInfo");
                    mMacAddress = (String) c.getMethod("getMacAddress", new Class[0]).invoke(c, new Object[0]);
                } catch (ClassNotFoundException e3) {
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            } else {
                try {
                    c = Class.forName("com.com2us.wrapper.WrapperJinterface");
                    mMacAddress = (String) c.getMethod("getMacAddressEx", new Class[0]).invoke(c, new Object[0]);
                } catch (ClassNotFoundException e4) {
                    if (!isMacAddressLoaded) {
                        mMacAddress = loadMacAddress(context);
                        isMacAddressLoaded = true;
                    }
                } catch (Exception e222) {
                    e222.printStackTrace();
                }
                if (mMacAddress == null) {
                    String macAddress = _getMacAddress(context);
                    if (macAddress != null) {
                        mMacAddress = saveMacAddress(context, macAddress);
                    }
                }
            }
        }
        return mMacAddress;
    }

    private static void initializeCommonData(Activity activity, int tryCount, int tryDelay) {
        File commonDataFile = new File(activity.getFilesDir(), Integer.toString("CommonData.dat".hashCode()));
        mMacAddress = loadCommonData(activity, commonDataFile).getProperty(CommonPropertyConstants.MAC_ADDR_PROPERTY);
        if (!varifyMacAddress(mMacAddress)) {
            mMacAddress = null;
        }
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService("connectivity");
        String macAddress = ((WifiManager) activity.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        if (macAddress != null) {
            macAddress = compileMacAddress(macAddress);
            Properties saveCommonProperties = new Properties();
            saveCommonProperties.setProperty(CommonPropertyConstants.MAC_ADDR_PROPERTY, macAddress);
            if (saveCommonData(activity, saveCommonProperties, commonDataFile)) {
                mMacAddress = macAddress;
            }
        }
    }

    private static String loadMacAddress(Context context) {
        String macAddress = loadCommonData(context, new File(context.getFilesDir(), Integer.toString("CommonData.dat".hashCode()))).getProperty(CommonPropertyConstants.MAC_ADDR_PROPERTY);
        if (!varifyMacAddress(macAddress)) {
            macAddress = null;
        }
        logger.d("[WrapperUtility][loadMacAddress]" + macAddress);
        return macAddress;
    }

    private static String _getMacAddress(Context context) {
        String macAddress = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getMacAddress();
        logger.d("[WrapperUtility][getMacAddress]" + macAddress);
        return macAddress;
    }

    private static String saveMacAddress(Context context, String _macAddress) {
        File commonDataFile = new File(context.getFilesDir(), Integer.toString("CommonData.dat".hashCode()));
        String macAddress = compileMacAddress(_macAddress);
        Properties saveCommonProperties = new Properties();
        saveCommonProperties.setProperty(CommonPropertyConstants.MAC_ADDR_PROPERTY, macAddress);
        if (!saveCommonData(context, saveCommonProperties, commonDataFile)) {
            macAddress = null;
        }
        logger.d("[WrapperUtility][saveMacAddress]" + macAddress);
        return macAddress;
    }

    private static Properties loadCommonData(Context context, File file) {
        Exception e;
        Throwable th;
        Properties properties = new Properties();
        if (file.exists()) {
            FileInputStream fis = null;
            try {
                byte[] crc32 = new byte[8];
                byte[] encryptedData = new byte[(((int) file.length()) - 8)];
                FileInputStream fis2 = new FileInputStream(file);
                try {
                    fis2.read(crc32, 0, 8);
                    fis2.read(encryptedData);
                    String androidId = Secure.getString(context.getContentResolver(), "android_id");
                    if (androidId == null) {
                        androidId = "CommonData.dat";
                    }
                    byte[] decryptedData = decrypt("AES", androidId, encryptedData);
                    if (ByteBuffer.wrap(crc32).getLong() == generateCRC32(decryptedData)) {
                        properties.load(new ByteArrayInputStream(decryptedData));
                    }
                    try {
                        fis2.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    fis = fis2;
                    try {
                        e2.printStackTrace();
                        file.delete();
                        try {
                            fis.close();
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                        return properties;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            fis.close();
                        } catch (Exception e222) {
                            e222.printStackTrace();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fis = fis2;
                    fis.close();
                    throw th;
                }
            } catch (Exception e4) {
                e222 = e4;
                e222.printStackTrace();
                file.delete();
                fis.close();
                return properties;
            }
        }
        return properties;
    }

    private static boolean saveCommonData(Context context, Properties properties, File file) {
        Exception e;
        Throwable th;
        FileOutputStream fos = null;
        try {
            ByteArrayOutputStream data = new ByteArrayOutputStream();
            properties.store(data, null);
            byte[] crc32 = new byte[8];
            ByteBuffer.wrap(crc32).putLong(generateCRC32(data.toByteArray()));
            String androidId = Secure.getString(context.getContentResolver(), "android_id");
            if (androidId == null) {
                androidId = "CommonData.dat";
            }
            byte[] encryptData = encrypt("AES", androidId, data.toByteArray());
            FileOutputStream fos2 = new FileOutputStream(file);
            try {
                fos2.write(crc32);
                fos2.write(encryptData);
                try {
                    fos2.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                fos = fos2;
                return true;
            } catch (Exception e3) {
                e2 = e3;
                fos = fos2;
                try {
                    e2.printStackTrace();
                    try {
                        fos.close();
                    } catch (Exception e22) {
                        e22.printStackTrace();
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        fos.close();
                    } catch (Exception e222) {
                        e222.printStackTrace();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fos = fos2;
                fos.close();
                throw th;
            }
        } catch (Exception e4) {
            e222 = e4;
            e222.printStackTrace();
            fos.close();
            return false;
        }
    }

    public static long generateCRC32(byte[] data) {
        CRC32 crc32 = new CRC32();
        crc32.update(data);
        return crc32.getValue();
    }

    public static String generateCRC32toHexString(byte[] data) {
        return String.format("%08x", new Object[]{Long.valueOf(generateCRC32(data))});
    }

    public static char GetActiveWifi(ConnectivityManager connectivityManager) {
        int i = 1;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return '\u0000';
        }
        boolean ret;
        if (networkInfo.isConnected() && networkInfo.getType() == 1) {
            ret = true;
        } else {
            ret = false;
        }
        if (!ret) {
            i = 0;
        }
        return (char) i;
    }

    public static byte[] encrypt(String algorithm, String keyData, byte[] data) {
        try {
            byte[] privateKey;
            if (algorithm.equals("AES")) {
                privateKey = GetKey(keyData.getBytes("UTF-8"));
            } else {
                privateKey = keyData.getBytes("UTF-8");
            }
            SecretKeySpec key = new SecretKeySpec(privateKey, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(1, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(String algorithm, String keyData, byte[] data) {
        try {
            byte[] privateKey;
            if (algorithm.equals("AES")) {
                privateKey = GetKey(keyData.getBytes("UTF-8"));
            } else {
                privateKey = keyData.getBytes("UTF-8");
            }
            SecretKeySpec key = new SecretKeySpec(privateKey, algorithm);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(2, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static byte[] GetKey(byte[] suggestedKey) {
        int i;
        byte[] kRaw = suggestedKey;
        ArrayList<Byte> kList = new ArrayList();
        for (i = 0; i < ModuleConfig.ACTIVEUSER_MODULE; i += 8) {
            kList.add(Byte.valueOf(kRaw[(i / 8) % kRaw.length]));
        }
        byte[] byteArray = new byte[kList.size()];
        for (i = 0; i < kList.size(); i++) {
            byteArray[i] = ((Byte) kList.get(i)).byteValue();
        }
        return byteArray;
    }

    public static byte[] getHash(byte[] data, String algorithm) {
        try {
            MessageDigest verify = MessageDigest.getInstance(algorithm);
            verify.update(data);
            return verify.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getHashHex(byte[] data, String algorithm) {
        String ret = i.a;
        byte[] hash = getHash(data, algorithm);
        for (int i = 0; i < hash.length; i++) {
            ret = new StringBuilder(String.valueOf(ret)).append(String.format("%02x", new Object[]{Byte.valueOf(hash[i])})).toString();
        }
        return ret;
    }

    public static String compileMacAddress(String macAddress) {
        String temp = macAddress.toLowerCase();
        macAddress = i.a;
        int cnt = 0;
        int i = 0;
        while (i < temp.length()) {
            char currentChar = temp.charAt(i);
            if ((currentChar >= '0' && currentChar <= '9') || (currentChar >= 'a' && currentChar <= 'f')) {
                cnt++;
                macAddress = new StringBuilder(String.valueOf(macAddress)).append(String.valueOf(currentChar)).toString();
                if (cnt % 2 == 0 && i < temp.length() - 1) {
                    macAddress = new StringBuilder(String.valueOf(macAddress)).append(":").toString();
                }
            }
            i++;
        }
        return macAddress;
    }

    public static boolean varifyMacAddress(String macAddress) {
        if (macAddress == null || macAddress.length() != MAC_ADDRESS_LENGTH) {
            return false;
        }
        for (int i = 0; i < macAddress.length(); i++) {
            char currentChar = macAddress.charAt(i);
            if ((i + 1) % 3 == 0) {
                if (currentChar != ':') {
                    return false;
                }
            } else if (currentChar < '0' || currentChar > '9') {
                if (currentChar < 'a') {
                    return false;
                }
                if (currentChar > 'f') {
                    return false;
                }
            }
        }
        return true;
    }
}
