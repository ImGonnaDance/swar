package com.com2us.wrapper.function;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import com.com2us.wrapper.kernel.CWrapperData;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;
import java.util.StringTokenizer;
import jp.co.dimage.android.o;

public class CResource {
    private static Activity a = null;
    private static AssetManager b = null;
    private static String c = null;
    private static LinkedList<String> d = new LinkedList();
    private static HashMap<Integer, AssetFileDescriptor> e = new HashMap();

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] a = new int[EResourcePathType.values().length];

        static {
            try {
                a[EResourcePathType.ASSETS.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[EResourcePathType.SDCARD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    public enum EResourcePathType {
        ASSETS,
        SDCARD
    }

    private CResource() {
    }

    public static int R(String str) {
        int i = 0;
        String[] strArr = new String[3];
        StringTokenizer stringTokenizer = new StringTokenizer(str, ".", false);
        while (i < 3) {
            strArr[i] = stringTokenizer.nextToken();
            i++;
        }
        return a.getResources().getIdentifier(strArr[2], strArr[1], CFunction.getPackageName());
    }

    public static AssetFileDescriptor assetOpenFd(String str) {
        try {
            return b.openFd(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static void closeFileDescriptor(String str) {
        try {
            AssetFileDescriptor assetFileDescriptor = (AssetFileDescriptor) e.remove(Integer.valueOf(str.hashCode()));
            if (assetFileDescriptor != null) {
                assetFileDescriptor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeFileDescriptorAll() {
        try {
            Set keySet = e.keySet();
            for (int i = 0; i < keySet.size(); i++) {
                AssetFileDescriptor assetFileDescriptor = (AssetFileDescriptor) e.remove(keySet.toArray()[i]);
                if (assetFileDescriptor != null) {
                    assetFileDescriptor.close();
                }
            }
            e.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static FileDescriptor getFileDescriptorFromAsset(String str, int[] iArr) {
        long startOffset;
        FileDescriptor fileDescriptor;
        long j = -1;
        AssetFileDescriptor assetFileDescriptor = (AssetFileDescriptor) e.get(Integer.valueOf(str.hashCode()));
        if (assetFileDescriptor == null) {
            assetFileDescriptor = assetOpenFd(getFullPathName(str, EResourcePathType.ASSETS));
            e.put(Integer.valueOf(str.hashCode()), assetFileDescriptor);
        }
        if (assetFileDescriptor != null) {
            startOffset = assetFileDescriptor.getStartOffset();
            j = assetFileDescriptor.getLength();
            fileDescriptor = assetFileDescriptor.getFileDescriptor();
        } else {
            fileDescriptor = null;
            startOffset = -1;
        }
        iArr[0] = (int) startOffset;
        iArr[1] = (int) j;
        return fileDescriptor;
    }

    public static String getFullPathName(String str, EResourcePathType eResourcePathType) {
        int i = 0;
        String str2;
        String str3;
        switch (AnonymousClass1.a[eResourcePathType.ordinal()]) {
            case o.a /*1*/:
                String assetFileNameAppended = CWrapperData.getInstance().getAssetFileNameAppended();
                AssetFileDescriptor assetFileDescriptor = null;
                str2 = null;
                while (i < d.size()) {
                    str3 = ((String) d.get(i)) + "/" + str + assetFileNameAppended;
                    try {
                        AssetFileDescriptor openFd = b.openFd(str3);
                        if (openFd != null) {
                            try {
                                openFd.close();
                                openFd = null;
                            } catch (Exception e) {
                                assetFileDescriptor = openFd;
                            }
                        }
                        assetFileDescriptor = openFd;
                    } catch (IOException e2) {
                        if (assetFileDescriptor != null) {
                            try {
                                assetFileDescriptor.close();
                                assetFileDescriptor = null;
                            } catch (Exception e3) {
                                str3 = null;
                            }
                        }
                        str3 = null;
                    } catch (Throwable th) {
                        if (assetFileDescriptor != null) {
                            try {
                                assetFileDescriptor.close();
                            } catch (Exception e4) {
                            }
                        }
                    }
                    if (str3 != null) {
                        return str3;
                    }
                    i++;
                    str2 = str3;
                }
                str3 = str2;
                return str3;
            case o.b /*2*/:
                for (int i2 = 0; i2 < d.size(); i2++) {
                    str3 = c + "/" + ((String) d.get(i2)) + "/" + str;
                    if (new File(str3).exists()) {
                        if (str3 == null) {
                            str3 = "/sdcard/Android/data/" + CFunction.getPackageName() + "/files/SND/" + str;
                            if (!new File(str3).exists()) {
                                str3 = null;
                            }
                        }
                        if (str3 == null) {
                            str2 = a.getFilesDir().getPath();
                            str3 = str2.substring(0, str2.indexOf(a.getPackageName())) + a.getPackageName() + "/" + str;
                            if (!new File(str3).exists()) {
                                str3 = null;
                            }
                        }
                        if (str3 == null) {
                            str3 = "/sdcard/Android/data/" + CFunction.getPackageName() + "/files/" + str;
                            if (!new File(str3).exists()) {
                                return null;
                            }
                        }
                        return str3;
                    }
                }
                str3 = null;
                if (str3 == null) {
                    str3 = "/sdcard/Android/data/" + CFunction.getPackageName() + "/files/SND/" + str;
                    if (new File(str3).exists()) {
                        str3 = null;
                    }
                }
                if (str3 == null) {
                    str2 = a.getFilesDir().getPath();
                    str3 = str2.substring(0, str2.indexOf(a.getPackageName())) + a.getPackageName() + "/" + str;
                    if (new File(str3).exists()) {
                        str3 = null;
                    }
                }
                if (str3 == null) {
                    str3 = "/sdcard/Android/data/" + CFunction.getPackageName() + "/files/" + str;
                    if (new File(str3).exists()) {
                        return null;
                    }
                }
                return str3;
            default:
                return null;
        }
    }

    public static void initialize(Activity activity) {
        a = activity;
        b = activity.getAssets();
        if (CFunction.getSystemVersionCode() >= 8) {
            c = a.getExternalFilesDir(null) + "Resources";
        } else {
            c = "/sdcard/Android/data/" + CFunction.getPackageName() + "/files/Resources";
        }
        String initialLanguage = CFunction.getInitialLanguage();
        String str = "game_res";
        d.addLast(initialLanguage + "_r" + CFunction.getInitialCountry() + "/" + str);
        d.addLast(initialLanguage + "/" + str);
        d.addLast("common/" + str);
    }

    public static void uninitialize() {
        closeFileDescriptorAll();
        a = null;
        b = null;
        d.clear();
    }
}
