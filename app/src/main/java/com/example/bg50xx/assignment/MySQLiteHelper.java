package com.example.bg50xx.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Blob;
import java.util.ArrayList;
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

    void addArt(Artwork art){

        Log.d("addArt", art.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        String n = db.getPath();
        Log.d("addArt", n);

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, art.getTitle()); // get title
        values.put(KEY_AUTHOR, art.getAuthor()); // get author
        values.put(KEY_DESCRIPTION, art.getDescription()); // get album
        values.put(KEY_YEAR, art.getYear()); // get price
        values.put(KEY_ROOM, art.getRoom());
        values.put(KEY_IMAGE, art.getImage());
        values.put(KEY_RATING, art.getRating());
        values.put(KEY_EDIT, art.getEdit());

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
                        " _id = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        Artwork art = null;
        if (cursor != null && cursor.moveToFirst()) {

            // 4. build art object
            art = new Artwork();
            art.id = Integer.parseInt(cursor.getString(0));
            art.setTitle(cursor.getString(1));
            art.setAuthor(cursor.getString(2));
            art.setYear(cursor.getInt(3));
            art.setDescription(cursor.getString(4));
            art.setRoom(cursor.getInt(5));
            art.setImage(cursor.getBlob(6));
            art.setRating(cursor.getFloat(7));
            art.setEdit(cursor.getInt(8));

            Log.d("getArt(" + id + ")", art.toString());

        }
        cursor.close();
        db.close();
        // 5. return art
        return art;
    }

    // Get All Art
    public List<Artwork> getAllArt() {
        List<Artwork> art = new LinkedList<Artwork>();

        // 1. build the query
        String query = "SELECT  * FROM " + DB_TABLE;

        // 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build art and add it to list
        Artwork a;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                a = new Artwork();
                a.id = Integer.parseInt(cursor.getString(0));
                a.setTitle(cursor.getString(1));
                a.setAuthor(cursor.getString(2));
                a.setYear(cursor.getInt(3));
                a.setDescription(cursor.getString(4));
                a.setRoom(cursor.getInt(5));
                a.setImage(cursor.getBlob(6));
                a.setRating(cursor.getFloat(7));
                a.setEdit(cursor.getInt(8));
                // Add track to tracks
                art.add(a);
            } while (cursor.moveToNext());
        }

        Log.d("getAllTracks()", art.toString());

        cursor.close();
        // return art
        return art;
    }

    // Updating single art
    public int updateArt(Artwork art) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, art.getTitle()); // get title
        values.put(KEY_AUTHOR, art.getAuthor()); // get author
        values.put(KEY_DESCRIPTION, art.getDescription()); // get album
        values.put(KEY_YEAR, art.getYear()); // get price
        values.put(KEY_ROOM, art.getRoom());
        values.put(KEY_IMAGE, art.getImage());
        values.put(KEY_RATING, art.getRating());
        values.put(KEY_EDIT, art.getEdit());

        // 3. updating row
        int i = db.update(DB_TABLE, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[] { (String.valueOf(art.id)) }); //selection args

        // 4. close
        db.close();

        return i;

    }

    // Deleting single track
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

    public List<Artwork> getRanked(){
        List<Artwork> art = new LinkedList<Artwork>();
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(DB_TABLE, // a. table
                        COLUMNS, // b. column names
                        KEY_RATING+ " > ?", // c. selections
                        new String[] { "0" }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. go over each row, build art and add it to list
        Artwork a;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                a = new Artwork();
                a.id = Integer.parseInt(cursor.getString(0));
                a.setTitle(cursor.getString(1));
                a.setAuthor(cursor.getString(2));
                a.setYear(cursor.getInt(3));
                a.setDescription(cursor.getString(4));
                a.setRoom(cursor.getInt(5));
                a.setImage(cursor.getBlob(6));
                a.setRating(cursor.getFloat(7));
                a.setEdit(cursor.getInt(8));
                // Add track to tracks
                art.add(a);
            } while (cursor.moveToNext());
        }

        Log.d("getAllTracks()", art.toString());

        cursor.close();
        // return art
        return art;
    }
    public List<Artwork> getUnranked(){
        List<Artwork> art = new LinkedList<Artwork>();
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(DB_TABLE, // a. table
                        COLUMNS, // b. column names
                        KEY_RATING+ " = ?", // c. selections
                        new String[] { "0" }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. go over each row, build art and add it to list
        Artwork a;
        if (cursor != null && cursor.moveToFirst()) {
            do {
                a = new Artwork();
                a.id = Integer.parseInt(cursor.getString(0));
                a.setTitle(cursor.getString(1));
                a.setAuthor(cursor.getString(2));
                a.setYear(cursor.getInt(3));
                a.setDescription(cursor.getString(4));
                a.setRoom(cursor.getInt(5));
                a.setImage(cursor.getBlob(6));
                a.setRating(cursor.getFloat(7));
                a.setEdit(cursor.getInt(8));
                // Add track to tracks
                art.add(a);
            } while (cursor.moveToNext());
        }

        Log.d("getAllTracks()", art.toString());

        cursor.close();
        // return art
        return art;
    }

    public Artwork getArtByTitle(String title){

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(DB_TABLE, // a. table
                        COLUMNS, // b. column names
                        KEY_TITLE + " = ?", // c. selections
                        new String[] { title }, // d. selections args
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
            art.setYear(cursor.getInt(3));
            art.setDescription(cursor.getString(4));
            art.setRoom(cursor.getInt(5));
            art.setImage(cursor.getBlob(6));
            art.setRating(cursor.getFloat(7));
            art.setEdit(cursor.getInt(8));

            Log.d("getArt(" + title + ")", art.toString());
        }
        cursor.close();
        db.close();
        // 5. return track
        return art;
    }

    // Getting artwork Count
    public int getArtworkCount() {
        String countQuery = "SELECT  * FROM " + DB_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}
