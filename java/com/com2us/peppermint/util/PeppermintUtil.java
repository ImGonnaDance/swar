package com.com2us.peppermint.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import com.com2us.peppermint.PeppermintURL;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class PeppermintUtil {
    public static Bundle decodeUrl(String str) {
        Bundle bundle = new Bundle();
        if (str != null) {
            try {
                for (String split : str.split("&")) {
                    String[] split2 = split.split("=");
                    if (split2.length == 2) {
                        bundle.putString(URLDecoder.decode(split2[0], "UTF-8"), URLDecoder.decode(split2[1], "UTF-8"));
                    }
                }
            } catch (UnsupportedEncodingException e) {
                PeppermintLog.i("decodeUrl Failed.");
                e.printStackTrace();
            }
        }
        return bundle;
    }

    public static String deviceName() {
        return Build.MODEL;
    }

    public static String generateClientKey(String str) throws Exception {
        String mD5Hash = PeppermintEncryption.getMD5Hash(String.valueOf(System.currentTimeMillis()).getBytes("UTF-8"));
        byte[] bytes = mD5Hash.getBytes("UTF-8");
        byte[] bArr = new byte[16];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = bytes[i];
        }
        byte[] bArr2 = null;
        try {
            bArr2 = PeppermintEncryption.encryptAES(bArr, str.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new StringBuilder(String.valueOf(mD5Hash)).append(PeppermintEncryption.getMD5Hash(str.getBytes("UTF-8"))).append(new String(PeppermintEncryption.encodeBase64(bArr2))).toString();
    }

    public static Bundle getBundleFrojObj(JSONObject jSONObject) {
        Bundle bundle = new Bundle();
        if (jSONObject == null) {
            return bundle;
        }
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            try {
                String str = (String) keys.next();
                String string = jSONObject.getString(str);
                bundle.putString(str, string);
                PeppermintLog.i("getBundleFrojObj key : " + str + " value : " + string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return bundle;
    }

    public static byte[] getByteArrFromBitmap(Bitmap bitmap) {
        byte[] bArr = null;
        if (bitmap == null) {
            return null;
        }
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            bArr = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            return bArr;
        } catch (IOException e) {
            try {
                e.printStackTrace();
                return bArr;
            } catch (Exception e2) {
                return new byte[0];
            }
        }
    }

    public static ArrayList<String> getContactsEmail(Context context, int i, int i2) {
        Cursor query = context.getContentResolver().query(Contacts.CONTENT_URI, null, null, null, null);
        ArrayList<String> arrayList = new ArrayList();
        query.moveToPosition(i);
        if (!query.isAfterLast()) {
            do {
                Cursor query2 = context.getContentResolver().query(Email.CONTENT_URI, null, "contact_id = " + query.getString(query.getColumnIndex("_id")), null, null);
                while (query2.moveToNext()) {
                    arrayList.add(query2.getString(query2.getColumnIndex("data1")));
                }
                query2.close();
            } while (query.moveToNext());
        }
        query.close();
        return arrayList;
    }

    public static ArrayList<String> getContactsPhonenumber(Context context, int i, int i2) {
        ArrayList<String> arrayList = new ArrayList();
        Cursor query = context.getContentResolver().query(Phone.CONTENT_URI, null, null, null, null);
        while (query.moveToNext()) {
            arrayList.add(query.getString(query.getColumnIndex("data1")));
        }
        query.close();
        return arrayList;
    }

    public static int getDegreeFromOrientation(int i) {
        return i == 6 ? 90 : i == 3 ? 180 : i == 8 ? 270 : 0;
    }

    public static JSONObject getJSONObjectFromBundle(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        if (bundle == null) {
            return jSONObject;
        }
        for (String str : bundle.keySet()) {
            try {
                String string = bundle.getString(str);
                jSONObject.put(str, string);
                PeppermintLog.i("getJSONObjectFromBundle key : " + str + " value : " + string);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    public static Bitmap getRotatedBitmap(Bitmap bitmap, int i) {
        if (i == 0 || bitmap == null) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate((float) i, ((float) bitmap.getWidth()) / 2.0f, ((float) bitmap.getHeight()) / 2.0f);
        try {
            Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            if (bitmap == createBitmap) {
                return bitmap;
            }
            bitmap.recycle();
            return createBitmap;
        } catch (OutOfMemoryError e) {
            PeppermintLog.i("getRotatedBitmap Failed. out if Memory");
            return bitmap;
        }
    }

    public static String getStringByByte(String str, int i) {
        int i2 = 0;
        StringBuffer stringBuffer = new StringBuffer(i);
        char[] toCharArray = str.toCharArray();
        int length = toCharArray.length;
        int i3 = 0;
        while (i2 < length) {
            char c = toCharArray[i2];
            i3 += String.valueOf(c).getBytes().length;
            if (i3 > i) {
                break;
            }
            stringBuffer.append(c);
            i2++;
        }
        return stringBuffer.toString();
    }

    public static byte[] macAddress(Context context) {
        boolean z = true;
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        if (wifiManager != null && wifiManager.getConnectionInfo() != null && wifiManager.getConnectionInfo().getMacAddress() != null) {
            return wifiManager.getConnectionInfo().getMacAddress().getBytes();
        }
        if (wifiManager.isWifiEnabled()) {
            z = false;
        } else {
            while (!wifiManager.setWifiEnabled(true)) {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        while (true) {
            if (wifiManager.getConnectionInfo() != null && wifiManager.getConnectionInfo().getMacAddress() != null) {
                break;
            }
            try {
                Thread.sleep(500);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        byte[] bytes = wifiManager.getConnectionInfo().getMacAddress().getBytes();
        if (z) {
            wifiManager.setWifiEnabled(false);
        }
        return bytes;
    }

    public static Bundle parseUrl(String str) {
        try {
            URL url = new URL(str.replace(PeppermintURL.PEPPERMINT_SCHEME, WebClient.INTENT_PROTOCOL_START_HTTP));
            Bundle decodeUrl = decodeUrl(url.getQuery());
            decodeUrl.putAll(decodeUrl(url.getRef()));
            return decodeUrl;
        } catch (MalformedURLException e) {
            PeppermintLog.i("parseUrl Failed");
            return new Bundle();
        }
    }
}
