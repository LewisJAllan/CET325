package com.example.bg50xx.assignment;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Lewis on 26/12/2017.
 */

public class ArtworkProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.bg50xx.assignment";
    private static final String BASE_PATH = "artwork";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    private static final int ART = 1;
    private static final int ART_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH, ART);
        uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#",ART_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        MySQLiteHelper helper = new MySQLiteHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        Log.d("uri", uri.toString());
        switch (uriMatcher.match(uri)) {
            case ART:
                cursor =  database.query(MySQLiteHelper.DB_TABLE,MySQLiteHelper.COLUMNS,
                        s,null,null,null,MySQLiteHelper.KEY_ID + " ASC");
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case ART:
                // return the mime type of the Content Provider
                return "vnd.android.cursor.dir/artwork";
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        long id = database.insert(MySQLiteHelper.DB_TABLE,null,contentValues);

        if (id > 0) {
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Insertion Failed for URI :" + uri);

    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        int delCount = 0;
        switch (uriMatcher.match(uri)) {
            case ART:
                delCount =  database.delete(MySQLiteHelper.DB_TABLE,s,strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return delCount;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updCount = 0;
        switch (uriMatcher.match(uri)) {
            case ART:
                updCount =  database.update(MySQLiteHelper.DB_TABLE,contentValues,s,strings);
                break;
            default:
                throw new IllegalArgumentException("This is an Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return updCount;
    }
}
