package com.adpick.advertiser.sdk;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.format.DateFormat;
import android.util.Log;
import com.mobileapptracker.MATEvent;
import jp.co.cyberz.fox.a.a.i;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class AdPickAdvertiser {
    private static String actype = i.a;
    public static Context context;
    private static String debug = "NO";
    private static String extdata = i.a;

    public static class AsyncRequest extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onCancelled() {
            super.onCancelled();
        }

        protected String doInBackground(String... urls) {
            String response = i.a;
            Log.i("oddm", "tracking : " + urls[0]);
            try {
                new DefaultHttpClient().execute(new HttpGet(urls[0]));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    public static void debug(String mode) {
        debug = mode;
    }

    public static void init(Context context, String secretkey) {
        context = context;
        try {
            String time = DateFormat.format("yy/MM/dd", System.currentTimeMillis()).toString();
            SetPref("secretkey", secretkey);
            String installed = GetPref("installed");
            String executedate = GetPref("executedate");
            String certkey = GetPref("certkey");
            String executecount = GetPref("executecount");
            if (debug == "YES") {
                Log.i("adpick", "secretkey=" + secretkey + " installd=" + installed + " executedate=" + executedate + " executecount=" + executecount + " certkey=" + certkey + " time=" + time);
            }
            if (installed.equals("done") && !executedate.equals(time)) {
                if (executecount == null || executecount.equals(i.a)) {
                    executecount = a.d;
                }
                int execnt = Integer.parseInt(executecount) + 1;
                SetPref("executecount", Integer.toString(execnt));
                SetPref("executedate", time);
                UserActivity("execute", Integer.toString(execnt));
                if (debug == "YES") {
                    Log.i("adpick", "executed " + Integer.toString(execnt));
                }
            }
            if (installed == "YES") {
                UserActivity("install", i.a);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Logined() {
        String strlogincnt = GetPref(MATEvent.LOGIN);
        Integer logincnt = Integer.valueOf(0);
        if (strlogincnt != i.a) {
            logincnt = Integer.valueOf(Integer.parseInt(strlogincnt));
        }
        logincnt = Integer.valueOf(logincnt.intValue() + 1);
        if (logincnt.intValue() <= 10) {
            SetPref(MATEvent.LOGIN, Integer.toString(logincnt.intValue()));
            UserActivity(MATEvent.LOGIN, i.a);
        }
    }

    public static void Action(String actype) {
        String stractioncnt = GetPref(actype);
        Integer actioncnt = Integer.valueOf(0);
        if (stractioncnt != i.a) {
            actioncnt = Integer.valueOf(Integer.parseInt(stractioncnt));
        }
        actioncnt = Integer.valueOf(actioncnt.intValue() + 1);
        if (actioncnt.intValue() <= 10) {
            SetPref(actype, Integer.toString(actioncnt.intValue()));
            UserActivity(actype, i.a);
        }
    }

    public static void Payment(String price) {
        if (price != i.a) {
            UserActivity("payment", price);
        }
    }

    public static void UserActivity(String actype, String strdata) {
        if (actype == "install") {
            SetPref("installed", "done");
        }
        actype = actype;
        if (strdata == i.a) {
            extdata = i.a;
        } else {
            extdata = strdata;
        }
        SendToPodgateServer();
    }

    public static String MakeUrlString() {
        String strdevice = Build.DEVICE;
        String strproduct = Build.PRODUCT;
        String strversion = VERSION.RELEASE;
        String strlocale = context.getResources().getConfiguration().locale.getCountry();
        String url = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf("http://pub.podgate.com/cpi.app?secret=" + GetPref("secretkey"))).append("&device=").append(strdevice).toString())).append("&version=").append(strversion).toString())).append("&product=").append(strproduct).toString())).append("&locale=").append(strlocale).toString())).append("&app=").append(context.getApplicationContext().getPackageName()).toString())).append("&actype=").append(actype).toString())).append("&extdata=").append(extdata).toString())).append("&certkey=").append(GetPref("certkey")).toString();
        if (debug == "YES") {
            Log.i("adpick", "tracking : " + url);
        }
        return url;
    }

    public static String GetPref(String getkey) {
        return context.getSharedPreferences("PodgateADKeys", 0).getString(getkey, i.a);
    }

    public static void SetPref(String setkey, String value) {
        Editor ed = context.getSharedPreferences("PodgateADKeys", 0).edit();
        ed.putString(setkey, value);
        ed.commit();
    }

    public static void SendToPodgateServer() {
        new AsyncRequest().execute(new String[]{MakeUrlString()});
    }
}
