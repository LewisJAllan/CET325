package com.example.bg50xx.assignment;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lewis on 18/12/2017.
 */

public class MasterView extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private CursorAdapter cursorAdapter = null;
    MySQLiteHelper db;
    ListView list;
    List<Artwork> all;
    List<Artwork> Ranked;
    List<Artwork> Unranked;
    SQLiteDatabase database;
    Cursor cursor;
    Artwork art;
    int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);
        cursorAdapter = new ArtCursorAdapter(this,null,0);
        list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);
        db = new MySQLiteHelper(this);
        selected = 1;


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
                        .setNegativeButton(android.R.string.cancel, null)
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
                                bitmap = Bitmap.createScaledBitmap(bitmap, 600, 400, false);
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] bitmapdata = stream.toByteArray();
                                insertArt(title, author, year, description, room, rating, bitmapdata, 1);
                                restartLoader();
                            }
                        }).create()
                        .show();
            }
        });
        try {
            all = db.getAllArt();
        }catch(NullPointerException e){
            Toast.makeText(this,"No Data.",Toast.LENGTH_SHORT).show();
            all = null;
        }
        try{
            Ranked = db.getRanked();
        }catch(NullPointerException e){
            Toast.makeText(this,"No Ranked Data.",Toast.LENGTH_SHORT).show();
            Ranked = null;
        }
        try{
            Unranked = db.getUnranked();
        }catch(NullPointerException e){
            Toast.makeText(this,"No Unranked Data.",Toast.LENGTH_SHORT).show();
            Unranked = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;

        switch (item.getItemId()){
            case R.id.action_ticket:
                myIntent = new Intent(this.getApplication().getApplicationContext(), Ticket.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_home:
                myIntent = new Intent(this.getApplication().getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_ranked:
                String[] zero = new String[]{"0"};
                cursor = getContentResolver().query(ArtworkProvider.CONTENT_URI,null,MySQLiteHelper.KEY_RATING + " > ?",zero,null, null);
                Log.d("cursor", cursor.toString());
                int data = cursor.getCount();

                // make sure database is not empty, otherwise set the adapter to null
                if(data == 0 || data < 1){
                    list.setAdapter(null);
                } else {
                    cursorAdapter = new ArtCursorAdapter(this, cursor, 0);
                    list.setAdapter(cursorAdapter);
                }
                return true;
            case R.id.action_gallery:
                cursor = getContentResolver().query(ArtworkProvider.CONTENT_URI,null,null,null,null, null);
                Log.d("cursor", cursor.toString());
                int alld = cursor.getCount();

                // make sure database is not empty, otherwise set the adapter to null
                if(alld== 0 || alld < 1){
                    list.setAdapter(null);
                } else {
                    cursorAdapter = new ArtCursorAdapter(this, cursor, 0);
                    list.setAdapter(cursorAdapter);
                }
                return true;
//            case R.id.action_user:
//                createUserDialog();
//                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void insertArt(String title,String author, int year, String description, int room, float rating, byte[] bitmapdata, int edit) {
        art = new Artwork(title, author, description, year, room, bitmapdata, rating, edit);
        db.addArt(art);
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
    public void onLoadFinished(android.content.Loader<Cursor> loader, Cursor cursor) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        cursorAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(android.content.Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        cursorAdapter.swapCursor(null);
    }
}
