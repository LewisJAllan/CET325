package com.example.bg50xx.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.CursorAdapter;

import org.w3c.dom.Text;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by Lewis on 26/12/2017.
 */

public class ArtCursorAdapter extends CursorAdapter {

    //Inititialize relevant instruments and variables
    MySQLiteHelper db;
    Artwork art;
    String title, description, author;
    int year, room, edit, id;
    float newrating;
    byte[] image;
    //build constructor
    public ArtCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }
    //locate list to populate the bind view to
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(
                R.layout.contact_list_item,parent,false );
    }


    @Override
    public void bindView(View view, final Context context, final Cursor cursor) {
        //retrieve values from cursor, which connects to database
        id = cursor.getInt(
                cursor.getColumnIndex(MySQLiteHelper.KEY_ID));
        title = cursor.getString(
                cursor.getColumnIndex(MySQLiteHelper.KEY_TITLE));
        author = cursor.getString(
                cursor.getColumnIndex(MySQLiteHelper.KEY_AUTHOR));
        year = cursor.getInt(
                cursor.getColumnIndex(MySQLiteHelper.KEY_YEAR));
        room = cursor.getInt(
                cursor.getColumnIndex(MySQLiteHelper.KEY_ROOM));
        newrating = cursor.getFloat(
                cursor.getColumnIndex(MySQLiteHelper.KEY_RATING));
        image = cursor.getBlob(
                cursor.getColumnIndex(MySQLiteHelper.KEY_IMAGE));
        edit = cursor.getInt(
                cursor.getColumnIndex(MySQLiteHelper.KEY_EDIT));
        description = cursor.getString(
                cursor.getColumnIndex(MySQLiteHelper.KEY_DESCRIPTION));
        ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
        Bitmap picture = BitmapFactory.decodeStream(imageStream);
        //locate tools to be later populated with database values
        final TextView txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        final TextView txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
        final TextView txtYear = (TextView) view.findViewById(R.id.txtYear);
        final TextView txtRoom = (TextView) view.findViewById(R.id.txtRoom);
        final RatingBar ratingbar = (RatingBar) view.findViewById(R.id.ratingBar);
        final ImageView pic = (ImageView) view.findViewById(R.id.imageDocIcon);
        final TextView txtDescription = (TextView) view.findViewById(R.id.txtDescription);
        final TextView txtEdit = (TextView) view.findViewById(R.id.txtEdit);
        final TextView txtID = (TextView) view.findViewById(R.id.txtID);
        //set the values retrieved from the database
        txtID.setText(String.valueOf(id));
        txtTitle.setText(title);
        txtAuthor.setText(author);
        txtYear.setText(String.valueOf(year));
        txtRoom.setText(String.valueOf(room));
        ratingbar.setRating(newrating);
        pic.setImageBitmap(picture);
        txtDescription.setText(description);
        txtEdit.setText(String.valueOf(edit));
        ratingbar.setFocusable(false);
        Log.d("artTitle", title);
        //update the database with changes set to the rating bar of each list item
        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar,
                                        float rating, boolean fromUser) {
                //open a database connection
                db = new MySQLiteHelper(context);
                int currentID = Integer.parseInt(txtID.getText().toString());
                String currentTitle = txtTitle.getText().toString();
                String currentAuthor = txtAuthor.getText().toString();
                String currentDescription = txtDescription.getText().toString();
                int currentYear = Integer.parseInt(txtYear.getText().toString());
                int currentRoom = Integer.parseInt(txtRoom.getText().toString());
                Bitmap bitmap = ((BitmapDrawable) pic.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                float currentRating = rating;
                int currentEdit = Integer.parseInt(txtEdit.getText().toString());
                //create object of Artwork to pass to the Update operation in the database
                art = new Artwork(currentTitle,currentAuthor, currentDescription,currentYear,currentRoom,imageInByte,currentRating, currentEdit);
                art.id = currentID;
                Log.d("artTitle", art.toString());
                ratingbar.setRating(currentRating);
                //update the database
                db.updateArt(art);
                //close the database connection after the changes are uploaded
                db.close();
            }
        });
    }
}
