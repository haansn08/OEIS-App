package com.shaan.oeis;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoritesDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "favorites.db";

    public static final String TABLE_FAVORITES = "favorites";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_SEQUENCE_ID = "seqid";
    public static final String COLUMN_SEQUENCE_NAME = "seqname";

    FavoritesDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + TABLE_FAVORITES + " (" +
                        COLUMN_ID + " integer primary key autoincrement," +
                        COLUMN_SEQUENCE_ID + " text not null unique," +
                        COLUMN_SEQUENCE_NAME + " text not null unique)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(
                "delete table if exists " + TABLE_FAVORITES
        );
        onCreate(sqLiteDatabase);
    }
}
