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

    public static final String[] COLUMNS = {KEY_ID, KEY_TITLE, KEY_AUTHOR, KEY_YEAR, KEY_DESCRIPTION, KEY_ROOM, KEY_IMAGE, KEY_RATING};

    // Table create statement
    private static final String CREATE_TABLE = "CREATE TABLE " +
            DB_TABLE + " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            KEY_TITLE + " TEXT, "+
            KEY_AUTHOR + " TEXT, " + KEY_YEAR + " INTEGER, " +
            KEY_DESCRIPTION + " TEXT, " + KEY_ROOM + " INTEGER, " +
            KEY_IMAGE + " BLOB, "+ KEY_RATING + " FLOAT)";

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

    public void addArtwork(Artwork art){

        Log.d("addArt", art.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        String n = db.getPath();
        Log.d("addArt", n);

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, art.getTitle()); // get title
        values.put(KEY_AUTHOR, art.getAuthor()); // get author and so on
        values.put(KEY_YEAR, art.getYear());
        values.put(KEY_DESCRIPTION, art.getDescription());
        values.put(KEY_ROOM, art.getRoom());
        values.put(KEY_IMAGE, art.getImage());
        values.put(KEY_RATING, art.getRating());

        // 3. insert
        db.insert(DB_TABLE, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public Artwork getArt(int id){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(DB_TABLE, // a. table
                        COLUMNS, // b. column names
                        " id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        Artwork art = null;
        if (cursor != null && cursor.moveToFirst()) {

            // 4. build book object
            art = new Artwork();
            art.id = Integer.parseInt(cursor.getString(0));
            art.setTitle(cursor.getString(1));
            art.setAuthor(cursor.getString(2));
            art.setYear(Integer.parseInt(cursor.getString(3)));
            art.setDescription(cursor.getString(4));
            art.setRoom(Integer.parseInt(cursor.getString(5)));
            art.setImage(cursor.getBlob(6));
            art.setRating(Float.parseFloat(cursor.getString(7)));

            Log.d("getArt(" + id + ")", art.toString());

        }
        cursor.close();
        // 5. return art
        return art;
    }
    // Get All Books
    public List<Artwork> getAllArt() {
        List<Artwork> arts = new LinkedList<Artwork>();

        // 1. build the query
        String query = "SELECT  * FROM " + DB_TABLE;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Artwork art = null;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                art = new Artwork();
                art.id = Integer.parseInt(cursor.getString(0));
                art.setTitle(cursor.getString(1));
                art.setAuthor(cursor.getString(2));
                art.setYear(Integer.parseInt(cursor.getString(3)));
                art.setDescription(cursor.getString(4));
                art.setRoom(Integer.parseInt(cursor.getString(5)));
                art.setImage(cursor.getBlob(6));
                art.setRating(Integer.parseInt(cursor.getString(7)));

                // Add book to books
                arts.add(art);
            } while (cursor.moveToNext());
        }

        Log.d("getAllArtwork()", arts.toString());

        cursor.close();
        // return books
        return arts;
    }
    // Updating single art
    public int updateArt(Artwork art) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(KEY_TITLE, art.getTitle()); // get title
        values.put(KEY_AUTHOR, art.getAuthor()); // get author and so on
        values.put(KEY_YEAR, art.getYear());
        values.put(KEY_DESCRIPTION, art.getDescription());
        values.put(KEY_ROOM, art.getRoom());
        values.put(KEY_IMAGE, art.getImage());
        values.put(KEY_RATING, art.getRating());

        // 3. updating row
        int i = db.update(DB_TABLE, //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(art.id) }); //selection args

        // 4. close
        db.close();

        return i;

    }
    // Deleting single book
    public void deleteArt(Artwork art) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(DB_TABLE,
                KEY_ID+" = ?",
                new String[] { String.valueOf(art.id) });

        // 3. close
        db.close();

        Log.d("deleteArt", art.toString());

    }
}
