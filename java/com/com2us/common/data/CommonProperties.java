package com.com2us.common.data;

import android.content.Context;
import android.provider.Settings.Secure;
import com.com2us.common.security.Crypto;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

public final class CommonProperties {
    private static final String COMMON_DATA = "CommonData.dat";
    private static final String COMMON_DATA_PROPERTIES = String.valueOf(COMMON_DATA.hashCode());
    private static Properties properties = new Properties();

    private CommonProperties() {
    }

    public static synchronized void loadProperties(Context context) {
        Throwable th;
        Exception e;
        synchronized (CommonProperties.class) {
            FileInputStream fis = null;
            try {
                File file = new File("/data/data/" + context.getPackageName() + "/" + COMMON_DATA_PROPERTIES);
                if (file.exists()) {
                    FileInputStream fis2 = new FileInputStream(file);
                    try {
                        byte[] crc32 = new byte[8];
                        byte[] encryptedData = new byte[(((int) file.length()) - 8)];
                        fis2.read(crc32, 0, 8);
                        fis2.read(encryptedData);
                        String androidId = Secure.getString(context.getContentResolver(), "android_id");
                        if (androidId == null) {
                            androidId = COMMON_DATA;
                        }
                        byte[] decryptedData = Crypto.decrypt("AES", androidId, encryptedData);
                        if (ByteBuffer.wrap(crc32).getLong() == Crypto.generateCRC32(decryptedData)) {
                            properties.load(new ByteArrayInputStream(decryptedData));
                        }
                        if (fis2 != null) {
                            try {
                                fis2.close();
                                fis = fis2;
                            } catch (IOException e2) {
                                fis = fis2;
                            } catch (Throwable th2) {
                                th = th2;
                                fis = fis2;
                                throw th;
                            }
                        }
                    } catch (Exception e3) {
                        e = e3;
                        fis = fis2;
                        try {
                            e.printStackTrace();
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (IOException e4) {
                                }
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            if (fis != null) {
                                try {
                                    fis.close();
                                } catch (IOException e5) {
                                }
                            }
                            try {
                                throw th;
                            } catch (Throwable th4) {
                                th = th4;
                            }
                        }
                    } catch (Throwable th5) {
                        th = th5;
                        fis = fis2;
                        if (fis != null) {
                            fis.close();
                        }
                        throw th;
                    }
                } else if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e6) {
                    }
                }
            } catch (Exception e7) {
                e = e7;
                e.printStackTrace();
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }

    public static synchronized void setProperty(String name, String value) {
        synchronized (CommonProperties.class) {
            properties.setProperty(name, value);
        }
    }

    public static synchronized String getProperty(String name) {
        String property;
        synchronized (CommonProperties.class) {
            property = properties.getProperty(name);
        }
        return property;
    }

    public static synchronized void removeProperty(String name) {
        synchronized (CommonProperties.class) {
            properties.remove(name);
        }
    }

    public static synchronized String tooString() {
        String properties;
        synchronized (CommonProperties.class) {
            properties = properties.toString();
        }
        return properties;
    }

    public static synchronized void storeProperties(Context context) {
        Throwable th;
        Exception e;
        synchronized (CommonProperties.class) {
            FileOutputStream fos = null;
            try {
                FileOutputStream fos2 = new FileOutputStream(new File("/data/data/" + context.getPackageName() + "/" + COMMON_DATA_PROPERTIES));
                try {
                    ByteArrayOutputStream data = new ByteArrayOutputStream();
                    properties.store(data, null);
                    byte[] crc32 = new byte[8];
                    ByteBuffer.wrap(crc32).putLong(Crypto.generateCRC32(data.toByteArray()));
                    String androidId = Secure.getString(context.getContentResolver(), "android_id");
                    if (androidId == null) {
                        androidId = COMMON_DATA;
                    }
                    byte[] encryptData = Crypto.encrypt("AES", androidId, data.toByteArray());
                    fos2.write(crc32);
                    fos2.write(encryptData);
                    if (fos2 != null) {
                        try {
                            fos2.close();
                            fos = fos2;
                        } catch (IOException e2) {
                            fos = fos2;
                        } catch (Throwable th2) {
                            th = th2;
                            fos = fos2;
                            throw th;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    fos = fos2;
                    try {
                        e.printStackTrace();
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e4) {
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e5) {
                            }
                        }
                        try {
                            throw th;
                        } catch (Throwable th4) {
                            th = th4;
                        }
                    }
                } catch (Throwable th5) {
                    th = th5;
                    fos = fos2;
                    if (fos != null) {
                        fos.close();
                    }
                    throw th;
                }
            } catch (Exception e6) {
                e = e6;
                e.printStackTrace();
                if (fos != null) {
                    fos.close();
                }
            }
        }
    }
}
