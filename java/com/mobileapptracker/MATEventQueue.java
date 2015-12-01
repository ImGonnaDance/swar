package com.mobileapptracker;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import com.com2us.peppermint.PeppermintConstant;
import java.util.concurrent.Semaphore;
import org.json.JSONException;
import org.json.JSONObject;

public class MATEventQueue {
    private static long d = 0;
    private SharedPreferences a;
    private Semaphore b = new Semaphore(1, true);
    private MobileAppTracker c;

    public class Add implements Runnable {
        final /* synthetic */ MATEventQueue a;
        private String b = null;
        private String c = null;
        private JSONObject d = null;
        private boolean e = false;

        protected Add(MATEventQueue mATEventQueue, String link, String data, JSONObject postBody, boolean firstSession) {
            this.a = mATEventQueue;
            this.b = link;
            this.c = data;
            this.d = postBody;
            this.e = firstSession;
        }

        public void run() {
            try {
                this.a.b.acquire();
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("link", this.b);
                    jSONObject.put(PeppermintConstant.JSON_KEY_DATA, this.c);
                    jSONObject.put("post_body", this.d);
                    jSONObject.put("first_session", this.e);
                    int queueSize = this.a.getQueueSize() + 1;
                    this.a.setQueueSize(queueSize);
                    this.a.setQueueItemForKey(jSONObject, Integer.toString(queueSize));
                } catch (JSONException e) {
                    Log.w("MobileAppTracker", "Failed creating event for queueing");
                    e.printStackTrace();
                    this.a.b.release();
                }
            } catch (InterruptedException e2) {
                Log.w("MobileAppTracker", "Interrupted adding event to queue");
                e2.printStackTrace();
            } finally {
                this.a.b.release();
            }
        }
    }

    public class Dump implements Runnable {
        final /* synthetic */ MATEventQueue a;

        protected Dump(MATEventQueue mATEventQueue) {
            this.a = mATEventQueue;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r12 = this;
            r0 = r12.a;
            r3 = r0.getQueueSize();
            if (r3 != 0) goto L_0x0009;
        L_0x0008:
            return;
        L_0x0009:
            r0 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r0 = r0.b;	 Catch:{ InterruptedException -> 0x0098 }
            r0.acquire();	 Catch:{ InterruptedException -> 0x0098 }
            r0 = 1;
            r1 = 50;
            if (r3 <= r1) goto L_0x001b;
        L_0x0017:
            r0 = r3 + -50;
            r0 = r0 + 1;
        L_0x001b:
            if (r0 > r3) goto L_0x018e;
        L_0x001d:
            r4 = java.lang.Integer.toString(r0);	 Catch:{ InterruptedException -> 0x0098 }
            r1 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r5 = r1.getKeyFromQueue(r4);	 Catch:{ InterruptedException -> 0x0098 }
            if (r5 == 0) goto L_0x0180;
        L_0x0029:
            r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0081 }
            r1.<init>(r5);	 Catch:{ JSONException -> 0x0081 }
            r2 = "link";
            r6 = r1.getString(r2);	 Catch:{ JSONException -> 0x0081 }
            r2 = "data";
            r2 = r1.getString(r2);	 Catch:{ JSONException -> 0x0081 }
            r7 = "post_body";
            r7 = r1.getJSONObject(r7);	 Catch:{ JSONException -> 0x0081 }
            r8 = "first_session";
            r1 = r1.getBoolean(r8);	 Catch:{ JSONException -> 0x0081 }
            if (r1 == 0) goto L_0x0060;
        L_0x0048:
            r1 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r1 = r1.c;	 Catch:{ InterruptedException -> 0x0098 }
            r1 = r1.d;	 Catch:{ InterruptedException -> 0x0098 }
            monitor-enter(r1);	 Catch:{ InterruptedException -> 0x0098 }
            r8 = r12.a;	 Catch:{ all -> 0x0095 }
            r8 = r8.c;	 Catch:{ all -> 0x0095 }
            r8 = r8.d;	 Catch:{ all -> 0x0095 }
            r10 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;
            r8.wait(r10);	 Catch:{ all -> 0x0095 }
            monitor-exit(r1);	 Catch:{ all -> 0x0095 }
        L_0x0060:
            r1 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r1 = r1.c;	 Catch:{ InterruptedException -> 0x0098 }
            if (r1 == 0) goto L_0x0172;
        L_0x0068:
            r1 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r1 = r1.c;	 Catch:{ InterruptedException -> 0x0098 }
            r1 = r1.makeRequest(r6, r2, r7);	 Catch:{ InterruptedException -> 0x0098 }
            if (r1 == 0) goto L_0x00a7;
        L_0x0074:
            r1 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r1.removeKeyFromQueue(r4);	 Catch:{ InterruptedException -> 0x0098 }
            r4 = 0;
            com.mobileapptracker.MATEventQueue.d = r4;	 Catch:{ InterruptedException -> 0x0098 }
        L_0x007e:
            r0 = r0 + 1;
            goto L_0x001b;
        L_0x0081:
            r0 = move-exception;
            r0.printStackTrace();	 Catch:{ InterruptedException -> 0x0098 }
            r0 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r0.removeKeyFromQueue(r4);	 Catch:{ InterruptedException -> 0x0098 }
            r0 = r12.a;
            r0 = r0.b;
            r0.release();
            goto L_0x0008;
        L_0x0095:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ InterruptedException -> 0x0098 }
            throw r0;	 Catch:{ InterruptedException -> 0x0098 }
        L_0x0098:
            r0 = move-exception;
            r0.printStackTrace();	 Catch:{ all -> 0x011c }
            r0 = r12.a;
            r0 = r0.b;
            r0.release();
            goto L_0x0008;
        L_0x00a7:
            r2 = r0 + -1;
            r0 = "&sdk_retry_attempt=";
            r0 = r6.indexOf(r0);	 Catch:{ InterruptedException -> 0x0098 }
            if (r0 <= 0) goto L_0x00e8;
        L_0x00b1:
            r1 = -1;
            r7 = r0 + 19;
            r0 = r7 + 1;
        L_0x00b6:
            r8 = r6.substring(r7, r0);	 Catch:{ StringIndexOutOfBoundsException -> 0x00c1 }
            r1 = java.lang.Integer.parseInt(r8);	 Catch:{ NumberFormatException -> 0x0199 }
            r0 = r0 + 1;
            goto L_0x00b6;
        L_0x00c1:
            r0 = move-exception;
        L_0x00c2:
            r0 = r1 + 1;
            r1 = "&sdk_retry_attempt=\\d+";
            r7 = new java.lang.StringBuilder;	 Catch:{ InterruptedException -> 0x0098 }
            r8 = "&sdk_retry_attempt=";
            r7.<init>(r8);	 Catch:{ InterruptedException -> 0x0098 }
            r0 = r7.append(r0);	 Catch:{ InterruptedException -> 0x0098 }
            r0 = r0.toString();	 Catch:{ InterruptedException -> 0x0098 }
            r0 = r6.replaceFirst(r1, r0);	 Catch:{ InterruptedException -> 0x0098 }
            r1 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0117 }
            r1.<init>(r5);	 Catch:{ JSONException -> 0x0117 }
            r5 = "link";
            r1.put(r5, r0);	 Catch:{ JSONException -> 0x0117 }
            r0 = r12.a;	 Catch:{ JSONException -> 0x0117 }
            r0.setQueueItemForKey(r1, r4);	 Catch:{ JSONException -> 0x0117 }
        L_0x00e8:
            r0 = com.mobileapptracker.MATEventQueue.d;	 Catch:{ InterruptedException -> 0x0098 }
            r4 = 0;
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 != 0) goto L_0x0127;
        L_0x00f2:
            r0 = 30;
            com.mobileapptracker.MATEventQueue.d = r0;	 Catch:{ InterruptedException -> 0x0098 }
        L_0x00f7:
            r0 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
            r4 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
            r6 = java.lang.Math.random();	 Catch:{ InterruptedException -> 0x0098 }
            r4 = r4 * r6;
            r0 = r0 + r4;
            r4 = com.mobileapptracker.MATEventQueue.d;	 Catch:{ InterruptedException -> 0x0098 }
            r4 = (double) r4;
            r0 = r0 * r4;
            r4 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
            r0 = r0 * r4;
            r0 = (long) r0;
            java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x016e }
            r0 = r2;
            goto L_0x007e;
        L_0x0117:
            r0 = move-exception;
            r0.printStackTrace();	 Catch:{ InterruptedException -> 0x0098 }
            goto L_0x00e8;
        L_0x011c:
            r0 = move-exception;
            r1 = r12.a;
            r1 = r1.b;
            r1.release();
            throw r0;
        L_0x0127:
            r0 = com.mobileapptracker.MATEventQueue.d;	 Catch:{ InterruptedException -> 0x0098 }
            r4 = 30;
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 > 0) goto L_0x0137;
        L_0x0131:
            r0 = 90;
            com.mobileapptracker.MATEventQueue.d = r0;	 Catch:{ InterruptedException -> 0x0098 }
            goto L_0x00f7;
        L_0x0137:
            r0 = com.mobileapptracker.MATEventQueue.d;	 Catch:{ InterruptedException -> 0x0098 }
            r4 = 90;
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 > 0) goto L_0x0147;
        L_0x0141:
            r0 = 600; // 0x258 float:8.41E-43 double:2.964E-321;
            com.mobileapptracker.MATEventQueue.d = r0;	 Catch:{ InterruptedException -> 0x0098 }
            goto L_0x00f7;
        L_0x0147:
            r0 = com.mobileapptracker.MATEventQueue.d;	 Catch:{ InterruptedException -> 0x0098 }
            r4 = 600; // 0x258 float:8.41E-43 double:2.964E-321;
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 > 0) goto L_0x0157;
        L_0x0151:
            r0 = 3600; // 0xe10 float:5.045E-42 double:1.7786E-320;
            com.mobileapptracker.MATEventQueue.d = r0;	 Catch:{ InterruptedException -> 0x0098 }
            goto L_0x00f7;
        L_0x0157:
            r0 = com.mobileapptracker.MATEventQueue.d;	 Catch:{ InterruptedException -> 0x0098 }
            r4 = 3600; // 0xe10 float:5.045E-42 double:1.7786E-320;
            r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
            if (r0 > 0) goto L_0x0167;
        L_0x0161:
            r0 = 21600; // 0x5460 float:3.0268E-41 double:1.0672E-319;
            com.mobileapptracker.MATEventQueue.d = r0;	 Catch:{ InterruptedException -> 0x0098 }
            goto L_0x00f7;
        L_0x0167:
            r0 = 86400; // 0x15180 float:1.21072E-40 double:4.26873E-319;
            com.mobileapptracker.MATEventQueue.d = r0;	 Catch:{ InterruptedException -> 0x0098 }
            goto L_0x00f7;
        L_0x016e:
            r0 = move-exception;
            r0 = r2;
            goto L_0x007e;
        L_0x0172:
            r1 = "MobileAppTracker";
            r2 = "Dropping queued request because no MAT object was found";
            android.util.Log.d(r1, r2);	 Catch:{ InterruptedException -> 0x0098 }
            r1 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r1.removeKeyFromQueue(r4);	 Catch:{ InterruptedException -> 0x0098 }
            goto L_0x007e;
        L_0x0180:
            r1 = "MobileAppTracker";
            r2 = "Null request skipped from queue";
            android.util.Log.d(r1, r2);	 Catch:{ InterruptedException -> 0x0098 }
            r1 = r12.a;	 Catch:{ InterruptedException -> 0x0098 }
            r1.removeKeyFromQueue(r4);	 Catch:{ InterruptedException -> 0x0098 }
            goto L_0x007e;
        L_0x018e:
            r0 = r12.a;
            r0 = r0.b;
            r0.release();
            goto L_0x0008;
        L_0x0199:
            r0 = move-exception;
            goto L_0x00c2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.mobileapptracker.MATEventQueue.Dump.run():void");
        }
    }

    public MATEventQueue(Context context, MobileAppTracker mat) {
        this.a = context.getSharedPreferences("mat_queue", 0);
        this.c = mat;
    }

    protected synchronized String getKeyFromQueue(String key) {
        return this.a.getString(key, null);
    }

    protected synchronized int getQueueSize() {
        return this.a.getInt("queuesize", 0);
    }

    protected synchronized void removeKeyFromQueue(String key) {
        setQueueSize(getQueueSize() - 1);
        Editor edit = this.a.edit();
        edit.remove(key);
        edit.commit();
    }

    protected synchronized void setQueueItemForKey(JSONObject item, String key) {
        Editor edit = this.a.edit();
        edit.putString(key, item.toString());
        edit.commit();
    }

    protected synchronized void setQueueSize(int size) {
        Editor edit = this.a.edit();
        if (size < 0) {
            size = 0;
        }
        edit.putInt("queuesize", size);
        edit.commit();
    }
}
