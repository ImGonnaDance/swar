package jp.co.dimage.android;

import android.os.Environment;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import jp.co.cyberz.fox.a.a.i;

public class m {
    public static String a() {
        return new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getPath())).append("/").toString();
    }

    public static String a(File file) {
        String str = i.a;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    return str;
                }
                str = readLine;
            }
        } catch (Exception e) {
            return i.a;
        }
    }

    private static String a(String str) {
        return a(new File(str));
    }

    public static boolean a(File file, String str) {
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
        bufferedWriter.write(str);
        bufferedWriter.flush();
        bufferedWriter.close();
        return true;
    }

    public static boolean a(String str, String str2) {
        return (!p.a(str) && new File(str).exists()) ? p.a(str2) || new File(str, str2).exists() : false;
    }

    private static boolean a(String str, String str2, String str3) {
        File parentFile = new File(str, str2).getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return a(new File(str, str2), str3);
    }

    private static boolean b() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean b(File file) {
        if (!file.exists()) {
            return false;
        }
        try {
            if (file.isFile()) {
                file.delete();
            }
            if (file.isDirectory()) {
                for (File b : file.listFiles()) {
                    b(b);
                }
            }
            File parentFile = file.getParentFile();
            if (parentFile.exists()) {
                parentFile.delete();
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean b(String str, String str2) {
        boolean z = false;
        if (!(p.a(str) || p.a(str2) || !a(str, str2))) {
            try {
                z = b(new File(str, str2));
            } catch (Exception e) {
            }
        }
        return z;
    }

    private static boolean c(String str, String str2) {
        return a(new File(str), str2);
    }
}
