package com.mobileapptracker;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import jp.co.cyberz.fox.a.a.i;

final class g {
    g() {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(int r6) {
        /*
        r2 = 0;
        r1 = "";
        r3 = new android.net.Uri$Builder;
        r3.<init>();
        r0 = "https";
        r0 = r3.scheme(r0);
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = com.mobileapptracker.b.a();
        r4 = r4.append(r5);
        r5 = ".deeplink.mobileapptracking.com";
        r4 = r4.append(r5);
        r4 = r4.toString();
        r0 = r0.authority(r4);
        r4 = "v1";
        r0 = r0.appendPath(r4);
        r4 = "link.txt";
        r0 = r0.appendPath(r4);
        r4 = "platform";
        r5 = "android";
        r0 = r0.appendQueryParameter(r4, r5);
        r4 = "advertiser_id";
        r5 = com.mobileapptracker.b.a();
        r0 = r0.appendQueryParameter(r4, r5);
        r4 = "ver";
        r5 = "3.9.1";
        r0 = r0.appendQueryParameter(r4, r5);
        r4 = "package_name";
        r5 = com.mobileapptracker.b.c();
        r4 = r0.appendQueryParameter(r4, r5);
        r5 = "ad_id";
        r0 = com.mobileapptracker.b.e();
        if (r0 == 0) goto L_0x00c7;
    L_0x0061:
        r0 = com.mobileapptracker.b.e();
    L_0x0065:
        r0 = r4.appendQueryParameter(r5, r0);
        r4 = "user_agent";
        r5 = com.mobileapptracker.b.d();
        r0.appendQueryParameter(r4, r5);
        r0 = com.mobileapptracker.b.e();
        if (r0 == 0) goto L_0x0085;
    L_0x0078:
        r0 = "google_ad_tracking_disabled";
        r4 = com.mobileapptracker.b.f();
        r4 = java.lang.Integer.toString(r4);
        r3.appendQueryParameter(r0, r4);
    L_0x0085:
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x00d6 }
        r3 = r3.build();	 Catch:{ Exception -> 0x00d6 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x00d6 }
        r0.<init>(r3);	 Catch:{ Exception -> 0x00d6 }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x00d6 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x00d6 }
        r0.setReadTimeout(r6);	 Catch:{ Exception -> 0x00d6 }
        r0.setConnectTimeout(r6);	 Catch:{ Exception -> 0x00d6 }
        r3 = "X-MAT-Key";
        r4 = com.mobileapptracker.b.b();	 Catch:{ Exception -> 0x00d6 }
        r0.setRequestProperty(r3, r4);	 Catch:{ Exception -> 0x00d6 }
        r3 = "GET";
        r0.setRequestMethod(r3);	 Catch:{ Exception -> 0x00d6 }
        r3 = 1;
        r0.setDoInput(r3);	 Catch:{ Exception -> 0x00d6 }
        r0.connect();	 Catch:{ Exception -> 0x00d6 }
        r3 = r0.getResponseCode();	 Catch:{ Exception -> 0x00d6 }
        r4 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r3 != r4) goto L_0x00cc;
    L_0x00bb:
        r2 = r0.getInputStream();	 Catch:{ Exception -> 0x00d6 }
    L_0x00bf:
        r0 = a(r2);	 Catch:{ Exception -> 0x00d6 }
        r2.close();	 Catch:{ IOException -> 0x00d1 }
    L_0x00c6:
        return r0;
    L_0x00c7:
        r0 = com.mobileapptracker.b.g();
        goto L_0x0065;
    L_0x00cc:
        r2 = r0.getErrorStream();	 Catch:{ Exception -> 0x00d6 }
        goto L_0x00bf;
    L_0x00d1:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00c6;
    L_0x00d6:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x00e5 }
        r2.close();	 Catch:{ IOException -> 0x00df }
        r0 = r1;
        goto L_0x00c6;
    L_0x00df:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r1;
        goto L_0x00c6;
    L_0x00e5:
        r0 = move-exception;
        r2.close();	 Catch:{ IOException -> 0x00ea }
    L_0x00e9:
        throw r0;
    L_0x00ea:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x00e9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobileapptracker.g.a(int):java.lang.String");
    }

    private static String a(InputStream inputStream) {
        if (inputStream == null) {
            return i.a;
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringBuilder = new StringBuilder();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine != null) {
                stringBuilder.append(readLine).append("\n");
            } else {
                bufferedReader.close();
                return stringBuilder.toString();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected static org.json.JSONObject a(java.lang.String r10, org.json.JSONObject r11, boolean r12) {
        /*
        r2 = 0;
        r9 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r0.<init>(r10);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;
        r0.setReadTimeout(r1);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;
        r0.setConnectTimeout(r1);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1 = 1;
        r0.setDoInput(r1);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        if (r11 == 0) goto L_0x0026;
    L_0x0020:
        r1 = r11.length();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        if (r1 != 0) goto L_0x00c6;
    L_0x0026:
        r1 = "GET";
        r0.setRequestMethod(r1);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
    L_0x002b:
        r0.connect();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r6 = r0.getResponseCode();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        if (r12 == 0) goto L_0x0048;
    L_0x0034:
        r1 = "MobileAppTracker";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r4 = "Request completed with status ";
        r3.<init>(r4);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r3 = r3.append(r6);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r3 = r3.toString();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        android.util.Log.d(r1, r3);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
    L_0x0048:
        if (r6 != r9) goto L_0x0118;
    L_0x004a:
        r5 = r0.getInputStream();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
    L_0x004e:
        r1 = a(r5);	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        if (r12 == 0) goto L_0x0068;
    L_0x0054:
        r3 = "MobileAppTracker";
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        r7 = "Server response: ";
        r4.<init>(r7);	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        r4 = r4.append(r1);	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        android.util.Log.d(r3, r4);	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
    L_0x0068:
        r4 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        r4.<init>();	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        r7 = new org.json.JSONTokener;	 Catch:{ Exception -> 0x01e8, all -> 0x0232 }
        r7.<init>(r1);	 Catch:{ Exception -> 0x01e8, all -> 0x0232 }
        r3 = new org.json.JSONObject;	 Catch:{ Exception -> 0x01e8, all -> 0x0232 }
        r3.<init>(r7);	 Catch:{ Exception -> 0x01e8, all -> 0x0232 }
        if (r12 == 0) goto L_0x01e5;
    L_0x0079:
        r1 = r3.length();	 Catch:{ Exception -> 0x023b, all -> 0x0232 }
        if (r1 <= 0) goto L_0x01e5;
    L_0x007f:
        r1 = "errors";
        r1 = r3.has(r1);	 Catch:{ JSONException -> 0x01da }
        if (r1 == 0) goto L_0x011e;
    L_0x0087:
        r1 = "errors";
        r1 = r3.getJSONArray(r1);	 Catch:{ JSONException -> 0x01da }
        r1 = r1.length();	 Catch:{ JSONException -> 0x01da }
        if (r1 == 0) goto L_0x011e;
    L_0x0093:
        r1 = "errors";
        r1 = r3.getJSONArray(r1);	 Catch:{ JSONException -> 0x01da }
        r4 = 0;
        r1 = r1.getString(r4);	 Catch:{ JSONException -> 0x01da }
        r4 = "MobileAppTracker";
        r7 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x01da }
        r8 = "Event was rejected by server with error: ";
        r7.<init>(r8);	 Catch:{ JSONException -> 0x01da }
        r1 = r7.append(r1);	 Catch:{ JSONException -> 0x01da }
        r1 = r1.toString();	 Catch:{ JSONException -> 0x01da }
        android.util.Log.d(r4, r1);	 Catch:{ JSONException -> 0x01da }
        r1 = r3;
    L_0x00b3:
        r3 = "X-MAT-Responder";
        r0 = r0.getHeaderField(r3);	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        if (r6 < r9) goto L_0x01f6;
    L_0x00bb:
        r3 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r6 >= r3) goto L_0x01f6;
    L_0x00bf:
        if (r5 == 0) goto L_0x00c4;
    L_0x00c1:
        r5.close();	 Catch:{ IOException -> 0x01f0 }
    L_0x00c4:
        r0 = r1;
    L_0x00c5:
        return r0;
    L_0x00c6:
        r1 = 1;
        r0.setDoOutput(r1);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1 = "Content-Type";
        r3 = "application/json";
        r0.setRequestProperty(r1, r3);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1 = "Accept";
        r3 = "application/json";
        r0.setRequestProperty(r1, r3);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1 = "POST";
        r0.setRequestMethod(r1);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1 = r0.getOutputStream();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r3 = r11.toString();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r4 = "UTF-8";
        r3 = r3.getBytes(r4);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1.write(r3);	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        r1.close();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        goto L_0x002b;
    L_0x00f3:
        r0 = move-exception;
    L_0x00f4:
        if (r12 == 0) goto L_0x010a;
    L_0x00f6:
        r1 = "MobileAppTracker";
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0234 }
        r4 = "Request error with URL ";
        r3.<init>(r4);	 Catch:{ all -> 0x0234 }
        r3 = r3.append(r10);	 Catch:{ all -> 0x0234 }
        r3 = r3.toString();	 Catch:{ all -> 0x0234 }
        android.util.Log.d(r1, r3);	 Catch:{ all -> 0x0234 }
    L_0x010a:
        r0.printStackTrace();	 Catch:{ all -> 0x0234 }
        if (r2 == 0) goto L_0x0112;
    L_0x010f:
        r2.close();	 Catch:{ IOException -> 0x021f }
    L_0x0112:
        r0 = new org.json.JSONObject;
        r0.<init>();
        goto L_0x00c5;
    L_0x0118:
        r5 = r0.getErrorStream();	 Catch:{ Exception -> 0x00f3, all -> 0x0225 }
        goto L_0x004e;
    L_0x011e:
        r1 = "log_action";
        r1 = r3.has(r1);	 Catch:{ JSONException -> 0x01da }
        if (r1 == 0) goto L_0x01a1;
    L_0x0126:
        r1 = "log_action";
        r1 = r3.getString(r1);	 Catch:{ JSONException -> 0x01da }
        r4 = "null";
        r1 = r1.equals(r4);	 Catch:{ JSONException -> 0x01da }
        if (r1 != 0) goto L_0x01a1;
    L_0x0134:
        r1 = "log_action";
        r1 = r3.getString(r1);	 Catch:{ JSONException -> 0x01da }
        r4 = "false";
        r1 = r1.equals(r4);	 Catch:{ JSONException -> 0x01da }
        if (r1 != 0) goto L_0x01a1;
    L_0x0142:
        r1 = "log_action";
        r1 = r3.getString(r1);	 Catch:{ JSONException -> 0x01da }
        r4 = "true";
        r1 = r1.equals(r4);	 Catch:{ JSONException -> 0x01da }
        if (r1 != 0) goto L_0x01a1;
    L_0x0150:
        r1 = "log_action";
        r1 = r3.getJSONObject(r1);	 Catch:{ JSONException -> 0x01da }
        r4 = "conversion";
        r4 = r1.has(r4);	 Catch:{ JSONException -> 0x01da }
        if (r4 == 0) goto L_0x019e;
    L_0x015e:
        r4 = "conversion";
        r1 = r1.getJSONObject(r4);	 Catch:{ JSONException -> 0x01da }
        r4 = "status";
        r4 = r1.has(r4);	 Catch:{ JSONException -> 0x01da }
        if (r4 == 0) goto L_0x019e;
    L_0x016c:
        r4 = "status";
        r4 = r1.getString(r4);	 Catch:{ JSONException -> 0x01da }
        r7 = "rejected";
        r4 = r4.equals(r7);	 Catch:{ JSONException -> 0x01da }
        if (r4 == 0) goto L_0x0197;
    L_0x017a:
        r4 = "status_code";
        r1 = r1.getString(r4);	 Catch:{ JSONException -> 0x01da }
        r4 = "MobileAppTracker";
        r7 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x01da }
        r8 = "Event was rejected by server: status code ";
        r7.<init>(r8);	 Catch:{ JSONException -> 0x01da }
        r1 = r7.append(r1);	 Catch:{ JSONException -> 0x01da }
        r1 = r1.toString();	 Catch:{ JSONException -> 0x01da }
        android.util.Log.d(r4, r1);	 Catch:{ JSONException -> 0x01da }
        r1 = r3;
        goto L_0x00b3;
    L_0x0197:
        r1 = "MobileAppTracker";
        r4 = "Event was accepted by server";
        android.util.Log.d(r1, r4);	 Catch:{ JSONException -> 0x01da }
    L_0x019e:
        r1 = r3;
        goto L_0x00b3;
    L_0x01a1:
        r1 = "options";
        r1 = r3.has(r1);	 Catch:{ JSONException -> 0x01da }
        if (r1 == 0) goto L_0x01d7;
    L_0x01a9:
        r1 = "options";
        r1 = r3.getJSONObject(r1);	 Catch:{ JSONException -> 0x01da }
        r4 = "conversion_status";
        r4 = r1.has(r4);	 Catch:{ JSONException -> 0x01da }
        if (r4 == 0) goto L_0x01d7;
    L_0x01b7:
        r4 = "conversion_status";
        r1 = r1.getString(r4);	 Catch:{ JSONException -> 0x01da }
        r4 = "MobileAppTracker";
        r7 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x01da }
        r8 = "Event was ";
        r7.<init>(r8);	 Catch:{ JSONException -> 0x01da }
        r1 = r7.append(r1);	 Catch:{ JSONException -> 0x01da }
        r7 = " by server";
        r1 = r1.append(r7);	 Catch:{ JSONException -> 0x01da }
        r1 = r1.toString();	 Catch:{ JSONException -> 0x01da }
        android.util.Log.d(r4, r1);	 Catch:{ JSONException -> 0x01da }
    L_0x01d7:
        r1 = r3;
        goto L_0x00b3;
    L_0x01da:
        r1 = move-exception;
        r4 = "MobileAppTracker";
        r7 = "Server response status could not be parsed";
        android.util.Log.d(r4, r7);	 Catch:{ Exception -> 0x023b, all -> 0x0232 }
        r1.printStackTrace();	 Catch:{ Exception -> 0x023b, all -> 0x0232 }
    L_0x01e5:
        r1 = r3;
        goto L_0x00b3;
    L_0x01e8:
        r1 = move-exception;
        r3 = r4;
    L_0x01ea:
        r1.printStackTrace();	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
        r1 = r3;
        goto L_0x00b3;
    L_0x01f0:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x00c4;
    L_0x01f6:
        r1 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r6 != r1) goto L_0x0212;
    L_0x01fa:
        if (r0 == 0) goto L_0x0212;
    L_0x01fc:
        if (r12 == 0) goto L_0x0205;
    L_0x01fe:
        r0 = "MobileAppTracker";
        r1 = "Request received 400 error from MAT server, won't be retried";
        android.util.Log.d(r0, r1);	 Catch:{ Exception -> 0x0237, all -> 0x0232 }
    L_0x0205:
        if (r5 == 0) goto L_0x020a;
    L_0x0207:
        r5.close();	 Catch:{ IOException -> 0x020d }
    L_0x020a:
        r0 = r2;
        goto L_0x00c5;
    L_0x020d:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x020a;
    L_0x0212:
        if (r5 == 0) goto L_0x0112;
    L_0x0214:
        r5.close();	 Catch:{ IOException -> 0x0219 }
        goto L_0x0112;
    L_0x0219:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0112;
    L_0x021f:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0112;
    L_0x0225:
        r0 = move-exception;
        r5 = r2;
    L_0x0227:
        if (r5 == 0) goto L_0x022c;
    L_0x0229:
        r5.close();	 Catch:{ IOException -> 0x022d }
    L_0x022c:
        throw r0;
    L_0x022d:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x022c;
    L_0x0232:
        r0 = move-exception;
        goto L_0x0227;
    L_0x0234:
        r0 = move-exception;
        r5 = r2;
        goto L_0x0227;
    L_0x0237:
        r0 = move-exception;
        r2 = r5;
        goto L_0x00f4;
    L_0x023b:
        r1 = move-exception;
        goto L_0x01ea;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobileapptracker.g.a(java.lang.String, org.json.JSONObject, boolean):org.json.JSONObject");
    }
}
