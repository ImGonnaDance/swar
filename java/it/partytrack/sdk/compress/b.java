package it.partytrack.sdk.compress;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import com.com2us.peppermint.PeppermintConstant;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import jp.co.cyberz.fox.a.a.i;
import org.json.JSONException;
import org.json.JSONObject;

public final class b {
    private static boolean a = false;

    private static SQLiteDatabase a(boolean z) {
        try {
            g gVar = new g(d.f110a);
            return z ? gVar.getWritableDatabase() : gVar.getReadableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } catch (NullPointerException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static a a() {
        a aVar;
        SQLiteException e;
        SQLiteDatabase a = a(false);
        if (a == null) {
            return null;
        }
        try {
            Cursor query = a.query("events", null, "id != ? AND resend_count < ?", new String[]{a.d, String.valueOf(10)}, null, null, "id ASC", a.e);
            query.moveToFirst();
            aVar = new a(query);
            try {
                query.close();
                a.close();
                return aVar;
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    e.printStackTrace();
                    return aVar;
                } finally {
                    a.close();
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            aVar = null;
            e.printStackTrace();
            return aVar;
        }
    }

    static void m4a() {
        try {
            d.f110a.deleteDatabase("partytrack");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void a(int i) {
        if (a.a()) {
            String format = String.format(Locale.ENGLISH, "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);", new Object[]{"events", "event_id", "created_at", "updated_at"});
            SQLiteDatabase a = a(true);
            if (a != null) {
                try {
                    a.execSQL(format, new Object[]{Integer.valueOf(i), Long.valueOf(System.currentTimeMillis()), Long.valueOf(System.currentTimeMillis())});
                } catch (SQLiteException e) {
                    e.printStackTrace();
                } finally {
                    a.close();
                }
            }
        }
    }

    static void a(int i, Map map) {
        if (a.a()) {
            JSONObject jSONObject = new JSONObject();
            try {
                Set<String> keySet = map.keySet();
                if (keySet != null) {
                    for (String str : keySet) {
                        "key:" + str;
                        "value:" + ((String) map.get(str));
                        jSONObject.put(str, map.get(str));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String format = String.format(Locale.ENGLISH, "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", new Object[]{"events", "event_id", PeppermintConstant.JSON_KEY_PARAMS, "created_at", "updated_at"});
            SQLiteDatabase a = a(true);
            if (a != null) {
                try {
                    a.execSQL(format, new Object[]{Integer.valueOf(i), jSONObject.toString(), Long.valueOf(System.currentTimeMillis()), Long.valueOf(System.currentTimeMillis())});
                } catch (SQLiteException e2) {
                    e2.printStackTrace();
                } finally {
                    a.close();
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(it.partytrack.sdk.compress.a r6) {
        /*
        r0 = 1;
        r1 = a(r0);
        if (r1 == 0) goto L_0x004b;
    L_0x0007:
        r0 = r6.f126a;	 Catch:{ SQLiteException -> 0x0071 }
        r2 = "start_event";
        r0 = r0.equals(r2);	 Catch:{ SQLiteException -> 0x0071 }
        if (r0 == 0) goto L_0x004c;
    L_0x0011:
        r0 = a();	 Catch:{ SQLiteException -> 0x0071 }
        if (r0 != 0) goto L_0x004c;
    L_0x0017:
        r0 = java.util.Locale.ENGLISH;	 Catch:{ SQLiteException -> 0x0071 }
        r2 = "UPDATE %s SET %s = ? WHERE %s = ?";
        r3 = 3;
        r3 = new java.lang.Object[r3];	 Catch:{ SQLiteException -> 0x0071 }
        r4 = 0;
        r5 = "events";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0071 }
        r4 = 1;
        r5 = "id";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0071 }
        r4 = 2;
        r5 = "id";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0071 }
        r0 = java.lang.String.format(r0, r2, r3);	 Catch:{ SQLiteException -> 0x0071 }
        r2 = 2;
        r2 = new java.lang.Object[r2];	 Catch:{ SQLiteException -> 0x0071 }
        r3 = 0;
        r4 = 0;
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ SQLiteException -> 0x0071 }
        r2[r3] = r4;	 Catch:{ SQLiteException -> 0x0071 }
        r3 = 1;
        r4 = r6.a;	 Catch:{ SQLiteException -> 0x0071 }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ SQLiteException -> 0x0071 }
        r2[r3] = r4;	 Catch:{ SQLiteException -> 0x0071 }
        r1.execSQL(r0, r2);	 Catch:{ SQLiteException -> 0x0071 }
    L_0x0048:
        r1.close();
    L_0x004b:
        return;
    L_0x004c:
        r0 = java.util.Locale.ENGLISH;	 Catch:{ SQLiteException -> 0x0071 }
        r2 = "DELETE FROM %s WHERE %s = ?;";
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ SQLiteException -> 0x0071 }
        r4 = 0;
        r5 = "events";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0071 }
        r4 = 1;
        r5 = "id";
        r3[r4] = r5;	 Catch:{ SQLiteException -> 0x0071 }
        r0 = java.lang.String.format(r0, r2, r3);	 Catch:{ SQLiteException -> 0x0071 }
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ SQLiteException -> 0x0071 }
        r3 = 0;
        r4 = r6.a;	 Catch:{ SQLiteException -> 0x0071 }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ SQLiteException -> 0x0071 }
        r2[r3] = r4;	 Catch:{ SQLiteException -> 0x0071 }
        r1.execSQL(r0, r2);	 Catch:{ SQLiteException -> 0x0071 }
        goto L_0x0048;
    L_0x0071:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0079 }
        r1.close();
        goto L_0x004b;
    L_0x0079:
        r0 = move-exception;
        r1.close();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: it.partytrack.sdk.compress.b.a(it.partytrack.sdk.compress.a):void");
    }

    static void a(h hVar) {
        if (a.a()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("item_name", hVar.f122a);
                jSONObject.put("item_price", Float.toString(hVar.a));
                jSONObject.put("item_price_currency", hVar.b);
                jSONObject.put("item_num", hVar.f121a);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (jSONObject.length() != 0) {
                String format = String.format(Locale.ENGLISH, "INSERT INTO %s(%s, %s, %s, %s) VALUES(?, ? , ?, ?);", new Object[]{"events", "event_identifier", PeppermintConstant.JSON_KEY_PARAMS, "created_at", "updated_at"});
                SQLiteDatabase a = a(true);
                if (a != null) {
                    try {
                        a.execSQL(format, new Object[]{"payment", jSONObject.toString(), Long.valueOf(System.currentTimeMillis()), Long.valueOf(System.currentTimeMillis())});
                    } catch (SQLiteException e2) {
                        e2.printStackTrace();
                    } finally {
                        a.close();
                    }
                }
            }
        }
    }

    static void a(String str) {
        if (a.a()) {
            String format = String.format(Locale.ENGLISH, "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?);", new Object[]{"events", "event_identifier", "created_at", "updated_at"});
            SQLiteDatabase a = a(true);
            if (a != null) {
                try {
                    a.execSQL(format, new Object[]{str, Long.valueOf(System.currentTimeMillis()), Long.valueOf(System.currentTimeMillis())});
                } catch (SQLiteException e) {
                    e.printStackTrace();
                } finally {
                    a.close();
                }
            }
        }
    }

    static void a(String str, Map map) {
        if (a.a()) {
            JSONObject jSONObject = new JSONObject();
            try {
                Set<String> keySet = map.keySet();
                if (keySet != null) {
                    for (String str2 : keySet) {
                        jSONObject.put(str2, map.get(str2));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String str22 = String.format(Locale.ENGLISH, "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?);", new Object[]{"events", "event_identifier", PeppermintConstant.JSON_KEY_PARAMS, "created_at", "updated_at"});
            SQLiteDatabase a = a(true);
            if (a != null) {
                try {
                    a.execSQL(str22, new Object[]{str, jSONObject.toString(), Long.valueOf(System.currentTimeMillis()), Long.valueOf(System.currentTimeMillis())});
                } catch (SQLiteException e2) {
                    e2.printStackTrace();
                } finally {
                    a.close();
                }
            }
        }
    }

    static void m5a(boolean z) {
        if (!a) {
            boolean z2 = d.f112a != null && d.f112a.intValue() == 1;
            if (z2) {
                String valueOf;
                a = true;
                loop0:
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                    }
                    a a = a();
                    if ((!z && !a()) || a == null || a.a == 0) {
                        break loop0;
                    }
                    SortedMap treeMap = new TreeMap();
                    treeMap.putAll(d.f117b);
                    if (d.f119c.size() > 0) {
                        treeMap.putAll(d.f119c);
                    }
                    treeMap.putAll(a.a());
                    String a2 = a.a(Gateway.REQUEST_REFERRER);
                    String a3 = a.a("full_referrer");
                    if (a2 != null) {
                        treeMap.put("android_referrer_id", a2);
                    }
                    if (a3 != null) {
                        treeMap.put("android_full_referrer", a3);
                    }
                    if (a.c()) {
                        treeMap.put("privileged", a.e);
                    }
                    treeMap.put("gmtoffset", String.valueOf(d.b));
                    treeMap.put("timezone", d.d);
                    treeMap.put("system_version", d.c);
                    treeMap.put("language_code", d.e);
                    treeMap.put("country_code", d.f);
                    treeMap.put("sdk_version", "1.10.0");
                    treeMap.put("model", d.f116b);
                    treeMap.put("app_version", String.valueOf(d.a));
                    a2 = a.b();
                    if (a2 != i.a) {
                        treeMap.put("adtruth_payload", a2);
                    }
                    f fVar = new f(a.a(treeMap));
                    fVar.f123a = "POST";
                    fVar.a(fVar.a(d.f115a ? "https" : WebClient.INTENT_PROTOCOL_START_HTTP, "action.adzcore.com", "a"));
                    if (fVar.a == SelectTarget.TARGETING_SUCCESS) {
                        a(a);
                    } else if (fVar.a >= 400 && fVar.a < 500) {
                        valueOf = a.b == 0 ? a.f126a : String.valueOf(a.b);
                        a.a("Failed to track event");
                        a.a("Assigned event [ " + valueOf + " ] is some possibility of don't register PartyTrack");
                        a(a);
                    } else if (fVar.a == 302) {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(fVar.b));
                        intent.setFlags(268435456);
                        new Handler(Looper.getMainLooper()).post(new c(intent));
                        break loop0;
                    } else {
                        valueOf = String.format(Locale.ENGLISH, "UPDATE %s SET %s = ? WHERE %s = ?", new Object[]{"events", "resend_count", PeppermintConstant.JSON_KEY_ID});
                        SQLiteDatabase a4 = a(true);
                        if (a4 != null) {
                            try {
                                a4.execSQL(valueOf, new Object[]{Integer.valueOf(a.c + 1), Integer.valueOf(a.a)});
                            } catch (SQLiteException e2) {
                                e2.printStackTrace();
                            } finally {
                                a4.close();
                            }
                        } else {
                            continue;
                        }
                    }
                }
                valueOf = String.format(Locale.ENGLISH, "DELETE FROM %s WHERE resend_count >= ?", new Object[]{"events"});
                SQLiteDatabase a5 = a(true);
                if (a5 != null) {
                    try {
                        a5.execSQL(valueOf, new Object[]{Integer.valueOf(10)});
                    } catch (SQLiteException e22) {
                        e22.printStackTrace();
                    } finally {
                        a5.close();
                    }
                }
                a = false;
            }
        }
    }

    private static boolean m6a() {
        if (d.f120c) {
            return true;
        }
        SQLiteDatabase a = a(false);
        if (a != null) {
            try {
                Cursor query = a.query("events", null, "id = 0", null, null, null, "id ASC", a.e);
                int count = query.getCount();
                query.close();
                a.close();
                if (count == 1) {
                    d.f120c = true;
                    return true;
                }
            } catch (SQLiteException e) {
                a.close();
                e.printStackTrace();
            }
        }
        return false;
    }
}
