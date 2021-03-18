package com.spbstu.sneakerhunterkotlin

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistoryDatabaseHelper(context: Context?) :
    SQLiteOpenHelper(
        context,
        DB_MAME,
        null,
        DB_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase) {
        updateMyDatabase(db, 7, DB_VERSION)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        updateMyDatabase(db, oldVersion, newVersion)
    }

    private fun updateMyDatabase(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        if (oldVersion < 8) {
            //db.execSQL("DROP TABLE HISTORY;");
            db.execSQL(
                """CREATE TABLE HISTORY (
                        _id INTEGER PRIMARY KEY AUTOINCREMENT,
                        SNEAKER_KEY INTEGER UNIQUE);""".trimIndent()
            )
            db.execSQL(
                """CREATE TRIGGER twelve_rows_only AFTER INSERT ON HISTORY BEGIN
                            DELETE FROM HISTORY WHERE _id <= (SELECT _id FROM HISTORY ORDER BY _id DESC LIMIT 20, 1); 
                        END;""".trimIndent()

            )
            db.execSQL("""
                CREATE TABLE FAVORITES(
                    _id INTEGER PRIMARY KEY AUTOINCREMENT,
                    SNEAKER_KEY INTEGER UNIQUE);
            """.trimIndent())
        }
    }

    companion object {
        private const val DB_MAME = "SneakerHunterHistory"
        private const val DB_VERSION = 8
    }
}
