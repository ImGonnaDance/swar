package kr.co.cashslide;

import android.content.Context;
import android.os.Build.VERSION;
import com.facebook.internal.ServerProtocol;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import jp.co.cyberz.fox.a.a.i;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

class Request {
    private static final int CPA_END = 9;
    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 5;
    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 100;
    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = 15000;
    private static final String SERVER_URL = "http://sdk.cashslide.co.kr";
    private static final String SUCCESS_MESSAGE = "success";
    private static final String UPLOAD_CLICK_URL = "http://sdk.cashslide.co.kr/click_infos_sdk_encrypt";
    private String mAppId;
    private Context mContext;
    private int mReward = 0;

    public Request(Context context, String appId) {
        this.mContext = context;
        this.mAppId = appId;
        disableConnectionReuseIfNecessary();
    }

    private void disableConnectionReuseIfNecessary() {
        if (Integer.parseInt(VERSION.SDK) < 8) {
            System.setProperty("http.keepAlive", "false");
        }
    }

    private boolean sendPost(URI uri, List<NameValuePair> parameters) {
        String responseStr;
        if (Integer.parseInt(VERSION.SDK) >= CPA_END) {
            responseStr = sendPostHttpUrlConnection(uri, parameters);
        } else {
            responseStr = sendPostHttpClient(uri, parameters);
        }
        if (responseStr == null) {
            return false;
        }
        try {
            this.mReward = Integer.parseInt(responseStr.substring(SUCCESS_MESSAGE.length()).replaceAll("\n", i.a));
        } catch (Exception e) {
        }
        return true;
    }

    private String sendPostHttpUrlConnection(URI uri, List<NameValuePair> parameters) {
        String responseStr;
        Throwable th;
        HttpURLConnection urlConnection = null;
        OutputStream out = null;
        BufferedReader bufferedReader = null;
        try {
            urlConnection = (HttpURLConnection) uri.toURL().openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod("POST");
            StringBuffer sb = new StringBuffer();
            for (NameValuePair pair : parameters) {
                sb.append(pair.getName()).append("=").append(URLEncoder.encode(pair.getValue())).append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
            OutputStream out2 = new BufferedOutputStream(urlConnection.getOutputStream());
            try {
                out2.write(sb.toString().getBytes());
                out2.flush();
                out2.close();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                try {
                    StringBuffer stringBuffer = new StringBuffer(i.a);
                    String line = i.a;
                    String LineSeparator = System.getProperty("line.separator");
                    while (true) {
                        line = bufferedReader2.readLine();
                        if (line == null) {
                            break;
                        }
                        stringBuffer.append(new StringBuilder(String.valueOf(line)).append(LineSeparator).toString());
                    }
                    bufferedReader2.close();
                    responseStr = stringBuffer.toString();
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (IOException e) {
                            responseStr = null;
                        }
                    }
                    if (bufferedReader2 != null) {
                        try {
                            bufferedReader2.close();
                            bufferedReader = bufferedReader2;
                            out = out2;
                        } catch (IOException e2) {
                            responseStr = null;
                            bufferedReader = bufferedReader2;
                            out = out2;
                        }
                    } else {
                        out = out2;
                    }
                } catch (MalformedURLException e3) {
                    bufferedReader = bufferedReader2;
                    out = out2;
                } catch (IOException e4) {
                    bufferedReader = bufferedReader2;
                    out = out2;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedReader = bufferedReader2;
                    out = out2;
                }
            } catch (MalformedURLException e5) {
                out = out2;
                responseStr = null;
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e6) {
                        responseStr = null;
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e7) {
                        responseStr = null;
                    }
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                return responseStr;
            } catch (IOException e8) {
                out = out2;
                responseStr = null;
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e9) {
                        responseStr = null;
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e10) {
                        responseStr = null;
                    }
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                return responseStr;
            } catch (Throwable th3) {
                th = th3;
                out = out2;
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e11) {
                    }
                }
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e12) {
                    }
                }
                throw th;
            }
        } catch (MalformedURLException e13) {
        } catch (IOException e14) {
        } catch (Throwable th4) {
            th = th4;
        }
        if (urlConnection != null) {
            urlConnection.disconnect();
        }
        return responseStr;
    }

    private String sendPostHttpClient(URI uri, List<NameValuePair> parameters) {
        Throwable th;
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(WebClient.INTENT_PROTOCOL_START_HTTP, PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        HttpParams params = new BasicHttpParams();
        ThreadSafeClientConnManager connectionManager = new ThreadSafeClientConnManager(params, schemeRegistry);
        ConnManagerParams.setMaxTotalConnections(params, DEFAULT_MAX_TOTAL_CONNECTIONS);
        ConnManagerParams.setMaxConnectionsPerRoute(params, new ConnPerRouteBean(DEFAULT_MAX_CONNECTIONS_PER_ROUTE));
        HttpClient httpClient = new DefaultHttpClient(connectionManager, null);
        httpClient.getParams().setIntParameter("http.socket.timeout", DEFAULT_READ_TIMEOUT_MILLISECONDS);
        HttpUriRequest post = new HttpPost(uri);
        HttpProtocolParams.setUseExpectContinue(post.getParams(), false);
        BufferedReader bufferedReader = null;
        try {
            ((HttpEntityEnclosingRequest) post).setEntity(new UrlEncodedFormEntity(parameters));
            BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(httpClient.execute(post).getEntity().getContent()));
            try {
                StringBuffer stringBuffer = new StringBuffer(i.a);
                String line = i.a;
                String LineSeparator = System.getProperty("line.separator");
                while (true) {
                    line = bufferedReader2.readLine();
                    if (line == null) {
                        break;
                    }
                    stringBuffer.append(new StringBuilder(String.valueOf(line)).append(LineSeparator).toString());
                }
                bufferedReader2.close();
                String responseStr = stringBuffer.toString();
                if (bufferedReader2 != null) {
                    try {
                        bufferedReader2.close();
                        bufferedReader = bufferedReader2;
                        return responseStr;
                    } catch (IOException e) {
                        bufferedReader = bufferedReader2;
                        return null;
                    }
                }
                return responseStr;
            } catch (ClientProtocolException e2) {
                bufferedReader = bufferedReader2;
            } catch (IOException e3) {
                bufferedReader = bufferedReader2;
            } catch (Throwable th2) {
                th = th2;
                bufferedReader = bufferedReader2;
            }
        } catch (ClientProtocolException e4) {
            if (bufferedReader == null) {
                return null;
            }
            try {
                bufferedReader.close();
                return null;
            } catch (IOException e5) {
                return null;
            }
        } catch (IOException e6) {
            if (bufferedReader == null) {
                return null;
            }
            try {
                bufferedReader.close();
                return null;
            } catch (IOException e7) {
                return null;
            }
        } catch (Throwable th3) {
            th = th3;
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e8) {
                }
            }
            throw th;
        }
    }

    private List<NameValuePair> paramsList() {
        String deviceId = DeviceIdManager.getDeviceId(this.mContext);
        List<NameValuePair> postParameters = new ArrayList();
        postParameters.add(new BasicNameValuePair("device_id", deviceId));
        postParameters.add(new BasicNameValuePair("click_info[count]", a.e));
        postParameters.add(new BasicNameValuePair("click_info[ad_type]", "9"));
        postParameters.add(new BasicNameValuePair(ServerProtocol.FALLBACK_DIALOG_PARAM_APP_ID, this.mAppId));
        return postParameters;
    }

    public boolean sendAppFirstLaunched() {
        boolean result = false;
        try {
            result = sendPost(new URI(UPLOAD_CLICK_URL), paramsList());
        } catch (Exception e) {
        }
        return result;
    }

    public int getReward() {
        return this.mReward;
    }

    public boolean sendMissionCompleted() {
        boolean result = false;
        try {
            result = sendPost(new URI(UPLOAD_CLICK_URL), paramsList());
        } catch (Exception e) {
        }
        return result;
    }
}
