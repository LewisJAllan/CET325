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
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    //initialize variables globally
    private CursorAdapter cursorAdapter = null;
    MySQLiteHelper db;
    ListView list;
    Cursor cursor;
    Artwork art;
    MySQLiteHelper orderDB;
    SQLiteDatabase database;
    //errors when running ArtworkProvider query, variables for creating new query for ordering list
    String table;
    String[] columns;
    String groupBy;
    String having;
    String orderBy;
    String where;
    String[] selectionArgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterview);
        //create adapter for pulling all database information to populate list
        cursorAdapter = new ArtCursorAdapter(this,null,0);
        list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(cursorAdapter);
        //open database connection
        db = new MySQLiteHelper(this);
        //when list item is clicked, view detailed database entry
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(), "Item Clicked",Toast.LENGTH_LONG).show();
                TextView tv = (TextView) view.findViewById(R.id.txtTitle);
                String text = tv.getText().toString();
                Log.d("Clicked", text);
                //pass the Title to find the database entry to populate the activity
                Intent detailIntent = new Intent(MasterView.this, Detailed.class);
                detailIntent.putExtra("id", text);
                startActivity(detailIntent);
            }
        });
        //when list item is pressed longer, open dialog box to delete selected entry
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, final long id){

                LayoutInflater li = LayoutInflater.from(MasterView.this);
                View getDeleteDialog = li.inflate(R.layout.dialog_delete, null);
                //open delete dialog box
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MasterView.this);
                alertDialogBuilder.setView(getDeleteDialog);

                alertDialogBuilder
                        .setNegativeButton(android.R.string.cancel, null)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id) {
                                int adapterPosition = position - list.getHeaderViewsCount();
                                Cursor cursor = (Cursor) cursorAdapter.getItem(adapterPosition);
                                String selected = cursor.getString(cursor.getColumnIndex(MySQLiteHelper.KEY_ID));
                                //get the Artwork object for selected entry
                                art = db.getArt(Integer.parseInt(selected));
                                art.id = Integer.parseInt(selected);
                                Log.d("deleteArt", art.getTitle());
                                //only delete user created entries
                                if(art.getEdit() == 1) {
                                    db.deleteArt(art);
                                    Toast.makeText(view.getContext(), "Record deleted.", Toast.LENGTH_LONG).show();
                                    restartLoader();
                                }
                                else {
                                    Toast.makeText(view.getContext(), "Cannot delete this record.", Toast.LENGTH_LONG).show();
                                }
                    }
                }).create().show();
                return true;
            }

        });

        //Adding a new user created entry
        getLoaderManager().initLoader(0, null, this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                LayoutInflater li = LayoutInflater.from(MasterView.this);
                View getEmpIdView = li.inflate(R.layout.dialog_get_art, null);
                //open dialog box for adding a new entry
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MasterView.this);
                // set dialog_get_art.xml to alertdialog builder
                alertDialogBuilder.setView(getEmpIdView);
                //retieve instruments for user interaction
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
                                //creae Artwork object to easily pass to the database to update then close connection
                                art = new Artwork(title, author, description, year, room, bitmapdata, rating, 1);
                                db.addArt(art);
                                Toast.makeText(view.getContext(),"Created Artwork " + title,Toast.LENGTH_LONG).show();
                                db.close();
                                //refresh the list to pull the new data entry with the others
                                restartLoader();
                            }
                        }).create()
                        .show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent myIntent;
        orderDB = new MySQLiteHelper(this);
        database = orderDB.getReadableDatabase();

        // Make sure table is immutable
        table = MySQLiteHelper.DB_TABLE;
        columns = new String[]{"*"};
        groupBy = null;
        having = null;
        orderBy = null;
        where = null;
        selectionArgs = null;

        //go to the selected activity or run  the relevant database query for ordering the list view
        switch (item.getItemId()){
            case R.id.action_ticket:
                db.close();
                myIntent = new Intent(this.getApplication().getApplicationContext(), Ticket.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_map:
                myIntent = new Intent(this.getApplication().getApplicationContext(), MapsActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_home:
                db.close();
                myIntent = new Intent(this.getApplication().getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            case R.id.action_rating:
                orderBy = orderDB.KEY_RATING + " desc";
                cursor = database.query(
                        table,
                        columns,
                        where,
                        selectionArgs,
                        groupBy,
                        having,
                        orderBy);

                int result = cursor.getCount();

                // make sure database is not empty, otherwise set the adapter to null
                if(result == 0 || result < 1){
                    list.setAdapter(null);
                } else{
                    cursorAdapter = new ArtCursorAdapter(this, cursor, 0);
                    list.setAdapter(cursorAdapter);
                }
                return true;
            case R.id.action_title:
                orderBy = orderDB.KEY_TITLE + " ASC";
                cursor = database.query(
                        table,
                        columns,
                        where,
                        selectionArgs,
                        groupBy,
                        having,
                        orderBy);

                int data = cursor.getCount();

                // make sure database is not empty, otherwise set the adapter to null
                if(data == 0 || data < 1){
                    list.setAdapter(null);
                } else{
                    cursorAdapter = new ArtCursorAdapter(this, cursor, 0);
                    list.setAdapter(cursorAdapter);
                }
                return true;
            case R.id.action_author:
                orderBy = orderDB.KEY_TITLE + " ASC, " + orderDB.KEY_AUTHOR + " ASC";
                cursor = database.query(
                        table,
                        columns,
                        where,
                        selectionArgs,
                        groupBy,
                        having,
                        orderBy);

                int tally = cursor.getCount();

                // make sure database is not empty, otherwise set the adapter to null
                if(tally == 0 || tally < 1){
                    list.setAdapter(null);
                } else{
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
            //Ranked and Unranked can succesfully run the Artwork Provider query
            case R.id.action_ranked:
                String[] zero = new String[]{"0"};
                cursor = getContentResolver().query(ArtworkProvider.CONTENT_URI,null,MySQLiteHelper.KEY_RATING + " > ?",zero,null, null);
                Log.d("cursor", cursor.toString());
                int rank = cursor.getCount();
                // make sure database is not empty, otherwise set the adapter to null
                if(rank == 0 || rank < 1){
                    list.setAdapter(null);
                } else {
                    cursorAdapter = new ArtCursorAdapter(this, cursor, 0);
                    list.setAdapter(cursorAdapter);
                }
                return true;
            case R.id.action_unranked:
                String[] nought = new String[]{"0"};
                cursor = getContentResolver().query(ArtworkProvider.CONTENT_URI,null,MySQLiteHelper.KEY_RATING + " = ?",nought,null, null);
                Log.d("cursor", cursor.toString());
                int unrank = cursor.getCount();
                // make sure database is not empty, otherwise set the adapter to null
                if(unrank == 0 || unrank < 1){
                    list.setAdapter(null);
                } else {
                    cursorAdapter = new ArtCursorAdapter(this, cursor, 0);
                    list.setAdapter(cursorAdapter);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //refresh the list with the latest database
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
