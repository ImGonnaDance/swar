package com.com2us.module.offerwall;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class OfferwallNetwork {
    private String SERVER = null;

    public void setServer(String url) {
        this.SERVER = url;
    }

    public Object processNetworkTask() {
        StringEntity stringEntity;
        Throwable th;
        Offerwall.logger.d(Constants.TAG, "processNetwork");
        String responseStr = null;
        HttpPost httpPost = null;
        HttpClient client = null;
        HttpParams httpClientParams = new BasicHttpParams();
        HashMap<String, String> dataMap = new HashMap();
        dataMap.put("oq_appid", OfferwallData.get(1));
        dataMap.put("oq_mac", OfferwallData.get(2));
        dataMap.put("oq_lan", OfferwallData.get(3));
        dataMap.put("oq_con", OfferwallData.get(4));
        dataMap.put("oq_devicetype", OfferwallData.get(5));
        dataMap.put("oq_osver", OfferwallData.get(6));
        dataMap.put("oq_libver", Constants.Version);
        dataMap.put("oq_appver", OfferwallData.get(7));
        dataMap.put("oq_appvercode", OfferwallData.get(8));
        dataMap.put("oq_uid", OfferwallData.get(9));
        dataMap.put("oq_did", OfferwallData.get(10));
        String vid = OfferwallData.get(12);
        if (vid == null) {
            dataMap.put("oq_vid", "-703");
        } else {
            dataMap.put("oq_vid", vid);
        }
        dataMap.put("oq_eventid", OfferwallData.get(13));
        dataMap.put("oq_mcc", OfferwallData.get(18));
        dataMap.put("oq_mnc", OfferwallData.get(19));
        String jsonStr = new JSONObject(dataMap).toString();
        if (jsonStr == null) {
            return null;
        }
        Offerwall.logger.d(Constants.TAG, "processNetwork:" + jsonStr);
        try {
            HttpClient client2 = new DefaultHttpClient(httpClientParams);
            try {
                StringEntity entity = new StringEntity(jsonStr, "UTF-8");
                try {
                    Offerwall.logger.d(Constants.TAG, "server url :" + this.SERVER);
                    HttpPost httpPost2 = new HttpPost(this.SERVER);
                    try {
                        httpPost2.setEntity(entity);
                        httpPost2.setHeader("Content-Type", "text/html");
                        HttpResponse response = client2.execute(httpPost2);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity responseEntity = response.getEntity();
                            if (responseEntity != null) {
                                responseStr = EntityUtils.toString(responseEntity);
                                responseEntity.consumeContent();
                            }
                            if (client2 != null) {
                                client2.getConnectionManager().shutdown();
                            }
                            if (responseStr != null) {
                                client = client2;
                                httpPost = httpPost2;
                                return responseStr;
                            }
                            client = client2;
                            httpPost = httpPost2;
                            return null;
                        }
                        if (client2 != null) {
                            client2.getConnectionManager().shutdown();
                        }
                        client = client2;
                        httpPost = httpPost2;
                        return null;
                    } catch (RuntimeException e) {
                        stringEntity = entity;
                        client = client2;
                        httpPost = httpPost2;
                        try {
                            httpPost.abort();
                            if (client != null) {
                                client.getConnectionManager().shutdown();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (client != null) {
                                client.getConnectionManager().shutdown();
                            }
                            throw th;
                        }
                    } catch (IOException e2) {
                        stringEntity = entity;
                        client = client2;
                        httpPost = httpPost2;
                        if (client != null) {
                            client.getConnectionManager().shutdown();
                        }
                        return null;
                    } catch (Throwable th3) {
                        th = th3;
                        stringEntity = entity;
                        client = client2;
                        httpPost = httpPost2;
                        if (client != null) {
                            client.getConnectionManager().shutdown();
                        }
                        throw th;
                    }
                } catch (RuntimeException e3) {
                    stringEntity = entity;
                    client = client2;
                    httpPost.abort();
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    return null;
                } catch (IOException e4) {
                    stringEntity = entity;
                    client = client2;
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    stringEntity = entity;
                    client = client2;
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    throw th;
                }
            } catch (UnsupportedEncodingException e5) {
                try {
                    e5.printStackTrace();
                    if (client2 != null) {
                        client2.getConnectionManager().shutdown();
                    }
                    client = client2;
                    return null;
                } catch (RuntimeException e6) {
                    client = client2;
                    httpPost.abort();
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    return null;
                } catch (IOException e7) {
                    client = client2;
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    return null;
                } catch (Throwable th5) {
                    th = th5;
                    client = client2;
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    throw th;
                }
            }
        } catch (RuntimeException e8) {
            httpPost.abort();
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
            return null;
        } catch (IOException e9) {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
            return null;
        }
    }

    public Object processNetworkRewardTask() {
        StringEntity stringEntity;
        Throwable th;
        Offerwall.logger.d(Constants.TAG, "processNetwork_Reward");
        String responseStr = null;
        HttpPost httpPost = null;
        HttpClient client = null;
        HttpParams httpClientParams = new BasicHttpParams();
        HashMap<String, String> dataMap = new HashMap();
        dataMap.put("oq_appid", OfferwallData.get(1));
        dataMap.put("oq_did", OfferwallData.get(10));
        dataMap.put("oq_uid", OfferwallData.get(9));
        String vid = OfferwallData.get(12);
        if (vid == null) {
            dataMap.put("oq_vid", "-703");
        } else {
            dataMap.put("oq_vid", vid);
        }
        dataMap.put("oq_mac", OfferwallData.get(2));
        dataMap.put("oq_libver", Constants.Version);
        dataMap.put("oq_eventid", OfferwallProperties.getProperty("rewardEventID"));
        JSONObject jsonObj = new JSONObject(dataMap);
        String jsonStr = jsonObj.toString();
        if (jsonStr == null) {
            return null;
        }
        Offerwall.logger.d(Constants.TAG, "processNetwork_Reward:" + jsonObj.toString());
        try {
            HttpClient client2 = new DefaultHttpClient(httpClientParams);
            try {
                StringEntity entity = new StringEntity(jsonStr, "UTF-8");
                try {
                    HttpPost httpPost2 = new HttpPost(OfferwallDefine.OFFERWALL_REWARD_LOG_SERVER);
                    try {
                        httpPost2.setEntity(entity);
                        httpPost2.setHeader("Content-Type", "text/html");
                        HttpResponse response = client2.execute(httpPost2);
                        if (response.getStatusLine().getStatusCode() == 200) {
                            HttpEntity responseEntity = response.getEntity();
                            if (responseEntity != null) {
                                responseStr = EntityUtils.toString(responseEntity);
                                responseEntity.consumeContent();
                            }
                            if (client2 != null) {
                                client2.getConnectionManager().shutdown();
                            }
                            if (responseStr != null) {
                                client = client2;
                                httpPost = httpPost2;
                                return responseStr;
                            }
                            client = client2;
                            httpPost = httpPost2;
                            return null;
                        }
                        if (client2 != null) {
                            client2.getConnectionManager().shutdown();
                        }
                        client = client2;
                        httpPost = httpPost2;
                        return null;
                    } catch (RuntimeException e) {
                        stringEntity = entity;
                        client = client2;
                        httpPost = httpPost2;
                        try {
                            httpPost.abort();
                            if (client != null) {
                                client.getConnectionManager().shutdown();
                            }
                            return null;
                        } catch (Throwable th2) {
                            th = th2;
                            if (client != null) {
                                client.getConnectionManager().shutdown();
                            }
                            throw th;
                        }
                    } catch (IOException e2) {
                        stringEntity = entity;
                        client = client2;
                        httpPost = httpPost2;
                        if (client != null) {
                            client.getConnectionManager().shutdown();
                        }
                        return null;
                    } catch (Throwable th3) {
                        th = th3;
                        stringEntity = entity;
                        client = client2;
                        httpPost = httpPost2;
                        if (client != null) {
                            client.getConnectionManager().shutdown();
                        }
                        throw th;
                    }
                } catch (RuntimeException e3) {
                    stringEntity = entity;
                    client = client2;
                    httpPost.abort();
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    return null;
                } catch (IOException e4) {
                    stringEntity = entity;
                    client = client2;
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    return null;
                } catch (Throwable th4) {
                    th = th4;
                    stringEntity = entity;
                    client = client2;
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    throw th;
                }
            } catch (UnsupportedEncodingException e5) {
                try {
                    e5.printStackTrace();
                    if (client2 != null) {
                        client2.getConnectionManager().shutdown();
                    }
                    client = client2;
                    return null;
                } catch (RuntimeException e6) {
                    client = client2;
                    httpPost.abort();
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    return null;
                } catch (IOException e7) {
                    client = client2;
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    return null;
                } catch (Throwable th5) {
                    th = th5;
                    client = client2;
                    if (client != null) {
                        client.getConnectionManager().shutdown();
                    }
                    throw th;
                }
            }
        } catch (RuntimeException e8) {
            httpPost.abort();
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
            return null;
        } catch (IOException e9) {
            if (client != null) {
                client.getConnectionManager().shutdown();
            }
            return null;
        }
    }
}
