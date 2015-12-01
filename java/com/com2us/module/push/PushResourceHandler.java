package com.com2us.module.push;

import android.content.Context;
import android.content.Intent;
import com.com2us.peppermint.PeppermintConstant;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

public class PushResourceHandler {
    private static final String PUSH_FILE_NAME = "push.dat";

    public static synchronized void save(Context context, LinkedHashMap<String, PushResource> map) {
        Throwable th;
        Exception e;
        synchronized (PushResourceHandler.class) {
            ObjectOutputStream oos = null;
            try {
                ObjectOutputStream oos2 = new ObjectOutputStream(new BufferedOutputStream(context.openFileOutput(PUSH_FILE_NAME, 0)));
                try {
                    oos2.writeObject(map);
                    if (oos2 != null) {
                        try {
                            oos2.close();
                            oos = oos2;
                        } catch (Exception e2) {
                            oos = oos2;
                        } catch (Throwable th2) {
                            th = th2;
                            oos = oos2;
                            throw th;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    oos = oos2;
                    try {
                        if (PushConfig.LOG) {
                            e.printStackTrace();
                        }
                        if (oos != null) {
                            try {
                                oos.close();
                            } catch (Exception e4) {
                            }
                        }
                        PushConfig.LogI("[PushResourceHandler]save: " + map.toString());
                    } catch (Throwable th3) {
                        th = th3;
                        if (oos != null) {
                            try {
                                oos.close();
                            } catch (Exception e5) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    oos = oos2;
                    if (oos != null) {
                        oos.close();
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                if (PushConfig.LOG) {
                    e.printStackTrace();
                }
                if (oos != null) {
                    oos.close();
                }
                PushConfig.LogI("[PushResourceHandler]save: " + map.toString());
            }
            try {
                PushConfig.LogI("[PushResourceHandler]save: " + map.toString());
            } catch (Throwable th5) {
                th = th5;
            }
        }
    }

    public static synchronized LinkedHashMap<String, PushResource> load(Context context) {
        LinkedHashMap<String, PushResource> map;
        Exception e;
        Throwable th;
        synchronized (PushResourceHandler.class) {
            map = new LinkedHashMap();
            ObjectInputStream ois = null;
            try {
                ObjectInputStream ois2 = new ObjectInputStream(new BufferedInputStream(context.openFileInput(PUSH_FILE_NAME)));
                try {
                    map = (LinkedHashMap) ois2.readObject();
                    if (ois2 != null) {
                        try {
                            ois2.close();
                            ois = ois2;
                        } catch (Exception e2) {
                            ois = ois2;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    ois = ois2;
                    try {
                        if (PushConfig.LOG) {
                            e.printStackTrace();
                        }
                        if (ois != null) {
                            try {
                                ois.close();
                            } catch (Exception e4) {
                            }
                        }
                        PushConfig.LogI("[PushResourceHandler]load: " + map.toString());
                        return map;
                    } catch (Throwable th2) {
                        th = th2;
                        if (ois != null) {
                            try {
                                ois.close();
                            } catch (Exception e5) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    ois = ois2;
                    if (ois != null) {
                        ois.close();
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                if (PushConfig.LOG) {
                    e.printStackTrace();
                }
                if (ois != null) {
                    ois.close();
                }
                PushConfig.LogI("[PushResourceHandler]load: " + map.toString());
                return map;
            }
            PushConfig.LogI("[PushResourceHandler]load: " + map.toString());
        }
        return map;
    }

    public static Intent putIntentExtra(Intent intent, String noticeID, LinkedHashMap<String, PushResource> map) {
        PushResource res = (PushResource) map.get(noticeID);
        intent.putExtra("title", res.title);
        intent.putExtra("msg", res.msg);
        intent.putExtra("bigmsg", res.bigmsg);
        intent.putExtra("ticker", res.ticker);
        intent.putExtra("noticeID", res.noticeID);
        intent.putExtra(PeppermintConstant.JSON_KEY_TYPE, res.type);
        intent.putExtra("icon", res.icon);
        intent.putExtra("sound", res.sound);
        intent.putExtra("active", res.active);
        intent.putExtra("packageName", res.packageName);
        intent.putExtra("broadcastAction", res.broadcastAction);
        intent.putExtra("buckettype", res.buckettype);
        intent.putExtra("bucketsize", res.bucketsize);
        intent.putExtra("bigpicture", res.bigpicture);
        intent.putExtra("icon_color", res.icon_color);
        return intent;
    }
}
