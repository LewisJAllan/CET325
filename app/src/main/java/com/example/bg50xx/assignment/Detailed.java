package com.example.bg50xx.assignment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;

/**
 * Created by Lewis on 02/01/2018.
 */

public class Detailed extends AppCompatActivity {

    private String ID;
    boolean editable;
    float rating, currentrating;
    String title, author, description;
    int year, room, edit;
    Bitmap picture;
    EditText editTitle;
    RatingBar ratingBar;
    EditText editAuthor;
    EditText editDescription;
    EditText editRoom;
    EditText editYear;
    ImageView pic;
    byte[] image;
    final ViewGroup nullParent = null;

    public String getID(){
        return this.ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }

//    public float getRating(){
//        return this.rating;
//    }
//
//    public void setRating(float rating){
//        this.rating = rating;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        Intent intent = getIntent();
        setID(intent.getStringExtra("id"));

        ratingBar = (RatingBar) findViewById(R.id.ratingDetailed);
        editTitle = (EditText) findViewById(R.id.editDetTitle);
        editAuthor = (EditText) findViewById(R.id.editDetAuthor);
        editDescription = (EditText) findViewById(R.id.editDetDescription);
        editRoom = (EditText) findViewById(R.id.editDetRoom);
        editYear = (EditText) findViewById(R.id.editDetYear);
        pic = (ImageView) findViewById(R.id.imgDetailed);

        Cursor cursor = getContentResolver().query(ArtworkProvider.CONTENT_URI,null,MySQLiteHelper.KEY_ID + " = ?",new String[] {getID()},null, null);
        try {
            title = cursor.getString(
                    cursor.getColumnIndex(MySQLiteHelper.KEY_TITLE));
            author = cursor.getString(
                    cursor.getColumnIndex(MySQLiteHelper.KEY_AUTHOR));
            year = cursor.getInt(
                    cursor.getColumnIndex(MySQLiteHelper.KEY_YEAR));
            room = cursor.getInt(
                    cursor.getColumnIndex(MySQLiteHelper.KEY_ROOM));
            currentrating = cursor.getFloat(
                    cursor.getColumnIndex(MySQLiteHelper.KEY_RATING));
            image = cursor.getBlob(
                    cursor.getColumnIndex(MySQLiteHelper.KEY_IMAGE));
            edit = cursor.getInt(
                    cursor.getColumnIndex(MySQLiteHelper.KEY_EDIT));
            description = cursor.getString(
                    cursor.getColumnIndex(MySQLiteHelper.KEY_DESCRIPTION));
            ByteArrayInputStream imageStream = new ByteArrayInputStream(image);
            picture = BitmapFactory.decodeStream(imageStream);
        }catch(NullPointerException e){
            Toast.makeText(this,"No item brought back from database",Toast.LENGTH_LONG).show();
        }

        editTitle.setText(title);
        editAuthor.setText(author);
        editYear.setText(String.valueOf(year));
        editRoom.setText(String.valueOf(room));
        ratingBar.setRating(currentrating);
        pic.setImageBitmap(picture);
        editDescription.setText(description);

        editable = (edit>0?true:false);
        cursor.close();
    }
}
