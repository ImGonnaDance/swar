package jp.co.dimage.android;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.com2us.module.manager.ModuleConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.Properties;
import jp.co.cyberz.fox.a.a.i;

public final class p implements f {
    public static String a = i.a;
    public static Thread b = null;
    public static final Uri c = Uri.parse("content://com.facebook.katana.provider.AttributionIdProvider");
    public static final String d = "aid";

    private static class a implements Runnable {
        private Context a = null;

        public a(Context context) {
            this.a = context;
        }

        public final void run() {
            Throwable th;
            Cursor cursor = null;
            try {
                Cursor query;
                try {
                    query = this.a.getContentResolver().query(p.c, new String[]{p.d}, null, null, null);
                    if (query != null) {
                        try {
                            if (query.moveToFirst()) {
                                p.a = query.getString(query.getColumnIndex(p.d));
                                if (query != null) {
                                    try {
                                        query.close();
                                        return;
                                    } catch (Exception e) {
                                        return;
                                    }
                                }
                                return;
                            }
                        } catch (Exception e2) {
                            if (query != null) {
                                try {
                                    query.close();
                                } catch (Exception e3) {
                                    return;
                                }
                            }
                        } catch (Throwable th2) {
                            cursor = query;
                            th = th2;
                            if (cursor != null) {
                                try {
                                    cursor.close();
                                } catch (Exception e4) {
                                }
                            }
                            throw th;
                        }
                    }
                    if (query != null) {
                        try {
                            query.close();
                        } catch (Exception e5) {
                        }
                    }
                } catch (Exception e6) {
                    query = null;
                    if (query != null) {
                        query.close();
                    }
                } catch (Throwable th3) {
                    th = th3;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            } catch (Throwable th4) {
                Log.e(f.aU, i.a, th4);
            }
        }
    }

    public static String a() {
        return a;
    }

    public static synchronized String a(Context context) {
        String str;
        synchronized (p.class) {
            if (context == null) {
                str = null;
            } else if (i.a(a) && b == null) {
                Thread thread = new Thread(new a(context));
                b = thread;
                thread.start();
                str = i.a;
            } else {
                str = a;
            }
        }
        return str;
    }

    private static String a(Context context, String str, String str2) {
        FileInputStream openFileInput;
        Throwable th;
        FileInputStream fileInputStream;
        IOException iOException;
        String str3 = null;
        try {
            Properties properties = new Properties();
            openFileInput = context.openFileInput(str);
            try {
                properties.load(openFileInput);
                str3 = properties.getProperty(str2, i.a);
                if (openFileInput != null) {
                    try {
                        openFileInput.close();
                    } catch (IOException e) {
                    }
                }
            } catch (FileNotFoundException e2) {
                try {
                    a.d(f.aU, "loadValue failed. file '" + str + "' not found.");
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                        } catch (IOException e3) {
                        }
                    }
                    return str3;
                } catch (Throwable th2) {
                    th = th2;
                    if (openFileInput != null) {
                        try {
                            openFileInput.close();
                        } catch (IOException e4) {
                        }
                    }
                    throw th;
                }
            } catch (IOException e5) {
                IOException iOException2 = e5;
                fileInputStream = openFileInput;
                iOException = iOException2;
                try {
                    a.b(f.aU, "loadValue failed. " + iOException.getMessage());
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e6) {
                        }
                    }
                    return str3;
                } catch (Throwable th3) {
                    th = th3;
                    openFileInput = fileInputStream;
                    if (openFileInput != null) {
                        openFileInput.close();
                    }
                    throw th;
                }
            }
        } catch (FileNotFoundException e7) {
            openFileInput = str3;
            a.d(f.aU, "loadValue failed. file '" + str + "' not found.");
            if (openFileInput != null) {
                openFileInput.close();
            }
            return str3;
        } catch (IOException e8) {
            iOException = e8;
            fileInputStream = str3;
            a.b(f.aU, "loadValue failed. " + iOException.getMessage());
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            return str3;
        } catch (Throwable th4) {
            Throwable th5 = th4;
            openFileInput = str3;
            th = th5;
            if (openFileInput != null) {
                openFileInput.close();
            }
            throw th;
        }
        return str3;
    }

    private static String a(String str, String str2) {
        StringBuffer stringBuffer = new StringBuffer();
        String str3 = "0123456789abcdef";
        try {
            MessageDigest instance = MessageDigest.getInstance(str2);
            instance.update(str.getBytes());
            byte[] digest = instance.digest();
            for (int i : digest) {
                int i2;
                if (i2 < 0) {
                    i2 += ModuleConfig.SOCIAL_MEDIA_MOUDLE;
                }
                stringBuffer.append(str3.charAt(i2 / 16));
                stringBuffer.append(str3.charAt(i2 % 16));
            }
        } catch (Exception e) {
        }
        return stringBuffer.toString();
    }

    public static String a(String str, String[] strArr) {
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = URLEncoder.encode(strArr[i]);
        }
        return new MessageFormat(str).format(strArr);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r6, java.lang.String r7, java.lang.String r8, java.lang.String r9) {
        /*
        r0 = 0;
        r1 = new java.util.Properties;	 Catch:{ FileNotFoundException -> 0x0019, IOException -> 0x003c, all -> 0x0060 }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x0019, IOException -> 0x003c, all -> 0x0060 }
        r1.setProperty(r8, r9);	 Catch:{ FileNotFoundException -> 0x0019, IOException -> 0x003c, all -> 0x0060 }
        r2 = 0;
        r0 = r6.openFileOutput(r7, r2);	 Catch:{ FileNotFoundException -> 0x0019, IOException -> 0x003c, all -> 0x0060 }
        r2 = "ADMAGE Session Information";
        r1.store(r0, r2);	 Catch:{ FileNotFoundException -> 0x0019, IOException -> 0x0075 }
        if (r0 == 0) goto L_0x0018;
    L_0x0015:
        r0.close();	 Catch:{ IOException -> 0x006c }
    L_0x0018:
        return;
    L_0x0019:
        r1 = move-exception;
        r1 = "F.O.X";
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x006e }
        r3 = "saveValue failed. file '";
        r2.<init>(r3);	 Catch:{ all -> 0x006e }
        r2 = r2.append(r7);	 Catch:{ all -> 0x006e }
        r3 = "' not found.";
        r2 = r2.append(r3);	 Catch:{ all -> 0x006e }
        r2 = r2.toString();	 Catch:{ all -> 0x006e }
        jp.co.dimage.android.a.b(r1, r2);	 Catch:{ all -> 0x006e }
        if (r0 == 0) goto L_0x0018;
    L_0x0036:
        r0.close();	 Catch:{ IOException -> 0x003a }
        goto L_0x0018;
    L_0x003a:
        r0 = move-exception;
        goto L_0x0018;
    L_0x003c:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0040:
        r2 = "F.O.X";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0073 }
        r4 = "saveValue failed. ";
        r3.<init>(r4);	 Catch:{ all -> 0x0073 }
        r0 = r0.getMessage();	 Catch:{ all -> 0x0073 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x0073 }
        r0 = r0.toString();	 Catch:{ all -> 0x0073 }
        jp.co.dimage.android.a.b(r2, r0);	 Catch:{ all -> 0x0073 }
        if (r1 == 0) goto L_0x0018;
    L_0x005a:
        r1.close();	 Catch:{ IOException -> 0x005e }
        goto L_0x0018;
    L_0x005e:
        r0 = move-exception;
        goto L_0x0018;
    L_0x0060:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0064:
        if (r1 == 0) goto L_0x0069;
    L_0x0066:
        r1.close();	 Catch:{ IOException -> 0x006a }
    L_0x0069:
        throw r0;
    L_0x006a:
        r1 = move-exception;
        goto L_0x0069;
    L_0x006c:
        r0 = move-exception;
        goto L_0x0018;
    L_0x006e:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0064;
    L_0x0073:
        r0 = move-exception;
        goto L_0x0064;
    L_0x0075:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0040;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.p.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static boolean a(String str) {
        return str == null || str.length() == 0;
    }

    public static String b(String str) {
        if (a(str)) {
            return null;
        }
        int indexOf = str.indexOf("://");
        if (indexOf < 0) {
            return str;
        }
        indexOf += 3;
        int indexOf2 = str.indexOf("/", indexOf);
        return indexOf2 > 0 ? str.substring(indexOf, indexOf2) : str.substring(indexOf, str.length());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean b(android.content.Context r3) {
        /*
        r0 = 0;
        r1 = "__ADMAGE_DUMMY__";
        r2 = 0;
        r3.openFileOutput(r1, r2);	 Catch:{ Exception -> 0x000e, all -> 0x0017 }
        r0 = "__ADMAGE_DUMMY__";
        r3.deleteFile(r0);	 Catch:{ Exception -> 0x001e }
    L_0x000c:
        r0 = 1;
    L_0x000d:
        return r0;
    L_0x000e:
        r1 = move-exception;
        r1 = "__ADMAGE_DUMMY__";
        r3.deleteFile(r1);	 Catch:{ Exception -> 0x0015 }
        goto L_0x000d;
    L_0x0015:
        r1 = move-exception;
        goto L_0x000d;
    L_0x0017:
        r0 = move-exception;
        r1 = "__ADMAGE_DUMMY__";
        r3.deleteFile(r1);	 Catch:{ Exception -> 0x0020 }
    L_0x001d:
        throw r0;
    L_0x001e:
        r0 = move-exception;
        goto L_0x000c;
    L_0x0020:
        r1 = move-exception;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: jp.co.dimage.android.p.b(android.content.Context):boolean");
    }

    public static String c(String str) {
        return a(str, "SHA-1");
    }

    private static String d(String str) {
        return a(str, "MD5");
    }

    private static String e(String str) {
        return a(str, "SHA-256");
    }
}
