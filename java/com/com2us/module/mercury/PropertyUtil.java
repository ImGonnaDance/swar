package com.com2us.module.mercury;

import android.content.Context;
import android.text.TextUtils;
import com.com2us.module.offerwall.Constants;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import jp.co.cyberz.fox.a.a.i;

public class PropertyUtil {
    public static Logger logger = LoggerGroup.createLogger(Constants.TAG);
    private static File m_profile = null;
    private static PropertyUtil m_propertyUtil = null;
    private static Properties m_pros = null;
    private final String PROPERTY_FILE = "/Mercury.properties";
    private Context context = null;

    public PropertyUtil(Context _context) {
        this.context = _context;
        m_profile = new File(new StringBuilder(String.valueOf(_context.getApplicationContext().getFilesDir().getPath())).append("/Mercury.properties").toString());
        logger.d("m_profile Path : " + _context.getApplicationContext().getFilesDir().getPath() + "/Mercury.properties");
        m_pros = new Properties();
        try {
            if (!m_profile.getParentFile().exists()) {
                m_profile.getParentFile().mkdir();
            }
            if (!m_profile.exists()) {
                m_profile.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.d("Property File Load Failed -> " + e.getMessage());
        }
    }

    public static synchronized PropertyUtil getInstance(Context context) {
        PropertyUtil propertyUtil;
        synchronized (PropertyUtil.class) {
            if (m_propertyUtil == null) {
                m_propertyUtil = new PropertyUtil(context.getApplicationContext());
            }
            propertyUtil = m_propertyUtil;
        }
        return propertyUtil;
    }

    public synchronized void loadProperty() {
        IOException e;
        Throwable th;
        FileInputStream m_fis = null;
        try {
            FileInputStream m_fis2 = new FileInputStream(m_profile);
            try {
                m_pros.load(m_fis2);
                if (m_fis2 != null) {
                    try {
                        m_fis2.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    } catch (Throwable th2) {
                        th = th2;
                        m_fis = m_fis2;
                        throw th;
                    }
                }
            } catch (IOException e3) {
                e2 = e3;
                m_fis = m_fis2;
                try {
                    e2.printStackTrace();
                    if (m_fis != null) {
                        try {
                            m_fis.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        } catch (Throwable th3) {
                            th = th3;
                            throw th;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (m_fis != null) {
                        try {
                            m_fis.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                m_fis = m_fis2;
                if (m_fis != null) {
                    m_fis.close();
                }
                throw th;
            }
        } catch (IOException e4) {
            e222 = e4;
            e222.printStackTrace();
            if (m_fis != null) {
                m_fis.close();
            }
        }
    }

    public String getProperty(String key) {
        String retVal = m_pros.getProperty(key);
        logger.d("getProperty(" + key + ") is " + retVal);
        return retVal;
    }

    public synchronized void setProperty(String key, String value) {
        if (TextUtils.isEmpty(value)) {
            value = i.a;
        }
        m_pros.setProperty(key, value);
    }

    public void clearProperty() {
        m_pros.clear();
    }

    public synchronized void storeProperty(String comment) {
        Throwable th;
        Exception e;
        FileOutputStream m_fos = null;
        try {
            FileOutputStream m_fos2 = new FileOutputStream(m_profile);
            try {
                m_pros.store(m_fos2, comment);
                if (m_fos2 != null) {
                    try {
                        m_fos2.close();
                        m_fos = m_fos2;
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    } catch (Throwable th2) {
                        th = th2;
                        m_fos = m_fos2;
                        throw th;
                    }
                }
                m_fos = m_fos2;
            } catch (Exception e3) {
                e = e3;
                m_fos = m_fos2;
                try {
                    e.printStackTrace();
                    if (m_fos != null) {
                        try {
                            m_fos.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        } catch (Throwable th3) {
                            th = th3;
                            throw th;
                        }
                    }
                } catch (Throwable th4) {
                    th = th4;
                    if (m_fos != null) {
                        try {
                            m_fos.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th5) {
                th = th5;
                m_fos = m_fos2;
                if (m_fos != null) {
                    m_fos.close();
                }
                throw th;
            }
        } catch (Exception e4) {
            e = e4;
            e.printStackTrace();
            if (m_fos != null) {
                m_fos.close();
            }
        }
    }

    public void deleteFile() {
        m_profile.delete();
    }
}
