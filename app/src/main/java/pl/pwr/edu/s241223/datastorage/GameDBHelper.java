package pl.pwr.edu.s241223.datastorage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import pl.pwr.edu.s241223.datastorage.GameHistory.*;


import androidx.annotation.Nullable;

public class GameDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "gamehistory.db";
    public static final int DATABASE_VERSION = 1;

    public GameDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_GAME_HISTORY_TABLE = "CREATE TABLE " +
                GameHistoryEntry.TABLE_NAME + " (" +
                GameHistoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                GameHistoryEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                GameHistoryEntry.COLUMN_WON + " TEXT NOT NULL, " +
                GameHistoryEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_GAME_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GameHistoryEntry.TABLE_NAME);
    }
}
