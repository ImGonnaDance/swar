package com.com2us.module.mercury;

import android.content.Context;
import android.util.Log;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import jp.co.cyberz.fox.a.a.i;

public class MercuryProperties {
    private static final String mPropName = "mercury.properties";
    private static Properties prop = new Properties();

    public static String getProperty(String name) {
        return prop.getProperty(name);
    }

    public static void setProperty(String name, String value) {
        prop.setProperty(name, value);
    }

    public static void loadProperties(Context context) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(mPropName);
            prop.load(fis);
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e2) {
            FileOutputStream fos = null;
            try {
                fos = context.openFileOutput(mPropName, 0);
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e3) {
                    }
                }
            } catch (IOException e1) {
                e1.printStackTrace();
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e4) {
                    }
                }
            } catch (Throwable th) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e5) {
                    }
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e6) {
                }
            }
        } catch (FileNotFoundException e7) {
            e7.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e8) {
                }
            }
        } catch (Throwable th2) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e9) {
                }
            }
        }
    }

    public static void storeProperties(Context context) {
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(mPropName, 0);
            prop.store(fos, null);
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e2) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e3) {
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e5) {
                }
            }
        } catch (Throwable th) {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e6) {
                }
            }
        }
    }

    public static void removeProperties(String key) {
        prop.remove(key);
    }

    public static void addPID(String pid) {
        String pidList = getProperty("pidlist");
        if (pidList == null) {
            pidList = i.a;
        }
        pidList = new StringBuilder(String.valueOf(pidList)).append(new StringBuilder(String.valueOf(pid)).append(i.b).toString()).toString();
        Log.d(Constants.TAG, "pid added. pidList : " + pidList);
        setProperty("pidlist", pidList);
    }

    public static String getPIDList() {
        String pidList = getProperty("pidlist");
        if (pidList == null) {
            return i.a;
        }
        return pidList;
    }
}
