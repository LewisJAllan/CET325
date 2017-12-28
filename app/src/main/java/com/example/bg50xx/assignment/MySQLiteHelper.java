package com.example.bg50xx.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Blob;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Lewis on 18/12/2017.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "artwork.db";

    // Table Names
    public static final String DB_TABLE = "artwork";

    // column names
    public static final String KEY_ID = "_id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_AUTHOR = "author";
    public static final String KEY_YEAR = "year";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_ROOM = "room";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_RATING = "rating";
    public static final String KEY_EDIT = "edit";

    public static final String[] COLUMNS = {KEY_ID, KEY_TITLE, KEY_AUTHOR, KEY_YEAR, KEY_DESCRIPTION, KEY_ROOM, KEY_IMAGE, KEY_RATING, KEY_EDIT};

    // Table create statement
    private static final String CREATE_TABLE = "CREATE TABLE " +
            DB_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TITLE + " TEXT, "+
            KEY_AUTHOR + " TEXT, " + KEY_YEAR + " INTEGER, " +
            KEY_DESCRIPTION + " TEXT, " + KEY_ROOM + " INTEGER, " +
            KEY_IMAGE + " BLOB, "+ KEY_RATING + " FLOAT, " + KEY_EDIT + " INTEGER DEFAULT 1)";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating table
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);

        // create new table
        onCreate(db);
    }

    //-----------------------------------------------------------------------
    //CRUD Operations - Create (add), Read (get), Update, Delete

}
