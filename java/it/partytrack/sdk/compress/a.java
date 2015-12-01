package it.partytrack.sdk.compress;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.com2us.module.manager.ModuleConfig;
import com.com2us.module.mercury.MercuryDefine;
import com.com2us.peppermint.PeppermintConstant;
import com.facebook.internal.ServerProtocol;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Pattern;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class a {
    int a;
    long f125a;
    String f126a;
    Map f127a;
    int b;
    int c;

    public a(Cursor cursor) {
        int columnIndex = cursor.getColumnIndex(PeppermintConstant.JSON_KEY_ID);
        if (cursor.getCount() != 0 && columnIndex != -1) {
            this.a = cursor.getInt(columnIndex);
            this.b = cursor.getInt(cursor.getColumnIndex("event_id"));
            this.f126a = cursor.getString(cursor.getColumnIndex("event_identifier"));
            String string = cursor.getString(cursor.getColumnIndex(PeppermintConstant.JSON_KEY_PARAMS));
            if (string != null && string.length() > 0) {
                try {
                    JSONObject jSONObject = new JSONObject(string);
                    Iterator keys = jSONObject.keys();
                    this.f127a = new HashMap();
                    while (keys.hasNext()) {
                        string = (String) keys.next();
                        this.f127a.put(string, jSONObject.getString(string));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            this.c = cursor.getInt(cursor.getColumnIndex("resend_count"));
            this.f125a = cursor.getLong(cursor.getColumnIndex("created_at"));
            cursor.getLong(cursor.getColumnIndex("updated_at"));
        }
    }

    static String a(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream openFileInput = d.f110a.openFileInput("partytrack." + str);
            Reader inputStreamReader = new InputStreamReader(openFileInput, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    inputStreamReader.close();
                    openFileInput.close();
                    return stringBuilder.toString();
                }
                stringBuilder.append(readLine);
            }
        } catch (NullPointerException e) {
            return null;
        } catch (FileNotFoundException e2) {
            return null;
        } catch (IOException e3) {
            return null;
        }
    }

    static String a(String str, String str2) {
        if (str2 == null) {
            return i.a;
        }
        if (str.startsWith("m")) {
            str = Pattern.compile("md").matcher(str).replaceFirst("MD");
        } else if (str.startsWith("s")) {
            str = Pattern.compile("sha").matcher(str).replaceFirst("SHA-");
        }
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            StringBuilder stringBuilder = new StringBuilder();
            instance.update(str2.getBytes());
            byte[] digest = instance.digest();
            for (byte b : digest) {
                String toHexString = Integer.toHexString(b & 255);
                if (toHexString.length() == 1) {
                    stringBuilder.append(0);
                }
                stringBuilder.append(toHexString);
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    static SortedMap a(SortedMap sortedMap) {
        String str = "SHA-256";
        StringBuilder stringBuilder = new StringBuilder();
        SortedMap treeMap = new TreeMap();
        if (sortedMap != null) {
            treeMap.putAll(sortedMap);
        }
        treeMap.put(ServerProtocol.FALLBACK_DIALOG_PARAM_APP_ID, String.valueOf(d.f109a));
        treeMap.put("app_key", d.f113a);
        for (Entry value : treeMap.entrySet()) {
            stringBuilder.append((String) value.getValue());
            stringBuilder.append(":");
        }
        String stringBuilder2 = stringBuilder.toString();
        stringBuilder2 = a(str, stringBuilder2.substring(0, stringBuilder2.length() - 1));
        if (sortedMap == null) {
            sortedMap = new TreeMap();
        }
        sortedMap.put("digest", stringBuilder2);
        return sortedMap;
    }

    static void a() {
        SortedMap treeMap = new TreeMap();
        Object a = a("collector_js_version");
        if (a == null) {
            a = "0.0";
        }
        treeMap.put("adtruth_js_collector_version", a);
        f fVar = new f(a(treeMap));
        fVar.f123a = "GET";
        fVar.a(fVar.a(WebClient.INTENT_PROTOCOL_START_HTTP, "setting.adzcore.com", "i.json"));
        if (fVar.a >= 400 && fVar.a < 500) {
            a("Failed to initialize request");
            a("Please check APP_ID, APP_KEY, and PartTrack's settings your application");
            d.f112a = Integer.valueOf(0);
        } else if (fVar.a != SelectTarget.TARGETING_SUCCESS) {
            a("Failed to initialize request");
            a("Please check your device could connect internet");
            a("Another case, PartyTrack server might be buzy, so please check later or contact us");
        } else {
            try {
                JSONObject jSONObject = new JSONObject(fVar.c);
                if (jSONObject.has("adtruth_js_collector_version")) {
                    String string = jSONObject.getString("adtruth_js_collector_version");
                    if (!(string == null || string.length() == 0)) {
                        a("collector_js_version", string);
                        a("collector_js", jSONObject.getString("adtruth_js_collector"));
                    }
                }
                Integer valueOf = Integer.valueOf(jSONObject.getInt("status"));
                d.f112a = valueOf;
                if (valueOf.intValue() != 1) {
                    a("Invalid app status received");
                    a("Please cehck your app status about PartyTack");
                    b.a();
                    return;
                }
                if (jSONObject.has("ssl") && jSONObject.getInt("ssl") == 0) {
                    d.f115a = false;
                }
                JSONArray jSONArray = jSONObject.getJSONArray("identifiers");
                for (int i = 0; i < jSONArray.length(); i++) {
                    String string2 = jSONArray.getString(i);
                    String a2 = e.a(string2);
                    "identifier key:" + string2;
                    "identifier value:" + a2;
                    if (a2.length() > 0) {
                        d.f117b.put(string2, a2);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                d.f112a = Integer.valueOf(0);
            }
        }
    }

    public static void a(Context context, int i, String str) {
        d.f109a = i;
        d.f113a = str;
        d.f110a = context.getApplicationContext();
        try {
            d.a = (float) context.getPackageManager().getPackageInfo(context.getPackageName(), ModuleConfig.ACTIVEUSER_MODULE).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void m7a(String str) {
        if (str != null && d.f118b) {
            Log.d("PartyTrackDebugInfo", str);
        }
    }

    static void m8a(String str, String str2) {
        try {
            OutputStream openFileOutput = d.f110a.openFileOutput("partytrack." + str, 0);
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(openFileOutput, "UTF-8"));
            printWriter.append(str2);
            printWriter.close();
            openFileOutput.close();
        } catch (NullPointerException e) {
        } catch (UnsupportedEncodingException e2) {
        } catch (FileNotFoundException e3) {
        } catch (IOException e4) {
        }
    }

    static boolean m9a() {
        return d.f112a == null || d.f112a.intValue() == 1;
    }

    static String b() {
        String a = a("collector_js");
        if (a == null) {
            return i.a;
        }
        new Handler(Looper.getMainLooper()).post(new j(a));
        int i = 0;
        while (d.g == null && i < 10) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
        }
        a = i.a;
        if (d.g == null) {
            return a;
        }
        a = new String(d.g);
        d.g = null;
        return a;
    }

    static String b(String str) {
        return String.format(Locale.ENGLISH, "/%s.%s.%d%s", new Object[]{a.e, a.e, Integer.valueOf(d.f109a), str});
    }

    static boolean m10b() {
        return (d.f110a == null || d.f113a == null) ? false : true;
    }

    static boolean c() {
        if (d.f111a != null) {
            return d.f111a.booleanValue();
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(new String[]{"sh", "-c", "echo $PATH"}).getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                stringBuilder.append(readLine);
            }
            for (Object obj : stringBuilder.toString().split(":")) {
                if (new File(obj + "/su").exists()) {
                    d.f111a = Boolean.valueOf(true);
                    return true;
                }
            }
            d.f111a = Boolean.valueOf(false);
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            d.f111a = Boolean.valueOf(false);
            return false;
        }
    }

    String m11a() {
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : this.f127a.entrySet()) {
            try {
                jSONObject.put((String) entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject.toString();
    }

    Map m12a() {
        Map hashMap = new HashMap();
        hashMap.put("device_timestamp", String.valueOf(this.f125a));
        if (this.f126a.equals("start_event")) {
            if (!(this.f127a == null || this.f127a.get("parameters") == null)) {
                hashMap.put("parameters", (String) this.f127a.get("parameters"));
            }
        } else if (this.f126a.equals("payment")) {
            JSONObject jSONObject = new JSONObject();
            for (String str : new String[]{"item_name", "item_price", "item_price_currency", "item_num"}) {
                try {
                    jSONObject.put(str, this.f127a.get(str));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            hashMap.put(MercuryDefine.TYPE_EVENT, this.f126a);
            hashMap.put("parameters", jSONObject.toString());
        } else if (this.b == 0) {
            hashMap.put(MercuryDefine.TYPE_EVENT, this.f126a);
            if (!(this.f127a == null || this.f127a.isEmpty())) {
                hashMap.put("parameters", a());
            }
        } else {
            hashMap.put("event_id", String.valueOf(this.b));
            if (!(this.f127a == null || this.f127a.isEmpty())) {
                hashMap.put("parameters", a());
            }
        }
        return hashMap;
    }
}
