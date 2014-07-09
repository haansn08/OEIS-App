package com.shaan.oeis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecentSearchesDatabase extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "recent.db";
    public static int DATABASE_VERSION = 3;
    public static String COLUMN_ID = "_id";
    public static String COLUMN_SEARCH_QUERY = "search_query";
    public static String TABLE_RECENT_SEARCHES = "recent_searches";

    RecentSearchesDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_RECENT_SEARCHES + " (" +
                COLUMN_ID + " integer primary key autoincrement," +
                COLUMN_SEARCH_QUERY + " text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_RECENT_SEARCHES);
        onCreate(sqLiteDatabase);
    }
}
