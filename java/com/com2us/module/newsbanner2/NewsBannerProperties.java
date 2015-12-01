package com.com2us.module.newsbanner2;

import android.content.Context;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class NewsBannerProperties {
    public static final String AUTO_SHOW_TIME_PROPERTY = "AUTO_SHOW_TIME";
    public static final String CPI_PIDS_PROPERTY = "CPI_PIDS";
    public static final String NEWSBANNER_PROPERTIES = "newsbanner.props";
    public static final String TAB_IMAGE_URL_DOWN_CLOSE_PROPERTY = "TAB_IMAGE_URL_DOWN_CLOSE";
    public static final String TAB_IMAGE_URL_DOWN_OPEN_PROPERTY = "TAB_IMAGE_URL_DOWN_OPEN";
    public static final String TAB_IMAGE_URL_UP_CLOSE_PROPERTY = "TAB_IMAGE_URL_UP_CLOSE";
    public static final String TAB_IMAGE_URL_UP_OPEN_PROPERTY = "TAB_IMAGE_URL_UP_OPEN";
    private static Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private static Properties prop = new Properties();

    public static String getProperty(String name) {
        return prop.getProperty(name);
    }

    public static void setProperty(String name, String value) {
        logger.v("[NewsBannerProperties][setProperty]name=" + name + ", value=" + value);
        prop.setProperty(name, value);
    }

    public static void loadProperties(Context context) {
        FileInputStream fis = null;
        try {
            fis = context.openFileInput(NEWSBANNER_PROPERTIES);
            prop.load(fis);
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e2) {
            logger.v("[NewsBannerProperties][loadProperties]" + e2.toString());
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e3) {
                }
            }
        } catch (IOException e4) {
            e4.printStackTrace();
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e5) {
                }
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e6) {
                }
            }
        }
        logger.v("[NewsBannerProperties][loadProperties]" + prop.toString());
    }

    public static void storeProperties(Context context) {
        logger.v("[NewsBannerProperties][storeProperties]" + prop.toString());
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(NEWSBANNER_PROPERTIES, 0);
            prop.store(fos, null);
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
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
