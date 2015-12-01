package com.com2us.module.push;

import android.content.Context;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;

/* compiled from: PushConfig */
abstract class MsgsOfNoticeID {
    private static final String fileName = "msgsMap.dat";

    MsgsOfNoticeID() {
    }

    private static synchronized LinkedHashMap<String, String[]> loadingMsgsData(Context context) {
        LinkedHashMap<String, String[]> msgsMap;
        Exception e;
        Throwable th;
        synchronized (MsgsOfNoticeID.class) {
            PushConfig.LogI("loading msgs data");
            LinkedHashMap<String, String[]> msgsMap2 = new LinkedHashMap();
            ObjectInputStream is = null;
            try {
                ObjectInputStream is2 = new ObjectInputStream(context.openFileInput(fileName));
                try {
                    msgsMap2 = (LinkedHashMap) is2.readObject();
                    PushConfig.LogI("loading msgsMap data success");
                    if (is2 != null) {
                        try {
                            is2.close();
                        } catch (Exception e2) {
                            PushConfig.LogI(e2.toString());
                        }
                    }
                    is = is2;
                    msgsMap = msgsMap2;
                } catch (Exception e3) {
                    e = e3;
                    is = is2;
                    msgsMap = msgsMap2;
                    try {
                        PushConfig.LogI(e.toString());
                        msgsMap2 = new LinkedHashMap();
                    } catch (Throwable th2) {
                        th = th2;
                        msgsMap2 = msgsMap;
                        if (is != null) {
                            try {
                                is.close();
                            } catch (Exception e22) {
                                PushConfig.LogI(e22.toString());
                            }
                        }
                        throw th;
                    }
                    try {
                        PushConfig.LogI("loading msgsMap data fail");
                        if (is != null) {
                            try {
                                is.close();
                            } catch (Exception e222) {
                                PushConfig.LogI(e222.toString());
                            }
                        }
                        msgsMap = msgsMap2;
                        return msgsMap;
                    } catch (Throwable th3) {
                        th = th3;
                        if (is != null) {
                            is.close();
                        }
                        throw th;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    is = is2;
                    if (is != null) {
                        is.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                msgsMap = msgsMap2;
                PushConfig.LogI(e.toString());
                msgsMap2 = new LinkedHashMap();
                PushConfig.LogI("loading msgsMap data fail");
                if (is != null) {
                    is.close();
                }
                msgsMap = msgsMap2;
                return msgsMap;
            }
        }
        return msgsMap;
    }

    private static synchronized boolean savingMsgsData(Context context, LinkedHashMap<String, String[]> msgsMap) {
        Throwable th;
        boolean z = false;
        synchronized (MsgsOfNoticeID.class) {
            PushConfig.LogI("saving msgs Data");
            ObjectOutputStream os = null;
            try {
                ObjectOutputStream os2 = new ObjectOutputStream(context.openFileOutput(fileName, 0));
                try {
                    os2.writeObject(msgsMap);
                    PushConfig.LogI("savingMsgsData is true");
                    if (os2 != null) {
                        try {
                            os2.close();
                        } catch (Exception e2) {
                            PushConfig.LogI(e2.toString());
                        }
                    }
                    z = true;
                    os = os2;
                } catch (Exception e) {
                    os = os2;
                    try {
                        PushConfig.LogI("savingMsgsData is false");
                        if (os != null) {
                            try {
                                os.close();
                            } catch (Exception e22) {
                                PushConfig.LogI(e22.toString());
                            }
                        }
                        return z;
                    } catch (Throwable th2) {
                        th = th2;
                        if (os != null) {
                            try {
                                os.close();
                            } catch (Exception e222) {
                                PushConfig.LogI(e222.toString());
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    os = os2;
                    if (os != null) {
                        os.close();
                    }
                    throw th;
                }
            } catch (Exception e3) {
                PushConfig.LogI("savingMsgsData is false");
                if (os != null) {
                    os.close();
                }
                return z;
            }
        }
        return z;
    }

    public static synchronized String[] addMsg(Context context, String noticeID, String msg) {
        String[] strArr;
        synchronized (MsgsOfNoticeID.class) {
            PushConfig.LogI("add Msgs Data");
            LinkedHashMap<String, String[]> msgsMap = loadingMsgsData(context);
            String[] oldSeqArray = null;
            try {
                String[] newSeqArray;
                if (msgsMap.containsKey(noticeID)) {
                    oldSeqArray = (String[]) msgsMap.get(noticeID);
                }
                if (oldSeqArray != null) {
                    newSeqArray = new String[(oldSeqArray.length + 1)];
                    for (int i = 0; i < oldSeqArray.length; i++) {
                        newSeqArray[i] = oldSeqArray[i];
                        PushConfig.LogI("newSeqArray[" + i + "] : " + newSeqArray[i]);
                    }
                    newSeqArray[oldSeqArray.length] = msg;
                    PushConfig.LogI("newSeqArray[" + oldSeqArray.length + "] : " + newSeqArray[oldSeqArray.length]);
                } else {
                    PushConfig.LogI("oldSeqArray is null");
                    newSeqArray = new String[]{msg};
                }
                if (msgsMap.containsKey(noticeID)) {
                    msgsMap.remove(noticeID);
                }
                msgsMap.put(noticeID, newSeqArray);
                savingMsgsData(context, msgsMap);
                strArr = newSeqArray;
            } catch (Exception e) {
                PushConfig.LogI(e.toString());
                strArr = new String[1];
            }
        }
        return strArr;
    }

    public static synchronized boolean clearMsgs(Context context, String noticeID) {
        boolean z;
        synchronized (MsgsOfNoticeID.class) {
            PushConfig.LogI("clear Msgs Data");
            LinkedHashMap<String, String[]> msgsMap = loadingMsgsData(context);
            String[] seqArray = null;
            try {
                if (msgsMap.containsKey(noticeID)) {
                    seqArray = (String[]) msgsMap.get(noticeID);
                }
                if (seqArray == null) {
                    z = true;
                } else {
                    msgsMap.remove(noticeID);
                    z = savingMsgsData(context, msgsMap);
                }
            } catch (Exception e) {
                PushConfig.LogI(e.toString());
                z = false;
            }
        }
        return z;
    }

    public static synchronized boolean clearAllMsgs(Context context) {
        boolean deleteFile;
        synchronized (MsgsOfNoticeID.class) {
            PushConfig.LogI("clear all Msgs Data");
            try {
                deleteFile = context.deleteFile(fileName);
            } catch (Exception e) {
                PushConfig.LogI(e.toString());
                deleteFile = false;
            }
        }
        return deleteFile;
    }
}
