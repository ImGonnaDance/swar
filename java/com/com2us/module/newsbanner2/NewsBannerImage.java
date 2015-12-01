package com.com2us.module.newsbanner2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.com2us.module.util.Logger;
import com.com2us.module.util.LoggerGroup;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class NewsBannerImage {
    private static Logger logger = LoggerGroup.createLogger(Constants.TAG);

    static class DateComparator implements Comparator<File> {
        DateComparator() {
        }

        public int compare(File lhs, File rhs) {
            long diff = lhs.lastModified() - rhs.lastModified();
            if (diff > 0) {
                return 1;
            }
            return diff < 0 ? -1 : 0;
        }
    }

    private static File rootDir(Context context) {
        return context.getFilesDir();
    }

    public static File[] bannerLists(Context context) {
        File dirPath = makeBannerDir(context);
        if (dirPath == null || !dirPath.isDirectory()) {
            return null;
        }
        return dirPath.listFiles();
    }

    private static File makeBannerDir(Context context) {
        return new File(rootDir(context), Constants.CACHED_IMAGE_BANNER_DIR);
    }

    private static String makeBannerFileName(String url) {
        try {
            return url.substring(url.lastIndexOf(47) + 1);
        } catch (Exception e) {
            logger.w("[NewsBannerImage][makeBannerFile]url=" + url, e);
            return url;
        }
    }

    public static File makeBannerFile(Context context, String url) {
        return new File(makeBannerDir(context), makeBannerFileName(url));
    }

    public static File makeTabDir(Context context) {
        return new File(rootDir(context), Constants.CACHED_IMAGE_TAB_DIR);
    }

    public static File makeTabFile(Context context, String filename) {
        return new File(makeTabDir(context), filename);
    }

    private static Bitmap loadCachedBitmap(File filePath, int sampleSize) {
        FileNotFoundException e;
        Throwable th;
        logger.v("[NewsBannerImage][loadCachedBitma]file=" + filePath.toString());
        FileInputStream fis = null;
        Bitmap bitmap = null;
        if (filePath.exists()) {
            try {
                Options options = new Options();
                options.inSampleSize = sampleSize;
                FileInputStream fis2 = new FileInputStream(filePath);
                try {
                    bitmap = BitmapFactory.decodeStream(fis2, null, options);
                    if (fis2 != null) {
                        try {
                            fis2.close();
                            fis = fis2;
                        } catch (IOException e2) {
                            fis = fis2;
                        }
                    }
                } catch (FileNotFoundException e3) {
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
                        return bitmap;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fis != null) {
                            try {
                                fis.close();
                            } catch (IOException e5) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fis = fis2;
                    if (fis != null) {
                        fis.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e6) {
                e = e6;
                e.printStackTrace();
                if (fis != null) {
                    fis.close();
                }
                return bitmap;
            }
        }
        return bitmap;
    }

    private static void deleteBannerFile(Context context) {
        int i;
        File[] files = bannerLists(context);
        if (logger.isLogged()) {
            for (i = 0; i < files.length; i++) {
                logger.v("[NewsBannerImage][deleteBannerFile]BeforeSort:" + files[i].toString() + ",date=" + files[i].lastModified());
            }
        }
        Arrays.sort(files, new DateComparator());
        if (logger.isLogged()) {
            for (i = 0; i < files.length; i++) {
                logger.v("[NewsBannerImage][deleteBannerFile]AfterSort:" + files[i].toString() + ", date=" + files[i].lastModified());
            }
        }
        for (i = 0; i < files.length - 8; i++) {
            File f = files[i];
            if (f.isDirectory()) {
                for (File file : f.listFiles()) {
                    file.delete();
                }
            }
            if (f.delete()) {
                logger.d("[NewsBannerImage][deleteBannerFile]Success:" + f.getName());
            } else {
                logger.w("[NewsBannerImage][deleteBannerFile]Fail:" + f.getName());
            }
        }
    }

    private static void deleteDir(File f) {
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                deleteDir(file);
            }
        }
        if (f.delete()) {
            logger.d("[NewsBannerImage][deleteDir]Success:" + f.getPath());
        } else {
            logger.w("[NewsBannerImage][deleteDir]Fail:" + f.getPath());
        }
    }

    public static Bitmap getBannerBitmap(Context context, String url, String hash) throws ImageDataException, IOException {
        File file = makeBannerFile(context, url);
        if (!file.exists()) {
            try {
                storeBannerFile(context, url, hash);
            } catch (ImageDataException e) {
                throw e;
            } catch (IOException e2) {
                throw e2;
            }
        }
        Bitmap bitmap = loadCachedBitmap(file, 1);
        if (bitmap == null || bitmap.getHeight() > 0) {
            return bitmap;
        }
        file.delete();
        bitmap.recycle();
        throw new IOException("BITMAP : Wrong Format");
    }

    public static Bitmap getTabBitmap(Context context, String filename, String url, String hash, String property, boolean isHDSize) throws ImageDataException, IOException {
        File tabFile = makeTabFile(context, filename);
        if (!makeTabFile(context, filename).exists()) {
            try {
                storeTabFile(context, filename, url, hash);
                NewsBannerProperties.setProperty(property, url);
                NewsBannerProperties.storeProperties(context);
            } catch (ImageDataException e) {
                throw e;
            } catch (IOException e2) {
                throw e2;
            }
        }
        Bitmap bitmap = loadCachedBitmap(tabFile, 1);
        if (bitmap == null || bitmap.getHeight() > 0) {
            return bitmap;
        }
        makeTabFile(context, filename).delete();
        bitmap.recycle();
        throw new IOException("BITMAP : Wrong Format");
    }

    private static void storeBannerFile(Context context, String url, String hash) throws ImageDataException, IOException {
        File dirPath = makeBannerDir(context);
        if (dirPath.exists() || dirPath.mkdirs()) {
            if (!makeBannerFile(context, url).exists()) {
                File[] fs = bannerLists(context);
                if (fs != null && fs.length > 8) {
                    deleteBannerFile(context);
                }
            }
            if (url != null) {
                try {
                    storeFileFromUrl(makeBannerFile(context, url), url, hash);
                    return;
                } catch (ImageDataException e) {
                    throw e;
                } catch (IOException e2) {
                    throw e2;
                }
            }
            return;
        }
        logger.w("[NewsBannerImage][storeBannerFile]Failed To create Directroy : " + dirPath.getName());
    }

    private static void storeTabFile(Context context, String filename, String url, String hash) throws ImageDataException, IOException {
        File dirPath = makeTabDir(context);
        if (!dirPath.exists()) {
            deleteDir(new File(context.getFilesDir(), Constants.CACHED_IMAGE_DIR_PATH));
            if (!dirPath.mkdirs()) {
                logger.w("[NewsBannerImage][storeTabFile]Failed To create Directroy : " + dirPath.getName());
                return;
            }
        }
        if (url != null) {
            try {
                storeFileFromUrl(makeTabFile(context, filename), url, hash);
            } catch (ImageDataException e) {
                throw e;
            } catch (IOException e2) {
                throw e2;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void storeFileFromUrl(java.io.File r21, java.lang.String r22, java.lang.String r23) throws com.com2us.module.newsbanner2.ImageDataException, java.io.IOException {
        /*
        r17 = logger;
        r18 = new java.lang.StringBuilder;
        r19 = "[NewsBannerImage][storeFileFromUrl]file=";
        r18.<init>(r19);
        r19 = r21.toString();
        r18 = r18.append(r19);
        r19 = ",url=";
        r18 = r18.append(r19);
        r0 = r18;
        r1 = r22;
        r18 = r0.append(r1);
        r18 = r18.toString();
        r17.v(r18);
        r6 = 0;
        r2 = 0;
        r12 = 0;
        r17 = 8096; // 0x1fa0 float:1.1345E-41 double:4.0E-320;
        r0 = r17;
        r3 = new byte[r0];
        r10 = 0;
        r4 = 0;
        r8 = 0;
        r14 = new java.lang.StringBuilder;
        r14.<init>();
        r11 = 0;
        r16 = 0;
        r17 = "SHA1";
        r16 = java.security.MessageDigest.getInstance(r17);	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r15 = new java.net.URL;	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r0 = r22;
        r15.<init>(r0);	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r17 = java.lang.System.out;	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r18 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r19 = "TargetURL=";
        r18.<init>(r19);	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r19 = r15.getFile();	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r18 = r18.toString();	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r17.println(r18);	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r17 = r15.openConnection();	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r0 = r17;
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r4 = r0;
        r10 = r4.getInputStream();	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r7 = new java.io.FileOutputStream;	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
        r0 = r21;
        r7.<init>(r0);	 Catch:{ IOException -> 0x0163, NoSuchAlgorithmException -> 0x0161 }
    L_0x0073:
        r12 = r10.read(r3);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        if (r12 > 0) goto L_0x00ee;
    L_0x0079:
        r8 = r16.digest();	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r9 = 0;
    L_0x007e:
        r0 = r8.length;	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r17 = r0;
        r0 = r17;
        if (r9 < r0) goto L_0x010b;
    L_0x0085:
        r13 = r14.toString();	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r17 = logger;	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r18 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r19 = "hash=";
        r18.<init>(r19);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r0 = r18;
        r1 = r23;
        r18 = r0.append(r1);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r19 = " ret=";
        r18 = r18.append(r19);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r0 = r18;
        r18 = r0.append(r13);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r18 = r18.toString();	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r17.d(r18);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        if (r23 == 0) goto L_0x00c0;
    L_0x00af:
        r17 = new java.lang.String;	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r0 = r17;
        r0.<init>(r13);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r0 = r23;
        r1 = r17;
        r17 = r0.equals(r1);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        if (r17 != 0) goto L_0x012d;
    L_0x00c0:
        r11 = 1;
        r17 = new com.com2us.module.newsbanner2.ImageDataException;	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r18 = "different hashes";
        r17.<init>(r18);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        throw r17;	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
    L_0x00c9:
        r5 = move-exception;
        r6 = r7;
    L_0x00cb:
        r11 = 1;
        throw r5;	 Catch:{ all -> 0x00cd }
    L_0x00cd:
        r17 = move-exception;
    L_0x00ce:
        if (r11 == 0) goto L_0x00de;
    L_0x00d0:
        if (r2 == 0) goto L_0x00d5;
    L_0x00d2:
        r2.recycle();
    L_0x00d5:
        r2 = 0;
        if (r6 == 0) goto L_0x00db;
    L_0x00d8:
        r6.close();	 Catch:{ IOException -> 0x014d }
    L_0x00db:
        r21.delete();
    L_0x00de:
        if (r10 == 0) goto L_0x00e3;
    L_0x00e0:
        r10.close();	 Catch:{ IOException -> 0x014f }
    L_0x00e3:
        if (r6 == 0) goto L_0x00e8;
    L_0x00e5:
        r6.close();	 Catch:{ IOException -> 0x0151 }
    L_0x00e8:
        if (r4 == 0) goto L_0x00ed;
    L_0x00ea:
        r4.disconnect();	 Catch:{ Exception -> 0x0153 }
    L_0x00ed:
        throw r17;
    L_0x00ee:
        r17 = 0;
        r0 = r17;
        r7.write(r3, r0, r12);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r17 = 0;
        r0 = r16;
        r1 = r17;
        r0.update(r3, r1, r12);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        goto L_0x0073;
    L_0x0100:
        r5 = move-exception;
        r6 = r7;
    L_0x0102:
        r11 = 1;
        r17 = new com.com2us.module.newsbanner2.ImageDataException;	 Catch:{ all -> 0x00cd }
        r0 = r17;
        r0.<init>(r5);	 Catch:{ all -> 0x00cd }
        throw r17;	 Catch:{ all -> 0x00cd }
    L_0x010b:
        r17 = "%02x";
        r18 = 1;
        r0 = r18;
        r0 = new java.lang.Object[r0];	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r18 = r0;
        r19 = 0;
        r20 = r8[r9];	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r20 = java.lang.Byte.valueOf(r20);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r18[r19] = r20;	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r17 = java.lang.String.format(r17, r18);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r0 = r17;
        r14 = r14.append(r0);	 Catch:{ IOException -> 0x00c9, NoSuchAlgorithmException -> 0x0100, all -> 0x015d }
        r9 = r9 + 1;
        goto L_0x007e;
    L_0x012d:
        if (r11 == 0) goto L_0x013d;
    L_0x012f:
        if (r2 == 0) goto L_0x0134;
    L_0x0131:
        r2.recycle();
    L_0x0134:
        r2 = 0;
        if (r7 == 0) goto L_0x013a;
    L_0x0137:
        r7.close();	 Catch:{ IOException -> 0x0155 }
    L_0x013a:
        r21.delete();
    L_0x013d:
        if (r10 == 0) goto L_0x0142;
    L_0x013f:
        r10.close();	 Catch:{ IOException -> 0x0157 }
    L_0x0142:
        if (r7 == 0) goto L_0x0147;
    L_0x0144:
        r7.close();	 Catch:{ IOException -> 0x0159 }
    L_0x0147:
        if (r4 == 0) goto L_0x014c;
    L_0x0149:
        r4.disconnect();	 Catch:{ Exception -> 0x015b }
    L_0x014c:
        return;
    L_0x014d:
        r18 = move-exception;
        goto L_0x00db;
    L_0x014f:
        r18 = move-exception;
        goto L_0x00e3;
    L_0x0151:
        r18 = move-exception;
        goto L_0x00e8;
    L_0x0153:
        r18 = move-exception;
        goto L_0x00ed;
    L_0x0155:
        r17 = move-exception;
        goto L_0x013a;
    L_0x0157:
        r17 = move-exception;
        goto L_0x0142;
    L_0x0159:
        r17 = move-exception;
        goto L_0x0147;
    L_0x015b:
        r17 = move-exception;
        goto L_0x014c;
    L_0x015d:
        r17 = move-exception;
        r6 = r7;
        goto L_0x00ce;
    L_0x0161:
        r5 = move-exception;
        goto L_0x0102;
    L_0x0163:
        r5 = move-exception;
        goto L_0x00cb;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.com2us.module.newsbanner2.NewsBannerImage.storeFileFromUrl(java.io.File, java.lang.String, java.lang.String):void");
    }
}
