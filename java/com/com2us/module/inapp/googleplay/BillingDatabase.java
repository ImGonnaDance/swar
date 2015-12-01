package com.com2us.module.inapp.googleplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.lang.reflect.Array;

public class BillingDatabase {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mDb = this.mDatabaseHelper.getWritableDatabase();

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, Utility.getString(0), null, 1);
        }

        public void onCreate(SQLiteDatabase db) {
            createPurchaseTable(db);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            System.out.println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + Utility.getString(1) + "(" + Utility.getString(7) + " TEXT PRIMARY KEY, " + Utility.getString(2) + " TEXT, " + Utility.getString(3) + " TEXT, " + Utility.getString(4) + " TEXT)");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + Utility.getString(5) + "(" + Utility.getString(6) + " TEXT PRIMARY KEY)");
        }

        private void createPurchaseTable(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + Utility.getString(1) + "(" + Utility.getString(7) + " TEXT PRIMARY KEY, " + Utility.getString(2) + " TEXT, " + Utility.getString(3) + " TEXT, " + Utility.getString(4) + " TEXT)");
            db.execSQL("CREATE TABLE " + Utility.getString(5) + "(" + Utility.getString(6) + " TEXT PRIMARY KEY)");
        }
    }

    public BillingDatabase(Context context) {
        this.mDatabaseHelper = new DatabaseHelper(context);
    }

    public void close() {
        this.mDatabaseHelper.close();
    }

    public synchronized void updatePurchase(String signedData, String signature, String responseStr) {
        ContentValues values = new ContentValues();
        values.put(Utility.getString(7), signedData);
        values.put(Utility.getString(2), signedData);
        values.put(Utility.getString(3), signature);
        values.put(Utility.getString(4), responseStr);
        this.mDb.replace(Utility.getString(1), null, values);
    }

    public synchronized void deletePurchase(String signedData) {
        this.mDb.delete(Utility.getString(1), Utility.getString(7) + "=?", new String[]{signedData});
    }

    public synchronized String[][] getPurchaseData() {
        String[][] strArr;
        Cursor cursor = this.mDb.query(Utility.getString(1), new String[]{Utility.getString(7), Utility.getString(2), Utility.getString(3), Utility.getString(4)}, null, null, null, null, null, null);
        if (cursor == null) {
            strArr = null;
        } else {
            String[][] ret = (String[][]) Array.newInstance(String.class, new int[]{cursor.getCount(), 4});
            int cnt = 0;
            while (cursor.moveToNext()) {
                try {
                    ret[cnt][0] = cursor.getString(0);
                    ret[cnt][1] = cursor.getString(1);
                    ret[cnt][2] = cursor.getString(2);
                    ret[cnt][3] = cursor.getString(3);
                    cnt++;
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            strArr = ret;
        }
        return strArr;
    }

    public synchronized void updateLogData(String logData) {
        ContentValues values = new ContentValues();
        values.put(Utility.getString(6), logData);
        this.mDb.replace(Utility.getString(5), null, values);
    }

    public synchronized void deleteLogData(String hash) {
        this.mDb.delete(Utility.getString(5), Utility.getString(6) + "=?", new String[]{hash});
    }

    public synchronized String[][] getLogData() {
        String[][] strArr;
        Cursor cursor = this.mDb.query(Utility.getString(5), new String[]{Utility.getString(6)}, null, null, null, null, null, null);
        if (cursor == null) {
            strArr = null;
        } else {
            String[][] ret = (String[][]) Array.newInstance(String.class, new int[]{cursor.getCount(), 1});
            int cnt = 0;
            while (cursor.moveToNext()) {
                try {
                    ret[cnt][0] = cursor.getString(0);
                    cnt++;
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
            if (cursor != null) {
                cursor.close();
            }
            strArr = ret;
        }
        return strArr;
    }
}
