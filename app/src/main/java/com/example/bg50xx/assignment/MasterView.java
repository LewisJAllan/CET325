package com.example.bg50xx.assignment;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.database.Cursor;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by Lewis on 18/12/2017.
 */

public class MasterView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private CursorAdapter cursorAdapter = null;
    MySQLiteHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);
        cursorAdapter = new ArtCursorAdapter(this,null,0);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);

        //db = new MySQLiteHelper(this);

//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nhm);
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] bitMapData = stream.toByteArray();
//
//
//        // add art
//        insertArt("Test", "Lewis", 1992, "Test", 1, 5, bitMapData);

        getLoaderManager().initLoader(0, null, this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LayoutInflater li = LayoutInflater.from(MasterView.this);
                View getEmpIdView = li.inflate(R.layout.dialog_get_art, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MasterView.this);
                // set dialog_get_art.xml to alertdialog builder
                alertDialogBuilder.setView(getEmpIdView);

                final EditText titleInput = (EditText) getEmpIdView.findViewById(R.id.dialogTitle);
                final EditText authorInput = (EditText) getEmpIdView.findViewById(R.id.dialogAuthor);
                final EditText yearInput = (EditText) getEmpIdView.findViewById(R.id.dialogYear);
                final EditText descriptionInput = (EditText) getEmpIdView.findViewById(R.id.dialogDescription);
                final EditText roomInput = (EditText) getEmpIdView.findViewById(R.id.dialogRoom);
                final RatingBar ratingInput = (RatingBar) getEmpIdView.findViewById(R.id.dialogRating);
                // set dialog message

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // get user input and set it to result
                                // edit text
                                String title = titleInput.getText().toString();
                                String author = authorInput.getText().toString();
                                int year = Integer.parseInt(yearInput.getText().toString());
                                String description = descriptionInput.getText().toString();
                                int room = Integer.parseInt(roomInput.getText().toString());
                                float rating = ratingInput.getRating();
                                Drawable drawable = getResources().getDrawable(getResources()
                                        .getIdentifier("defaultpic", "drawable", getPackageName()));
                                Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bitmapdata = stream.toByteArray();
                                insertArt(title, author, year, description, room, rating, bitmapdata);
                                restartLoader();
                            }
                        }).create()
                        .show();
            }
        });
    }

    private void insertArt(String title,String author, int year, String description, int room, float rating, byte[] bitmapdata) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.KEY_TITLE,title);
        values.put(MySQLiteHelper.KEY_AUTHOR,author);
        values.put(MySQLiteHelper.KEY_YEAR,year);
        values.put(MySQLiteHelper.KEY_DESCRIPTION,description);
        values.put(MySQLiteHelper.KEY_ROOM,room);
        values.put(MySQLiteHelper.KEY_RATING,rating);
        values.put(MySQLiteHelper.KEY_IMAGE,bitmapdata);
        Uri artUri  = getContentResolver().insert(ArtworkProvider.CONTENT_URI,values);
        Toast.makeText(this,"Created Artwork " + title,Toast.LENGTH_LONG).show();
    }

    private void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, ArtworkProvider.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        cursorAdapter.swapCursor(null);
    }

    @Override
    public void onStart(){

        super.onStart();



    }
}
