package it.partytrack.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.com2us.module.activeuser.Constants.Network.Gateway;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReferrerReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        CharSequence stringExtra = intent.getStringExtra(Gateway.REQUEST_REFERRER);
        if ("com.android.vending.INSTALL_REFERRER".equals(intent.getAction()) && stringExtra != null) {
            PrintWriter printWriter;
            try {
                OutputStream openFileOutput = context.openFileOutput("partytrack.full_referrer", 0);
                printWriter = new PrintWriter(new OutputStreamWriter(openFileOutput, "UTF-8"));
                printWriter.append(stringExtra);
                printWriter.close();
                openFileOutput.close();
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e2) {
                e2.printStackTrace();
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            Matcher matcher = Pattern.compile("adzcore(.*)adzcore").matcher(stringExtra);
            if (matcher.find()) {
                try {
                    OutputStream openFileOutput2 = context.openFileOutput("partytrack.referrer", 0);
                    printWriter = new PrintWriter(new OutputStreamWriter(openFileOutput2, "UTF-8"));
                    printWriter.append(matcher.group(1));
                    printWriter.close();
                    openFileOutput2.close();
                } catch (NullPointerException e5) {
                    e5.printStackTrace();
                } catch (UnsupportedEncodingException e22) {
                    e22.printStackTrace();
                } catch (FileNotFoundException e32) {
                    e32.printStackTrace();
                } catch (IOException e42) {
                    e42.printStackTrace();
                }
            }
        }
    }
}
