package com.spbstu.sneakerhunter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_MAME = "SneakerHunterHistory";
    private static final int DB_VERSION = 4;

    public HistoryDatabaseHelper(Context context){
        super(context, DB_MAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 3, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 4) {
//            db.execSQL("DROP TABLE HISTORY;");
            db.execSQL("CREATE TABLE HISTORY (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "SNEAKER_KEY INTEGER UNIQUE);");
            db.execSQL("CREATE TRIGGER twelve_rows_only AFTER INSERT ON HISTORY" +
                    " BEGIN\n" +
                    " DELETE FROM HISTORY WHERE _id <= (SELECT _id FROM HISTORY" +
                    " ORDER BY _id DESC LIMIT 20, 1);" +
                    " END;");
        }
    }
}
