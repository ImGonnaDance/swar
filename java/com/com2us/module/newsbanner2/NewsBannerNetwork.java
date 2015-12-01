package com.com2us.module.newsbanner2;

import android.content.Context;
import com.com2us.module.mercury.MercuryDefine;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import com.com2us.module.util.WrapperUtility;
import com.com2us.peppermint.PeppermintConstant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;
import jp.co.cyberz.fox.a.a.i;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewsBannerNetwork {
    public static final int REQUEST_ACTION = 2;
    public static final int REQUEST_ACTION_SINGLE_GAME = 3;
    public static final int REQUEST_BANNER_INFO = 0;
    public static final int REQUEST_OPEN = 1;
    public static final int REQUEST_REWARD = 4;
    public static final int REWARD_NETWORK_GAME = 2;
    public static final int REWARD_NOMAL_BANNER = 0;
    public static final int REWARD_SINGLE_GAME = 1;
    private static Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private Context context;
    private ArrayList<String> cpiPIDList = new ArrayList();
    private NewsBanner newsBanner;
    final NetworkListener responseListener;

    interface NetworkListener {
        void responseBannerInfo(BannerData[] bannerDataArr, Setting setting, CPI cpi, Tab tab);

        void responseReward(int i, int i2);
    }

    class BannerData {
        public String horizonalImgHash;
        public String horizonalImgUrl;
        public String linkType;
        public String linkUrl;
        public int pid;
        public int reward;
        public String verticalImgHash;
        public String verticalImgUrl;

        BannerData() {
        }
    }

    class CPI {
        public String msg;
        public String msg_cancel;
        public String msg_ok;

        CPI() {
        }
    }

    class Setting {
        public int animationPlayTime;
        public int autoShowPeriod;
        public int rollingPeriod;
        public long visibleTimeAfterAutoShow;

        Setting() {
        }

        public String toString() {
            return "visibleTimeAfterAutoShow=" + this.visibleTimeAfterAutoShow + i.b + "rollingPeriod=" + this.rollingPeriod + i.b + "animationPlayTime=" + this.animationPlayTime + i.b + "autoShowPeriod=" + this.autoShowPeriod;
        }
    }

    class Tab {
        public String imagehash_ceiling_close;
        public String imagehash_ceiling_open;
        public String imagehash_ground_close;
        public String imagehash_ground_open;
        public String imageurl_ceiling_close;
        public String imageurl_ceiling_open;
        public String imageurl_ground_close;
        public String imageurl_ground_open;

        Tab() {
        }
    }

    public NewsBannerNetwork(NewsBanner newsBanner, NetworkListener listener, Context context) {
        this.newsBanner = newsBanner;
        this.responseListener = listener;
        this.context = context;
        String pids = NewsBannerProperties.getProperty(NewsBannerProperties.CPI_PIDS_PROPERTY);
        if (pids != null) {
            StringTokenizer st = new StringTokenizer(pids, "|");
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                if (!this.cpiPIDList.contains(token)) {
                    this.cpiPIDList.add(token);
                }
            }
        }
    }

    public ArrayList<String> getClickedBannerPidList() {
        return this.cpiPIDList;
    }

    public void request(final int type, final int pid) {
        new Thread() {
            public void run() {
                boolean z = true;
                try {
                    String[] sendData = NewsBannerNetwork.this.makeSendData(type, pid);
                    NewsBannerNetwork newsBannerNetwork = NewsBannerNetwork.this;
                    String str = sendData[NewsBannerNetwork.REWARD_NOMAL_BANNER];
                    String str2 = sendData[NewsBannerNetwork.REWARD_SINGLE_GAME];
                    if (type != 0) {
                        z = false;
                    }
                    String responseData = newsBannerNetwork.requestToServer(str, str2, z);
                    if (responseData != null) {
                        NewsBannerNetwork.this.parseReceivedData(type, responseData, pid);
                    }
                } catch (Exception e) {
                    NewsBannerNetwork.this.newsBanner.runNewsBannerCallback(-7);
                    throw e;
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }.start();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String[] makeSendData(int r14, int r15) {
        /*
        r13 = this;
        r12 = 1;
        r11 = 2;
        r5 = com.com2us.module.newsbanner2.Constants.Network.TARGET_SERVER;
        r2 = new org.json.JSONObject;
        r2.<init>();
        r0 = com.com2us.module.newsbanner2.NewsBannerData.get(r12);
        r8 = "appid";
        r2.put(r8, r0);	 Catch:{ Exception -> 0x00c5 }
        r8 = 9;
        r3 = com.com2us.module.newsbanner2.NewsBannerData.get(r8);	 Catch:{ Exception -> 0x00c5 }
        r8 = "mac";
        if (r3 == 0) goto L_0x004f;
    L_0x001c:
        r2.put(r8, r3);	 Catch:{ Exception -> 0x00c5 }
        r8 = "udid";
        r9 = 4;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        switch(r14) {
            case 0: goto L_0x0052;
            case 1: goto L_0x00cc;
            case 2: goto L_0x0102;
            case 3: goto L_0x0102;
            case 4: goto L_0x015a;
            default: goto L_0x002c;
        };
    L_0x002c:
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r10 = "[NewsBannerNetwork][makeSendData]";
        r9.<init>(r10);
        r10 = r2.toString();
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.v(r9);
        r9 = new java.lang.String[r11];
        r8 = 0;
        r9[r8] = r5;
        if (r2 != 0) goto L_0x017f;
    L_0x004b:
        r8 = 0;
    L_0x004c:
        r9[r12] = r8;
        return r9;
    L_0x004f:
        r3 = "";
        goto L_0x001c;
    L_0x0052:
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c5 }
        r9 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x00c5 }
        r8.<init>(r9);	 Catch:{ Exception -> 0x00c5 }
        r9 = "request.c2s";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x00c5 }
        r5 = r8.toString();	 Catch:{ Exception -> 0x00c5 }
        r8 = "libver";
        r9 = com.com2us.module.newsbanner2.Constants.Version;	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "appver";
        r9 = 2;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "appvercode";
        r9 = 13;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "devicetype";
        r9 = 3;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "lan";
        r9 = 11;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "con";
        r9 = 6;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "osver";
        r9 = 5;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "width";
        r9 = com.com2us.module.newsbanner2.NewsBannerData.applicationScreenWidth;	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "height";
        r9 = com.com2us.module.newsbanner2.NewsBannerData.applicationScreenHeight;	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "did";
        r9 = 14;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        goto L_0x002c;
    L_0x00c5:
        r1 = move-exception;
        r1.printStackTrace();
        r2 = 0;
        goto L_0x002c;
    L_0x00cc:
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c5 }
        r9 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x00c5 }
        r8.<init>(r9);	 Catch:{ Exception -> 0x00c5 }
        r9 = "open.c2s";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x00c5 }
        r5 = r8.toString();	 Catch:{ Exception -> 0x00c5 }
        r8 = "pid";
        r2.put(r8, r15);	 Catch:{ Exception -> 0x00c5 }
        r8 = "did";
        r9 = 14;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "appver";
        r9 = 2;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "libver";
        r9 = com.com2us.module.newsbanner2.Constants.Version;	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        goto L_0x002c;
    L_0x0102:
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c5 }
        r9 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x00c5 }
        r8.<init>(r9);	 Catch:{ Exception -> 0x00c5 }
        r9 = "action.c2s";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x00c5 }
        r5 = r8.toString();	 Catch:{ Exception -> 0x00c5 }
        r8 = "pid";
        r2.put(r8, r15);	 Catch:{ Exception -> 0x00c5 }
        r8 = 12;
        r4 = com.com2us.module.newsbanner2.NewsBannerData.get(r8);	 Catch:{ Exception -> 0x00c5 }
        r8 = "did";
        r9 = 14;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "appver";
        r9 = 2;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r8 = "libver";
        r9 = com.com2us.module.newsbanner2.Constants.Version;	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        r6 = 0;
        if (r4 == 0) goto L_0x014e;
    L_0x0140:
        r8 = r4.trim();	 Catch:{ Exception -> 0x00c5 }
        r8 = r8.length();	 Catch:{ Exception -> 0x00c5 }
        if (r8 <= 0) goto L_0x014e;
    L_0x014a:
        r6 = java.lang.Long.parseLong(r4);	 Catch:{ Exception -> 0x0155 }
    L_0x014e:
        r8 = "uid";
        r2.put(r8, r6);	 Catch:{ Exception -> 0x00c5 }
        goto L_0x002c;
    L_0x0155:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ Exception -> 0x00c5 }
        goto L_0x014e;
    L_0x015a:
        r8 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00c5 }
        r9 = java.lang.String.valueOf(r5);	 Catch:{ Exception -> 0x00c5 }
        r8.<init>(r9);	 Catch:{ Exception -> 0x00c5 }
        r9 = "reward.c2s";
        r8 = r8.append(r9);	 Catch:{ Exception -> 0x00c5 }
        r5 = r8.toString();	 Catch:{ Exception -> 0x00c5 }
        r8 = "pid";
        r2.put(r8, r15);	 Catch:{ Exception -> 0x00c5 }
        r8 = "did";
        r9 = 14;
        r9 = com.com2us.module.newsbanner2.NewsBannerData.get(r9);	 Catch:{ Exception -> 0x00c5 }
        r2.put(r8, r9);	 Catch:{ Exception -> 0x00c5 }
        goto L_0x002c;
    L_0x017f:
        r8 = r2.toString();
        goto L_0x004c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.newsbanner2.NewsBannerNetwork.makeSendData(int, int):java.lang.String[]");
    }

    private String requestToServer(String url, String data, boolean checkHash) throws NetworkDataException {
        Exception e;
        Throwable th;
        logger.v("[NewsBannerNetwork][requestToServer] has been called :" + url + i.b + data);
        String responseStr = null;
        HttpPost httpPost = null;
        HttpClient client = null;
        HttpParams httpClientParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpClientParams, 10000);
        HttpConnectionParams.setSoTimeout(httpClientParams, 10000);
        try {
            HttpClient client2 = new DefaultHttpClient(httpClientParams);
            try {
                StringEntity entity = new StringEntity(data, "UTF-8");
                HttpPost httpPost2 = new HttpPost(url);
                try {
                    httpPost2.setHeader("Content-type", "text/html");
                    httpPost2.setEntity(entity);
                    HttpResponse response = client2.execute(httpPost2);
                    if (response.getStatusLine().getStatusCode() == SelectTarget.TARGETING_SUCCESS) {
                        logger.d("[NewsBannerNetwork][requestToServer]HttpStatus.SC_OK");
                        HttpEntity responseEntity = response.getEntity();
                        if (responseEntity != null) {
                            responseStr = EntityUtils.toString(responseEntity);
                            responseEntity.consumeContent();
                            if (checkHash) {
                                try {
                                    if (response.getFirstHeader("newshash").getValue().trim().equals(WrapperUtility.getHashHex(responseStr.getBytes(), "SHA-1"))) {
                                        logger.d("[NewsBannerNetwork][requestToServer]hash value is correct");
                                    } else {
                                        throw new Exception("[NewsBannerNetwork][requestToServer]hash value is wrong");
                                    }
                                } catch (Exception e2) {
                                    logger.d("[NewsBannerNetwork][requestToServer]", e2);
                                    this.newsBanner.runNewsBannerCallback(-7);
                                    responseStr = null;
                                }
                            }
                        }
                    }
                    client2.getConnectionManager().shutdown();
                    client = client2;
                    httpPost = httpPost2;
                } catch (RuntimeException e3) {
                    e2 = e3;
                    client = client2;
                    httpPost = httpPost2;
                    try {
                        logger.d("[NewsBannerNetwork][requestToServer]", e2);
                        this.newsBanner.runNewsBannerCallback(-2);
                        httpPost.abort();
                        client.getConnectionManager().shutdown();
                        return responseStr;
                    } catch (Throwable th2) {
                        th = th2;
                        client.getConnectionManager().shutdown();
                        throw th;
                    }
                } catch (IOException e4) {
                    e2 = e4;
                    client = client2;
                    httpPost = httpPost2;
                    logger.d("[NewsBannerNetwork][requestToServer]", e2);
                    this.newsBanner.runNewsBannerCallback(-2);
                    client.getConnectionManager().shutdown();
                    return responseStr;
                } catch (Throwable th3) {
                    th = th3;
                    client = client2;
                    httpPost = httpPost2;
                    client.getConnectionManager().shutdown();
                    throw th;
                }
            } catch (RuntimeException e5) {
                e2 = e5;
                client = client2;
                logger.d("[NewsBannerNetwork][requestToServer]", e2);
                this.newsBanner.runNewsBannerCallback(-2);
                httpPost.abort();
                client.getConnectionManager().shutdown();
                return responseStr;
            } catch (IOException e6) {
                e2 = e6;
                client = client2;
                logger.d("[NewsBannerNetwork][requestToServer]", e2);
                this.newsBanner.runNewsBannerCallback(-2);
                client.getConnectionManager().shutdown();
                return responseStr;
            } catch (Throwable th4) {
                th = th4;
                client = client2;
                client.getConnectionManager().shutdown();
                throw th;
            }
        } catch (RuntimeException e7) {
            e2 = e7;
            logger.d("[NewsBannerNetwork][requestToServer]", e2);
            this.newsBanner.runNewsBannerCallback(-2);
            httpPost.abort();
            client.getConnectionManager().shutdown();
            return responseStr;
        } catch (IOException e8) {
            e2 = e8;
            logger.d("[NewsBannerNetwork][requestToServer]", e2);
            this.newsBanner.runNewsBannerCallback(-2);
            client.getConnectionManager().shutdown();
            return responseStr;
        }
        return responseStr;
    }

    private void parseReceivedData(int type, String data, int pid) throws Exception {
        logger.v("[NewsBannerNetwork][parseReceivedData]type=" + type + " , " + data);
        switch (type) {
            case REWARD_NOMAL_BANNER /*0*/:
                JSONObject bannerJObj = new JSONObject(data);
                Setting setting = new Setting();
                int bannerCount = bannerJObj.getInt("count");
                if (bannerCount == 0) {
                    this.newsBanner.runNewsBannerCallback(-4);
                    return;
                }
                JSONObject settingJObj = bannerJObj.getJSONObject("setting");
                setting.visibleTimeAfterAutoShow = (long) settingJObj.getInt("delay");
                if (setting.visibleTimeAfterAutoShow == 0) {
                    setting.visibleTimeAfterAutoShow = 3600;
                }
                setting.animationPlayTime = settingJObj.getInt("delay_ani");
                if (setting.animationPlayTime == 0) {
                    setting.animationPlayTime = 300;
                }
                setting.autoShowPeriod = settingJObj.getInt("delay_time");
                if (setting.autoShowPeriod == 0) {
                    setting.autoShowPeriod = 3600;
                }
                setting.rollingPeriod = settingJObj.getInt("delay_roll");
                if (setting.rollingPeriod == 0) {
                    setting.rollingPeriod = 10;
                }
                JSONArray bannerJArr = bannerJObj.getJSONArray("banner");
                BannerData[] bannerData = new BannerData[bannerCount];
                for (int i = REWARD_NOMAL_BANNER; i < bannerCount; i += REWARD_SINGLE_GAME) {
                    bannerData[i] = new BannerData();
                    BannerData bannerData2 = bannerData[i];
                    bannerData2.pid = bannerJArr.getJSONObject(i).getInt("pid");
                    bannerData2 = bannerData[i];
                    bannerData2.verticalImgUrl = bannerJArr.getJSONObject(i).getString("imageurl_ver");
                    bannerData2 = bannerData[i];
                    bannerData2.horizonalImgUrl = bannerJArr.getJSONObject(i).getString("imageurl_hor");
                    bannerData2 = bannerData[i];
                    bannerData2.reward = bannerJArr.getJSONObject(i).optInt("reward");
                    bannerData2 = bannerData[i];
                    bannerData2.linkType = bannerJArr.getJSONObject(i).getString(PeppermintConstant.JSON_KEY_TYPE);
                    bannerData2 = bannerData[i];
                    bannerData2.linkUrl = bannerJArr.getJSONObject(i).optString("downurl");
                    try {
                        bannerData2 = bannerData[i];
                        bannerData2.verticalImgHash = bannerJArr.getJSONObject(i).getString("imagever_hash");
                        bannerData2 = bannerData[i];
                        bannerData2.horizonalImgHash = bannerJArr.getJSONObject(i).getString("imagehor_hash");
                    } catch (Exception e) {
                        logger.d("NewsBannerNetwork][parseReceivedData]No data for ImageHash", e);
                    }
                }
                CPI cpi = new CPI();
                try {
                    JSONObject cpiObj = bannerJObj.getJSONObject(MercuryDefine.WEBVIEW_TYPE_CPI);
                    cpi.msg = cpiObj.getString("msg");
                    cpi.msg_ok = cpiObj.getString("msg_ok");
                    cpi.msg_cancel = cpiObj.getString("msg_cancel");
                } catch (Exception e2) {
                    logger.d("[NewsBannerNetwork][parseReceivedData]No data for CPI", e2);
                }
                Tab tab = new Tab();
                try {
                    JSONObject tabObj = bannerJObj.getJSONObject("tab");
                    tab.imageurl_ceiling_open = tabObj.getString("imageurl_ceiling_open");
                    tab.imageurl_ceiling_close = tabObj.getString("imageurl_ceiling_close");
                    tab.imageurl_ground_open = tabObj.getString("imageurl_ground_open");
                    tab.imageurl_ground_close = tabObj.getString("imageurl_ground_close");
                    tab.imagehash_ceiling_open = tabObj.getString("imagehash_ceiling_open");
                    tab.imagehash_ceiling_close = tabObj.getString("imagehash_ceiling_close");
                    tab.imagehash_ground_open = tabObj.getString("imagehash_ground_open");
                    tab.imagehash_ground_close = tabObj.getString("imagehash_ground_close");
                    this.responseListener.responseBannerInfo(bannerData, setting, cpi, tab);
                    return;
                } catch (Exception e22) {
                    logger.d("[NewsBannerNetwork][parseReceivedData]No data for Tab", e22);
                    throw e22;
                }
            case REQUEST_ACTION_SINGLE_GAME /*3*/:
                String pid_str = String.valueOf(pid);
                if (!this.cpiPIDList.contains(pid_str)) {
                    this.cpiPIDList.add(pid_str);
                    storeCPIList();
                    return;
                }
                return;
            case REQUEST_REWARD /*4*/:
                int reward;
                JSONObject jSONObject = new JSONObject(data);
                int cpi_pid = jSONObject.getInt("pid");
                if (jSONObject.isNull("reward")) {
                    reward = REWARD_NOMAL_BANNER;
                } else {
                    reward = jSONObject.getInt("reward");
                }
                int result = jSONObject.getInt(PeppermintConstant.JSON_KEY_RESULT);
                if (result == REWARD_SINGLE_GAME || result == REWARD_NETWORK_GAME) {
                    this.cpiPIDList.remove(String.valueOf(cpi_pid));
                    storeCPIList();
                }
                this.responseListener.responseReward(result, reward);
                return;
            default:
                return;
        }
    }

    private void storeCPIList() {
        StringBuilder pids = new StringBuilder();
        Iterator it = this.cpiPIDList.iterator();
        while (it.hasNext()) {
            pids.append((String) it.next()).append("|");
        }
        NewsBannerProperties.setProperty(NewsBannerProperties.CPI_PIDS_PROPERTY, pids.toString());
        NewsBannerProperties.storeProperties(this.context);
    }
}
