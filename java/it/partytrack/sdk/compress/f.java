package it.partytrack.sdk.compress;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public final class f {
    int a;
    String f123a;
    private Map f124a;
    String b;
    String c;

    public f(Map map) {
        this.f124a = map;
    }

    private String a() {
        List<NameValuePair> arrayList = new ArrayList();
        Iterator it = new ArrayList(this.f124a.keySet()).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            arrayList.add(new BasicNameValuePair(str, (String) this.f124a.get(str)));
        }
        StringBuilder stringBuilder = new StringBuilder();
        Object obj = 1;
        for (NameValuePair nameValuePair : arrayList) {
            if (obj != null) {
                obj = null;
            } else {
                stringBuilder.append("&");
            }
            stringBuilder.append(URLEncoder.encode(nameValuePair.getName(), "UTF-8"));
            stringBuilder.append("=");
            stringBuilder.append(URLEncoder.encode(nameValuePair.getValue(), "UTF-8"));
        }
        "params: " + stringBuilder.toString();
        return stringBuilder.toString();
    }

    private void a(InputStream inputStream, String str) {
        if (this.a == SelectTarget.TARGETING_SUCCESS) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                try {
                    stringBuilder.append(readLine);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.c = stringBuilder.toString();
        } else if (this.a == 302) {
            this.b = str;
        }
    }

    private void a(OutputStream outputStream) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(a());
            bufferedWriter.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    URL a(String str, String str2, String str3) {
        try {
            if (this.f123a == "GET") {
                return new URL(String.format(Locale.ENGLISH, "%s://%s%s?%s", new Object[]{str, str2, a.b(str3), a()}));
            }
            return new URL(String.format(Locale.ENGLISH, "%s://%s%s", new Object[]{str, str2, a.b(str3)}));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    void a(URL url) {
        if (url != null) {
            url.toString();
            try {
                if (url.getProtocol().equals("https")) {
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    httpsURLConnection.setReadTimeout(10000);
                    httpsURLConnection.setConnectTimeout(10000);
                    httpsURLConnection.setInstanceFollowRedirects(false);
                    if (this.f123a == "POST") {
                        httpsURLConnection.setRequestMethod(this.f123a);
                        httpsURLConnection.setDoInput(true);
                        httpsURLConnection.setDoOutput(true);
                        a(httpsURLConnection.getOutputStream());
                    }
                    this.a = httpsURLConnection.getResponseCode();
                    a(httpsURLConnection.getInputStream(), httpsURLConnection.getHeaderField("Location"));
                    return;
                }
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setInstanceFollowRedirects(false);
                if (this.f123a == "POST") {
                    httpURLConnection.setRequestMethod(this.f123a);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);
                    a(httpURLConnection.getOutputStream());
                }
                this.a = httpURLConnection.getResponseCode();
                a(httpURLConnection.getInputStream(), httpURLConnection.getHeaderField("Location"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
