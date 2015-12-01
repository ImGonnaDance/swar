package com.com2us.module.offerwall;

import android.content.Context;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import jp.co.cyberz.fox.a.a.i;

public class OfferwallProperties {
    public static Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private static final String mPropName = "offerwall.properties";
    private static Properties prop = new Properties();

    public static String getProperty(String name) {
        if (prop != null) {
            return prop.getProperty(name);
        }
        logger.d(Constants.TAG, "Error: Properties didn't create");
        return i.a;
    }

    public static void setProperty(String name, String value) {
        if (prop == null) {
            logger.d(Constants.TAG, "Error: Properties didn't create");
        } else {
            prop.setProperty(name, value);
        }
    }

    public static void loadProperties(Context context) {
        if (prop == null) {
            logger.d(Constants.TAG, "Error: Properties didn't create");
            return;
        }
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
        if (prop == null) {
            logger.d(Constants.TAG, "Error: Properties didn't create");
            return;
        }
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
}
