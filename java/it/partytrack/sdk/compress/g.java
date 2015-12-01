package it.partytrack.sdk.compress;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.com2us.peppermint.PeppermintConstant;
import java.util.Locale;

public final class g extends SQLiteOpenHelper {
    public g(Context context) {
        super(context, "partytrack", null, 1);
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(String.format(Locale.ENGLISH, "CREATE TABLE %s(%s %s, %s %s, %s %s, %s %s, %s %s, %s %s, %s %s);", new Object[]{"events", PeppermintConstant.JSON_KEY_ID, "INTEGER PRIMARY KEY", "event_id", "INTEGER NOT NULL DEFAULT 0", "event_identifier", "TEXT NOT NULL DEFAULT ''", PeppermintConstant.JSON_KEY_PARAMS, "TEXT", "resend_count", "INTEGER DEFAULT 0", "created_at", "INTEGER NOT NULL", "updated_at", "INTEGER NOT NULL"}));
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
